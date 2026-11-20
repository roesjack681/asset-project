import request from '@/config/axios'

// 交叉口信息 VO
export interface OpsIntersectionVO {
  id: string // 主键
  glbm: string // 管理部门
  dldm: string // 道路代码
  xzqh: string // 行政区划
  lddm: string // 路段代码
  dlmc: string // 道路名称
  ldmc: string // 路段名称
  qslc: string // 起始里程
  jslc: string // 结束里程
  deletedTime: Date // 删除时间
}

// 交叉口信息 API
export const OpsIntersectionApi = {
  // 查询交叉口信息分页
  getOpsIntersectionPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-intersection/page`, params })
  },

  // 查询交叉口信息详情
  getOpsIntersection: async (id: number) => {
    return await request.get({ url: `/traffic/ops-intersection/get?id=` + id })
  },

  // 新增交叉口信息
  createOpsIntersection: async (data: OpsIntersectionVO) => {
    return await request.post({ url: `/traffic/ops-intersection/create`, data })
  },

  // 修改交叉口信息
  updateOpsIntersection: async (data: OpsIntersectionVO) => {
    return await request.put({ url: `/traffic/ops-intersection/update`, data })
  },

  // 删除交叉口信息
  deleteOpsIntersection: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-intersection/delete?id=` + id })
  },

  // 导出交叉口信息 Excel
  exportOpsIntersection: async (params) => {
    return await request.download({ url: `/traffic/ops-intersection/export-excel`, params })
  }
}