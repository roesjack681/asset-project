package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceExtService;
import com.holdtime.ops.module.service.TrafficOpsMemberExtService;
import com.holdtime.ops.module.service.TrafficOpsRegisterExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class TrafficOpsMemberExtServiceImpl implements TrafficOpsMemberExtService {
    @Resource
    private TrafficOpsMemberMapper trafficOpsMemberMapper;
    @Resource
    private TrafficOpsAccessoryEquipmentMapper trafficOpsAccessoryEquipmentMapper;
    @Resource
    private TrafficOpsQrcodeMapper trafficOpsQrcodeMapper;
    @Resource
    private TrafficOpsElectricPoliceMapper trafficOpsElectricPoliceMapper;
    @Resource
    private TrafficOpsRegisterExtService opsRegisterExtService;
//    @Resource
//    private TrafficOpsRegisterMapper opsRegisterMapper;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private TrafficOpsMaintenanceExtService opsMaintenanceExtService;

    @Override
    public String saveOpsMemberInfo(TrafficOpsMemberExtSaveReqVO saveReqVO) {
        // 保存生成记录
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }
        // 保存杆件信息
        TrafficOpsMemberDO opsMemberDO = BeanUtils.toBean(saveReqVO, TrafficOpsMemberDO.class);
        if (StringUtils.isNotBlank(saveReqVO.getCreator())) {
            AppTrafficOpsRegisterRespVO register = opsRegisterExtService.getOpsRegister(saveReqVO.getCreator());
            if (register != null) {
                if (StringUtils.isEmpty(opsMemberDO.getId())) {
                    opsMemberDO.setCreator(register.getUserId());
                } else {
                    opsMemberDO.setUpdater(register.getUserId());
                }

            }
        }

        if (StringUtils.isEmpty(opsMemberDO.getId())) { // 新增
            trafficOpsMemberMapper.insert(opsMemberDO);
        } else {
            validateOpsMemberExists(opsMemberDO.getId());

            trafficOpsMemberMapper.updateById(opsMemberDO);

            // 软删杆件附属信息
            LambdaQueryWrapper<TrafficOpsAccessoryEquipmentDO> delRegionPolygonWrapper = new LambdaQueryWrapper<>();
            delRegionPolygonWrapper.eq(TrafficOpsAccessoryEquipmentDO::getMemberId, opsMemberDO.getId());
            trafficOpsAccessoryEquipmentMapper.delete(delRegionPolygonWrapper);
        }

        // 保存杆件附属信息
        List<TrafficOpsAccessoryEquipmentDO> equipmentDOList = Optional.ofNullable(saveReqVO.getAccessoryEquipmentList()).orElse(new ArrayList<>()).stream().map(equipmentDO -> {
            TrafficOpsAccessoryEquipmentDO equipment = BeanUtils.toBean(equipmentDO, TrafficOpsAccessoryEquipmentDO.class);
            equipment.setMemberId(opsMemberDO.getId());
            return equipment;
        }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(equipmentDOList)) {
            trafficOpsAccessoryEquipmentMapper.insertBatch(equipmentDOList);
        }

        return opsMemberDO.getId();
    }

    @Override
    public TrafficOpsMemberExtRespVO getOpsMember(String memberId) {
        TrafficOpsMemberDO opsMember = trafficOpsMemberMapper.selectById(memberId);
        TrafficOpsMemberExtRespVO respVO = BeanUtils.toBean(opsMember, TrafficOpsMemberExtRespVO.class);
        if (respVO != null) {
            // 查询QrCode
            if (StringUtils.isNotBlank(respVO.getEquNumber())) {
                TrafficOpsQrcodeDO opsQrcodeDO = trafficOpsQrcodeMapper.selectById(respVO.getEquNumber());
                if (opsQrcodeDO != null) {
                    respVO.setQrCodeUrl(opsQrcodeDO.getQrcodeFile());
                }
            }

            // 查询维护人
            if (StringUtils.isNotBlank(opsMember.getCreator())) {
                AdminUserDO adminUserDO = adminUserMapper.selectById(opsMember.getCreator());
                if (adminUserDO != null)  {
                    respVO.setCreator(adminUserDO.getNickname());
                }
            }

            // 查询电警信息
            LambdaQueryWrapper<TrafficOpsElectricPoliceDO> electricPoliceWrapper = new LambdaQueryWrapperX<>();
            electricPoliceWrapper.eq(TrafficOpsElectricPoliceDO::getMemberBh, respVO.getId());
            List<TrafficOpsElectricPoliceDO> electricPoliceList = trafficOpsElectricPoliceMapper.selectList(electricPoliceWrapper);
            respVO.setElectricPoliceVOList(BeanUtils.toBean(electricPoliceList, TrafficOpsElectricPoliceRespVO.class));

            // 查询附属信息
            LambdaQueryWrapperX<TrafficOpsAccessoryEquipmentDO> wrapper = new LambdaQueryWrapperX<>();
            wrapper.eq(TrafficOpsAccessoryEquipmentDO::getMemberId, respVO.getId());
            List<TrafficOpsAccessoryEquipmentDO> list = trafficOpsAccessoryEquipmentMapper.selectList(wrapper);
            respVO.setAccessoryEquipmentList(BeanUtils.toBean(list, TrafficOpsAccessoryEquipmentRespVO.class));
        }
        return respVO;
    }

    @Override
    public PageResult<TrafficOpsMemberExtRespVO> getOpsMemberPage(TrafficOpsMemberPageReqVO pageReqVO, String isPaging) {
        LambdaQueryWrapperX<TrafficOpsMemberDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsMemberDO>()
                .likeIfPresent(TrafficOpsMemberDO::getEquName, pageReqVO.getEquName())
                .eqIfPresent(TrafficOpsMemberDO::getEquNumber, pageReqVO.getEquNumber())
                .eqIfPresent(TrafficOpsMemberDO::getEquType, pageReqVO.getEquType())
                .eqIfPresent(TrafficOpsMemberDO::getJibLength, pageReqVO.getJibLength())
                .likeIfPresent(TrafficOpsMemberDO::getRoadName, pageReqVO.getRoadName())
                .eqIfPresent(TrafficOpsMemberDO::getMemberType, pageReqVO.getMemberType())
                .eqIfPresent(TrafficOpsMemberDO::getLng, pageReqVO.getLng())
                .eqIfPresent(TrafficOpsMemberDO::getLat, pageReqVO.getLat())
                .betweenIfPresent(TrafficOpsMemberDO::getCreateTime, pageReqVO.getCreateTime())
                .eqIfPresent(TrafficOpsMemberDO::getAdress, pageReqVO.getAdress())
                .betweenIfPresent(TrafficOpsMemberDO::getInstallDate, pageReqVO.getInstallDate())
                .eqIfPresent(TrafficOpsMemberDO::getManufacturer, pageReqVO.getManufacturer())
                .betweenIfPresent(TrafficOpsMemberDO::getDeletedTime, pageReqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsMemberDO::getUnit, pageReqVO.getUnit())
                .eqIfPresent(TrafficOpsMemberDO::getHight, pageReqVO.getHight())
                .eqIfPresent(TrafficOpsMemberDO::getBarLength, pageReqVO.getBarLength())
                .likeIfPresent(TrafficOpsMemberDO::getDominion, pageReqVO.getDominion())
                .eqIfPresent(TrafficOpsMemberDO::getStatus, pageReqVO.getStatus())
                .orderByDesc(TrafficOpsMemberDO::getId);

        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    opsDOLambdaQueryWrapperX.like(TrafficOpsMemberDO::getIntersectionName, roadName);
                }
            }
        }

        if(!StringUtil.isEmpty(isPaging)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }

        if (StringUtils.isNotBlank(pageReqVO.getCreator())) {
            opsDOLambdaQueryWrapperX.exists("select 1 from system_users u where traffic_ops_member.creator = u.id and u.nickname like '%" + pageReqVO.getCreator() + "%'");
        }

        if (StringUtils.isNotBlank(pageReqVO.getSerialNumber())) {
            opsDOLambdaQueryWrapperX.exists("SELECT 1 from traffic_ops_qrcode q where  traffic_ops_member.equ_number = q.id and q.affiliated_unit = '1' and lpad( q.serial_num, 3, '0' ) like '%" + pageReqVO.getSerialNumber() + "%'");
        }

        PageResult<TrafficOpsMemberDO> pageResult = trafficOpsMemberMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);
        PageResult<TrafficOpsMemberExtRespVO> resultPage = BeanUtils.toBean(pageResult, TrafficOpsMemberExtRespVO.class);
        Optional.ofNullable(resultPage.getList()).orElse(new ArrayList<>()).forEach(opsMemberResp -> {
            if (StringUtils.isNotBlank(opsMemberResp.getCreator())) {
                AdminUserDO adminUserDO = adminUserMapper.selectById(opsMemberResp.getCreator());
                if (adminUserDO != null)  {
                    opsMemberResp.setCreator(adminUserDO.getNickname());
                }
            }

            if (StringUtils.isNotBlank(opsMemberResp.getEquNumber() )) {
                TrafficOpsQrcodeDO opsQrcodeDO = trafficOpsQrcodeMapper.selectById(opsMemberResp.getEquNumber());
                if (opsQrcodeDO != null) {
                    opsMemberResp.setQrCodeUrl(opsQrcodeDO.getQrcodeFile());
                }
            }

            // 查询电警数量
            LambdaQueryWrapper<TrafficOpsElectricPoliceDO> electricPoliceWrapper = new LambdaQueryWrapperX<>();
            electricPoliceWrapper.eq(TrafficOpsElectricPoliceDO::getMemberBh, opsMemberResp.getId());
            Long cnt = trafficOpsElectricPoliceMapper.selectCount(electricPoliceWrapper);
            opsMemberResp.setEctricPoliceCnt(cnt);

            opsMemberResp.setMaintenanceCnt(opsMaintenanceExtService.queryEquipMaintenanceCnt(opsMemberResp.getId()));
        });
        return resultPage;
    }

    @Override
    public void deleteOpsMemberInfo(String id) {
        // 校验杆件存在
        validateOpsMemberExists(id);

        // 更新电警信息的memberBh
        LambdaUpdateWrapper<TrafficOpsElectricPoliceDO> electricPoliceWrapper = new LambdaUpdateWrapper<>();
        electricPoliceWrapper.set(TrafficOpsElectricPoliceDO::getMemberBh, null);
        electricPoliceWrapper.eq(TrafficOpsElectricPoliceDO::getMemberBh, id);
        trafficOpsElectricPoliceMapper.update(electricPoliceWrapper);

        // 软删除杆件
        trafficOpsMemberMapper.deleteById(id);
    }

    private void validateOpsMemberExists(String id) {
        if (trafficOpsMemberMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MEMBER_NOT_EXISTS);
        }
    }
}