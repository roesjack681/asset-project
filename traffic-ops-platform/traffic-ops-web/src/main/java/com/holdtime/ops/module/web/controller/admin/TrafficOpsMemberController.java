package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.TrafficOpsMemberExtService;
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
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.service.TrafficOpsMemberService;

@Tag(name = "管理后台 - 杆件")
@RestController
@RequestMapping("/traffic/ops-member")
@Validated
public class TrafficOpsMemberController {

    @Resource
    private TrafficOpsMemberService opsMemberService;

    @Resource
    private TrafficOpsMemberExtService trafficOpsMemberExtService;

    @PostMapping("/create")
    @Operation(summary = "创建杆件")
    @PreAuthorize("@ss.hasPermission('traffic:ops-member:create')")
    public CommonResult<String> createOpsMember(@Valid @RequestBody TrafficOpsMemberSaveReqVO createReqVO) {
        return success(opsMemberService.createOpsMember(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新杆件")
    @PreAuthorize("@ss.hasPermission('traffic:ops-member:update')")
    public CommonResult<Boolean> updateOpsMember(@Valid @RequestBody TrafficOpsMemberSaveReqVO updateReqVO) {
        opsMemberService.updateOpsMember(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除杆件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-member:delete')")
    public CommonResult<Boolean> deleteOpsMember(@RequestParam("id") String id) {
        trafficOpsMemberExtService.deleteOpsMemberInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得杆件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-member:query')")
    public CommonResult<TrafficOpsMemberExtRespVO> getOpsMember(@RequestParam("id") String id) {
        return success(trafficOpsMemberExtService.getOpsMember(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得杆件分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-member:query')")
    public CommonResult<PageResult<TrafficOpsMemberExtRespVO>> getOpsMemberPage(@Valid TrafficOpsMemberPageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        return success(trafficOpsMemberExtService.getOpsMemberPage(pageReqVO,isFy));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出杆件 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-member:export')")
    public void exportOpsMemberExcel(@Valid TrafficOpsMemberPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsMemberDO> list = opsMemberService.getOpsMemberPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "杆件.xls", "数据", TrafficOpsMemberRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsMemberRespVO.class));
    }


}