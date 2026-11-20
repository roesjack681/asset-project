package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsPoliceEquipmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 警用装备资产信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsPoliceEquipmentService {

    /**
     * 创建警用装备资产信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsPoliceEquipment(@Valid TrafficOpsPoliceEquipmentSaveReqVO createReqVO);

    /**
     * 更新警用装备资产信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsPoliceEquipment(@Valid TrafficOpsPoliceEquipmentSaveReqVO updateReqVO);

    /**
     * 删除警用装备资产信息
     *
     * @param id 编号
     */
    void deleteOpsPoliceEquipment(String id);

    /**
     * 获得警用装备资产信息
     *
     * @param id 编号
     * @return 警用装备资产信息
     */
    TrafficOpsPoliceEquipmentDO getOpsPoliceEquipment(String id);

    /**
     * 获得警用装备资产信息分页
     *
     * @param pageReqVO 分页查询
     * @return 警用装备资产信息分页
     */
    PageResult<TrafficOpsPoliceEquipmentDO> getOpsPoliceEquipmentPage(TrafficOpsPoliceEquipmentPageReqVO pageReqVO);

}