package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailDO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailExtSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailRespVO;
import com.holdtime.ops.module.service.TrafficOpsGuardrailExtService;
import com.holdtime.ops.module.service.TrafficOpsGuardrailService;
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

@Tag(name = "管理后台 - 护栏")
@RestController
@RequestMapping("/traffic/ops-guardrail")
@Validated
public class AppTrafficOpsGuardrailController {

    @Resource
    private TrafficOpsGuardrailService opsGuardrailService;
    @Resource
    private TrafficOpsGuardrailExtService opsGuardrailExtService;

    @PostMapping("/create")
    @Operation(summary = "创建护栏")
    public CommonResult<String> createOpsGuardrail(@Valid @RequestBody TrafficOpsGuardrailExtSaveReqVO createReqVO) {
        return success(opsGuardrailExtService.saveOpsGuardrail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护栏")
    public CommonResult<Boolean> updateOpsGuardrail(@Valid @RequestBody TrafficOpsGuardrailExtSaveReqVO updateReqVO) {
        opsGuardrailExtService.saveOpsGuardrail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护栏")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsGuardrail(@RequestParam("id") String id) {
        opsGuardrailService.deleteOpsGuardrail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得护栏")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsGuardrailExtRespVO> getOpsGuardrail(@RequestParam("id") String id) {
        return success(opsGuardrailExtService.getOpsGuardrail(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得护栏分页")
    public CommonResult<PageResult<TrafficOpsGuardrailRespVO>> getOpsGuardrailPage(@Valid TrafficOpsGuardrailPageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        if(!StringUtil.isEmpty(isFy)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
        PageResult<TrafficOpsGuardrailDO> pageResult = opsGuardrailService.getOpsGuardrailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsGuardrailRespVO.class));
    }


}