import request from '@/config/axios'

// 公安设备监测结果 VO
export interface MonitorResultsVO {
  id: string // 主键
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
  source: string // 来源：1-接口；2-导入
  deletedTime: Date // 删除时间
}

export interface MonitorResultsRemarkVO {
  id: string // 主键
  equSituationList: [] // 设备情况
  equDesc: string // 设备情况描述
}

// 公安设备监测结果 API
export const MonitorResultsApi = {
  // 查询公安设备监测结果分页
  getMonitorResultsPage: async (params: any) => {
    return await request.get({ url: `/police/monitor-results/page`, params })
  },

  // 查询公安设备监测结果详情
  getMonitorResults: async (id: number) => {
    return await request.get({ url: `/police/monitor-results/get?id=` + id })
  },

  // 新增公安设备监测结果
  createMonitorResults: async (data: MonitorResultsVO) => {
    return await request.post({ url: `/police/monitor-results/create`, data })
  },

  // 修改公安设备监测结果
  updateMonitorResults: async (data: MonitorResultsVO) => {
    return await request.put({ url: `/police/monitor-results/update`, data })
  },

  // 删除公安设备监测结果
  deleteMonitorResults: async (id: number) => {
    return await request.delete({ url: `/police/monitor-results/delete?id=` + id })
  },

  // 导出公安设备监测结果 Excel
  exportMonitorResults: async (params) => {
    return await request.download({ url: `/police/monitor-results/export-excel`, params })
  },

  // 保存结果照片
  updateMonitorResults: async (data) => {
    return await request.post({ url: `/police/monitor-results/updateMonitorResults`, data })
  },

  // 修改公安设备监测结果
  remarkMonitorResults: async (data: MonitorResultsRemarkVO) => {
    return await request.post({ url: `/police/monitor-results/remarkMonitorResults`, data })
  },
}
