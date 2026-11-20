package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssueDetailMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssueHistoryMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssueRecordMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsPoliceEquipmentMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.TrafficOpsIssueRecordPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsPoliceEquipmentPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsPoliceEquipmentRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsPoliceEquipmentSaveReqVO;
import com.holdtime.ops.module.service.TrafficOpsPoliceEquipmentExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 警用装备资产信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsPoliceEquipmentExtServiceImpl implements TrafficOpsPoliceEquipmentExtService {

    @Resource
    private TrafficOpsPoliceEquipmentMapper opsPoliceEquipmentMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;
    @Resource
    private TrafficOpsIssueDetailMapper opsIssueDetailMapper;
    @Resource
    private TrafficOpsIssueRecordMapper opsIssueRecordMapper;
    @Resource
    private TrafficOpsIssueHistoryMapper opsIssueHistoryMapper;
    @Resource
    private DeptService deptService;

    @Override
    public String saveOpsPoliceEquipment(TrafficOpsPoliceEquipmentSaveReqVO saveReqVO) {
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 1.保存报修申请信息
        TrafficOpsPoliceEquipmentDO opsPoliceEquipmentDO = BeanUtils.toBean(saveReqVO, TrafficOpsPoliceEquipmentDO.class);

        if (StringUtils.isBlank(opsPoliceEquipmentDO.getId())) { // 新增报修申请
            if (StringUtils.isNotBlank(saveReqVO.getRecordBh())) {
                // 查询用户信息
                AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.parseLong(saveReqVO.getRecordBh()));
                if (adminUserRespDTO == null) {
                    throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
                }
                opsPoliceEquipmentDO.setRecorder(adminUserRespDTO.getNickname());
                opsPoliceEquipmentDO.setRecordDeptBh(adminUserRespDTO.getDeptId() + "");

                DeptRespDTO deptRespDTO = deptApi.getDept(adminUserRespDTO.getDeptId());
                if (deptRespDTO != null) {
                    opsPoliceEquipmentDO.setRecordDept(deptRespDTO.getName());
                }
            }

            opsPoliceEquipmentMapper.insert(opsPoliceEquipmentDO);
        } else { // 修改报修申请信息
            // 校验存在
            validateOpsPoliceEquipmentExists(opsPoliceEquipmentDO.getId());

            opsPoliceEquipmentMapper.updateById(opsPoliceEquipmentDO);
        }
        return opsPoliceEquipmentDO.getId();
    }

    private void validateOpsPoliceEquipmentExists(String id) {
        if (opsPoliceEquipmentMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_POLICE_EQUIPMENT_NOT_EXISTS);
        }
    }

    @Override
    public List<TrafficOpsPoliceEquipmentRespVO> queryIssuedPoliceEquipment(String deptId, String keyword) {
        LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.isNotNull(TrafficOpsPoliceEquipmentDO::getUserBh);
        lambdaQueryWrapperX.eqIfPresent(TrafficOpsPoliceEquipmentDO::getUseDeptBh, deptId);
//        lambdaQueryWrapperX.likeIfPresent(TrafficOpsPoliceEquipmentDO::getEncryptionCard, keyword);
        lambdaQueryWrapperX.likeIfPresent(TrafficOpsPoliceEquipmentDO::getPsn, keyword);
        return BeanUtils.toBean(opsPoliceEquipmentMapper.selectList(lambdaQueryWrapperX), TrafficOpsPoliceEquipmentRespVO.class);
    }

    @Override
    public List<String> autocompletePoliceEquipInfoList(String keywork, String keyType) {
        LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (OpsConstants.STRING_ONE.equals(keyType)) {
            lambdaQueryWrapperX.select(TrafficOpsPoliceEquipmentDO::getManufacturer);
            lambdaQueryWrapperX.likeIfPresent(TrafficOpsPoliceEquipmentDO::getManufacturer, keywork);
            lambdaQueryWrapperX.groupBy(TrafficOpsPoliceEquipmentDO::getManufacturer);
        } else if (OpsConstants.STRING_TWO.equals(keyType)) {
            lambdaQueryWrapperX.select(TrafficOpsPoliceEquipmentDO::getSupplier);
            lambdaQueryWrapperX.likeIfPresent(TrafficOpsPoliceEquipmentDO::getSupplier, keywork);
            lambdaQueryWrapperX.groupBy(TrafficOpsPoliceEquipmentDO::getSupplier);
        } else if (OpsConstants.STRING_THREE.equals(keyType)) {
            lambdaQueryWrapperX.select(TrafficOpsPoliceEquipmentDO::getModel);
            lambdaQueryWrapperX.likeIfPresent(TrafficOpsPoliceEquipmentDO::getModel, keywork);
            lambdaQueryWrapperX.groupBy(TrafficOpsPoliceEquipmentDO::getModel);
        }


        List<Map<String, Object>> list = opsPoliceEquipmentMapper.selectMaps(lambdaQueryWrapperX);

        return Optional.ofNullable(list).orElse(new ArrayList<>()).stream().map(map -> {
            if (OpsConstants.STRING_ONE.equals(keyType)) {
                return map.get("manufacturer") + "";
            } else if (OpsConstants.STRING_TWO.equals(keyType)) {
                return map.get("supplier") + "";
            }
            return map.get("model") + "";
        }).collect(Collectors.toList());
    }

    @Override
    public String recycleOpsPoliceEquipment(String id) {
        if (StringUtils.isBlank(id)) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }
        // 查询设备信息
        TrafficOpsPoliceEquipmentDO equipment = Optional.ofNullable(opsPoliceEquipmentMapper.selectById(id))
                .orElseThrow(() -> exception(OpsErrorCodeConstants.OPS_POLICE_EQUIPMENT_NOT_EXISTS));
        //清空设备表的领用信息
        opsPoliceEquipmentMapper.update(null,
                new LambdaUpdateWrapper<TrafficOpsPoliceEquipmentDO>()
                        .set(TrafficOpsPoliceEquipmentDO::getUseDeptBh, null)
                        .set(TrafficOpsPoliceEquipmentDO::getUseDept, null)
                        .set(TrafficOpsPoliceEquipmentDO::getUserBh, null)
                        .set(TrafficOpsPoliceEquipmentDO::getUserName, null)
                        .eq(TrafficOpsPoliceEquipmentDO::getId, id));

        processIssueRecord(id, equipment);
        return equipment.getId();

    }

    /**
     * 处理设备领用记录
     */
    private void processIssueRecord(String equipmentId, TrafficOpsPoliceEquipmentDO equipment) {
        // 1. 查询领用详情
        TrafficOpsIssueDetailDO issueDetail = opsIssueDetailMapper.selectOne(
                new LambdaQueryWrapper<TrafficOpsIssueDetailDO>()
                        .eq(TrafficOpsIssueDetailDO::getEquId, equipmentId)
                        .last("LIMIT 1"));

        if (issueDetail == null) {
            return;
        }

        // 2. 查询领用记录
        TrafficOpsIssueRecordDO issueRecord = Optional.ofNullable(
                        opsIssueRecordMapper.selectById(issueDetail.getIssueBh()))
                .orElseThrow(() -> exception(OpsErrorCodeConstants.OPS_ISSUE_RECORD_NOT_EXISTS));

        // 3. 保存回收历史记录
        TrafficOpsIssueHistoryDO historyRecord = buildIssueHistory(equipment, issueRecord, equipmentId);
        opsIssueHistoryMapper.insert(historyRecord);

        // 4. 删除领用详情
        opsIssueDetailMapper.delete(new LambdaQueryWrapper<TrafficOpsIssueDetailDO>()
                .eq(TrafficOpsIssueDetailDO::getEquId, equipmentId)
                .eq(TrafficOpsIssueDetailDO::getIssueBh, issueDetail.getIssueBh()));
    }

    /**
     * 构建回收历史记录
     */
    private TrafficOpsIssueHistoryDO buildIssueHistory(
            TrafficOpsPoliceEquipmentDO equipment,
            TrafficOpsIssueRecordDO issueRecord,
            String equipmentId) {

        return TrafficOpsIssueHistoryDO.builder()
                .equId(equipmentId)
                .issueUserBh(issueRecord.getUserBh())
                .issueUserName(issueRecord.getUserName())
                .issueTime(issueRecord.getCreateTime())
                .recycleTime(DateUtil.toLocalDateTime(new Date()))
                .recycleUserBh(String.valueOf(SecurityFrameworkUtils.getLoginUserId()))
                .recycleUserName(SecurityFrameworkUtils.getLoginUserNickname())
                .useDeptBh(equipment.getUseDeptBh())
                .useDept(equipment.getUseDept())
                .userName(equipment.getUserName())
                .userBh(equipment.getUserBh())
                .psn(equipment.getPsn())
                .build();
    }

    @Override
    public PageResult<TrafficOpsPoliceEquipmentDO> getOpsPoliceEquipmentPage(TrafficOpsPoliceEquipmentPageReqVO reqVO){
        LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO>()
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getEncryptionCard, reqVO.getEncryptionCard())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getPsn, reqVO.getPsn())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getBrand, reqVO.getBrand())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getModel, reqVO.getModel())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getBattery, reqVO.getBattery())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getVersion, reqVO.getVersion())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecordBh, reqVO.getRecordBh())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecorder, reqVO.getRecorder())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecordDeptBh, reqVO.getRecordDeptBh())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getRecordDept, reqVO.getRecordDept())
                //.eqIfPresent(TrafficOpsPoliceEquipmentDO::getUseDeptBh, reqVO.getUseDeptBh())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getUseDept, reqVO.getUseDept())
                .eqIfPresent(TrafficOpsPoliceEquipmentDO::getUserBh, reqVO.getUserBh())
                .likeIfPresent(TrafficOpsPoliceEquipmentDO::getUserName, reqVO.getUserName())
                .betweenIfPresent(TrafficOpsPoliceEquipmentDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsPoliceEquipmentDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsPoliceEquipmentDO::getId);

                Set<Long> deptIds = convertSet(deptService.getChildDeptList(SecurityFrameworkUtils.getLoginUserDeptId()), DeptDO::getId);
                deptIds.add(SecurityFrameworkUtils.getLoginUserDeptId()); // 包括自身
                lambdaQueryWrapperX.and(wrapper -> {
                    wrapper.in(TrafficOpsPoliceEquipmentDO::getUseDeptBh,deptIds)
                            .or().isNull(TrafficOpsPoliceEquipmentDO::getUseDeptBh);
                });

                 return opsPoliceEquipmentMapper.selectPage(reqVO,lambdaQueryWrapperX);
    }
}