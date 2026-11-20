package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备监测结果导入信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficMonitorResultsImportDetailService {

    /**
     * 创建设备监测结果导入信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResultsImportDetail(@Valid TrafficMonitorResultsImportDetailSaveReqVO createReqVO);

    /**
     * 更新设备监测结果导入信息
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResultsImportDetail(@Valid TrafficMonitorResultsImportDetailSaveReqVO updateReqVO);

    /**
     * 删除设备监测结果导入信息
     *
     * @param id 编号
     */
    void deleteMonitorResultsImportDetail(String id);

    /**
     * 获得设备监测结果导入信息
     *
     * @param id 编号
     * @return 设备监测结果导入信息
     */
    TrafficMonitorResultsImportDetailDO getMonitorResultsImportDetail(String id);

    /**
     * 获得设备监测结果导入信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备监测结果导入信息分页
     */
    PageResult<TrafficMonitorResultsImportDetailDO> getMonitorResultsImportDetailPage(TrafficMonitorResultsImportDetailPageReqVO pageReqVO);

}