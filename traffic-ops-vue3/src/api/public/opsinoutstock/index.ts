import request from '@/config/axios'

// 设备出入库管理 VO
export interface OpsInoutStockVO {
  id: string // 主键
  stockType: string // 出入库类型
  stockPerson: string // 操作人
  remark: string // 备注
  deletedTime: Date // 删除时间
}

// 设备出入库管理 API
export const OpsInoutStockApi = {
  // 查询设备出入库管理分页
  getOpsInoutStockPage: async (params: any) => {
    return await request.get({ url: `/public/ops-inout-stock/page`, params })
  },

  // 查询设备出入库管理详情
  getOpsInoutStock: async (id: number) => {
    return await request.get({ url: `/public/ops-inout-stock/get?id=` + id })
  },

  // 新增设备出入库管理
  createOpsInoutStock: async (data: OpsInoutStockVO) => {
    return await request.post({ url: `/public/ops-inout-stock/create`, data })
  },

  // 修改设备出入库管理
  updateOpsInoutStock: async (data: OpsInoutStockVO) => {
    return await request.put({ url: `/public/ops-inout-stock/update`, data })
  },

  // 删除设备出入库管理
  deleteOpsInoutStock: async (id: number) => {
    return await request.delete({ url: `/public/ops-inout-stock/delete?id=` + id })
  },

  // 导出设备出入库管理 Excel
  exportOpsInoutStock: async (params) => {
    return await request.download({ url: `/public/ops-inout-stock/export-excel`, params })
  }
}