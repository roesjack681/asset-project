package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceUnfixableFollowDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备维保无法修复跟进 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsMaintenanceUnfixableFollowService {

    /**
     * 创建设备维保无法修复跟进
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintenanceUnfixableFollow(@Valid TrafficOpsMaintenanceUnfixableFollowSaveReqVO createReqVO);

    /**
     * 更新设备维保无法修复跟进
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintenanceUnfixableFollow(@Valid TrafficOpsMaintenanceUnfixableFollowSaveReqVO updateReqVO);

    /**
     * 删除设备维保无法修复跟进
     *
     * @param id 编号
     */
    void deleteOpsMaintenanceUnfixableFollow(String id);

    /**
     * 获得设备维保无法修复跟进
     *
     * @param id 编号
     * @return 设备维保无法修复跟进
     */
    TrafficOpsMaintenanceUnfixableFollowDO getOpsMaintenanceUnfixableFollow(String id);

    /**
     * 获得设备维保无法修复跟进分页
     *
     * @param pageReqVO 分页查询
     * @return 设备维保无法修复跟进分页
     */
    PageResult<TrafficOpsMaintenanceUnfixableFollowDO> getOpsMaintenanceUnfixableFollowPage(TrafficOpsMaintenanceUnfixableFollowPageReqVO pageReqVO);

}