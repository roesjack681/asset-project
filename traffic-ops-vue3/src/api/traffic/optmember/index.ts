import request from '@/config/axios'

// 杆件 VO
export interface OpsMemberVO {
  id: string // 主键
  equName: string // 设备名称
  equNumber: string // 设备编号
  equType: string // 设备类型
  jibLength: string // 副臂长度
  roadName: string // 所在道路名称
  memberType: string // 杆件类型
  intersectionName: string // 灯控路口名称
  lng: string // GPS定位-经度
  lat: string // GPS定位-纬度
  adress: string // 安装位置
  installDate: Date // 安装时间
  manufacturer: string // 厂家
  deletedTime: Date // 删除时间
  unit: string // 归属单位
  hight: string // 高度
  barLength: string // 横杆长度
}

// 杆件 API
export const OpsMemberApi = {
  // 查询杆件分页
  getOpsMemberPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-member/page`, params })
  },

  // 查询杆件详情
  getOpsMember: async (id: number) => {
    return await request.get({ url: `/traffic/ops-member/get?id=` + id })
  },

  // 新增杆件
  createOpsMember: async (data: OpsMemberVO) => {
    return await request.post({ url: `/traffic/ops-member/create`, data })
  },

  // 修改杆件
  updateOpsMember: async (data: OpsMemberVO) => {
    return await request.put({ url: `/traffic/ops-member/update`, data })
  },

  // 删除杆件
  deleteOpsMember: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-member/delete?id=` + id })
  },

  // 导出杆件 Excel
  exportOpsMember: async (params) => {
    return await request.download({ url: `/traffic/ops-member/export-excel`, params })
  }
}