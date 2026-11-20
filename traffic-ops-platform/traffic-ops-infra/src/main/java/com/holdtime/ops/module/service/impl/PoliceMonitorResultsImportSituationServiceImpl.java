package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceMonitorResultsImportSituationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportSituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PoliceMonitorResultsImportSituationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 公安监测结果导入近两日状态 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorResultsImportSituationServiceImpl implements PoliceMonitorResultsImportSituationService {

    @Resource
    private PoliceMonitorResultsImportSituationMapper monitorResultsImportSituationMapper;

    @Override
    public String createMonitorResultsImportSituation(PoliceMonitorResultsImportSituationSaveReqVO createReqVO) {
        // 插入
        PoliceMonitorResultsImportSituationDO monitorResultsImportSituation = BeanUtils.toBean(createReqVO, PoliceMonitorResultsImportSituationDO.class);
        monitorResultsImportSituationMapper.insert(monitorResultsImportSituation);
        // 返回
        return monitorResultsImportSituation.getId();
    }

    @Override
    public void updateMonitorResultsImportSituation(PoliceMonitorResultsImportSituationSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsImportSituationExists(updateReqVO.getId());
        // 更新
        PoliceMonitorResultsImportSituationDO updateObj = BeanUtils.toBean(updateReqVO, PoliceMonitorResultsImportSituationDO.class);
        monitorResultsImportSituationMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorResultsImportSituation(String id) {
        // 校验存在
        validateMonitorResultsImportSituationExists(id);
        // 删除
        monitorResultsImportSituationMapper.deleteById(id);
    }

    private void validateMonitorResultsImportSituationExists(String id) {
        if (monitorResultsImportSituationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_IMPORT_SITUATION_NOT_EXISTS);
        }
    }

    @Override
    public PoliceMonitorResultsImportSituationDO getMonitorResultsImportSituation(String id) {
        return monitorResultsImportSituationMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceMonitorResultsImportSituationDO> getMonitorResultsImportSituationPage(PoliceMonitorResultsImportSituationPageReqVO pageReqVO) {
        return monitorResultsImportSituationMapper.selectPage(pageReqVO);
    }

}