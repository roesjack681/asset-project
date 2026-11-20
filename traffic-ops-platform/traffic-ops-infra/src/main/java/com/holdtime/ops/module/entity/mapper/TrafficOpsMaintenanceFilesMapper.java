package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceFilesDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 维保材料信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsMaintenanceFilesMapper extends BaseMapperX<TrafficOpsMaintenanceFilesDO> {

    default PageResult<TrafficOpsMaintenanceFilesDO> selectPage(AppTrafficOpsMaintenanceFilesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintenanceFilesDO>()
                .eqIfPresent(TrafficOpsMaintenanceFilesDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(TrafficOpsMaintenanceFilesDO::getProgressId, reqVO.getProgressId())
                .eqIfPresent(TrafficOpsMaintenanceFilesDO::getRepairType, reqVO.getRepairType())
                .eqIfPresent(TrafficOpsMaintenanceFilesDO::getFileType, reqVO.getFileType())
                .eqIfPresent(TrafficOpsMaintenanceFilesDO::getFileUrl, reqVO.getFileUrl())
                .betweenIfPresent(TrafficOpsMaintenanceFilesDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceFilesDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMaintenanceFilesDO::getId));
    }

}