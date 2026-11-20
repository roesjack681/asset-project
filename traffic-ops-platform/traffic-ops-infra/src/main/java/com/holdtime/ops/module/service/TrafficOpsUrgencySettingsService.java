package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsUrgencySettingsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 维保紧急程度配置 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsUrgencySettingsService {

    /**
     * 创建维保紧急程度配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsUrgencySettings(@Valid TrafficOpsUrgencySettingsSaveReqVO createReqVO);

    /**
     * 更新维保紧急程度配置
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsUrgencySettings(@Valid TrafficOpsUrgencySettingsSaveReqVO updateReqVO);

    /**
     * 删除维保紧急程度配置
     *
     * @param id 编号
     */
    void deleteOpsUrgencySettings(String id);

    /**
     * 获得维保紧急程度配置
     *
     * @param id 编号
     * @return 维保紧急程度配置
     */
    TrafficOpsUrgencySettingsDO getOpsUrgencySettings(String id);

    /**
     * 获得维保紧急程度配置分页
     *
     * @param pageReqVO 分页查询
     * @return 维保紧急程度配置分页
     */
    PageResult<TrafficOpsUrgencySettingsDO> getOpsUrgencySettingsPage(TrafficOpsUrgencySettingsPageReqVO pageReqVO);

}