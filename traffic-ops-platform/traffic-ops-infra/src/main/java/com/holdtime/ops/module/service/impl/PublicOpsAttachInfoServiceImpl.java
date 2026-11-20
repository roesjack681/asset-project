package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsAttachInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsAttachInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PublicOpsAttachInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 资产附件信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsAttachInfoServiceImpl implements PublicOpsAttachInfoService {

    @Resource
    private PublicOpsAttachInfoMapper opsAttachInfoMapper;

    @Override
    public String createOpsAttachInfo(PublicOpsAttachInfoSaveReqVO createReqVO) {
        // 插入
        PublicOpsAttachInfoDO opsAttachInfo = BeanUtils.toBean(createReqVO, PublicOpsAttachInfoDO.class);
        opsAttachInfoMapper.insert(opsAttachInfo);
        // 返回
        return opsAttachInfo.getId();
    }

    @Override
    public void updateOpsAttachInfo(PublicOpsAttachInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsAttachInfoExists(updateReqVO.getId());
        // 更新
        PublicOpsAttachInfoDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsAttachInfoDO.class);
        opsAttachInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsAttachInfo(String id) {
        // 校验存在
        validateOpsAttachInfoExists(id);
        // 删除
        opsAttachInfoMapper.deleteById(id);
    }

    private void validateOpsAttachInfoExists(String id) {
        if (opsAttachInfoMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_ATTACH_INFO_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsAttachInfoDO getOpsAttachInfo(String id) {
        return opsAttachInfoMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsAttachInfoDO> getOpsAttachInfoPage(PublicOpsAttachInfoPageReqVO pageReqVO) {
        return opsAttachInfoMapper.selectPage(pageReqVO);
    }

}