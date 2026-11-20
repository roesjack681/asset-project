package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsPageReqVO;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsSaveReqVO;
import com.holdtime.ops.module.service.PoliceMonitorResultsExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 公安设备监测结果 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorResultsExtServiceImpl implements PoliceMonitorResultsExtService {

    @Resource
    private PoliceMonitorResultsMapper monitorResultsMapper;
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private TrafficOpsMaintenanceMapper trafficOpsMaintenanceMapper;
    @Resource
    private TrafficOpsMaintainProgressMapper opsMaintainProgressMapper;
    @Resource
    private TrafficOpsMaintenanceFilesMapper opsMaintenanceFilesMapper;


    @Override
    public PageResult<PoliceMonitorResultsDO> getMonitorResultsPage(PoliceMonitorResultsPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PoliceMonitorResultsDO> wrapper = new LambdaQueryWrapperX<PoliceMonitorResultsDO>()
                .eqIfPresent(PoliceMonitorResultsDO::getEquType, pageReqVO.getEquType())
                .eqIfPresent(PoliceMonitorResultsDO::getEquId, pageReqVO.getEquId())
                .eqIfPresent(PoliceMonitorResultsDO::getIpAddr, pageReqVO.getIpAddr())
//                .likeIfPresent(PoliceMonitorResultsDO::getEquName, pageReqVO.getEquName())
//                .eqIfPresent(PoliceMonitorResultsDO::getMemberBh, pageReqVO.getMemberBh())
                .eqIfPresent(PoliceMonitorResultsDO::getDirection, pageReqVO.getDirection())
                .eqIfPresent(PoliceMonitorResultsDO::getEventType, pageReqVO.getEventType())
                .betweenIfPresent(PoliceMonitorResultsDO::getOccurrenceTime, pageReqVO.getOccurrenceTime())
                .eqIfPresent(PoliceMonitorResultsDO::getStatus, pageReqVO.getStatus())
                .eqIfPresent(PoliceMonitorResultsDO::getIsRepairOrder, pageReqVO.getIsRepairOrder())
                .eqIfPresent(PoliceMonitorResultsDO::getMaintenanceId, pageReqVO.getMaintenanceId())
                .eqIfPresent(PoliceMonitorResultsDO::getMaintenanceUserId, pageReqVO.getMaintenanceUserId())
                .eqIfPresent(PoliceMonitorResultsDO::getMaintenancePersonnel, pageReqVO.getMaintenancePersonnel())
                .eqIfPresent(PoliceMonitorResultsDO::getMaintenancePhone, pageReqVO.getMaintenancePhone())
                .eqIfPresent(PoliceMonitorResultsDO::getSource, pageReqVO.getSource())
                .likeIfPresent(PoliceMonitorResultsDO::getEquDesc, pageReqVO.getEquDesc())
                .betweenIfPresent(PoliceMonitorResultsDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorResultsDO::getUpdateTime, pageReqVO.getUpdateTime())
                .betweenIfPresent(PoliceMonitorResultsDO::getRemarkDate, pageReqVO.getRemarkDate())
                .betweenIfPresent(PoliceMonitorResultsDO::getDeletedTime, pageReqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorResultsDO::getId);

        if (OpsConstants.STRING_ONE.equals(pageReqVO.getResultImg())) {
            wrapper.isNotNull(PoliceMonitorResultsDO::getResultImg);
        } else if (OpsConstants.STRING_ZERO.equals(pageReqVO.getResultImg())) {
            wrapper.isNull(PoliceMonitorResultsDO::getResultImg);
        }

        if (OpsConstants.STRING_ONE.equals(pageReqVO.getEvidenceFileId())) {
            wrapper.isNotNull(PoliceMonitorResultsDO::getEvidenceFileId);
        } else if (OpsConstants.STRING_ZERO.equals(pageReqVO.getEvidenceFileId())) {
            wrapper.isNull(PoliceMonitorResultsDO::getEvidenceFileId);
        }

        if (StringUtils.isNotBlank(pageReqVO.getMemberBh())) {
            if ("true".equals(pageReqVO.getMemberBh())) {
//                wrapper.isNotNull(PoliceMonitorResultsDO::getMemberBh);
                wrapper.exists("select 1 from police_ops_electric_police e where police_monitor_results.equ_id = e.id and e.member_bh is not null");
            } else {
//                wrapper.isNull(PoliceMonitorResultsDO::getMemberBh);
                wrapper.exists("select 1 from police_ops_electric_police e where police_monitor_results.equ_id = e.id and e.member_bh is null");
            }
        }

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

        String existsSql = "SELECT 1 from police_ops_electric_police e where police_monitor_results.equ_id = e.id ";
        boolean hasChildSql = false;
        if (StringUtils.isNotBlank(equLableChildSql.toString())) {
            existsSql = existsSql + equLableChildSql;
            hasChildSql = true;
        }

        if (StringUtils.isNotBlank(unitChildSql.toString())) {
            existsSql = existsSql + " and (" + unitChildSql + ")";
            hasChildSql = true;
        }

        if (StringUtils.isNotBlank(pageReqVO.getEquName())) {
            existsSql = existsSql + " and e.equ_name like '%" + pageReqVO.getEquName() + "%'";
            hasChildSql = true;
        }
        if (hasChildSql) {
            wrapper.exists(existsSql);
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquSituationList())) {
            wrapper.and(wrap -> {
                if (OpsConstants.STRING_TWO.equals(pageReqVO.getIncludeEquSituation())) {
                    wrap.isNull(PoliceMonitorResultsDO::getEquSituation).or(wrp2 -> {
                        for (String equSituation : pageReqVO.getEquSituationList()) {
                            wrp2.notLike(PoliceMonitorResultsDO::getEquSituation, equSituation);
                        }
                    });
                } else {
                    for (String equSituation : pageReqVO.getEquSituationList()) {
                        if (OpsConstants.STRING_ZERO.equals(equSituation)) {
                            wrap.or().isNull(PoliceMonitorResultsDO::getEquSituation);
                        } else {
                            wrap.or().like(PoliceMonitorResultsDO::getEquSituation, equSituation);
                        }
                    }
                }

            });
        }

        return monitorResultsMapper.selectPage(pageReqVO, wrapper);
    }

    @Override
    public void updateMonitorResults(PoliceMonitorResultsSaveReqVO saveReqVO) {
        if (saveReqVO == null
                || StringUtils.isBlank(saveReqVO.getId())
                || StringUtils.isBlank(saveReqVO.getResultImg())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        PoliceMonitorResultsDO policeMonitorResultsDO = monitorResultsMapper.selectById(saveReqVO.getId());
        if (policeMonitorResultsDO == null) {
            throw exception(OpsErrorCodeConstants.POLICE_MONITOR_RESULTS_NOT_EXISTS);
        }

        policeMonitorResultsDO.setResultImg(saveReqVO.getResultImg());
        policeMonitorResultsDO.setStatus(OpsConstants.EQU_STATUS_ZC);
        policeMonitorResultsDO.setUpdateTime(DateUtil.toLocalDateTime(new Date()));
        monitorResultsMapper.updateById(policeMonitorResultsDO);

        if (StringUtils.isNotBlank(policeMonitorResultsDO.getMaintenanceId())) {
            // 更新维护单信息
            TrafficOpsMaintenanceDO maintenanceDO = trafficOpsMaintenanceMapper.selectById(policeMonitorResultsDO.getMaintenanceId());
            maintenanceDO.setStatus(OpsConstants.STATUS_COMPLETED);
            trafficOpsMaintenanceMapper.updateById(maintenanceDO);

            TrafficOpsMaintainProgressDO progressDO = buildMaintainProgressDO(maintenanceDO);
            opsMaintainProgressMapper.insert(progressDO);

            TrafficOpsMaintenanceFilesDO fileDO = buildMaintenanceFilesDO(maintenanceDO, progressDO, saveReqVO.getResultImg());
            opsMaintenanceFilesMapper.insert(fileDO);
        }

        // 更新电警信息
        if (StringUtils.isNotBlank(policeMonitorResultsDO.getEquId())) {
            PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO = policeOpsElectricPoliceMapper.selectById(policeMonitorResultsDO.getEquId());
            if (policeOpsElectricPoliceDO != null) {
                policeOpsElectricPoliceDO.setStatus(OpsConstants.EQU_STATUS_ZC);
                policeOpsElectricPoliceMapper.updateById(policeOpsElectricPoliceDO);
            }
        }
    }

    @Override
    public void remarkMonitorResults(PoliceMonitorResultsSaveReqVO saveReqVO) {
        if (saveReqVO == null
                || StringUtils.isBlank(saveReqVO.getId())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        PoliceMonitorResultsDO policeMonitorResultsDO = monitorResultsMapper.selectById(saveReqVO.getId());
        if (policeMonitorResultsDO == null) {
            throw exception(OpsErrorCodeConstants.POLICE_MONITOR_RESULTS_NOT_EXISTS);
        }

        if (StringUtils.isNotBlank(saveReqVO.getStatus())) {
            policeMonitorResultsDO.setStatus(saveReqVO.getStatus());
        }

        if (!CollectionUtils.isEmpty(saveReqVO.getEquSituationList())) {
            policeMonitorResultsDO.setEquSituation(StringUtils.join(saveReqVO.getEquSituationList(), OpsConstants.EN_STRING_SEMICOLON));
        }else{
            policeMonitorResultsDO.setEquSituation(null);
        }
        policeMonitorResultsDO.setEquDesc(saveReqVO.getEquDesc());
        policeMonitorResultsDO.setUpdateTime(DateUtil.toLocalDateTime(new Date()));
        policeMonitorResultsDO.setEvidenceFileId(saveReqVO.getEvidenceFileId());
        policeMonitorResultsDO.setRemarkDate(DateUtil.toLocalDateTime(new Date()));
        monitorResultsMapper.updateById(policeMonitorResultsDO);
    }

    // 构建维修记录
    private TrafficOpsMaintainProgressDO buildMaintainProgressDO(TrafficOpsMaintenanceDO maintenanceDO) {
        TrafficOpsMaintainProgressDO progressDO = new TrafficOpsMaintainProgressDO();
        progressDO.setMaintenanceId(maintenanceDO.getId());
        progressDO.setMaintenance("经人工检测，已修复");
        progressDO.setMaintenanceUserId(maintenanceDO.getMaintenanceUserId());
        progressDO.setMaintenancePersonnel(maintenanceDO.getMaintenancePersonnel());
        progressDO.setMaintenancePhone(maintenanceDO.getMaintenancePhone());
        progressDO.setMaintenanceDate(DateUtil.toLocalDateTime(new Date()));
        progressDO.setIsChange(OpsConstants.STRING_ZERO);
        return progressDO;
    }

    // 构建维修文件记录
    private TrafficOpsMaintenanceFilesDO buildMaintenanceFilesDO(
            TrafficOpsMaintenanceDO maintenanceDO,
            TrafficOpsMaintainProgressDO progressDO,
            String resultImgUrl) {

        TrafficOpsMaintenanceFilesDO fileDO = new TrafficOpsMaintenanceFilesDO();
        fileDO.setMaintenanceId(maintenanceDO.getId());
        fileDO.setProgressId(progressDO.getId());
        fileDO.setRepairType(OpsConstants.REPAIR_TYPE_TWO);
        fileDO.setFileType(OpsConstants.STRING_ONE);
        fileDO.setFileUrl(resultImgUrl);
        return fileDO;
    }

}