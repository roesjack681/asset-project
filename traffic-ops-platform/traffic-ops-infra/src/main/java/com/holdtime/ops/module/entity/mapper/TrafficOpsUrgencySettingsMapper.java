package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsUrgencySettingsDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 维保紧急程度配置 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsUrgencySettingsMapper extends BaseMapperX<TrafficOpsUrgencySettingsDO> {

    default PageResult<TrafficOpsUrgencySettingsDO> selectPage(TrafficOpsUrgencySettingsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsUrgencySettingsDO>()
                .eqIfPresent(TrafficOpsUrgencySettingsDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .eqIfPresent(TrafficOpsUrgencySettingsDO::getUrgencyLevel, reqVO.getUrgencyLevel())
                .eqIfPresent(TrafficOpsUrgencySettingsDO::getDeadline, reqVO.getDeadline())
                .eqIfPresent(TrafficOpsUrgencySettingsDO::getNotifyPersonnel, reqVO.getNotifyPersonnel())
                .betweenIfPresent(TrafficOpsUrgencySettingsDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsUrgencySettingsDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsUrgencySettingsDO::getId));
    }

}