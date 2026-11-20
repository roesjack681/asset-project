package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceMonitorResultsImportDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PoliceMonitorResultsImportDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 公安监测结果导入详情 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorResultsImportDetailServiceImpl implements PoliceMonitorResultsImportDetailService {

    @Resource
    private PoliceMonitorResultsImportDetailMapper monitorResultsImportDetailMapper;

    @Override
    public String createMonitorResultsImportDetail(PoliceMonitorResultsImportDetailSaveReqVO createReqVO) {
        // 插入
        PoliceMonitorResultsImportDetailDO monitorResultsImportDetail = BeanUtils.toBean(createReqVO, PoliceMonitorResultsImportDetailDO.class);
        monitorResultsImportDetailMapper.insert(monitorResultsImportDetail);
        // 返回
        return monitorResultsImportDetail.getId();
    }

    @Override
    public void updateMonitorResultsImportDetail(PoliceMonitorResultsImportDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsImportDetailExists(updateReqVO.getId());
        // 更新
        PoliceMonitorResultsImportDetailDO updateObj = BeanUtils.toBean(updateReqVO, PoliceMonitorResultsImportDetailDO.class);
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
    public PoliceMonitorResultsImportDetailDO getMonitorResultsImportDetail(String id) {
        return monitorResultsImportDetailMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceMonitorResultsImportDetailDO> getMonitorResultsImportDetailPage(PoliceMonitorResultsImportDetailPageReqVO pageReqVO) {
        return monitorResultsImportDetailMapper.selectPage(pageReqVO);
    }

}