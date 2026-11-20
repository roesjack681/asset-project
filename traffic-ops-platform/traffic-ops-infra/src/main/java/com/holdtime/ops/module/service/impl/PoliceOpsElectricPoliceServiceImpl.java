package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceOpsElectricPoliceService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 电警 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PoliceOpsElectricPoliceServiceImpl implements PoliceOpsElectricPoliceService {

    @Resource
    private PoliceOpsElectricPoliceMapper opsElectricPoliceMapper;

    @Override
    public String createOpsElectricPolice(PoliceOpsElectricPoliceSaveReqVO createReqVO) {
        // 插入
        PoliceOpsElectricPoliceDO opsElectricPolice = BeanUtils.toBean(createReqVO, PoliceOpsElectricPoliceDO.class);
        opsElectricPoliceMapper.insert(opsElectricPolice);
        // 返回
        return opsElectricPolice.getId();
    }

    @Override
    public void updateOpsElectricPolice(PoliceOpsElectricPoliceSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsElectricPoliceExists(updateReqVO.getId());
        // 更新
        PoliceOpsElectricPoliceDO updateObj = BeanUtils.toBean(updateReqVO, PoliceOpsElectricPoliceDO.class);
        opsElectricPoliceMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsElectricPolice(String id) {
        // 校验存在
        validateOpsElectricPoliceExists(id);
        // 删除
        opsElectricPoliceMapper.deleteById(id);
    }

    private void validateOpsElectricPoliceExists(String id) {
        if (opsElectricPoliceMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.POLICE_OPS_ELECTRIC_POLICE_NOT_EXISTS);
        }
    }

    @Override
    public PoliceOpsElectricPoliceDO getOpsElectricPolice(String id) {
        return opsElectricPoliceMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceOpsElectricPoliceDO> getOpsElectricPolicePage(PoliceOpsElectricPolicePageReqVO pageReqVO) {
        return opsElectricPoliceMapper.selectPage(pageReqVO);
    }

}