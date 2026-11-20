package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioEquipmentMonitorDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 集指设备检测记录 Service 接口
 *
 * @author 管理员
 */
public interface TrafficVioEquipmentMonitorDetailService {

    /**
     * 创建集指设备检测记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createVioEquipmentMonitorDetail(@Valid TrafficVioEquipmentMonitorDetailSaveReqVO createReqVO);

    /**
     * 更新集指设备检测记录
     *
     * @param updateReqVO 更新信息
     */
    void updateVioEquipmentMonitorDetail(@Valid TrafficVioEquipmentMonitorDetailSaveReqVO updateReqVO);

    /**
     * 删除集指设备检测记录
     *
     * @param id 编号
     */
    void deleteVioEquipmentMonitorDetail(String id);

    /**
     * 获得集指设备检测记录
     *
     * @param id 编号
     * @return 集指设备检测记录
     */
    TrafficVioEquipmentMonitorDetailDO getVioEquipmentMonitorDetail(String id);

    /**
     * 获得集指设备检测记录分页
     *
     * @param pageReqVO 分页查询
     * @return 集指设备检测记录分页
     */
    PageResult<TrafficVioEquipmentMonitorDetailDO> getVioEquipmentMonitorDetailPage(TrafficVioEquipmentMonitorDetailPageReqVO pageReqVO);

}