package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficVioSurveilScreenErrDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 违法抓拍无效记录 Service 接口
 *
 * @author 管理员
 */
public interface TrafficVioSurveilScreenErrService {

    /**
     * 创建违法抓拍无效记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createVioSurveilScreenErr(@Valid TrafficVioSurveilScreenErrSaveReqVO createReqVO);

    /**
     * 更新违法抓拍无效记录
     *
     * @param updateReqVO 更新信息
     */
    void updateVioSurveilScreenErr(@Valid TrafficVioSurveilScreenErrSaveReqVO updateReqVO);

    /**
     * 删除违法抓拍无效记录
     *
     * @param id 编号
     */
    void deleteVioSurveilScreenErr(String id);

    /**
     * 获得违法抓拍无效记录
     *
     * @param id 编号
     * @return 违法抓拍无效记录
     */
    TrafficVioSurveilScreenErrDO getVioSurveilScreenErr(String id);

    /**
     * 获得违法抓拍无效记录分页
     *
     * @param pageReqVO 分页查询
     * @return 违法抓拍无效记录分页
     */
    PageResult<TrafficVioSurveilScreenErrDO> getVioSurveilScreenErrPage(TrafficVioSurveilScreenErrPageReqVO pageReqVO);

}