package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsRadarDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 雷达 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsRadarService {

    /**
     * 创建雷达
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsRadar(@Valid TrafficOpsRadarSaveReqVO createReqVO);

    /**
     * 更新雷达
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsRadar(@Valid TrafficOpsRadarSaveReqVO updateReqVO);

    /**
     * 删除雷达
     *
     * @param id 编号
     */
    void deleteOpsRadar(String id);

    /**
     * 获得雷达
     *
     * @param id 编号
     * @return 雷达
     */
    TrafficOpsRadarDO getOpsRadar(String id);

    /**
     * 获得雷达分页
     *
     * @param pageReqVO 分页查询
     * @return 雷达分页
     */
    PageResult<TrafficOpsRadarDO> getOpsRadarPage(TrafficOpsRadarPageReqVO pageReqVO);

}