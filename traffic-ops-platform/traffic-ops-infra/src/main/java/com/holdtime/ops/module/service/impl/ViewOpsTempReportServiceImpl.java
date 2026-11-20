package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewOpsTempReportService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsTempReportDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewOpsTempReportMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * VIEW Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewOpsTempReportServiceImpl implements ViewOpsTempReportService {

    @Resource
    private ViewOpsTempReportMapper viewOpsTempReportMapper;

    @Override
    public String createViewOpsTempReport(ViewOpsTempReportSaveReqVO createReqVO) {
        // 插入
        ViewOpsTempReportDO viewOpsTempReport = BeanUtils.toBean(createReqVO, ViewOpsTempReportDO.class);
        viewOpsTempReportMapper.insert(viewOpsTempReport);
        // 返回
        return viewOpsTempReport.getId();
    }

    @Override
    public void updateViewOpsTempReport(ViewOpsTempReportSaveReqVO updateReqVO) {
        // 校验存在
        validateViewOpsTempReportExists(updateReqVO.getId());
        // 更新
        ViewOpsTempReportDO updateObj = BeanUtils.toBean(updateReqVO, ViewOpsTempReportDO.class);
        viewOpsTempReportMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewOpsTempReport(String id) {
        // 校验存在
        validateViewOpsTempReportExists(id);
        // 删除
        viewOpsTempReportMapper.deleteById(id);
    }

    private void validateViewOpsTempReportExists(String id) {
        if (viewOpsTempReportMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_TEMP_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public ViewOpsTempReportDO getViewOpsTempReport(String id) {
        return viewOpsTempReportMapper.selectById(id);
    }

    @Override
    public PageResult<ViewOpsTempReportDO> getViewOpsTempReportPage(ViewOpsTempReportPageReqVO pageReqVO) {
        return viewOpsTempReportMapper.selectPage(pageReqVO);
    }

}