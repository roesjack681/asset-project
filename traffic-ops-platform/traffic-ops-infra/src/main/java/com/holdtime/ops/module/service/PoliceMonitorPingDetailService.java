package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorPingDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 公安监测通达详情 Service 接口
 *
 * @author 管理员
 */
public interface PoliceMonitorPingDetailService {

    /**
     * 创建公安监测通达详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorPingDetail(@Valid PoliceMonitorPingDetailSaveReqVO createReqVO);

    /**
     * 更新公安监测通达详情
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorPingDetail(@Valid PoliceMonitorPingDetailSaveReqVO updateReqVO);

    /**
     * 删除公安监测通达详情
     *
     * @param id 编号
     */
    void deleteMonitorPingDetail(String id);

    /**
     * 获得公安监测通达详情
     *
     * @param id 编号
     * @return 公安监测通达详情
     */
    PoliceMonitorPingDetailDO getMonitorPingDetail(String id);

    /**
     * 获得公安监测通达详情分页
     *
     * @param pageReqVO 分页查询
     * @return 公安监测通达详情分页
     */
    PageResult<PoliceMonitorPingDetailDO> getMonitorPingDetailPage(PoliceMonitorPingDetailPageReqVO pageReqVO);

}