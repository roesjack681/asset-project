package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewOpsDjMonitorStatisticsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsDjMonitorStatisticsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewOpsDjMonitorStatisticsMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 电警监控信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewOpsDjMonitorStatisticsServiceImpl implements ViewOpsDjMonitorStatisticsService {

    @Resource
    private ViewOpsDjMonitorStatisticsMapper viewOpsDjMonitorStatisticsMapper;

    @Override
    public String createViewOpsDjMonitorStatistics(ViewOpsDjMonitorStatisticsSaveReqVO createReqVO) {
        // 插入
        ViewOpsDjMonitorStatisticsDO viewOpsDjMonitorStatistics = BeanUtils.toBean(createReqVO, ViewOpsDjMonitorStatisticsDO.class);
        viewOpsDjMonitorStatisticsMapper.insert(viewOpsDjMonitorStatistics);
        // 返回
        return viewOpsDjMonitorStatistics.getId();
    }

    @Override
    public void updateViewOpsDjMonitorStatistics(ViewOpsDjMonitorStatisticsSaveReqVO updateReqVO) {
        // 校验存在
        validateViewOpsDjMonitorStatisticsExists(updateReqVO.getId());
        // 更新
        ViewOpsDjMonitorStatisticsDO updateObj = BeanUtils.toBean(updateReqVO, ViewOpsDjMonitorStatisticsDO.class);
        viewOpsDjMonitorStatisticsMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewOpsDjMonitorStatistics(String id) {
        // 校验存在
        validateViewOpsDjMonitorStatisticsExists(id);
        // 删除
        viewOpsDjMonitorStatisticsMapper.deleteById(id);
    }

    private void validateViewOpsDjMonitorStatisticsExists(String id) {
        if (viewOpsDjMonitorStatisticsMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_DJ_MONITOR_STATISTICS_NOT_EXISTS);
        }
    }

    @Override
    public ViewOpsDjMonitorStatisticsDO getViewOpsDjMonitorStatistics(String id) {
        return viewOpsDjMonitorStatisticsMapper.selectById(id);
    }

    @Override
    public PageResult<ViewOpsDjMonitorStatisticsDO> getViewOpsDjMonitorStatisticsPage(ViewOpsDjMonitorStatisticsPageReqVO pageReqVO) {
        return viewOpsDjMonitorStatisticsMapper.selectPage(pageReqVO);
    }

}