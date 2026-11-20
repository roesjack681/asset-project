package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserRespVO;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceRecordMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.handler.ExcelCellByMultiHandler;
import com.holdtime.ops.module.handler.ExcelStyleHandler;
import com.holdtime.ops.module.model.dto.ElectricFaultImportExcelDTO;
import com.holdtime.ops.module.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;


import com.holdtime.ops.module.model.vo.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 设备维保信息")
@RestController
@RequestMapping("/traffic/ops-maintenance")
@Validated
public class TrafficOpsMaintenanceController {

//    @Resource
//    private TrafficOpsMaintenanceService opsMaintenanceService;
    @Resource
    private TrafficOpsMaintenanceExtService trafficOpsMaintenanceBizService;
//    @Resource
//    private TrafficOpsRegisterExtService trafficOpsRegisterExtService;
//    @Resource
//    private TrafficOpsMaintenanceRecordService trafficOpsMaintenanceRecordService;
    @Resource
    private TrafficEquipFaultImportExtService equipFaultImportExtService;
    @Resource
    private TrafficOpsMaintenanceMapper opsMaintenanceMapper;
    @Resource
    private TrafficOpsMaintenanceRecordMapper opsMaintenanceRecordMapper;
    @Resource
    private TrafficOpsElectricPoliceMapper trafficOpsElectricPoliceMapper;
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;

    @PostMapping("/create")
    @Operation(summary = "创建设备维保信息")
    public CommonResult<String> createOpsMaintenance(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO createReqVO) {
        return success(trafficOpsMaintenanceBizService.saveOpsMaintenance(createReqVO, OpsConstants.STRING_ONE));
    }

    @PostMapping("/createAll")
    @Operation(summary = "创建设备维保信息及维修全过程")
    public CommonResult<String> createOpsMaintenanceAll(@Valid @RequestBody TrafficOpsMaintenanceSaveAllReqVO createReqVO) {
        return success(trafficOpsMaintenanceBizService.saveOpsMaintenance(createReqVO));
    }


//    @PutMapping("/update")
//    @Operation(summary = "更新设备维保信息")
//    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance:update')")
//    public CommonResult<Boolean> updateOpsMaintenance(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO updateReqVO) {
//        opsMaintenanceService.updateOpsMaintenance(updateReqVO);
//        return success(true);
//    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备维保信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsMaintenance(@RequestParam("id") String id) {
        trafficOpsMaintenanceBizService.deleteOpsMaintenance(id);
        return success(true);
    }

//    @GetMapping("/get")
//    @Operation(summary = "获得设备维保信息")
//    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance:query')")
//    public CommonResult<TrafficOpsMaintenanceRespVO> getOpsMaintenance(@RequestParam("id") String id) {
//        TrafficOpsMaintenanceDO opsMaintenance = opsMaintenanceService.getOpsMaintenance(id);
//        return success(BeanUtils.toBean(opsMaintenance, TrafficOpsMaintenanceRespVO.class));
//    }

    @GetMapping("/page")
    @Operation(summary = "获得设备维保信息分页")
    public CommonResult<PageResult<TrafficOpsMaintenanceRespVO>> getOpsMaintenancePage(@RequestParam(value = "isPagination", required = false, defaultValue = "1") String isPagination, @Valid TrafficOpsMaintenancePageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsMaintenanceDO>()
                .eqIfPresent(TrafficOpsMaintenanceDO::getAffiliatedUnit, pageReqVO.getAffiliatedUnit())
                .eqIfPresent(TrafficOpsMaintenanceDO::getEquType, pageReqVO.getEquType())
                .eqIfPresent(TrafficOpsMaintenanceDO::getEquId, pageReqVO.getEquId())
                .likeIfPresent(TrafficOpsMaintenanceDO::getEquAddr, pageReqVO.getEquAddr())
                .eqIfPresent(TrafficOpsMaintenanceDO::getEquLng, pageReqVO.getEquLng())
                .eqIfPresent(TrafficOpsMaintenanceDO::getEquLat, pageReqVO.getEquLat())
                .likeIfPresent(TrafficOpsMaintenanceDO::getProblem, pageReqVO.getProblem())
                .likeIfPresent(TrafficOpsMaintenanceDO::getFaultType, pageReqVO.getFaultType())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getFaultDate, pageReqVO.getFaultDate())
                .likeIfPresent(TrafficOpsMaintenanceDO::getReportPersonnel, pageReqVO.getReportPersonnel())
                .likeIfPresent(TrafficOpsMaintenanceDO::getMaintenancePersonnel, pageReqVO.getMaintenancePersonnel())
                .eqIfPresent(TrafficOpsMaintenanceDO::getReportUserId, pageReqVO.getReportUserId())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getReportTime, pageReqVO.getReportTime())
                .eqIfPresent(TrafficOpsMaintenanceDO::getReportDeptId, pageReqVO.getReportDeptId())
                .eqIfPresent(TrafficOpsMaintenanceDO::getReportDept, pageReqVO.getReportDept())
                .eqIfPresent(TrafficOpsMaintenanceDO::getDispatchUserId, pageReqVO.getDispatchUserId())
                .likeIfPresent(TrafficOpsMaintenanceDO::getDispatch, pageReqVO.getDispatch())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getDispatchTime, pageReqVO.getDispatchTime())
