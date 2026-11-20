package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.mapper.TrafficOpsPoliceEquipmentMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.dto.ExcelImportPoliceEquipDTO;
import com.holdtime.ops.module.service.ExcelImportExtService;
import com.holdtime.ops.module.service.TrafficOpsPoliceEquipmentExtService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsPoliceEquipmentDO;
import com.holdtime.ops.module.service.TrafficOpsPoliceEquipmentService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 警用装备资产信息")
@RestController
@RequestMapping("/traffic/ops-police-equipment")
@Validated
public class TrafficOpsPoliceEquipmentController {

    @Resource
    private TrafficOpsPoliceEquipmentService opsPoliceEquipmentService;
    @Resource
    private TrafficOpsPoliceEquipmentExtService opsPoliceEquipmentExtService;
    @Resource
    private TrafficOpsPoliceEquipmentMapper opsPoliceEquipmentMapper;
    @Resource
    private ExcelImportExtService excelImportExtService;

    @PostMapping("/create")
    @Operation(summary = "创建警用装备资产信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-equipment:create')")
    public CommonResult<String> createOpsPoliceEquipment(@Valid @RequestBody TrafficOpsPoliceEquipmentSaveReqVO createReqVO) {
        return success(opsPoliceEquipmentExtService.saveOpsPoliceEquipment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新警用装备资产信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-equipment:update')")
    public CommonResult<Boolean> updateOpsPoliceEquipment(@Valid @RequestBody TrafficOpsPoliceEquipmentSaveReqVO updateReqVO) {
        opsPoliceEquipmentExtService.saveOpsPoliceEquipment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除警用装备资产信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-equipment:delete')")
    public CommonResult<Boolean> deleteOpsPoliceEquipment(@RequestParam("id") String id) {
        opsPoliceEquipmentService.deleteOpsPoliceEquipment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得警用装备资产信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-equipment:query')")
    public CommonResult<TrafficOpsPoliceEquipmentRespVO> getOpsPoliceEquipment(@RequestParam("id") String id) {
        TrafficOpsPoliceEquipmentDO opsPoliceEquipment = opsPoliceEquipmentService.getOpsPoliceEquipment(id);
        return success(BeanUtils.toBean(opsPoliceEquipment, TrafficOpsPoliceEquipmentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得警用装备资产信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-equipment:query')")
    public CommonResult<PageResult<TrafficOpsPoliceEquipmentRespVO>> getOpsPoliceEquipmentPage(@Valid TrafficOpsPoliceEquipmentPageReqVO pageReqVO) {

        PageResult<TrafficOpsPoliceEquipmentDO> pageResult = opsPoliceEquipmentExtService.getOpsPoliceEquipmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsPoliceEquipmentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出警用装备资产信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-equipment:export')")
    public void exportOpsPoliceEquipmentExcel(@Valid TrafficOpsPoliceEquipmentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsPoliceEquipmentDO> list = opsPoliceEquipmentService.getOpsPoliceEquipmentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "警用装备资产信息.xls", "数据", TrafficOpsPoliceEquipmentRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsPoliceEquipmentRespVO.class));
    }

    /**
     * 获取已分配的警用设备
     * @return 维修人员信息
     */
    @GetMapping("/queryIssuedPoliceEquipment")
    @Operation(summary = "获取已分配的警用设备")
    public CommonResult<List<TrafficOpsPoliceEquipmentRespVO>> queryIssuedPoliceEquipment(@RequestParam(value = "deptId", required = false) String deptId,
                                                                                          @RequestParam(value = "keyword", required = false) String keyword) {
        return success(opsPoliceEquipmentExtService.queryIssuedPoliceEquipment(deptId, keyword));
    }


    @GetMapping("/unallocatedPage")
    @Operation(summary = "获得未分配的警用装备")
    public CommonResult<List<TrafficOpsPoliceEquipmentRespVO>> getUnallocatedOpsPoliceEquipmentPage(@Valid TrafficOpsPoliceEquipmentPageReqVO reqVO) {
        LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO>()
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getEncryptionCard, reqVO.getEncryptionCard())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getPsn, reqVO.getPsn())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getBrand, reqVO.getBrand())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getModel, reqVO.getModel())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getBattery, reqVO.getBattery())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecordBh, reqVO.getRecordBh())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecorder, reqVO.getRecorder())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecordDeptBh, reqVO.getRecordDeptBh())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecordDept, reqVO.getRecordDept())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getUseDeptBh, reqVO.getUseDeptBh())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getUseDept, reqVO.getUseDept())
//                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getUserBh, reqVO.getUserBh())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getUserName, reqVO.getUserName())
                .betweenIfPresent(TrafficOpsPoliceEquipmentDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsPoliceEquipmentDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsPoliceEquipmentDO::getId);
        opsDOLambdaQueryWrapperX.isNull(TrafficOpsPoliceEquipmentDO::getUserBh);
        List<TrafficOpsPoliceEquipmentDO> pageResult = opsPoliceEquipmentMapper.selectList(opsDOLambdaQueryWrapperX);
        return success(BeanUtils.toBean(pageResult, TrafficOpsPoliceEquipmentRespVO.class));
    }

    @GetMapping("/queryPoliceEquipment")
    @Operation(summary = "查询警用设备")
    public CommonResult<List<TrafficOpsPoliceEquipmentOptionRespVO>> queryPoliceEquipment(@RequestParam(value = "queryType", required = false) String queryType,
                                                                                          @RequestParam(value = "issueBh", required = false) String issueBh,
                                                                                          @Valid TrafficOpsPoliceEquipmentPageReqVO reqVO) {
        LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO>()
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getEquType, reqVO.getEquType())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getPsn, reqVO.getPsn())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getUseDeptBh, reqVO.getUseDeptBh())
                .orderByDesc(TrafficOpsPoliceEquipmentDO::getId);
        if (OpsConstants.STRING_ONE.equals(queryType)) { // 未分配
            opsDOLambdaQueryWrapperX.isNull(TrafficOpsPoliceEquipmentDO::getUserBh);
        } else if (OpsConstants.STRING_TWO.equals(queryType)) { // 已分配
            opsDOLambdaQueryWrapperX.isNotNull(TrafficOpsPoliceEquipmentDO::getUserBh);
        }

        if (StringUtils.isNotBlank(issueBh)) {
            opsDOLambdaQueryWrapperX.exists("select 1 from traffic_ops_issue_detail d where traffic_ops_police_equipment.id = d.equ_id and d.issue_bh = '" + issueBh + "'");
        }
        List<TrafficOpsPoliceEquipmentDO> pageResult = opsPoliceEquipmentMapper.selectList(opsDOLambdaQueryWrapperX);
        return success(BeanUtils.toBean(pageResult, TrafficOpsPoliceEquipmentOptionRespVO.class));
    }

    @PostMapping("/importPoliceEquipInfo")
    @Operation(summary = "导入警用设备信息")
    public CommonResult<ImportRecordRespVO> importPoliceEquipInfo(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("警用设备", file, ExcelImportPoliceEquipDTO.class));
    }

    /**
     * 获取已分配的警用设备
     * @return 维修人员信息
     */
    @GetMapping("/autocompletePoliceEquipInfoList")
    @Operation(summary = "获取已分配的警用设备")
    public CommonResult<List<String>> autocompletePoliceEquipInfoList(@RequestParam(value = "keyword", required = false) String keyword,
                                                                      @RequestParam(value = "keyType", required = false) String keyType) {
        return success(opsPoliceEquipmentExtService.autocompletePoliceEquipInfoList(keyword, keyType));
    }

    @PostMapping("/recycle")
    @Operation(summary = "回收警用装备资产信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-equipment:recycle')")
    public CommonResult<String> recycleOpsPoliceEquipment(@RequestParam("id") String id) {
        return success(opsPoliceEquipmentExtService.recycleOpsPoliceEquipment(id));
    }

}