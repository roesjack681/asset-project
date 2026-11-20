package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailAuxiliaryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 护栏辅助设备 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsGuardrailAuxiliaryService {

    /**
     * 创建护栏辅助设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsGuardrailAuxiliary(@Valid TrafficOpsGuardrailAuxiliarySaveReqVO createReqVO);

    /**
     * 更新护栏辅助设备
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsGuardrailAuxiliary(@Valid TrafficOpsGuardrailAuxiliarySaveReqVO updateReqVO);

    /**
     * 删除护栏辅助设备
     *
     * @param id 编号
     */
    void deleteOpsGuardrailAuxiliary(String id);

    /**
     * 获得护栏辅助设备
     *
     * @param id 编号
     * @return 护栏辅助设备
     */
    TrafficOpsGuardrailAuxiliaryDO getOpsGuardrailAuxiliary(String id);

    /**
     * 获得护栏辅助设备分页
     *
     * @param pageReqVO 分页查询
     * @return 护栏辅助设备分页
     */
    PageResult<TrafficOpsGuardrailAuxiliaryDO> getOpsGuardrailAuxiliaryPage(TrafficOpsGuardrailAuxiliaryPageReqVO pageReqVO);

}