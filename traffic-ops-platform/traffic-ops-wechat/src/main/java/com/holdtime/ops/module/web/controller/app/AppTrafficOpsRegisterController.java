package com.holdtime.ops.module.web.controller.app;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.system.api.sms.SmsCodeApi;
import cn.iocoder.yudao.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import cn.iocoder.yudao.module.system.controller.admin.auth.vo.AuthSmsLoginReqVO;
import cn.iocoder.yudao.module.system.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.system.enums.sms.SmsSceneEnum;
import com.holdtime.ops.module.conf.WechatConfig;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.service.TrafficOpsRegisterService;
import com.holdtime.ops.module.service.WechatService;
import com.holdtime.ops.module.weixin.sdk.model.SNSUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.*;
import javax.servlet.http.*;
import java.io.IOException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.servlet.ServletUtils.getClientIP;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsRegisterDO;
import com.holdtime.ops.module.service.TrafficOpsRegisterExtService;

@Slf4j
@Tag(name = "用户 APP - 用户中心")
@RestController
@RequestMapping("/traffic/ops-register")
@Validated
public class AppTrafficOpsRegisterController {

    @Resource
    private TrafficOpsRegisterService opsRegisterService;
    @Resource
    private TrafficOpsRegisterExtService opsRegisterExtService;
    @Resource
    private WechatService wechatService;
    @Resource
    private SmsCodeApi smsCodeApi;

    @GetMapping("jumpMenu")
    @Operation(summary = "菜单跳转")
    @Parameter(name = "code", description = "code", required = true)
    public void jumpMenu(HttpServletResponse response, String code, @RequestParam(value = "affiliatedUnit", required = false, defaultValue = "1") String affiliatedUnit) {
        String redirectUrl = "";
        String openId;
        try {
            // 获取微信用户基础信息
            log.info("========微信Code:{}", code);
            SNSUserInfo userInfo = opsRegisterExtService.reAuthByWechatUserInfo(response, "/app-api/traffic/ops-register/jumpMenu?affiliatedUnit=" + affiliatedUnit, code);
            log.info("++++++++微信OpenId:{}", userInfo.getOpenId());

            if (!StringUtils.hasText(userInfo.getOpenId())) {
                return;
            }

            openId = userInfo.getOpenId();

            AppTrafficOpsRegisterPageReqVO pageReqVO = new AppTrafficOpsRegisterPageReqVO();
            pageReqVO.setOpenid(openId);
            PageResult<TrafficOpsRegisterDO> pageResult = opsRegisterService.getOpsRegisterPage(pageReqVO);

            redirectUrl = WechatConfig.getVueHost() +  "/#/register?openid=" + openId + "&affiliatedUnit=" + affiliatedUnit;
            if (!CollUtil.isEmpty(pageResult.getList())){
                if (OpsConstants.STRING_ONE.equals(affiliatedUnit)) {
                    redirectUrl = WechatConfig.getVueHost() +  "/#/nav/home?openid=" + openId + "&userId=" + pageResult.getList().get(0).getUserId() + "&registerId=" + pageResult.getList().get(0).getId();
                } else {
                    redirectUrl = WechatConfig.getVueHost() +  "/#/nav2/home?openid=" + openId + "&userId=" + pageResult.getList().get(0).getUserId() + "&registerId=" + pageResult.getList().get(0).getId();
                }

            }
            response.sendRedirect(redirectUrl);
            return;

        } catch (Exception e) {
            log.error("获取微信发生异常", e);
            redirectUrl += WechatConfig.getVueHost() + "/fail";
        }

        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("重定向发生异常", e);
        }
    }

    @GetMapping("getWechatJsSdkSignatureInfo")
    @Operation(summary = "获取微信Js Sdk签名信息")
    @Parameter(name = "desUrl", description = "当前页面URL", required = true)
    public CommonResult<HdtmWeixinJssdkSignatureRespVO> getWechatJsSdkSignatureInfo(@RequestParam String desUrl) {
//        String url = URLDecoder.decode(desUrl, "UTF-8");
        HdtmWeixinJssdkSignatureRespVO voInfo = wechatService.getWeixinJssdkSignature(desUrl);
        return success(voInfo);
    }

    @GetMapping("/sendSmsValidCode/{phone}")
    @Operation(summary = "发送短信")
    public CommonResult<String> sendSmsValidCode(@PathVariable String phone) {
        SmsCodeSendReqDTO reqDTO = new SmsCodeSendReqDTO();
        reqDTO.setMobile(phone);
        reqDTO.setScene(SmsSceneEnum.MEMBER_LOGIN.getScene());
        reqDTO.setCreateIp(getClientIP());
        smsCodeApi.sendSmsCode(reqDTO);
        return success(null);
    }

    @PostMapping("/create")
    @Operation(summary = "创建人员注册绑定信息")
    public CommonResult<String> createOpsRegister(@Valid @RequestBody AppTrafficOpsRegisterSaveReqVO createReqVO) {
        // 验证验证码
//        AuthSmsLoginReqVO smsCodeUseReqDTO = new AuthSmsLoginReqVO();
//        smsCodeUseReqDTO.setMobile(createReqVO.getPhone());
//        smsCodeUseReqDTO.setCode(createReqVO.getValidCode());
//        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(smsCodeUseReqDTO, SmsSceneEnum.MEMBER_LOGIN.getScene(), getClientIP()));

        return success(opsRegisterExtService.createOpsRegister(createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得人员注册绑定信息")
    @Parameter(name = "openId", description = "微信OpenId", required = true, example = "1024")
    public CommonResult<AppTrafficOpsRegisterRespVO> getOpsRegister(@RequestParam("openId") String openId) {
        return success(opsRegisterExtService.getOpsRegister(openId));
    }

    @GetMapping("/unbind")
    @Operation(summary = "解绑注册信息")
    @Parameter(name = "openId", description = "微信OpenId", required = true, example = "1024")
    public CommonResult<?> unbindOpsRegister(@RequestParam("openId") String openId) {
        opsRegisterExtService.unbindOpsRegister(openId);
        return success(true);
    }

}