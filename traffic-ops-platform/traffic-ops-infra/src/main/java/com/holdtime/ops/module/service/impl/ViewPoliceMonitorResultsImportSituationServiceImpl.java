package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewPoliceMonitorResultsImportSituationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewPoliceMonitorResultsImportSituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewPoliceMonitorResultsImportSituationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * VIEW Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewPoliceMonitorResultsImportSituationServiceImpl implements ViewPoliceMonitorResultsImportSituationService {

    @Resource
    private ViewPoliceMonitorResultsImportSituationMapper viewPoliceMonitorResultsImportSituationMapper;

    @Override
    public String createViewPoliceMonitorResultsImportSituation(ViewPoliceMonitorResultsImportSituationSaveReqVO createReqVO) {
        // 插入
        ViewPoliceMonitorResultsImportSituationDO viewPoliceMonitorResultsImportSituation = BeanUtils.toBean(createReqVO, ViewPoliceMonitorResultsImportSituationDO.class);
        viewPoliceMonitorResultsImportSituationMapper.insert(viewPoliceMonitorResultsImportSituation);
        // 返回
        return viewPoliceMonitorResultsImportSituation.getEquId();
    }

    @Override
    public void updateViewPoliceMonitorResultsImportSituation(ViewPoliceMonitorResultsImportSituationSaveReqVO updateReqVO) {
        // 校验存在
        validateViewPoliceMonitorResultsImportSituationExists(updateReqVO.getEquId());
        // 更新
        ViewPoliceMonitorResultsImportSituationDO updateObj = BeanUtils.toBean(updateReqVO, ViewPoliceMonitorResultsImportSituationDO.class);
        viewPoliceMonitorResultsImportSituationMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewPoliceMonitorResultsImportSituation(String id) {
        // 校验存在
        validateViewPoliceMonitorResultsImportSituationExists(id);
        // 删除
        viewPoliceMonitorResultsImportSituationMapper.deleteById(id);
    }

    private void validateViewPoliceMonitorResultsImportSituationExists(String id) {
        if (viewPoliceMonitorResultsImportSituationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_IMPORT_SITUATION_NOT_EXISTS);
        }
    }

    @Override
    public ViewPoliceMonitorResultsImportSituationDO getViewPoliceMonitorResultsImportSituation(String id) {
        return viewPoliceMonitorResultsImportSituationMapper.selectById(id);
    }

    @Override
    public PageResult<ViewPoliceMonitorResultsImportSituationDO> getViewPoliceMonitorResultsImportSituationPage(ViewPoliceMonitorResultsImportSituationPageReqVO pageReqVO) {
        return viewPoliceMonitorResultsImportSituationMapper.selectPage(pageReqVO);
    }

}