package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorPingAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 电警通断检测管理 Service 接口
 *
 * @author 管理员
 */
public interface PoliceMonitorPingAnalysisService {

    /**
     * 创建电警通断检测管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorPingAnalysis(@Valid PoliceMonitorPingAnalysisSaveReqVO createReqVO);

    /**
     * 更新电警通断检测管理
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorPingAnalysis(@Valid PoliceMonitorPingAnalysisSaveReqVO updateReqVO);

    /**
     * 删除电警通断检测管理
     *
     * @param id 编号
     */
    void deleteMonitorPingAnalysis(String id);

    /**
     * 获得电警通断检测管理
     *
     * @param id 编号
     * @return 电警通断检测管理
     */
    PoliceMonitorPingAnalysisDO getMonitorPingAnalysis(String id);

    /**
     * 获得电警通断检测管理分页
     *
     * @param pageReqVO 分页查询
     * @return 电警通断检测管理分页
     */
    PageResult<PoliceMonitorPingAnalysisDO> getMonitorPingAnalysisPage(PoliceMonitorPingAnalysisPageReqVO pageReqVO);

}