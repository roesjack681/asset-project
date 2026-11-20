package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsAttachInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 资产附件信息 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsAttachInfoService {

    /**
     * 创建资产附件信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsAttachInfo(@Valid PublicOpsAttachInfoSaveReqVO createReqVO);

    /**
     * 更新资产附件信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsAttachInfo(@Valid PublicOpsAttachInfoSaveReqVO updateReqVO);

    /**
     * 删除资产附件信息
     *
     * @param id 编号
     */
    void deleteOpsAttachInfo(String id);

    /**
     * 获得资产附件信息
     *
     * @param id 编号
     * @return 资产附件信息
     */
    PublicOpsAttachInfoDO getOpsAttachInfo(String id);

    /**
     * 获得资产附件信息分页
     *
     * @param pageReqVO 分页查询
     * @return 资产附件信息分页
     */
    PageResult<PublicOpsAttachInfoDO> getOpsAttachInfoPage(PublicOpsAttachInfoPageReqVO pageReqVO);

}