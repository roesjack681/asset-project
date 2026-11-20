import request from '@/config/axios'

// 资产摄像设备信息 VO
export interface OpsCameraInfoVO {
  id: string // 主键
  cameraBh: string // 摄像头编号
  cameraUser: string // 摄像头用户
  cameraPassword: string // 摄像头密码
  cameraGroup: string // 摄像头组
  installLocation: string // 安装位置
  installDate: Date // 安装时间
  deletedTime: Date // 删除时间
}

// 资产摄像设备信息 API
export const OpsCameraInfoApi = {
  // 查询资产摄像设备信息分页
  getOpsCameraInfoPage: async (params: any) => {
    return await request.get({ url: `/public/ops-camera-info/page`, params })
  },

  // 查询资产摄像设备信息详情
  getOpsCameraInfo: async (id: number) => {
    return await request.get({ url: `/public/ops-camera-info/get?id=` + id })
  },

  // 新增资产摄像设备信息
  createOpsCameraInfo: async (data: OpsCameraInfoVO) => {
    return await request.post({ url: `/public/ops-camera-info/create`, data })
  },

  // 修改资产摄像设备信息
  updateOpsCameraInfo: async (data: OpsCameraInfoVO) => {
    return await request.put({ url: `/public/ops-camera-info/update`, data })
  },

  // 删除资产摄像设备信息
  deleteOpsCameraInfo: async (id: number) => {
    return await request.delete({ url: `/public/ops-camera-info/delete?id=` + id })
  },

  // 导出资产摄像设备信息 Excel
  exportOpsCameraInfo: async (params) => {
    return await request.download({ url: `/public/ops-camera-info/export-excel`, params })
  }
}