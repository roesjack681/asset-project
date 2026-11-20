package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailRProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 护栏关联产品信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsGuardrailRProductService {

    /**
     * 创建护栏关联产品信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsGuardrailRProduct(@Valid TrafficOpsGuardrailRProductSaveReqVO createReqVO);

    /**
     * 更新护栏关联产品信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsGuardrailRProduct(@Valid TrafficOpsGuardrailRProductSaveReqVO updateReqVO);

    /**
     * 删除护栏关联产品信息
     *
     * @param id 编号
     */
    void deleteOpsGuardrailRProduct(String id);

    /**
     * 获得护栏关联产品信息
     *
     * @param id 编号
     * @return 护栏关联产品信息
     */
    TrafficOpsGuardrailRProductDO getOpsGuardrailRProduct(String id);

    /**
     * 获得护栏关联产品信息分页
     *
     * @param pageReqVO 分页查询
     * @return 护栏关联产品信息分页
     */
    PageResult<TrafficOpsGuardrailRProductDO> getOpsGuardrailRProductPage(TrafficOpsGuardrailRProductPageReqVO pageReqVO);

}