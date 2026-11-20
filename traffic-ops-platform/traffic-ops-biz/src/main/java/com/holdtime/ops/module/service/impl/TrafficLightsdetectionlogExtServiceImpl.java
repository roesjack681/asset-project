package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.alibaba.excel.util.StringUtils;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.TrafficCrossingMapper;
import com.holdtime.ops.module.entity.mapper.TrafficLightsMapper;
import com.holdtime.ops.module.entity.mapper.TrafficLightsdetectionlogMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalLightMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.TrafficLightsdetectionlogSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintenanceFileDTO;
import com.holdtime.ops.module.service.TrafficLightsdetectionlogExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 信号灯检测信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficLightsdetectionlogExtServiceImpl implements TrafficLightsdetectionlogExtService {

    @Resource
    private TrafficLightsdetectionlogMapper lightsdetectionlogMapper;
    @Resource
    private TrafficLightsMapper lightsMapper;
    @Resource
    private TrafficCrossingMapper crossingMapper;
    @Resource
    private TrafficOpsSignalLightMapper opsSignalLightMapper;

    @Override
    public Long createLightsdetectionlog(TrafficLightsdetectionlogSaveReqVO createReqVO) {
        String[] jgGroup;
        int num = 0;
        //判断信号灯检测结果
        if(StringUtils.isNotBlank(createReqVO.getJcjgex())){
            jgGroup = createReqVO.getJcjgex().split(";");
            for (String jg : jgGroup) {
                if (StringUtils.isNotBlank(jg) && jg.contains("BB")) {
                    num++;
                }
            }
        }
        if(num > 0){
            //查询信号灯信息，判断是否有常规黑灯
            TrafficLightsDO lights = lightsMapper.selectById(createReqVO.getSbid());
            if(lights != null){
                num = num - lights.getCh();
                if(num > 2){ //3桢检测均出问题代表信号灯有问题
                    createReqVO.setPdjg("1"); //长黑情况
                    // 插入
                    TrafficLightsdetectionlogDO lightsdetectionlog = BeanUtils.toBean(createReqVO, TrafficLightsdetectionlogDO.class);
                    lightsdetectionlogMapper.insert(lightsdetectionlog);
                    //查有问题的灯ID及路口id取出对应的路口名称和方向
                    TrafficCrossingDO crossing = crossingMapper.selectById(createReqVO.getLkid());
                    if (StringUtils.isNotBlank(crossing.getLkmc())) {
                        List<String> roadNameList = HanLPUtils.segmentForRoad(crossing.getLkmc());
                        LambdaQueryWrapperX<TrafficOpsSignalLightDO> signalLightDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
                        if (!CollectionUtils.isEmpty(roadNameList)) {
                            for (String roadName : roadNameList) {
                                signalLightDOLambdaQueryWrapperX.like(TrafficOpsSignalLightDO::getIntersectionName, roadName);
                            }
                        }
                        signalLightDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsSignalLightDO::getAdress,lights.getXhdfx());
                        List<TrafficOpsSignalLightDO> signalLightList= opsSignalLightMapper.selectList(signalLightDOLambdaQueryWrapperX);
                        if (!CollectionUtils.isEmpty(signalLightList)) {
                            List<TrafficOpsSignalLightDO> sList = new ArrayList<>();
                            for (TrafficOpsSignalLightDO signalLight : signalLightList) {
                                signalLight.setStatus(OpsConstants.EQU_STATUS_GZ);
                                signalLight.setUpdateTime(LocalDateTimeUtil.of(new Date()));
                                sList.add(signalLight);
                            }
                            opsSignalLightMapper.updateBatch(sList);
                        }
                    }
                }
            }
        }
        return null;
    }

}