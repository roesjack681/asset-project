package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsStockDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备库存管理 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsStockMapper extends BaseMapperX<PublicOpsStockDO> {

    default PageResult<PublicOpsStockDO> selectPage(PublicOpsStockPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsStockDO>()
                .eqIfPresent(PublicOpsStockDO::getCmSort, reqVO.getCmSort())
                .eqIfPresent(PublicOpsStockDO::getSurplusNum, reqVO.getSurplusNum())
                .eqIfPresent(PublicOpsStockDO::getTotalNum, reqVO.getTotalNum())
                .betweenIfPresent(PublicOpsStockDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PublicOpsStockDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PublicOpsStockDO::getId));
    }

}