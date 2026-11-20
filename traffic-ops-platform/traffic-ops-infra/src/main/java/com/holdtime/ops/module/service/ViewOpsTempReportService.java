package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsTempReportDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * VIEW Service 接口
 *
 * @author 管理员
 */
public interface ViewOpsTempReportService {

    /**
     * 创建VIEW
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createViewOpsTempReport(@Valid ViewOpsTempReportSaveReqVO createReqVO);

    /**
     * 更新VIEW
     *
     * @param updateReqVO 更新信息
     */
    void updateViewOpsTempReport(@Valid ViewOpsTempReportSaveReqVO updateReqVO);

    /**
     * 删除VIEW
     *
     * @param id 编号
     */
    void deleteViewOpsTempReport(String id);

    /**
     * 获得VIEW
     *
     * @param id 编号
     * @return VIEW
     */
    ViewOpsTempReportDO getViewOpsTempReport(String id);

    /**
     * 获得VIEW分页
     *
     * @param pageReqVO 分页查询
     * @return VIEW分页
     */
    PageResult<ViewOpsTempReportDO> getViewOpsTempReportPage(ViewOpsTempReportPageReqVO pageReqVO);

}