package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficTslJunctionInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficTslJunctionInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 信控交叉口信息 Service 实现类
 *
 * @author yangli
 */
@Service
@Validated
public class TrafficTslJunctionInfoServiceImpl implements TrafficTslJunctionInfoService {

    @Resource
    private TrafficTslJunctionInfoMapper tslJunctionInfoMapper;

    @Override
    public String createTslJunctionInfo(TrafficTslJunctionInfoSaveReqVO createReqVO) {
        // 插入
        TrafficTslJunctionInfoDO tslJunctionInfo = BeanUtils.toBean(createReqVO, TrafficTslJunctionInfoDO.class);
        tslJunctionInfoMapper.insert(tslJunctionInfo);
        // 返回
        return tslJunctionInfo.getId();
    }

    @Override
    public void updateTslJunctionInfo(TrafficTslJunctionInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateTslJunctionInfoExists(updateReqVO.getId());
        // 更新
        TrafficTslJunctionInfoDO updateObj = BeanUtils.toBean(updateReqVO, TrafficTslJunctionInfoDO.class);
        tslJunctionInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteTslJunctionInfo(String id) {
        // 校验存在
        validateTslJunctionInfoExists(id);
        // 删除
        tslJunctionInfoMapper.deleteById(id);
    }

    private void validateTslJunctionInfoExists(String id) {
        if (tslJunctionInfoMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.TSL_JUNCTION_INFO_NOT_EXISTS);
        }
    }

    @Override
    public TrafficTslJunctionInfoDO getTslJunctionInfo(String id) {
        return tslJunctionInfoMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficTslJunctionInfoDO> getTslJunctionInfoPage(TrafficTslJunctionInfoPageReqVO pageReqVO) {
        return tslJunctionInfoMapper.selectPage(pageReqVO);
    }

}