package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintainProgressService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintainProgressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintainProgressMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保进度 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsMaintainProgressServiceImpl implements TrafficOpsMaintainProgressService {

    @Resource
    private TrafficOpsMaintainProgressMapper opsMaintainProgressMapper;

    @Override
    public String createOpsMaintainProgress(AppTrafficOpsMaintainProgressSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintainProgressDO opsMaintainProgress = BeanUtils.toBean(createReqVO, TrafficOpsMaintainProgressDO.class);
        opsMaintainProgressMapper.insert(opsMaintainProgress);
        // 返回
        return opsMaintainProgress.getId();
    }

    @Override
    public void updateOpsMaintainProgress(AppTrafficOpsMaintainProgressSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintainProgressExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintainProgressDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintainProgressDO.class);
        opsMaintainProgressMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintainProgress(String id) {
        // 校验存在
        validateOpsMaintainProgressExists(id);
        // 删除
        opsMaintainProgressMapper.deleteById(id);
    }

    private void validateOpsMaintainProgressExists(String id) {
        if (opsMaintainProgressMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTAIN_PROGRESS_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintainProgressDO getOpsMaintainProgress(String id) {
        return opsMaintainProgressMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintainProgressDO> getOpsMaintainProgressPage(AppTrafficOpsMaintainProgressPageReqVO pageReqVO) {
        return opsMaintainProgressMapper.selectPage(pageReqVO);
    }

}