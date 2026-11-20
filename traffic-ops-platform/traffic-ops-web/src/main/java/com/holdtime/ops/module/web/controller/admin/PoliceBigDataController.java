package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.PoliceOpsBigDataService;
import com.holdtime.ops.module.service.PoliceOpsMemberExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 项目名称： traffic-ops-platform
 * 功能描述： TrafficBigDataController概述
 * 创 建 人： yl
 * 创建日期： 2024年12月19日 11:40:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
@Tag(name = "管理后台 - 大数据")
@RestController
@RequestMapping("/police/ops-bigdata")
@Validated
public class PoliceBigDataController {
    @Resource
    private PoliceOpsBigDataService policeOpsBigDataService;
    @Resource
    private PoliceOpsMemberExtService policeOpsMemberExtService;

    @GetMapping("/get")
    @Operation(summary = "基础设施")
    @Parameter(name = "equName", description = "交叉口名称", required = true, example = "1024")
    public CommonResult<PoliceOpsBigDataBaseRespVO> getPoliceOpsBigDataBase(@RequestParam(value = "equName", required = false) String equName, @RequestParam(value = "dominion", required = false) String dominion, @RequestParam(value = "status", required = false) String status) {
        return success(policeOpsBigDataService.getPoliceOpsBigDataBase(equName, dominion, status));
    }

    @GetMapping("/autocompletePoliceOpsMemberList")
    @Operation(summary = "自动匹配")
    public CommonResult<List<PoliceOpsMemberExtRespVO>> autocompletePoliceOpsMemberList(@RequestParam("equName") String equName) {
        return success(policeOpsMemberExtService.autocompletePoliceOpsMemberList(equName));
    }

    @GetMapping("/queryPoliceOpsPositionList")
    @Operation(summary = "获取位置信息")
    public CommonResult<List<PoliceOpsPositionRespVO>> queryPoliceOpsPositionList(@RequestParam(value = "equName", required = false) String equName) {
        return success(policeOpsBigDataService.queryPoliceOpsPositionList(equName));
    }

    @GetMapping("/analysisPoliceOpsElectricCnt")
    @Operation(summary = "公安电警数量统计")
    public CommonResult<Map<String, Object>> analysisPoliceOpsElectricCnt() {
        return success(policeOpsBigDataService.analysisPoliceOpsElectricCnt());
    }

    @GetMapping("/analysisPoliceOpsElectricMaintenanceCnt")
    @Operation(summary = "公安电警维修数量统计")
    public CommonResult<List<Map<String, Object>>> analysisPoliceOpsElectricMaintenanceCnt() {
        return success(policeOpsBigDataService.analysisPoliceOpsElectricMaintenanceCnt());
    }

    @GetMapping("/analysisElectricPoliceMonitorByLabel")
    @Operation(summary = "根据标签分析公安电警监测异常数量")
    public CommonResult<List<Map<String, Collection<Integer>>>> analysisElectricPoliceMonitorByLabel(@RequestParam(value = "hour") Integer hour) {
        List<Map<String, Collection<Integer>>> resultMap = policeOpsBigDataService.analysisElectricPoliceMonitorByLabel(hour);
        return success(resultMap);
    }

    @GetMapping("/analysisPoliceElectricPoliceOnOffAvgRate")
    @Operation(summary = "公安电警维修数量统计")
    public CommonResult<Map<String, BigDecimal>> analysisPoliceElectricPoliceOnOffAvgRate() {
        return success(policeOpsBigDataService.analysisPoliceElectricPoliceOnOffAvgRate());
    }
}
