package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsRegisterService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsRegisterDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsRegisterMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 人员注册绑定信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsRegisterServiceImpl implements TrafficOpsRegisterService {

    @Resource
    private TrafficOpsRegisterMapper opsRegisterMapper;

    @Override
    public String createOpsRegister(AppTrafficOpsRegisterSaveReqVO createReqVO) {
        // 插入
        TrafficOpsRegisterDO opsRegister = BeanUtils.toBean(createReqVO, TrafficOpsRegisterDO.class);
        opsRegisterMapper.insert(opsRegister);
        // 返回
        return opsRegister.getId();
    }

    @Override
    public void updateOpsRegister(AppTrafficOpsRegisterSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsRegisterExists(updateReqVO.getId());
        // 更新
        TrafficOpsRegisterDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsRegisterDO.class);
        opsRegisterMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsRegister(String id) {
        // 校验存在
        validateOpsRegisterExists(id);
        // 删除
        opsRegisterMapper.deleteById(id);
    }

    private void validateOpsRegisterExists(String id) {
        if (opsRegisterMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_REGISTER_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsRegisterDO getOpsRegister(String id) {
        return opsRegisterMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsRegisterDO> getOpsRegisterPage(AppTrafficOpsRegisterPageReqVO pageReqVO) {
        return opsRegisterMapper.selectPage(pageReqVO);
    }

}