package com.holdtime.ops.module.job.spw;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.PoliceOpsElectricPolicePageReqVO;
import com.holdtime.ops.module.service.PoliceMonitorPingExtService;
import com.holdtime.ops.module.utils.PingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PoliceMonitorPingExecJob implements JobHandler {
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private PoliceMonitorPingExtService policeMonitorPingExtService;

    // 全局线程池 - 根据CPU核心数和I/O密集型任务调整
    private ExecutorService executorService;
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static final int MAX_POOL_SIZE = 50;
    private static final int BATCH_SIZE = 500; // 每批处理设备数

    @PostConstruct
    public void init() {
        // 使用可伸缩线程池
        executorService = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
        log.info("PoliceMonitorPingExecJob 线程池初始化完成, coreSize: {}, maxSize: {}", CORE_POOL_SIZE, MAX_POOL_SIZE);
    }

    @PreDestroy
    public void destroy() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            log.info("PoliceMonitorPingExecJob 线程池已关闭");
        }
    }

    @Override
    public String execute(String param) {
        scheduledPingEvent();
        return "分析完成！";
    }

    private void scheduledPingEvent() {
        LocalDateTime execDate = DateUtil.toLocalDateTime(new Date());
        long startTime = System.currentTimeMillis();

        // 1. 一次性查询所有设备
        List<PoliceOpsElectricPoliceDO> allDevices = Optional.ofNullable(
                policeOpsElectricPoliceMapper.selectList()).orElseGet(ArrayList::new);

        if (CollectionUtils.isEmpty(allDevices)) {
            log.info("没有需要检测的设备");
            return;
        }

        log.info("开始设备通断检测, 设备数量: {}", allDevices.size());

        // 2. 分批提交任务
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<List<PoliceOpsElectricPoliceDO>> batches = partition(allDevices, BATCH_SIZE);

        for (int batchIndex = 0; batchIndex < batches.size(); batchIndex++) {
            List<PoliceOpsElectricPoliceDO> batch = batches.get(batchIndex);
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                processBatch(batch, execDate);
            }, executorService);
            futures.add(future);
        }

        // 3. 等待所有批次完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        long elapsed = System.currentTimeMillis() - startTime;
        log.info("设备通断检测完成, 设备数量: {}, 耗时: {}秒", allDevices.size(), elapsed / 1000.0);
    }

    /**
     * 处理一批设备 - 方案3: 批量处理 + 保持单条推送格式
     */
    private void processBatch(List<PoliceOpsElectricPoliceDO> devices, LocalDateTime execDate) {
        String threadName = Thread.currentThread().getName();
        log.debug("线程 {} 开始处理 {} 个设备", threadName, devices.size());

        // 1. 并行ping所有设备
        List<PoliceMonitorPingDetailDO> pingResults = devices.parallelStream()
            .map(device -> {
                try {
                    PingUtil.PingResult pingResult = PingUtil.pingWithLossRate(
                        device.getIpAddr(), 1000, 10);

                    PoliceMonitorPingDetailDO pingDetail = new PoliceMonitorPingDetailDO();
                    pingDetail.setEquId(device.getId());
                    pingDetail.setIpAddr(device.getIpAddr());
                    pingDetail.setEquLable(device.getEquLable());
                    pingDetail.setOccurrenceTime(execDate);
                    pingDetail.setPingStatus(pingResult.isReachable() ? "正常" : "IP不可达");
                    pingDetail.setPingCnt(10);
                    pingDetail.setLossRate(java.math.BigDecimal.valueOf(pingResult.getLossRate()));
                    pingDetail.setSource(OpsConstants.STRING_ONE);

                    // 先插入数据库
                    policeMonitorPingExtService.insertPingDetail(pingDetail);

                    return pingDetail;
                } catch (Exception e) {
                    log.error("ping检测异常 - IP: {}, 设备ID: {}, 线程: {}",
                        device.getIpAddr(), device.getId(), threadName, e);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        // 2. 异步推送所有结果到Kafka (保持单条格式，消费端无需修改)
        if (!CollectionUtils.isEmpty(pingResults)) {
            pingResults.forEach(pingDetail -> policeMonitorPingExtService.sendPingToKafka(pingDetail));
        }

        int successCount = pingResults.size();
        int failCount = devices.size() - successCount;
        log.debug("线程 {} 处理完成, 成功: {}, 失败: {}", threadName, successCount, failCount);
    }

    /**
     * 将列表分成指定大小的批次
     */
    private <T> List<List<T>> partition(List<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            batches.add(list.subList(i, Math.min(i + batchSize, list.size())));
        }
        return batches;
    }
}
