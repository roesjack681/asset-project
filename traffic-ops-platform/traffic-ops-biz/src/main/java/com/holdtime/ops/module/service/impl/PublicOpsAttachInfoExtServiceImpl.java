package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.entity.PublicOpsAttachInfoDO;
import com.holdtime.ops.module.entity.mapper.PublicOpsAttachInfoMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.PublicOpsAttachInfoExtSaveReqVO;
import com.holdtime.ops.module.model.vo.PublicOpsAttachInfoRespVO;
import com.holdtime.ops.module.service.PublicOpsAttachInfoExtService;
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
 * 资产附件信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsAttachInfoExtServiceImpl implements PublicOpsAttachInfoExtService {

    @Resource
    private PublicOpsAttachInfoMapper opsAttachInfoMapper;


    @Override
    public void saveOpsAttachInfo(String affiliatedUnit, String businessModel, String businessId, List<PublicOpsAttachInfoExtSaveReqVO> saveReqVOList) {
        if (StringUtils.isBlank(affiliatedUnit)
                || StringUtils.isBlank(businessModel)
                || StringUtils.isBlank(businessId)) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 删除之前关联的附件
        LambdaQueryWrapper<PublicOpsAttachInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicOpsAttachInfoDO::getAffiliatedUnit, affiliatedUnit);
        queryWrapper.eq(PublicOpsAttachInfoDO::getBusinessModel, businessModel);
        queryWrapper.eq(PublicOpsAttachInfoDO::getBusinessId, businessId);
        opsAttachInfoMapper.delete(queryWrapper);

        List<PublicOpsAttachInfoDO> instList = Optional.ofNullable(saveReqVOList).orElse(new ArrayList<>()).stream()
                .map(saveReqVO -> {
                    PublicOpsAttachInfoDO opsAttachInfoDO = BeanUtils.toBean(saveReqVO, PublicOpsAttachInfoDO.class);
                    opsAttachInfoDO.setAffiliatedUnit(affiliatedUnit);
                    opsAttachInfoDO.setBusinessModel(businessModel);
                    opsAttachInfoDO.setBusinessId(businessId);
                    return opsAttachInfoDO;
                })
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(instList)) {
            opsAttachInfoMapper.insertBatch(instList);
        }
    }

    @Override
    public List<PublicOpsAttachInfoRespVO> queryOpsAttachInfoList(String affiliatedUnit, String businessModel, String businessId) {
        LambdaQueryWrapper<PublicOpsAttachInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicOpsAttachInfoDO::getAffiliatedUnit, affiliatedUnit);
        if (StringUtils.isNotBlank(businessModel)) {
            queryWrapper.eq(PublicOpsAttachInfoDO::getBusinessModel, businessModel);
        }
        queryWrapper.eq(PublicOpsAttachInfoDO::getBusinessId, businessId);
        return BeanUtils.toBean(opsAttachInfoMapper.selectList(queryWrapper), PublicOpsAttachInfoRespVO.class);
    }
}