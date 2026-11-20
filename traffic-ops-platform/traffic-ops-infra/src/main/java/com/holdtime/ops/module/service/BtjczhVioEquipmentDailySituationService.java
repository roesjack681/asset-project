package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.BtjczhVioEquipmentDailySituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 集指设备每日情况 Service 接口
 *
 * @author 管理员
 */
public interface BtjczhVioEquipmentDailySituationService {

    /**
     * 创建集指设备每日情况
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createBtjczhVioEquipmentDailySituation(@Valid BtjczhVioEquipmentDailySituationSaveReqVO createReqVO);

    /**
     * 更新集指设备每日情况
     *
     * @param updateReqVO 更新信息
     */
    void updateBtjczhVioEquipmentDailySituation(@Valid BtjczhVioEquipmentDailySituationSaveReqVO updateReqVO);

    /**
     * 删除集指设备每日情况
     *
     * @param id 编号
     */
    void deleteBtjczhVioEquipmentDailySituation(String id);

    /**
     * 获得集指设备每日情况
     *
     * @param id 编号
     * @return 集指设备每日情况
     */
    BtjczhVioEquipmentDailySituationDO getBtjczhVioEquipmentDailySituation(String id);

    /**
     * 获得集指设备每日情况分页
     *
     * @param pageReqVO 分页查询
     * @return 集指设备每日情况分页
     */
    PageResult<BtjczhVioEquipmentDailySituationDO> getBtjczhVioEquipmentDailySituationPage(BtjczhVioEquipmentDailySituationPageReqVO pageReqVO);

}