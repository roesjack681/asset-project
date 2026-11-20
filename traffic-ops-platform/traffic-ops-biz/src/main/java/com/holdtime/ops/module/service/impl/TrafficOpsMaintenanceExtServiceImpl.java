package com.holdtime.ops.module.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.conf.OpsProjectEmayConfig;
import com.holdtime.ops.module.conf.WechatConfig;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.kafka.producer.BoundaryMsgProducer;
import com.holdtime.ops.module.mapper.OpsMaintenanceExtMapper;
import com.holdtime.ops.module.model.dto.TrafficOpsEquipInfoDTO;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.*;
import com.holdtime.ops.module.utils.HanLPUtils;
import com.holdtime.ops.module.weixin.sdk.WeixinUtil;
import com.holdtime.ops.module.weixin.sdk.api.model.SendTemplateMessage;
import com.holdtime.ops.module.weixin.sdk.api.model.TemplateData;
import com.holdtime.ops.module.weixin.sdk.api.model.WechatTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 设备维保信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TrafficOpsMaintenanceExtServiceImpl implements TrafficOpsMaintenanceExtService {

    @Resource
    private TrafficOpsMaintenanceMapper opsMaintenanceMapper;
    @Resource
    private TrafficOpsMaintenanceFilesMapper opsMaintenanceFilesMapper;
    @Resource
    private TrafficOpsMaintainProgressMapper opsMaintainProgressMapper;
    @Resource
    private TrafficOpsMaintainCmMapper opsMaintainCmMapper;
    @Resource
    private TrafficOpsRegisterMapper trafficOpsRegisterMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private DeptApi deptApi;
    @Resource
    private DeptService deptService;
    @Resource
    private WechatAuthCenterExtService wechatAuthCenterExtService;
    @Resource
    private TrafficOpsMaintenanceTeamMapper opsMaintenanceTeamMapper;
    @Resource
    private TrafficOpsSignalMapper opsSignalMapper;
    @Resource
    private TrafficOpsSignalLightMapper opsSignalLightMapper;
    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;
    @Resource
    private TrafficOpsMemberMapper opsMemberMapper;
    @Resource
    private TrafficOpsBigledMapper opsBigledMapper;
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private OpsProjectEmayConfig opsProjectEmayConfig;
    @Resource
    private PublicOpsNoticeRecordExtService opsNoticeRecordExtService;
    @Resource
    private TrafficOpsMaintainFlowRecordExtService opsMaintainFlowRecordExtService;
    @Resource
    private OpsMaintenanceExtMapper opsMaintenanceExtMapper;
    @Resource
    private OpsMaintenanceCenterStrategyService opsMaintenanceCenterStrategyService;
    @Resource
    private ViewOpsMonitorResultsMapper opsMonitorResultsMapper;

    @Resource
    private TrafficOpsMaintenanceGuardrailMapper opsMaintenanceGuardrailMapper;
    @Resource
    private PublicOpsAttachInfoExtService opsAttachInfoExtService;
    @Resource
    private TrafficOpsGuardrailInsuranceMapper opsGuardrailInsuranceMapper;
    @Resource
    private TrafficOpsGuardrailGapMapper opsGuardrailGapMapper;
    @Resource
    private ObjectProvider<BoundaryMsgProducer> boundaryMsgProducerObjectProvider;
    @Resource
    private PublicEquipOnOffCheckResultMapper publicEquipOnOffCheckResultMapper;
    @Resource
    private TrafficOpsMaintenanceUnfixableInfoMapper trafficOpsMaintenanceUnfixableInfoMapper;
    @Resource
    private TrafficOpsMaintenanceUnfixableFollowMapper trafficOpsMaintenanceUnfixableFollowMapper;


    @Override
    public String saveOpsMaintenance(TrafficOpsMaintenanceSaveReqVO saveReqVO, String source) {
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 1.保存报修申请信息
        TrafficOpsMaintenanceDO opsMaintenanceDO = BeanUtils.toBean(saveReqVO, TrafficOpsMaintenanceDO.class);
        if (StringUtils.isBlank(saveReqVO.getAffiliatedUnit())) {
            opsMaintenanceDO.setAffiliatedUnit(OpsConstants.STRING_ONE);
        }

        if (OpsConstants.STRING_ONE.equals(opsMaintenanceDO.getAffiliatedUnit()) && StringUtils.isBlank(saveReqVO.getSource())) {
            opsMaintenanceDO.setSource(OpsConstants.STRING_ONE);
        }

        if (saveReqVO.getFaultDate() == null) {
            saveReqVO.setFaultDate(new Date());
        }
        opsMaintenanceDO.setFaultDate(DateUtil.toLocalDateTime(saveReqVO.getFaultDate()));
        opsMaintenanceDO.setMaintenanceDate(LocalDateTimeUtil.of(new Date()));
        // 关联设备
        if (OpsConstants.STRING_ONE.equals(saveReqVO.getAffiliatedUnit())
                && StringUtils.isNotBlank(saveReqVO.getEquId())
                && OpsConstants.EQU_TYPE_DJ.equals(saveReqVO.getEquType())) {
            // 查询电警
            TrafficOpsElectricPoliceDO opsElectricPoliceDO = opsElectricPoliceMapper.selectById(saveReqVO.getEquId());
            if (opsElectricPoliceDO != null) {
                opsMaintenanceDO.setIpAddr(opsElectricPoliceDO.getEquName());
                opsMaintenanceDO.setDjType(opsElectricPoliceDO.getDjType());
            }
        }

        String operateResult;
        if (OpsConstants.STRING_ONE.equals(source)) {
            operateResult = "PC端上报工单";
        } else if (OpsConstants.STRING_THREE.equals(source)) {
            operateResult = "自动检测上报工单";
        } else {
            operateResult = "微信端上报工单";
        }
        if (StringUtils.isBlank(opsMaintenanceDO.getId())) { // 新增报修申请
            opsMaintenanceDO.setStatus(OpsConstants.STATUS_PENDING_ORDERS);

            if (StringUtils.isNotBlank(saveReqVO.getReportUserId())) {
                // 查询用户信息
                AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.parseLong(saveReqVO.getReportUserId()));
                if (adminUserRespDTO == null) {
                    throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
                }
                opsMaintenanceDO.setReportPersonnel(adminUserRespDTO.getNickname());
                opsMaintenanceDO.setReportDeptId(adminUserRespDTO.getDeptId() + "");

                DeptRespDTO deptRespDTO = deptApi.getDept(adminUserRespDTO.getDeptId());
                if (deptRespDTO != null) {
                    opsMaintenanceDO.setReportDept(deptRespDTO.getName());
                }
            }

            opsMaintenanceDO.setReportTime(LocalDateTimeUtil.of(new Date()));

            // 生成工单号
            opsMaintenanceDO.setOrderNum(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"));
            // 如果选择了维修人员，则为待接单状态
            if (StringUtils.isNotBlank(saveReqVO.getMaintenanceUserId())) {
                opsMaintenanceDO.setStatus(OpsConstants.STATUS_ACCEPT_ORDER);
                operateResult = "PC端上报工单并派单";
            }
            fillMaintenanceProp(opsMaintenanceDO);
            opsMaintenanceMapper.insert(opsMaintenanceDO);

            // 保存全流程信息
            TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
            opsMaintainFlowRecordSaveReqVO.setMaintenanceId(opsMaintenanceDO.getId());
            opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_REPORT);
            if (StringUtils.isNotBlank(saveReqVO.getReportUserId())) {
                opsMaintainFlowRecordSaveReqVO.setUserId(Long.parseLong(saveReqVO.getReportUserId()));
            }
            opsMaintainFlowRecordSaveReqVO.setOperateStatus(opsMaintenanceDO.getStatus());

            opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
            opsMaintainFlowRecordSaveReqVO.setRemark(saveReqVO.getRemark());
            opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);
        } else { // 修改报修申请信息
            // 校验存在
            validateOpsMaintenanceExists(opsMaintenanceDO.getId());

            opsMaintenanceMapper.updateById(opsMaintenanceDO);

            // 删除文件信息
            LambdaQueryWrapperX<TrafficOpsMaintenanceFilesDO> deleteSiteLambdaWrapperX = new LambdaQueryWrapperX<>();
            deleteSiteLambdaWrapperX.eq(TrafficOpsMaintenanceFilesDO::getMaintenanceId, opsMaintenanceDO.getId());
            deleteSiteLambdaWrapperX.eq(TrafficOpsMaintenanceFilesDO::getRepairType, OpsConstants.REPAIR_TYPE_ONE);
            opsMaintenanceFilesMapper.delete(deleteSiteLambdaWrapperX);
        }

        // 保存材料
        if (!CollectionUtils.isEmpty(saveReqVO.getFileInfoList())) {
            List<TrafficOpsMaintenanceFilesDO> instFileList = new ArrayList<>();
            for (TrafficOpsMaintenanceFileDTO fileSaveReqVO : saveReqVO.getFileInfoList()) {
                TrafficOpsMaintenanceFilesDO instFileDO = BeanUtils.toBean(fileSaveReqVO, TrafficOpsMaintenanceFilesDO.class);
                instFileDO.setMaintenanceId(opsMaintenanceDO.getId());
                instFileDO.setRepairType(OpsConstants.REPAIR_TYPE_ONE);
                instFileList.add(instFileDO);
            }
            opsMaintenanceFilesMapper.insertBatch(instFileList);
        }

        return opsMaintenanceDO.getId();
    }

    @Override
    public String saveOpsMaintenance(TrafficOpsMaintenanceSaveAllReqVO saveReqVO) {
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 1.保存报修申请信息
        TrafficOpsMaintenanceDO opsMaintenanceDO = BeanUtils.toBean(saveReqVO, TrafficOpsMaintenanceDO.class);
        if (StringUtils.isBlank(saveReqVO.getAffiliatedUnit())) {
            opsMaintenanceDO.setAffiliatedUnit(OpsConstants.STRING_ONE);
        }

        if (OpsConstants.STRING_ONE.equals(saveReqVO.getAffiliatedUnit()) && StringUtils.isBlank(saveReqVO.getSource())) {
            opsMaintenanceDO.setSource(OpsConstants.STRING_ONE);
        }

        opsMaintenanceDO.setFaultDate(DateUtil.toLocalDateTime(saveReqVO.getFaultDate()));
        opsMaintenanceDO.setMaintenanceDate(LocalDateTimeUtil.of(new Date()));
        //保存派单人信息
        if(WebFrameworkUtils.getLoginUserId() == null){
            opsMaintenanceDO.setDispatchUserId(saveReqVO.getReportUserId());
            opsMaintenanceDO.setDispatch(saveReqVO.getReportPersonnel());
        }else{
            AdminUserRespDTO currentUser = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
            opsMaintenanceDO.setDispatchUserId(currentUser.getId() + "");
            opsMaintenanceDO.setDispatch(currentUser.getNickname());
        }
        opsMaintenanceDO.setDispatchTime(LocalDateTimeUtil.of(new Date()));

        String operateResult = "工单补录新增";
        if (StringUtils.isBlank(opsMaintenanceDO.getId())) { // 新增报修申请
            opsMaintenanceDO.setStatus(OpsConstants.STATUS_PENDING_ORDERS);

            if (StringUtils.isNotBlank(saveReqVO.getReportUserId())) {
                // 查询用户信息
                AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.parseLong(saveReqVO.getReportUserId()));
                if (adminUserRespDTO == null) {
                    throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
                }
                opsMaintenanceDO.setReportPersonnel(adminUserRespDTO.getNickname());
                opsMaintenanceDO.setReportDeptId(adminUserRespDTO.getDeptId() + "");

                DeptRespDTO deptRespDTO = deptApi.getDept(adminUserRespDTO.getDeptId());
                if (deptRespDTO != null) {
                    opsMaintenanceDO.setReportDept(deptRespDTO.getName());
                }
            }

            opsMaintenanceDO.setReportTime(LocalDateTimeUtil.of(new Date()));

            // 生成工单号
            opsMaintenanceDO.setOrderNum(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"));
            fillMaintenanceProp(opsMaintenanceDO);
            opsMaintenanceMapper.insert(opsMaintenanceDO);
        } else { // 修改报修申请信息
            // 校验存在
            validateOpsMaintenanceExists(opsMaintenanceDO.getId());

            operateResult = "工单补录修改";

            opsMaintenanceMapper.updateById(opsMaintenanceDO);

            // 删除文件信息
            LambdaQueryWrapperX<TrafficOpsMaintenanceFilesDO> deleteSiteLambdaWrapperX = new LambdaQueryWrapperX<>();
            deleteSiteLambdaWrapperX.eq(TrafficOpsMaintenanceFilesDO::getMaintenanceId, opsMaintenanceDO.getId());
            deleteSiteLambdaWrapperX.eq(TrafficOpsMaintenanceFilesDO::getRepairType, OpsConstants.REPAIR_TYPE_ONE);
            opsMaintenanceFilesMapper.delete(deleteSiteLambdaWrapperX);
        }

        // 更新设备取电位置
        if (OpsConstants.STRING_ONE.equals(saveReqVO.getAffiliatedUnit())) {
            updatePowerSupplyLocation(saveReqVO.getEquId(), saveReqVO.getEquType(), saveReqVO.getPowerSupplyLocation());
        }

        // 保存材料
        if (!CollectionUtils.isEmpty(saveReqVO.getFileInfoList())) {
            List<TrafficOpsMaintenanceFilesDO> instFileList = new ArrayList<>();
            for (TrafficOpsMaintenanceFileDTO fileSaveReqVO : saveReqVO.getFileInfoList()) {
                TrafficOpsMaintenanceFilesDO instFileDO = BeanUtils.toBean(fileSaveReqVO, TrafficOpsMaintenanceFilesDO.class);
                instFileDO.setMaintenanceId(opsMaintenanceDO.getId());
                instFileDO.setRepairType(OpsConstants.REPAIR_TYPE_ONE);
                instFileList.add(instFileDO);
            }
            opsMaintenanceFilesMapper.insertBatch(instFileList);
        }
        // 保存维修信息
        String isFinish = OpsConstants.STRING_ZERO;
        if (!CollectionUtils.isEmpty(saveReqVO.getProgressVO())) {
            for (TrafficOpsMaintainProgressSaveReqVO vo : saveReqVO.getProgressVO()) {
                vo.setMaintenanceId(opsMaintenanceDO.getId());
                vo.setAffiliatedUnit(opsMaintenanceDO.getAffiliatedUnit());
                saveMaintenanceProgressALL(vo);

                if (OpsConstants.STRING_ONE.equals(vo.getIsFinish())) {
                    isFinish = OpsConstants.STRING_ONE;
                }
            }
        }

        // 保存确认人、确认状态
        if (OpsConstants.STRING_ONE.equals(isFinish)) {
            opsMaintenanceDO.setStatus(OpsConstants.STATUS_CONFIRM);
//            opsMaintenanceDO.setConfirmor(currentUser.getNickname());
//            opsMaintenanceDO.setConfirmorUserId(currentUser.getId() + "");
//            opsMaintenanceDO.setConfirmDate(LocalDateTimeUtil.of(new Date()));

            // 保存完成时间
            opsMaintenanceDO.setFinishTime(LocalDateTimeUtil.of(new Date()));
            if (opsMaintenanceDO.getFaultDate() != null)  {
                Duration duration = Duration.between(opsMaintenanceDO.getFaultDate(), opsMaintenanceDO.getFinishTime());
                double workTime = Math.round(duration.toMinutes() * 100.0 / 60.0) / 100.0;
                opsMaintenanceDO.setWorkTime(workTime);
            }
            opsMaintenanceMapper.updateById(opsMaintenanceDO);
        }

        // 保存全流程信息
        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(opsMaintenanceDO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_SUPPLEMENT);
        opsMaintainFlowRecordSaveReqVO.setUserId(Long.parseLong(saveReqVO.getReportUserId()));
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(opsMaintenanceDO.getStatus());

        opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
        opsMaintainFlowRecordSaveReqVO.setRemark(saveReqVO.getRemark());
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);

        return opsMaintenanceDO.getId();
    }

    @Override
    public PageResult<TrafficOpsMaintenanceRespVO> getOpsMaintenancePage(TrafficOpsMaintenancePageReqVO pageReqVO, String queryType) {
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isBlank(pageReqVO.getAffiliatedUnit())) {
            pageReqVO.setAffiliatedUnit(OpsConstants.STRING_ONE);
        }
