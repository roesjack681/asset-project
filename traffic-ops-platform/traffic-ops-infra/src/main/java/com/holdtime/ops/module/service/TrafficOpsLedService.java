package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsLedDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * Led屏 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsLedService {

    /**
     * 创建Led屏
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsLed(@Valid TrafficOpsLedSaveReqVO createReqVO);

    /**
     * 更新Led屏
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsLed(@Valid TrafficOpsLedSaveReqVO updateReqVO);

    /**
     * 删除Led屏
     *
     * @param id 编号
     */
    void deleteOpsLed(String id);

    /**
     * 获得Led屏
     *
     * @param id 编号
     * @return Led屏
     */
    TrafficOpsLedDO getOpsLed(String id);

    /**
     * 获得Led屏分页
     *
     * @param pageReqVO 分页查询
     * @return Led屏分页
     */
    PageResult<TrafficOpsLedDO> getOpsLedPage(TrafficOpsLedPageReqVO pageReqVO);

}