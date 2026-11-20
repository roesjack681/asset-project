package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.holdtime.ops.module.service.IamAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 统一用户中心回调控制器
 */
@Tag(name = "统一用户中心回调")
@RestController
@RequestMapping("/iam/callback")
@Validated
@Slf4j
public class SnowySSOLoginController {

    @Resource
    private IamAuthService iamAuthService;


    @GetMapping("/sso-login")
    @Operation(summary = "使用ID Token登录")
    @PermitAll
    public CommonResult<AuthLoginRespVO> loginWithIdToken(@RequestParam @NotBlank String token) {
        log.info("收到ID Token登录请求");
        return CommonResult.success(iamAuthService.ssoLogin(token));

    }

    @GetMapping("/pushClient")
    @Operation(summary = "客户端通知")
    @PermitAll
    public CommonResult<?> pushClient(Map<String, String> map) {
        log.info("收到客户端通知");
        iamAuthService.pushClient(map);
        return CommonResult.error(200, "ok");
    }

}
