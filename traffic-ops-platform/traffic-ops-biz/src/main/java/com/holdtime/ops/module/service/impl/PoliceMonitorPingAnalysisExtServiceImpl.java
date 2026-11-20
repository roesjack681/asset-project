package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.PoliceMonitorPingAnalysisMapper;
import com.holdtime.ops.module.entity.mapper.PoliceMonitorPingDetailMapper;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.ViewPoliceMonitorPingDetailSituationMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.mapper.PoliceOpsEquipInfoExtMapper;
import com.holdtime.ops.module.model.dto.PoliceMonitorPingDetailDTO;
import com.holdtime.ops.module.model.dto.PoliceMonitorPingHourStatusDTO;
import com.holdtime.ops.module.model.vo.PoliceMonitorPingAnalysisExtPageReqVO;
import com.holdtime.ops.module.model.vo.PoliceMonitorPingAnalysisExtRespVO;
import com.holdtime.ops.module.service.PoliceMonitorPingAnalysisExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 电警通断检测管理 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorPingAnalysisExtServiceImpl implements PoliceMonitorPingAnalysisExtService {

    @Resource
    private PoliceMonitorPingAnalysisMapper monitorPingAnalysisMapper;
    @Resource
    private PoliceMonitorPingDetailMapper policeMonitorPingDetailMapper;
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private PoliceOpsEquipInfoExtMapper policeOpsEquipInfoExtMapper;
    @Resource
    private ViewPoliceMonitorPingDetailSituationMapper viewPoliceMonitorPingDetailSituationMapper;

    @Override
    public PageResult<PoliceMonitorPingAnalysisExtRespVO> getMonitorPingAnalysisPage(PoliceMonitorPingAnalysisExtPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PoliceMonitorPingAnalysisDO> wrapper = new LambdaQueryWrapperX<PoliceMonitorPingAnalysisDO>()
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getEquId, pageReqVO.getEquId())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getEquLable, pageReqVO.getEquLable())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getAutoPingNormalCnt, pageReqVO.getAutoPingNormalCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getAutoPingAbnormalCnt, pageReqVO.getAutoPingAbnormalCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getAutoPingHealthRate, pageReqVO.getAutoPingHealthRate())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getAutoMonitorCnt, pageReqVO.getAutoMonitorCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getPingNormalCnt, pageReqVO.getPingNormalCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getPingAbnormalCnt, pageReqVO.getPingAbnormalCnt())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getPingHealthRate, pageReqVO.getPingHealthRate())
                .eqIfPresent(PoliceMonitorPingAnalysisDO::getMonitorCnt, pageReqVO.getMonitorCnt())
                .betweenIfPresent(PoliceMonitorPingAnalysisDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorPingAnalysisDO::getDeletedTime, pageReqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorPingAnalysisDO::getId);


        StringBuilder equLableChildSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            for (String equLabel : pageReqVO.getEquLableList()) {
                equLableChildSql.append(" and e.equ_lable like '%").append(equLabel).append("%'");
            }
        }

        StringBuilder unitChildSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(pageReqVO.getUnitList())) {
            for (String unit : pageReqVO.getUnitList()) {
                if (StringUtils.isNotBlank(unitChildSql.toString())) {
                    unitChildSql.append(" or ");
                }
                unitChildSql.append(" e.unit = '").append(unit).append("'");
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getStatus())) {
            String source = pageReqVO.getSource();
            if (StringUtils.isBlank(source)) {
                source = OpsConstants.STRING_ONE;
            }

            if (OpsConstants.STRING_ZERO.equals(pageReqVO.getStatus())) { // 全断
                wrapper.eq(OpsConstants.STRING_ONE.equals(source) ? PoliceMonitorPingAnalysisDO::getAutoPingNormalCnt : PoliceMonitorPingAnalysisDO::getPingNormalCnt, OpsConstants.STRING_ZERO);
                wrapper.ne(OpsConstants.STRING_ONE.equals(source) ? PoliceMonitorPingAnalysisDO::getAutoPingAbnormalCnt : PoliceMonitorPingAnalysisDO::getPingAbnormalCnt, OpsConstants.STRING_ZERO);
            } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getStatus())) { // 全通
                wrapper.eq(OpsConstants.STRING_ONE.equals(source) ? PoliceMonitorPingAnalysisDO::getAutoPingAbnormalCnt : PoliceMonitorPingAnalysisDO::getPingAbnormalCnt, OpsConstants.STRING_ZERO);
                wrapper.ne(OpsConstants.STRING_ONE.equals(source) ? PoliceMonitorPingAnalysisDO::getAutoPingNormalCnt : PoliceMonitorPingAnalysisDO::getPingNormalCnt, OpsConstants.STRING_ZERO);

            } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getStatus())) {
                wrapper.ne(OpsConstants.STRING_ONE.equals(source) ? PoliceMonitorPingAnalysisDO::getAutoPingNormalCnt : PoliceMonitorPingAnalysisDO::getPingNormalCnt, OpsConstants.STRING_ZERO);
                wrapper.ne(OpsConstants.STRING_ONE.equals(source) ? PoliceMonitorPingAnalysisDO::getAutoPingAbnormalCnt : PoliceMonitorPingAnalysisDO::getPingAbnormalCnt, OpsConstants.STRING_ZERO);
            }
        }

        String existsSql = "SELECT 1 from police_ops_electric_police e where police_monitor_ping_analysis.equ_id = e.id ";
        boolean hasChildSql = false;
        if (StringUtils.isNotBlank(equLableChildSql.toString())) {
            existsSql = existsSql + equLableChildSql;
            hasChildSql = true;
        }

        if (StringUtils.isNotBlank(unitChildSql.toString())) {
            existsSql = existsSql + " and (" + unitChildSql + ")";
            hasChildSql = true;
        }

        if (StringUtils.isNotBlank(pageReqVO.getIpAddr())) {
            existsSql = existsSql + " and e.ip_addr like '%" + pageReqVO.getIpAddr() + "%'";
            hasChildSql = true;
        }

        if (StringUtils.isNotBlank(pageReqVO.getEquName())) {
            existsSql = existsSql + " and e.equ_name like '%" + pageReqVO.getEquName() + "%'";
            hasChildSql = true;
        }
        if (hasChildSql) {
            wrapper.exists(existsSql);
        }

        if (StringUtils.isNotBlank(pageReqVO.getFinalPingStatus())) {
            String existsSql1 = "select 1 from v_police_monitor_ping_detail_situation s where police_monitor_ping_analysis.equ_id = s.equ_id ";
            existsSql1 = existsSql1 + " and s.final_ping_status = '" + pageReqVO.getFinalPingStatus() + "'";
            wrapper.exists(existsSql1);
        }

        PageResult<PoliceMonitorPingAnalysisExtRespVO> pageResult = BeanUtils.toBean(monitorPingAnalysisMapper.selectPage(pageReqVO, wrapper), PoliceMonitorPingAnalysisExtRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(policeMonitorPingAnalysisExtRespVO -> {
            if (StringUtils.isNotBlank(policeMonitorPingAnalysisExtRespVO.getEquId())) {
                PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO = policeOpsElectricPoliceMapper.selectById(policeMonitorPingAnalysisExtRespVO.getEquId());
                if (policeOpsElectricPoliceDO != null) {
                    policeMonitorPingAnalysisExtRespVO.setIpAddr(policeOpsElectricPoliceDO.getIpAddr());
                    policeMonitorPingAnalysisExtRespVO.setEquName(policeOpsElectricPoliceDO.getEquName());
                    policeMonitorPingAnalysisExtRespVO.setEquLable(policeOpsElectricPoliceDO.getEquLable());
                }
            }

            LambdaQueryWrapperX<ViewPoliceMonitorPingDetailSituationDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            lambdaQueryWrapperX.eq(ViewPoliceMonitorPingDetailSituationDO::getEquId, policeMonitorPingAnalysisExtRespVO.getEquId());
            List<ViewPoliceMonitorPingDetailSituationDO> situationDOList =  viewPoliceMonitorPingDetailSituationMapper.selectList(lambdaQueryWrapperX);
            if (!CollectionUtils.isEmpty(situationDOList)) {
                policeMonitorPingAnalysisExtRespVO.setLatestOccurrenceDay(situationDOList.get(0).getLatestOccurrenceDay());
                policeMonitorPingAnalysisExtRespVO.setPingStatus(situationDOList.get(0).getLatestPingStatus());
                policeMonitorPingAnalysisExtRespVO.setFinalPingStatus(situationDOList.get(0).getFinalPingStatus());
            }
        });
        return pageResult;
    }

    @Override
    public PageResult<PoliceMonitorPingDetailDTO> queryMonitorPingDetailResult(Page<?> page, String equId, String startDate, String endDate) {
        if (StringUtils.isBlank(equId)) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        if (com.xingyuv.captcha.util.StringUtils.isBlank(startDate)) {
            startDate = null;
        }
        if (com.xingyuv.captcha.util.StringUtils.isBlank(endDate)) {
            endDate = null;
        }
        IPage<PoliceMonitorPingDetailDTO> resultDTOIPage = policeOpsEquipInfoExtMapper.queryMonitorPingDetailResult(page, equId, startDate, endDate);
        if (resultDTOIPage == null || CollectionUtils.isEmpty(resultDTOIPage.getRecords())) {
            return new PageResult<>();
        }

        resultDTOIPage.getRecords().forEach(dto -> {
            // 查询每日检查状态
            LambdaQueryWrapper<PoliceMonitorPingDetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PoliceMonitorPingDetailDO::getEquId, dto.getEquId());
            queryWrapper.exists("select 1 from police_monitor_ping_detail r where police_monitor_ping_detail.id = r.id and date_format(occurrence_time, '%Y-%m-%d') = '" + dto.getMonitorDay() + "'");
            queryWrapper.orderByAsc(PoliceMonitorPingDetailDO::getOccurrenceTime);
            List<PoliceMonitorPingDetailDO> list = policeMonitorPingDetailMapper.selectList(queryWrapper);
            List<PoliceMonitorPingHourStatusDTO> statusList = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().map(monitorResultsDO -> {
                PoliceMonitorPingHourStatusDTO statusDTO = BeanUtils.toBean(monitorResultsDO, PoliceMonitorPingHourStatusDTO.class);
                statusDTO.setMonitorHour(DateUtil.format(monitorResultsDO.getOccurrenceTime(), "HH:mm"));
                statusDTO.setStatus(monitorResultsDO.getPingStatus());
                return statusDTO;
            }).collect(Collectors.toList());
            dto.setHourStatusResultList(statusList);
        });
        return new PageResult<>(resultDTOIPage.getRecords(), page.getTotal());
    }
}