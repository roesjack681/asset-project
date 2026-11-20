import request from '@/config/axios'

// 区域用户组信息 VO
export interface UserAreaGroupVO {
  id: string // 主键
  affiliatedUnit: string // 所属单位：1-交警；2-公安
  groupName: string // 组名
  groupContent: string // 主要运维内容
  groupArea: string // 可维护区域
  groupUserId: string // 组员id
  leadUserId: number // 队长id
  deletedTime: Date // 删除时间
}

// 区域用户组信息 API
export const UserAreaGroupApi = {
  // 查询区域用户组信息分页
  getUserAreaGroupPage: async (params: any) => {
    return await request.get({ url: `/public/user-area-group/page`, params })
  },

  // 查询区域用户组信息详情
  getUserAreaGroup: async (id: number) => {
    return await request.get({ url: `/public/user-area-group/get?id=` + id })
  },

  // 新增区域用户组信息
  createUserAreaGroup: async (data: UserAreaGroupVO) => {
    return await request.post({ url: `/public/user-area-group/create`, data })
  },

  // 修改区域用户组信息
  updateUserAreaGroup: async (data: UserAreaGroupVO) => {
    return await request.put({ url: `/public/user-area-group/update`, data })
  },

  // 删除区域用户组信息
  deleteUserAreaGroup: async (id: number) => {
    return await request.delete({ url: `/public/user-area-group/delete?id=` + id })
  },

  // 导出区域用户组信息 Excel
  exportUserAreaGroup: async (params) => {
    return await request.download({ url: `/public/user-area-group/export-excel`, params })
  }
}