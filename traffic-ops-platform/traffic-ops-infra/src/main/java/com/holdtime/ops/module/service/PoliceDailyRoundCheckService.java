package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceDailyRoundCheckDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 日常巡检信息 Service 接口
 *
 * @author 管理员
 */
public interface PoliceDailyRoundCheckService {

    /**
     * 创建日常巡检信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createDailyRoundCheck(@Valid PoliceDailyRoundCheckSaveReqVO createReqVO);

    /**
     * 更新日常巡检信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDailyRoundCheck(@Valid PoliceDailyRoundCheckSaveReqVO updateReqVO);

    /**
     * 删除日常巡检信息
     *
     * @param id 编号
     */
    void deleteDailyRoundCheck(String id);

    /**
     * 获得日常巡检信息
     *
     * @param id 编号
     * @return 日常巡检信息
     */
    PoliceDailyRoundCheckDO getDailyRoundCheck(String id);

    /**
     * 获得日常巡检信息分页
     *
     * @param pageReqVO 分页查询
     * @return 日常巡检信息分页
     */
    PageResult<PoliceDailyRoundCheckDO> getDailyRoundCheckPage(PoliceDailyRoundCheckPageReqVO pageReqVO);

}