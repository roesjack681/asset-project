import request from '@/config/axios'

// 设备库存明细 VO
export interface OpsStockDetailVO {
  id: string // 主键
  stockId: string // 库存编号
  cmSort: string // 耗材种类
  cmNum: number // 耗材数量
  flag: string // 新旧标识
  remark: string // 备注
  deletedTime: Date // 删除时间
}

// 设备库存明细 API
export const OpsStockDetailApi = {
  // 查询设备库存明细分页
  getOpsStockDetailPage: async (params: any) => {
    return await request.get({ url: `/public/ops-stock-detail/page`, params })
  },

  // 查询设备库存明细详情
  getOpsStockDetail: async (id: number) => {
    return await request.get({ url: `/public/ops-stock-detail/get?id=` + id })
  },

  // 新增设备库存明细
  createOpsStockDetail: async (data: OpsStockDetailVO) => {
    return await request.post({ url: `/public/ops-stock-detail/create`, data })
  },

  // 修改设备库存明细
  updateOpsStockDetail: async (data: OpsStockDetailVO) => {
    return await request.put({ url: `/public/ops-stock-detail/update`, data })
  },

  // 删除设备库存明细
  deleteOpsStockDetail: async (id: number) => {
    return await request.delete({ url: `/public/ops-stock-detail/delete?id=` + id })
  },

  // 导出设备库存明细 Excel
  exportOpsStockDetail: async (params) => {
    return await request.download({ url: `/public/ops-stock-detail/export-excel`, params })
  }
}