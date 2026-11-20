package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TrafficOpsPoliceMaintenanceExtServiceImpl implements TrafficOpsPoliceMaintenanceExtService {
    @Resource
    private TrafficOpsPoliceEquipmentMapper opsPoliceEquipmentMapper;
    @Resource
    private TrafficOpsPoliceMaintenanceMapper opsPoliceMaintenanceMapper;
    @Resource
    private TrafficOpsPoliceMaintenanceDetailMapper opsPoliceMaintenanceDetailMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;

    @Override
    public String saveOpsPoliceMaintenance(TrafficOpsPoliceMaintenanceExtSaveReqVO saveReqVO) {
        if (saveReqVO == null) {

            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 1.保存报修申请信息
        TrafficOpsPoliceMaintenanceDO opsPoliceMaintenanceDO = BeanUtils.toBean(saveReqVO, TrafficOpsPoliceMaintenanceDO.class);

        if (StringUtils.isBlank(opsPoliceMaintenanceDO.getId())) { // 新增报修申请
            opsPoliceMaintenanceDO.setStatus(OpsConstants.STATUS_CONFIRM);
            opsPoliceMaintenanceDO.setReportTime(LocalDateTimeUtil.of(new Date()));

            if (StringUtils.isNotBlank(saveReqVO.getReportUserId())) {
                // 查询用户信息
                AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.parseLong(saveReqVO.getReportUserId()));
                if (adminUserRespDTO == null) {
                    throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
                }
                opsPoliceMaintenanceDO.setReportPersonnel(adminUserRespDTO.getNickname());
                opsPoliceMaintenanceDO.setReportDeptId(adminUserRespDTO.getDeptId() + "");

                DeptRespDTO deptRespDTO = deptApi.getDept(adminUserRespDTO.getDeptId());
                if (deptRespDTO != null) {
                    opsPoliceMaintenanceDO.setReportDept(deptRespDTO.getName());
                }
            }

            opsPoliceMaintenanceMapper.insert(opsPoliceMaintenanceDO);
        } else { // 修改报修申请信息
            // 校验存在
            validateOpsPoliceMaintenanceExists(opsPoliceMaintenanceDO.getId());

            opsPoliceMaintenanceMapper.updateById(opsPoliceMaintenanceDO);

            // 删除设备信息
            LambdaQueryWrapperX<TrafficOpsPoliceMaintenanceDetailDO> delDetailWrapper = new LambdaQueryWrapperX<>();
            delDetailWrapper.eq(TrafficOpsPoliceMaintenanceDetailDO::getMaintenanceId, opsPoliceMaintenanceDO.getId());
            opsPoliceMaintenanceDetailMapper.delete(delDetailWrapper);
        }

        // 保存设备信息
        if (!CollectionUtils.isEmpty(saveReqVO.getDetailVOList())) {
            List<TrafficOpsPoliceMaintenanceDetailDO> instList = new ArrayList<>();
            for (TrafficOpsPoliceMaintenanceDetailSaveReqVO detailSaveReqVO : saveReqVO.getDetailVOList()) {
                TrafficOpsPoliceMaintenanceDetailDO instInfo = BeanUtils.toBean(detailSaveReqVO, TrafficOpsPoliceMaintenanceDetailDO.class);
                instInfo.setMaintenanceId(opsPoliceMaintenanceDO.getId());
                instList.add(instInfo);
            }
            opsPoliceMaintenanceDetailMapper.insertBatch(instList);
        }

        return opsPoliceMaintenanceDO.getId();
    }

    @Override
    public PageResult<TrafficOpsPoliceMaintenanceExtRespVO> getOpsPoliceMaintenancePage(TrafficOpsPoliceMaintenancePageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsPoliceMaintenanceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eqIfPresent(TrafficOpsPoliceMaintenanceDO::getReportUserId, pageReqVO.getReportUserId());
        queryWrapperX.eqIfPresent(TrafficOpsPoliceMaintenanceDO::getMaintenanceUserId, pageReqVO.getMaintenanceUserId());
        queryWrapperX.eqIfPresent(TrafficOpsPoliceMaintenanceDO::getStatus, pageReqVO.getStatus());

        String customOrderSql = " CASE WHEN STATUS = 8 THEN 1 WHEN STATUS = 4 THEN 4 WHEN STATUS = 5 THEN 5 WHEN STATUS = 9 then 9 ELSE 99 END ,create_time DESC";
        queryWrapperX.last("ORDER BY" + customOrderSql);


        PageResult<TrafficOpsPoliceMaintenanceExtRespVO> pageResult = BeanUtils.toBean(opsPoliceMaintenanceMapper.selectPage(pageReqVO, queryWrapperX), TrafficOpsPoliceMaintenanceExtRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(respVO -> respVO.setDetailVOList(queryOpsPoliceMaintenanceDetailList(respVO.getId())));
        return pageResult;
    }

    private List<TrafficOpsPoliceMaintenanceDetailExtRespVO> queryOpsPoliceMaintenanceDetailList(String maintenanceId) {
        LambdaQueryWrapperX<TrafficOpsPoliceMaintenanceDetailDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficOpsPoliceMaintenanceDetailDO::getMaintenanceId, maintenanceId);
        List<TrafficOpsPoliceMaintenanceDetailExtRespVO> detailExtRespVOList = BeanUtils.toBean(opsPoliceMaintenanceDetailMapper.selectList(lambdaQueryWrapperX), TrafficOpsPoliceMaintenanceDetailExtRespVO.class);
        Optional.ofNullable(detailExtRespVOList).orElse(new ArrayList<>()).forEach(respVO -> {
            // 查询设备信息
            TrafficOpsPoliceEquipmentDO equipmentDO = opsPoliceEquipmentMapper.selectById(respVO.getEquId());
            if (equipmentDO != null) {
                respVO.setPsn(equipmentDO.getPsn());
                respVO.setEquType(equipmentDO.getEquType());
                respVO.setEncryptionCard(equipmentDO.getEncryptionCard());
            }
        });
        return detailExtRespVOList;
    }

    @Override
    public TrafficOpsPoliceMaintenanceExtRespVO getOpsPoliceMaintenance(String id) {
        TrafficOpsPoliceMaintenanceDO opsPoliceMaintenanceDO = opsPoliceMaintenanceMapper.selectById(id);
        if (opsPoliceMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_POLICE_MAINTENANCE_NOT_EXISTS);
        }

        TrafficOpsPoliceMaintenanceExtRespVO respVO = BeanUtils.toBean(opsPoliceMaintenanceDO, TrafficOpsPoliceMaintenanceExtRespVO.class);


        respVO.setDetailVOList(queryOpsPoliceMaintenanceDetailList(opsPoliceMaintenanceDO.getId()));

        return respVO;
    }

    @Override
    public void auditOpsPoliceMaintenance(TrafficOpsPoliceMaintenanceSaveReqVO saveReqVO) {
        TrafficOpsPoliceMaintenanceDO opsPoliceMaintenanceDO =  opsPoliceMaintenanceMapper.selectById(saveReqVO.getId());
        if (opsPoliceMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_POLICE_MAINTENANCE_NOT_EXISTS);
        }
        opsPoliceMaintenanceDO.setStatus(saveReqVO.getStatus());
        opsPoliceMaintenanceDO.setAuditorBh(saveReqVO.getAuditorBh());
        opsPoliceMaintenanceDO.setAuditor(saveReqVO.getAuditor());
        opsPoliceMaintenanceDO.setAuditDate(LocalDateTimeUtil.of(new Date()));
        opsPoliceMaintenanceDO.setAuditOpinions(saveReqVO.getAuditOpinions());

        opsPoliceMaintenanceDO.setMaintenanceUserId(saveReqVO.getMaintenanceUserId());
        if (StringUtils.isNotBlank(saveReqVO.getMaintenanceUserId())) {
            // 查询用户信息
            AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.parseLong(saveReqVO.getMaintenanceUserId()));
            if (adminUserRespDTO == null) {
                throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
            }
            opsPoliceMaintenanceDO.setMaintenancePersonnel(adminUserRespDTO.getNickname());
            opsPoliceMaintenanceDO.setMaintenancePhone(adminUserRespDTO.getMobile());
        }
        opsPoliceMaintenanceMapper.updateById(opsPoliceMaintenanceDO);
    }

    @Override
    public void saveOpsPoliceMaintenanceResult(TrafficOpsPoliceMaintenanceSaveReqVO saveReqVO) {
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        TrafficOpsPoliceMaintenanceDO opsPoliceMaintenanceDO =  opsPoliceMaintenanceMapper.selectById(saveReqVO.getId());
        if (opsPoliceMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_POLICE_MAINTENANCE_NOT_EXISTS);
        }

        opsPoliceMaintenanceDO.setSuggestion(saveReqVO.getSuggestion());
        opsPoliceMaintenanceDO.setStatus(OpsConstants.STATUS_COMPLETED);
        opsPoliceMaintenanceDO.setMaintenanceDate(LocalDateTimeUtil.of(new Date()));
        opsPoliceMaintenanceMapper.updateById(opsPoliceMaintenanceDO);
    }

    @Override
    public void deleteOpsPoliceMaintenance(String id) {
        // 校验存在
        validateOpsPoliceMaintenanceExists(id);
        // 删除
        opsPoliceMaintenanceMapper.deleteById(id);
        // 删除维保设备
        LambdaQueryWrapperX<TrafficOpsPoliceMaintenanceDetailDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(TrafficOpsPoliceMaintenanceDetailDO::getMaintenanceId,id);
        opsPoliceMaintenanceDetailMapper.delete(queryWrapperX);

    }

    private void validateOpsPoliceMaintenanceExists(String id) {
        if (opsPoliceMaintenanceMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_POLICE_MAINTENANCE_NOT_EXISTS);
        }
    }
}