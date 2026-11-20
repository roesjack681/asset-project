package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceAuxiliaryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 护栏辅助设备维护详细 Service 接口
 *
 * @author yanxl
 */
public interface TrafficOpsMaintenanceAuxiliaryService {

    /**
     * 创建护栏辅助设备维护详细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintenanceAuxiliary(@Valid TrafficOpsMaintenanceAuxiliarySaveReqVO createReqVO);

    /**
     * 更新护栏辅助设备维护详细
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintenanceAuxiliary(@Valid TrafficOpsMaintenanceAuxiliarySaveReqVO updateReqVO);

    /**
     * 删除护栏辅助设备维护详细
     *
     * @param id 编号
     */
    void deleteOpsMaintenanceAuxiliary(String id);

    /**
     * 获得护栏辅助设备维护详细
     *
     * @param id 编号
     * @return 护栏辅助设备维护详细
     */
    TrafficOpsMaintenanceAuxiliaryDO getOpsMaintenanceAuxiliary(String id);

    /**
     * 获得护栏辅助设备维护详细分页
     *
     * @param pageReqVO 分页查询
     * @return 护栏辅助设备维护详细分页
     */
    PageResult<TrafficOpsMaintenanceAuxiliaryDO> getOpsMaintenanceAuxiliaryPage(TrafficOpsMaintenanceAuxiliaryPageReqVO pageReqVO);

}