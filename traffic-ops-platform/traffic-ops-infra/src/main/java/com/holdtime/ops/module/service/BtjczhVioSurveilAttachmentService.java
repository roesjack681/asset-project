package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.BtjczhVioSurveilAttachmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 违法抓拍附件 Service 接口
 *
 * @author 管理员
 */
public interface BtjczhVioSurveilAttachmentService {

    /**
     * 创建违法抓拍附件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createBtjczhVioSurveilAttachment(@Valid BtjczhVioSurveilAttachmentSaveReqVO createReqVO);

    /**
     * 更新违法抓拍附件
     *
     * @param updateReqVO 更新信息
     */
    void updateBtjczhVioSurveilAttachment(@Valid BtjczhVioSurveilAttachmentSaveReqVO updateReqVO);

    /**
     * 删除违法抓拍附件
     *
     * @param id 编号
     */
    void deleteBtjczhVioSurveilAttachment(String id);

    /**
     * 获得违法抓拍附件
     *
     * @param id 编号
     * @return 违法抓拍附件
     */
    BtjczhVioSurveilAttachmentDO getBtjczhVioSurveilAttachment(String id);

    /**
     * 获得违法抓拍附件分页
     *
     * @param pageReqVO 分页查询
     * @return 违法抓拍附件分页
     */
    PageResult<BtjczhVioSurveilAttachmentDO> getBtjczhVioSurveilAttachmentPage(BtjczhVioSurveilAttachmentPageReqVO pageReqVO);

}