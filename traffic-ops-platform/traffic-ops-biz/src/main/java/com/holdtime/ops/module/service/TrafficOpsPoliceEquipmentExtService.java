package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.TrafficOpsIssueRecordDO;
import com.holdtime.ops.module.entity.TrafficOpsPoliceEquipmentDO;
import com.holdtime.ops.module.model.vo.TrafficOpsIssueRecordPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsPoliceEquipmentPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsPoliceEquipmentRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsPoliceEquipmentSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 警用装备资产信息 Service 接口
 *
 * @author 管理员
 */
public interface TrafficOpsPoliceEquipmentExtService {

    /**
     * 保存警用装备资产信息
     *
     * @param saveReqVO 保存信息
     * @return 编号
     */
    String saveOpsPoliceEquipment(@Valid TrafficOpsPoliceEquipmentSaveReqVO saveReqVO);


    /**
     * 获取已发放的警用设备
     * @param deptId 使用部门
     * @param keyword 查询条件（加密卡）
     * @return 已发放的警用设备
     */
    List<TrafficOpsPoliceEquipmentRespVO> queryIssuedPoliceEquipment(String deptId, String keyword);

    /**
     * 自动关联警用设备信息
     * @param keywork 查询key
     * @param keyType key类型：1-生产厂家，2-供货厂家，3-型号
     * @return 自动关联警用设备信息
     */
    List<String> autocompletePoliceEquipInfoList(String keywork, String keyType);

    /**
     * 回收警用装备信息
     * @param id
     * @return
     */
    String recycleOpsPoliceEquipment(String id);

    /**
     * 获得警用设备列表信息
     * @param pageReqVO
     * @return
     */
    PageResult<TrafficOpsPoliceEquipmentDO> getOpsPoliceEquipmentPage(TrafficOpsPoliceEquipmentPageReqVO pageReqVO);

}