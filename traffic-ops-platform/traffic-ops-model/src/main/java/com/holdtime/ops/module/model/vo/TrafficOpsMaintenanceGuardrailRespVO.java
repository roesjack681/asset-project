package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 护栏维护详细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceGuardrailRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28659")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "维保主键", example = "28263")
    @ExcelProperty("维保主键")
    private String maintenanceId;

    @Schema(description = "护栏主键", example = "687")
    @ExcelProperty("护栏主键")
    private String guardrailId;

    @Schema(description = "护栏维护类型：清洗；更换；拆除", example = "2")
    @ExcelProperty("护栏维护类型：清洗；更换；拆除")
    private String maintenanceType;

    @Schema(description = "更换类型：损毁；换新", example = "1")
    @ExcelProperty("更换类型：损毁；换新")
    private String changeType;

    @Schema(description = "起始位置名称", example = "张三")
    @ExcelProperty("起始位置名称")
    private String startName;

    @Schema(description = "起始位置经度")
    @ExcelProperty("起始位置经度")
    private String startLng;

    @Schema(description = "起始位置纬度")
    @ExcelProperty("起始位置纬度")
    private String startLat;

    @Schema(description = "截止位置名称", example = "赵六")
    @ExcelProperty("截止位置名称")
    private String endName;

    @Schema(description = "截止位置经度")
    @ExcelProperty("截止位置经度")
    private String endLng;

    @Schema(description = "截止位置纬度")
    @ExcelProperty("截止位置纬度")
    private String endLat;

    @Schema(description = "是否增加开口")
    @ExcelProperty("是否增加开口")
    private String isGap;

    @Schema(description = "变更前产品编号")
    @ExcelProperty("变更前产品编号")
    private String moditfyBeProduct;

    @Schema(description = "变更前片数")
    private Integer moditfyBePieces;

    @Schema(description = "变更后产品编号")
    @ExcelProperty("变更后产品编号")
    private String moditfyAfProduct;

    @Schema(description = "变更后片数")
    private Integer moditfyAfPieces;

    @Schema(description = "变更前图片")
    @ExcelProperty("变更前图片")
    private String moditfyBeImg;

    @Schema(description = "变更后图片")
    @ExcelProperty("变更后图片")
    private String moditfyAfImg;

    @Schema(description = "变更内容描述")
    @ExcelProperty("变更内容描述")
    private String desc;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "赔付类型：保险；运维", example = "1")
    @ExcelProperty("赔付类型：保险；运维")
    private String claimType;

    @Schema(description = "拆除类型：拆首段;拆尾端;拆中段;拆辅助设备")
    private String removalType;

    @Schema(description = "是否首位置变更")
    @ExcelProperty("是否首位置变更")
    private String isHeadModitfy;

    @Schema(description = "是否尾位置变更")
    @ExcelProperty("是否尾位置变更")
    private String isTailModitfy;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}