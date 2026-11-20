<template>
  <div class="bigdata">
    <div class="bigdata_title relative">
      <div class="timer absolute">{{datetime}} </div>
      <div> 办公场地监控 </div>
    </div>
    <div class="bigdata-content">
      <div class="bigdata-content-left">
        <div class="input-area">
          <el-input class="input" v-model="queryParams.cameraName" placeholder="搜索设备名称"/>
          <el-button class="button" type="primary" round @click="getList">搜索</el-button>
        </div>
        <div class="list-area">
          <div class="list-item" v-for="i in list" :key="i.id" @click="chooseDev(i)">
            <img class="listimg" src="../../../assets/imgs2/list.png" alt=""/>
            <div class="list-title">{{i.cameraName}}</div>
            <img v-if="chooseDevIds.indexOf(i.id)===-1" class="listcheck" src="../../../assets/imgs2/check.png" alt=""/>
            <img v-else class="listcheck" src="../../../assets/imgs2/checked.png" alt=""/>
          </div>
        </div>
      </div>
      <div class="bigdata-content-right bigdata-content-right2" style="width: calc(100% - 300px);padding-left: 10px;">
        <div class="video-detail-big">
          <div class="video-item">
            <div style="width: 100%;height: 100%;position: relative" v-if="currentItem.url">
              <div class="options" v-show="currentItem.url">
                <img class="top" @click="moveVideo('top')" src="../../../assets/imgs2/top.png" alt=""/>
                <img class="left" @click="moveVideo('left')" src="../../../assets/imgs2/left.png" alt=""/>
                <img class="right" @click="moveVideo('right')" src="../../../assets/imgs2/right.png" alt=""/>
                <img class="bottom" @click="moveVideo('bottom')" src="../../../assets/imgs2/bittom.png" alt=""/>
                <img class="middle" @click="moveVideo('middle')" src="../../../assets/imgs2/yuan.png" alt=""/>
              </div>
              <div class="video-item-title">
                {{currentItem.cameraName}}
                <div class="big" @click="closeVideo">X</div>
              </div>
              <div class="video-play-area novideo" id="video_big_item" v-if="currentItem.url">
                正在加载
              </div>
              <div class="video-play-area novideo" v-if="!currentItem.url">
                设备离线
              </div>
            </div>
            <div style="width: 100%;height: 100%;position: relative" v-else>
              <div class="video-play-area novideo">
                未选择视频
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import {onMounted, ref} from "vue";
import moment from 'moment'
import Player from 'xgplayer'
import FlvPlugin from 'xgplayer-flv'
import "xgplayer/dist/index.min.css"
import {OpsCameraInfoApi, OpsCameraInfoVO} from "@/api/public/opscamerainfo";
import {TsDeviceInfoApi} from '@/api/public/deviceinfo';
import CryptoJS from 'crypto-js'
const currentItem = ref({});
const datetime = ref('');
const timer = ref(null);
const loading = ref(true) // 列表的加载中
const list = ref<OpsCameraInfoVO[]>([]) // 列表的数据
const chooseDevIds = ref([])
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  cameraBh: undefined,
  cameraName: undefined,
  cameraUser: undefined,
  cameraPassword: undefined,
  cameraGroup: undefined,
  installLocation: undefined,
  installDate: [],
  createTime: [],
  deletedTime: []
})
const getTime = () => {
  if(timer.value) return
  timer.value = setInterval(() => {
    datetime.value = moment().format('YYYY-MM-DD HH:mm:ss')
  }, 1000)
}

