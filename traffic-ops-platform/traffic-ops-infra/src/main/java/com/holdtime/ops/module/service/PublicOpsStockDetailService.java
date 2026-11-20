package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsStockDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备库存明细 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsStockDetailService {

    /**
     * 创建设备库存明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsStockDetail(@Valid PublicOpsStockDetailSaveReqVO createReqVO);

    /**
     * 更新设备库存明细
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsStockDetail(@Valid PublicOpsStockDetailSaveReqVO updateReqVO);

    /**
     * 删除设备库存明细
     *
     * @param id 编号
     */
    void deleteOpsStockDetail(String id);

    /**
     * 获得设备库存明细
     *
     * @param id 编号
     * @return 设备库存明细
     */
    PublicOpsStockDetailDO getOpsStockDetail(String id);

    /**
     * 获得设备库存明细分页
     *
     * @param pageReqVO 分页查询
     * @return 设备库存明细分页
     */
    PageResult<PublicOpsStockDetailDO> getOpsStockDetailPage(PublicOpsStockDetailPageReqVO pageReqVO);

}