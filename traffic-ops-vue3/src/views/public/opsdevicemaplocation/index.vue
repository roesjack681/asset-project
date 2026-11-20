<template>
  <div class="bigdata">
    <div class="bigdata_title relative">
      <div class="timer absolute">{{datetime}} </div>
      <div> 车辆监控 </div>
    </div>
    <div class="bigdata-content">
      <div class="bigdata-content-left">
        <div class="input-area">
          <el-input class="input" v-model="queryParams.plateNum" placeholder="搜索设备名称"/>
          <el-button class="button" type="primary" round @click="getList">搜索</el-button>
        </div>
        <div class="list-area">
          <div class="list-item" v-for="i in list" :key="i.plateNum" @click="chooseOneCar(i)">
            <img class="listimg" src="../../../assets/imgs2/list.png" alt=""/>
            <div class="list-title">{{i.plateNum}}</div>
            <img v-if="chooseDevIds.indexOf(i.plateNum)===-1" class="listcheck" src="../../../assets/imgs2/check.png" alt=""/>
            <img v-else class="listcheck" src="../../../assets/imgs2/checked.png" alt=""/>
          </div>
        </div>
      </div>
      <div class="bigdata-content-right bigdata-content-right2" style="width: calc(100% - 300px);padding-left: 10px;">
        <div class="video-detail-big">
          <div class="video-item">
            <div style="width: 100%;height: 100%;position: relative">
              <div class="video-play-area novideo" style="width: 100%;height: 100%" id="map2"></div>
              <div class="chooseMapStyle" v-if="VITE_VUE_NETTYPE.value !== '内网'">
                <el-select v-model="currentStyle" placeholder="请选择">
                  <el-option
                    @click="changeMapStyle(item)"
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
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
import { OpsDeviceApi, OpsDeviceVO } from '@/api/public/opsdevice'
import moment from 'moment'
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  plateNum: undefined
})
const currentItem = ref({});
const datetime = ref('');
const timer = ref(null);
const map = ref(null);
const layer = ref(null);
const infoWindow = ref(null);
const markers = ref([]);
const list = ref([]);
const chooseDevIds = ref([]);
const mapViewIndex =  ref(0);
const VITE_VUE_NETTYPE = ref(import.meta.env.VITE_VUE_NETTYPE)
const currentStyle = ref('1')
const currentMapOptions = ref({
    resizeEnable: true,
    mapStyle: 'amap://styles/normal',
    center: [109.95315,40.621327],
    zoom: 14,
  })
