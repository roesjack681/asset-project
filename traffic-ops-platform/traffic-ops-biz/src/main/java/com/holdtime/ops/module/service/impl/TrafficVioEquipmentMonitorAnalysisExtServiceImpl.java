package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.mapper.OpsAnalysisExtMapper;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficVioEquipmentMonitorAnalysisExtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 集指设备检测分析 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class TrafficVioEquipmentMonitorAnalysisExtServiceImpl implements TrafficVioEquipmentMonitorAnalysisExtService {

    @Resource
    private TrafficVioEquipmentMonitorAnalysisMapper vioEquipmentMonitorAnalysisMapper;
    @Resource
    private TrafficOpsElectricPoliceMapper trafficOpsElectricPoliceMapper;
    @Resource
    private TrafficOpsMemberMapper trafficOpsMemberMapper;
    @Resource
    private TrafficVioEquipmentMonitorDetailMapper trafficVioEquipmentMonitorDetailMapper;
    @Resource
    private ViewVioSurveilAnalysisMapper viewVioSurveilAnalysisMapper;
    @Resource
    private TrafficVioEquipmentIllegalAnalysisMapper trafficVioEquipmentIllegalAnalysisMapper;
    @Resource
    private OpsAnalysisExtMapper opsAnalysisExtMapper;

    @Override
    public TrafficVioEquipmentMonitorAnalysisDO getVioEquipmentMonitorAnalysis(String id) {
        return vioEquipmentMonitorAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficVioEquipmentMonitorAnalysisExtRespVO> getVioEquipmentMonitorAnalysisPage(TrafficVioEquipmentMonitorAnalysisExtPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficVioEquipmentMonitorAnalysisDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficVioEquipmentMonitorAnalysisDO>()
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getEquId, pageReqVO.getEquId())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getKkbh, pageReqVO.getKkbh())
                .eqIfPresent(TrafficVioEquipmentMonitorAnalysisDO::getSbbh, pageReqVO.getSbbh())
                .orderByDesc(TrafficVioEquipmentMonitorAnalysisDO::getId);

//        if(StringUtils.isNotBlank(pageReqVO.getIntersectionName())){
//            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
//            if (!CollectionUtils.isEmpty(roadNameList)) {
//                for (String roadName : roadNameList) {
//                    lambdaQueryWrapperX.like(TrafficMonitorResultsImportDetailDO::getIntersectionName, roadName);
//                }
//            }
//        }
        if (pageReqVO.getValidSurveilCnt() != null) {
            if (OpsConstants.STRING_FIVE.equals(pageReqVO.getValidSurveilCntOperator())) {
                lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilCnt, pageReqVO.getValidSurveilCnt());
            } else if (OpsConstants.STRING_FOUR.equals(pageReqVO.getValidSurveilCntOperator())) {
                lambdaQueryWrapperX.le(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilCnt, pageReqVO.getValidSurveilCnt());
            } else if (OpsConstants.STRING_THREE.equals(pageReqVO.getValidSurveilCntOperator())) {
                lambdaQueryWrapperX.lt(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilCnt, pageReqVO.getValidSurveilCnt());
            } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getValidSurveilCntOperator())) {
                lambdaQueryWrapperX.ge(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilCnt, pageReqVO.getValidSurveilCnt());
            } else {
                lambdaQueryWrapperX.gt(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilCnt, pageReqVO.getValidSurveilCnt());
            }
        }

        if (pageReqVO.getInvalidSurveilCnt() != null) {
            if (OpsConstants.STRING_FIVE.equals(pageReqVO.getInvalidSurveilCntOperator())) {
                lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorAnalysisDO::getInvalidSurveilCnt, pageReqVO.getInvalidSurveilCnt());
            } else if (OpsConstants.STRING_FOUR.equals(pageReqVO.getInvalidSurveilCntOperator())) {
                lambdaQueryWrapperX.le(TrafficVioEquipmentMonitorAnalysisDO::getInvalidSurveilCnt, pageReqVO.getInvalidSurveilCnt());
            } else if (OpsConstants.STRING_THREE.equals(pageReqVO.getInvalidSurveilCntOperator())) {
                lambdaQueryWrapperX.lt(TrafficVioEquipmentMonitorAnalysisDO::getInvalidSurveilCnt, pageReqVO.getInvalidSurveilCnt());
            } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getInvalidSurveilCntOperator())) {
                lambdaQueryWrapperX.ge(TrafficVioEquipmentMonitorAnalysisDO::getInvalidSurveilCnt, pageReqVO.getInvalidSurveilCnt());
            } else {
                lambdaQueryWrapperX.gt(TrafficVioEquipmentMonitorAnalysisDO::getInvalidSurveilCnt, pageReqVO.getInvalidSurveilCnt());
            }
        }

        if (pageReqVO.getNoSurveilCnt() != null) {
            if (OpsConstants.STRING_FIVE.equals(pageReqVO.getNoSurveilCntOperator())) {
                lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorAnalysisDO::getNoSurveilCnt, pageReqVO.getNoSurveilCnt());
            } else if (OpsConstants.STRING_FOUR.equals(pageReqVO.getNoSurveilCntOperator())) {
                lambdaQueryWrapperX.le(TrafficVioEquipmentMonitorAnalysisDO::getNoSurveilCnt, pageReqVO.getNoSurveilCnt());
            } else if (OpsConstants.STRING_THREE.equals(pageReqVO.getNoSurveilCntOperator())) {
                lambdaQueryWrapperX.lt(TrafficVioEquipmentMonitorAnalysisDO::getNoSurveilCnt, pageReqVO.getNoSurveilCnt());
            } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getNoSurveilCntOperator())) {
                lambdaQueryWrapperX.ge(TrafficVioEquipmentMonitorAnalysisDO::getNoSurveilCnt, pageReqVO.getNoSurveilCnt());
            } else {
                lambdaQueryWrapperX.gt(TrafficVioEquipmentMonitorAnalysisDO::getNoSurveilCnt, pageReqVO.getNoSurveilCnt());
            }
        }

        if (pageReqVO.getValidSurveilRate() != null) {
            if (OpsConstants.STRING_FIVE.equals(pageReqVO.getValidSurveilOperator())) {
                lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilRate, pageReqVO.getValidSurveilRate());
            } else if (OpsConstants.STRING_FOUR.equals(pageReqVO.getValidSurveilOperator())) {
                lambdaQueryWrapperX.le(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilRate, pageReqVO.getValidSurveilRate());
            } else if (OpsConstants.STRING_THREE.equals(pageReqVO.getValidSurveilOperator())) {
                lambdaQueryWrapperX.lt(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilRate, pageReqVO.getValidSurveilRate());
            } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getValidSurveilOperator())) {
                lambdaQueryWrapperX.ge(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilRate, pageReqVO.getValidSurveilRate());
            } else {
                lambdaQueryWrapperX.gt(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilRate, pageReqVO.getValidSurveilRate());
            }
        }



