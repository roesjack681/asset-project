package com.holdtime.ops.module.service;

import cn.iocoder.yudao.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.holdtime.ops.module.utils.IamTokenUtil;

import java.util.Map;

/**
 * 统一用户中心认证服务
 */
public interface IamAuthService {
    /**
     * 从ID Token中获取用户信息
     *
     * @param idToken ID令牌
     * @return 用户信息
     */
    IamTokenUtil.UserInfo getUserInfoFromIdToken(String idToken);

    /**
     * 验证ID Token签名
     *
     * @param idToken ID令牌
     * @return 验证结果
     */
    boolean verifyIdToken(String idToken);

    /**
     * 检查令牌是否过期
     *
     * @param accessToken 访问令牌
     * @return 是否过期
     */
    boolean isTokenExpired(String accessToken);

    /**
     * 单点登录
     *
     * @param idToken ID令牌
     * @return 验证结果
     */
    AuthLoginRespVO ssoLogin(String idToken);

    /**
     * 单点登出
     *
     * @param loginId 用户ID
     */
    void ssoLogout(String loginId);

    /**
     * 服务端通知
     * @param paramMap 通知参数
     */
    void pushClient(Map<String, String> paramMap);
}
