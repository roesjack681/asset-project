package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.UserRoleDO;
import cn.iocoder.yudao.module.system.dal.mysql.permission.UserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.service.WechatAuthCenterExtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 设备维保信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class WechatAuthCenterExtServiceImpl implements WechatAuthCenterExtService {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public boolean hasSpecifyRoleAuth(Long userId, Long roleId) {
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(userId);
        if  (adminUserRespDTO == null) {
            return false;
        }

        LambdaQueryWrapper<UserRoleDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRoleDO::getUserId, adminUserRespDTO.getId());
        lambdaQueryWrapper.eq(UserRoleDO::getRoleId, roleId);
        long count = userRoleMapper.selectCount(lambdaQueryWrapper);
        return count > 0;
    }

}