package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationNewDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 终端最新位置 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsDeviceLocationNewMapper extends BaseMapperX<PublicOpsDeviceLocationNewDO> {

    default PageResult<PublicOpsDeviceLocationNewDO> selectPage(PublicOpsDeviceLocationNewPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsDeviceLocationNewDO>()
                .eqIfPresent(PublicOpsDeviceLocationNewDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(PublicOpsDeviceLocationNewDO::getLatitude, reqVO.getLatitude())
                .eqIfPresent(PublicOpsDeviceLocationNewDO::getLongitude, reqVO.getLongitude())
                .eqIfPresent(PublicOpsDeviceLocationNewDO::getSpeed, reqVO.getSpeed())
                .eqIfPresent(PublicOpsDeviceLocationNewDO::getDirection, reqVO.getDirection())
                .betweenIfPresent(PublicOpsDeviceLocationNewDO::getReportTime, reqVO.getReportTime())
                .betweenIfPresent(PublicOpsDeviceLocationNewDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PublicOpsDeviceLocationNewDO::getId));
    }

}