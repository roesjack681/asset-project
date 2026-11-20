import request from '@/config/axios'

// 护栏维护详细 VO
export interface OpsMaintenanceGuardrailVO {
  id: string // 主键
  maintenanceId: string // 维保主键
  guardrailId: string // 护栏主键
  maintenanceType: string // 护栏维护类型：清洗；更换；拆除
  changeType: string // 更换类型：损毁；换新
  startName: string // 起始位置名称
  startLng: string // 起始位置经度
  startLat: string // 起始位置纬度
  endName: string // 截止位置名称
  endLng: string // 截止位置经度
  endLat: string // 截止位置纬度
  isGap: string // 是否增加开口
  moditfyBeProduct: string // 变更前产品编号
  moditfyAfProduct: string // 变更后产品编号
  moditfyBeImg: string // 变更前图片
  moditfyAfImg: string // 变更后图片
  pieces: number // 变更片数
  describe: string // 变更内容描述
  remark: string // 备注
  claimType: string // 赔付类型：保险；运维
  isHeadModitfy: string // 是否首位置变更
  isTailModitfy: string // 是否尾位置变更
  deletedTime: Date // 删除时间
}

// 护栏维护详细 API
export const OpsMaintenanceGuardrailApi = {
  // 查询护栏维护详细分页
  getOpsMaintenanceGuardrailPage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-maintenance-guardrail/page`, params })
  },

  // 查询护栏维护详细详情
  getOpsMaintenanceGuardrail: async (id: number) => {
    return await request.get({ url: `/traffic/ops-maintenance-guardrail/get?id=` + id })
  },

  // 新增护栏维护详细
  createOpsMaintenanceGuardrail: async (data: OpsMaintenanceGuardrailVO) => {
    return await request.post({ url: `/traffic/ops-maintenance-guardrail/create`, data })
  },

  // 修改护栏维护详细
  updateOpsMaintenanceGuardrail: async (data: OpsMaintenanceGuardrailVO) => {
    return await request.put({ url: `/traffic/ops-maintenance-guardrail/update`, data })
  },

  // 删除护栏维护详细
  deleteOpsMaintenanceGuardrail: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-maintenance-guardrail/delete?id=` + id })
  },

  // 导出护栏维护详细 Excel
  exportOpsMaintenanceGuardrail: async (params) => {
    return await request.download({ url: `/traffic/ops-maintenance-guardrail/export-excel`, params })
  }
}