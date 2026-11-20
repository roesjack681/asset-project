package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 信号灯 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsSignalService {

    /**
     * 创建信号灯
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsSignal(@Valid TrafficOpsSignalSaveReqVO createReqVO);

    /**
     * 更新信号灯
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsSignal(@Valid TrafficOpsSignalSaveReqVO updateReqVO);

    /**
     * 删除信号灯
     *
     * @param id 编号
     */
    void deleteOpsSignal(String id);

    /**
     * 获得信号灯
     *
     * @param id 编号
     * @return 信号灯
     */
    TrafficOpsSignalDO getOpsSignal(String id);

    /**
     * 获得信号灯分页
     *
     * @param pageReqVO 分页查询
     * @return 信号灯分页
     */
    PageResult<TrafficOpsSignalDO> getOpsSignalPage(TrafficOpsSignalPageReqVO pageReqVO);

}