package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.ViewVioSurveilScreenErrInfoDO;
import com.holdtime.ops.module.model.vo.TrafficVioSurveilScreenErrSaveReqVO;
import com.holdtime.ops.module.model.vo.ViewVioSurveilScreenErrInfoExtPageReqVO;
import com.holdtime.ops.module.model.vo.ViewVioSurveilScreenErrInfoPageReqVO;
import com.holdtime.ops.module.model.vo.ViewVioSurveilScreenErrInfoSaveReqVO;

import javax.validation.Valid;

/**
 * 集指无效违法记录 Service 接口
 *
 * @author 管理员
 */
public interface ViewVioSurveilScreenErrInfoExtService {

    /**
     * 更新集指无效违法记录
     *
     * @param updateReqVO 更新信息
     */
    void updateVioSurveilScreenErrInfo(@Valid TrafficVioSurveilScreenErrSaveReqVO updateReqVO);

    /**
     * 获得集指无效违法记录分页
     *
     * @param pageReqVO 分页查询
     * @return 集指无效违法记录分页
     */
    PageResult<ViewVioSurveilScreenErrInfoDO> getViewVioSurveilScreenErrInfoPage(ViewVioSurveilScreenErrInfoExtPageReqVO pageReqVO);

}