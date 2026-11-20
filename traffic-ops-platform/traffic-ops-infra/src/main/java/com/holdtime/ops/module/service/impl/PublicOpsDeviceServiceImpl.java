package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsDeviceService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsDeviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PublicOpsDeviceMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 终端设备信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsDeviceServiceImpl implements PublicOpsDeviceService {

    @Resource
    private PublicOpsDeviceMapper opsDeviceMapper;

    @Override
    public String createOpsDevice(PublicOpsDeviceSaveReqVO createReqVO) {
        // 插入
        PublicOpsDeviceDO opsDevice = BeanUtils.toBean(createReqVO, PublicOpsDeviceDO.class);
        opsDeviceMapper.insert(opsDevice);
        // 返回
        return opsDevice.getId();
    }

    @Override
    public void updateOpsDevice(PublicOpsDeviceSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsDeviceExists(updateReqVO.getId());
        // 更新
        PublicOpsDeviceDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsDeviceDO.class);
        opsDeviceMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsDevice(String id) {
        // 校验存在
        validateOpsDeviceExists(id);
        // 删除
        opsDeviceMapper.deleteById(id);
    }

    private void validateOpsDeviceExists(String id) {
        if (opsDeviceMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_DEVICE_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsDeviceDO getOpsDevice(String id) {
        return opsDeviceMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsDeviceDO> getOpsDevicePage(PublicOpsDevicePageReqVO pageReqVO) {
        return opsDeviceMapper.selectPage(pageReqVO);
    }

}