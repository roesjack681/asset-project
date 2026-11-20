package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsQrcodeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsQrcodeMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 资产二维码信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficOpsQrcodeServiceImpl implements TrafficOpsQrcodeService {

    @Resource
    private TrafficOpsQrcodeMapper opsQrcodeMapper;

    @Override
    public String createOpsQrcode(TrafficOpsQrcodeSaveReqVO createReqVO) {
        // 插入
        TrafficOpsQrcodeDO opsQrcode = BeanUtils.toBean(createReqVO, TrafficOpsQrcodeDO.class);
        opsQrcodeMapper.insert(opsQrcode);
        // 返回
        return opsQrcode.getId();
    }

    @Override
    public void updateOpsQrcode(TrafficOpsQrcodeSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsQrcodeExists(updateReqVO.getId());
        // 更新
        TrafficOpsQrcodeDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsQrcodeDO.class);
        opsQrcodeMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsQrcode(String id) {
        // 校验存在
        validateOpsQrcodeExists(id);
        // 删除
        opsQrcodeMapper.deleteById(id);
    }

    private void validateOpsQrcodeExists(String id) {
        if (opsQrcodeMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_QRCODE_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsQrcodeDO getOpsQrcode(String id) {
        return opsQrcodeMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsQrcodeDO> getOpsQrcodePage(TrafficOpsQrcodePageReqVO pageReqVO) {
        return opsQrcodeMapper.selectPage(pageReqVO);
    }

}