package com.holdtime.ops.module.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.model.dto.TrafficOpsMaintenanceRankDTO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintainPointCountRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintainPointRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintenanceNearbyRespVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintenanceRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 维保自定义Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OpsMaintenanceExtMapper {
    /**
     * 统计维修人员维修工作排名
     * @return 微信排名信息
     */
    @Select("select t.id as user_id, t.nickname as user_name, ifnull( s.done_order_cnt, 0 ) as done_order_cnt, ifnull( s.undone_order_cnt, 0 ) as undone_order_cnt, ifnull( s.finish_rate, 0 ) as finish_rate from system_users t left join ( select r.maintenance_user_id, r.maintenance_personnel, sum( r.done_order ) done_order_cnt, sum( r.undone_order ) undone_order_cnt, replace ( concat( round(( sum( r.done_order ) / ( sum( r.done_order ) + sum( r.undone_order )) * 100 ), 2 ), '%' ), '.00', '' ) as finish_rate from ( select m.id, m.`status`, t.maintenance_user_id, t.maintenance_personnel, case when m.`status` = '5' then 1 else 0 end as done_order, case when m.`status` <> '5' then 1 else 0 end as undone_order from traffic_ops_maintenance m, traffic_ops_maintain_progress p, traffic_ops_maintenance_team t where m.id = p.maintenance_id and p.id = t.progress_id and m.deleted = '0' and p.deleted = '0' and t.deleted = '0' and m.affiliated_unit = '1' group by m.id, m.`status`, t.maintenance_user_id, t.maintenance_personnel ) r group by r.maintenance_user_id, r.maintenance_personnel order by done_order_cnt desc ) s on t.id = s.maintenance_user_id where t.post_ids like '%6%' order by done_order_cnt desc")
    List<TrafficOpsMaintenanceRankDTO> analysisMaintenanceRanking();

    /**
     * 统计近三年指定设备维修量
     * @param equType 设备类型
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 近三年维修量
     */
    @Select("select date_format( t.report_time, '%Y/%m' ) as months, ifnull( count( 1 ), 0 ) as cnt from traffic_ops_maintenance t where t.equ_type = #{equType} and date_format( t.report_time, '%Y-%m' ) >= #{startDate} and date_format( t.report_time, '%Y-%m' ) <= #{endDate} group by date_format( t.report_time, '%Y/%m' ) order by date_format( t.report_time, '%Y/%m')")
    List<Map<String, Object>> analysisNearlyThreeYearsMaintenanceCnt(@Param("equType") String equType, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 查询交警的设备ID不为空的，设备类型是电警的，状态不是完成的（工单地图点位）
     * @return
     */
    @Select("SELECT m.id, m.equ_type, m.equ_id, m.fault_date, m.problem, m.fault_type, m.urgency_level, m.`status`,m.maintenance_personnel, e.ip_addr, e.lng, e.lat, e.dj_type, e.equ_lable FROM `traffic_ops_maintenance` m, traffic_ops_electric_police e WHERE e.id = m.equ_id AND m.affiliated_unit = '1'AND (m.`status` != '5' AND m.`status` != '7' AND m.`status` != '9') AND m.equ_type = '电警' and m.deleted = '0'")
    List<TrafficOpsMaintainPointRespVO> queryMaintenancePoint();

    /**
     * 统计各类电警类型的报单数
     * @return
     */
    @Select("select IFNULL(a.dj_type, '其他') AS type, COUNT(*) AS count from (SELECT m.id, m.equ_type, m.equ_id, m.fault_date, m.problem, m.fault_type, m.urgency_level, m.`status`, e.ip_addr, e.lng, e.lat, e.dj_type, e.equ_lable FROM `traffic_ops_maintenance` m, traffic_ops_electric_police e WHERE e.id = m.equ_id AND m.affiliated_unit = '1' AND ( m.`status` != '5' AND m.`status` != '7' AND m.`status` != '9' ) and m.deleted = '0' AND m.equ_type = '电警') a group by IFNULL(a.dj_type, '其他')")
    List<TrafficOpsMaintainPointCountRespVO> queryMaintenancePointCount();

    /**
     * 统计各工单状态
     * @return
     */
    @Select("select  IFNULL(a.status, '其他') AS type, COUNT(*) AS count from (SELECT m.id, m.equ_type, m.equ_id, m.fault_date, m.problem, m.fault_type, m.urgency_level, m.`status`, e.ip_addr, e.lng, e.lat, e.dj_type, e.equ_lable FROM `traffic_ops_maintenance` m, traffic_ops_electric_police e WHERE e.id = m.equ_id AND m.affiliated_unit = '1'AND m.`status` != '5' and m.deleted = '0' AND m.equ_type = '电警' AND (#{djType} = '' OR e.dj_type=#{djType})) a group by IFNULL(a.status, '其他')")
    List<TrafficOpsMaintainPointCountRespVO> queryMaintenancePointStatusCount(@Param("djType") String djType);

    /**
     * 查询交警的就近待接单的工单
     * @return
     */
    @Select("SELECT m.id, m.order_num, m.report_personnel, m.equ_addr, m.equ_type, m.equ_id, m.fault_date, m.problem, m.fault_type, m.urgency_level, m.`status`, e.ip_addr, e.lng, e.lat,e.adress, e.dj_type, e.equ_lable, IFNULL(ROUND(ST_Distance_Sphere(ST_GeomFromText(CONCAT('POINT(', e.lng, ' ', e.lat, ')')), ST_GeomFromText (CONCAT( 'POINT(', #{lng}, ' ', #{lat}, ')' )) ) / 1000, 2 ),99999) AS distance_km FROM `traffic_ops_maintenance` m JOIN traffic_ops_electric_police e ON e.id = m.equ_id WHERE m.affiliated_unit = '1'AND m.`status`='1' AND m.equ_type = '电警'AND m.deleted = '0' AND m.problem like CONCAT('%',#{problem},'%') ORDER BY distance_km ASC")
    IPage<TrafficOpsMaintenanceNearbyRespVO> getNearbyOrdersPage(Page<?> page, @Param("problem") String problem, @Param("lng") String lng, @Param("lat") String lat);

    /**
     * 统计运维工作白板
     * @return 当日工单数、派单率、完成率；待派单数、平均维修时长、待处理数
     */
    @Select("select sum( today_order ) AS today_order_sum, round( sum( today_dispatch ) / if(sum( today_order ) = 0, 1, sum( today_order )), 2 ) AS today_dispatch_rate, round( sum( today_finish ) / if(sum( today_order ) =0 , 1, sum( today_order )), 2 ) AS today_finish_rate, sum( pending_orders ) AS pending_orders_sum, round( sum( work_time ) / if (sum( all_finish ) = 0, 1, sum( all_finish )), 2 ) AS avg_work_time, sum( untreated ) AS untreated_sum from ( select if ( to_days( t.fault_date ) = to_days( now()), 1, 0 ) today_order, if ( ( to_days( t.fault_date ) = to_days( now()) and to_days( t.dispatch_time ) = to_days( now())), 1, 0 ) today_dispatch, if ( ( to_days( t.fault_date ) = to_days( now()) and to_days( t.finish_time ) = to_days( now())), 1, 0 ) today_finish, if ( t.`status` = '1', 1, 0 ) pending_orders, ifnull( t.work_time, 0 ) work_time, if ( t.`status` = '5', 1, 0 ) all_finish, if ( f.operate_status = '4', 1, 0 ) untreated from traffic_ops_maintenance t left join ( select maintenance_id, operate_status from ( select r.maintenance_id, r.operate_status, row_number() over ( partition by r.maintenance_id order by create_time desc ) as rn from traffic_ops_maintain_flow_record r where r.flow_type = '5' ) t where rn = 1 ) f on t.id = f.maintenance_id where t.affiliated_unit = #{affiliatedUnit} and t.deleted = '0' ) e")
    Map<String, Object> analysisMaintenanceDashboardCnt(@Param("affiliatedUnit") String affiliatedUnit);

    /**
     * 工单完成情况
     * @return 当月、本年及全部工单数及完成率
     */
    @Select("select sum( r.cur_month_order ) cur_month_order_sum, round( sum( r.cur_month_finish ) / if(sum( r.cur_month_order ) = 0, 1, sum( r.cur_month_order )), 2 ) AS cur_month_finish_rate, sum( r.cur_year_order ) cur_year_order_sum, round( sum( r.cur_year_finish ) / if(sum( r.cur_year_order ) = 0, 1, sum( r.cur_year_order )), 2 ) AS cur_year_finish_rate, count( 1 ) all_order_sum, round( sum( r.all_finish ) / if(count( 1 )=0, 1,count( 1 )), 2 ) AS all_finish_rate  from ( select if ( date_format(t.fault_date, '%y-%m') = date_format( now(),  '%y-%m'), 1, 0 ) cur_month_order, if (date_format(t.fault_date, '%y-%m') = date_format( now(),  '%y-%m') and t.`status` = '5', 1, 0 ) cur_month_finish, if ( date_format(t.fault_date, '%y') = date_format( now(),  '%y'), 1, 0 ) cur_year_order, if (date_format(t.fault_date, '%y') = date_format( now(),  '%y') and t.`status` = '5', 1, 0 ) cur_year_finish, if (t.`status` = '5', 1, 0 ) all_finish from traffic_ops_maintenance t where t.affiliated_unit = #{affiliatedUnit} and t.deleted = '0') r")
    Map<String, Object> analysisMaintenanceFinishSituation(@Param("affiliatedUnit") String affiliatedUnit);

    /**
     * 根据标签分析电警监测异常数量
     * @return 不同标签的检测异常数量
     */
    @Select("select t.label, count( 1 ) as equ_cnt from system_dict_data t left join (select e.equ_lable, e.deleted, r.normal_cnt, r.abnormal_cnt from traffic_ops_electric_police e, ( select `traffic_monitor_results`.`equ_id` as `equ_id`, sum(( case when ( `traffic_monitor_results`.`status` = '正常' ) then 1 else 0 end )) as `normal_cnt`, sum(( case when ( `traffic_monitor_results`.`status` <> '正常' ) then 1 else 0 end )) as `abnormal_cnt` from `traffic_monitor_results` where `traffic_monitor_results`.`source` = '2' and `traffic_monitor_results`.`equ_type` = '2'  and traffic_monitor_results.occurrence_time >= now() - interval #{hour} hour group by `traffic_monitor_results`.`equ_id` order by `abnormal_cnt` desc) r where r.equ_id = e.id and r.normal_cnt = 0) e on instr( e.equ_lable, t.label ) > 0 and e.deleted = '0' where t.dict_type = 'electric_lable' group by t.label")
    List<Map<String, Collection<Integer>>> analysisElectricPoliceMonitorByLabel(@Param("hour") Integer hour);

    /**
     * 统计近1年公安、交警设备维修量
     * @param affiliatedUnit 所属单位
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 近三年维修量
     */
    @Select("select date_format (t.report_time, '%Y/%m' ) AS months, ifnull(count(t.id), 0 ) AS cnt from traffic_ops_maintenance t where t.deleted = b'0' and t.affiliated_unit = #{ affiliatedUnit } and date_format(T.report_time, '%Y-%m' ) >= #{ startDate } and date_format (T.report_time, '%Y-%m') <= #{ endDate } group by date_format ( t.report_time, '%Y/%m' ) order by date_format ( t.report_time, '%Y/%m' )")
    List<Map<String, Object>> analysisNearlyOneYearsMaintenanceCnt(@Param("affiliatedUnit") String affiliatedUnit, @Param("startDate") String startDate, @Param("endDate") String endDate);

}