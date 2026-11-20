package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficMonitorResultsHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsHistoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsHistoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备监测结果历史 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficMonitorResultsHistoryServiceImpl implements TrafficMonitorResultsHistoryService {

    @Resource
    private TrafficMonitorResultsHistoryMapper monitorResultsHistoryMapper;

    @Override
    public String createMonitorResultsHistory(TrafficMonitorResultsHistorySaveReqVO createReqVO) {
        // 插入
        TrafficMonitorResultsHistoryDO monitorResultsHistory = BeanUtils.toBean(createReqVO, TrafficMonitorResultsHistoryDO.class);
        monitorResultsHistoryMapper.insert(monitorResultsHistory);
        // 返回
        return monitorResultsHistory.getId();
    }

    @Override
    public void updateMonitorResultsHistory(TrafficMonitorResultsHistorySaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsHistoryExists(updateReqVO.getId());
        // 更新
        TrafficMonitorResultsHistoryDO updateObj = BeanUtils.toBean(updateReqVO, TrafficMonitorResultsHistoryDO.class);
        monitorResultsHistoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorResultsHistory(String id) {
        // 校验存在
        validateMonitorResultsHistoryExists(id);
        // 删除
        monitorResultsHistoryMapper.deleteById(id);
    }

    private void validateMonitorResultsHistoryExists(String id) {
        if (monitorResultsHistoryMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_HISTORY_NOT_EXISTS);
        }
    }

    @Override
    public TrafficMonitorResultsHistoryDO getMonitorResultsHistory(String id) {
        return monitorResultsHistoryMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficMonitorResultsHistoryDO> getMonitorResultsHistoryPage(TrafficMonitorResultsHistoryPageReqVO pageReqVO) {
        return monitorResultsHistoryMapper.selectPage(pageReqVO);
    }

}