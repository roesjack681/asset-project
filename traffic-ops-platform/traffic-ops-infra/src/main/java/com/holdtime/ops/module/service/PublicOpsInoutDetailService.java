package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsInoutDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备出入库存明细 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsInoutDetailService {

    /**
     * 创建设备出入库存明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsInoutDetail(@Valid PublicOpsInoutDetailSaveReqVO createReqVO);

    /**
     * 更新设备出入库存明细
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsInoutDetail(@Valid PublicOpsInoutDetailSaveReqVO updateReqVO);

    /**
     * 删除设备出入库存明细
     *
     * @param id 编号
     */
    void deleteOpsInoutDetail(String id);

    /**
     * 获得设备出入库存明细
     *
     * @param id 编号
     * @return 设备出入库存明细
     */
    PublicOpsInoutDetailDO getOpsInoutDetail(String id);

    /**
     * 获得设备出入库存明细分页
     *
     * @param pageReqVO 分页查询
     * @return 设备出入库存明细分页
     */
    PageResult<PublicOpsInoutDetailDO> getOpsInoutDetailPage(PublicOpsInoutDetailPageReqVO pageReqVO);

}