import request from '@/config/axios'

// 护栏保险信息 VO
export interface OpsGuardrailInsuranceVO {
  id: string // 主键
  manceGuardrailId: string // 护栏维护主键
  vehicleNum: string // 车牌号
  insuranceCompany: string // 保险公司名称
  insurancePolicy: string // 保单编号
  payMoney: number // 赔付金额
  productId: string // 赔付护栏产品编号
  pieces: number // 赔付片数
  deletedTime: Date // 删除时间
}

// 护栏保险信息 API
export const OpsGuardrailInsuranceApi = {
  // 查询护栏保险信息分页
  getOpsGuardrailInsurancePage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-guardrail-insurance/page`, params })
  },

  // 查询护栏保险信息详情
  getOpsGuardrailInsurance: async (id: number) => {
    return await request.get({ url: `/traffic/ops-guardrail-insurance/get?id=` + id })
  },

  // 新增护栏保险信息
  createOpsGuardrailInsurance: async (data: OpsGuardrailInsuranceVO) => {
    return await request.post({ url: `/traffic/ops-guardrail-insurance/create`, data })
  },

  // 修改护栏保险信息
  updateOpsGuardrailInsurance: async (data: OpsGuardrailInsuranceVO) => {
    return await request.put({ url: `/traffic/ops-guardrail-insurance/update`, data })
  },

  // 删除护栏保险信息
  deleteOpsGuardrailInsurance: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-guardrail-insurance/delete?id=` + id })
  },

  // 导出护栏保险信息 Excel
  exportOpsGuardrailInsurance: async (params) => {
    return await request.download({ url: `/traffic/ops-guardrail-insurance/export-excel`, params })
  }
}