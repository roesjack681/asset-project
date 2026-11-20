package com.holdtime.ops.module.web.controller.app;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.conf.WechatConfig;
import com.holdtime.ops.module.entity.TrafficOpsRegisterDO;
import com.holdtime.ops.module.model.vo.AppTrafficOpsRegisterPageReqVO;
import com.holdtime.ops.module.service.TrafficOpsRegisterExtService;
import com.holdtime.ops.module.service.TrafficOpsRegisterService;
import com.holdtime.ops.module.weixin.sdk.model.SNSUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Tag(name = "警务专区 - 一键报修")
@RestController
@RequestMapping("/third/ops-oauth")
@Validated
public class AppTrafficOpsThirdAuthController {
    static Integer WECHAT_ERROR_CODE = 40001;
    private static SymmetricCrypto SYMMETRIC_CRYPTO = new SymmetricCrypto(SymmetricAlgorithm.AES,
            "6667bcb08eb448e97a3fd0421ab2a9cc".getBytes());

    @Resource
    private TrafficOpsRegisterService opsRegisterService;
    @Resource
    private TrafficOpsRegisterExtService opsRegisterExtService;
//    @Resource
//    private WechatService wechatService;
//    @Resource
//    private SmsCodeApi smsCodeApi;

    @GetMapping("/jumpMenu")
    @ResponseBody
    @Operation(summary = "亨达海天跳转一键上报")
    public CommonResult<String> getPubOpenIdOr(HttpServletResponse response, String code, HttpServletRequest request, @RequestParam("data") String data) {
        String redirectUrl = "";
        String openId;
        try {
            if(!StringUtils.hasText(data)){
                log.error("参数缺失");
                return CommonResult.error(WECHAT_ERROR_CODE,"参数缺失！");
            }

            log.info("========微信Code:{}", code);
            SNSUserInfo userInfo = opsRegisterExtService.reAuthByWechatUserInfo(response, "/app-api/third/ops-oauth/jumpMenu?data=" + data, code);
            log.info("++++++++微信OpenId:{}", userInfo.getOpenId());

            if (!StringUtils.hasText(userInfo.getOpenId())) {
                return null;
            }

            openId = userInfo.getOpenId();
            //根据openId查询注册的信息，如未注册此时保存
            AppTrafficOpsRegisterPageReqVO pageReqVO = new AppTrafficOpsRegisterPageReqVO();
            pageReqVO.setOpenid(openId);
            PageResult<TrafficOpsRegisterDO> pageResult = opsRegisterService.getOpsRegisterPage(pageReqVO);
            if (CollUtil.isEmpty(pageResult.getList())){
                String dataParam = SYMMETRIC_CRYPTO.decryptStr(data);
                JSONObject obj = JSONUtil.parseObj(dataParam);
                TrafficOpsRegisterDO registerDO =  new TrafficOpsRegisterDO();
                registerDO.setOpenid(openId);
                registerDO.setName(obj.getStr("name"));
                registerDO.setPhone(obj.getStr("phone"));
                opsRegisterExtService.createOpsUserAndRegister(registerDO);
//                opsRegisterService.createOpsRegister(BeanUtils.toBean(registerDO, AppTrafficOpsRegisterSaveReqVO.class));
            }
            redirectUrl += WechatConfig.getVueHost() +  "/#/report?openid=" + openId;
            //http://ops.nmholdtime.com/opsv/#/nav/report?openid=oiqjk64H_bpt_7gMdNMU9dgKgeZA
            response.sendRedirect(redirectUrl);

            return CommonResult.success(openId);
        } catch (Exception e) {
            log.error("获取微信发生异常", e);
            return CommonResult.error(WECHAT_ERROR_CODE,"获取微信授权异常");
        }
    }

}