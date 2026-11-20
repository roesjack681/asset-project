import request from '@/config/axios'

// 电警 VO
export interface OpsElectricPoliceVO {
  id: string // 主键
  equNumber: string // 设备编号
  equName: string // 设备名称
  equType: string // 设备类型
  softwareVersion: string // 软件版本
  roadName: string // 所在道路名称
  direction: string // 方向
  intersectionName: string // 灯控路口名称
  djType: string // 电警类型
  lng: string // GPS定位-经度
  lwBh: string // 路网编号
  lat: string // GPS定位-纬度
  jzBh: string // 集指编号
  adress: string // 安装位置
  installDate: Date // 安装时间
  manufacturer: string // 厂家
  equModel: string // 型号
  unit: string // 归属单位
  shootingRange: string // 拍摄范围
  deletedTime: Date // 删除时间
  lens: string // 镜头
  focalLength: string // 焦距
  aperture: string // 光圈
  installationMethod: string // 安装方式
  communicationModel: string // 通信方式
  protocol: string // 协议
  ipAddr: string // IP地址
  gateway: string // 网关
  gjxAddr: string // 光交箱位置信息
  gjxLng: string // 光交箱GPS定位-经度
  gjxLat: string // 光交箱GPS定位-纬度
}

// 电警 API
export const OpsElectricPoliceApi = {
  // 查询电警分页
  getOpsElectricPolicePage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-electric-police/page`, params })
  },

  // 查询电警详情
  getOpsElectricPolice: async (id: number) => {
    return await request.get({ url: `/traffic/ops-electric-police/get?id=` + id })
  },

  // 新增电警
  createOpsElectricPolice: async (data: OpsElectricPoliceVO) => {
    return await request.post({ url: `/traffic/ops-electric-police/create`, data })
  },

  // 修改电警
  updateOpsElectricPolice: async (data: OpsElectricPoliceVO) => {
    return await request.put({ url: `/traffic/ops-electric-police/update`, data })
  },

  // 删除电警
  deleteOpsElectricPolice: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-electric-police/delete?id=` + id })
  },

  // 导出电警 Excel
  exportOpsElectricPolice: async (params) => {
    return await request.download({ url: `/traffic/ops-electric-police/export-excel`, params })
  },
  //  根据IP自动补充电警信息
  autocompleteTrafficOpsElectricPoliceList: async (ipAddr: string) => {
    return await request.download({ url: `/traffic/ops-electric-police/autocompleteTrafficOpsElectricPoliceList?ipAddr=` + ipAddr })
  }
}