import request from '@/config/axios'

// 警用装备统计 VO
export interface OpsPoliceEquipmentStatisticsVO {
  deptId: string // 部门编号
  deptName: string // 部门名称
  totalNum: number // 领用数量总和
}

// 警用装备统计详情 VO
export interface OpsPoliceEquipmentStatisticsDetailVO {
  id: string // 主键
  equId: string // 装备编号
  equName: string // 装备名称
  psn: string // PSN
  brand: string // 品牌
  model: string // 型号
  useDeptBh: string // 使用部门编号
  useDept: string // 使用部门
  userName: string // 使用人
  userBh: string // 使用人编号
  issueTime: Date // 领用时间
}

// 警用装备统计 API
export const OpsPoliceEquipmentStatisticsApi = {
  // 查询警用装备统计分页
  getOpsPoliceEquipmentStatisticsPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-police-equipment-statistics/page`, params })
  },

  // 查询警用装备统计详情列表
  getOpsPoliceEquipmentStatisticsDetail: async (params: any) => {
    return await request.get({ url: `/traffic/ops-police-equipment-statistics/detail`, params })
  },

  // 导出警用装备统计 Excel
  exportOpsPoliceEquipmentStatistics: async (params) => {
    return await request.download({ url: `/traffic/ops-police-equipment-statistics/export-excel`, params })
  }
}
