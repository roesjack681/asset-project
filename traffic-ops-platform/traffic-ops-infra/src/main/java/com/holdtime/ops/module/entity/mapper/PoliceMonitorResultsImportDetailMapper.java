package com.holdtime.ops.module.entity.mapper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportDetailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 公安监测结果导入详情 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PoliceMonitorResultsImportDetailMapper extends BaseMapperX<PoliceMonitorResultsImportDetailDO> {

    default PageResult<PoliceMonitorResultsImportDetailDO> selectPage(PoliceMonitorResultsImportDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceMonitorResultsImportDetailDO>()
                .eqIfPresent(PoliceMonitorResultsImportDetailDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PoliceMonitorResultsImportDetailDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(PoliceMonitorResultsImportDetailDO::getEquLable, reqVO.getEquLable())
                .betweenIfPresent(PoliceMonitorResultsImportDetailDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(PoliceMonitorResultsImportDetailDO::getVideoStatus, reqVO.getVideoStatus())
                .eqIfPresent(PoliceMonitorResultsImportDetailDO::getPingStatus, reqVO.getPingStatus())
                .betweenIfPresent(PoliceMonitorResultsImportDetailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorResultsImportDetailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorResultsImportDetailDO::getId));
    }

}