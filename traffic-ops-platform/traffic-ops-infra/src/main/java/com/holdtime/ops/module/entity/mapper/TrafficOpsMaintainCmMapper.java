package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintainCmDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备维保耗材 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsMaintainCmMapper extends BaseMapperX<TrafficOpsMaintainCmDO> {

    default PageResult<TrafficOpsMaintainCmDO> selectPage(AppTrafficOpsMaintainCmPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintainCmDO>()
                .eqIfPresent(TrafficOpsMaintainCmDO::getProgressId, reqVO.getProgressId())
                .eqIfPresent(TrafficOpsMaintainCmDO::getCmSort, reqVO.getCmSort())
                .eqIfPresent(TrafficOpsMaintainCmDO::getCmNum, reqVO.getCmNum())
                .betweenIfPresent(TrafficOpsMaintainCmDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintainCmDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMaintainCmDO::getId));
    }

}