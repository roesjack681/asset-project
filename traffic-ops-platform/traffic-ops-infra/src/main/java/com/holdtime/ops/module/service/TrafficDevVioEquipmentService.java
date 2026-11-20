package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficDevVioEquipmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 违法抓拍设备 Service 接口
 *
 * @author 管理员
 */
public interface TrafficDevVioEquipmentService {

    /**
     * 创建违法抓拍设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createDevVioEquipment(@Valid TrafficDevVioEquipmentSaveReqVO createReqVO);

    /**
     * 更新违法抓拍设备
     *
     * @param updateReqVO 更新信息
     */
    void updateDevVioEquipment(@Valid TrafficDevVioEquipmentSaveReqVO updateReqVO);

    /**
     * 删除违法抓拍设备
     *
     * @param id 编号
     */
    void deleteDevVioEquipment(String id);

    /**
     * 获得违法抓拍设备
     *
     * @param id 编号
     * @return 违法抓拍设备
     */
    TrafficDevVioEquipmentDO getDevVioEquipment(String id);

    /**
     * 获得违法抓拍设备分页
     *
     * @param pageReqVO 分页查询
     * @return 违法抓拍设备分页
     */
    PageResult<TrafficDevVioEquipmentDO> getDevVioEquipmentPage(TrafficDevVioEquipmentPageReqVO pageReqVO);

}