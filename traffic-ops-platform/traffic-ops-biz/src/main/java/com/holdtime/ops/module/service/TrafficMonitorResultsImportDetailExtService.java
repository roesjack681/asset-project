package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficMonitorResultsImportDetailExtPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficMonitorResultsImportDetailExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficMonitorResultsImportDetailSaveReqVO;

/**
 * 设备监测结果导入信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficMonitorResultsImportDetailExtService {

    /**
     * 获得设备监测结果导入信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备监测结果导入信息分页
     */
    PageResult<TrafficMonitorResultsImportDetailExtRespVO> getMonitorResultsImportDetailPage(TrafficMonitorResultsImportDetailExtPageReqVO pageReqVO);

    /**
     * 检测结果备注
     * @param saveReqVO 保存检测结果
     */
    void remarkMonitorResults(TrafficMonitorResultsImportDetailSaveReqVO saveReqVO);
}