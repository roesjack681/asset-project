package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.PoliceMonitorPingDetailDO;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.TrafficEquipPingMonitorResultDO;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.TrafficEquipPingMonitorResultMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.PoliceMonitorPingDetailExtRespVO;
import com.holdtime.ops.module.model.vo.TrafficEquipPingMonitorResultPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficEquipPingMonitorResultSaveReqVO;
import com.holdtime.ops.module.service.TrafficEquipPingMonitorResultExtService;
import com.holdtime.ops.module.service.TrafficEquipPingMonitorResultService;
import com.holdtime.ops.module.utils.PingUtil;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 重点考核设备通断检测 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TrafficEquipPingMonitorResultExtServiceImpl implements TrafficEquipPingMonitorResultExtService {

    @Resource
    private TrafficEquipPingMonitorResultMapper equipPingMonitorResultMapper;

    @Override
    public TrafficEquipPingMonitorResultDO pingTrafficOpsElectricPolice(TrafficOpsElectricPoliceDO opsElectricPoliceDO,
                                                                        LocalDateTime occurrenceTime, String source,
                                                                        String batchNum, Integer pingCnt) {
        if  (opsElectricPoliceDO == null) {
            throw exception(OpsErrorCodeConstants.OPS_ELECTRIC_POLICE_NOT_EXISTS);
        }

        if (occurrenceTime == null) {
            occurrenceTime = DateUtil.toLocalDateTime(new Date());
        }

        if (pingCnt == null) {
            pingCnt = 10;
        }
        PingUtil.PingResult pingResult = PingUtil.pingWithLossRate(opsElectricPoliceDO.getIpAddr(), 1000, pingCnt);
        TrafficEquipPingMonitorResultDO pingDetailDO = new TrafficEquipPingMonitorResultDO();
        pingDetailDO.setEquId(opsElectricPoliceDO.getId());
        pingDetailDO.setIpAddr(opsElectricPoliceDO.getIpAddr());
        pingDetailDO.setOccurrenceTime(occurrenceTime);
        pingDetailDO.setPingStatus(pingResult.isReachable() ? "正常" : "IP不可达");
        pingDetailDO.setPingCnt(pingCnt);
        pingDetailDO.setLossRate(BigDecimal.valueOf(pingResult.getLossRate()));
        pingDetailDO.setSource(source);
        pingDetailDO.setExecBatchNum(batchNum);
        equipPingMonitorResultMapper.insert(pingDetailDO);
        return pingDetailDO;
    }
}