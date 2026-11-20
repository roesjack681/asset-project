package com.holdtime.ops.module.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * 项目名称： passenger-platform
 * 功能描述： PassengerErrorCodeConstants概述
 * 创 建 人： yl
 * 创建日期： 2024年10月16日 14:53:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
public interface OpsErrorCodeConstants {
    // ========== 公共错误 1001 ==========
    ErrorCode SYSTEM_COMMON_ERROR_PARAM_MISSING = new ErrorCode(1001000001, "非法访问，缺少必要信息！");
    ErrorCode SYSTEM_COMMON_ERROR_EXPORT_ERROR = new ErrorCode(1001000002, "数据导出失败！");
    ErrorCode SYSTEM_COMMON_ERROR_PHONE_NOT_EXISTS = new ErrorCode(1001000002, "手机号不存在！");
    ErrorCode API_AUTH_NOT_EXISTS = new ErrorCode(1001005001, "接口授权不存在");

    ErrorCode API_AUTH_CODE_EXPIRE = new ErrorCode(1001005002, "接口授权已过期");

    ErrorCode API_VISIT_NOT_ALLOW = new ErrorCode(1001008001, "接口未授权！！");

    ErrorCode API_VISIT_TIMESTAMP_EXPIRE = new ErrorCode(1001008002, "时间戳过期！");

    ErrorCode API_VISIT_AUTH_NOT_VALIDATED = new ErrorCode(1001008003, "接口授权验证失败！");

    // ========== public 1002 ==========
    // ========== 微信、用户绑定 1002001 ==========
    ErrorCode OPS_REGISTER_NOT_EXISTS = new ErrorCode(1002001001, "人员注册绑定信息不存在");
    ErrorCode OPS_REGISTER_EXISTS = new ErrorCode(1002001002, "人员注册绑定信息已存在");
    ErrorCode OPS_REGISTER_ERROR = new ErrorCode(1002001003, "人员注册绑定信息有误，请联系管理员！");
    ErrorCode WEIXIN_TOKEN_NOT_EXISTS = new ErrorCode(1002001004, "微信Token不存在");
    ErrorCode WEIXIN_JSSDK_SIGNATURE_NOT_EXISTS = new ErrorCode(1002001005, "微信票据不存在");
    ErrorCode WEIXIN_MEDIA_NOT_EXISTS = new ErrorCode(1002001006, "微信媒体文件不存在");
    ErrorCode EQUIP_ON_OFF_CHECK_RESULT_NOT_EXISTS = new ErrorCode(1002001007, "设备通断测结果不存在");

