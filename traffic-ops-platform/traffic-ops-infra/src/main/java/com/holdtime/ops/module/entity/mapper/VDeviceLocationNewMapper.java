package com.holdtime.ops.module.entity.mapper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.VDeviceLocationNewDO;
import com.holdtime.ops.module.model.vo.VDeviceLocationNewPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车辆最新位置查询 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface VDeviceLocationNewMapper extends BaseMapperX<VDeviceLocationNewDO> {

    default PageResult<VDeviceLocationNewDO> selectPage(VDeviceLocationNewPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VDeviceLocationNewDO>()
                .eqIfPresent(VDeviceLocationNewDO::getImei, reqVO.getImei())
                .likeIfPresent(VDeviceLocationNewDO::getPlateNum, reqVO.getPlateNum())
                .eqIfPresent(VDeviceLocationNewDO::getDeviceId, reqVO.getDeviceId())
                .betweenIfPresent(VDeviceLocationNewDO::getReportTime, reqVO.getReportTime())
                .betweenIfPresent(VDeviceLocationNewDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VDeviceLocationNewDO::getId));
    }

}