//        queryWrapperX.eq(TrafficOpsMaintenanceDO::getAffiliatedUnit, pageReqVO.getAffiliatedUnit());
        if(!StringUtil.isEmpty(pageReqVO.getProblem())){
            List<String> stringList = HanLPUtils.segmentForRoad(pageReqVO.getProblem());
            if (!CollectionUtils.isEmpty(stringList)) {
                for (String str : stringList) {
                    queryWrapperX.like(TrafficOpsMaintenanceDO::getProblem, str);
                }
            }
        }
        queryWrapperX.eq(TrafficOpsMaintenanceDO::getAffiliatedUnit, pageReqVO.getAffiliatedUnit());
        queryWrapperX.eqIfPresent(TrafficOpsMaintenanceDO::getEquType, pageReqVO.getEquType());
        queryWrapperX.eqIfPresent(TrafficOpsMaintenanceDO::getEquId, pageReqVO.getEquId());
        queryWrapperX.likeIfPresent(TrafficOpsMaintenanceDO::getEquAddr, pageReqVO.getEquAddr());
        queryWrapperX.eqIfPresent(TrafficOpsMaintenanceDO::getFaultType, pageReqVO.getFaultType());
        if (OpsConstants.QUERY_TYPE_THREE.equals(queryType)) {
            // 查询用户有没有查询全部的角色
            boolean hasAuth = wechatAuthCenterExtService.hasSpecifyRoleAuth(Long.parseLong(pageReqVO.getReportUserId()), OpsConstants.ROLE_VIEW_ALL_ID);
            if (!hasAuth) {
                queryWrapperX.and(wrap -> wrap.eq(TrafficOpsMaintenanceDO::getReportUserId, pageReqVO.getReportUserId())
                        .or()
                        .eq(TrafficOpsMaintenanceDO::getMaintenanceUserId, pageReqVO.getReportUserId()));
            }
        } else {
            queryWrapperX.eqIfPresent(TrafficOpsMaintenanceDO::getReportUserId, pageReqVO.getReportUserId());
            queryWrapperX.eqIfPresent(TrafficOpsMaintenanceDO::getMaintenanceUserId, pageReqVO.getMaintenanceUserId());
            if (OpsConstants.QUERY_TYPE_ONE.equals(queryType)) {
                queryWrapperX.isNull(TrafficOpsMaintenanceDO::getDispatchUserId);
                queryWrapperX.eq(TrafficOpsMaintenanceDO::getStatus, OpsConstants.STATUS_PENDING_ORDERS);
            } else if (OpsConstants.QUERY_TYPE_TWO.equals(queryType)) {
                queryWrapperX.eqIfPresent(TrafficOpsMaintenanceDO::getDispatchUserId, pageReqVO.getDispatchUserId());
            }
        }


        queryWrapperX.eqIfPresent(TrafficOpsMaintenanceDO::getDispatch, pageReqVO.getDispatch());
        queryWrapperX.eqIfPresent(TrafficOpsMaintenanceDO::getStatus, pageReqVO.getStatus());
        //queryWrapperX.orderByAsc(TrafficOpsMaintenanceDO::getStatus).orderByDesc(TrafficOpsMaintenanceDO::getCreateTime).orderByDesc(TrafficOpsMaintenanceDO::getId);
        String customOrderSql = " case status when 1 then 1 when 6 then 2 when 4 then 3 when 8 then 4 when 5 then 5 when 9 then 6 when 10 then 7 when 7 then 8 else 9 end ,create_time desc";
        queryWrapperX.last("ORDER BY" + customOrderSql);


        PageResult<TrafficOpsMaintenanceRespVO> pageResult = BeanUtils.toBean(opsMaintenanceMapper.selectPage(pageReqVO, queryWrapperX), TrafficOpsMaintenanceRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(respVO -> respVO.setFileDTOList(getOpsMaintenanceFiles(respVO.getId(), null, OpsConstants.REPAIR_TYPE_ONE)));
        return pageResult;
    }

    @Override
    public void dispatchRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        if (saveReqVO == null || StringUtils.isBlank(saveReqVO.getId())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 校验存在
        TrafficOpsMaintenanceDO maintenanceDO = opsMaintenanceMapper.selectById(saveReqVO.getId());
        if (maintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        // 校验注册用户是否存在
        TrafficOpsRegisterDO dispatchRegisterDO = getOpsRegisterByUserId(saveReqVO.getDispatchUserId());
        if (dispatchRegisterDO == null || StringUtils.isBlank(dispatchRegisterDO.getOpenid())) {
            throw exception(OpsErrorCodeConstants.OPS_REGISTER_NOT_EXISTS);
        }

        TrafficOpsRegisterDO maintenanceRegisterDO = getOpsRegisterByUserId(saveReqVO.getMaintenanceUserId());
        if (maintenanceRegisterDO == null || StringUtils.isBlank(maintenanceRegisterDO.getOpenid())) {
            throw exception(OpsErrorCodeConstants.OPS_REGISTER_NOT_EXISTS);
        }

        maintenanceDO.setDispatchUserId(saveReqVO.getDispatchUserId());
        maintenanceDO.setDispatch(dispatchRegisterDO.getName());
        maintenanceDO.setDispatchTime(LocalDateTimeUtil.of(new Date()));
        maintenanceDO.setDispatchAdress(saveReqVO.getDispatchAdress());
        maintenanceDO.setDispatchLng(saveReqVO.getDispatchLng());
        maintenanceDO.setDispatchLat(saveReqVO.getDispatchLat());
        maintenanceDO.setMaintenanceUserId(saveReqVO.getMaintenanceUserId());
        maintenanceDO.setMaintenancePersonnel(maintenanceRegisterDO.getName());
        maintenanceDO.setMaintenancePhone(saveReqVO.getMaintenancePhone());
        maintenanceDO.setStatus(OpsConstants.STATUS_ACCEPT_ORDER);
        maintenanceDO.setProjectedRepair(saveReqVO.getProjectedRepair());
        opsMaintenanceMapper.updateById(maintenanceDO);

        // 保存流程
        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(maintenanceDO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_DISPATCH);
        opsMaintainFlowRecordSaveReqVO.setUserId(Long.parseLong(saveReqVO.getDispatchUserId()));
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(maintenanceDO.getStatus());
        String operateResult = "微信派单[" + maintenanceRegisterDO.getName() + "]";
        opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
        opsMaintainFlowRecordSaveReqVO.setRemark(saveReqVO.getRemark());
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);

        // 给维修用户推送信息
        Map<String, TemplateData> data = new HashMap<>();
        data.put("character_string1" , new TemplateData().setValue(maintenanceDO.getOrderNum()));
        data.put("thing10" , new TemplateData().setValue(maintenanceDO.getReportPersonnel()));
        data.put("time6" , new TemplateData().setValue(DateUtil.format(maintenanceDO.getReportTime(), "yyyy/MM/dd HH:mm:ss")));
        data.put("thing48" , new TemplateData().setValue(maintenanceDO.getDispatch()));

        WechatTemplate template = new WechatTemplate();
        template.setTemplate_id(WechatConfig.getMgtTodoTempId());
        template.setData(data);

        template.setTouser(maintenanceRegisterDO.getOpenid());
//        template.setUrl();
        try {
            SendTemplateMessage.sendTemplateMessage(WeixinUtil.getAccessToken().getToken() , template);
        } catch (Exception e) {
            log.error("模板消息发送失败！" , e);
        }
    }

    @Override
    public void cancelRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        if (saveReqVO == null || StringUtils.isBlank(saveReqVO.getId())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 校验存在
        TrafficOpsMaintenanceDO maintenanceDO = opsMaintenanceMapper.selectById(saveReqVO.getId());
        if (maintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        if (OpsConstants.STATUS_COMPLETED.equals(maintenanceDO.getStatus())) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_ALLOW_CANCEL);
        }

        maintenanceDO.setCancelUserId(saveReqVO.getCancelUserId());
        maintenanceDO.setCancelPersonnel(saveReqVO.getCancelPersonnel());
        maintenanceDO.setCancelTime(LocalDateTimeUtil.of(new Date()));
        maintenanceDO.setCancelReason(saveReqVO.getCancelReason());
        maintenanceDO.setStatus(OpsConstants.STATUS_CANCEL);
        opsMaintenanceMapper.updateById(maintenanceDO);

        // 保存全流程信息
        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(maintenanceDO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_CANCEL);
        opsMaintainFlowRecordSaveReqVO.setUserId(Long.parseLong(saveReqVO.getCancelUserId()));
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(maintenanceDO.getStatus());

        opsMaintainFlowRecordSaveReqVO.setOperateResult("取消报单");
        opsMaintainFlowRecordSaveReqVO.setRemark(saveReqVO.getRemark());
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);

        //通知上报人
        TrafficOpsRegisterDO reportRegisterDO = getOpsRegisterByUserId(maintenanceDO.getReportUserId());
        if (reportRegisterDO == null || StringUtils.isBlank(reportRegisterDO.getOpenid())) {
            throw exception(OpsErrorCodeConstants.OPS_REGISTER_NOT_EXISTS);
        }
        Map<String, TemplateData> data = new HashMap<>();
        data.put("thing5" , new TemplateData().setValue(saveReqVO.getCancelReason()));
        data.put("time4" , new TemplateData().setValue(DateUtil.format(new Date(), "yyyy/MM/dd HH:mm:ss")));

        WechatTemplate template = new WechatTemplate();
        template.setTemplate_id(WechatConfig.getMgtTodoTempId());
        template.setData(data);
        template.setTouser(reportRegisterDO.getOpenid());
        try {
            SendTemplateMessage.sendTemplateMessage(WeixinUtil.getAccessToken().getToken() , template);
        } catch (Exception e) {
            log.error("模板消息发送失败！" , e);
        }

    }

    private TrafficOpsRegisterDO getOpsRegisterByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        LambdaQueryWrapperX<TrafficOpsRegisterDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.isNotNull(TrafficOpsRegisterDO::getOpenid);
        lambdaQueryWrapperX.eq(TrafficOpsRegisterDO::getUserId, userId);

        List<TrafficOpsRegisterDO> registerDOList = trafficOpsRegisterMapper.selectList(lambdaQueryWrapperX);
        if (CollUtil.isEmpty(registerDOList) || registerDOList.size() > 1) {
            return null;
        }
        return registerDOList.get(0);
    }

    @Override
    public TrafficOpsMaintenanceRespVO getOpsMaintenance(String id) {
        TrafficOpsMaintenanceDO opsMaintenanceDO = opsMaintenanceMapper.selectById(id);
        if (opsMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        TrafficOpsMaintenanceRespVO respVO = BeanUtils.toBean(opsMaintenanceDO, TrafficOpsMaintenanceRespVO.class);
        if (OpsConstants.STRING_TWO.equals(opsMaintenanceDO.getSource()) && StringUtils.isNotBlank(opsMaintenanceDO.getReportUserId() )) {
            //通知上报人
            TrafficOpsRegisterDO reportRegisterDO = getOpsRegisterByUserId(opsMaintenanceDO.getReportUserId());
            if (reportRegisterDO != null) {
                respVO.setReportUserPhone(reportRegisterDO.getPhone());
            }
        }

        respVO.setFileDTOList(getOpsMaintenanceFiles(opsMaintenanceDO.getId(), null, OpsConstants.REPAIR_TYPE_ONE));

        respVO.setEquipInfoDTO(queryOpsEquipInfo(respVO.getAffiliatedUnit(), respVO.getEquType(), respVO.getEquId()));

        return respVO;
    }

    @Override
    public List<TrafficOpsMaintainProgressRespVO> getOpsMaintenanceProgress(String maintenanceId) {
        // 查询维修工单信息
        TrafficOpsMaintenanceDO opsMaintenanceDO = opsMaintenanceMapper.selectById(maintenanceId);
        if (opsMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        LambdaQueryWrapperX<TrafficOpsMaintainProgressDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficOpsMaintainProgressDO::getMaintenanceId, maintenanceId);
        lambdaQueryWrapperX.orderByAsc(TrafficOpsMaintainProgressDO::getCreateTime).orderByDesc(TrafficOpsMaintainProgressDO::getId);
        List<TrafficOpsMaintainProgressRespVO> progressRespVOList = BeanUtils.toBean(opsMaintainProgressMapper.selectList(lambdaQueryWrapperX), TrafficOpsMaintainProgressRespVO.class);
        Optional.ofNullable(progressRespVOList).orElse(new ArrayList<>()).forEach(progressRespVO -> {
            progressRespVO.setFileDTOList(getOpsMaintenanceFiles(maintenanceId, progressRespVO.getId(), OpsConstants.REPAIR_TYPE_TWO));

            progressRespVO.setOpsMaintainCmSaveReqVOList(getOpsMaintainCmList(progressRespVO.getId()));

            progressRespVO.setOpsMaintainTeamSaveReqVOList(getOpsMaintainTeamList(progressRespVO.getId()));

            // 如果是护栏，查询护栏维修信息
            if (OpsConstants.EQU_TYPE_GUARDRAIL.equals(opsMaintenanceDO.getEquType())) {
                TrafficOpsMaintenanceGuardrailExtRespVO respVO = queryMaintenanceGuardrail(maintenanceId);
                progressRespVO.setOpsMaintenanceGuardrailVO(respVO);
            }
        });


        return progressRespVOList;
    }

    @Override
    public void acceptRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        TrafficOpsMaintenanceDO opsMaintenanceDO = opsMaintenanceMapper.selectById(saveReqVO.getId());
        if (opsMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        if (!OpsConstants.STATUS_ACCEPT_ORDER.equals(opsMaintenanceDO.getStatus())) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_ALLOW_ACCEPT);
        }

        if (!saveReqVO.getMaintenanceUserId().equals(opsMaintenanceDO.getMaintenanceUserId())) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_ALLOW_USER);
        }
        opsMaintenanceDO.setStatus(OpsConstants.STATUS_IN_MAINTENANCE);
        opsMaintenanceDO.setReceiveDate(LocalDateTimeUtil.of(new Date()));
        opsMaintenanceDO.setReceiveAdress(saveReqVO.getReceiveAdress());
        opsMaintenanceDO.setReceiveLng(saveReqVO.getReceiveLng());
        opsMaintenanceDO.setReceiveLat(saveReqVO.getReceiveLat());

        opsMaintenanceMapper.updateById(opsMaintenanceDO);

        // 保存全流程信息
        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(opsMaintenanceDO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_RECEIVE);
        opsMaintainFlowRecordSaveReqVO.setUserId(Long.parseLong(saveReqVO.getMaintenanceUserId()));
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(opsMaintenanceDO.getStatus());
        String operateResult = "维修人员接单";
        if (StringUtils.isBlank(opsMaintenanceDO.getDispatchUserId())) {
            operateResult += "[抢单]";
        }
        opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
        opsMaintainFlowRecordSaveReqVO.setRemark(saveReqVO.getRemark());
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);
    }

    @Override
    public void saveMaintenanceProgress(TrafficOpsMaintainProgressSaveReqVO saveReqVO) {
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 查询维保记录
        boolean isUpdMain = false;
        TrafficOpsMaintenanceDO opsMaintenanceDO = opsMaintenanceMapper.selectById(saveReqVO.getMaintenanceId());
        if (opsMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        if (!"系统检测".equals(saveReqVO.getMaintenancePersonnel()) && !OpsConstants.STATUS_IN_MAINTENANCE.equals(opsMaintenanceDO.getStatus())) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_ALLOW_MAINTENANCE);
        }

        // 关联设备
        if (StringUtils.isBlank(opsMaintenanceDO.getIpAddr())
                &&OpsConstants.STRING_ONE.equals(saveReqVO.getAffiliatedUnit())
                && StringUtils.isNotBlank(saveReqVO.getEquId())
                && OpsConstants.EQU_TYPE_DJ.equals(saveReqVO.getEquType())) {
            // 查询电警
            TrafficOpsElectricPoliceDO opsElectricPoliceDO = opsElectricPoliceMapper.selectById(saveReqVO.getEquId());
            if (opsElectricPoliceDO != null) {
                opsMaintenanceDO.setIpAddr(opsElectricPoliceDO.getEquName());
                opsMaintenanceDO.setDjType(opsElectricPoliceDO.getDjType());
            }
        }

        StringBuilder operateResult = new StringBuilder("工单维修：");
        // 1.保存报修进度信息
        TrafficOpsMaintainProgressDO progressDO = BeanUtils.toBean(saveReqVO, TrafficOpsMaintainProgressDO.class);
        operateResult.append(progressDO.getMaintenance());
        progressDO.setAffiliatedUnit(opsMaintenanceDO.getAffiliatedUnit());
        if (StringUtils.isBlank(progressDO.getId())) { // 新增报修进度申请

            if (StringUtils.isNotBlank(saveReqVO.getMaintenanceUserId())) {
                // 查询用户信息
                AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.parseLong(saveReqVO.getMaintenanceUserId()));
                if (adminUserRespDTO == null) {
                    throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
                }
                progressDO.setMaintenancePersonnel(adminUserRespDTO.getNickname());
            }

            progressDO.setMaintenanceDate(LocalDateTimeUtil.of(new Date()));

            opsMaintainProgressMapper.insert(progressDO);
        } else { // 修改报修申请信息
            // 校验存在
            validateOpsMaintainProgressExists(saveReqVO.getId());

            opsMaintainProgressMapper.updateById(progressDO);

            // 删除文件信息
            LambdaQueryWrapperX<TrafficOpsMaintenanceFilesDO> deleteSiteLambdaWrapperX = new LambdaQueryWrapperX<>();
            deleteSiteLambdaWrapperX.eq(TrafficOpsMaintenanceFilesDO::getProgressId, progressDO.getId());
            deleteSiteLambdaWrapperX.eq(TrafficOpsMaintenanceFilesDO::getRepairType, OpsConstants.REPAIR_TYPE_TWO);
            opsMaintenanceFilesMapper.delete(deleteSiteLambdaWrapperX);

            // 删除耗材信息
            LambdaQueryWrapperX<TrafficOpsMaintainCmDO> deleteOpsMaintainCmLambdaWrapperX = new LambdaQueryWrapperX<>();
            deleteOpsMaintainCmLambdaWrapperX.eq(TrafficOpsMaintainCmDO::getProgressId, progressDO.getId());
            opsMaintainCmMapper.delete(deleteOpsMaintainCmLambdaWrapperX);

            // 删除维修成员信息
            LambdaQueryWrapperX<TrafficOpsMaintenanceTeamDO> deleteOpsMaintainTeamLambdaWrapperX = new LambdaQueryWrapperX<>();
            deleteOpsMaintainTeamLambdaWrapperX.eq(TrafficOpsMaintenanceTeamDO::getProgressId, progressDO.getId());
            opsMaintenanceTeamMapper.delete(deleteOpsMaintainTeamLambdaWrapperX);

        }

        // 更新设备取电位置
        if (OpsConstants.STRING_ONE.equals(saveReqVO.getAffiliatedUnit())) {
            updatePowerSupplyLocation(saveReqVO.getEquId(), saveReqVO.getEquType(), saveReqVO.getPowerSupplyLocation());
        }

        // 更新维修记录信息
        if (StringUtils.isNotBlank(saveReqVO.getEquId()) && StringUtils.isNotBlank(saveReqVO.getEquType())) {
            opsMaintenanceDO.setEquId(saveReqVO.getEquId());
            opsMaintenanceDO.setEquType(saveReqVO.getEquType());
            isUpdMain = true;
        }

        if (StringUtils.isNotBlank(saveReqVO.getFaultType())) {
            opsMaintenanceDO.setFaultType(saveReqVO.getFaultType());
            isUpdMain = true;
        }

        if (StringUtils.isNotBlank(saveReqVO.getActualRepair())) {
            opsMaintenanceDO.setActualRepair(saveReqVO.getActualRepair());
            isUpdMain = true;
        }

        // 保存材料
        if (!CollectionUtils.isEmpty(saveReqVO.getFileDTOList())) {
            List<TrafficOpsMaintenanceFilesDO> instFileList = new ArrayList<>();
            for (TrafficOpsMaintenanceFileDTO fileSaveReqVO : saveReqVO.getFileDTOList()) {
                TrafficOpsMaintenanceFilesDO instFileDO = BeanUtils.toBean(fileSaveReqVO, TrafficOpsMaintenanceFilesDO.class);
                instFileDO.setMaintenanceId(progressDO.getMaintenanceId());
                instFileDO.setProgressId(progressDO.getId());
                instFileDO.setRepairType(OpsConstants.REPAIR_TYPE_TWO);
                instFileList.add(instFileDO);
            }
            opsMaintenanceFilesMapper.insertBatch(instFileList);
        }

        // 保存耗材信息
        if (!CollectionUtils.isEmpty(saveReqVO.getOpsMaintainCmSaveReqVOList())) {
            List<TrafficOpsMaintainCmDO> instCmDOList = new ArrayList<>();
            for (AppTrafficOpsMaintainCmSaveReqVO opsMaintainCmSaveReqVO : saveReqVO.getOpsMaintainCmSaveReqVOList()) {
                TrafficOpsMaintainCmDO instCmDO = BeanUtils.toBean(opsMaintainCmSaveReqVO, TrafficOpsMaintainCmDO.class);
                instCmDO.setProgressId(progressDO.getId());
                instCmDOList.add(instCmDO);
            }
            opsMaintainCmMapper.insertBatch(instCmDOList);
        }

        // 保存维修成员信息
        if (!CollectionUtils.isEmpty(saveReqVO.getOpsMaintainTeamSaveReqVOList())) {
            operateResult.append(";维修成员：");
            List<TrafficOpsMaintenanceTeamDO> instTeamDOList = new ArrayList<>();
            for (TrafficOpsMaintenanceTeamSaveReqVO opsMaintainTeamSaveReqVO : saveReqVO.getOpsMaintainTeamSaveReqVOList()) {
                TrafficOpsMaintenanceTeamDO instTeamDO = BeanUtils.toBean(opsMaintainTeamSaveReqVO, TrafficOpsMaintenanceTeamDO.class);
                instTeamDO.setProgressId(progressDO.getId());
                instTeamDO.setMaintenanceId(saveReqVO.getId());
                instTeamDOList.add(instTeamDO);

                operateResult.append(instTeamDO.getMaintenancePersonnel()).append("、");
            }
            opsMaintenanceTeamMapper.insertBatch(instTeamDOList);
        }

        opsMaintenanceDO.setMaintenanceDate(LocalDateTimeUtil.of(new Date()));
        // 维修完成，更改状态
        if (OpsConstants.STRING_ONE.equals(saveReqVO.getIsFinish())) {
            if (OpsConstants.STRING_ONE.equals(opsMaintenanceDO.getAffiliatedUnit() )) {
                // 网络工单
                if (StringUtils.isNotBlank(opsMaintenanceDO.getReportUserId())
                        && OpsConstants.STRING_TWO.equals(opsMaintenanceDO.getSource())) {
                    // 查询上报用户信息
                    AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.parseLong(opsMaintenanceDO.getReportUserId()));
                    if (adminUserRespDTO != null) {
                        String finishMsg = opsProjectEmayConfig.getNoticeMsgMap().get("finishMsg");
                        opsNoticeRecordExtService.sendNoticeMsg(opsMaintenanceDO.getId(), OpsConstants.NOTICE_TYPE_SMS,
                                Long.parseLong(opsMaintenanceDO.getReportUserId()), adminUserRespDTO.getMobile(), finishMsg);
                    }
                }
            }
            // 2025-07-24 公安、交警都加确认功能
            opsMaintenanceDO.setStatus(OpsConstants.STATUS_CONFIRM);
            opsMaintenanceDO.setFinishTime(LocalDateTimeUtil.of(new Date()));
            if (opsMaintenanceDO.getFaultDate() != null)  {
                Duration duration = Duration.between(opsMaintenanceDO.getFaultDate(), opsMaintenanceDO.getFinishTime());
                double workTime = Math.round(duration.toMinutes() * 100.0 / 60.0) / 100.0;
                opsMaintenanceDO.setWorkTime(workTime);
            }
            isUpdMain = true;
        }else{
            //同时更新设备的状态
            updateEquStatus(opsMaintenanceDO.getEquId(),opsMaintenanceDO.getEquType(),OpsConstants.EQU_STATUS_GZ, saveReqVO.getAffiliatedUnit());
        }

        // 调起处理类
        opsMaintenanceCenterStrategyService.assignMaintenanceHandler(opsMaintenanceDO, saveReqVO);

        if (isUpdMain) {
            fillMaintenanceProp(opsMaintenanceDO);
            opsMaintenanceMapper.updateById(opsMaintenanceDO);
        }

        // 保存全流程信息
        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(opsMaintenanceDO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_MAINTENANCE);
        opsMaintainFlowRecordSaveReqVO.setUserId(Long.parseLong(saveReqVO.getMaintenanceId()));
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(opsMaintenanceDO.getStatus());

        opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult.toString());
        opsMaintainFlowRecordSaveReqVO.setRemark(opsMaintenanceDO.getRemark());
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);
    }

    private void updatePowerSupplyLocation(String equId,String equType,String powerSupplyLocation) {
        // 更新设备取电位置
        if (StringUtils.isNotBlank(powerSupplyLocation)
                && StringUtils.isNotBlank(equId)
                && StringUtils.isNotBlank(equType)) {
            String memberId = equId;
            if (OpsConstants.EQU_TYPE_DJ.equals(equType)) {
                // 查询电警信息
                TrafficOpsElectricPoliceDO opsElectricPoliceDO = opsElectricPoliceMapper.selectById(equId);
                if (opsElectricPoliceDO != null) {
                    memberId = opsElectricPoliceDO.getMemberBh();
                }
            }
            LambdaUpdateWrapper<TrafficOpsMemberDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(TrafficOpsMemberDO::getId, memberId);
            updateWrapper.set(TrafficOpsMemberDO::getPowerSupplyLocation, powerSupplyLocation);
            opsMemberMapper.update(updateWrapper);
        }
    }

    private TrafficOpsMaintenanceGuardrailExtRespVO queryMaintenanceGuardrail(String maintenanceId) {
        // 查询护栏微信信息
        LambdaQueryWrapperX<TrafficOpsMaintenanceGuardrailDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficOpsMaintenanceGuardrailDO::getMaintenanceId, maintenanceId);
        List<TrafficOpsMaintenanceGuardrailDO> maintenanceGuardrailDOList = opsMaintenanceGuardrailMapper.selectList(lambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(maintenanceGuardrailDOList)) {
            return null;
        }

        TrafficOpsMaintenanceGuardrailExtRespVO respVO = BeanUtils.toBean(maintenanceGuardrailDOList.get(0), TrafficOpsMaintenanceGuardrailExtRespVO.class);
        // 查询变更前后照片
        List<PublicOpsAttachInfoRespVO> attachInfoRespVOList = opsAttachInfoExtService.queryOpsAttachInfoList(OpsConstants.STRING_ONE, null, respVO.getId());
        respVO.setAttachInfoList(attachInfoRespVOList);

        // 查询保险信息
        LambdaQueryWrapperX<TrafficOpsGuardrailInsuranceDO> insuranceDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        insuranceDOLambdaQueryWrapperX.eq(TrafficOpsGuardrailInsuranceDO::getManceGuardrailId, respVO.getId());
        List<TrafficOpsGuardrailInsuranceDO> opsMaintenanceInsuranceDOList = opsGuardrailInsuranceMapper.selectList(insuranceDOLambdaQueryWrapperX);
        if (!CollectionUtils.isEmpty(opsMaintenanceInsuranceDOList)) {
            TrafficOpsGuardrailInsuranceRespVO opsMaintenanceInsuranceRespVO = BeanUtils.toBean(opsMaintenanceInsuranceDOList.get(0), TrafficOpsGuardrailInsuranceRespVO.class);
            respVO.setInsuranceInfoVO(opsMaintenanceInsuranceRespVO);
        }
        // 查询护栏开口信息
        LambdaQueryWrapperX<TrafficOpsGuardrailGapDO> gapDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        gapDOLambdaQueryWrapperX.eq(TrafficOpsGuardrailGapDO::getManceGuardrailId, respVO.getId());
        List<TrafficOpsGuardrailGapDO> opsMaintenanceGuardrailGapDOList = opsGuardrailGapMapper.selectList(gapDOLambdaQueryWrapperX);
        if (!CollectionUtils.isEmpty(opsMaintenanceGuardrailGapDOList)) {
            List<TrafficOpsGuardrailGapRespVO> opsMaintenanceGuardrailGapRespVOList = BeanUtils.toBean(opsMaintenanceGuardrailGapDOList, TrafficOpsGuardrailGapRespVO.class);
            respVO.setGuardrailGapVOList(opsMaintenanceGuardrailGapRespVOList);
        }

        return respVO;
    }

    @Override
    public Map<String, Long> queryRepairOrderCnt(Long userId, String affiliatedUnit) {
        Map<String, Long> map = new HashMap<>();

        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> dpdLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        dpdLambdaQueryWrapperX.eq(TrafficOpsMaintenanceDO::getAffiliatedUnit, affiliatedUnit);
        dpdLambdaQueryWrapperX.isNull(TrafficOpsMaintenanceDO::getMaintenanceUserId);
        dpdLambdaQueryWrapperX.eq(TrafficOpsMaintenanceDO::getStatus, OpsConstants.STATUS_PENDING_ORDERS);
        Long dpdCnt = opsMaintenanceMapper.selectCount(dpdLambdaQueryWrapperX);
        map.put("dpdCnt", dpdCnt);

        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> dwxLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        dwxLambdaQueryWrapperX.eq(TrafficOpsMaintenanceDO::getAffiliatedUnit, affiliatedUnit);
        dwxLambdaQueryWrapperX.eq(TrafficOpsMaintenanceDO::getMaintenanceUserId, userId);
        List<String> inStatus = new ArrayList<>();
        inStatus.add(OpsConstants.STATUS_COMPLETED);
        inStatus.add(OpsConstants.STATUS_REJECT);
        dwxLambdaQueryWrapperX.notIn(TrafficOpsMaintenanceDO::getStatus, inStatus);
        Long dwxCnt = opsMaintenanceMapper.selectCount(dwxLambdaQueryWrapperX);
        map.put("dwxCnt", dwxCnt);
        return map;

    }

    private void updateEquStatus(String id,String type,String status, String affiliatedUnit) {
        if (OpsConstants.STRING_TWO.equals(affiliatedUnit)) {
            if(OpsConstants.EQU_TYPE_DJ.equals(type)){
                PoliceOpsElectricPoliceDO updateObj = new PoliceOpsElectricPoliceDO();
                updateObj.setId(id);
                updateObj.setStatus(status);
                // 更新
                policeOpsElectricPoliceMapper.updateById(updateObj);
            }
        } else {
            if(OpsConstants.EQU_TYPE_XHJ.equals(type)){
                // 更新
                TrafficOpsSignalDO updateObj = new TrafficOpsSignalDO();
                updateObj.setId(id);
                updateObj.setStatus(status);
                opsSignalMapper.updateById(updateObj);

            }else if(OpsConstants.EQU_TYPE_XHD.equals(type)){
                // 更新
                TrafficOpsSignalLightDO updateObj = new TrafficOpsSignalLightDO();
                updateObj.setId(id);
                updateObj.setStatus(status);
                // 更新
                opsSignalLightMapper.updateById(updateObj);

            }else if(OpsConstants.EQU_TYPE_DJ.equals(type)){
                TrafficOpsElectricPoliceDO updateObj = new TrafficOpsElectricPoliceDO();
                updateObj.setId(id);
                updateObj.setStatus(status);
                // 更新
                opsElectricPoliceMapper.updateById(updateObj);
            }else if(OpsConstants.EQU_TYPE_GJ.equals(type)){
                TrafficOpsMemberDO updateObj = new TrafficOpsMemberDO();
                updateObj.setId(id);
                updateObj.setStatus(status);
                // 更新
                opsMemberMapper.updateById(updateObj);
            }else if(OpsConstants.EQU_TYPE_LED.equals(type)){
                TrafficOpsBigledDO updateObj= new TrafficOpsBigledDO();
                updateObj.setId(id);
                updateObj.setStatus(status);
                // 更新
                opsBigledMapper.updateById(updateObj);
            }
        }
    }

    private TrafficOpsEquipInfoDTO queryOpsEquipInfo(String affiliatedUnit, String equType, String equId)  {

        if (OpsConstants.STRING_TWO.equals(affiliatedUnit)) {
            if(OpsConstants.EQU_TYPE_DJ.equals(equType)){
                return BeanUtils.toBean(policeOpsElectricPoliceMapper.selectById(equId), TrafficOpsEquipInfoDTO.class);
            }
        } else {
            if(OpsConstants.EQU_TYPE_XHJ.equals(equType)){
                return BeanUtils.toBean(opsSignalMapper.selectById(equId), TrafficOpsEquipInfoDTO.class);
            }else if(OpsConstants.EQU_TYPE_XHD.equals(equType)){
                return BeanUtils.toBean(opsSignalLightMapper.selectById(equId), TrafficOpsEquipInfoDTO.class);
            }else if(OpsConstants.EQU_TYPE_DJ.equals(equType)){
                return BeanUtils.toBean(opsElectricPoliceMapper.selectById(equId), TrafficOpsEquipInfoDTO.class);
            }else if(OpsConstants.EQU_TYPE_GJ.equals(equType)){
                return BeanUtils.toBean(opsMemberMapper.selectById(equId), TrafficOpsEquipInfoDTO.class);
            }else if(OpsConstants.EQU_TYPE_LED.equals(equType)){
                return BeanUtils.toBean(opsBigledMapper.selectById(equId), TrafficOpsEquipInfoDTO.class);
            }
        }
        return null;
    }

    private List<AppTrafficOpsMaintainCmSaveReqVO> getOpsMaintainCmList(String progressId) {
        LambdaQueryWrapperX<TrafficOpsMaintainCmDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(TrafficOpsMaintainCmDO::getProgressId, progressId);
        queryWrapperX.orderByDesc(TrafficOpsMaintainCmDO::getCreateTime).orderByDesc(TrafficOpsMaintainCmDO::getId);
        List<TrafficOpsMaintainCmDO> maintainCmDOList = opsMaintainCmMapper.selectList(queryWrapperX);
        return BeanUtils.toBean(maintainCmDOList, AppTrafficOpsMaintainCmSaveReqVO.class);
    }

    private List<TrafficOpsMaintenanceFileDTO> getOpsMaintenanceFiles(String maintenanceId, String progressId, String repairType) {
        LambdaQueryWrapperX<TrafficOpsMaintenanceFilesDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(TrafficOpsMaintenanceFilesDO::getMaintenanceId, maintenanceId);
        if (StringUtils.isNotBlank(progressId)) {
            queryWrapperX.eq(TrafficOpsMaintenanceFilesDO::getProgressId, progressId);
        }
        queryWrapperX.eq(TrafficOpsMaintenanceFilesDO::getRepairType, repairType);
        queryWrapperX.orderByDesc(TrafficOpsMaintenanceFilesDO::getCreateTime).orderByDesc(TrafficOpsMaintenanceFilesDO::getId);
        List<TrafficOpsMaintenanceFilesDO> fileList = opsMaintenanceFilesMapper.selectList(queryWrapperX);
        return Optional.ofNullable(fileList).orElse(new ArrayList<>()).stream().map(fileInfo -> {
            TrafficOpsMaintenanceFileDTO fileDTO = new TrafficOpsMaintenanceFileDTO();
            fileDTO.setFileType(fileInfo.getFileType());
            fileDTO.setFileUrl(fileInfo.getFileUrl());
            return fileDTO;
        }).collect(Collectors.toList());
    }

    private List<TrafficOpsMaintenanceTeamSaveReqVO> getOpsMaintainTeamList(String progressId) {
        LambdaQueryWrapperX<TrafficOpsMaintenanceTeamDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(TrafficOpsMaintenanceTeamDO::getProgressId, progressId);
        queryWrapperX.orderByDesc(TrafficOpsMaintenanceTeamDO::getCreateTime).orderByDesc(TrafficOpsMaintenanceTeamDO::getId);
        List<TrafficOpsMaintenanceTeamDO> maintainTeamDOList = opsMaintenanceTeamMapper.selectList(queryWrapperX);
        return BeanUtils.toBean(maintainTeamDOList, TrafficOpsMaintenanceTeamSaveReqVO.class);
    }

    private void validateOpsMaintenanceExists(String id) {
        if (opsMaintenanceMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }
    }

    private void validateOpsMaintainProgressExists(String id) {
        if (opsMaintainProgressMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTAIN_PROGRESS_NOT_EXISTS);
        }
    }

    @Override
    public void dispatchRepairOrderPc(TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        if (saveReqVO == null || StringUtils.isBlank(saveReqVO.getId())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 校验存在
        TrafficOpsMaintenanceDO maintenanceDO = opsMaintenanceMapper.selectById(saveReqVO.getId());
        if (maintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }
//PC端保存时不验证是否已经手机端绑定
//        // 校验注册用户是否存在
//        TrafficOpsRegisterDO dispatchRegisterDO = getOpsRegisterByUserId(saveReqVO.getDispatchUserId());
//        if (dispatchRegisterDO == null || StringUtils.isBlank(dispatchRegisterDO.getOpenid())) {
//            throw exception(OpsErrorCodeConstants.OPS_REGISTER_NOT_EXISTS);
//        }
//
//        TrafficOpsRegisterDO maintenanceRegisterDO = getOpsRegisterByUserId(saveReqVO.getMaintenanceUserId());
//        if (maintenanceRegisterDO == null || StringUtils.isBlank(maintenanceRegisterDO.getOpenid())) {
//            throw exception(OpsErrorCodeConstants.OPS_REGISTER_NOT_EXISTS);
//        }

        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        String operateResult = "PC派单[" + saveReqVO.getMaintenancePersonnel() + "]";
        if (OpsConstants.STATUS_TEMPORARILY_UNABLE_REPAIR.equals(maintenanceDO.getStatus())) {
            operateResult = "PC挂单后重派[" + saveReqVO.getMaintenancePersonnel() + "]";
            opsMaintainFlowRecordSaveReqVO.setRemark(maintenanceDO.getProblem());
        }

        AdminUserRespDTO currentUser = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        maintenanceDO.setDispatchUserId(currentUser.getId() + "");
        maintenanceDO.setDispatch(currentUser.getNickname());
        maintenanceDO.setDispatchTime(LocalDateTimeUtil.of(new Date()));
        maintenanceDO.setProjectedRepair(saveReqVO.getProjectedRepair());
//        maintenanceDO.setDispatchAdress(saveReqVO.getDispatchAdress());
//        maintenanceDO.setDispatchLng(saveReqVO.getDispatchLng());
//        maintenanceDO.setDispatchLat(saveReqVO.getDispatchLat());
        maintenanceDO.setMaintenanceUserId(saveReqVO.getMaintenanceUserId());
        maintenanceDO.setMaintenancePersonnel(saveReqVO.getMaintenancePersonnel());
        maintenanceDO.setMaintenancePhone(saveReqVO.getMaintenancePhone());
        maintenanceDO.setStatus(OpsConstants.STATUS_ACCEPT_ORDER);
        // ===== add by yanxl 20251217 暂时无法修复的订单重新派单 start ================
        maintenanceDO.setProblem(saveReqVO.getProblem());
        // ===== add by yanxl 20251217 暂时无法修复的订单重新派单 end ================
        opsMaintenanceMapper.updateById(maintenanceDO);

        // 保存全流程信息

        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(maintenanceDO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_DISPATCH);
        opsMaintainFlowRecordSaveReqVO.setUserId(currentUser.getId());
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(maintenanceDO.getStatus());
        opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);

        TrafficOpsRegisterDO reportRegisterDO = getOpsRegisterByUserId(saveReqVO.getReportUserId());
        if (reportRegisterDO != null) {
            // 网络报单给上报人发送短信
            if (OpsConstants.STRING_ONE.equals(saveReqVO.getAffiliatedUnit())
                    && OpsConstants.STRING_TWO.equals(saveReqVO.getSource())
                    && StringUtils.isNotBlank(reportRegisterDO.getPhone())) {
//            sms4JService.sendNoticeMsg(reportRegisterDO.getPhone(), "");
                String finishMsg = opsProjectEmayConfig.getNoticeMsgMap().get("dispatchMsg");
                opsNoticeRecordExtService.sendNoticeMsg(maintenanceDO.getId(), OpsConstants.NOTICE_TYPE_SMS,
                        Long.parseLong(maintenanceDO.getReportUserId()), reportRegisterDO.getPhone(), finishMsg);

            } else {
                if (StringUtils.isNotBlank(reportRegisterDO.getOpenid())) {
                    // 给上报人推送信息
                    Map<String, TemplateData> data = new HashMap<>();
                    data.put("thing3" , new TemplateData().setValue("您上报的工单已经被派单受理！"));
                    data.put("time18" , new TemplateData().setValue(DateUtil.format(new Date(), "yyyy/MM/dd HH:mm:ss")));
                    WechatTemplate template = new WechatTemplate();
                    template.setTemplate_id(WechatConfig.getMgtTodoSlTempId());
                    template.setData(data);
                    template.setTouser(reportRegisterDO.getOpenid());
                    try {
                        SendTemplateMessage.sendTemplateMessage(WeixinUtil.getAccessToken().getToken() , template);
                    } catch (Exception e) {
                        log.error("模板消息发送失败！" , e);
                    }
                }
            }
        }


    }

    private void saveMaintenanceProgressALL(TrafficOpsMaintainProgressSaveReqVO saveReqVO) {
        boolean isUpdMain = false;
        // 1.保存报修进度信息
        TrafficOpsMaintainProgressDO progressDO = BeanUtils.toBean(saveReqVO, TrafficOpsMaintainProgressDO.class);
        if (StringUtils.isBlank(progressDO.getId())) { // 新增报修进度申请

            if (StringUtils.isNotBlank(saveReqVO.getMaintenanceUserId())) {
                // 查询用户信息
                AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.parseLong(saveReqVO.getMaintenanceUserId()));
                if (adminUserRespDTO == null) {
                    throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
                }
                progressDO.setMaintenancePersonnel(adminUserRespDTO.getNickname());
            }

            //progressDO.setMaintenanceDate(LocalDateTimeUtil.of(new Date()));

            opsMaintainProgressMapper.insert(progressDO);
        } else { // 修改报修申请信息
            // 校验存在
            validateOpsMaintainProgressExists(saveReqVO.getId());

            opsMaintainProgressMapper.updateById(progressDO);

            // 删除文件信息
            LambdaQueryWrapperX<TrafficOpsMaintenanceFilesDO> deleteSiteLambdaWrapperX = new LambdaQueryWrapperX<>();
            deleteSiteLambdaWrapperX.eq(TrafficOpsMaintenanceFilesDO::getProgressId, progressDO.getId());
            deleteSiteLambdaWrapperX.eq(TrafficOpsMaintenanceFilesDO::getRepairType, OpsConstants.REPAIR_TYPE_TWO);
            opsMaintenanceFilesMapper.delete(deleteSiteLambdaWrapperX);

            // 删除耗材信息
            LambdaQueryWrapperX<TrafficOpsMaintainCmDO> deleteOpsMaintainCmLambdaWrapperX = new LambdaQueryWrapperX<>();
            deleteOpsMaintainCmLambdaWrapperX.eq(TrafficOpsMaintainCmDO::getProgressId, progressDO.getId());
            opsMaintainCmMapper.delete(deleteOpsMaintainCmLambdaWrapperX);

            // 删除维修成员信息
            LambdaQueryWrapperX<TrafficOpsMaintenanceTeamDO> deleteOpsMaintainTeamLambdaWrapperX = new LambdaQueryWrapperX<>();
            deleteOpsMaintainTeamLambdaWrapperX.eq(TrafficOpsMaintenanceTeamDO::getProgressId, progressDO.getId());
            opsMaintenanceTeamMapper.delete(deleteOpsMaintainTeamLambdaWrapperX);

        }

