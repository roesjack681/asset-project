import request from '@/config/axios'

// 公安监测结果导入分析 VO
export interface MonitorResultsImportAnalysisVO {
  id: string // 主键
  equId: string // 设备id
  equLable: string // 设备标签
  eventType: string // 检测事件
  videoNormalCnt: number // 视频正常数
  videoAbnormalCnt: number // 视频异常数
  videoHealthRate: number // 视频健康率
  pingNormalCnt: number // ping正常数
  pingAbnormalCnt: number // ping异常数
  pingHealthRate: number // ping健康率
  monitorCnt: number // 检测次数
  lastRepairTime: Date // 上次修复时间
  deletedTime: Date // 删除时间
}

// 公安监测结果导入分析 API
export const MonitorResultsImportAnalysisApi = {
  // 查询公安监测结果导入分析分页
  getMonitorResultsImportAnalysisPage: async (params: any) => {
    return await request.get({ url: `/police/monitor-results-import-analysis/page`, params })
  },
  monitorResultsImportDetailPage: async (params: any) => {
    return await request.get({ url: `/police/monitor-results-import-detail/page`, params })
  },

  // 查询公安监测结果导入分析详情
  getMonitorResultsImportAnalysis: async (id: number) => {
    return await request.get({ url: `/police/monitor-results-import-analysis/get?id=` + id })
  },

  // 新增公安监测结果导入分析
  createMonitorResultsImportAnalysis: async (data: MonitorResultsImportAnalysisVO) => {
    return await request.post({ url: `/police/monitor-results-import-analysis/create`, data })
  },

  // 修改公安监测结果导入分析
  updateMonitorResultsImportAnalysis: async (data: MonitorResultsImportAnalysisVO) => {
    return await request.put({ url: `/police/monitor-results-import-analysis/update`, data })
  },

  // 删除公安监测结果导入分析
  deleteMonitorResultsImportAnalysis: async (id: number) => {
    return await request.delete({ url: `/police/monitor-results-import-analysis/delete?id=` + id })
  },

  // 导出公安监测结果导入分析 Excel
  exportMonitorResultsImportAnalysis: async (params) => {
    return await request.download({ url: `/police/monitor-results-import-analysis/export-excel`, params })
  },

  // 查询公安监测结果导入分析分页
  queryHuazhiMonitorOnOffResultPage: async (params: any) => {
    return await request.get({ url: `/police/monitor-results-huazhi-analysis/pageOnOff`, params })
  },
  // 导出公安监测结果导入分析 Excel
  exportMonitorResultsHuazhiAnalysisExcel: async (params) => {
    return await request.download({ url: `/police/monitor-results-huazhi-analysis/export-excel`, params })
  },
}