//        if (StringUtils.isNotBlank(pageReqVO.getMonitorSituation())) {
//            if ("true".equals(pageReqVO.getMonitorSituation())) {
//                lambdaQueryWrapperX.isNotNull(TrafficMonitorResultsImportDetailDO::getMonitorSituation);
//            } else {
//                lambdaQueryWrapperX.isNull(TrafficMonitorResultsImportDetailDO::getMonitorSituation);
//            }
//        }

        if (OpsConstants.STRING_ZERO.equals(pageReqVO.getPingStatus())) { // 全断
            lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorAnalysisDO::getPingNormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getPingStatus())) { // 全通
            lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorAnalysisDO::getPingAbnormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getPingStatus())) {
            lambdaQueryWrapperX.ne(TrafficVioEquipmentMonitorAnalysisDO::getPingNormalCnt, OpsConstants.STRING_ZERO);
            lambdaQueryWrapperX.ne(TrafficVioEquipmentMonitorAnalysisDO::getPingAbnormalCnt, OpsConstants.STRING_ZERO);
        }

        if (OpsConstants.STRING_ZERO.equals(pageReqVO.getVideoStatus())) { // 全断
            lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorAnalysisDO::getVideoNormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getVideoStatus())) { // 全通
            lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorAnalysisDO::getVideoAbnormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getVideoStatus())) {
            lambdaQueryWrapperX.ne(TrafficVioEquipmentMonitorAnalysisDO::getVideoNormalCnt, OpsConstants.STRING_ZERO);
            lambdaQueryWrapperX.ne(TrafficVioEquipmentMonitorAnalysisDO::getVideoAbnormalCnt, OpsConstants.STRING_ZERO);
        }

        if (OpsConstants.STRING_ZERO.equals(pageReqVO.getHasValidSurveilCnt())) { // 无有效违法
            lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getHasValidSurveilCnt())) {
            lambdaQueryWrapperX.ne(TrafficVioEquipmentMonitorAnalysisDO::getValidSurveilCnt, OpsConstants.STRING_ZERO);
        }

        if (OpsConstants.STRING_ZERO.equals(pageReqVO.getHasRecognitionAccuracyRate())) { // 无有效违法
            lambdaQueryWrapperX.isNull(TrafficVioEquipmentMonitorAnalysisDO::getRecognitionAccuracyRate);
        } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getHasRecognitionAccuracyRate())) {
            lambdaQueryWrapperX.isNotNull(TrafficVioEquipmentMonitorAnalysisDO::getRecognitionAccuracyRate);
        }

        StringBuilder childSql = new StringBuilder();
