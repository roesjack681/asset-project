import request from '@/config/axios'

// 电警通断检测管理 VO
export interface MonitorPingAnalysisVO {
  id: string // 主键
  equId: string // 设备id
  equLable: string // 设备标签
  autoPingNormalCnt: number // 自动ping正常数
  autoPingAbnormalCnt: number // 自动ping异常数
  autoPingHealthRate: number // 自动ping健康率
  autoMonitorCnt: number // 自动检测次数
  pingNormalCnt: number // ping正常数
  pingAbnormalCnt: number // ping异常数
  pingHealthRate: number // ping健康率
  monitorCnt: number // 检测次数
  deletedTime: Date // 删除时间
}

export interface PoliceMonitorPingDetailVO {
  equId: string // 设备ID
  equName: string // 设备名称
  ipAddr: string // IP地址
  equLable: string // 标签
  monitorDay: string // 检测日
  dayMonitorCnt: string // 检测次数
  hourStatusResultList: []
}

// 电警通断检测管理 API
export const MonitorPingAnalysisApi = {
  // 查询电警通断检测管理分页
  getMonitorPingAnalysisPage: async (params: any) => {
    return await request.get({ url: `/police/monitor-ping-analysis/page`, params })
  },

  // 查询电警通断检测管理详情
  getMonitorPingAnalysis: async (id: number) => {
    return await request.get({ url: `/police/monitor-ping-analysis/get?id=` + id })
  },

  // 新增电警通断检测管理
  createMonitorPingAnalysis: async (data: MonitorPingAnalysisVO) => {
    return await request.post({ url: `/police/monitor-ping-analysis/create`, data })
  },

  // 修改电警通断检测管理
  updateMonitorPingAnalysis: async (data: MonitorPingAnalysisVO) => {
    return await request.put({ url: `/police/monitor-ping-analysis/update`, data })
  },

  // 删除电警通断检测管理
  deleteMonitorPingAnalysis: async (id: number) => {
    return await request.delete({ url: `/police/monitor-ping-analysis/delete?id=` + id })
  },

  // 导出电警通断检测管理 Excel
  exportMonitorPingAnalysis: async (params) => {
    return await request.download({ url: `/police/monitor-ping-analysis/export-excel`, params })
  },

  // 检测公安电警通断情况
  pingPoliceOpsElectricPolice: async (equId: any) => {
    return await request.get({ url: `/police/monitor-ping-analysis/pingPoliceOpsElectricPolice?equId=` + equId })
  },
  // 查询电警通断检测详情
  queryMonitorPingDetailResult: async (params: any) => {
    return await request.get({ url: `/police/monitor-ping-analysis/queryMonitorPingDetailResult`,  params})
  }
}