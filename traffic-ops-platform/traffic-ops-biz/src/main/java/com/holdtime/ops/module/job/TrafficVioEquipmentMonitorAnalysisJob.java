package com.holdtime.ops.module.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.holdtime.ops.module.entity.TrafficVioEquipmentMonitorAnalysisDO;
import com.holdtime.ops.module.entity.ViewVioEquipmentMonitorAnalysisDO;
import com.holdtime.ops.module.entity.mapper.TrafficVioEquipmentMonitorAnalysisMapper;
import com.holdtime.ops.module.entity.mapper.ViewVioEquipmentMonitorAnalysisMapper;
import com.holdtime.ops.module.mapper.TrafficMonitorResultsExtMapper;
import com.holdtime.ops.module.model.vo.ViewVioEquipmentMonitorAnalysisPageReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

@Component
@Slf4j
public class TrafficVioEquipmentMonitorAnalysisJob implements JobHandler {
    @Resource
    private ViewVioEquipmentMonitorAnalysisMapper viewVioEquipmentMonitorAnalysisMapper;
    @Resource
    private TrafficVioEquipmentMonitorAnalysisMapper trafficVioEquipmentMonitorAnalysisMapper;
    @Resource
    private TrafficMonitorResultsExtMapper trafficMonitorResultsExtMapper;

    /**
     * 定时任务：互联网执行，将通断、视频情况及违法状态情况汇总分析表中
     * @param param 执行参数
     * @return 执行结果
     */
    @Override
    public String execute(String param) {
        scheduledAnalysisEvent();
        return "分析完成！";
    }

    private void scheduledAnalysisEvent() {
        // 清空历史分析
        trafficMonitorResultsExtMapper.deleteTrafficVioEquipmentMonitorAnalysis();

        int pageSize = 1000;
        int pageNo = 1;

        // 使用 ThreadPoolExecutor 替代 Executors.newFixedThreadPool
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new ThreadPoolExecutor.CallerRunsPolicy());

        try {
            ViewVioEquipmentMonitorAnalysisPageReqVO pageReqVO = new ViewVioEquipmentMonitorAnalysisPageReqVO();
            do {
                pageReqVO.setPageSize(pageSize);
                pageReqVO.setPageNo(pageNo);

                List<ViewVioEquipmentMonitorAnalysisDO> analysisDOList = Optional.ofNullable(
                        viewVioEquipmentMonitorAnalysisMapper.selectPage(pageReqVO).getList()).orElseGet(ArrayList::new);
                if (CollectionUtils.isEmpty(analysisDOList)) {
                    pageNo = 0;
                    continue;
                }
                pageNo++;

                // 使用 CompletableFuture 替代 CountDownLatch
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                List<TrafficVioEquipmentMonitorAnalysisDO> instList = Collections.synchronizedList(new ArrayList<>());

                analysisDOList.forEach(item -> {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        TrafficVioEquipmentMonitorAnalysisDO detailDO = buildMonitorDetailDO(item);
                        instList.add(detailDO);
                    }, executorService);
                    futures.add(future);
                });

                // 等待所有任务完成
                try {
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                            .get(60, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    Thread.currentThread().interrupt();
                    log.error("任务执行异常", e);
                }

                trafficVioEquipmentMonitorAnalysisMapper.insert(instList);
            } while (pageNo != 0);
        } finally {
            // 优雅关闭线程池
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private TrafficVioEquipmentMonitorAnalysisDO buildMonitorDetailDO(ViewVioEquipmentMonitorAnalysisDO item) {
        TrafficVioEquipmentMonitorAnalysisDO detailDO = new TrafficVioEquipmentMonitorAnalysisDO();
        detailDO.setEquId(item.getEquId());
        detailDO.setKkbh(item.getKkbh());
        detailDO.setSbbh(item.getSbbh());
        detailDO.setPingNormalCnt(item.getPingNormalCnt() == null ? 0 : item.getPingNormalCnt().intValue());
        detailDO.setPingAbnormalCnt(item.getPingAbnormalCnt() == null ? 0 : item.getPingAbnormalCnt().intValue());
        detailDO.setPingHealthRate(item.getPingHealthRate());
        detailDO.setVideoNormalCnt(item.getVideoNormalCnt() == null ? 0 : item.getVideoNormalCnt().intValue());
        detailDO.setVideoAbnormalCnt(item.getVideoAbnormalCnt() == null ? 0 : item.getVideoAbnormalCnt().intValue());
        detailDO.setVideoHealthRate(item.getVideoHealthRate());
        detailDO.setNoSurveilCnt(item.getNoSurveilCnt() == null ? 0 : item.getNoSurveilCnt().intValue());
        detailDO.setValidSurveilCnt(item.getValidSurveilCnt() == null ? 0 : item.getValidSurveilCnt().intValue());
        detailDO.setInvalidSurveilCnt(item.getInvalidSurveilCnt() == null ? 0 : item.getInvalidSurveilCnt().intValue());
        detailDO.setValidSurveilRate(item.getValidSurveilRate());
        detailDO.setRecognitionAccuracyRate(item.getRecognitionAccuracyRate());
        detailDO.setRecognitionAccuracyPickDate(item.getRecognitionAccuracyPickDate());
        detailDO.setMonitorCnt(item.getMonitorCnt() == null ? 0 : item.getMonitorCnt().intValue());
        return detailDO;
    }
}
