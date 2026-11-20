package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceDailyRoundCheckDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 日常巡检信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PoliceDailyRoundCheckMapper extends BaseMapperX<PoliceDailyRoundCheckDO> {

    default PageResult<PoliceDailyRoundCheckDO> selectPage(PoliceDailyRoundCheckPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceDailyRoundCheckDO>()
                .eqIfPresent(PoliceDailyRoundCheckDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PoliceDailyRoundCheckDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .eqIfPresent(PoliceDailyRoundCheckDO::getEquStatus, reqVO.getEquStatus())
                .eqIfPresent(PoliceDailyRoundCheckDO::getCheckItem, reqVO.getCheckItem())
                .eqIfPresent(PoliceDailyRoundCheckDO::getQuestion, reqVO.getQuestion())
                .eqIfPresent(PoliceDailyRoundCheckDO::getSolution, reqVO.getSolution())
                .eqIfPresent(PoliceDailyRoundCheckDO::getCheckPerson, reqVO.getCheckPerson())
                .eqIfPresent(PoliceDailyRoundCheckDO::getCheckType, reqVO.getCheckType())
                .eqIfPresent(PoliceDailyRoundCheckDO::getCheckPersonId, reqVO.getCheckPersonId())
                .betweenIfPresent(PoliceDailyRoundCheckDO::getCheckDate, reqVO.getCheckDate())
                .betweenIfPresent(PoliceDailyRoundCheckDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PoliceDailyRoundCheckDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PoliceDailyRoundCheckDO::getId));
    }

}