package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsIssueHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIssueHistoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssueHistoryMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 警用装备历史领用记录 Service 实现类
 *
 * @author 杨丽
 */
@Service
@Validated
public class TrafficOpsIssueHistoryServiceImpl implements TrafficOpsIssueHistoryService {

    @Resource
    private TrafficOpsIssueHistoryMapper opsIssueHistoryMapper;

    @Override
    public String createOpsIssueHistory(TrafficOpsIssueHistorySaveReqVO createReqVO) {
        // 插入
        TrafficOpsIssueHistoryDO opsIssueHistory = BeanUtils.toBean(createReqVO, TrafficOpsIssueHistoryDO.class);
        opsIssueHistoryMapper.insert(opsIssueHistory);
        // 返回
        return opsIssueHistory.getId();
    }

    @Override
    public void updateOpsIssueHistory(TrafficOpsIssueHistorySaveReqVO updateReqVO) {
        // 校验存在
        validateOpsIssueHistoryExists(updateReqVO.getId());
        // 更新
        TrafficOpsIssueHistoryDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsIssueHistoryDO.class);
        opsIssueHistoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsIssueHistory(String id) {
        // 校验存在
        validateOpsIssueHistoryExists(id);
        // 删除
        opsIssueHistoryMapper.deleteById(id);
    }

    private void validateOpsIssueHistoryExists(String id) {
        if (opsIssueHistoryMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_ISSUE_HISTORY_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsIssueHistoryDO getOpsIssueHistory(String id) {
        return opsIssueHistoryMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsIssueHistoryDO> getOpsIssueHistoryPage(TrafficOpsIssueHistoryPageReqVO pageReqVO) {
        return opsIssueHistoryMapper.selectPage(pageReqVO);
    }

}