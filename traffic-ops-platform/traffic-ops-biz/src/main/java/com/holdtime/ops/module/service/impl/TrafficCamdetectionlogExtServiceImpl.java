package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.TrafficCamdetectionlogDO;
import com.holdtime.ops.module.entity.mapper.TrafficCamdetectionlogMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.TrafficCamdetectionlogPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficCamdetectionlogSaveReqVO;
import com.holdtime.ops.module.service.TrafficCamdetectionlogExtService;
import com.holdtime.ops.module.service.TrafficCamdetectionlogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备状态检测 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficCamdetectionlogExtServiceImpl implements TrafficCamdetectionlogExtService {

    @Resource
    private TrafficCamdetectionlogMapper camdetectionlogMapper;

    @Override
    public Long createCamdetectionlog(TrafficCamdetectionlogSaveReqVO createReqVO) {
        // 插入
        TrafficCamdetectionlogDO camdetectionlog = BeanUtils.toBean(createReqVO, TrafficCamdetectionlogDO.class);
        camdetectionlogMapper.insert(camdetectionlog);
        // 返回
        return null;
    }


}