    // ========== 报修模块 1002002 ==========
    ErrorCode OPS_MAINTENANCE_NOT_EXISTS = new ErrorCode(1002002001, "设备维保信息不存在");
    ErrorCode OPS_MAINTAIN_PROGRESS_NOT_EXISTS = new ErrorCode(1002002002, "设备维保进度不存在");
    ErrorCode OPS_MAINTENANCE_FILES_NOT_EXISTS = new ErrorCode(1002002003, "维保材料信息不存在");
    ErrorCode OPS_MAINTAIN_CM_NOT_EXISTS = new ErrorCode(1002002004, "设备维保耗材不存在");
    ErrorCode OPS_MAINTENANCE_NOT_ALLOW_ACCEPT = new ErrorCode(1002002005, "该维修单当前状态不可接！");
    ErrorCode OPS_MAINTENANCE_NOT_ALLOW_USER = new ErrorCode(1002002006, "非指定人员，不可接该维修单！");
    ErrorCode OPS_MAINTENANCE_NOT_ALLOW_MAINTENANCE = new ErrorCode(1002002007, "该维修单当前状态不可维修！");
    ErrorCode OPS_MAINTENANCE_NOT_ALLOW_CANCEL = new ErrorCode(1002002008, "维修已完成，不可取消维修单！");
    ErrorCode OPS_MAINTENANCE_TEAM_NOT_EXISTS = new ErrorCode(1002002009, "设备维保组员信息不存在");
    ErrorCode OPS_URGENCY_SETTINGS_NOT_EXISTS = new ErrorCode(1002002010, "维保紧急程度配置不存在");
    ErrorCode VIEW_OPS_MAINTENANCE_WORK_STATUS_NOT_EXISTS = new ErrorCode(1002002011, "维修工作人员状态记录不存在");
    ErrorCode OPS_NOTICE_RECORD_NOT_EXISTS = new ErrorCode(1002002012, "消息通知记录不存在");
    ErrorCode OPS_MAINTAIN_FLOW_RECORD_NOT_EXISTS = new ErrorCode(1002002013, "设备维保全流程不存在");
    ErrorCode OPS_MAINTENANCE_GUARDRAIL_NOT_EXISTS = new ErrorCode(1002002014, "护栏维护详细不存在");
    ErrorCode OPS_GUARDRAIL_AUXILIARY_NOT_EXISTS = new ErrorCode(1002002015, "护栏辅助设备不存在");
    ErrorCode OPS_GUARDRAIL_GAP_NOT_EXISTS = new ErrorCode(1002002016, "护栏开口信息不存在");
    ErrorCode OPS_GUARDRAIL_INSURANCE_NOT_EXISTS = new ErrorCode(1002002017, "护栏保险信息不存在");
    ErrorCode OPS_GUARDRAIL_PRODUCT_NOT_EXISTS = new ErrorCode(1002002018, "护栏产品信息不存在");
    ErrorCode OPS_GUARDRAIL_R_PRODUCT_NOT_EXISTS = new ErrorCode(1002002019, "护栏关联产品信息不存在");
    ErrorCode OPS_MAINTENANCE_AUXILIARY_NOT_EXISTS = new ErrorCode(1002002020, "护栏辅助设备维护详细不存在");
    // ========== 设备维保无法修复信息 ==========
    ErrorCode OPS_MAINTENANCE_UNFIXABLE_INFO_NOT_EXISTS = new ErrorCode(1002002021, "设备维保无法修复信息不存在");
    // ========== 设备维保无法修复跟进 ==========
    ErrorCode OPS_MAINTENANCE_UNFIXABLE_FOLLOW_NOT_EXISTS = new ErrorCode(1002002022, "设备维保无法修复跟进不存在");

    // ========== 设备二维码 1002003 ==========
    ErrorCode OPS_QRCODE_NOT_EXISTS = new ErrorCode(1002003001, "资产二维码信息不存在");
    ErrorCode OPS_QRCODE_GEN_RECORD_NOT_EXISTS = new ErrorCode(1002003002, "资产二维码生成记录不存在");

    // ========== 日常巡检 1002004 ==========
    ErrorCode DAILY_ROUND_CHECK_NOT_EXISTS = new ErrorCode(1002004001, "日常巡检信息不存在");

    // ========== 监控模块 1002005 ==========
    ErrorCode PUBLIC_OPS_CAMERA_INFO_NOT_EXISTS = new ErrorCode(1002005001, "资产摄像信息不存在");
    ErrorCode OPS_DEVICE_NOT_EXISTS = new ErrorCode(1002005002, "终端设备信息不存在");
    ErrorCode OPS_DEVICE_LOCATION_NEW_NOT_EXISTS = new ErrorCode(1002005003, "终端最新位置不存在");
    ErrorCode OPS_DEVICE_LOCATION_NOT_EXISTS = new ErrorCode(1002005004, "终端最新位置不存在");

    // ========== 导入 1002006 ==========
    ErrorCode IMPORT_RECORD_NOT_EXISTS = new ErrorCode(1002006001, "Excel导入记录不存在");
    ErrorCode IMPORT_DATA_NOT_EXISTS = new ErrorCode(1002006002, "Excel导入数据不存在");
    ErrorCode IMPORT_DATA_EXCEPTION = new ErrorCode(1002006003, "Excel导入数据发生错误");

    //============库存管理1002007===========
    ErrorCode OPS_STOCK_DETAIL_NOT_EXISTS = new ErrorCode(1002007001, "设备库存明细不存在");
    ErrorCode OPS_STOCK_NOT_EXISTS = new ErrorCode(1002007002, "设备库存管理不存在");
    ErrorCode OPS_INOUT_STOCK_NOT_EXISTS = new ErrorCode(1002007003, "设备出入库管理不存在");
    ErrorCode OPS_INOUT_DETAIL_NOT_EXISTS = new ErrorCode(1002007004, "设备出入库存明细不存在");