//        // 更新维修记录信息
//        if (StringUtils.isNotBlank(saveReqVO.getEquId())) {
//            opsMaintenanceDO.setEquId(saveReqVO.getEquId());
//            isUpdMain = true;
//        }
//
//        if (StringUtils.isNotBlank(saveReqVO.getFaultType())) {
//            opsMaintenanceDO.setFaultType(saveReqVO.getFaultType());
//            isUpdMain = true;
//        }

        // 保存材料
        if (!CollectionUtils.isEmpty(saveReqVO.getFileDTOList())) {
            List<TrafficOpsMaintenanceFilesDO> instFileList = new ArrayList<>();
            for (TrafficOpsMaintenanceFileDTO fileSaveReqVO : saveReqVO.getFileDTOList()) {
                TrafficOpsMaintenanceFilesDO instFileDO = BeanUtils.toBean(fileSaveReqVO, TrafficOpsMaintenanceFilesDO.class);
                instFileDO.setMaintenanceId(progressDO.getMaintenanceId());
                instFileDO.setProgressId(progressDO.getId());
                instFileDO.setRepairType(OpsConstants.REPAIR_TYPE_TWO);
                instFileList.add(instFileDO);
            }
            opsMaintenanceFilesMapper.insertBatch(instFileList);
        }

        // 保存耗材信息
        if (!CollectionUtils.isEmpty(saveReqVO.getOpsMaintainCmSaveReqVOList())) {
            List<TrafficOpsMaintainCmDO> instCmDOList = new ArrayList<>();
            for (AppTrafficOpsMaintainCmSaveReqVO opsMaintainCmSaveReqVO : saveReqVO.getOpsMaintainCmSaveReqVOList()) {
                TrafficOpsMaintainCmDO instCmDO = BeanUtils.toBean(opsMaintainCmSaveReqVO, TrafficOpsMaintainCmDO.class);
                instCmDO.setProgressId(progressDO.getId());
                instCmDOList.add(instCmDO);
            }
            opsMaintainCmMapper.insertBatch(instCmDOList);
        }

        // 保存组员信息
        if (!CollectionUtils.isEmpty(saveReqVO.getOpsMaintainTeamSaveReqVOList())) {
            List<TrafficOpsMaintenanceTeamDO> instTeamDOList = new ArrayList<>();
            for (TrafficOpsMaintenanceTeamSaveReqVO opsMaintainTeamSaveReqVO : saveReqVO.getOpsMaintainTeamSaveReqVOList()) {
                TrafficOpsMaintenanceTeamDO instTeamDO = BeanUtils.toBean(opsMaintainTeamSaveReqVO, TrafficOpsMaintenanceTeamDO.class);
                instTeamDO.setProgressId(progressDO.getId());
                instTeamDO.setMaintenanceId(saveReqVO.getId());
                instTeamDOList.add(instTeamDO);
            }
            opsMaintenanceTeamMapper.insertBatch(instTeamDOList);
        }

        TrafficOpsMaintenanceDO opsMaintenanceDO = new TrafficOpsMaintenanceDO();
        opsMaintenanceDO.setId(saveReqVO.getMaintenanceId());
        opsMaintenanceDO.setMaintenanceDate(LocalDateTimeUtil.of(new Date()));
        // 维修完成，更改状态
        if (OpsConstants.STRING_ONE.equals(saveReqVO.getIsFinish())) {
            opsMaintenanceDO.setStatus(OpsConstants.STATUS_CONFIRM);
            isUpdMain = true;
        }

        if (isUpdMain) {
            opsMaintenanceMapper.updateById(opsMaintenanceDO);
        }
    }

    @Override
    public void deleteOpsMaintenance(String id) {
        // 校验存在
        validateOpsMaintenanceExists(id);
        // 删除
        opsMaintenanceMapper.deleteById(id);
        //删除维保进度
        LambdaQueryWrapperX<TrafficOpsMaintainProgressDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(TrafficOpsMaintainProgressDO::getMaintenanceId,id);
        opsMaintainProgressMapper.delete(queryWrapperX);
        //删除维保材料
        LambdaQueryWrapperX<TrafficOpsMaintenanceFilesDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(TrafficOpsMaintenanceFilesDO::getMaintenanceId, id);
        opsMaintenanceFilesMapper.delete(queryWrapperX1);
    }


    @Override
    public TrafficOpsMaintenanceRespDpVO getOpsMaintenanceForDp(String status) {
        TrafficOpsMaintenanceRespDpVO respVO = new TrafficOpsMaintenanceRespDpVO();
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(TrafficOpsMaintenanceDO::getAffiliatedUnit, OpsConstants.STRING_ONE);
        if(StringUtils.isNotEmpty(status)){
            queryWrapperX.eqIfPresent(TrafficOpsMaintenanceDO::getStatus, status);
        }
        //注释下面这句，因为现在基本都是PC端报修
        //queryWrapperX.ne(TrafficOpsMaintenanceDO::getStatus, OpsConstants.STATUS_COMPLETED);
        queryWrapperX.orderByDesc(TrafficOpsMaintenanceDO::getCreateTime);
        List<TrafficOpsMaintenanceDO> listVO = opsMaintenanceMapper.selectList(queryWrapperX);
        respVO.setMaintenanceList(BeanUtils.toBean(listVO,TrafficOpsMaintenanceRespDpListVO.class));

        List<TrafficOpsMaintenanceDO> listVO1 = opsMaintenanceMapper.selectList(new LambdaQueryWrapperX<TrafficOpsMaintenanceDO>().eq(TrafficOpsMaintenanceDO::getAffiliatedUnit, OpsConstants.STRING_ONE));
        Map<String, Long> statusCountMap = listVO1.stream()
                .collect(Collectors.groupingBy(
                        TrafficOpsMaintenanceDO::getStatus,
                        Collectors.counting()
                ));

        Long countStatus1 = statusCountMap.getOrDefault("1", 0L);
        Long countStatus6 = statusCountMap.getOrDefault("6", 0L);
        respVO.setDpdCount(countStatus1 + "");
        respVO.setDjdCount(countStatus6 +"");
        return respVO;
    }

    @Override
    public void batchDispatchRepairOrder(TrafficOpsMaintenanceBatchDispatchSaveReqVO saveReqVO) {

        if (saveReqVO == null || CollectionUtil.isEmpty(saveReqVO.getMaintenanceIdList())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 校验存在
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in(TrafficOpsMaintenanceDO::getId, saveReqVO.getMaintenanceIdList());
        List<TrafficOpsMaintenanceDO> maintenanceDOList = opsMaintenanceMapper.selectList(queryWrapperX);
        if (CollectionUtil.isEmpty(maintenanceDOList) || maintenanceDOList.size() != saveReqVO.getMaintenanceIdList().size()) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        Map<String, TrafficOpsMaintenanceDO> maintenanceDOMap = maintenanceDOList.stream().collect(Collectors.toMap(TrafficOpsMaintenanceDO::getId, Function.identity(), (key1, key2) -> (key1)));


        AdminUserRespDTO currentUser = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        List<TrafficOpsMaintenanceDO> updList = new ArrayList<>();
        for (String maintenanceId : saveReqVO.getMaintenanceIdList()) {
            TrafficOpsMaintenanceDO maintenanceDO = maintenanceDOMap.get(maintenanceId);
            maintenanceDO.setDispatchUserId(currentUser.getId() + "");
            maintenanceDO.setDispatch(currentUser.getNickname());
            maintenanceDO.setDispatchTime(LocalDateTimeUtil.of(new Date()));
            maintenanceDO.setMaintenanceUserId(saveReqVO.getMaintenanceUserId());
            maintenanceDO.setMaintenancePersonnel(saveReqVO.getMaintenancePersonnel());
            maintenanceDO.setMaintenancePhone(saveReqVO.getMaintenancePhone());
            maintenanceDO.setStatus(OpsConstants.STATUS_ACCEPT_ORDER);
            updList.add(maintenanceDO);
        }

        if (!updList.isEmpty())  {
            opsMaintenanceMapper.updateBatch(updList);

            // 保存全流程信息
            TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
            opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_DISPATCH);
            opsMaintainFlowRecordSaveReqVO.setUserId(currentUser.getId());
            opsMaintainFlowRecordSaveReqVO.setOperateStatus(OpsConstants.STATUS_ACCEPT_ORDER);
            opsMaintainFlowRecordSaveReqVO.setOperateResult("PC批量派单[" + saveReqVO.getMaintenancePersonnel() + "]");

            List<String> maintainanceIdList = updList.stream().map(TrafficOpsMaintenanceDO::getId).collect(Collectors.toList());
            opsMaintainFlowRecordExtService.batchSaveMaintainFlowRecord(maintainanceIdList, opsMaintainFlowRecordSaveReqVO);
        }
    }

    @Override
    public void confirmRepairOrder(TrafficOpsMaintenanceConfirmSaveReqVO confirmSaveReqVO, Long userId) {
        if (confirmSaveReqVO == null || StringUtils.isBlank(confirmSaveReqVO.getId())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 校验存在
        TrafficOpsMaintenanceDO maintenanceDO = opsMaintenanceMapper.selectById(confirmSaveReqVO.getId());
        if (maintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }
        AdminUserRespDTO currentUser = adminUserApi.getUser(userId);
        maintenanceDO.setConfirmorUserId(currentUser.getId() + "");
        maintenanceDO.setConfirmor(currentUser.getNickname());
        maintenanceDO.setConfirmDate(LocalDateTimeUtil.of(new Date()));
//        maintenanceDO.setStatus(OpsConstants.STATUS_COMPLETED);
        maintenanceDO.setStatus(confirmSaveReqVO.getStatus());
        maintenanceDO.setRemark(confirmSaveReqVO.getRemark());
        opsMaintenanceMapper.updateById(maintenanceDO);

        if (StringUtils.isNotBlank(maintenanceDO.getEquId())
                && StringUtils.isNotBlank(maintenanceDO.getEquType())
                && OpsConstants.STATUS_COMPLETED.equals(maintenanceDO.getStatus())) {
            // 更新设备状态
            updateEquStatus(maintenanceDO.getEquId(),maintenanceDO.getEquType(),OpsConstants.EQU_STATUS_ZC, maintenanceDO.getAffiliatedUnit());
        }

        // 保存维修流程
        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(confirmSaveReqVO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_CONFIRM);
        opsMaintainFlowRecordSaveReqVO.setUserId(currentUser.getId());
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(confirmSaveReqVO.getStatus());
        String operateResult = "确认-未修复";
        if (OpsConstants.STATUS_COMPLETED.equals(maintenanceDO.getStatus())) {
            operateResult = "确认-已修复";
        }
        opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
        opsMaintainFlowRecordSaveReqVO.setRemark(confirmSaveReqVO.getRemark());
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);
    }

    @Override
    public List<UserRespVO> queryAllUserInfoList(Long deptId) {
        LambdaQueryWrapperX<AdminUserDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.inIfPresent(AdminUserDO::getDeptId, getDeptCondition(deptId));

        List<AdminUserDO> userList = adminUserMapper.selectList(queryWrapperX);
        return BeanUtils.toBean(userList, UserRespVO.class);
    }

    @Override
    public Long queryEquipMaintenanceCnt(String equId) {
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(TrafficOpsMaintenanceDO::getEquId, equId);
        return opsMaintenanceMapper.selectCount(queryWrapperX);
    }

    @Override
    public void rejectRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        if (saveReqVO == null || StringUtils.isBlank(saveReqVO.getId())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 校验存在
        TrafficOpsMaintenanceDO maintenanceDO = opsMaintenanceMapper.selectById(saveReqVO.getId());
        if (maintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        if (OpsConstants.STATUS_COMPLETED.equals(maintenanceDO.getStatus())) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_ALLOW_CANCEL);
        }

        LambdaUpdateWrapper<TrafficOpsMaintenanceDO> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.set(TrafficOpsMaintenanceDO::getMaintenanceUserId, null);
