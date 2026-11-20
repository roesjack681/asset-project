import request from '@/config/axios'

// 设备库存管理 VO
export interface OpsStockVO {
  id: string // 主键
  cmSort: string // 耗材种类
  surplusNum: number // 耗材当前数量
  totalNum: string // 总入库量
  deletedTime: Date // 删除时间
}

// 设备库存管理 API
export const OpsStockApi = {
  // 查询设备库存管理分页
  getOpsStockPage: async (params: any) => {
    return await request.get({ url: `/public/ops-stock/page`, params })
  },

  // 查询设备库存管理详情
  getOpsStock: async (id: number) => {
    return await request.get({ url: `/public/ops-stock/get?id=` + id })
  },

  // 新增设备库存管理
  createOpsStock: async (data: OpsStockVO) => {
    return await request.post({ url: `/public/ops-stock/create`, data })
  },

  // 修改设备库存管理
  updateOpsStock: async (data: OpsStockVO) => {
    return await request.put({ url: `/public/ops-stock/update`, data })
  },

  // 删除设备库存管理
  deleteOpsStock: async (id: number) => {
    return await request.delete({ url: `/public/ops-stock/delete?id=` + id })
  },

  // 导出设备库存管理 Excel
  exportOpsStock: async (params) => {
    return await request.download({ url: `/public/ops-stock/export-excel`, params })
  }
}