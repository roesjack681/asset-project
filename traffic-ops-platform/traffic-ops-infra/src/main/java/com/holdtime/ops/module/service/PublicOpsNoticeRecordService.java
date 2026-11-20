package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsNoticeRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 消息通知记录 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsNoticeRecordService {

    /**
     * 创建消息通知记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsNoticeRecord(@Valid PublicOpsNoticeRecordSaveReqVO createReqVO);

    /**
     * 更新消息通知记录
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsNoticeRecord(@Valid PublicOpsNoticeRecordSaveReqVO updateReqVO);

    /**
     * 删除消息通知记录
     *
     * @param id 编号
     */
    void deleteOpsNoticeRecord(String id);

    /**
     * 获得消息通知记录
     *
     * @param id 编号
     * @return 消息通知记录
     */
    PublicOpsNoticeRecordDO getOpsNoticeRecord(String id);

    /**
     * 获得消息通知记录分页
     *
     * @param pageReqVO 分页查询
     * @return 消息通知记录分页
     */
    PageResult<PublicOpsNoticeRecordDO> getOpsNoticeRecordPage(PublicOpsNoticeRecordPageReqVO pageReqVO);

}