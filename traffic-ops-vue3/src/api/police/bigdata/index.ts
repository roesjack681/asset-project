import request from '@/config/axios'


// 基础设施
export const baseEqu = (params) => {
  return request.get({ url: '/police/ops-bigdata/get', params })
}

// 获取位置信息
export const queryPoliceOpsPositionList = (params) => {
  return request.get({ url: '/police/ops-bigdata/queryPoliceOpsPositionList', params })
}

//自动匹配
export const autocompletePoliceOpsMemberList = (params) => {
  return request.get({ url: '/police/ops-bigdata/autocompletePoliceOpsMemberList', params })
}

