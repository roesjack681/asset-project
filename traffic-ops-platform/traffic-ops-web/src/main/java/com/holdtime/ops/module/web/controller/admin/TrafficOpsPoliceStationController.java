package com.holdtime.ops.module.web.controller.admin;

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
import com.holdtime.ops.module.entity.TrafficOpsPoliceStationDO;
import com.holdtime.ops.module.service.TrafficOpsPoliceStationService;

@Tag(name = "管理后台 - 警用装备采集站")
@RestController
@RequestMapping("/traffic/ops-police-station")
@Validated
public class TrafficOpsPoliceStationController {

    @Resource
    private TrafficOpsPoliceStationService opsPoliceStationService;

    @PostMapping("/create")
    @Operation(summary = "创建警用装备采集站")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-station:create')")
    public CommonResult<String> createOpsPoliceStation(@Valid @RequestBody TrafficOpsPoliceStationSaveReqVO createReqVO) {
        return success(opsPoliceStationService.createOpsPoliceStation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新警用装备采集站")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-station:update')")
    public CommonResult<Boolean> updateOpsPoliceStation(@Valid @RequestBody TrafficOpsPoliceStationSaveReqVO updateReqVO) {
        opsPoliceStationService.updateOpsPoliceStation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除警用装备采集站")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-station:delete')")
    public CommonResult<Boolean> deleteOpsPoliceStation(@RequestParam("id") String id) {
        opsPoliceStationService.deleteOpsPoliceStation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得警用装备采集站")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-station:query')")
    public CommonResult<TrafficOpsPoliceStationRespVO> getOpsPoliceStation(@RequestParam("id") String id) {
        TrafficOpsPoliceStationDO opsPoliceStation = opsPoliceStationService.getOpsPoliceStation(id);
        return success(BeanUtils.toBean(opsPoliceStation, TrafficOpsPoliceStationRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得警用装备采集站分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-station:query')")
    public CommonResult<PageResult<TrafficOpsPoliceStationRespVO>> getOpsPoliceStationPage(@Valid TrafficOpsPoliceStationPageReqVO pageReqVO) {
        PageResult<TrafficOpsPoliceStationDO> pageResult = opsPoliceStationService.getOpsPoliceStationPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsPoliceStationRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出警用装备采集站 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-station:export')")
    public void exportOpsPoliceStationExcel(@Valid TrafficOpsPoliceStationPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsPoliceStationDO> list = opsPoliceStationService.getOpsPoliceStationPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "警用装备采集站.xls", "数据", TrafficOpsPoliceStationRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsPoliceStationRespVO.class));
    }

}