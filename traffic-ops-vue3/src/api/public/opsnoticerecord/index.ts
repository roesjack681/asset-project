import request from '@/config/axios'

// 消息通知记录 VO
export interface OpsNoticeRecordVO {
  id: string // 主键
  businessType: string // 业务类型
  businessId: string // 业务主键
  noticeType: string // 消息类型:1-SMS;2-IM;3-mail
  receiveUserType: string // 接收者类型
  receiveUserId: string // 接收者ID
  receiveUserName: string // 接收者姓名
  receiveUserPhone: string // 接收者电话
  noticeMsg: string // 通知消息
  sendTime: Date // 发送时间
  deletedTime: Date // 删除时间
}

export interface OpsNoticeMsgVO {
  id: string // 主键
  businessId: string // 业务ID
  userId: number // 用户ID
  receivePhone: string // 接收者电话
  msgType: string // 消息类型
  noticeMsg: string // 消息内容
}

// 消息通知记录 API
export const OpsNoticeRecordApi = {
  // 查询消息通知记录分页
  getOpsNoticeRecordPage: async (params: any) => {
    return await request.get({ url: `/public/ops-notice-record/page`, params })
  },

  // 查询消息通知记录详情
  getOpsNoticeRecord: async (id: number) => {
    return await request.get({ url: `/public/ops-notice-record/get?id=` + id })
  },

  // 新增消息通知记录
  createOpsNoticeRecord: async (data: OpsNoticeRecordVO) => {
    return await request.post({ url: `/public/ops-notice-record/create`, data })
  },

  // 修改消息通知记录
  updateOpsNoticeRecord: async (data: OpsNoticeRecordVO) => {
    return await request.put({ url: `/public/ops-notice-record/update`, data })
  },

  // 删除消息通知记录
  deleteOpsNoticeRecord: async (id: number) => {
    return await request.delete({ url: `/public/ops-notice-record/delete?id=` + id })
  },

  // 导出消息通知记录 Excel
  exportOpsNoticeRecord: async (params) => {
    return await request.download({ url: `/public/ops-notice-record/export-excel`, params })
  },
  // 取消报修单，
  sendNoticeMsg: async (data) => {
    return await request.post({ url: `/public/ops-notice-record/sendNoticeMsg`, data })
  }
}