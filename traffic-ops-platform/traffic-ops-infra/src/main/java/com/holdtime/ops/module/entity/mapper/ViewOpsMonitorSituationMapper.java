package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewOpsMonitorSituationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewOpsMonitorSituationMapper extends BaseMapperX<ViewOpsMonitorSituationDO> {

    default PageResult<ViewOpsMonitorSituationDO> selectPage(ViewOpsMonitorSituationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewOpsMonitorSituationDO>()
                .eqIfPresent(ViewOpsMonitorSituationDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewOpsMonitorSituationDO::getLatestStatus, reqVO.getLatestStatus())
                .eqIfPresent(ViewOpsMonitorSituationDO::getPrevStatus, reqVO.getPrevStatus())
                .eqIfPresent(ViewOpsMonitorSituationDO::getStatusChangeDesc, reqVO.getStatusChangeDesc())
                .orderByDesc(ViewOpsMonitorSituationDO::getEquId));
    }

}