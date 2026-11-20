package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.TrafficOpsElectricPoliceRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsElectricPoliceSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 电警 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsElectricPoliceExtService {
    /**
     * 保存/修改电警
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String saveOpsElectricPolice(@Valid TrafficOpsElectricPoliceSaveReqVO createReqVO);

    /**
     * 自动完成查询
     * @param ipAddr IP地址
     * @return 根据IP模糊查询电警信息
     */
    List<TrafficOpsElectricPoliceRespVO> autocompleteOpsElectricPolice(String ipAddr);

}