package com.holdtime.ops.module.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.model.dto.PoliceMonitorPingDetailDTO;
import com.holdtime.ops.module.model.dto.TrafficOpsMonitorDetailResultDTO;
import com.holdtime.ops.module.model.vo.PoliceMonitorResultsImportAnalysisExtRespVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 维保自定义Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface PoliceOpsEquipInfoExtMapper {
    /**
     * 查询附近的杆件信息
     * @return 不同标签的数量
     */
    @Select("SELECT t.equ_name from police_ops_electric_police t where t.lng is not null and t.lat is not null and (FLOOR(ST_DISTANCE(POINT(t.lng, t.lat), POINT ('${lng}', '${lat}')) * 111195)) <= 3")
    List<Map<String, String>> queryNearByPoliceMember(@Param("lng") String lng, @Param("lat") String lat);

    /**
     * 杆件采集分析
     */
    @Select("SELECT u.id, u.nickname, t.sum_cnt, t.today_sum_cnt, today_earliest_start_date, today_last_start_date, TIMESTAMPDIFF(HOUR, today_earliest_start_date, today_last_start_date) job_time FROM ( SELECT s.creator, count(*) AS sum_cnt, sum( CASE WHEN DATE ( s.create_time ) = curdate() THEN 1 ELSE 0 END ) AS today_sum_cnt, min( CASE WHEN DATE ( s.create_time ) = curdate() THEN s.create_time ELSE NULL END ) today_earliest_start_date, max( CASE WHEN DATE ( s.create_time ) = curdate() THEN s.create_time ELSE NULL END ) today_last_start_date FROM police_ops_member s GROUP BY s.creator ) AS t JOIN system_users u ON t.creator = u.id ORDER BY t.today_sum_cnt DESC")
    List<Map<String, Object>> analysisCollectMemberByUserJob();

    /**
     * 删除交警检测结果状态分析（保留最新一条）
     * @return
     */
    @Delete("delete from police_monitor_results_import_situation where id in ( select id from ( select id, row_number() over ( partition by equ_id order by occurrence_time desc ) as rn from police_monitor_results_import_situation ) t where t.rn > 1 );")
    int deletePoliceMonitorResultsImportSituationKeepLastOne();

    /**
     * 查询分析检查结果
     * @param page 分页参数
     * @return 设备监控结果
     */
    @Select("select e.id as equ_id, e.equ_name, e.equ_lable, e.ip_addr, date_format( t.occurrence_time, '%Y-%m-%d' ) as monitor_day, count( 1 ) as day_monitor_cnt from police_monitor_ping_detail t left join police_ops_electric_police e on t.equ_id = e.id where e.deleted = '0' and t.equ_id = #{equ_id} and (#{startDate} is null or date_format( occurrence_time, '%Y-%m-%d %H:%i:%s' ) >= #{startDate}) and (#{endDate} is null or date_format(occurrence_time, '%Y-%m-%d %H:%i:%s') <= #{endDate}) group by e.id, e.equ_name, e.equ_lable, e.ip_addr, monitor_day order by monitor_day desc")
    IPage<PoliceMonitorPingDetailDTO> queryMonitorPingDetailResult(Page<?> page, @Param("equ_id") String ipAddr, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 删除公安通断检测结果状态分析（保留最新2条）
     * @return
     */
    @Delete("delete from police_monitor_ping_situation where id in ( select id from ( select id, row_number() over ( partition by equ_id order by occurrence_time desc ) as rn from police_monitor_ping_situation ) t where t.rn > 2 );")
    int deletePoliceMonitorPingSituationKeepLastTwo();

    /**
     * 查询分析检查结果
     * @param page 分页参数
     * @return 设备监控结果
     */
    @Select("select `e`.`id` as `equ_id`, `e`.`equ_name` as `equ_name`, `e`.`ip_addr` as `ip_addr`, `e`.`equ_lable` as `equ_lable`, `r`.`video_normal_cnt` as `video_normal_cnt`, `r`.`video_abnormal_cnt` as `video_abnormal_cnt`, `r`.`video_health_rate` as `video_health_rate`, case when r.video_normal_cnt <> 0 and r.video_abnormal_cnt = 0 then '全通' when r.video_normal_cnt = 0 and r.video_abnormal_cnt <> 0 then '全断' WHEN r.video_normal_cnt <> 0  AND r.video_abnormal_cnt <> 0 THEN '时断时通' else '' end as video_status, `r`.`ping_normal_cnt` as `ping_normal_cnt`, `r`.`ping_abnormal_cnt` as `ping_abnormal_cnt`, `r`.`ping_health_rate` as `ping_health_rate`, case when r.ping_normal_cnt <> 0 and r.ping_abnormal_cnt = 0 then '全通' when r.ping_normal_cnt = 0 and r.ping_abnormal_cnt <> 0 then '全断' WHEN r.ping_normal_cnt <> 0  AND r.ping_abnormal_cnt <> 0 THEN '时断时通' else '' end as ping_status, `r`.`monitor_sum_cnt` as `monitor_cnt` from ( `police_ops_electric_police` `e` left join ( select `d`.`equ_id` as `equ_id`, sum(( case when ( `d`.`video_status` = '正常' ) then 1 else 0 end )) as `video_normal_cnt`, sum(( case when ( `d`.`video_status` <> '正常' ) then 1 else 0 end )) as `video_abnormal_cnt`, round(( sum(( case when ( `d`.`video_status` = '正常' ) then 1 else 0 end )) / nullif( count( 0 ), 0 )), 2 ) as `video_health_rate`, sum(( case when ( `d`.`ping_status` = '正常' ) then 1 else 0 end )) as `ping_normal_cnt`, sum(( case when ( `d`.`ping_status` <> '正常' ) then 1 else 0 end )) as `ping_abnormal_cnt`, round(( sum(( case when ( `d`.`ping_status` = '正常' ) then 1 else 0 end )) / nullif( count( 0 ), 0 )), 2 ) as `ping_health_rate`, sum(( case when ( `d`.`occurrence_time` is not null ) then 1 else 0 end )) as `monitor_sum_cnt` from ( `police_monitor_results_import_detail` `d` left join `police_monitor_results_import_analysis` `a` on (( `d`.`equ_id` = `a`.`equ_id` ))) where (d.occurrence_time >= #{startDate} and d.occurrence_time <= #{endDate}) group by `d`.`equ_id` ) `r` on (( `e`.`id` = `r`.`equ_id` ))) where ( #{ipAddr} = '' or e.ip_addr = #{ipAddr} ) and ( #{equLable} = '' or e.equ_lable = #{equLable} ) and ( #{pingStatus} = '' or ( #{pingStatus} = '1' and r.ping_normal_cnt <> 0 and r.ping_abnormal_cnt = 0 ) or ( #{pingStatus} = '2' and r.ping_normal_cnt = 0 and r.ping_abnormal_cnt <> 0 ) or ( #{pingStatus} = '3' and r.ping_normal_cnt <> 0 and r.ping_abnormal_cnt <> 0 ) ) and ( #{videoStatus} = '' or ( #{videoStatus} = '1' and r.video_normal_cnt <> 0 and r.video_abnormal_cnt = 0 ) or ( #{videoStatus} = '2' and r.video_normal_cnt = 0 and r.video_abnormal_cnt <> 0 ) or ( #{videoStatus} = '3' and r.video_normal_cnt <> 0 and r.video_abnormal_cnt <> 0 ) ) order by `r`.`ping_abnormal_cnt` desc, `r`.`video_abnormal_cnt` desc")
    IPage<PoliceMonitorResultsImportAnalysisExtRespVO> queryHuazhiMonitorOnOffResultPage(Page<?> page, @Param("ipAddr") String ipAddr, @Param("equLable") String equLable, @Param("pingStatus") String pingStatus, @Param("videoStatus") String videoStatus, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 公安电警数量统计
     */
    @Select("select sum(case when t.dj_type = '球机' then 1 else 0 end)  as qj_cnt, sum(case when t.dj_type = '枪机' then 1 else 0 end)  as bolt_cnt, sum(case when t.equ_lable = '平安包头' then 1 else 0 end) as pabt_cnt, sum(case when t.equ_lable = '雪亮工程' then 1 else 0 end) as xlgc_cnt, group_concat( distinct concat(ifnull(m.manufacturer,''), ':', m.cnt) order by m.cnt desc separator ',' )                                       as pabt_manufacturer_cnt, group_concat( distinct concat(ifnull(n.manufacturer,''), ':', n.cnt) order by n.cnt desc separator ',' )                                       as xlgc_manufacturer_cnt from police_ops_electric_police t left join ( select equ_lable, ifnull(manufacturer,'') manufacturer, count(*) cnt from police_ops_electric_police where equ_lable = '平安包头' group by ifnull(manufacturer,'') ) m on m.equ_lable = t.equ_lable left join ( select equ_lable, ifnull(manufacturer,'') manufacturer, count(*) cnt from police_ops_electric_police where equ_lable = '雪亮工程' group by ifnull(manufacturer,'') ) n on n.equ_lable = t.equ_lable")
    Map<String, Object> analysisPoliceOpsElectricCnt();

    /**
     * 公安电警维修数量统计
     */
    @Select("select ifnull(p.equ_lable, '其他') as equ_lable, count(t.id) cnt from traffic_ops_maintenance t, police_ops_electric_police p where t.equ_id = p.id group by ifnull(p.equ_lable, '其他')")
    List<Map<String, Object>> analysisPoliceOpsElectricMaintenanceCnt();

    /**
     * 根据标签分析电警监测异常数量
     * @return 不同标签的检测异常数量
     */
    @Select("select e.equ_lable, count(*) as equ_cnt from police_ops_electric_police e, ( select d.`equ_id` as `equ_id`, sum(( case when ( d.ping_status = '正常' ) then 1 else 0 end )) as `normal_cnt`, sum(( case when ( `d`.`ping_status` <> '正常' ) then 1 else 0 end )) as `abnormal_cnt` from `police_monitor_ping_detail`  d where `d`.`source` = '1' and d.occurrence_time >= now() - interval #{hour} hour group by `d`.`equ_id` order by `abnormal_cnt` desc ) r where r.equ_id = e.id and r.normal_cnt = 0 group by e.equ_lable")
    List<Map<String, Collection<Integer>>> analysisPoliceElectricPoliceMonitorByLabel(@Param("hour") Integer hour);

    /**
     * 分析电警平均在线率
     * @return 不同标签的检测异常数量
     */
    @Select("select avg(ping_health_rate) ping_health_avg, avg(video_health_rate) video_health_avg from police_monitor_results_import_analysis")
    Map<String, BigDecimal> analysisPoliceElectricPoliceOnOffAvgRate();
}