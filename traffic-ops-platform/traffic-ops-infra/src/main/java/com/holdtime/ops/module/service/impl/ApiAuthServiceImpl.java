package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.ApiAuthDO;
import com.holdtime.ops.module.entity.mapper.ApiAuthMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.ApiAuthPageReqVO;
import com.holdtime.ops.module.model.vo.ApiAuthSaveReqVO;
import com.holdtime.ops.module.service.ApiAuthService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 接口授权 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class ApiAuthServiceImpl implements ApiAuthService {

    @Resource
    private ApiAuthMapper apiAuthMapper;

    @Override
    public String createApiAuth(ApiAuthSaveReqVO createReqVO) {
        // 插入
        ApiAuthDO apiAuth = BeanUtils.toBean(createReqVO, ApiAuthDO.class);
        apiAuthMapper.insert(apiAuth);
        // 返回
        return apiAuth.getId();
    }

    @Override
    public void updateApiAuth(ApiAuthSaveReqVO updateReqVO) {
        // 校验存在
        validateApiAuthExists(updateReqVO.getId());
        // 更新
        ApiAuthDO updateObj = BeanUtils.toBean(updateReqVO, ApiAuthDO.class);
        apiAuthMapper.updateById(updateObj);
    }

    @Override
    public void deleteApiAuth(String id) {
        // 校验存在
        validateApiAuthExists(id);
        // 删除
        apiAuthMapper.deleteById(id);
    }

    private void validateApiAuthExists(String id) {
        if (apiAuthMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.API_AUTH_NOT_EXISTS);
        }
    }

    @Override
    public ApiAuthDO getApiAuth(String id) {
        return apiAuthMapper.selectById(id);
    }

    @Override
    public PageResult<ApiAuthDO> getApiAuthPage(ApiAuthPageReqVO pageReqVO) {
        return apiAuthMapper.selectPage(pageReqVO);
    }

}