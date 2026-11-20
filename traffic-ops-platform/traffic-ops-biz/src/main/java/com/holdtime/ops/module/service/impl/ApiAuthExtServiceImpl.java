package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.ApiAuthDO;
import com.holdtime.ops.module.model.vo.ApiAuthPageReqVO;
import com.holdtime.ops.module.service.ApiAuthExtService;
import com.holdtime.ops.module.service.ApiAuthService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

@Service
public class ApiAuthExtServiceImpl implements ApiAuthExtService {
    @Resource
    private ApiAuthService apiAuthService;

    @Override
    public ApiAuthDO queryApiAuthInfo(String clientSn) {
        ApiAuthPageReqVO reqVO = new ApiAuthPageReqVO();
        reqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        reqVO.setClientSn(clientSn);
        PageResult<ApiAuthDO> pageResult = apiAuthService.getApiAuthPage(reqVO);
        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getList())) {
            return null;
        }

        return pageResult.getList().get(0);
    }
}
