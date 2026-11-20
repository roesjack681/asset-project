package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 大屏 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_bigled")
@KeySequence("traffic_ops_bigled_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsBigledDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 设备编号
     */
    private String equNumber;
    /**
     * 设备名称
     */
    private String equName;
    /**
     * 设备类型
     *
     * 枚举 {@link TODO equ_type 对应的类}
     */
    private String equType;
    /**
     * 所在道路名称
     */
    private String roadName;
    /**
     * 灯控路口名称
     */
    private String intersectionName;
    /**
     * GPS定位-经度
     */
    private String lng;
    /**
     * GPS定位-纬度
     */
    private String lat;
    /**
     * 描述
     */
    private String des;
    /**
     * 点位名称
     */
    private String adress;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 屏幕朝向
     */
    private String screenOrientation;
    /**
     * 子网
     */
    private String subnet;
    /**
     * 网关
     */
    private String gateway;
    /**
     * 球机IP
     */
    private String ptzIp;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String passwd;
    /**
     * 违章编码
     */
    private String violationCode;
    /**
     * 诱导编码
     */
    private String inducedEncoding;
    /**
     * 大屏对应编号
     */
    private String dpbh;
    /**
     * 备注
     */
    private String remark;
    /**
     * 大小
     */
    private String size;
    /**
     * 安装时间
     */
    private LocalDateTime installDate;
    /**
     * 厂家
     */
    private String manufacturer;
    /**
     * 归属单位
     */
    private String unit;
    /**
     * 所属管辖单位
     */
    private String dominion;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 设备图片
     */
    private String equImg;

}