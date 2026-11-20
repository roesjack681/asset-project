package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficVioEquipmentInvalidReasonAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 集指设备错误原因分析 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficVioEquipmentInvalidReasonAnalysisMapper extends BaseMapperX<TrafficVioEquipmentInvalidReasonAnalysisDO> {

    default PageResult<TrafficVioEquipmentInvalidReasonAnalysisDO> selectPage(TrafficVioEquipmentInvalidReasonAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficVioEquipmentInvalidReasonAnalysisDO>()
                .eqIfPresent(TrafficVioEquipmentInvalidReasonAnalysisDO::getSbbh, reqVO.getSbbh())
                .geIfPresent(TrafficVioEquipmentInvalidReasonAnalysisDO::getOccurrenceTime, reqVO.getOccurrenceDay())
                .eqIfPresent(TrafficVioEquipmentInvalidReasonAnalysisDO::getInvalidReason, reqVO.getInvalidReason())
                .eqIfPresent(TrafficVioEquipmentInvalidReasonAnalysisDO::getInvalidCnt, reqVO.getInvalidCnt())
                .betweenIfPresent(TrafficVioEquipmentInvalidReasonAnalysisDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrafficVioEquipmentInvalidReasonAnalysisDO::getId));
    }

}