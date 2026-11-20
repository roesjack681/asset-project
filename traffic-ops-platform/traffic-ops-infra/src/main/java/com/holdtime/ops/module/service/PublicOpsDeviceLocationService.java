package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 终端最新位置 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsDeviceLocationService {

    /**
     * 创建终端最新位置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsDeviceLocation(@Valid PublicOpsDeviceLocationSaveReqVO createReqVO);

    /**
     * 更新终端最新位置
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsDeviceLocation(@Valid PublicOpsDeviceLocationSaveReqVO updateReqVO);

    /**
     * 删除终端最新位置
     *
     * @param id 编号
     */
    void deleteOpsDeviceLocation(String id);

    /**
     * 获得终端最新位置
     *
     * @param id 编号
     * @return 终端最新位置
     */
    PublicOpsDeviceLocationDO getOpsDeviceLocation(String id);

    /**
     * 获得终端最新位置分页
     *
     * @param pageReqVO 分页查询
     * @return 终端最新位置分页
     */
    PageResult<PublicOpsDeviceLocationDO> getOpsDeviceLocationPage(PublicOpsDeviceLocationPageReqVO pageReqVO);

}