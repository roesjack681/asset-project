package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import com.holdtime.ops.module.entity.TrafficVioSurveilScreenErrDO;
import com.holdtime.ops.module.entity.ViewVioSurveilScreenErrInfoDO;
import com.holdtime.ops.module.entity.mapper.TrafficVioSurveilScreenErrMapper;
import com.holdtime.ops.module.entity.mapper.ViewVioSurveilScreenErrInfoMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.TrafficVioSurveilScreenErrSaveReqVO;
import com.holdtime.ops.module.model.vo.ViewVioSurveilScreenErrInfoExtPageReqVO;
import com.holdtime.ops.module.service.ViewVioSurveilScreenErrInfoExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 集指无效违法记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewVioSurveilScreenErrInfoExtServiceImpl implements ViewVioSurveilScreenErrInfoExtService {

    @Resource
    private ViewVioSurveilScreenErrInfoMapper viewVioSurveilScreenErrInfoMapper;
    @Resource
    private TrafficVioSurveilScreenErrMapper trafficVioSurveilScreenErrMapper;

    @Override
    public void updateVioSurveilScreenErrInfo(@Valid TrafficVioSurveilScreenErrSaveReqVO updateReqVO) {
        // 校验存在
        validateVioSurveilScreenErrInfoExists(updateReqVO.getId());
        // 更新
        TrafficVioSurveilScreenErrDO updateObj = BeanUtils.toBean(updateReqVO, TrafficVioSurveilScreenErrDO.class);
        updateObj.setReviewDate(DateUtil.toLocalDateTime(new Date()));
        updateObj.setReviewer(SecurityFrameworkUtils.getLoginUserNickname());
        trafficVioSurveilScreenErrMapper.updateById(updateObj);
    }

    private void validateVioSurveilScreenErrInfoExists(String id) {
        if (trafficVioSurveilScreenErrMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.VIO_SURVEIL_SCREEN_ERR_NOT_EXISTS);
        }
    }

    @Override
    public PageResult<ViewVioSurveilScreenErrInfoDO> getViewVioSurveilScreenErrInfoPage(ViewVioSurveilScreenErrInfoExtPageReqVO pageReqVO) {
        LambdaQueryWrapperX<ViewVioSurveilScreenErrInfoDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<ViewVioSurveilScreenErrInfoDO>()
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getSbbh, pageReqVO.getSbbh())
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getHphm, pageReqVO.getHphm())
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getHpzl, pageReqVO.getHpzl())
                .betweenIfPresent(ViewVioSurveilScreenErrInfoDO::getWfsj, pageReqVO.getWfsj())
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getWfxw, pageReqVO.getWfxw())
                .likeIfPresent(ViewVioSurveilScreenErrInfoDO::getCwyy, pageReqVO.getCwyy())
                .likeIfPresent(ViewVioSurveilScreenErrInfoDO::getReviewResult, pageReqVO.getReviewResult())
                .likeIfPresent(ViewVioSurveilScreenErrInfoDO::getReviewer, pageReqVO.getReviewer())
                .betweenIfPresent(ViewVioSurveilScreenErrInfoDO::getLrsj, pageReqVO.getLrsj())
                .betweenIfPresent(ViewVioSurveilScreenErrInfoDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(ViewVioSurveilScreenErrInfoDO::getReviewDate, pageReqVO.getReviewDate());

        if (StringUtils.isNotBlank(pageReqVO.getIsConsistentAuditReason())) {
            if (OpsConstants.STRING_ONE.equals(pageReqVO.getIsConsistentAuditReason())) {
                lambdaQueryWrapperX.exists("select 1 from view_vio_surveil_screen_err_info e where e.id = view_vio_surveil_screen_err_info.id and e.cwyy like concat( '%', view_vio_surveil_screen_err_info.review_result, '%' )");
            } else {
                lambdaQueryWrapperX.exists("select 1 from view_vio_surveil_screen_err_info e where e.id = view_vio_surveil_screen_err_info.id and e.cwyy not like concat( '%', view_vio_surveil_screen_err_info.review_result, '%' )");
            }
        }

        StringBuilder unitChildSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(pageReqVO.getUnitList())) {
            for (String unit : pageReqVO.getUnitList()) {
                if (StringUtils.isNotBlank(unitChildSql.toString())) {
                    unitChildSql.append(" or ");
                }
                unitChildSql.append(" e.unit = '").append(unit).append("'");
            }
            String existsSql = "SELECT 1 from traffic_ops_electric_police e where view_vio_surveil_screen_err_info.sbbh = e.sbbh ";
            existsSql = existsSql + " and (" + unitChildSql + ")";
            lambdaQueryWrapperX.exists(existsSql);
        }

        if (StringUtils.isNotBlank(pageReqVO.getOptType())) {
            if (OpsConstants.STRING_ONE.equals(pageReqVO.getOptType())) { // 查询待审记录
                lambdaQueryWrapperX.isNull(ViewVioSurveilScreenErrInfoDO::getReviewResult);
                // 每次同步近10天的数据，审核10天前的数据
                lambdaQueryWrapperX.le(ViewVioSurveilScreenErrInfoDO::getWfsj, DateUtil.endOfDay(DateUtil.offsetDay(new Date(), -11)));
                lambdaQueryWrapperX.orderByAsc(ViewVioSurveilScreenErrInfoDO::getWfsj);
            } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getOptType())) {
                lambdaQueryWrapperX.isNotNull(ViewVioSurveilScreenErrInfoDO::getReviewResult);
                lambdaQueryWrapperX.orderByDesc(ViewVioSurveilScreenErrInfoDO::getId);
            }
        } else {
            lambdaQueryWrapperX.orderByDesc(ViewVioSurveilScreenErrInfoDO::getId);
        }

        if (StringUtils.isNotBlank(pageReqVO.getIsReview())) {
            if (OpsConstants.STRING_ONE.equals(pageReqVO.getIsReview())) {
                lambdaQueryWrapperX.isNotNull(ViewVioSurveilScreenErrInfoDO::getReviewResult);
            } else {
                lambdaQueryWrapperX.isNull(ViewVioSurveilScreenErrInfoDO::getReviewResult);
            }
        }


        return viewVioSurveilScreenErrInfoMapper.selectPage(pageReqVO, lambdaQueryWrapperX);
    }

}