package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsRegisterDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 人员注册绑定信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsRegisterService {

    /**
     * 创建人员注册绑定信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsRegister(@Valid AppTrafficOpsRegisterSaveReqVO createReqVO);

    /**
     * 更新人员注册绑定信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsRegister(@Valid AppTrafficOpsRegisterSaveReqVO updateReqVO);

    /**
     * 删除人员注册绑定信息
     *
     * @param id 编号
     */
    void deleteOpsRegister(String id);

    /**
     * 获得人员注册绑定信息
     *
     * @param id 编号
     * @return 人员注册绑定信息
     */
    TrafficOpsRegisterDO getOpsRegister(String id);

    /**
     * 获得人员注册绑定信息分页
     *
     * @param pageReqVO 分页查询
     * @return 人员注册绑定信息分页
     */
    PageResult<TrafficOpsRegisterDO> getOpsRegisterPage(AppTrafficOpsRegisterPageReqVO pageReqVO);

}