    // ========== 资产附件 1002008 ==========
    ErrorCode OPS_ATTACH_INFO_NOT_EXISTS = new ErrorCode(1002008001, "资产附件信息不存在");


    // ========== traffic 1003 ==========
    // ========== 交警资产 1003001 ==========
    ErrorCode OPS_ROAD_NOT_EXISTS = new ErrorCode(1003001001, "道路信息不存在");
    ErrorCode OPS_INTERSECTION_NOT_EXISTS = new ErrorCode(1003001002, "路口信息不存在");
    ErrorCode OPS_SIGNAL_NOT_EXISTS = new ErrorCode(1003001003, "信号机不存在");
    ErrorCode OPS_SIGNAL_LIGHT_NOT_EXISTS = new ErrorCode(1003001004, "信号灯不存在");
    ErrorCode OPS_ELECTRIC_POLICE_NOT_EXISTS = new ErrorCode(1003001005, "电警不存在");
    ErrorCode OPS_RADAR_NOT_EXISTS = new ErrorCode(1003001006, "雷达不存在");
    ErrorCode OPS_GUARDRAIL_NOT_EXISTS = new ErrorCode(1003001007, "护栏不存在");
    ErrorCode OPS_MEMBER_NOT_EXISTS = new ErrorCode(1003001008, "杆件不存在");
    ErrorCode OPS_ACCESSORY_EQUIPMENT_NOT_EXISTS = new ErrorCode(1003001009, "杆件附属设备不存在");
    ErrorCode OPS_LED_NOT_EXISTS = new ErrorCode(1003001010, "Led屏不存在");
    ErrorCode OPS_BIGLED_NOT_EXISTS = new ErrorCode(1003001011, "大屏不存在");
    ErrorCode OPS_MARKLINE_NOT_EXISTS = new ErrorCode(1003001012, "标线不存在");
    ErrorCode OPS_SIGN_BOARD_NOT_EXISTS = new ErrorCode(1003001013, "标志牌不存在");

    // ========== 警用装备信息 ==========
    ErrorCode OPS_POLICE_MAINTENANCE_DETAIL_NOT_EXISTS = new ErrorCode(1003001014, "警用装备报修详情信息不存在");
    ErrorCode OPS_POLICE_MAINTENANCE_NOT_EXISTS = new ErrorCode(1003001015, "警用装备报修信息不存在");
    ErrorCode OPS_POLICE_EQUIPMENT_NOT_EXISTS = new ErrorCode(1003001016, "警用装备资产信息不存在");
    ErrorCode OPS_ISSUE_RECORD_NOT_EXISTS = new ErrorCode(1003001017, "领用记录不存在");
    ErrorCode OPS_ISSUE_DETAIL_NOT_EXISTS = new ErrorCode(1003001018, "领用详情不存在");
    ErrorCode OPS_ISSUE_PERSON_NOT_EXISTS = new ErrorCode(1003001019, "设备领用人信息不存在");
    ErrorCode OPS_ISSUE_HISTORY_NOT_EXISTS = new ErrorCode(1003001020, "警用装备历史领用记录不存在");
    ErrorCode OPS_POLICE_STATION_NOT_EXISTS = new ErrorCode(1003001021, "警用装备采集站不存在");
    ErrorCode COLLECTION_STATION_INFO_NOT_EXISTS = new ErrorCode(1003001022, "警用设备采集站信息不存在");

