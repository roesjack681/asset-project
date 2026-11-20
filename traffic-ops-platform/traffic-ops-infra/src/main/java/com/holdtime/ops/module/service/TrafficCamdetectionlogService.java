package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficCamdetectionlogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备状态检测 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficCamdetectionlogService {

    /**
     * 创建设备状态检测
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCamdetectionlog(@Valid TrafficCamdetectionlogSaveReqVO createReqVO);

    /**
     * 更新设备状态检测
     *
     * @param updateReqVO 更新信息
     */
    void updateCamdetectionlog(@Valid TrafficCamdetectionlogSaveReqVO updateReqVO);

    /**
     * 删除设备状态检测
     *
     * @param id 编号
     */
    void deleteCamdetectionlog(Long id);

    /**
     * 获得设备状态检测
     *
     * @param id 编号
     * @return 设备状态检测
     */
    TrafficCamdetectionlogDO getCamdetectionlog(Long id);

    /**
     * 获得设备状态检测分页
     *
     * @param pageReqVO 分页查询
     * @return 设备状态检测分页
     */
    PageResult<TrafficCamdetectionlogDO> getCamdetectionlogPage(TrafficCamdetectionlogPageReqVO pageReqVO);

}