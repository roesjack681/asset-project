package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.VDeviceLocationNewDO;
import com.holdtime.ops.module.model.vo.VDeviceLocationNewPageReqVO;

/**
 * 车辆最新位置查询 Service 接口
 *
 * @author 超级管理员
 */
public interface VDeviceLocationNewService {

    /**
     * 获得车辆最新位置查询
     *
     * @param id 编号
     * @return 车辆最新位置查询
     */
    VDeviceLocationNewDO getVDeviceLocationNew(String id);

    /**
     * 获得车辆最新位置查询分页
     *
     * @param pageReqVO 分页查询
     * @return 车辆最新位置查询分页
     */
    PageResult<VDeviceLocationNewDO> getVDeviceLocationNewPage(VDeviceLocationNewPageReqVO pageReqVO);

}