package com.holdtime.ops.module.enums;

/**
 * 常量
 */
public interface OpsConstants {
    String EN_STRING_SEMICOLON = ";";
    /**
     * 常量：0
     */
    String STRING_ZERO = "0";

    /**
     * 常量：1
     */
    String STRING_ONE = "1";

    /**
     * 常量：2
     */
    String STRING_TWO = "2";

    /**
     * 常量：3
     */
    String STRING_THREE = "3";

    /**
     * 常量：4
     */
    String STRING_FOUR = "4";

    /**
     * 常量：5
     */
    String STRING_FIVE = "5";

    /**
     * 待派单
     */
    String STATUS_PENDING_ORDERS = "1";

    /**
     * 待接单
     */
    String STATUS_ACCEPT_ORDER = "6";

    /**
     * 维修中(接单后)
     */
    String STATUS_IN_MAINTENANCE = "4";

    /**
     * 已完成
     */
    String STATUS_COMPLETED = "5";
    /**
     * 取消
     */
    String STATUS_CANCEL = "7";
    /**
     * 待确认
     */
    String STATUS_CONFIRM = "8";
    /**
     * 驳回
     */
    String STATUS_REJECT = "9";

    /**
     * 暂时无法修复
     */
    String STATUS_TEMPORARILY_UNABLE_REPAIR = "10";

    /**
     * 报修类型：上报
     */
    String REPAIR_TYPE_ONE = "1";

    /**
     * 报修类型：维修
     */
    String REPAIR_TYPE_TWO = "2";
    /**
     * 报修类型：暂时无法修复
     */
    String REPAIR_TYPE_THREE = "3";

    /**
     * 查询类型：待派单
     */
    String QUERY_TYPE_ONE = "1";

    /**
     * 报修类型：已派单
     */
    String QUERY_TYPE_TWO = "2";

    /**
     * 报修类型：根据用户权限-差全部/我的报修及维修
     */
    String QUERY_TYPE_THREE = "3";

    /**
     * 角色——派单角色ID
     */
    Long ROLE_ASSIGN_ID = 158L;

    /**
     * 角色——查看全部的角色ID
     */
    Long ROLE_VIEW_ALL_ID = 160L;

    /**
     * 角色——查看设备的角色ID
     */
    Long ROLE_VIEW_EQUIP_ID = 161L;

    /**
     * 角色——取消订单角色ID
     */
    Long ROLE_CANCEL_ORDER_ID = 162L;

    /**
     * 设备状态：正常
     */
    String EQU_STATUS_ZC = "正常";

    /**
     * 设备状态：维修中
     */
    String EQU_STATUS_WXZ = "维修中";

    /**
     * 设备状态：故障
     */
    String EQU_STATUS_GZ = "故障";

    /**
     * 设备状态：拆除
     */
    String EQU_STATUS_CC = "拆除";

    /**
     * 设备类型：信号机
     */
    String EQU_TYPE_XHJ = "信号机";

    /**
     * 设备类型：信号灯
     */
    String EQU_TYPE_XHD = "信号灯";

    /**
     * 设备类型：电警
     */
    String EQU_TYPE_DJ = "电警";

    /**
     * 设备类型：杆件
     */
    String EQU_TYPE_GJ = "杆件";

    /**
     * 设备类型：led屏
     */
    String EQU_TYPE_LED = "led屏";

    /**
     * 设备类型：护栏
     */
    String EQU_TYPE_GUARDRAIL = "护栏";

    /**
     * 打印类型：单打
     */
    String PRINT_TYPE_SINGLE = "single";

    /**
     * 常量：是
     */
    String STRING_YES = "是";

    /**
     * 常量：否
     */
    String STRING_NO = "否";

    /**
     * 用户类型：上报人
     */
    String USER_TYPE_REPORT = "上报人";

    /**
     * 用户类型：派单人
     */
    String USER_TYPE_DISPATCH = "派单人";

    /**
     * 用户类型：维修人
     */
    String USER_TYPE_MAINTENANCE = "维修人";

    /**
     * 用户类型：确认人
     */
    String USER_TYPE_CONFIRM = "确认人";

    /**
     *  用户类型：取消人
     */
    String USER_TYPE_CANCEL = "取消人";

    /**
     * 用户类型：驳回人
     */
    String USER_TYPE_REJECT = "驳回人";

    /**
     * 用户类型：其他
     */
    String USER_TYPE_OTHER = "其他";

    /**
     * 消息通知类型：短信
     */
    String NOTICE_TYPE_SMS = "1";

    /**
     * 流程类型：1-上报；
     */
    String FLOW_TYPE_REPORT = "1";

    /**
     * 流程类型：2-派单
     */
    String FLOW_TYPE_DISPATCH = "2";

    /**
     * 流程类型：3-接单；
     */
    String FLOW_TYPE_RECEIVE = "3";

    /**
     * 流程类型：4-维修
     */
    String FLOW_TYPE_MAINTENANCE = "4";

    /**
     * 流程类型：5-确认
     */
    String FLOW_TYPE_CONFIRM = "5";

    /**
     * 流程类型：6-驳回
     */
    String FLOW_TYPE_REJECT = "6";

    /**
     * 流程类型：7-取消
     */
    String FLOW_TYPE_CANCEL = "7";
    /**
     * 流程类型：8-补录
     */
    String FLOW_TYPE_SUPPLEMENT = "8";
    /**
     * 流程类型：9-挂单
     */
    String FLOW_TYPE_MOUNT = "9";
    /**
     * 流程类型：10-自动完成
     */
    String FLOW_TYPE_AUTO_FINISH = "10";

    /**
     * 业务模块：交警标线
     */
    String BUSINESS_MODEL_TRAFFIC_MARK_LINE = "traffic_mark_line";

    /**
     * 业务模块：交警护栏
     */
    String BUSINESS_MODEL_TRAFFIC_GUARDRAIL = "traffic_guardrail";
    /**
     * 设备安装状态：拆除
     */
    String EQU_INSTALL_STATUS_CC = "拆除";
    /**
     * 设备安装状态：安装
     */
    String EQU_INSTALL_STATUS_AZ = "安装";

    /**
     * 检测正常情况
     */
    String PARAM_KEY_MONITOR_NORMAL_SITUATION = "MONITOR_NORMAL_SITUATION";
}
