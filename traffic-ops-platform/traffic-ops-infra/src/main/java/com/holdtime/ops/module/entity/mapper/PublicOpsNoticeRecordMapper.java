package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsNoticeRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 消息通知记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsNoticeRecordMapper extends BaseMapperX<PublicOpsNoticeRecordDO> {

    default PageResult<PublicOpsNoticeRecordDO> selectPage(PublicOpsNoticeRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsNoticeRecordDO>()
                .eqIfPresent(PublicOpsNoticeRecordDO::getBusinessType, reqVO.getBusinessType())
                .eqIfPresent(PublicOpsNoticeRecordDO::getBusinessId, reqVO.getBusinessId())
                .eqIfPresent(PublicOpsNoticeRecordDO::getNoticeType, reqVO.getNoticeType())
                .eqIfPresent(PublicOpsNoticeRecordDO::getReceiveUserType, reqVO.getReceiveUserType())
                .eqIfPresent(PublicOpsNoticeRecordDO::getReceiveUserId, reqVO.getReceiveUserId())
                .likeIfPresent(PublicOpsNoticeRecordDO::getReceiveUserName, reqVO.getReceiveUserName())
                .eqIfPresent(PublicOpsNoticeRecordDO::getReceiveUserPhone, reqVO.getReceiveUserPhone())
                .eqIfPresent(PublicOpsNoticeRecordDO::getNoticeMsg, reqVO.getNoticeMsg())
                .betweenIfPresent(PublicOpsNoticeRecordDO::getSendTime, reqVO.getSendTime())
                .betweenIfPresent(PublicOpsNoticeRecordDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PublicOpsNoticeRecordDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PublicOpsNoticeRecordDO::getId));
    }

}