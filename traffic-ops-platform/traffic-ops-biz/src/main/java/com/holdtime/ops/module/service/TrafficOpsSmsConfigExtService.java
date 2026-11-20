package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.TrafficOpsSmsConfigSendReqVO;
import javax.validation.Valid;

/**
 * 资产短信发送配置 Service 接口
 *
 * @author 杨丽
 */
public interface TrafficOpsSmsConfigExtService {

    /**
     * 查找配置表人员发送信息
     *
     * @param sendReqVO 发送信息
     * @return 编号
     */
    String sendSms(@Valid TrafficOpsSmsConfigSendReqVO sendReqVO);


}