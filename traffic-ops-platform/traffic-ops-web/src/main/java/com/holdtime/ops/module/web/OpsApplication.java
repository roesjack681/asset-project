/**
 * Copyright @ 2023 HoldTime Inner Mongolia Co. Ltd.
 */
package com.holdtime.ops.module.web;

import com.holdtime.ops.module.kafka.producer.BoundaryMsgProducer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;


@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${yudao.info.base-package}
@MapperScan(value = {"com.holdtime.yudao.basesystem.module.entity.mapper",
        "com.holdtime.ops.module.entity.mapper", "com.holdtime.ops.module.mapper"})
@SpringBootApplication(scanBasePackages =
        {"${yudao.info.base-package}.module",
                "com.holdtime.yudao.basesystem",
                "cn.iocoder.yudao.framework.flowable",
                "com.holdtime.ops"
        })
@EnableCaching
public class OpsApplication {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(OpsApplication.class, args);
//        RailwayTicketCheckDataSyncJob railwayDataSyncJob = ac.getBean(RailwayTicketCheckDataSyncJob.class);
//        railwayDataSyncJob.execute("");

//        CodegenService codegenService = ac.getBean(CodegenService.class);
//        CodegenCreateListReqVO codegenCreateListReqVO = new CodegenCreateListReqVO();
//        codegenCreateListReqVO.setDataSourceConfigId(0L);
//        String[] tables = new String[]{
//                "view_vio_equipment_monitor_analysis"
//        };
//        codegenCreateListReqVO.setTableNames(Arrays.asList(tables));
//        codegenService.createCodegenList(1L, codegenCreateListReqVO);
    }
}
