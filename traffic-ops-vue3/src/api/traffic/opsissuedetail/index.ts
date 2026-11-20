import request from '@/config/axios'

// 领用详情 VO
export interface OpsIssueDetailVO {
  id: string // 主键
  issueBh: string // 领用编号
  equId: string // 装备编号
  useDeptBh: string // 使用部门编号
  useDept: string // 使用部门
  userName: string // 使用人
  userBh: string // 使用人编号
  deletedTime: Date // 删除时间
}

// 领用详情 API
export const OpsIssueDetailApi = {
  // 查询领用详情分页
  getOpsIssueDetailPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-issue-detail/page`, params })
  },

  // 查询领用详情详情
  getOpsIssueDetail: async (id: number) => {
    return await request.get({ url: `/traffic/ops-issue-detail/get?id=` + id })
  },

  // 新增领用详情
  createOpsIssueDetail: async (data: OpsIssueDetailVO) => {
    return await request.post({ url: `/traffic/ops-issue-detail/create`, data })
  },

  // 修改领用详情
  updateOpsIssueDetail: async (data: OpsIssueDetailVO) => {
    return await request.put({ url: `/traffic/ops-issue-detail/update`, data })
  },

  // 删除领用详情
  deleteOpsIssueDetail: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-issue-detail/delete?id=` + id })
  },

  // 导出领用详情 Excel
  exportOpsIssueDetail: async (params) => {
    return await request.download({ url: `/traffic/ops-issue-detail/export-excel`, params })
  }
}