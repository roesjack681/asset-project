package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.system.enums.ErrorCodeConstants;
import com.holdtime.ops.module.entity.PublicOpsNoticeRecordDO;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import com.holdtime.ops.module.entity.mapper.PublicOpsNoticeRecordMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.OpsNoticeMsgVO;
import com.holdtime.ops.module.service.PublicOpsNoticeRecordExtService;
import com.holdtime.ops.module.service.Sms4JService;
import com.holdtime.ops.module.utils.emay.SmsSingleReqDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 消息通知记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsNoticeRecordExtServiceImpl implements PublicOpsNoticeRecordExtService {
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private Sms4JService sms4JService;
    @Resource
    private PublicOpsNoticeRecordMapper opsNoticeRecordMapper;
    @Resource
    private TrafficOpsMaintenanceMapper opsMaintenanceMapper;

    @Override
    public void sendNoticeMsg(OpsNoticeMsgVO opsNoticeMsgVO) {
        if (opsNoticeMsgVO.getUserId() == null && StringUtils.isBlank(opsNoticeMsgVO.getReceivePhone())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }
        String receivePhone = opsNoticeMsgVO.getReceivePhone();
        if (opsNoticeMsgVO.getUserId()  != null) {
            // 查询用户信息
            AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(opsNoticeMsgVO.getUserId());
            if (adminUserRespDTO == null) {
                throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
            }
            receivePhone = adminUserRespDTO.getMobile();
        }

        if (StringUtils.isBlank(receivePhone) ) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PHONE_NOT_EXISTS);
        }

        sendNoticeMsg(opsNoticeMsgVO.getBusinessId(), opsNoticeMsgVO.getMsgType(), opsNoticeMsgVO.getUserId(), receivePhone, opsNoticeMsgVO.getNoticeMsg());
    }

    @Override
    public void sendNoticeMsg(String businessId, String noticeType, Long receiveUserId,
                               String receivePhone, String noticeMsg) {

        // 查询报修单是否存在
        TrafficOpsMaintenanceDO opsMaintenanceDO = opsMaintenanceMapper.selectById(businessId);
        if (opsMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        Map<String, String> userInfoMap = orgUserTypeMap(opsMaintenanceDO);

        // 保存发送短信记录
        PublicOpsNoticeRecordDO noticeRecordDO = new PublicOpsNoticeRecordDO();
        noticeRecordDO.setBusinessType("maintenance");
        noticeRecordDO.setBusinessId(businessId);
        noticeRecordDO.setNoticeType(noticeType);

        if (receiveUserId  != null) {
            String userType = userInfoMap.get(receiveUserId + "");
            if (StringUtils.isBlank(userType)) {
                userType = OpsConstants.USER_TYPE_OTHER;
            }
            noticeRecordDO.setReceiveUserType(userType);
            noticeRecordDO.setReceiveUserId(receiveUserId + "");
            // 查询用户信息
            AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(receiveUserId);
            if (adminUserRespDTO == null) {
                throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
            }
            receivePhone = adminUserRespDTO.getMobile();
            noticeRecordDO.setReceiveUserName(adminUserRespDTO.getNickname());
        }

        if (StringUtils.isBlank(receivePhone) ) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PHONE_NOT_EXISTS);
        }

        noticeRecordDO.setReceiveUserPhone(receivePhone);
        // 发送短信
        SmsSingleReqDTO singleReqDTO = sms4JService.sendCustomNoticeMsg(receivePhone, noticeMsg);

        noticeRecordDO.setNoticeMsg(singleReqDTO.getContent());
        noticeRecordDO.setSendTime(DateUtil.toLocalDateTime(new Date()));
        opsNoticeRecordMapper.insert(noticeRecordDO);
    }

    private Map<String, String> orgUserTypeMap(TrafficOpsMaintenanceDO opsMaintenanceDO) {
        if (opsMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        Map<String, String> map = new HashMap<>();

        if (StringUtils.isNotBlank(opsMaintenanceDO.getReportUserId())) {
            map.put(opsMaintenanceDO.getReportUserId(), OpsConstants.USER_TYPE_REPORT);
        }

        if (StringUtils.isNotBlank(opsMaintenanceDO.getDispatchUserId())) {
            map.put(opsMaintenanceDO.getDispatchUserId(), OpsConstants.USER_TYPE_DISPATCH);
        }

        if (StringUtils.isNotBlank(opsMaintenanceDO.getMaintenanceUserId())) {
            map.put(opsMaintenanceDO.getMaintenanceUserId(), OpsConstants.USER_TYPE_MAINTENANCE);
        }

        if (StringUtils.isNotBlank(opsMaintenanceDO.getConfirmorUserId())) {
            map.put(opsMaintenanceDO.getConfirmorUserId(), OpsConstants.USER_TYPE_CONFIRM);
        }

        if (StringUtils.isNotBlank(opsMaintenanceDO.getRejectUserId())) {
            map.put(opsMaintenanceDO.getRejectUserId(), OpsConstants.USER_TYPE_REJECT);
        }

        if (StringUtils.isNotBlank(opsMaintenanceDO.getCancelUserId())) {
            map.put(opsMaintenanceDO.getCancelUserId(), OpsConstants.USER_TYPE_CANCEL);
        }

        return map;
    }

}