package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMarklineDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 标线 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsMarklineService {

    /**
     * 创建标线
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMarkline(@Valid TrafficOpsMarklineSaveReqVO createReqVO);

    /**
     * 更新标线
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMarkline(@Valid TrafficOpsMarklineSaveReqVO updateReqVO);

    /**
     * 删除标线
     *
     * @param id 编号
     */
    void deleteOpsMarkline(String id);

    /**
     * 获得标线
     *
     * @param id 编号
     * @return 标线
     */
    TrafficOpsMarklineDO getOpsMarkline(String id);

    /**
     * 获得标线分页
     *
     * @param pageReqVO 分页查询
     * @return 标线分页
     */
    PageResult<TrafficOpsMarklineDO> getOpsMarklinePage(TrafficOpsMarklinePageReqVO pageReqVO);

}