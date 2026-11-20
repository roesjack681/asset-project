package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsElectricPoliceService;
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

@Tag(name = "用户 APP - 电警")
@RestController
@RequestMapping("/traffic/ops-electric-police")
@Validated
public class AppTrafficOpsElectricPoliceController {

    @Resource
    private TrafficOpsElectricPoliceService opsElectricPoliceService;
    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;
    @Resource
    private TrafficOpsMemberMapper opsMemberMapper;

    @PostMapping("/create")
    @Operation(summary = "创建电警")
    public CommonResult<String> createOpsElectricPolice(@Valid @RequestBody TrafficOpsElectricPoliceSaveReqVO createReqVO) {
        return success(opsElectricPoliceService.createOpsElectricPolice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新电警")
    public CommonResult<Boolean> updateOpsElectricPolice(@Valid @RequestBody TrafficOpsElectricPoliceSaveReqVO updateReqVO) {
        opsElectricPoliceService.updateOpsElectricPolice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除电警")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsElectricPolice(@RequestParam("id") String id) {
        opsElectricPoliceService.deleteOpsElectricPolice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得电警")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsElectricPoliceRespVO> getOpsElectricPolice(@RequestParam("id") String id) {
        TrafficOpsElectricPoliceDO opsElectricPolice = opsElectricPoliceService.getOpsElectricPolice(id);
        return success(BeanUtils.toBean(opsElectricPolice, TrafficOpsElectricPoliceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得电警分页")
    public CommonResult<PageResult<TrafficOpsElectricPoliceRespVO>> getOpsElectricPolicePage(@Valid TrafficOpsElectricPolicePageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    opsDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getIntersectionName, roadName);
                }
            }
        }
        opsDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsElectricPoliceDO::getIpAddr, pageReqVO.getIpAddr());
        PageResult<TrafficOpsElectricPoliceRespVO> pageResult = BeanUtils.toBean(opsElectricPoliceMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX), TrafficOpsElectricPoliceRespVO.class);

        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(opsElectricPoliceRespVO -> {
            if (StringUtils.isNotBlank(opsElectricPoliceRespVO.getMemberBh())) {
                TrafficOpsMemberDO memberDO = opsMemberMapper.selectById(opsElectricPoliceRespVO.getMemberBh());
                if (memberDO != null) {
                    opsElectricPoliceRespVO.setPowerSupplyLocation(memberDO.getPowerSupplyLocation());
                }
            }
        });
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出电警 Excel")
    public void exportOpsElectricPoliceExcel(@Valid TrafficOpsElectricPolicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsElectricPoliceDO> list = opsElectricPoliceService.getOpsElectricPolicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "电警.xls", "数据", TrafficOpsElectricPoliceRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsElectricPoliceRespVO.class));
    }

    @GetMapping("/getLocation")
    @Operation(summary = "获得电警位置")
    public void getOpsSignalLocation() {
        TrafficOpsElectricPolicePageReqVO pageReqVO = new TrafficOpsElectricPolicePageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<TrafficOpsElectricPoliceDO> pageResult = opsElectricPoliceService.getOpsElectricPolicePage(pageReqVO);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(electricPolice -> {
            if(StringUtils.isNotEmpty(electricPolice.getIntersectionName()) && StringUtils.isEmpty(electricPolice.getLat())){
                GeoCodeInfoVO geoCodeInfoVO = GisUtils.getGeoInfoByAddress("包头市"+ electricPolice.getIntersectionName().replace("-","/"));
                if(geoCodeInfoVO != null && StringUtils.isNotEmpty(geoCodeInfoVO.getLocation())){
                    String[] locations = geoCodeInfoVO.getLocation().split(",");
                    TrafficOpsElectricPoliceSaveReqVO updateReqVO = new TrafficOpsElectricPoliceSaveReqVO();
                    updateReqVO.setId(electricPolice.getId());
                    updateReqVO.setLng(locations[0]);
                    updateReqVO.setLat(locations[1]);
                    opsElectricPoliceService.updateOpsElectricPolice(updateReqVO);
                }
            }
        });
    }

}