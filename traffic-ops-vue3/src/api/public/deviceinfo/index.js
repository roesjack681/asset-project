import request from '@/config/axios'
// const baseurl = 'http://police.xiaoluxueche.com:3002'
const baseurl = 'http://47.92.76.83:48080'
export const TsDeviceInfoApi = {
  // 通过摄像头id播放实时视频
  getStartStream: async (params) => {
    return await request.get({ url: baseurl + '/openApi/v1/getStartStream', params})
  },

  //通过摄像头id停止播放实时视频
  getStopStream: async (params) => {
    return await request.get({ url: baseurl + '/openApi/v1/getStopStream', params})
  },

  continuousMove: async (params, data) => {
    return await request.post({ url: baseurl + '/openApi/v1/continuousMove' + params, data })
  },
  stopMove: async (params, data) => {
    return await request.post({ url: baseurl + '/openApi/v1/stopMove' + params, data })
  }
}
