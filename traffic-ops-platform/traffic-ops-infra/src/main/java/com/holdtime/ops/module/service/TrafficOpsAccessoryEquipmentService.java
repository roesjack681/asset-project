package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsAccessoryEquipmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 杆件附属设备 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsAccessoryEquipmentService {

    /**
     * 创建杆件附属设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsAccessoryEquipment(@Valid TrafficOpsAccessoryEquipmentSaveReqVO createReqVO);

    /**
     * 更新杆件附属设备
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsAccessoryEquipment(@Valid TrafficOpsAccessoryEquipmentSaveReqVO updateReqVO);

    /**
     * 删除杆件附属设备
     *
     * @param id 编号
     */
    void deleteOpsAccessoryEquipment(String id);

    /**
     * 获得杆件附属设备
     *
     * @param id 编号
     * @return 杆件附属设备
     */
    TrafficOpsAccessoryEquipmentDO getOpsAccessoryEquipment(String id);

    /**
     * 获得杆件附属设备分页
     *
     * @param pageReqVO 分页查询
     * @return 杆件附属设备分页
     */
    PageResult<TrafficOpsAccessoryEquipmentDO> getOpsAccessoryEquipmentPage(TrafficOpsAccessoryEquipmentPageReqVO pageReqVO);

}