package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.TrafficOpsBigledDO;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsBigledMapper;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsBigledService;
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

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 大屏")
@RestController
@RequestMapping("/traffic/ops-bigled")
@Validated
public class AppTrafficOpsBigledController {

    @Resource
    private TrafficOpsBigledService opsBigledService;
    @Resource
    private TrafficOpsBigledMapper opsBigledMapper;

    @PostMapping("/create")
    @Operation(summary = "创建大屏")
    public CommonResult<String> createOpsBigled(@Valid @RequestBody TrafficOpsBigledSaveReqVO createReqVO) {
        return success(opsBigledService.createOpsBigled(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新大屏")
    public CommonResult<Boolean> updateOpsBigled(@Valid @RequestBody TrafficOpsBigledSaveReqVO updateReqVO) {
        opsBigledService.updateOpsBigled(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除大屏")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsBigled(@RequestParam("id") String id) {
        opsBigledService.deleteOpsBigled(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得大屏")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsBigledRespVO> getOpsBigled(@RequestParam("id") String id) {
        TrafficOpsBigledDO opsBigled = opsBigledService.getOpsBigled(id);
        return success(BeanUtils.toBean(opsBigled, TrafficOpsBigledRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得大屏分页")
    public CommonResult<PageResult<TrafficOpsBigledRespVO>> getOpsBigledPage(@Valid TrafficOpsBigledPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsBigledDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    opsDOLambdaQueryWrapperX.like(TrafficOpsBigledDO::getIntersectionName, roadName);
                }
            }
        }
        PageResult<TrafficOpsBigledDO> pageResult = opsBigledMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);
        return success(BeanUtils.toBean(pageResult, TrafficOpsBigledRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出大屏 Excel")
    public void exportOpsBigledExcel(@Valid TrafficOpsBigledPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsBigledDO> list = opsBigledService.getOpsBigledPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "大屏.xls", "数据", TrafficOpsBigledRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsBigledRespVO.class));
    }

    @GetMapping("/getLocation")
    @Operation(summary = "获得大屏位置")
    public void getOpsSignalLocation() {
        TrafficOpsBigledPageReqVO pageReqVO = new TrafficOpsBigledPageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<TrafficOpsBigledDO> pageResult = opsBigledService.getOpsBigledPage(pageReqVO);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(bigled -> {
            if(StringUtils.isNotEmpty(bigled.getAdress()) && StringUtils.isEmpty(bigled.getLat())){
                GeoCodeInfoVO geoCodeInfoVO = GisUtils.getGeoInfoByAddress("包头市"+ bigled.getAdress());
                if(geoCodeInfoVO != null && StringUtils.isNotEmpty(geoCodeInfoVO.getLocation())){
                    String[] locations = geoCodeInfoVO.getLocation().split(",");
                    TrafficOpsBigledSaveReqVO updateReqVO = new TrafficOpsBigledSaveReqVO();
                    updateReqVO.setId(bigled.getId());
                    updateReqVO.setLng(locations[0]);
                    updateReqVO.setLat(locations[1]);
                    opsBigledService.updateOpsBigled(updateReqVO);
                }
            }
        });
    }

}