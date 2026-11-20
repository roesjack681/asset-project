package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.model.vo.*;
import javax.validation.Valid;

/**
 * 领用记录 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsIssueRecordExtService {

    /**
     * 创建领用记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsIssueRecord(@Valid TrafficOpsIssueRecordExtSaveReqVO createReqVO);

    /**
     * 获得领用记录
     *
     * @param id 编号
     * @return 领用记录
     */
    TrafficOpsIssueRecordRespVO getOpsIssueRecord(String id);

    /**
     * 获得领用记录页
     * @param pageReqVO
     * @return
     */
    PageResult<TrafficOpsIssueRecordRespVO> getOpsIssueRecordPage(TrafficOpsIssueRecordPageReqVO pageReqVO);

}