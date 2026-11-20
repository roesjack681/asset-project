package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.HaPsFeedbackDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 方案展示留言记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HaPsFeedbackMapper extends BaseMapperX<HaPsFeedbackDO> {

    default PageResult<HaPsFeedbackDO> selectPage(HaPsFeedbackPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HaPsFeedbackDO>()
                .likeIfPresent(HaPsFeedbackDO::getName, reqVO.getName())
                .likeIfPresent(HaPsFeedbackDO::getPhone, reqVO.getPhone())
                .likeIfPresent(HaPsFeedbackDO::getEmail, reqVO.getEmail())
                .betweenIfPresent(HaPsFeedbackDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(HaPsFeedbackDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(HaPsFeedbackDO::getId));
    }

}