package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsSmsConfigDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 资产短信发送配置 Service 接口
 *
 * @author 杨丽
 */
public interface TrafficOpsSmsConfigService {

    /**
     * 创建资产短信发送配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsSmsConfig(@Valid TrafficOpsSmsConfigSaveReqVO createReqVO);

    /**
     * 更新资产短信发送配置
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsSmsConfig(@Valid TrafficOpsSmsConfigSaveReqVO updateReqVO);

    /**
     * 删除资产短信发送配置
     *
     * @param id 编号
     */
    void deleteOpsSmsConfig(String id);

    /**
     * 获得资产短信发送配置
     *
     * @param id 编号
     * @return 资产短信发送配置
     */
    TrafficOpsSmsConfigDO getOpsSmsConfig(String id);

    /**
     * 获得资产短信发送配置分页
     *
     * @param pageReqVO 分页查询
     * @return 资产短信发送配置分页
     */
    PageResult<TrafficOpsSmsConfigDO> getOpsSmsConfigPage(TrafficOpsSmsConfigPageReqVO pageReqVO);

}