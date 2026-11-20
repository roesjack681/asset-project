package com.holdtime.ops.module.enums;

/**
 * 项目名称： driving-train-data-sync
 * 功能描述： HttpStatusEnum概述
 * 创 建 人： yanxl
 * 创建日期： 2022年07月06日 08:54:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
public enum HttpStatusEnum {
    STATUS_400(400, "请求报文存在语法错误"),
    STATUS_401(401, "未授权"),
    STATUS_403(403, "服务器拒绝该次访问"),
    STATUS_404(404, "服务器上无法找到请求的资源"),
    STATUS_500(500, "服务器在执行请求时发生了错误"),
    STATUS_503(503, "服务不可用");

    // 成员变量
    private final int status;
    private final String desc;

    HttpStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    // 普通方法
    public static HttpStatusEnum getHttpStatus(int status) {
        for (HttpStatusEnum httpStatus : HttpStatusEnum.values()) {
            if (httpStatus.getStatus() == status) {
                return httpStatus;
            }
        }
        return null;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
