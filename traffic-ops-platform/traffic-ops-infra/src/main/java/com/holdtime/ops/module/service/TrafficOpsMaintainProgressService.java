package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintainProgressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备维保进度 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsMaintainProgressService {

    /**
     * 创建设备维保进度
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMaintainProgress(@Valid AppTrafficOpsMaintainProgressSaveReqVO createReqVO);

    /**
     * 更新设备维保进度
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMaintainProgress(@Valid AppTrafficOpsMaintainProgressSaveReqVO updateReqVO);

    /**
     * 删除设备维保进度
     *
     * @param id 编号
     */
    void deleteOpsMaintainProgress(String id);

    /**
     * 获得设备维保进度
     *
     * @param id 编号
     * @return 设备维保进度
     */
    TrafficOpsMaintainProgressDO getOpsMaintainProgress(String id);

    /**
     * 获得设备维保进度分页
     *
     * @param pageReqVO 分页查询
     * @return 设备维保进度分页
     */
    PageResult<TrafficOpsMaintainProgressDO> getOpsMaintainProgressPage(AppTrafficOpsMaintainProgressPageReqVO pageReqVO);

}