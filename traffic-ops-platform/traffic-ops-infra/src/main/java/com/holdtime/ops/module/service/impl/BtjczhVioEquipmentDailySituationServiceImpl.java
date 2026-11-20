package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.BtjczhVioEquipmentDailySituationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.BtjczhVioEquipmentDailySituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.BtjczhVioEquipmentDailySituationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 集指设备每日情况 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BtjczhVioEquipmentDailySituationServiceImpl implements BtjczhVioEquipmentDailySituationService {

    @Resource
    private BtjczhVioEquipmentDailySituationMapper btjczhVioEquipmentDailySituationMapper;

    @Override
    public String createBtjczhVioEquipmentDailySituation(BtjczhVioEquipmentDailySituationSaveReqVO createReqVO) {
        // 插入
        BtjczhVioEquipmentDailySituationDO btjczhVioEquipmentDailySituation = BeanUtils.toBean(createReqVO, BtjczhVioEquipmentDailySituationDO.class);
        btjczhVioEquipmentDailySituationMapper.insert(btjczhVioEquipmentDailySituation);
        // 返回
        return btjczhVioEquipmentDailySituation.getId();
    }

    @Override
    public void updateBtjczhVioEquipmentDailySituation(BtjczhVioEquipmentDailySituationSaveReqVO updateReqVO) {
        // 校验存在
        validateBtjczhVioEquipmentDailySituationExists(updateReqVO.getId());
        // 更新
        BtjczhVioEquipmentDailySituationDO updateObj = BeanUtils.toBean(updateReqVO, BtjczhVioEquipmentDailySituationDO.class);
        btjczhVioEquipmentDailySituationMapper.updateById(updateObj);
    }

    @Override
    public void deleteBtjczhVioEquipmentDailySituation(String id) {
        // 校验存在
        validateBtjczhVioEquipmentDailySituationExists(id);
        // 删除
        btjczhVioEquipmentDailySituationMapper.deleteById(id);
    }

    private void validateBtjczhVioEquipmentDailySituationExists(String id) {
        if (btjczhVioEquipmentDailySituationMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.BTJCZH_VIO_EQUIPMENT_DAILY_SITUATION_NOT_EXISTS);
        }
    }

    @Override
    public BtjczhVioEquipmentDailySituationDO getBtjczhVioEquipmentDailySituation(String id) {
        return btjczhVioEquipmentDailySituationMapper.selectById(id);
    }

    @Override
    public PageResult<BtjczhVioEquipmentDailySituationDO> getBtjczhVioEquipmentDailySituationPage(BtjczhVioEquipmentDailySituationPageReqVO pageReqVO) {
        return btjczhVioEquipmentDailySituationMapper.selectPage(pageReqVO);
    }

}