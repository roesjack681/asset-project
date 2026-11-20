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
import com.holdtime.ops.module.entity.PublicUserAreaGroupDO;
import com.holdtime.ops.module.service.PublicUserAreaGroupService;

@Tag(name = "管理后台 - 区域用户组信息")
@RestController
@RequestMapping("/public/user-area-group")
@Validated
public class PublicUserAreaGroupController {

    @Resource
    private PublicUserAreaGroupService userAreaGroupService;

    @PostMapping("/create")
    @Operation(summary = "创建区域用户组信息")
    @PreAuthorize("@ss.hasPermission('public:user-area-group:create')")
    public CommonResult<String> createUserAreaGroup(@Valid @RequestBody PublicUserAreaGroupSaveReqVO createReqVO) {
        return success(userAreaGroupService.createUserAreaGroup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新区域用户组信息")
    @PreAuthorize("@ss.hasPermission('public:user-area-group:update')")
    public CommonResult<Boolean> updateUserAreaGroup(@Valid @RequestBody PublicUserAreaGroupSaveReqVO updateReqVO) {
        userAreaGroupService.updateUserAreaGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除区域用户组信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('public:user-area-group:delete')")
    public CommonResult<Boolean> deleteUserAreaGroup(@RequestParam("id") String id) {
        userAreaGroupService.deleteUserAreaGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得区域用户组信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('public:user-area-group:query')")
    public CommonResult<PublicUserAreaGroupRespVO> getUserAreaGroup(@RequestParam("id") String id) {
        PublicUserAreaGroupDO userAreaGroup = userAreaGroupService.getUserAreaGroup(id);
        return success(BeanUtils.toBean(userAreaGroup, PublicUserAreaGroupRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得区域用户组信息分页")
    @PreAuthorize("@ss.hasPermission('public:user-area-group:query')")
    public CommonResult<PageResult<PublicUserAreaGroupRespVO>> getUserAreaGroupPage(@Valid PublicUserAreaGroupPageReqVO pageReqVO) {
        PageResult<PublicUserAreaGroupDO> pageResult = userAreaGroupService.getUserAreaGroupPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicUserAreaGroupRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出区域用户组信息 Excel")
    @PreAuthorize("@ss.hasPermission('public:user-area-group:export')")
    public void exportUserAreaGroupExcel(@Valid PublicUserAreaGroupPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicUserAreaGroupDO> list = userAreaGroupService.getUserAreaGroupPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "区域用户组信息.xls", "数据", PublicUserAreaGroupRespVO.class,
                        BeanUtils.toBean(list, PublicUserAreaGroupRespVO.class));
    }

}