package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailRProductDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 护栏关联产品信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsGuardrailRProductMapper extends BaseMapperX<TrafficOpsGuardrailRProductDO> {

    default PageResult<TrafficOpsGuardrailRProductDO> selectPage(TrafficOpsGuardrailRProductPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsGuardrailRProductDO>()
                .eqIfPresent(TrafficOpsGuardrailRProductDO::getProductId, reqVO.getProductId())
                .eqIfPresent(TrafficOpsGuardrailRProductDO::getGuardrailId, reqVO.getGuardrailId())
                .eqIfPresent(TrafficOpsGuardrailRProductDO::getPieces, reqVO.getPieces())
                .betweenIfPresent(TrafficOpsGuardrailRProductDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsGuardrailRProductDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsGuardrailRProductDO::getId));
    }

}