package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.ImportRecordDO;
import com.holdtime.ops.module.entity.mapper.ImportRecordMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.ImportRecordPageReqVO;
import com.holdtime.ops.module.model.vo.ImportRecordSaveReqVO;
import com.holdtime.ops.module.service.ImportRecordService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 电动自行车导入 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class ImportRecordServiceImpl implements ImportRecordService {

    @Resource
    private ImportRecordMapper importRecordMapper;

    @Override
    public String createImportRecord(ImportRecordSaveReqVO createReqVO) {
        // 插入
        ImportRecordDO importRecord = BeanUtils.toBean(createReqVO, ImportRecordDO.class);
        importRecordMapper.insert(importRecord);
        // 返回
        return importRecord.getId();
    }

    @Override
    public void updateImportRecord(ImportRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateImportRecordExists(updateReqVO.getId());
        // 更新
        ImportRecordDO updateObj = BeanUtils.toBean(updateReqVO, ImportRecordDO.class);
        importRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteImportRecord(String id) {
        // 校验存在
        validateImportRecordExists(id);
        // 删除
        importRecordMapper.deleteById(id);
    }

    private void validateImportRecordExists(String id) {
        if (importRecordMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.IMPORT_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public ImportRecordDO getImportRecord(String id) {
        return importRecordMapper.selectById(id);
    }

    @Override
    public PageResult<ImportRecordDO> getImportRecordPage(ImportRecordPageReqVO pageReqVO) {
        return importRecordMapper.selectPage(pageReqVO);
    }

}