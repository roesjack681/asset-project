package com.holdtime.ops.module.entity.mapper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.ImportRecordDO;
import com.holdtime.ops.module.model.vo.ImportRecordPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电动自行车导入 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ImportRecordMapper extends BaseMapperX<ImportRecordDO> {

    default PageResult<ImportRecordDO> selectPage(ImportRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ImportRecordDO>()
                .likeIfPresent(ImportRecordDO::getFileName, reqVO.getFileName())
                .eqIfPresent(ImportRecordDO::getImportFile, reqVO.getImportFile())
                .eqIfPresent(ImportRecordDO::getImportNum, reqVO.getImportNum())
                .eqIfPresent(ImportRecordDO::getSucNum, reqVO.getSucNum())
                .eqIfPresent(ImportRecordDO::getFailNum, reqVO.getFailNum())
                .eqIfPresent(ImportRecordDO::getFailFile, reqVO.getFailFile())
                .betweenIfPresent(ImportRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ImportRecordDO::getId));
    }

}