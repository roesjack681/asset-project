import request from '@/config/axios'

// 信号灯 VO
export interface OpsSignalLightVO {
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
  ledPattern: string // 信号灯图案
  ledType: string // 灯具类型
  ledSize: string // 尺寸数据
  ledHeadNum: string // 灯头数量
  arrangement: string // 排列方式
  signalDisplayMode: string // 信号显示方式
  deletedTime: Date // 删除时间
}

// 信号灯 API
export const OpsSignalLightApi = {
  // 查询信号灯分页
  getOpsSignalLightPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-signal-light/page`, params })
  },

  // 查询信号灯详情
  getOpsSignalLight: async (id: number) => {
    return await request.get({ url: `/traffic/ops-signal-light/get?id=` + id })
  },

  // 新增信号灯
  createOpsSignalLight: async (data: OpsSignalLightVO) => {
    return await request.post({ url: `/traffic/ops-signal-light/create`, data })
  },

  // 修改信号灯
  updateOpsSignalLight: async (data: OpsSignalLightVO) => {
    return await request.put({ url: `/traffic/ops-signal-light/update`, data })
  },

  // 删除信号灯
  deleteOpsSignalLight: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-signal-light/delete?id=` + id })
  },

  // 导出信号灯 Excel
  exportOpsSignalLight: async (params) => {
    return await request.download({ url: `/traffic/ops-signal-light/export-excel`, params })
  }
}