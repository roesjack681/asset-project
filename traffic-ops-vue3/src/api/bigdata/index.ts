import request from '@/config/axios'


// 基础设施
export const baseEqu = (params) => {
  return request.get({ url: '/traffic/ops-bigdata/get', params })
}

// 获取位置信息
export const queryTrafficOpsPositionList = (params) => {
  return request.get({ url: '/traffic/ops-bigdata/queryTrafficOpsPositionList', params })
}

//自动匹配
export const autocompleteOpsIntersection = (params) => {
  return request.get({ url: '/traffic/ops-bigdata/autocompleteOpsIntersection', params })
}

//运维人员排行
export const analysisMaintenanceRanking = () => {
  return request.get({ url: '/traffic/ops-bigdata/analysisMaintenanceRanking' })
}

//当前运维人员状态
export const maintenanceWorkStatus = (params) => {
  return request.get({ url: '/traffic/view-ops-maintenance-work-status/page', params })
}

//待维修设备
export const maintenancePagedp = (params) => {
  return request.get({ url: '/traffic/ops-maintenance/page-dp', params })
}

//维修类型：
export const getWxType = () => {
  return request.get({ url: '/traffic/ops-bigdata/getWxType' })
}

//资产运维大数据近三年接口
export const analysisNearlyThreeYearsMaintenanceCnt = (equType) => {
  return request.get({ url: '/traffic/ops-bigdata/analysisNearlyThreeYearsMaintenanceCnt?equType=' + equType })
}

export const generateOpsDetailReport = (equStatus, dominion, zcType) => {
  return request.download({url: '/traffic/report/generateOpsDetailReport?equStatus='+ equStatus + '&dominion=' + dominion + '&zcType=' + zcType})
}

export const generateOpsDetailReport2 = (equStatus, dominion) => {
  return 'http://192.168.11.64:8504/traffic/report/generateOpsDetailReport?equStatus='+ equStatus + '&dominion=' + dominion
}

export const trafficOpsMemberGet = (id) => {
  return request.get({ url: '/traffic/ops-member/get?id=' + id })
}
export const trafficOpsMemberGetPage = (params) => {
  return request.get({ url: '/traffic/ops-member/page', params})
}
//根据标签分析电警信息
export const analysisElectricPoliceByLabel = () => {
  return request.get({ url: '/traffic/ops-bigdata/analysisElectricPoliceByLabel'})
}
//根据事件类型分析监控结果
export const analysisMonitorResultByEventType = (equType) => {
  return request.get({ url: '/traffic/ops-bigdata/analysisMonitorResultByEventType?equType=' + equType})
}
//根据IP、方向以及事件类型分析设备监控结果
export const analysisMonitorResultByKeyWord = (params) => {
  return request.get({ url: '/traffic/ops-bigdata/analysisMonitorResultByKeyWord', params})
}
//长时间不在线设备
export const analysisElectricPoliceMonitorByLabel = (hour) => {
  return request.get({ url: '/traffic/ops-bigdata/analysisElectricPoliceMonitorByLabel?hour=' + hour})
}

//运维大数据
export const analysisMaintenanceFinishSituation = (affiliatedUnit = 1) => {
  return request.get({ url: '/traffic/ops-bigdata/analysisMaintenanceFinishSituation?affiliatedUnit=' + affiliatedUnit})
}

//工作白板
export const analysisMaintenanceDashboardCnt = (affiliatedUnit = 1) => {
  return request.get({ url: '/traffic/ops-bigdata/analysisMaintenanceDashboardCnt?affiliatedUnit=' + affiliatedUnit})
}

//工单量统计折线图
export const analysisNearlyOneYearsMaintenanceCnt = (affiliatedUnit = 1) => {
  return request.get({ url: '/traffic/ops-bigdata/analysisNearlyOneYearsMaintenanceCnt?affiliatedUnit=' + affiliatedUnit})
}
export const analysisPoliceOpsElectricCnt = () => {
  return request.get({ url: '/police/ops-bigdata/analysisPoliceOpsElectricCnt'})
}
//电警在线率
export const analysisKhsbMonitorResult = (intervalDay) => {
  return request.get({ url: '/traffic/ops-bigdata/analysisKhsbMonitorResult?intervalDay=' + intervalDay})
}

export const calcVioValidSurveilAvgRate = () => {
  return request.get({ url: '/traffic/ops-bigdata/calcVioValidSurveilAvgRate'})
}

// /traffic/ops-bigdata/getWxType?affiliatedUnit=2&equLabel=平安包头
//  /traffic/ops-bigdata/getWxType?affiliatedUnit=2&equLabel=雪亮工程
export const getWxTypePolice = (equLabel) => {
  return request.get({ url: `/traffic/ops-bigdata/getWxType?affiliatedUnit=2&equLabel=${equLabel}`})
}

export const analysisPoliceElectricPoliceOnOffAvgRate = () => {
  return request.get({ url: `/police/ops-bigdata/analysisPoliceElectricPoliceOnOffAvgRate`})
}

// /police/ops-bigdata/analysisElectricPoliceMonitorByLabel?hour=24
export const analysisElectricPoliceMonitorByLabel2 = (hour) => {
  return request.get({ url: '/police/ops-bigdata/analysisElectricPoliceMonitorByLabel?hour=' + hour})
}

export const analysisPoliceOpsElectricMaintenanceCnt = () => {
  return request.get({ url: '/police/ops-bigdata/analysisPoliceOpsElectricMaintenanceCnt'})
}
