package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 公安设备监测结果 Service 接口
 *
 * @author 管理员
 */
public interface PoliceMonitorResultsService {

    /**
     * 创建公安设备监测结果
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResults(@Valid PoliceMonitorResultsSaveReqVO createReqVO);

    /**
     * 更新公安设备监测结果
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResults(@Valid PoliceMonitorResultsSaveReqVO updateReqVO);

    /**
     * 删除公安设备监测结果
     *
     * @param id 编号
     */
    void deleteMonitorResults(String id);

    /**
     * 获得公安设备监测结果
     *
     * @param id 编号
     * @return 公安设备监测结果
     */
    PoliceMonitorResultsDO getMonitorResults(String id);

    /**
     * 获得公安设备监测结果分页
     *
     * @param pageReqVO 分页查询
     * @return 公安设备监测结果分页
     */
    PageResult<PoliceMonitorResultsDO> getMonitorResultsPage(PoliceMonitorResultsPageReqVO pageReqVO);

}