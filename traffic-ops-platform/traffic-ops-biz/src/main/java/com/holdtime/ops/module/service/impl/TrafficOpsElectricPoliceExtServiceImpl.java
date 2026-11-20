package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.TrafficOpsElectricPoliceRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsElectricPoliceSaveReqVO;
import com.holdtime.ops.module.service.TrafficOpsElectricPoliceExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 电警 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsElectricPoliceExtServiceImpl implements TrafficOpsElectricPoliceExtService {

    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;
    @Resource
    private TrafficOpsMemberMapper opsMemberMapper;


    @Override
    public String saveOpsElectricPolice(TrafficOpsElectricPoliceSaveReqVO createReqVO) {
        // 插入
        TrafficOpsElectricPoliceDO opsElectricPolice = BeanUtils.toBean(createReqVO, TrafficOpsElectricPoliceDO.class);
        // 如果绑定了杆件信息,同步位置信息
        if (StringUtils.isNotBlank(createReqVO.getMemberBh())) {
            TrafficOpsMemberDO opsMember = opsMemberMapper.selectById(createReqVO.getMemberBh());
            if (opsMember != null)  {
                opsElectricPolice.setLat(opsMember.getLat());
                opsElectricPolice.setLng(opsMember.getLng());
            }
        }

        if (StringUtils.isBlank(createReqVO.getId())) {
            opsElectricPoliceMapper.insert(opsElectricPolice);
        } else {
            // 校验存在
            validateOpsElectricPoliceExists(createReqVO.getId());

            opsElectricPoliceMapper.updateById(opsElectricPolice);
        }

        // 返回
        return opsElectricPolice.getId();
    }

    private void validateOpsElectricPoliceExists(String id) {
        if (opsElectricPoliceMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_ELECTRIC_POLICE_NOT_EXISTS);
        }
    }

    @Override
    public List<TrafficOpsElectricPoliceRespVO> autocompleteOpsElectricPolice(String ipAddr) {
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.likeIfPresent(TrafficOpsElectricPoliceDO::getIpAddr, ipAddr);

        Page<TrafficOpsElectricPoliceDO> page = new Page<>(1, 20);
        List<TrafficOpsElectricPoliceDO> list = opsElectricPoliceMapper.selectPage(page, lambdaQueryWrapperX).getRecords();

        return BeanUtils.toBean(list, TrafficOpsElectricPoliceRespVO.class);
    }
}