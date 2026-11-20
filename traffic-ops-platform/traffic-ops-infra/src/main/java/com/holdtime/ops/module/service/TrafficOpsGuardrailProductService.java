package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 护栏产品信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsGuardrailProductService {

    /**
     * 创建护栏产品信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsGuardrailProduct(@Valid TrafficOpsGuardrailProductSaveReqVO createReqVO);

    /**
     * 更新护栏产品信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsGuardrailProduct(@Valid TrafficOpsGuardrailProductSaveReqVO updateReqVO);

    /**
     * 删除护栏产品信息
     *
     * @param id 编号
     */
    void deleteOpsGuardrailProduct(String id);

    /**
     * 获得护栏产品信息
     *
     * @param id 编号
     * @return 护栏产品信息
     */
    TrafficOpsGuardrailProductDO getOpsGuardrailProduct(String id);

    /**
     * 获得护栏产品信息分页
     *
     * @param pageReqVO 分页查询
     * @return 护栏产品信息分页
     */
    PageResult<TrafficOpsGuardrailProductDO> getOpsGuardrailProductPage(TrafficOpsGuardrailProductPageReqVO pageReqVO);

}