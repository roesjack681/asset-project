package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsNoticeRecordService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsNoticeRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PublicOpsNoticeRecordMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 消息通知记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsNoticeRecordServiceImpl implements PublicOpsNoticeRecordService {

    @Resource
    private PublicOpsNoticeRecordMapper opsNoticeRecordMapper;

    @Override
    public String createOpsNoticeRecord(PublicOpsNoticeRecordSaveReqVO createReqVO) {
        // 插入
        PublicOpsNoticeRecordDO opsNoticeRecord = BeanUtils.toBean(createReqVO, PublicOpsNoticeRecordDO.class);
        opsNoticeRecordMapper.insert(opsNoticeRecord);
        // 返回
        return opsNoticeRecord.getId();
    }

    @Override
    public void updateOpsNoticeRecord(PublicOpsNoticeRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsNoticeRecordExists(updateReqVO.getId());
        // 更新
        PublicOpsNoticeRecordDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsNoticeRecordDO.class);
        opsNoticeRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsNoticeRecord(String id) {
        // 校验存在
        validateOpsNoticeRecordExists(id);
        // 删除
        opsNoticeRecordMapper.deleteById(id);
    }

    private void validateOpsNoticeRecordExists(String id) {
        if (opsNoticeRecordMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_NOTICE_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsNoticeRecordDO getOpsNoticeRecord(String id) {
        return opsNoticeRecordMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsNoticeRecordDO> getOpsNoticeRecordPage(PublicOpsNoticeRecordPageReqVO pageReqVO) {
        return opsNoticeRecordMapper.selectPage(pageReqVO);
    }

}