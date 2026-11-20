package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsDeviceLocationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PublicOpsDeviceLocationMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 终端最新位置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsDeviceLocationServiceImpl implements PublicOpsDeviceLocationService {

    @Resource
    private PublicOpsDeviceLocationMapper opsDeviceLocationMapper;

    @Override
    public String createOpsDeviceLocation(PublicOpsDeviceLocationSaveReqVO createReqVO) {
        // 插入
        PublicOpsDeviceLocationDO opsDeviceLocation = BeanUtils.toBean(createReqVO, PublicOpsDeviceLocationDO.class);
        opsDeviceLocationMapper.insert(opsDeviceLocation);
        // 返回
        return opsDeviceLocation.getId();
    }

    @Override
    public void updateOpsDeviceLocation(PublicOpsDeviceLocationSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsDeviceLocationExists(updateReqVO.getId());
        // 更新
        PublicOpsDeviceLocationDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsDeviceLocationDO.class);
        opsDeviceLocationMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsDeviceLocation(String id) {
        // 校验存在
        validateOpsDeviceLocationExists(id);
        // 删除
        opsDeviceLocationMapper.deleteById(id);
    }

    private void validateOpsDeviceLocationExists(String id) {
        if (opsDeviceLocationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_DEVICE_LOCATION_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsDeviceLocationDO getOpsDeviceLocation(String id) {
        return opsDeviceLocationMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsDeviceLocationDO> getOpsDeviceLocationPage(PublicOpsDeviceLocationPageReqVO pageReqVO) {
        return opsDeviceLocationMapper.selectPage(pageReqVO);
    }

}