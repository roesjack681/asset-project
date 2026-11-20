package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.TrafficOpsSignalLightDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalLightMapper;
import com.holdtime.ops.module.model.vo.TrafficOpsSignalLightPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsSignalLightRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsSignalLightSaveReqVO;
import com.holdtime.ops.module.service.TrafficOpsSignalLightService;
import com.holdtime.ops.module.utils.HanLPUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 信号灯")
@RestController
@RequestMapping("/traffic/ops-signal-light")
@Validated
public class AppTrafficOpsSignalLightController {

    @Resource
    private TrafficOpsSignalLightService opsSignalLightService;
    @Resource
    private TrafficOpsSignalLightMapper opsSignalLightMapper;

    @PostMapping("/create")
    @Operation(summary = "创建信号灯")
    public CommonResult<String> createOpsSignalLight(@Valid @RequestBody TrafficOpsSignalLightSaveReqVO createReqVO) {
        return success(opsSignalLightService.createOpsSignalLight(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新信号灯")
    public CommonResult<Boolean> updateOpsSignalLight(@Valid @RequestBody TrafficOpsSignalLightSaveReqVO updateReqVO) {
        opsSignalLightService.updateOpsSignalLight(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除信号灯")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsSignalLight(@RequestParam("id") String id) {
        opsSignalLightService.deleteOpsSignalLight(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得信号灯")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsSignalLightRespVO> getOpsSignalLight(@RequestParam("id") String id) {
        TrafficOpsSignalLightDO opsSignalLight = opsSignalLightService.getOpsSignalLight(id);
        return success(BeanUtils.toBean(opsSignalLight, TrafficOpsSignalLightRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得信号灯分页")
    public CommonResult<PageResult<TrafficOpsSignalLightRespVO>> getOpsSignalLightPage(@Valid TrafficOpsSignalLightPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsSignalLightDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
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
    public void exportOpsSignalLightExcel(@Valid TrafficOpsSignalLightPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsSignalLightDO> list = opsSignalLightService.getOpsSignalLightPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "信号灯.xls", "数据", TrafficOpsSignalLightRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsSignalLightRespVO.class));
    }

}