import request from '@/config/axios'

// 警用装备历史领用记录 VO
export interface OpsIssueHistoryVO {
  id: string // 主键
  useDeptBh: string // 领用部门编号
  useDept: string // 领用部门
  issueUserName: string // 领用人
  issueUserBh: string // 领用人编号
  issueTime: Date // 领用时间
  equId: string // 装备编号
  userName: string // 使用人
  userBh: string // 使用人编号
  recycleUserBh: string // 回收人
  recycleUserName: string // 回收人编号
  recycleTime: Date // 回收时间
  deletedTime: Date // 删除时间
}

// 警用装备历史领用记录 API
export const OpsIssueHistoryApi = {
  // 查询警用装备历史领用记录分页
  getOpsIssueHistoryPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-issue-history/page`, params })
  },

  // 查询警用装备历史领用记录详情
  getOpsIssueHistory: async (id: number) => {
    return await request.get({ url: `/traffic/ops-issue-history/get?id=` + id })
  },

  // 新增警用装备历史领用记录
  createOpsIssueHistory: async (data: OpsIssueHistoryVO) => {
    return await request.post({ url: `/traffic/ops-issue-history/create`, data })
  },

  // 修改警用装备历史领用记录
  updateOpsIssueHistory: async (data: OpsIssueHistoryVO) => {
    return await request.put({ url: `/traffic/ops-issue-history/update`, data })
  },

  // 删除警用装备历史领用记录
  deleteOpsIssueHistory: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-issue-history/delete?id=` + id })
  },

  // 导出警用装备历史领用记录 Excel
  exportOpsIssueHistory: async (params) => {
    return await request.download({ url: `/traffic/ops-issue-history/export-excel`, params })
  }
}