//        boolean hasChild = false;
        if (StringUtils.isNotBlank(pageReqVO.getIpAddr())) {
            childSql.append(" and e.ip_addr like '%").append(pageReqVO.getIpAddr()).append("%'");
//            hasChild = true;
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
//            hasChild = true;
            for (String equLabel : pageReqVO.getEquLableList()) {
                childSql.append(" and e.equ_lable like '%").append(equLabel).append("%'");
            }
        }


        if (!CollectionUtils.isEmpty(pageReqVO.getUnitList())) {
            StringBuilder unitChild = new StringBuilder();
            int index = 0;
            for (String unit : pageReqVO.getUnitList()) {
                if (index != 0) {
                    unitChild.append(" or ");
                }
                unitChild.append("e.unit = '").append(unit).append("'");
                index++;
            }
            childSql.append(" and (").append(unitChild).append(")");
        }

//        if (StringUtils.isNotBlank(pageReqVO.getDjType())) {
//            childSql.append(" and e.dj_type = '").append(pageReqVO.getDjType()).append("'");
//            hasChild = true;
//        }
        childSql.append(" and e.dj_type = '电子警察'");
        String existsSql = "select 1 from traffic_ops_electric_police e where e.id = traffic_vio_equipment_monitor_analysis.equ_id" + childSql;
        lambdaQueryWrapperX.exists(existsSql);


        PageResult<TrafficVioEquipmentMonitorAnalysisExtRespVO> pageResult = BeanUtils.toBean(vioEquipmentMonitorAnalysisMapper.selectPage(pageReqVO, lambdaQueryWrapperX), TrafficVioEquipmentMonitorAnalysisExtRespVO.class);

        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getList())) {
            return new PageResult<>();
        }

        List<String> equIdList = pageResult.getList().stream().map(TrafficVioEquipmentMonitorAnalysisExtRespVO::getEquId).collect(Collectors.toList());


        LambdaQueryWrapper<TrafficOpsElectricPoliceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TrafficOpsElectricPoliceDO::getId, equIdList);
        List<TrafficOpsElectricPoliceDO> electricPoliceDOList = trafficOpsElectricPoliceMapper.selectList(queryWrapper);

        List<String> memberBhList = Optional.ofNullable(electricPoliceDOList).orElse(new ArrayList<>()).stream().map(TrafficOpsElectricPoliceDO::getMemberBh).filter(StringUtils::isNotBlank).collect(Collectors.toList());

        Map<String, TrafficOpsMemberDO> memberMap;
        if (!CollectionUtils.isEmpty(memberBhList)) {
            LambdaQueryWrapper<TrafficOpsMemberDO> memeberQueryWrapper = new LambdaQueryWrapper<>();
            memeberQueryWrapper.in(TrafficOpsMemberDO::getId, memberBhList);
            List<TrafficOpsMemberDO> memberList = trafficOpsMemberMapper.selectList(memeberQueryWrapper);
            memberMap = Optional.ofNullable(memberList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(TrafficOpsMemberDO::getId, member -> member));
        } else {
            memberMap = new HashMap<>();
        }

        // 查询有效合计
        List<String> sbbhList = pageResult.getList().stream().map(TrafficVioEquipmentMonitorAnalysisExtRespVO::getSbbh).collect(Collectors.toList());
        LambdaQueryWrapper<TrafficVioEquipmentIllegalAnalysisDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(TrafficVioEquipmentIllegalAnalysisDO::getSbbh, sbbhList);
        List<TrafficVioEquipmentIllegalAnalysisDO> illegalAnalysisDOList = trafficVioEquipmentIllegalAnalysisMapper.selectList(lambdaQueryWrapper);
        Map<String, Integer> validCntMap = Optional.ofNullable(illegalAnalysisDOList).orElse(new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(TrafficVioEquipmentIllegalAnalysisDO::getSbbh,
                        Collectors.summingInt(TrafficVioEquipmentIllegalAnalysisDO::getIllegalCnt)));

        Map<String, TrafficOpsElectricPoliceDO> electricPoliceDOMap = Optional.ofNullable(electricPoliceDOList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(TrafficOpsElectricPoliceDO::getId, electricPoliceDO -> electricPoliceDO));


        pageResult.getList().forEach(dto -> {
            if (!CollectionUtils.isEmpty(validCntMap) && validCntMap.containsKey(dto.getSbbh())) {
                dto.setValidSurveilCnt(validCntMap.get(dto.getSbbh()));
                dto.setValidSurveilRate(new BigDecimal(dto.getValidSurveilCnt()).divide(new BigDecimal(dto.getValidSurveilCnt() + dto.getInvalidSurveilCnt()), 4, RoundingMode.HALF_UP));
            }

            TrafficOpsElectricPoliceDO electricPoliceDO = electricPoliceDOMap.get(dto.getEquId());
            if (electricPoliceDO != null) {
                dto.setIpAddr(electricPoliceDO.getIpAddr());
                dto.setEquLabel(electricPoliceDO.getEquLable());
                dto.setUnit(electricPoliceDO.getUnit());
                dto.setUnderWarranty(electricPoliceDO.getUnderWarranty());

                TrafficOpsMemberDO memberDO = memberMap.get(electricPoliceDO.getMemberBh());
                if (memberDO != null) {
                    String serialNum = "";
                    if (memberDO.getSerialNum() != null) {
                        serialNum = String.format("%03d", memberDO.getSerialNum());
                    }
                    String memberName = memberDO.getRoadName() + "/" + memberDO.getIntersectionName() + "/" + memberDO.getAdress() + "/" + serialNum;
                    dto.setMemberName(memberName);
                }
            }
        });

        return pageResult;
    }

    @Override
    public void analysisVioEquipmentSurveil(Date startDate) {
        Date endDate = DateUtil.offsetDay(new Date(), -2);
        LocalDateTime[] occurrenceDay = new LocalDateTime[] {
                DateUtil.toLocalDateTime(DateUtil.beginOfDay(startDate)),
                DateUtil.toLocalDateTime(DateUtil.endOfDay(endDate))
        };

        // 批量查询已存在的数据
        LambdaQueryWrapperX<TrafficVioEquipmentMonitorDetailDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.betweenIfPresent(TrafficVioEquipmentMonitorDetailDO::getOccurrenceTime, occurrenceDay);
        List<TrafficVioEquipmentMonitorDetailDO> existsVioEquipmentMonitorDetailDOList = trafficVioEquipmentMonitorDetailMapper.selectList(lambdaQueryWrapperX);
        Map<String, TrafficVioEquipmentMonitorDetailDO> existsVioEquipmentMonitorDetailDOMap = existsVioEquipmentMonitorDetailDOList.stream()
                .collect(Collectors.toMap(detail -> detail.getEquId() + DateUtil.format(detail.getOccurrenceTime(), "yyyyMMdd"), detailDO -> detailDO, (k1, k2) -> k1));

        // 初始化线程池
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Future<?>> futures = new ArrayList<>();

        ViewVioSurveilAnalysisPageReqVO pageReqVO = new ViewVioSurveilAnalysisPageReqVO();
        int pageSize = 10000;
        int pageNo = 1;

        do {
            pageReqVO.setPageSize(pageSize);
            pageReqVO.setPageNo(pageNo);
            pageReqVO.setOccurrenceDay(occurrenceDay);

            List<ViewVioSurveilAnalysisDO> analysisDOList = Optional.ofNullable(
                    viewVioSurveilAnalysisMapper.selectPage(pageReqVO).getList()).orElseGet(ArrayList::new);
            if (CollectionUtils.isEmpty(analysisDOList)) {
                pageNo = 0;
                continue;
            }
            pageNo++;

            // 提交任务到线程池
            futures.add(executorService.submit(() -> {
                List<TrafficVioEquipmentMonitorDetailDO> instList = Collections.synchronizedList(new ArrayList<>());;
                List<TrafficVioEquipmentMonitorDetailDO> updList = Collections.synchronizedList(new ArrayList<>());;

                analysisDOList.forEach(item -> {
                    TrafficVioEquipmentMonitorDetailDO existDetailDO = existsVioEquipmentMonitorDetailDOMap.get(item.getEquId() + DateTimeFormatter.ofPattern("yyyyMMdd").format(item.getOccurrenceDay()));
                    String updId = existDetailDO != null ? existDetailDO.getId() : null;
                    TrafficVioEquipmentMonitorDetailDO detailDO = buildMonitorDetailDO(item, updId);
                    if (existDetailDO == null) {
                        instList.add(detailDO);
                    } else {
                        updList.add(detailDO);
                    }
                });

                // 批量插入和更新
                if (!CollectionUtils.isEmpty(instList)) {
                    trafficVioEquipmentMonitorDetailMapper.insertBatch(instList);
                }
                if (!CollectionUtils.isEmpty(updList)) {
                    trafficVioEquipmentMonitorDetailMapper.updateById(updList);
                }
            }));

        } while (pageNo != 0);

        // 等待所有任务完成
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("多线程执行异常", e);
            }
        });

        executorService.shutdown();
    }

    @Override
    public List<Map<String, Collection<Integer>>> analysisVioInvalidReasonPieChart(String sbbh, String analysisDay) {
        return opsAnalysisExtMapper.analysisVioInvalidReasonPieChart(sbbh, analysisDay);
    }

    @Override
    public List<Map<String, Object>> analysisVioValidLineChart(String sbbh) {
        return opsAnalysisExtMapper.analysisVioValidLineChart(sbbh);
    }

    @Override
    public void remarkVioEquipmentMonitor(TrafficVioEquipmentMonitorRemarkSaveReqVO saveReqVO) {
        if (StringUtils.isBlank(saveReqVO.getOperateMark()) || saveReqVO.getOperateDate() == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_PARAM_MISSING);
        }

        // 根据设备编号和执行时间查询记录
        LambdaQueryWrapperX<TrafficVioEquipmentMonitorDetailDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorDetailDO::getSbbh, saveReqVO.getSbbh());
        lambdaQueryWrapperX.eq(TrafficVioEquipmentMonitorDetailDO::getOccurrenceTime, DateUtil.beginOfDay(saveReqVO.getOperateDate()));
        List<TrafficVioEquipmentMonitorDetailDO> detailDOList = trafficVioEquipmentMonitorDetailMapper.selectList(lambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(detailDOList)) {
            throw exception(new ErrorCode(1003002006, "未找到设备[" + saveReqVO.getSbbh() + "]的[" + DateUtil.format(saveReqVO.getOperateDate(), "yyyy-MM-dd") + "]的违法分析数据！"));
        }

        TrafficVioEquipmentMonitorDetailDO detailDO = detailDOList.get(0);
        detailDO.setOperateDate(DateUtil.toLocalDateTime(saveReqVO.getOperateDate()));
        detailDO.setOperateMark(saveReqVO.getOperateMark());
        detailDO.setOperateDesc(saveReqVO.getOperateDesc());
        trafficVioEquipmentMonitorDetailMapper.updateById(detailDO);
    }

    @Override
    public List<Map<String, Collection<Integer>>> analysisVioIllegalPieChart(String sbbh, String analysisDay) {
        return opsAnalysisExtMapper.analysisVioIllegalPieChart(sbbh, analysisDay);
    }

    private TrafficVioEquipmentMonitorDetailDO buildMonitorDetailDO(ViewVioSurveilAnalysisDO item, String updId) {
        TrafficVioEquipmentMonitorDetailDO detailDO = new TrafficVioEquipmentMonitorDetailDO();
        detailDO.setId(updId);
        if (StringUtils.isBlank(updId)) { // 更新情况不更新以下内容
            detailDO.setOccurrenceTime(item.getOccurrenceDay().atStartOfDay());
            detailDO.setEquId(item.getEquId());
            detailDO.setKkbh(item.getKkbh());
            detailDO.setSbbh(item.getSbbh());
            detailDO.setIpAddr(item.getIpAddr());

            int normalCnt = judgeMonitorCnt(item.getNormalCnt());
            int abnormalCnt = judgeMonitorCnt(item.getAbnormalCnt());
            if (normalCnt == 0 && abnormalCnt > 0) {
                detailDO.setPingStatus("全断");
            } else if (normalCnt > 0 && abnormalCnt == 0) {
                detailDO.setPingStatus("全通");
            } else if (normalCnt > 0 && abnormalCnt > 0) {
                detailDO.setPingStatus("时断时通");
            }

            detailDO.setVideoStatus(item.getStatus());
        }

        detailDO.setNoSurveilCnt(item.getNoSurveilCnt() == null ? 0 : item.getNoSurveilCnt().intValue());
        detailDO.setValidSurveilCnt(item.getValidSurveilCnt() == null ? 0 : item.getValidSurveilCnt().intValue());
        detailDO.setInvalidSurveilCnt(item.getInvalidSurveilCnt() == null ? 0 : item.getInvalidSurveilCnt().intValue());
        detailDO.setRecognitionAccuracyRate(item.getRecognitionAccuracyRate());
        detailDO.setRecognitionAccuracyPickDate(item.getRecognitionAccuracyPickDate());
        return detailDO;
    }

    private int judgeMonitorCnt(BigDecimal monitorCnt) {
        if (monitorCnt == null) {
            return 0;
        }

        return monitorCnt.intValue();
    }

}