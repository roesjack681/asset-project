package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalLightMapper;
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
import com.holdtime.ops.module.entity.TrafficOpsSignalLightDO;
import com.holdtime.ops.module.service.TrafficOpsSignalLightService;

@Tag(name = "管理后台 - 信号灯")
@RestController
@RequestMapping("/traffic/ops-signal-light")
@Validated
public class TrafficOpsSignalLightController {

    @Resource
    private TrafficOpsSignalLightService opsSignalLightService;
    @Resource
    private TrafficOpsSignalLightMapper opsSignalLightMapper;

    @PostMapping("/create")
    @Operation(summary = "创建信号灯")
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal-light:create')")
    public CommonResult<String> createOpsSignalLight(@Valid @RequestBody TrafficOpsSignalLightSaveReqVO createReqVO) {
        return success(opsSignalLightService.createOpsSignalLight(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新信号灯")
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal-light:update')")
    public CommonResult<Boolean> updateOpsSignalLight(@Valid @RequestBody TrafficOpsSignalLightSaveReqVO updateReqVO) {
        opsSignalLightService.updateOpsSignalLight(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除信号灯")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal-light:delete')")
    public CommonResult<Boolean> deleteOpsSignalLight(@RequestParam("id") String id) {
        opsSignalLightService.deleteOpsSignalLight(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得信号灯")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal-light:query')")
    public CommonResult<TrafficOpsSignalLightRespVO> getOpsSignalLight(@RequestParam("id") String id) {
        TrafficOpsSignalLightDO opsSignalLight = opsSignalLightService.getOpsSignalLight(id);
        return success(BeanUtils.toBean(opsSignalLight, TrafficOpsSignalLightRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得信号灯分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal-light:query')")
    public CommonResult<PageResult<TrafficOpsSignalLightRespVO>> getOpsSignalLightPage(@Valid TrafficOpsSignalLightPageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        LambdaQueryWrapperX<TrafficOpsSignalLightDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsSignalLightDO>()
                .eqIfPresent(TrafficOpsSignalLightDO::getEquNumber, pageReqVO.getEquNumber())
                .likeIfPresent(TrafficOpsSignalLightDO::getEquName, pageReqVO.getEquName())
                .eqIfPresent(TrafficOpsSignalLightDO::getEquType, pageReqVO.getEquType())
                .likeIfPresent(TrafficOpsSignalLightDO::getIntersectionName, pageReqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsSignalLightDO::getLng, pageReqVO.getLng())
                .eqIfPresent(TrafficOpsSignalLightDO::getLat, pageReqVO.getLat())
                .eqIfPresent(TrafficOpsSignalLightDO::getAdress, pageReqVO.getAdress())
                .betweenIfPresent(TrafficOpsSignalLightDO::getInstallDate, pageReqVO.getInstallDate())
                .eqIfPresent(TrafficOpsSignalLightDO::getManufacturer, pageReqVO.getManufacturer())
                .eqIfPresent(TrafficOpsSignalLightDO::getEquModel, pageReqVO.getEquModel())
                .eqIfPresent(TrafficOpsSignalLightDO::getUnit, pageReqVO.getUnit())
                .eqIfPresent(TrafficOpsSignalLightDO::getLedPattern, pageReqVO.getLedPattern())
                .eqIfPresent(TrafficOpsSignalLightDO::getLedType, pageReqVO.getLedType())
                .eqIfPresent(TrafficOpsSignalLightDO::getLedSize, pageReqVO.getLedSize())
                .eqIfPresent(TrafficOpsSignalLightDO::getLedHeadNum, pageReqVO.getLedHeadNum())
                .eqIfPresent(TrafficOpsSignalLightDO::getArrangement, pageReqVO.getArrangement())
                .eqIfPresent(TrafficOpsSignalLightDO::getSignalDisplayMode, pageReqVO.getSignalDisplayMode())
                .betweenIfPresent(TrafficOpsSignalLightDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsSignalLightDO::getDeletedTime, pageReqVO.getDeletedTime())
                .likeIfPresent(TrafficOpsSignalLightDO::getDominion, pageReqVO.getDominion())
                .eqIfPresent(TrafficOpsSignalLightDO::getStatus, pageReqVO.getStatus())
                .orderByDesc(TrafficOpsSignalLightDO::getId);
        if(!StringUtil.isEmpty(isFy)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    opsDOLambdaQueryWrapperX.like(TrafficOpsSignalLightDO::getIntersectionName, roadName);
                }
            }
        }
        PageResult<TrafficOpsSignalLightDO> pageResult = opsSignalLightMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);
        return success(BeanUtils.toBean(pageResult, TrafficOpsSignalLightRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出信号灯 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-signal-light:export')")
    public void exportOpsSignalLightExcel(@Valid TrafficOpsSignalLightPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsSignalLightDO> list = opsSignalLightService.getOpsSignalLightPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "信号灯.xls", "数据", TrafficOpsSignalLightRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsSignalLightRespVO.class));
    }

}