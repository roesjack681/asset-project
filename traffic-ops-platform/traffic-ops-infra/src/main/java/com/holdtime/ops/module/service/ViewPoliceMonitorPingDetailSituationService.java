package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewPoliceMonitorPingDetailSituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * VIEW Service 接口
 *
 * @author yanxl
 */
public interface ViewPoliceMonitorPingDetailSituationService {

    /**
     * 创建VIEW
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createViewPoliceMonitorPingDetailSituation(@Valid ViewPoliceMonitorPingDetailSituationSaveReqVO createReqVO);

    /**
     * 更新VIEW
     *
     * @param updateReqVO 更新信息
     */
    void updateViewPoliceMonitorPingDetailSituation(@Valid ViewPoliceMonitorPingDetailSituationSaveReqVO updateReqVO);

    /**
     * 删除VIEW
     *
     * @param id 编号
     */
    void deleteViewPoliceMonitorPingDetailSituation(String id);

    /**
     * 获得VIEW
     *
     * @param id 编号
     * @return VIEW
     */
    ViewPoliceMonitorPingDetailSituationDO getViewPoliceMonitorPingDetailSituation(String id);

    /**
     * 获得VIEW分页
     *
     * @param pageReqVO 分页查询
     * @return VIEW分页
     */
    PageResult<ViewPoliceMonitorPingDetailSituationDO> getViewPoliceMonitorPingDetailSituationPage(ViewPoliceMonitorPingDetailSituationPageReqVO pageReqVO);

}