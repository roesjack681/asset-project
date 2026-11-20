package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficVioEquipmentIllegalAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 集指设备违法原因分析 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficVioEquipmentIllegalAnalysisMapper extends BaseMapperX<TrafficVioEquipmentIllegalAnalysisDO> {

    default PageResult<TrafficVioEquipmentIllegalAnalysisDO> selectPage(TrafficVioEquipmentIllegalAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficVioEquipmentIllegalAnalysisDO>()
                .eqIfPresent(TrafficVioEquipmentIllegalAnalysisDO::getSbbh, reqVO.getSbbh())
                .betweenIfPresent(TrafficVioEquipmentIllegalAnalysisDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(TrafficVioEquipmentIllegalAnalysisDO::getIllegalReason, reqVO.getIllegalReason())
                .eqIfPresent(TrafficVioEquipmentIllegalAnalysisDO::getIllegalCnt, reqVO.getIllegalCnt())
                .betweenIfPresent(TrafficVioEquipmentIllegalAnalysisDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrafficVioEquipmentIllegalAnalysisDO::getId));
    }

}