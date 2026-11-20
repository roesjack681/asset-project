/**
 * Copyright @ 2023 HoldTime Inner Mongolia Co. Ltd.
 */
package com.holdtime.ops.module.web;

import com.holdtime.ops.module.kafka.consumer.InternetToVideoMsgConsumer;
import com.holdtime.ops.module.kafka.consumer.VideoToInternetMsgConsumer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;


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
public class OpsWechatApplication {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(OpsWechatApplication.class, args);
//        RailwayTicketCheckDataSyncJob railwayDataSyncJob = ac.getBean(RailwayTicketCheckDataSyncJob.class);
//        railwayDataSyncJob.execute("");

//        CodegenService codegenService = ac.getBean(CodegenService.class);
//        CodegenCreateListReqVO codegenCreateListReqVO = new CodegenCreateListReqVO();
//        codegenCreateListReqVO.setDataSourceConfigId(0L);
//        String[] tables = new String[]{
//                "v_pt_bus_line_apply_bpm"
//        };
//        codegenCreateListReqVO.setTableNames(Arrays.asList(tables));
//        codegenService.createCodegenList(1L, codegenCreateListReqVO);
    }
}
