package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintainCmService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintainCmDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintainCmMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保耗材 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsMaintainCmServiceImpl implements TrafficOpsMaintainCmService {

    @Resource
    private TrafficOpsMaintainCmMapper opsMaintainCmMapper;

    @Override
    public String createOpsMaintainCm(AppTrafficOpsMaintainCmSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintainCmDO opsMaintainCm = BeanUtils.toBean(createReqVO, TrafficOpsMaintainCmDO.class);
        opsMaintainCmMapper.insert(opsMaintainCm);
        // 返回
        return opsMaintainCm.getId();
    }

    @Override
    public void updateOpsMaintainCm(AppTrafficOpsMaintainCmSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintainCmExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintainCmDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintainCmDO.class);
        opsMaintainCmMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintainCm(String id) {
        // 校验存在
        validateOpsMaintainCmExists(id);
        // 删除
        opsMaintainCmMapper.deleteById(id);
    }

    private void validateOpsMaintainCmExists(String id) {
        if (opsMaintainCmMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTAIN_CM_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintainCmDO getOpsMaintainCm(String id) {
        return opsMaintainCmMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintainCmDO> getOpsMaintainCmPage(AppTrafficOpsMaintainCmPageReqVO pageReqVO) {
        return opsMaintainCmMapper.selectPage(pageReqVO);
    }

}