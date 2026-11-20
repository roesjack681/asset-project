import request from '@/config/axios'

// 终端最新位置 VO
export interface OpsDeviceLocationNewVO {
  id: string // 主键
  deviceId: string // 终端编号
  latitude: number // GPS纬度
  longitude: number // GPS经度
  speed: number // GPS速度(km/h)
  direction: number // 方向(0-359度)
  reportTime: Date // 位置上报时间
}

// 终端最新位置 API
export const OpsDeviceLocationNewApi = {
  // 查询终端最新位置分页
  getOpsDeviceLocationNewPage: async (params: any) => {
    return await request.get({ url: `/public/ops-device-location-new/page`, params })
  },

  // 查询终端最新位置详情
  getOpsDeviceLocationNew: async (id: number) => {
    return await request.get({ url: `/public/ops-device-location-new/get?id=` + id })
  },

  // 新增终端最新位置
  createOpsDeviceLocationNew: async (data: OpsDeviceLocationNewVO) => {
    return await request.post({ url: `/public/ops-device-location-new/create`, data })
  },

  // 修改终端最新位置
  updateOpsDeviceLocationNew: async (data: OpsDeviceLocationNewVO) => {
    return await request.put({ url: `/public/ops-device-location-new/update`, data })
  },

  // 删除终端最新位置
  deleteOpsDeviceLocationNew: async (id: number) => {
    return await request.delete({ url: `/public/ops-device-location-new/delete?id=` + id })
  },

  // 导出终端最新位置 Excel
  exportOpsDeviceLocationNew: async (params) => {
    return await request.download({ url: `/public/ops-device-location-new/export-excel`, params })
  }
}