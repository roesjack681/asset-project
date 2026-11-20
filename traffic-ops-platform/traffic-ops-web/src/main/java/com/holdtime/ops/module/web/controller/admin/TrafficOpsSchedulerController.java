package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsBigDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 运维调度")
@RestController
@RequestMapping("/traffic/ops-scheduler")
@Validated
public class TrafficOpsSchedulerController {

    @Resource
    private TrafficOpsBigDataService opsBigDataService;


    @GetMapping("/queryMaintenancePoint")
    @Operation(summary = "获取维修点位信息")
    public CommonResult<List<TrafficOpsMaintainPointRespVO>> queryMaintenancePoint() {
        return success(opsBigDataService.queryMaintenancePoint());
    }

    @GetMapping("/queryMaintenancePointCount")
    @Operation(summary = "获取各电警类型数量")
    public CommonResult<List<TrafficOpsMaintainPointCountRespVO>> queryMaintenancePointCount() {
        return success(opsBigDataService.queryMaintenancePointCount());
    }

    @GetMapping("/queryMaintenancePointStatusCount")
    @Operation(summary = "获取各状态单数量")
    public CommonResult<List<TrafficOpsMaintainPointCountRespVO>> queryMaintenancePointStatusCount(@RequestParam(value = "djType",required = false) String djType ) {
        if (StringUtils.isBlank(djType)) {
            djType = "";
        }
        return success(opsBigDataService.queryMaintenancePointStatusCount(djType));
    }

}