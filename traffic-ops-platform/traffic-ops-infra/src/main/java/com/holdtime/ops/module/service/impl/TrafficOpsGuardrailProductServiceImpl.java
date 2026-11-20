package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsGuardrailProductService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailProductMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 护栏产品信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsGuardrailProductServiceImpl implements TrafficOpsGuardrailProductService {

    @Resource
    private TrafficOpsGuardrailProductMapper opsGuardrailProductMapper;

    @Override
    public String createOpsGuardrailProduct(TrafficOpsGuardrailProductSaveReqVO createReqVO) {
        // 插入
        TrafficOpsGuardrailProductDO opsGuardrailProduct = BeanUtils.toBean(createReqVO, TrafficOpsGuardrailProductDO.class);
        opsGuardrailProductMapper.insert(opsGuardrailProduct);
        // 返回
        return opsGuardrailProduct.getId();
    }

    @Override
    public void updateOpsGuardrailProduct(TrafficOpsGuardrailProductSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsGuardrailProductExists(updateReqVO.getId());
        // 更新
        TrafficOpsGuardrailProductDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsGuardrailProductDO.class);
        opsGuardrailProductMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsGuardrailProduct(String id) {
        // 校验存在
        validateOpsGuardrailProductExists(id);
        // 删除
        opsGuardrailProductMapper.deleteById(id);
    }

    private void validateOpsGuardrailProductExists(String id) {
        if (opsGuardrailProductMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_GUARDRAIL_PRODUCT_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsGuardrailProductDO getOpsGuardrailProduct(String id) {
        return opsGuardrailProductMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsGuardrailProductDO> getOpsGuardrailProductPage(TrafficOpsGuardrailProductPageReqVO pageReqVO) {
        return opsGuardrailProductMapper.selectPage(pageReqVO);
    }

}