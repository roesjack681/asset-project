import request from '@/config/axios'

// 方案展示留言记录 VO
export interface HaPsFeedbackVO {
  id: string // 主键
  name: string // 姓名
  phone: string // 手机号
  email: string // 邮箱
  deletedTime: Date // 删除时间
}

// 方案展示留言记录 API
export const HaPsFeedbackApi = {
  // 查询方案展示留言记录分页
  getHaPsFeedbackPage: async (params: any) => {
    return await request.get({ url: `/traffic/ha-ps-feedback/page`, params })
  },

  // 查询方案展示留言记录详情
  getHaPsFeedback: async (id: number) => {
    return await request.get({ url: `/traffic/ha-ps-feedback/get?id=` + id })
  },

  // 新增方案展示留言记录
  createHaPsFeedback: async (data: HaPsFeedbackVO) => {
    return await request.post({ url: `/traffic/ha-ps-feedback/create`, data })
  },

  // 修改方案展示留言记录
  updateHaPsFeedback: async (data: HaPsFeedbackVO) => {
    return await request.put({ url: `/traffic/ha-ps-feedback/update`, data })
  },

  // 删除方案展示留言记录
  deleteHaPsFeedback: async (id: number) => {
    return await request.delete({ url: `/traffic/ha-ps-feedback/delete?id=` + id })
  },

  // 导出方案展示留言记录 Excel
  exportHaPsFeedback: async (params) => {
    return await request.download({ url: `/traffic/ha-ps-feedback/export-excel`, params })
  }
}