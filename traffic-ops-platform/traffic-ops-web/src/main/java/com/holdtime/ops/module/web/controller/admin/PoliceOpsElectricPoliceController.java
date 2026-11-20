package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.PoliceOpsMemberDO;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.PoliceOpsMemberMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsQrcodeMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.service.PoliceOpsElectricPoliceExtService;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.stream.Collectors;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;


import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.service.PoliceOpsElectricPoliceService;

@Tag(name = "管理后台 - 电警")
@RestController
@RequestMapping("/police/ops-electric-police")
@Validated
public class PoliceOpsElectricPoliceController {

    @Resource
    private PoliceOpsElectricPoliceService opsElectricPoliceService;
    @Resource
    private PoliceOpsElectricPoliceExtService policeOpsElectricPoliceExtService;
    @Resource
    private PoliceOpsElectricPoliceMapper opsElectricPoliceMapper;
    @Resource
    private TrafficOpsQrcodeMapper trafficOpsQrcodeMapper;
    @Resource
    private TrafficOpsMaintenanceExtService opsMaintenanceExtService;
    @Resource
    private PoliceOpsMemberMapper opsMemberMapper;

    @PostMapping("/create")
    @Operation(summary = "创建电警")
    @PreAuthorize("@ss.hasPermission('police:ops-electric-police:create')")
    public CommonResult<String> createOpsElectricPolice(@Valid @RequestBody PoliceOpsElectricPoliceSaveReqVO createReqVO) {
        return success(opsElectricPoliceService.createOpsElectricPolice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新电警")
    @PreAuthorize("@ss.hasPermission('police:ops-electric-police:update')")
    public CommonResult<Boolean> updateOpsElectricPolice(@Valid @RequestBody PoliceOpsElectricPoliceSaveReqVO updateReqVO) {
        opsElectricPoliceService.updateOpsElectricPolice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除电警")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:ops-electric-police:delete')")
    public CommonResult<Boolean> deleteOpsElectricPolice(@RequestParam("id") String id) {
        opsElectricPoliceService.deleteOpsElectricPolice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得电警")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:ops-electric-police:query')")
    public CommonResult<PoliceOpsElectricPoliceRespVO> getOpsElectricPolice(@RequestParam("id") String id) {
        PoliceOpsElectricPoliceDO opsElectricPolice = opsElectricPoliceService.getOpsElectricPolice(id);
        return success(BeanUtils.toBean(opsElectricPolice, PoliceOpsElectricPoliceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得电警分页")
    @PreAuthorize("@ss.hasPermission('police:ops-electric-police:query')")
    public CommonResult<PageResult<PoliceOpsElectricPoliceExtRespVO>> getOpsElectricPolicePage(@Valid PoliceOpsElectricPolicePageReqVO pageReqVO) {
        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<PoliceOpsElectricPoliceDO>()
                .likeIfPresent(PoliceOpsElectricPoliceDO::getIpAddr, pageReqVO.getIpAddr())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getDirection, pageReqVO.getDirection())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getDjType, pageReqVO.getDjType())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getStatus, pageReqVO.getStatus())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getUnderWarranty, pageReqVO.getUnderWarranty());

        if(!StringUtil.isEmpty(pageReqVO.getEquName())){
            List<String> stringList = HanLPUtils.segmentForRoad(pageReqVO.getEquName());
            if (!CollectionUtils.isEmpty(stringList)) {
                for (String str : stringList) {
                    lambdaQueryWrapperX.like(PoliceOpsElectricPoliceDO::getEquName, str);
                }
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getMemberBh())) {
            if ("true".equals(pageReqVO.getMemberBh())) {
                lambdaQueryWrapperX.isNotNull(PoliceOpsElectricPoliceDO::getMemberBh);
            } else {
                lambdaQueryWrapperX.isNull(PoliceOpsElectricPoliceDO::getMemberBh);
            }
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            for (String equLabel : pageReqVO.getEquLableList()) {
                lambdaQueryWrapperX.like(PoliceOpsElectricPoliceDO::getEquLable, equLabel);
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getSerialNumber())) {
            lambdaQueryWrapperX.exists("select 1 from police_ops_member m where exists (SELECT 1 from traffic_ops_qrcode q where  m.equ_number = q.id and q.affiliated_unit = '2' and CONCAT(q.serial_prefix, lpad( q.serial_num, 5, '0' )) like '%" + pageReqVO.getSerialNumber() + "%') and police_ops_electric_police.member_bh = m.id");
        }

        lambdaQueryWrapperX.orderByDesc(PoliceOpsElectricPoliceDO::getMemberBh);
        lambdaQueryWrapperX.orderByAsc(PoliceOpsElectricPoliceDO::getId);

        PageResult<PoliceOpsElectricPoliceExtRespVO> pageResult = BeanUtils.toBean(opsElectricPoliceMapper.selectPage(pageReqVO, lambdaQueryWrapperX), PoliceOpsElectricPoliceExtRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(opsElectricPoliceExtRespVO -> {
            if (StringUtils.isNotBlank(opsElectricPoliceExtRespVO.getMemberBh())) {
                LambdaQueryWrapperX<TrafficOpsQrcodeDO> qrcodeDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
                qrcodeDOLambdaQueryWrapperX.exists("SELECT 1 from police_ops_member m where traffic_ops_qrcode.id = m.equ_number and m.id = '" + opsElectricPoliceExtRespVO.getMemberBh() + "'");
                qrcodeDOLambdaQueryWrapperX.eq(TrafficOpsQrcodeDO::getAffiliatedUnit, OpsConstants.STRING_TWO);
                List<TrafficOpsQrcodeDO> qrcodeDOList = trafficOpsQrcodeMapper.selectList(qrcodeDOLambdaQueryWrapperX);
                if (!CollectionUtils.isEmpty(qrcodeDOList)) {
                    opsElectricPoliceExtRespVO.setQrcodeRespVO(BeanUtils.toBean(qrcodeDOList.get(0), TrafficOpsQrcodeRespVO.class));
                }

                PoliceOpsMemberDO opsMember = opsMemberMapper.selectById(opsElectricPoliceExtRespVO.getMemberBh());
                if(opsMember != null){
                    opsElectricPoliceExtRespVO.setPowerSupplyLocation(opsMember.getPowerSupplyLocation());
                }
            }

            opsElectricPoliceExtRespVO.setMaintenanceCnt(opsMaintenanceExtService.queryEquipMaintenanceCnt(opsElectricPoliceExtRespVO.getId()));
        });

        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出电警 Excel")
    @PreAuthorize("@ss.hasPermission('police:ops-electric-police:export')")
    public void exportOpsElectricPoliceExcel(@Valid PoliceOpsElectricPolicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);

        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<PoliceOpsElectricPoliceDO>()
                .likeIfPresent(PoliceOpsElectricPoliceDO::getIpAddr, pageReqVO.getIpAddr())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getDirection, pageReqVO.getDirection())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getDjType, pageReqVO.getDjType())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getStatus, pageReqVO.getStatus());

