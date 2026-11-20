package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportSituationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 公安监测结果导入近两日状态 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PoliceMonitorResultsImportSituationMapper extends BaseMapperX<PoliceMonitorResultsImportSituationDO> {

    default PageResult<PoliceMonitorResultsImportSituationDO> selectPage(PoliceMonitorResultsImportSituationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceMonitorResultsImportSituationDO>()
                .eqIfPresent(PoliceMonitorResultsImportSituationDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PoliceMonitorResultsImportSituationDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(PoliceMonitorResultsImportSituationDO::getEquLable, reqVO.getEquLable())
                .betweenIfPresent(PoliceMonitorResultsImportSituationDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(PoliceMonitorResultsImportSituationDO::getVideoStatus, reqVO.getVideoStatus())
                .eqIfPresent(PoliceMonitorResultsImportSituationDO::getPingStatus, reqVO.getPingStatus())
                .betweenIfPresent(PoliceMonitorResultsImportSituationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PoliceMonitorResultsImportSituationDO::getId));
    }

}