package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.excel.util.StringUtils;
import com.holdtime.ops.module.entity.TrafficOpsMaintainFlowRecordDO;
import com.holdtime.ops.module.entity.TrafficOpsRegisterDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintainFlowRecordMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsRegisterMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintainFlowRecordSaveReqVO;
import com.holdtime.ops.module.service.TrafficOpsMaintainFlowRecordExtService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保全流程 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsMaintainFlowRecordExtServiceImpl implements TrafficOpsMaintainFlowRecordExtService {

    @Resource
    private TrafficOpsMaintainFlowRecordMapper opsMaintainFlowRecordMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private TrafficOpsRegisterMapper opsRegisterMapper;


    @Override
    public void saveMaintainFlowRecord(TrafficOpsMaintainFlowRecordSaveReqVO createReqVO) {
        if (StringUtils.isBlank(createReqVO.getMaintenanceId())
                || StringUtils.isBlank(createReqVO.getFlowType())
                || createReqVO.getUserId() == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }
        TrafficOpsMaintainFlowRecordDO opsMaintainFlowRecordDO = BeanUtils.toBean(createReqVO, TrafficOpsMaintainFlowRecordDO.class);
        // 查询用户信息
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(createReqVO.getUserId());
        if (adminUserRespDTO != null) {
            opsMaintainFlowRecordDO.setUserName(adminUserRespDTO.getNickname());
            opsMaintainFlowRecordDO.setUserPhone(adminUserRespDTO.getMobile());
            opsMaintainFlowRecordDO.setUserName(adminUserRespDTO.getNickname());
        }

        if (StringUtils.isBlank(createReqVO.getRegisterId())) {
            LambdaQueryWrapperX<TrafficOpsRegisterDO> queryWrapper = new LambdaQueryWrapperX<>();
            queryWrapper.eq(TrafficOpsRegisterDO::getUserId, createReqVO.getUserId());
            List<TrafficOpsRegisterDO> trafficOpsRegisterDOList = opsRegisterMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(trafficOpsRegisterDOList) ) {
                opsMaintainFlowRecordDO.setRegisterId(trafficOpsRegisterDOList.get(0).getId());
            }
        }
        opsMaintainFlowRecordMapper.insert(opsMaintainFlowRecordDO);
    }

    @Override
    public void batchSaveMaintainFlowRecord(List<String> maintainIdList, TrafficOpsMaintainFlowRecordSaveReqVO createReqVO) {
        for (String maintainId : maintainIdList)  {
            createReqVO.setMaintenanceId(maintainId);
            saveMaintainFlowRecord(createReqVO);
        }
    }

    @Override
    public List<TrafficOpsMaintainFlowRecordDO> getOpsMaintainFlowRecord(String maintainId) {
        LambdaQueryWrapperX<TrafficOpsMaintainFlowRecordDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(TrafficOpsMaintainFlowRecordDO::getMaintenanceId, maintainId);
        queryWrapper.orderByAsc(TrafficOpsMaintainFlowRecordDO::getCreateTime);
        return opsMaintainFlowRecordMapper.selectList(queryWrapper);
    }
}