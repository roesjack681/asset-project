package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.ImportRecordDO;
import com.holdtime.ops.module.model.vo.ImportRecordPageReqVO;
import com.holdtime.ops.module.model.vo.ImportRecordSaveReqVO;

import javax.validation.Valid;

/**
 * Excel导入记录 Service 接口
 *
 * @author 超级管理员
 */
public interface ImportRecordService {

    /**
     * 创建Excel导入记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createImportRecord(@Valid ImportRecordSaveReqVO createReqVO);

    /**
     * 更新Excel导入记录
     *
     * @param updateReqVO 更新信息
     */
    void updateImportRecord(@Valid ImportRecordSaveReqVO updateReqVO);

    /**
     * 删除Excel导入记录
     *
     * @param id 编号
     */
    void deleteImportRecord(String id);

    /**
     * 获得Excel导入记录
     *
     * @param id 编号
     * @return Excel导入记录
     */
    ImportRecordDO getImportRecord(String id);

    /**
     * 获得Excel导入记录分页
     *
     * @param pageReqVO 分页查询
     * @return Excel导入记录分页
     */
    PageResult<ImportRecordDO> getImportRecordPage(ImportRecordPageReqVO pageReqVO);

}