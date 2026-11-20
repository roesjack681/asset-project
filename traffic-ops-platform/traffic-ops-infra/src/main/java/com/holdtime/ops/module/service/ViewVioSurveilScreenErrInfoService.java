package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewVioSurveilScreenErrInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 集指无效违法记录 Service 接口
 *
 * @author 管理员
 */
public interface ViewVioSurveilScreenErrInfoService {

    /**
     * 创建集指无效违法记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createViewVioSurveilScreenErrInfo(@Valid ViewVioSurveilScreenErrInfoSaveReqVO createReqVO);

    /**
     * 更新集指无效违法记录
     *
     * @param updateReqVO 更新信息
     */
    void updateViewVioSurveilScreenErrInfo(@Valid ViewVioSurveilScreenErrInfoSaveReqVO updateReqVO);

    /**
     * 删除集指无效违法记录
     *
     * @param id 编号
     */
    void deleteViewVioSurveilScreenErrInfo(String id);

    /**
     * 获得集指无效违法记录
     *
     * @param id 编号
     * @return 集指无效违法记录
     */
    ViewVioSurveilScreenErrInfoDO getViewVioSurveilScreenErrInfo(String id);

    /**
     * 获得集指无效违法记录分页
     *
     * @param pageReqVO 分页查询
     * @return 集指无效违法记录分页
     */
    PageResult<ViewVioSurveilScreenErrInfoDO> getViewVioSurveilScreenErrInfoPage(ViewVioSurveilScreenErrInfoPageReqVO pageReqVO);

}