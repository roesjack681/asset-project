<template>
  <div class="bigdata">
    <div id="map"></div>
    <div class="chooseMapStyle">
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
    <div class="demo-autocomplete">
        <el-autocomplete
          size="large"
          class="inline-input"
          v-model="keyword"
          :fetch-suggestions="querySearch"
          placeholder="请输入内容"
          :trigger-on-focus="false"
        />
      <el-button size="large" class="searchbtn" @click="querySearchSelect(keyword)">搜 索</el-button>
    </div>
    <div class="bigdata_title">
<!--      <div><a href="/#/assetManegament" target="_blank">资产运维管理平台</a></div>-->
      <div>资产运维管理平台</div>
    </div>
    <div id="left-area" class="area left-area animate__animated animate__bounceInLeft">
      <div class="areabox top">
        <div>
          <div style="display: flex;align-items: center">
            <img src="/绿.png" alt=""/>
            <span>正常</span>
            <span v-if="greenNumber">（{{greenNumber}}）</span>
          </div>
          <div style="display: flex;align-items: center">
            <img src="/橙.png" alt=""/>
            <span>部分正常</span>
            <span v-if="orangeNumber">（{{orangeNumber}}）</span>
          </div>
          <div style="display: flex;align-items: center">
            <img src="/红.png" alt=""/>
            <span>全坏</span>
            <span v-if="redNumber">（{{redNumber}}）</span>
          </div>
        </div>
      </div>
    </div>
    <div class="right-area2 animate__animated" id="right-area2" style="display: none">
      <div class="bt-area">
        <img class="bt1" src="../../../assets/imgs/bt1.png" alt=""/>
        <img class="close" @click="toggleBoxCloseRight" src="../../../assets/imgs/close.png" alt=""/>
      </div>
      <div class="right-area2-1">
        <div class="r1-content">
          <img v-if="ganjianRightInfo.memberType=='T型杆'" class="tl" src="../../../assets/imgs/t.png" alt=""/>
          <img v-else class="tl" src="../../../assets/imgs/l.png" alt=""/>
          <div class="sxt-top">
            <template v-for="i in ganjianRightInfo.electricPoliceVOList" :key="i.id">
              <img v-if="i.addressXh != '球'" src="../../../assets/imgs/sxt-qj-green.png" alt=""/>
            </template>
<!--            <img src="../../assets/imgs/sxt-qj2-green.png" alt=""/>-->
<!--            <img src="../../assets/imgs/sxt-qj-red.png" alt=""/>-->
<!--            <img src="../../assets/imgs/sxt-qj2-red.png" alt=""/>-->
          </div>
          <div class="sxt-bottom">
            <template v-for="i in ganjianRightInfo.electricPoliceVOList" :key="i.id">
             <img v-if="i.addressXh == '球'"  src="../../../assets/imgs/sxt-qj3-green.png" alt=""/>
<!--            <img src="../../assets/imgs/sxt-qj3-red.png" alt=""/>-->
            </template>
          </div>
        </div>
      </div>
      <img style="display: block; width: 90%;margin: 0 auto;height: auto" src="../../../assets/imgs/fgx.png" alt=""/>
      <div style="margin-top: 15px">
        <img class="bt2" src="../../../assets/imgs/bt2.png" alt=""/>
      </div>
      <div class="wt-area">
        <div class="wt-area-wrapper">
          <div class="wt-area-info">
            <div>方向：</div>
            <div>{{ganjianRightInfo.adress}}</div>
          </div>
          <div class="wt-area-info">
            <div>杆件类型：</div>
            <div>{{ganjianRightInfo.memberType || 'L型杆'}}</div>
          </div>
        </div>
        <div class="wt-area-img" v-if="ganjianRightInfo.equImgs.length">
