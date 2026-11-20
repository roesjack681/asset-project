package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIssueHistoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 警用装备历史领用记录 Service 接口
 *
 * @author 杨丽
 */
public interface TrafficOpsIssueHistoryService {

    /**
     * 创建警用装备历史领用记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsIssueHistory(@Valid TrafficOpsIssueHistorySaveReqVO createReqVO);

    /**
     * 更新警用装备历史领用记录
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsIssueHistory(@Valid TrafficOpsIssueHistorySaveReqVO updateReqVO);

    /**
     * 删除警用装备历史领用记录
     *
     * @param id 编号
     */
    void deleteOpsIssueHistory(String id);

    /**
     * 获得警用装备历史领用记录
     *
     * @param id 编号
     * @return 警用装备历史领用记录
     */
    TrafficOpsIssueHistoryDO getOpsIssueHistory(String id);

    /**
     * 获得警用装备历史领用记录分页
     *
     * @param pageReqVO 分页查询
     * @return 警用装备历史领用记录分页
     */
    PageResult<TrafficOpsIssueHistoryDO> getOpsIssueHistoryPage(TrafficOpsIssueHistoryPageReqVO pageReqVO);

}