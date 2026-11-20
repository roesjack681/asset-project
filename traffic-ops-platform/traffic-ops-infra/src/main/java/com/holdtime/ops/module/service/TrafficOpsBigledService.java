package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsBigledDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 大屏 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsBigledService {

    /**
     * 创建大屏
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsBigled(@Valid TrafficOpsBigledSaveReqVO createReqVO);

    /**
     * 更新大屏
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsBigled(@Valid TrafficOpsBigledSaveReqVO updateReqVO);

    /**
     * 删除大屏
     *
     * @param id 编号
     */
    void deleteOpsBigled(String id);

    /**
     * 获得大屏
     *
     * @param id 编号
     * @return 大屏
     */
    TrafficOpsBigledDO getOpsBigled(String id);

    /**
     * 获得大屏分页
     *
     * @param pageReqVO 分页查询
     * @return 大屏分页
     */
    PageResult<TrafficOpsBigledDO> getOpsBigledPage(TrafficOpsBigledPageReqVO pageReqVO);

}