    // ========== 设备检测 1003002 ==========
    ErrorCode CAMDETECTIONLOG_NOT_EXISTS = new ErrorCode(1003002001, "设备状态检测不存在");
    ErrorCode LIGHTSDETECTIONLOG_NOT_EXISTS = new ErrorCode(1003002002, "信号灯检测信息不存在");
    ErrorCode LIGHTS_NOT_EXISTS = new ErrorCode(1003002003, "信号灯信息不存在");
    ErrorCode CROSSING_NOT_EXISTS = new ErrorCode(1003002004, "路口信息不存在");
    ErrorCode MONITOR_RESULTS_NOT_EXISTS = new ErrorCode(1003002005, "设备监测结果不存在");
    ErrorCode MONITOR_RESULTS_PARAM_MISSING = new ErrorCode(1003002006, "必要信息传值错误！");
    ErrorCode OPS_DJ_MONITOR_STATISTICS_NOT_EXISTS = new ErrorCode(1003002007, "监测电警设备统计不存在");
    ErrorCode MONITOR_RESULTS_DAILY_ANALYSIS_NOT_EXISTS = new ErrorCode(1003002008, "设备监测结果每日分析不存在");
    ErrorCode MONITOR_RESULTS_DAILY_DETAIL_NOT_EXISTS = new ErrorCode(1003002009, "设备监测结果每日分析详情不存在");
    ErrorCode MONITOR_RESULTS_HISTORY_NOT_EXISTS = new ErrorCode(1003002010, "设备监测结果历史不存在");
    ErrorCode MONITOR_RESULTS_IMPORT_DETAIL_NOT_EXISTS = new ErrorCode(1003002011, "设备监测结果导入信息不存在");
    ErrorCode MONITOR_RESULTS_IMPORT_DETAIL_HISTORY_NOT_EXISTS = new ErrorCode(1003002012, "设备监测结果导入信息历史不存在");
    ErrorCode MONITOR_RESULTS_IMPORT_ANALYSIS_NOT_EXISTS = new ErrorCode(1003002013, "设备监测结果导入分析不存在");
    ErrorCode MONITOR_RESULTS_IMPORT_SITUATION_NOT_EXISTS = new ErrorCode(1003002014, "公安监测结果导入近两日状态不存在");
    ErrorCode DEV_VIO_EQUIPMENT_NOT_EXISTS = new ErrorCode(1003002015, "违法抓拍设备不存在");
    ErrorCode DEV_TOLLGATE_QA_RESULT_NOT_EXISTS = new ErrorCode(1003002016, "卡口质量检测结果不存在");
    ErrorCode DEV_TOLLGATE_RECTIFICATION_NOT_EXISTS = new ErrorCode(1003002017, "卡口质量评价不存在");
    ErrorCode VIO_SURVEIL_NOT_EXISTS = new ErrorCode(1003002018, "违法抓拍有效记录不存在");
    ErrorCode VIO_SURVEIL_SCREEN_NOT_EXISTS = new ErrorCode(1003002019, "违法抓拍有效记录不存在");
    ErrorCode VIO_SURVEIL_SCREEN_ERR_NOT_EXISTS = new ErrorCode(1003002020, "违法抓拍无效记录不存在");
    ErrorCode VIO_EQUIPMENT_MONITOR_DETAIL_NOT_EXISTS = new ErrorCode(1003002021, "集指设备检测记录不存在");
    ErrorCode VIO_EQUIPMENT_MONITOR_ANALYSIS_NOT_EXISTS = new ErrorCode(1003002022, "集指设备检测分析不存在");
    ErrorCode BTJCZH_VIO_EQUIPMENT_DAILY_SITUATION_NOT_EXISTS = new ErrorCode(1003002023, "集指设备每日情况不存在");
    ErrorCode VIO_SURVEIL_ANALYSIS_NOT_EXISTS = new ErrorCode(1003002024, "违法抓拍设备分析不存在");
    ErrorCode VIO_EQUIPMENT_DAILY_SITUATION_NOT_EXISTS = new ErrorCode(1003002025, "违法抓拍设备每日情况不存在");
    ErrorCode VIEW_VIO_EQUIPMENT_MONITOR_ANALYSIS_NOT_EXISTS = new ErrorCode(1003002026, "违法抓拍设备监控分析不存在");
    ErrorCode EQUIP_PING_MONITOR_RESULT_NOT_EXISTS = new ErrorCode(1003002027, "重点考核设备通断检测不存在");
    ErrorCode VIO_EQUIPMENT_INVALID_REASON_ANALYSIS_NOT_EXISTS = new ErrorCode(1003002028, "集指设备错误原因分析不存在");
    ErrorCode BTJCZH_VIO_SURVEIL_ATTACHMENT_NOT_EXISTS = new ErrorCode(1003002029, "违法抓拍附件不存在");
    ErrorCode VIEW_VIO_SURVEIL_SCREEN_ERR_INFO_NOT_EXISTS = new ErrorCode(1003002030, "集指无效违法记录不存在");
    ErrorCode VIEW_OPS_MONITOR_SITUATION_NOT_EXISTS = new ErrorCode(1003002031, "VIEW不存在");
    ErrorCode VIO_EQUIPMENT_ILLEGAL_ANALYSIS_NOT_EXISTS = new ErrorCode(1003002032, "集指设备违法原因分析不存在");

