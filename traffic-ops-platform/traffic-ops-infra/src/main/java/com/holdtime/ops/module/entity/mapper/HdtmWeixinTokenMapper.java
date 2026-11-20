package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.HdtmWeixinTokenDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 微信Token Mapper
 *
 * @author yangli
 */
@Mapper
public interface HdtmWeixinTokenMapper extends BaseMapperX<HdtmWeixinTokenDO> {

    default PageResult<HdtmWeixinTokenDO> selectPage(HdtmWeixinTokenPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HdtmWeixinTokenDO>()
                .eqIfPresent(HdtmWeixinTokenDO::getAccessToken, reqVO.getAccessToken())
                .eqIfPresent(HdtmWeixinTokenDO::getGetTokenMillis, reqVO.getGetTokenMillis())
                .eqIfPresent(HdtmWeixinTokenDO::getJsapiTicket, reqVO.getJsapiTicket())
                .eqIfPresent(HdtmWeixinTokenDO::getGetTicketMillis, reqVO.getGetTicketMillis())
                .betweenIfPresent(HdtmWeixinTokenDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(HdtmWeixinTokenDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(HdtmWeixinTokenDO::getId));
    }

}