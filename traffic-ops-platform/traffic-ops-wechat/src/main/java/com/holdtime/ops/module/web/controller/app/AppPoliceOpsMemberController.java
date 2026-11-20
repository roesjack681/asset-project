package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.PoliceOpsMemberExtService;
import com.holdtime.ops.module.service.PoliceOpsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 公安杆件")
@RestController
@RequestMapping("/police/ops-member")
@Validated
public class AppPoliceOpsMemberController {

    @Resource
    private PoliceOpsMemberService policeOpsMemberService;

    @Resource
    private PoliceOpsMemberExtService policeOpsMemberExtService;

    @PostMapping("/create")
    @Operation(summary = "创建杆件")
    public CommonResult<String> createOpsMember(@Valid @RequestBody PoliceOpsMemberExtSaveReqVO createReqVO) {
        return success(policeOpsMemberExtService.saveOpsMemberInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新杆件")
    public CommonResult<Boolean> updateOpsMember(@Valid @RequestBody PoliceOpsMemberExtSaveReqVO updateReqVO) {
        policeOpsMemberExtService.saveOpsMemberInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除杆件")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsMember(@RequestParam("id") String id) {
        policeOpsMemberExtService.deleteOpsMember(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得杆件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PoliceOpsMemberExtRespVO> getOpsMember(@RequestParam("id") String id) {
        return success(policeOpsMemberExtService.getOpsMember(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得杆件分页")
    public CommonResult<PageResult<PoliceOpsMemberExtRespVO>> getOpsMemberPage(@Valid PoliceOpsMemberPageReqVO pageReqVO) {
        return success(policeOpsMemberExtService.getOpsMemberPage(pageReqVO, null));
    }

    @GetMapping("/queryNearByPoliceMember")
    @Operation(summary = "查询附近可能的杆件信息")
    public CommonResult<List<String>> queryNearByPoliceMember(@RequestParam("lat") String lat, @RequestParam("lng") String lng) {
        return success(policeOpsMemberExtService.queryNearByPoliceMember(lat, lng));
    }

    @GetMapping("/autocompletePoliceOpsMemberList")
    @Operation(summary = "根据设备名称自动补全查询杆件信息")
    public CommonResult<List<PoliceOpsMemberExtRespVO>> autocompletePoliceOpsMemberList(@RequestParam("equName") String equName) {
        return success(policeOpsMemberExtService.autocompletePoliceOpsMemberList(equName));
    }

    @GetMapping("/queryPoliceElectricPolice")
    @Operation(summary = "根据设备名称查询电警信息")
    public CommonResult<List<PoliceOpsElectricPoliceRespVO>> queryPoliceElectricPolice(@RequestParam("equName") String equName) {
        return success(policeOpsMemberExtService.queryPoliceElectricPolice(equName));
    }

}