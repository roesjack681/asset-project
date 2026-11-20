package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.entity.TrafficOpsRoadDO;
import com.holdtime.ops.module.model.vo.TrafficOpsMemberExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMemberPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsRoadPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsRoadSaveReqVO;

import javax.validation.Valid;

/**
 * 道路信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsRoadExtService {

    /**
     * 根据路获取杆件及绑定设备信息
     * @param id
     * @return
     */
    PageResult<TrafficOpsMemberExtRespVO> getOpsRoadPage(TrafficOpsMemberPageReqVO pageReqVO,String id);

    /**
     * 保存核验结果
     * @param createReqVO
     * @return
     */
    String checkResult(@Valid TrafficOpsMemberExtRespVO createReqVO);

}