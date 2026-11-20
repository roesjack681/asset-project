package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.HdtmWeixinJssdkSignatureRespVO;
import com.holdtime.ops.module.model.vo.HdtmWeixinTokenRespVO;

public interface WechatService {
    /**
     * 获取微信临时票据
     * @param url 请求地址
     */
    HdtmWeixinJssdkSignatureRespVO getWeixinJssdkSignature(String url);

    /**
     * 获取Weixin Token统一方法
     * @return 微信Token
     */
    HdtmWeixinTokenRespVO getWeixinToken();

    /**
     * 从微信下载媒体文件
     * @param mediaId
     * @return
     */
    String downloadWeixinMedia(String mediaId);

}
