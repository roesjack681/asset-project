package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.PublicOpsAttachInfoExtSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailAuxiliarySaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailRProductSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintainProgressSaveReqVO;
import com.holdtime.ops.module.service.OpsMaintenanceCenterHandlerService;
import com.holdtime.ops.module.service.PublicOpsAttachInfoExtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OpsMaintenanceCenterHandlerServiceImpl implements OpsMaintenanceCenterHandlerService {
    @Resource
    private TrafficOpsMaintenanceGuardrailMapper opsMaintenanceGuardrailMapper;
    @Resource
    private PublicOpsAttachInfoExtService opsAttachInfoExtService;
    @Resource
    private TrafficOpsGuardrailInsuranceMapper opsGuardrailInsuranceMapper;
    @Resource
    private TrafficOpsGuardrailGapMapper opsGuardrailGapMapper;
    @Resource
    private TrafficOpsGuardrailMapper opsGuardrailMapper;
    @Resource
    private TrafficOpsGuardrailRProductMapper opsGuardrailRProductMapper;
    @Resource
    private TrafficOpsGuardrailAuxiliaryMapper opsGuardrailAuxiliaryMapper;
    @Resource
    private TrafficOpsMaintenanceAuxiliaryMapper opsMaintenanceAuxiliaryMapper;
    @Resource
    private TrafficOpsGuardrailProductMapper opsGuardrailProductMapper;

    @Override
    public String handlerGuardrail(TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO) {
        if (progressVO == null || progressVO.getOpsMaintenanceGuardrailVO() == null) {
            return OpsConstants.STRING_ZERO;
        }

        // 保存护栏维修详细信息
        TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO = saveMaintenanceGuardrail(opsMaintenanceDO, progressVO);

        // 保存变更前后照片
        saveAttachInfo(opsMaintenanceDO, progressVO, maintenanceGuardrailDO);

        // 处理护栏维护类型
        handleMaintenanceType(opsMaintenanceDO, progressVO, maintenanceGuardrailDO);

        // 保存保险信息
        saveInsuranceInfo(progressVO, maintenanceGuardrailDO);

        // 保存开口信息
        saveGuardrailGapInfo(opsMaintenanceDO, progressVO, maintenanceGuardrailDO);

        return OpsConstants.STRING_ONE;
    }

    private TrafficOpsMaintenanceGuardrailDO saveMaintenanceGuardrail(TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO) {
        TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO = BeanUtils.toBean(progressVO.getOpsMaintenanceGuardrailVO(), TrafficOpsMaintenanceGuardrailDO.class);
        maintenanceGuardrailDO.setMaintenanceId(opsMaintenanceDO.getId());
        opsMaintenanceGuardrailMapper.insert(maintenanceGuardrailDO);
        return maintenanceGuardrailDO;
    }

    private void saveAttachInfo(TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO) {
        if (!CollectionUtils.isEmpty(progressVO.getOpsMaintenanceGuardrailVO().getAttachInfoList())) {
            Map<String, List<PublicOpsAttachInfoExtSaveReqVO>> attachInfoListMap = progressVO.getOpsMaintenanceGuardrailVO().getAttachInfoList().stream()
                    .collect(Collectors.groupingBy(PublicOpsAttachInfoExtSaveReqVO::getBusinessModel));
            attachInfoListMap.keySet().forEach(key -> opsAttachInfoExtService.saveOpsAttachInfo(
                    opsMaintenanceDO.getAffiliatedUnit(), key, maintenanceGuardrailDO.getId(), attachInfoListMap.get(key)));
        }
    }

    private void handleMaintenanceType(TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO) {
        TrafficOpsGuardrailDO opsGuardrailDO = getOpsGuardrailDO(maintenanceGuardrailDO);

        boolean isUpd = true;
        if ("加装".equals(maintenanceGuardrailDO.getMaintenanceType())) {
            handleInstallation(progressVO, opsGuardrailDO);
        } else if ("拆除".equals(maintenanceGuardrailDO.getMaintenanceType())) {
            handleRemoval(progressVO, maintenanceGuardrailDO, opsGuardrailDO);
        } else if ("换新".equals(maintenanceGuardrailDO.getChangeType())) {
            handleReplacement(maintenanceGuardrailDO, opsGuardrailDO);
        } else if ("维修".equals(maintenanceGuardrailDO.getChangeType())) {
            handleRepair(opsMaintenanceDO, progressVO, maintenanceGuardrailDO);
            isUpd = false;
        }
        if (isUpd) {
            opsGuardrailMapper.updateById(opsGuardrailDO);
        }

    }

    private TrafficOpsGuardrailDO getOpsGuardrailDO(TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO) {
        if (StringUtils.isNotBlank(maintenanceGuardrailDO.getGuardrailId())) {
            return opsGuardrailMapper.selectById(maintenanceGuardrailDO.getGuardrailId());
        }
        return null;
    }

    private void handleInstallation(TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsGuardrailDO opsGuardrailDO) {
        if (!CollectionUtils.isEmpty(progressVO.getOpsMaintenanceGuardrailVO().getRproductList())) {
            saveRProductList(progressVO, opsGuardrailDO);
        }

        if (!CollectionUtils.isEmpty(progressVO.getOpsMaintenanceGuardrailVO().getAuxiliaryList())) {
            saveAuxiliaryList(progressVO, opsGuardrailDO);
        }
    }

    private void saveRProductList(TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsGuardrailDO opsGuardrailDO) {
        List<TrafficOpsGuardrailRProductDO> rProductDOList = progressVO.getOpsMaintenanceGuardrailVO().getRproductList().stream()
                .map(vo -> {
                    TrafficOpsGuardrailRProductDO rProductDO = BeanUtils.toBean(vo, TrafficOpsGuardrailRProductDO.class);
                    if (opsGuardrailDO != null) {
                        opsGuardrailDO.setPieces(opsGuardrailDO.getPieces() + vo.getPieces());
                        // 查询护栏产品信息
                        TrafficOpsGuardrailProductDO productDO = opsGuardrailProductMapper.selectById(vo.getProductId());
                        if (productDO != null) {
                            opsGuardrailDO.setLength(opsGuardrailDO.getLength().add(BigDecimal.valueOf(vo.getPieces()).multiply(productDO.getLength())));
                        }
                        rProductDO.setGuardrailId(opsGuardrailDO.getId());
                    }
                    return rProductDO;
                }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(rProductDOList)) {
            opsGuardrailRProductMapper.insert(rProductDOList);
        }
    }

    private void saveAuxiliaryList(TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsGuardrailDO opsGuardrailDO) {
        List<TrafficOpsGuardrailAuxiliaryDO> auxiliaryDOList = progressVO.getOpsMaintenanceGuardrailVO().getAuxiliaryList().stream()
                .map(vo -> {
                    TrafficOpsGuardrailAuxiliaryDO auxiliaryDO = BeanUtils.toBean(vo, TrafficOpsGuardrailAuxiliaryDO.class);
                    if (opsGuardrailDO != null) {
                        auxiliaryDO.setGuardrailId(opsGuardrailDO.getId());
                    }
                    return auxiliaryDO;
                }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(auxiliaryDOList)) {
            opsGuardrailAuxiliaryMapper.insert(auxiliaryDOList);
        }
    }

    private void handleRemoval(TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO, TrafficOpsGuardrailDO opsGuardrailDO) {
        List<String> delIdList = new ArrayList<>();
        if (!"拆除辅助设备".equals(maintenanceGuardrailDO.getRemovalType())) {
            removeRProducts(opsGuardrailDO, progressVO, maintenanceGuardrailDO, delIdList);
        } else {
            removeAuxiliaries(progressVO, maintenanceGuardrailDO, delIdList);
        }
    }

    private void removeRProducts(TrafficOpsGuardrailDO opsGuardrailDO, TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO, List<String> delIdList) {
        if (!CollectionUtils.isEmpty(progressVO.getOpsMaintenanceGuardrailVO().getRproductList())) {
            LambdaQueryWrapper<TrafficOpsGuardrailRProductDO> rProductDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
            rProductDOLambdaQueryWrapper.eq(TrafficOpsGuardrailRProductDO::getGuardrailId, maintenanceGuardrailDO.getGuardrailId());
            List<TrafficOpsGuardrailRProductDO> rProductDOList = opsGuardrailRProductMapper.selectList(rProductDOLambdaQueryWrapper);
            Map<String, TrafficOpsGuardrailRProductDO> rProductDOMap = rProductDOList.stream()
                    .collect(Collectors.toMap(TrafficOpsGuardrailRProductDO::getId, rProductDO -> rProductDO, (k1, k2) -> k1));

            List<TrafficOpsGuardrailRProductDO> updDOInfoList = new ArrayList<>();
            for (TrafficOpsGuardrailRProductSaveReqVO saveReqVO : progressVO.getOpsMaintenanceGuardrailVO().getRproductList()) {
                TrafficOpsGuardrailRProductDO rProductDO = rProductDOMap.get(saveReqVO.getId());
                if (rProductDO != null) {
                    opsGuardrailDO.setPieces(opsGuardrailDO.getPieces() - saveReqVO.getPieces());

                    // 查询护栏产品信息
                    TrafficOpsGuardrailProductDO productDO = opsGuardrailProductMapper.selectById(rProductDO.getProductId());
                    if (productDO != null) {
                        opsGuardrailDO.setLength(opsGuardrailDO.getLength().subtract(productDO.getLength().multiply(BigDecimal.valueOf(saveReqVO.getPieces()))));
                    }

                    int pieces = rProductDO.getPieces() - saveReqVO.getPieces();
                    if (pieces > 0) {
                        rProductDO.setPieces(pieces);
                        updDOInfoList.add(rProductDO);
                    } else {
                        delIdList.add(rProductDO.getId());
                    }
                }
            }

            if (!CollectionUtils.isEmpty(updDOInfoList)) {
                opsGuardrailRProductMapper.updateById(updDOInfoList);
            }
            if (!CollectionUtils.isEmpty(delIdList)) {
                opsGuardrailRProductMapper.deleteByIds(delIdList);
            }
        }
    }

    private void removeAuxiliaries(TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO, List<String> delIdList) {
        if (!CollectionUtils.isEmpty(progressVO.getOpsMaintenanceGuardrailVO().getAuxiliaryList())) {
            LambdaQueryWrapper<TrafficOpsGuardrailAuxiliaryDO> auxiliaryDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
            auxiliaryDOLambdaQueryWrapper.eq(TrafficOpsGuardrailAuxiliaryDO::getGuardrailId, maintenanceGuardrailDO.getGuardrailId());
            List<TrafficOpsGuardrailAuxiliaryDO> auxiliaryDOList = opsGuardrailAuxiliaryMapper.selectList(auxiliaryDOLambdaQueryWrapper);
            Map<String, TrafficOpsGuardrailAuxiliaryDO> auxiliaryDOMap = auxiliaryDOList.stream()
                    .collect(Collectors.toMap(TrafficOpsGuardrailAuxiliaryDO::getId, auxiliaryDO -> auxiliaryDO, (k1, k2) -> k1));

            List<TrafficOpsGuardrailAuxiliaryDO> updDOInfoList = new ArrayList<>();
            for (TrafficOpsGuardrailAuxiliarySaveReqVO saveReqVO : progressVO.getOpsMaintenanceGuardrailVO().getAuxiliaryList()) {
                TrafficOpsGuardrailAuxiliaryDO auxiliaryDO = auxiliaryDOMap.get(saveReqVO.getId());
                if (auxiliaryDO != null) {
                    int auxiliaryNum = auxiliaryDO.getAuxiliaryNum() - saveReqVO.getAuxiliaryNum();
                    if (auxiliaryNum > 0) {
                        auxiliaryDO.setAuxiliaryNum(auxiliaryNum);
                        updDOInfoList.add(auxiliaryDO);
                    } else {
                        delIdList.add(auxiliaryDO.getId());
                    }
                }
            }

            if (!CollectionUtils.isEmpty(updDOInfoList)) {
                opsGuardrailAuxiliaryMapper.updateById(updDOInfoList);
            }
            if (!CollectionUtils.isEmpty(delIdList)) {
                opsGuardrailAuxiliaryMapper.deleteByIds(delIdList);
            }
        }
    }

    private void handleReplacement(TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO, TrafficOpsGuardrailDO opsGuardrailDO) {
        LambdaQueryWrapper<TrafficOpsGuardrailRProductDO> beforeRProductLambdaQueryWrapper = new LambdaQueryWrapperX<>();
        beforeRProductLambdaQueryWrapper.eq(TrafficOpsGuardrailRProductDO::getGuardrailId, maintenanceGuardrailDO.getGuardrailId());
        beforeRProductLambdaQueryWrapper.eq(TrafficOpsGuardrailRProductDO::getProductId, maintenanceGuardrailDO.getModitfyBeProduct());
        List<TrafficOpsGuardrailRProductDO> beforeRProductList = opsGuardrailRProductMapper.selectList(beforeRProductLambdaQueryWrapper);

        if (!CollectionUtils.isEmpty(beforeRProductList)) {
            TrafficOpsGuardrailRProductDO beforeRProductDO = beforeRProductList.get(0);

            opsGuardrailDO.setPieces(opsGuardrailDO.getPieces() - maintenanceGuardrailDO.getModitfyBePieces());
            // 查询护栏产品信息
            TrafficOpsGuardrailProductDO oldProductDO = opsGuardrailProductMapper.selectById(maintenanceGuardrailDO.getModitfyBeProduct());
            if (oldProductDO != null) {
                opsGuardrailDO.setLength(opsGuardrailDO.getLength().subtract(oldProductDO.getLength().multiply(BigDecimal.valueOf(maintenanceGuardrailDO.getModitfyBePieces()))));
            }

            if (beforeRProductDO.getPieces() - maintenanceGuardrailDO.getModitfyBePieces() > 0) {
                beforeRProductDO.setPieces(beforeRProductDO.getPieces() - maintenanceGuardrailDO.getModitfyBePieces());

                opsGuardrailRProductMapper.updateById(beforeRProductDO);
            } else {
                opsGuardrailRProductMapper.deleteById(beforeRProductDO.getId());
            }

            LambdaQueryWrapper<TrafficOpsGuardrailRProductDO> afterRProductLambdaQueryWrapper = new LambdaQueryWrapperX<>();
            afterRProductLambdaQueryWrapper.eq(TrafficOpsGuardrailRProductDO::getGuardrailId, maintenanceGuardrailDO.getGuardrailId());
            afterRProductLambdaQueryWrapper.eq(TrafficOpsGuardrailRProductDO::getProductId, maintenanceGuardrailDO.getModitfyAfProduct());
            List<TrafficOpsGuardrailRProductDO> afterRProductList = opsGuardrailRProductMapper.selectList(afterRProductLambdaQueryWrapper);

            opsGuardrailDO.setPieces(opsGuardrailDO.getPieces() - maintenanceGuardrailDO.getModitfyAfPieces());
            // 查询护栏产品信息
            TrafficOpsGuardrailProductDO newProductDO = opsGuardrailProductMapper.selectById(maintenanceGuardrailDO.getModitfyAfProduct());
            if (newProductDO != null) {
                opsGuardrailDO.setLength(opsGuardrailDO.getLength().add(newProductDO.getLength().multiply(BigDecimal.valueOf(maintenanceGuardrailDO.getModitfyAfPieces()))));
            }

            if (CollectionUtils.isEmpty(afterRProductList)) {
                TrafficOpsGuardrailRProductDO afterRProductDO = new TrafficOpsGuardrailRProductDO();
                afterRProductDO.setGuardrailId(maintenanceGuardrailDO.getGuardrailId());
                afterRProductDO.setProductId(maintenanceGuardrailDO.getModitfyAfProduct());
                afterRProductDO.setPieces(maintenanceGuardrailDO.getModitfyAfPieces());
                opsGuardrailRProductMapper.insert(afterRProductDO);
            } else {
                TrafficOpsGuardrailRProductDO afterRProductDO = afterRProductList.get(0);
                afterRProductDO.setPieces(afterRProductDO.getPieces() + maintenanceGuardrailDO.getModitfyAfPieces());
                opsGuardrailRProductMapper.updateById(afterRProductDO);
            }
        }
    }

    private void handleRepair(TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO) {
        if (!CollectionUtils.isEmpty(progressVO.getOpsMaintenanceGuardrailVO().getAuxiliaryList())) {
            List<TrafficOpsMaintenanceAuxiliaryDO> maintenanceAuxiliaryDOList = progressVO.getOpsMaintenanceGuardrailVO().getAuxiliaryList().stream()
                    .map(saveReqVO -> {
                        TrafficOpsMaintenanceAuxiliaryDO auxiliaryDO = new TrafficOpsMaintenanceAuxiliaryDO();
                        auxiliaryDO.setMaintenanceId(opsMaintenanceDO.getId());
                        auxiliaryDO.setGuardrailId(maintenanceGuardrailDO.getGuardrailId());
                        auxiliaryDO.setAuxiliaryId(saveReqVO.getId());
                        auxiliaryDO.setAuxiliaryName(saveReqVO.getAuxiliaryName());
                        auxiliaryDO.setAuxiliaryNum(saveReqVO.getAuxiliaryNum());
                        return auxiliaryDO;
                    }).collect(Collectors.toList());
            opsMaintenanceAuxiliaryMapper.insert(maintenanceAuxiliaryDOList);
        }
    }

    private void saveInsuranceInfo(TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO) {
        if (progressVO.getOpsMaintenanceGuardrailVO().getInsuranceInfoVO() != null) {
            TrafficOpsGuardrailInsuranceDO insuranceDO = BeanUtils.toBean(progressVO.getOpsMaintenanceGuardrailVO().getInsuranceInfoVO(), TrafficOpsGuardrailInsuranceDO.class);
            insuranceDO.setManceGuardrailId(maintenanceGuardrailDO.getId());
            opsGuardrailInsuranceMapper.insert(insuranceDO);
        }
    }

    private void saveGuardrailGapInfo(TrafficOpsMaintenanceDO opsMaintenanceDO, TrafficOpsMaintainProgressSaveReqVO progressVO, TrafficOpsMaintenanceGuardrailDO maintenanceGuardrailDO) {
        if (!CollectionUtils.isEmpty(progressVO.getOpsMaintenanceGuardrailVO().getGuardrailGapVOList())) {
            List<TrafficOpsGuardrailGapDO> gapDOList = progressVO.getOpsMaintenanceGuardrailVO().getGuardrailGapVOList().stream()
                    .map(vo -> {
                        TrafficOpsGuardrailGapDO gapDO = BeanUtils.toBean(vo, TrafficOpsGuardrailGapDO.class);
                        gapDO.setGuardrailId(opsMaintenanceDO.getEquId());
                        gapDO.setMaintenanceId(opsMaintenanceDO.getId());
                        gapDO.setManceGuardrailId(maintenanceGuardrailDO.getId());
                        return gapDO;
                    }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(gapDOList)) {
                opsGuardrailGapMapper.insert(gapDOList);
            }
        }
    }
}