const options = ref([
  {
    value: '1',
    label: '2D标准',
    mapOptions: {
      resizeEnable: true,
      mapStyle: 'amap://styles/normal',
      center: [109.95315,40.621327],
      zoom: 14,
    }
  },
  {
    value: '2',
    label: '3D标准',
    mapOptions: {
      resizeEnable: true,
      mapStyle: 'amap://styles/normal', //normal、blue
      center: [109.95315,40.621327],
      viewMode: '3D', // 设置地图为 3D 模式，这是显示 2.5D 效果的基础
      pitch: 65, // 地图倾斜角度，可根据需求调整
      rotation: 0,
      zoom: 14,
    }
  }
])
const markersObj = ref({})
const getTime = () => {
  if(timer.value) return
  timer.value = setInterval(() => {
    datetime.value = moment().format('YYYY-MM-DD HH:mm:ss')
  }, 1000)
}
const chooseOneCar = (item) => {
  if(chooseDevIds.value.indexOf(item.plateNum) === -1) {
    chooseDevIds.value = [item.plateNum]
    infoWindow.value = new AMap.InfoWindow({
      content: `<div style='width: 160px;font-size: 12px;padding: 5px;color: #000000;background: #ffffff'>
                <div>车牌号：${item.plateNum}</div>
                <div>IMEI：${item.imei}</div>
          </div>`,  // 信息窗口内容
      offset: new AMap.Pixel(-20, -20)
    });
    infoWindow.value.open(map.value, [item.longitude, item.latitude]);
    map.value.setZoomAndCenter(map.value.getZoom()>17?map.value.getZoom():17,[item.longitude, item.latitude])
  } else {
    chooseDevIds.value = []
  }
}
const moveOneCar = (carMarker) => {
  carMarker.marker.moveAlong(carMarker.position, {
    // 每一段的时长
    duration: 11000,//可根据实际采集时间间隔设置
    // JSAPI2.0 是否延道路自动设置角度在 moveAlong 里设置
    autoRotation: true,
  })
}
const getList = async () => {
  try {
    const data = await OpsDeviceApi.getOpsDeviceLocationPage(queryParams)
    list.value = data.list

    data.list.forEach(e => {
      if(!markersObj.value[e.plateNum]) {
        markersObj.value[e.plateNum] = {
          position: []
        }
        markersObj.value[e.plateNum].position.push([e.longitude, e.latitude])
        drawMarkerSingle(e)
      } else if(markersObj.value[e.plateNum].position.length === 1) {
        markersObj.value[e.plateNum].position.push([e.longitude, e.latitude])
        moveOneCar(markersObj.value[e.plateNum])
      } else {
        markersObj.value[e.plateNum].position.splice(0, 1)
        markersObj.value[e.plateNum].position.push([e.longitude, e.latitude])
        moveOneCar(markersObj.value[e.plateNum])
      }
    })
    if(markers.value.length < 1000 && mapViewIndex.value < 1) {
      mapViewIndex.value = 1
      map.value.setFitView(markers.value)
    }
  } finally {
    //
  }
}
const changeMapStyle = (item) => {
  currentMapOptions.value = item.mapOptions
  initMap(currentMapOptions.value)
  markersObj.value = {}
  markers.value = []
  mapViewIndex.value = 0
  if(timer.value) {
    clearInterval(timer.value)
  }
  setTimeout(() => {
    getList()
    timer.value = setInterval(() => {
      getList()
    }, 10000)
  }, 200)
}
const initMap = (option) => {
  if(VITE_VUE_NETTYPE.value === '内网') {
    map.value = new AMap.Map('map2', {
      resizeEnable: true,
      zoom: 14, // 初始化缩放级别
      viewMode: '3D',
      // mapStyle: 'amap://styles/normal', //normal、blue
      center: [109.95315,40.621327],
      // 指定离线地图路径
      layers: [
        new AMap.TileLayer({
          visible: true,
          zIndex: 99,
          opacity: 1,
          zooms: [6, 16],
          getTileUrl: (x, y, z) => {
            return `${import.meta.env.VITE_BASE_URL_MAP}offline-map/${z}/${x}/${y}/tile.png`;
            // return `http://47.93.181.25:8000/offline-map/${z}/${x}/${y}/tile.png`;
          }
        }),
      ],
    });
  }else {
    map.value = new AMap.Map('map2', option)
  }
  AMap.plugin('AMap.MoveAnimation', function () {})
}
const drawMarker = (markersData) => {
  if(markers.value.length) {
    clearMarkers()
  }
  markers.value = [];
  markersData.forEach((markersData) => {
    if(markersData.latitude && markersData.longitude) {
      let curData = {
        map: map.value,
        position: new AMap.LngLat(markersData.longitude, markersData.latitude),
        angle: markersData.direction,
        anchor:'bottom-center',
        autoRotation: true,
        icon: new AMap.Icon({
          image: `/car.png`,
          imageSize: new AMap.Size(20, 35),
          size: new AMap.Size(20, 35),
          imageOffset: new AMap.Pixel(0,0)
        })
      };
      let newMarker = new AMap.Marker(curData);
      markers.value.push(newMarker);
      markersObj.value[markersData.plateNum] = {
        marker: newMarker,
        position: [[markersData.longitude, markersData.latitude]],
        plateNum: markersData.plateNum
      }
      // 添加鼠标事件监听
      newMarker.on('click', function(e) {
        infoWindow.value = new AMap.InfoWindow({
          content: `<div style='width: 160px;font-size: 12px;padding: 5px;color: #000000;background: #ffffff'>
                <div>车牌号：${markersData.plateNum}</div>
                <div>IMEI：${markersData.imei}</div>
          </div>`,  // 信息窗口内容
          offset: new AMap.Pixel(-20, -20)
        });
        infoWindow.value.open(map.value, newMarker.getPosition()); // 在marker位置打开信息窗口
      });
    }
  })
  if(markers.value.length < 1000 && mapViewIndex.value < 1) {
    mapViewIndex.value = 1
    map.value.setFitView(markers.value)
  }
}
const drawMarkerSingle = (markersData) => {
    if(markersData.latitude && markersData.longitude) {
      let curData = {
        map: map.value,
        position: new AMap.LngLat(markersData.longitude, markersData.latitude),
        angle: markersData.direction,
        anchor:'bottom-center',
        autoRotation: true,
        icon: new AMap.Icon({
          image: `/car.png`,
          imageSize: new AMap.Size(20, 35),
          size: new AMap.Size(20, 35),
          imageOffset: new AMap.Pixel(0,0)
        })
      };
      let newMarker = new AMap.Marker(curData);
      markers.value.push(newMarker);
      markersObj.value[markersData.plateNum].marker = newMarker

      // 添加鼠标事件监听
      newMarker.on('click', function(e) {
        infoWindow.value = new AMap.InfoWindow({
          content: `<div style='width: 160px;font-size: 12px;padding: 5px;color: #000000;background: #ffffff'>
                <div>车牌号：${markersData.plateNum}</div>
                <div>IMEI：${markersData.imei}</div>
          </div>`,  // 信息窗口内容
          offset: new AMap.Pixel(-20, -20)
        });
        infoWindow.value.open(map.value, newMarker.getPosition()); // 在marker位置打开信息窗口
      });
    }
}
const clearMarkers = () => {
  markers.value.forEach(marker => {
    map.value.remove(marker);
  });
  markers.value = []; // 清空数组
}
onMounted(() => {
  getTime()
  initMap(currentMapOptions.value)
  getList()
  timer.value = setInterval(() => {
    getList()
  }, 10000)
});
</script>
<style scoped lang="scss">
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
          background: #ffffff;
          overflow: hidden;
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
              top: 10px;
              cursor: pointer;
              width: 16px;
              height: 16px;
            }
          }
          .video-play-area {
            color: #ffffff;
            width: 100%;
            height: calc(100%);
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
          background: #ffffff;
          overflow: hidden;
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
              top: 10px;
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
  top: 30px;
  right: 30px;
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
