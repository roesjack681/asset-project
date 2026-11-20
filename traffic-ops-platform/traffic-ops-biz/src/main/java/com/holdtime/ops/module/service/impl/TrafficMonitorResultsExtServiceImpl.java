package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDO;
import com.holdtime.ops.module.entity.TrafficMonitorResultsHistoryDO;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsHistoryMapper;
import com.holdtime.ops.module.entity.mapper.TrafficMonitorResultsMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.mapper.OpsAnalysisExtMapper;
import com.holdtime.ops.module.mapper.TrafficMonitorResultsExtMapper;
import com.holdtime.ops.module.model.dto.TrafficOpsMemeberCameraDTO;
import com.holdtime.ops.module.model.dto.TrafficOpsMonitorResultDTO;
import com.holdtime.ops.module.model.vo.TrafficMonitorResultsExtSaveReqVO;
import com.holdtime.ops.module.service.TrafficMonitorResultsExtService;
import com.xingyuv.captcha.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备监测结果 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Transactional(rollbackFor = Exception.class)
public class TrafficMonitorResultsExtServiceImpl implements TrafficMonitorResultsExtService {

    @Resource
    private TrafficMonitorResultsMapper monitorResultsMapper;
    @Resource
    private OpsAnalysisExtMapper opsAnalysisExtMapper;
    @Resource
    private TrafficOpsMemberMapper trafficOpsMemberMapper;
    @Resource
    private TrafficMonitorResultsHistoryMapper monitorResultsHistoryMapper;
    @Resource
    private TrafficMonitorResultsExtMapper monitorResultsExtMapper;

    @Override
    public String createMonitorResults(TrafficMonitorResultsExtSaveReqVO createReqVO) {
        if(!createReqVO.getEquType().equals("1") && !createReqVO.getEquType().equals("2")){
            throw exception(OpsErrorCodeConstants.MONITOR_RESULTS_PARAM_MISSING);
        }
        // 插入
        TrafficMonitorResultsDO monitorResults = BeanUtils.toBean(createReqVO, TrafficMonitorResultsDO.class);
        monitorResults.setOccurrenceTime(DateUtil.toLocalDateTime(createReqVO.getOccurrenceTime()));
        monitorResultsMapper.insert(monitorResults);
        // 返回
        return monitorResults.getId();
    }

    @Override
    public PageResult<TrafficOpsMemeberCameraDTO> analysisMonitorResult(Page<?> page, String startDate, String endDate) {
        IPage<TrafficOpsMonitorResultDTO> resultDTOIPage = opsAnalysisExtMapper.analysisMonitorResult(page, startDate, endDate);
        if (resultDTOIPage == null || CollectionUtils.isEmpty(resultDTOIPage.getRecords())) {
            return new PageResult<>();
        }

        Map<String, List<TrafficOpsMonitorResultDTO>> resultMap = resultDTOIPage.getRecords().stream().collect(
                Collectors.groupingBy(dto -> StringUtils.isBlank(dto.getMemberBh()) ? "未绑定" : dto.getMemberBh(), LinkedHashMap::new, Collectors.toList()));

        LambdaQueryWrapper<TrafficOpsMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TrafficOpsMemberDO::getId, resultMap.keySet());
        List<TrafficOpsMemberDO> memberList = trafficOpsMemberMapper.selectList(queryWrapper);
        Map<String, TrafficOpsMemberDO> memberMap = Optional.ofNullable(memberList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(TrafficOpsMemberDO::getId, member -> member));

        List<TrafficOpsMemeberCameraDTO> memeberCameraDTOList = new LinkedList<>();
        for (String memberId : resultMap.keySet() ) {
            TrafficOpsMemeberCameraDTO memeberCameraDTO = new TrafficOpsMemeberCameraDTO();
            memeberCameraDTO.setMemberBh(memberId);
            memeberCameraDTO.setMemberName(memberId);
            TrafficOpsMemberDO memberDO = memberMap.get(memberId);
            if (memberDO != null) {

                String serialNum = "";
                if (memberDO.getSerialNum() != null) {
                    serialNum = String.format("%03d", memberDO.getSerialNum());
                }
                String memberName = memberDO.getRoadName() + "/" + memberDO.getIntersectionName() + "/" + memberDO.getAdress() + "/" + serialNum;
                memeberCameraDTO.setMemberName(memberName);
            }

            List<TrafficOpsMonitorResultDTO> resultList = resultMap.get(memberId);
            memeberCameraDTO.setMonitorResultDTOCnt(resultList.size());
            Optional.of(resultList).orElse(new ArrayList<>()).forEach(dto -> {
                TrafficOpsMemeberCameraDTO monitorResultDTO = BeanUtils.toBean(memeberCameraDTO, TrafficOpsMemeberCameraDTO.class);
                monitorResultDTO.setMonitorResultDTO(dto);
                memeberCameraDTOList.add(monitorResultDTO);
            });

        }
        return new PageResult<>(memeberCameraDTOList, page.getTotal());
    }

    @Override
    public void archiveTrafficMonitorResults() {
        Date date = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));

        LambdaQueryWrapperX<TrafficMonitorResultsDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.lt(TrafficMonitorResultsDO::getOccurrenceTime, date);
        List<TrafficMonitorResultsDO>  resultsDOList = monitorResultsMapper.selectList(lambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(resultsDOList)) {
            return;
        }
        List<TrafficMonitorResultsHistoryDO> instHistoryList = resultsDOList.stream().map(equResult -> BeanUtils.toBean(equResult, TrafficMonitorResultsHistoryDO.class)).collect(Collectors.toList());
        monitorResultsHistoryMapper.insert(instHistoryList);

        monitorResultsExtMapper.deleteTrafficMonitorResults(date);
    }
}