package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsDeviceLocationNewService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationNewDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PublicOpsDeviceLocationNewMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 终端最新位置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsDeviceLocationNewServiceImpl implements PublicOpsDeviceLocationNewService {

    @Resource
    private PublicOpsDeviceLocationNewMapper opsDeviceLocationNewMapper;

    @Override
    public String createOpsDeviceLocationNew(PublicOpsDeviceLocationNewSaveReqVO createReqVO) {
        // 插入
        PublicOpsDeviceLocationNewDO opsDeviceLocationNew = BeanUtils.toBean(createReqVO, PublicOpsDeviceLocationNewDO.class);
        opsDeviceLocationNewMapper.insert(opsDeviceLocationNew);
        // 返回
        return opsDeviceLocationNew.getId();
    }

    @Override
    public void updateOpsDeviceLocationNew(PublicOpsDeviceLocationNewSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsDeviceLocationNewExists(updateReqVO.getId());
        // 更新
        PublicOpsDeviceLocationNewDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsDeviceLocationNewDO.class);
        opsDeviceLocationNewMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsDeviceLocationNew(String id) {
        // 校验存在
        validateOpsDeviceLocationNewExists(id);
        // 删除
        opsDeviceLocationNewMapper.deleteById(id);
    }

    private void validateOpsDeviceLocationNewExists(String id) {
        if (opsDeviceLocationNewMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_DEVICE_LOCATION_NEW_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsDeviceLocationNewDO getOpsDeviceLocationNew(String id) {
        return opsDeviceLocationNewMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsDeviceLocationNewDO> getOpsDeviceLocationNewPage(PublicOpsDeviceLocationNewPageReqVO pageReqVO) {
        return opsDeviceLocationNewMapper.selectPage(pageReqVO);
    }

}