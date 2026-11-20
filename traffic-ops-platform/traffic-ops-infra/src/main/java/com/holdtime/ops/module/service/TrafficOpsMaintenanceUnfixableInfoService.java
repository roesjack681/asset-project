package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceUnfixableInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备维保无法修复信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsMaintenanceUnfixableInfoService {

    /**
     * 创建设备维保无法修复信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintenanceUnfixableInfo(@Valid TrafficOpsMaintenanceUnfixableInfoSaveReqVO createReqVO);

    /**
     * 更新设备维保无法修复信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintenanceUnfixableInfo(@Valid TrafficOpsMaintenanceUnfixableInfoSaveReqVO updateReqVO);

    /**
     * 删除设备维保无法修复信息
     *
     * @param id 编号
     */
    void deleteOpsMaintenanceUnfixableInfo(String id);

    /**
     * 获得设备维保无法修复信息
     *
     * @param id 编号
     * @return 设备维保无法修复信息
     */
    TrafficOpsMaintenanceUnfixableInfoDO getOpsMaintenanceUnfixableInfo(String id);

    /**
     * 获得设备维保无法修复信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备维保无法修复信息分页
     */
    PageResult<TrafficOpsMaintenanceUnfixableInfoDO> getOpsMaintenanceUnfixableInfoPage(TrafficOpsMaintenanceUnfixableInfoPageReqVO pageReqVO);

}