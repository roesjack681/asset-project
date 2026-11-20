package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsCameraInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsCameraInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PublicOpsCameraInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 资产摄像设备信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsCameraInfoServiceImpl implements PublicOpsCameraInfoService {

    @Resource
    private PublicOpsCameraInfoMapper opsCameraInfoMapper;

    @Override
    public String createOpsCameraInfo(PublicOpsCameraInfoSaveReqVO createReqVO) {
        // 插入
        PublicOpsCameraInfoDO opsCameraInfo = BeanUtils.toBean(createReqVO, PublicOpsCameraInfoDO.class);
        opsCameraInfoMapper.insert(opsCameraInfo);
        // 返回
        return opsCameraInfo.getId();
    }

    @Override
    public void updateOpsCameraInfo(PublicOpsCameraInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsCameraInfoExists(updateReqVO.getId());
        // 更新
        PublicOpsCameraInfoDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsCameraInfoDO.class);
        opsCameraInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsCameraInfo(String id) {
        // 校验存在
        validateOpsCameraInfoExists(id);
        // 删除
        opsCameraInfoMapper.deleteById(id);
    }

    private void validateOpsCameraInfoExists(String id) {
        if (opsCameraInfoMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.PUBLIC_OPS_CAMERA_INFO_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsCameraInfoDO getOpsCameraInfo(String id) {
        return opsCameraInfoMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsCameraInfoDO> getOpsCameraInfoPage(PublicOpsCameraInfoPageReqVO pageReqVO) {
        return opsCameraInfoMapper.selectPage(pageReqVO);
    }

}