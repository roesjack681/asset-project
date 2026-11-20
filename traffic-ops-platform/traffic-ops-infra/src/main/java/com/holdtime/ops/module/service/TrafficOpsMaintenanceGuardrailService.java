package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceGuardrailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 护栏维护详细 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsMaintenanceGuardrailService {

    /**
     * 创建护栏维护详细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintenanceGuardrail(@Valid TrafficOpsMaintenanceGuardrailSaveReqVO createReqVO);

    /**
     * 更新护栏维护详细
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintenanceGuardrail(@Valid TrafficOpsMaintenanceGuardrailSaveReqVO updateReqVO);

    /**
     * 删除护栏维护详细
     *
     * @param id 编号
     */
    void deleteOpsMaintenanceGuardrail(String id);

    /**
     * 获得护栏维护详细
     *
     * @param id 编号
     * @return 护栏维护详细
     */
    TrafficOpsMaintenanceGuardrailDO getOpsMaintenanceGuardrail(String id);

    /**
     * 获得护栏维护详细分页
     *
     * @param pageReqVO 分页查询
     * @return 护栏维护详细分页
     */
    PageResult<TrafficOpsMaintenanceGuardrailDO> getOpsMaintenanceGuardrailPage(TrafficOpsMaintenanceGuardrailPageReqVO pageReqVO);

}