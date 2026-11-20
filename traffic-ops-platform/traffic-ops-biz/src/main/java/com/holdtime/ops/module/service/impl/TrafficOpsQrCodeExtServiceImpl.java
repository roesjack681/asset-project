package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.holdtime.ops.module.conf.OpsProjectQrCodeConfig;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import com.holdtime.ops.module.entity.TrafficOpsQrcodeGenRecordDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsQrcodeGenRecordMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsQrcodeMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsQrCodeExtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
public class TrafficOpsQrCodeExtServiceImpl implements TrafficOpsQrCodeExtService {
    @Resource
    private TrafficOpsQrcodeGenRecordMapper trafficOpsQrcodeGenRecordMapper;
    @Resource
    private TrafficOpsQrcodeMapper trafficOpsQrcodeMapper;
    @Resource
    private OpsProjectQrCodeConfig opsProjectConfig;
    @Resource
    FileService fileService;

    @Override
    public void genEquipQrCode(TrafficOpsQrcodeGenRecordExtSaveReqVO saveReqVO) {
        // 保存生成记录
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }
        // 保存二维码生成记录信息
        TrafficOpsQrcodeGenRecordDO genRecordDO = BeanUtils.toBean(saveReqVO, TrafficOpsQrcodeGenRecordDO.class);
        genRecordDO.setAffiliatedUnit(OpsConstants.STRING_ONE);
        genRecordDO.setGenDate(DateUtil.toLocalDateTime(new Date()));
        genRecordDO.setDirectionCnt(JSONUtil.toJsonStr(saveReqVO.getDirectionCntList()));
        if (StringUtils.isEmpty(genRecordDO.getId())) { // 新增
            trafficOpsQrcodeGenRecordMapper.insert(genRecordDO);
        } else {
            validateQrcodeGenRecordExists(genRecordDO.getId());

            trafficOpsQrcodeGenRecordMapper.updateById(genRecordDO);

            // 软删二维码信息
            LambdaQueryWrapper<TrafficOpsQrcodeDO> delRegionPolygonWrapper = new LambdaQueryWrapper<>();
            delRegionPolygonWrapper.eq(TrafficOpsQrcodeDO::getGenRecordId, genRecordDO.getId());
            trafficOpsQrcodeMapper.delete(delRegionPolygonWrapper);
        }

