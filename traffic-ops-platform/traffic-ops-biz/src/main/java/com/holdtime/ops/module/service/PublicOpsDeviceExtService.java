package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.PublicOpsDeviceSaveReqVO;

public interface PublicOpsDeviceExtService {
    String saveDevice(PublicOpsDeviceSaveReqVO saveReqVO);

    void deleteDevice(String id);
}
