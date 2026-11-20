import request from '@/config/axios'

// 警用装备资产信息 VO
export interface OpsPoliceEquipmentVO {
  id: string // 主键
  equName: string // 设备名称
  equType: string // 类型
  encryptionCard: string // 加密卡
  psn: string // PSN
  brand: string // 品牌
  model: string // 型号
  manufacturer: string // 厂家
  battery: string // 电池
  remark: string // 备注
  recordBh: string // 录入人编号
  recorder: string // 录入人
  recordDeptBh: string // 录入部门编号
  recordDept: string // 录入部门
  useDeptBh: string // 使用部门编号
  useDept: string // 使用部门
  userBh: string // 使用人编号
  userName: string // 使用人
  deletedTime: Date // 删除时间
}

// 警用装备资产信息 API
export const OpsPoliceEquipmentApi = {
  // 查询警用装备资产信息分页
  getOpsPoliceEquipmentPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-police-equipment/page`, params })
  },

  // 查询警用装备资产信息详情
  getOpsPoliceEquipment: async (id: number) => {
    return await request.get({ url: `/traffic/ops-police-equipment/get?id=` + id })
  },

  // 新增警用装备资产信息
  createOpsPoliceEquipment: async (data: OpsPoliceEquipmentVO) => {
    return await request.post({ url: `/traffic/ops-police-equipment/create`, data })
  },

  // 修改警用装备资产信息
  updateOpsPoliceEquipment: async (data: OpsPoliceEquipmentVO) => {
    return await request.put({ url: `/traffic/ops-police-equipment/update`, data })
  },

  // 删除警用装备资产信息
  deleteOpsPoliceEquipment: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-police-equipment/delete?id=` + id })
  },

  // 导出警用装备资产信息 Excel
  exportOpsPoliceEquipment: async (params) => {
    return await request.download({ url: `/traffic/ops-police-equipment/export-excel`, params })
  },

  //厂家信息模糊查询?keyword=&keyType=
  autocompletePoliceEquipInfoList: async (params: any) => {
    return await request.get({ url: `/traffic/ops-police-equipment/autocompletePoliceEquipInfoList`, params })
  },
  //资产回收
  recycleOpsPoliceEquipment: async (id: number) => {
    return await request.post({ url: `/traffic/ops-police-equipment/recycle?id=` + id })
  },
}
