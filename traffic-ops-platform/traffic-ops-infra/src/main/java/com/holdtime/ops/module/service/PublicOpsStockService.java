package com.holdtime.ops.module.service;

import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsStockDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 设备库存管理 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsStockService {

    /**
     * 创建设备库存管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsStock(@Valid PublicOpsStockSaveReqVO createReqVO);

    /**
     * 更新设备库存管理
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsStock(@Valid PublicOpsStockSaveReqVO updateReqVO);

    /**
     * 删除设备库存管理
     *
     * @param id 编号
     */
    void deleteOpsStock(String id);

    /**
     * 获得设备库存管理
     *
     * @param id 编号
     * @return 设备库存管理
     */
    PublicOpsStockDO getOpsStock(String id);

    /**
     * 获得设备库存管理分页
     *
     * @param pageReqVO 分页查询
     * @return 设备库存管理分页
     */
    PageResult<PublicOpsStockDO> getOpsStockPage(PublicOpsStockPageReqVO pageReqVO);

}