package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.HdtmWeixinTokenService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.HdtmWeixinTokenDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.HdtmWeixinTokenMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 微信Token Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class HdtmWeixinTokenServiceImpl implements HdtmWeixinTokenService {

    @Resource
    private HdtmWeixinTokenMapper weixinTokenMapper;

    @Override
    public String createWeixinToken(HdtmWeixinTokenSaveReqVO createReqVO) {
        // 插入
        HdtmWeixinTokenDO weixinToken = BeanUtils.toBean(createReqVO, HdtmWeixinTokenDO.class);
        weixinTokenMapper.insert(weixinToken);
        // 返回
        return weixinToken.getId();
    }

    @Override
    public void updateWeixinToken(HdtmWeixinTokenSaveReqVO updateReqVO) {
        // 校验存在
        validateWeixinTokenExists(updateReqVO.getId());
        // 更新
        HdtmWeixinTokenDO updateObj = BeanUtils.toBean(updateReqVO, HdtmWeixinTokenDO.class);
        weixinTokenMapper.updateById(updateObj);
    }

    @Override
    public void deleteWeixinToken(String id) {
        // 校验存在
        validateWeixinTokenExists(id);
        // 删除
        weixinTokenMapper.deleteById(id);
    }

    private void validateWeixinTokenExists(String id) {
        if (weixinTokenMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.WEIXIN_TOKEN_NOT_EXISTS);
        }
    }

    @Override
    public HdtmWeixinTokenDO getWeixinToken(String id) {
        return weixinTokenMapper.selectById(id);
    }

    @Override
    public PageResult<HdtmWeixinTokenDO> getWeixinTokenPage(HdtmWeixinTokenPageReqVO pageReqVO) {
        return weixinTokenMapper.selectPage(pageReqVO);
    }

}