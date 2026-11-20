package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssuePersonMapper;
import com.xingyuv.captcha.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIssuePersonDO;
import com.holdtime.ops.module.service.TrafficOpsIssuePersonService;

import java.util.Set;

@Tag(name = "管理后台 - 设备领用人信息")
@RestController
@RequestMapping("/traffic/ops-issue-person")
@Validated
public class TrafficOpsIssuePersonController {

    @Resource
    private TrafficOpsIssuePersonService opsIssuePersonService;
    @Resource
    private TrafficOpsIssuePersonMapper opsIssuePersonMapper;
    @Resource
    private DeptService deptService;

    @PostMapping("/create")
    @Operation(summary = "创建设备领用人信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-person:create')")
    public CommonResult<String> createOpsIssuePerson(@Valid @RequestBody TrafficOpsIssuePersonSaveReqVO createReqVO) {
        return success(opsIssuePersonService.createOpsIssuePerson(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备领用人信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-person:update')")
    public CommonResult<Boolean> updateOpsIssuePerson(@Valid @RequestBody TrafficOpsIssuePersonSaveReqVO updateReqVO) {
        opsIssuePersonService.updateOpsIssuePerson(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备领用人信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-person:delete')")
    public CommonResult<Boolean> deleteOpsIssuePerson(@RequestParam("id") String id) {
        opsIssuePersonService.deleteOpsIssuePerson(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备领用人信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-person:query')")
    public CommonResult<TrafficOpsIssuePersonRespVO> getOpsIssuePerson(@RequestParam("id") String id) {
        TrafficOpsIssuePersonDO opsIssuePerson = opsIssuePersonService.getOpsIssuePerson(id);
        return success(BeanUtils.toBean(opsIssuePerson, TrafficOpsIssuePersonRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备领用人信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-person:query')")
    public CommonResult<PageResult<TrafficOpsIssuePersonRespVO>> getOpsIssuePersonPage(@Valid TrafficOpsIssuePersonPageReqVO pageReqVO) {
        PageResult<TrafficOpsIssuePersonDO> pageResult = opsIssuePersonService.getOpsIssuePersonPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsIssuePersonRespVO.class));
    }

//    @GetMapping("/export-excel")
//    @Operation(summary = "导出设备领用人信息 Excel")
//    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-person:export')")
//    @OperateLog(type = EXPORT)
//    public void exportOpsIssuePersonExcel(@Valid TrafficOpsIssuePersonPageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<TrafficOpsIssuePersonDO> list = opsIssuePersonService.getOpsIssuePersonPage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "设备领用人信息.xls", "数据", TrafficOpsIssuePersonRespVO.class,
//                        BeanUtils.toBean(list, TrafficOpsIssuePersonRespVO.class));
//    }

    @GetMapping("/personPage")
    @Operation(summary = "选择领用人")
    public CommonResult<PageResult<TrafficOpsIssuePersonRespVO>> getOpsIssueSelectPersonPage(@RequestParam(value = "deptId",required = false) String deptId,@RequestParam(value = "name",required = false) String name) {
        LambdaQueryWrapperX<TrafficOpsIssuePersonDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        TrafficOpsIssuePersonPageReqVO pageReqVO =  new TrafficOpsIssuePersonPageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);

        if (StringUtils.isNotBlank(deptId)) {
            Set<Long> deptIds = convertSet(deptService.getChildDeptList(Long.parseLong(deptId)), DeptDO::getId);
            deptIds.add(Long.parseLong(deptId)); // 包括自身
            opsDOLambdaQueryWrapperX.in(TrafficOpsIssuePersonDO::getDeptId, deptIds);
        }

//        if(!StringUtil.isEmpty(deptId)){
//            opsDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsIssuePersonDO::getDeptId,deptId);
//        }
        if(!StringUtil.isEmpty(name)){
            opsDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsIssuePersonDO::getUserName,name);
        }
        PageResult<TrafficOpsIssuePersonDO> pageResult = opsIssuePersonMapper.selectPage(pageReqVO,opsDOLambdaQueryWrapperX);
        return success(BeanUtils.toBean(pageResult, TrafficOpsIssuePersonRespVO.class));
    }

}