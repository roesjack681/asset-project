package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficMonitorResultsDailyDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDailyDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsDailyDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备监测结果每日分析详情 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficMonitorResultsDailyDetailServiceImpl implements TrafficMonitorResultsDailyDetailService {

    @Resource
    private TrafficMonitorResultsDailyDetailMapper monitorResultsDailyDetailMapper;

    @Override
    public String createMonitorResultsDailyDetail(TrafficMonitorResultsDailyDetailSaveReqVO createReqVO) {
        // 插入
        TrafficMonitorResultsDailyDetailDO monitorResultsDailyDetail = BeanUtils.toBean(createReqVO, TrafficMonitorResultsDailyDetailDO.class);
        monitorResultsDailyDetailMapper.insert(monitorResultsDailyDetail);
        // 返回
        return monitorResultsDailyDetail.getId();
    }

    @Override
    public void updateMonitorResultsDailyDetail(TrafficMonitorResultsDailyDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsDailyDetailExists(updateReqVO.getId());
        // 更新
        TrafficMonitorResultsDailyDetailDO updateObj = BeanUtils.toBean(updateReqVO, TrafficMonitorResultsDailyDetailDO.class);
        monitorResultsDailyDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorResultsDailyDetail(String id) {
        // 校验存在
        validateMonitorResultsDailyDetailExists(id);
        // 删除
        monitorResultsDailyDetailMapper.deleteById(id);
    }

    private void validateMonitorResultsDailyDetailExists(String id) {
        if (monitorResultsDailyDetailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_DAILY_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficMonitorResultsDailyDetailDO getMonitorResultsDailyDetail(String id) {
        return monitorResultsDailyDetailMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficMonitorResultsDailyDetailDO> getMonitorResultsDailyDetailPage(TrafficMonitorResultsDailyDetailPageReqVO pageReqVO) {
        return monitorResultsDailyDetailMapper.selectPage(pageReqVO);
    }

}