        if(!StringUtil.isEmpty(pageReqVO.getEquName())){
            List<String> stringList = HanLPUtils.segmentForRoad(pageReqVO.getEquName());
            if (!CollectionUtils.isEmpty(stringList)) {
                for (String str : stringList) {
                    lambdaQueryWrapperX.like(PoliceOpsElectricPoliceDO::getEquName, str);
                }
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getMemberBh())) {
            if ("true".equals(pageReqVO.getMemberBh())) {
                lambdaQueryWrapperX.isNotNull(PoliceOpsElectricPoliceDO::getMemberBh);
            } else {
                lambdaQueryWrapperX.isNull(PoliceOpsElectricPoliceDO::getMemberBh);
            }
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            for (String equLabel : pageReqVO.getEquLableList()) {
                lambdaQueryWrapperX.like(PoliceOpsElectricPoliceDO::getEquLable, equLabel);
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getSerialNumber())) {
            lambdaQueryWrapperX.exists("select 1 from police_ops_member m where exists (SELECT 1 from traffic_ops_qrcode q where  m.equ_number = q.id and q.affiliated_unit = '2' and CONCAT(q.serial_prefix, lpad( q.serial_num, 5, '0' )) like '%" + pageReqVO.getSerialNumber() + "%') and police_ops_electric_police.member_bh = m.id");
        }

        lambdaQueryWrapperX.orderByDesc(PoliceOpsElectricPoliceDO::getMemberBh);
        lambdaQueryWrapperX.orderByAsc(PoliceOpsElectricPoliceDO::getId);

        PageResult<PoliceOpsElectricPoliceExtRespVO> pageResult = BeanUtils.toBean(opsElectricPoliceMapper.selectPage(pageReqVO, lambdaQueryWrapperX), PoliceOpsElectricPoliceExtRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(opsElectricPoliceExtRespVO -> {
            if (StringUtils.isNotBlank(opsElectricPoliceExtRespVO.getMemberBh())) {
                LambdaQueryWrapperX<TrafficOpsQrcodeDO> qrcodeDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
                qrcodeDOLambdaQueryWrapperX.exists("SELECT 1 from police_ops_member m where traffic_ops_qrcode.id = m.equ_number and m.id = '" + opsElectricPoliceExtRespVO.getMemberBh() + "'");
                qrcodeDOLambdaQueryWrapperX.eq(TrafficOpsQrcodeDO::getAffiliatedUnit, OpsConstants.STRING_TWO);
                List<TrafficOpsQrcodeDO> qrcodeDOList = trafficOpsQrcodeMapper.selectList(qrcodeDOLambdaQueryWrapperX);
                if (!CollectionUtils.isEmpty(qrcodeDOList)) {
                    opsElectricPoliceExtRespVO.setQrcodeRespVO(BeanUtils.toBean(qrcodeDOList.get(0), TrafficOpsQrcodeRespVO.class));
                }
            }

            opsElectricPoliceExtRespVO.setMaintenanceCnt(opsMaintenanceExtService.queryEquipMaintenanceCnt(opsElectricPoliceExtRespVO.getId()));
        });

//        List<PoliceOpsElectricPoliceDO> list = opsElectricPoliceService.getOpsElectricPolicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "电警.xls", "数据", PoliceOpsElectricPoliceRespVO.class,
                        BeanUtils.toBean(pageResult.getList(), PoliceOpsElectricPoliceRespVO.class));
    }

    @GetMapping("/autocompletePoliceOpsElectricPoliceList")
    @Operation(summary = "根据设备名称自动补全查询电警信息")
    public CommonResult<List<PoliceOpsElectricPoliceRespVO>> autocompletePoliceOpsMemberList(@RequestParam("equName") String equName) {
        return success(policeOpsElectricPoliceExtService.autocompletePoliceOpsElectricPoliceList(equName));
    }

}