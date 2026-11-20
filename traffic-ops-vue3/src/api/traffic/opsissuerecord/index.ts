import request from '@/config/axios'

// 领用记录 VO
export interface OpsIssueRecordVO {
  id: string // 主键
  useDeptBh: string // 领用部门编号
  useDept: string // 领用部门
  userName: string // 领用人
  userBh: string // 领用人编号
  issueTime: Date // 领用时间
  issueNum: number // 领用数量
  deletedTime: Date // 删除时间
}

// 领用记录 API
export const OpsIssueRecordApi = {
  // 查询领用记录分页
  getOpsIssueRecordPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-issue-record/page`, params })
  },

  // 查询领用记录详情
  getOpsIssueRecord: async (id: number) => {
    return await request.get({ url: `/traffic/ops-issue-record/get?id=` + id })
  },

  // 新增领用记录
  createOpsIssueRecord: async (data: OpsIssueRecordVO) => {
    return await request.post({ url: `/traffic/ops-issue-record/create`, data })
  },

  // 修改领用记录
  updateOpsIssueRecord: async (data: OpsIssueRecordVO) => {
    return await request.put({ url: `/traffic/ops-issue-record/update`, data })
  },

  // 删除领用记录
  deleteOpsIssueRecord: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-issue-record/delete?id=` + id })
  },

  // 导出领用记录 Excel
  exportOpsIssueRecord: async (params) => {
    return await request.download({ url: `/traffic/ops-issue-record/export-excel`, params })
  },

  // 部门下的人的接口
  queryAllUserInfoList: async (deptId: number) => {
    return await request.get({ url: `/traffic/ops-maintenance/queryAllUserInfoList?deptId=` + deptId })
  },

  //设备字典接口
  unallocatedPage: async (params: number) => {
    return await request.get({ url: `/traffic/ops-police-equipment/unallocatedPage`, params })
  },

  queryPoliceEquipment: async (issueBh, queryType) => { //1-未分配 2-已分配 不传-全部
    if(!issueBh) {
      return await request.get({ url: `/traffic/ops-police-equipment/queryPoliceEquipment?queryType=${queryType}` })
    }
    if(!queryType) {
      return await request.get({ url: `/traffic/ops-police-equipment/queryPoliceEquipment?issueBh=${issueBh}` })
    }
    if(!issueBh && !queryType) {
      return await request.get({ url: `/traffic/ops-police-equipment/queryPoliceEquipment` })
    }
    return await request.get({ url: `/traffic/ops-police-equipment/queryPoliceEquipment?issueBh=${issueBh}&queryType=${queryType}` })

  },
}
