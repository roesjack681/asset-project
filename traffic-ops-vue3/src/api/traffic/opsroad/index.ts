import request from '@/config/axios'

// 道路信息 VO
export interface OpsRoadVO {
  id: string // 主键
  glbm: string // 管理部门
  dldm: string // 道路代码
  dlmc: string // 道路名称
  dllx: string // 道路类型
  glxzdj: string // 公路行政等级
  qs: string // 起始
  js: string // 结束
  xqlc: string // 辖区里程
  qsmc: string // 起始名称
  jsmc: string // 结束名称
  deletedTime: Date // 删除时间
}

export interface OpsRoadMemberVO {
  id: undefined,
  equName: undefined,
  equNumber: undefined,
  equType: undefined,
  jibLength: undefined,
  roadName: undefined,
  memberType: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  adress: undefined,
  installDate: undefined,
  manufacturer: undefined,
  deletedTime: undefined,
  unit: undefined,
  hight: undefined,
  barLength: undefined,
  creator: undefined,
  serialNum: undefined,
  equImg: undefined,
  equImg2: undefined,
  equImg3: undefined,
  srcList: [],
  electricPoliceVOList: [
    {
      id: '',
      adress: '',
      ipAddr: '',
      djType: ''
    }
  ],
  checkRemark: undefined
}

export interface OpsRoadMemberSaveVO {
  id: undefined,
  electricPoliceVOList: [
    {
      id: ''
    }
  ],
  checkRemark: undefined
}

// 道路信息 API
export const OpsRoadApi = {
  // 查询道路信息分页
  getOpsRoadPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-road/page`, params })
  },
  // 查询道路信息分页
  getOpsRoadDict: async (params: any) => {
    return await request.get({ url: `/traffic/ops-road/roadDict`, params })
  },

  // 查询道路信息详情
  getOpsRoad: async (id: number) => {
    return await request.get({ url: `/traffic/ops-road/get?id=` + id })
  },

  // 新增道路信息
  createOpsRoad: async (data: OpsRoadVO) => {
    return await request.post({ url: `/traffic/ops-road/create`, data })
  },

  // 修改道路信息
  updateOpsRoad: async (data: OpsRoadVO) => {
    return await request.put({ url: `/traffic/ops-road/update`, data })
  },

  // 删除道路信息
  deleteOpsRoad: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-road/delete?id=` + id })
  },

  // 导出道路信息 Excel
  exportOpsRoad: async (params) => {
    return await request.download({ url: `/traffic/ops-road/export-excel`, params })
  },

  // 查询道路杆件信息
  getRoadMember: async (params: any) => {
    return await request.get({ url: `/traffic/ops-road/getRoadMember`, params })
  },
  // 保存核验结果
  checkResult: async (data: OpsRoadMemberSaveVO) => {
    return await request.post({ url: `/traffic/ops-road/checkResult`, data })
  }
}