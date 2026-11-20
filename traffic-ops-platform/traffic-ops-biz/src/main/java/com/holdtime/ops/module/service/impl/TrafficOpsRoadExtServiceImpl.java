package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.TrafficOpsRoadDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsRoadMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsRoadExtService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Validated
public class TrafficOpsRoadExtServiceImpl implements TrafficOpsRoadExtService {

    @Resource
    private TrafficOpsRoadMapper opsRoadMapper;
    @Resource
    private TrafficOpsMemberMapper trafficOpsMemberMapper;
    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;


    @Override
    public PageResult<TrafficOpsMemberExtRespVO> getOpsRoadPage(TrafficOpsMemberPageReqVO pageReqVO,String id) {
        TrafficOpsRoadDO roadDO = opsRoadMapper.selectById(id);
        if (StringUtils.isNotBlank(roadDO.getDlmc())) {
            LambdaQueryWrapperX<TrafficOpsMemberDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(TrafficOpsMemberDO::getRoadName,roadDO.getDlmc());
            queryWrapperX.isNull(TrafficOpsMemberDO::getIsCheck);
            queryWrapperX.orderByAsc(TrafficOpsMemberDO::getSerialNum);
            PageResult<TrafficOpsMemberDO> memebetPageResult = trafficOpsMemberMapper.selectPage(pageReqVO, queryWrapperX);
            PageResult<TrafficOpsMemberExtRespVO> resultPage = BeanUtils.toBean(memebetPageResult, TrafficOpsMemberExtRespVO.class);
            Optional.ofNullable(resultPage.getList()).orElse(new ArrayList<>()).forEach(opsMemberResp -> {
                LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(TrafficOpsElectricPoliceDO::getMemberBh,opsMemberResp.getId());
                List<TrafficOpsElectricPoliceDO> electricPoliceVOList = opsElectricPoliceMapper.selectList(queryWrapperX1);
                //queryWrapperX1.orderByAsc(TrafficOpsMemberDO::getSerialNum);
                if (!CollectionUtils.isEmpty(electricPoliceVOList)) {
                    opsMemberResp.setElectricPoliceVOList(BeanUtils.toBean(electricPoliceVOList, TrafficOpsElectricPoliceRespVO.class));
                }
            });
            return resultPage;
        }
        return null;
    }

    @Override
    public String checkResult(TrafficOpsMemberExtRespVO createReqVO) {
        //更新杆件信息
        TrafficOpsMemberDO updateObj = BeanUtils.toBean(createReqVO, TrafficOpsMemberDO.class);
        updateObj.setIsCheck(OpsConstants.STRING_YES);
        trafficOpsMemberMapper.updateById(updateObj);

        //删除原有该杆件下所有绑定关系
        LambdaUpdateWrapper<TrafficOpsElectricPoliceDO> updwrapper = new LambdaUpdateWrapper<>();
        updwrapper.set(TrafficOpsElectricPoliceDO::getMemberBh, null);
        updwrapper.eq(TrafficOpsElectricPoliceDO::getMemberBh, createReqVO.getId());
        opsElectricPoliceMapper.update(updwrapper);

        //更新电警的绑定关系
        Optional.ofNullable(createReqVO.getElectricPoliceVOList()).orElse(new ArrayList<>()).forEach(opsElectricPoliceResp -> {
            TrafficOpsElectricPoliceDO updateElectricPolice = BeanUtils.toBean(opsElectricPoliceResp, TrafficOpsElectricPoliceDO.class);
            updateElectricPolice.setIsCheck(OpsConstants.STRING_YES);
            updateElectricPolice.setMemberBh(createReqVO.getId());
            opsElectricPoliceMapper.updateById(updateElectricPolice);
        });

        // 返回
        return createReqVO.getId();
    }

}