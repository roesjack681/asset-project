package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * VIEW Service 接口
 *
 * @author 管理员
 */
public interface ViewOpsMonitorResultsService {
    /**
     * 获得VIEW
     *
     * @param id 编号
     * @return VIEW
     */
    ViewOpsMonitorResultsDO getViewOpsMonitorResults(String id);

    /**
     * 获得VIEW分页
     *
     * @param pageReqVO 分页查询
     * @return VIEW分页
     */
    PageResult<ViewOpsMonitorResultsDO> getViewOpsMonitorResultsPage(ViewOpsMonitorResultsPageReqVO pageReqVO);
}