package com.holdtime.ops.module.web.controller.admin;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsQrcodeMapper;
import com.holdtime.ops.module.entity.mapper.ViewOpsTempReportMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.dto.ExcelImportTempReportDTO;
import com.holdtime.ops.module.service.ExcelImportExtService;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
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
import com.holdtime.ops.module.entity.ViewOpsTempReportDO;
import com.holdtime.ops.module.service.ViewOpsTempReportService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - VIEW")
@RestController
@RequestMapping("/traffic/view-ops-temp-report")
@Validated
public class ViewOpsTempReportController {

    @Resource
    private ViewOpsTempReportService viewOpsTempReportService;
    @Resource
    private ViewOpsTempReportMapper viewOpsTempReportMapper;
    @Resource
    private TrafficOpsMaintenanceExtService opsMaintenanceExtService;
    @Resource
    private TrafficOpsQrcodeMapper trafficOpsQrcodeMapper;
    @Resource
    private ExcelImportExtService excelImportExtService;

    @GetMapping("/get")
    @Operation(summary = "获得VIEW")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ViewOpsTempReportRespVO> getViewOpsTempReport(@RequestParam("id") String id) {
        ViewOpsTempReportDO viewOpsTempReport = viewOpsTempReportService.getViewOpsTempReport(id);
        return success(BeanUtils.toBean(viewOpsTempReport, ViewOpsTempReportRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得VIEW分页")
    public CommonResult<PageResult<ViewOpsTempReportRespVO>> getViewOpsTempReportPage(@Valid ViewOpsTempReportPageReqVO pageReqVO) {
        return success(queryOpsTempReportPage(pageReqVO, OpsConstants.STRING_ONE));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出VIEW Excel")
    public void exportViewOpsTempReportExcel(@Valid ViewOpsTempReportPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        List<ViewOpsTempReportRespVO> list = queryOpsTempReportPage(pageReqVO, OpsConstants.STRING_ZERO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "报表"+ DateUtil.format(new Date(), "yyyyMMddHHmmss") + ".xls",
                "数据", ViewOpsTempReportRespVO.class, list);
    }

    @PostMapping("/importTempReportInfo")
    @Operation(summary = "导入电警监测信息")
    public CommonResult<ImportRecordRespVO> importTempReportInfo(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("电警报表", file, ExcelImportTempReportDTO.class));
    }

    private PageResult<ViewOpsTempReportRespVO> queryOpsTempReportPage(ViewOpsTempReportPageReqVO pageReqVO, String isPage) {
        LambdaQueryWrapperX<ViewOpsTempReportDO> opsTempReportDOLambdaQueryWrapperX = new LambdaQueryWrapperX<ViewOpsTempReportDO>()
                .eqIfPresent(ViewOpsTempReportDO::getEquId, pageReqVO.getEquId())
                .eqIfPresent(ViewOpsTempReportDO::getIpAddr, pageReqVO.getIpAddr())
                .eqIfPresent(ViewOpsTempReportDO::getSource, pageReqVO.getSource())
                .betweenIfPresent(ViewOpsTempReportDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(ViewOpsTempReportDO::getDeletedTime, pageReqVO.getDeletedTime())
                .eqIfPresent(ViewOpsTempReportDO::getEquNumber, pageReqVO.getEquNumber())
                .likeIfPresent(ViewOpsTempReportDO::getEquName, pageReqVO.getEquName())
                .eqIfPresent(ViewOpsTempReportDO::getEquType, pageReqVO.getEquType())
                .likeIfPresent(ViewOpsTempReportDO::getRoadName, pageReqVO.getRoadName())
                .likeIfPresent(ViewOpsTempReportDO::getIntersectionName, pageReqVO.getIntersectionName())
                .eqIfPresent(ViewOpsTempReportDO::getAdress, pageReqVO.getAdress())
                .betweenIfPresent(ViewOpsTempReportDO::getInstallDate, pageReqVO.getInstallDate())
                .eqIfPresent(ViewOpsTempReportDO::getManufacturer, pageReqVO.getManufacturer())
                .eqIfPresent(ViewOpsTempReportDO::getEquModel, pageReqVO.getEquModel())
                .eqIfPresent(ViewOpsTempReportDO::getUnit, pageReqVO.getUnit())
                .eqIfPresent(ViewOpsTempReportDO::getShootingRange, pageReqVO.getShootingRange())
                .eqIfPresent(ViewOpsTempReportDO::getLens, pageReqVO.getLens())
                .eqIfPresent(ViewOpsTempReportDO::getFocalLength, pageReqVO.getFocalLength())
                .eqIfPresent(ViewOpsTempReportDO::getAperture, pageReqVO.getAperture())
                .eqIfPresent(ViewOpsTempReportDO::getSoftwareVersion, pageReqVO.getSoftwareVersion())
                .eqIfPresent(ViewOpsTempReportDO::getInstallationMethod, pageReqVO.getInstallationMethod())
                .eqIfPresent(ViewOpsTempReportDO::getCommunicationModel, pageReqVO.getCommunicationModel())
                .eqIfPresent(ViewOpsTempReportDO::getProtocol, pageReqVO.getProtocol())
                .eqIfPresent(ViewOpsTempReportDO::getGateway, pageReqVO.getGateway())
                .eqIfPresent(ViewOpsTempReportDO::getGjxAddr, pageReqVO.getGjxAddr())
                .eqIfPresent(ViewOpsTempReportDO::getGjxLng, pageReqVO.getGjxLng())
                .eqIfPresent(ViewOpsTempReportDO::getGjxLat, pageReqVO.getGjxLat())
                .eqIfPresent(ViewOpsTempReportDO::getDirection, pageReqVO.getDirection())
                .eqIfPresent(ViewOpsTempReportDO::getDjType, pageReqVO.getDjType())
                .eqIfPresent(ViewOpsTempReportDO::getLwBh, pageReqVO.getLwBh())
                .eqIfPresent(ViewOpsTempReportDO::getJzBh, pageReqVO.getJzBh())
                .eqIfPresent(ViewOpsTempReportDO::getDominion, pageReqVO.getDominion())
                .eqIfPresent(ViewOpsTempReportDO::getStatus, pageReqVO.getStatus())
                .eqIfPresent(ViewOpsTempReportDO::getEquImg, pageReqVO.getEquImg())
//                .eqIfPresent(ViewOpsTempReportDO::getMemberBh, pageReqVO.getMemberBh())
                .eqIfPresent(ViewOpsTempReportDO::getIsCheck, pageReqVO.getIsCheck())
                .eqIfPresent(ViewOpsTempReportDO::getEquLable, pageReqVO.getEquLable())
                .likeIfPresent(ViewOpsTempReportDO::getUserName, pageReqVO.getUserName())
                .eqIfPresent(ViewOpsTempReportDO::getPassword, pageReqVO.getPassword())
                .eqIfPresent(ViewOpsTempReportDO::getAddressXh, pageReqVO.getAddressXh())
                .eqIfPresent(ViewOpsTempReportDO::getKkbh, pageReqVO.getKkbh())
                .eqIfPresent(ViewOpsTempReportDO::getSbbh, pageReqVO.getSbbh())
                .betweenIfPresent(ViewOpsTempReportDO::getMaintenanceExpiryDate, pageReqVO.getMaintenanceExpiryDate())
                .orderByDesc(ViewOpsTempReportDO::getId);

        if (OpsConstants.STRING_ZERO.equals(isPage)) {
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }

        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    opsTempReportDOLambdaQueryWrapperX.like(ViewOpsTempReportDO::getIntersectionName, roadName);
                }
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getMemberBh())) {
            if ("true".equals(pageReqVO.getMemberBh())) {
                opsTempReportDOLambdaQueryWrapperX.isNotNull(ViewOpsTempReportDO::getMemberBh);
            } else {
                opsTempReportDOLambdaQueryWrapperX.isNull(ViewOpsTempReportDO::getMemberBh);
            }
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            for (String equLabel : pageReqVO.getEquLableList()) {
                opsTempReportDOLambdaQueryWrapperX.like(ViewOpsTempReportDO::getEquLable, equLabel);
            }
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getUnitList())) {
            opsTempReportDOLambdaQueryWrapperX.and(wrapper -> {
                for (String unit : pageReqVO.getUnitList()) {
                    wrapper.or().eq(ViewOpsTempReportDO::getUnit, unit);
                }
            });
        }


        if (StringUtils.isNotBlank(pageReqVO.getSerialNumber())) {
            opsTempReportDOLambdaQueryWrapperX.exists("select 1 from traffic_ops_member m where exists (SELECT 1 from traffic_ops_qrcode q where  m.equ_number = q.id and q.affiliated_unit = '1' and lpad( q.serial_num, 3, '0' ) like '%" + pageReqVO.getSerialNumber() + "%') and view_ops_temp_report.member_bh = m.id");
        }

        PageResult<ViewOpsTempReportDO> pageResult = viewOpsTempReportMapper.selectPage(pageReqVO, opsTempReportDOLambdaQueryWrapperX);
        PageResult<ViewOpsTempReportRespVO> pageResultVO = BeanUtils.toBean(pageResult, ViewOpsTempReportRespVO.class);
        Optional.ofNullable(pageResultVO.getList()).orElse(new ArrayList<>()).forEach(opsTempReportRespVO -> {
            if (StringUtils.isNotBlank(opsTempReportRespVO.getMemberBh())) {
                LambdaQueryWrapperX<TrafficOpsQrcodeDO> qrcodeDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
                qrcodeDOLambdaQueryWrapperX.exists("SELECT 1 from traffic_ops_member m where traffic_ops_qrcode.id = m.equ_number and m.id = '" + opsTempReportRespVO.getMemberBh() + "'");
                qrcodeDOLambdaQueryWrapperX.eq(TrafficOpsQrcodeDO::getAffiliatedUnit, OpsConstants.STRING_ONE);
                List<TrafficOpsQrcodeDO> qrcodeDOList = trafficOpsQrcodeMapper.selectList(qrcodeDOLambdaQueryWrapperX);
                if (!CollectionUtils.isEmpty(qrcodeDOList)) {
                    opsTempReportRespVO.setQrcodeRespVO(BeanUtils.toBean(qrcodeDOList.get(0), TrafficOpsQrcodeRespVO.class));
                }
            }

            opsTempReportRespVO.setMaintenanceCnt(opsMaintenanceExtService.queryEquipMaintenanceCnt(opsTempReportRespVO.getId()));
        });
        return pageResultVO;
    }
}