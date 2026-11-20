import request from '@/config/axios'

// 信号灯 VO
export interface OpsSignalVO {
  id: string // 主键
  equName: string // 设备名称
  equType: string // 设备类型
  roadName: string // 所在道路名称
  intersectionName: string // 灯控路口名称
  lng: string // GPS定位-经度
  lat: string // GPS定位-纬度
  adress: string // 安装位置
  installDate: Date // 安装时间
  networkType: string // 联网方式
  networkAddr: string // 联网位置
  phase: string // 信号控制相位
  networkStatus: string // 联网状态
  manufacturer: string // 信号机厂家
  equModel: string // 信号机型号
  equNumber: string // 信号机编号
  ipAddr: string // IP地址
  mask: string // 子网掩码
  gateway: string // 网关
  belongLoop: string // 所属环路
  networkProtocol: string // 联网协议
  serverIp: string // 服务器IP地址
  panorama: string // 路口全景图
  accessControl: string // 设备箱门禁
  unit: string // 归属单位
  deletedTime: Date // 删除时间
}

// 信号灯 API
export const OpsSignalApi = {
  // 查询信号灯分页
  getOpsSignalPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-signal/page`, params })
  },

  // 查询信号灯详情
  getOpsSignal: async (id: number) => {
    return await request.get({ url: `/traffic/ops-signal/get?id=` + id })
  },

  // 新增信号灯
  createOpsSignal: async (data: OpsSignalVO) => {
    return await request.post({ url: `/traffic/ops-signal/create`, data })
  },

  // 修改信号灯
  updateOpsSignal: async (data: OpsSignalVO) => {
    return await request.put({ url: `/traffic/ops-signal/update`, data })
  },

  // 删除信号灯
  deleteOpsSignal: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-signal/delete?id=` + id })
  },

  // 导出信号灯 Excel
  exportOpsSignal: async (params) => {
    return await request.download({ url: `/traffic/ops-signal/export-excel`, params })
  },

  //1.信号机配时周方案
  queryJunctionPlanInfo: async (id: number) => {
    return await request.get({ url: `/traffic/ops-signal/queryJunctionPlanInfo?signalId=` + id })
  },
  //信号机配时周方案
  queryJunctionDayPlanList: async (junctionId, dayPlanId) => {
    return await request.get({ url: `/traffic/ops-signal/queryJunctionDayPlanList?junctionId=${junctionId}&dayPlanId=` + dayPlanId})
  },

  //信号机配时阶段信息
  queryJunctionTimeStageList: async (junctionId: number, planId: number) => {
    return await request.get({ url: `/traffic/ops-signal/queryJunctionTimeStageList?junctionId=${junctionId}&planId=${planId}`})
  },

}