<!--          <img class="wt-img" src="../../assets/imgs/wt.png" alt=""/>-->
          <img class="wt-img" :key="ganjianRightInfo.equImgs[imgIndex]" :src="ganjianRightInfo.equImgs[imgIndex]" alt=""/>
          <img class="fdj" @click="dialogVisible=true" src="../../../assets/imgs/fdj.png" alt=""/>
          <img class="left" :class="imgIndex===0?'disabled':''" @click="prev(-1)" src="../../../assets/imgs/left.png" alt=""/>
          <img class="right" :class="imgIndex===ganjianRightInfo.equImgs.length -1?'disabled':''" @click="prev(1)" src="../../../assets/imgs/right.png" alt=""/>
        </div>
      </div>
      <img style="display: block; width: 90%;margin: 0 auto;height: auto" src="../../../assets/imgs/fgx.png" alt=""/>
      <div style="margin-top: 15px" v-if="ganjianRightInfo.electricPoliceVOList.length || ganjianRightInfo.accessoryEquipmentList.length">
        <img class="bt2" src="../../../assets/imgs/bt3.png" alt=""/>
      </div>
      <div class="fssb-area" v-if="ganjianRightInfo.electricPoliceVOList.length || ganjianRightInfo.accessoryEquipmentList.length">
        <div class="fssb-area-thead">
          <div>设备名称</div>
<!--          <div>维修数</div> -->
          <div>状态</div>
        </div>
        <div class="fssb-area-tbody-wrapper">
          <div class="fssb-area-tbody" v-for="i in ganjianRightInfo.electricPoliceVOList" :key="i.id">
            <div>{{i.adress}}</div>
