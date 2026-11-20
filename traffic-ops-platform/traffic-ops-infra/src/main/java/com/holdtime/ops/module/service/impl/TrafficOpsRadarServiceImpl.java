package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsRadarService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsRadarDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsRadarMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 雷达 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsRadarServiceImpl implements TrafficOpsRadarService {

    @Resource
    private TrafficOpsRadarMapper opsRadarMapper;

    @Override
    public String createOpsRadar(TrafficOpsRadarSaveReqVO createReqVO) {
        // 插入
        TrafficOpsRadarDO opsRadar = BeanUtils.toBean(createReqVO, TrafficOpsRadarDO.class);
        opsRadarMapper.insert(opsRadar);
        // 返回
        return opsRadar.getId();
    }

    @Override
    public void updateOpsRadar(TrafficOpsRadarSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsRadarExists(updateReqVO.getId());
        // 更新
        TrafficOpsRadarDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsRadarDO.class);
        opsRadarMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsRadar(String id) {
        // 校验存在
        validateOpsRadarExists(id);
        // 删除
        opsRadarMapper.deleteById(id);
    }

    private void validateOpsRadarExists(String id) {
        if (opsRadarMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_RADAR_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsRadarDO getOpsRadar(String id) {
        return opsRadarMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsRadarDO> getOpsRadarPage(TrafficOpsRadarPageReqVO pageReqVO) {
        return opsRadarMapper.selectPage(pageReqVO);
    }

}