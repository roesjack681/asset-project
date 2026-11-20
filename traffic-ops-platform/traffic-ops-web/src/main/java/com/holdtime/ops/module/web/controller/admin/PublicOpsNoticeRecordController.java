package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.PublicOpsNoticeRecordExtService;
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

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsNoticeRecordDO;
import com.holdtime.ops.module.service.PublicOpsNoticeRecordService;

@Tag(name = "管理后台 - 消息通知记录")
@RestController
@RequestMapping("/public/ops-notice-record")
@Validated
public class PublicOpsNoticeRecordController {

    @Resource
    private PublicOpsNoticeRecordService opsNoticeRecordService;
    @Resource
    private PublicOpsNoticeRecordExtService opsNoticeRecordExtService;

    @PostMapping("/create")
    @Operation(summary = "创建消息通知记录")
    @PreAuthorize("@ss.hasPermission('public:ops-notice-record:create')")
    public CommonResult<String> createOpsNoticeRecord(@Valid @RequestBody PublicOpsNoticeRecordSaveReqVO createReqVO) {
        return success(opsNoticeRecordService.createOpsNoticeRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新消息通知记录")
    @PreAuthorize("@ss.hasPermission('public:ops-notice-record:update')")
    public CommonResult<Boolean> updateOpsNoticeRecord(@Valid @RequestBody PublicOpsNoticeRecordSaveReqVO updateReqVO) {
        opsNoticeRecordService.updateOpsNoticeRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除消息通知记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('public:ops-notice-record:delete')")
    public CommonResult<Boolean> deleteOpsNoticeRecord(@RequestParam("id") String id) {
        opsNoticeRecordService.deleteOpsNoticeRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得消息通知记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('public:ops-notice-record:query')")
    public CommonResult<PublicOpsNoticeRecordRespVO> getOpsNoticeRecord(@RequestParam("id") String id) {
        PublicOpsNoticeRecordDO opsNoticeRecord = opsNoticeRecordService.getOpsNoticeRecord(id);
        return success(BeanUtils.toBean(opsNoticeRecord, PublicOpsNoticeRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得消息通知记录分页")
    @PreAuthorize("@ss.hasPermission('public:ops-notice-record:query')")
    public CommonResult<PageResult<PublicOpsNoticeRecordRespVO>> getOpsNoticeRecordPage(@Valid PublicOpsNoticeRecordPageReqVO pageReqVO) {
        PageResult<PublicOpsNoticeRecordDO> pageResult = opsNoticeRecordService.getOpsNoticeRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicOpsNoticeRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出消息通知记录 Excel")
    @PreAuthorize("@ss.hasPermission('public:ops-notice-record:export')")
    public void exportOpsNoticeRecordExcel(@Valid PublicOpsNoticeRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicOpsNoticeRecordDO> list = opsNoticeRecordService.getOpsNoticeRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "消息通知记录.xls", "数据", PublicOpsNoticeRecordRespVO.class,
                        BeanUtils.toBean(list, PublicOpsNoticeRecordRespVO.class));
    }

    /**
     * 发送短信通知
     * @param opsNoticeMsgVO 发送短信通知
     * @return 报修ID
     */
    @PostMapping("/sendNoticeMsg")
    @Operation(summary = "发送短信通知")
    public CommonResult<?> sendNoticeMsg(@Valid @RequestBody OpsNoticeMsgVO opsNoticeMsgVO) {
        opsNoticeRecordExtService.sendNoticeMsg(opsNoticeMsgVO);
        return success(true);
    }

}