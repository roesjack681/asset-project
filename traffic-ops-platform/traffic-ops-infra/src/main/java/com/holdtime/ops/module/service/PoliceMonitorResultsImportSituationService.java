package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportSituationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 公安监测结果导入近两日状态 Service 接口
 *
 * @author 管理员
 */
public interface PoliceMonitorResultsImportSituationService {

    /**
     * 创建公安监测结果导入近两日状态
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMonitorResultsImportSituation(@Valid PoliceMonitorResultsImportSituationSaveReqVO createReqVO);

    /**
     * 更新公安监测结果导入近两日状态
     *
     * @param updateReqVO 更新信息
     */
    void updateMonitorResultsImportSituation(@Valid PoliceMonitorResultsImportSituationSaveReqVO updateReqVO);

    /**
     * 删除公安监测结果导入近两日状态
     *
     * @param id 编号
     */
    void deleteMonitorResultsImportSituation(String id);

    /**
     * 获得公安监测结果导入近两日状态
     *
     * @param id 编号
     * @return 公安监测结果导入近两日状态
     */
    PoliceMonitorResultsImportSituationDO getMonitorResultsImportSituation(String id);

    /**
     * 获得公安监测结果导入近两日状态分页
     *
     * @param pageReqVO 分页查询
     * @return 公安监测结果导入近两日状态分页
     */
    PageResult<PoliceMonitorResultsImportSituationDO> getMonitorResultsImportSituationPage(PoliceMonitorResultsImportSituationPageReqVO pageReqVO);

}