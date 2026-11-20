import request from '@/config/axios'

// 终端设备信息 VO
export interface OpsChedulingVO {
  id: string // 主键
  model: string // model
  imei: string // IMEI
  plateNum: string // 车牌号
  cameraInfo: string // 摄像头信息
  status: string // 状态
  remark: string // 备注
  deletedTime: Date // 删除时间
}

// 终端设备信息 API
export const OpsChedulingApi = {
  //所有点位信息
  queryMaintenancePoint: async (params: any) => {
    return await request.get({ url: `/traffic/ops-scheduler/queryMaintenancePoint`, params })
  },
  //左侧电警类型
  queryMaintenancePointCount: async () => {
    return await request.get({ url: `/traffic/ops-scheduler/queryMaintenancePointCount`})
  },

  // 右侧各状态工单数
  queryMaintenancePointStatusCount: async (djType) => {
    return await request.get({ url: `/traffic/ops-scheduler/queryMaintenancePointStatusCount?djType=` + djType })
  },
  //批量派单接口
  batchDispatchRepairOrder: async (data) => {
    return await request.post({ url: `/traffic/ops-maintenance/batchDispatchRepairOrder`, data})
  },
}
