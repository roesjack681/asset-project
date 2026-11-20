package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsUrgencySettingsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsUrgencySettingsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsUrgencySettingsMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 维保紧急程度配置 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsUrgencySettingsServiceImpl implements TrafficOpsUrgencySettingsService {

    @Resource
    private TrafficOpsUrgencySettingsMapper opsUrgencySettingsMapper;

    @Override
    public String createOpsUrgencySettings(TrafficOpsUrgencySettingsSaveReqVO createReqVO) {
        // 插入
        TrafficOpsUrgencySettingsDO opsUrgencySettings = BeanUtils.toBean(createReqVO, TrafficOpsUrgencySettingsDO.class);
        opsUrgencySettingsMapper.insert(opsUrgencySettings);
        // 返回
        return opsUrgencySettings.getId();
    }

    @Override
    public void updateOpsUrgencySettings(TrafficOpsUrgencySettingsSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsUrgencySettingsExists(updateReqVO.getId());
        // 更新
        TrafficOpsUrgencySettingsDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsUrgencySettingsDO.class);
        opsUrgencySettingsMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsUrgencySettings(String id) {
        // 校验存在
        validateOpsUrgencySettingsExists(id);
        // 删除
        opsUrgencySettingsMapper.deleteById(id);
    }

    private void validateOpsUrgencySettingsExists(String id) {
        if (opsUrgencySettingsMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_URGENCY_SETTINGS_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsUrgencySettingsDO getOpsUrgencySettings(String id) {
        return opsUrgencySettingsMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsUrgencySettingsDO> getOpsUrgencySettingsPage(TrafficOpsUrgencySettingsPageReqVO pageReqVO) {
        return opsUrgencySettingsMapper.selectPage(pageReqVO);
    }

}