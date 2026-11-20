import request from '@/config/axios'

// 重点考核设备通断检测 VO
export interface EquipPingMonitorResultVO {
  id: string // 主键
  equId: string // 设备id
  ipAddr: string // 设备IP
  occurrenceTime: Date // 检测时间
  pingStatus: string // ping状态
  pingCnt: number // ping次数
  lossRate: number // 丢包率
  source: string // 来源：1-自动；2-手动
  execBatchNum: string // 执行批次号
  deletedTime: Date // 删除时间
}

// 重点考核设备通断检测 API
export const EquipPingMonitorResultApi = {
  // 查询重点考核设备通断检测分页
  getEquipPingMonitorResultPage: async (params: any) => {
    return await request.get({ url: `/traffic/equip-ping-monitor-result/page`, params })
  },

  // 查询重点考核设备通断检测详情
  getEquipPingMonitorResult: async (id: number) => {
    return await request.get({ url: `/traffic/equip-ping-monitor-result/get?id=` + id })
  },

  // 新增重点考核设备通断检测
  createEquipPingMonitorResult: async (data: EquipPingMonitorResultVO) => {
    return await request.post({ url: `/traffic/equip-ping-monitor-result/create`, data })
  },

  // 修改重点考核设备通断检测
  updateEquipPingMonitorResult: async (data: EquipPingMonitorResultVO) => {
    return await request.put({ url: `/traffic/equip-ping-monitor-result/update`, data })
  },

  // 删除重点考核设备通断检测
  deleteEquipPingMonitorResult: async (id: number) => {
    return await request.delete({ url: `/traffic/equip-ping-monitor-result/delete?id=` + id })
  },

  // 导出重点考核设备通断检测 Excel
  exportEquipPingMonitorResult: async (params) => {
    return await request.download({ url: `/traffic/equip-ping-monitor-result/export-excel`, params })
  }
}