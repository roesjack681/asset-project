package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsPoliceStationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 警用装备采集站 Service 接口
 *
 * @author 杨丽
 */
public interface TrafficOpsPoliceStationService {

    /**
     * 创建警用装备采集站
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsPoliceStation(@Valid TrafficOpsPoliceStationSaveReqVO createReqVO);

    /**
     * 更新警用装备采集站
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsPoliceStation(@Valid TrafficOpsPoliceStationSaveReqVO updateReqVO);

    /**
     * 删除警用装备采集站
     *
     * @param id 编号
     */
    void deleteOpsPoliceStation(String id);

    /**
     * 获得警用装备采集站
     *
     * @param id 编号
     * @return 警用装备采集站
     */
    TrafficOpsPoliceStationDO getOpsPoliceStation(String id);

    /**
     * 获得警用装备采集站分页
     *
     * @param pageReqVO 分页查询
     * @return 警用装备采集站分页
     */
    PageResult<TrafficOpsPoliceStationDO> getOpsPoliceStationPage(TrafficOpsPoliceStationPageReqVO pageReqVO);

}