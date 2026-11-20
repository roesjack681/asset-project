import request from '@/config/axios'

// 集指无效违法记录 VO
export interface ViewVioSurveilScreenErrInfoVO {
  id: string // 主键
  sbbh: string // 设备编号
  hphm: string // 号牌号码
  hpzl: string // 号牌种类
  wfsj: Date // 违法时间
  wfxw: string // 违法行为
  cwyy: string // 错误原因
  url1: string // 图片1
  url2: string // 图片2
  url3: string // 图片3
  lrsj: Date // 录入时间
}

// 集指无效违法记录 API
export const ViewVioSurveilScreenErrInfoApi = {
  // 查询集指无效违法记录分页
  getViewVioSurveilScreenErrInfoPage: async (params: any) => {
    return await request.get({ url: `/traffic/view-vio-surveil-screen-err-info/page`, params })
  },

  // 查询集指无效违法记录详情
  getViewVioSurveilScreenErrInfo: async (id: number) => {
    return await request.get({ url: `/traffic/view-vio-surveil-screen-err-info/get?id=` + id })
  },

  // 新增集指无效违法记录
  createViewVioSurveilScreenErrInfo: async (data: ViewVioSurveilScreenErrInfoVO) => {
    return await request.post({ url: `/traffic/view-vio-surveil-screen-err-info/create`, data })
  },

  // 修改集指无效违法记录
  updateViewVioSurveilScreenErrInfo: async (data: ViewVioSurveilScreenErrInfoVO) => {
    return await request.put({ url: `/traffic/view-vio-surveil-screen-err-info/update`, data })
  },

  // 删除集指无效违法记录
  deleteViewVioSurveilScreenErrInfo: async (id: number) => {
    return await request.delete({ url: `/traffic/view-vio-surveil-screen-err-info/delete?id=` + id })
  },

  // 导出集指无效违法记录 Excel
  exportViewVioSurveilScreenErrInfo: async (params) => {
    return await request.download({ url: `/traffic/view-vio-surveil-screen-err-info/export-excel`, params })
  },

  //审核列表接口
  getViewVioSurveilScreenErrReviewPage: async (params: any) => {
    return await request.get({ url: `/traffic/view-vio-surveil-screen-err-info/reviewPage`, params })
  },
  // 复审保存接口
  saveReviewResult: async (data: ViewVioSurveilScreenErrInfoVO) => {
    return await request.put({ url: `/traffic/view-vio-surveil-screen-err-info/saveReviewResult`, data })
  },
}
