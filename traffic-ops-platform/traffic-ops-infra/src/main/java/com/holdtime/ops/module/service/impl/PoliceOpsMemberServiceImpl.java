package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceOpsMemberService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceOpsMemberDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PoliceOpsMemberMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 杆件 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PoliceOpsMemberServiceImpl implements PoliceOpsMemberService {

    @Resource
    private PoliceOpsMemberMapper opsMemberMapper;

    @Override
    public String createOpsMember(PoliceOpsMemberSaveReqVO createReqVO) {
        // 插入
        PoliceOpsMemberDO opsMember = BeanUtils.toBean(createReqVO, PoliceOpsMemberDO.class);
        opsMemberMapper.insert(opsMember);
        // 返回
        return opsMember.getId();
    }

    @Override
    public void updateOpsMember(PoliceOpsMemberSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMemberExists(updateReqVO.getId());
        // 更新
        PoliceOpsMemberDO updateObj = BeanUtils.toBean(updateReqVO, PoliceOpsMemberDO.class);
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
            throw exception(OpsErrorCodeConstants.POLICE_OPS_MEMBER_NOT_EXISTS);
        }
    }

    @Override
    public PoliceOpsMemberDO getOpsMember(String id) {
        return opsMemberMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceOpsMemberDO> getOpsMemberPage(PoliceOpsMemberPageReqVO pageReqVO) {
        return opsMemberMapper.selectPage(pageReqVO);
    }

}