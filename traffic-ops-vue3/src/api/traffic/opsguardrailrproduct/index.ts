import request from '@/config/axios'

// 护栏关联产品信息 VO
export interface OpsGuardrailRProductVO {
  id: string // 主键
  productId: string // 产品编号
  guardrailId: string // 护栏编号
  pieces: number // 片数
  deletedTime: Date // 删除时间
}

// 护栏关联产品信息 API
export const OpsGuardrailRProductApi = {
  // 查询护栏关联产品信息分页
  getOpsGuardrailRProductPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-guardrail-R-product/page`, params })
  },

  // 查询护栏关联产品信息详情
  getOpsGuardrailRProduct: async (id: number) => {
    return await request.get({ url: `/traffic/ops-guardrail-R-product/get?id=` + id })
  },

  // 新增护栏关联产品信息
  createOpsGuardrailRProduct: async (data: OpsGuardrailRProductVO) => {
    return await request.post({ url: `/traffic/ops-guardrail-R-product/create`, data })
  },

  // 修改护栏关联产品信息
  updateOpsGuardrailRProduct: async (data: OpsGuardrailRProductVO) => {
    return await request.put({ url: `/traffic/ops-guardrail-R-product/update`, data })
  },

  // 删除护栏关联产品信息
  deleteOpsGuardrailRProduct: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-guardrail-R-product/delete?id=` + id })
  },

  // 导出护栏关联产品信息 Excel
  exportOpsGuardrailRProduct: async (params) => {
    return await request.download({ url: `/traffic/ops-guardrail-R-product/export-excel`, params })
  }
}