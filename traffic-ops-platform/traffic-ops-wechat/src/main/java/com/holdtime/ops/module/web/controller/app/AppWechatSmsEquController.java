package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.alibaba.excel.util.StringUtils;
import com.holdtime.ops.module.conf.WechatConfig;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.TrafficOpsSmsElectricPolicePageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsSmsElectricPoliceRespVO;
import com.holdtime.ops.module.utils.StringEncoder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Tag(name = "用户 APP - 短信链接点击查询设备信息")
@RestController
@RequestMapping
@Validated
@Slf4j
public class AppWechatSmsEquController {
    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;

    @GetMapping("/s/r")
    @Operation(summary = "短信链接点击查询设备信息")
    public void getResult(HttpServletResponse response, @RequestParam("p") String param) {
        String redirectUrl = "";
        try {
            redirectUrl += WechatConfig.getVueHost() + "/#/equStatusList?param=" + param;
            response.sendRedirect(redirectUrl);
        } catch  (Exception e) {
            log.error(e.getMessage(), e);
            try {
                redirectUrl += WechatConfig.getVueHost() + "/#/equStatusList?param=" + param;
                response.sendRedirect(redirectUrl);
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    @GetMapping("/traffic/sms-equ/smsEquOffLine")
    @Operation(summary = "根据参数查询设备信息")
    public CommonResult<List<TrafficOpsSmsElectricPolicePageReqVO>> smsEquOffLine(@RequestParam("param") String param) {

        try {
            // 1. 参数校验
            if (StringUtils.isBlank(param)) {
                throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
            }

            // 2. 解析参数
            Map<String, List<String>> groupMap = parseParam(param);

            // 3. 处理业务逻辑
            List<TrafficOpsSmsElectricPolicePageReqVO> result = processGroups(groupMap);

            return CommonResult.success(result);
        } catch (IllegalArgumentException e) {
            log.error("参数格式错误", e);
            return CommonResult.error(GlobalErrorCodeConstants.UNKNOWN.getCode(),"参数格式错误");
        } catch (Exception e) {
            log.error("查询设备信息失败", e);
            return CommonResult.error(GlobalErrorCodeConstants.UNKNOWN.getCode(),"查询设备信息失败");
        }
    }

    /**
     * 解析参数并分组
     */
    private Map<String, List<String>> parseParam(String param) {
        // 参数解码
        param = StringEncoder.decode(param);
        String[] paramArr = param.split(";");
        Map<String, List<String>> groupMap = new HashMap<>();

        for (String group : paramArr) {
            String[] groupParts = group.split("-");
            if (groupParts.length != 2) {
                throw new IllegalArgumentException("参数格式不正确: " + group);
            }

            String groupId = groupParts[0];
            String[] equNumbers = groupParts[1].split(",");
            groupMap.put(groupId, Arrays.asList(equNumbers));
        }

        return groupMap;
    }

    /**
     * 处理分组数据
     */
    private List<TrafficOpsSmsElectricPolicePageReqVO> processGroups(
            Map<String, List<String>> groupMap) {

        List<TrafficOpsSmsElectricPolicePageReqVO> result = new ArrayList<>();

        // 处理所有分组
        groupMap.forEach((groupId, equNumbers) -> {
            List<TrafficOpsSmsElectricPoliceRespVO> policeList = equNumbers.stream()
                    .map(equid -> {
                        TrafficOpsElectricPoliceDO policeDO = opsElectricPoliceMapper.selectById(equid);
                        TrafficOpsSmsElectricPoliceRespVO respVO = new TrafficOpsSmsElectricPoliceRespVO();
                        BeanUtils.copyProperties(policeDO, respVO);
                        return respVO;
                    })
                    .collect(Collectors.toList());

            TrafficOpsSmsElectricPolicePageReqVO reqVO = new TrafficOpsSmsElectricPolicePageReqVO();
            reqVO.setSendType(groupId);
            reqVO.setElectricPoliceList(policeList);
            result.add(reqVO);
        });

        return result;
    }

//    @GetMapping("/smsEquOffLine/{param}")
//    @Operation(summary = "根据参数查询设备信息")
//    public List<TrafficOpsSmsElectricPolicePageReqVO> smsEquOffLine(HttpServletResponse response, @PathVariable("param") String param) {
//        List<TrafficOpsSmsElectricPolicePageReqVO> listVo = null;
//        List<TrafficOpsSmsElectricPoliceRespVO> electricPoliceList = null;
//
//        // 解析 param 格式如 "1-101,102,103;2-104,105"
//        String[] paramArr = param.split(";");
//
//        // 使用 Map 存储分组数据，key 是分组ID（1或2），value 是对应的设备号列表
//        Map<String, List<String>> groupMap = new HashMap<>();
//
//        for (String group : paramArr) {
//            // 每组格式如 "1-101,102,103"
//            String[] groupParts = group.split("-");
//            if (groupParts.length != 2) {
//                continue; // 跳过无效格式
//            }
//            String groupId = groupParts[0]; // 分组ID（1或2）
//            String[] equNumbers = groupParts[1].split(","); // 设备号数组
//            // 存入 Map
//            groupMap.put(groupId, Arrays.asList(equNumbers));
//        }
//
//        // 获取分组1的设备号列表
//        electricPoliceList = new ArrayList<>();
//        List<String> group1EquNumbers = groupMap.getOrDefault("1", Collections.emptyList());
//        for (String equid : group1EquNumbers) {
//            TrafficOpsElectricPoliceDO opsElectricPolice = opsElectricPoliceMapper.selectById(equid);
//            TrafficOpsSmsElectricPoliceRespVO respVO = new TrafficOpsSmsElectricPoliceRespVO();
//            BeanUtils.copyProperties(opsElectricPolice, respVO);
//            electricPoliceList.add(respVO);
//        }
//        TrafficOpsSmsElectricPolicePageReqVO reqVO = new TrafficOpsSmsElectricPolicePageReqVO();
//        reqVO.setSendType("1");
//        reqVO.setElectricPoliceList(electricPoliceList);
//        listVo.add(reqVO);
//        // 获取分组2的设备号列表
//        electricPoliceList = new ArrayList<>();
//        List<String> group2EquNumbers = groupMap.getOrDefault("2", Collections.emptyList());
//        for (String equid : group2EquNumbers) {
//            TrafficOpsElectricPoliceDO opsElectricPolice = opsElectricPoliceMapper.selectById(equid);
//            TrafficOpsSmsElectricPoliceRespVO respVO = new TrafficOpsSmsElectricPoliceRespVO();
//            BeanUtils.copyProperties(opsElectricPolice, respVO);
//            electricPoliceList.add(respVO);
//        }
//        reqVO.setSendType("2");
//        reqVO.setElectricPoliceList(electricPoliceList);
//        listVo.add(reqVO);
//        return listVo;
//    }

}