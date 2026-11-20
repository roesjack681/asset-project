package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.TrafficOpsRegisterExtService;
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
import com.holdtime.ops.module.entity.TrafficOpsUrgencySettingsDO;
import com.holdtime.ops.module.service.TrafficOpsUrgencySettingsService;

@Tag(name = "管理后台 - 维保紧急程度配置")
@RestController
@RequestMapping("/traffic/ops-urgency-settings")
@Validated
public class TrafficOpsUrgencySettingsController {

    @Resource
    private TrafficOpsUrgencySettingsService opsUrgencySettingsService;
    @Resource
    private TrafficOpsRegisterExtService trafficOpsRegisterExtService;

    @PostMapping("/create")
    @Operation(summary = "创建维保紧急程度配置")
    @PreAuthorize("@ss.hasPermission('traffic:ops-urgency-settings:create')")
    public CommonResult<String> createOpsUrgencySettings(@Valid @RequestBody TrafficOpsUrgencySettingsSaveReqVO createReqVO) {
        return success(opsUrgencySettingsService.createOpsUrgencySettings(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新维保紧急程度配置")
    @PreAuthorize("@ss.hasPermission('traffic:ops-urgency-settings:update')")
    public CommonResult<Boolean> updateOpsUrgencySettings(@Valid @RequestBody TrafficOpsUrgencySettingsSaveReqVO updateReqVO) {
        opsUrgencySettingsService.updateOpsUrgencySettings(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除维保紧急程度配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-urgency-settings:delete')")
    public CommonResult<Boolean> deleteOpsUrgencySettings(@RequestParam("id") String id) {
        opsUrgencySettingsService.deleteOpsUrgencySettings(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得维保紧急程度配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-urgency-settings:query')")
    public CommonResult<TrafficOpsUrgencySettingsRespVO> getOpsUrgencySettings(@RequestParam("id") String id) {
        TrafficOpsUrgencySettingsDO opsUrgencySettings = opsUrgencySettingsService.getOpsUrgencySettings(id);
        return success(BeanUtils.toBean(opsUrgencySettings, TrafficOpsUrgencySettingsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得维保紧急程度配置分页")
    public CommonResult<PageResult<TrafficOpsUrgencySettingsRespVO>> getOpsUrgencySettingsPage(@Valid TrafficOpsUrgencySettingsPageReqVO pageReqVO) {
        PageResult<TrafficOpsUrgencySettingsDO> pageResult = opsUrgencySettingsService.getOpsUrgencySettingsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsUrgencySettingsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出维保紧急程度配置 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-urgency-settings:export')")
    public void exportOpsUrgencySettingsExcel(@Valid TrafficOpsUrgencySettingsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsUrgencySettingsDO> list = opsUrgencySettingsService.getOpsUrgencySettingsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "维保紧急程度配置.xls", "数据", TrafficOpsUrgencySettingsRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsUrgencySettingsRespVO.class));
    }

    /**
     * 查询可选择人员
     * @return 查询可选择人员
     */
    @GetMapping("/getAllOpsRegister")
    @Operation(summary = "查询所有注册用户")
    public CommonResult<List<AppTrafficOpsRegisterRespVO>> getAllOpsRegister() {
        return success(trafficOpsRegisterExtService.getAllOpsRegister());
    }

}