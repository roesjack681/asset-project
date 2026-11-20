package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.entity.PoliceMonitorPingAnalysisDO;
import com.holdtime.ops.module.model.dto.PoliceMonitorPingDetailDTO;
import com.holdtime.ops.module.model.dto.TrafficOpsMonitorDetailResultDTO;
import com.holdtime.ops.module.model.vo.*;

import javax.validation.Valid;

/**
 * 电警通断检测管理 Service 接口
 *
 * @author 管理员
 */
public interface PoliceMonitorPingAnalysisExtService {

    /**
     * 获得电警通断检测管理分页
     *
     * @param pageReqVO 分页查询
     * @return 电警通断检测管理分页
     */
    PageResult<PoliceMonitorPingAnalysisExtRespVO> getMonitorPingAnalysisPage(PoliceMonitorPingAnalysisExtPageReqVO pageReqVO);

    /**
     * 查询公安通断检测详情
     * @param page 分页信息
     * @param equId 设备编号
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 检测分析结果
     */
    PageResult<PoliceMonitorPingDetailDTO> queryMonitorPingDetailResult(Page<?> page, String equId, String startDate, String endDate);
}