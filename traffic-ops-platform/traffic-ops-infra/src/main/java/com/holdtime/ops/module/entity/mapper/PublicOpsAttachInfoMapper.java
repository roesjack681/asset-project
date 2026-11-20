package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PublicOpsAttachInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 资产附件信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PublicOpsAttachInfoMapper extends BaseMapperX<PublicOpsAttachInfoDO> {

    default PageResult<PublicOpsAttachInfoDO> selectPage(PublicOpsAttachInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicOpsAttachInfoDO>()
                .eqIfPresent(PublicOpsAttachInfoDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .eqIfPresent(PublicOpsAttachInfoDO::getBusinessModel, reqVO.getBusinessModel())
                .eqIfPresent(PublicOpsAttachInfoDO::getBusinessId, reqVO.getBusinessId())
                .eqIfPresent(PublicOpsAttachInfoDO::getFileType, reqVO.getFileType())
                .likeIfPresent(PublicOpsAttachInfoDO::getFileName, reqVO.getFileName())
                .eqIfPresent(PublicOpsAttachInfoDO::getFilePath, reqVO.getFilePath())
                .eqIfPresent(PublicOpsAttachInfoDO::getFileSize, reqVO.getFileSize())
                .betweenIfPresent(PublicOpsAttachInfoDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PublicOpsAttachInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PublicOpsAttachInfoDO::getId));
    }

}