const setVideo = (url, domID) => {
  var dom = document.getElementById(domID)
  let myplay = new Player({
    id: domID,
    isLive: true,
    plugins: [FlvPlugin],
    playsinline: true,
    autoplay: true,
    height: dom.innerHeight,
    width: dom.innerWidth,
    flv: {
      retryCount: 30, // 重试 3 次，默认值
      retryDelay: 1000, // 每次重试间隔 1 秒，默认值
      loadTimeout: 10000, // 请求超时时间为 10 秒，默认值
      fetchOptions: {
        // 该参数会透传给 fetch，默认值为 undefined
        mode: 'cors'
      }
    },
    allList: [],
    url: url,
  })
}
const getList = async () => {
  try {
    const data = await OpsCameraInfoApi.getOpsCameraInfoPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    //
  }
}
const chooseDev = async (item) => {
  var index = chooseDevIds.value.indexOf(item.id)
  if(index > -1) {
    chooseDevIds.value.splice(index, 1)
    if(!item.id) return
    const ts = +new Date()
    const data = {
      ts,
      sign: CryptoJS.MD5(`${ts}CCE8D58AF49D48E8BE1A7F848E7A51D5`).toString().toUpperCase(),
      user: 'TRAFFIC-SAFETY',
      cameraId: item.id
    }
    await TsDeviceInfoApi.getStopStream(data)
    currentItem.value = {}
  } else {
    if(chooseDevIds.value.length) {
      const ts = +new Date()
      const data = {
        ts,
        sign: CryptoJS.MD5(`${ts}CCE8D58AF49D48E8BE1A7F848E7A51D5`).toString().toUpperCase(),
        user: 'TRAFFIC-SAFETY',
        cameraId: currentItem.value.id
      }
      await TsDeviceInfoApi.getStopStream(data)
      currentItem.value = {}
    }
    chooseDevIds.value = [item.id]
    var res = {}
    if(item.id) {
      const ts = +new Date()
      const data2 = {
        ts,
        sign: CryptoJS.MD5(`${ts}CCE8D58AF49D48E8BE1A7F848E7A51D5`).toString().toUpperCase(),
        user: 'TRAFFIC-SAFETY',
        cameraId: item.id
      }
      res = await TsDeviceInfoApi.getStartStream(data2)
      if(res && res.streamUrls && res.streamUrls.length) {
        res.streamUrls.forEach(e => {
          e.id = item.id
          e.deviceSn = item.deviceSn
          e.cameraId = item.cameraId
          e.cameraName = item.cameraName
        })
        currentItem.value = res.streamUrls[0]
        console.log(currentItem.value)
        setTimeout(() => {
          if(item.id && currentItem.value.url) {
            setVideo(currentItem.value.url, 'video_big_item')
          }
        }, 2000)
      } else {
        currentItem.value = {}
      }
    }
  }
}
const closeVideo = async () => {
  const ts = +new Date()
  const data = {
    ts,
    sign: CryptoJS.MD5(`${ts}CCE8D58AF49D48E8BE1A7F848E7A51D5`).toString().toUpperCase(),
    user: 'TRAFFIC-SAFETY',
    cameraId: currentItem.value.id
  }
  await TsDeviceInfoApi.getStopStream(data)
  currentItem.value = {}
  chooseDevIds.value = []
}
const moveVideo = async (type) => {
  const ts = +new Date()
  const sign = CryptoJS.MD5(`${ts}CCE8D58AF49D48E8BE1A7F848E7A51D5`).toString().toUpperCase()
  const params = `?ts=${ts}&sign=${sign}&user=TRAFFIC-SAFETY`
  var o = {
    cameraId: currentItem.value.id,
    pan: '',
    tilt: '',
    zoom: ''
  }
  var o2 = {
    cameraId: currentItem.value.id,
    pan: true,
    tilt: true,
    zoom: true
  }
  if(type === 'top') {
    o.tilt = 0.4
  }
  if(type === 'bottom') {
    o.tilt = -0.4
  }
  if(type === 'left') {
    o.pan = 0.4
  }
  if(type === 'right') {
    o.tilt = -0.4
  }
  await TsDeviceInfoApi.continuousMove(params, o)
  await TsDeviceInfoApi.stopMove(params, o2)
}
onMounted(() => {
  getTime()
  getList()
});
</script>
<style scoped lang="scss">
::v-deep .pb-0 {
  padding: 0!important;
}
.bigdata {
  position: relative;
  width: 100%;
  height: calc(100vh - 120px);
  overflow: hidden;
  background: #02004d;
  box-sizing: border-box;

  .bigdata_title {
    position: relative;
    top: 0;
    left: 50%;
    width: 100%;
    padding: 10px 0;
    font-size: 19px;
    color: #fff;
    text-align: center;
    background: url('../../../assets/imgs2/bigdata/title2.png');
    background-size: 100% 100%;
    transform: translateX(-50%);
    .timer {
      position: absolute;
      // top: 50%;
      // transform: translateY(-50%);
      right: 10%;
      font-size: 12px;
      color: #32C5FF;
      bottom: 0px;
    }
  }

  .bigdata-content {
    display: flex;
    width: 100%;
    min-height: 540px;
    height: calc(100% - 46px);
    box-sizing: border-box;
    padding: 20px;

    .bigdata-content-left {
      position: relative;
      width: 300px;
      background: url("../../../assets/imgs2/jklb.png");
      background-size: 100% 100%;
      .input-area {
        position: relative;
        border-radius: 30px;
        overflow: hidden;
        width: 95%;
        margin: 40px auto 20px auto;
        border: 1px solid rgba(72, 238, 250, 0.64)!important;
        .input {
          ::v-deep .el-input--medium .el-input__inner {
            height: 33px;
            line-height: 33px;
            background: transparent;
            border: none;
            font-size: 13px;
          }
          input {
            background: transparent!important;
            border: none!important;
          }
        }
        .button {
          position: absolute;
          right: 1px;
          top: 1px;
          width: 70px;
          background: rgba(72, 238, 250, 0.5996);
          border: none;
          color: #ffffff;
          height: 33px;
        }
      }
      .list-area {
        width: 96%;
        height: calc(100% - 100px);
        overflow: auto;
        margin: 0 auto;
        .list-item {
          cursor: pointer;
          align-items: center;
          display: flex;
          color: #ffffff;
          padding-bottom: 10px;
          padding-top: 10px;
          border-bottom: 1px solid rgba(255, 255, 255, 0.4017);
          .listimg {
            width: 16px;
            height: auto;
            margin-right: 4px;
          }
          .list-title {
            font-size: 16px;
            flex: 1;
          }
          .listcheck {
            width: 22px;
            height: 22px;
            cursor: pointer;
          }
        }
      }
    }

    .bigdata-content-right {
      flex: 1;
      .video-area {
        width: 100%;
        height: 100%;
        display: flex;
        flex: 1;
        flex-wrap: wrap;
        overflow: auto;
        .video-item {
          width: 100%;
          height: 100%;
          margin: 0px 0px 10px 10px;
          border-radius: 5px;
          background: #333333;
          .video-item-title {
            position: relative;
            background: #000000;
            color: #ffffff;
            height: 33px;
            line-height: 33px;
            padding: 0 6px;
            .big {
              position: absolute;
              right: 6px;
              top: 0px;
              cursor: pointer;
              width: 16px;
              height: 16px;
            }
          }
          .video-play-area {
            color: #ffffff;
            width: 100%;
            height: calc(100% - 33px);
            &.novideo {
              text-align: center;
              display: flex;
              align-items: center;
              justify-content: center;
              font-size: 16px;
            }
          }
          .nochoosevideo{
            color: #ffffff;
            width: 100%;
            height: calc(100%);
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
          }
        }
      }
    }
    .bigdata-content-right2 {
      display: flex;
      flex-direction: column;
      .video-detail-big {
        position: relative;
        flex: 1;
        .video-item {
          width: 100%;
          height: 100%;
          margin: 0px 0px 0px 0px;
          border-radius: 5px;
          background: #333333;
          .video-item-title {
            position: relative;
            background: #000000;
            color: #ffffff;
            height: 33px;
            line-height: 33px;
            padding: 0 6px;
            .big {
              position: absolute;
              right: 6px;
              top: 0px;
              cursor: pointer;
              width: 16px;
              height: 16px;
            }
          }
          .video-play-area {
            color: #ffffff;
            width: 100%;
            height: calc(100% - 33px);
            &.novideo {
              text-align: center;
              display: flex;
              align-items: center;
              justify-content: center;
              font-size: 16px;
            }
          }
          .nochoosevideo{
            color: #ffffff;
            width: 100%;
            height: calc(100%);
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
          }
        }
        .options {
          z-index: 10001;
          position: absolute;
          width: 110px;
          height: 110px;
          border-radius: 50%;
          background: rgba(255,255,255,0.4);
          border: 1px solid #ffffff;
          bottom: 20px;
          left: 20px;
          cursor: pointer;
          .left {
            position: absolute;
            width:16px;
            height: 16px;
            left: 10px;
            top: 50%;
            transform: translate(0, -50%);
          }
          .right {
            position: absolute;
            width:16px;
            height: 16px;
            right: 10px;
            top: 50%;
            transform: translate(0, -50%);
          }
          .top {
            position: absolute;
            width:16px;
            height: 16px;
            left: 50%;
            top: 10px;
            transform: translate(-50%, 0);
          }
          .bottom {
            position: absolute;
            width:16px;
            height: 16px;
            left: 50%;
            bottom: 10px;
            transform: translate(-50%, 0);
          }
          .middle {
            position: absolute;
            width:32px;
            height: 32px;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
          }
        }
      }
    }
  }
}

