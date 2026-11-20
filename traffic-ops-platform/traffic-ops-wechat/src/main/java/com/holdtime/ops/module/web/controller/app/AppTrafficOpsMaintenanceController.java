package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.PublicEquipOnOffCheckResultDO;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 设备维保")
@RestController
@RequestMapping("/traffic/ops-maintenance")
@Validated
public class AppTrafficOpsMaintenanceController {

    @Resource
    private TrafficOpsMaintenanceExtService trafficOpsMaintenanceBizService;
    @Resource
    private TrafficOpsRegisterExtService trafficOpsRegisterExtService;
    @Resource
    private TrafficOpsIntersectionExtService opsIntersectionExtService;
    @Resource
    private TrafficOpsElectricPoliceExtService opsElectricPoliceExtService;
    @Resource
    private PublicEquipOnOffCheckResultService publicEquipOnOffCheckResultService;

    /**
     * 报修:常规报修；语音报修；视频报修
     * @param createReqVO 报修内容
     * @return 报修ID
     */
    @PostMapping("/callRepairOrder")
    @Operation(summary = "上报报修记录")
    public CommonResult<String> callRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO createReqVO) {
        return success(trafficOpsMaintenanceBizService.saveOpsMaintenance(createReqVO, OpsConstants.STRING_ZERO));
    }

    /**
     * 待派单维保记录
     * @param pageReqVO 查询条件
     * @return 我上报维修记录信息
     */
    @PostMapping("/getPendingOrder/{userId}")
    @Operation(summary = "待派单记录")
    public CommonResult<PageResult<TrafficOpsMaintenanceRespVO>> getPendingOrder(@PathVariable String userId, @Valid @RequestBody TrafficOpsMaintenancePageReqVO pageReqVO) {
        pageReqVO.setDispatchUserId(userId);
        return success(trafficOpsMaintenanceBizService.getOpsMaintenancePage(pageReqVO, OpsConstants.QUERY_TYPE_ONE));
    }

    /**
     * 查询所有维修人员信息
     * @return 维修人员信息
     */
    @GetMapping("/getAllOpsRegister")
    @Operation(summary = "查询所有的维修人员")
    @Parameter(name = "dispatchUserId", description = "派单人员用户编号", required = true, example = "1024")
    public CommonResult<List<AppTrafficOpsRegisterRespVO>> getAllOpsRegister(@RequestParam(value = "dispatchUserId") Long dispatchUserId,
                                                                             @RequestParam(value = "name", required = false) String name) {
        return success(trafficOpsRegisterExtService.getAllOpsRegister(dispatchUserId, name));
    }

    /**
     * 派单
     * @param saveReqVO 维保记录
     * @return 报修ID
     */
    @PostMapping("/dispatchRepairOrder")
    @Operation(summary = "派单")
    public CommonResult<?> dispatchRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.dispatchRepairOrder(saveReqVO);
        return success(true);
    }

    /**
     * 取消报修单
     * @param saveReqVO 维保记录
     * @return 报修ID
     */
    @PostMapping("/cancelRepairOrder")
    @Operation(summary = "取消报修单")
    public CommonResult<?> cancelRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.cancelRepairOrder(saveReqVO);
        return success(true);
    }

    /**
     * 所有工单
     * @param userId 用户ID
     * @param pageReqVO 查询条件
     * @return 我上报维修记录信息
     */
    @PostMapping("/getAllRepairPage")
    @Operation(summary = "所有工单")
    public CommonResult<PageResult<TrafficOpsMaintenanceRespVO>> getMyAssignedOrder(@RequestParam(value = "userId") String userId, @Valid @RequestBody TrafficOpsMaintenancePageReqVO pageReqVO) {
        pageReqVO.setReportUserId(userId);
        return success(trafficOpsMaintenanceBizService.getOpsMaintenancePage(pageReqVO, OpsConstants.QUERY_TYPE_THREE));
    }

    /**
     * 我上报的报修记录分页记录
     * @param pageReqVO 查询条件
     * @return 我上报维修记录信息
     */
    @PostMapping("/myCallRepairPage/{userId}")
    @Operation(summary = "我的上报记录")
    public CommonResult<PageResult<TrafficOpsMaintenanceRespVO>> myCallRepairPage(@PathVariable String userId, @Valid @RequestBody TrafficOpsMaintenancePageReqVO pageReqVO) {
        pageReqVO.setReportUserId(userId);
        return success(trafficOpsMaintenanceBizService.getOpsMaintenancePage(pageReqVO, null));
    }

    /**
     * 接单
     * @param saveReqVO 表单信息
     * @return 接单是否成功
     */
    @PostMapping("/acceptRepairOrder")
    @Operation(summary = "接单")
    public CommonResult<?> acceptRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.acceptRepairOrder(saveReqVO);
        return success(true);
    }

    /**
     * 我的工单记录分页记录
     * @param pageReqVO 查询条件
     * @return 我的工单信息
     */
    @PostMapping("/myWorkOrderPage/{userId}")
    @Operation(summary = "我的工单")
    public CommonResult<PageResult<TrafficOpsMaintenanceRespVO>> myWorkOrder(@PathVariable String userId, @Valid @RequestBody TrafficOpsMaintenancePageReqVO pageReqVO) {
        pageReqVO.setMaintenanceUserId(userId);
        return success(trafficOpsMaintenanceBizService.getOpsMaintenancePage(pageReqVO, null));
    }

    /**
     * 报修记录详情
     * @param id 报修记录ID
     * @return 报修记录详情
     */
    @GetMapping("/myWorkOrderInfo")
    @Operation(summary = "我的工单详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsMaintenanceRespVO> getOpsMaintenance(@RequestParam("id") String id) {
        return success(trafficOpsMaintenanceBizService.getOpsMaintenance(id));
    }

    /**
     * 维修记录信息
     * @param maintenanceId 维保ID
     * @return 报修记录详情
     */
    @GetMapping("/maintenanceProgress/{maintenanceId}")
    @Operation(summary = "维修记录信息")
    @Parameter(name = "maintenanceId", description = "维保ID", required = true)
    public CommonResult<List<TrafficOpsMaintainProgressRespVO>> getOpsMaintenanceProgress(@PathVariable String maintenanceId) {
        return success(trafficOpsMaintenanceBizService.getOpsMaintenanceProgress(maintenanceId));
    }

    /**
     * 根据道路名称关联设备信息 20250326注释掉用于手机端关联设备的接口，统一改为取各自设备的page接口
     * @param pageReqVO
     * @return
     */
