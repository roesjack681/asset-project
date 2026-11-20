import request from '@/config/axios'

// 标线 VO
export interface OpsMarklineVO {
  id: string // 主键
  roadName: string // 道路名称
  roadParts: string // 路段
  marklineSort: string // 施画种类
  marklineNum: string // 数量
  lineWidth: string // 线宽
  specifications: string // 规格
  proportion: string // 比例
  area: string // 面积
  paintingUnit: string // 施画单位
  drawingProcess: string // 施划工艺
  applicationTime: Date // 施划时间
  deletedTime: Date // 删除时间
}

// 标线 API
export const OpsMarklineApi = {
  // 查询标线分页
  getOpsMarklinePage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-markline/page`, params })
  },

  // 查询标线详情
  getOpsMarkline: async (id: number) => {
    return await request.get({ url: `/traffic/ops-markline/get?id=` + id })
  },

  // 新增标线
  createOpsMarkline: async (data: OpsMarklineVO) => {
    return await request.post({ url: `/traffic/ops-markline/create`, data })
  },

  // 修改标线
  updateOpsMarkline: async (data: OpsMarklineVO) => {
    return await request.put({ url: `/traffic/ops-markline/update`, data })
  },

  // 删除标线
  deleteOpsMarkline: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-markline/delete?id=` + id })
  },

  // 导出标线 Excel
  exportOpsMarkline: async (params) => {
    return await request.download({ url: `/traffic/ops-markline/export-excel`, params })
  }
}