<!--            <div>0</div>-->
            <div><span class="zc">{{i.status}}</span></div>
          </div>
          <div class="fssb-area-tbody" v-for="i in ganjianRightInfo.accessoryEquipmentList" :key="i.id">
            <div>{{i.equType}}</div>
            <!--            <div>0</div>-->
            <div><span class="zc">正常</span></div>
          </div>
        </div>
      </div>
    </div>
    <DialogNotitle width="50%" v-model="dialogVisible">
      <div class="DialogNotitle-content">
        <div class="DialogNotitle-title">
          现场照片
          <span @click="dialogVisible = false">×</span>
        </div>
        <img style="width: 100%;height: auto" :src="ganjianRightInfo.equImgs[imgIndex]" alt=""/>
      </div>
    </DialogNotitle>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import {
  queryPoliceOpsPositionList,
  autocompletePoliceOpsMemberList,
  baseEqu
} from '@/api/police/bigdata'
import { OpsMemberApi, OpsMemberVO } from '@/api/police/opsmember'
const map = ref(null);
const layer = ref(null);
const dialogVisible = ref(false);
const markers = ref([]);
const basedata = ref({});
const greenNumber = ref(0);
const redNumber = ref(0);
const orangeNumber = ref(0);
const keyword = ref('');
// const getWxTypeList = ref([])
const currentStyle = ref('2')
const imgIndex = ref(0)
// const showTypeName = ref('杆件')
const ganjianRightInfo = ref({
  electricPoliceVOList: [],
  accessoryEquipmentList: [],
  equImgs: []
})
const currentMapOptions = ref({
  resizeEnable: true,
  mapStyle: 'amap://styles/normal', //normal、blue
  center: [109.95315,40.621327],
  viewMode: '3D', // 设置地图为 3D 模式，这是显示 2.5D 效果的基础
  pitch: 65, // 地图倾斜角度，可根据需求调整
  rotation: 0,
  zoom: 14,
})
const options = ref([
  {
    value: '1',
    label: '2D标准',
    mapOptions: {
      resizeEnable: true,
      mapStyle: 'amap://styles/ac19c54a3821baa249afb9797ee19412',
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
  },
  {
    value: '3',
    label: '3D卫星',
    mapOptions: {
      resizeEnable: true,
      mapStyle: 'amap://styles/normal', //normal、blue
      center: [109.95315,40.621327],
      viewMode: '3D', // 设置地图为 3D 模式，这是显示 2.5D 效果的基础
      pitch: 65, // 地图倾斜角度，可根据需求调整
      rotation: 0,
      zoom: 14,
      features: ['bg', 'road', 'point'],
      layers: [
        // 高德默认标准图层
        new AMap.TileLayer.Satellite(),
        // 楼块图层
        new AMap.Buildings({
          zooms: [14, 20],
          zIndex: 10,
          //heightFactor: 2//2倍于默认高度，3D下有效
        })
      ],
    }
  },
])
const changeMapStyle = (item) => {
  currentMapOptions.value = item.mapOptions
  initMap(currentMapOptions.value)
  setTimeout(() => {
    getPosiList('', '')
  }, 1200)
}
const initMap = (options) => {
  map.value = new AMap.Map('map', options)
}
const drawLayer = (markersData) => {
  if(markers.value.length) {
    clearMarkers()
  }
    // 创建 AMap.LabelsLayer 图层
    layer.value = new AMap.LabelsLayer({
      zooms: [3, 20],
      zIndex: 1000,
      collision: false
    });

    // 将图层添加到地图
    map.value.add(layer.value);

    markers.value = [];
    greenNumber.value = 0
    redNumber.value = 0
    orangeNumber.value = 0
  markersData.forEach((markersData) => {
    if(!markersData.status) markersData.status = '绿'
    if(markersData.lng && markersData.lat && markersData.equType) {
      var curData = {
        position: new AMap.LngLat(markersData.lng, markersData.lat),
        data: markersData,
        icon: {
          type: 'image',
          image: `/${markersData.status}.png`,
          size: [20, 20],
          anchor: 'bottom-center',
        }
      };
      let labelMarker = new AMap.LabelMarker(curData);

      markers.value.push(labelMarker);
      if(markersData.status == '绿') {
        greenNumber.value++
      } else if(markersData.status == '红') {
        redNumber.value++
      } else {
        orangeNumber.value++
      }
      // 给marker绑定事件
      labelMarker.on('click', async function(e){
        console.log(e, markersData, 1)
        if(markersData.equType == '杆件') {
          ganjianRightInfo.value = await OpsMemberApi.getOpsMember(markersData.id)
          imgIndex.value = 0
          console.log(ganjianRightInfo.value)
          ganjianRightInfo.value.equImgs = []
          if(ganjianRightInfo.value.equImg) {
            ganjianRightInfo.value.equImgs.push(ganjianRightInfo.value.equImg)
          }
          if(ganjianRightInfo.value.equImg2) {
            ganjianRightInfo.value.equImgs.push(ganjianRightInfo.value.equImg2)
          }
          if(ganjianRightInfo.value.equImg3) {
            ganjianRightInfo.value.equImgs.push(ganjianRightInfo.value.equImg3)
          }
          toggleBoxRight()
        }
      });
    }
  })
    // 一次性将海量点添加到图层
    layer.value.add(markers.value)
    if(markers.value.length < 1000) {
      map.value.setFitView(markers.value)
    }
}
const clearMarkers = () => {
  map.value.remove(layer.value);
  markers.value.forEach(marker => {
    map.value.remove(marker);
  });
  markers.value = []; // 清空数组
}
const toggleBoxRight = () => {
  const right2 = document.getElementById('right-area2')
  if(right2.className.indexOf('animate__bounceInRight') == -1) {
    right2.classList.add('animate__bounceInRight');
    right2.classList.remove('animate__bounceOutRight');
    if(right2.style.display == 'none') right2.style.display = 'block'
  } else {
    right2.classList.remove('animate__bounceOutRight');
    right2.classList.add('animate__bounceInRight');
  }
}
const toggleBoxCloseRight = () => {
  const right2 = document.getElementById('right-area2')
  right2.classList.add('animate__bounceOutRight');
  right2.classList.remove('animate__bounceInRight');
}

const restaurants = ref([])
const querySearch = async (queryString, cb) => {
  if(queryString) {
    const data = await autocompletePoliceOpsMemberList({equName: queryString})
    data.map(e => {
      e.value = e.equName
      return
    })
    restaurants.value = data
    console.log(restaurants.value)
  }
  var results = queryString
    ? restaurants.value.filter(createFilter(queryString))
    : restaurants.value
  // 调用 callback 返回建议列表的数据
  cb(results)
}
const createFilter = (queryString) => {
  return (restaurant) => {
    return (
      restaurant.value
        .toLowerCase()
        .indexOf(queryString.toLowerCase()) === 0
    )
  }
}

const querySearchSelect = (equName) => {
  baseEquAPI(equName)
  getPosiList(equName, '')
}
const getPosiList = async (equName, equType) => {
  const datalist = await queryPoliceOpsPositionList({equName: equName, equType: equType || ''})
  drawLayer(datalist)
}
const prev = (type) => {
  if(imgIndex.value > 0) {
    if(type === -1) {
      imgIndex.value = imgIndex.value - 1
    }
    return
  }
  if(imgIndex.value < ganjianRightInfo.value.equImgs.length - 1) {
    if(type === 1) {
      imgIndex.value = imgIndex.value + 1
    }
    return;
  }
}

onMounted(() => {
  initMap(currentMapOptions.value)
  setTimeout(() => {
    getPosiList('', '')
  }, 200)
});
</script>

<style scoped lang="scss">
.bigdata {
  position: relative;
  width: 100%;
  height: calc(100vh - 170px);
  min-height: 1200px;
  overflow: hidden;
  .showicon {
    position: absolute;
    top: 14px;
    right: 14px;
    width: 22px;
    z-index: 1;
    cursor: pointer;
  }
  #map {
    width: 100%;
    height: calc(100vh - 170px);
    min-height: 1200px;
  }
  .bigdata_title {
    position: absolute;
    width: 76%;
    top: 0px;
    left: 50%;
    transform: translateX(-50%);
    text-align: center;
    color: #ffffff;
    background: url("../../../assets/imgs/bigdata/title.png");
    background-size: 100% 100%;
    padding: 10px 0 20px 0;
    font-size: 19px;
  }
}
.left-area {
  position: absolute;
  top: 60px;
  left: 15px;
  width: 200px;
}
.right-area {
  position: absolute;
  top: 60px;
  right: 15px;
  width: 340px;
}
.right-area2 {
  position: absolute;
  top: 60px;
  right: 15px;
  width: 360px;
  //height: 1000px;
  max-height: 93%;
  overflow: auto;
  background: rgba(3, 86, 189, 1);
  border: 1px dashed #707070;
  z-index: 101;
  box-sizing: border-box;
  border-radius: 10px;
  .bt-area {
    position: relative;
    width: 100%;
    background: url(../../assets/imgs/kk.png);
    background-size: 100% 100%;
    padding-left: 44px;
    height: 50px;
    line-height: 50px;
    padding-top: 5px;
    .bt1 {
      width: 80%;
    }
    .close {
      position: absolute;
      right: 20px;
      top: 15px;
      width: 15px;
      height: 15px;
      cursor: pointer;
    }
  }
  .bt2{
    width: auto;
    height: 40px;
  }
  .right-area2-1 {
    height: 250px;
    padding: 30px;
    .r1-content {
      position: relative;
      width: 100%;
      height: 100%;
      padding-top: 10px;
      .tl {
        width: 100%;
        height: 100%;
      }
      .sxt-top {
        position: absolute;
        display: flex;
        width: 100%;
        top: -15px;
        justify-content: space-between;
        img {
          width: 20px;
          height: auto;
          margin: 0 5px;
        }
      }
      .sxt-bottom {
        position: absolute;
        display: flex;
        width: 100%;
        top: 24px;
        justify-content: center;
        //justify-content: space-between;
        img {
          width: 20px;
          height: auto;
          margin: 0 5px;
        }
      }
    }
  }
  .wt-area {
    padding: 0 20px;
    .wt-area-wrapper {
      display: flex;
      color: #ffffff;
      .wt-area-info {
        flex: 1;
        text-align: left;
        font-size: 15px;
        font-weight: bold;
      }
    }
    .wt-area-img {
      position: relative;
      width: 100%;
      padding: 10px 0;
      .wt-img {
        width: 100%;
        height: auto;
      }
      .fdj {
        width: 45px;
        height: 45px;
        cursor: pointer;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      }
      .left {
        cursor: pointer;
        width: 35px;
        height: 35px;
        position: absolute;
        top: 50%;
        left: 20px;
        transform: translate(0%, -50%);
      }
      .right {
        cursor: pointer;
        width: 35px;
        height: 35px;
        position: absolute;
        top: 50%;
        right: 20px;
        transform: translate(0%, -50%);
      }
      .disabled {
        opacity: 0.5;
      }
    }
  }
  .fssb-area {
    padding: 0 20px;
    color: #ffffff;
    .fssb-area-thead {
      font-size: 15px;
    }
    .fssb-area-tbody-wrapper {
      //height: 220px;
      //overflow: auto;
    }
    .fssb-area-tbody {
      font-size: 13px;
      &:nth-child(odd) {
        background: rgba(65, 160, 235, 0.3);
      }
      .zc {
        padding: 3px 10px;
        background: #3176FF;
        color: #ffffff;
      }
      .sh {
        padding: 3px 10px;
        background: #B43B46;
        color: #ffffff;
      }
      .yc {
        padding: 3px 10px;
        background: #AAA9BC;
        color: #ffffff;
      }
    }
    .fssb-area-thead,.fssb-area-tbody {
      display: flex;
      padding: 8px 0;
      align-items: center;
      div {
        flex: 1;
        text-align: center;
      }
    }
  }
}
.area {
  color: #ffffff;
  .title {
    position: relative;
    z-index: 2;
  }
  .areabox {
    position: relative;
    border-radius: 10px;
    background: rgba(0, 0, 0, 0.6);
    border: 2px solid rgba(0, 186, 235, 1);
    margin-bottom: 20px;
    padding: 10px;
  }
  .top {
    height: auto;
    .ssimg {
      z-index: 1;
      position: absolute;
      border-radius: 8px;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
    }
  }
  .middle {
    height: 380px;
    .eventlist {
      .eventlist-title {
        display: flex;
        margin-top: 10px;
        div {
          text-align: center;
          flex: 1;
          color:#0E9EED;
          font-weight: bold;
        }
      }
      .eventlist-content-wrapper {
        height: 300px;
        overflow: auto;
        scrollbar-width: thin; /* 可以是 'auto', 'thin', 'none' 或 'inherit' */
        scrollbar-color: #888 rgba(0, 0, 0, 0.6); /* 滚动条实际可拖动部分的颜色 滚动条轨道的背景颜色 */
      }
      .eventlist-content {
        display: flex;
        margin-top: 6px;
        div {
          text-align: center;
          flex: 1;
          font-size: 13px;
        }
      }
    }
  }
  .bottom {
    height: 350px;
  }
}

