package com.holdtime.ops.module.service.impl.opsmarkline;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMarklineService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMarklineDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMarklineMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 标线 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsMarklineServiceImpl implements TrafficOpsMarklineService {

    @Resource
    private TrafficOpsMarklineMapper opsMarklineMapper;

    @Override
    public String createOpsMarkline(TrafficOpsMarklineSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMarklineDO opsMarkline = BeanUtils.toBean(createReqVO, TrafficOpsMarklineDO.class);
        opsMarklineMapper.insert(opsMarkline);
        // 返回
        return opsMarkline.getId();
    }

    @Override
    public void updateOpsMarkline(TrafficOpsMarklineSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMarklineExists(updateReqVO.getId());
        // 更新
        TrafficOpsMarklineDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMarklineDO.class);
        opsMarklineMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMarkline(String id) {
        // 校验存在
        validateOpsMarklineExists(id);
        // 删除
        opsMarklineMapper.deleteById(id);
    }

    private void validateOpsMarklineExists(String id) {
        if (opsMarklineMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MARKLINE_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMarklineDO getOpsMarkline(String id) {
        return opsMarklineMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMarklineDO> getOpsMarklinePage(TrafficOpsMarklinePageReqVO pageReqVO) {
        return opsMarklineMapper.selectPage(pageReqVO);
    }

}