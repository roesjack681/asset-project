package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficEquipPingMonitorResultService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficEquipPingMonitorResultDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficEquipPingMonitorResultMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 重点考核设备通断检测 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficEquipPingMonitorResultServiceImpl implements TrafficEquipPingMonitorResultService {

    @Resource
    private TrafficEquipPingMonitorResultMapper equipPingMonitorResultMapper;

    @Override
    public String createEquipPingMonitorResult(TrafficEquipPingMonitorResultSaveReqVO createReqVO) {
        // 插入
        TrafficEquipPingMonitorResultDO equipPingMonitorResult = BeanUtils.toBean(createReqVO, TrafficEquipPingMonitorResultDO.class);
        equipPingMonitorResultMapper.insert(equipPingMonitorResult);
        // 返回
        return equipPingMonitorResult.getId();
    }

    @Override
    public void updateEquipPingMonitorResult(TrafficEquipPingMonitorResultSaveReqVO updateReqVO) {
        // 校验存在
        validateEquipPingMonitorResultExists(updateReqVO.getId());
        // 更新
        TrafficEquipPingMonitorResultDO updateObj = BeanUtils.toBean(updateReqVO, TrafficEquipPingMonitorResultDO.class);
        equipPingMonitorResultMapper.updateById(updateObj);
    }

    @Override
    public void deleteEquipPingMonitorResult(String id) {
        // 校验存在
        validateEquipPingMonitorResultExists(id);
        // 删除
        equipPingMonitorResultMapper.deleteById(id);
    }

    private void validateEquipPingMonitorResultExists(String id) {
        if (equipPingMonitorResultMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.EQUIP_PING_MONITOR_RESULT_NOT_EXISTS);
        }
    }

    @Override
    public TrafficEquipPingMonitorResultDO getEquipPingMonitorResult(String id) {
        return equipPingMonitorResultMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficEquipPingMonitorResultDO> getEquipPingMonitorResultPage(TrafficEquipPingMonitorResultPageReqVO pageReqVO) {
        return equipPingMonitorResultMapper.selectPage(pageReqVO);
    }

}