package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.TrafficOpsRoadDO;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsRoadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 道路信息")
@RestController
@RequestMapping("/traffic/ops-road")
@Validated
public class AppTrafficOpsRoadController {

    @Resource
    private TrafficOpsRoadService opsRoadService;

    @GetMapping("/roadDict")
    @Operation(summary = "获得道路信息字典信息(不分页)")
    public CommonResult<PageResult<TrafficOpsRoadRespVO>> getOpsRoadDictList(@Valid TrafficOpsRoadPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<TrafficOpsRoadDO> pageResult = opsRoadService.getOpsRoadPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsRoadRespVO.class));
    }

}