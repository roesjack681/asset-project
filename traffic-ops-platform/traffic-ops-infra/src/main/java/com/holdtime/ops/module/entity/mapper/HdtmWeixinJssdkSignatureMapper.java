package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.HdtmWeixinJssdkSignatureDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 微信JS SDK签名信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface HdtmWeixinJssdkSignatureMapper extends BaseMapperX<HdtmWeixinJssdkSignatureDO> {

    default PageResult<HdtmWeixinJssdkSignatureDO> selectPage(HdtmWeixinJssdkSignaturePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HdtmWeixinJssdkSignatureDO>()
                .eqIfPresent(HdtmWeixinJssdkSignatureDO::getSignature, reqVO.getSignature())
                .eqIfPresent(HdtmWeixinJssdkSignatureDO::getTimessamp, reqVO.getTimessamp())
                .eqIfPresent(HdtmWeixinJssdkSignatureDO::getNonceStr, reqVO.getNonceStr())
                .eqIfPresent(HdtmWeixinJssdkSignatureDO::getUrl, reqVO.getUrl())
                .eqIfPresent(HdtmWeixinJssdkSignatureDO::getJsapiTicket, reqVO.getJsapiTicket())
                .betweenIfPresent(HdtmWeixinJssdkSignatureDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(HdtmWeixinJssdkSignatureDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(HdtmWeixinJssdkSignatureDO::getId));
    }

}