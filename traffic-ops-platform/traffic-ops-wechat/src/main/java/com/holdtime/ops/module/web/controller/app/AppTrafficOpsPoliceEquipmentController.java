package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.model.vo.TrafficOpsPoliceEquipmentRespVO;
import com.holdtime.ops.module.service.TrafficOpsPoliceEquipmentExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 警用装备资产信息")
@RestController
@RequestMapping("/traffic/ops-police-equipment")
@Validated
public class AppTrafficOpsPoliceEquipmentController {

    @Resource
    private TrafficOpsPoliceEquipmentExtService opsPoliceEquipmentExtService;

    /**
     * 获取已发放的警用设备
     * @return 维修人员信息
     */
    @GetMapping("/queryIssuedPoliceEquipment")
    @Operation(summary = "获取已发放的警用设备")
    public CommonResult<List<TrafficOpsPoliceEquipmentRespVO>> queryIssuedPoliceEquipment(@RequestParam(value = "deptId", required = false) String deptId,
                                                                                          @RequestParam(value = "keyword", required = false) String keyword) {
        return success(opsPoliceEquipmentExtService.queryIssuedPoliceEquipment(deptId, keyword));
    }

}