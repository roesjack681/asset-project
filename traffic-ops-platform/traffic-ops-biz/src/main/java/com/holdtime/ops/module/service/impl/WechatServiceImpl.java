package com.holdtime.ops.module.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.io.FileUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;
import cn.iocoder.yudao.module.infra.dal.mysql.file.FileMapper;
import cn.iocoder.yudao.module.infra.framework.file.core.client.FileClient;
import cn.iocoder.yudao.module.infra.framework.file.core.utils.FileTypeUtils;
import cn.iocoder.yudao.module.infra.service.file.FileConfigService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.holdtime.ops.module.conf.WechatConfig;
import com.holdtime.ops.module.entity.HdtmWeixinJssdkSignatureDO;
import com.holdtime.ops.module.entity.HdtmWeixinTokenDO;
import com.holdtime.ops.module.entity.mapper.HdtmWeixinJssdkSignatureMapper;
import com.holdtime.ops.module.entity.mapper.HdtmWeixinTokenMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.HdtmWeixinJssdkSignatureRespVO;
import com.holdtime.ops.module.model.vo.HdtmWeixinTokenRespVO;
import com.holdtime.ops.module.service.WechatService;
import com.holdtime.ops.module.weixin.sdk.WeixinUtil;
import com.holdtime.ops.module.weixin.sdk.api.model.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
@Slf4j
public class WechatServiceImpl implements WechatService {
    @Resource
    private HdtmWeixinTokenMapper hdtmWeixinTokenMapper;
    @Resource
    private HdtmWeixinJssdkSignatureMapper hdtmWeixinJssdkSignatureMapper;
    @Resource
    private FileMapper fileMapper;

    @Resource
    private FileConfigService fileConfigService;

    @Override
    public HdtmWeixinJssdkSignatureRespVO getWeixinJssdkSignature(String url) {
        HdtmWeixinJssdkSignatureRespVO voInfo = new HdtmWeixinJssdkSignatureRespVO();
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("微信JS SDK前面获取失败：缺少必要参数！");
        }

