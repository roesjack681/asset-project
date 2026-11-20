package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 资产二维码信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficOpsQrcodeMapper extends BaseMapperX<TrafficOpsQrcodeDO> {

    default PageResult<TrafficOpsQrcodeDO> selectPage(TrafficOpsQrcodePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsQrcodeDO>()
                .eqIfPresent(TrafficOpsQrcodeDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .eqIfPresent(TrafficOpsQrcodeDO::getRoadId, reqVO.getRoadId())
                .likeIfPresent(TrafficOpsQrcodeDO::getRaodName, reqVO.getRaodName())
                .eqIfPresent(TrafficOpsQrcodeDO::getDirection, reqVO.getDirection())
                .eqIfPresent(TrafficOpsQrcodeDO::getGenRecordId, reqVO.getGenRecordId())
                .eqIfPresent(TrafficOpsQrcodeDO::getOpsType, reqVO.getOpsType())
                .eqIfPresent(TrafficOpsQrcodeDO::getSerialNum, reqVO.getSerialNum())
                .betweenIfPresent(TrafficOpsQrcodeDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsQrcodeDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsQrcodeDO::getId));
    }

}