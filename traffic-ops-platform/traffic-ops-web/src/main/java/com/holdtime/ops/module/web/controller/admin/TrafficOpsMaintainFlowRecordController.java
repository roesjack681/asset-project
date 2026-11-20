package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.TrafficOpsMaintainFlowRecordService;
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
import com.holdtime.ops.module.entity.TrafficOpsMaintainFlowRecordDO;

@Tag(name = "管理后台 - 设备维保全流程")
@RestController
@RequestMapping("/traffic/ops-maintain-flow-record")
@Validated
public class TrafficOpsMaintainFlowRecordController {

    @Resource
    private TrafficOpsMaintainFlowRecordService opsMaintainFlowRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建设备维保全流程")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintain-flow-record:create')")
    public CommonResult<String> createOpsMaintainFlowRecord(@Valid @RequestBody TrafficOpsMaintainFlowRecordSaveReqVO createReqVO) {
        return success(opsMaintainFlowRecordService.createOpsMaintainFlowRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备维保全流程")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintain-flow-record:update')")
    public CommonResult<Boolean> updateOpsMaintainFlowRecord(@Valid @RequestBody TrafficOpsMaintainFlowRecordSaveReqVO updateReqVO) {
        opsMaintainFlowRecordService.updateOpsMaintainFlowRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备维保全流程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintain-flow-record:delete')")
    public CommonResult<Boolean> deleteOpsMaintainFlowRecord(@RequestParam("id") String id) {
        opsMaintainFlowRecordService.deleteOpsMaintainFlowRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备维保全流程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintain-flow-record:query')")
    public CommonResult<TrafficOpsMaintainFlowRecordRespVO> getOpsMaintainFlowRecord(@RequestParam("id") String id) {
        TrafficOpsMaintainFlowRecordDO opsMaintainFlowRecord = opsMaintainFlowRecordService.getOpsMaintainFlowRecord(id);
        return success(BeanUtils.toBean(opsMaintainFlowRecord, TrafficOpsMaintainFlowRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备维保全流程分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintain-flow-record:query')")
    public CommonResult<PageResult<TrafficOpsMaintainFlowRecordRespVO>> getOpsMaintainFlowRecordPage(@Valid TrafficOpsMaintainFlowRecordPageReqVO pageReqVO) {
        PageResult<TrafficOpsMaintainFlowRecordDO> pageResult = opsMaintainFlowRecordService.getOpsMaintainFlowRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsMaintainFlowRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备维保全流程 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintain-flow-record:export')")
    public void exportOpsMaintainFlowRecordExcel(@Valid TrafficOpsMaintainFlowRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsMaintainFlowRecordDO> list = opsMaintainFlowRecordService.getOpsMaintainFlowRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备维保全流程.xls", "数据", TrafficOpsMaintainFlowRecordRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsMaintainFlowRecordRespVO.class));
    }

}