import request from '@/config/axios'

// 警用装备报修信息 VO
export interface OpsPoliceMaintenanceVO {
  id: string // 主键
  reportPersonnel: string // 上报人员
  reportUserId: string // 上报人员编号
  reportTime: Date // 上报时间
  reportDeptId: string // 上报人员部门编号
  reportDept: string // 上报人员部门
  auditorBh: string // 审核人员编号
  auditDate: Date // 审核时间
  auditor: string // 审核人
  auditOpinions: string // 审核意见
  maintenanceUserId: string // 维修人编号
  maintenancePersonnel: string // 维修人
  maintenancePhone: string // 维护人员手机号
  maintenanceDate: Date // 维护时间
  suggestion: string // 维护建议
  status: string // 状态
  remark: string // 备注
  deletedTime: Date // 删除时间
}

// 警用装备报修信息 API
export const OpsPoliceMaintenanceApi = {
  // 查询警用装备报修信息分页
  getOpsPoliceMaintenancePage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-police-maintenance/page`, params })
  },

  // 查询警用装备报修信息详情
  getOpsPoliceMaintenance: async (id: number) => {
    return await request.get({ url: `/traffic/ops-police-maintenance/get?id=` + id })
  },

  // 新增警用装备报修信息
  createOpsPoliceMaintenance: async (data: OpsPoliceMaintenanceVO) => {
    return await request.post({ url: `/traffic/ops-police-maintenance/create`, data })
  },

  // 修改警用装备报修信息
  updateOpsPoliceMaintenance: async (data: OpsPoliceMaintenanceVO) => {
    return await request.put({ url: `/traffic/ops-police-maintenance/update`, data })
  },

  // 删除警用装备报修信息
  deleteOpsPoliceMaintenance: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-police-maintenance/delete?id=` + id })
  },

  // 导出警用装备报修信息 Excel
  exportOpsPoliceMaintenance: async (params) => {
    return await request.download({ url: `/traffic/ops-police-maintenance/export-excel`, params })
  },

  //审核警用装备报修信息
  auditOpsPoliceMaintenance: async (data: OpsPoliceMaintenanceVO) => {
    return await request.post({ url: `/traffic/ops-police-maintenance/audit`, data })
  },

  //获得未分配的警用装备
  unallocatedPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-police-equipment/unallocatedPage`, params })
  },

  //设备psn模糊查询
  queryIssuedPoliceEquipment: async (params: any) => {
    return await request.get({ url: `/traffic/ops-police-equipment/queryIssuedPoliceEquipment`, params })
  },
}
