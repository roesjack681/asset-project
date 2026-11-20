package com.holdtime.ops.module.service;

/**
 * 微信权限中心 Service 接口
 *
 * @author 芋道源码
 */
public interface WechatAuthCenterExtService {

    /**
     * 是否有指定权限
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 是否有权限
     */
    boolean hasSpecifyRoleAuth(Long userId, Long roleId);

}