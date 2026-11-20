package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionDeviceInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 信控交叉口设备信息 Service 接口
 *
 * @author yangli
 */
public interface TrafficTslJunctionDeviceInfoService {

    /**
     * 创建信控交叉口设备信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createTslJunctionDeviceInfo(@Valid TrafficTslJunctionDeviceInfoSaveReqVO createReqVO);

    /**
     * 更新信控交叉口设备信息
     *
     * @param updateReqVO 更新信息
     */
    void updateTslJunctionDeviceInfo(@Valid TrafficTslJunctionDeviceInfoSaveReqVO updateReqVO);

    /**
     * 删除信控交叉口设备信息
     *
     * @param id 编号
     */
    void deleteTslJunctionDeviceInfo(String id);

    /**
     * 获得信控交叉口设备信息
     *
     * @param id 编号
     * @return 信控交叉口设备信息
     */
    TrafficTslJunctionDeviceInfoDO getTslJunctionDeviceInfo(String id);

    /**
     * 获得信控交叉口设备信息分页
     *
     * @param pageReqVO 分页查询
     * @return 信控交叉口设备信息分页
     */
    PageResult<TrafficTslJunctionDeviceInfoDO> getTslJunctionDeviceInfoPage(TrafficTslJunctionDeviceInfoPageReqVO pageReqVO);

}