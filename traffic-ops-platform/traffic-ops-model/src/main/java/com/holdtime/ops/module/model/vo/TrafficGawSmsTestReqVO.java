package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 设备监测结果新增/修改 Request VO")
@Data
public class TrafficGawSmsTestReqVO {
    /**
     * 短信地址
     */
    private String apiUrl;

    /**
     * 序列号
     */
    private String serialNum;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 发送人姓名
     */
    private String sendUserName;


    /**
     * 发送部门
     */
    private String sendUserDept;

    /**
     * 接收手机号
     */
    private String receivePhone;


}