package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.service.WechatAuthCenterExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 设备维保")
@RestController
@RequestMapping("/traffic/auth-center")
@Validated
public class AppAuthCenterController {

    @Resource
    private WechatAuthCenterExtService wechatAuthCenterExtService;

    /**
     * 有无派单权限
     * @param userId 用户ID
     * @return 是否有权限
     */
    @PostMapping("/hasDispatchOrderAuth")
    @Operation(summary = "有无派单权限")
    public CommonResult<?> hasDispatchOrderAuth(@RequestParam(value = "userId") Long userId) {
        return success(wechatAuthCenterExtService.hasSpecifyRoleAuth(userId, OpsConstants.ROLE_ASSIGN_ID));
    }

    /**
     * 有无查看设备的权限
     * @param userId 用户ID
     * @return 是否有权限
     */
    @PostMapping("/hasViewEquipAuth")
    @Operation(summary = "有无查看设备的权限")
    public CommonResult<?> hasViewEquipAuth(@RequestParam(value = "userId") Long userId) {
        return success(wechatAuthCenterExtService.hasSpecifyRoleAuth(userId, OpsConstants.ROLE_VIEW_EQUIP_ID));
    }

    /**
     * 有无取消订单的权限
     * @param userId 用户ID
     * @return 是否有权限
     */
    @PostMapping("/hasCancelOrderAuth")
    @Operation(summary = "有无取消订单的权限")
    public CommonResult<?> hasCancelOrderAuth(@RequestParam(value = "userId") Long userId) {
        return success(wechatAuthCenterExtService.hasSpecifyRoleAuth(userId, OpsConstants.ROLE_CANCEL_ORDER_ID));
    }
}