        try {
            HdtmWeixinTokenRespVO weixinToken = getWeixinToken();
            if (weixinToken == null) {
                return null;
            }

            LambdaQueryWrapperX<HdtmWeixinJssdkSignatureDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            lambdaQueryWrapperX.eq(HdtmWeixinJssdkSignatureDO::getId, weixinToken.getId());
            lambdaQueryWrapperX.eq(HdtmWeixinJssdkSignatureDO::getUrl, url);
            List<HdtmWeixinJssdkSignatureDO> list = hdtmWeixinJssdkSignatureMapper.selectList(lambdaQueryWrapperX);
            if (list == null || list.isEmpty()) {
                HdtmWeixinJssdkSignatureDO  jsSdkSignature = new HdtmWeixinJssdkSignatureDO();
                jsSdkSignature.setId(WechatConfig.getAppId());
                jsSdkSignature.setUrl(url);
                jsSdkSignature.setJsapiTicket(weixinToken.getJsapiTicket());
                jsSdkSignature.setTimessamp(String.valueOf(weixinToken.getGetTokenMillis()));
                resetJsSdkSignature(jsSdkSignature);
                if (StringUtils.isNotBlank(jsSdkSignature.getJsapiTicket())) {
                    hdtmWeixinJssdkSignatureMapper.insert(jsSdkSignature);
                    BeanUtils.copyProperties(jsSdkSignature, voInfo);
                    return voInfo;
                }
            } else {
                HdtmWeixinJssdkSignatureDO jsSdkSignature = list.get(0);
                if (weixinToken.getJsapiTicket().equals(jsSdkSignature.getJsapiTicket())) {
                    BeanUtils.copyProperties(jsSdkSignature, voInfo);
                    return voInfo;
                }

                jsSdkSignature.setJsapiTicket(weixinToken.getJsapiTicket());
                jsSdkSignature.setTimessamp(String.valueOf(weixinToken.getGetTokenMillis()));
                resetJsSdkSignature(jsSdkSignature);

                LambdaUpdateWrapper<HdtmWeixinJssdkSignatureDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(HdtmWeixinJssdkSignatureDO::getId, weixinToken.getId());
                updateWrapper.eq(HdtmWeixinJssdkSignatureDO::getUrl, url);
                hdtmWeixinJssdkSignatureMapper.update(jsSdkSignature, updateWrapper);

                BeanUtils.copyProperties(jsSdkSignature, voInfo);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return voInfo;
    }

    @Override
    public HdtmWeixinTokenRespVO getWeixinToken() {
        long getTokenMillis = System.currentTimeMillis()/1000;

        HdtmWeixinTokenRespVO voInfo = new HdtmWeixinTokenRespVO();

        HdtmWeixinTokenDO weixinToken;
        try {
            weixinToken = hdtmWeixinTokenMapper.selectById(WechatConfig.getAppId());
            if (weixinToken == null) {
                weixinToken = new HdtmWeixinTokenDO();
                weixinToken.setId(WechatConfig.getAppId());
                weixinToken.setGetTokenMillis(getTokenMillis);

                weixinToken = resetToken(weixinToken);

                if (weixinToken != null) {
                    hdtmWeixinTokenMapper.insert(weixinToken);
                }
                BeanUtils.copyProperties(weixinToken, voInfo);
            } else {
                //  有效期超过一半就重新获取，防止token过期
                if ((getTokenMillis - weixinToken.getGetTokenMillis()) <= Long.parseLong(WechatConfig.getTokenExpiresIn()) / 2) {
                    BeanUtils.copyProperties(weixinToken, voInfo);
                    return voInfo;
                } else {
                    weixinToken.setGetTokenMillis(getTokenMillis);
                    weixinToken = resetToken(weixinToken);

                    if (weixinToken != null) {
                        hdtmWeixinTokenMapper.updateById(weixinToken);
                    }

                    BeanUtils.copyProperties(weixinToken, voInfo);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return voInfo;
    }

    @Override
    public String downloadWeixinMedia(String mediaId) {
        try (InputStream inputStream = WeixinUtil.downloadMedia(mediaId)) {
            if (inputStream == null) {
                throw exception(OpsErrorCodeConstants.WEIXIN_MEDIA_NOT_EXISTS);
            }

            // 计算默认的 path 名
            byte[] byteArray = IoUtil.readBytes(inputStream);
            String type = FileTypeUtils.getMineType(byteArray);
            if (StringUtils.isEmpty(type)) {
                type = "mp3";
            }

            String name = UUID.randomUUID().toString().replace("-", "") + "." + type;
            String path = FileUtils.generatePath(byteArray, name);
            // 如果 name 为空，则使用 path 填充
            if (StrUtil.isEmpty(name)) {
                name = path;
            }

            // 上传到文件存储器
            FileClient client = fileConfigService.getMasterFileClient();
            Assert.notNull(client, "客户端(master) 不能为空");
            String url = client.upload(byteArray, path, type);

            // 保存到数据库
            FileDO file = new FileDO();
            file.setConfigId(client.getId());
            file.setName(name);
            file.setPath(path);
            file.setUrl(url);
            file.setType(type);
            file.setSize(byteArray.length);
            fileMapper.insert(file);

            return url;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * 重新生成并设定传入的jsSdkSignature
     *
     * @param jsSdkSignature 微信票据
     */
    private void resetJsSdkSignature(HdtmWeixinJssdkSignatureDO jsSdkSignature) {
        try{
            jsSdkSignature.setNonceStr(WeixinUtil.getRandomString(17));
            String signature = WeixinUtil.getJsSdkSignature(jsSdkSignature.getJsapiTicket(), jsSdkSignature.getTimessamp(), jsSdkSignature.getNonceStr(), jsSdkSignature.getUrl());
            jsSdkSignature.setSignature(signature);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * 重新获取并设定传入的weixinToken
     * @param weixinToken 微信Token持久化对象
     * @return 微信Token持久化对象
     */
    private HdtmWeixinTokenDO resetToken(HdtmWeixinTokenDO weixinToken) {
        AccessToken token = WeixinUtil.getAccessToken();
        if (token == null || StringUtils.isBlank(token.getToken())) {
            log.error("无法获取微信AccessToken");
            return null;
        }

        weixinToken.setAccessToken(token.getToken());
        //  获取调用微信JS接口的临时票据（有效期2小时）
        String jsapiTicket = WeixinUtil.getJsapiTicket(token.getToken());
        if (StringUtils.isBlank(jsapiTicket)) {
            log.error("无法获取微信AccessToken");
            return null;
        }
        weixinToken.setJsapiTicket(jsapiTicket);
        weixinToken.setGetTicketMillis(weixinToken.getGetTokenMillis());
        return weixinToken;
    }
}
