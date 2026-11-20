package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsIssuePersonService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIssuePersonDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssuePersonMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备领用人信息 Service 实现类
 *
 * @author 杨丽
 */
@Service
@Validated
public class TrafficOpsIssuePersonServiceImpl implements TrafficOpsIssuePersonService {

    @Resource
    private TrafficOpsIssuePersonMapper opsIssuePersonMapper;

    @Override
    public String createOpsIssuePerson(TrafficOpsIssuePersonSaveReqVO createReqVO) {
        // 插入
        TrafficOpsIssuePersonDO opsIssuePerson = BeanUtils.toBean(createReqVO, TrafficOpsIssuePersonDO.class);
        opsIssuePersonMapper.insert(opsIssuePerson);
        // 返回
        return opsIssuePerson.getId();
    }

    @Override
    public void updateOpsIssuePerson(TrafficOpsIssuePersonSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsIssuePersonExists(updateReqVO.getId());
        // 更新
        TrafficOpsIssuePersonDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsIssuePersonDO.class);
        opsIssuePersonMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsIssuePerson(String id) {
        // 校验存在
        validateOpsIssuePersonExists(id);
        // 删除
        opsIssuePersonMapper.deleteById(id);
    }

    private void validateOpsIssuePersonExists(String id) {
        if (opsIssuePersonMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_ISSUE_PERSON_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsIssuePersonDO getOpsIssuePerson(String id) {
        return opsIssuePersonMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsIssuePersonDO> getOpsIssuePersonPage(TrafficOpsIssuePersonPageReqVO pageReqVO) {
        return opsIssuePersonMapper.selectPage(pageReqVO);
    }

}