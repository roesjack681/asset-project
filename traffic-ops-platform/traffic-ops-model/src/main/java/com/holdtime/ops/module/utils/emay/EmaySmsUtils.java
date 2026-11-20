package com.holdtime.ops.module.utils.emay;


import cn.hutool.json.JSONUtil;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import cn.zhxu.okhttps.SHttpTask;
import com.holdtime.ops.module.enums.HttpStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * 项目名称： hdtm-sms
 * 功能描述： EmaySmsClinet概述
 * 创 建 人： yanxl
 * 创建日期： 2022年10月08日 23:09:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
@Slf4j
public class EmaySmsUtils {

    public static String sendSingleSms(String host, String appId, String secretKey,
                                                        String signName, SmsSingleReqDTO smsReqDTO) {
        if (StringUtils.isNotBlank(signName)) {
            smsReqDTO.setContent(signName + smsReqDTO.getContent());
        }

        String reqPath = host + "/inter/sendSingleSMS";
        return sendSms(reqPath, appId, secretKey, "UTF-8", true, AESUtil.ALGORITHM_AEPP, smsReqDTO);
    }

    public static String sendSingleSms(String host, String appId, String secretKey, String encode, boolean isGzip,
                                       String algorithm, String signName, SmsSingleReqDTO smsReqDTO) {
        if (StringUtils.isNotBlank(signName)) {
            smsReqDTO.setContent(signName + smsReqDTO.getContent());
        }

        String reqPath = host + "/inter/sendSingleSMS";
        return sendSms(reqPath, appId, secretKey, encode, isGzip, algorithm, smsReqDTO);
    }

    private static String sendSms(String reqPath, String appId, String secretKey, String encode,
                                                   boolean isGzip, String algorithm, SmsBaseReqDTO smsBaseReqDTO) {
        if (StringUtils.isBlank(encode)) {
            encode = "UTF-8";
        }

        SHttpTask shttpTask = OkHttps.sync(reqPath);
        shttpTask.bodyType("application/json");
        shttpTask.charset(StandardCharsets.UTF_8);
        shttpTask.addHeader("appId", appId);
        shttpTask.addHeader("encode", encode);

        String reqJson = JSONUtil.toJsonStr(smsBaseReqDTO);
        try {
            byte[] bytes = reqJson.getBytes(encode);
            if (isGzip) {
                shttpTask.addHeader("gzip", "on");
                bytes = GZipUtil.compress(bytes);
            }

            byte[] parambytes = AESUtil.encrypt(bytes, secretKey.getBytes(), algorithm);
            shttpTask.setBodyPara(parambytes);
            HttpResult httpResult = shttpTask.post();
            HttpStatusEnum httpStatusEnum = HttpStatusEnum.getHttpStatus(httpResult.getStatus());
            if (httpStatusEnum != null) {
                log.error(httpStatusEnum.getDesc());
                return httpStatusEnum.getStatus() + "";
            }

            return httpResult.getHeader("result");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return "ERROR";
    }

    public static void main(String[] args) {
        SmsSingleReqDTO dto = new SmsSingleReqDTO();
        dto.setMobile("15391204131");
        dto.setContent("短信接口测试2！");
        String result = EmaySmsUtils.sendSingleSms("http://shmtn.b2m.cn", "9SDK-EMY-0999-JCZMM",
                "REPLACE_WITH_LOCAL_VALUE", "UTF-8", true,
                "AES/ECB/PKCS5Padding", "【包头市公安局交通管理支队】", dto);
        System.out.println(result);
    }
}
