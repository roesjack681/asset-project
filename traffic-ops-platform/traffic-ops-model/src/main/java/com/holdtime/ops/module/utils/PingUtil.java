package com.holdtime.ops.module.utils;

import lombok.Getter;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 高效检测主机通断情况的工具类
 */
public class PingUtil {

    /**
     * 默认超时时间（毫秒）
     */
    private static final int DEFAULT_TIMEOUT = 1000;

    /**
     * 默认Ping次数
     */
    private static final int DEFAULT_PING_COUNT = 5;

    /**
     * Ping单个主机
     *
     * @param host 主机地址（IP或域名）
     * @return 是否可达
     */
    public static boolean ping(String host) {
        return ping(host, DEFAULT_TIMEOUT);
    }

    /**
     * Ping单个主机，支持自定义超时时间
     *
     * @param host    主机地址（IP或域名）
     * @param timeout 超时时间（毫秒）
     * @return 是否可达
     */
    public static boolean ping(String host, int timeout) {
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            return inetAddress.isReachable(timeout);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 批量Ping多个主机
     *
     * @param hosts 主机地址列表（IP或域名）
     * @return 主机通断情况的Map，键为主机地址，值为是否可达
     */
    public static Map<String, Boolean> batchPing(String[] hosts) {
        return batchPing(hosts, DEFAULT_TIMEOUT);
    }

    /**
     * 批量Ping多个主机，支持自定义超时时间
     *
     * @param hosts   主机地址列表（IP或域名）
     * @param timeout 超时时间（毫秒）
     * @return 主机通断情况的Map，键为主机地址，值为是否可达
     */
    public static Map<String, Boolean> batchPing(String[] hosts, int timeout) {
        Map<String, Boolean> result = new HashMap<>();
        for (String host : hosts) {
            result.put(host, ping(host, timeout));
        }
        return result;
    }

    /**
     * 检测主机通断并计算丢包率
     *
     * @param host    主机地址（IP或域名）
     * @param timeout 超时时间（毫秒）
     * @param count   Ping次数
     * @return 包含通断状态和丢包率的对象
     */
    public static PingResult pingWithLossRate(String host, int timeout, int count) {
        int success = 0;
        for (int i = 0; i < count; i++) {
            if (ping(host, timeout)) {
                success++;
            }
        }
        double lossRate = (count - success) * 100.0 / count;
        return new PingResult(success > 0, lossRate);
    }

    /**
     * 检测主机通断并计算丢包率（使用默认Ping次数）
     *
     * @param host    主机地址（IP或域名）
     * @param timeout 超时时间（毫秒）
     * @return 包含通断状态和丢包率的对象
     */
    public static PingResult pingWithLossRate(String host, int timeout) {
        return pingWithLossRate(host, timeout, DEFAULT_PING_COUNT);
    }

    /**
     * 检测主机通断并计算丢包率（使用默认超时时间和Ping次数）
     *
     * @param host 主机地址（IP或域名）
     * @return 包含通断状态和丢包率的对象
     */
    public static PingResult pingWithLossRate(String host) {
        return pingWithLossRate(host, DEFAULT_TIMEOUT, DEFAULT_PING_COUNT);
    }

    /**
     * Ping结果对象
     */
    @Getter
    public static class PingResult {
        private final boolean reachable;
        private final double lossRate;

        public PingResult(boolean reachable, double lossRate) {
            this.reachable = reachable;
            this.lossRate = lossRate;
        }

        @Override
        public String toString() {
            return "PingResult{" +
                    "reachable=" + reachable +
                    ", lossRate=" + lossRate +
                    "%}";
        }
    }

    public static void main(String[] args) {
        System.out.println(PingUtil.ping("127.0.0.1"));
        System.out.println(PingUtil.ping("192.168.111.24"));
        System.out.println(PingUtil.ping("192.168.112.24", 1000));
        System.out.println(PingUtil.batchPing(new String[] {"127.0.0.1", "192.168.111.24"}));
        System.out.println(PingUtil.pingWithLossRate("127.0.0.1"));
    }
}