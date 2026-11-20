package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintainFlowRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备维保全流程 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsMaintainFlowRecordService {

    /**
     * 创建设备维保全流程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintainFlowRecord(@Valid TrafficOpsMaintainFlowRecordSaveReqVO createReqVO);

    /**
     * 更新设备维保全流程
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintainFlowRecord(@Valid TrafficOpsMaintainFlowRecordSaveReqVO updateReqVO);

    /**
     * 删除设备维保全流程
     *
     * @param id 编号
     */
    void deleteOpsMaintainFlowRecord(String id);

    /**
     * 获得设备维保全流程
     *
     * @param id 编号
     * @return 设备维保全流程
     */
    TrafficOpsMaintainFlowRecordDO getOpsMaintainFlowRecord(String id);

    /**
     * 获得设备维保全流程分页
     *
     * @param pageReqVO 分页查询
     * @return 设备维保全流程分页
     */
    PageResult<TrafficOpsMaintainFlowRecordDO> getOpsMaintainFlowRecordPage(TrafficOpsMaintainFlowRecordPageReqVO pageReqVO);

}