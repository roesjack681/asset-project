package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.holdtime.ops.module.entity.PoliceDailyRoundCheckDO;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.PoliceDailyRoundCheckMapper;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.OpsElectricPoliceRespVO;
import com.holdtime.ops.module.model.vo.PoliceDailyRoundCheckExtRespVO;
import com.holdtime.ops.module.model.vo.PoliceDailyRoundCheckPageReqVO;
import com.holdtime.ops.module.model.vo.PoliceDailyRoundCheckSaveReqVO;
import com.holdtime.ops.module.service.PoliceDailyRoundCheckExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 日常巡检信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceDailyRoundCheckExtServiceImpl implements PoliceDailyRoundCheckExtService {

    @Resource
    private PoliceDailyRoundCheckMapper dailyRoundCheckMapper;
    @Resource
    private PoliceOpsElectricPoliceMapper electricPoliceMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public String createDailyRoundCheck(PoliceDailyRoundCheckSaveReqVO createReqVO) {
        // 插入
        PoliceDailyRoundCheckDO dailyRoundCheck = BeanUtils.toBean(createReqVO, PoliceDailyRoundCheckDO.class);
        AdminUserRespDTO currentUser = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        dailyRoundCheck.setCheckPersonId(currentUser.getId() + "");
        dailyRoundCheck.setCheckPerson(currentUser.getNickname());
        dailyRoundCheck.setCheckDate(LocalDateTimeUtil.of(new Date()));
        //所属单位：公安
        dailyRoundCheck.setAffiliatedUnit(OpsConstants.STRING_TWO);
        dailyRoundCheck.setCheckType("内勤");
        dailyRoundCheckMapper.insert(dailyRoundCheck);
        // 返回
        return dailyRoundCheck.getId();
    }

    @Override
    public PoliceDailyRoundCheckExtRespVO getDailyRoundCheck(String id) {
        PoliceDailyRoundCheckDO dailyRoundCheck = dailyRoundCheckMapper.selectById(id);
        if (dailyRoundCheck == null) {
            throw exception(OpsErrorCodeConstants.DAILY_ROUND_CHECK_NOT_EXISTS);
        }

        PoliceDailyRoundCheckExtRespVO respVO = BeanUtils.toBean(dailyRoundCheck, PoliceDailyRoundCheckExtRespVO.class);
        if (StringUtils.isNotEmpty(dailyRoundCheck.getEquId())) {
            PoliceOpsElectricPoliceDO electricPolice = electricPoliceMapper.selectById(dailyRoundCheck.getEquId());
            respVO.setElectricPoliceRespVO(BeanUtils.toBean(electricPolice, OpsElectricPoliceRespVO.class));
        }
        return respVO;
    }

    @Override
    public PageResult<PoliceDailyRoundCheckExtRespVO> getDailyRoundCheckPage(PoliceDailyRoundCheckPageReqVO pageReqVO) {
        PageResult<PoliceDailyRoundCheckDO> doPageResult = dailyRoundCheckMapper.selectPage(pageReqVO);

        PageResult<PoliceDailyRoundCheckExtRespVO> pageResult = BeanUtils.toBean(doPageResult, PoliceDailyRoundCheckExtRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach(dailyRoundCheck -> {
            if (StringUtils.isNotEmpty(dailyRoundCheck.getEquId())) {
                PoliceOpsElectricPoliceDO electricPolice = electricPoliceMapper.selectById(dailyRoundCheck.getEquId());
                dailyRoundCheck.setElectricPoliceRespVO(BeanUtils.toBean(electricPolice, OpsElectricPoliceRespVO.class));
            }
        });
        return BeanUtils.toBean(pageResult, PoliceDailyRoundCheckExtRespVO.class);
    }


}