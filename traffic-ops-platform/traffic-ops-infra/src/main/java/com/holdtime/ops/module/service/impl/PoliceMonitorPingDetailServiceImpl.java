package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceMonitorPingDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorPingDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.PoliceMonitorPingDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 公安监测通达详情 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorPingDetailServiceImpl implements PoliceMonitorPingDetailService {

    @Resource
    private PoliceMonitorPingDetailMapper monitorPingDetailMapper;

    @Override
    public String createMonitorPingDetail(PoliceMonitorPingDetailSaveReqVO createReqVO) {
        // 插入
        PoliceMonitorPingDetailDO monitorPingDetail = BeanUtils.toBean(createReqVO, PoliceMonitorPingDetailDO.class);
        monitorPingDetailMapper.insert(monitorPingDetail);
        // 返回
        return monitorPingDetail.getId();
    }

    @Override
    public void updateMonitorPingDetail(PoliceMonitorPingDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateMonitorPingDetailExists(updateReqVO.getId());
        // 更新
        PoliceMonitorPingDetailDO updateObj = BeanUtils.toBean(updateReqVO, PoliceMonitorPingDetailDO.class);
        monitorPingDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonitorPingDetail(String id) {
        // 校验存在
        validateMonitorPingDetailExists(id);
        // 删除
        monitorPingDetailMapper.deleteById(id);
    }

    private void validateMonitorPingDetailExists(String id) {
        if (monitorPingDetailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.POLICE_MONITOR_PING_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public PoliceMonitorPingDetailDO getMonitorPingDetail(String id) {
        return monitorPingDetailMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceMonitorPingDetailDO> getMonitorPingDetailPage(PoliceMonitorPingDetailPageReqVO pageReqVO) {
        return monitorPingDetailMapper.selectPage(pageReqVO);
    }

}