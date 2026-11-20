import request from '@/config/axios'

// Led屏 VO
export interface OpsLedVO {
  id: string // 主键
  equNumber: string // 设备编号
  equName: string // 设备名称
  equType: string // 设备类型
  roadName: string // 所在道路名称
  intersectionName: string // 灯控路口名称
  lng: string // GPS定位-经度
  lat: string // GPS定位-纬度
  adress: string // 安装位置
  installDate: Date // 安装时间
  manufacturer: string // 厂家
  equModel: string // 型号
  unit: string // 归属单位
  size: string // 尺寸
  displayPurpose: string // 显示用途
  deletedTime: Date // 删除时间
}

// Led屏 API
export const OpsLedApi = {
  // 查询Led屏分页
  getOpsLedPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-led/page`, params })
  },

  // 查询Led屏详情
  getOpsLed: async (id: number) => {
    return await request.get({ url: `/traffic/ops-led/get?id=` + id })
  },

  // 新增Led屏
  createOpsLed: async (data: OpsLedVO) => {
    return await request.post({ url: `/traffic/ops-led/create`, data })
  },

  // 修改Led屏
  updateOpsLed: async (data: OpsLedVO) => {
    return await request.put({ url: `/traffic/ops-led/update`, data })
  },

  // 删除Led屏
  deleteOpsLed: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-led/delete?id=` + id })
  },

  // 导出Led屏 Excel
  exportOpsLed: async (params) => {
    return await request.download({ url: `/traffic/ops-led/export-excel`, params })
  }
}