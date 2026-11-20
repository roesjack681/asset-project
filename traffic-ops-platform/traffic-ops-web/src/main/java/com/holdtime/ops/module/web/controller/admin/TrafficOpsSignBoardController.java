package com.holdtime.ops.module.web.controller.admin;

import com.fhs.common.utils.StringUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
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
import com.holdtime.ops.module.entity.TrafficOpsSignBoardDO;
import com.holdtime.ops.module.service.TrafficOpsSignBoardService;

@Tag(name = "管理后台 - 标志牌")
@RestController
@RequestMapping("/traffic/ops-sign-board")
@Validated
public class TrafficOpsSignBoardController {

    @Resource
    private TrafficOpsSignBoardService opsSignBoardService;

    @PostMapping("/create")
    @Operation(summary = "创建标志牌")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sign-board:create')")
    public CommonResult<String> createOpsSignBoard(@Valid @RequestBody TrafficOpsSignBoardSaveReqVO createReqVO) {
        return success(opsSignBoardService.createOpsSignBoard(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新标志牌")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sign-board:update')")
    public CommonResult<Boolean> updateOpsSignBoard(@Valid @RequestBody TrafficOpsSignBoardSaveReqVO updateReqVO) {
        opsSignBoardService.updateOpsSignBoard(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除标志牌")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-sign-board:delete')")
    public CommonResult<Boolean> deleteOpsSignBoard(@RequestParam("id") String id) {
        opsSignBoardService.deleteOpsSignBoard(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得标志牌")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sign-board:query')")
    public CommonResult<TrafficOpsSignBoardRespVO> getOpsSignBoard(@RequestParam("id") String id) {
        TrafficOpsSignBoardDO opsSignBoard = opsSignBoardService.getOpsSignBoard(id);
        return success(BeanUtils.toBean(opsSignBoard, TrafficOpsSignBoardRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得标志牌分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sign-board:query')")
    public CommonResult<PageResult<TrafficOpsSignBoardRespVO>> getOpsSignBoardPage(@Valid TrafficOpsSignBoardPageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        if(!StringUtil.isEmpty(isFy)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
        PageResult<TrafficOpsSignBoardDO> pageResult = opsSignBoardService.getOpsSignBoardPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsSignBoardRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出标志牌 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sign-board:export')")
    public void exportOpsSignBoardExcel(@Valid TrafficOpsSignBoardPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsSignBoardDO> list = opsSignBoardService.getOpsSignBoardPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "标志牌.xls", "数据", TrafficOpsSignBoardRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsSignBoardRespVO.class));
    }

}