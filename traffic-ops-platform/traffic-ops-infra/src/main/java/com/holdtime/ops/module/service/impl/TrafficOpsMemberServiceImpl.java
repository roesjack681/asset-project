package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMemberService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 杆件 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsMemberServiceImpl implements TrafficOpsMemberService {

    @Resource
    private TrafficOpsMemberMapper opsMemberMapper;

    @Override
    public String createOpsMember(TrafficOpsMemberSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMemberDO opsMember = BeanUtils.toBean(createReqVO, TrafficOpsMemberDO.class);
        opsMemberMapper.insert(opsMember);
        // 返回
        return opsMember.getId();
    }

    @Override
    public void updateOpsMember(TrafficOpsMemberSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMemberExists(updateReqVO.getId());
        // 更新
        TrafficOpsMemberDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMemberDO.class);
        opsMemberMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMember(String id) {
        // 校验存在
        validateOpsMemberExists(id);
        // 删除
        opsMemberMapper.deleteById(id);
    }

    private void validateOpsMemberExists(String id) {
        if (opsMemberMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MEMBER_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMemberDO getOpsMember(String id) {
        return opsMemberMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMemberDO> getOpsMemberPage(TrafficOpsMemberPageReqVO pageReqVO) {
        return opsMemberMapper.selectPage(pageReqVO);
    }

}