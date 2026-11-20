import request from '@/config/axios'

// 护栏 VO
export interface OpsGuardrailVO {
  id: string // 主键
  equName: string // 设备名称
  equType: string // 设备类型
  roadName: string // 所在道路名称
  intersectionName: string // 灯控路口名称
  lng: string // GPS定位-经度
  lat: string // GPS定位-纬度
  adress: string // 安装位置
  installDate: Date // 安装时间
  hight: string // 高度
  length: string // 长度
  material: string // 材质
  manufacturer: string // 厂家
  unit: string // 归属单位
  dominion: string // 所属管辖单位
  status: string // 状态
  equImg: string // 设备图片
  model: string // 型号
  pieces: string // 片数
  totalLength: string // 总长（米数）
  pedestal: string // 底座
  humanDecency: string // 人行车档
  stud: string // 立柱车档
  sign: string // 防撞桶指示牌
  gzbsz: string // 钢字爆闪柱
  tynbsd: string // 太阳能爆闪灯
  rxdfzz: string // 人行道防撞柱
  remark: string // 备注
  deletedTime: Date // 删除时间
}

// 护栏 API
export const OpsGuardrailApi = {
  // 查询护栏分页
  getOpsGuardrailPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-guardrail/page`, params })
  },

  // 查询护栏详情
  getOpsGuardrail: async (id: number) => {
    return await request.get({ url: `/traffic/ops-guardrail/get?id=` + id })
  },

  // 新增护栏
  createOpsGuardrail: async (data: OpsGuardrailVO) => {
    return await request.post({ url: `/traffic/ops-guardrail/create`, data })
  },

  // 修改护栏
  updateOpsGuardrail: async (data: OpsGuardrailVO) => {
    return await request.put({ url: `/traffic/ops-guardrail/update`, data })
  },

  // 删除护栏
  deleteOpsGuardrail: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-guardrail/delete?id=` + id })
  },

  // 导出护栏 Excel
  exportOpsGuardrail: async (params) => {
    return await request.download({ url: `/traffic/ops-guardrail/export-excel`, params })
  },

  //护栏产品
  getOpsGuardrailProductPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-guardrail-product/page`, params })
  },
}
