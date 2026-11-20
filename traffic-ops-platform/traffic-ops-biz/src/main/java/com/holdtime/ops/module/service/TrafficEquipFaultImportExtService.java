package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备故障信息导入 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficEquipFaultImportExtService {

    ImportRecordRespVO importEquipFaultInfo(MultipartFile file);

    /**
     * 导入设备故障信息
     * @param file 导入Excel文件
     * @param importTypeClass 导入类型
     * @return 导入结果
     */
    ImportRecordRespVO importEquipFaultInfo(MultipartFile file, Class<?> importTypeClass);

}