package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.entity.TrafficOpsIntersectionDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIntersectionMapper;
import com.holdtime.ops.module.model.vo.TrafficOpsIntersectionRespVO;
import com.holdtime.ops.module.service.TrafficOpsIntersectionExtService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.List;


/**
 * 交叉口信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsIntersectionExtServiceImpl implements TrafficOpsIntersectionExtService {

    @Resource
    private TrafficOpsIntersectionMapper trafficOpsIntersectionMapper;

    @Override
    public List<TrafficOpsIntersectionRespVO> autocompleteOpsIntersection(String intersectionName) {
        LambdaQueryWrapperX<TrafficOpsIntersectionDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.likeIfPresent(TrafficOpsIntersectionDO::getLdmc, intersectionName);

        Page<TrafficOpsIntersectionDO> page = new Page<>(1, 20);
        List<TrafficOpsIntersectionDO> list = trafficOpsIntersectionMapper.selectPage(page, lambdaQueryWrapperX).getRecords();

        return BeanUtils.toBean(list, TrafficOpsIntersectionRespVO.class);
    }

}