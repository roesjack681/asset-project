package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.TrafficOpsIntersectionDO;
import com.holdtime.ops.module.model.vo.TrafficOpsIntersectionPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsIntersectionRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsIntersectionSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 交叉口信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsIntersectionExtService {

    /**
     * 自动完成查询
     * @param intersectionName 路口名称
     * @return 道路信息
     */
    List<TrafficOpsIntersectionRespVO> autocompleteOpsIntersection(String intersectionName);

}