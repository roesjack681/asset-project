package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorPingSituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 公安通断监测近两日状态 Service 接口
 *
 * @author yanxl
 */
public interface PoliceMonitorPingSituationService {

    /**
     * 创建公安通断监测近两日状态
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorPingSituation(@Valid PoliceMonitorPingSituationSaveReqVO createReqVO);

    /**
     * 更新公安通断监测近两日状态
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorPingSituation(@Valid PoliceMonitorPingSituationSaveReqVO updateReqVO);

    /**
     * 删除公安通断监测近两日状态
     *
     * @param id 编号
     */
    void deleteMonitorPingSituation(String id);

    /**
     * 获得公安通断监测近两日状态
     *
     * @param id 编号
     * @return 公安通断监测近两日状态
     */
    PoliceMonitorPingSituationDO getMonitorPingSituation(String id);

    /**
     * 获得公安通断监测近两日状态分页
     *
     * @param pageReqVO 分页查询
     * @return 公安通断监测近两日状态分页
     */
    PageResult<PoliceMonitorPingSituationDO> getMonitorPingSituationPage(PoliceMonitorPingSituationPageReqVO pageReqVO);

}