import request from '@/config/axios'

// 公安监测通达详情 VO
export interface MonitorPingDetailVO {
  id: string // 主键
  equId: string // 设备id
  ipAddr: string // 设备IP
  equLable: string // 设备标签
  occurrenceTime: Date // 检测时间
  pingStatus: string // ping状态
  pingCnt: number // ping次数
  lossRate: number // 丢包率
  source: string // 来源：1-自动；2-手动
  deletedTime: Date // 删除时间
}

// 公安监测通达详情 API
export const MonitorPingDetailApi = {
  // 查询公安监测通达详情分页
  getMonitorPingDetailPage: async (params: any) => {
    return await request.get({ url: `/police/monitor-ping-detail/page`, params })
  },

  // 查询公安监测通达详情详情
  getMonitorPingDetail: async (id: number) => {
    return await request.get({ url: `/police/monitor-ping-detail/get?id=` + id })
  },

  // 新增公安监测通达详情
  createMonitorPingDetail: async (data: MonitorPingDetailVO) => {
    return await request.post({ url: `/police/monitor-ping-detail/create`, data })
  },

  // 修改公安监测通达详情
  updateMonitorPingDetail: async (data: MonitorPingDetailVO) => {
    return await request.put({ url: `/police/monitor-ping-detail/update`, data })
  },

  // 删除公安监测通达详情
  deleteMonitorPingDetail: async (id: number) => {
    return await request.delete({ url: `/police/monitor-ping-detail/delete?id=` + id })
  },

  // 导出公安监测通达详情 Excel
  exportMonitorPingDetail: async (params) => {
    return await request.download({ url: `/police/monitor-ping-detail/export-excel`, params })
  }
}