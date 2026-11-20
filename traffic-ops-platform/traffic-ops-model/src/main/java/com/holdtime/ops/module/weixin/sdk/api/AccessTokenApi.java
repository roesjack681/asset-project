package com.holdtime.ops.module.weixin.sdk.api;

import com.alibaba.fastjson.JSONObject;
import com.holdtime.ops.module.weixin.sdk.api.model.AccessToken;
import com.holdtime.ops.module.weixin.sdk.util.HttpUtil;
import org.springframework.util.StringUtils;

public class AccessTokenApi {
    public static String ACCESS_TOKEN_URL= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获取公众号的全局唯一票据
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
        try {
            JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);
            // 如果请求成功
            if (null != jsonObject) {
                if (StringUtils.hasText(jsonObject.getString("access_token"))) {
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
                }else{
                    //log.error(jsonObject.toString());
                    accessToken = null;
                    System.out.println("获取公众号的全局唯一票据出错，" + jsonObject.toString());
                }
            } 
        }catch (Exception e) {
            accessToken = null;
            e.printStackTrace();
        }
        return accessToken;
    }
}
