import request from '@/config/axios'

// 设备出入库存明细 VO
export interface OpsInoutDetailVO {
  id: string // 主键
  inoutId: string // 设备出入库id
  cmSort: string // 耗材种类
  cmNum: number // 耗材数量
  flag: string // 新旧标识
  deletedTime: Date // 删除时间
}

// 设备出入库存明细 API
export const OpsInoutDetailApi = {
  // 查询设备出入库存明细分页
  getOpsInoutDetailPage: async (params: any) => {
    return await request.get({ url: `/public/ops-inout-detail/page`, params })
  },

  // 查询设备出入库存明细详情
  getOpsInoutDetail: async (id: number) => {
    return await request.get({ url: `/public/ops-inout-detail/get?id=` + id })
  },

  // 新增设备出入库存明细
  createOpsInoutDetail: async (data: OpsInoutDetailVO) => {
    return await request.post({ url: `/public/ops-inout-detail/create`, data })
  },

  // 修改设备出入库存明细
  updateOpsInoutDetail: async (data: OpsInoutDetailVO) => {
    return await request.put({ url: `/public/ops-inout-detail/update`, data })
  },

  // 删除设备出入库存明细
  deleteOpsInoutDetail: async (id: number) => {
    return await request.delete({ url: `/public/ops-inout-detail/delete?id=` + id })
  },

  // 导出设备出入库存明细 Excel
  exportOpsInoutDetail: async (params) => {
    return await request.download({ url: `/public/ops-inout-detail/export-excel`, params })
  }
}