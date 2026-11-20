package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficVioEquipmentMonitorDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioEquipmentMonitorDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficVioEquipmentMonitorDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 集指设备检测记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficVioEquipmentMonitorDetailServiceImpl implements TrafficVioEquipmentMonitorDetailService {

    @Resource
    private TrafficVioEquipmentMonitorDetailMapper vioEquipmentMonitorDetailMapper;

    @Override
    public String createVioEquipmentMonitorDetail(TrafficVioEquipmentMonitorDetailSaveReqVO createReqVO) {
        // 插入
        TrafficVioEquipmentMonitorDetailDO vioEquipmentMonitorDetail = BeanUtils.toBean(createReqVO, TrafficVioEquipmentMonitorDetailDO.class);
        vioEquipmentMonitorDetailMapper.insert(vioEquipmentMonitorDetail);
        // 返回
        return vioEquipmentMonitorDetail.getId();
    }

    @Override
    public void updateVioEquipmentMonitorDetail(TrafficVioEquipmentMonitorDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateVioEquipmentMonitorDetailExists(updateReqVO.getId());
        // 更新
        TrafficVioEquipmentMonitorDetailDO updateObj = BeanUtils.toBean(updateReqVO, TrafficVioEquipmentMonitorDetailDO.class);
        vioEquipmentMonitorDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteVioEquipmentMonitorDetail(String id) {
        // 校验存在
        validateVioEquipmentMonitorDetailExists(id);
        // 删除
        vioEquipmentMonitorDetailMapper.deleteById(id);
    }

    private void validateVioEquipmentMonitorDetailExists(String id) {
        if (vioEquipmentMonitorDetailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_EQUIPMENT_MONITOR_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficVioEquipmentMonitorDetailDO getVioEquipmentMonitorDetail(String id) {
        return vioEquipmentMonitorDetailMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficVioEquipmentMonitorDetailDO> getVioEquipmentMonitorDetailPage(TrafficVioEquipmentMonitorDetailPageReqVO pageReqVO) {
        return vioEquipmentMonitorDetailMapper.selectPage(pageReqVO);
    }

}