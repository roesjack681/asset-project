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
 * 杆件 DO
 *
 * @author 芋道源码
 */
@TableName("police_ops_member")
@KeySequence("police_ops_member_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceOpsMemberDO extends BaseDO {

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
     * 安装位置
     */
    private String adress;
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
     * 高度
     */
    private String hight;
    /**
     * 横杆长度
     */
    private String barLength;
    /**
     * 副臂长度
     */
    private String jibLength;
    /**
     * 杆件类型
     */
    private String memberType;
    /**
     * 管辖单位
     */
    private String dominion;
    /**
     * 状态
     */
    private String status;
    /**
     * 设备图片
     */
    private String equImg;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 序列号
     */
    private Integer serialNum;
    /**
     * 设备图片2
     */
    private String equImg2;
    /**
     * 设备图片3
     */
    private String equImg3;
    /**
     * 是否核查
     */
    private String isCheck;
    /**
     * 核查备注说明
     */
    private String checkRemark;
    /**
     * 图纸
     */
    private String drawing;
    /**
     * 取电位置
     */
    private String powerSupplyLocation;

}