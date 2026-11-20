package com.holdtime.ops.module.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.module.infra.dal.dataobject.config.ConfigDO;
import cn.iocoder.yudao.module.infra.service.config.ConfigService;
import com.holdtime.ops.module.entity.ApiAuthDO;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.ApiAuthExtService;
import com.holdtime.ops.module.utils.SortUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;

/**
 * 项目名称： dmv-biz<br>
 * 功能描述： ApiAuthCheckAop概述：接口授权检查切点类<br>
 * 创  建 人： yanxl<br>
 * 创建日期： 2021年04月13日<br>
 * 修  改 人：  <br>
 * 修改时间：<br>
 * 修改内容：<br>
 * 版      本： 1.0.0
 */
@Aspect
@Component
@Slf4j
public class ApiAuthCheckAop {
    @Resource
    private HttpServletRequest request;

    @Resource
    private ConfigService configService;

    @Resource
    private ApiAuthExtService apiAuthExtService;

    //Controller层切点
    @Pointcut("@annotation(com.holdtime.ops.module.aop.annotation.ApiAuthCheck)")
    public void controllerAspect() {
    }

    @Around("controllerAspect()")
    public Object controllerLog(ProceedingJoinPoint point) {

        String ts = request.getParameter("ts");
        String sign = request.getParameter("sign");
        String user = request.getParameter("user");
        if (!StringUtils.hasText(ts)
                || !StringUtils.hasText(sign)
                || !StringUtils.hasText(user)) {
            return error(OpsErrorCodeConstants.API_VISIT_NOT_ALLOW);
        }

        long timestamp = Long.parseLong(ts);

        Object result = null;
        try {
            ConfigDO configDO = configService.getConfigByKey("API_EXPIRE_SECOND");
            long apiExpireSecond = 3000L;
            if (configDO != null) {
                try {
                    apiExpireSecond = Long.parseLong(configDO.getValue());
                } catch (NumberFormatException ignored) {
                }
            }
            boolean isExpire = Math.abs(System.currentTimeMillis() - timestamp) > (apiExpireSecond * 1000);
            if (isExpire) {
                return error(OpsErrorCodeConstants.API_VISIT_TIMESTAMP_EXPIRE);
            }

            //获取RequestBody参数，此处需要配合过滤器处理request后才能获取
            String bodyParam = ServletUtil.getBody(request);
//            if (StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE)) {
//                bodyParam = new String(StreamUtils.copyToByteArray(request.getInputStream()), request.getCharacterEncoding());
//            } else if (StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
//                bodyParam = JSONUtil.toJsonStr(HttpServletTools.getParameterMap(request));
//            }
            //获取RequestParam参数
            Map<String, String[]> queryParams = request.getParameterMap();

            //获取PathVariable参数
            ServletWebRequest webRequest = new ServletWebRequest(request, null);
            @SuppressWarnings("unchecked")
            Map<String, String> reqPathMap = (Map<String, String>) webRequest.getAttribute(
                    HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);

            // authCode#PathVariable#body#queryParams#timestamp
            ApiAuthDO apiAuthDO = apiAuthExtService.queryApiAuthInfo(user);
            if (apiAuthDO == null) {
                return error(OpsErrorCodeConstants.API_VISIT_NOT_ALLOW);
            }

            if (DateUtil.toLocalDateTime(new Date()).isAfter(apiAuthDO.getExpireDate())) {
                return error(OpsErrorCodeConstants.API_AUTH_CODE_EXPIRE);
            }

            if (!validateData(bodyParam, queryParams, reqPathMap, apiAuthDO.getAuthCode(), ts, sign)) {
                return error(OpsErrorCodeConstants.API_VISIT_AUTH_NOT_VALIDATED);
            }

            //返回结果
            result = point.proceed();
        } catch (Exception e) {
            log.error("验签异常：" + e.getMessage(), e);
        } catch (Throwable e) {
            log.error("切面方法执行发生异常：" + e.getMessage(), e);
        }
        return result;
    }

    private boolean validateData(String body, Map<String, String[]> queryParams, Map<String, String> reqPathMap, String authCode, String timestamp, String sign) {
        StringBuilder stringBuilder = new StringBuilder();

        if (ArrayUtil.isNotEmpty(reqPathMap)) {
            for (String key : reqPathMap.keySet()) {
                String value = reqPathMap.get(key);
                if(isBase64(value)){
                    continue;
                }
                stringBuilder.append(key).append("=").append(value);
            }
        }

        if (StringUtils.hasText(body) ) {
            log.info("body 排序前参数：" + body);
            if (JSONUtil.isTypeJSON(body)) {
                body = JSONUtil.toJsonStr(SortUtil.jsonToMap(removeBase64Element(body)));
            }
            log.info("body 排序后参数：" + body);
            stringBuilder.append(body);
//            stringBuilder.append("#");
        }

        if (!CollectionUtils.isEmpty(queryParams)) {
            queryParams.entrySet().stream().filter(param -> !"tssignuser".contains(param.getKey())).sorted(Map.Entry.comparingByKey())
                    .forEach(paramEntry -> {
                        String paramValue = String.join(",", Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                        stringBuilder.append(paramEntry.getKey()).append("=").append(paramValue);
                    });
        }

        // authCode#PathVariable#body#queryParams#timestamp
        String sourceData = String.join("#", authCode, stringBuilder.toString(), timestamp);
        //log.info("签名原文：" + sourceData);
        String signData = DigestUtil.md5Hex(sourceData);
        //log.info("签名摘要：" + signData);
        return signData.equals(sign);
    }

    @SuppressWarnings("unchecked")
    public static String removeBase64Element(String jsonBody) {
        if (!JSONUtil.isTypeJSON(jsonBody)) {
            return jsonBody;
        }

        // 配置JSONConfig忽略null值
        JSONConfig config = new JSONConfig();
        config.setIgnoreNullValue(true);

        // 递归处理JSON对象
        Object jsonObj = JSONUtil.parse(jsonBody);
        if (jsonObj instanceof Map) {
            // 处理JSON对象
            Map<String, Object> map = (Map<String, Object>) jsonObj;
            Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                String value = String.valueOf(entry.getValue());

                if (isBase64(value)) {
                    // 如果值是base64,移除该键值对
                    it.remove();
                } else if (JSONUtil.isTypeJSON(value)) {
                    // 如果值是JSON,递归处理
                    entry.setValue(removeBase64Element(value));
                }
            }
        } else if (jsonObj instanceof Collection) {
            // 处理JSON数组
            Collection<Object> collection = (Collection<Object>) jsonObj;
            collection.removeIf(item -> {
                String value = String.valueOf(item);
                if (isBase64(value)) {
                    return true;
                } else if (JSONUtil.isTypeJSON(value)) {
                    item = removeBase64Element(value);
                }
                return false;
            });
        }

        return JSONUtil.toJsonStr(jsonObj, config);
    }

    public static boolean isBase64(String input) {
        // 如果输入为空，直接返回 false
        if (!StringUtils.hasText(input)) {
            return false;
        }

        // Base64 编码的字符串长度必须是 4 的倍数
        if (input.length() % 4 != 0) {
            return false;
        }

        try {
            // 使用 Base64 解码器解码字符串
            Base64.getDecoder().decode(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
