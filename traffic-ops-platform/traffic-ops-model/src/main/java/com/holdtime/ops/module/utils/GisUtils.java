package com.holdtime.ops.module.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.holdtime.ops.module.model.vo.GeoCodeInfoVO;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class GisUtils {

    public static GeoCodeInfoVO getGeoInfoByAddress(String address) {
        String queryUrl = "https://restapi.amap.com/v3/geocode/geo?key=10431867c9df27dc80b5f6263afa28ac&address=" + address;
        String result = HttpUtil.get(queryUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if (jsonObject.getInt("status") == 1) {
            System.out.println(jsonObject);
            List<GeoCodeInfoVO> geoCodeInfoVOList = JSONUtil.toList(jsonObject.getJSONArray("geocodes"), GeoCodeInfoVO.class);
            if (!CollectionUtils.isEmpty(geoCodeInfoVOList)) {
                return geoCodeInfoVOList.get(0);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        GeoCodeInfoVO geoCodeInfoVO = getGeoInfoByAddress("包头市三十五中路南");
        System.out.println(geoCodeInfoVO);
    }
}
