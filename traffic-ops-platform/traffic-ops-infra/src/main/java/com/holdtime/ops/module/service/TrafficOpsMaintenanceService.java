package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsMaintenanceService {

    /**
     * 创建设备维保信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintenance(@Valid TrafficOpsMaintenanceSaveReqVO createReqVO);

    /**
     * 更新设备维保信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintenance(@Valid TrafficOpsMaintenanceSaveReqVO updateReqVO);

    /**
     * 删除设备维保信息
     *
     * @param id 编号
     */
    void deleteOpsMaintenance(String id);

    /**
     * 获得设备维保信息
     *
     * @param id 编号
     * @return 设备维保信息
     */
    TrafficOpsMaintenanceDO getOpsMaintenance(String id);

    /**
     * 获得设备维保信息分页
     *
     * @param isPagination 是否分页
     * @param pageReqVO 分页查询
     * @return 设备维保信息分页
     */
    PageResult<TrafficOpsMaintenanceDO> getOpsMaintenancePage(String isPagination, TrafficOpsMaintenancePageReqVO pageReqVO);

}