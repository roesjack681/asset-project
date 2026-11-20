package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.PublicOpsNoticeRecordDO;
import com.holdtime.ops.module.model.vo.OpsNoticeMsgVO;
import com.holdtime.ops.module.model.vo.PublicOpsNoticeRecordPageReqVO;
import com.holdtime.ops.module.model.vo.PublicOpsNoticeRecordSaveReqVO;

import javax.validation.Valid;

/**
 * 消息通知记录 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsNoticeRecordExtService {
    /**
     * 发送自定义短信消息
     * @param opsNoticeMsgVO 短信消息
     */
    void sendNoticeMsg(OpsNoticeMsgVO opsNoticeMsgVO);

    void sendNoticeMsg(String businessId, String noticeType, Long receiveUserId,
                  String receivePhone, String noticeMsg);
}