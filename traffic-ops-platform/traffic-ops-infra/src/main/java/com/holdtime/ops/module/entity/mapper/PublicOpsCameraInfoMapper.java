package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsCameraInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 资产摄像设备信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsCameraInfoMapper extends BaseMapperX<PublicOpsCameraInfoDO> {

    default PageResult<PublicOpsCameraInfoDO> selectPage(PublicOpsCameraInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsCameraInfoDO>()
                .likeIfPresent(PublicOpsCameraInfoDO::getCameraName, reqVO.getCameraName())
                .eqIfPresent(PublicOpsCameraInfoDO::getCameraBh, reqVO.getCameraBh())
                .eqIfPresent(PublicOpsCameraInfoDO::getCameraUser, reqVO.getCameraUser())
                .eqIfPresent(PublicOpsCameraInfoDO::getCameraPassword, reqVO.getCameraPassword())
                .eqIfPresent(PublicOpsCameraInfoDO::getCameraGroup, reqVO.getCameraGroup())
                .eqIfPresent(PublicOpsCameraInfoDO::getInstallLocation, reqVO.getInstallLocation())
                .betweenIfPresent(PublicOpsCameraInfoDO::getInstallDate, reqVO.getInstallDate())
                .betweenIfPresent(PublicOpsCameraInfoDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PublicOpsCameraInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PublicOpsCameraInfoDO::getId));
    }

}