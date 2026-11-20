package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsIssuePersonDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备领用人信息 Mapper
 *
 * @author 杨丽
 */
@Mapper
public interface TrafficOpsIssuePersonMapper extends BaseMapperX<TrafficOpsIssuePersonDO> {

    default PageResult<TrafficOpsIssuePersonDO> selectPage(TrafficOpsIssuePersonPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsIssuePersonDO>()
                .likeIfPresent(TrafficOpsIssuePersonDO::getUserName, reqVO.getUserName())
                .eqIfPresent(TrafficOpsIssuePersonDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(TrafficOpsIssuePersonDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsIssuePersonDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsIssuePersonDO::getId));
    }

}