//                .eqIfPresent(TrafficOpsMaintenanceDO::getSuggestion, pageReqVO.getSuggestion())
                .eqIfPresent(TrafficOpsMaintenanceDO::getStatus, pageReqVO.getStatus())
                .likeIfPresent(TrafficOpsMaintenanceDO::getRemark, pageReqVO.getRemark())
                .likeIfPresent(TrafficOpsMaintenanceDO::getConfirmor, pageReqVO.getConfirmor())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getConfirmDate, pageReqVO.getConfirmDate())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getFinishTime, pageReqVO.getFinishTime())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getDeletedTime, pageReqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsMaintenanceDO::getSource, pageReqVO.getSource())
                .likeIfPresent(TrafficOpsMaintenanceDO::getRejectPersonnel, pageReqVO.getRejectPersonnel())
                .likeIfPresent(TrafficOpsMaintenanceDO::getIpAddr, pageReqVO.getIpAddr())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getRejectTime, pageReqVO.getRejectTime())
                .eqIfPresent(TrafficOpsMaintenanceDO::getProjectedRepair, pageReqVO.getProjectedRepair())
                .eqIfPresent(TrafficOpsMaintenanceDO::getActualRepair, pageReqVO.getActualRepair())
                .orderByDesc(TrafficOpsMaintenanceDO::getCreateTime);

        if (!OpsConstants.STRING_ONE.equals(isPagination)) {
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            StringBuilder childSql = new StringBuilder();
            for (String equLabel : pageReqVO.getEquLableList()) {
                childSql.append(" and e.equ_lable like '%").append(equLabel).append("%'");
            }

            if (OpsConstants.STRING_ONE.equals(pageReqVO.getAffiliatedUnit())) {
                lambdaQueryWrapperX.exists("SELECT 1 from traffic_ops_electric_police e where traffic_ops_maintenance.equ_id = e.id" + childSql);
            } else {
                lambdaQueryWrapperX.exists("SELECT 1 from police_ops_electric_police e where traffic_ops_maintenance.equ_id = e.id" + childSql);
            }

        }

        if (pageReqVO.getRepairCnt() != null) {
            if (OpsConstants.STRING_FIVE.equals(pageReqVO.getRepairCntOperator())) {
                lambdaQueryWrapperX.eq(TrafficOpsMaintenanceDO::getRepairCnt, pageReqVO.getRepairCnt());
            } else if (OpsConstants.STRING_FOUR.equals(pageReqVO.getRepairCntOperator())) {
                lambdaQueryWrapperX.le(TrafficOpsMaintenanceDO::getRepairCnt, pageReqVO.getRepairCnt());
            } else if (OpsConstants.STRING_THREE.equals(pageReqVO.getRepairCntOperator())) {
                lambdaQueryWrapperX.lt(TrafficOpsMaintenanceDO::getRepairCnt, pageReqVO.getRepairCnt());
            } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getRepairCntOperator())) {
                lambdaQueryWrapperX.ge(TrafficOpsMaintenanceDO::getRepairCnt, pageReqVO.getRepairCnt());
            } else {
                lambdaQueryWrapperX.gt(TrafficOpsMaintenanceDO::getRepairCnt, pageReqVO.getRepairCnt());
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getSuggestion())) {
            lambdaQueryWrapperX.exists("SELECT 1 from traffic_ops_maintain_progress p where p.maintenance_id = traffic_ops_maintenance.id and p.maintenance like '%" + pageReqVO.getSuggestion() + "%'");
        }

        PageResult<TrafficOpsMaintenanceRespVO> pageResult = BeanUtils.toBean( opsMaintenanceMapper.selectPage(pageReqVO, lambdaQueryWrapperX), TrafficOpsMaintenanceRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach((item) -> {
            // 查询电警信息
            item.setEquLabel(queryEquLabel(item.getEquType(), item.getAffiliatedUnit(), item.getEquId()));
        });
        return success(pageResult);
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出设备维保信息 Excel")
    public void exportOpsMaintenanceExcel(@Valid TrafficOpsMaintenanceRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);

        LambdaQueryWrapperX<TrafficOpsMaintenanceRecordDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsMaintenanceRecordDO>()
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getAffiliatedUnit, pageReqVO.getAffiliatedUnit())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getEquType, pageReqVO.getEquType())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getEquId, pageReqVO.getEquId())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getEquAddr, pageReqVO.getEquAddr())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getEquLng, pageReqVO.getEquLng())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getEquLat, pageReqVO.getEquLat())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getProblem, pageReqVO.getProblem())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getFaultType, pageReqVO.getFaultType())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getFaultDate, pageReqVO.getFaultDate())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getReportPersonnel, pageReqVO.getReportPersonnel())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getMaintenancePersonnel, pageReqVO.getMaintenancePersonnel())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getReportUserId, pageReqVO.getReportUserId())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getReportTime, pageReqVO.getReportTime())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getReportDeptId, pageReqVO.getReportDeptId())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getReportDept, pageReqVO.getReportDept())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getDispatchUserId, pageReqVO.getDispatchUserId())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getDispatch, pageReqVO.getDispatch())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getDispatchTime, pageReqVO.getDispatchTime())
//                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getSuggestion, pageReqVO.getSuggestion())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getStatus, pageReqVO.getStatus())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getRemark, pageReqVO.getRemark())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getConfirmor, pageReqVO.getConfirmor())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getConfirmDate, pageReqVO.getConfirmDate())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getFinishTime, pageReqVO.getFinishTime())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getDeletedTime, pageReqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getSource, pageReqVO.getSource())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getProjectedRepair, pageReqVO.getProjectedRepair())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getActualRepair, pageReqVO.getActualRepair())
                .orderByDesc(TrafficOpsMaintenanceRecordDO::getId);

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            StringBuilder childSql = new StringBuilder();
            for (String equLabel : pageReqVO.getEquLableList()) {
                childSql.append(" and e.equ_lable like '%").append(equLabel).append("%'");
            }

            if (OpsConstants.STRING_ONE.equals(pageReqVO.getAffiliatedUnit())) {
                lambdaQueryWrapperX.exists("SELECT 1 from traffic_ops_electric_police e where v_ops_maintenance_record.equ_id = e.id" + childSql);
            } else {
                lambdaQueryWrapperX.exists("SELECT 1 from police_ops_electric_police e where v_ops_maintenance_record.equ_id = e.id" + childSql);
            }

        }

        if (StringUtils.isNotBlank(pageReqVO.getSuggestion())) {
            lambdaQueryWrapperX.exists("SELECT 1 from traffic_ops_maintain_progress p where p.maintenance_id = v_ops_maintenance_record.id and p.maintenance like '%" + pageReqVO.getSuggestion() + "%'");
        }

        List<TrafficOpsMaintenanceRespVO> list = BeanUtils.toBean(opsMaintenanceRecordMapper.selectPage(pageReqVO, lambdaQueryWrapperX).getList(), TrafficOpsMaintenanceRespVO.class);
        Optional.ofNullable(list).orElse(new ArrayList<>()).forEach((item) -> {
            // 查询电警信息
            item.setEquLabel(queryEquLabel(item.getEquType(), item.getAffiliatedUnit(), item.getEquId()));
        });
        // 导出 Excel
