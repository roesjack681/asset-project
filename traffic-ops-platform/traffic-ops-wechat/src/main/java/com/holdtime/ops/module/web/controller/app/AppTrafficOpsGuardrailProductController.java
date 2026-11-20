package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailProductDO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailProductPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailProductRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailProductSaveReqVO;
import com.holdtime.ops.module.service.TrafficOpsGuardrailProductService;
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

@Tag(name = "管理后台 - 护栏产品信息")
@RestController
@RequestMapping("/traffic/ops-guardrail-product")
@Validated
public class AppTrafficOpsGuardrailProductController {

    @Resource
    private TrafficOpsGuardrailProductService opsGuardrailProductService;


    @GetMapping("/get")
    @Operation(summary = "获得护栏产品信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsGuardrailProductRespVO> getOpsGuardrailProduct(@RequestParam("id") String id) {
        TrafficOpsGuardrailProductDO opsGuardrailProduct = opsGuardrailProductService.getOpsGuardrailProduct(id);
        return success(BeanUtils.toBean(opsGuardrailProduct, TrafficOpsGuardrailProductRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得护栏产品信息分页")
    public CommonResult<PageResult<TrafficOpsGuardrailProductRespVO>> getOpsGuardrailProductPage(@Valid TrafficOpsGuardrailProductPageReqVO pageReqVO) {
        PageResult<TrafficOpsGuardrailProductDO> pageResult = opsGuardrailProductService.getOpsGuardrailProductPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsGuardrailProductRespVO.class));
    }

}