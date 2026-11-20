package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.BtjczhVioEquipmentDailySituationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 集指设备每日情况 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BtjczhVioEquipmentDailySituationMapper extends BaseMapperX<BtjczhVioEquipmentDailySituationDO> {

    default PageResult<BtjczhVioEquipmentDailySituationDO> selectPage(BtjczhVioEquipmentDailySituationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BtjczhVioEquipmentDailySituationDO>()
                .geIfPresent(BtjczhVioEquipmentDailySituationDO::getOccurrenceDay, reqVO.getOccurrenceDay())
                .eqIfPresent(BtjczhVioEquipmentDailySituationDO::getKkbh, reqVO.getKkbh())
                .eqIfPresent(BtjczhVioEquipmentDailySituationDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(BtjczhVioEquipmentDailySituationDO::getNoSurveilCnt, reqVO.getNoSurveilCnt())
                .eqIfPresent(BtjczhVioEquipmentDailySituationDO::getValidSurveilCnt, reqVO.getValidSurveilCnt())
                .eqIfPresent(BtjczhVioEquipmentDailySituationDO::getInvalidSurveilCnt, reqVO.getInvalidSurveilCnt())
                .eqIfPresent(BtjczhVioEquipmentDailySituationDO::getRecognitionAccuracyRate, reqVO.getRecognitionAccuracyRate())
                .betweenIfPresent(BtjczhVioEquipmentDailySituationDO::getRecognitionAccuracyPickDate, reqVO.getRecognitionAccuracyPickDate())
                .betweenIfPresent(BtjczhVioEquipmentDailySituationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BtjczhVioEquipmentDailySituationDO::getId));
    }

}