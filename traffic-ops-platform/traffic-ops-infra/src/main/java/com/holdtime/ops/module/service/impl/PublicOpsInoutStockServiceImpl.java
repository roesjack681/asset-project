package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsInoutStockService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsInoutStockDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PublicOpsInoutStockMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备出入库管理 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsInoutStockServiceImpl implements PublicOpsInoutStockService {

    @Resource
    private PublicOpsInoutStockMapper opsInoutStockMapper;

    @Override
    public String createOpsInoutStock(PublicOpsInoutStockSaveReqVO createReqVO) {
        // 插入
        PublicOpsInoutStockDO opsInoutStock = BeanUtils.toBean(createReqVO, PublicOpsInoutStockDO.class);
        opsInoutStockMapper.insert(opsInoutStock);
        // 返回
        return opsInoutStock.getId();
    }

    @Override
    public void updateOpsInoutStock(PublicOpsInoutStockSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsInoutStockExists(updateReqVO.getId());
        // 更新
        PublicOpsInoutStockDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsInoutStockDO.class);
        opsInoutStockMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsInoutStock(String id) {
        // 校验存在
        validateOpsInoutStockExists(id);
        // 删除
        opsInoutStockMapper.deleteById(id);
    }

    private void validateOpsInoutStockExists(String id) {
        if (opsInoutStockMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_INOUT_STOCK_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsInoutStockDO getOpsInoutStock(String id) {
        return opsInoutStockMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsInoutStockDO> getOpsInoutStockPage(PublicOpsInoutStockPageReqVO pageReqVO) {
        return opsInoutStockMapper.selectPage(pageReqVO);
    }

}