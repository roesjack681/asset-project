package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.PoliceOpsElectricPoliceExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备维保信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PoliceOpsElectricPoliceExtServiceImpl implements PoliceOpsElectricPoliceExtService {
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;

    @Override
    public List<PoliceOpsElectricPoliceRespVO> autocompletePoliceOpsElectricPoliceList(String equName) {
        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        List<String> stringList = HanLPUtils.segmentForRoad(equName);
        if (!CollectionUtils.isEmpty(stringList)) {
            for (String str : stringList) {
                lambdaQueryWrapperX.like(PoliceOpsElectricPoliceDO::getEquName, str);
            }
        }

        Page<PoliceOpsElectricPoliceDO> page = new Page<>(1, 20);
        List<PoliceOpsElectricPoliceDO> list = policeOpsElectricPoliceMapper.selectList(page, lambdaQueryWrapperX);
        return list.stream()
                .map(policeOpsElectricPoliceDO -> BeanUtils.toBean(policeOpsElectricPoliceDO, PoliceOpsElectricPoliceRespVO.class))
                .collect(Collectors.toList());
    }
}