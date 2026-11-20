package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicUserAreaGroupDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 区域用户组信息 Mapper
 *
 * @author 杨丽
 */
@Mapper
public interface PublicUserAreaGroupMapper extends BaseMapperX<PublicUserAreaGroupDO> {

    default PageResult<PublicUserAreaGroupDO> selectPage(PublicUserAreaGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicUserAreaGroupDO>()
                .eqIfPresent(PublicUserAreaGroupDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .likeIfPresent(PublicUserAreaGroupDO::getGroupName, reqVO.getGroupName())
                .eqIfPresent(PublicUserAreaGroupDO::getGroupContent, reqVO.getGroupContent())
                .eqIfPresent(PublicUserAreaGroupDO::getGroupArea, reqVO.getGroupArea())
                .eqIfPresent(PublicUserAreaGroupDO::getGroupUserId, reqVO.getGroupUserId())
                .eqIfPresent(PublicUserAreaGroupDO::getLeadUserId, reqVO.getLeadUserId())
                .betweenIfPresent(PublicUserAreaGroupDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PublicUserAreaGroupDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PublicUserAreaGroupDO::getId));
    }

}