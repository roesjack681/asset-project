package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PublicUserAreaGroupService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PublicUserAreaGroupDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PublicUserAreaGroupMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 区域用户组信息 Service 实现类
 *
 * @author 杨丽
 */
@Service
@Validated
public class PublicUserAreaGroupServiceImpl implements PublicUserAreaGroupService {

    @Resource
    private PublicUserAreaGroupMapper userAreaGroupMapper;

    @Override
    public String createUserAreaGroup(PublicUserAreaGroupSaveReqVO createReqVO) {
        // 插入
        PublicUserAreaGroupDO userAreaGroup = BeanUtils.toBean(createReqVO, PublicUserAreaGroupDO.class);
        userAreaGroupMapper.insert(userAreaGroup);
        // 返回
        return userAreaGroup.getId();
    }

    @Override
    public void updateUserAreaGroup(PublicUserAreaGroupSaveReqVO updateReqVO) {
        // 校验存在
        validateUserAreaGroupExists(updateReqVO.getId());
        // 更新
        PublicUserAreaGroupDO updateObj = BeanUtils.toBean(updateReqVO, PublicUserAreaGroupDO.class);
        userAreaGroupMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserAreaGroup(String id) {
        // 校验存在
        validateUserAreaGroupExists(id);
        // 删除
        userAreaGroupMapper.deleteById(id);
    }

    private void validateUserAreaGroupExists(String id) {
        if (userAreaGroupMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.USER_AREA_GROUP_NOT_EXISTS);
        }
    }

    @Override
    public PublicUserAreaGroupDO getUserAreaGroup(String id) {
        return userAreaGroupMapper.selectById(id);
    }

    @Override
    public PageResult<PublicUserAreaGroupDO> getUserAreaGroupPage(PublicUserAreaGroupPageReqVO pageReqVO) {
        return userAreaGroupMapper.selectPage(pageReqVO);
    }

}