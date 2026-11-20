package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.HdtmWeixinTokenDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 微信Token Service 接口
 *
 * @author yangli
 */
public interface HdtmWeixinTokenService {

    /**
     * 创建微信Token
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createWeixinToken(@Valid HdtmWeixinTokenSaveReqVO createReqVO);

    /**
     * 更新微信Token
     *
     * @param updateReqVO 更新信息
     */
    void updateWeixinToken(@Valid HdtmWeixinTokenSaveReqVO updateReqVO);

    /**
     * 删除微信Token
     *
     * @param id 编号
     */
    void deleteWeixinToken(String id);

    /**
     * 获得微信Token
     *
     * @param id 编号
     * @return 微信Token
     */
    HdtmWeixinTokenDO getWeixinToken(String id);

    /**
     * 获得微信Token分页
     *
     * @param pageReqVO 分页查询
     * @return 微信Token分页
     */
    PageResult<HdtmWeixinTokenDO> getWeixinTokenPage(HdtmWeixinTokenPageReqVO pageReqVO);

}