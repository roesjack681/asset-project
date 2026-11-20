package com.holdtime.ops.module.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hdtm.weixin")
public class WechatConfig {
    /**
     * 与接口配置信息中的Token要一致
     */
    private static String token;

    /**
     * 第三方用户唯一凭证
     */
    private static String appId;

    /**
	 * 第三方用户唯一凭证密钥
     */
    private static String appSecret;
    
    /**
     * token有效期（秒）
     */
    private static String tokenExpiresIn;
    
    private static String accessTokenUrl;
    
    private static String getJsapiTicketUrl;
    
    /**
     * 回调主机头
     */
    private static String redirectHost;

	/**
	 * 前端地址
	 */
	private static String vueHost;
    
    /**
     * 待办提醒模板消息ID
     */
    private static String mgtTodoTempId;
    
    /**
     * 取消工单模板消息ID
     */
    private static String cancelOrderTempId;
    
    /**
     * 派单后通知上报人员
     */
    private static String mgtTodoSlTempId;
	/**
	 * 根据紧急程度推送
	 */
	private static String urgeOrderTempId;


	public static String getToken() {
		return token;
	}

	public void setToken(String token) {
		WechatConfig.token = token;
	}

	public static String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		WechatConfig.appId = appId;
	}

	public static String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		WechatConfig.appSecret = appSecret;
	}

	public static String getTokenExpiresIn() {
		return tokenExpiresIn;
	}

	public void setTokenExpiresIn(String tokenExpiresIn) {
		WechatConfig.tokenExpiresIn = tokenExpiresIn;
	}
	
	public static String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	public void setAccessTokenUrl(String accessTokenUrl) {
		WechatConfig.accessTokenUrl = accessTokenUrl;
	}

	public static String getGetJsapiTicketUrl() {
		return getJsapiTicketUrl;
	}

	public void setGetJsapiTicketUrl(String getJsapiTicketUrl) {
		WechatConfig.getJsapiTicketUrl = getJsapiTicketUrl;
	}

	public static String getRedirectHost() {
		return redirectHost;
	}

	public void setRedirectHost(String redirectHost) {
		WechatConfig.redirectHost = redirectHost;
	}

	public static String getVueHost() {
		return vueHost;
	}

	public void setVueHost(String vueHost) {
		WechatConfig.vueHost = vueHost;
	}

	public static String getMgtTodoTempId() {
		return mgtTodoTempId;
	}

	public void setMgtTodoTempId(String mgtTodoTempId) {
		WechatConfig.mgtTodoTempId = mgtTodoTempId;
	}

	public static String getCancelOrderTempId() {
		return cancelOrderTempId;
	}

	public void setCancelOrderTempId(String cancelOrderTempId) {
		WechatConfig.cancelOrderTempId = cancelOrderTempId;
	}

	public static String getMgtTodoSlTempId() {
		return mgtTodoSlTempId;
	}

	public void setMgtTodoSlTempId(String mgtTodoSlTempId) {
		WechatConfig.mgtTodoSlTempId = mgtTodoSlTempId;
	}

	public static String getUrgeOrderTempId() {
		return urgeOrderTempId;
	}

	public void setUrgeOrderTempId(String urgeOrderTempId) {
		WechatConfig.urgeOrderTempId = urgeOrderTempId;
	}
	
}
