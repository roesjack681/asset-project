package com.holdtime.ops.module.model.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ExcelImportExcelPaBtDTO {

    @ExcelProperty("合同名称")
    private String contractName;

    @ExcelProperty("区域")
    private String geoName;

    @ExcelProperty("合同编号")
    private String contractNum;

    @ExcelProperty("承建单位")
    private String contractorUnit;

    @ExcelProperty("摄像机名称")
    private String cameraName;

    @ExcelProperty("摄像机IP")
    private String cameraIp;

    @ExcelProperty("在线状态")
    private String onlineStatus;

    @ExcelProperty("用户名")
    private String userName;

    @ExcelProperty("密码")
    private String password;
}