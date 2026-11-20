import request from '@/config/axios'

// 设备维保信息 VO
export interface OpsMaintenanceVO {
  id: string // 主键
  affiliatedUnit: string // 所属单位
  equType: string // 设备类型
  equId: string // 设备编号
  problem: string // 存在问题
  faultType: string // 故障分类
  faultDate: Date // 故障时间
  maintenance: string // 维护情况
  maintenanceDate: Date // 维护时间
  maintenancePersonnel: string // 维护人员
  suggestion: string // 维护建议
  remark: string // 备注
  deletedTime: Date // 删除时间
}

export interface OpsMaintenanceBatchDispatchVO {
  maintenanceUserId: string,
  maintenancePersonnel: string,
  maintenanceIdList: []
}

export interface OpsMaintenanceConfirmVO {
  id: string,
  status: string,
  remark: string
}

// 设备维保信息 API
export const OpsMaintenanceApi = {
  // 查询设备维保信息分页
  getOpsMaintenancePage: async (params: any) => {
    return await request.get({ url: `/traffic/ops-maintenance/page`, params })
  },

  // 查询设备维保信息详情
  getOpsMaintenance: async (id: number) => {
    return await request.get({ url: `/traffic/ops-maintenance/get?id=` + id })
  },

  // 新增设备维保信息
  createOpsMaintenance: async (data: OpsMaintenanceVO) => {
    return await request.post({ url: `/traffic/ops-maintenance/create`, data })
  },
  autocompleteOpsIntersection: async (intersectionName: any) => {
    return await request.get({ url: 'http://47.93.181.25:8000/trafficOps/admin-api/traffic/ops-bigdata/autocompleteOpsIntersection?intersectionName=' + intersectionName })
  },
  queryTrafficOpsPositionList2: async (intersectionName,id) => {
    return await request.get({ url: 'http://ops.nmholdtime.com/trafficOpsWx/app-api/traffic/ops-maintenance/initOpsAssetPage?pageNo=1&pageSize=10&intersectionName=' + intersectionName + '&id=' + id})
  },
  queryTrafficOpsPositionList: async (intersectionName,id) => {
    return await request.get({ url: '/traffic/ops-signal/page?pageNo=1&pageSize=10&intersectionName=' + intersectionName + '&id=' + id})
  },

  autocompletePoliceOpsElectricPoliceList: async (equName: any) => {
    return await request.get({ url: '/police/ops-electric-police/autocompletePoliceOpsElectricPoliceList?equName=' + equName })
  },



  //创建设备维保信息及维修全过程1
  createAllOpsMaintenance: async (data: OpsMaintenanceVO) => {
    return await request.post({ url: `/traffic/ops-maintenance/createAll`, data })
  },


  //获取用户精简信息列表
  getUserList: async (deptId) => {
    if(deptId) return await request.get({ url: `/system/user/page?pageNo=1&pageSize=100&deptId=` + deptId})
    else return await request.get({ url: `/system/user/page?pageNo=1&pageSize=100`})
  },

  queryAllUserInfoList: async (deptId) => {
    if (deptId) {
      return await request.get({ url: `/traffic/ops-maintenance/queryAllUserInfoList?deptId=` + deptId})
    } else {
      return await request.get({ url: `/traffic/ops-maintenance/queryAllUserInfoList?deptId=`})
    }
  },

  // 修改设备维保信息
  updateOpsMaintenance: async (data: OpsMaintenanceVO) => {
    return await request.put({ url: `/traffic/ops-maintenance/update`, data })
  },

  // 删除设备维保信息
  deleteOpsMaintenance: async (id: number) => {
    return await request.delete({ url: `/traffic/ops-maintenance/delete?id=` + id })
  },

  //派单
  dispatchRepairOrderPc: async (data: any) => {
    return await request.post({ url: `/traffic/ops-maintenance/dispatchRepairOrderPc`, data})
  },
    //批量派单
  batchDispatchRepairOrder: async (data: OpsMaintenanceBatchDispatchVO) => {
    return await request.post({ url: `/traffic/ops-maintenance/batchDispatchRepairOrder`, data})
  },
  //接单
  acceptRepairOrderPc: async (data: any) => {
    return await request.post({ url: `/traffic/ops-maintenance/acceptRepairOrderPc`, data})
  },

  //查询所有的维修人员
  getAllOpsRegisterPc: async () => {
    return await request.get({ url: `traffic/ops-maintenance/getAllOpsRegisterPc`})
  },

  // 导出设备维保信息 Excel
  exportOpsMaintenance: async (params) => {
    return await request.download({ url: `/traffic/ops-maintenance/export-excel`, params })
  },

  maintenanceProgress: async (maintenanceId: number) => {
    return await request.get({ url: `/traffic/ops-maintenance/maintenanceProgress/` + maintenanceId })
  },
  // 取消报修单，
  cancelRepairOrderPc: async (data) => {
    return await request.post({ url: `/traffic/ops-maintenance/cancelRepairOrderPc`, data })
  },
    // 维保信息确认
  confirmRepairOrder: async (data) => {
    return await request.post({ url: `/traffic/ops-maintenance/confirmRepairOrder`, data })
  },
  // 维保信息确认
  mountOrder: async (data) => {
    return await request.post({ url: `/traffic/ops-maintenance/mountOrder`, data })
  },
  //电警时查询是否有未完成的工单
  queryOrderByIp: async (equId, ipAddr,type=1) => {
    return await request.get({ url: `/traffic/ops-maintenance/queryOrderByIp?equId=${equId}&ipAddr=${ipAddr}&type=${type}` })
  },
  //跟进记录
  queryOpsMaintenanceUnfixableInfo: async (maintenanceId) => {
    return await request.post({ url: `/traffic/ops-maintenance/queryOpsMaintenanceUnfixableInfo?id=${maintenanceId}` })
  },
}
