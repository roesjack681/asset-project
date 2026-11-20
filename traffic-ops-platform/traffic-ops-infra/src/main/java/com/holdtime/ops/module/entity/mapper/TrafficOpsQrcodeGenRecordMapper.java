package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeGenRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 资产二维码生成记录 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficOpsQrcodeGenRecordMapper extends BaseMapperX<TrafficOpsQrcodeGenRecordDO> {

    default PageResult<TrafficOpsQrcodeGenRecordDO> selectPage(TrafficOpsQrcodeGenRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsQrcodeGenRecordDO>()
                .eqIfPresent(TrafficOpsQrcodeGenRecordDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .betweenIfPresent(TrafficOpsQrcodeGenRecordDO::getGenDate, reqVO.getGenDate())
                .eqIfPresent(TrafficOpsQrcodeGenRecordDO::getRoadId, reqVO.getRoadId())
                .likeIfPresent(TrafficOpsQrcodeGenRecordDO::getRoadName, reqVO.getRoadName())
                .eqIfPresent(TrafficOpsQrcodeGenRecordDO::getOpsType, reqVO.getOpsType())
                .eqIfPresent(TrafficOpsQrcodeGenRecordDO::getDirectionCnt, reqVO.getDirectionCnt())
                .betweenIfPresent(TrafficOpsQrcodeGenRecordDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsQrcodeGenRecordDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsQrcodeGenRecordDO::getId));
    }

}