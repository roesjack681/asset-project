package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.HdtmWeixinJssdkSignatureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 微信JS SDK签名信息 Service 接口
 *
 * @author yangli
 */
public interface HdtmWeixinJssdkSignatureService {

    /**
     * 创建微信JS SDK签名信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createWeixinJssdkSignature(@Valid HdtmWeixinJssdkSignatureSaveReqVO createReqVO);

    /**
     * 更新微信JS SDK签名信息
     *
     * @param updateReqVO 更新信息
     */
    void updateWeixinJssdkSignature(@Valid HdtmWeixinJssdkSignatureSaveReqVO updateReqVO);

    /**
     * 删除微信JS SDK签名信息
     *
     * @param id 编号
     */
    void deleteWeixinJssdkSignature(String id);

    /**
     * 获得微信JS SDK签名信息
     *
     * @param id 编号
     * @return 微信JS SDK签名信息
     */
    HdtmWeixinJssdkSignatureDO getWeixinJssdkSignature(String id);

    /**
     * 获得微信JS SDK签名信息分页
     *
     * @param pageReqVO 分页查询
     * @return 微信JS SDK签名信息分页
     */
    PageResult<HdtmWeixinJssdkSignatureDO> getWeixinJssdkSignaturePage(HdtmWeixinJssdkSignaturePageReqVO pageReqVO);

}