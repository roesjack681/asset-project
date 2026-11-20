package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.VDeviceLocationNewDO;
import com.holdtime.ops.module.entity.mapper.VDeviceLocationNewMapper;
import com.holdtime.ops.module.model.vo.VDeviceLocationNewPageReqVO;
import com.holdtime.ops.module.service.VDeviceLocationNewService;
import com.holdtime.ops.module.utils.GPSConverterUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 车辆最新位置查询 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class VDeviceLocationNewServiceImpl implements VDeviceLocationNewService {

    @Resource
    private VDeviceLocationNewMapper vDeviceLocationNewMapper;

    @Override
    public VDeviceLocationNewDO getVDeviceLocationNew(String id) {
        return vDeviceLocationNewMapper.selectById(id);
    }

    @Override
    public PageResult<VDeviceLocationNewDO> getVDeviceLocationNewPage(VDeviceLocationNewPageReqVO pageReqVO) {
        PageResult<VDeviceLocationNewDO> pageResult = vDeviceLocationNewMapper.selectPage(pageReqVO);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).stream().filter(s -> s.getLongitude() != null && s.getLatitude() != null).forEach(deviceLocationNewPageReqVO -> {
           double[] jwd = GPSConverterUtils.wgs84ToGcj02(deviceLocationNewPageReqVO.getLongitude().doubleValue(),deviceLocationNewPageReqVO.getLatitude().doubleValue());
           deviceLocationNewPageReqVO.setLongitude(BigDecimal.valueOf(jwd[0]));
           deviceLocationNewPageReqVO.setLatitude(BigDecimal.valueOf(jwd[1]));
        });
        return pageResult;
    }

}