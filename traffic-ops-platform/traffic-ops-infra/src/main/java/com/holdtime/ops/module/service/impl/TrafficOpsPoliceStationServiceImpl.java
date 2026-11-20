package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsPoliceStationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsPoliceStationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsPoliceStationMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 警用装备采集站 Service 实现类
 *
 * @author 杨丽
 */
@Service
@Validated
public class TrafficOpsPoliceStationServiceImpl implements TrafficOpsPoliceStationService {

    @Resource
    private TrafficOpsPoliceStationMapper opsPoliceStationMapper;

    @Override
    public String createOpsPoliceStation(TrafficOpsPoliceStationSaveReqVO createReqVO) {
        // 插入
        TrafficOpsPoliceStationDO opsPoliceStation = BeanUtils.toBean(createReqVO, TrafficOpsPoliceStationDO.class);
        opsPoliceStationMapper.insert(opsPoliceStation);
        // 返回
        return opsPoliceStation.getId();
    }

    @Override
    public void updateOpsPoliceStation(TrafficOpsPoliceStationSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsPoliceStationExists(updateReqVO.getId());
        // 更新
        TrafficOpsPoliceStationDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsPoliceStationDO.class);
        opsPoliceStationMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsPoliceStation(String id) {
        // 校验存在
        validateOpsPoliceStationExists(id);
        // 删除
        opsPoliceStationMapper.deleteById(id);
    }

    private void validateOpsPoliceStationExists(String id) {
        if (opsPoliceStationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_POLICE_STATION_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsPoliceStationDO getOpsPoliceStation(String id) {
        return opsPoliceStationMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsPoliceStationDO> getOpsPoliceStationPage(TrafficOpsPoliceStationPageReqVO pageReqVO) {
        return opsPoliceStationMapper.selectPage(pageReqVO);
    }

}