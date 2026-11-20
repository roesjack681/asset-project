package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.PoliceMonitorResultsDO;
import com.holdtime.ops.module.entity.PoliceMonitorResultsHistoryDO;
import com.holdtime.ops.module.entity.mapper.PoliceMonitorResultsHistoryMapper;
import com.holdtime.ops.module.entity.mapper.PoliceMonitorResultsMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsHistoryPageReqVO;
import com.holdtime.ops.module.service.PoliceMonitorResultsHistoryExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * 公安设备监测结果历史 Service 实现类
 *
 * @author yanxl
 */
@Service
@Validated
public class PoliceMonitorResultsHistoryExtServiceImpl implements PoliceMonitorResultsHistoryExtService {

    @Resource
    private PoliceMonitorResultsHistoryMapper monitorResultsHistoryMapper;
    @Resource
    private PoliceMonitorResultsMapper policeMonitorResultsMapper;

    @Override
    public void deleteAllOldMonitorResultsHistory() {
        LambdaQueryWrapperX<PoliceMonitorResultsHistoryDO> delHistoryDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        delHistoryDOLambdaQueryWrapperX.le(PoliceMonitorResultsHistoryDO::getMonitorDate, DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -2)));
        monitorResultsHistoryMapper.delete(delHistoryDOLambdaQueryWrapperX);
    }

    @Override
    public void saveMonitorResultsHistory(String monitorId) {
        if (StringUtils.isBlank(monitorId)) {
            return;
        }
        PoliceMonitorResultsDO policeMonitorResultsDO = policeMonitorResultsMapper.selectById(monitorId);
        if (policeMonitorResultsDO == null) {
            return;
        }

        PoliceMonitorResultsHistoryDO historyDO = BeanUtils.toBean(policeMonitorResultsDO, PoliceMonitorResultsHistoryDO.class);
        historyDO.setId(null);

        historyDO.setChangeSituation(OpsConstants.STRING_ZERO);
        List<PoliceMonitorResultsHistoryDO> yesterdayHistoryDOList = queryMonitorResultsHistory(policeMonitorResultsDO.getEquId(), DateUtil.offsetDay(new Date(), -1));
        if (!CollectionUtils.isEmpty(yesterdayHistoryDOList)) {
            PoliceMonitorResultsHistoryDO yesterdayHistoryDO = yesterdayHistoryDOList.get(0);
            if ("IP不可达".equals(yesterdayHistoryDO.getStatus()) && "正常".equals(historyDO.getStatus())) {
                historyDO.setChangeSituation(OpsConstants.STRING_ONE);
            } else if ("正常".equals(yesterdayHistoryDO.getStatus()) && "IP不可达".equals(historyDO.getStatus())) {
                historyDO.setChangeSituation(OpsConstants.STRING_TWO);
            }
        }

        // 查询该设备的历史记录
        List<PoliceMonitorResultsHistoryDO> todayHistoryDOList = queryMonitorResultsHistory(policeMonitorResultsDO.getEquId(), new Date());
        // 查询昨日变更记录
        if (CollectionUtils.isEmpty(todayHistoryDOList)) { // 新增今日记录
            historyDO.setMonitorDate(DateUtil.toLocalDateTime(DateUtil.beginOfDay(new Date())));
            monitorResultsHistoryMapper.insert(historyDO);
        } else { // 更新今日记录
            historyDO.setId(todayHistoryDOList.get(0).getId());
            monitorResultsHistoryMapper.updateById(historyDO);
        }
    }

    @Override
    public PageResult<PoliceMonitorResultsHistoryDO> getMonitorResultsHistoryPage(PoliceMonitorResultsHistoryPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PoliceMonitorResultsHistoryDO> wrapper = new LambdaQueryWrapperX<PoliceMonitorResultsHistoryDO>()
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getEquType, pageReqVO.getEquType())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getEquId, pageReqVO.getEquId())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getIpAddr, pageReqVO.getIpAddr())
                .likeIfPresent(PoliceMonitorResultsHistoryDO::getEquName, pageReqVO.getEquName())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getDirection, pageReqVO.getDirection())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getEventType, pageReqVO.getEventType())
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getOccurrenceTime, pageReqVO.getOccurrenceTime())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getStatus, pageReqVO.getStatus())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getIsRepairOrder, pageReqVO.getIsRepairOrder())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getMaintenanceId, pageReqVO.getMaintenanceId())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getMaintenanceUserId, pageReqVO.getMaintenanceUserId())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getMaintenancePersonnel, pageReqVO.getMaintenancePersonnel())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getMaintenancePhone, pageReqVO.getMaintenancePhone())
                .likeIfPresent(PoliceMonitorResultsHistoryDO::getEquDesc, pageReqVO.getEquDesc())
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getUpdateTime, pageReqVO.getUpdateTime())
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getRemarkDate, pageReqVO.getRemarkDate())
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getDeletedTime, pageReqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorResultsHistoryDO::getId);

        wrapper.eq(PoliceMonitorResultsHistoryDO::getMonitorDate, DateUtil.beginOfDay(new Date()));
        if (OpsConstants.STRING_ONE.equals(pageReqVO.getResultImg())) {
            wrapper.isNotNull(PoliceMonitorResultsHistoryDO::getResultImg);
        } else if (OpsConstants.STRING_ZERO.equals(pageReqVO.getResultImg())) {
            wrapper.isNull(PoliceMonitorResultsHistoryDO::getResultImg);
        }

        if (StringUtils.isNotBlank(pageReqVO.getChangeSituation())) {
            if (OpsConstants.STRING_ZERO.equals(pageReqVO.getChangeSituation())) {
                wrapper.and(wap -> wap.eq(PoliceMonitorResultsHistoryDO::getChangeSituation, OpsConstants.STRING_ZERO).or().isNull(PoliceMonitorResultsHistoryDO::getChangeSituation));
            } else {
                wrapper.eqIfPresent(PoliceMonitorResultsHistoryDO::getChangeSituation, pageReqVO.getChangeSituation());
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getMemberBh())) {
            if ("true".equals(pageReqVO.getMemberBh())) {
                wrapper.isNotNull(PoliceMonitorResultsHistoryDO::getMemberBh);
            } else {
                wrapper.isNull(PoliceMonitorResultsHistoryDO::getMemberBh);
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

        String existsSql = "SELECT 1 from police_ops_electric_police e where police_monitor_results_history.equ_id = e.id ";
        boolean hasChildSql = false;
        if (StringUtils.isNotBlank(equLableChildSql.toString())) {
            existsSql = existsSql + equLableChildSql;
            hasChildSql = true;
        }

        if (StringUtils.isNotBlank(unitChildSql.toString())) {
            existsSql = existsSql + " and (" + unitChildSql + ")";
            hasChildSql = true;
        }
        if (hasChildSql) {
            wrapper.exists(existsSql);
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquSituationList())) {
            wrapper.and(wrp -> {
                for (String equSituation : pageReqVO.getEquSituationList()) {
                    if (OpsConstants.STRING_ZERO.equals(equSituation)) {
                        wrp.or().isNull(PoliceMonitorResultsHistoryDO::getEquSituation);
                    } else {
                        wrp.or().like(PoliceMonitorResultsHistoryDO::getEquSituation, equSituation);
                    }
                }
            });
        }

        return monitorResultsHistoryMapper.selectPage(pageReqVO, wrapper);
    }

    private List<PoliceMonitorResultsHistoryDO> queryMonitorResultsHistory(String equId, Date queryDate) {
        LambdaQueryWrapperX<PoliceMonitorResultsHistoryDO> todayHistoryDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        todayHistoryDOLambdaQueryWrapperX.eq(PoliceMonitorResultsHistoryDO::getMonitorDate, DateUtil.beginOfDay(queryDate));
        todayHistoryDOLambdaQueryWrapperX.eq(PoliceMonitorResultsHistoryDO::getEquId, equId);
        return monitorResultsHistoryMapper.selectList(todayHistoryDOLambdaQueryWrapperX);
    }
}