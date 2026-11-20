import request from '@/config/axios'

// 雷达 VO
export interface OpsRadarVO {
  id: string // 主键
  equName: string // 设备名称
  equNumber: string // 设备编号
  equType: string // 设备类型
  softwareVersion: string // 软件版本
  roadName: string // 所在道路名称
  intersectionName: string // 灯控路口名称
  lng: string // GPS定位-经度
  lat: string // GPS定位-纬度
  adress: string // 安装位置
  installDate: Date // 安装时间
  manufacturer: string // 厂家
  equModel: string // 型号
  unit: string // 归属单位
  operatingFrequency: string // 工作频率
  detectionRange: string // 探测距离
  detectionAngle: string // 探测角度
  installationMethod: string // 安装方式
  communicationModel: string // 通信方式
  protocol: string // 协议
  deletedTime: Date // 删除时间
}

// 雷达 API
export const OpsRadarApi = {
  // 查询雷达分页
  getOpsRadarPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-radar/page`, params })
  },

  // 查询雷达详情
  getOpsRadar: async (id: number) => {
    return await request.get({ url: `/traffic/ops-radar/get?id=` + id })
  },

  // 新增雷达
  createOpsRadar: async (data: OpsRadarVO) => {
    return await request.post({ url: `/traffic/ops-radar/create`, data })
  },

  // 修改雷达
  updateOpsRadar: async (data: OpsRadarVO) => {
    return await request.put({ url: `/traffic/ops-radar/update`, data })
  },

  // 删除雷达
  deleteOpsRadar: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-radar/delete?id=` + id })
  },

  // 导出雷达 Excel
  exportOpsRadar: async (params) => {
    return await request.download({ url: `/traffic/ops-radar/export-excel`, params })
  }
}