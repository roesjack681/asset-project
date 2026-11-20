package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.service.WechatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 微信文件中心")
@RestController
@RequestMapping("/traffic/wechat")
@Validated
public class AppWechatFileController {

    @Resource
    private WechatService wechatService;

    /**
     * 从微信下载媒体文件
     * @return 维修人员信息
     */
    @GetMapping("/download")
    @Operation(summary = "查询所有的维修人员")
    public CommonResult<String> downloadWeixinMedia(@RequestParam(value = "serverId") String serverId) {
        return success(wechatService.downloadWeixinMedia(serverId));
    }

}