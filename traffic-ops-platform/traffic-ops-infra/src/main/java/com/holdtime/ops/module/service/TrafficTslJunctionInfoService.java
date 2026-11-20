package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficTslJunctionInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 信控交叉口信息 Service 接口
 *
 * @author yangli
 */
public interface TrafficTslJunctionInfoService {

    /**
     * 创建信控交叉口信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createTslJunctionInfo(@Valid TrafficTslJunctionInfoSaveReqVO createReqVO);

    /**
     * 更新信控交叉口信息
     *
     * @param updateReqVO 更新信息
     */
    void updateTslJunctionInfo(@Valid TrafficTslJunctionInfoSaveReqVO updateReqVO);

    /**
     * 删除信控交叉口信息
     *
     * @param id 编号
     */
    void deleteTslJunctionInfo(String id);

    /**
     * 获得信控交叉口信息
     *
     * @param id 编号
     * @return 信控交叉口信息
     */
    TrafficTslJunctionInfoDO getTslJunctionInfo(String id);

    /**
     * 获得信控交叉口信息分页
     *
     * @param pageReqVO 分页查询
     * @return 信控交叉口信息分页
     */
    PageResult<TrafficTslJunctionInfoDO> getTslJunctionInfoPage(TrafficTslJunctionInfoPageReqVO pageReqVO);

}