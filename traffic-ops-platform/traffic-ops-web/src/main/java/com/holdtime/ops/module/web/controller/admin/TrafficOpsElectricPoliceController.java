package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsQrcodeMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.service.TrafficEquipFaultImportExtService;
import com.holdtime.ops.module.service.TrafficOpsElectricPoliceExtService;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.service.TrafficOpsElectricPoliceService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 电警")
@RestController
@RequestMapping("/traffic/ops-electric-police")
@Validated
public class TrafficOpsElectricPoliceController {

    @Resource
    private TrafficOpsElectricPoliceService opsElectricPoliceService;
    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;
    @Resource
    private TrafficEquipFaultImportExtService equipFaultImportExtService;
    @Resource
    private TrafficOpsMemberMapper opsMemberMapper;
    @Resource
    private TrafficOpsMaintenanceExtService opsMaintenanceExtService;
    @Resource
    private TrafficOpsQrcodeMapper trafficOpsQrcodeMapper;
    @Resource
    private TrafficOpsElectricPoliceMapper trafficOpsElectricPoliceMapper;
    @Resource
    private TrafficOpsElectricPoliceExtService opsElectricPoliceExtService;

    @PostMapping("/create")
    @Operation(summary = "创建电警")
    @PreAuthorize("@ss.hasPermission('traffic:ops-electric-police:create')")
    public CommonResult<String> createOpsElectricPolice(@Valid @RequestBody TrafficOpsElectricPoliceSaveReqVO createReqVO) {
        return success(opsElectricPoliceExtService.saveOpsElectricPolice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新电警")
    @PreAuthorize("@ss.hasPermission('traffic:ops-electric-police:update')")
    public CommonResult<Boolean> updateOpsElectricPolice(@Valid @RequestBody TrafficOpsElectricPoliceSaveReqVO updateReqVO) {
        opsElectricPoliceExtService.saveOpsElectricPolice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除电警")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-electric-police:delete')")
    public CommonResult<Boolean> deleteOpsElectricPolice(@RequestParam("id") String id) {
        opsElectricPoliceService.deleteOpsElectricPolice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得电警")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-electric-police:query')")
    public CommonResult<TrafficOpsElectricPoliceRespVO> getOpsElectricPolice(@RequestParam("id") String id) {
        TrafficOpsElectricPoliceDO opsElectricPolice = opsElectricPoliceService.getOpsElectricPolice(id);
        return success(BeanUtils.toBean(opsElectricPolice, TrafficOpsElectricPoliceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得电警分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-electric-police:query')")
    public CommonResult<PageResult<TrafficOpsElectricPoliceRespVO>> getOpsElectricPolicePage(@Valid TrafficOpsElectricPolicePageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> opsDOLambdaQueryWrapperX = (LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>) new LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>()
                .eqIfPresent(TrafficOpsElectricPoliceDO::getEquNumber, pageReqVO.getEquNumber())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getEquName, pageReqVO.getEquName())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getRoadName, pageReqVO.getRoadName())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getEquType, pageReqVO.getEquType())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getSoftwareVersion, pageReqVO.getSoftwareVersion())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getDirection, pageReqVO.getDirection())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getIntersectionName, pageReqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getDjType, pageReqVO.getDjType())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLng, pageReqVO.getLng())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLwBh, pageReqVO.getLwBh())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLat, pageReqVO.getLat())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getJzBh, pageReqVO.getJzBh())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getAdress, pageReqVO.getAdress())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getInstallDate, pageReqVO.getInstallDate())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getManufacturer, pageReqVO.getManufacturer())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getCreateTime, pageReqVO.getCreateTime())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getEquModel, pageReqVO.getEquModel())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getUnit, pageReqVO.getUnit())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getShootingRange, pageReqVO.getShootingRange())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getDeletedTime, pageReqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLens, pageReqVO.getLens())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getFocalLength, pageReqVO.getFocalLength())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getAperture, pageReqVO.getAperture())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getInstallationMethod, pageReqVO.getInstallationMethod())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getCommunicationModel, pageReqVO.getCommunicationModel())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getProtocol, pageReqVO.getProtocol())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getIpAddr, pageReqVO.getIpAddr())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGateway, pageReqVO.getGateway())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGjxAddr, pageReqVO.getGjxAddr())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGjxLng, pageReqVO.getGjxLng())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGjxLat, pageReqVO.getGjxLat())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getDominion, pageReqVO.getDominion())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getStatus, pageReqVO.getStatus())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getUnderWarranty, pageReqVO.getUnderWarranty())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getSbbh, pageReqVO.getSbbh())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getShotDirection, pageReqVO.getShotDirection())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getXjType, pageReqVO.getXjType())
                .orderByAsc(TrafficOpsElectricPoliceDO::getId);
        if(!StringUtil.isEmpty(isFy)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    opsDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getIntersectionName, roadName);
                }
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getMemberBh())) {
            if ("true".equals(pageReqVO.getMemberBh())) {
                opsDOLambdaQueryWrapperX.isNotNull(TrafficOpsElectricPoliceDO::getMemberBh);
            } else {
                opsDOLambdaQueryWrapperX.isNull(TrafficOpsElectricPoliceDO::getMemberBh);
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getKkbh())) {
            if ("true".equals(pageReqVO.getKkbh())) {
                opsDOLambdaQueryWrapperX.isNotNull(TrafficOpsElectricPoliceDO::getKkbh);
            } else {
                opsDOLambdaQueryWrapperX.isNull(TrafficOpsElectricPoliceDO::getKkbh);
            }
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            for (String equLabel : pageReqVO.getEquLableList()) {
                opsDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getEquLable, equLabel);
            }
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getUnitList())) {
            opsDOLambdaQueryWrapperX.and(wrapper -> {
                for (String unit : pageReqVO.getUnitList()) {
                    wrapper.or().eq(TrafficOpsElectricPoliceDO::getUnit, unit);
                }
            });
        }


        if (StringUtils.isNotBlank(pageReqVO.getSerialNumber())) {
            opsDOLambdaQueryWrapperX.exists("select 1 from traffic_ops_member m where exists (SELECT 1 from traffic_ops_qrcode q where  m.equ_number = q.id and q.affiliated_unit = '1' and lpad( q.serial_num, 3, '0' ) like '%" + pageReqVO.getSerialNumber() + "%') and traffic_ops_electric_police.member_bh = m.id");
        }

        PageResult<TrafficOpsElectricPoliceDO> pageResult = opsElectricPoliceMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);
        PageResult<TrafficOpsElectricPoliceRespVO> pageResultVO = BeanUtils.toBean(pageResult, TrafficOpsElectricPoliceRespVO.class);
        Optional.ofNullable(pageResultVO.getList()).orElse(new ArrayList<>()).forEach(opsElectricPoliceRespVO -> {
            if (StringUtils.isNotBlank(opsElectricPoliceRespVO.getMemberBh())) {
                TrafficOpsMemberDO memberDO = opsMemberMapper.selectById(opsElectricPoliceRespVO.getMemberBh());
                if (memberDO != null) {
                    opsElectricPoliceRespVO.setPowerSupplyLocation(memberDO.getPowerSupplyLocation());
                }

                LambdaQueryWrapperX<TrafficOpsQrcodeDO> qrcodeDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
                qrcodeDOLambdaQueryWrapperX.exists("SELECT 1 from traffic_ops_member m where traffic_ops_qrcode.id = m.equ_number and m.id = '" + opsElectricPoliceRespVO.getMemberBh() + "'");
                qrcodeDOLambdaQueryWrapperX.eq(TrafficOpsQrcodeDO::getAffiliatedUnit, OpsConstants.STRING_ONE);
                List<TrafficOpsQrcodeDO> qrcodeDOList = trafficOpsQrcodeMapper.selectList(qrcodeDOLambdaQueryWrapperX);
                if (!CollectionUtils.isEmpty(qrcodeDOList)) {
                    opsElectricPoliceRespVO.setQrcodeRespVO(BeanUtils.toBean(qrcodeDOList.get(0), TrafficOpsQrcodeRespVO.class));
                }
            }

            opsElectricPoliceRespVO.setMaintenanceCnt(opsMaintenanceExtService.queryEquipMaintenanceCnt(opsElectricPoliceRespVO.getId()));
        });
        return success(pageResultVO);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出电警 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-electric-police:export')")
    public void exportOpsElectricPoliceExcel(@Valid TrafficOpsElectricPolicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> opsDOLambdaQueryWrapperX = (LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>) new LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>()
                .eqIfPresent(TrafficOpsElectricPoliceDO::getEquNumber, pageReqVO.getEquNumber())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getEquName, pageReqVO.getEquName())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getEquType, pageReqVO.getEquType())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getSoftwareVersion, pageReqVO.getSoftwareVersion())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getDirection, pageReqVO.getDirection())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getIntersectionName, pageReqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getDjType, pageReqVO.getDjType())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLng, pageReqVO.getLng())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLwBh, pageReqVO.getLwBh())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLat, pageReqVO.getLat())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getJzBh, pageReqVO.getJzBh())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getAdress, pageReqVO.getAdress())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getInstallDate, pageReqVO.getInstallDate())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getManufacturer, pageReqVO.getManufacturer())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getCreateTime, pageReqVO.getCreateTime())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getEquModel, pageReqVO.getEquModel())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getUnit, pageReqVO.getUnit())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getShootingRange, pageReqVO.getShootingRange())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getDeletedTime, pageReqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLens, pageReqVO.getLens())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getFocalLength, pageReqVO.getFocalLength())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getAperture, pageReqVO.getAperture())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getInstallationMethod, pageReqVO.getInstallationMethod())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getCommunicationModel, pageReqVO.getCommunicationModel())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getProtocol, pageReqVO.getProtocol())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getIpAddr, pageReqVO.getIpAddr())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGateway, pageReqVO.getGateway())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGjxAddr, pageReqVO.getGjxAddr())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGjxLng, pageReqVO.getGjxLng())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGjxLat, pageReqVO.getGjxLat())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getDominion, pageReqVO.getDominion())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getStatus, pageReqVO.getStatus())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getSbbh, pageReqVO.getSbbh())
                .orderByAsc(TrafficOpsElectricPoliceDO::getId);

        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    opsDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getIntersectionName, roadName);
                }
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getMemberBh())) {
            if ("true".equals(pageReqVO.getMemberBh())) {
                opsDOLambdaQueryWrapperX.isNotNull(TrafficOpsElectricPoliceDO::getMemberBh);
            } else {
                opsDOLambdaQueryWrapperX.isNull(TrafficOpsElectricPoliceDO::getMemberBh);
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getKkbh())) {
            if ("true".equals(pageReqVO.getKkbh())) {
                opsDOLambdaQueryWrapperX.isNotNull(TrafficOpsElectricPoliceDO::getKkbh);
            } else {
                opsDOLambdaQueryWrapperX.isNull(TrafficOpsElectricPoliceDO::getKkbh);
            }
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            for (String equLabel : pageReqVO.getEquLableList()) {
                opsDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getEquLable, equLabel);
            }
        }

        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<TrafficOpsElectricPoliceDO> pageResult = opsElectricPoliceMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);

        // 导出 Excel
        ExcelUtils.write(response, "电警.xls", "数据", TrafficOpsElectricPoliceRespVO.class,
                        BeanUtils.toBean(pageResult.getList(), TrafficOpsElectricPoliceRespVO.class));
    }


    @PostMapping("/importFaultInfo")
    @Operation(summary = "导入电警故障信息")
    public CommonResult<ImportRecordRespVO> importFilingsExcel(@RequestParam("file") MultipartFile file) {
        return success(equipFaultImportExtService.importEquipFaultInfo(file));
    }

    @GetMapping("/autocompleteTrafficOpsElectricPoliceList")
    @Operation(summary = "根据设备名称自动补全查询电警信息")
    public CommonResult<List<TrafficOpsElectricPoliceRespVO>> autocompleteTrafficOpsElectricPoliceList(@RequestParam("ipAddr") String ipAddr) {
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getIpAddr, ipAddr);

        Page<TrafficOpsElectricPoliceDO> page = new Page<>(1, 20);
        List<TrafficOpsElectricPoliceDO> list = trafficOpsElectricPoliceMapper.selectList(page, lambdaQueryWrapperX);
        return success(BeanUtils.toBean(list, TrafficOpsElectricPoliceRespVO.class));
    }

}