package com.holdtime.ops.module.utils;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SortUtil {
    public static Map<String, Object> sortMapByKey(Map<String, Object> oriMap, final boolean isRise) {
        if (oriMap == null || oriMap.isEmpty()) {
            return new TreeMap<>();
        }

        Map<String, Object> sortMap = new TreeMap<>((o1, o2) -> {
            if (isRise) {
                // 升序排序
                return o1.compareTo(o2);
            } else {
                // 降序排序
                return o2.compareTo(o1);
            }
        });
        sortMap.putAll(oriMap);
        return sortMap;
    }

    /**
     * JSON转顺序排序的Map(为了方便后期获取数据,应答就不返回JSON字符串了,可自行去转换)
     *
     * @param jsonStr 原始json
     * @return 响应的map
     */
    public static Map<String, Object> jsonToMap(String jsonStr) {
        Map<String, Object> treeMap = new TreeMap<>();
        JSONObject json = JSONObject.parseObject(jsonStr, Feature.OrderedField);// Feature.OrderedField实现解析后保存不乱序
        for (String key : json.keySet()) {
            Object value = json.get(key);
            // 判断传入kay-value中是否含有""或null
            if (json.get(key) == null || value == null || value.toString().length() == 0) {
                // 当JSON字符串存在null时,不将该kay-value放入Map中,即显示的结果不包括该kay-value
                continue;
            }
            // 判断是否为JSONArray(json数组)
            if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                List<Object> arrayList = new ArrayList<>();
                for (Object object : jsonArray) {
                    // 判断是否为JSONObject，如果是 转化成TreeMap
                    if (object instanceof JSONObject) {
                        object = jsonToMap(object.toString());
                    }
                    arrayList.add(object);
                }
                treeMap.put(key, arrayList);
            } else {
                // 判断该JSON中是否嵌套JSON
                boolean flag = isJSONValid(value.toString());
                if (flag) {
                    // 若嵌套json了,通过递归再对嵌套的json(即子json)进行排序
                    value = jsonToMap(value.toString());
                }
                // 其他基础类型直接放入treeMap
                // JSONObject可进行再次解析转换
                treeMap.put(key, value);
            }
        }
        return treeMap;
    }

    /**
     * 校验是否是JSON字符串
     *
     * @param json 传入数据
     * @return 是JSON返回true, 否则false
     */
    public static boolean isJSONValid(String json) {
        try {
            JSONObject.parseObject(json);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        String str = "{\"passageway\":\"公安一所实人核验\",\"reqParam\":\"150202199001050614\",\"resultCode\":\"200\",\"resultContent\":\"true\",\"source\":\"市局\"}";
//        Map<String, Object> json = jsonToMap(str);
//        System.out.println("排序后的json>>>"+JSONObject.toJSONString(json));
        String signData = DigestUtil.md5Hex("CE00A473B7E0F8E4B772BF8258831E05#{\"passageway\":\"公安一所实人核验\",\"reqParam\":\"150202199001050614\",\"resultCode\":\"200\",\"resultContent\":\"true\",\"source\":\"市局\"}#1716285559782");
        System.out.println("加签原文>>>" + signData);

    }

}
