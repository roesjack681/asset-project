package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.model.vo.TrafficOpsMemberExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMemberExtSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMemberPageReqVO;

import javax.validation.Valid;

/**
 * 设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsMemberExtService {

    /**
     * 保存或修改杆件信息
     *
     * @param saveReqVO 二维码内容
     */
    String saveOpsMemberInfo(@Valid TrafficOpsMemberExtSaveReqVO saveReqVO);

    /**
     * 查询杆件信息
     *
     * @param memberId 杆件ID
     */
    TrafficOpsMemberExtRespVO getOpsMember(String memberId);

    /**
     * 查询杆件分页信息
     *
     * @param pageReqVO 杆件查询条件
     * @param isPaging 是否分页
     */
    PageResult<TrafficOpsMemberExtRespVO> getOpsMemberPage(TrafficOpsMemberPageReqVO pageReqVO, String isPaging);

    void deleteOpsMemberInfo(String id);
}