package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsIssueDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsIssueDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssueDetailMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 领用详情 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsIssueDetailServiceImpl implements TrafficOpsIssueDetailService {

    @Resource
    private TrafficOpsIssueDetailMapper opsIssueDetailMapper;

    @Override
    public String createOpsIssueDetail(TrafficOpsIssueDetailSaveReqVO createReqVO) {
        // 插入
        TrafficOpsIssueDetailDO opsIssueDetail = BeanUtils.toBean(createReqVO, TrafficOpsIssueDetailDO.class);
        opsIssueDetailMapper.insert(opsIssueDetail);
        // 返回
        return opsIssueDetail.getId();
    }

    @Override
    public void updateOpsIssueDetail(TrafficOpsIssueDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsIssueDetailExists(updateReqVO.getId());
        // 更新
        TrafficOpsIssueDetailDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsIssueDetailDO.class);
        opsIssueDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsIssueDetail(String id) {
        // 校验存在
        validateOpsIssueDetailExists(id);
        // 删除
        opsIssueDetailMapper.deleteById(id);
    }

    private void validateOpsIssueDetailExists(String id) {
        if (opsIssueDetailMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_ISSUE_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsIssueDetailDO getOpsIssueDetail(String id) {
        return opsIssueDetailMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsIssueDetailDO> getOpsIssueDetailPage(TrafficOpsIssueDetailPageReqVO pageReqVO) {
        return opsIssueDetailMapper.selectPage(pageReqVO);
    }

}