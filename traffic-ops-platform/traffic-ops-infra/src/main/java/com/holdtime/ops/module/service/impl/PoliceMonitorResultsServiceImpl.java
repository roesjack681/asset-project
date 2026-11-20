package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceMonitorResultsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PoliceMonitorResultsMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 公安设备监测结果 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorResultsServiceImpl implements PoliceMonitorResultsService {

    @Resource
    private PoliceMonitorResultsMapper monitorResultsMapper;

    @Override
    public String createMonitorResults(PoliceMonitorResultsSaveReqVO createReqVO) {
        // 插入
        PoliceMonitorResultsDO monitorResults = BeanUtils.toBean(createReqVO, PoliceMonitorResultsDO.class);
        monitorResultsMapper.insert(monitorResults);
        // 返回
        return monitorResults.getId();
    }

    @Override
    public void updateMonitorResults(PoliceMonitorResultsSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorResultsExists(updateReqVO.getId());
        // 更新
        PoliceMonitorResultsDO updateObj = BeanUtils.toBean(updateReqVO, PoliceMonitorResultsDO.class);
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
            throw exception(OpsErrorCodeConstants.POLICE_MONITOR_RESULTS_NOT_EXISTS);
        }
    }

    @Override
    public PoliceMonitorResultsDO getMonitorResults(String id) {
        return monitorResultsMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceMonitorResultsDO> getMonitorResultsPage(PoliceMonitorResultsPageReqVO pageReqVO) {
        return monitorResultsMapper.selectPage(pageReqVO);
    }

}