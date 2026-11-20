package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.entity.PoliceMonitorResultsDO;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailDO;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsImportDetailMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficMonitorResultsImportDetailExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备监测结果导入信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficMonitorResultsImportDetailExtServiceImpl implements TrafficMonitorResultsImportDetailExtService {

    @Resource
    private TrafficMonitorResultsImportDetailMapper monitorResultsImportDetailMapper;
    @Resource
    private TrafficOpsMemberMapper trafficOpsMemberMapper;
    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;

    @Override
    public PageResult<TrafficMonitorResultsImportDetailExtRespVO> getMonitorResultsImportDetailPage(TrafficMonitorResultsImportDetailExtPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficMonitorResultsImportDetailDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eqIfPresent(TrafficMonitorResultsImportDetailDO::getEquId, pageReqVO.getEquId());
        lambdaQueryWrapperX.eqIfPresent(TrafficMonitorResultsImportDetailDO::getMemberBh, pageReqVO.getMemberBh());
        lambdaQueryWrapperX.likeIfPresent(TrafficMonitorResultsImportDetailDO::getRoadName, pageReqVO.getRoadName());

        if(StringUtils.isNotBlank(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    lambdaQueryWrapperX.like(TrafficMonitorResultsImportDetailDO::getIntersectionName, roadName);
                }
            }
        }
        lambdaQueryWrapperX.eqIfPresent(TrafficMonitorResultsImportDetailDO::getDirection, pageReqVO.getDirection());
        lambdaQueryWrapperX.eqIfPresent(TrafficMonitorResultsImportDetailDO::getIpAddr, pageReqVO.getIpAddr());
        lambdaQueryWrapperX.eqIfPresent(TrafficMonitorResultsImportDetailDO::getStatus, pageReqVO.getStatus());

        if (OpsConstants.STRING_ONE.equals(pageReqVO.getResultImg())) {
            lambdaQueryWrapperX.isNotNull(TrafficMonitorResultsImportDetailDO::getResultImg);
        } else if (OpsConstants.STRING_ZERO.equals(pageReqVO.getResultImg())) {
            lambdaQueryWrapperX.isNull(TrafficMonitorResultsImportDetailDO::getResultImg);
        }

        if (StringUtils.isNotBlank(pageReqVO.getMonitorSituation())) {
            if ("true".equals(pageReqVO.getMonitorSituation())) {
                lambdaQueryWrapperX.isNotNull(TrafficMonitorResultsImportDetailDO::getMonitorSituation);
            } else {
                lambdaQueryWrapperX.isNull(TrafficMonitorResultsImportDetailDO::getMonitorSituation);
            }
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getMonitorSituationList())) {
            lambdaQueryWrapperX.and(wrapper -> {
                for (String monitorSituation : pageReqVO.getMonitorSituationList()) {
                    wrapper.or().like(TrafficMonitorResultsImportDetailDO::getMonitorSituation, monitorSituation);
                }
            });
        }

        StringBuilder childSql = new StringBuilder();
        boolean hasChild = false;
        if (StringUtils.isNotBlank(pageReqVO.getKkbh())) {
            if ("true".equals(pageReqVO.getKkbh())) {
                childSql.append(" and e.kkbh is not null");
            } else {
                childSql.append(" and e.kkbh is null");
            }
            hasChild = true;
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            hasChild = true;
            for (String equLabel : pageReqVO.getEquLableList()) {
                childSql.append(" and e.equ_lable like '%").append(equLabel).append("%'");
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getDjType())) {
            childSql.append(" and e.dj_type = '").append(pageReqVO.getDjType()).append("'");
            hasChild = true;
        }

        if (StringUtils.isNotBlank(pageReqVO.getUnderWarranty())) {
            childSql.append(" and e.under_warranty = '").append(pageReqVO.getUnderWarranty()).append("'");
            hasChild = true;
        }

        if (hasChild) {
            String existsSql = "select 1 from traffic_ops_electric_police e where e.id = traffic_monitor_results_import_detail.equ_id" + childSql;
            lambdaQueryWrapperX.exists(existsSql);
        }


        PageResult<TrafficMonitorResultsImportDetailExtRespVO> pageResult = BeanUtils.toBean(monitorResultsImportDetailMapper.selectPage(pageReqVO, lambdaQueryWrapperX), TrafficMonitorResultsImportDetailExtRespVO.class);

        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getList())) {
            return new PageResult<>();
        }

        Map<String, List<TrafficMonitorResultsImportDetailExtRespVO>> resultMap = pageResult.getList().stream().collect(
                Collectors.groupingBy(dto -> StringUtils.isBlank(dto.getMemberBh()) ? "未绑定" : dto.getMemberBh(), LinkedHashMap::new, Collectors.toList()));


        LambdaQueryWrapper<TrafficOpsMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TrafficOpsMemberDO::getId, resultMap.keySet());
        List<TrafficOpsMemberDO> memberList = trafficOpsMemberMapper.selectList(queryWrapper);

        Map<String, TrafficOpsMemberDO> memberMap = Optional.ofNullable(memberList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(TrafficOpsMemberDO::getId, member -> member));


        pageResult.getList().forEach(dto -> {
            TrafficOpsMemberDO memberDO = memberMap.get(dto.getMemberBh());
            if (memberDO != null) {
                String serialNum = "";
                if (memberDO.getSerialNum() != null) {
                    serialNum = String.format("%03d", memberDO.getSerialNum());
                }
                String memberName = memberDO.getRoadName() + "/" + memberDO.getIntersectionName() + "/" + memberDO.getAdress() + "/" + serialNum;
                dto.setMemberName(memberName);
            }

            // 查询电警
            TrafficOpsElectricPoliceDO opsElectricPoliceDO = opsElectricPoliceMapper.selectById(dto.getEquId());
            if (opsElectricPoliceDO != null) {
                dto.setUnderWarranty(OpsConstants.STRING_ONE.equals(opsElectricPoliceDO.getUnderWarranty()) ? "是" : "否");
            }
        });

        return pageResult;
    }

    @Override
    public void remarkMonitorResults(TrafficMonitorResultsImportDetailSaveReqVO saveReqVO) {
        if (saveReqVO == null
                || StringUtils.isBlank(saveReqVO.getId())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        TrafficMonitorResultsImportDetailDO detailDO = monitorResultsImportDetailMapper.selectById(saveReqVO.getId());
        if (detailDO == null) {
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_IMPORT_DETAIL_NOT_EXISTS);
        }


        if (!CollectionUtils.isEmpty(saveReqVO.getEquSituationList())) {
            detailDO.setEquSituation(StringUtils.join(saveReqVO.getEquSituationList(), OpsConstants.EN_STRING_SEMICOLON));
        }
        detailDO.setEquDesc(saveReqVO.getEquDesc());
        detailDO.setEvidenceFileId(saveReqVO.getEvidenceFileId());
        detailDO.setRemarkDate(DateUtil.toLocalDateTime(new Date()));
        detailDO.setRemarkId(SecurityFrameworkUtils.getLoginUserId());
        detailDO.setRemarkName(SecurityFrameworkUtils.getLoginUserNickname());
        detailDO.setUpdateTime(DateUtil.toLocalDateTime(new Date()));
        monitorResultsImportDetailMapper.updateById(detailDO);
    }

}