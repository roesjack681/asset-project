package com.holdtime.ops.module.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.model.dto.TrafficOpsMonitorDetailResultDTO;
import com.holdtime.ops.module.model.dto.TrafficOpsMonitorResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 维保自定义Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OpsAnalysisExtMapper {
    /**
     * 根据标签分析电警信息
     * @return 不同标签的数量
     */
    @Select("select t.label, count(e.id) as equ_cnt from system_dict_data t left join traffic_ops_electric_police e on instr(e.equ_lable, t.label) > 0 and e.deleted = '0' where t.dict_type = 'electric_lable' group by t.label")
    List<Map<String, Collection<Integer>>> analysisElectricPoliceByLabel();

    /**
     * 根据事件类型分析监控结果
     * @param equType 设备类型
     * @return 不同事件推送数量
     */
    @Select("select event_type, count(1) report_cnt from traffic_monitor_results t where t.equ_type = #{equType} group by event_type")
    List<Map<String, Collection<Integer>>> analysisMonitorResultByEventType(@Param("equType") String equType);

    /**
     * 根据IP、方向以及事件类型分析设备监控结果
     * @return 设备监控结果
     */
    @Select("select t.ip_addr, t.direction, t.event_type, count(1) abnormal_cnt from traffic_monitor_results t where t.equ_type = #{equType} group by t.ip_addr, t.direction, t.event_type order by count(1) desc, max(id)")
    List<Map<String, Collection<Integer>>> analysisMonitorResultByKeyWord(@Param("equType") String equType);

    /**
     * 查询分析检查结果
     * @param page 分页参数
     * @return 设备监控结果
     */
    @Select("select equ_id, member_bh, road_name, intersection_name, direction, ip_addr, event_type, sum( case when `status` = '正常' then cnt else 0 end ) as normal_cnt, sum( case when `status` != '正常' then cnt else 0 end ) as abnormal_cnt, count( distinct day ) as monitor_sum_days, sum( cnt ) as monitor_sum_cnt, round((sum(case when `status` = '正常' then cnt else 0 end ) / ifnull(sum( cnt ), 1)), 2) health_rate from ( select equ_id, member_bh, road_name, intersection_name, direction, ip_addr, event_type, `status`, date ( occurrence_time ) as day, count(*) as cnt from traffic_monitor_results where source = '2' and equ_type = '2' and date_format(occurrence_time, '%Y-%m-%d %H:%i:%s') >= #{startDate} and date_format(occurrence_time, '%Y-%m-%d %H:%i:%s') <= #{endDate} group by equ_id, member_bh, road_name, intersection_name, direction, ip_addr, event_type, `status`, day ) as d group by equ_id, member_bh, road_name, intersection_name, direction, ip_addr, event_type order by abnormal_cnt desc")
    IPage<TrafficOpsMonitorResultDTO> analysisMonitorResult(Page<?> page, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 查询分析检查结果
     * @param page 分页参数
     * @return 设备监控结果
     */
    @Select("select t.equ_id, t.road_name, t.intersection_name, t.member_bh, t.ip_addr, t.event_type, date_format( t.occurrence_time, '%Y-%m-%d') AS monitor_day, count( 1 ) as day_monitor_cnt from traffic_monitor_results t where source = '2' and equ_type = '2' and t.ip_addr = #{ipAddr} and t.event_type=#{eventType} and (#{startDate} is null or date_format( occurrence_time, '%Y-%m-%d %H:%i:%s' ) >= #{startDate}) and (#{endDate} is null or date_format(occurrence_time, '%Y-%m-%d %H:%i:%s') <= #{endDate}) group by t.equ_id, t.road_name, t.intersection_name, t.member_bh, t.ip_addr, t.event_type, monitor_day order by monitor_day desc, day_monitor_cnt desc")
    IPage<TrafficOpsMonitorDetailResultDTO> analysisMonitorDetailResult(Page<?> page, @Param("ipAddr") String ipAddr, @Param("eventType") String eventType, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 分析集指考核设备
     * @return 集指考核设备
     */
//    @Select("select occurrence_time, sum(( case when ( `t`.`status` = '正常' ) then 1 else 0 end )) normal_cnt from ( select equ_id, date_format( occurrence_time, '%Y-%m-%d' ) occurrence_time, `status`, row_number() over ( partition by equ_id, date_format( occurrence_time, '%Y-%m-%d' ) order by occurrence_time desc ) as rn from traffic_monitor_results where source = '2' and equ_type = '2' and equ_id is not null ) t where t.rn = '1' and occurrence_time >= DATE_SUB(CURDATE(), INTERVAL #{intervalDay} DAY) and exists ( select 1 from traffic_ops_electric_police e where t.equ_id = e.id and e.deleted = '0' and dj_type = '电子警察' ) group by occurrence_time")
    @Select("select occurrence_day as occurrence_time, count( 1 ) as normal_cnt from v_ops_monitor_results_daily_analysis where abnormal_cnt = 0 and dj_type = '电子警察' group by occurrence_day order by occurrence_day limit 1, #{intervalDay}")
    List<Map<String, Object>> analysisKhsbMonitorResult(@Param("intervalDay") int intervalDay);


    @Select("select avg(t.valid_surveil_rate) as avg_rate from traffic_vio_equipment_monitor_analysis t where exists ( select 1 from traffic_ops_electric_police e where t.equ_id = e.id and e.dj_type = '电子警察')")
    Map<String, BigDecimal> calcVioValidSurveilAvgRate();

    /**
     * 分析违法抓拍无效原因
     * @return 分析时间
     */
    @Select("select sbbh, cwyy, date_format(wfsj,'%Y%m%d') wfsj, count(1) cnt from btjczh_vio_surveil_screen_err where date_format(wfsj,'%Y%m%d') >= #{analysisDay} group by sbbh, cwyy, date_format(wfsj,'%Y%m%d')")
    List<Map<String, Object>> analysisVioInvalidReason(@Param("analysisDay") String analysisDay);

    /**
     * 设备无效原因分析（饼图）
     * @return 分析时间
     */
    @Select("select concat(d.`value`, ':', d.label) invalid_reason, sum( t.invalid_cnt ) sum_cnt from traffic_vio_equipment_invalid_reason_analysis t left join system_dict_data d on d.dict_type = 'jz_invalid_reason' and d.`value` = substring_index(t.invalid_reason, ':', 1) where t.sbbh = #{sbbh} and (#{analysisDay} = '' or date_format( t.occurrence_time, '%Y%m%d' ) = #{analysisDay} ) group by CONCAT(d.`value`, ':', d.label)")
    List<Map<String, Collection<Integer>>> analysisVioInvalidReasonPieChart(@Param("sbbh") String sbbh, @Param("analysisDay") String analysisDay);

    /**
     * 设备无效原因分析（饼图）
     * @return 分析时间
     */
    @Select("select t.sbbh, t.occurrence_time, sum(t.valid_surveil_cnt) sum_cnt, max(t.operate_date) operate_date, max(t.operate_mark) operate_mark from traffic_vio_equipment_monitor_detail t where sbbh = #{sbbh} group by t.equ_id, t.sbbh, t.occurrence_time order by t.occurrence_time")
    List<Map<String, Object>> analysisVioValidLineChart(@Param("sbbh") String sbbh);

    /**
     * 分析违法抓拍违法原因
     * @return 分析时间
     */
    @Select("select sbbh, wfxw, date_format(wfsj, '%Y%m%d') wfsj, count(1) cnt from btjczh_vio_surveil where date_format(wfsj, '%Y%m%d') >= #{analysisDay} group by sbbh, wfxw, date_format(wfsj,'%Y%m%d')")
    List<Map<String, Object>> analysisVioIllegal(@Param("analysisDay") String analysisDay);

    /**
     * 设备有效违法行为分析（饼图）
     * @return 分析时间
     */
    @Select("select d.label, sum(t.illegal_cnt) sum_cnt from traffic_vio_equipment_illegal_analysis t left join system_dict_data d ON d.dict_type = 'illegal_code' AND d.`value` = t.illegal_reason WHERE t.sbbh = #{sbbh} and (#{analysisDay} = '' or date_format( t.occurrence_time, '%Y%m%d' ) = #{analysisDay} ) group by d.label")
    List<Map<String, Collection<Integer>>> analysisVioIllegalPieChart(@Param("sbbh") String sbbh, @Param("analysisDay") String analysisDay);


}