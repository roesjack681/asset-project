package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalMapper;
import com.holdtime.ops.module.service.TrafficTslTimingPlanExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import org.springframework.util.CollectionUtils;
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
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import com.holdtime.ops.module.service.TrafficOpsSignalService;

@Tag(name = "管理后台 - 信号机")
@RestController
@RequestMapping("/traffic/ops-signal")
@Validated
public class TrafficOpsSignalController {

    @Resource
    private TrafficOpsSignalService opsSignalService;
    @Resource
    private TrafficTslTimingPlanExtService tslTimingPlanExtService;
    @Resource
    private TrafficOpsSignalMapper trafficOpsSignalMapper;

    @PostMapping("/create")
    @Operation(summary = "创建信号机")
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal:create')")
    public CommonResult<String> createOpsSignal(@Valid @RequestBody TrafficOpsSignalSaveReqVO createReqVO) {
        return success(opsSignalService.createOpsSignal(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新信号机")
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal:update')")
    public CommonResult<Boolean> updateOpsSignal(@Valid @RequestBody TrafficOpsSignalSaveReqVO updateReqVO) {
        opsSignalService.updateOpsSignal(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除信号机")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal:delete')")
    public CommonResult<Boolean> deleteOpsSignal(@RequestParam("id") String id) {
        opsSignalService.deleteOpsSignal(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得信号机")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsSignalRespVO> getOpsSignal(@RequestParam("id") String id) {
        TrafficOpsSignalDO opsSignal = opsSignalService.getOpsSignal(id);
        return success(BeanUtils.toBean(opsSignal, TrafficOpsSignalRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得信号机分页")
    public CommonResult<PageResult<TrafficOpsSignalRespVO>> getOpsSignalPage(@Valid TrafficOpsSignalPageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        LambdaQueryWrapperX<TrafficOpsSignalDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsSignalDO>()
                .likeIfPresent(TrafficOpsSignalDO::getEquName, pageReqVO.getEquName())
                .eqIfPresent(TrafficOpsSignalDO::getEquType, pageReqVO.getEquType())
                .likeIfPresent(TrafficOpsSignalDO::getIntersectionName, pageReqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsSignalDO::getLng, pageReqVO.getLng())
                .eqIfPresent(TrafficOpsSignalDO::getLat, pageReqVO.getLat())
                .eqIfPresent(TrafficOpsSignalDO::getAdress, pageReqVO.getAdress())
                .betweenIfPresent(TrafficOpsSignalDO::getInstallDate, pageReqVO.getInstallDate())
                .eqIfPresent(TrafficOpsSignalDO::getNetworkType, pageReqVO.getNetworkType())
                .eqIfPresent(TrafficOpsSignalDO::getNetworkAddr, pageReqVO.getNetworkAddr())
                .eqIfPresent(TrafficOpsSignalDO::getPhase, pageReqVO.getPhase())
                .eqIfPresent(TrafficOpsSignalDO::getNetworkStatus, pageReqVO.getNetworkStatus())
                .eqIfPresent(TrafficOpsSignalDO::getManufacturer, pageReqVO.getManufacturer())
                .eqIfPresent(TrafficOpsSignalDO::getEquModel, pageReqVO.getEquModel())
                .eqIfPresent(TrafficOpsSignalDO::getEquNumber, pageReqVO.getEquNumber())
                .eqIfPresent(TrafficOpsSignalDO::getIpAddr, pageReqVO.getIpAddr())
                .eqIfPresent(TrafficOpsSignalDO::getMask, pageReqVO.getMask())
                .eqIfPresent(TrafficOpsSignalDO::getGateway, pageReqVO.getGateway())
                .eqIfPresent(TrafficOpsSignalDO::getBelongLoop, pageReqVO.getBelongLoop())
                .eqIfPresent(TrafficOpsSignalDO::getNetworkProtocol, pageReqVO.getNetworkProtocol())
                .eqIfPresent(TrafficOpsSignalDO::getServerIp, pageReqVO.getServerIp())
                .eqIfPresent(TrafficOpsSignalDO::getPanorama, pageReqVO.getPanorama())
                .eqIfPresent(TrafficOpsSignalDO::getAccessControl, pageReqVO.getAccessControl())
                .eqIfPresent(TrafficOpsSignalDO::getUnit, pageReqVO.getUnit())
                .likeIfPresent(TrafficOpsSignalDO::getDominion, pageReqVO.getDominion())
                .eqIfPresent(TrafficOpsSignalDO::getStatus, pageReqVO.getStatus())
                .betweenIfPresent(TrafficOpsSignalDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsSignalDO::getDeletedTime, pageReqVO.getDeletedTime())
                .orderByDesc(TrafficOpsSignalDO::getId);
        if(!StringUtil.isEmpty(isFy)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    opsDOLambdaQueryWrapperX.like(TrafficOpsSignalDO::getIntersectionName, roadName);
                }
            }
        }
        PageResult<TrafficOpsSignalDO> pageResult = trafficOpsSignalMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);
        return success(BeanUtils.toBean(pageResult, TrafficOpsSignalRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出信号机 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal:export')")
    public void exportOpsSignalExcel(@Valid TrafficOpsSignalPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsSignalDO> list = opsSignalService.getOpsSignalPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "信号灯.xls", "数据", TrafficOpsSignalRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsSignalRespVO.class));
    }

    @GetMapping("/queryJunctionPlanInfo")
    @Operation(summary = "获得信号机配时周方案")
    @Parameter(name = "id", description = "信号机ID", required = true, example = "1024")
    public CommonResult<TrafficTslJunctionPlanInfoExtRespVO> queryJunctionPlanInfo(@RequestParam("signalId") String signalId) {
        return success(tslTimingPlanExtService.queryJunctionPlanInfo(signalId));
    }

    @GetMapping("/queryJunctionDayPlanList")
    @Operation(summary = "获得信号机配时指定日方案信息")
    @Parameter(name = "id", description = "日方案ID", required = true, example = "1024")
    public CommonResult<List<TrafficTslJunctionDayPlanDetailRespVO>> queryJunctionDayPlanList(@RequestParam("junctionId") String junctionId,
                                                                                              @RequestParam("dayPlanId") String dayPlanId) {
        return success(tslTimingPlanExtService.queryJunctionDayPlanList(junctionId, dayPlanId));
    }

    @GetMapping("/queryJunctionTimeStageList")
    @Operation(summary = "获得信号机配时阶段信息")
    public CommonResult<List<TrafficTslJunctionTimingPlanStageRespVO>> queryJunctionTimeStageList(@RequestParam("junctionId") String junctionId,
                                                                                   @RequestParam("planId") String planId) {
        return success(tslTimingPlanExtService.queryJunctionTimeStageList(junctionId, planId));
    }

}