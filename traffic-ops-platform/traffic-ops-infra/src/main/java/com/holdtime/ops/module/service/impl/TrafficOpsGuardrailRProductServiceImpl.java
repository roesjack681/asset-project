package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsGuardrailRProductService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailRProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailRProductMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 护栏关联产品信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsGuardrailRProductServiceImpl implements TrafficOpsGuardrailRProductService {

    @Resource
    private TrafficOpsGuardrailRProductMapper opsGuardrailRProductMapper;

    @Override
    public String createOpsGuardrailRProduct(TrafficOpsGuardrailRProductSaveReqVO createReqVO) {
        // 插入
        TrafficOpsGuardrailRProductDO opsGuardrailRProduct = BeanUtils.toBean(createReqVO, TrafficOpsGuardrailRProductDO.class);
        opsGuardrailRProductMapper.insert(opsGuardrailRProduct);
        // 返回
        return opsGuardrailRProduct.getId();
    }

    @Override
    public void updateOpsGuardrailRProduct(TrafficOpsGuardrailRProductSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsGuardrailRProductExists(updateReqVO.getId());
        // 更新
        TrafficOpsGuardrailRProductDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsGuardrailRProductDO.class);
        opsGuardrailRProductMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsGuardrailRProduct(String id) {
        // 校验存在
        validateOpsGuardrailRProductExists(id);
        // 删除
        opsGuardrailRProductMapper.deleteById(id);
    }

    private void validateOpsGuardrailRProductExists(String id) {
        if (opsGuardrailRProductMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_GUARDRAIL_R_PRODUCT_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsGuardrailRProductDO getOpsGuardrailRProduct(String id) {
        return opsGuardrailRProductMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsGuardrailRProductDO> getOpsGuardrailRProductPage(TrafficOpsGuardrailRProductPageReqVO pageReqVO) {
        return opsGuardrailRProductMapper.selectPage(pageReqVO);
    }

}