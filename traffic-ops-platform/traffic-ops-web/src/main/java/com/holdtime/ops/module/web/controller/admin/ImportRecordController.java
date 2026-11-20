package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.holdtime.ops.module.entity.ImportRecordDO;
import com.holdtime.ops.module.model.vo.ImportRecordPageReqVO;
import com.holdtime.ops.module.model.vo.ImportRecordRespVO;
import com.holdtime.ops.module.model.vo.ImportRecordSaveReqVO;
import com.holdtime.ops.module.service.ImportRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - Excel导入记录")
@RestController
@RequestMapping("/traffic/import-record")
@Validated
public class ImportRecordController {

    @Resource
    private ImportRecordService importRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建Excel导入记录")
    public CommonResult<String> createImportRecord(@Valid @RequestBody ImportRecordSaveReqVO createReqVO) {
        return success(importRecordService.createImportRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新Excel导入记录")
    public CommonResult<Boolean> updateImportRecord(@Valid @RequestBody ImportRecordSaveReqVO updateReqVO) {
        importRecordService.updateImportRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除Excel导入记录")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteImportRecord(@RequestParam("id") String id) {
        importRecordService.deleteImportRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得Excel导入记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ImportRecordRespVO> getImportRecord(@RequestParam("id") String id) {
        ImportRecordDO importRecord = importRecordService.getImportRecord(id);
        return success(BeanUtils.toBean(importRecord, ImportRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得Excel导入记录分页")
    public CommonResult<PageResult<ImportRecordRespVO>> getImportRecordPage(@Valid ImportRecordPageReqVO pageReqVO) {
        PageResult<ImportRecordDO> pageResult = importRecordService.getImportRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ImportRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出Excel导入记录 Excel")
    public void exportImportRecordExcel(@Valid ImportRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ImportRecordDO> list = importRecordService.getImportRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "Excel导入记录.xls", "数据", ImportRecordRespVO.class,
                        BeanUtils.toBean(list, ImportRecordRespVO.class));
    }

}