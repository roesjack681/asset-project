package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.BtjczhVioSurveilAttachmentDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 违法抓拍附件 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BtjczhVioSurveilAttachmentMapper extends BaseMapperX<BtjczhVioSurveilAttachmentDO> {

    default PageResult<BtjczhVioSurveilAttachmentDO> selectPage(BtjczhVioSurveilAttachmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BtjczhVioSurveilAttachmentDO>()
                .eqIfPresent(BtjczhVioSurveilAttachmentDO::getHpzl, reqVO.getHpzl())
                .eqIfPresent(BtjczhVioSurveilAttachmentDO::getHphm, reqVO.getHphm())
                .eqIfPresent(BtjczhVioSurveilAttachmentDO::getUrl1, reqVO.getUrl1())
                .eqIfPresent(BtjczhVioSurveilAttachmentDO::getUrl2, reqVO.getUrl2())
                .eqIfPresent(BtjczhVioSurveilAttachmentDO::getUrl3, reqVO.getUrl3())
                .eqIfPresent(BtjczhVioSurveilAttachmentDO::getLrsj, reqVO.getLrsj())
                .betweenIfPresent(BtjczhVioSurveilAttachmentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BtjczhVioSurveilAttachmentDO::getId));
    }

}