package com.holdtime.ops.module.job;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TrafficMonitorResultsAnalysisJob implements JobHandler {
    @Resource
    private ViewOpsMonitorResultsDailyAnalysisMapper opsMonitorResultsDailyAnalysisMapper;
    @Resource
    private TrafficMonitorResultsDailyAnalysisMapper monitorResultsDailyAnalysisMapper;
    @Resource
    private TrafficMonitorResultsDailyDetailMapper monitorResultsDailyDetailMapper;
    @Resource
    private TrafficMonitorResultsMapper monitorResultsMapper;

    @Override
    public String execute(String param) {
        scheduledAnalysisEvent();
        return "分析完成！";
    }

    private void scheduledAnalysisEvent() {
        Date date = DateUtil.offsetDay(new Date(), -1);
        String analysisDay = DateUtil.format(date, "yyyyMMdd");
        // 清除当日的检测分析结果
        LambdaQueryWrapperX<TrafficMonitorResultsDailyDetailDO> delDetailLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        delDetailLambdaQueryWrapperX.exists("select 1 from traffic_monitor_results_daily_analysis a where traffic_monitor_results_daily_detail.analysis_id = a.id and date_format(a.analysis_date,'%Y%m%d') = '" + analysisDay + "'");
        monitorResultsDailyDetailMapper.delete(delDetailLambdaQueryWrapperX);

        LambdaQueryWrapperX<TrafficMonitorResultsDailyAnalysisDO> delAnalysisDoLambdaQueryWrapper = new LambdaQueryWrapperX<>();
        delAnalysisDoLambdaQueryWrapper.eq(TrafficMonitorResultsDailyAnalysisDO::getAnalysisDate, DateUtil.beginOfDay(date));
        monitorResultsDailyAnalysisMapper.delete(delAnalysisDoLambdaQueryWrapper);

        LambdaQueryWrapperX<ViewOpsMonitorResultsDailyAnalysisDO> analysisDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        analysisDOLambdaQueryWrapperX.eq(ViewOpsMonitorResultsDailyAnalysisDO::getOccurrenceDay, analysisDay);
        List<ViewOpsMonitorResultsDailyAnalysisDO> list = opsMonitorResultsDailyAnalysisMapper.selectList(analysisDOLambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        LambdaQueryWrapperX<TrafficMonitorResultsDO> monitorResultsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        monitorResultsDOLambdaQueryWrapperX.isNotNull(TrafficMonitorResultsDO::getEquId);
        monitorResultsDOLambdaQueryWrapperX.gt(TrafficMonitorResultsDO::getOccurrenceTime, DateUtil.beginOfDay(date));
        List<TrafficMonitorResultsDO> resultsDOList = monitorResultsMapper.selectList(monitorResultsDOLambdaQueryWrapperX);
        Map<String, List<TrafficMonitorResultsDO>> resultsDOMap = resultsDOList.stream().collect(Collectors.groupingBy(TrafficMonitorResultsDO::getEquId));

        // 配置线程池
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new CopyOnWriteArrayList<>();

        list.forEach(viewOpsMonitorResultsDailyAnalysisDO -> futures.add(executorService.submit(() -> {
            try {
                TrafficMonitorResultsDailyAnalysisDO analysisDO = BeanUtils.toBean(viewOpsMonitorResultsDailyAnalysisDO, TrafficMonitorResultsDailyAnalysisDO.class);
                analysisDO.setAnalysisDate(DateUtil.toLocalDateTime(DateUtil.beginOfDay(date)));
                monitorResultsDailyAnalysisMapper.insert(analysisDO);

                List<TrafficMonitorResultsDO> equResultList = resultsDOMap.get(viewOpsMonitorResultsDailyAnalysisDO.getEquId());
                if (!CollectionUtils.isEmpty(equResultList)) {
                    List<TrafficMonitorResultsDailyDetailDO> instList = equResultList.stream().map(equResult -> {
                        TrafficMonitorResultsDailyDetailDO instInfo = BeanUtils.toBean(equResult, TrafficMonitorResultsDailyDetailDO.class);
                        instInfo.setAnalysisId(analysisDO.getId());
                        return instInfo;
                    }).collect(Collectors.toList());
                    monitorResultsDailyDetailMapper.insert(instList);
                }
            } catch (Exception e) {
                log.error("多线程处理异常", e);
            }
        })));

        // 等待所有任务完成
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("任务执行异常", e);
            }
        });

        executorService.shutdown();
    }
}
