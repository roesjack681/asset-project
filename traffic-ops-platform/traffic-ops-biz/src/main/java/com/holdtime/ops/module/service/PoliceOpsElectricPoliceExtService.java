package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.PoliceOpsElectricPoliceRespVO;
import java.util.List;

/**
 * 设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface PoliceOpsElectricPoliceExtService {

    /**
     * 查询公安电警信息
     * @param equName 设备名称
     * @return 公安交警杆件信息
     */
    List<PoliceOpsElectricPoliceRespVO> autocompletePoliceOpsElectricPoliceList(String equName);
}