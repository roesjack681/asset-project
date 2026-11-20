package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.mapper.PoliceOpsEquipInfoExtMapper;
import com.holdtime.ops.module.model.dto.PoliceMonitorPingDetailDTO;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsImportAnalysisExtPageReqVO;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsImportAnalysisExtRespVO;
import com.holdtime.ops.module.service.PoliceMonitorResultsImportAnalysisExtService;
import com.xingyuv.captcha.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 公安监测结果导入分析 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorResultsImportAnalysisExtServiceImpl implements PoliceMonitorResultsImportAnalysisExtService {

    @Resource
    private ViewPoliceMonitorResultsImportAnalysisMapper monitorResultsImportAnalysisMapper;
    @Resource
    private PoliceMonitorResultsImportAnalysisMapper policeMonitorResultsImportAnalysisMapper;
    @Resource
    private ViewPoliceMonitorResultsImportSituationMapper viewPoliceMonitorResultsImportSituationMapper;
    @Resource
    private TrafficOpsMaintainFlowRecordMapper opsMaintainFlowRecordMapper;
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private PoliceOpsEquipInfoExtMapper policeOpsEquipInfoExtMapper;


    @Override
    public void analysisMonitorResultsImportDetail(LocalDateTime occurrenceTime) {
        if (occurrenceTime ==  null) {
            occurrenceTime = DateUtil.toLocalDateTime(new Date());
        }
        // 查询所有电警统计结果
        List<ViewPoliceMonitorResultsImportAnalysisDO> importAnalysisDOList = monitorResultsImportAnalysisMapper.selectList();
        if (CollectionUtils.isEmpty(importAnalysisDOList)) {
            return;
        }

        // 初始化线程池
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Void>> futures = new ArrayList<>();

        // 查询电警分析
        List<PoliceMonitorResultsImportAnalysisDO> analysisDOList = policeMonitorResultsImportAnalysisMapper.selectList();
        Map<String, PoliceMonitorResultsImportAnalysisDO> analysisDOMap = Optional.ofNullable(analysisDOList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(PoliceMonitorResultsImportAnalysisDO::getEquId, electricPoliceDO -> electricPoliceDO, (k1, k2) -> k1));

        // 线程安全的列表
        List<PoliceMonitorResultsImportAnalysisDO> instList = Collections.synchronizedList(new ArrayList<>());
        List<PoliceMonitorResultsImportAnalysisDO> updList = Collections.synchronizedList(new ArrayList<>());

        // 拆分任务
        int batchSize = 100;
        LocalDateTime finalOccurrenceTime = occurrenceTime;
        for (int i = 0; i < importAnalysisDOList.size(); i += batchSize) {
            int end = Math.min(i + batchSize, importAnalysisDOList.size());
            List<ViewPoliceMonitorResultsImportAnalysisDO> batch = importAnalysisDOList.subList(i, end);

            futures.add(executorService.submit(() -> {
                for (ViewPoliceMonitorResultsImportAnalysisDO importAnalysisDO : batch) {
                    PoliceMonitorResultsImportAnalysisDO analysisDO = BeanUtils.toBean(importAnalysisDO, PoliceMonitorResultsImportAnalysisDO.class);
                    analysisDO.setMonitorCnt(importAnalysisDO.getMonitorSumCnt());
                    // 查询设备是否有工单，并且确认时间
                    LocalDateTime lastRepairTime = null;
                    if (StringUtils.isNotBlank(importAnalysisDO.getEquId())) {
                        LambdaQueryWrapper<TrafficOpsMaintainFlowRecordDO> flowRecordDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                        flowRecordDOLambdaQueryWrapper.eq(TrafficOpsMaintainFlowRecordDO::getOperateStatus, OpsConstants.STATUS_COMPLETED);
                        flowRecordDOLambdaQueryWrapper.exists("select 1 from traffic_ops_maintenance m where m.equ_id = '" + importAnalysisDO.getEquId() + "' and m.id = traffic_ops_maintain_flow_record.maintenance_id and m.report_time = ( select max(report_time) from traffic_ops_maintenance where equ_id = m.equ_id)");
                        flowRecordDOLambdaQueryWrapper.orderByDesc(TrafficOpsMaintainFlowRecordDO::getUpdateTime);
                        List<TrafficOpsMaintainFlowRecordDO> flowRecordDOList = opsMaintainFlowRecordMapper.selectList(flowRecordDOLambdaQueryWrapper);
                        if (!CollectionUtils.isEmpty(flowRecordDOList)) {
                            lastRepairTime = flowRecordDOList.get(0).getUpdateTime();
                        }
                    }

                    if (!CollectionUtils.isEmpty(analysisDOMap) && analysisDOMap.containsKey(importAnalysisDO.getEquId())) {
                        analysisDO.setId(analysisDOMap.get(importAnalysisDO.getEquId()).getId());
                        analysisDO.setCreator(analysisDOMap.get(importAnalysisDO.getEquId()).getCreator());
                        analysisDO.setCreateTime(analysisDOMap.get(importAnalysisDO.getEquId()).getCreateTime());
                        if (lastRepairTime == null) {
                            analysisDO.setLastRepairTime(analysisDOMap.get(importAnalysisDO.getEquId()).getLastRepairTime());
                        } else {
                            analysisDO.setLastRepairTime(lastRepairTime);
                        }
                        updList.add(analysisDO);
                    } else {
                        if (lastRepairTime == null) {
                            analysisDO.setLastRepairTime(finalOccurrenceTime);
                        } else {
                            analysisDO.setLastRepairTime(lastRepairTime);
                        }
                        instList.add(analysisDO);
                    }
                }
                return null;
            }));
        }

        // 等待所有任务完成
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("多线程执行异常", e);
            }
        }

        // 关闭线程池
        executorService.shutdown();

        // 执行数据库操作
        if (!CollectionUtils.isEmpty(instList)) {
            policeMonitorResultsImportAnalysisMapper.insert(instList);
        }

        if (!CollectionUtils.isEmpty(updList)) {
            policeMonitorResultsImportAnalysisMapper.updateById(updList);
        }
    }

    @Override
    public PageResult<PoliceMonitorResultsImportAnalysisExtRespVO> getMonitorResultsImportAnalysisPage(PoliceMonitorResultsImportAnalysisExtPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PoliceMonitorResultsImportAnalysisDO> wrapper = new LambdaQueryWrapperX<PoliceMonitorResultsImportAnalysisDO>()
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getEquId, pageReqVO.getEquId())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getEquLable, pageReqVO.getEquLable())
                .eqIfPresent(PoliceMonitorResultsImportAnalysisDO::getEventType, pageReqVO.getEventType())
                .betweenIfPresent(PoliceMonitorResultsImportAnalysisDO::getLastRepairTime, pageReqVO.getLastRepairTime())
                .betweenIfPresent(PoliceMonitorResultsImportAnalysisDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorResultsImportAnalysisDO::getDeletedTime, pageReqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorResultsImportAnalysisDO::getPingAbnormalCnt)
                .orderByDesc(PoliceMonitorResultsImportAnalysisDO::getId);

        if (OpsConstants.STRING_ZERO.equals(pageReqVO.getPingStatus())) { // 全断
            wrapper.eq(PoliceMonitorResultsImportAnalysisDO::getPingNormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getPingStatus())) { // 全通
            wrapper.eq(PoliceMonitorResultsImportAnalysisDO::getPingAbnormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getPingStatus())) {
            wrapper.ne(PoliceMonitorResultsImportAnalysisDO::getPingNormalCnt, OpsConstants.STRING_ZERO);
            wrapper.ne(PoliceMonitorResultsImportAnalysisDO::getPingAbnormalCnt, OpsConstants.STRING_ZERO);
        }

        if (OpsConstants.STRING_ZERO.equals(pageReqVO.getVideoStatus())) { // 全断
            wrapper.eq(PoliceMonitorResultsImportAnalysisDO::getVideoNormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getVideoStatus())) { // 全通
            wrapper.eq(PoliceMonitorResultsImportAnalysisDO::getVideoAbnormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getVideoStatus())) {
            wrapper.ne(PoliceMonitorResultsImportAnalysisDO::getVideoNormalCnt, OpsConstants.STRING_ZERO);
            wrapper.ne(PoliceMonitorResultsImportAnalysisDO::getVideoAbnormalCnt, OpsConstants.STRING_ZERO);
        }

        String existsDjSql = "SELECT 1 from police_ops_electric_police e where police_monitor_results_import_analysis.equ_id = e.id ";
        boolean hasDjChildSql = false;

        if (StringUtils.isNotBlank(pageReqVO.getEquName())) {
            existsDjSql = existsDjSql + " and e.equ_name like '%" + pageReqVO.getEquName() + "%'";
            hasDjChildSql = true;
        }

        if (StringUtils.isNotBlank(pageReqVO.getIpAddr())) {
            existsDjSql = existsDjSql + " and e.ip_addr like '%" + pageReqVO.getIpAddr() + "%'";
            hasDjChildSql = true;
        }
        if (hasDjChildSql) {
            wrapper.exists(existsDjSql);
        }

        String existsSql = "select 1 from v_police_monitor_results_import_situation s where police_monitor_results_import_analysis.equ_id = s.equ_id ";
        boolean hasChildSql = false;

        if (StringUtils.isNotBlank(pageReqVO.getFinalPingStatus())) {
            existsSql = existsSql + " and s.final_ping_status = '" + pageReqVO.getFinalPingStatus() + "'";
            hasChildSql = true;
        }

        if (StringUtils.isNotBlank(pageReqVO.getFinalVideoStatus())) {
            existsSql = existsSql + " and s.final_video_status = '" + pageReqVO.getFinalVideoStatus() + "'";
            hasChildSql = true;
        }
        if (hasChildSql) {
            wrapper.exists(existsSql);
        }

        PageResult<PoliceMonitorResultsImportAnalysisExtRespVO> pageResult = BeanUtils.toBean(policeMonitorResultsImportAnalysisMapper.selectPage(pageReqVO, wrapper), PoliceMonitorResultsImportAnalysisExtRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach( respVO -> {
            PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO = policeOpsElectricPoliceMapper.selectById(respVO.getEquId());
            if (policeOpsElectricPoliceDO != null) {
                respVO.setEquName(policeOpsElectricPoliceDO.getEquName());
                respVO.setIpAddr(policeOpsElectricPoliceDO.getIpAddr());
                respVO.setEquLable(policeOpsElectricPoliceDO.getEquLable());
            }

            LambdaQueryWrapperX<ViewPoliceMonitorResultsImportSituationDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            lambdaQueryWrapperX.eq(ViewPoliceMonitorResultsImportSituationDO::getEquId, respVO.getEquId());
            List<ViewPoliceMonitorResultsImportSituationDO> situationDOList =  viewPoliceMonitorResultsImportSituationMapper.selectList(lambdaQueryWrapperX);
            if (!CollectionUtils.isEmpty(situationDOList)) {
                respVO.setFinalPingStatus(situationDOList.get(0).getFinalPingStatus());
                respVO.setFinalVideoStatus(situationDOList.get(0).getFinalVideoStatus());
            }
        });

        return pageResult;
    }

    @Override
    public PageResult<PoliceMonitorResultsImportAnalysisExtRespVO> queryHuazhiMonitorOnOffResultPage(PoliceMonitorResultsImportAnalysisExtPageReqVO pageReqVO) {
        if (StringUtils.isBlank(pageReqVO.getStartDate()) || StringUtils.isBlank(pageReqVO.getEndDate())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        if (pageReqVO.getIpAddr() == null) {
            pageReqVO.setIpAddr("");
        }

        if (pageReqVO.getEquLable() == null) {
            pageReqVO.setEquLable("");
        }

        if (pageReqVO.getPingStatus() == null) {
            pageReqVO.setPingStatus("");
        }

        if (pageReqVO.getVideoStatus() == null) {
            pageReqVO.setVideoStatus("");
        }

        Page<?> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        IPage<PoliceMonitorResultsImportAnalysisExtRespVO> resultDTOIPage = policeOpsEquipInfoExtMapper.queryHuazhiMonitorOnOffResultPage(page, pageReqVO.getIpAddr(),
                pageReqVO.getEquLable(), pageReqVO.getPingStatus(), pageReqVO.getVideoStatus(),
                pageReqVO.getStartDate(), pageReqVO.getEndDate());
        if (resultDTOIPage == null || CollectionUtils.isEmpty(resultDTOIPage.getRecords())) {
            return new PageResult<>();
        }

        return new PageResult<>(resultDTOIPage.getRecords(), resultDTOIPage.getTotal());
    }
}