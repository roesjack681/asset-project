import request from '@/config/axios'

// 警用装备报修详情信息 VO
export interface OpsPoliceMaintenanceDetailVO {
  id: string // 主键
  maintenanceId: string // 报修编号
  equId: string // 装备编号
  problem: string // 问题描述
  deletedTime: Date // 删除时间
}

// 警用装备报修详情信息 API
export const OpsPoliceMaintenanceDetailApi = {
  // 查询警用装备报修详情信息分页
  getOpsPoliceMaintenanceDetailPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-police-maintenance-detail/page`, params })
  },

  // 查询警用装备报修详情信息详情
  getOpsPoliceMaintenanceDetail: async (id: number) => {
    return await request.get({ url: `/traffic/ops-police-maintenance-detail/get?id=` + id })
  },

  // 新增警用装备报修详情信息
  createOpsPoliceMaintenanceDetail: async (data: OpsPoliceMaintenanceDetailVO) => {
    return await request.post({ url: `/traffic/ops-police-maintenance-detail/create`, data })
  },

  // 修改警用装备报修详情信息
  updateOpsPoliceMaintenanceDetail: async (data: OpsPoliceMaintenanceDetailVO) => {
    return await request.put({ url: `/traffic/ops-police-maintenance-detail/update`, data })
  },

  // 删除警用装备报修详情信息
  deleteOpsPoliceMaintenanceDetail: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-police-maintenance-detail/delete?id=` + id })
  },

  // 导出警用装备报修详情信息 Excel
  exportOpsPoliceMaintenanceDetail: async (params) => {
    return await request.download({ url: `/traffic/ops-police-maintenance-detail/export-excel`, params })
  }
}