package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsIntersectionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIntersectionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import com.holdtime.ops.module.entity.mapper.TrafficOpsIntersectionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 交叉口信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsIntersectionServiceImpl implements TrafficOpsIntersectionService {

    @Resource
    private TrafficOpsIntersectionMapper opsIntersectionMapper;

    @Override
    public String createOpsIntersection(TrafficOpsIntersectionSaveReqVO createReqVO) {
        // 插入
        TrafficOpsIntersectionDO opsIntersection = BeanUtils.toBean(createReqVO, TrafficOpsIntersectionDO.class);
        opsIntersectionMapper.insert(opsIntersection);
        // 返回
        return opsIntersection.getId();
    }

    @Override
    public void updateOpsIntersection(TrafficOpsIntersectionSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsIntersectionExists(updateReqVO.getId());
        // 更新
        TrafficOpsIntersectionDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsIntersectionDO.class);
        opsIntersectionMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsIntersection(String id) {
        // 校验存在
        validateOpsIntersectionExists(id);
        // 删除
        opsIntersectionMapper.deleteById(id);
    }

    private void validateOpsIntersectionExists(String id) {
        if (opsIntersectionMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_INTERSECTION_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsIntersectionDO getOpsIntersection(String id) {
        return opsIntersectionMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsIntersectionDO> getOpsIntersectionPage(TrafficOpsIntersectionPageReqVO pageReqVO) {
        return opsIntersectionMapper.selectPage(pageReqVO);
    }

}