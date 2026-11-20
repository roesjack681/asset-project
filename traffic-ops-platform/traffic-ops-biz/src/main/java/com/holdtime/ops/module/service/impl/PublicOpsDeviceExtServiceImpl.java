package com.holdtime.ops.module.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.holdtime.dm.api.DmApiClient;
import com.holdtime.ops.module.entity.PublicOpsDeviceDO;
import com.holdtime.ops.module.entity.mapper.PublicOpsDeviceMapper;
import com.holdtime.ops.module.model.vo.PublicOpsDeviceSaveReqVO;
import com.holdtime.ops.module.service.PublicOpsDeviceExtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class PublicOpsDeviceExtServiceImpl implements PublicOpsDeviceExtService {
    @Resource
    private PublicOpsDeviceMapper opsDeviceMapper;

    @Override
    public String saveDevice(PublicOpsDeviceSaveReqVO saveReqVO) {
        saveReqVO.setCameraInfo("1,前方;2,后方");
        opsDeviceMapper.insertOrUpdate(BeanUtil.toBean(saveReqVO, PublicOpsDeviceDO.class));
        ThreadUtil.execAsync(() -> {
            DmApiClient.writeCompany();
            DmApiClient.writeDevice(saveReqVO, false);
        });
        return saveReqVO.getId();
    }

    @Override
    public void deleteDevice(String id) {
        PublicOpsDeviceDO device = opsDeviceMapper.selectById(id);
        if (null != device) {
            DmApiClient.writeDevice(BeanUtil.toBean(device, PublicOpsDeviceSaveReqVO.class), true);
            opsDeviceMapper.deleteById(id);
        }
    }
}
