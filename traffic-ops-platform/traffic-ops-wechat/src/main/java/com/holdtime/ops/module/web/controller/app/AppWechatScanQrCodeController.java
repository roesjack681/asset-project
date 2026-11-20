package com.holdtime.ops.module.web.controller.app;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.conf.WechatConfig;
import com.holdtime.ops.module.entity.PoliceOpsMemberDO;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import com.holdtime.ops.module.entity.TrafficOpsRegisterDO;
import com.holdtime.ops.module.entity.mapper.PoliceOpsMemberMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.model.vo.AppTrafficOpsRegisterPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsQrcodeRespVO;
import com.holdtime.ops.module.service.TrafficOpsQrcodeService;
import com.holdtime.ops.module.service.TrafficOpsRegisterExtService;
import com.holdtime.ops.module.service.TrafficOpsRegisterService;
import com.holdtime.ops.module.weixin.sdk.model.SNSUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 微信扫码")
@RestController
@RequestMapping("/traffic/wechat")
@Validated
@Slf4j
public class AppWechatScanQrCodeController {
    @Resource
    private TrafficOpsRegisterService opsRegisterService;
    @Resource
    private TrafficOpsRegisterExtService opsRegisterExtService;
    @Resource
    private TrafficOpsQrcodeService trafficOpsQrcodeService;
    @Resource
    private TrafficOpsMemberMapper trafficOpsMemberMapper;
    @Resource
    private PoliceOpsMemberMapper policeOpsMemberMapper;

