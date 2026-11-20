package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.HaPsFeedbackDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 方案展示留言记录 Service 接口
 *
 * @author 芋道源码
 */
public interface HaPsFeedbackService {

    /**
     * 创建方案展示留言记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createHaPsFeedback(@Valid HaPsFeedbackSaveReqVO createReqVO);

    /**
     * 更新方案展示留言记录
     *
     * @param updateReqVO 更新信息
     */
    void updateHaPsFeedback(@Valid HaPsFeedbackSaveReqVO updateReqVO);

    /**
     * 删除方案展示留言记录
     *
     * @param id 编号
     */
    void deleteHaPsFeedback(String id);

    /**
     * 获得方案展示留言记录
     *
     * @param id 编号
     * @return 方案展示留言记录
     */
    HaPsFeedbackDO getHaPsFeedback(String id);

    /**
     * 获得方案展示留言记录分页
     *
     * @param pageReqVO 分页查询
     * @return 方案展示留言记录分页
     */
    PageResult<HaPsFeedbackDO> getHaPsFeedbackPage(HaPsFeedbackPageReqVO pageReqVO);

}