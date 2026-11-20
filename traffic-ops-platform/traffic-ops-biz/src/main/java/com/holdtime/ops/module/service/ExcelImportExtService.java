package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.ImportRecordRespVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel导入 Service 接口
 *
 * @author 芋道源码
 */
public interface ExcelImportExtService {

    /**
     * 导入设备故障信息
     * @param file 导入Excel文件
     * @param importTypeClass 导入类型
     * @return 导入结果
     */
    ImportRecordRespVO importExcelInfo(String importType, MultipartFile file, Class<?> importTypeClass);

}