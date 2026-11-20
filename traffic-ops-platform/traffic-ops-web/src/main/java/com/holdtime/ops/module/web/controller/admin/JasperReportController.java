package com.holdtime.ops.module.web.controller.admin;

import cn.hutool.core.lang.UUID;
import com.holdtime.ops.module.service.JasperReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "管理后台 - 导出报表")
@RestController
@RequestMapping("/traffic/report")
@Slf4j
public class JasperReportController {

    @Resource
    private JasperReportService jasperReportService;

    @GetMapping("/generateOpsDetailReport")
    @Operation(summary = "生成资产台账")
    public ResponseEntity<byte[]> getOpsMarkline(@RequestParam("dominion") String dominion, @RequestParam("equStatus") String equStatus, @RequestParam("zcType") String zcType) {
        byte[] bytes = jasperReportService.generateOpsDetailReport(dominion, equStatus,zcType);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UUID.randomUUID() + ".pdf\"");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}