package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ViewVioEquipmentDailySituationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewVioEquipmentDailySituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.ViewVioEquipmentDailySituationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 违法抓拍设备每日情况 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewVioEquipmentDailySituationServiceImpl implements ViewVioEquipmentDailySituationService {

    @Resource
    private ViewVioEquipmentDailySituationMapper viewVioEquipmentDailySituationMapper;

    @Override
    public String createViewVioEquipmentDailySituation(ViewVioEquipmentDailySituationSaveReqVO createReqVO) {
        // 插入
        ViewVioEquipmentDailySituationDO viewVioEquipmentDailySituation = BeanUtils.toBean(createReqVO, ViewVioEquipmentDailySituationDO.class);
        viewVioEquipmentDailySituationMapper.insert(viewVioEquipmentDailySituation);
        // 返回
        return viewVioEquipmentDailySituation.getSbbh();
    }

    @Override
    public void updateViewVioEquipmentDailySituation(ViewVioEquipmentDailySituationSaveReqVO updateReqVO) {
        // 校验存在
        validateViewVioEquipmentDailySituationExists(updateReqVO.getSbbh());
        // 更新
        ViewVioEquipmentDailySituationDO updateObj = BeanUtils.toBean(updateReqVO, ViewVioEquipmentDailySituationDO.class);
        viewVioEquipmentDailySituationMapper.updateById(updateObj);
    }

    @Override
    public void deleteViewVioEquipmentDailySituation(String id) {
        // 校验存在
        validateViewVioEquipmentDailySituationExists(id);
        // 删除
        viewVioEquipmentDailySituationMapper.deleteById(id);
    }

    private void validateViewVioEquipmentDailySituationExists(String id) {
        if (viewVioEquipmentDailySituationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_EQUIPMENT_DAILY_SITUATION_NOT_EXISTS);
        }
    }

    @Override
    public ViewVioEquipmentDailySituationDO getViewVioEquipmentDailySituation(String id) {
        return viewVioEquipmentDailySituationMapper.selectById(id);
    }

    @Override
    public PageResult<ViewVioEquipmentDailySituationDO> getViewVioEquipmentDailySituationPage(ViewVioEquipmentDailySituationPageReqVO pageReqVO) {
        return viewVioEquipmentDailySituationMapper.selectPage(pageReqVO);
    }

}