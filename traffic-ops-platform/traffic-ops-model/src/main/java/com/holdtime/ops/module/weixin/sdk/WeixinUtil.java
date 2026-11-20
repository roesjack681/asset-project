/**
  * Copyright @ 2016 HoldTime Inner Mongolia Co. Ltd.
  */ 
package com.holdtime.ops.module.weixin.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;
import com.alibaba.fastjson.JSONObject;
import com.holdtime.ops.module.conf.WechatConfig;
import com.holdtime.ops.module.weixin.sdk.api.UserInfoApi;
import com.holdtime.ops.module.weixin.sdk.api.model.AccessToken;
import com.holdtime.ops.module.weixin.sdk.model.SNSUserInfo;
import com.holdtime.ops.module.weixin.sdk.model.WeixinOauth2Token;
import com.holdtime.ops.module.weixin.sdk.util.HttpUtil;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/** 
 * 项目名称： wisdomcloud
 * 功能描述： WeixinUtil概述 微信工具类
 * 创  建 人： yanxiulu
 * 创建日期： 2020年2月17日
 * 修  改 人：  
 * 修改时间：
 * 修改内容：
 * 版      本： 1.0.0
 */
@Slf4j
public class WeixinUtil {
	/**
     * 随机字符串范围
     */
    private static final String[] RANDOM_STRS = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b",
            "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"};
    
    /**
     * 拼接获取code的请求路径
     * @param scope 应用授权作用域，可选值[snsapi_base/snsapi_userinfo];为null时默认为snsapi_base
     * @param state 重定向后会带上state参数[非必需]
     * @return 获取code的请求路径
     */
    public static String spellCodeUrl(String redirectMapping, String scope, String state) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
            // appid
            sb.append(WechatConfig.getAppId());
            // 授权后重定向的回调链接地址[必须参数]
            String redirectUri = WechatConfig.getRedirectHost() + redirectMapping;
            sb.append("&redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8"));
            // 应用授权作用域[必须参数，默认snsapi_base]
            if (!StringUtils.hasText(scope)) {
                scope = "snsapi_base";
            }
            sb.append("&response_type=code&scope=").append(scope);
            // 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值[非必需]
            if (StringUtils.hasText(state)) {
                sb.append("&state=").append(state);
            }
            // 无论直接打开还是做页面302重定向时候，必须带此参数
            sb.append("#wechat_redirect");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        
        return sb.toString();
    }
    
    /**
     * 通过网页获取微信用户OpenID
     * @return
     * @throws Exception 
     */
    public static String getOpenId(String code) throws Exception {
        if (StringUtils.hasText(code)) {
            try{
                WeixinOauth2Token weixinOauth2Token = UserInfoApi.getOauth2AccessToken(WechatConfig.getAppId(),
                        WechatConfig.getAppSecret(), code);
                // 用户标识
                return weixinOauth2Token.getOpenId();
            }catch(Exception e){
                throw new Exception("获取微信用户信息失败！", e);
            }
        }else{
            throw new Exception("对不起，您未同意系统获取信息！");
        }
    }
    
    /**
     * 通过网页获取微信用户信息
     * @return
     * @throws Exception 
     */
    public static SNSUserInfo getWeChatUserInfo(String code) throws Exception {
        if (StringUtils.hasText(code)) {
            try{
                WeixinOauth2Token weixinOauth2Token = UserInfoApi.getOauth2AccessToken(WechatConfig.getAppId(),
                        WechatConfig.getAppSecret(), code);
                // 网页授权接口访问凭证
                String accessToken = weixinOauth2Token.getAccessToken();
                // 用户标识
                String openId = weixinOauth2Token.getOpenId();
                log.info("++++++++授权中的openID:{}", openId);
                // 获取用户信息
                return UserInfoApi.getSNSUserInfo(accessToken, openId);
            }catch(Exception e){
                log.error(e.getMessage(), e);
                throw new Exception("获取微信用户信息失败！");
            }
        }else{
            throw new Exception("对不起，您未同意系统获取信息！");
        }
    }
    
    /**
     * 获取公众号的全局唯一票据(取配置文件中的appid,secret)
     * @return
     */
    public static AccessToken getAccessToken() {
        return getAccessToken(WechatConfig.getAppId(), WechatConfig.getAppSecret());
    }
    
    /**
     * 获取公众号的全局唯一票据
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        String requestUrl = WechatConfig.getAccessTokenUrl().replace("APPID", appid).replace("APPSECRET", appsecret);
        
        // 如果请求成功
        try {
            JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);
            if (null != jsonObject) {
            	log.info(jsonObject.toString());
                if (StringUtils.hasText(jsonObject.getString("access_token"))) {
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
                }else{
                    log.info("获取公众号的全局唯一票据出错，{}", jsonObject);
                }
            }
        } catch (Exception e) {
            //log.error(e.getMessage());
            accessToken = null;
            log.error(e.getMessage(), e);
        }
        
        return accessToken;
    }
    
    /**
     * 获取调用微信JS接口的临时票据（有效期2小时）
     * @param accessToken 公众号的全局唯一票据
     * @return
     */
    public static String getJsapiTicket(String accessToken) {
        String jsapiTicket = null;
        String requestUrl = WechatConfig.getGetJsapiTicketUrl().replace("ACCESS_TOKEN", accessToken);
        try {
        	JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);
        	if (null != jsonObject) {
        		if (StringUtils.hasText(jsonObject.getString("ticket"))) {
                    jsapiTicket =jsonObject.getString("ticket");
                }else{
                    log.error("获取调用微信JS接口的临时票据出错，{}", jsonObject);
                }
        	}
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        
        return jsapiTicket;
    }
    
    /**
     * 获取微信JS-SDK使用权限签名
     * @param jsapi_ticket 调用微信JS接口的临时票据（有效期2小时）
     * @param timestamp 生成签名的时间戳
     * @param nonce 生成签名的随机串
     * @param jsurl 调用JS接口页面的完整URL
     * @return
     */
    public static String getJsSdkSignature(String jsapi_ticket, String timestamp, String nonce, String jsurl) {
        /**
         * 对 jsapi_ticket、 timestamp 和 nonce 按字典排序 对所有待签名参数按照字段名的 ASCII
         * 码从小到大排序（字典序）后，使用 URL 键值对的格式（即key1=value1&key2=value2…）拼接成字符串
         * string1。这里需要注意的是所有参数名均为小写字符。 接下来对 string1 作 sha1 加密，字段名和字段值都采用原始值，不进行
         * URL 转义。即 signature=sha1(string1)。
         * **如果没有按照生成的key1=value&key2=value拼接的话会报错
         */
        //  如果时间戳长度大于10，只取前10位(即到秒)
        if (StringUtils.hasText(timestamp) && timestamp.length() > 10){
            timestamp = timestamp.substring(0, 10);
        }
        
        String[] paramArr = new String[] {
                "jsapi_ticket=" + jsapi_ticket,
                "timestamp=" + timestamp,
                "noncestr=" + nonce,
                "url=" + jsurl
        };
        Arrays.sort(paramArr);
        
        // 将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat("&" + paramArr[1]).concat("&" + paramArr[2]).concat("&" + paramArr[3]);
        System.out.println("拼接之后的content为:" + content);
        String gensignature = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 对拼接后的字符串进行 sha1 加密
            byte[] digest = md.digest(content.toString().getBytes());
            gensignature = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        // 将 sha1 加密后的字符串与 signature 进行对比
        if (gensignature != null) {
            return gensignature.toLowerCase();// 返回signature
        } else {
            return "false";
        }
    }

    public static InputStream downloadMedia(String mediaId) {
        try {
            String downloadUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
            downloadUrl = downloadUrl.replace("ACCESS_TOKEN", getAccessToken().getToken()).replace("MEDIA_ID", mediaId);

            URL urlGet = new URL(downloadUrl);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setConnectTimeout(60000);
            http.setReadTimeout(60000);
            http.connect();
            return http.getInputStream();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    
    /**
     * 获取指定长度的随机字符串(0...9,a...z,A...Z)
     * @param length 要生成的字符串长度
     * @return
     */
    public static String getRandomString(int length) {
        Random rad = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM_STRS[rad.nextInt(RANDOM_STRS.length)]);
        }
        return sb.toString();
    }
    
    /** 
     * 将字节数组转换为十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            strDigest.append(byteToHexStr(byteArray[i]));
        }
        return strDigest.toString();
    }

    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        return new String(tempArr);
    }
}
