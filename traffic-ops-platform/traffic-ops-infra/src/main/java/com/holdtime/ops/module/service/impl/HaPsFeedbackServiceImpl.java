package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.HaPsFeedbackService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.HaPsFeedbackDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.HaPsFeedbackMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 方案展示留言记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class HaPsFeedbackServiceImpl implements HaPsFeedbackService {

    @Resource
    private HaPsFeedbackMapper haPsFeedbackMapper;

    @Override
    public String createHaPsFeedback(HaPsFeedbackSaveReqVO createReqVO) {
        // 插入
        HaPsFeedbackDO haPsFeedback = BeanUtils.toBean(createReqVO, HaPsFeedbackDO.class);
        haPsFeedbackMapper.insert(haPsFeedback);
        // 返回
        return haPsFeedback.getId();
    }

    @Override
    public void updateHaPsFeedback(HaPsFeedbackSaveReqVO updateReqVO) {
        // 校验存在
        validateHaPsFeedbackExists(updateReqVO.getId());
        // 更新
        HaPsFeedbackDO updateObj = BeanUtils.toBean(updateReqVO, HaPsFeedbackDO.class);
        haPsFeedbackMapper.updateById(updateObj);
    }

    @Override
    public void deleteHaPsFeedback(String id) {
        // 校验存在
        validateHaPsFeedbackExists(id);
        // 删除
        haPsFeedbackMapper.deleteById(id);
    }

    private void validateHaPsFeedbackExists(String id) {
        if (haPsFeedbackMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.HA_PS_FEEDBACK_NOT_EXISTS);
        }
    }

    @Override
    public HaPsFeedbackDO getHaPsFeedback(String id) {
        return haPsFeedbackMapper.selectById(id);
    }

    @Override
    public PageResult<HaPsFeedbackDO> getHaPsFeedbackPage(HaPsFeedbackPageReqVO pageReqVO) {
        return haPsFeedbackMapper.selectPage(pageReqVO);
    }

}