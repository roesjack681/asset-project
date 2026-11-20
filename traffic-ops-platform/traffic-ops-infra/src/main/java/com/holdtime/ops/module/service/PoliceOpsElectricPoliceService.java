package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 电警 Service 接口
 *
 * @author 芋道源码
 */
public interface PoliceOpsElectricPoliceService {

    /**
     * 创建电警
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsElectricPolice(@Valid PoliceOpsElectricPoliceSaveReqVO createReqVO);

    /**
     * 更新电警
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsElectricPolice(@Valid PoliceOpsElectricPoliceSaveReqVO updateReqVO);

    /**
     * 删除电警
     *
     * @param id 编号
     */
    void deleteOpsElectricPolice(String id);

    /**
     * 获得电警
     *
     * @param id 编号
     * @return 电警
     */
    PoliceOpsElectricPoliceDO getOpsElectricPolice(String id);

    /**
     * 获得电警分页
     *
     * @param pageReqVO 分页查询
     * @return 电警分页
     */
    PageResult<PoliceOpsElectricPoliceDO> getOpsElectricPolicePage(PoliceOpsElectricPolicePageReqVO pageReqVO);

}