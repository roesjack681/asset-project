package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationDO;
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationNewDO;
import com.holdtime.ops.module.entity.mapper.PublicOpsDeviceLocationMapper;
import com.holdtime.ops.module.entity.mapper.PublicOpsDeviceLocationNewMapper;
import com.holdtime.ops.module.model.vo.PublicOpsDeviceLocationSaveReqVO;
import com.holdtime.ops.module.service.PublicOpsDeviceLocationExtService;
import com.holdtime.ops.module.service.PublicOpsDeviceLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PublicOpsDeviceLocationExtServiceImpl implements PublicOpsDeviceLocationExtService {
    @Resource
    private PublicOpsDeviceLocationMapper opsDeviceLocationMapper;
    @Resource
    private PublicOpsDeviceLocationNewMapper opsDeviceLocationNewMapper;
    @Resource
    private PublicOpsDeviceLocationService opsDeviceLocationService;

    @Override
    public String saveDeviceLocation(PublicOpsDeviceLocationSaveReqVO saveReqVO) {
        String id = opsDeviceLocationService.createOpsDeviceLocation(saveReqVO);
        opsDeviceLocationNewMapper.delete(PublicOpsDeviceLocationNewDO::getDeviceId, saveReqVO.getDeviceId());
        PublicOpsDeviceLocationNewDO deviceLocationNew = BeanUtils.toBean(saveReqVO, PublicOpsDeviceLocationNewDO.class);
        deviceLocationNew.setReportTime(DateUtil.toLocalDateTime(saveReqVO.getReportTime()));
        deviceLocationNew.setId(id);
        opsDeviceLocationNewMapper.insert(deviceLocationNew);
        return id;
    }

    @Override
    public PublicOpsDeviceLocationNewDO getDeviceLocationNew(String deviceId) {
        return opsDeviceLocationNewMapper.selectOne(PublicOpsDeviceLocationNewDO::getDeviceId, deviceId);
    }

    @Override
    public List<PublicOpsDeviceLocationDO> getHistoryLocation(String deviceId, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapperX<PublicOpsDeviceLocationDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PublicOpsDeviceLocationDO::getDeviceId, deviceId);
        queryWrapperX.ge(PublicOpsDeviceLocationDO::getReportTime, startTime);
        queryWrapperX.leIfPresent(PublicOpsDeviceLocationDO::getReportTime, endTime);
        queryWrapperX.orderByAsc(PublicOpsDeviceLocationDO::getReportTime);
        return opsDeviceLocationMapper.selectList(queryWrapperX);
    }


}
