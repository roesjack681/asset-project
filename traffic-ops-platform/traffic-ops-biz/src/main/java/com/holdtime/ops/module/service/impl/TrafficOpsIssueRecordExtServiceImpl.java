package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssueDetailMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsIssueRecordMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsPoliceEquipmentMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsIssueRecordExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.*;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 领用记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficOpsIssueRecordExtServiceImpl implements TrafficOpsIssueRecordExtService {

    @Resource
    private TrafficOpsIssueRecordMapper opsIssueRecordMapper;
    @Resource
    private TrafficOpsIssueDetailMapper opsIssueDetailMapper;
    @Resource
    private TrafficOpsPoliceEquipmentMapper opsPoliceEquipmentMapper;
    @Resource
    private DeptService deptService;

    @Override
    public String createOpsIssueRecord(TrafficOpsIssueRecordExtSaveReqVO saveReqVO) {
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }
        // 1.保存申领记录
        TrafficOpsIssueRecordDO issueRecordDO = BeanUtils.toBean(saveReqVO, TrafficOpsIssueRecordDO.class);
        saveReqVO.setIssueTime(LocalDateTimeUtil.of(new Date()));
        if (StringUtils.isBlank(issueRecordDO.getId())) { // 新增
            opsIssueRecordMapper.insert(issueRecordDO);
        } else { // 修改
            // 校验存在
            validateOpsIssueRecordExists(issueRecordDO.getId());
            opsIssueRecordMapper.updateById(issueRecordDO);
        }
        // 保存申领详情
        // 删除之前关联的
        LambdaQueryWrapper<TrafficOpsIssueDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrafficOpsIssueDetailDO::getIssueBh, issueRecordDO.getId());
        opsIssueDetailMapper.delete(queryWrapper);

//        List<TrafficOpsIssueDetailDO> detailList = Optional.ofNullable(saveReqVO.getIssueDetailList()).orElse(new ArrayList<>()).stream()
//                .map(detailSaveReqVO -> {
//                    TrafficOpsIssueDetailDO detailDO = BeanUtils.toBean(detailSaveReqVO, TrafficOpsIssueDetailDO.class);
//                    detailDO.setIssueBh(issueRecordDO.getId());
//                    return detailDO;
//                })
//                .collect(Collectors.toList());
//        if (!CollectionUtils.isEmpty(detailList)) {
//            opsIssueDetailMapper.insertBatch(detailList);
//        }
        Optional.ofNullable(saveReqVO.getIssueDetailList()).orElse(new ArrayList<>()).forEach(detail -> {
            TrafficOpsIssueDetailDO detailDO = BeanUtils.toBean(detail, TrafficOpsIssueDetailDO.class);
            detailDO.setIssueBh(issueRecordDO.getId());
            opsIssueDetailMapper.insert(detailDO);

            // 更新使用人
            if (StringUtils.isNotBlank(detail.getUserBh())) {
                LambdaUpdateWrapper<TrafficOpsPoliceEquipmentDO> trafficOpsPoliceEquipmentMapper = new LambdaUpdateWrapper<>();
                trafficOpsPoliceEquipmentMapper.set(TrafficOpsPoliceEquipmentDO::getUserBh, detail.getUserBh());
                trafficOpsPoliceEquipmentMapper.set(TrafficOpsPoliceEquipmentDO::getUserName, detail.getUserName());
                trafficOpsPoliceEquipmentMapper.set(TrafficOpsPoliceEquipmentDO::getUseDeptBh, detail.getUseDeptBh());
                trafficOpsPoliceEquipmentMapper.set(TrafficOpsPoliceEquipmentDO::getUseDept, detail.getUseDept());
                trafficOpsPoliceEquipmentMapper.eq(TrafficOpsPoliceEquipmentDO::getId, detail.getEquId());
                opsPoliceEquipmentMapper.update(trafficOpsPoliceEquipmentMapper);
            }
        });

        return issueRecordDO.getId();
    }


    private void validateOpsIssueRecordExists(String id) {
        if (opsIssueRecordMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_ISSUE_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsIssueRecordRespVO getOpsIssueRecord(String id) {
        TrafficOpsIssueRecordRespVO opsIssueRecordRespVO = BeanUtils.toBean(opsIssueRecordMapper.selectById(id), TrafficOpsIssueRecordRespVO.class);
        LambdaQueryWrapper<TrafficOpsIssueDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrafficOpsIssueDetailDO::getIssueBh, id);
        opsIssueRecordRespVO.setIssueDetailList(BeanUtils.toBean(opsIssueDetailMapper.selectList(queryWrapper), TrafficOpsIssueDetailRespVO.class));
        return opsIssueRecordRespVO;

    }

    @Override
    public PageResult<TrafficOpsIssueRecordRespVO> getOpsIssueRecordPage(TrafficOpsIssueRecordPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsIssueRecordDO> queryWrapper = buildQueryWrapper(pageReqVO);
        PageResult<TrafficOpsIssueRecordDO> pageResult = opsIssueRecordMapper.selectPage(pageReqVO, queryWrapper);
        PageResult<TrafficOpsIssueRecordRespVO> result = BeanUtils.toBean(pageResult, TrafficOpsIssueRecordRespVO.class);
        enrichResultWithDetailCount(result);
        return result;
    }

    private LambdaQueryWrapperX<TrafficOpsIssueRecordDO> buildQueryWrapper(TrafficOpsIssueRecordPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsIssueRecordDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.likeIfPresent(TrafficOpsIssueRecordDO::getUserName, pageReqVO.getUserName())
                   .betweenIfPresent(TrafficOpsIssueRecordDO::getIssueTime, pageReqVO.getIssueTime())
                   .eqIfPresent(TrafficOpsIssueRecordDO::getIssueNum, pageReqVO.getIssueNum());

        if (pageReqVO.getUseDeptBh() != null) {
            Set<Long> deptIds = getDeptIds(pageReqVO);
            queryWrapper.in(TrafficOpsIssueRecordDO::getUseDeptBh, deptIds);
        }
        return queryWrapper;
    }

    private Set<Long> getDeptIds(TrafficOpsIssueRecordPageReqVO pageReqVO) {
        Set<Long> deptIds = new HashSet<>();
        deptIds.add(Long.valueOf(pageReqVO.getUseDeptBh()));
        if ("1".equals(pageReqVO.getIsIncludeChild())) {
            deptIds.addAll(convertSet(deptService.getChildDeptList(Long.valueOf(pageReqVO.getUseDeptBh())), DeptDO::getId));
        }
        return deptIds;
    }

    private void enrichResultWithDetailCount(PageResult<TrafficOpsIssueRecordRespVO> result) {
        result.getList().forEach(record -> {
            LambdaQueryWrapper<TrafficOpsIssueDetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TrafficOpsIssueDetailDO::getIssueBh, record.getId());
            record.setDetailIssueNum(opsIssueDetailMapper.selectCount(queryWrapper));
        });
    }

}