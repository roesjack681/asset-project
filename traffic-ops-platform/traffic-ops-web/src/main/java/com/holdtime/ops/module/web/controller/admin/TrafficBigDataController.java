package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.model.dto.TrafficOpsHealthRateDTO;
import com.holdtime.ops.module.model.dto.TrafficOpsMaintenanceRankDTO;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.*;
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
@RequestMapping("/traffic/ops-bigdata")
@Validated
public class TrafficBigDataController {
    @Resource
    private TrafficOpsBigDataService opsBigDataService;
    @Resource
    private TrafficOpsIntersectionExtService opsIntersectionExtService;

    @GetMapping("/get")
    @Operation(summary = "基础设施")
    @Parameter(name = "intersectionName", description = "交叉口名称", required = true, example = "1024")
    public CommonResult<TrafficOpsBigDataBaseRespVO> getOpsBigled(@RequestParam(value = "intersectionName", required = false) String intersectionName,@RequestParam(value = "dominion", required = false) String dominion,@RequestParam(value = "status", required = false) String status) {
        TrafficOpsBigDataBaseRespVO respVo = opsBigDataService.getOpsBigDataBase(intersectionName,dominion,status);
        return success(BeanUtils.toBean(respVo, TrafficOpsBigDataBaseRespVO.class));
    }

    @GetMapping("/autocompleteOpsIntersection")
    @Operation(summary = "自动匹配")
    public CommonResult<List<TrafficOpsIntersectionRespVO>> autocompleteOpsIntersection(@RequestParam("intersectionName") String intersectionName) {
        return success(opsIntersectionExtService.autocompleteOpsIntersection(intersectionName));
    }

    @GetMapping("/queryTrafficOpsPositionList")
    @Operation(summary = "获取位置信息")
    public CommonResult<List<TrafficOpsPositionRespVO>> queryTrafficOpsPositionList(@RequestParam(value = "intersectionName", required = false) String intersectionName) {
        return success(opsBigDataService.queryTrafficOpsPositionList(intersectionName));
    }

    @GetMapping("/getWxType")
    @Operation(summary = "获取维修类型")
    public CommonResult<List<Map<String,Object>>> getWxType(@RequestParam(value = "affiliatedUnit", required = false) String affiliatedUnit, @RequestParam(value = "equLabel", required = false) String equLabel) {
        return (success(opsBigDataService.getWxType(affiliatedUnit, equLabel)));
    }

    @GetMapping("/analysisMaintenanceRanking")
    @Operation(summary = "统计分析维修人员排名")
    public CommonResult<List<TrafficOpsMaintenanceRankDTO>> analysisMaintenanceRanking() {
        return success(opsBigDataService.analysisMaintenanceRanking());
    }

    @GetMapping("/analysisNearlyThreeYearsMaintenanceCnt")
    @Operation(summary = "统计分析指定类型设备近三年维修量")
    public CommonResult<Map<String, Collection<Integer>>> analysisNearlyThreeYearsMaintenanceCnt(@RequestParam(value = "equType") String equType) {
        return success(opsBigDataService.analysisNearlyThreeYearsMaintenanceCnt(equType));
    }

    @GetMapping("/analysisElectricPoliceByLabel")
    @Operation(summary = "根据标签分析电警信息")
    public CommonResult<List<Map<String, Collection<Integer>>>> analysisElectricPoliceByLabel() {
        return success(opsBigDataService.analysisElectricPoliceByLabel());
    }

    @GetMapping("/analysisMonitorResultByEventType")
    @Operation(summary = "根据事件类型分析监控结果")
    public CommonResult<List<Map<String, Collection<Integer>>>> analysisMonitorResultByEventType(@RequestParam(value = "equType") String equType) {
        return success(opsBigDataService.analysisMonitorResultByEventType(equType));
    }

    @GetMapping("/analysisMonitorResultByKeyWord")
    @Operation(summary = "根据IP、方向以及事件类型分析设备监控结果")
    public CommonResult<List<Map<String, Collection<Integer>>>> analysisMonitorResultByKeyWord(@RequestParam(value = "equType") String equType) {
//        Page<?> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Collection<Integer>>> resultList = opsBigDataService.analysisMonitorResultByKeyWord(equType);
        return success(resultList);
    }

    @GetMapping("/analysisMaintenanceDashboardCnt")
    @Operation(summary = "统计运维工作白板")
    public CommonResult<Map<String, Object>> analysisMaintenanceDashboardCnt(@RequestParam(value = "affiliatedUnit") String affiliatedUnit) {
        Map<String, Object> resultMap = opsBigDataService.analysisMaintenanceDashboardCnt(affiliatedUnit);
        return success(resultMap);
    }

    @GetMapping("/analysisMaintenanceFinishSituation")
    @Operation(summary = "工单完成情况")
    public CommonResult<Map<String, Object>> analysisMaintenanceFinishSituation(@RequestParam(value = "affiliatedUnit") String affiliatedUnit) {
        Map<String, Object> resultMap = opsBigDataService.analysisMaintenanceFinishSituation(affiliatedUnit);
        return success(resultMap);
    }

    @GetMapping("/analysisElectricPoliceMonitorByLabel")
    @Operation(summary = "根据标签分析电警监测异常数量")
    public CommonResult<List<Map<String, Collection<Integer>>>> analysisElectricPoliceMonitorByLabel(@RequestParam(value = "hour") Integer hour) {
        List<Map<String, Collection<Integer>>> resultMap = opsBigDataService.analysisElectricPoliceMonitorByLabel(hour);
        return success(resultMap);
    }

    @GetMapping("/analysisKhsbMonitorResult")
    @Operation(summary = "分析集指考核设备在线率")
    public CommonResult<List<TrafficOpsHealthRateDTO>> analysisKhsbMonitorResult(@RequestParam(value = "intervalDay", required = false) Integer intervalDay) {
        List<TrafficOpsHealthRateDTO> resultMap = opsBigDataService.analysisKhsbMonitorResult(intervalDay);
        return success(resultMap);
    }

    @GetMapping("/calcVioValidSurveilAvgRate")
    @Operation(summary = "分析集指考核设备在线率")
    public CommonResult<BigDecimal> calcVioValidSurveilAvgRate() {
        return success(opsBigDataService.calcVioValidSurveilAvgRate());
    }

    @GetMapping("/analysisNearlyOneYearsMaintenanceCnt")
    @Operation(summary = "按所属部门分析近1年维修量")
    public CommonResult<Map<String, Integer>> analysisNearlyOneYearsMaintenanceCnt(@RequestParam(value = "affiliatedUnit") String affiliatedUnit) {
        return success(opsBigDataService.analysisNearlyOneYearsMaintenanceCnt(affiliatedUnit));
    }
}
