package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.model.dto.TrafficOpsMemeberCameraDTO;
import com.holdtime.ops.module.model.vo.TrafficMonitorResultsExtSaveReqVO;

import javax.validation.Valid;

/**
 * 设备监测结果 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficMonitorResultsExtService {

    /**
     * 创建设备监测结果
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResults(@Valid TrafficMonitorResultsExtSaveReqVO createReqVO);

    /**
     * 分析设备监测结果
     * @param page 分页信息
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 检测分析结果
     */
    PageResult<TrafficOpsMemeberCameraDTO> analysisMonitorResult(Page<?> page, String startDate, String endDate);

    /**
     * 检测数据归档
     */
    void archiveTrafficMonitorResults();
}