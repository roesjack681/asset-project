package com.holdtime.ops.module.service;

import com.holdtime.ops.module.entity.TrafficOpsRegisterDO;
import com.holdtime.ops.module.model.vo.AppTrafficOpsRegisterRespVO;
import com.holdtime.ops.module.model.vo.AppTrafficOpsRegisterSaveReqVO;
import com.holdtime.ops.module.weixin.sdk.model.SNSUserInfo;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 人员注册绑定信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsRegisterExtService {

    //String reAuthByWechatAuth(HttpServletResponse response, String redirectMapping, String scope, String code) throws Exception;

    SNSUserInfo reAuthByWechatUserInfo(HttpServletResponse response, String redirectMapping, String code) throws Exception;

    /**
     * 创建人员注册绑定信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsRegister(@Valid AppTrafficOpsRegisterSaveReqVO createReqVO);

    /**
     * 根据OpenId获取注册信息
     * @param openId 微信OpenId
     * @return 注册信息
     */
    AppTrafficOpsRegisterRespVO getOpsRegister(String openId);

    /**
     * 查询与当前用户同部门的维修人员信息
     * @param dispatchUserId 派单人用户ID
     * @param name 人员姓名
     * @return 注册信息
     */
    List<AppTrafficOpsRegisterRespVO> getAllOpsRegister(Long dispatchUserId, String name);

    /**
     * 解绑注册信息
     * @param openId 微信OpenId
     */
    void unbindOpsRegister(String openId);

    /**
     * 查询所有注册用户信息
     * @return 注册信息
     */
    List<AppTrafficOpsRegisterRespVO> getAllOpsRegister();

    /**
     * 警务专区（创建用户并进行微信绑定）
     *
     * @param opsRegisterDO 创建信息
     */
    void createOpsUserAndRegister(@Valid TrafficOpsRegisterDO opsRegisterDO);
}