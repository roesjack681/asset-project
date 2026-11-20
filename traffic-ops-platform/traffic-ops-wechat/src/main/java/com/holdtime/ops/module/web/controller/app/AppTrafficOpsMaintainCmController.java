package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.holdtime.ops.module.entity.TrafficOpsMaintainCmDO;
import com.holdtime.ops.module.model.vo.AppTrafficOpsMaintainCmPageReqVO;
import com.holdtime.ops.module.model.vo.AppTrafficOpsMaintainCmRespVO;
import com.holdtime.ops.module.model.vo.AppTrafficOpsMaintainCmSaveReqVO;
import com.holdtime.ops.module.service.TrafficOpsMaintainCmService;
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

@Tag(name = "用户 APP - 设备维保耗材")
@RestController
@RequestMapping("/traffic/ops-maintain-cm")
@Validated
public class AppTrafficOpsMaintainCmController {

    @Resource
    private TrafficOpsMaintainCmService opsMaintainCmService;

    @PostMapping("/create")
    @Operation(summary = "创建设备维保耗材")
    public CommonResult<String> createOpsMaintainCm(@Valid @RequestBody AppTrafficOpsMaintainCmSaveReqVO createReqVO) {
        return success(opsMaintainCmService.createOpsMaintainCm(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备维保耗材")
    public CommonResult<Boolean> updateOpsMaintainCm(@Valid @RequestBody AppTrafficOpsMaintainCmSaveReqVO updateReqVO) {
        opsMaintainCmService.updateOpsMaintainCm(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备维保耗材")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsMaintainCm(@RequestParam("id") String id) {
        opsMaintainCmService.deleteOpsMaintainCm(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备维保耗材")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppTrafficOpsMaintainCmRespVO> getOpsMaintainCm(@RequestParam("id") String id) {
        TrafficOpsMaintainCmDO opsMaintainCm = opsMaintainCmService.getOpsMaintainCm(id);
        return success(BeanUtils.toBean(opsMaintainCm, AppTrafficOpsMaintainCmRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备维保耗材分页")
    public CommonResult<PageResult<AppTrafficOpsMaintainCmRespVO>> getOpsMaintainCmPage(@Valid AppTrafficOpsMaintainCmPageReqVO pageReqVO) {
        PageResult<TrafficOpsMaintainCmDO> pageResult = opsMaintainCmService.getOpsMaintainCmPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppTrafficOpsMaintainCmRespVO.class));
    }

//    @GetMapping("/export-excel")
//    @Operation(summary = "导出设备维保耗材 Excel")
//    public void exportOpsMaintainCmExcel(@Valid AppTrafficOpsMaintainCmPageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<TrafficOpsMaintainCmDO> list = opsMaintainCmService.getOpsMaintainCmPage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "设备维保耗材.xls", "数据", TrafficOpsMaintainCmRespVO.class,
//                        BeanUtils.toBean(list, TrafficOpsMaintainCmRespVO.class));
//    }

}