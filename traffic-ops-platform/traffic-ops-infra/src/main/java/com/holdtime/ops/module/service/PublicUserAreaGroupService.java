package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicUserAreaGroupDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 区域用户组信息 Service 接口
 *
 * @author 杨丽
 */
public interface PublicUserAreaGroupService {

    /**
     * 创建区域用户组信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createUserAreaGroup(@Valid PublicUserAreaGroupSaveReqVO createReqVO);

    /**
     * 更新区域用户组信息
     *
     * @param updateReqVO 更新信息
     */
    void updateUserAreaGroup(@Valid PublicUserAreaGroupSaveReqVO updateReqVO);

    /**
     * 删除区域用户组信息
     *
     * @param id 编号
     */
    void deleteUserAreaGroup(String id);

    /**
     * 获得区域用户组信息
     *
     * @param id 编号
     * @return 区域用户组信息
     */
    PublicUserAreaGroupDO getUserAreaGroup(String id);

    /**
     * 获得区域用户组信息分页
     *
     * @param pageReqVO 分页查询
     * @return 区域用户组信息分页
     */
    PageResult<PublicUserAreaGroupDO> getUserAreaGroupPage(PublicUserAreaGroupPageReqVO pageReqVO);

}