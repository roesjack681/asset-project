package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsBigledMapper;
import com.holdtime.ops.module.utils.HanLPUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
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
import com.holdtime.ops.module.entity.TrafficOpsBigledDO;
import com.holdtime.ops.module.service.TrafficOpsBigledService;

@Tag(name = "管理后台 - 大屏")
@RestController
@RequestMapping("/traffic/ops-bigled")
@Validated
public class TrafficOpsBigledController {

    @Resource
    private TrafficOpsBigledService opsBigledService;
    @Resource
    private TrafficOpsBigledMapper opsBigledMapper;

    @PostMapping("/create")
    @Operation(summary = "创建大屏")
    @PreAuthorize("@ss.hasPermission('traffic:ops-bigled:create')")
    public CommonResult<String> createOpsBigled(@Valid @RequestBody TrafficOpsBigledSaveReqVO createReqVO) {
        return success(opsBigledService.createOpsBigled(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新大屏")
    @PreAuthorize("@ss.hasPermission('traffic:ops-bigled:update')")
    public CommonResult<Boolean> updateOpsBigled(@Valid @RequestBody TrafficOpsBigledSaveReqVO updateReqVO) {
        opsBigledService.updateOpsBigled(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除大屏")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-bigled:delete')")
    public CommonResult<Boolean> deleteOpsBigled(@RequestParam("id") String id) {
        opsBigledService.deleteOpsBigled(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得大屏")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-bigled:query')")
    public CommonResult<TrafficOpsBigledRespVO> getOpsBigled(@RequestParam("id") String id) {
        TrafficOpsBigledDO opsBigled = opsBigledService.getOpsBigled(id);
        return success(BeanUtils.toBean(opsBigled, TrafficOpsBigledRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得大屏分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-bigled:query')")
    public CommonResult<PageResult<TrafficOpsBigledRespVO>> getOpsBigledPage(@Valid TrafficOpsBigledPageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        LambdaQueryWrapperX<TrafficOpsBigledDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsBigledDO>()
                .eqIfPresent(TrafficOpsBigledDO::getEquNumber, pageReqVO.getEquNumber())
                .likeIfPresent(TrafficOpsBigledDO::getEquName, pageReqVO.getEquName())
                .eqIfPresent(TrafficOpsBigledDO::getEquType, pageReqVO.getEquType())
                .likeIfPresent(TrafficOpsBigledDO::getIntersectionName, pageReqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsBigledDO::getLng, pageReqVO.getLng())
                .eqIfPresent(TrafficOpsBigledDO::getLat, pageReqVO.getLat())
                .eqIfPresent(TrafficOpsBigledDO::getDes, pageReqVO.getDes())
                .eqIfPresent(TrafficOpsBigledDO::getAdress, pageReqVO.getAdress())
                .eqIfPresent(TrafficOpsBigledDO::getIpAddr, pageReqVO.getIpAddr())
                .eqIfPresent(TrafficOpsBigledDO::getScreenOrientation, pageReqVO.getScreenOrientation())
                .eqIfPresent(TrafficOpsBigledDO::getSubnet, pageReqVO.getSubnet())
                .eqIfPresent(TrafficOpsBigledDO::getGateway, pageReqVO.getGateway())
                .eqIfPresent(TrafficOpsBigledDO::getPtzIp, pageReqVO.getPtzIp())
                .likeIfPresent(TrafficOpsBigledDO::getUsername, pageReqVO.getUsername())
                .eqIfPresent(TrafficOpsBigledDO::getPasswd, pageReqVO.getPasswd())
                .eqIfPresent(TrafficOpsBigledDO::getViolationCode, pageReqVO.getViolationCode())
                .eqIfPresent(TrafficOpsBigledDO::getInducedEncoding, pageReqVO.getInducedEncoding())
                .eqIfPresent(TrafficOpsBigledDO::getDpbh, pageReqVO.getDpbh())
                .eqIfPresent(TrafficOpsBigledDO::getRemark, pageReqVO.getRemark())
                .eqIfPresent(TrafficOpsBigledDO::getSize, pageReqVO.getSize())
                .betweenIfPresent(TrafficOpsBigledDO::getInstallDate, pageReqVO.getInstallDate())
                .eqIfPresent(TrafficOpsBigledDO::getManufacturer, pageReqVO.getManufacturer())
                .eqIfPresent(TrafficOpsBigledDO::getUnit, pageReqVO.getUnit())
                .betweenIfPresent(TrafficOpsBigledDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsBigledDO::getDeletedTime, pageReqVO.getDeletedTime())
                .likeIfPresent(TrafficOpsBigledDO::getDominion, pageReqVO.getDominion())
                .eqIfPresent(TrafficOpsBigledDO::getStatus, pageReqVO.getStatus())
                .orderByDesc(TrafficOpsBigledDO::getId);
        if(!StringUtil.isEmpty(isFy)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
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
    @PreAuthorize("@ss.hasPermission('traffic:ops-bigled:export')")
    public void exportOpsBigledExcel(@Valid TrafficOpsBigledPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsBigledDO> list = opsBigledService.getOpsBigledPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "大屏.xls", "数据", TrafficOpsBigledRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsBigledRespVO.class));
    }

}