package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailInsuranceDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 护栏保险信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsGuardrailInsuranceMapper extends BaseMapperX<TrafficOpsGuardrailInsuranceDO> {

    default PageResult<TrafficOpsGuardrailInsuranceDO> selectPage(TrafficOpsGuardrailInsurancePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsGuardrailInsuranceDO>()
                .eqIfPresent(TrafficOpsGuardrailInsuranceDO::getManceGuardrailId, reqVO.getManceGuardrailId())
                .eqIfPresent(TrafficOpsGuardrailInsuranceDO::getVehicleNum, reqVO.getVehicleNum())
                .eqIfPresent(TrafficOpsGuardrailInsuranceDO::getInsuranceCompany, reqVO.getInsuranceCompany())
                .eqIfPresent(TrafficOpsGuardrailInsuranceDO::getInsurancePolicy, reqVO.getInsurancePolicy())
                .eqIfPresent(TrafficOpsGuardrailInsuranceDO::getPayMoney, reqVO.getPayMoney())
                .eqIfPresent(TrafficOpsGuardrailInsuranceDO::getProductId, reqVO.getProductId())
                .eqIfPresent(TrafficOpsGuardrailInsuranceDO::getPieces, reqVO.getPieces())
                .betweenIfPresent(TrafficOpsGuardrailInsuranceDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsGuardrailInsuranceDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsGuardrailInsuranceDO::getId));
    }

}