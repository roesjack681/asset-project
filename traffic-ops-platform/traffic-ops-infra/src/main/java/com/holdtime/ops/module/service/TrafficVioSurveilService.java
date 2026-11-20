package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioSurveilDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 违法抓拍待审记录 Service 接口
 *
 * @author 管理员
 */
public interface TrafficVioSurveilService {

    /**
     * 创建违法抓拍待审记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createVioSurveil(@Valid TrafficVioSurveilSaveReqVO createReqVO);

    /**
     * 更新违法抓拍待审记录
     *
     * @param updateReqVO 更新信息
     */
    void updateVioSurveil(@Valid TrafficVioSurveilSaveReqVO updateReqVO);

    /**
     * 删除违法抓拍待审记录
     *
     * @param id 编号
     */
    void deleteVioSurveil(String id);

    /**
     * 获得违法抓拍待审记录
     *
     * @param id 编号
     * @return 违法抓拍待审记录
     */
    TrafficVioSurveilDO getVioSurveil(String id);

    /**
     * 获得违法抓拍待审记录分页
     *
     * @param pageReqVO 分页查询
     * @return 违法抓拍待审记录分页
     */
    PageResult<TrafficVioSurveilDO> getVioSurveilPage(TrafficVioSurveilPageReqVO pageReqVO);

}