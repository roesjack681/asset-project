import request from '@/config/axios'

// 设备监测结果导入信息 VO
export interface MonitorResultsImportDetailVO {
  id: string // 主键
  equId: string // 设备id
  ipAddr: string // IP地址
  roadName: string // 道路名称
  intersectionName: string // 路口名称
  memberBh: string // 杆件编号
  direction: string // 方向
  occurrenceTime: Date // 检测时间
  status: string // 状态
  equSituation: string // 设备情况
  deletedTime: Date // 删除时间
}

// 设备监测结果导入信息 API
export const MonitorResultsImportDetailApi = {
  // 查询设备监测结果导入信息分页
  getMonitorResultsImportDetailPage: async (params: any) => {
    return await request.get({ url: `/traffic/monitor-results-import-detail/page`, params })
  },

  // 查询设备监测结果导入信息详情
  getMonitorResultsImportDetail: async (id: number) => {
    return await request.get({ url: `/traffic/monitor-results-import-detail/get?id=` + id })
  },

  // 新增设备监测结果导入信息
  createMonitorResultsImportDetail: async (data: MonitorResultsImportDetailVO) => {
    return await request.post({ url: `/traffic/monitor-results-import-detail/create`, data })
  },

  // 修改设备监测结果导入信息
  updateMonitorResultsImportDetail: async (data: MonitorResultsImportDetailVO) => {
    return await request.put({ url: `/traffic/monitor-results-import-detail/update`, data })
  },

  // 删除设备监测结果导入信息
  deleteMonitorResultsImportDetail: async (id: number) => {
    return await request.delete({ url: `/traffic/monitor-results-import-detail/delete?id=` + id })
  },

  // 导出设备监测结果导入信息 Excel
  exportMonitorResultsImportDetail: async (params) => {
    return await request.download({ url: `/traffic/monitor-results-import-detail/export-excel`, params })
  },
  // 修改公安设备监测结果
  remarktMonitorResultsImportDetail: async (data: MonitorResultsImportDetailVO) => {
    return await request.post({ url: `/traffic/monitor-results-import-detail/remarkMonitorResults`, data })
  },
}