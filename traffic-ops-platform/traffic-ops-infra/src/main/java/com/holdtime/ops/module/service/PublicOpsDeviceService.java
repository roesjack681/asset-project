package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsDeviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 终端设备信息 Service 接口
 *
 * @author 管理员
 */
public interface PublicOpsDeviceService {

    /**
     * 创建终端设备信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsDevice(@Valid PublicOpsDeviceSaveReqVO createReqVO);

    /**
     * 更新终端设备信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsDevice(@Valid PublicOpsDeviceSaveReqVO updateReqVO);

    /**
     * 删除终端设备信息
     *
     * @param id 编号
     */
    void deleteOpsDevice(String id);

    /**
     * 获得终端设备信息
     *
     * @param id 编号
     * @return 终端设备信息
     */
    PublicOpsDeviceDO getOpsDevice(String id);

    /**
     * 获得终端设备信息分页
     *
     * @param pageReqVO 分页查询
     * @return 终端设备信息分页
     */
    PageResult<PublicOpsDeviceDO> getOpsDevicePage(PublicOpsDevicePageReqVO pageReqVO);

}