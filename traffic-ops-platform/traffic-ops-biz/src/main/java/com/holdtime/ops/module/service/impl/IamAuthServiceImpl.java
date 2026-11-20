package com.holdtime.ops.module.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.util.monitor.TracerUtils;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.yudao.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.yudao.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.dept.vo.post.PostSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.role.RoleSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserSaveReqVO;
import cn.iocoder.yudao.module.system.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.PostDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.UserPostDO;
import cn.iocoder.yudao.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.dept.UserPostMapper;
import cn.iocoder.yudao.module.system.dal.mysql.oauth2.OAuth2AccessTokenMapper;
import cn.iocoder.yudao.module.system.dal.mysql.oauth2.OAuth2RefreshTokenMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.UserRoleMapper;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.dal.redis.oauth2.OAuth2AccessTokenRedisDAO;
import cn.iocoder.yudao.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.yudao.module.system.enums.logger.LoginResultEnum;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.dept.PostService;
import cn.iocoder.yudao.module.system.service.logger.LoginLogService;
import cn.iocoder.yudao.module.system.service.oauth2.OAuth2TokenService;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import cn.iocoder.yudao.module.system.service.permission.RoleService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.conf.HdtmIamConfig;
import com.holdtime.ops.module.service.IamAuthService;
import com.holdtime.ops.module.utils.CommonCryptogramUtil;
import com.holdtime.ops.module.utils.IamTokenUtil;
import com.holdtime.ops.module.utils.JwksHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.SetUtils.asSet;

/**
 * 统一用户中心认证服务实现
 */
@Slf4j
@Service
public class IamAuthServiceImpl implements IamAuthService {

    // ==================== 消息类型常量 ====================
    private static final String MSG_TYPE_LOGOUT_CALL = "logoutCall";
    private static final String MSG_TYPE_SIGNOUT = "signout";
    private static final String MSG_TYPE_IAM_DATA_CHANGE = "iamDataChange";

    // ==================== 参数名称常量 ====================
    private static final String PARAM_SIGN = "sign";
    private static final String PARAM_KEY = "key";
    private static final String PARAM_MSG_TYPE = "msgType";
    private static final String PARAM_LOGIN_ID = "loginId";
    private static final String PARAM_CLIENT = "client";
    private static final String PARAM_TIMESTAMP = "timestamp";
    private static final String PARAM_NONCE = "nonce";
    private static final String PARAM_DEVICE_ID = "deviceId";
    private static final String PARAM_MSG_DATA_TYPE = "msgDataType";
    private static final String PARAM_MSG_DATA = "msgData";

    // ==================== URL路径常量 ====================
    private static final String DEFAULT_PUSH_SERVER_PATH = "/iam/auth/protocol/oidc/pushServer";

    // ==================== 同步消息类型常量 ====================
    private static final String SYNC_MSG_TYPE_ORG_ADD = "orgAdd";
    private static final String SYNC_MSG_TYPE_ORG_UPDATE = "orgUpdate";
    private static final String SYNC_MSG_TYPE_ORG_DELETE = "orgDelete";
    private static final String SYNC_MSG_TYPE_POSITION_ADD = "positionAdd";
    private static final String SYNC_MSG_TYPE_POSITION_UPDATE = "positionUpdate";
    private static final String SYNC_MSG_TYPE_POSITION_DELETE = "positionDelete";
    private static final String SYNC_MSG_TYPE_USER_ADD = "userAdd";
    private static final String SYNC_MSG_TYPE_USER_UPDATE = "userUpdate";
    private static final String SYNC_MSG_TYPE_USER_DELETE = "userDelete";

    // ==================== 用户状态常量 ====================
    private static final int STATUS_NORMAL = 0;
    private static final String GENDER_MALE = "男";
    private static final int SEX_MALE = 1;
    private static final int SEX_FEMALE = 2;

