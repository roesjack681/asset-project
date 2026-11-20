import request from '@/config/axios'

// 警用设备采集站信息 VO
export interface CollectionStationInfoVO {
  id: number // 主键ID
  deviceCode: string // 设备编号/资产编号
  machineName: string // 机器名
  deptId: string // 部门
  vendor: string // 设备厂商
  cpuModel: string // CPU型号
  memoryInfo: string // 内存信息
  diskInfo: string // 硬盘信息
  osVersion: string // 操作系统
  softwareVersion: string // 软件版本
  macAddr: string // MAC地址
  ipAddr: string // IP地址
  location: string // 设备位置
  status: number // 设备状态 1正常 2维修 3停用 4报废
  remark: string // 备注
}

// 警用设备采集站信息 API
export const CollectionStationInfoApi = {
  // 查询警用设备采集站信息分页
  getCollectionStationInfoPage: async (params: any) => {
    return await request.get({ url: `/police/collection-station-info/page`, params })
  },

  // 查询警用设备采集站信息详情
  getCollectionStationInfo: async (id: number) => {
    return await request.get({ url: `/police/collection-station-info/get?id=` + id })
  },

  // 新增警用设备采集站信息
  createCollectionStationInfo: async (data: CollectionStationInfoVO) => {
    return await request.post({ url: `/police/collection-station-info/create`, data })
  },

  // 修改警用设备采集站信息
  updateCollectionStationInfo: async (data: CollectionStationInfoVO) => {
    return await request.put({ url: `/police/collection-station-info/update`, data })
  },

  // 删除警用设备采集站信息
  deleteCollectionStationInfo: async (id: number) => {
    return await request.delete({ url: `/police/collection-station-info/delete?id=` + id })
  },

  // 导出警用设备采集站信息 Excel
  exportCollectionStationInfo: async (params) => {
    return await request.download({ url: `/police/collection-station-info/export-excel`, params })
  }
}