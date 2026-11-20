package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.PoliceMonitorResultsHistoryDO;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsHistoryPageReqVO;

/**
 * 公安设备监测结果历史 Service 接口
 *
 * @author yanxl
 */
public interface PoliceMonitorResultsHistoryExtService {
    /**
     * 删除2天前的历史记录
     */
    void deleteAllOldMonitorResultsHistory();

    /**
     * 导入公安设备检测结果历史记录(保留近2天记录)
     * @param monitorId 检测结果导入ID
     */
    void saveMonitorResultsHistory(String monitorId);

    /**
     * 获得公安设备监测结果历史分页
     *
     * @param pageReqVO 分页查询
     * @return 公安设备监测结果历史分页
     */
    PageResult<PoliceMonitorResultsHistoryDO> getMonitorResultsHistoryPage(PoliceMonitorResultsHistoryPageReqVO pageReqVO);
}