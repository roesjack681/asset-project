package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicOpsInoutDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicOpsInoutDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PublicOpsInoutDetailMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备出入库存明细 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PublicOpsInoutDetailServiceImpl implements PublicOpsInoutDetailService {

    @Resource
    private PublicOpsInoutDetailMapper opsInoutDetailMapper;

    @Override
    public String createOpsInoutDetail(PublicOpsInoutDetailSaveReqVO createReqVO) {
        // 插入
        PublicOpsInoutDetailDO opsInoutDetail = BeanUtils.toBean(createReqVO, PublicOpsInoutDetailDO.class);
        opsInoutDetailMapper.insert(opsInoutDetail);
        // 返回
        return opsInoutDetail.getId();
    }

    @Override
    public void updateOpsInoutDetail(PublicOpsInoutDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsInoutDetailExists(updateReqVO.getId());
        // 更新
        PublicOpsInoutDetailDO updateObj = BeanUtils.toBean(updateReqVO, PublicOpsInoutDetailDO.class);
        opsInoutDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsInoutDetail(String id) {
        // 校验存在
        validateOpsInoutDetailExists(id);
        // 删除
        opsInoutDetailMapper.deleteById(id);
    }

    private void validateOpsInoutDetailExists(String id) {
        if (opsInoutDetailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_INOUT_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public PublicOpsInoutDetailDO getOpsInoutDetail(String id) {
        return opsInoutDetailMapper.selectById(id);
    }

    @Override
    public PageResult<PublicOpsInoutDetailDO> getOpsInoutDetailPage(PublicOpsInoutDetailPageReqVO pageReqVO) {
        return opsInoutDetailMapper.selectPage(pageReqVO);
    }

}