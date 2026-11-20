package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsSmsConfigService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsSmsConfigDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSmsConfigMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 资产短信发送配置 Service 实现类
 *
 * @author 杨丽
 */
@Service
@Validated
public class TrafficOpsSmsConfigServiceImpl implements TrafficOpsSmsConfigService {

    @Resource
    private TrafficOpsSmsConfigMapper opsSmsConfigMapper;

    @Override
    public String createOpsSmsConfig(TrafficOpsSmsConfigSaveReqVO createReqVO) {
        // 插入
        TrafficOpsSmsConfigDO opsSmsConfig = BeanUtils.toBean(createReqVO, TrafficOpsSmsConfigDO.class);
        opsSmsConfigMapper.insert(opsSmsConfig);
        // 返回
        return opsSmsConfig.getId();
    }

    @Override
    public void updateOpsSmsConfig(TrafficOpsSmsConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsSmsConfigExists(updateReqVO.getId());
        // 更新
        TrafficOpsSmsConfigDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsSmsConfigDO.class);
        opsSmsConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsSmsConfig(String id) {
        // 校验存在
        validateOpsSmsConfigExists(id);
        // 删除
        opsSmsConfigMapper.deleteById(id);
    }

    private void validateOpsSmsConfigExists(String id) {
        if (opsSmsConfigMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_SMS_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsSmsConfigDO getOpsSmsConfig(String id) {
        return opsSmsConfigMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsSmsConfigDO> getOpsSmsConfigPage(TrafficOpsSmsConfigPageReqVO pageReqVO) {
        return opsSmsConfigMapper.selectPage(pageReqVO);
    }

}