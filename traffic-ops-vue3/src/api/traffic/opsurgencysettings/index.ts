import request from '@/config/axios'

// 维保紧急程度配置 VO
export interface OpsUrgencySettingsVO {
  id: string // 主键
  urgencyLevel: string // 紧急程度
  deadline: string // 处理时限小时
  notifyPersonnel: string // 通知人员
  deletedTime: Date // 删除时间
}

// 维保紧急程度配置 API
export const OpsUrgencySettingsApi = {
  // 查询维保紧急程度配置分页
  getOpsUrgencySettingsPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-urgency-settings/page`, params })
  },

  // 查询维保紧急程度配置详情
  getOpsUrgencySettings: async (id: number) => {
    return await request.get({ url: `/traffic/ops-urgency-settings/get?id=` + id })
  },

  // 新增维保紧急程度配置
  createOpsUrgencySettings: async (data: OpsUrgencySettingsVO) => {
    return await request.post({ url: `/traffic/ops-urgency-settings/create`, data })
  },

  // 修改维保紧急程度配置
  updateOpsUrgencySettings: async (data: OpsUrgencySettingsVO) => {
    return await request.put({ url: `/traffic/ops-urgency-settings/update`, data })
  },

  // 删除维保紧急程度配置
  deleteOpsUrgencySettings: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-urgency-settings/delete?id=` + id })
  },

  // 导出维保紧急程度配置 Excel
  exportOpsUrgencySettings: async (params) => {
    return await request.download({ url: `/traffic/ops-urgency-settings/export-excel`, params })
  },
  //获取微信用户
  getAllOpsRegister: async (userId) => {
    return await request.get({ url: `/traffic/ops-urgency-settings/getAllOpsRegister?dispatchUserId=` + userId })
  }
}
