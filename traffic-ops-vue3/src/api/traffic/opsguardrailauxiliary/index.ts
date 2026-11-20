import request from '@/config/axios'

// 护栏辅助设备 VO
export interface OpsGuardrailAuxiliaryVO {
  id: string // 主键
  guardrailId: string // 护栏主键
  maintenanceId: string // 维保主键
  manceGuardrailId: string // 护栏维护主键
  auxiliaryName: string // 辅助设备名称
  auxiliaryNum: number // 数量
  auxiliaryImg: string // 图片
  status: string // 状态：安装；拆除
  installTime: Date // 安装时间
  demolishTime: Date // 拆除时间
  deletedTime: Date // 删除时间
}

// 护栏辅助设备 API
export const OpsGuardrailAuxiliaryApi = {
  // 查询护栏辅助设备分页
  getOpsGuardrailAuxiliaryPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-guardrail-auxiliary/page`, params })
  },

  // 查询护栏辅助设备详情
  getOpsGuardrailAuxiliary: async (id: number) => {
    return await request.get({ url: `/traffic/ops-guardrail-auxiliary/get?id=` + id })
  },

  // 新增护栏辅助设备
  createOpsGuardrailAuxiliary: async (data: OpsGuardrailAuxiliaryVO) => {
    return await request.post({ url: `/traffic/ops-guardrail-auxiliary/create`, data })
  },

  // 修改护栏辅助设备
  updateOpsGuardrailAuxiliary: async (data: OpsGuardrailAuxiliaryVO) => {
    return await request.put({ url: `/traffic/ops-guardrail-auxiliary/update`, data })
  },

  // 删除护栏辅助设备
  deleteOpsGuardrailAuxiliary: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-guardrail-auxiliary/delete?id=` + id })
  },

  // 导出护栏辅助设备 Excel
  exportOpsGuardrailAuxiliary: async (params) => {
    return await request.download({ url: `/traffic/ops-guardrail-auxiliary/export-excel`, params })
  }
}