package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailDO;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import com.holdtime.ops.module.entity.TrafficOpsSignalLightDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsGuardrailMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalLightMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.TrafficOpsBigDataBaseRespVO;
import com.holdtime.ops.module.service.JasperReportService;
import com.holdtime.ops.module.service.TrafficOpsBigDataService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperRunManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
@Slf4j
public class JasperReportServiceImpl implements JasperReportService {
    @Resource
    private TrafficOpsBigDataService trafficOpsBigDataService;
    @Resource
    private TrafficOpsSignalLightMapper trafficOpsSignalLightMapper;
    @Resource
    private TrafficOpsSignalMapper trafficOpsSignalMapper;
    @Resource
    private TrafficOpsGuardrailMapper opsGuardrailMapper;
    @Resource
    private DataSource dataSource;


    @Override
    public byte[] generateOpsDetailReport(String dominion, String equStatus,String zcType) {
        TrafficOpsBigDataBaseRespVO opsBigDataBaseRespVO = trafficOpsBigDataService.getOpsBigDataBase(null,dominion,equStatus);

        int sumDeviceCnt = 0;
        sumDeviceCnt += Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getSignalNum()) ? "0" : opsBigDataBaseRespVO.getSignalNum());
        sumDeviceCnt += Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getMarklineNum()) ? "0" : opsBigDataBaseRespVO.getMarklineNum());
        sumDeviceCnt += Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getBoardNum()) ? "0" : opsBigDataBaseRespVO.getBoardNum());
        sumDeviceCnt += Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getSignalLightNum()) ? "0" : opsBigDataBaseRespVO.getSignalLightNum());
        sumDeviceCnt += Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getRadarNum()) ? "0" : opsBigDataBaseRespVO.getRadarNum());
        sumDeviceCnt += Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getElectricPoliceNum()) ? "0" : opsBigDataBaseRespVO.getElectricPoliceNum());
        sumDeviceCnt += Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getMemberNum()) ? "0" : opsBigDataBaseRespVO.getMemberNum());
        sumDeviceCnt += Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getBigLedNum()) ? "0" : opsBigDataBaseRespVO.getBigLedNum());
        sumDeviceCnt += Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getGuardrailNum()) ? "0" : opsBigDataBaseRespVO.getGuardrailNum());

//        int divisor = sumDeviceCnt;
//        if (divisor == 0) {
//            divisor = 1;
//        }

        Map<String, Object> reportParamMap = new HashMap<>();

        reportParamMap.put("gxdw", StringUtils.isBlank(dominion) ? "" : dominion);
        reportParamMap.put("status", StringUtils.isBlank(equStatus) ? "" : equStatus);
        reportParamMap.put("zctype", StringUtils.isBlank(zcType) ? "" : zcType);
        reportParamMap.put("tjr", SecurityFrameworkUtils.getLoginUserNickname());
        reportParamMap.put("tjsj", DateUtil.format(new Date(), "yyyy年MM月dd日 HH:mm:ss"));

        // 设备总数
        reportParamMap.put("sbzs", sumDeviceCnt);
        // 信号机数量
        reportParamMap.put("xhjzs", Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getSignalNum()) ? "0" : opsBigDataBaseRespVO.getSignalNum()));
//        reportParamMap.put("xhjzb", calcDeviceRatio(opsBigDataBaseRespVO.getSignalNum(), divisor));
        // 统计联网/不联网
        LambdaQueryWrapperX<TrafficOpsSignalDO> signalDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        signalDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsSignalDO::getDominion,dominion);
        signalDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsSignalDO::getStatus, equStatus);
        List<TrafficOpsSignalDO> signalList = trafficOpsSignalMapper.selectList(signalDOLambdaQueryWrapperX);
        AtomicInteger ylwCnt = new AtomicInteger(0);
        AtomicInteger wlwCnt = new AtomicInteger(0);
        Optional.ofNullable(signalList).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getNetworkStatus())).forEach(trafficOpsSignaltDO -> {
            if ("是".equals(trafficOpsSignaltDO.getNetworkStatus())) {
                ylwCnt.addAndGet(1);
            } else {
                wlwCnt.addAndGet(1);
            }
        });
        reportParamMap.put("xhjylwzs", ylwCnt);
        reportParamMap.put("xhjwlwzs", wlwCnt);
        // 标线数量
        reportParamMap.put("bxzs", Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getMarklineNum()) ? "0" : opsBigDataBaseRespVO.getMarklineNum()));
//        reportParamMap.put("bxzb", calcDeviceRatio(opsBigDataBaseRespVO.getMarklineNum(), divisor));
        // 标志牌数量
        reportParamMap.put("bzpzs", Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getBoardNum()) ? "0" : opsBigDataBaseRespVO.getBoardNum()));
