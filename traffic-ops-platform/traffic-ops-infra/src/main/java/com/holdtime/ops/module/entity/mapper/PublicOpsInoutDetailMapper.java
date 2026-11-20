package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsInoutDetailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备出入库存明细 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsInoutDetailMapper extends BaseMapperX<PublicOpsInoutDetailDO> {

    default PageResult<PublicOpsInoutDetailDO> selectPage(PublicOpsInoutDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsInoutDetailDO>()
                .eqIfPresent(PublicOpsInoutDetailDO::getInoutId, reqVO.getInoutId())
                .eqIfPresent(PublicOpsInoutDetailDO::getCmSort, reqVO.getCmSort())
                .eqIfPresent(PublicOpsInoutDetailDO::getCmNum, reqVO.getCmNum())
                .eqIfPresent(PublicOpsInoutDetailDO::getFlag, reqVO.getFlag())
                .betweenIfPresent(PublicOpsInoutDetailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PublicOpsInoutDetailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PublicOpsInoutDetailDO::getId));
    }

}