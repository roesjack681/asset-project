package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIssueRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 领用记录 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsIssueRecordService {

    /**
     * 创建领用记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsIssueRecord(@Valid TrafficOpsIssueRecordSaveReqVO createReqVO);

    /**
     * 更新领用记录
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsIssueRecord(@Valid TrafficOpsIssueRecordSaveReqVO updateReqVO);

    /**
     * 删除领用记录
     *
     * @param id 编号
     */
    void deleteOpsIssueRecord(String id);

    /**
     * 获得领用记录
     *
     * @param id 编号
     * @return 领用记录
     */
    TrafficOpsIssueRecordDO getOpsIssueRecord(String id);

    /**
     * 获得领用记录分页
     *
     * @param pageReqVO 分页查询
     * @return 领用记录分页
     */
    PageResult<TrafficOpsIssueRecordDO> getOpsIssueRecordPage(TrafficOpsIssueRecordPageReqVO pageReqVO);

}