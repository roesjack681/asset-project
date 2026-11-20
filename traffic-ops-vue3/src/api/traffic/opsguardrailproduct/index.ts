import request from '@/config/axios'

// 护栏产品信息 VO
export interface OpsGuardrailProductVO {
  id: string // 主键
  hight: string // 高度
  length: number // 长度
  material: string // 材质
  manufacturer: string // 厂家
  model: string // 型号
  remark: string // 备注
  deletedTime: Date // 删除时间
  pedestal: string // 底座
}

// 护栏产品信息 API
export const OpsGuardrailProductApi = {
  // 查询护栏产品信息分页
  getOpsGuardrailProductPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-guardrail-product/page`, params })
  },

  // 查询护栏产品信息详情
  getOpsGuardrailProduct: async (id: number) => {
    return await request.get({ url: `/traffic/ops-guardrail-product/get?id=` + id })
  },

  // 新增护栏产品信息
  createOpsGuardrailProduct: async (data: OpsGuardrailProductVO) => {
    return await request.post({ url: `/traffic/ops-guardrail-product/create`, data })
  },

  // 修改护栏产品信息
  updateOpsGuardrailProduct: async (data: OpsGuardrailProductVO) => {
    return await request.put({ url: `/traffic/ops-guardrail-product/update`, data })
  },

  // 删除护栏产品信息
  deleteOpsGuardrailProduct: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-guardrail-product/delete?id=` + id })
  },

  // 导出护栏产品信息 Excel
  exportOpsGuardrailProduct: async (params) => {
    return await request.download({ url: `/traffic/ops-guardrail-product/export-excel`, params })
  }
}