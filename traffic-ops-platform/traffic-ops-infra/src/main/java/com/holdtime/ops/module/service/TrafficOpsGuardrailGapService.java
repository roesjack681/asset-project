package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailGapDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 护栏开口信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsGuardrailGapService {

    /**
     * 创建护栏开口信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsGuardrailGap(@Valid TrafficOpsGuardrailGapSaveReqVO createReqVO);

    /**
     * 更新护栏开口信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsGuardrailGap(@Valid TrafficOpsGuardrailGapSaveReqVO updateReqVO);

    /**
     * 删除护栏开口信息
     *
     * @param id 编号
     */
    void deleteOpsGuardrailGap(String id);

    /**
     * 获得护栏开口信息
     *
     * @param id 编号
     * @return 护栏开口信息
     */
    TrafficOpsGuardrailGapDO getOpsGuardrailGap(String id);

    /**
     * 获得护栏开口信息分页
     *
     * @param pageReqVO 分页查询
     * @return 护栏开口信息分页
     */
    PageResult<TrafficOpsGuardrailGapDO> getOpsGuardrailGapPage(TrafficOpsGuardrailGapPageReqVO pageReqVO);

}