.map-tips {
  position: absolute;
  right: 20px;
  top: 20px;

  .map-tips-item {
    display: flex;
    padding: 4px 10px;
    margin-top: 6px;
    font-size: 12px;
    background: rgb(255 255 255 / 90%);
    border-radius: 6px;

    img {
      width: 15px;
      height: auto;
      margin-right: 10px;
    }
  }
}

.mapQueryBox {
  position: absolute;
  top: 20px;
  left: 20px;
  color: #fff;
}

.checkboxDOM {
  position: absolute;
  right: 20px;
  bottom: 20px;
}

.chooseMapStyle {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: 10%;
  width: 100px;
}

::v-deep .amap-icon img {
  width: auto;
  height: 36px;
}

.input-card {
  right: 10px;
  bottom: 10px;
  display: flex;
  min-width: 0;
  word-wrap: break-word;
  border-width: 0;
  border-radius: 0.4rem;
  background-clip: border-box;
  flex-direction: column;
  flex: 1 1 auto;
}

.my-infowindow {
  overflow: hidden;
  background: url('../../../assets/imgs2/kk.png');
  background-size: 100% 100%;

  .kksc {
    position: absolute;
    top: 10px;
    right: 18px;
    width: 20px;
    height: 20px;
    cursor: pointer;
  }
}

::v-deep .amap-info-content {
  padding: 0;
  background: transparent;
}

::v-deep .amap-info-sharp {
  display: none;
}

.swiper-container {
  width: 100%;
  height: auto;
  padding: 10px 0 0 0;
  margin-left: auto;
  margin-right: auto;

}
.swiper-slide {
  position: relative;
  text-align: center;
  font-size: 13px;
  background: rgba(51, 51, 51, 1);
  border-radius: 5px;
  height: 100px;
  color: #ffffff;
  cursor: pointer;
  background-size: 100% 100%;
  /* Center slide text vertically */
  display: -webkit-box;
  display: -ms-flexbox;
  display: -webkit-flex;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  -webkit-align-items: center;
  align-items: center;
  transition-property:all;
  &:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: red;
    z-index: 1001;
    opacity: 0;
  }
}
#progressEffect{
  position: absolute;
  z-index: 10;
  top: 20px;
  left: 20px;
}
</style>
<style>
.input {
  .el-input--medium .el-input__inner {
    height: 33px!important;
    line-height: 33px!important;
    background: transparent;
    border: none;
    font-size: 13px;
  }
  input {
    background: transparent!important;
    border: none!important;
  }
}
</style>
