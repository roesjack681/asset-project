package com.holdtime.ops.module.service;

public interface JasperReportService {
    /**
     * 生成资产明细台账
     * @param dominion 管辖单位
     * @param equStatus 设备状态
     * @return 台账流
     */
    byte[] generateOpsDetailReport(String dominion, String equStatus,String zcType);
}
