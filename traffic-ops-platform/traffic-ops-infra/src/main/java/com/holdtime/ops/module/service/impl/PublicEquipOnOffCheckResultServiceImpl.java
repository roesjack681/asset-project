package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicEquipOnOffCheckResultService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicEquipOnOffCheckResultDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PublicEquipOnOffCheckResultMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备通断测结果 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicEquipOnOffCheckResultServiceImpl implements PublicEquipOnOffCheckResultService {

    @Resource
    private PublicEquipOnOffCheckResultMapper equipOnOffCheckResultMapper;

    @Override
    public String createEquipOnOffCheckResult(PublicEquipOnOffCheckResultSaveReqVO createReqVO) {
        // 插入
        PublicEquipOnOffCheckResultDO equipOnOffCheckResult = BeanUtils.toBean(createReqVO, PublicEquipOnOffCheckResultDO.class);
        equipOnOffCheckResultMapper.insert(equipOnOffCheckResult);
        // 返回
        return equipOnOffCheckResult.getId();
    }

    @Override
    public void updateEquipOnOffCheckResult(PublicEquipOnOffCheckResultSaveReqVO updateReqVO) {
        // 校验存在
        validateEquipOnOffCheckResultExists(updateReqVO.getId());
        // 更新
        PublicEquipOnOffCheckResultDO updateObj = BeanUtils.toBean(updateReqVO, PublicEquipOnOffCheckResultDO.class);
        equipOnOffCheckResultMapper.updateById(updateObj);
    }

    @Override
    public void deleteEquipOnOffCheckResult(String id) {
        // 校验存在
        validateEquipOnOffCheckResultExists(id);
        // 删除
        equipOnOffCheckResultMapper.deleteById(id);
    }

    private void validateEquipOnOffCheckResultExists(String id) {
        if (equipOnOffCheckResultMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.EQUIP_ON_OFF_CHECK_RESULT_NOT_EXISTS);
        }
    }

    @Override
    public PublicEquipOnOffCheckResultDO getEquipOnOffCheckResult(String id) {
        return equipOnOffCheckResultMapper.selectById(id);
    }

    @Override
    public PageResult<PublicEquipOnOffCheckResultDO> getEquipOnOffCheckResultPage(PublicEquipOnOffCheckResultPageReqVO pageReqVO) {
        return equipOnOffCheckResultMapper.selectPage(pageReqVO);
    }

}