    @Resource
    private HdtmIamConfig hdtmIamConfig;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private LoginLogService loginLogService;

    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserPostMapper userPostMapper;
    @Resource
    private OAuth2AccessTokenMapper oauth2AccessTokenMapper;
    @Resource
    private OAuth2RefreshTokenMapper oauth2RefreshTokenMapper;
    @Resource
    private OAuth2AccessTokenRedisDAO oauth2AccessTokenRedisDAO;

    /**
     * 数据同步处理器策略映射
     */
    private final Map<String, Consumer<JSONObject>> dataSyncHandlers = new HashMap<>();

    /**
     * 初始化数据同步处理器
     */
    @PostConstruct
    public void initDataSyncHandlers() {
        // 部门同步处理器
        dataSyncHandlers.put(SYNC_MSG_TYPE_ORG_ADD, this::syncDeptData);
        dataSyncHandlers.put(SYNC_MSG_TYPE_ORG_UPDATE, this::syncDeptData);
        dataSyncHandlers.put(SYNC_MSG_TYPE_ORG_DELETE, this::handleOrgDelete);

        // 岗位同步处理器
        dataSyncHandlers.put(SYNC_MSG_TYPE_POSITION_ADD, this::syncPositionData);
        dataSyncHandlers.put(SYNC_MSG_TYPE_POSITION_UPDATE, this::syncPositionData);
        dataSyncHandlers.put(SYNC_MSG_TYPE_POSITION_DELETE, this::handlePositionDelete);

        // 用户同步处理器
        dataSyncHandlers.put(SYNC_MSG_TYPE_USER_ADD, this::syncUserData);
        dataSyncHandlers.put(SYNC_MSG_TYPE_USER_UPDATE, this::syncUserData);
        dataSyncHandlers.put(SYNC_MSG_TYPE_USER_DELETE, this::handleUserDelete);
    }

    @Override
    public IamTokenUtil.UserInfo getUserInfoFromIdToken(String idToken) {
        return IamTokenUtil.getUserInfoFromIdToken(idToken);
    }

    @Override
    public boolean verifyIdToken(String idToken) {
        if (StrUtil.isBlank(idToken)) {
            return false;
        }

        try {
            if (Boolean.TRUE.equals(hdtmIamConfig.getUseJwks())) {
                log.debug("使用JWKS端点验证ID Token, URL: {}", hdtmIamConfig.getJwksUrl());
                return JwksHelper.verifyTokenWithJwks(
                        idToken,
                        hdtmIamConfig.getJwksUrl(),
                        hdtmIamConfig.getAlgorithm()
                );
            }

            String publicKey = hdtmIamConfig.getPublicKey();
            if (StrUtil.isBlank(publicKey)) {
                log.warn("未配置公钥，无法验证ID Token");
                return false;
            }

            log.debug("使用本地公钥验证ID Token");
            return IamTokenUtil.verifyToken(hdtmIamConfig.getAlgorithm(), publicKey, idToken);
        } catch (Exception e) {
            log.error("ID Token验证失败", e);
            return false;
        }
    }

    @Override
    public boolean isTokenExpired(String accessToken) {
        try {
            return IamTokenUtil.isTokenExpired(accessToken);
        } catch (Exception e) {
            log.error("检查令牌过期失败", e);
            return true;
        }
    }

    @Override
    public AuthLoginRespVO ssoLogin(String idToken) {
        // 1. 验证ID Token
        boolean verified = verifyIdToken(idToken);
        if (!verified) {
            throw exception(new ErrorCode(400, "ID Token验证失败!"));
        }

        // 2. 从token中获取用户信息
        IamTokenUtil.UserInfo userInfo = getUserInfoFromIdToken(idToken);

        // 3. 获取用户信息
        AdminUserDO adminUserDO = adminUserService.getUserByUsername(userInfo.getAccount());
        if (adminUserDO == null) {
            throw exception(new ErrorCode(500, "登录失败: 用户信息不存在！"));
        }

        // 4. 创建Token并记录登录日志
        return createTokenAfterLoginSuccess(adminUserDO.getId(), adminUserDO.getUsername());
    }