//    @GetMapping("/initOpsAssetPage")
//    @Operation(summary = "获得资产分页")
//    public CommonResult<PageResult<TrafficOpsSignalRespVO>> initOpsAssetPage(@Valid TrafficOpsGuardrailPageReqVO pageReqVO) {
//        List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
//        LambdaQueryWrapperX<TrafficOpsSignalDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
//        if (!CollectionUtils.isEmpty(roadNameList)) {
//            for (String roadName : roadNameList) {
//                opsDOLambdaQueryWrapperX.like(TrafficOpsSignalDO::getIntersectionName, roadName);
//            }
//        }
//
//        PageResult<TrafficOpsSignalDO> pageResult = trafficOpsSignalMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);
//        return success(BeanUtils.toBean(pageResult, TrafficOpsSignalRespVO.class));
//    }

    /**
     * 保存维修进度记录
     * @param saveReqVO 维保记录
     * @return 报修ID
     */
    @PostMapping("/saveMaintenanceProgress")
    @Operation(summary = "保存维修进度信息")
    public CommonResult<?> saveMaintenanceProgress(@Valid @RequestBody TrafficOpsMaintainProgressSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.saveMaintenanceProgress(saveReqVO);
        return success(true);
    }

    /**
     * 报修记录详情
     * @param id 报修记录ID
     * @return 报修记录详情
     */
    @GetMapping("/queryRepairOrderCnt")
    @Operation(summary = "查询报修记录数")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<Map<String, Long>> queryRepairOrderCnt(@RequestParam("id") Long id, @RequestParam(value = "affiliatedUnit", required = false, defaultValue = "1") String affiliatedUnit) {
        return success(trafficOpsMaintenanceBizService.queryRepairOrderCnt(id, affiliatedUnit));
    }

    /**
     * 驳回报修单
     * @param saveReqVO 维保记录
     * @return 报修ID
     */
    @PostMapping("/rejectRepairOrder")
    @Operation(summary = "驳回报修单")
    public CommonResult<?> saveMaintenanceProgress(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.rejectRepairOrder(saveReqVO);
        return success(true);
    }

    @GetMapping("/autocompleteOpsIntersection")
    @Operation(summary = "自动匹配交叉口")
    public CommonResult<List<TrafficOpsIntersectionRespVO>> autocompleteOpsIntersection(@RequestParam("intersectionName") String intersectionName) {
        return success(opsIntersectionExtService.autocompleteOpsIntersection(intersectionName));
    }

    @GetMapping("/autocompleteOpsElectricPolice")
    @Operation(summary = "自动匹配电警")
    public CommonResult<List<TrafficOpsElectricPoliceRespVO>> autocompleteOpsElectricPolice(@RequestParam("ipAddr") String ipAddr) {
        return success(opsElectricPoliceExtService.autocompleteOpsElectricPolice(ipAddr));
    }

    /**
     * 就近接单——所有待派的单子在公海
     * @param lng
     * @param lat
     * @return
     */
    @PostMapping("/getNearbyOrdersPage")
    @Operation(summary = "所有待派单记录-用于抢单模式")
    public CommonResult<PageResult<TrafficOpsMaintenanceNearbyRespVO>> getNearbyOrdersPage(@RequestBody TrafficOpsMaintenancePageReqVO pageReqVO,@RequestParam(value = "lng") String lng,@RequestParam(value = "lat") String lat) {
        Page<?> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        if(StringUtil.isEmpty(pageReqVO.getProblem())){
            pageReqVO.setProblem("");
        }
        return success(trafficOpsMaintenanceBizService.getNearbyOrdersPage(page,pageReqVO.getProblem(),lng,lat));
    }

    @PostMapping("/createAllForWx")
    @Operation(summary = "补录设备维保信息及维修全过程")
    public CommonResult<String> createOpsMaintenanceAll(@Valid @RequestBody TrafficOpsMaintenanceSaveAllReqVO createReqVO) {
        return success(trafficOpsMaintenanceBizService.saveOpsMaintenance(createReqVO));
    }

    /**
     * 手机端验证维修结果
     * @param saveReqVO 验证参数
     * @return
     */
    @PostMapping("/confirmRepairResult")
    @Operation(summary = "手机端验证维修结果")
    public CommonResult<String> confirmRepairResult(@RequestBody PublicEquipOnOffCheckResultSaveReqVO saveReqVO) {
        return success(trafficOpsMaintenanceBizService.confirmRepairResult(saveReqVO));
    }

    @GetMapping("/queryRepairResult")
    @Operation(summary = "查询检测结果")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PublicEquipOnOffCheckResultRespVO> queryRepairResult(@RequestParam("id") String id) {
        PublicEquipOnOffCheckResultDO equipOnOffCheckResult = publicEquipOnOffCheckResultService.getEquipOnOffCheckResult(id);
        if (equipOnOffCheckResult == null) {
            return error(OpsErrorCodeConstants.EQUIP_ON_OFF_CHECK_RESULT_NOT_EXISTS);
        }
        if (StringUtil.isEmpty(equipOnOffCheckResult.getPingStatus()) && StringUtil.isEmpty(equipOnOffCheckResult.getVideoStatus())) {
            return success(new PublicEquipOnOffCheckResultRespVO());
        }
        return success(BeanUtils.toBean(equipOnOffCheckResult, PublicEquipOnOffCheckResultRespVO.class));
    }

    @PostMapping("/saveOpsMaintenanceUnfixableInfo")
    @Operation(summary = "保存设备维保无法修复信息")
    public CommonResult<String> saveOpsMaintenanceUnfixableInfo(@Valid @RequestBody TrafficOpsMaintenanceUnfixableInfoExtSaveReqVO saveReqVO) {
        return success(trafficOpsMaintenanceBizService.saveOpsMaintenanceUnfixableInfo(saveReqVO));
    }

    @GetMapping("/queryOpsMaintenanceUnfixableInfo")
    @Operation(summary = "查询设备维保无法修复信息")
    public CommonResult<TrafficOpsMaintenanceUnfixableInfoExtRespVO> queryOpsMaintenanceUnfixableInfo(@RequestParam("id") String id) {
        return success(trafficOpsMaintenanceBizService.queryOpsMaintenanceUnfixableInfo(id));
    }

    /**
     * 检测后正常关单
     * @param saveReqVO 表单信息
     * @return 接单是否成功
     */
    @PostMapping("/closeRepairOrder")
    @Operation(summary = "关单")
    public CommonResult<?> closeRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.closeRepairOrder(saveReqVO);
        return success(true);
    }

}