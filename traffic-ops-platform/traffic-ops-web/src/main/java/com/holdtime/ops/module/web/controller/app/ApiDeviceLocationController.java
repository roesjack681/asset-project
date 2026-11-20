package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.framework.openapi.core.annotations.OpenApiValidateExt;
import com.holdtime.framework.openapi.core.model.ApiReqModel;
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationDO;
import com.holdtime.ops.module.model.vo.PublicOpsDeviceLocationPageReqVO;
import com.holdtime.ops.module.model.vo.PublicOpsDeviceLocationRespVO;
import com.holdtime.ops.module.model.vo.PublicOpsDeviceLocationSaveReqVO;
import com.holdtime.ops.module.service.PublicOpsDeviceLocationExtService;
import com.holdtime.ops.module.service.PublicOpsDeviceLocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "开放API - 终端位置,供设备调用")
@RestController
@RequestMapping("/openapi/ops/device-location")
@Validated
public class ApiDeviceLocationController {

    @Resource
    private PublicOpsDeviceLocationService opsDeviceLocationService;
    @Resource
    private PublicOpsDeviceLocationExtService deviceLocationExtService;

    @PostMapping("/create")
    @Operation(summary = "创建终端位置")
    @OpenApiValidateExt( signSoreArea = "jsonBody",tsExpireTime = 30)
    public CommonResult<String> createDeviceLocation(@RequestBody ApiReqModel<PublicOpsDeviceLocationSaveReqVO> reqModel) {
        return success(deviceLocationExtService.saveDeviceLocation(reqModel.getData()));
    }

    @PostMapping("/page")
    @Operation(summary = "获得终端位置分页")
    @OpenApiValidateExt( signSoreArea = "jsonBody",tsExpireTime = 30)
    public CommonResult<PageResult<PublicOpsDeviceLocationRespVO>> getDeviceLocationPage(@RequestBody  ApiReqModel<PublicOpsDeviceLocationPageReqVO> pageReqVO) {
        PageResult<PublicOpsDeviceLocationDO> pageResult = opsDeviceLocationService.getOpsDeviceLocationPage(pageReqVO.getData());
        return success(BeanUtils.toBean(pageResult, PublicOpsDeviceLocationRespVO.class));
    }

}