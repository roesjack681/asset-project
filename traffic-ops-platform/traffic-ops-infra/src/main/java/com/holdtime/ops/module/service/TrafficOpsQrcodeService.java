package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 资产二维码信息 Service 接口
 *
 * @author yangli
 */
public interface TrafficOpsQrcodeService {

    /**
     * 创建资产二维码信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsQrcode(@Valid TrafficOpsQrcodeSaveReqVO createReqVO);

    /**
     * 更新资产二维码信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsQrcode(@Valid TrafficOpsQrcodeSaveReqVO updateReqVO);

    /**
     * 删除资产二维码信息
     *
     * @param id 编号
     */
    void deleteOpsQrcode(String id);

    /**
     * 获得资产二维码信息
     *
     * @param id 编号
     * @return 资产二维码信息
     */
    TrafficOpsQrcodeDO getOpsQrcode(String id);

    /**
     * 获得资产二维码信息分页
     *
     * @param pageReqVO 分页查询
     * @return 资产二维码信息分页
     */
    PageResult<TrafficOpsQrcodeDO> getOpsQrcodePage(TrafficOpsQrcodePageReqVO pageReqVO);

}