    @GetMapping("/scanEquipQrcode/{qrCodeId}")
    @Operation(summary = "微信扫设备码，未绑定进行绑定，已绑定查看设备信息")
    public void scanEquipQrcode(HttpServletResponse response, String code, @PathVariable("qrCodeId") String qrCodeId) {
        String redirectUrl = "";
        try {
            // 1. 通过code获取openId
            log.info("========微信Code:{}", code);
            SNSUserInfo userInfo = opsRegisterExtService.reAuthByWechatUserInfo(response, "/app-api/traffic/wechat/scanEquipQrcode/" + qrCodeId, code);
            log.info("========微信OpenId:{}", userInfo.getOpenId());

            if (!StringUtils.hasText(userInfo.getOpenId())) {
                return;
            }
            String openId = userInfo.getOpenId();

            // 2. 通过openId查询认证信息
            AppTrafficOpsRegisterPageReqVO pageReqVO = new AppTrafficOpsRegisterPageReqVO();
            pageReqVO.setOpenid(openId);
            PageResult<TrafficOpsRegisterDO> pageResult = opsRegisterService.getOpsRegisterPage(pageReqVO);
            if (CollUtil.isEmpty(pageResult.getList())){
                redirectUrl += WechatConfig.getVueHost() + "/#/gjQrcodeError?msg=" + URLEncoder.encode("您无权限！","UTF-8");
                response.sendRedirect(redirectUrl);
                return;
            }

            // 3. 查询码信息
            TrafficOpsQrcodeDO qrcodeDO = trafficOpsQrcodeService.getOpsQrcode(qrCodeId);
            if (qrcodeDO == null) {
                redirectUrl += WechatConfig.getVueHost() + "/#/gjQrcodeError?msg=" + URLEncoder.encode("信息已不存在,请核实！","UTF-8");
                response.sendRedirect(redirectUrl);
                return;
            }

            // 4. 通过杆件信息查询设备信息
            LambdaQueryWrapper<TrafficOpsMemberDO> memberQueryWrapper = new LambdaQueryWrapper<>();
            memberQueryWrapper.eq(TrafficOpsMemberDO::getEquNumber, qrcodeDO.getId());
            List<TrafficOpsMemberDO> memberDOList = trafficOpsMemberMapper.selectList(memberQueryWrapper);
            if (CollectionUtil.isEmpty(memberDOList)) {
                redirectUrl += WechatConfig.getVueHost() + "/#/gjBindQrcode?openId=" + openId + "&qrCodeId=" + qrCodeId + "&serialNum=" + qrcodeDO.getSerialNum();
            }else{
                redirectUrl += WechatConfig.getVueHost() + "/#/gjBindQrcodeDetail?openId=" + openId + "&qrCodeId=" + qrCodeId + "&id=" + memberDOList.get(0).getId();
            }
            response.sendRedirect(redirectUrl);
        } catch  (Exception e) {
            log.error(e.getMessage(), e);
            try {
                redirectUrl += WechatConfig.getVueHost() + "/#/gjQrcodeError?msg=" + URLEncoder.encode(e.getMessage(),"UTF-8");
                response.sendRedirect(redirectUrl);
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    @GetMapping("/queryOpsQrcodeInfo")
    @Operation(summary = "获得资产二维码信息")
    @Parameter(name = "qrCodeId", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsQrcodeRespVO> getOpsQrcode(@RequestParam("qrCodeId") String qrCodeId) {
        TrafficOpsQrcodeDO opsQrcode = trafficOpsQrcodeService.getOpsQrcode(qrCodeId);
        return success(BeanUtils.toBean(opsQrcode, TrafficOpsQrcodeRespVO.class));
    }

    @GetMapping("/scanPoliceEquipQrcode/{qrCodeId}")
    @Operation(summary = "微信扫公安设备码，未绑定进行绑定，已绑定查看设备信息")
    public void scanPoliceEquipQrcode(HttpServletResponse response, String code, @PathVariable("qrCodeId") String qrCodeId) {
        String redirectUrl = "";
        try {
            // 1. 通过code获取openId
            log.info("========微信Code:{}", code);
            SNSUserInfo userInfo = opsRegisterExtService.reAuthByWechatUserInfo(response, "/app-api/traffic/wechat/scanPoliceEquipQrcode/" + qrCodeId, code);
            log.info("========微信OpenId:{}", userInfo.getOpenId());

            if (!StringUtils.hasText(userInfo.getOpenId())) {
                return;
            }
            String openId = userInfo.getOpenId();

            // 2. 通过openId查询认证信息
            AppTrafficOpsRegisterPageReqVO pageReqVO = new AppTrafficOpsRegisterPageReqVO();
            pageReqVO.setOpenid(openId);
            PageResult<TrafficOpsRegisterDO> pageResult = opsRegisterService.getOpsRegisterPage(pageReqVO);
            if (CollUtil.isEmpty(pageResult.getList())){
                redirectUrl += WechatConfig.getVueHost() + "/#/gjQrcodeError?msg=" + URLEncoder.encode("您无权限！","UTF-8");
                response.sendRedirect(redirectUrl);
                return;
            }

            // 3. 查询码信息
            TrafficOpsQrcodeDO qrcodeDO = trafficOpsQrcodeService.getOpsQrcode(qrCodeId);
            if (qrcodeDO == null) {
                redirectUrl += WechatConfig.getVueHost() + "/#/gjQrcodeError?msg=" + URLEncoder.encode("信息已不存在,请核实！","UTF-8");
                response.sendRedirect(redirectUrl);
                return;
            }

            // 4. 通过杆件信息查询设备信息
            LambdaQueryWrapper<PoliceOpsMemberDO> memberQueryWrapper = new LambdaQueryWrapper<>();
            memberQueryWrapper.eq(PoliceOpsMemberDO::getEquNumber, qrcodeDO.getId());
            List<PoliceOpsMemberDO> memberDOList = policeOpsMemberMapper.selectList(memberQueryWrapper);

            redirectUrl += WechatConfig.getVueHost() + "/#/collectpoliceMember?openId=" + openId + "&qrCodeId=" + qrCodeId + "&serialNum=" + qrcodeDO.getSerialNum();
            if (!CollectionUtil.isEmpty(memberDOList)) {
                redirectUrl += "&id=" + memberDOList.get(0).getId();
            }
            response.sendRedirect(redirectUrl);
        } catch  (Exception e) {
            log.error(e.getMessage(), e);
            try {
                redirectUrl += WechatConfig.getVueHost() + "/#/gjQrcodeError?msg=" + URLEncoder.encode(e.getMessage(),"UTF-8");
                response.sendRedirect(redirectUrl);
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }
}