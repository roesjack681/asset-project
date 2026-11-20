package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIntersectionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 交叉口信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsIntersectionService {

    /**
     * 创建交叉口信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsIntersection(@Valid TrafficOpsIntersectionSaveReqVO createReqVO);

    /**
     * 更新交叉口信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsIntersection(@Valid TrafficOpsIntersectionSaveReqVO updateReqVO);

    /**
     * 删除交叉口信息
     *
     * @param id 编号
     */
    void deleteOpsIntersection(String id);

    /**
     * 获得交叉口信息
     *
     * @param id 编号
     * @return 交叉口信息
     */
    TrafficOpsIntersectionDO getOpsIntersection(String id);

    /**
     * 获得交叉口信息分页
     *
     * @param pageReqVO 分页查询
     * @return 交叉口信息分页
     */
    PageResult<TrafficOpsIntersectionDO> getOpsIntersectionPage(TrafficOpsIntersectionPageReqVO pageReqVO);

}