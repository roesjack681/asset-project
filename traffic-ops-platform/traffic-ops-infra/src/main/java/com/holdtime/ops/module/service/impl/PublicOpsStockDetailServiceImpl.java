package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsStockDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsStockDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PublicOpsStockDetailMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备库存明细 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsStockDetailServiceImpl implements PublicOpsStockDetailService {

    @Resource
    private PublicOpsStockDetailMapper opsStockDetailMapper;

    @Override
    public String createOpsStockDetail(PublicOpsStockDetailSaveReqVO createReqVO) {
        // 插入
        PublicOpsStockDetailDO opsStockDetail = BeanUtils.toBean(createReqVO, PublicOpsStockDetailDO.class);
        opsStockDetailMapper.insert(opsStockDetail);
        // 返回
        return opsStockDetail.getId();
    }

    @Override
    public void updateOpsStockDetail(PublicOpsStockDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsStockDetailExists(updateReqVO.getId());
        // 更新
        PublicOpsStockDetailDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsStockDetailDO.class);
        opsStockDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsStockDetail(String id) {
        // 校验存在
        validateOpsStockDetailExists(id);
        // 删除
        opsStockDetailMapper.deleteById(id);
    }

    private void validateOpsStockDetailExists(String id) {
        if (opsStockDetailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_STOCK_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsStockDetailDO getOpsStockDetail(String id) {
        return opsStockDetailMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsStockDetailDO> getOpsStockDetailPage(PublicOpsStockDetailPageReqVO pageReqVO) {
        return opsStockDetailMapper.selectPage(pageReqVO);
    }

}