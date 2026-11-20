package com.holdtime.ops.module.service;

import com.holdtime.ops.module.entity.ApiAuthDO;

public interface ApiAuthExtService {
    /**
     * 根据用户SN获取接口授权码
     * @param clientSn 客户端序列号
     * @return 授权码
     */
    ApiAuthDO queryApiAuthInfo(String clientSn);
}
