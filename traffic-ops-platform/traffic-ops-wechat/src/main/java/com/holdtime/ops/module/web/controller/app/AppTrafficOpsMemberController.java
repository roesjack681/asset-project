package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsAccessoryEquipmentMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsMemberExtService;
import com.holdtime.ops.module.service.TrafficOpsMemberService;
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

@Tag(name = "用户 APP - 杆件")
@RestController
@RequestMapping("/traffic/ops-member")
@Validated
public class AppTrafficOpsMemberController {

    @Resource
    private TrafficOpsMemberService opsMemberService;
    @Resource
    private TrafficOpsMemberExtService trafficOpsMemberExtService;

    @PostMapping("/create")
    @Operation(summary = "创建杆件")
    public CommonResult<String> createOpsMember(@Valid @RequestBody TrafficOpsMemberExtSaveReqVO createReqVO) {
        return success(trafficOpsMemberExtService.saveOpsMemberInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新杆件")
    public CommonResult<Boolean> updateOpsMember(@Valid @RequestBody TrafficOpsMemberExtSaveReqVO updateReqVO) {
        trafficOpsMemberExtService.saveOpsMemberInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除杆件")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsMember(@RequestParam("id") String id) {
        trafficOpsMemberExtService.deleteOpsMemberInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得杆件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsMemberExtRespVO> getOpsMember(@RequestParam("id") String id) {
        return success(trafficOpsMemberExtService.getOpsMember(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得杆件分页")
    public CommonResult<PageResult<TrafficOpsMemberExtRespVO>> getOpsMemberPage(@Valid TrafficOpsMemberPageReqVO pageReqVO) {
        return success(trafficOpsMemberExtService.getOpsMemberPage(pageReqVO, null));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出杆件 Excel")
    public void exportOpsMemberExcel(@Valid TrafficOpsMemberPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsMemberDO> list = opsMemberService.getOpsMemberPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "杆件.xls", "数据", TrafficOpsMemberRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsMemberRespVO.class));
    }

}