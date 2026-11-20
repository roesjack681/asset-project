import request from '@/config/axios'

// 日常巡检信息 VO
export interface DailyRoundCheckVO {
  id: string // 主键
  equId: string // 巡检设备编号
  affiliatedUnit: string // 所属单位：1-交警；2-公安
  equStatus: string // 设备状态
  checkItem: string // 巡检项
  question: string // 问题
  solution: string // 解决方案
  checkPerson: string // 巡检人
  checkType: string // 巡检方式：内勤；外勤
  checkPersonId: string // 巡检人编号
  checkDate: Date // 巡检时间
  deletedTime: Date // 删除时间
}

// 日常巡检信息 API
export const DailyRoundCheckApi = {
  // 查询日常巡检信息分页
  getDailyRoundCheckPage: async (params: any) => {
    return await request.get({ url: `/police/daily-round-check/page`, params })
  },

  // 查询日常巡检信息详情
  getDailyRoundCheck: async (id: number) => {
    return await request.get({ url: `/police/daily-round-check/get?id=` + id })
  },

  // 新增日常巡检信息
  createDailyRoundCheck: async (data: DailyRoundCheckVO) => {
    return await request.post({ url: `/police/daily-round-check/create`, data })
  },

  // 修改日常巡检信息
  updateDailyRoundCheck: async (data: DailyRoundCheckVO) => {
    return await request.put({ url: `/police/daily-round-check/update`, data })
  },

  // 删除日常巡检信息
  deleteDailyRoundCheck: async (id: number) => {
    return await request.delete({ url: `/police/daily-round-check/delete?id=` + id })
  },

  // 导出日常巡检信息 Excel
  exportDailyRoundCheck: async (params) => {
    return await request.download({ url: `/police/daily-round-check/export-excel`, params })
  }
}