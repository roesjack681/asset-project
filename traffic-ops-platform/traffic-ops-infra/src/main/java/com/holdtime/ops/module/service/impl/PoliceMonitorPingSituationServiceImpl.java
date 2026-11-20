package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceMonitorPingSituationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorPingSituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PoliceMonitorPingSituationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 公安通断监测近两日状态 Service 实现类
 *
 * @author yanxl
 */
@Service
@Validated
public class PoliceMonitorPingSituationServiceImpl implements PoliceMonitorPingSituationService {

    @Resource
    private PoliceMonitorPingSituationMapper monitorPingSituationMapper;

    @Override
    public String createMonitorPingSituation(PoliceMonitorPingSituationSaveReqVO createReqVO) {
        // 插入
        PoliceMonitorPingSituationDO monitorPingSituation = BeanUtils.toBean(createReqVO, PoliceMonitorPingSituationDO.class);
        monitorPingSituationMapper.insert(monitorPingSituation);
        // 返回
        return monitorPingSituation.getId();
    }

    @Override
    public void updateMonitorPingSituation(PoliceMonitorPingSituationSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorPingSituationExists(updateReqVO.getId());
        // 更新
        PoliceMonitorPingSituationDO updateObj = BeanUtils.toBean(updateReqVO, PoliceMonitorPingSituationDO.class);
        monitorPingSituationMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorPingSituation(String id) {
        // 校验存在
        validateMonitorPingSituationExists(id);
        // 删除
        monitorPingSituationMapper.deleteById(id);
    }

    private void validateMonitorPingSituationExists(String id) {
        if (monitorPingSituationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.POLICE_MONITOR_PING_DETAIL_SITUATION_NOT_EXISTS);
        }
    }

    @Override
    public PoliceMonitorPingSituationDO getMonitorPingSituation(String id) {
        return monitorPingSituationMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceMonitorPingSituationDO> getMonitorPingSituationPage(PoliceMonitorPingSituationPageReqVO pageReqVO) {
        return monitorPingSituationMapper.selectPage(pageReqVO);
    }

}