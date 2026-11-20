package com.holdtime.ops.module.entity.mapper;


import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewVioEquipmentDailySituationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 违法抓拍设备每日情况 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewVioEquipmentDailySituationMapper extends BaseMapperX<ViewVioEquipmentDailySituationDO> {

    default PageResult<ViewVioEquipmentDailySituationDO> selectPage(ViewVioEquipmentDailySituationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewVioEquipmentDailySituationDO>()
                .eqIfPresent(ViewVioEquipmentDailySituationDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(ViewVioEquipmentDailySituationDO::getKkbh, reqVO.getKkbh())
                .eqIfPresent(ViewVioEquipmentDailySituationDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(ViewVioEquipmentDailySituationDO::getWfsj, reqVO.getWfsj())
                .betweenIfPresent(ViewVioEquipmentDailySituationDO::getWfsj, reqVO.getWfsjs())
                .eqIfPresent(ViewVioEquipmentDailySituationDO::getDswfCnt, reqVO.getDswfCnt())
                .eqIfPresent(ViewVioEquipmentDailySituationDO::getYxwfCnt, reqVO.getYxwfCnt())
                .eqIfPresent(ViewVioEquipmentDailySituationDO::getWxwfCnt, reqVO.getWxwfCnt())
                .eqIfPresent(ViewVioEquipmentDailySituationDO::getJcsj, reqVO.getJcsj())
                .eqIfPresent(ViewVioEquipmentDailySituationDO::getKyl, reqVO.getKyl())
                .orderByDesc(ViewVioEquipmentDailySituationDO::getSbbh));
    }

}