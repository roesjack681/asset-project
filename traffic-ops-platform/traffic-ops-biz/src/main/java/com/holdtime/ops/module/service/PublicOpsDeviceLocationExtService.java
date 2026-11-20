package com.holdtime.ops.module.service;

import com.holdtime.ops.module.entity.PublicOpsDeviceLocationDO;
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationNewDO;
import com.holdtime.ops.module.model.vo.PublicOpsDeviceLocationSaveReqVO;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicOpsDeviceLocationExtService {
    String saveDeviceLocation(PublicOpsDeviceLocationSaveReqVO saveReqVO);

    /**
     * 获取设备最新位置
     * @param deviceId
     * @return
     */
    PublicOpsDeviceLocationNewDO getDeviceLocationNew(String deviceId);

    /**
     * 获取设备历史轨迹
     * @param deviceId
     * @param startTime
     * @param endTime
     * @return
     */
    List<PublicOpsDeviceLocationDO> getHistoryLocation(String deviceId, LocalDateTime startTime, LocalDateTime endTime);
}
