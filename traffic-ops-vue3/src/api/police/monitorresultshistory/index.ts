import request from '@/config/axios'

// 公安设备监测结果历史 VO
export interface MonitorResultsHistoryVO {
  id: string // 主键
  monitorDate: Date // 检测日期
  equType: string // 设备类型
  equId: string // 设备id
  ipAddr: string // IP地址
  equName: string // 设备名称
  memberBh: string // 杆件编号
  direction: string // 方向
  eventType: string // 检测事件
  occurrenceTime: Date // 检测时间
  status: string // 状态
  resultImg: string // 检测结果图片
  isRepairOrder: string // 是否为工单
  maintenanceId: string // 维保主键
  maintenanceUserId: string // 维护人员编号
  maintenancePersonnel: string // 维护人员
  maintenancePhone: string // 维护人员手机号
  equSituation: string // 设备情况
  equDesc: string // 设备情况描述
  evidenceFileId: string // 设备情况证据
  remarkDate: Date // 备注时间
  changeSituation: string // 变更情况：0-未变化；1-已修复；2-未修复
  deletedTime: Date // 删除时间
}

// 公安设备监测结果历史 API
export const MonitorResultsHistoryApi = {
  // 查询公安设备监测结果历史分页
  getMonitorResultsHistoryPage: async (params: any) => {
    return await request.get({ url: `/police/monitor-results-history/page`, params })
  },

  // 查询公安设备监测结果历史详情
  getMonitorResultsHistory: async (id: number) => {
    return await request.get({ url: `/police/monitor-results-history/get?id=` + id })
  },

  // 新增公安设备监测结果历史
  createMonitorResultsHistory: async (data: MonitorResultsHistoryVO) => {
    return await request.post({ url: `/police/monitor-results-history/create`, data })
  },

  // 修改公安设备监测结果历史
  updateMonitorResultsHistory: async (data: MonitorResultsHistoryVO) => {
    return await request.put({ url: `/police/monitor-results-history/update`, data })
  },

  // 删除公安设备监测结果历史
  deleteMonitorResultsHistory: async (id: number) => {
    return await request.delete({ url: `/police/monitor-results-history/delete?id=` + id })
  },

  // 导出公安设备监测结果历史 Excel
  exportMonitorResultsHistory: async (params) => {
    return await request.download({ url: `/police/monitor-results-history/export-excel`, params })
  }
}