package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsStockDetailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备库存明细 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsStockDetailMapper extends BaseMapperX<PublicOpsStockDetailDO> {

    default PageResult<PublicOpsStockDetailDO> selectPage(PublicOpsStockDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsStockDetailDO>()
                .eqIfPresent(PublicOpsStockDetailDO::getStockId, reqVO.getStockId())
                .eqIfPresent(PublicOpsStockDetailDO::getCmSort, reqVO.getCmSort())
                .eqIfPresent(PublicOpsStockDetailDO::getCmNum, reqVO.getCmNum())
                .eqIfPresent(PublicOpsStockDetailDO::getFlag, reqVO.getFlag())
                .eqIfPresent(PublicOpsStockDetailDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(PublicOpsStockDetailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PublicOpsStockDetailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PublicOpsStockDetailDO::getId));
    }

}