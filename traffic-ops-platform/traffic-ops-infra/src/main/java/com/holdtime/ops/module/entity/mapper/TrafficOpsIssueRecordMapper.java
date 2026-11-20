package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsIssueRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 领用记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsIssueRecordMapper extends BaseMapperX<TrafficOpsIssueRecordDO> {

    default PageResult<TrafficOpsIssueRecordDO> selectPage(TrafficOpsIssueRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsIssueRecordDO>()
                .eqIfPresent(TrafficOpsIssueRecordDO::getUseDeptBh, reqVO.getUseDeptBh())
                .eqIfPresent(TrafficOpsIssueRecordDO::getUseDept, reqVO.getUseDept())
                .likeIfPresent(TrafficOpsIssueRecordDO::getUserName, reqVO.getUserName())
                .eqIfPresent(TrafficOpsIssueRecordDO::getUserBh, reqVO.getUserBh())
                .betweenIfPresent(TrafficOpsIssueRecordDO::getIssueTime, reqVO.getIssueTime())
                .eqIfPresent(TrafficOpsIssueRecordDO::getIssueNum, reqVO.getIssueNum())
                .betweenIfPresent(TrafficOpsIssueRecordDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsIssueRecordDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsIssueRecordDO::getId));
    }

}