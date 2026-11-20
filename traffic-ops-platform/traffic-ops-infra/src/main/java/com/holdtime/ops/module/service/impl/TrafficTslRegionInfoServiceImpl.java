package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficTslRegionInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslRegionInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficTslRegionInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信控区域信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficTslRegionInfoServiceImpl implements TrafficTslRegionInfoService {

    @Resource
    private TrafficTslRegionInfoMapper tslRegionInfoMapper;

    @Override
    public String createTslRegionInfo(TrafficTslRegionInfoSaveReqVO createReqVO) {
        // 插入
        TrafficTslRegionInfoDO tslRegionInfo = BeanUtils.toBean(createReqVO, TrafficTslRegionInfoDO.class);
        tslRegionInfoMapper.insert(tslRegionInfo);
        // 返回
        return tslRegionInfo.getId();
    }

    @Override
    public void updateTslRegionInfo(TrafficTslRegionInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateTslRegionInfoExists(updateReqVO.getId());
        // 更新
        TrafficTslRegionInfoDO updateObj = BeanUtils.toBean(updateReqVO, TrafficTslRegionInfoDO.class);
        tslRegionInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteTslRegionInfo(String id) {
        // 校验存在
        validateTslRegionInfoExists(id);
        // 删除
        tslRegionInfoMapper.deleteById(id);
    }

    private void validateTslRegionInfoExists(String id) {
        if (tslRegionInfoMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.TSL_REGION_INFO_NOT_EXISTS);
        }
    }

    @Override
    public TrafficTslRegionInfoDO getTslRegionInfo(String id) {
        return tslRegionInfoMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficTslRegionInfoDO> getTslRegionInfoPage(TrafficTslRegionInfoPageReqVO pageReqVO) {
        return tslRegionInfoMapper.selectPage(pageReqVO);
    }

}