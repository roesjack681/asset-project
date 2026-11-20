package com.holdtime.ops.module.web.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.entity.TrafficOpsIssueDetailDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssueDetailMapper;
import com.holdtime.ops.module.service.TrafficOpsIssueRecordExtService;
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
import com.holdtime.ops.module.entity.TrafficOpsIssueRecordDO;
import com.holdtime.ops.module.service.TrafficOpsIssueRecordService;

@Tag(name = "管理后台 - 领用记录")
@RestController
@RequestMapping("/traffic/ops-issue-record")
@Validated
public class TrafficOpsIssueRecordController {

    @Resource
    private TrafficOpsIssueRecordService opsIssueRecordService;
    @Resource
    private TrafficOpsIssueRecordExtService opsIssueRecordExtService;
    @Resource
    private TrafficOpsIssueDetailMapper opsIssueDetailMapper;

    @PostMapping("/create")
    @Operation(summary = "保存领用记录")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-record:create')")
    public CommonResult<String> createOpsIssueRecord(@Valid @RequestBody TrafficOpsIssueRecordExtSaveReqVO createReqVO) {
        return success(opsIssueRecordExtService.createOpsIssueRecord(createReqVO));
    }

//    @DeleteMapping("/delete")
//    @Operation(summary = "删除领用记录")
//    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-record:delete')")
//    public CommonResult<Boolean> deleteOpsIssueRecord(@RequestParam("id") String id) {
//        opsIssueRecordService.deleteOpsIssueRecord(id);
//        return success(true);
//    }

    @GetMapping("/get")
    @Operation(summary = "获得领用记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-record:query')")
    public CommonResult<TrafficOpsIssueRecordRespVO> getOpsIssueRecord(@RequestParam("id") String id) {
      return  success(opsIssueRecordExtService.getOpsIssueRecord(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得领用记录分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-record:query')")
    public CommonResult<PageResult<TrafficOpsIssueRecordRespVO>> getOpsIssueRecordPage(@Valid TrafficOpsIssueRecordPageReqVO pageReqVO) {
        return success(opsIssueRecordExtService.getOpsIssueRecordPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出领用记录 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-record:export')")
    public void exportOpsIssueRecordExcel(@Valid TrafficOpsIssueRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsIssueRecordDO> list = opsIssueRecordService.getOpsIssueRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "领用记录.xls", "数据", TrafficOpsIssueRecordRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsIssueRecordRespVO.class));
    }

}