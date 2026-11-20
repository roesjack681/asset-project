package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficMonitorResultsImportDetailHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailHistoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsImportDetailHistoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备监测结果导入信息历史 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficMonitorResultsImportDetailHistoryServiceImpl implements TrafficMonitorResultsImportDetailHistoryService {

    @Resource
    private TrafficMonitorResultsImportDetailHistoryMapper monitorResultsImportDetailHistoryMapper;

    @Override
    public String createMonitorResultsImportDetailHistory(TrafficMonitorResultsImportDetailHistorySaveReqVO createReqVO) {
        // 插入
        TrafficMonitorResultsImportDetailHistoryDO monitorResultsImportDetailHistory = BeanUtils.toBean(createReqVO, TrafficMonitorResultsImportDetailHistoryDO.class);
        monitorResultsImportDetailHistoryMapper.insert(monitorResultsImportDetailHistory);
        // 返回
        return monitorResultsImportDetailHistory.getId();
    }

    @Override
    public void updateMonitorResultsImportDetailHistory(TrafficMonitorResultsImportDetailHistorySaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsImportDetailHistoryExists(updateReqVO.getId());
        // 更新
        TrafficMonitorResultsImportDetailHistoryDO updateObj = BeanUtils.toBean(updateReqVO, TrafficMonitorResultsImportDetailHistoryDO.class);
        monitorResultsImportDetailHistoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorResultsImportDetailHistory(String id) {
        // 校验存在
        validateMonitorResultsImportDetailHistoryExists(id);
        // 删除
        monitorResultsImportDetailHistoryMapper.deleteById(id);
    }

    private void validateMonitorResultsImportDetailHistoryExists(String id) {
        if (monitorResultsImportDetailHistoryMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_IMPORT_DETAIL_HISTORY_NOT_EXISTS);
        }
    }

    @Override
    public TrafficMonitorResultsImportDetailHistoryDO getMonitorResultsImportDetailHistory(String id) {
        return monitorResultsImportDetailHistoryMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficMonitorResultsImportDetailHistoryDO> getMonitorResultsImportDetailHistoryPage(TrafficMonitorResultsImportDetailHistoryPageReqVO pageReqVO) {
        return monitorResultsImportDetailHistoryMapper.selectPage(pageReqVO);
    }

}