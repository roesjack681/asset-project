package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.model.vo.*;

import javax.validation.Valid;

/**
 * 警用设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsPoliceMaintenanceExtService {

    /**
     * 保存、修改警用设备维保记录
     *
     * @param saveReqVO 维保记录
     * @return 编号
     */
    String saveOpsPoliceMaintenance(@Valid TrafficOpsPoliceMaintenanceExtSaveReqVO saveReqVO);

    /**
     * 获取警用设备维保信息分页
     * @param pageReqVO 查询条件
     * @return 维保信息分页
     */
    PageResult<TrafficOpsPoliceMaintenanceExtRespVO> getOpsPoliceMaintenancePage(@Valid TrafficOpsPoliceMaintenancePageReqVO pageReqVO);

    /**
     * 获得警用设备维保信息
     *
     * @param id 编号
     * @return 警用设备维保信息
     */
    TrafficOpsPoliceMaintenanceExtRespVO getOpsPoliceMaintenance(String id);

    /**
     * 审核
     * @param saveReqVO 表单信息
     */
    void auditOpsPoliceMaintenance(TrafficOpsPoliceMaintenanceSaveReqVO saveReqVO);

    /**
     * 保存警用设备维保进度信息
     * @param saveReqVO 警用设备维保进度信息
     */
    void saveOpsPoliceMaintenanceResult(TrafficOpsPoliceMaintenanceSaveReqVO saveReqVO);

    /**
     * 删除警用设备维保信息
     *
     * @param id 编号
     */
    void deleteOpsPoliceMaintenance(String id);
}