package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.PoliceDailyRoundCheckDO;
import com.holdtime.ops.module.model.vo.PoliceDailyRoundCheckExtRespVO;
import com.holdtime.ops.module.model.vo.PoliceDailyRoundCheckPageReqVO;
import com.holdtime.ops.module.model.vo.PoliceDailyRoundCheckSaveReqVO;

import javax.validation.Valid;

/**
 * 日常巡检信息 Service 接口
 *
 * @author 管理员
 */
public interface PoliceDailyRoundCheckExtService {

    /**
     * 创建日常巡检信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createDailyRoundCheck(@Valid PoliceDailyRoundCheckSaveReqVO createReqVO);

    /**
     * 获得日常巡检信息
     *
     * @param id 编号
     * @return 日常巡检信息
     */
    PoliceDailyRoundCheckExtRespVO getDailyRoundCheck(String id);

    /**
     * 获得日常巡检信息分页
     *
     * @param pageReqVO 分页查询
     * @return 日常巡检信息分页
     */
    PageResult<PoliceDailyRoundCheckExtRespVO> getDailyRoundCheckPage(PoliceDailyRoundCheckPageReqVO pageReqVO);
}