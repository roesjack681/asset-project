package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.holdtime.ops.module.entity.VDeviceLocationNewDO;
import com.holdtime.ops.module.model.vo.VDeviceLocationNewPageReqVO;
import com.holdtime.ops.module.model.vo.VDeviceLocationNewRespVO;
import com.holdtime.ops.module.service.VDeviceLocationNewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 车辆最新位置查询")
@RestController
@RequestMapping("/ops/V-device-location-new")
@Validated
public class VDeviceLocationNewController {

    @Resource
    private VDeviceLocationNewService vDeviceLocationNewService;

    @GetMapping("/get")
    @Operation(summary = "获得车辆最新位置查询")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('ops:V-device-location-new:query')")
    public CommonResult<VDeviceLocationNewRespVO> getVDeviceLocationNew(@RequestParam("id") String id) {
        VDeviceLocationNewDO vDeviceLocationNew = vDeviceLocationNewService.getVDeviceLocationNew(id);
        return success(BeanUtils.toBean(vDeviceLocationNew, VDeviceLocationNewRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得车辆最新位置查询分页")
//    @PreAuthorize("@ss.hasPermission('ops:V-device-location-new:query')")
    public CommonResult<PageResult<VDeviceLocationNewRespVO>> getVDeviceLocationNewPage(@Valid VDeviceLocationNewPageReqVO pageReqVO) {
        PageResult<VDeviceLocationNewDO> pageResult = vDeviceLocationNewService.getVDeviceLocationNewPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VDeviceLocationNewRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出车辆最新位置查询 Excel")
//    @PreAuthorize("@ss.hasPermission('ops:V-device-location-new:export')")
    public void exportVDeviceLocationNewExcel(@Valid VDeviceLocationNewPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VDeviceLocationNewDO> list = vDeviceLocationNewService.getVDeviceLocationNewPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "车辆最新位置查询.xls", "数据", VDeviceLocationNewRespVO.class,
                        BeanUtils.toBean(list, VDeviceLocationNewRespVO.class));
    }

}