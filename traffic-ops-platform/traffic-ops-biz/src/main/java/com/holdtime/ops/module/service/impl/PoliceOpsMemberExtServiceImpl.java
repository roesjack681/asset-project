package com.holdtime.ops.module.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.mapper.PoliceOpsEquipInfoExtMapper;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.PoliceOpsMemberExtService;
import com.holdtime.ops.module.service.TrafficOpsRegisterExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import com.holdtime.ops.module.utils.TextConsistencyExtractor;
import com.holdtime.ops.module.utils.TextSimilarityAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备维保信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PoliceOpsMemberExtServiceImpl implements PoliceOpsMemberExtService {
    @Resource
    private PoliceOpsMemberMapper policeOpsMemberMapper;
    @Resource
    private TrafficOpsQrcodeMapper trafficOpsQrcodeMapper;
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private TrafficOpsRegisterExtService opsRegisterExtService;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private PoliceOpsEquipInfoExtMapper policeOpsEquipInfoExtMapper;
    @Resource
    private PoliceMonitorResultsMapper policeMonitorResultsMapper;

    @Override
    public String saveOpsMemberInfo(PoliceOpsMemberExtSaveReqVO saveReqVO) {
        // 保存生成记录
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }
        // 保存杆件信息
        PoliceOpsMemberDO opsMemberDO = BeanUtils.toBean(saveReqVO, PoliceOpsMemberDO.class);
        if (StringUtils.isNotBlank(saveReqVO.getCreator())) {
            AppTrafficOpsRegisterRespVO register = opsRegisterExtService.getOpsRegister(saveReqVO.getCreator());
            if (register != null) {
                if (StringUtils.isEmpty(opsMemberDO.getId())) {
                    opsMemberDO.setCreator(register.getUserId());
                } else {
                    opsMemberDO.setUpdater(register.getUserId());
                }

            }
        }

        if (StringUtils.isEmpty(opsMemberDO.getId())) { // 新增
            LambdaQueryWrapperX<PoliceOpsMemberDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            opsDOLambdaQueryWrapperX.eq(PoliceOpsMemberDO::getEquName, saveReqVO.getEquName());
            if (policeOpsMemberMapper.selectCount(opsDOLambdaQueryWrapperX) > 0) {
                throw exception(OpsErrorCodeConstants.POLICE_OPS_MEMBER_EXISTS);
            }
            policeOpsMemberMapper.insert(opsMemberDO);
        } else {
            validateOpsMemberExists(opsMemberDO.getId());
            policeOpsMemberMapper.updateById(opsMemberDO);
        }

        // 绑定电警信息
        if (!CollectionUtils.isEmpty(saveReqVO.getElectricPoliceIdList())) {
            for (String electricPoliceId : saveReqVO.getElectricPoliceIdList()) {
                PoliceOpsElectricPoliceDO electricPoliceDO = policeOpsElectricPoliceMapper.selectById(electricPoliceId);
                if (electricPoliceDO != null) {
                    electricPoliceDO.setMemberBh(opsMemberDO.getId());
                    policeOpsElectricPoliceMapper.updateById(electricPoliceDO);
                }
            }
        }

        // 新增电警信息
        List<PoliceOpsElectricPoliceDO> instElectricPoliceList = Optional.ofNullable(saveReqVO.getInstElectricPoliceInfoList()).orElse(new ArrayList<>()).stream().map(opsElectricPoliceSaveReqVO -> {
            PoliceOpsElectricPoliceDO electricPoliceDO = BeanUtils.toBean(opsElectricPoliceSaveReqVO, PoliceOpsElectricPoliceDO.class);
            electricPoliceDO.setMemberBh(opsMemberDO.getId());
            return electricPoliceDO;
        }).collect(Collectors.toList());
        if (!CollectionUtil.isEmpty(instElectricPoliceList)) {
            policeOpsElectricPoliceMapper.insertBatch(instElectricPoliceList);
        }

        // 拆除电警
        if (!CollectionUtil.isEmpty(saveReqVO.getRemoveElectricPoliceIdList())) {
            LambdaUpdateWrapper<PoliceOpsElectricPoliceDO> electricPoliceWrapper = new LambdaUpdateWrapper<>();
            electricPoliceWrapper.set(PoliceOpsElectricPoliceDO::getStatus, OpsConstants.EQU_STATUS_CC);
            electricPoliceWrapper.set(PoliceOpsElectricPoliceDO::getMemberBh, opsMemberDO.getId());
            electricPoliceWrapper.in(PoliceOpsElectricPoliceDO::getId, saveReqVO.getRemoveElectricPoliceIdList());
            policeOpsElectricPoliceMapper.update(electricPoliceWrapper);
        }


        return opsMemberDO.getId();
    }

    @Override
    public PoliceOpsMemberExtRespVO getOpsMember(String memberId) {
        PoliceOpsMemberDO opsMember = policeOpsMemberMapper.selectById(memberId);
        PoliceOpsMemberExtRespVO respVO = BeanUtils.toBean(opsMember, PoliceOpsMemberExtRespVO.class);
        if (respVO != null) {
            // 查询QrCode
            if (StringUtils.isNotBlank(respVO.getEquNumber())) {
                TrafficOpsQrcodeDO opsQrcodeDO = trafficOpsQrcodeMapper.selectById(respVO.getEquNumber());
                if (opsQrcodeDO != null) {
                    respVO.setQrCodeUrl(opsQrcodeDO.getQrcodeFile());
                }
            }

            // 查询维护人
            if (StringUtils.isNotBlank(opsMember.getCreator())) {
                AdminUserDO adminUserDO = adminUserMapper.selectById(opsMember.getCreator());
                if (adminUserDO != null)  {
                    respVO.setCreator(adminUserDO.getNickname());
                }
            }

            // 查询电警信息
            LambdaQueryWrapper<PoliceOpsElectricPoliceDO> electricPoliceWrapper = new LambdaQueryWrapperX<>();
            electricPoliceWrapper.eq(PoliceOpsElectricPoliceDO::getMemberBh, respVO.getId());
            List<PoliceOpsElectricPoliceDO> electricPoliceList = policeOpsElectricPoliceMapper.selectList(electricPoliceWrapper);
            // 遍历电警信息，关联查询PoliceMonitorResultsDO的status
            if (!CollectionUtils.isEmpty(electricPoliceList)) {
                List<String> equIds = electricPoliceList.stream().map(PoliceOpsElectricPoliceDO::getId).collect(Collectors.toList());
                LambdaQueryWrapper<PoliceMonitorResultsDO> monitorResultsWrapper = new LambdaQueryWrapper<>();
                monitorResultsWrapper.in(PoliceMonitorResultsDO::getEquId, equIds);
                List<PoliceMonitorResultsDO> monitorResultsList = policeMonitorResultsMapper.selectList(monitorResultsWrapper);
                Map<String, String> equIdToStatusMap = monitorResultsList.stream()
                        .collect(Collectors.toMap(PoliceMonitorResultsDO::getEquId, PoliceMonitorResultsDO::getStatus, (v1, v2) -> v1));
                electricPoliceList.forEach(electricPolice -> {
                    String status = equIdToStatusMap.get(electricPolice.getId());
                    if (status != null) {
                        electricPolice.setStatus(status);
                    }
                });
            }

            respVO.setElectricPoliceVOList(BeanUtils.toBean(electricPoliceList, PoliceOpsElectricPoliceRespVO.class));
        }
        return respVO;
    }

    @Override
    public PageResult<PoliceOpsMemberExtRespVO> getOpsMemberPage(PoliceOpsMemberPageReqVO pageReqVO, String isPaging) {
        LambdaQueryWrapperX<PoliceOpsMemberDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<PoliceOpsMemberDO>()
                .likeIfPresent(PoliceOpsMemberDO::getEquName, pageReqVO.getEquName())
                .eqIfPresent(PoliceOpsMemberDO::getEquNumber, pageReqVO.getEquNumber())
                .eqIfPresent(PoliceOpsMemberDO::getEquType, pageReqVO.getEquType())
                .eqIfPresent(PoliceOpsMemberDO::getJibLength, pageReqVO.getJibLength())
                .likeIfPresent(PoliceOpsMemberDO::getRoadName, pageReqVO.getRoadName())
                .eqIfPresent(PoliceOpsMemberDO::getMemberType, pageReqVO.getMemberType())
                .eqIfPresent(PoliceOpsMemberDO::getLng, pageReqVO.getLng())
                .eqIfPresent(PoliceOpsMemberDO::getLat, pageReqVO.getLat())
                .betweenIfPresent(PoliceOpsMemberDO::getCreateTime, pageReqVO.getCreateTime())
                .eqIfPresent(PoliceOpsMemberDO::getAdress, pageReqVO.getAdress())
                .betweenIfPresent(PoliceOpsMemberDO::getInstallDate, pageReqVO.getInstallDate())
                .eqIfPresent(PoliceOpsMemberDO::getManufacturer, pageReqVO.getManufacturer())
                .betweenIfPresent(PoliceOpsMemberDO::getDeletedTime, pageReqVO.getDeletedTime())
                .eqIfPresent(PoliceOpsMemberDO::getUnit, pageReqVO.getUnit())
                .eqIfPresent(PoliceOpsMemberDO::getHight, pageReqVO.getHight())
                .eqIfPresent(PoliceOpsMemberDO::getBarLength, pageReqVO.getBarLength())
                .likeIfPresent(PoliceOpsMemberDO::getDominion, pageReqVO.getDominion())
                .eqIfPresent(PoliceOpsMemberDO::getStatus, pageReqVO.getStatus())
                .orderByDesc(PoliceOpsMemberDO::getId);

//        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
//            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
//            if (!CollectionUtils.isEmpty(roadNameList)) {
//                for (String roadName : roadNameList) {
//                    opsDOLambdaQueryWrapperX.like(TrafficOpsMemberDO::getIntersectionName, roadName);
//                }
//            }
//        }

        if(!StringUtil.isEmpty(isPaging)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }

        if (StringUtils.isNotBlank(pageReqVO.getCreator())) {
            opsDOLambdaQueryWrapperX.exists("select 1 from system_users u where traffic_ops_member.creator = u.id and u.nickname like '%" + pageReqVO.getCreator() + "%'");
        }

        PageResult<PoliceOpsMemberDO> pageResult = policeOpsMemberMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);
        PageResult<PoliceOpsMemberExtRespVO> resultPage = BeanUtils.toBean(pageResult, PoliceOpsMemberExtRespVO.class);
        Optional.ofNullable(resultPage.getList()).orElse(new ArrayList<>()).forEach(opsMemberResp -> {
            if (StringUtils.isNotBlank(opsMemberResp.getCreator())) {
                AdminUserDO adminUserDO = adminUserMapper.selectById(opsMemberResp.getCreator());
                if (adminUserDO != null)  {
                    opsMemberResp.setCreator(adminUserDO.getNickname());
                }
            }

            if (StringUtils.isNotBlank(opsMemberResp.getEquNumber() )) {
                TrafficOpsQrcodeDO opsQrcodeDO = trafficOpsQrcodeMapper.selectById(opsMemberResp.getEquNumber());
                if (opsQrcodeDO != null) {
                    opsMemberResp.setQrCodeUrl(opsQrcodeDO.getQrcodeFile());
                }
            }
        });
        return resultPage;
    }

    @Override
    public List<String> queryNearByPoliceMember(String lng, String lat) {
        if (StringUtils.isBlank(lng) || StringUtils.isBlank(lat)) {
            return null;
        }

        List<Map<String, String>> resultMap = policeOpsEquipInfoExtMapper.queryNearByPoliceMember(lng, lat);
        List<String> resultList = Optional.ofNullable(resultMap)
                .orElse(Collections.emptyList())
                .stream()
                .map(map -> map.get("equ_name"))
                .collect(Collectors.toList());

        // 使用并行流处理相似文本分析
        return resultList.stream().map(equName -> {
                    List<TextSimilarityAnalyzer.SimilarText> similarTexts =
                            TextSimilarityAnalyzer.extractSimilarTexts(
                                    equName, resultList, 0.8, "levenshtein");

                    return similarTexts.stream()
                            .map(TextSimilarityAnalyzer.SimilarText::getText)
                            .collect(Collectors.toList());
                }).map(similarAddress ->
                        TextConsistencyExtractor.extractConsistentText(
                                similarAddress, 1.0)).distinct().collect(Collectors.toList());
    }

    @Override
    public List<PoliceOpsMemberExtRespVO> autocompletePoliceOpsMemberList(String equName) {
        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        List<String> stringList = HanLPUtils.segmentForRoad(equName);
        if (!CollectionUtils.isEmpty(stringList)) {
            for (String str : stringList) {
                lambdaQueryWrapperX.like(PoliceOpsElectricPoliceDO::getEquName, str);
            }
        }

        Page<PoliceOpsElectricPoliceDO> page = new Page<>(1, 20);
        List<PoliceOpsElectricPoliceDO> list = policeOpsElectricPoliceMapper.selectList(page, lambdaQueryWrapperX);
        return list.stream()
                .map(policeOpsElectricPoliceDO -> {
                    PoliceOpsMemberExtRespVO respVO = new PoliceOpsMemberExtRespVO();
                    respVO.setEquName(policeOpsElectricPoliceDO.getEquName());
                    return respVO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PoliceOpsElectricPoliceRespVO> queryPoliceElectricPolice(String equName) {
        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.likeIfPresent(PoliceOpsElectricPoliceDO::getEquName, equName);

        List<PoliceOpsElectricPoliceDO> list = policeOpsElectricPoliceMapper.selectList(lambdaQueryWrapperX);

        Optional.ofNullable(list).orElse(new ArrayList<>()).forEach(opsElectricPoliceResp -> {
            LambdaQueryWrapperX<PoliceMonitorResultsDO> queryWrapper = new LambdaQueryWrapperX<>();
            queryWrapper.eq(PoliceMonitorResultsDO::getIpAddr,opsElectricPoliceResp.getIpAddr());
            List<PoliceMonitorResultsDO> monitorResultsVOList = policeMonitorResultsMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(monitorResultsVOList)) {
                String resultImg = monitorResultsVOList.get(0).getResultImg();
                opsElectricPoliceResp.setEquImg(resultImg); // 或其他默认值
            }
        });

        return BeanUtils.toBean(list, PoliceOpsElectricPoliceRespVO.class);
    }

    @Override
    public PageResult<PoliceOpsMemberExtRespVO> getOpsMemberPage(PoliceOpsMemberPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PoliceOpsMemberDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<PoliceOpsMemberDO>()
                .eqIfPresent(PoliceOpsMemberDO::getEquNumber, pageReqVO.getEquNumber())
                .likeIfPresent(PoliceOpsMemberDO::getEquName, pageReqVO.getEquName())
                .eqIfPresent(PoliceOpsMemberDO::getEquType, pageReqVO.getEquType())
                .likeIfPresent(PoliceOpsMemberDO::getRoadName, pageReqVO.getRoadName())
                .likeIfPresent(PoliceOpsMemberDO::getIntersectionName, pageReqVO.getIntersectionName())
                .likeIfPresent(PoliceOpsMemberDO::getAdress, pageReqVO.getAdress())
                .betweenIfPresent(PoliceOpsMemberDO::getInstallDate, pageReqVO.getInstallDate())
                .likeIfPresent(PoliceOpsMemberDO::getManufacturer, pageReqVO.getManufacturer())
                .likeIfPresent(PoliceOpsMemberDO::getUnit, pageReqVO.getUnit())
                .eqIfPresent(PoliceOpsMemberDO::getMemberType, pageReqVO.getMemberType())
                .likeIfPresent(PoliceOpsMemberDO::getDominion, pageReqVO.getDominion())
                .eqIfPresent(PoliceOpsMemberDO::getStatus, pageReqVO.getStatus())
                .betweenIfPresent(PoliceOpsMemberDO::getCreateTime, pageReqVO.getCreateTime())
//                .eqIfPresent(PoliceOpsMemberDO::getSerialNum, pageReqVO.getSerialNum())
                .eqIfPresent(PoliceOpsMemberDO::getIsCheck, pageReqVO.getIsCheck())
                .likeIfPresent(PoliceOpsMemberDO::getCheckRemark, pageReqVO.getCheckRemark())
                .orderByDesc(PoliceOpsMemberDO::getId);

        if(!StringUtil.isEmpty(pageReqVO.getEquName())){
            List<String> equNameList = HanLPUtils.segmentForRoad(pageReqVO.getEquName());
            if (!CollectionUtils.isEmpty(equNameList)) {
                for (String equName : equNameList) {
                    opsDOLambdaQueryWrapperX.like(PoliceOpsMemberDO::getEquName, equName);
                }
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getCreator())) {
            opsDOLambdaQueryWrapperX.exists("select 1 from system_users u where police_ops_member.creator = u.id and u.nickname like '%" + pageReqVO.getCreator() + "%'");
        }

        if (StringUtils.isNotBlank(pageReqVO.getSerialNumber())) {
            opsDOLambdaQueryWrapperX.exists("SELECT 1 from traffic_ops_qrcode q where  police_ops_member.equ_number = q.id and q.affiliated_unit = '2' and CONCAT(q.serial_prefix, lpad( q.serial_num, 5, '0' )) like '%" + pageReqVO.getSerialNumber() + "%'");
        }

        PageResult<PoliceOpsMemberDO> pageResult = policeOpsMemberMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);
        PageResult<PoliceOpsMemberExtRespVO> resultPage = BeanUtils.toBean(pageResult, PoliceOpsMemberExtRespVO.class);
        Optional.ofNullable(resultPage.getList()).orElse(new ArrayList<>()).forEach(opsMemberResp -> {
            if (StringUtils.isNotBlank(opsMemberResp.getCreator())) {
                AdminUserDO adminUserDO = adminUserMapper.selectById(opsMemberResp.getCreator());
                if (adminUserDO != null)  {
                    opsMemberResp.setCreator(adminUserDO.getNickname());
                }
            }

            if (StringUtils.isNotBlank(opsMemberResp.getEquNumber() )) {
                TrafficOpsQrcodeDO opsQrcodeDO = trafficOpsQrcodeMapper.selectById(opsMemberResp.getEquNumber());
                if (opsQrcodeDO != null) {
                    opsMemberResp.setQrCodeUrl(opsQrcodeDO.getQrcodeFile());
                }
            }

            // 查询电警数量
            LambdaQueryWrapper<PoliceOpsElectricPoliceDO> electricPoliceWrapper = new LambdaQueryWrapperX<>();
            electricPoliceWrapper.eq(PoliceOpsElectricPoliceDO::getMemberBh, opsMemberResp.getId());
            Long cnt = policeOpsElectricPoliceMapper.selectCount(electricPoliceWrapper);
            opsMemberResp.setEctricPoliceCnt(cnt);
        });
        return resultPage;
    }

    @Override
    public void deleteOpsMember(String id) {
        // 校验杆件存在
        validateOpsMemberExists(id);

        // 更新电警信息的memberBh
        LambdaUpdateWrapper<PoliceOpsElectricPoliceDO> electricPoliceWrapper = new LambdaUpdateWrapper<>();
        electricPoliceWrapper.set(PoliceOpsElectricPoliceDO::getMemberBh, null);
        electricPoliceWrapper.eq(PoliceOpsElectricPoliceDO::getMemberBh, id);
        policeOpsElectricPoliceMapper.update(electricPoliceWrapper);

        // 软删除杆件
        policeOpsMemberMapper.deleteById(id);
    }

    private void validateOpsMemberExists(String id) {
        if (policeOpsMemberMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MEMBER_NOT_EXISTS);
        }
    }
}