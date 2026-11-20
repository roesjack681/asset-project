package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 终端最新位置 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsDeviceLocationMapper extends BaseMapperX<PublicOpsDeviceLocationDO> {

    default PageResult<PublicOpsDeviceLocationDO> selectPage(PublicOpsDeviceLocationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsDeviceLocationDO>()
                .eqIfPresent(PublicOpsDeviceLocationDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(PublicOpsDeviceLocationDO::getLatitude, reqVO.getLatitude())
                .eqIfPresent(PublicOpsDeviceLocationDO::getLongitude, reqVO.getLongitude())
                .eqIfPresent(PublicOpsDeviceLocationDO::getSpeed, reqVO.getSpeed())
                .eqIfPresent(PublicOpsDeviceLocationDO::getDirection, reqVO.getDirection())
                .betweenIfPresent(PublicOpsDeviceLocationDO::getReportTime, reqVO.getReportTime())
                .betweenIfPresent(PublicOpsDeviceLocationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PublicOpsDeviceLocationDO::getId));
    }

}