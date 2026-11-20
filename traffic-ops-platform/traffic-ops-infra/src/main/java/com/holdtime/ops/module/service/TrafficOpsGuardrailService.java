package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 护栏 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsGuardrailService {

    /**
     * 创建护栏
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsGuardrail(@Valid TrafficOpsGuardrailSaveReqVO createReqVO);

    /**
     * 更新护栏
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsGuardrail(@Valid TrafficOpsGuardrailSaveReqVO updateReqVO);

    /**
     * 删除护栏
     *
     * @param id 编号
     */
    void deleteOpsGuardrail(String id);

    /**
     * 获得护栏
     *
     * @param id 编号
     * @return 护栏
     */
    TrafficOpsGuardrailDO getOpsGuardrail(String id);

    /**
     * 获得护栏分页
     *
     * @param pageReqVO 分页查询
     * @return 护栏分页
     */
    PageResult<TrafficOpsGuardrailDO> getOpsGuardrailPage(TrafficOpsGuardrailPageReqVO pageReqVO);

}