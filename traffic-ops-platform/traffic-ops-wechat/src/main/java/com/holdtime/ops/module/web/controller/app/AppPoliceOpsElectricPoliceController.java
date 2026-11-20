package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.PoliceOpsMemberDO;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.PoliceOpsMemberMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.PoliceOpsElectricPoliceService;
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
@RequestMapping("/police/ops-electric-police")
@Validated
public class AppPoliceOpsElectricPoliceController {

    @Resource
    private PoliceOpsElectricPoliceService opsElectricPoliceService;
    @Resource
    private PoliceOpsElectricPoliceMapper opsElectricPoliceMapper;
    @Resource
    private PoliceOpsMemberMapper opsMemberMapper;

    @PostMapping("/create")
    @Operation(summary = "创建电警")
    public CommonResult<String> createOpsElectricPolice(@Valid @RequestBody PoliceOpsElectricPoliceSaveReqVO createReqVO) {
        return success(opsElectricPoliceService.createOpsElectricPolice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新电警")
    public CommonResult<Boolean> updateOpsElectricPolice(@Valid @RequestBody PoliceOpsElectricPoliceSaveReqVO updateReqVO) {
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
    public CommonResult<PoliceOpsElectricPoliceRespVO> getOpsElectricPolice(@RequestParam("id") String id) {
        PoliceOpsElectricPoliceDO opsElectricPolice = opsElectricPoliceService.getOpsElectricPolice(id);
        return success(BeanUtils.toBean(opsElectricPolice, PoliceOpsElectricPoliceRespVO.class));
    }

//    @GetMapping("/page")
//    @Operation(summary = "获得电警分页")
//    public CommonResult<PageResult<PoliceOpsElectricPoliceRespVO>> getOpsElectricPolicePage(@Valid PoliceOpsElectricPolicePageReqVO pageReqVO) {
//        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
//        if(!StringUtil.isEmpty(pageReqVO.getEquName())){
//            List<String> stringList = HanLPUtils.segmentForRoad(pageReqVO.getEquName());
//            if (!CollectionUtils.isEmpty(stringList)) {
//                for (String str : stringList) {
//                    lambdaQueryWrapperX.like(PoliceOpsElectricPoliceDO::getEquName, str);
//                }
//            }
//        }
//        PageResult<PoliceOpsElectricPoliceDO> pageResult = opsElectricPoliceMapper.selectPage(pageReqVO,lambdaQueryWrapperX);
//        return success(BeanUtils.toBean(pageResult, PoliceOpsElectricPoliceRespVO.class));
//    }

    @GetMapping("/page")
    @Operation(summary = "获得电警分页")
    public CommonResult<PageResult<PoliceOpsElectricPoliceExtRespVO>> getOpsElectricPolicePage(@Valid PoliceOpsElectricPolicePageReqVO pageReqVO) {
        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<PoliceOpsElectricPoliceDO>()
                .likeIfPresent(PoliceOpsElectricPoliceDO::getIpAddr, pageReqVO.getIpAddr());

        if(!StringUtil.isEmpty(pageReqVO.getEquName())){
            List<String> stringList = HanLPUtils.segmentForRoad(pageReqVO.getEquName());
            if (!CollectionUtils.isEmpty(stringList)) {
                for (String str : stringList) {
                    lambdaQueryWrapperX.like(PoliceOpsElectricPoliceDO::getEquName, str);
                }
            }
        }

        lambdaQueryWrapperX.orderByDesc(PoliceOpsElectricPoliceDO::getMemberBh);
        lambdaQueryWrapperX.orderByAsc(PoliceOpsElectricPoliceDO::getId);

        PageResult<PoliceOpsElectricPoliceExtRespVO> pageResult = BeanUtils.toBean(opsElectricPoliceMapper.selectPage(pageReqVO, lambdaQueryWrapperX), PoliceOpsElectricPoliceExtRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(opsElectricPoliceExtRespVO -> {
            if (StringUtils.isNotBlank(opsElectricPoliceExtRespVO.getMemberBh())) {
                PoliceOpsMemberDO opsMember = opsMemberMapper.selectById(opsElectricPoliceExtRespVO.getMemberBh());
                if(opsMember != null ){
                    opsElectricPoliceExtRespVO.setPowerSupplyLocation(opsMember.getPowerSupplyLocation());
                }
            }
        });

        return success(pageResult);
    }


}