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
import com.holdtime.ops.module.entity.TrafficOpsIntersectionDO;
import com.holdtime.ops.module.service.TrafficOpsIntersectionService;

@Tag(name = "管理后台 - 交叉口信息")
@RestController
@RequestMapping("/traffic/ops-intersection")
@Validated
public class TrafficOpsIntersectionController {

    @Resource
    private TrafficOpsIntersectionService opsIntersectionService;

    @PostMapping("/create")
    @Operation(summary = "创建交叉口信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-intersection:create')")
    public CommonResult<String> createOpsIntersection(@Valid @RequestBody TrafficOpsIntersectionSaveReqVO createReqVO) {
        return success(opsIntersectionService.createOpsIntersection(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新交叉口信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-intersection:update')")
    public CommonResult<Boolean> updateOpsIntersection(@Valid @RequestBody TrafficOpsIntersectionSaveReqVO updateReqVO) {
        opsIntersectionService.updateOpsIntersection(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除交叉口信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-intersection:delete')")
    public CommonResult<Boolean> deleteOpsIntersection(@RequestParam("id") String id) {
        opsIntersectionService.deleteOpsIntersection(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得交叉口信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-intersection:query')")
    public CommonResult<TrafficOpsIntersectionRespVO> getOpsIntersection(@RequestParam("id") String id) {
        TrafficOpsIntersectionDO opsIntersection = opsIntersectionService.getOpsIntersection(id);
        return success(BeanUtils.toBean(opsIntersection, TrafficOpsIntersectionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得交叉口信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-intersection:query')")
    public CommonResult<PageResult<TrafficOpsIntersectionRespVO>> getOpsIntersectionPage(@Valid TrafficOpsIntersectionPageReqVO pageReqVO) {
        PageResult<TrafficOpsIntersectionDO> pageResult = opsIntersectionService.getOpsIntersectionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsIntersectionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出交叉口信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-intersection:export')")
    public void exportOpsIntersectionExcel(@Valid TrafficOpsIntersectionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsIntersectionDO> list = opsIntersectionService.getOpsIntersectionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "交叉口信息.xls", "数据", TrafficOpsIntersectionRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsIntersectionRespVO.class));
    }

}