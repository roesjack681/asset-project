package com.holdtime.ops.module.web.controller.admin;

import com.fhs.common.utils.StringUtil;
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
import com.holdtime.ops.module.entity.TrafficOpsRadarDO;
import com.holdtime.ops.module.service.TrafficOpsRadarService;

@Tag(name = "管理后台 - 雷达")
@RestController
@RequestMapping("/traffic/ops-radar")
@Validated
public class TrafficOpsRadarController {

    @Resource
    private TrafficOpsRadarService opsRadarService;

    @PostMapping("/create")
    @Operation(summary = "创建雷达")
    @PreAuthorize("@ss.hasPermission('traffic:ops-radar:create')")
    public CommonResult<String> createOpsRadar(@Valid @RequestBody TrafficOpsRadarSaveReqVO createReqVO) {
        return success(opsRadarService.createOpsRadar(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新雷达")
    @PreAuthorize("@ss.hasPermission('traffic:ops-radar:update')")
    public CommonResult<Boolean> updateOpsRadar(@Valid @RequestBody TrafficOpsRadarSaveReqVO updateReqVO) {
        opsRadarService.updateOpsRadar(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除雷达")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-radar:delete')")
    public CommonResult<Boolean> deleteOpsRadar(@RequestParam("id") String id) {
        opsRadarService.deleteOpsRadar(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得雷达")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-radar:query')")
    public CommonResult<TrafficOpsRadarRespVO> getOpsRadar(@RequestParam("id") String id) {
        TrafficOpsRadarDO opsRadar = opsRadarService.getOpsRadar(id);
        return success(BeanUtils.toBean(opsRadar, TrafficOpsRadarRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得雷达分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-radar:query')")
    public CommonResult<PageResult<TrafficOpsRadarRespVO>> getOpsRadarPage(@Valid TrafficOpsRadarPageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        if(!StringUtil.isEmpty(isFy)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
        PageResult<TrafficOpsRadarDO> pageResult = opsRadarService.getOpsRadarPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsRadarRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出雷达 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-radar:export')")
    public void exportOpsRadarExcel(@Valid TrafficOpsRadarPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsRadarDO> list = opsRadarService.getOpsRadarPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "雷达.xls", "数据", TrafficOpsRadarRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsRadarRespVO.class));
    }

}