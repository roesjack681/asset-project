package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.HdtmWeixinJssdkSignatureService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.HdtmWeixinJssdkSignatureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.HdtmWeixinJssdkSignatureMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 微信JS SDK签名信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class HdtmWeixinJssdkSignatureServiceImpl implements HdtmWeixinJssdkSignatureService {

    @Resource
    private HdtmWeixinJssdkSignatureMapper weixinJssdkSignatureMapper;

    @Override
    public String createWeixinJssdkSignature(HdtmWeixinJssdkSignatureSaveReqVO createReqVO) {
        // 插入
        HdtmWeixinJssdkSignatureDO weixinJssdkSignature = BeanUtils.toBean(createReqVO, HdtmWeixinJssdkSignatureDO.class);
        weixinJssdkSignatureMapper.insert(weixinJssdkSignature);
        // 返回
        return weixinJssdkSignature.getId();
    }

    @Override
    public void updateWeixinJssdkSignature(HdtmWeixinJssdkSignatureSaveReqVO updateReqVO) {
        // 校验存在
        validateWeixinJssdkSignatureExists(updateReqVO.getId());
        // 更新
        HdtmWeixinJssdkSignatureDO updateObj = BeanUtils.toBean(updateReqVO, HdtmWeixinJssdkSignatureDO.class);
        weixinJssdkSignatureMapper.updateById(updateObj);
    }

    @Override
    public void deleteWeixinJssdkSignature(String id) {
        // 校验存在
        validateWeixinJssdkSignatureExists(id);
        // 删除
        weixinJssdkSignatureMapper.deleteById(id);
    }

    private void validateWeixinJssdkSignatureExists(String id) {
        if (weixinJssdkSignatureMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.WEIXIN_JSSDK_SIGNATURE_NOT_EXISTS);
        }
    }

    @Override
    public HdtmWeixinJssdkSignatureDO getWeixinJssdkSignature(String id) {
        return weixinJssdkSignatureMapper.selectById(id);
    }

    @Override
    public PageResult<HdtmWeixinJssdkSignatureDO> getWeixinJssdkSignaturePage(HdtmWeixinJssdkSignaturePageReqVO pageReqVO) {
        return weixinJssdkSignatureMapper.selectPage(pageReqVO);
    }

}