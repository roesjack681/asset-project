package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.holdtime.ops.module.entity.HaPsFeedbackDO;
import com.holdtime.ops.module.model.vo.HaPsFeedbackPageReqVO;
import com.holdtime.ops.module.model.vo.HaPsFeedbackRespVO;
import com.holdtime.ops.module.model.vo.HaPsFeedbackSaveReqVO;
import com.holdtime.ops.module.service.HaPsFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 方案展示留言记录")
@RestController
@RequestMapping("/traffic/ha-ps-feedback")
@Validated
public class AppHaPsFeedbackController {

    @Resource
    private HaPsFeedbackService haPsFeedbackService;

    @PostMapping("/create")
    @Operation(summary = "创建方案展示留言记录")
    public CommonResult<String> createHaPsFeedback(@Valid @RequestBody HaPsFeedbackSaveReqVO createReqVO) {
        return success(haPsFeedbackService.createHaPsFeedback(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新方案展示留言记录")
    public CommonResult<Boolean> updateHaPsFeedback(@Valid @RequestBody HaPsFeedbackSaveReqVO updateReqVO) {
        haPsFeedbackService.updateHaPsFeedback(updateReqVO);
        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得方案展示留言记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ha-ps-feedback:query')")
    public CommonResult<HaPsFeedbackRespVO> getHaPsFeedback(@RequestParam("id") String id) {
        HaPsFeedbackDO haPsFeedback = haPsFeedbackService.getHaPsFeedback(id);
        return success(BeanUtils.toBean(haPsFeedback, HaPsFeedbackRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得方案展示留言记录分页")
    @PreAuthorize("@ss.hasPermission('traffic:ha-ps-feedback:query')")
    public CommonResult<PageResult<HaPsFeedbackRespVO>> getHaPsFeedbackPage(@Valid HaPsFeedbackPageReqVO pageReqVO) {
        PageResult<HaPsFeedbackDO> pageResult = haPsFeedbackService.getHaPsFeedbackPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HaPsFeedbackRespVO.class));
    }

}