package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficMonitorResultsImportDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsImportDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备监测结果导入信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficMonitorResultsImportDetailServiceImpl implements TrafficMonitorResultsImportDetailService {

    @Resource
    private TrafficMonitorResultsImportDetailMapper monitorResultsImportDetailMapper;

    @Override
    public String createMonitorResultsImportDetail(TrafficMonitorResultsImportDetailSaveReqVO createReqVO) {
        // 插入
        TrafficMonitorResultsImportDetailDO monitorResultsImportDetail = BeanUtils.toBean(createReqVO, TrafficMonitorResultsImportDetailDO.class);
        monitorResultsImportDetailMapper.insert(monitorResultsImportDetail);
        // 返回
        return monitorResultsImportDetail.getId();
    }

    @Override
    public void updateMonitorResultsImportDetail(TrafficMonitorResultsImportDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsImportDetailExists(updateReqVO.getId());
        // 更新
        TrafficMonitorResultsImportDetailDO updateObj = BeanUtils.toBean(updateReqVO, TrafficMonitorResultsImportDetailDO.class);
        monitorResultsImportDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorResultsImportDetail(String id) {
        // 校验存在
        validateMonitorResultsImportDetailExists(id);
        // 删除
        monitorResultsImportDetailMapper.deleteById(id);
    }

    private void validateMonitorResultsImportDetailExists(String id) {
        if (monitorResultsImportDetailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_IMPORT_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficMonitorResultsImportDetailDO getMonitorResultsImportDetail(String id) {
        return monitorResultsImportDetailMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficMonitorResultsImportDetailDO> getMonitorResultsImportDetailPage(TrafficMonitorResultsImportDetailPageReqVO pageReqVO) {
        return monitorResultsImportDetailMapper.selectPage(pageReqVO);
    }

}