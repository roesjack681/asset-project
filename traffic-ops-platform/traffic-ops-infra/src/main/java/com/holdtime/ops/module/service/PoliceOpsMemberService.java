package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceOpsMemberDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 杆件 Service 接口
 *
 * @author 芋道源码
 */
public interface PoliceOpsMemberService {

    /**
     * 创建杆件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsMember(@Valid PoliceOpsMemberSaveReqVO createReqVO);

    /**
     * 更新杆件
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsMember(@Valid PoliceOpsMemberSaveReqVO updateReqVO);

    /**
     * 删除杆件
     *
     * @param id 编号
     */
    void deleteOpsMember(String id);

    /**
     * 获得杆件
     *
     * @param id 编号
     * @return 杆件
     */
    PoliceOpsMemberDO getOpsMember(String id);

    /**
     * 获得杆件分页
     *
     * @param pageReqVO 分页查询
     * @return 杆件分页
     */
    PageResult<PoliceOpsMemberDO> getOpsMemberPage(PoliceOpsMemberPageReqVO pageReqVO);

}