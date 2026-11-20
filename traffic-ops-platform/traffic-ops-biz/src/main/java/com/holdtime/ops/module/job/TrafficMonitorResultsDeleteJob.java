package com.holdtime.ops.module.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.holdtime.ops.module.service.TrafficMonitorResultsExtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class TrafficMonitorResultsDeleteJob implements JobHandler {
    @Resource
    private TrafficMonitorResultsExtService monitorResultsExtService;

    @Override
    public String execute(String param) {
        scheduledDeleteEvent();
        return "分析完成！";
    }

    private void scheduledDeleteEvent() {
        monitorResultsExtService.archiveTrafficMonitorResults();
    }
}
