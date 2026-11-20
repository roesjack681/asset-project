package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceMonitorResultsHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsHistoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PoliceMonitorResultsHistoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 公安设备监测结果历史 Service 实现类
 *
 * @author yanxl
 */
@Service
@Validated
public class PoliceMonitorResultsHistoryServiceImpl implements PoliceMonitorResultsHistoryService {

    @Resource
    private PoliceMonitorResultsHistoryMapper monitorResultsHistoryMapper;

    @Override
    public String createMonitorResultsHistory(PoliceMonitorResultsHistorySaveReqVO createReqVO) {
        // 插入
        PoliceMonitorResultsHistoryDO monitorResultsHistory = BeanUtils.toBean(createReqVO, PoliceMonitorResultsHistoryDO.class);
        monitorResultsHistoryMapper.insert(monitorResultsHistory);
        // 返回
        return monitorResultsHistory.getId();
    }

    @Override
    public void updateMonitorResultsHistory(PoliceMonitorResultsHistorySaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsHistoryExists(updateReqVO.getId());
        // 更新
        PoliceMonitorResultsHistoryDO updateObj = BeanUtils.toBean(updateReqVO, PoliceMonitorResultsHistoryDO.class);
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
            throw exception(OpsErrorCodeConstants.POLICE_MONITOR_RESULTS_HISTORY_NOT_EXISTS);
        }
    }

    @Override
    public PoliceMonitorResultsHistoryDO getMonitorResultsHistory(String id) {
        return monitorResultsHistoryMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceMonitorResultsHistoryDO> getMonitorResultsHistoryPage(PoliceMonitorResultsHistoryPageReqVO pageReqVO) {
        return monitorResultsHistoryMapper.selectPage(pageReqVO);
    }

}