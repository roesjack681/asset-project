package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewVioEquipmentDailySituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 违法抓拍设备每日情况 Service 接口
 *
 * @author 管理员
 */
public interface ViewVioEquipmentDailySituationService {

    /**
     * 创建违法抓拍设备每日情况
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createViewVioEquipmentDailySituation(@Valid ViewVioEquipmentDailySituationSaveReqVO createReqVO);

    /**
     * 更新违法抓拍设备每日情况
     *
     * @param updateReqVO 更新信息
     */
    void updateViewVioEquipmentDailySituation(@Valid ViewVioEquipmentDailySituationSaveReqVO updateReqVO);

    /**
     * 删除违法抓拍设备每日情况
     *
     * @param id 编号
     */
    void deleteViewVioEquipmentDailySituation(String id);

    /**
     * 获得违法抓拍设备每日情况
     *
     * @param id 编号
     * @return 违法抓拍设备每日情况
     */
    ViewVioEquipmentDailySituationDO getViewVioEquipmentDailySituation(String id);

    /**
     * 获得违法抓拍设备每日情况分页
     *
     * @param pageReqVO 分页查询
     * @return 违法抓拍设备每日情况分页
     */
    PageResult<ViewVioEquipmentDailySituationDO> getViewVioEquipmentDailySituationPage(ViewVioEquipmentDailySituationPageReqVO pageReqVO);

}