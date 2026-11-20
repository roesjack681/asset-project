package com.holdtime.ops.module.weixin.sdk.api.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.holdtime.ops.module.weixin.sdk.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendTemplateMessage {
	// 微信模板接口
	private final static String SEND_TEMPLAYE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	public static JSONObject sendTemplateMessage(String accessToken, WechatTemplate wechatTemplate) {
		JSONObject jsonObject = null;
		//String jsonString = JSONHandler.toJSON(wechatTemplate);
		String jsonString = JSONArray.toJSONString(wechatTemplate);
		System.out.println("模板内容");
		System.out.println(jsonString);
		System.out.println("我是accessToken");
		System.out.println(accessToken);
		String requestUrl = SEND_TEMPLAYE_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
		try {
			jsonObject = HttpUtil.httpsRequest(requestUrl, "POST", jsonString);
			System.out.println("我是jsonObject");
			System.out.println(jsonObject.toString());
		} catch (Exception e) {
			log.error("模板消息推送失败", e);
		}
		return jsonObject;
	}
}