//        updateWrapper.set(TrafficOpsMaintenanceDO::getMaintenancePersonnel, null);
//        updateWrapper.set(TrafficOpsMaintenanceDO::getMaintenancePhone, null);
//        updateWrapper.set(TrafficOpsMaintenanceDO::getMaintenanceDate, null);
        updateWrapper.set(TrafficOpsMaintenanceDO::getRejectUserId, saveReqVO.getRejectUserId());
        updateWrapper.set(TrafficOpsMaintenanceDO::getRejectPersonnel, saveReqVO.getRejectPersonnel());
        updateWrapper.set(TrafficOpsMaintenanceDO::getRejectTime, LocalDateTimeUtil.of(new Date()));
        updateWrapper.set(TrafficOpsMaintenanceDO::getRejectReason, saveReqVO.getRejectReason());
        updateWrapper.set(TrafficOpsMaintenanceDO::getStatus, OpsConstants.STATUS_REJECT);
        updateWrapper.eq(TrafficOpsMaintenanceDO::getId, maintenanceDO.getId());
        opsMaintenanceMapper.update(updateWrapper);

        // 保存全流程
        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(maintenanceDO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_REJECT);
        opsMaintainFlowRecordSaveReqVO.setUserId(Long.parseLong(saveReqVO.getRejectUserId()));
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(OpsConstants.STATUS_REJECT);
        String operateResult = "工单驳回";
        opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);

