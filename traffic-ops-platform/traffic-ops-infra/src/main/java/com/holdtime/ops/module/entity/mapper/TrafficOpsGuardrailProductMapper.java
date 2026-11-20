package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailProductDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 护栏产品信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsGuardrailProductMapper extends BaseMapperX<TrafficOpsGuardrailProductDO> {

    default PageResult<TrafficOpsGuardrailProductDO> selectPage(TrafficOpsGuardrailProductPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsGuardrailProductDO>()
                .eqIfPresent(TrafficOpsGuardrailProductDO::getHight, reqVO.getHight())
                .eqIfPresent(TrafficOpsGuardrailProductDO::getLength, reqVO.getLength())
                .eqIfPresent(TrafficOpsGuardrailProductDO::getMaterial, reqVO.getMaterial())
                .eqIfPresent(TrafficOpsGuardrailProductDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsGuardrailProductDO::getModel, reqVO.getModel())
                .eqIfPresent(TrafficOpsGuardrailProductDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TrafficOpsGuardrailProductDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsGuardrailProductDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsGuardrailProductDO::getPedestal, reqVO.getPedestal())
                .orderByDesc(TrafficOpsGuardrailProductDO::getId));
    }

}