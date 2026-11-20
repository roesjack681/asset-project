import request from '@/config/axios'

// 维保单导入记录 VO
export interface ImportRecordVO {
  id: string // 主键
  importType: string // 导入类型
  fileName: string // 文件名
  importFile: string // 导入文件
  importNum: number // 导入记录数
  sucNum: number // 成功记录数
  failNum: number // 失败记录数
  syncSucNum: number // 同步成功数量
  syncFailNum: number // 同步失败数量
  failFile: string // 失败文件
  deptId: string // 操作用户部门
  deletedTime: Date // 删除时间
}

// 维保单导入记录 API
export const ImportRecordApi = {
  // 查询维保单导入记录分页
  getImportRecordPage: async (params: any) => {
    return await request.get({ url: `/traffic/import-record/page`, params })
  },

  // 查询维保单导入记录详情
  getImportRecord: async (id: number) => {
    return await request.get({ url: `/traffic/import-record/get?id=` + id })
  },

  // 新增维保单导入记录
  createImportRecord: async (data: ImportRecordVO) => {
    return await request.post({ url: `/traffic/import-record/create`, data })
  },

  // 修改维保单导入记录
  updateImportRecord: async (data: ImportRecordVO) => {
    return await request.put({ url: `/traffic/import-record/update`, data })
  },

  // 删除维保单导入记录
  deleteImportRecord: async (id: number) => {
    return await request.delete({ url: `/traffic/import-record/delete?id=` + id })
  },

  // 导出维保单导入记录 Excel
  exportImportRecord: async (params) => {
    return await request.download({ url: `/traffic/import-record/export-excel`, params })
  }
}