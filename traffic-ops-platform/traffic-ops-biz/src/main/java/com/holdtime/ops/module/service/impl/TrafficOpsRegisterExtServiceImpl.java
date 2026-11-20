package com.holdtime.ops.module.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.holdtime.ops.module.entity.TrafficOpsRegisterDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsRegisterMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.AppTrafficOpsRegisterRespVO;
import com.holdtime.ops.module.model.vo.AppTrafficOpsRegisterSaveReqVO;
import com.holdtime.ops.module.service.TrafficOpsRegisterExtService;
import com.holdtime.ops.module.utils.PinyinUtils;
import com.holdtime.ops.module.weixin.sdk.WeixinUtil;
import com.holdtime.ops.module.weixin.sdk.model.SNSUserInfo;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
//import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;


/**
 * 人员注册绑定信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsRegisterExtServiceImpl implements TrafficOpsRegisterExtService {

    @Resource
    private TrafficOpsRegisterMapper opsRegisterMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private AdminUserService adminUserService;

    @Override
    public SNSUserInfo reAuthByWechatUserInfo(HttpServletResponse response, String redirectMapping, String code) throws Exception {
        SNSUserInfo userInfo = null;
        try {
            // code不存在，重新获取code
            if (StringUtils.isBlank(code)) {
                response.sendRedirect(WeixinUtil.spellCodeUrl(redirectMapping, "snsapi_userinfo", null));
            } else {
                // 获取微信用户信息
                userInfo = WeixinUtil.getWeChatUserInfo(code);
            }
        } catch (Exception e) {
            throw new Exception("获取微信授权失败！", e);
        }
        if(userInfo == null) {
            userInfo = new SNSUserInfo();
        }
        return userInfo;
    }

    @Override
    public String createOpsRegister(AppTrafficOpsRegisterSaveReqVO createReqVO) {
        //判断手机号是否在用户表中存在，如果存在可以保存，如果不存在，提示错误信息
        AdminUserDO adminUserDO = userService.getUserByMobile(createReqVO.getPhone());
        if (adminUserDO == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 判断该用户是否已绑定微信，已绑定不允许重复绑定
        LambdaQueryWrapperX<TrafficOpsRegisterDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficOpsRegisterDO::getUserId, adminUserDO.getId());
        List<TrafficOpsRegisterDO> registerDOList = opsRegisterMapper.selectList(lambdaQueryWrapperX);

        // 插入
        TrafficOpsRegisterDO opsRegister = BeanUtils.toBean(createReqVO, TrafficOpsRegisterDO.class);
        opsRegister.setUserId(adminUserDO.getId() + "");
        if (registerDOList.isEmpty()) {
            opsRegisterMapper.insert(opsRegister);

        } else {
            if (StringUtils.isNotBlank(registerDOList.get(0).getOpenid())) {
                throw exception(OpsErrorCodeConstants.OPS_REGISTER_EXISTS);
            }
            opsRegister.setId(registerDOList.get(0).getId());
            opsRegisterMapper.updateById(opsRegister);
        }

        // 用户名不一致，更新信息
        if (!adminUserDO.getNickname().equals(createReqVO.getName())) {
            LambdaUpdateWrapper<AdminUserDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(AdminUserDO::getNickname, createReqVO.getName());
            updateWrapper.eq(AdminUserDO::getId, adminUserDO.getId());
            adminUserMapper.update(adminUserDO, updateWrapper);
        }

        // 返回
        return opsRegister.getId();
    }

    @Override
    public AppTrafficOpsRegisterRespVO getOpsRegister(String openId) {
        return BeanUtils.toBean(getOpsRegisterByOpenId(openId), AppTrafficOpsRegisterRespVO.class);
    }

    @Override
    public List<AppTrafficOpsRegisterRespVO> getAllOpsRegister(Long dispatchUserId, String name) {
        LambdaQueryWrapperX<TrafficOpsRegisterDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.isNotNull(TrafficOpsRegisterDO::getOpenid);
        lambdaQueryWrapperX.likeIfPresent(TrafficOpsRegisterDO::getName, name);

        if (dispatchUserId != null) {
            // 查询派单人部门信息
            AdminUserDO adminUserDO = adminUserMapper.selectById(dispatchUserId);
            if (adminUserDO != null) {
                Set<Long> deptIds = convertSet(deptService.getChildDeptList(adminUserDO.getDeptId()), DeptDO::getId);
                deptIds.add(adminUserDO.getDeptId()); // 包括自身
                lambdaQueryWrapperX.exists("select 1 from system_users u where traffic_ops_register.user_id = u.id and u.dept_id in ('" + StringUtils.join(deptIds, "', '") + "')");
            }
        }
        return BeanUtils.toBean(opsRegisterMapper.selectList(lambdaQueryWrapperX), AppTrafficOpsRegisterRespVO.class);
    }

    @Override
    public void unbindOpsRegister(String openId) {
        TrafficOpsRegisterDO registerDO = getOpsRegisterByOpenId(openId);
        // 解绑记录
        LambdaUpdateWrapper<TrafficOpsRegisterDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(TrafficOpsRegisterDO::getOpenid, null);
        lambdaUpdateWrapper.eq(TrafficOpsRegisterDO::getId, registerDO.getId());
        opsRegisterMapper.update(lambdaUpdateWrapper);
    }

    private TrafficOpsRegisterDO getOpsRegisterByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        LambdaQueryWrapperX<TrafficOpsRegisterDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eqIfPresent(TrafficOpsRegisterDO::getOpenid, openId);

        List<TrafficOpsRegisterDO> registerDOList = opsRegisterMapper.selectList(lambdaQueryWrapperX);
        if (CollUtil.isEmpty(registerDOList) || registerDOList.size() > 1) {
            throw exception(OpsErrorCodeConstants.OPS_REGISTER_ERROR);
        }
        return registerDOList.get(0);
    }

    @Override
    public List<AppTrafficOpsRegisterRespVO> getAllOpsRegister() {
        LambdaQueryWrapperX<TrafficOpsRegisterDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.isNotNull(TrafficOpsRegisterDO::getOpenid);
        return BeanUtils.toBean(opsRegisterMapper.selectList(lambdaQueryWrapperX), AppTrafficOpsRegisterRespVO.class);
    }

    @Override
    public void createOpsUserAndRegister(TrafficOpsRegisterDO opsRegisterDO) {
        //判断手机号是否在用户表中存在，如果存在可以保存，如果不存在，提示错误信息
        AdminUserDO adminUserDO = userService.getUserByMobile(opsRegisterDO.getPhone());
        if (adminUserDO == null) {
            // 生成用户名
            String userName = PinyinUtils.getFirstSpell(opsRegisterDO.getName(), false);
            int checkCnt = 0;
            do {
                // 判断用户名是否唯一
                AdminUserDO existAdminUser = adminUserService.getUserByUsername(userName);
                if (existAdminUser != null) { // 用户名已存在
                    userName += new Random().nextInt(100);
                    checkCnt++;
                } else {
                    // 跳出检查
                    checkCnt = 3;
                }
            } while(checkCnt < 2);

            adminUserDO = new AdminUserDO();
            adminUserDO.setNickname(opsRegisterDO.getName());
            adminUserDO.setUsername(userName);
            adminUserDO.setMobile(opsRegisterDO.getPhone());
            adminUserMapper.insert(adminUserDO);
        }

        // 判断该用户是否已绑定微信，已绑定不允许重复绑定
        LambdaQueryWrapperX<TrafficOpsRegisterDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficOpsRegisterDO::getUserId, adminUserDO.getId());
        List<TrafficOpsRegisterDO> registerDOList = opsRegisterMapper.selectList(lambdaQueryWrapperX);

        // 插入
        opsRegisterDO.setUserId(adminUserDO.getId() + "");
        if (registerDOList.isEmpty()) {
            opsRegisterMapper.insert(opsRegisterDO);

        } else {
            if (StringUtils.isNotBlank(registerDOList.get(0).getOpenid())) {
                throw exception(OpsErrorCodeConstants.OPS_REGISTER_EXISTS);
            }
            opsRegisterDO.setId(registerDOList.get(0).getId());
            opsRegisterMapper.updateById(opsRegisterDO);
        }

        // 用户名不一致，更新信息
        if (!adminUserDO.getNickname().equals(opsRegisterDO.getName())) {
            LambdaUpdateWrapper<AdminUserDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(AdminUserDO::getNickname, opsRegisterDO.getName());
            updateWrapper.eq(AdminUserDO::getId, adminUserDO.getId());
            adminUserMapper.update(adminUserDO, updateWrapper);
        }
    }

}