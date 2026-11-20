package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsIssueRecordService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIssueRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssueRecordMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 领用记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsIssueRecordServiceImpl implements TrafficOpsIssueRecordService {

    @Resource
    private TrafficOpsIssueRecordMapper opsIssueRecordMapper;

    @Override
    public String createOpsIssueRecord(TrafficOpsIssueRecordSaveReqVO createReqVO) {
        // 插入
        TrafficOpsIssueRecordDO opsIssueRecord = BeanUtils.toBean(createReqVO, TrafficOpsIssueRecordDO.class);
        opsIssueRecordMapper.insert(opsIssueRecord);
        // 返回
        return opsIssueRecord.getId();
    }

    @Override
    public void updateOpsIssueRecord(TrafficOpsIssueRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsIssueRecordExists(updateReqVO.getId());
        // 更新
        TrafficOpsIssueRecordDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsIssueRecordDO.class);
        opsIssueRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsIssueRecord(String id) {
        // 校验存在
        validateOpsIssueRecordExists(id);
        // 删除
        opsIssueRecordMapper.deleteById(id);
    }

    private void validateOpsIssueRecordExists(String id) {
        if (opsIssueRecordMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_ISSUE_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsIssueRecordDO getOpsIssueRecord(String id) {
        return opsIssueRecordMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsIssueRecordDO> getOpsIssueRecordPage(TrafficOpsIssueRecordPageReqVO pageReqVO) {
        return opsIssueRecordMapper.selectPage(pageReqVO);
    }

}