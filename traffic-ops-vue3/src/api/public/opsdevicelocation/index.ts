import request from '@/config/axios'

// 终端最新位置 VO
export interface OpsDeviceLocationVO {
  id: string // 主键
  deviceId: string // 终端编号
  latitude: number // GPS纬度
  longitude: number // GPS经度
  speed: number // GPS速度(km/h)
  direction: number // 方向(0-359度)
  reportTime: Date // 位置上报时间
}

// 终端最新位置 API
export const OpsDeviceLocationApi = {
  // 查询终端最新位置分页
  getOpsDeviceLocationPage: async (params: any) => {
    return await request.get({ url: `/public/ops-device-location/page`, params })
  },

  // 查询终端最新位置详情
  getOpsDeviceLocation: async (id: number) => {
    return await request.get({ url: `/public/ops-device-location/get?id=` + id })
  },

  // 新增终端最新位置
  createOpsDeviceLocation: async (data: OpsDeviceLocationVO) => {
    return await request.post({ url: `/public/ops-device-location/create`, data })
  },

  // 修改终端最新位置
  updateOpsDeviceLocation: async (data: OpsDeviceLocationVO) => {
    return await request.put({ url: `/public/ops-device-location/update`, data })
  },

  // 删除终端最新位置
  deleteOpsDeviceLocation: async (id: number) => {
    return await request.delete({ url: `/public/ops-device-location/delete?id=` + id })
  },

  // 导出终端最新位置 Excel
  exportOpsDeviceLocation: async (params) => {
    return await request.download({ url: `/public/ops-device-location/export-excel`, params })
  }
}