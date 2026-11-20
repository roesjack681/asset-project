package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioSurveilScreenDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 违法抓拍有效记录 Service 接口
 *
 * @author 管理员
 */
public interface TrafficVioSurveilScreenService {

    /**
     * 创建违法抓拍有效记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createVioSurveilScreen(@Valid TrafficVioSurveilScreenSaveReqVO createReqVO);

    /**
     * 更新违法抓拍有效记录
     *
     * @param updateReqVO 更新信息
     */
    void updateVioSurveilScreen(@Valid TrafficVioSurveilScreenSaveReqVO updateReqVO);

    /**
     * 删除违法抓拍有效记录
     *
     * @param id 编号
     */
    void deleteVioSurveilScreen(String id);

    /**
     * 获得违法抓拍有效记录
     *
     * @param id 编号
     * @return 违法抓拍有效记录
     */
    TrafficVioSurveilScreenDO getVioSurveilScreen(String id);

    /**
     * 获得违法抓拍有效记录分页
     *
     * @param pageReqVO 分页查询
     * @return 违法抓拍有效记录分页
     */
    PageResult<TrafficVioSurveilScreenDO> getVioSurveilScreenPage(TrafficVioSurveilScreenPageReqVO pageReqVO);

}