package com.holdtime.ops.module.web.controller.app;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.kafka.producer.BoundaryMsgProducer;
import com.holdtime.ops.module.service.JasperReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;



@Tag(name = "报表测试")
@RestController
@RequestMapping("/test/report")
@Validated
public class TestReportController {
    @Resource
    private BoundaryMsgProducer boundaryMsgProducer;

    @Resource
    private JasperReportService jasperReportService;


    @GetMapping("/generateOpsDetailReport")
    @Operation(summary = "生成资产台账")
    public ResponseEntity<byte[]> getOpsMarkline(@RequestParam(value = "dominion", required = false) String dominion, @RequestParam(value = "equStatus", required = false) String equStatus,@RequestParam(value = "zcType") String zcType) {
        byte[] bytes = jasperReportService.generateOpsDetailReport(dominion, equStatus,zcType
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UUID.randomUUID() + ".pdf\"");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/testReport")
    @Operation(summary = "测试报表")
    public CommonResult<String> testReport() {
        boundaryMsgProducer.sendMessageFromInternetToVideo("test", "测试", "{\"data\":\"测试\"}");
        return CommonResult.success("");
    }

//    @PostMapping("/importGaEquipInfo")
//    @Operation(summary = "导入报修信息")
//    public CommonResult<ImportRecordRespVO> importMaintenanceInfo(@RequestParam("file") MultipartFile file) {
//        return success(excelImportExtService.importEquipFaultInfo(file, ExcelImportExcelPaBtDTO.class));
//    }
}
