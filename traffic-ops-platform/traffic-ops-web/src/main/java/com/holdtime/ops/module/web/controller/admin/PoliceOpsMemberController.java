package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.service.PoliceOpsMemberExtService;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
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
import com.holdtime.ops.module.entity.PoliceOpsMemberDO;
import com.holdtime.ops.module.service.PoliceOpsMemberService;

@Tag(name = "管理后台 - 公安杆件")
@RestController
@RequestMapping("/police/ops-member")
@Validated
public class PoliceOpsMemberController {

    @Resource
    private PoliceOpsMemberService opsMemberService;
    @Resource
    private PoliceOpsMemberExtService opsMemberExtService;
    @Resource
    private TrafficOpsMaintenanceExtService opsMaintenanceExtService;

    @PostMapping("/create")
    @Operation(summary = "创建杆件")
    @PreAuthorize("@ss.hasPermission('police:ops-member:create')")
    public CommonResult<String> createOpsMember(@Valid @RequestBody PoliceOpsMemberSaveReqVO createReqVO) {
        return success(opsMemberService.createOpsMember(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新杆件")
    @PreAuthorize("@ss.hasPermission('police:ops-member:update')")
    public CommonResult<Boolean> updateOpsMember(@Valid @RequestBody PoliceOpsMemberSaveReqVO updateReqVO) {
        opsMemberService.updateOpsMember(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除杆件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:ops-member:delete')")
    public CommonResult<Boolean> deleteOpsMember(@RequestParam("id") String id) {
        opsMemberExtService.deleteOpsMember(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得杆件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:ops-member:query')")
    public CommonResult<PoliceOpsMemberRespVO> getOpsMember(@RequestParam("id") String id) {
        return success(opsMemberExtService.getOpsMember(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得杆件分页")
    @PreAuthorize("@ss.hasPermission('police:ops-member:query')")
    public CommonResult<PageResult<PoliceOpsMemberExtRespVO>> getOpsMemberPage(@Valid PoliceOpsMemberPageReqVO pageReqVO) {
        PageResult<PoliceOpsMemberExtRespVO> pageResult = opsMemberExtService.getOpsMemberPage(pageReqVO);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(policeOpsMemberExtRespVO -> {
            policeOpsMemberExtRespVO.setMaintenanceCnt(opsMaintenanceExtService.queryEquipMaintenanceCnt(policeOpsMemberExtRespVO.getId()));
        });
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出杆件 Excel")
    @PreAuthorize("@ss.hasPermission('police:ops-member:export')")
    public void exportOpsMemberExcel(@Valid PoliceOpsMemberPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PoliceOpsMemberDO> list = opsMemberService.getOpsMemberPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "杆件.xls", "数据", PoliceOpsMemberRespVO.class,
                        BeanUtils.toBean(list, PoliceOpsMemberRespVO.class));
    }

}