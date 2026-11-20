package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsStockService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsStockDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PublicOpsStockMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备库存管理 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsStockServiceImpl implements PublicOpsStockService {

    @Resource
    private PublicOpsStockMapper opsStockMapper;

    @Override
    public String createOpsStock(PublicOpsStockSaveReqVO createReqVO) {
        // 插入
        PublicOpsStockDO opsStock = BeanUtils.toBean(createReqVO, PublicOpsStockDO.class);
        opsStockMapper.insert(opsStock);
        // 返回
        return opsStock.getId();
    }

    @Override
    public void updateOpsStock(PublicOpsStockSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsStockExists(updateReqVO.getId());
        // 更新
        PublicOpsStockDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsStockDO.class);
        opsStockMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsStock(String id) {
        // 校验存在
        validateOpsStockExists(id);
        // 删除
        opsStockMapper.deleteById(id);
    }

    private void validateOpsStockExists(String id) {
        if (opsStockMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_STOCK_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsStockDO getOpsStock(String id) {
        return opsStockMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsStockDO> getOpsStockPage(PublicOpsStockPageReqVO pageReqVO) {
        return opsStockMapper.selectPage(pageReqVO);
    }

}