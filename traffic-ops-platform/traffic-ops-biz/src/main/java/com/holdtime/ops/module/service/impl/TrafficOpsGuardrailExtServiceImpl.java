package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailAuxiliaryMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailGapMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailRProductMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.PublicOpsAttachInfoExtService;
import com.holdtime.ops.module.service.TrafficOpsGuardrailExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 护栏 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsGuardrailExtServiceImpl implements TrafficOpsGuardrailExtService {

    @Resource
    private TrafficOpsGuardrailMapper opsGuardrailMapper;
    @Resource
    private PublicOpsAttachInfoExtService opsAttachInfoExtService;
    @Resource
    private TrafficOpsGuardrailRProductMapper opsGuardrailRProductMapper;
    @Resource
    private TrafficOpsGuardrailAuxiliaryMapper opsGuardrailAuxiliaryMapper;
    @Resource
    private TrafficOpsGuardrailGapMapper opsGuardrailGapMapper;

    @Override
    public String saveOpsGuardrail(TrafficOpsGuardrailExtSaveReqVO saveReqVO) {
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 1.保存报修申请信息
        TrafficOpsGuardrailDO opsGuardrailDO = BeanUtils.toBean(saveReqVO, TrafficOpsGuardrailDO.class);
        if (StringUtils.isBlank(opsGuardrailDO.getId())) { // 新增围栏
            opsGuardrailMapper.insert(opsGuardrailDO);
        } else { // 修改围栏信息
            // 校验存在
            validateOpsGuardrailExists(opsGuardrailDO.getId());

            opsGuardrailMapper.updateById(opsGuardrailDO);
        }

        // 保存材料
        if (!CollectionUtils.isEmpty(saveReqVO.getAttachInfoList())) {
            opsAttachInfoExtService.saveOpsAttachInfo(OpsConstants.STRING_ONE, OpsConstants.BUSINESS_MODEL_TRAFFIC_GUARDRAIL, opsGuardrailDO.getId(), saveReqVO.getAttachInfoList());
        }
        // 保存产品信息
        if (!CollectionUtils.isEmpty(saveReqVO.getRproductList())) {
            saverProduct(opsGuardrailDO.getId(), saveReqVO.getRproductList());
        }
        // 保存附属设备信息
        if (!CollectionUtils.isEmpty(saveReqVO.getAuxiliaryList())) {
            saveAuxiliary(opsGuardrailDO.getId(), saveReqVO.getAuxiliaryList());
        }
        // 保存开口信息
        if (!CollectionUtils.isEmpty(saveReqVO.getGapList())) {
            saveGap(opsGuardrailDO.getId(), saveReqVO.getGapList());
        }
        return opsGuardrailDO.getId();
    }

    @Override
    public TrafficOpsGuardrailExtRespVO getOpsGuardrail(String id) {
        TrafficOpsGuardrailExtRespVO opsGuardrailExtRespVO = BeanUtils.toBean(opsGuardrailMapper.selectById(id), TrafficOpsGuardrailExtRespVO.class);
        opsGuardrailExtRespVO.setAttachInfoList(opsAttachInfoExtService.queryOpsAttachInfoList(OpsConstants.STRING_ONE, OpsConstants.BUSINESS_MODEL_TRAFFIC_GUARDRAIL, opsGuardrailExtRespVO.getId()));
        opsGuardrailExtRespVO.setRproductList(queryProductList(opsGuardrailExtRespVO.getId()));
        opsGuardrailExtRespVO.setAuxiliaryList(queryAuxiliaryList(opsGuardrailExtRespVO.getId()));
        opsGuardrailExtRespVO.setGapList(queryGapList(opsGuardrailExtRespVO.getId()));
        return opsGuardrailExtRespVO;
    }

    private void validateOpsGuardrailExists(String id) {
        if (opsGuardrailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_GUARDRAIL_NOT_EXISTS);
        }
    }

    private void saverProduct(String id,List<TrafficOpsGuardrailRProductSaveReqVO> rproductList){
        // 删除之前关联的
        LambdaQueryWrapper<TrafficOpsGuardrailRProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrafficOpsGuardrailRProductDO::getGuardrailId, id);
        opsGuardrailRProductMapper.delete(queryWrapper);

        List<TrafficOpsGuardrailRProductDO> instList = Optional.ofNullable(rproductList).orElse(new ArrayList<>()).stream()
                .map(saveReqVO -> {
                    TrafficOpsGuardrailRProductDO opsRProductDO = BeanUtils.toBean(saveReqVO, TrafficOpsGuardrailRProductDO.class);
                    opsRProductDO.setGuardrailId(id);
                    return opsRProductDO;
                })
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(instList)) {
            opsGuardrailRProductMapper.insertBatch(instList);
        }
    }

    private void saveAuxiliary(String id,List<TrafficOpsGuardrailAuxiliarySaveReqVO> auxiliaryList){
        // 删除之前关联的
        LambdaQueryWrapper<TrafficOpsGuardrailAuxiliaryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrafficOpsGuardrailAuxiliaryDO::getGuardrailId, id);
        opsGuardrailAuxiliaryMapper.delete(queryWrapper);

        List<TrafficOpsGuardrailAuxiliaryDO> instList = Optional.ofNullable(auxiliaryList).orElse(new ArrayList<>()).stream()
                .map(saveReqVO -> {
                    TrafficOpsGuardrailAuxiliaryDO opsAuxiliaryDO = BeanUtils.toBean(saveReqVO, TrafficOpsGuardrailAuxiliaryDO.class);
                    opsAuxiliaryDO.setGuardrailId(id);
                    opsAuxiliaryDO.setStatus(OpsConstants.EQU_INSTALL_STATUS_AZ);
                    return opsAuxiliaryDO;
                })
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(instList)) {
            opsGuardrailAuxiliaryMapper.insertBatch(instList);
        }
    }

    private void saveGap(String id,List<TrafficOpsGuardrailGapSaveReqVO> gapList){
        // 删除之前关联的
        LambdaQueryWrapper<TrafficOpsGuardrailGapDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrafficOpsGuardrailGapDO::getGuardrailId, id);
        opsGuardrailGapMapper.delete(queryWrapper);

        List<TrafficOpsGuardrailGapDO> instList = Optional.ofNullable(gapList).orElse(new ArrayList<>()).stream()
                .map(saveReqVO -> {
                    TrafficOpsGuardrailGapDO opsGapDO = BeanUtils.toBean(saveReqVO, TrafficOpsGuardrailGapDO.class);
                    opsGapDO.setGuardrailId(id);
                    return opsGapDO;
                })
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(instList)) {
            opsGuardrailGapMapper.insertBatch(instList);
        }
    }

    private List<TrafficOpsGuardrailRProductRespVO> queryProductList(String businessId) {
        LambdaQueryWrapper<TrafficOpsGuardrailRProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrafficOpsGuardrailRProductDO::getGuardrailId, businessId);
        return BeanUtils.toBean(opsGuardrailRProductMapper.selectList(queryWrapper), TrafficOpsGuardrailRProductRespVO.class);
    }
    private List<TrafficOpsGuardrailAuxiliaryRespVO> queryAuxiliaryList(String businessId) {
        LambdaQueryWrapper<TrafficOpsGuardrailAuxiliaryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrafficOpsGuardrailAuxiliaryDO::getGuardrailId, businessId);
        return BeanUtils.toBean(opsGuardrailAuxiliaryMapper.selectList(queryWrapper), TrafficOpsGuardrailAuxiliaryRespVO.class);
    }
    private List<TrafficOpsGuardrailGapRespVO> queryGapList(String businessId) {
        LambdaQueryWrapper<TrafficOpsGuardrailGapDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrafficOpsGuardrailGapDO::getGuardrailId, businessId);
        return BeanUtils.toBean(opsGuardrailGapMapper.selectList(queryWrapper), TrafficOpsGuardrailGapRespVO.class);
    }
}