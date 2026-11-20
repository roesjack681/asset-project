package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicEquipOnOffCheckResultDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备通断测结果 Service 接口
 *
 * @author 管理员
 */
public interface PublicEquipOnOffCheckResultService {

    /**
     * 创建设备通断测结果
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createEquipOnOffCheckResult(@Valid PublicEquipOnOffCheckResultSaveReqVO createReqVO);

    /**
     * 更新设备通断测结果
     *
     * @param updateReqVO 更新信息
     */
    void updateEquipOnOffCheckResult(@Valid PublicEquipOnOffCheckResultSaveReqVO updateReqVO);

    /**
     * 删除设备通断测结果
     *
     * @param id 编号
     */
    void deleteEquipOnOffCheckResult(String id);

    /**
     * 获得设备通断测结果
     *
     * @param id 编号
     * @return 设备通断测结果
     */
    PublicEquipOnOffCheckResultDO getEquipOnOffCheckResult(String id);

    /**
     * 获得设备通断测结果分页
     *
     * @param pageReqVO 分页查询
     * @return 设备通断测结果分页
     */
    PageResult<PublicEquipOnOffCheckResultDO> getEquipOnOffCheckResultPage(PublicEquipOnOffCheckResultPageReqVO pageReqVO);

}