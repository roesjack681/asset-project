package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 维保记录 Service 接口
 *
 * @author yangli
 */
public interface TrafficOpsMaintenanceRecordService {

    /**
     * 创建维保记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintenanceRecord(@Valid TrafficOpsMaintenanceRecordSaveReqVO createReqVO);

    /**
     * 更新维保记录
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintenanceRecord(@Valid TrafficOpsMaintenanceRecordSaveReqVO updateReqVO);

    /**
     * 删除维保记录
     *
     * @param id 编号
     */
    void deleteOpsMaintenanceRecord(String id);

    /**
     * 获得维保记录
     *
     * @param id 编号
     * @return 维保记录
     */
    TrafficOpsMaintenanceRecordDO getOpsMaintenanceRecord(String id);

    /**
     * 获得维保记录分页
     *
     * @param pageReqVO 分页查询
     * @return 维保记录分页
     */
    PageResult<TrafficOpsMaintenanceRecordDO> getOpsMaintenanceRecordPage(TrafficOpsMaintenanceRecordPageReqVO pageReqVO);

}