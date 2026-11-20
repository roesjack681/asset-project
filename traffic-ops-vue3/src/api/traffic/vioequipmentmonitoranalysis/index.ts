import request from '@/config/axios'

// 集指设备检测分析 VO
export interface VioEquipmentMonitorAnalysisVO {
  id: string // 主键
  equId: string // 设备id
  kkbh: string // 卡口编号
  sbbh: string // 设备编号
  videoNormalCnt: number // 视频正常数
  videoAbnormalCnt: number // 视频异常数
  videoHealthRate: number // 视频健康率
  pingNormalCnt: number // ping正常数
  pingAbnormalCnt: number // ping异常数
  pingHealthRate: number // ping健康率
  noSurveilCnt: number // 无违法次数
  validSurveilCnt: number // 有效违法数
  invalidSurveilCnt: number // 无效违法数
  recognitionAccuracyRate: number // 识别准确率
  monitorCnt: number // 检测次数
  deletedTime: Date // 删除时间
}

// 违法抓拍设备每日情况 VO
export interface ViewVioEquipmentDailySituationVO {
  sbbh: string // 设备编号
  kkbh: string // 卡口编号
  ipAddr: string // IP地址
  wfsj: Date //
  dswfCnt: number //
  yxwfCnt: number //
  wxwfCnt: number //
  jcsj: Date // 检测时间
  kyl: number // 数据可用率
}

export interface VioEquipmentMonitorRemarkSaveReqVO {
  sbbh: string // 设备编号
  operateMark: string // 视频正常数
  operateDesc: string // 视频异常数
  operateDate: Date // 删除时间
}

// 集指设备检测分析 API
export const VioEquipmentMonitorAnalysisApi = {
  // 查询集指设备检测分析分页
  getVioEquipmentMonitorAnalysisPage: async (params: any) => {
    return await request.get({ url: `/traffic/vio-equipment-monitor-analysis/page`, params })
  },
  // 查询违法抓拍设备每日情况分页
  getViewVioEquipmentDailySituationPage: async (params: any) => {
    return await request.get({ url: `/traffic/vio-equipment-monitor-analysis/pageDailySituation`, params })
  },

  // 查询集指设备检测分析详情
  getVioEquipmentMonitorAnalysis: async (id: number) => {
    return await request.get({ url: `/traffic/vio-equipment-monitor-analysis/get?id=` + id })
  },

  // 新增集指设备检测分析
  createVioEquipmentMonitorAnalysis: async (data: VioEquipmentMonitorAnalysisVO) => {
    return await request.post({ url: `/traffic/vio-equipment-monitor-analysis/create`, data })
  },

  // 修改集指设备检测分析
  updateVioEquipmentMonitorAnalysis: async (data: VioEquipmentMonitorAnalysisVO) => {
    return await request.put({ url: `/traffic/vio-equipment-monitor-analysis/update`, data })
  },

  // 删除集指设备检测分析
  deleteVioEquipmentMonitorAnalysis: async (id: number) => {
    return await request.delete({ url: `/traffic/vio-equipment-monitor-analysis/delete?id=` + id })
  },

  // 导出集指设备检测分析 Excel
  exportVioEquipmentMonitorAnalysis: async (params) => {
    return await request.download({ url: `/traffic/vio-equipment-monitor-analysis/export-excel`, params })
  },
   // 导出违法抓拍设备每日情况 Excel
  exportViewVioEquipmentDailySituation: async (params) => {
    return await request.download({ url: `/traffic/vio-equipment-monitor-analysis/exportDailySituation`, params })
  },
  vioEquipmentMonitorAnalysispageDetail: async (params: any) => {
    return await request.get({ url: `/traffic/vio-equipment-monitor-analysis/pageDetail`, params })
  },
  //无效违法
  analysisVioInvalidReasonPieChart: async (params: any) => {
    return await request.get({ url: `/traffic/vio-equipment-monitor-analysis/analysisVioInvalidReasonPieChart`, params })
  },
  //有效违法
  analysisVioValidLineChart: async (params: any) => {
    return await request.get({ url: `/traffic/vio-equipment-monitor-analysis/analysisVioValidLineChart`, params })
  },
  //有效违法行为分析饼图
  analysisVioIllegalPieChart: async (params: any) => {
    return await request.get({ url: `/traffic/vio-equipment-monitor-analysis/analysisVioIllegalPieChart`, params })
  },
  // 查询集指设备错误原因分析分页
  getVioEquipmentInvalidReasonAnalysisPage: async (params: any) => {
    return await request.get({ url: `/traffic/vio-equipment-monitor-analysis/pageInvalidReasonAnalysis`, params })
  },
  // 导出集指设备错误原因分析 Excel
  exportVioEquipmentInvalidReasonAnalysis: async (params) => {
    return await request.download({ url: `/traffic/vio-equipment-monitor-analysis/exportInvalidReasonAnalysis`, params })
  },
   // 修改集指设备检测分析
  remarkVioEquipmentMonitor: async (data: VioEquipmentMonitorRemarkSaveReqVO) => {
    return await request.post({ url: `/traffic/vio-equipment-monitor-analysis/remarkVioEquipmentMonitor`, data })
  },
  // 查询集指设备违法原因分析分页
  getVioEquipmentIllegalAnalysisPage: async (params: any) => {
    return await request.get({ url: `/traffic/vio-equipment-monitor-analysis/pageIllegalAnalysis`, params })
  },
  // 导出集指设备违法原因分析 Excel
  exportVioEquipmentIllegalAnalysis: async (params) => {
    return await request.download({ url: `/traffic/vio-equipment-monitor-analysis/exportIllegalAnalysis`, params })
  }
}
