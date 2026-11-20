import request from '@/config/axios'

// 终端设备信息 VO
export interface OpsDeviceVO {
  id: string // 主键
  model: string // model
  imei: string // IMEI
  plateNum: string // 车牌号
  cameraInfo: string // 摄像头信息
  status: string // 状态
  remark: string // 备注
  deletedTime: Date // 删除时间
}

// 终端设备信息 API
export const OpsDeviceApi = {
  // 查询终端设备信息分页
  getOpsDevicePage: async (params: any) => {
    return await request.get({ url: `/public/ops-device/page`, params })
  },

  // 查询终端设备信息详情
  getOpsDevice: async (id: number) => {
    return await request.get({ url: `/public/ops-device/get?id=` + id })
  },

  // 新增终端设备信息
  createOpsDevice: async (data: OpsDeviceVO) => {
    return await request.post({ url: `/public/ops-device/create`, data })
  },

  // 修改终端设备信息
  updateOpsDevice: async (data: OpsDeviceVO) => {
    return await request.put({ url: `/public/ops-device/update`, data })
  },

  // 删除终端设备信息
  deleteOpsDevice: async (id: number) => {
    return await request.delete({ url: `/public/ops-device/delete?id=` + id })
  },

  // 导出终端设备信息 Excel
  exportOpsDevice: async (params) => {
    return await request.download({ url: `/public/ops-device/export-excel`, params })
  },
  //获取所有位置车辆
  getOpsDeviceLocationPage: async (params: any) => {
    return await request.get({ url: `/ops/V-device-location-new/page`, params })
  }

}
