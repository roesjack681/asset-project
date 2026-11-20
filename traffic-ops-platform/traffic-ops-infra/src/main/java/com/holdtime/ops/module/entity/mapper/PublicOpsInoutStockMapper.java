package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsInoutStockDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备出入库管理 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsInoutStockMapper extends BaseMapperX<PublicOpsInoutStockDO> {

    default PageResult<PublicOpsInoutStockDO> selectPage(PublicOpsInoutStockPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsInoutStockDO>()
                .eqIfPresent(PublicOpsInoutStockDO::getStockType, reqVO.getStockType())
                .eqIfPresent(PublicOpsInoutStockDO::getStockPerson, reqVO.getStockPerson())
                .eqIfPresent(PublicOpsInoutStockDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(PublicOpsInoutStockDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PublicOpsInoutStockDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PublicOpsInoutStockDO::getId));
    }

}