        // 保存二维码信息
        List<TrafficOpsQrcodeDO> qrcodeDOList = Optional.ofNullable(saveReqVO.getDirectionCntList())
                .orElseGet(ArrayList::new)
                .stream()
                .flatMap(directionCntDTO -> {
                    int genCnt = directionCntDTO.getGenCnt();
                    if (genCnt <= 0) {
                        return Stream.empty();
                    }

                    int initialSerialNum = queryMaxSerialNum(OpsConstants.STRING_ONE, null, genRecordDO.getRoadId(), directionCntDTO.getDirection());

                    return IntStream.range(0, genCnt)
                            .mapToObj(i -> {
                                TrafficOpsQrcodeDO qrcodeDO = new TrafficOpsQrcodeDO();
                                String id = IdUtil.getSnowflake().nextId() + "";
                                String qrcodeContent = opsProjectConfig.getApiHost() +
                                        "/traffic/wechat/scanEquipQrcode/" + id;

                                qrcodeDO.setId(id);
                                qrcodeDO.setAffiliatedUnit(OpsConstants.STRING_ONE);
                                qrcodeDO.setRoadId(genRecordDO.getRoadId());
                                qrcodeDO.setRaodName(genRecordDO.getRoadName());
                                qrcodeDO.setDirection(directionCntDTO.getDirection());
                                qrcodeDO.setGenRecordId(genRecordDO.getId());
                                qrcodeDO.setOpsType(genRecordDO.getOpsType());
                                qrcodeDO.setQrcodeContent(qrcodeContent);
                                qrcodeDO.setQrcodeFile(genQrcode(id, qrcodeContent,
                                        opsProjectConfig.getWidth(), opsProjectConfig.getHeight()));
                                qrcodeDO.setSerialNum(initialSerialNum + i);

                                return qrcodeDO;
                            });
                })
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(qrcodeDOList)) {
            trafficOpsQrcodeMapper.insertBatch(qrcodeDOList);
        }
    }

    private String genQrcode(String qrCodeId, String content, Integer width, Integer height)  {
        if (width == null) {
            width = 300;
        }

        if (height == null) {
            height = 300;
        }
        byte[] qrcodeBytes = QrCodeUtil.generatePng(content, width, height);
        // 1.存储上传的导入文件
        return fileService.createFile(qrCodeId + ".png", null, qrcodeBytes);
    }

    private Integer queryMaxSerialNum(String affiliatedUnit, String serialPrefix, String roadId, String direction) {
        LambdaQueryWrapper<TrafficOpsQrcodeDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(affiliatedUnit)) {
            queryWrapper.eq(TrafficOpsQrcodeDO::getAffiliatedUnit, affiliatedUnit);
        }

        if (StringUtils.isNotBlank(serialPrefix)) {
            queryWrapper.eq(TrafficOpsQrcodeDO::getSerialPrefix, serialPrefix);
        }

        if (StringUtils.isNotBlank(roadId)) {
            queryWrapper.eq(TrafficOpsQrcodeDO::getRoadId, roadId);
        }

        if (StringUtils.isNotBlank(direction)) {
            queryWrapper.eq(TrafficOpsQrcodeDO::getDirection, direction);
        }

        queryWrapper.orderByDesc(TrafficOpsQrcodeDO::getSerialNum);
        List<TrafficOpsQrcodeDO> qrcodeDOList = trafficOpsQrcodeMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(qrcodeDOList)) {
            return 1;
        }
        return qrcodeDOList.get(0).getSerialNum() + 1;
    }

    private void validateQrcodeGenRecordExists(String id) {
        if (trafficOpsQrcodeGenRecordMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_QRCODE_GEN_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public void deleteEquipQrCodeByGenId(String genRecordId) {
        validateQrcodeGenRecordExists(genRecordId);

        // 软删二维码信息
        LambdaQueryWrapper<TrafficOpsQrcodeDO> delQrcodeWrapper = new LambdaQueryWrapper<>();
        delQrcodeWrapper.eq(TrafficOpsQrcodeDO::getGenRecordId, genRecordId);
        trafficOpsQrcodeMapper.delete(delQrcodeWrapper);

        // 软删二维码生成记录信息
        trafficOpsQrcodeGenRecordMapper.deleteById(genRecordId);
    }

    @Override
    public List<TrafficOpsQrcodePrintRespVO> queryOpsQrcodePrintDataList(String printType, String keyword) {
        LambdaQueryWrapper<TrafficOpsQrcodeDO> queryWrapper = new LambdaQueryWrapper<>();
        if (OpsConstants.PRINT_TYPE_SINGLE.equals(printType) ) {
            queryWrapper.eq(TrafficOpsQrcodeDO::getId, keyword);
        } else {
            queryWrapper.eq(TrafficOpsQrcodeDO::getGenRecordId, keyword);
        }
        queryWrapper.orderByAsc(TrafficOpsQrcodeDO::getSerialNum);
        List<TrafficOpsQrcodeDO> qrcodeDOList = trafficOpsQrcodeMapper.selectList(queryWrapper);
        return BeanUtils.toBean(qrcodeDOList, TrafficOpsQrcodePrintRespVO.class);
    }

    @Override
    public void genPoliceEquipQrCode(PoliceOpsQrcodeGenRecordExtSaveReqVO saveReqVO) {
        // 保存生成记录
        if (saveReqVO == null) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        // 保存二维码生成记录信息
        TrafficOpsQrcodeGenRecordDO genRecordDO = BeanUtils.toBean(saveReqVO, TrafficOpsQrcodeGenRecordDO.class);
        genRecordDO.setGenDate(DateUtil.toLocalDateTime(new Date()));
        genRecordDO.setAffiliatedUnit(OpsConstants.STRING_TWO);
        Map<String, Object> genParam = new HashMap<>();
        genParam.put("genCnt", saveReqVO.getGenCnt());
        genParam.put("geoCode", saveReqVO.getGeoCode());
        genRecordDO.setDirectionCnt(JSONUtil.toJsonStr(genParam));
        if (StringUtils.isEmpty(genRecordDO.getId())) { // 新增
            trafficOpsQrcodeGenRecordMapper.insert(genRecordDO);
        } else {
            validateQrcodeGenRecordExists(genRecordDO.getId());

            trafficOpsQrcodeGenRecordMapper.updateById(genRecordDO);

            // 软删二维码信息
            LambdaQueryWrapper<TrafficOpsQrcodeDO> delRegionPolygonWrapper = new LambdaQueryWrapper<>();
            delRegionPolygonWrapper.eq(TrafficOpsQrcodeDO::getGenRecordId, genRecordDO.getId());
            trafficOpsQrcodeMapper.delete(delRegionPolygonWrapper);
        }

        // 保存二维码信息
        String serialPrefix = saveReqVO.getGeoCode().substring(saveReqVO.getGeoCode().length() - 2);
        int initialSerialNum = queryMaxSerialNum(OpsConstants.STRING_TWO, serialPrefix, null, null);
        List<TrafficOpsQrcodeDO> qrcodeDOList = IntStream.range(0, saveReqVO.getGenCnt())
                .mapToObj(i -> {
                    TrafficOpsQrcodeDO qrcodeDO = new TrafficOpsQrcodeDO();
                    String id = String.valueOf(IdUtil.getSnowflake().nextId());
                    String qrcodeContent = String.format("%s/traffic/wechat/scanPoliceEquipQrcode/%s",
                            opsProjectConfig.getApiHost(), id);

                    qrcodeDO.setId(id);
                    qrcodeDO.setAffiliatedUnit(OpsConstants.STRING_TWO);
                    qrcodeDO.setRoadId(genRecordDO.getRoadId());
                    qrcodeDO.setRaodName(genRecordDO.getRoadName());
                    qrcodeDO.setGenRecordId(genRecordDO.getId());
                    qrcodeDO.setOpsType(genRecordDO.getOpsType());
                    qrcodeDO.setQrcodeContent(qrcodeContent);
                    qrcodeDO.setQrcodeFile(genQrcode(id, qrcodeContent,
                            opsProjectConfig.getWidth(), opsProjectConfig.getHeight()));
                    qrcodeDO.setSerialPrefix(serialPrefix);
                    qrcodeDO.setSerialNum(initialSerialNum + i);
                    return qrcodeDO;
                })
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(qrcodeDOList)) {
            trafficOpsQrcodeMapper.insertBatch(qrcodeDOList);
        }
    }
}