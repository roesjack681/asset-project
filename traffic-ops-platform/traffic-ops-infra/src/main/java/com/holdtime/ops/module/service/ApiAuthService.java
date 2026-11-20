package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.ApiAuthDO;
import com.holdtime.ops.module.model.vo.ApiAuthPageReqVO;
import com.holdtime.ops.module.model.vo.ApiAuthSaveReqVO;

import javax.validation.Valid;

/**
 * 接口授权 Service 接口
 *
 * @author 超级管理员
 */
public interface ApiAuthService {

    /**
     * 创建接口授权
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createApiAuth(@Valid ApiAuthSaveReqVO createReqVO);

    /**
     * 更新接口授权
     *
     * @param updateReqVO 更新信息
     */
    void updateApiAuth(@Valid ApiAuthSaveReqVO updateReqVO);

    /**
     * 删除接口授权
     *
     * @param id 编号
     */
    void deleteApiAuth(String id);

    /**
     * 获得接口授权
     *
     * @param id 编号
     * @return 接口授权
     */
    ApiAuthDO getApiAuth(String id);

    /**
     * 获得接口授权分页
     *
     * @param pageReqVO 分页查询
     * @return 接口授权分页
     */
    PageResult<ApiAuthDO> getApiAuthPage(ApiAuthPageReqVO pageReqVO);

}