package com.holdtime.ops.module.web.controller.admin;

import cn.hutool.core.io.IoUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.infra.controller.admin.file.vo.file.FileUploadReqVO;
import com.holdtime.ops.module.model.vo.FileExtRespVO;
import com.holdtime.ops.module.service.FileExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 文件存储")
@RestController
@RequestMapping("/public/file")
@Validated
@Slf4j
public class CustomFileController {

    @Resource
    private FileExtService fileExtService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "模式一：后端上传文件")
    public CommonResult<FileExtRespVO> uploadFile(FileUploadReqVO uploadReqVO, @RequestParam(required = false) String isCompress) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String path = uploadReqVO.getPath();
        return success(fileExtService.uploadFile(file.getOriginalFilename(), path, IoUtil.readBytes(file.getInputStream()), isCompress));
    }

}
