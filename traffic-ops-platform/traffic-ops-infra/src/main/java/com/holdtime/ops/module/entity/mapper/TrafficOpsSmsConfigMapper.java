package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsSmsConfigDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 资产短信发送配置 Mapper
 *
 * @author 杨丽
 */
@Mapper
public interface TrafficOpsSmsConfigMapper extends BaseMapperX<TrafficOpsSmsConfigDO> {

    default PageResult<TrafficOpsSmsConfigDO> selectPage(TrafficOpsSmsConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsSmsConfigDO>()
                .eqIfPresent(TrafficOpsSmsConfigDO::getSendType, reqVO.getSendType())
                .likeIfPresent(TrafficOpsSmsConfigDO::getReceiveName, reqVO.getReceiveName())
                .eqIfPresent(TrafficOpsSmsConfigDO::getReceivePhone, reqVO.getReceivePhone())
                .eqIfPresent(TrafficOpsSmsConfigDO::getUserBh, reqVO.getUserBh())
                .eqIfPresent(TrafficOpsSmsConfigDO::getReceiveStatus, reqVO.getReceiveStatus())
                .betweenIfPresent(TrafficOpsSmsConfigDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsSmsConfigDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsSmsConfigDO::getId));
    }

}