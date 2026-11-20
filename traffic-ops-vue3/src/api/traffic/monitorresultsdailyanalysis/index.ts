import request from '@/config/axios'

// 设备监测结果每日分析 VO
export interface MonitorResultsDailyAnalysisVO {
  id: string // 主键
  analysisDate: Date // 分析时间
  equId: string // 设备ID
  memberBh: string // 杆件编号
  roadName: string // 道路名称
  intersectionName: string // 交叉口名称
  direction: string // 方向
  ipAddr: string // IP地址
  djType: string // 电警类型
  equLable: string // 设备标签
  eventType: string // 检测事件
  normalCnt: number // 正常数
  abnormalCnt: number // 异常数
  monitorSumDays: number // 检测天数
  monitorSumCnt: number // 检测总次数
  healthRate: number // 健康率
  deletedTime: Date // 删除时间
}

// 设备监测结果每日分析 API
export const MonitorResultsDailyAnalysisApi = {
  // 查询设备监测结果每日分析分页
  getMonitorResultsDailyAnalysisPage: async (params: any) => {
    return await request.get({ url: `/traffic/monitor-results-daily-analysis/page`, params })
  },

  // 查询设备监测结果每日分析详情
  getMonitorResultsDailyAnalysis: async (id: number) => {
    return await request.get({ url: `/traffic/monitor-results-daily-analysis/get?id=` + id })
  },

  // 新增设备监测结果每日分析
  createMonitorResultsDailyAnalysis: async (data: MonitorResultsDailyAnalysisVO) => {
    return await request.post({ url: `/traffic/monitor-results-daily-analysis/create`, data })
  },

  // 修改设备监测结果每日分析
  updateMonitorResultsDailyAnalysis: async (data: MonitorResultsDailyAnalysisVO) => {
    return await request.put({ url: `/traffic/monitor-results-daily-analysis/update`, data })
  },

  // 删除设备监测结果每日分析
  deleteMonitorResultsDailyAnalysis: async (id: number) => {
    return await request.delete({ url: `/traffic/monitor-results-daily-analysis/delete?id=` + id })
  },

  // 导出设备监测结果每日分析 Excel
  exportMonitorResultsDailyAnalysis: async (params) => {
    return await request.download({ url: `/traffic/monitor-results-daily-analysis/export-excel`, params })
  }
}