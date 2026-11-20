package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产摄像设备信息新增/修改 Request VO")
@Data
public class PublicOpsCameraInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7111")
    private String id;

    @Schema(description = "摄像头名称")
    private String cameraName;

    @Schema(description = "摄像头编号")
    private String cameraBh;

    @Schema(description = "摄像头用户")
    private String cameraUser;

    @Schema(description = "摄像头密码")
    private String cameraPassword;

    @Schema(description = "摄像头组")
    private String cameraGroup;

    @Schema(description = "安装位置")
    private String installLocation;

    @Schema(description = "安装时间")
    private LocalDateTime installDate;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}