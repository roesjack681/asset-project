import request from '@/config/axios'

// 标志牌 VO
export interface TrafficOpsSignBoardVO {
  id: string // 主键
  equName: string // 设备名称
  equType: string // 设备类型
  installDate: Date // 安装时间
  adress: string // 安装位置
  lng: string // GPS定位-经度
  lat: string // GPS定位-纬度
  direction: string // 方向
  labelContent: string // 标牌内容
  specifications: string // 规格
  ggunit: string // 规格单位
  num: string // 数量
  unit: string // 归属单位
  dominion: string // 所属管辖单位
  manufacturer: string // 生产厂家
  installManufacturer: string // 安装厂家
  deletedTime: Date // 删除时间
}

// 标志牌 API
export const TrafficOpsSignBoardApi = {
  // 查询标志牌分页
  getTrafficOpsSignBoardPage: async (params: any) => {
    return await request.get({ url: `/ops/traffic-ops-sign-board/page`, params })
  },

  // 查询标志牌详情
  getTrafficOpsSignBoard: async (id: number) => {
    return await request.get({ url: `/ops/traffic-ops-sign-board/get?id=` + id })
  },

  // 新增标志牌
  createTrafficOpsSignBoard: async (data: TrafficOpsSignBoardVO) => {
    return await request.post({ url: `/ops/traffic-ops-sign-board/create`, data })
  },

  // 修改标志牌
  updateTrafficOpsSignBoard: async (data: TrafficOpsSignBoardVO) => {
    return await request.put({ url: `/ops/traffic-ops-sign-board/update`, data })
  },

  // 删除标志牌
  deleteTrafficOpsSignBoard: async (id: number) => {
    return await request.delete({ url: `/ops/traffic-ops-sign-board/delete?id=` + id })
  },

  // 导出标志牌 Excel
  exportTrafficOpsSignBoard: async (params) => {
    return await request.download({ url: `/ops/traffic-ops-sign-board/export-excel`, params })
  }
}