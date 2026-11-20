package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.aop.annotation.ApiAuthCheck;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsQrcodeMapper;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficEquipFaultImportExtService;
import com.holdtime.ops.module.service.TrafficOpsElectricPoliceExtService;
import com.holdtime.ops.module.service.TrafficOpsElectricPoliceService;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "第三方 - 电警")
@RestController
@RequestMapping("/traffic-api")
@Validated
public class TrafficApiOpsElectricPoliceController {
    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;


    @ApiAuthCheck
    @GetMapping("/electric-police-page")
    @Operation(summary = "根据IP查询设备信息")
    public CommonResult<List<TrafficOpsElectricPoliceRespVO>> getOpsElectricPolicePage(@RequestParam(required = false) String ipAddr) {
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> opsDOLambdaQueryWrapperX = (LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>) new LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>()
                .eqIfPresent(TrafficOpsElectricPoliceDO::getIpAddr, ipAddr)
                .orderByAsc(TrafficOpsElectricPoliceDO::getId);

        List<TrafficOpsElectricPoliceDO> policeList = opsElectricPoliceMapper.selectList(opsDOLambdaQueryWrapperX);
        return success(BeanUtils.toBean(policeList,TrafficOpsElectricPoliceRespVO.class));
    }


}