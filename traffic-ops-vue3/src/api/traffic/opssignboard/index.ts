import request from '@/config/axios'

// 标志牌 VO
export interface OpsSignBoardVO {
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
export const OpsSignBoardApi = {
  // 查询标志牌分页
  getOpsSignBoardPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-sign-board/page`, params })
  },

  // 查询标志牌详情
  getOpsSignBoard: async (id: number) => {
    return await request.get({ url: `/traffic/ops-sign-board/get?id=` + id })
  },

  // 新增标志牌
  createOpsSignBoard: async (data: OpsSignBoardVO) => {
    return await request.post({ url: `/traffic/ops-sign-board/create`, data })
  },

  // 修改标志牌
  updateOpsSignBoard: async (data: OpsSignBoardVO) => {
    return await request.put({ url: `/traffic/ops-sign-board/update`, data })
  },

  // 删除标志牌
  deleteOpsSignBoard: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-sign-board/delete?id=` + id })
  },

  // 导出标志牌 Excel
  exportOpsSignBoard: async (params) => {
    return await request.download({ url: `/traffic/ops-sign-board/export-excel`, params })
  }
}