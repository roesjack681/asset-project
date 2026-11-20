package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserRespVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.model.vo.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsMaintenanceExtService {

    /**
     * 保存、修改维保记录
     *
     * @param saveReqVO 维保记录
     * @return 编号
     */
    String saveOpsMaintenance(@Valid TrafficOpsMaintenanceSaveReqVO saveReqVO, String source);

    /**
     * 保存所有
     *
     * @param saveReqVO 维保记录
     * @return 编号
     */
    String saveOpsMaintenance(@Valid TrafficOpsMaintenanceSaveAllReqVO saveReqVO);

    /**
     * 获取设备维保信息分页
     * @param pageReqVO 查询条件
     * @return 维保信息分页
     */
    PageResult<TrafficOpsMaintenanceRespVO> getOpsMaintenancePage(@Valid TrafficOpsMaintenancePageReqVO pageReqVO, String queryType);

    /**
     * 派单记录
     * @param saveReqVO 派单信息
     */
    void dispatchRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO);

    /**
     * 取消报修单记录
     * @param saveReqVO 派单信息
     */
    void cancelRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO);

    /**
     * 获得设备维保信息
     *
     * @param id 编号
     * @return 设备维保信息
     */
    TrafficOpsMaintenanceRespVO getOpsMaintenance(String id);

    /**
     * 获得设备维保进度信息
     * @param maintenanceId 维保记录ID
     * @return 设备维保进度信息
     */
    List<TrafficOpsMaintainProgressRespVO> getOpsMaintenanceProgress(String maintenanceId);

    /**
     * 维修员接单
     * @param saveReqVO 表单信息
     */
    void acceptRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO);

    /**
     * 保存维保进度信息
     * @param saveReqVO 维保进度信息
     */
    void saveMaintenanceProgress(TrafficOpsMaintainProgressSaveReqVO saveReqVO);

    /**
     * 查询待派单数及待维修数
     * @param userId 用户ID
     * @return 返回查询结果
     */
    Map<String, Long> queryRepairOrderCnt(Long userId, String affiliatedUnit);

    /**
     * PC保存派单
     * @param saveReqVO 派单信息
     */
    void dispatchRepairOrderPc(TrafficOpsMaintenanceSaveReqVO saveReqVO);

    /**
     * 删除设备维保信息
     *
     * @param id 编号
     */
    void deleteOpsMaintenance(String id);


    /**
     * 大屏获得设备维保信息分页
     * @param status
     * @return
     */
    TrafficOpsMaintenanceRespDpVO getOpsMaintenanceForDp(String status);


    /**
     * 批量派单、一键派单
     * @param saveReqVO 派单信息
     */
    void batchDispatchRepairOrder(TrafficOpsMaintenanceBatchDispatchSaveReqVO saveReqVO);

    /**
     * 报修单确认
     * @param confirmSaveReqVO 确认信息
     */
    void confirmRepairOrder(TrafficOpsMaintenanceConfirmSaveReqVO confirmSaveReqVO, Long userId);

    /**
     * 查询所有用户信息
     * @param deptId 部门ID
     */
    List<UserRespVO> queryAllUserInfoList(Long deptId);

    /**
     * 查询指定设备的维修次数
     * @param equId 设备ID
     * @return 维修次数
     */
    Long queryEquipMaintenanceCnt(String equId);

    /**
     * 报修单退回
     * @param saveReqVO 派单信息
     */
    void rejectRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO);

    /**
     * 挂单
     * @param saveReqVO 派单信息
     */
    void mountOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO);

    /**
     * 就近接单
     * @param problem
     * @param lng
     * @param lat
     * @return
     */
    PageResult<TrafficOpsMaintenanceNearbyRespVO> getNearbyOrdersPage(Page<?> page, String problem, String lng, String lat);

    /**
     * 查询维保记录
     * @param maintainId 维保记录ID
     * @return 维保记录
     */
    List<TrafficOpsMaintainFlowRecordRespVO> queryMaintainFlowRecord(String maintainId);

    /**
     * 查询此IP已下的所有未完成的维保单
     * @param equId
     * @param ipAddr
     */
    String queryOrderByIp(String equId,String ipAddr,String type);

    /**
     * 根据工单ID确认保修结果
     * @param saveReqVO 检测参数
     */
    String confirmRepairResult(PublicEquipOnOffCheckResultSaveReqVO saveReqVO);

    /**
     * 工单暂时无法修复
     * @param saveReqVO 保存参数
     * @return 主键
     */
    String saveOpsMaintenanceUnfixableInfo(TrafficOpsMaintenanceUnfixableInfoExtSaveReqVO saveReqVO);

    /**
     * 工单暂时无法修复
     * @param id 主键
     * @return 记录信息
     */
    TrafficOpsMaintenanceUnfixableInfoExtRespVO queryOpsMaintenanceUnfixableInfo(String id);

    /**
     * 维修员关单
     * @param saveReqVO 表单信息
     */
    void closeRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO);
}