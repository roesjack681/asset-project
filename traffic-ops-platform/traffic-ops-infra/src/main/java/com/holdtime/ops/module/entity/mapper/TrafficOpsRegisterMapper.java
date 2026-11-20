package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsRegisterDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 人员注册绑定信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsRegisterMapper extends BaseMapperX<TrafficOpsRegisterDO> {

    default PageResult<TrafficOpsRegisterDO> selectPage(AppTrafficOpsRegisterPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsRegisterDO>()
                .likeIfPresent(TrafficOpsRegisterDO::getName, reqVO.getName())
                .eqIfPresent(TrafficOpsRegisterDO::getPhone, reqVO.getPhone())
                .eqIfPresent(TrafficOpsRegisterDO::getOpenid, reqVO.getOpenid())
                .eqIfPresent(TrafficOpsRegisterDO::getUserId, reqVO.getUserId())
                .betweenIfPresent(TrafficOpsRegisterDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsRegisterDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsRegisterDO::getId));
    }

}