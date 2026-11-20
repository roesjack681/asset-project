package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 大屏分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsBigledPageReqVO extends PageParam {

    @Schema(description = "设备编号")
    private String equNumber;

    @Schema(description = "设备名称", example = "王五")
    private String equName;

    @Schema(description = "设备类型", example = "1")
    private String equType;

    @Schema(description = "所在道路名称", example = "赵六")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "赵六")
    private String intersectionName;

    @Schema(description = "GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    private String lat;

    @Schema(description = "描述")
    private String des;

    @Schema(description = "点位名称")
    private String adress;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "屏幕朝向")
    private String screenOrientation;

    @Schema(description = "子网")
    private String subnet;

    @Schema(description = "网关")
    private String gateway;

    @Schema(description = "球机IP")
    private String ptzIp;

    @Schema(description = "用户名", example = "王五")
    private String username;

    @Schema(description = "密码")
    private String passwd;

    @Schema(description = "违章编码")
    private String violationCode;

    @Schema(description = "诱导编码")
    private String inducedEncoding;

    @Schema(description = "大屏对应编号")
    private String dpbh;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "大小")
    private String size;

    @Schema(description = "安装时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] installDate;

    @Schema(description = "厂家")
    private String manufacturer;

    @Schema(description = "归属单位")
    private String unit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

    @Schema(description = "所属管辖单位")
    private String dominion;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

}