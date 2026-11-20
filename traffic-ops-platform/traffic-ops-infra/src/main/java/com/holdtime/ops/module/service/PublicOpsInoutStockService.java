package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsInoutStockDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备出入库管理 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsInoutStockService {

    /**
     * 创建设备出入库管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsInoutStock(@Valid PublicOpsInoutStockSaveReqVO createReqVO);

    /**
     * 更新设备出入库管理
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsInoutStock(@Valid PublicOpsInoutStockSaveReqVO updateReqVO);

    /**
     * 删除设备出入库管理
     *
     * @param id 编号
     */
    void deleteOpsInoutStock(String id);

    /**
     * 获得设备出入库管理
     *
     * @param id 编号
     * @return 设备出入库管理
     */
    PublicOpsInoutStockDO getOpsInoutStock(String id);

    /**
     * 获得设备出入库管理分页
     *
     * @param pageReqVO 分页查询
     * @return 设备出入库管理分页
     */
    PageResult<PublicOpsInoutStockDO> getOpsInoutStockPage(PublicOpsInoutStockPageReqVO pageReqVO);

}