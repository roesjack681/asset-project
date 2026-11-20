package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.BtjczhVioSurveilAttachmentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.BtjczhVioSurveilAttachmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.BtjczhVioSurveilAttachmentMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 违法抓拍附件 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BtjczhVioSurveilAttachmentServiceImpl implements BtjczhVioSurveilAttachmentService {

    @Resource
    private BtjczhVioSurveilAttachmentMapper btjczhVioSurveilAttachmentMapper;

    @Override
    public String createBtjczhVioSurveilAttachment(BtjczhVioSurveilAttachmentSaveReqVO createReqVO) {
        // 插入
        BtjczhVioSurveilAttachmentDO btjczhVioSurveilAttachment = BeanUtils.toBean(createReqVO, BtjczhVioSurveilAttachmentDO.class);
        btjczhVioSurveilAttachmentMapper.insert(btjczhVioSurveilAttachment);
        // 返回
        return btjczhVioSurveilAttachment.getId();
    }

    @Override
    public void updateBtjczhVioSurveilAttachment(BtjczhVioSurveilAttachmentSaveReqVO updateReqVO) {
        // 校验存在
        validateBtjczhVioSurveilAttachmentExists(updateReqVO.getId());
        // 更新
        BtjczhVioSurveilAttachmentDO updateObj = BeanUtils.toBean(updateReqVO, BtjczhVioSurveilAttachmentDO.class);
        btjczhVioSurveilAttachmentMapper.updateById(updateObj);
    }

    @Override
    public void deleteBtjczhVioSurveilAttachment(String id) {
        // 校验存在
        validateBtjczhVioSurveilAttachmentExists(id);
        // 删除
        btjczhVioSurveilAttachmentMapper.deleteById(id);
    }

    private void validateBtjczhVioSurveilAttachmentExists(String id) {
        if (btjczhVioSurveilAttachmentMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.BTJCZH_VIO_SURVEIL_ATTACHMENT_NOT_EXISTS);
        }
    }

    @Override
    public BtjczhVioSurveilAttachmentDO getBtjczhVioSurveilAttachment(String id) {
        return btjczhVioSurveilAttachmentMapper.selectById(id);
    }

    @Override
    public PageResult<BtjczhVioSurveilAttachmentDO> getBtjczhVioSurveilAttachmentPage(BtjczhVioSurveilAttachmentPageReqVO pageReqVO) {
        return btjczhVioSurveilAttachmentMapper.selectPage(pageReqVO);
    }

}