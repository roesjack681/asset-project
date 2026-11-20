import request from '@/config/axios'

// 设备领用人信息 VO
export interface OpsIssuePersonVO {
  id: string // 主键
  userName: string // 用户名姓名
  deptId: string // 部门编号
  deletedTime: Date // 删除时间
}

// 设备领用人信息 API
export const OpsIssuePersonApi = {
  // 查询设备领用人信息分页
  getOpsIssuePersonPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-issue-person/page`, params })
  },

  // 查询设备领用人信息详情
  getOpsIssuePerson: async (id: number) => {
    return await request.get({ url: `/traffic/ops-issue-person/get?id=` + id })
  },

  // 新增设备领用人信息
  createOpsIssuePerson: async (data: OpsIssuePersonVO) => {
    return await request.post({ url: `/traffic/ops-issue-person/create`, data })
  },

  // 修改设备领用人信息
  updateOpsIssuePerson: async (data: OpsIssuePersonVO) => {
    return await request.put({ url: `/traffic/ops-issue-person/update`, data })
  },

  // 删除设备领用人信息
  deleteOpsIssuePerson: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-issue-person/delete?id=` + id })
  },

  // 导出设备领用人信息 Excel
  exportOpsIssuePerson: async (params) => {
    return await request.download({ url: `/traffic/ops-issue-person/export-excel`, params })
  },

  // 选择领用人
  getOpsIssuePersonNoPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-issue-person/personPage`, params })
  },
}
