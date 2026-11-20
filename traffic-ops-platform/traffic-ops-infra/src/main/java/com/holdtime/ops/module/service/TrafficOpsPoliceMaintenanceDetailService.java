package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsPoliceMaintenanceDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 警用装备报修详情信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsPoliceMaintenanceDetailService {

    /**
     * 创建警用装备报修详情信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsPoliceMaintenanceDetail(@Valid TrafficOpsPoliceMaintenanceDetailSaveReqVO createReqVO);

    /**
     * 更新警用装备报修详情信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsPoliceMaintenanceDetail(@Valid TrafficOpsPoliceMaintenanceDetailSaveReqVO updateReqVO);

    /**
     * 删除警用装备报修详情信息
     *
     * @param id 编号
     */
    void deleteOpsPoliceMaintenanceDetail(String id);

    /**
     * 获得警用装备报修详情信息
     *
     * @param id 编号
     * @return 警用装备报修详情信息
     */
    TrafficOpsPoliceMaintenanceDetailDO getOpsPoliceMaintenanceDetail(String id);

    /**
     * 获得警用装备报修详情信息分页
     *
     * @param pageReqVO 分页查询
     * @return 警用装备报修详情信息分页
     */
    PageResult<TrafficOpsPoliceMaintenanceDetailDO> getOpsPoliceMaintenanceDetailPage(TrafficOpsPoliceMaintenanceDetailPageReqVO pageReqVO);

}