package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewVioSurveilScreenErrInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewVioSurveilScreenErrInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewVioSurveilScreenErrInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 集指无效违法记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewVioSurveilScreenErrInfoServiceImpl implements ViewVioSurveilScreenErrInfoService {

    @Resource
    private ViewVioSurveilScreenErrInfoMapper viewVioSurveilScreenErrInfoMapper;

    @Override
    public String createViewVioSurveilScreenErrInfo(ViewVioSurveilScreenErrInfoSaveReqVO createReqVO) {
        // 插入
        ViewVioSurveilScreenErrInfoDO viewVioSurveilScreenErrInfo = BeanUtils.toBean(createReqVO, ViewVioSurveilScreenErrInfoDO.class);
        viewVioSurveilScreenErrInfoMapper.insert(viewVioSurveilScreenErrInfo);
        // 返回
        return viewVioSurveilScreenErrInfo.getId();
    }

    @Override
    public void updateViewVioSurveilScreenErrInfo(ViewVioSurveilScreenErrInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateViewVioSurveilScreenErrInfoExists(updateReqVO.getId());
        // 更新
        ViewVioSurveilScreenErrInfoDO updateObj = BeanUtils.toBean(updateReqVO, ViewVioSurveilScreenErrInfoDO.class);
        viewVioSurveilScreenErrInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewVioSurveilScreenErrInfo(String id) {
        // 校验存在
        validateViewVioSurveilScreenErrInfoExists(id);
        // 删除
        viewVioSurveilScreenErrInfoMapper.deleteById(id);
    }

    private void validateViewVioSurveilScreenErrInfoExists(String id) {
        if (viewVioSurveilScreenErrInfoMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIEW_VIO_SURVEIL_SCREEN_ERR_INFO_NOT_EXISTS);
        }
    }

    @Override
    public ViewVioSurveilScreenErrInfoDO getViewVioSurveilScreenErrInfo(String id) {
        return viewVioSurveilScreenErrInfoMapper.selectById(id);
    }

    @Override
    public PageResult<ViewVioSurveilScreenErrInfoDO> getViewVioSurveilScreenErrInfoPage(ViewVioSurveilScreenErrInfoPageReqVO pageReqVO) {
        return viewVioSurveilScreenErrInfoMapper.selectPage(pageReqVO);
    }

}