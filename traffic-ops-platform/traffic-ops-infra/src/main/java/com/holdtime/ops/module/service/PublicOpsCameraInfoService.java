package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsCameraInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 资产摄像设备信息 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsCameraInfoService {

    /**
     * 创建资产摄像设备信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsCameraInfo(@Valid PublicOpsCameraInfoSaveReqVO createReqVO);

    /**
     * 更新资产摄像设备信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsCameraInfo(@Valid PublicOpsCameraInfoSaveReqVO updateReqVO);

    /**
     * 删除资产摄像设备信息
     *
     * @param id 编号
     */
    void deleteOpsCameraInfo(String id);

    /**
     * 获得资产摄像设备信息
     *
     * @param id 编号
     * @return 资产摄像设备信息
     */
    PublicOpsCameraInfoDO getOpsCameraInfo(String id);

    /**
     * 获得资产摄像设备信息分页
     *
     * @param pageReqVO 分页查询
     * @return 资产摄像设备信息分页
     */
    PageResult<PublicOpsCameraInfoDO> getOpsCameraInfoPage(PublicOpsCameraInfoPageReqVO pageReqVO);

}