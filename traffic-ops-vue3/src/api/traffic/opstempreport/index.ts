import request from '@/config/axios'

// 交警运维临时报 VO
export interface OpsTempReportVO {
  id: string // 主键
  equId: string // 设备id
  ipAddr: string // IP地址
  source: string // 来源：1-接口；2-导入
  deletedTime: Date // 删除时间
}

// VIEW VO
export interface ViewOpsTempReportVO {
  id: string // 主键
  equId: string // 设备id
  ipAddr: string // IP地址
  source: string // 来源：1-接口；2-导入
  deletedTime: Date // 删除时间
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
  shootingRange: string // 拍摄范围
  lens: string // 镜头
  focalLength: string // 焦距
  aperture: string // 光圈
  softwareVersion: string // 软件版本
  installationMethod: string // 安装方式
  communicationModel: string // 通信方式
  protocol: string // 协议
  gateway: string // 网关
  gjxAddr: string // 光交箱位置信息
  gjxLng: string // 光交箱GPS定位-经度
  gjxLat: string // 光交箱GPS定位-纬度
  direction: string // 方向
  djType: string // 电警类型
  lwBh: string // 路网编号
  jzBh: string // 集指编号
  dominion: string // 管辖单位
  status: string // 状态
  equImg: string // 设备图片
  memberBh: string // 杆件编号
  isCheck: string // 是否核查
  equLable: string // 设备标签
  userName: string // 用户名
  password: string // 密码
  addressXh: string // 安装位置序号
  kkbh: string // 卡口编号
  sbbh: string // 设备编号
  maintenanceExpiryDate: Date // 维保到期时间
}

// 交警运维临时报 API
export const OpsTempReportApi = {
  // 查询VIEW分页
  getViewOpsTempReportPage: async (params: any) => {
    return await request.get({ url: `/traffic/view-ops-temp-report/page`, params })
  },

  // 查询VIEW详情
  getViewOpsTempReport: async (id: number) => {
    return await request.get({ url: `/traffic/view-ops-temp-report/get?id=` + id })
  },
  // 导出VIEW Excel
  exportViewOpsTempReport: async (params) => {
    return await request.download({ url: `/traffic/view-ops-temp-report/export-excel`, params })
  },
  // 查询交警运维临时报分页
  getOpsTempReportPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-temp-report/page`, params })
  },

  // 查询交警运维临时报详情
  getOpsTempReport: async (id: number) => {
    return await request.get({ url: `/traffic/ops-temp-report/get?id=` + id })
  },

  // 新增交警运维临时报
  createOpsTempReport: async (data: OpsTempReportVO) => {
    return await request.post({ url: `/traffic/ops-temp-report/create`, data })
  },

  // 修改交警运维临时报
  updateOpsTempReport: async (data: OpsTempReportVO) => {
    return await request.put({ url: `/traffic/ops-temp-report/update`, data })
  },

  // 删除交警运维临时报
  deleteOpsTempReport: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-temp-report/delete?id=` + id })
  },

  // 清空交警运维临时报
  clearOpsTempReport: async () => {
    return await request.delete({ url: `/traffic/ops-temp-report/clearAll`})
  },

  // 导出交警运维临时报 Excel
  exportOpsTempReport: async (params) => {
    return await request.download({ url: `/traffic/ops-temp-report/export-excel`, params })
  }
}