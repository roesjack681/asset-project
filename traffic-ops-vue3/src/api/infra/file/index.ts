import request from '@/config/axios'

export interface FilePageReqVO extends PageParam {
  path?: string
  type?: string
  createTime?: Date[]
}

// 文件预签名地址 Response VO
export interface FilePresignedUrlRespVO {
  // 文件配置编号
  configId: number
  // 文件上传 URL
  uploadUrl: string
  // 文件 URL
  url: string
}

// 查询文件列表
export const getFilePage = (params: FilePageReqVO) => {
  return request.get({ url: '/infra/file/page', params })
}

// 删除文件
export const deleteFile = (id: number) => {
  return request.delete({ url: '/infra/file/delete?id=' + id })
}

// 获取文件预签名地址
export const getFilePresignedUrl = (path: string) => {
  return request.get<FilePresignedUrlRespVO>({
    url: '/infra/file/presigned-url',
    params: { path }
  })
}
export const getFilePresignedVideoUrl = (path: string) => {
  return request.get<FilePresignedUrlRespVO>({
    url: '/public/file/upload?isCompress=1',
    params: { path }
  })
}

// 创建文件
export const createFile = (data: any) => {
  return request.post({ url: '/infra/file/create', data })
}

// 上传文件
export const updateFile = (data: any) => {
  return request.upload({ url: '/infra/file/upload', data })
}
export const updateVideoFile = (data: any) => {
  return request.upload({ url: '/public/file/upload?isCompress=1', data })
}
// 上传文件
export const updateFileImport = (data: any) => {
  return request.upload({ url: '/traffic/ops-maintenance/importMaintenanceInfo', data })
}

// 上传文件
export const updateFileImportMonitor = (data: any) => {
  return request.upload({ url: '/traffic/dj-monitor-statistics/importDjMonitorInfo', data })
}

export const updateFileImportMonitorCommon = (url:string, data: any, timeout: number) => {
  if( timeout) {
    return request.upload({ url, data, timeout })
  } else {
    return request.upload({ url, data })
  }
}

// 上传文件
export const updateFileImportPoliceMonitor = (data: any) => {
  return request.upload({ url: '/police/monitor-results/importPoliceMonitorInfo', data })
}

// 上传文件
export const updateFileImportTempReport = (data: any) => {
  return request.upload({ url: '/traffic/view-ops-temp-report/importTempReportInfo', data })
}
