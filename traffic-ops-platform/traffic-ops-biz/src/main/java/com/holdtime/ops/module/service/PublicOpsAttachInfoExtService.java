package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.PublicOpsAttachInfoDO;
import com.holdtime.ops.module.model.vo.PublicOpsAttachInfoExtSaveReqVO;
import com.holdtime.ops.module.model.vo.PublicOpsAttachInfoPageReqVO;
import com.holdtime.ops.module.model.vo.PublicOpsAttachInfoRespVO;
import com.holdtime.ops.module.model.vo.PublicOpsAttachInfoSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 资产附件信息 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsAttachInfoExtService {

    /**
     * 创建资产附件信息
     *
     * @param saveReqVOList 附件信息
     */
    void saveOpsAttachInfo(String affiliatedUnit, String businessModel, String businessId, @Valid List<PublicOpsAttachInfoExtSaveReqVO> saveReqVOList);

    /**
     * 查询资产附件信息
     * @param affiliatedUnit 所属单位
     * @param businessModel 业务模块
     * @param businessId 业务ID
     * @return 资产附件
     */
    List<PublicOpsAttachInfoRespVO> queryOpsAttachInfoList(String affiliatedUnit, String businessModel, String businessId);

}