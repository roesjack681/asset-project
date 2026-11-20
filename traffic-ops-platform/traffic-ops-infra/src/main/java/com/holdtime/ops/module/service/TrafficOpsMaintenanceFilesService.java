package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceFilesDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 维保材料信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsMaintenanceFilesService {

    /**
     * 创建维保材料信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintenanceFiles(@Valid AppTrafficOpsMaintenanceFilesSaveReqVO createReqVO);

    /**
     * 更新维保材料信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintenanceFiles(@Valid AppTrafficOpsMaintenanceFilesSaveReqVO updateReqVO);

    /**
     * 删除维保材料信息
     *
     * @param id 编号
     */
    void deleteOpsMaintenanceFiles(String id);

    /**
     * 获得维保材料信息
     *
     * @param id 编号
     * @return 维保材料信息
     */
    TrafficOpsMaintenanceFilesDO getOpsMaintenanceFiles(String id);

    /**
     * 获得维保材料信息分页
     *
     * @param pageReqVO 分页查询
     * @return 维保材料信息分页
     */
    PageResult<TrafficOpsMaintenanceFilesDO> getOpsMaintenanceFilesPage(AppTrafficOpsMaintenanceFilesPageReqVO pageReqVO);

}