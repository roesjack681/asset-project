package com.holdtime.ops.module.job;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.holdtime.component.notice.client.dingtalk.HdDingTalkRobotClient;
import com.holdtime.ops.module.mapper.PoliceOpsEquipInfoExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CollectMemberInfoAnalysisJob implements JobHandler {
    @Resource
    private PoliceOpsEquipInfoExtMapper policeOpsEquipInfoExtMapper;
    @Resource
    HdDingTalkRobotClient hdDingTalkRobotClient;

    @Override
    public String execute(String param) {
        analysisResultNoticePushEvent();
        return "推送完成！";
    }

    private void analysisResultNoticePushEvent() {
        List<Map<String, Object>> collectResultList = policeOpsEquipInfoExtMapper.analysisCollectMemberByUserJob();
        if (CollectionUtils.isEmpty(collectResultList)) {
            return;
        }

        // 表格头部
        String header = "| 姓名 | 总数 | 今日采集数 | 今日最早开始时间 | 今日结束时间 | 工作时间 | \n |:----:|:----:|:----:|:----:|:----:|:----:|  \n  ";


        // 表格数据
        StringBuilder markdownTable = new StringBuilder(header);
        int totalSumCnt = 0;
        int todayTotalSumCnt = 0;
        for (Map<String, Object> collectResult : collectResultList) {
            String nickname = (String) collectResult.get("nickname");
            Long sumCnt = (Long) collectResult.get("sum_cnt");
            BigDecimal todaySumCnt = (BigDecimal) collectResult.get("today_sum_cnt");
            LocalDateTime todayEarliestStartDate = (LocalDateTime) collectResult.get("today_earliest_start_date");
            String todayEarliestStartDateStr = "";
            if (todayEarliestStartDate != null) {
                todayEarliestStartDateStr = DateUtil.format(todayEarliestStartDate, "yyyy-MM-dd HH:mm:ss");
            }

            LocalDateTime todayLastStartDate = (LocalDateTime) collectResult.get("today_last_start_date");
            String todayLastStartDateStr = "";
            if (todayLastStartDate != null) {
                todayLastStartDateStr = DateUtil.format(todayLastStartDate, "yyyy-MM-dd HH:mm:ss");
            }
            Long jobTime = (Long) collectResult.get("job_time");
            String row = "| " + nickname + " | " + sumCnt.intValue() + " | " + todaySumCnt.intValue() + " | " + todayEarliestStartDateStr + " | " + todayLastStartDateStr + " | " + (jobTime == null ? "" : jobTime) + " |  \n  ";
            markdownTable.append(row);

            totalSumCnt += sumCnt.intValue();
            todayTotalSumCnt += todaySumCnt.intValue();

        }


        // 推送消息
        hdDingTalkRobotClient.sendMarkdownMessage("公安杆件采集分析","### 公安杆件" + DateUtil.format(new Date(), "yyyy年MM月dd日") + "数据分析\n" +
                "#### 累计总计：" + totalSumCnt + "条\n" + "#### 今日总计：" + todayTotalSumCnt + "条\n\n" + markdownTable);
    }
}
