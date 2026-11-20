package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewOpsMonitorResultsMapper extends BaseMapperX<ViewOpsMonitorResultsDO> {

    default PageResult<ViewOpsMonitorResultsDO> selectPage(ViewOpsMonitorResultsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewOpsMonitorResultsDO>()
                .eqIfPresent(ViewOpsMonitorResultsDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewOpsMonitorResultsDO::getMemberBh, reqVO.getMemberBh())
                .likeIfPresent(ViewOpsMonitorResultsDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(ViewOpsMonitorResultsDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(ViewOpsMonitorResultsDO::getDirection, reqVO.getDirection())
                .eqIfPresent(ViewOpsMonitorResultsDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(ViewOpsMonitorResultsDO::getEventType, reqVO.getEventType())
        );
    }

}