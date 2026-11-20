package com.holdtime.ops.module.datapermission.config;

import cn.iocoder.yudao.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import com.holdtime.ops.module.entity.TrafficOpsIssuePersonDO;
import com.holdtime.ops.module.entity.TrafficOpsIssueRecordDO;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import com.holdtime.ops.module.entity.TrafficOpsPoliceEquipmentDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的数据权限 Configuration
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
public class HdtmDataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(TrafficOpsMaintenanceDO.class, "report_dept_id");
            rule.addDeptColumn(TrafficOpsIssueRecordDO.class, "use_dept_bh");
            rule.addDeptColumn(TrafficOpsIssuePersonDO.class,"dept_id");
            //rule.addDeptColumn(TrafficOpsPoliceEquipmentDO.class,"use_dept_bh");
        };
    }

}