//        reportParamMap.put("zbpzb", calcDeviceRatio(opsBigDataBaseRespVO.getBoardNum(), divisor));
        // 信号灯
        reportParamMap.put("xhdzs", Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getSignalLightNum()) ? "0" : opsBigDataBaseRespVO.getSignalLightNum()));
//        reportParamMap.put("xhdzb", calcDeviceRatio(opsBigDataBaseRespVO.getSignalLightNum(), divisor));
        // 统计国标/非国标
        LambdaQueryWrapperX<TrafficOpsSignalLightDO> signalLightDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        signalLightDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsSignalLightDO::getDominion,dominion);
        signalLightDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsSignalLightDO::getStatus, equStatus);
        List<TrafficOpsSignalLightDO> list = trafficOpsSignalLightMapper.selectList(signalLightDOLambdaQueryWrapperX);
        AtomicInteger gbCnt = new AtomicInteger(0);
        AtomicInteger fgbCnt = new AtomicInteger(0);
        Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getLedType())).forEach(trafficOpsSignalLightDO -> {
            if (trafficOpsSignalLightDO.getLedType().startsWith("G")) {
                gbCnt.addAndGet(1);
            } else if (trafficOpsSignalLightDO.getLedType().startsWith("F")) {
                fgbCnt.addAndGet(1);
            }
        });
        reportParamMap.put("xhdgbzs", gbCnt.get());
        reportParamMap.put("xhdfgbzs", fgbCnt.get());
        // 雷达
        reportParamMap.put("ldzs", Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getRadarNum()) ? "0" : opsBigDataBaseRespVO.getRadarNum()));
//        reportParamMap.put("ldzb", calcDeviceRatio(opsBigDataBaseRespVO.getRadarNum(), divisor));
        // 电警
        reportParamMap.put("djzs", Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getElectricPoliceNum()) ? "0" : opsBigDataBaseRespVO.getElectricPoliceNum()));
//        reportParamMap.put("djzb", calcDeviceRatio(opsBigDataBaseRespVO.getElectricPoliceNum(), divisor));
        // 杆件
        reportParamMap.put("gjzs", Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getMemberNum()) ? "0" : opsBigDataBaseRespVO.getMemberNum()));
//        reportParamMap.put("gjzb", calcDeviceRatio(opsBigDataBaseRespVO.getMemberNum(), divisor));
        // 大屏
        reportParamMap.put("dpzs", Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getBigLedNum()) ? "0" : opsBigDataBaseRespVO.getBigLedNum()));
//        reportParamMap.put("dpzb", calcDeviceRatio(opsBigDataBaseRespVO.getBigLedNum(), divisor));
        // 护栏
        reportParamMap.put("hlzs", Integer.parseInt(StringUtils.isBlank(opsBigDataBaseRespVO.getGuardrailNum()) ? "0" : opsBigDataBaseRespVO.getGuardrailNum()));
//        reportParamMap.put("hlzb", calcDeviceRatio(opsBigDataBaseRespVO.getGuardrailNum(), divisor));
        //护栏
        LambdaQueryWrapperX<TrafficOpsGuardrailDO> guardraiDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        guardraiDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsGuardrailDO::getDominion,dominion);
        guardraiDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsGuardrailDO::getStatus, equStatus);
        List<TrafficOpsGuardrailDO> guardrailDOList = opsGuardrailMapper.selectList(guardraiDOLambdaQueryWrapperX);
//        TODO
//        int hlps = Optional.ofNullable(guardrailDOList).orElse(new ArrayList<>()).stream().filter(o -> StringUtils.isNotBlank(o.getPieces())).mapToInt(o -> Integer.parseInt(o.getPieces())).sum();
//        BigDecimal hlzms = Optional.ofNullable(guardrailDOList).orElse(new ArrayList<>()).stream().filter(o -> StringUtils.isNotBlank(o.getTotalLength())).map(o -> new BigDecimal(o.getTotalLength())).reduce(BigDecimal.ZERO, BigDecimal::add);
//        hlzms = hlzms.setScale(2, RoundingMode.DOWN);
        reportParamMap.put("hlps", 0 + "");
        reportParamMap.put("hlzms", 0 + "");

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("report/traffic_ops_ledger_report.jasper");

            return JasperRunManager.runReportToPdf(stream, reportParamMap, conn);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_EXPORT_ERROR);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    private String calcDeviceRatio(String deviceCnt, Integer sumDeviceCnt) {
        BigDecimal total = new BigDecimal(sumDeviceCnt); // 总数值
        BigDecimal part = new BigDecimal(deviceCnt); // 部分数值
        BigDecimal ratio = part.multiply(new BigDecimal(100)).divide(total, 2, RoundingMode.HALF_UP); // 计算占比，保留两位小数
        return ratio + "%";
    }
}
