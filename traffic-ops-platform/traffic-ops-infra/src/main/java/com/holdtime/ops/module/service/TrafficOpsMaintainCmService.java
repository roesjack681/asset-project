package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintainCmDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备维保耗材 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsMaintainCmService {

    /**
     * 创建设备维保耗材
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintainCm(@Valid AppTrafficOpsMaintainCmSaveReqVO createReqVO);

    /**
     * 更新设备维保耗材
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintainCm(@Valid AppTrafficOpsMaintainCmSaveReqVO updateReqVO);

    /**
     * 删除设备维保耗材
     *
     * @param id 编号
     */
    void deleteOpsMaintainCm(String id);

    /**
     * 获得设备维保耗材
     *
     * @param id 编号
     * @return 设备维保耗材
     */
    TrafficOpsMaintainCmDO getOpsMaintainCm(String id);

    /**
     * 获得设备维保耗材分页
     *
     * @param pageReqVO 分页查询
     * @return 设备维保耗材分页
     */
    PageResult<TrafficOpsMaintainCmDO> getOpsMaintainCmPage(AppTrafficOpsMaintainCmPageReqVO pageReqVO);

}