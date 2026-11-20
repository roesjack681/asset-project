package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsSignalService;
import com.holdtime.ops.module.utils.GisUtils;
import com.holdtime.ops.module.utils.HanLPUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP  - 信号机")
@RestController
@RequestMapping("/traffic/ops-signal")
@Validated
public class AppTrafficOpsSignalController {

    @Resource
    private TrafficOpsSignalService opsSignalService;
    @Resource
    private TrafficOpsSignalMapper trafficOpsSignalMapper;

    @PostMapping("/create")
    @Operation(summary = "创建信号机")
    public CommonResult<String> createOpsSignal(@Valid @RequestBody TrafficOpsSignalSaveReqVO createReqVO) {
        return success(opsSignalService.createOpsSignal(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新信号机")
    public CommonResult<Boolean> updateOpsSignal(@Valid @RequestBody TrafficOpsSignalSaveReqVO updateReqVO) {
        opsSignalService.updateOpsSignal(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除信号机")
    @Parameter(name = "id", description = "编号", required = true)
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
    public CommonResult<PageResult<TrafficOpsSignalRespVO>> getOpsSignalPage(@Valid TrafficOpsSignalPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsSignalDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
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
    public void exportOpsSignalExcel(@Valid TrafficOpsSignalPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsSignalDO> list = opsSignalService.getOpsSignalPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "信号灯.xls", "数据", TrafficOpsSignalRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsSignalRespVO.class));
    }

    @GetMapping("/getLocation")
    @Operation(summary = "获得信号机位置")
    public void getOpsSignalLocation() {
        TrafficOpsSignalPageReqVO pageReqVO = new TrafficOpsSignalPageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<TrafficOpsSignalDO> pageResult = opsSignalService.getOpsSignalPage(pageReqVO);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(signal -> {
            if(StringUtils.isNotEmpty(signal.getIntersectionName())){
                GeoCodeInfoVO geoCodeInfoVO = GisUtils.getGeoInfoByAddress("包头市"+ signal.getIntersectionName().replace("-","/"));
                if(geoCodeInfoVO != null && StringUtils.isNotEmpty(geoCodeInfoVO.getLocation())){
                    String[] locations = geoCodeInfoVO.getLocation().split(",");
                    TrafficOpsSignalSaveReqVO updateReqVO = new TrafficOpsSignalSaveReqVO();
                    updateReqVO.setId(signal.getId());
                    updateReqVO.setLng(locations[0]);
                    updateReqVO.setLat(locations[1]);
                    opsSignalService.updateOpsSignal(updateReqVO);
                }
            }
        });
    }

}