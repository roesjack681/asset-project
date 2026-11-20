package com.holdtime.ops.module.entity.mapper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.ApiAuthDO;
import com.holdtime.ops.module.model.vo.ApiAuthPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 接口授权 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ApiAuthMapper extends BaseMapperX<ApiAuthDO> {

    default PageResult<ApiAuthDO> selectPage(ApiAuthPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ApiAuthDO>()
                .eqIfPresent(ApiAuthDO::getClientSn, reqVO.getClientSn())
                .eqIfPresent(ApiAuthDO::getAuthCode, reqVO.getAuthCode())
                .betweenIfPresent(ApiAuthDO::getStartDate, reqVO.getStartDate())
                .betweenIfPresent(ApiAuthDO::getExpireDate, reqVO.getExpireDate())
                .eqIfPresent(ApiAuthDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ApiAuthDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ApiAuthDO::getId));
    }

}