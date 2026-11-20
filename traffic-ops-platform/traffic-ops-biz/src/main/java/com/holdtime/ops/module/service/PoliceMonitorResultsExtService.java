package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.PoliceMonitorResultsDO;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsPageReqVO;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsSaveReqVO;

/**
 * 公安设备监测结果 Service 接口
 *
 * @author 管理员
 */
public interface PoliceMonitorResultsExtService {
    /**
     * 获得公安设备监测结果分页
     *
     * @param pageReqVO 分页查询
     * @return 公安设备监测结果分页
     */
    PageResult<PoliceMonitorResultsDO> getMonitorResultsPage(PoliceMonitorResultsPageReqVO pageReqVO);

    /**
     * 上传检测结果
     * @param saveReqVO 保存检测结果
     */
    void updateMonitorResults(PoliceMonitorResultsSaveReqVO saveReqVO);

    /**
     * 上传检测结果
     * @param saveReqVO 保存检测结果
     */
    void remarkMonitorResults(PoliceMonitorResultsSaveReqVO saveReqVO);
}