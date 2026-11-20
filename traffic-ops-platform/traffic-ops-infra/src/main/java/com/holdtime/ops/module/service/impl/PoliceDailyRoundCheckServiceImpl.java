package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.PoliceDailyRoundCheckService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceDailyRoundCheckDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.PoliceDailyRoundCheckMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 日常巡检信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceDailyRoundCheckServiceImpl implements PoliceDailyRoundCheckService {

    @Resource
    private PoliceDailyRoundCheckMapper dailyRoundCheckMapper;

    @Override
    public String createDailyRoundCheck(PoliceDailyRoundCheckSaveReqVO createReqVO) {
        // 插入
        PoliceDailyRoundCheckDO dailyRoundCheck = BeanUtils.toBean(createReqVO, PoliceDailyRoundCheckDO.class);
        dailyRoundCheckMapper.insert(dailyRoundCheck);
        // 返回
        return dailyRoundCheck.getId();
    }

    @Override
    public void updateDailyRoundCheck(PoliceDailyRoundCheckSaveReqVO updateReqVO) {
        // 校验存在
        validateDailyRoundCheckExists(updateReqVO.getId());
        // 更新
        PoliceDailyRoundCheckDO updateObj = BeanUtils.toBean(updateReqVO, PoliceDailyRoundCheckDO.class);
        dailyRoundCheckMapper.updateById(updateObj);
    }

    @Override
    public void deleteDailyRoundCheck(String id) {
        // 校验存在
        validateDailyRoundCheckExists(id);
        // 删除
        dailyRoundCheckMapper.deleteById(id);
    }

    private void validateDailyRoundCheckExists(String id) {
        if (dailyRoundCheckMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.DAILY_ROUND_CHECK_NOT_EXISTS);
        }
    }

    @Override
    public PoliceDailyRoundCheckDO getDailyRoundCheck(String id) {
        return dailyRoundCheckMapper.selectById(id);
    }

    @Override
    public PageResult<PoliceDailyRoundCheckDO> getDailyRoundCheckPage(PoliceDailyRoundCheckPageReqVO pageReqVO) {
        return dailyRoundCheckMapper.selectPage(pageReqVO);
    }

}