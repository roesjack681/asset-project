package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficMonitorResultsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备监测结果 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficMonitorResultsServiceImpl implements TrafficMonitorResultsService {

    @Resource
    private TrafficMonitorResultsMapper monitorResultsMapper;

    @Override
    public String createMonitorResults(TrafficMonitorResultsSaveReqVO createReqVO) {
        if(!createReqVO.getEquType().equals("1") && !createReqVO.getEquType().equals("2")){
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_PARAM_MISSING);
        }
        // 插入
        TrafficMonitorResultsDO monitorResults = BeanUtils.toBean(createReqVO, TrafficMonitorResultsDO.class);
        monitorResultsMapper.insert(monitorResults);
        // 返回
        return monitorResults.getId();
    }

    @Override
    public void updateMonitorResults(TrafficMonitorResultsSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsExists(updateReqVO.getId());
        // 更新
        TrafficMonitorResultsDO updateObj = BeanUtils.toBean(updateReqVO, TrafficMonitorResultsDO.class);
        monitorResultsMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorResults(String id) {
        // 校验存在
        validateMonitorResultsExists(id);
        // 删除
        monitorResultsMapper.deleteById(id);
    }

    private void validateMonitorResultsExists(String id) {
        if (monitorResultsMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_NOT_EXISTS);
        }
    }

    @Override
    public TrafficMonitorResultsDO getMonitorResults(String id) {
        return monitorResultsMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficMonitorResultsDO> getMonitorResultsPage(TrafficMonitorResultsPageReqVO pageReqVO) {
        return monitorResultsMapper.selectPage(pageReqVO);
    }

}