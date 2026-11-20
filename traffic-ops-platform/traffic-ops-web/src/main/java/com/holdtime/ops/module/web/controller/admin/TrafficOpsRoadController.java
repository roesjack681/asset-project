package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.TrafficOpsRoadExtService;
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
import com.holdtime.ops.module.entity.TrafficOpsRoadDO;
import com.holdtime.ops.module.service.TrafficOpsRoadService;

@Tag(name = "管理后台 - 道路信息")
@RestController
@RequestMapping("/traffic/ops-road")
@Validated
public class TrafficOpsRoadController {

    @Resource
    private TrafficOpsRoadService opsRoadService;
    @Resource
    private TrafficOpsRoadExtService opsRoadExtService;

    @PostMapping("/create")
    @Operation(summary = "创建道路信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-road:create')")
    public CommonResult<String> createOpsRoad(@Valid @RequestBody TrafficOpsRoadSaveReqVO createReqVO) {
        return success(opsRoadService.createOpsRoad(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新道路信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-road:update')")
    public CommonResult<Boolean> updateOpsRoad(@Valid @RequestBody TrafficOpsRoadSaveReqVO updateReqVO) {
        opsRoadService.updateOpsRoad(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除道路信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-road:delete')")
    public CommonResult<Boolean> deleteOpsRoad(@RequestParam("id") String id) {
        opsRoadService.deleteOpsRoad(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得道路信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-road:query')")
    public CommonResult<TrafficOpsRoadRespVO> getOpsRoad(@RequestParam("id") String id) {
        TrafficOpsRoadDO opsRoad = opsRoadService.getOpsRoad(id);
        return success(BeanUtils.toBean(opsRoad, TrafficOpsRoadRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得道路信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-road:query')")
    public CommonResult<PageResult<TrafficOpsRoadRespVO>> getOpsRoadPage(@Valid TrafficOpsRoadPageReqVO pageReqVO) {
        PageResult<TrafficOpsRoadDO> pageResult = opsRoadService.getOpsRoadPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsRoadRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出道路信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-road:export')")
    public void exportOpsRoadExcel(@Valid TrafficOpsRoadPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsRoadDO> list = opsRoadService.getOpsRoadPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "道路信息.xls", "数据", TrafficOpsRoadRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsRoadRespVO.class));
    }

    @GetMapping("/getRoadMember")
    @Operation(summary = "获得道路所有杆件及绑定设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-road:query')")
    public CommonResult<PageResult<TrafficOpsMemberExtRespVO>> getRoadMember(@Valid TrafficOpsMemberPageReqVO pageReqVO,@RequestParam("id") String id) {
        //根据道路名称查询杆件信息
        PageResult<TrafficOpsMemberExtRespVO> pageResult = opsRoadExtService.getOpsRoadPage(pageReqVO,id);
        return success(BeanUtils.toBean(pageResult, TrafficOpsMemberExtRespVO.class));
    }

    @PostMapping("/checkResult")
    @Operation(summary = "保存核验结果")
    @PreAuthorize("@ss.hasPermission('traffic:ops-road:create')")
    public CommonResult<String> checkResult(@Valid @RequestBody TrafficOpsMemberExtRespVO createReqVO) {
        return success(opsRoadExtService.checkResult(createReqVO));
    }

    @GetMapping("/roadDict")
    @Operation(summary = "获得道路信息字典信息(不分页)")
    public CommonResult<PageResult<TrafficOpsRoadRespVO>> getOpsRoadDictList(@Valid TrafficOpsRoadPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<TrafficOpsRoadDO> pageResult = opsRoadService.getOpsRoadPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsRoadRespVO.class));
    }

}