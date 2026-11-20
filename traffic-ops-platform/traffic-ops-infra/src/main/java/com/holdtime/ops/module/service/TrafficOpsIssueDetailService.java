package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIssueDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 领用详情 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsIssueDetailService {

    /**
     * 创建领用详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsIssueDetail(@Valid TrafficOpsIssueDetailSaveReqVO createReqVO);

    /**
     * 更新领用详情
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsIssueDetail(@Valid TrafficOpsIssueDetailSaveReqVO updateReqVO);

    /**
     * 删除领用详情
     *
     * @param id 编号
     */
    void deleteOpsIssueDetail(String id);

    /**
     * 获得领用详情
     *
     * @param id 编号
     * @return 领用详情
     */
    TrafficOpsIssueDetailDO getOpsIssueDetail(String id);

    /**
     * 获得领用详情分页
     *
     * @param pageReqVO 分页查询
     * @return 领用详情分页
     */
    PageResult<TrafficOpsIssueDetailDO> getOpsIssueDetailPage(TrafficOpsIssueDetailPageReqVO pageReqVO);

}