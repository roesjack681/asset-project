package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailInsuranceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 护栏保险信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsGuardrailInsuranceService {

    /**
     * 创建护栏保险信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsGuardrailInsurance(@Valid TrafficOpsGuardrailInsuranceSaveReqVO createReqVO);

    /**
     * 更新护栏保险信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsGuardrailInsurance(@Valid TrafficOpsGuardrailInsuranceSaveReqVO updateReqVO);

    /**
     * 删除护栏保险信息
     *
     * @param id 编号
     */
    void deleteOpsGuardrailInsurance(String id);

    /**
     * 获得护栏保险信息
     *
     * @param id 编号
     * @return 护栏保险信息
     */
    TrafficOpsGuardrailInsuranceDO getOpsGuardrailInsurance(String id);

    /**
     * 获得护栏保险信息分页
     *
     * @param pageReqVO 分页查询
     * @return 护栏保险信息分页
     */
    PageResult<TrafficOpsGuardrailInsuranceDO> getOpsGuardrailInsurancePage(TrafficOpsGuardrailInsurancePageReqVO pageReqVO);

}