package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsDeviceDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 终端设备信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsDeviceMapper extends BaseMapperX<PublicOpsDeviceDO> {

    default PageResult<PublicOpsDeviceDO> selectPage(PublicOpsDevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsDeviceDO>()
                .eqIfPresent(PublicOpsDeviceDO::getModel, reqVO.getModel())
                .eqIfPresent(PublicOpsDeviceDO::getImei, reqVO.getImei())
                .eqIfPresent(PublicOpsDeviceDO::getPlateNum, reqVO.getPlateNum())
                .eqIfPresent(PublicOpsDeviceDO::getCameraInfo, reqVO.getCameraInfo())
                .eqIfPresent(PublicOpsDeviceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PublicOpsDeviceDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(PublicOpsDeviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PublicOpsDeviceDO::getId));
    }

}