.top1 {
  display: flex;
  margin-top: 10px;
  .top1-item {
    //flex: 1;
    width: 50%;
    text-align: center;
    cursor: pointer;
    img {
      width: 25px;
      height: 26px;
    }
    div {
      font-size: 13px;
      padding: 3px 0;
    }
  }
}
::v-deep .el-progress-circle__track {
  stroke: #188BE1;
}

::v-deep .map-marker {
  width: 48px;
  height: auto;
}

::v-deep .amap-info-content {
  position: relative;
  background: transparent;
  padding: 0;
  line-height: 1.4;
  overflow: auto;
  .tankuang {
    position: relative;
    width: 380px;
    height: auto;
    //height: 300px;
    background: url("../../assets/imgs/bigdata/tankuang.png");
    background-size: 100% 100%;
    color: #ffffff;
    padding: 20px 20px 20px 20px;
    font-size: 13px;
    .tankuang_title {
      position: absolute;
      left: 10px;
      top: 8px;
      width: 60%;
      text-align: center;
      color: #0F2142;
      span {
        background: url("../../assets/imgs/bigdata/title-box.png");
        background-size: 100% 100%;
        padding: 0 8px;
      }
    }
    .tankuang_content {
      margin-top: 20px;
      width: 66%;
      font-size: 16px;
      .tankuang_content_item {
        display: flex;
        color: #E0E0E0;
        padding: 3px 0;
        .tankuang_content_item_title {
          color: #00A8A4
        }
        .tankuang_content_item_info {
          flex: 1;
        }
      }
    }
  }
}

.demo-autocomplete {
  position: absolute;
  border: 1px solid #00BAEB;
  background: #00BAEB;
  border-radius: 4px;
  top: 80px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  .inline-input {
    width: 300px;
  }
  ::v-deep .el-input__wrapper {
    background: #000000;
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
    box-shadow: none;
  }
  ::v-deep .el-input__inner {
    color: #15DBF5;
    font-size: 16px;
  }
  .searchbtn {
    background: #00BAEB;
    color: #000000;
    border-color: #00BAEB;
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    width: 100px;
  }
}
.chooseMapStyle {
  position: absolute;
  top: 20px;
  right: 60px;
  width: 100px;
  z-index: 10001;
}
.ellipsis {
  overflow: hidden;/* 超出的文本隐藏 */
  text-overflow: ellipsis;/* 用省略号显示 */
  white-space: nowrap;/* 不换行 */
}
</style>
