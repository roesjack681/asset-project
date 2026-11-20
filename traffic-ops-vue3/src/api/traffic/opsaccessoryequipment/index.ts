import request from '@/config/axios'

// 杆件附属设备 VO
export interface OpsAccessoryEquipmentVO {
  id: string // 主键
  memberId: string // 杆件编号
  equType: string // 设备类型名称
  manufacturer: string // 厂家
  equModel: string // 设备型号
  equNum: string // 数量
  remark: string // 备注
  deletedTime: Date // 删除时间
}

// 杆件附属设备 API
export const OpsAccessoryEquipmentApi = {
  // 查询杆件附属设备分页
  getOpsAccessoryEquipmentPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-accessory-equipment/page`, params })
  },

  // 查询杆件附属设备详情
  getOpsAccessoryEquipment: async (id: number) => {
    return await request.get({ url: `/traffic/ops-accessory-equipment/get?id=` + id })
  },

  // 新增杆件附属设备
  createOpsAccessoryEquipment: async (data: OpsAccessoryEquipmentVO) => {
    return await request.post({ url: `/traffic/ops-accessory-equipment/create`, data })
  },

  // 修改杆件附属设备
  updateOpsAccessoryEquipment: async (data: OpsAccessoryEquipmentVO) => {
    return await request.put({ url: `/traffic/ops-accessory-equipment/update`, data })
  },

  // 删除杆件附属设备
  deleteOpsAccessoryEquipment: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-accessory-equipment/delete?id=` + id })
  },

  // 导出杆件附属设备 Excel
  exportOpsAccessoryEquipment: async (params) => {
    return await request.download({ url: `/traffic/ops-accessory-equipment/export-excel`, params })
  }
}