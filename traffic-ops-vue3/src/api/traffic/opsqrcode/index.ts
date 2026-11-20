import request from '@/config/axios'

// 资产二维码信息 VO
export interface OpsQrcodeVO {
  id: string // 主键
  roadId: string // 道路ID
  raodName: string // 道路名称
  direction: string // 方向
  genRecordId: string // 生成记录ID
  opsType: string // 资产类型
  serialNum: number // 序列号
  deletedTime: Date // 删除时间
}

export interface OpsQrcodePrintVO {
  raodName: string // 道路名称
  direction: string // 方向
  serialNum: number // 序列号
  qrcodeFile: string // 二维码文件
}

// 资产二维码信息 API
export const OpsQrcodeApi = {
  // 查询资产二维码信息分页
  getOpsQrcodePage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-qrcode/page`, params })
  },

  // 查询资产二维码信息详情
  getOpsQrcode: async (id: number) => {
    return await request.get({ url: `/traffic/ops-qrcode/get?id=` + id })
  },

  // 新增资产二维码信息
  createOpsQrcode: async (data: OpsQrcodeVO) => {
    return await request.post({ url: `/traffic/ops-qrcode/create`, data })
  },

  // 修改资产二维码信息
  updateOpsQrcode: async (data: OpsQrcodeVO) => {
    return await request.put({ url: `/traffic/ops-qrcode/update`, data })
  },

  // 删除资产二维码信息
  deleteOpsQrcode: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-qrcode/delete?id=` + id })
  },

  // 导出资产二维码信息 Excel
  exportOpsQrcode: async (params) => {
    return await request.download({ url: `/traffic/ops-qrcode/export-excel`, params })
  },
  // 查询打印数据
  queryPrintData: async (params) => {
    return await request.get({ url: `/traffic/ops-qrcode/queryPrintData`, params})
  }
}