package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsRoadService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsRoadDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsRoadMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 道路信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsRoadServiceImpl implements TrafficOpsRoadService {

    @Resource
    private TrafficOpsRoadMapper opsRoadMapper;

    @Override
    public String createOpsRoad(TrafficOpsRoadSaveReqVO createReqVO) {
        // 插入
        TrafficOpsRoadDO opsRoad = BeanUtils.toBean(createReqVO, TrafficOpsRoadDO.class);
        opsRoadMapper.insert(opsRoad);
        // 返回
        return opsRoad.getId();
    }

    @Override
    public void updateOpsRoad(TrafficOpsRoadSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsRoadExists(updateReqVO.getId());
        // 更新
        TrafficOpsRoadDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsRoadDO.class);
        opsRoadMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsRoad(String id) {
        // 校验存在
        validateOpsRoadExists(id);
        // 删除
        opsRoadMapper.deleteById(id);
    }

    private void validateOpsRoadExists(String id) {
        if (opsRoadMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_ROAD_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsRoadDO getOpsRoad(String id) {
        return opsRoadMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsRoadDO> getOpsRoadPage(TrafficOpsRoadPageReqVO pageReqVO) {
        return opsRoadMapper.selectPage(pageReqVO);
    }

}