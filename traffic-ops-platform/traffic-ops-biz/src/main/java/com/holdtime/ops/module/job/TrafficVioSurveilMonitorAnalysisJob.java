package com.holdtime.ops.module.job;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.holdtime.ops.module.service.TrafficVioEquipmentMonitorAnalysisExtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
@Slf4j
public class TrafficVioSurveilMonitorAnalysisJob implements JobHandler {
    @Resource
    private TrafficVioEquipmentMonitorAnalysisExtService trafficVioEquipmentMonitorAnalysisExtService;

    /**
     *  定时任务：互联网执行，将每日通断、视频情况及违法状态情况保存到明细表中
     * @param param 定时任务参数
     * @return 任务执行结果
     */
    @Override
    public String execute(String param) {
        scheduledAnalysisEvent(param);
        return "分析完成！";
    }

    private void scheduledAnalysisEvent(String diffDay) {
        if (StringUtils.isBlank(diffDay)) {
            diffDay = "2";
        }
        Date date = DateUtil.offsetDay(new Date(), (-1 * Integer.parseInt(diffDay)));

        trafficVioEquipmentMonitorAnalysisExtService.analysisVioEquipmentSurveil(date);
    }
}