//        ExcelUtils.write(response, "设备维保信息.xls", "数据", TrafficOpsMaintenanceRespVO.class,
//                        BeanUtils.toBean(list, TrafficOpsMaintenanceRespVO.class));

//        int[] cols = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        EasyExcel.write(response.getOutputStream(), TrafficOpsMaintenanceRespVO.class).autoCloseStream(false)
                .registerWriteHandler(new ExcelCellByMultiHandler(0))
                .registerWriteHandler(new HorizontalCellStyleStrategy(ExcelStyleHandler.getHeadStyle(), ExcelStyleHandler.getContentStyle()))
                .sheet("数据").doWrite(BeanUtils.toBean(list, TrafficOpsMaintenanceRespVO.class));

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("设备维保信息.xls", StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    }

    private String queryEquLabel(String equType, String affiliatedUnit, String equId)  {
        String label = "";
        if (OpsConstants.EQU_TYPE_DJ.equals(equType)) {
            if (OpsConstants.STRING_ONE.equals(affiliatedUnit)) {
                TrafficOpsElectricPoliceDO opsElectricPoliceDO = trafficOpsElectricPoliceMapper.selectById(equId);
                if (opsElectricPoliceDO != null) {
                    label = opsElectricPoliceDO.getEquLable();
                }
            } else {
                PoliceOpsElectricPoliceDO opsElectricPoliceDO = policeOpsElectricPoliceMapper.selectById(equId);
                if (opsElectricPoliceDO != null) {
                    label = opsElectricPoliceDO.getEquLable();
                }
            }
        }
        return label;

    }

    /**
     * 报修记录详情
     * @param id 报修记录ID
     * @return 报修记录详情
     */
    @GetMapping("/get")
    @Operation(summary = "工单详情")
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
     * 派单
     * @param saveReqVO 维保记录
     * @return 报修ID
     */
    @PostMapping("/dispatchRepairOrderPc")
    @Operation(summary = "派单")
    public CommonResult<?> dispatchRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.dispatchRepairOrderPc(saveReqVO);
        return success(true);
    }

    /**
     * 批量派单
     * @param saveReqVO 维保记录
     * @return 报修ID
     */
    @PostMapping("/batchDispatchRepairOrder")
    @Operation(summary = "批量派单")
    public CommonResult<?> dispatchRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceBatchDispatchSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.batchDispatchRepairOrder(saveReqVO);
        return success(true);
    }

    /**
     * 接单:派单时候自动接单
     * @param saveReqVO 表单信息
     * @return 接单是否成功
     */
    @PostMapping("/acceptRepairOrderPc")
    @Operation(summary = "接单")
    public CommonResult<?> acceptRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.acceptRepairOrder(saveReqVO);
        return success(true);
    }

    /**
     * 取消报修单
     * @param saveReqVO 维保记录
     * @return 报修ID
     */
    @PostMapping("/cancelRepairOrderPc")
    @Operation(summary = "取消报修单")
    public CommonResult<?> cancelRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.cancelRepairOrder(saveReqVO);
        return success(true);
    }

    /**
     * 保存维修进度记录
     * @param saveReqVO 维保记录
     * @return 报修ID
     */
    @PostMapping("/saveMaintenanceProgressPc")
    @Operation(summary = "保存维修进度信息")
    public CommonResult<?> saveMaintenanceProgress(@Valid @RequestBody TrafficOpsMaintainProgressSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.saveMaintenanceProgress(saveReqVO);
        return success(true);
    }

    /**
     * 确认报修
     * @param confirmSaveReqVO 确认信息
     */
    @PostMapping("/confirmRepairOrder")
    @Operation(summary = "确认报修")
    public CommonResult<?> confirmRepairOrder(@Valid @RequestBody TrafficOpsMaintenanceConfirmSaveReqVO confirmSaveReqVO) {
        trafficOpsMaintenanceBizService.confirmRepairOrder(confirmSaveReqVO, WebFrameworkUtils.getLoginUserId());
        return success(true);
    }


    @GetMapping("/page-dp")
    @Operation(summary = "大屏待维修设备")
    @Parameter(name = "status", description = "状态", example = "1024")
    public CommonResult<TrafficOpsMaintenanceRespDpVO> getOpsMaintenancePageDp(@RequestParam(value = "status", required = false) String status) {
        return success(trafficOpsMaintenanceBizService.getOpsMaintenanceForDp(status));
    }

    @PostMapping("/importMaintenanceInfo")
    @Operation(summary = "导入报修信息")
    public CommonResult<ImportRecordRespVO> importMaintenanceInfo(@RequestParam("file") MultipartFile file) {
        return success(equipFaultImportExtService.importEquipFaultInfo(file, ElectricFaultImportExcelDTO.class));
    }

    @GetMapping("/queryAllUserInfoList")
    @Operation(summary = "查询所有用户信息")
    public CommonResult<List<UserRespVO>> queryAllUserInfoList(@RequestParam(value = "deptId", required = false) Long deptId) {
        return success(trafficOpsMaintenanceBizService.queryAllUserInfoList(deptId));
    }

    /**
     * 退回报修单
     * @param saveReqVO 维保记录
     * @return 报修ID
     */
    @PostMapping("/rejectRepairOrderPc")
    @Operation(summary = "退回报修单")
    public CommonResult<?> rejectRepairOrderPc(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        trafficOpsMaintenanceBizService.rejectRepairOrder(saveReqVO);
        return success(true);
    }

    /**
     * 挂单
     * @param saveReqVO 维保记录
     */
    @PostMapping("/mountOrder")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> mountOrder(@Valid @RequestBody TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        // 校验存在
        trafficOpsMaintenanceBizService.mountOrder(saveReqVO);
        return success(true);
    }

    @GetMapping("/queryOrderByIp")
    @Operation(summary = "根据IP查询是否有未完成的工单")
    public CommonResult<String> queryOrderByIp(@RequestParam("equId") String equId,@RequestParam("ipAddr") String ipAddr,@RequestParam("type") String type) {
        return success(trafficOpsMaintenanceBizService.queryOrderByIp(equId, ipAddr,type));
    }

    @PostMapping("/queryOpsMaintenanceUnfixableInfo")
    @Operation(summary = "查询设备维保无法修复信息")
    public CommonResult<TrafficOpsMaintenanceUnfixableInfoExtRespVO> queryOpsMaintenanceUnfixableInfo(@RequestParam("id") String id) {
        return success(trafficOpsMaintenanceBizService.queryOpsMaintenanceUnfixableInfo(id));
    }

}