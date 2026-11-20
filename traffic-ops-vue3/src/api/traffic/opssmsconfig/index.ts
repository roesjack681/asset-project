import request from '@/config/axios'

// 资产短信发送配置 VO
export interface OpsSmsConfigVO {
  id: string // 主键
  sendType: string // 发送类型：1重点考核视频；2重点考核卡口
  receiveName: string // 接收人姓名
  receivePhone: string // 接收人手机号
  userBh: string // 用户编号
  receiveStatus: string // 接收状态:on;off
  deletedTime: Date // 删除时间
}

// 资产短信发送配置 API
export const OpsSmsConfigApi = {
  // 查询资产短信发送配置分页
  getOpsSmsConfigPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-sms-config/page`, params })
  },

  // 查询资产短信发送配置详情
  getOpsSmsConfig: async (id: number) => {
    return await request.get({ url: `/traffic/ops-sms-config/get?id=` + id })
  },

  // 新增资产短信发送配置
  createOpsSmsConfig: async (data: OpsSmsConfigVO) => {
    return await request.post({ url: `/traffic/ops-sms-config/create`, data })
  },

  // 修改资产短信发送配置
  updateOpsSmsConfig: async (data: OpsSmsConfigVO) => {
    return await request.put({ url: `/traffic/ops-sms-config/update`, data })
  },

  // 删除资产短信发送配置
  deleteOpsSmsConfig: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-sms-config/delete?id=` + id })
  },

  // 导出资产短信发送配置 Excel
  exportOpsSmsConfig: async (params) => {
    return await request.download({ url: `/traffic/ops-sms-config/export-excel`, params })
  }
}