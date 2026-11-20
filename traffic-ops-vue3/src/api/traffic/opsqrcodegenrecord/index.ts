import request from '@/config/axios'

// 资产二维码生成记录 VO
export interface OpsQrcodeGenRecordVO {
  id: string // 主键
  genDate: Date // 生成日期
  roadId: string // 道路ID
  roadName: string // 道路名称
  opsType: string // 资产类型
  directionCnt: string // 方向和数量(json存储)
  deletedTime: Date // 删除时间
}

export interface PoliceOpsQrcodeGenRecordVO {
  id: string // 主键
  affiliatedUnit: string,
  genDate: Date // 生成日期
  roadId: string // 道路ID
  roadName: string // 道路名称
  opsType: string // 资产类型
  genCnt: string // 方向和数量(json存储)
  deletedTime: Date // 删除时间
}

// 资产二维码生成记录 API
export const OpsQrcodeGenRecordApi = {
  // 查询资产二维码生成记录分页
  getOpsQrcodeGenRecordPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-qrcode-gen-record/page`, params })
  },

  // 查询资产二维码生成记录详情
  getOpsQrcodeGenRecord: async (id: number) => {
    return await request.get({ url: `/traffic/ops-qrcode-gen-record/get?id=` + id })
  },

  // 新增资产二维码生成记录
  createOpsQrcodeGenRecord: async (data: OpsQrcodeGenRecordVO) => {
    return await request.post({ url: `/traffic/ops-qrcode-gen-record/create`, data })
  },

  // 修改资产二维码生成记录
  updateOpsQrcodeGenRecord: async (data: OpsQrcodeGenRecordVO) => {
    return await request.put({ url: `/traffic/ops-qrcode-gen-record/update`, data })
  },

  // 删除资产二维码生成记录
  deleteOpsQrcodeGenRecord: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-qrcode-gen-record/delete?id=` + id })
  },

  // 导出资产二维码生成记录 Excel
  exportOpsQrcodeGenRecord: async (params) => {
    return await request.download({ url: `/traffic/ops-qrcode-gen-record/export-excel`, params })
  },

   // 新增资产二维码生成记录
  createPoliceOpsQrcodeGenRecord: async (data: PoliceOpsQrcodeGenRecordVO) => {
    return await request.post({ url: `/traffic/ops-qrcode-gen-record/createPoliceEquipQrCode`, data })
  },
}