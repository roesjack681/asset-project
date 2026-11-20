package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficDevTollgateQaResultDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 卡口质量检测结果 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficDevTollgateQaResultMapper extends BaseMapperX<TrafficDevTollgateQaResultDO> {

    default PageResult<TrafficDevTollgateQaResultDO> selectPage(TrafficDevTollgateQaResultPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficDevTollgateQaResultDO>()
                .eqIfPresent(TrafficDevTollgateQaResultDO::getKkbh, reqVO.getKkbh())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getFxlx, reqVO.getFxlx())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getJcsj, reqVO.getJcsj())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getHpzl, reqVO.getHpzl())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getHphm, reqVO.getHphm())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getGcsj, reqVO.getGcsj())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getRdlsj, reqVO.getRdlsj())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getGcxh, reqVO.getGcxh())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getSbhpzl, reqVO.getSbhpzl())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getSbhphm, reqVO.getSbhphm())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getHcbj, reqVO.getHcbj())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getHcsj, reqVO.getHcsj())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getHcr, reqVO.getHcr())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getSjly, reqVO.getSjly())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getCsbj, reqVO.getCsbj())
                .eqIfPresent(TrafficDevTollgateQaResultDO::getBjcsbj, reqVO.getBjcsbj())
                .betweenIfPresent(TrafficDevTollgateQaResultDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrafficDevTollgateQaResultDO::getId));
    }

}