package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeGenRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 资产二维码生成记录 Service 接口
 *
 * @author yangli
 */
public interface TrafficOpsQrcodeGenRecordService {

    /**
     * 创建资产二维码生成记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsQrcodeGenRecord(@Valid TrafficOpsQrcodeGenRecordSaveReqVO createReqVO);

    /**
     * 更新资产二维码生成记录
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsQrcodeGenRecord(@Valid TrafficOpsQrcodeGenRecordSaveReqVO updateReqVO);

    /**
     * 删除资产二维码生成记录
     *
     * @param id 编号
     */
    void deleteOpsQrcodeGenRecord(String id);

    /**
     * 获得资产二维码生成记录
     *
     * @param id 编号
     * @return 资产二维码生成记录
     */
    TrafficOpsQrcodeGenRecordDO getOpsQrcodeGenRecord(String id);

    /**
     * 获得资产二维码生成记录分页
     *
     * @param pageReqVO 分页查询
     * @return 资产二维码生成记录分页
     */
    PageResult<TrafficOpsQrcodeGenRecordDO> getOpsQrcodeGenRecordPage(TrafficOpsQrcodeGenRecordPageReqVO pageReqVO);

}