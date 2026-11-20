package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsElectricPoliceService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 电警 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsElectricPoliceServiceImpl implements TrafficOpsElectricPoliceService {

    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;

    @Override
    public String createOpsElectricPolice(TrafficOpsElectricPoliceSaveReqVO createReqVO) {
        // 插入
        TrafficOpsElectricPoliceDO opsElectricPolice = BeanUtils.toBean(createReqVO, TrafficOpsElectricPoliceDO.class);
        opsElectricPoliceMapper.insert(opsElectricPolice);
        // 返回
        return opsElectricPolice.getId();
    }

    @Override
    public void updateOpsElectricPolice(TrafficOpsElectricPoliceSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsElectricPoliceExists(updateReqVO.getId());
        // 更新
        TrafficOpsElectricPoliceDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsElectricPoliceDO.class);
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
            throw exception(OpsErrorCodeConstants.OPS_ELECTRIC_POLICE_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsElectricPoliceDO getOpsElectricPolice(String id) {
        return opsElectricPoliceMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsElectricPoliceDO> getOpsElectricPolicePage(TrafficOpsElectricPolicePageReqVO pageReqVO) {
        return opsElectricPoliceMapper.selectPage(pageReqVO);
    }

}