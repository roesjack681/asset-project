package com.holdtime.ops.module.utils;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class GPSConverterUtils {
    static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    // π
    static double pi = 3.1415926535897932384626;
    // 长半轴
    static double a = 6378245.0;
    // 扁率
    static double ee = 0.00669342162296594323;

    /**
     * 百度坐标系(BD-09)转WGS坐标
     *
     * @param lng 百度坐标纬度
     * @param lat 百度坐标经度
     * @return WGS84坐标数组
     */
    public static double[] bd09ToWgs84(double lng, double lat) {
        double[] gcj = bd09ToGcj02(lng, lat);
        double[] wgs84 = gcj02ToWgs84(gcj[0], gcj[1]);
        return wgs84;
    }

    /**
     * WGS坐标转百度坐标系(BD-09)
     *
     * @param lng WGS84坐标系的经度
     * @param lat WGS84坐标系的纬度
     * @return 百度坐标数组
     */
    public static double[] wgs84ToBd09(double lng, double lat) {
        double[] gcj = wgs84ToGcj02(lng, lat);
        double[] bd09 = gcj02ToBd09(gcj[0], gcj[1]);
        return bd09;
    }

    /**
     * 火星坐标系(GCJ-02)转百度坐标系(BD-09)
     * <p>
     * 谷歌、高德——>百度
     *
     * @param lng 火星坐标经度
     * @param lat 火星坐标纬度
     * @return 百度坐标数组
     */
    public static double[] gcj02ToBd09(double lng, double lat) {
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * x_pi);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_pi);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[]{bd_lng, bd_lat};
    }

    /**
     * 百度坐标系(BD-09)转火星坐标系(GCJ-02)
     * <p>
     * 百度——>谷歌、高德
     *
     * @param bd_lon 百度坐标纬度
     * @param bd_lat 百度坐标经度
     * @return 火星坐标数组
     */
    public static double[] bd09ToGcj02(double bd_lon, double bd_lat) {
        double x = bd_lon - 0.0065;
        double y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new double[]{gg_lng, gg_lat};
    }

    /**
     * WGS84转GCJ02(火星坐标系)
     *
     * @param lng WGS84坐标系的经度
     * @param lat WGS84坐标系的纬度
     * @return 火星坐标数组
     */
    public static double[] wgs84ToGcj02(double lng, double lat) {
        if (out_of_china(lng, lat)) {
            return new double[]{lng, lat};
        }
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * pi;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * pi);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * pi);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        return new double[]{mglng, mglat};
    }

    /**
     * GCJ02(火星坐标系)转GPS84
     *
     * @param lng 火星坐标系的经度
     * @param lat 火星坐标系纬度
     * @return WGS84坐标数组
     */
    public static double[] gcj02ToWgs84(double lng, double lat) {
        if (out_of_china(lng, lat)) {
            return new double[]{lng, lat};
        }
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * pi;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * pi);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * pi);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        return new double[]{lng * 2 - mglng, lat * 2 - mglat};
    }

    /**
     * 纬度转换
     *
     * @param lng
     * @param lat
     * @return
     */
    public static double transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * pi) + 20.0 * Math.sin(2.0 * lng * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * pi) + 40.0 * Math.sin(lat / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * pi) + 320 * Math.sin(lat * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 经度转换
     *
     * @param lng
     * @param lat
     * @return
     */
    public static double transformlng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * pi) + 20.0 * Math.sin(2.0 * lng * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * pi) + 40.0 * Math.sin(lng / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * pi) + 300.0 * Math.sin(lng / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 批量转换坐标点（GCJ-02 转 WGS-84）
     * @param coordinates 格式：经度1,纬度1;经度2,纬度2
     * @return 转换后的坐标串
     */
    public static String batchGcj02ToWgs84(String coordinates) {
        if (StrUtil.isBlank(coordinates)) {
            return "";
        }

        try {
            StringBuilder result = new StringBuilder();
            String[] points = coordinates.split(";");

            for (String point : points) {
                String[] parts = point.split(",");
                if (parts.length != 2) {
                    log.error("[batchGcj02ToWgs84][坐标格式错误:{}]", point);
                    continue;
                }

                double lng = Double.parseDouble(parts[0].trim());
                double lat = Double.parseDouble(parts[1].trim());
                double[] converted = gcj02ToWgs84(lng, lat);

                if (result.length() > 0) {
                    result.append(";");
                }
                result.append(String.format("%.6f,%.6f", converted[0], converted[1]));
            }

            return result.toString();
        } catch (Exception e) {
            log.error("[batchGcj02ToWgs84][转换失败:{}]", coordinates, e);
            return coordinates;
        }
    }

    public static String batchWgs84ToGcj02(String coordinates) {
        if (StrUtil.isBlank(coordinates)) {
            return "";
        }

        try {
            StringBuilder result = new StringBuilder();
            String[] points = coordinates.split(";");

            for (String point : points) {
                String[] parts = point.split(",");
                if (parts.length != 2) {
                    log.error("[batchGcj02ToWgs84][坐标格式错误:{}]", point);
                    continue;
                }

                double lng = Double.parseDouble(parts[0].trim());
                double lat = Double.parseDouble(parts[1].trim());
                double[] converted = wgs84ToGcj02(lng, lat);

                if (result.length() > 0) {
                    result.append(";");
                }
                result.append(String.format("%.6f,%.6f", converted[0], converted[1]));
            }

            return result.toString();
        } catch (Exception e) {
            log.error("[batchWgs84ToGcj02][转换失败:{}]", coordinates, e);
            return coordinates;
        }
    }

    /**
     * 判断是否在国内，不在国内不做偏移
     *
     * @param lng
     * @param lat
     * @return
     */
    public static boolean out_of_china(double lng, double lat) {
        if (lng < 72.004 || lng > 137.8347) {
            return true;
        } else if (lat < 0.8293 || lat > 55.8271) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
//        String coordinates = "109.920739,40.531325;109.920256,40.534518;109.919753,40.538210;109.919685,40.539512;109.919726,40.540445;109.919835,40.541213;109.920035,40.542167;109.920898,40.544123;109.938754,40.566249;109.940184,40.568001;109.940498,40.568517;109.940737,40.568983;109.940910,40.569458;109.940960,40.569771;109.941002,40.570100;109.940998,40.570666;109.940887,40.571240;109.940801,40.571530;109.940714,40.571815;109.940594,40.572140;109.940488,40.572466;109.940254,40.573120;109.939078,40.576414;109.938939,40.576867;109.938830,40.577326;109.938643,40.578254;109.938461,40.579299;109.937610,40.584494;109.937603,40.584961;109.937630,40.585429;109.937663,40.585821;109.937689,40.586213;109.937853,40.587521;109.938006,40.588851;109.938144,40.590176;109.938311,40.591508;109.938472,40.592159;109.938692,40.592808;109.939263,40.593922;109.940071,40.594986;109.941061,40.595980;109.943204,40.597915;109.947319,40.601670;109.949779,40.603853;109.950643,40.604552;109.951390,40.605158;109.952094,40.605832;109.953435,40.607124;109.954023,40.607331;109.955291,40.607605;109.955714,40.607797;109.956172,40.608066;109.956509,40.608211;109.956922,40.608292;109.960791,40.606292;109.968536,40.602145;109.969735,40.601601;109.970864,40.600959;109.973130,40.599717;109.973814,40.599187;109.974148,40.598893;109.974499,40.598557;109.974897,40.598055;109.978748,40.590019;109.978793,40.589783;109.978926,40.589649;109.979103,40.589569;109.979339,40.589333;109.988272,40.586427;109.992938,40.584967;109.997922,40.583479;110.004856,40.581389;110.021476,40.576318;110.020876,40.575168;110.020582,40.574593;110.020427,40.574304;110.020287,40.574023;110.018842,40.571161;110.018501,40.570490;110.018380,40.570334;110.018137,40.569684;110.018150,40.569616;110.018169,40.569606;110.018169,40.569606";
        double[] tets = wgs84ToGcj02(109.931785,40.626079);
        System.out.println(tets[0]);
        System.out.println(tets[1]);
//        Date date = new Date(1743664729000L);
//        System.out.println(date);
    }
}
