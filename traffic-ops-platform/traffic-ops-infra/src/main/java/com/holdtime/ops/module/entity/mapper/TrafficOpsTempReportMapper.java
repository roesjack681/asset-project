package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsTempReportDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 交警运维临时报 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsTempReportMapper extends BaseMapperX<TrafficOpsTempReportDO> {

    default PageResult<TrafficOpsTempReportDO> selectPage(TrafficOpsTempReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsTempReportDO>()
                .eqIfPresent(TrafficOpsTempReportDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficOpsTempReportDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficOpsTempReportDO::getSource, reqVO.getSource())
                .betweenIfPresent(TrafficOpsTempReportDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsTempReportDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsTempReportDO::getId));
    }

}