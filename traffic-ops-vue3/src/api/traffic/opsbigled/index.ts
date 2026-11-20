import request from '@/config/axios'

// 大屏 VO
export interface OpsBigledVO {
  id: string // 主键
  equNumber: string // 设备编号
  equName: string // 设备名称
  equType: string // 设备类型
  roadName: string // 所在道路名称
  intersectionName: string // 灯控路口名称
  lng: string // GPS定位-经度
  lat: string // GPS定位-纬度
  describe: string // 描述
  adress: string // 点位名称
  ipAddr: string // IP地址
  screenOrientation: string // 屏幕朝向
  subnet: string // 子网
  gateway: string // 网关
  ptzIp: string // 球机IP
  username: string // 用户名
  passwd: string // 密码
  violationCode: string // 违章编码
  inducedEncoding: string // 诱导编码
  dpbh: string // 大屏对应编号
  remark: string // 备注
  size: string // 大小
  installDate: Date // 安装时间
  manufacturer: string // 厂家
  unit: string // 归属单位
  deletedTime: Date // 删除时间
}

// 大屏 API
export const OpsBigledApi = {
  // 查询大屏分页
  getOpsBigledPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-bigled/page`, params })
  },

  // 查询大屏详情
  getOpsBigled: async (id: number) => {
    return await request.get({ url: `/traffic/ops-bigled/get?id=` + id })
  },

  // 新增大屏
  createOpsBigled: async (data: OpsBigledVO) => {
    return await request.post({ url: `/traffic/ops-bigled/create`, data })
  },

  // 修改大屏
  updateOpsBigled: async (data: OpsBigledVO) => {
    return await request.put({ url: `/traffic/ops-bigled/update`, data })
  },

  // 删除大屏
  deleteOpsBigled: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-bigled/delete?id=` + id })
  },

  // 导出大屏 Excel
  exportOpsBigled: async (params) => {
    return await request.download({ url: `/traffic/ops-bigled/export-excel`, params })
  }
}