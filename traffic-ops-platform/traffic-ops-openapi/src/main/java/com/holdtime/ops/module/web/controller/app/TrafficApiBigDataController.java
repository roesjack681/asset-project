package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.aop.annotation.ApiAuthCheck;
import com.holdtime.ops.module.model.vo.TrafficOpsBigDataBaseRespVO;
import com.holdtime.ops.module.service.TrafficOpsBigDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 项目名称： traffic-ops-platform
 * 功能描述： TrafficBigDataController概述
 * 创 建 人： yl
 * 创建日期： 2024年12月19日 11:40:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
@Tag(name = "第三方 - 大数据")
@RestController
@RequestMapping("/traffic-api")
@Validated

public class TrafficApiBigDataController {
    @Resource
    private TrafficOpsBigDataService opsBigDataService;

    @ApiAuthCheck
    @GetMapping("/bigdata-get")
    @Operation(summary = "设备数量及状态统计")
    @Parameter(name = "intersectionName", description = "交叉口名称", required = true, example = "1024")
    public CommonResult<TrafficOpsBigDataBaseRespVO> getOpsBigled(@RequestParam(value = "intersectionName", required = false) String intersectionName,@RequestParam(value = "dominion", required = false) String dominion,@RequestParam(value = "status", required = false) String status) {
        TrafficOpsBigDataBaseRespVO respVo = opsBigDataService.getOpsBigDataBase(intersectionName,dominion,status);
        return success(BeanUtils.toBean(respVo, TrafficOpsBigDataBaseRespVO.class));
    }

}