//        //通知上报人
//        TrafficOpsRegisterDO reportRegisterDO = getOpsRegisterByUserId(maintenanceDO.getReportUserId());
//        if (reportRegisterDO == null || StringUtils.isBlank(reportRegisterDO.getOpenid())) {
//            throw exception(OpsErrorCodeConstants.OPS_REGISTER_NOT_EXISTS);
//        }
//        Map<String, TemplateData> data = new HashMap<>();
//        data.put("thing5" , new TemplateData().setValue(saveReqVO.getCancelReason()));
//        data.put("time4" , new TemplateData().setValue(DateUtil.format(new Date(), "yyyy/MM/dd HH:mm:ss")));
//
//        WechatTemplate template = new WechatTemplate();
//        template.setTemplate_id(WechatConfig.getMgtTodoTempId());
//        template.setData(data);
//        template.setTouser(reportRegisterDO.getOpenid());
//        try {
//            SendTemplateMessage.sendTemplateMessage(WeixinUtil.getAccessToken().getToken() , template);
//        } catch (Exception e) {
//            log.error("模板消息发送失败！" , e);
//        }
    }

    @Override
    public void mountOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        if (saveReqVO == null || StringUtils.isBlank(saveReqVO.getId())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 校验存在
        TrafficOpsMaintenanceDO maintenanceDO = opsMaintenanceMapper.selectById(saveReqVO.getId());
        if (maintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        // 状态改为暂时无法修复
        maintenanceDO.setStatus(OpsConstants.STATUS_TEMPORARILY_UNABLE_REPAIR);
        opsMaintenanceMapper.updateById(maintenanceDO);

        // 保存全流程
        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(maintenanceDO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_MOUNT);
        opsMaintainFlowRecordSaveReqVO.setUserId(WebFrameworkUtils.getLoginUserId());
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(OpsConstants.STATUS_TEMPORARILY_UNABLE_REPAIR);
        String operateResult = "挂单";
        opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
        opsMaintainFlowRecordSaveReqVO.setRemark(saveReqVO.getRemark());
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);
    }

    /**
     * 获得部门条件：查询指定部门的子部门编号们，包括自身
     * @param deptId 部门编号
     * @return 部门编号集合
     */
    private Set<Long> getDeptCondition(Long deptId) {
        if (deptId == null) {
            return Collections.emptySet();
        }
        Set<Long> deptIds = convertSet(deptService.getChildDeptList(deptId), DeptDO::getId);
        deptIds.add(deptId); // 包括自身
        return deptIds;
    }

    @Override
    public PageResult<TrafficOpsMaintenanceNearbyRespVO> getNearbyOrdersPage(Page<?> page, String problem, String lng, String lat) {
        IPage<TrafficOpsMaintenanceNearbyRespVO> resultDTOIPage = opsMaintenanceExtMapper.getNearbyOrdersPage(page,problem,lng,lat);
        if (resultDTOIPage == null || CollectionUtils.isEmpty(resultDTOIPage.getRecords())) {
            return new PageResult<>();
        }
        return new PageResult<>(resultDTOIPage.getRecords(), page.getTotal());
    }

    @Override
    public List<TrafficOpsMaintainFlowRecordRespVO> queryMaintainFlowRecord(String maintainId) {
        return BeanUtils.toBean(opsMaintainFlowRecordExtService.getOpsMaintainFlowRecord(maintainId), TrafficOpsMaintainFlowRecordRespVO.class);
    }

    private void fillMaintenanceProp(TrafficOpsMaintenanceDO maintenanceDO) {
        if (StringUtils.isBlank(maintenanceDO.getEquId())
                || maintenanceDO.getRepairCnt() != null
                || StringUtils.isNotBlank(maintenanceDO.getOnlineRate())) {
            return;
        }
        // 根据设备ID查询微信次数
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> repariCntLambda = new LambdaQueryWrapperX<>();
        repariCntLambda.eq(TrafficOpsMaintenanceDO::getEquId, maintenanceDO.getEquId());
        Long repairCnt = opsMaintenanceMapper.selectCount(repariCntLambda);
        maintenanceDO.setRepairCnt(repairCnt.intValue());

        // 查询设备当前在线率
        ViewOpsMonitorResultsDO viewOpsMonitorResultsDO = opsMonitorResultsMapper.selectById(maintenanceDO.getEquId());
        if (viewOpsMonitorResultsDO != null) {
            maintenanceDO.setOnlineRate(NumberUtil.formatPercent(viewOpsMonitorResultsDO.getHealthRate(), 2));
        }
    }


    @Override
    public String queryOrderByIp(String equId, String ipAddr,String type) {
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(TrafficOpsMaintenanceDO::getAffiliatedUnit, type)
                .and(wrapper -> wrapper.eq(TrafficOpsMaintenanceDO::getStatus, OpsConstants.STATUS_PENDING_ORDERS)
                        .or().eq(TrafficOpsMaintenanceDO::getStatus, OpsConstants.STATUS_ACCEPT_ORDER)
                        .or().eq(TrafficOpsMaintenanceDO::getStatus, OpsConstants.STATUS_IN_MAINTENANCE))
                .and(wrapper -> wrapper.eq(TrafficOpsMaintenanceDO::getEquId, equId)
                        .or().like(TrafficOpsMaintenanceDO::getProblem, ipAddr));
        List<TrafficOpsMaintenanceDO> maintenanceList = opsMaintenanceMapper.selectList(queryWrapperX);
        if(!maintenanceList.isEmpty()){
            return maintenanceList.get(0).getId();
        }
        return null;
    }

    @Override
    public String confirmRepairResult(PublicEquipOnOffCheckResultSaveReqVO saveReqVO) {
        // 校验存在
        TrafficOpsMaintenanceDO maintenanceDO = opsMaintenanceMapper.selectById(saveReqVO.getMaintenanceId());
        if (maintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        PublicEquipOnOffCheckResultDO publicEquipOnOffCheckResultDO = BeanUtils.toBean(saveReqVO, PublicEquipOnOffCheckResultDO.class);
        publicEquipOnOffCheckResultDO.setAffiliatedUnit(maintenanceDO.getAffiliatedUnit());
        if (StringUtils.isNotBlank(saveReqVO.getCheckUserId())) {
            // 查询用户信息
            AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.parseLong(saveReqVO.getCheckUserId()));
            if (adminUserRespDTO != null) {
                publicEquipOnOffCheckResultDO.setCheckUserPersonnel(adminUserRespDTO.getNickname());
            }
        }
        publicEquipOnOffCheckResultDO.setEquId(maintenanceDO.getEquId());
        if (StringUtils.isBlank(saveReqVO.getCheckType())) {
            saveReqVO.setCheckType(OpsConstants.STRING_ONE);
        }
        publicEquipOnOffCheckResultDO.setCheckType(saveReqVO.getCheckType());
        publicEquipOnOffCheckResultDO.setIpAddr(maintenanceDO.getIpAddr());
        publicEquipOnOffCheckResultDO.setEquType(maintenanceDO.getEquType());
        publicEquipOnOffCheckResultDO.setIsRepairOrder(OpsConstants.STRING_YES);
        publicEquipOnOffCheckResultMapper.insert(publicEquipOnOffCheckResultDO);

        // 互联网推送视频网做通断检测和视频截图
        boundaryMsgProducerObjectProvider.ifAvailable(boundaryMsgProducer -> boundaryMsgProducer.sendMessageFromInternetToVideo("POLICE_EQU_CONFIRM", "HEALTH_CHECK", JSONUtil.toJsonStr(publicEquipOnOffCheckResultDO)));

        return publicEquipOnOffCheckResultDO.getId();
    }

    @Override
    public String saveOpsMaintenanceUnfixableInfo(TrafficOpsMaintenanceUnfixableInfoExtSaveReqVO saveReqVO) {
        if (saveReqVO == null || StringUtils.isBlank(saveReqVO.getId())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 查询是否存在无法修复记录
        TrafficOpsMaintenanceUnfixableInfoDO unfixableInfoDO = trafficOpsMaintenanceUnfixableInfoMapper.selectById(saveReqVO.getId());
        if (unfixableInfoDO == null) { // 新增维修记录
            TrafficOpsMaintenanceUnfixableInfoDO insertDO = BeanUtils.toBean(saveReqVO, TrafficOpsMaintenanceUnfixableInfoDO.class);
            trafficOpsMaintenanceUnfixableInfoMapper.insert(insertDO);
        } else {
            LambdaUpdateWrapper<TrafficOpsMaintenanceUnfixableInfoDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.set(TrafficOpsMaintenanceUnfixableInfoDO::getEstimatedRepairDate, saveReqVO.getEstimatedRepairDate());
            lambdaUpdateWrapper.eq(TrafficOpsMaintenanceUnfixableInfoDO::getId, saveReqVO.getId());
            trafficOpsMaintenanceUnfixableInfoMapper.update(lambdaUpdateWrapper);
        }

        TrafficOpsMaintenanceUnfixableFollowDO followDO = BeanUtils.toBean(saveReqVO, TrafficOpsMaintenanceUnfixableFollowDO.class);
        followDO.setId(null);
        followDO.setUnfixableId(saveReqVO.getId());
        followDO.setCoordinationDate(DateUtil.toLocalDateTime(new Date()));
        if (StringUtils.isNotBlank(saveReqVO.getUnfixableReason()) && StringUtils.isBlank(saveReqVO.getCoordinationContent())) {
            followDO.setCoordinationContent(saveReqVO.getUnfixableReason());
        }
        trafficOpsMaintenanceUnfixableFollowMapper.insert(followDO);

        // 保存材料
        if (!CollectionUtils.isEmpty(saveReqVO.getFileInfoList())) {
            List<TrafficOpsMaintenanceFilesDO> instFileList = new ArrayList<>();
            for (TrafficOpsMaintenanceFileDTO fileSaveReqVO : saveReqVO.getFileInfoList()) {
                TrafficOpsMaintenanceFilesDO instFileDO = BeanUtils.toBean(fileSaveReqVO, TrafficOpsMaintenanceFilesDO.class);
                instFileDO.setMaintenanceId(saveReqVO.getId());
                instFileDO.setProgressId(followDO.getId());
                instFileDO.setRepairType(OpsConstants.REPAIR_TYPE_THREE);
                instFileList.add(instFileDO);
            }
            opsMaintenanceFilesMapper.insertBatch(instFileList);
        }


        return saveReqVO.getId();
    }

    @Override
    public TrafficOpsMaintenanceUnfixableInfoExtRespVO queryOpsMaintenanceUnfixableInfo(String id) {
        // 查询是否存在无法修复记录
        TrafficOpsMaintenanceUnfixableInfoDO unfixableInfoDO = trafficOpsMaintenanceUnfixableInfoMapper.selectById(id);
        if (unfixableInfoDO == null) { // 新增维修记录
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_UNFIXABLE_INFO_NOT_EXISTS);
        }

        TrafficOpsMaintenanceUnfixableInfoExtRespVO unfixableInfoExtRespVO = BeanUtils.toBean(unfixableInfoDO, TrafficOpsMaintenanceUnfixableInfoExtRespVO.class);
        // 查询跟进记录
        LambdaQueryWrapperX<TrafficOpsMaintenanceUnfixableFollowDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficOpsMaintenanceUnfixableFollowDO::getUnfixableId, id);
        lambdaQueryWrapperX.orderByDesc(TrafficOpsMaintenanceUnfixableFollowDO::getCreateTime);
        List<TrafficOpsMaintenanceUnfixableFollowDO> unfixableFollowDOList = trafficOpsMaintenanceUnfixableFollowMapper.selectList(lambdaQueryWrapperX);
        List<TrafficOpsMaintenanceUnfixableFollowRespVO> followRespVOList = BeanUtils.toBean(unfixableFollowDOList, TrafficOpsMaintenanceUnfixableFollowRespVO.class);
        Optional.ofNullable(followRespVOList).orElse(new ArrayList<>()).forEach(respVO -> respVO.setFileDTOList(getOpsMaintenanceFiles(id, respVO.getId(), OpsConstants.REPAIR_TYPE_THREE)));

        unfixableInfoExtRespVO.setFollowRespVOList(followRespVOList);


        return unfixableInfoExtRespVO;
    }

    @Override
    public void closeRepairOrder(TrafficOpsMaintenanceSaveReqVO saveReqVO) {
        TrafficOpsMaintenanceDO opsMaintenanceDO = opsMaintenanceMapper.selectById(saveReqVO.getId());
        if (opsMaintenanceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_EXISTS);
        }

        if (!OpsConstants.STATUS_ACCEPT_ORDER.equals(opsMaintenanceDO.getStatus())) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_ALLOW_ACCEPT);
        }

        if (!saveReqVO.getMaintenanceUserId().equals(opsMaintenanceDO.getMaintenanceUserId())) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_NOT_ALLOW_USER);
        }
        opsMaintenanceDO.setStatus(OpsConstants.STATUS_COMPLETED);


        AdminUserRespDTO currentUser = adminUserApi.getUser(Long.parseLong(saveReqVO.getMaintenanceUserId()));
        opsMaintenanceDO.setConfirmorUserId(currentUser.getId() + "");
        opsMaintenanceDO.setConfirmor(currentUser.getNickname());
        opsMaintenanceDO.setConfirmDate(LocalDateTimeUtil.of(new Date()));

        String operateResult = currentUser.getNickname() + "进行通断检测，设备恢复正常，关单";
//        maintenanceDO.setStatus(OpsConstants.STATUS_COMPLETED);
        opsMaintenanceDO.setRemark(operateResult);
        opsMaintenanceMapper.updateById(opsMaintenanceDO);

        if (StringUtils.isNotBlank(opsMaintenanceDO.getEquId())
                && StringUtils.isNotBlank(opsMaintenanceDO.getEquType())
                && OpsConstants.STATUS_COMPLETED.equals(opsMaintenanceDO.getStatus())) {
            // 更新设备状态
            updateEquStatus(opsMaintenanceDO.getEquId(),opsMaintenanceDO.getEquType(),OpsConstants.EQU_STATUS_ZC, opsMaintenanceDO.getAffiliatedUnit());
        }

        // 保存维修流程
        TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
        opsMaintainFlowRecordSaveReqVO.setMaintenanceId(opsMaintenanceDO.getId());
        opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_CONFIRM);
        opsMaintainFlowRecordSaveReqVO.setUserId(currentUser.getId());
        opsMaintainFlowRecordSaveReqVO.setOperateStatus(opsMaintenanceDO.getStatus());
        opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
        opsMaintainFlowRecordSaveReqVO.setRemark(opsMaintenanceDO.getRemark());
        opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);
    }
}