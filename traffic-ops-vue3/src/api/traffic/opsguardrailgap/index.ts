import request from '@/config/axios'

// 护栏开口信息 VO
export interface OpsGuardrailGapVO {
  id: string // 主键
  guardrailId: string // 护栏主键
  maintenanceId: string // 维保主键
  manceGuardrailId: string // 护栏维护主键
  gapName: string // 开口名称
  gapType: string // 开口类型
  lng: string // GPS定位-经度
  lat: string // GPS定位-纬度
  deletedTime: Date // 删除时间
}

// 护栏开口信息 API
export const OpsGuardrailGapApi = {
  // 查询护栏开口信息分页
  getOpsGuardrailGapPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-guardrail-gap/page`, params })
  },

  // 查询护栏开口信息详情
  getOpsGuardrailGap: async (id: number) => {
    return await request.get({ url: `/traffic/ops-guardrail-gap/get?id=` + id })
  },

  // 新增护栏开口信息
  createOpsGuardrailGap: async (data: OpsGuardrailGapVO) => {
    return await request.post({ url: `/traffic/ops-guardrail-gap/create`, data })
  },

  // 修改护栏开口信息
  updateOpsGuardrailGap: async (data: OpsGuardrailGapVO) => {
    return await request.put({ url: `/traffic/ops-guardrail-gap/update`, data })
  },

  // 删除护栏开口信息
  deleteOpsGuardrailGap: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-guardrail-gap/delete?id=` + id })
  },

  // 导出护栏开口信息 Excel
  exportOpsGuardrailGap: async (params) => {
    return await request.download({ url: `/traffic/ops-guardrail-gap/export-excel`, params })
  }
}