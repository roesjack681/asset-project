package com.holdtime.ops.module.job.gaw;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import com.holdtime.ops.module.entity.BtjczhVioEquipmentDailySituationDO;
import com.holdtime.ops.module.entity.ViewVioEquipmentDailySituationDO;
import com.holdtime.ops.module.entity.mapper.BtjczhVioEquipmentDailySituationMapper;
import com.holdtime.ops.module.entity.mapper.ViewVioEquipmentDailySituationMapper;
import com.holdtime.ops.module.model.vo.ViewVioEquipmentDailySituationPageReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
@Slf4j
public class BtjczhVioEquipmentDailySituationAnalysisJob implements JobHandler {
    @Resource
    private ViewVioEquipmentDailySituationMapper viewVioEquipmentDailySituationMapper;
    @Resource
    private BtjczhVioEquipmentDailySituationMapper btjczhVioEquipmentDailySituationMapper;

    /**
     * 定时任务：公安内网执行，将分析结果存到每日分析表
     * @param param
     * @return
     */
    @Override
    public String execute(String param) {
        scheduledAnalysisEvent(param);
        return "分析完成！";
    }

    private void scheduledAnalysisEvent(String diffDay) {
        int pageSize = 1000;
        int pageNo = 1;

        if (StringUtils.isBlank(diffDay)) {
            diffDay = "7";
        }
//        Date date = DateUtil.offsetDay(new Date(), (-1 * Integer.parseInt(diffDay)));
        Date endDate = DateUtil.offsetDay(new Date(), -2);
        Date startDate = DateUtil.offsetDay(new Date(), (-1 * Integer.parseInt(diffDay)));
        LocalDateTime[] wfsj = new LocalDateTime[] {
                DateUtil.toLocalDateTime(DateUtil.beginOfDay(startDate)),
                DateUtil.toLocalDateTime(DateUtil.endOfDay(endDate))
        };

        // 删除历史记录，重新查询
        LambdaQueryWrapperX<BtjczhVioEquipmentDailySituationDO> deleteLambdaQueryWrapper = new LambdaQueryWrapperX<>();
        deleteLambdaQueryWrapper.betweenIfPresent(BtjczhVioEquipmentDailySituationDO::getOccurrenceDay, wfsj);
        btjczhVioEquipmentDailySituationMapper.delete(deleteLambdaQueryWrapper);

        ViewVioEquipmentDailySituationPageReqVO pageReqVO = new ViewVioEquipmentDailySituationPageReqVO();
        do {
            pageReqVO.setPageSize(pageSize);
            pageReqVO.setPageNo(pageNo);
            pageReqVO.setWfsjs(wfsj);

            List<ViewVioEquipmentDailySituationDO> analysisDOList = Optional.ofNullable(
                    viewVioEquipmentDailySituationMapper.selectPage(pageReqVO).getList()).orElseGet(ArrayList::new);
            if (CollectionUtils.isEmpty(analysisDOList)) {
                pageNo = 0;
                continue;
            }
            pageNo++;

            List<BtjczhVioEquipmentDailySituationDO> instList = new ArrayList<>();

            analysisDOList.forEach(item -> {
                BtjczhVioEquipmentDailySituationDO detailDO = buildMonitorDetailDO(item);
                instList.add(detailDO);
            });

            btjczhVioEquipmentDailySituationMapper.insert(instList);
        } while (pageNo != 0);
    }

    private BtjczhVioEquipmentDailySituationDO buildMonitorDetailDO(ViewVioEquipmentDailySituationDO item) {
        BtjczhVioEquipmentDailySituationDO detailDO = new BtjczhVioEquipmentDailySituationDO();
        detailDO.setOccurrenceDay(item.getWfsj());
        detailDO.setKkbh(item.getKkbh());
        detailDO.setSbbh(item.getSbbh());
        detailDO.setNoSurveilCnt(item.getDswfCnt() == null ? 0 : item.getDswfCnt().intValue());
        detailDO.setValidSurveilCnt(item.getYxwfCnt() == null ? 0 : item.getYxwfCnt().intValue());
        detailDO.setInvalidSurveilCnt(item.getWxwfCnt() == null ? 0 : item.getWxwfCnt().intValue());
        detailDO.setRecognitionAccuracyRate(item.getKyl());
        detailDO.setRecognitionAccuracyPickDate(item.getJcsj());

        return  detailDO;
    }
}
