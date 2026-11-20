package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficEquipPingMonitorResultDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 重点考核设备通断检测 Service 接口
 *
 * @author 管理员
 */
public interface TrafficEquipPingMonitorResultService {

    /**
     * 创建重点考核设备通断检测
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createEquipPingMonitorResult(@Valid TrafficEquipPingMonitorResultSaveReqVO createReqVO);

    /**
     * 更新重点考核设备通断检测
     *
     * @param updateReqVO 更新信息
     */
    void updateEquipPingMonitorResult(@Valid TrafficEquipPingMonitorResultSaveReqVO updateReqVO);

    /**
     * 删除重点考核设备通断检测
     *
     * @param id 编号
     */
    void deleteEquipPingMonitorResult(String id);

    /**
     * 获得重点考核设备通断检测
     *
     * @param id 编号
     * @return 重点考核设备通断检测
     */
    TrafficEquipPingMonitorResultDO getEquipPingMonitorResult(String id);

    /**
     * 获得重点考核设备通断检测分页
     *
     * @param pageReqVO 分页查询
     * @return 重点考核设备通断检测分页
     */
    PageResult<TrafficEquipPingMonitorResultDO> getEquipPingMonitorResultPage(TrafficEquipPingMonitorResultPageReqVO pageReqVO);

}