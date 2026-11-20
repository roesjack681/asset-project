package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsTempReportDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 交警运维临时报 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsTempReportService {

    /**
     * 创建交警运维临时报
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsTempReport(@Valid TrafficOpsTempReportSaveReqVO createReqVO);

    /**
     * 更新交警运维临时报
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsTempReport(@Valid TrafficOpsTempReportSaveReqVO updateReqVO);

    /**
     * 删除交警运维临时报
     *
     * @param id 编号
     */
    void deleteOpsTempReport(String id);

    /**
     * 获得交警运维临时报
     *
     * @param id 编号
     * @return 交警运维临时报
     */
    TrafficOpsTempReportDO getOpsTempReport(String id);

    /**
     * 获得交警运维临时报分页
     *
     * @param pageReqVO 分页查询
     * @return 交警运维临时报分页
     */
    PageResult<TrafficOpsTempReportDO> getOpsTempReportPage(TrafficOpsTempReportPageReqVO pageReqVO);

}