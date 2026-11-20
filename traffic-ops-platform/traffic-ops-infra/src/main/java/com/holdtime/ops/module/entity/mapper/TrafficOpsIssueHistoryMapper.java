package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsIssueHistoryDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 警用装备历史领用记录 Mapper
 *
 * @author 杨丽
 */
@Mapper
public interface TrafficOpsIssueHistoryMapper extends BaseMapperX<TrafficOpsIssueHistoryDO> {

    default PageResult<TrafficOpsIssueHistoryDO> selectPage(TrafficOpsIssueHistoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsIssueHistoryDO>()
                .eqIfPresent(TrafficOpsIssueHistoryDO::getUseDeptBh, reqVO.getUseDeptBh())
                .eqIfPresent(TrafficOpsIssueHistoryDO::getUseDept, reqVO.getUseDept())
                .likeIfPresent(TrafficOpsIssueHistoryDO::getIssueUserName, reqVO.getIssueUserName())
                .eqIfPresent(TrafficOpsIssueHistoryDO::getIssueUserBh, reqVO.getIssueUserBh())
                .betweenIfPresent(TrafficOpsIssueHistoryDO::getIssueTime, reqVO.getIssueTime())
                .eqIfPresent(TrafficOpsIssueHistoryDO::getEquId, reqVO.getEquId())
                .likeIfPresent(TrafficOpsIssueHistoryDO::getPsn,reqVO.getPsn())
                .likeIfPresent(TrafficOpsIssueHistoryDO::getUserName, reqVO.getUserName())
                .eqIfPresent(TrafficOpsIssueHistoryDO::getUserBh, reqVO.getUserBh())
                .eqIfPresent(TrafficOpsIssueHistoryDO::getRecycleUserBh, reqVO.getRecycleUserBh())
                .likeIfPresent(TrafficOpsIssueHistoryDO::getRecycleUserName, reqVO.getRecycleUserName())
                .betweenIfPresent(TrafficOpsIssueHistoryDO::getRecycleTime, reqVO.getRecycleTime())
                .betweenIfPresent(TrafficOpsIssueHistoryDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsIssueHistoryDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsIssueHistoryDO::getId));
    }

}