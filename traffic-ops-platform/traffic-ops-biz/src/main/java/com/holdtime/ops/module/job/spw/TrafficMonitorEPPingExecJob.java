package com.holdtime.ops.module.job.spw;

import cn.hutool.core.lang.UUID;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.holdtime.ops.module.conf.OpsProjectSpwConfig;
import com.holdtime.ops.module.entity.TrafficEquipPingMonitorResultDO;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.enums.HttpStatusEnum;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.dto.TrafficOpsSmsConfigSendDTO;
import com.holdtime.ops.module.model.vo.TrafficOpsSmsConfigSendReqVO;
import com.holdtime.ops.module.service.TrafficEquipPingMonitorResultExtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TrafficMonitorEPPingExecJob implements JobHandler {
    @Resource
    private TrafficOpsElectricPoliceMapper trafficOpsElectricPoliceMapper;
    @Resource
    private TrafficEquipPingMonitorResultExtService trafficEquipPingMonitorResultExtService;
    @Resource
    private OpsProjectSpwConfig opsProjectSpwConfig;

    @Override
    public String execute(String param) {
        scheduledPingEvent();
        return "分析完成！";
    }

    private void scheduledPingEvent() {
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getDjType, "电子警察");
        List<TrafficOpsElectricPoliceDO> opsElectricPoliceDOList = trafficOpsElectricPoliceMapper.selectList(lambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(opsElectricPoliceDOList)) {
            return;
        }

        String batchNum = UUID.randomUUID().toString(true);
        opsElectricPoliceDOList.forEach(electricPoliceDO -> {
            trafficEquipPingMonitorResultExtService.pingTrafficOpsElectricPolice(electricPoliceDO, null, OpsConstants.STRING_THREE, batchNum, 100);
        });
    }
}
