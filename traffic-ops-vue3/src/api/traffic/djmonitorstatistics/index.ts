import request from '@/config/axios'

// 监测电警设备统计 VO
export interface DjMonitorStatisticsVO {
  id: string // 主键
  ipAddr: string // IP地址
  eventType: string // 检测事件
  occurrenceTime: Date // 检测时间
  status: string // 状态
  cxTime: string // 持续在线时长
  avgTime: string // 平均在线时长
  deletedTime: Date // 删除时间
}

// 电警监控信息 VO
export interface ViewOpsDjMonitorStatisticsVO {
  id: string // 主键
  ipAddr: string // IP地址
  eventType: string // 检测事件
  occurrenceTime: Date // 检测时间
  status: string // 状态
  cxTime: string // 持续在线时长
  avgTime: string // 平均在线时长
  deletedTime: Date // 删除时间
}

export interface ViewOpsMonitorResultsVO {
  equId: string // 设备ID
  memberBh: string // 杆件编号
  roadName: string // 所在道路名称
  intersectionName: string // 灯控路口名称
  direction: string // 方向
  ipAddr: string // IP地址
  eventType: string // 检测事件
  normalCnt: number // 正常次数
  abnormalCnt: number // 异常次数
  monitorSumDays: number // 总检测天数
  monitorSumCnt: number // 总检测次数
  healthRate: number // 健康率
}

export interface OpsMonitorDetailResultsVO {
  equId: string // 设备ID
  memberBh: string // 杆件编号
  roadName: string // 所在道路名称
  intersectionName: string // 灯控路口名称
  direction: string // 方向
  ipAddr: string // IP地址
  eventType: string // 检测事件
  monitorDay: string // 检测日
  dayMonitorCnt: string // 检测次数
  hourStatusResultList: []
}

// 监测电警设备统计 API
export const DjMonitorStatisticsApi = {
  // 查询电警监控信息分页
  getViewOpsDjMonitorStatisticsPage: async (params: any) => {
    return await request.get({ url: `/traffic/view-ops-dj-monitor-statistics/page`, params })
  },

  analysisMonitorResultPage: async (params: any) => {
    return await request.get({ url: `/traffic/monitor-results/analysisMonitorResultPage`, params })
  },

  // 查询VIEW分页
  getViewOpsMonitorResultsPage: async (params: any) => {
    return await request.get({ url: `/traffic/view-ops-monitor-results/page`, params })
  },
  // 查询VIEW分页数据舱用
  getViewOpsMonitorDailyResultsPage: async (params: any) => {
    return await request.get({ url: `/traffic/view-ops-monitor-results-daily-analysis/page`, params })
  },

  // 查询VIEW详情
  getViewOpsMonitorResults: async (id: number) => {
    return await request.get({ url: `/traffic/view-ops-monitor-results/get?id=` + id })
  },
  // 查询电警监控信息分页
  exportViewOpsDjMonitorStatisticsInfo: async (params: any) => {
    return await request.download({ url: `/traffic/view-ops-monitor-results/export-excel`, params })
  },

  // 查询检测详情
  analysisMonitorDetailResult: async (params: any) => {
    return await request.get({ url: `/traffic/view-ops-monitor-results/analysisMonitorDetailResult`, params })
  },


  // 查询电警监控信息详情
  getViewOpsDjMonitorStatistics: async (id: number) => {
    return await request.get({ url: `/traffic/view-ops-dj-monitor-statistics/get?id=` + id })
  },

  // 查询电警监控信息详情
  getMonitorResultsPage: async (params: any) => {
    return await request.get({ url: `/traffic/monitor-results/page`, params })
  },

  // 查询监测电警设备统计分页
  getDjMonitorStatisticsPage: async (params: any) => {
    return await request.get({ url: `/traffic/dj-monitor-statistics/page`, params })
  },

  // 查询监测电警设备统计详情
  getDjMonitorStatistics: async (id: number) => {
    return await request.get({ url: `/traffic/dj-monitor-statistics/get?id=` + id })
  },

  // 新增监测电警设备统计
  createDjMonitorStatistics: async (data: DjMonitorStatisticsVO) => {
    return await request.post({ url: `/traffic/dj-monitor-statistics/create`, data })
  },

  // 修改监测电警设备统计
  updateDjMonitorStatistics: async (data: DjMonitorStatisticsVO) => {
    return await request.put({ url: `/traffic/dj-monitor-statistics/update`, data })
  },

  // 删除监测电警设备统计
  deleteDjMonitorStatistics: async (id: number) => {
    return await request.delete({ url: `/traffic/dj-monitor-statistics/delete?id=` + id })
  },

  // 导出监测电警设备统计 Excel
  exportDjMonitorStatistics: async (params) => {
    return await request.download({ url: `/traffic/dj-monitor-statistics/export-excel`, params })
  }
}
