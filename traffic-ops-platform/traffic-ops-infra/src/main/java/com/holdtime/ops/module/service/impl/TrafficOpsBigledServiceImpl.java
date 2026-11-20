package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsBigledService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsBigledDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsBigledMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 大屏 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsBigledServiceImpl implements TrafficOpsBigledService {

    @Resource
    private TrafficOpsBigledMapper opsBigledMapper;

    @Override
    public String createOpsBigled(TrafficOpsBigledSaveReqVO createReqVO) {
        // 插入
        TrafficOpsBigledDO opsBigled = BeanUtils.toBean(createReqVO, TrafficOpsBigledDO.class);
        opsBigledMapper.insert(opsBigled);
        // 返回
        return opsBigled.getId();
    }

    @Override
    public void updateOpsBigled(TrafficOpsBigledSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsBigledExists(updateReqVO.getId());
        // 更新
        TrafficOpsBigledDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsBigledDO.class);
        opsBigledMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsBigled(String id) {
        // 校验存在
        validateOpsBigledExists(id);
        // 删除
        opsBigledMapper.deleteById(id);
    }

    private void validateOpsBigledExists(String id) {
        if (opsBigledMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_BIGLED_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsBigledDO getOpsBigled(String id) {
        return opsBigledMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsBigledDO> getOpsBigledPage(TrafficOpsBigledPageReqVO pageReqVO) {
        return opsBigledMapper.selectPage(pageReqVO);
    }

}