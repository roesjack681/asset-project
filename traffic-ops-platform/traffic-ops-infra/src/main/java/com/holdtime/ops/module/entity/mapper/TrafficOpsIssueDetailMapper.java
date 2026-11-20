package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsIssueDetailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 领用详情 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsIssueDetailMapper extends BaseMapperX<TrafficOpsIssueDetailDO> {

    default PageResult<TrafficOpsIssueDetailDO> selectPage(TrafficOpsIssueDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsIssueDetailDO>()
                .eqIfPresent(TrafficOpsIssueDetailDO::getIssueBh, reqVO.getIssueBh())
                .eqIfPresent(TrafficOpsIssueDetailDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficOpsIssueDetailDO::getUseDeptBh, reqVO.getUseDeptBh())
                .eqIfPresent(TrafficOpsIssueDetailDO::getUseDept, reqVO.getUseDept())
                .likeIfPresent(TrafficOpsIssueDetailDO::getUserName, reqVO.getUserName())
                .eqIfPresent(TrafficOpsIssueDetailDO::getUserBh, reqVO.getUserBh())
                .betweenIfPresent(TrafficOpsIssueDetailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsIssueDetailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsIssueDetailDO::getId));
    }

}