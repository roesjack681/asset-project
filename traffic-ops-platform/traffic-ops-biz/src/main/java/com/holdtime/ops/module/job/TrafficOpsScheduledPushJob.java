package com.holdtime.ops.module.job;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.holdtime.ops.module.conf.WechatConfig;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import com.holdtime.ops.module.entity.TrafficOpsUrgencySettingsDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsUrgencySettingsMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.weixin.sdk.WeixinUtil;
import com.holdtime.ops.module.weixin.sdk.api.model.SendTemplateMessage;
import com.holdtime.ops.module.weixin.sdk.api.model.TemplateData;
import com.holdtime.ops.module.weixin.sdk.api.model.WechatTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
@Slf4j
public class TrafficOpsScheduledPushJob implements JobHandler {
    @Resource
    private TrafficOpsMaintenanceMapper opsMaintenanceMapper;
    @Resource
    private TrafficOpsUrgencySettingsMapper opsUrgencySettingsMapper;

    @Override
    public String execute(String param) {
        scheduledPushEvent();
        return "推送完成！";
    }

    private void scheduledPushEvent() {
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        // 获取当前时间并截断到分钟精度
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        // 添加精确到分钟的查询条件
        queryWrapperX.eq(TrafficOpsMaintenanceDO::getDeadline, now);
        List<TrafficOpsMaintenanceDO> maintenanceDOList = opsMaintenanceMapper.selectList(queryWrapperX);
        if (!CollectionUtils.isEmpty(maintenanceDOList)) {
            for (TrafficOpsMaintenanceDO maintenanceDo : maintenanceDOList) {
                if(!maintenanceDo.getStatus().equals(OpsConstants.STATUS_COMPLETED)){
                    //根据紧急程度推送对应的人员信息
                    LambdaQueryWrapperX<TrafficOpsUrgencySettingsDO> queryWrapper = new LambdaQueryWrapperX<>();
                    queryWrapper.eqIfPresent(TrafficOpsUrgencySettingsDO::getUrgencyLevel,maintenanceDo.getUrgencyLevel());
                    List<TrafficOpsUrgencySettingsDO> urgencySettingsDOList = opsUrgencySettingsMapper.selectList(queryWrapper);
                    if (!CollectionUtils.isEmpty(urgencySettingsDOList)) {
                        if(StringUtils.isNotEmpty(urgencySettingsDOList.get(0).getNotifyOpenid())){
                            String[] persons = urgencySettingsDOList.get(0).getNotifyOpenid().split(",");
                            for (String personOpenid : persons) {
                                //推送提醒信息到配置的人员
                                Map<String, TemplateData> data = new HashMap<>();
                                data.put("thing1" , new TemplateData().setValue("该工单的紧急程度为："+maintenanceDo.getUrgencyLevel()+ ",请尽快处理！"));
                                data.put("time16" , new TemplateData().setValue(DateUtil.format(new Date(), "yyyy/MM/dd HH:mm:ss")));

                                WechatTemplate template = new WechatTemplate();
                                template.setTemplate_id(WechatConfig.getMgtTodoTempId());
                                template.setData(data);
                                template.setTouser(personOpenid);
                                try {
                                    SendTemplateMessage.sendTemplateMessage(WeixinUtil.getAccessToken().getToken() , template);
                                } catch (Exception e) {
                                    log.error("模板消息发送失败！" , e);
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
