package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.TrafficOpsMarklineDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMarklineMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.TrafficOpsMarklineExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMarklineExtSaveReqVO;
import com.holdtime.ops.module.service.PublicOpsAttachInfoExtService;
import com.holdtime.ops.module.service.TrafficOpsMarklineExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 标线 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsMarklineExtServiceImpl implements TrafficOpsMarklineExtService {

    @Resource
    private TrafficOpsMarklineMapper opsMarklineMapper;
    @Resource
    private PublicOpsAttachInfoExtService opsAttachInfoExtService;

    @Override
    public String saveOpsMarkline(TrafficOpsMarklineExtSaveReqVO saveReqVO) {
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 1.保存报修申请信息
        TrafficOpsMarklineDO opsMarklineDO = BeanUtils.toBean(saveReqVO, TrafficOpsMarklineDO.class);
        if (StringUtils.isBlank(opsMarklineDO.getId())) { // 新增标线
            opsMarklineMapper.insert(opsMarklineDO);
        } else { // 修改报修申请信息
            // 校验存在
            validateOpsMarklineExists(opsMarklineDO.getId());

            opsMarklineMapper.updateById(opsMarklineDO);
        }

        // 保存材料
        if (!CollectionUtils.isEmpty(saveReqVO.getAttachInfoList())) {
            opsAttachInfoExtService.saveOpsAttachInfo(OpsConstants.STRING_ONE, OpsConstants.BUSINESS_MODEL_TRAFFIC_MARK_LINE, opsMarklineDO.getId(), saveReqVO.getAttachInfoList());
        }
        return opsMarklineDO.getId();
    }

    @Override
    public TrafficOpsMarklineExtRespVO getOpsMarkline(String id) {
        TrafficOpsMarklineExtRespVO opsMarklineExtRespVO = BeanUtils.toBean(opsMarklineMapper.selectById(id), TrafficOpsMarklineExtRespVO.class);
        opsMarklineExtRespVO.setAttachInfoList(opsAttachInfoExtService.queryOpsAttachInfoList(OpsConstants.STRING_ONE, OpsConstants.BUSINESS_MODEL_TRAFFIC_MARK_LINE, opsMarklineExtRespVO.getId()));
        return opsMarklineExtRespVO;
    }

    private void validateOpsMarklineExists(String id) {
        if (opsMarklineMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MARKLINE_NOT_EXISTS);
        }
    }
}