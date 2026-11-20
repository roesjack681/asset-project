package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "大数据——基础设施 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsBigDataBaseRespVO {

    @Schema(description = "路")
    private String roadNum;

    @Schema(description = "交叉口")
    private String intersectionNum;

    @Schema(description = "信号机")
    private String signalNum;

    @Schema(description = "信号机-正常")
    private String signalZNum;

    @Schema(description = "信号机-维修中")
    private String signalWNum;

    @Schema(description = "信号机-故障")
    private String signalGNum;

    @Schema(description = "电警")
    private String electricPoliceNum;

    @Schema(description = "电警-正常")
    private String electricPoliceZNum;

    @Schema(description = "电警-维修中")
    private String electricPoliceWNum;

    @Schema(description = "电警-故障")
    private String electricPoliceGNum;

    @Schema(description = "LED大屏")
    private String bigLedNum;

    @Schema(description = "LED大屏-正常")
    private String bigLedZNum;

    @Schema(description = "LED大屏-维修中")
    private String bigLedWNum;

    @Schema(description = "LED大屏-故障")
    private String bigLedGNum;

    @Schema(description = "杆件")
    private String memberNum;

    @Schema(description = "杆件-正常")
    private String memberZNum;

    @Schema(description = "杆件-维修中")
    private String memberWNum;

    @Schema(description = "杆件-故障")
    private String memberGNum;

    @Schema(description = "信号灯数")
    private String signalLightNum;

    @Schema(description = "信号灯数-正常")
    private String signalLightZNum;

    @Schema(description = "信号灯数-维修中")
    private String signalLightWNum;

    @Schema(description = "信号灯数-故障")
    private String signalLightGNum;

    @Schema(description = "标线数")
    private String marklineNum;

    @Schema(description = "标志牌数")
    private String boardNum;

    @Schema(description = "标志牌数-正常")
    private String boardZNum;

    @Schema(description = "标志牌数-维修中")
    private String boardWNum;

    @Schema(description = "标志牌数-故障")
    private String boardGNum;

    @Schema(description = "雷达数")
    private String radarNum;

    @Schema(description = "雷达数-正常")
    private String radarZNum;

    @Schema(description = "雷达数-维修中")
    private String radarWNum;

    @Schema(description = "雷达数-故障")
    private String radarGNum;

    @Schema(description = "护栏数")
    private String guardrailNum;

    @Schema(description = "护栏数-正常")
    private String guardrailZNum;

    @Schema(description = "护栏数-维修中")
    private String guardrailWNum;

    @Schema(description = "护栏数-故障")
    private String guardrailGNum;

    @Schema(description = "设备总数")
    private String equNum;

    @Schema(description = "信号机占比")
    private String signalProp;

    @Schema(description = "电警占比")
    private String electricPoliceProp;

    @Schema(description = "LED大屏占比")
    private String bigLedProp;

    @Schema(description = "杆件占比")
    private String memberProp;

    @Schema(description = "信号灯占比")
    private String signalLightProp;

    @Schema(description = "标线占比")
    private String marklineProp;

    @Schema(description = "标志牌占比")
    private String boardProp;

    @Schema(description = "雷达占比")
    private String radarProp;

    @Schema(description = "护栏占比")
    private String guardrailProp;

}