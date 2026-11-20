package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsPoliceMaintenanceDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsPoliceMaintenanceDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsPoliceMaintenanceDetailMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 警用装备报修详情信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsPoliceMaintenanceDetailServiceImpl implements TrafficOpsPoliceMaintenanceDetailService {

    @Resource
    private TrafficOpsPoliceMaintenanceDetailMapper opsPoliceMaintenanceDetailMapper;

    @Override
    public String createOpsPoliceMaintenanceDetail(TrafficOpsPoliceMaintenanceDetailSaveReqVO createReqVO) {
        // 插入
        TrafficOpsPoliceMaintenanceDetailDO opsPoliceMaintenanceDetail = BeanUtils.toBean(createReqVO, TrafficOpsPoliceMaintenanceDetailDO.class);
        opsPoliceMaintenanceDetailMapper.insert(opsPoliceMaintenanceDetail);
        // 返回
        return opsPoliceMaintenanceDetail.getId();
    }

    @Override
    public void updateOpsPoliceMaintenanceDetail(TrafficOpsPoliceMaintenanceDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsPoliceMaintenanceDetailExists(updateReqVO.getId());
        // 更新
        TrafficOpsPoliceMaintenanceDetailDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsPoliceMaintenanceDetailDO.class);
        opsPoliceMaintenanceDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsPoliceMaintenanceDetail(String id) {
        // 校验存在
        validateOpsPoliceMaintenanceDetailExists(id);
        // 删除
        opsPoliceMaintenanceDetailMapper.deleteById(id);
    }

    private void validateOpsPoliceMaintenanceDetailExists(String id) {
        if (opsPoliceMaintenanceDetailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_POLICE_MAINTENANCE_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsPoliceMaintenanceDetailDO getOpsPoliceMaintenanceDetail(String id) {
        return opsPoliceMaintenanceDetailMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsPoliceMaintenanceDetailDO> getOpsPoliceMaintenanceDetailPage(TrafficOpsPoliceMaintenanceDetailPageReqVO pageReqVO) {
        return opsPoliceMaintenanceDetailMapper.selectPage(pageReqVO);
    }

}