    // ========== 信控交叉口配时方案信息 1003003 ==========
    ErrorCode TSL_JUNCTION_DEVICE_INFO_NOT_EXISTS = new ErrorCode(1003003001, "交叉口信控设备信息不存在");
    ErrorCode TSL_REGION_INFO_NOT_EXISTS = new ErrorCode(1003003002, "信控区域息不存在");
    ErrorCode TSL_JUNCTION_INFO_NOT_EXISTS = new ErrorCode(1003003003, "信控交叉口信息不存在");
    ErrorCode TSL_JUNCTION_PLAN_INFO_NOT_EXISTS = new ErrorCode(1003003004, "信控交叉口配时周方案信息不存在");
    ErrorCode TSL_JUNCTION_DAY_PLAN_INFO_NOT_EXISTS = new ErrorCode(1003003005, "信控交叉口日方案信息不存在");
    ErrorCode TSL_JUNCTION_DAY_PLAN_DETAIL_NOT_EXISTS = new ErrorCode(1003003006, "信控交叉口日方案详细信息不存在");
    ErrorCode TSL_JUNCTION_TIMING_PLAN_NOT_EXISTS = new ErrorCode(1003003007, "信控交叉口配时方案信息不存在");
    ErrorCode TSL_JUNCTION_TIMING_PLAN_STAGE_NOT_EXISTS = new ErrorCode(1003003008, "信控交叉口配时阶段信息不存在");


    // ========== police 1004 ==========
    // ========== 公安资产 1004001 ==========
    ErrorCode POLICE_OPS_MEMBER_NOT_EXISTS = new ErrorCode(1004001001, "杆件不存在");
    ErrorCode POLICE_OPS_MEMBER_EXISTS = new ErrorCode(1004001002, "杆件名称已存在,请重新命名！");
    ErrorCode POLICE_OPS_ELECTRIC_POLICE_NOT_EXISTS = new ErrorCode(1004001003, "电警不存在");
    ErrorCode POLICE_MONITOR_RESULTS_NOT_EXISTS = new ErrorCode(1004001004, "公安设备监测结果不存在");
    ErrorCode POLICE_MONITOR_RESULTS_HISTORY_NOT_EXISTS = new ErrorCode(1004001005, "公安设备监测结果历史不存在");
    ErrorCode POLICE_MONITOR_PING_ANALYSIS_NOT_EXISTS = new ErrorCode(1004001006, "电警通断检测管理不存在");
    ErrorCode POLICE_MONITOR_PING_DETAIL_NOT_EXISTS = new ErrorCode(1004001007, "公安监测通达详情不存在");
    ErrorCode POLICE_MONITOR_PING_DETAIL_SITUATION_NOT_EXISTS = new ErrorCode(1004001008, "公安通断监测近两日状态不存在");

    // ========== other 1005 ==========
    ErrorCode HA_PS_FEEDBACK_NOT_EXISTS = new ErrorCode(1005001001, "方案展示留言记录不存在");
    ErrorCode OPS_SMS_CONFIG_NOT_EXISTS = new ErrorCode(1005001002, "资产短信发送配置不存在");
    ErrorCode USER_AREA_GROUP_NOT_EXISTS = new ErrorCode(1005001003, "区域用户组信息不存在");


    // ========== 临时功能 1901 ==========
    // ========== 交警运维临时报 ==========
    ErrorCode OPS_TEMP_REPORT_NOT_EXISTS = new ErrorCode(1901001001, "交警运维临时报不存在");









}