    @Override
    public void ssoLogout(String loginId) {
        if (StrUtil.isBlank(loginId)) {
            log.warn("loginId为空，无法通知认证中心注销");
            return;
        }

        if (!hdtmIamConfig.getEnabled()) {
            log.debug("统一用户中心未启用，跳过注销通知");
            return;
        }

        try {
            String pushServerUrl = getPushServerUrl();
            Map<String, Object> params = buildSignoutParams(loginId);
            String sign = generateSign(params, hdtmIamConfig.getSecretKey());
            params.put(PARAM_SIGN, sign);

            log.info("通知认证中心注销用户，loginId: {}, URL: {}", loginId, pushServerUrl);

            String result = HttpUtil.get(pushServerUrl, params);
            log.info("认证中心注销响应内容: {}", result);

        } catch (Exception e) {
            log.error("通知认证中心注销异常", e);
        }
    }

    private String getPushServerUrl() {
        String pushServerUrl = hdtmIamConfig.getPushServerUrl();
        return StrUtil.isNotBlank(pushServerUrl) ? pushServerUrl : hdtmIamConfig.getBaseUrl() + DEFAULT_PUSH_SERVER_PATH;
    }

    private Map<String, Object> buildSignoutParams(String loginId) {
        long timestamp = System.currentTimeMillis();
        String nonce = UUID.randomUUID().toString().replace("-", "");

        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_MSG_TYPE, MSG_TYPE_SIGNOUT);
        params.put(PARAM_LOGIN_ID, loginId);
        params.put(PARAM_CLIENT, hdtmIamConfig.getClientId());
        params.put(PARAM_TIMESTAMP, String.valueOf(timestamp));
        params.put(PARAM_NONCE, nonce);
        return params;
    }

    @Override
    public void pushClient(Map<String, String> paramMap) {
        if (!hdtmIamConfig.getEnabled()) {
            log.debug("统一用户中心未启用，跳过消息处理");
            return;
        }
        if (!verifySign(paramMap)) {
            log.error("签名验证失败，参数: {}", paramMap);
            throw exception(new ErrorCode(401, "签名验证失败"));
        }

        String msgType = paramMap.get(PARAM_MSG_TYPE);
        if (MSG_TYPE_LOGOUT_CALL.equals(msgType)) {
            handleLogoutCall(paramMap);
        } else if (MSG_TYPE_IAM_DATA_CHANGE.equals(msgType)) {
            handleDataChange(paramMap);
        } else {
            log.warn("未知的消息类型: {}", msgType);
        }
    }

    /**
     * 处理登出调用
     */
    private void handleLogoutCall(Map<String, String> paramMap) {
        try {
            String loginId = paramMap.get(PARAM_LOGIN_ID);
            String deviceId = paramMap.get(PARAM_DEVICE_ID);

            log.info("收到认证中心登出通知，loginId: {}, deviceId: {}", loginId, deviceId);

            revokeUserToken(loginId);

            log.info("登出处理完成，loginId: {}", loginId);

        } catch (Exception e) {
            log.error("处理登出消息失败", e);
            throw exception(new ErrorCode(500, "处理消息失败: " + e.getMessage()));
        }
    }

    /**
     * 处理数据变更
     */
    private void handleDataChange(Map<String, String> paramMap) {
        String msgDataType = paramMap.get(PARAM_MSG_DATA_TYPE);
        String encryptedData = paramMap.get(PARAM_MSG_DATA);

        if (StrUtil.isBlank(msgDataType) || StrUtil.isBlank(encryptedData)) {
            log.warn("数据同步参数不完整，msgDataType: {}, encryptedData: {}", msgDataType, encryptedData);
            return;
        }

        try {
            String jsonStr = CommonCryptogramUtil.doSm4CbcDecrypt(encryptedData);
            log.info("收到统一用户中心数据同步消息，类型: {}, 数据: {}", msgDataType, jsonStr);

            Consumer<JSONObject> handler = dataSyncHandlers.get(msgDataType);
            if (handler != null) {
                handler.accept(JSONUtil.parseObj(jsonStr));
            } else {
                log.warn("未知的数据同步类型: {}", msgDataType);
            }
        } catch (Exception e) {
            log.error("处理数据同步消息失败，msgDataType: {}", msgDataType, e);
            throw exception(new ErrorCode(500, "处理数据同步消息失败: " + e.getMessage()));
        }
    }

    /**
     * 构建签名字符串
     * 签名算法规则：将所有参数按照字典顺序依次排列（key除外，挂在最后面）
     *
     * @param paramMap 请求参数
     * @param secretKey 密钥
     * @param removeSign 是否移除sign参数
     * @return 签名字符串
     */
    private String buildSignString(Map<String, String> paramMap, String secretKey, boolean removeSign) {
        TreeMap<String, String> sortedMap = new TreeMap<>(paramMap);
        if (removeSign) {
            sortedMap.remove(PARAM_SIGN);
        }

        StringBuilder signStr = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            if (StrUtil.isBlank(entry.getValue())) {
                continue;
            }
            if (!first) {
                signStr.append("&");
            }
            signStr.append(entry.getKey()).append("=").append(entry.getValue());
            first = false;
        }

        signStr.append("&").append(PARAM_KEY).append("=").append(secretKey);
        return signStr.toString();
    }

    /**
     * 生成签名
     *
     * @param params 请求参数
     * @param secretKey 密钥
     * @return MD5签名
     */
    private String generateSign(Map<String, Object> params, String secretKey) {
        TreeMap<String, String> sortedMap = new TreeMap<>();
        params.forEach((key, value) -> {
            if (value != null) {
                sortedMap.put(key, value.toString());
            }
        });

        String signStr = buildSignString(sortedMap, secretKey, false);
        return SecureUtil.md5(signStr);
    }

    /**
     * 验证认证中心推送消息的签名
     * 签名算法规则：将所有参数按照字典顺序依次排列（key除外，挂在最后面），然后进行 md5 摘要
     *
     * @param paramMap 请求参数
     * @return 验证结果
     */
    private boolean verifySign(Map<String, String> paramMap) {
        String receivedSign = paramMap.get(PARAM_SIGN);
        if (StrUtil.isBlank(receivedSign)) {
            log.warn("签名为空");
            return false;
        }

        String signStr = buildSignString(paramMap, hdtmIamConfig.getSecretKey(), true);
        String calculatedSign = SecureUtil.md5(signStr);
        boolean result = receivedSign.equalsIgnoreCase(calculatedSign);

        log.debug("签名验证结果: {}, 接收签名: {}, 计算签名: {}", result, receivedSign, calculatedSign);
        return result;
    }

    /**
     * 撤销用户token
     *
     * @param loginId 登录ID（用户名或账号）
     */
    private void revokeUserToken(String loginId) {
        if (StrUtil.isBlank(loginId)) {
            log.warn("loginId为空，无法撤销token");
            return;
        }

        try {
            Long userId = Long.parseLong(loginId);
            AdminUserDO adminUserDO = adminUserService.getUser(userId);
            if (adminUserDO == null) {
                log.warn("用户不存在，loginId: {}", userId);
                return;
            }

            int deletedCount = removeAccessToken(loginId);
            createLoginLog(userId, adminUserDO.getNickname(), UserTypeEnum.ADMIN.getValue(),
                    LoginLogTypeEnum.LOGOUT_DELETE.getType());

            log.info("已撤销用户token，userId: {}, loginId: {}, 删除数量: {}", adminUserDO.getId(), loginId, deletedCount);

        } catch (Exception e) {
            log.error("撤销用户token失败，loginId: {}", loginId, e);
            throw e;
        }
    }

    /**
     * 移除访问令牌
     */
    private int removeAccessToken(String loginId) {
        LambdaQueryWrapper<OAuth2AccessTokenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OAuth2AccessTokenDO::getUserId, loginId);
        List<OAuth2AccessTokenDO> accessTokenList = oauth2AccessTokenMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(accessTokenList)) {
            return 0;
        }

        for (OAuth2AccessTokenDO accessTokenDO : accessTokenList) {
            oauth2AccessTokenMapper.deleteById(accessTokenDO.getId());
            oauth2AccessTokenRedisDAO.delete(accessTokenDO.getAccessToken());
            oauth2RefreshTokenMapper.deleteByRefreshToken(accessTokenDO.getRefreshToken());
        }

        return accessTokenList.size();
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username) {
        createLoginLog(userId, username, UserTypeEnum.ADMIN.getValue(), LoginLogTypeEnum.LOGIN_SOCIAL.getType());
        if (userId != null) {
            adminUserService.updateUserLogin(userId, ServletUtils.getClientIP());
        }

        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(
                userId,
                UserTypeEnum.ADMIN.getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT,
                null
        );
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    private void createLoginLog(Long userId, String username, Integer userType, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    /**
     * 同步部门数据
     */
    private void syncDeptData(JSONObject jsonObject) {
        Long deptId = jsonObject.getLong("id");
        String deptName = jsonObject.getStr("name");
        Long parentId = jsonObject.getLong("parentId");
        Integer sortCode = jsonObject.getInt("sortCode");

        if (deptId == null || StrUtil.isBlank(deptName)) {
            log.warn("部门数据不完整，跳过同步");
            return;
        }

        DeptSaveReqVO saveReqVO = buildDeptSaveReqVO(deptId, deptName, parentId, sortCode);

        DeptDO existDept = deptService.getDept(deptId);
        if (existDept == null) {
            deptService.createDept(saveReqVO);
            log.info("创建部门成功，deptId: {}, deptName: {}", deptId, deptName);
        } else {
            deptService.updateDept(saveReqVO);
            log.info("更新部门成功，deptId: {}, deptName: {}", deptId, deptName);
        }
    }

    private DeptSaveReqVO buildDeptSaveReqVO(Long deptId, String deptName, Long parentId, Integer sortCode) {
        DeptSaveReqVO saveReqVO = new DeptSaveReqVO();
        saveReqVO.setId(deptId);
        saveReqVO.setName(deptName);
        saveReqVO.setParentId(parentId != null ? parentId : 0L);
        saveReqVO.setSort(sortCode);
        saveReqVO.setStatus(STATUS_NORMAL);
        return saveReqVO;
    }

    /**
     * 处理部门删除
     */
    private void handleOrgDelete(JSONObject jsonObject) {
        Long orgId = jsonObject.getLong("id");
        if (orgId != null) {
            deptService.deleteDept(orgId);
            log.info("部门删除同步成功，deptId: {}", orgId);
        }
    }

    /**
     * 同步岗位数据
     */
    private void syncPositionData(JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        Long orgId = jsonObject.getLong("orgId");
        String positionName = jsonObject.getStr("name");
        String code = jsonObject.getStr("code");
        int sortCode = jsonObject.getInt("sortCode");

        if (id == null || StrUtil.isBlank(positionName)) {
            log.warn("岗位数据不完整，跳过同步");
            return;
        }

        PostSaveReqVO postSaveReqVO = buildPostSaveReqVO(id, positionName, code, sortCode);
        PostDO postDO = postService.getPost(id);

        if (postDO == null) {
            postService.createPost(postSaveReqVO);
        } else {
            postService.updatePost(postSaveReqVO);
        }

        syncRoleForPosition(id, positionName, code, sortCode);
        log.info("同步岗位数据，positionId: {}, positionName: {}, orgId: {}", id, positionName, orgId);
    }

    private PostSaveReqVO buildPostSaveReqVO(Long id, String positionName, String code, int sortCode) {
        PostSaveReqVO postSaveReqVO = new PostSaveReqVO();
        postSaveReqVO.setId(id);
        postSaveReqVO.setName(positionName);
        postSaveReqVO.setCode(code);
        postSaveReqVO.setSort(sortCode);
        postSaveReqVO.setStatus(STATUS_NORMAL);
        return postSaveReqVO;
    }

    /**
     * 为岗位同步创建角色
     */
    private void syncRoleForPosition(Long positionId, String positionName, String code, int sortCode) {
        RoleDO roleDO = roleService.getRole(positionId);
        if (roleDO == null) {
            RoleSaveReqVO roleSaveReqVO = new RoleSaveReqVO();
            roleSaveReqVO.setId(positionId);
            roleSaveReqVO.setName(positionName);
            roleSaveReqVO.setCode(code);
            roleSaveReqVO.setSort(sortCode);
            roleSaveReqVO.setStatus(STATUS_NORMAL);
            roleService.createRole(roleSaveReqVO, null);
        }
    }

    /**
     * 处理岗位删除
     */
    private void handlePositionDelete(JSONObject jsonObject) {
        Long positionId = jsonObject.getLong("id");
        if (positionId != null) {
            postService.deletePost(positionId);
            log.info("岗位删除同步成功，positionId: {}", positionId);
        }
    }

    /**
     * 同步用户数据
     */
    private void syncUserData(JSONObject jsonObject) {
        Long userId = jsonObject.getLong("id");
        String name = jsonObject.getStr("name");
        String gender = jsonObject.getStr("gender");
        String phone = jsonObject.getStr("phone");
        String email = jsonObject.getStr("email");
        Long orgId = jsonObject.getLong("orgId");
        Long positionId = jsonObject.getLong("positionId");

        if (userId == null) {
            log.warn("用户数据不完整，跳过同步");
            return;
        }

        UserSaveReqVO userSaveReqVO = buildUserSaveReqVO(userId, name, gender, phone, email, orgId, positionId);

        AdminUserDO existUser = adminUserService.getUserByUsername(phone);
        if (existUser == null) {
            adminUserService.createUser(userSaveReqVO);

            if (positionId != null) {
                permissionService.assignUserRole(userId, asSet(positionId));
            }
            log.info("创建用户成功，userId: {}, username: {}", userId, name);
        } else {
            adminUserService.updateUser(userSaveReqVO);
            log.info("更新用户成功，userId: {}, username: {}", userId, name);
        }

        // 2.2 插入关联岗位
        LambdaQueryWrapper<UserPostDO> deleteLambdaQueryWrapper = new LambdaQueryWrapper<>();
        deleteLambdaQueryWrapper.eq(UserPostDO::getUserId, userId);
        userPostMapper.delete(deleteLambdaQueryWrapper);

        userPostMapper.insert(new UserPostDO().setUserId(userId).setPostId(positionId));
    }

    private UserSaveReqVO buildUserSaveReqVO(Long userId, String name, String gender,
                                            String phone, String email, Long orgId, Long positionId) {
        UserSaveReqVO userSaveReqVO = new UserSaveReqVO();
        userSaveReqVO.setId(userId);
        userSaveReqVO.setNickname(name);
        userSaveReqVO.setUsername(phone);
        userSaveReqVO.setSex(GENDER_MALE.equals(gender) ? SEX_MALE : SEX_FEMALE);
        userSaveReqVO.setMobile(phone);
        userSaveReqVO.setEmail(email);
        userSaveReqVO.setPostIds(asSet(positionId));
        userSaveReqVO.setDeptId(orgId);
        return userSaveReqVO;
    }

    /**
     * 处理用户删除
     */
    private void handleUserDelete(JSONObject jsonObject) {
        Long userId = jsonObject.getLong("id");
        if (userId != null) {
            adminUserService.deleteUser(userId);
            log.info("用户删除同步成功，userId: {}", userId);
        }
    }
}
