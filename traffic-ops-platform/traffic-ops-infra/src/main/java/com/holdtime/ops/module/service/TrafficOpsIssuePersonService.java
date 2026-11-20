package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIssuePersonDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备领用人信息 Service 接口
 *
 * @author 杨丽
 */
public interface TrafficOpsIssuePersonService {

    /**
     * 创建设备领用人信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsIssuePerson(@Valid TrafficOpsIssuePersonSaveReqVO createReqVO);

    /**
     * 更新设备领用人信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsIssuePerson(@Valid TrafficOpsIssuePersonSaveReqVO updateReqVO);

    /**
     * 删除设备领用人信息
     *
     * @param id 编号
     */
    void deleteOpsIssuePerson(String id);

    /**
     * 获得设备领用人信息
     *
     * @param id 编号
     * @return 设备领用人信息
     */
    TrafficOpsIssuePersonDO getOpsIssuePerson(String id);

    /**
     * 获得设备领用人信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备领用人信息分页
     */
    PageResult<TrafficOpsIssuePersonDO> getOpsIssuePersonPage(TrafficOpsIssuePersonPageReqVO pageReqVO);

}