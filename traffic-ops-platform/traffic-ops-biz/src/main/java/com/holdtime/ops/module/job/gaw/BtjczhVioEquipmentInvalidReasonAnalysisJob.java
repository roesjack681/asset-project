package com.holdtime.ops.module.job.gaw;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.holdtime.ops.module.entity.TrafficVioEquipmentInvalidReasonAnalysisDO;
import com.holdtime.ops.module.entity.mapper.TrafficVioEquipmentInvalidReasonAnalysisMapper;
import com.holdtime.ops.module.mapper.OpsAnalysisExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Component
@Slf4j
public class BtjczhVioEquipmentInvalidReasonAnalysisJob implements JobHandler {
    @Resource
    private OpsAnalysisExtMapper opsAnalysisExtMapper;
    @Resource
    private TrafficVioEquipmentInvalidReasonAnalysisMapper trafficVioEquipmentInvalidReasonAnalysisMapper;

    /**
     * 定时任务：公安内网执行，分析违法抓拍无效原因
     * @param param 执行参数
     * @return 执行结果
     */
    @Override
    public String execute(String param) {
        scheduledAnalysisEvent(param);
        return "分析完成！";
    }

    private void scheduledAnalysisEvent(String diffDay) {
        if (StringUtils.isBlank(diffDay)) {
            diffDay = "7";
        }
        Date date = DateUtil.offsetDay(DateUtil.beginOfDay(new Date()), (-1 * Integer.parseInt(diffDay)));

        // 删除历史数据
        LambdaQueryWrapperX<TrafficVioEquipmentInvalidReasonAnalysisDO> deleteLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        deleteLambdaQueryWrapperX.ge(TrafficVioEquipmentInvalidReasonAnalysisDO::getOccurrenceTime, date);
        trafficVioEquipmentInvalidReasonAnalysisMapper.delete(deleteLambdaQueryWrapperX);

        List<Map<String, Object>> resultList = opsAnalysisExtMapper.analysisVioInvalidReason(DateUtil.format(date, "yyyyMMdd"));
        if (CollectionUtils.isEmpty(resultList)) {
            return;
        }

        List<TrafficVioEquipmentInvalidReasonAnalysisDO> instList = new ArrayList<>();
        Optional.of(resultList).orElse(new ArrayList<>()).forEach(resultMap -> {
            String sbbh = (String) resultMap.get("sbbh");
            String wfsj = (String) resultMap.get("wfsj");
            String cwyy = (String) resultMap.get("cwyy");
            Long cnt = (Long) resultMap.get("cnt");

            TrafficVioEquipmentInvalidReasonAnalysisDO invalidReasonAnalysisDO = new TrafficVioEquipmentInvalidReasonAnalysisDO();
            invalidReasonAnalysisDO.setSbbh(sbbh);
            if (StringUtils.isNotBlank(wfsj)) {
                invalidReasonAnalysisDO.setOccurrenceTime(DateUtil.toLocalDateTime(DateUtil.parse(wfsj, "yyyyMMdd")));
            }
            invalidReasonAnalysisDO.setInvalidReason(cwyy);
            invalidReasonAnalysisDO.setInvalidCnt(cnt);
            instList.add(invalidReasonAnalysisDO);
        });

        trafficVioEquipmentInvalidReasonAnalysisMapper.insert(instList);
    }

}
