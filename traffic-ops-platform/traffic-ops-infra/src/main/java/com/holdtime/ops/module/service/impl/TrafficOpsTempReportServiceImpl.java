package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsTempReportService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsTempReportDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsTempReportMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 交警运维临时报 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsTempReportServiceImpl implements TrafficOpsTempReportService {

    @Resource
    private TrafficOpsTempReportMapper opsTempReportMapper;

    @Override
    public String createOpsTempReport(TrafficOpsTempReportSaveReqVO createReqVO) {
        // 插入
        TrafficOpsTempReportDO opsTempReport = BeanUtils.toBean(createReqVO, TrafficOpsTempReportDO.class);
        opsTempReportMapper.insert(opsTempReport);
        // 返回
        return opsTempReport.getId();
    }

    @Override
    public void updateOpsTempReport(TrafficOpsTempReportSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsTempReportExists(updateReqVO.getId());
        // 更新
        TrafficOpsTempReportDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsTempReportDO.class);
        opsTempReportMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsTempReport(String id) {
        // 校验存在
        validateOpsTempReportExists(id);
        // 删除
        opsTempReportMapper.deleteById(id);
    }

    private void validateOpsTempReportExists(String id) {
        if (opsTempReportMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_TEMP_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsTempReportDO getOpsTempReport(String id) {
        return opsTempReportMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsTempReportDO> getOpsTempReportPage(TrafficOpsTempReportPageReqVO pageReqVO) {
        return opsTempReportMapper.selectPage(pageReqVO);
    }

}