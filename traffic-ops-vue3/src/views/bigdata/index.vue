<template>
  <div class="bigdata">
    <img class="showicon" @click="toggleBox" src="../../assets/imgs/bigdata/show.png" alt=""/>
    <div id="map"></div>
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
     <!-- <div><a href="/#/assetManegament" target="_blank">资产运维管理平台</a></div> -->
      <div>资产运维管理平台</div>
    </div>
    <div id="left-area" class="area left-area animate__animated animate__bounceInLeft">
      <div class="areabox top">
        <div class="title">基础设施</div>
        <div class="top1">
          <div class="top1-item">
            <img src="../../assets/imgs/bigdata/lu.png" alt=""/>
            <div>路</div>
            <div style="color:#D63A0A">{{basedata.roadNum}}个</div>
          </div>
          <div class="top1-item">
            <img src="../../assets/imgs/bigdata/lukou.png" alt=""/>
            <div>路口</div>
            <div style="color:#06DA1A">{{basedata.intersectionNum}}个</div>
          </div>
        </div>
        <div class="top1">
          <div class="top1-item" @click="handleSelect('信号机')">
            <img src="../../assets/imgs/bigdata/icon1.png" alt=""/>
            <div>信号机</div>
            <div style="color:#D63A0A">{{basedata.signalNum}}个</div>
          </div>
          <div class="top1-item" @click="handleSelect('电警')">
            <img src="../../assets/imgs/bigdata/icon2.png" alt=""/>
            <div>电警</div>
            <div style="color:#E8980C">{{basedata.electricPoliceNum}}个</div>
          </div>
          <div class="top1-item" @click="handleSelect('led屏')">
            <img src="../../assets/imgs/bigdata/icon3.png" alt=""/>
            <div>led大屏</div>
            <div style="color:#06DA1A">{{basedata.bigLedNum}}个</div>
          </div>
          <div class="top1-item" @click="handleSelect('杆件')">
            <img src="../../assets/imgs/bigdata/icon4.png" alt=""/>
            <div>杆件</div>
            <div style="color:#ED5A02">{{basedata.memberNum}}个</div>
          </div>
        </div>
        <div class="top1">
          <div class="top1-item" @click="handleSelect('信号灯')">
            <img src="../../assets/imgs/xhd_icon.png" alt=""/>
            <div>信号灯</div>
            <div style="color:#D63A0A">{{basedata.signalLightNum}}个</div>
          </div>
          <div class="top1-item" @click="handleSelect('标线')">
            <img src="../../assets/imgs/bx_icon.png" alt=""/>
            <div>标线</div>
            <div style="color:#E8980C">{{basedata.marklineNum}}个</div>
          </div>
          <div class="top1-item" @click="handleSelect('标志牌')">
            <img src="../../assets/imgs/bzp_icon.png" alt=""/>
            <div>标志牌</div>
            <div style="color:#06DA1A">{{basedata.boardNum}}个</div>
          </div>
          <div class="top1-item" @click="handleSelect('护栏')">
            <img src="../../assets/imgs/hl_icon.png" alt=""/>
            <div>护栏</div>
            <div style="color:#06DA1A">{{basedata.guardrailNum}}个</div>
          </div>
          <!-- <div class="top1-item" @click="handleSelect('雷达')">
            <img src="../../assets/imgs/ld_icon.png" alt=""/>
            <div>雷达</div>
            <div style="color:#ED5A02">{{basedata.radarNum}}个</div>
          </div> -->
        </div>
      </div>
      <div class="areabox middle" v-show="showTypeName==='杆件'">
        <div class="title">各设备占比</div>
        <div class="top1" style="margin-top: 20px">
          <div class="top1-item">
            <el-progress class="custom-progress-ring" :width="60" type="circle" color="#FCC801" :stroke-width=4 :percentage="basedata.signalProp">
              <template #default="{ percentage }">
                <span style="color:#CA6026;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>信号机</div>
          </div>
          <div class="top1-item">
            <el-progress :width="60" type="circle" color="#3CD3AD" :stroke-width=4 :percentage="basedata.electricPoliceProp">
              <template #default="{ percentage }">
                <span style="color:#3CD3AD;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>电警</div>
          </div>
          <div class="top1-item">
            <el-progress :width="60" type="circle" color="#9D50BB" :stroke-width=4 :percentage="basedata.bigLedProp">
              <template #default="{ percentage }">
                <span style="color:#9D50BB;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>led大屏</div>
          </div>
          <div class="top1-item">
            <el-progress :width="60" type="circle" color="#CA6026" :stroke-width=4 :percentage="basedata.memberProp">
              <template #default="{ percentage }">
                <span style="color:#CA6026;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>杆件</div>
          </div>
        </div>
        <div class="top1" style="margin-top: 20px">
          <div class="top1-item">
            <el-progress class="custom-progress-ring" :width="60" type="circle" color="#FCC801" :stroke-width=4 :percentage="basedata.signalLightProp">
              <template #default="{ percentage }">
                <span style="color:#CA6026;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>信号灯</div>
          </div>
          <div class="top1-item">
            <el-progress :width="60" type="circle" color="#3CD3AD" :stroke-width=4 :percentage="basedata.marklineProp">
              <template #default="{ percentage }">
                <span style="color:#3CD3AD;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>标线</div>
          </div>
          <div class="top1-item">
            <el-progress :width="60" type="circle" color="#9D50BB" :stroke-width=4 :percentage="basedata.boardProp">
              <template #default="{ percentage }">
                <span style="color:#9D50BB;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>标志牌</div>
          </div>
          <!-- <div class="top1-item">
            <el-progress :width="60" type="circle" color="#CA6026" :stroke-width=4 :percentage="basedata.radarProp">
              <template #default="{ percentage }">
                <span style="color:#CA6026;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>雷达</div>
          </div> -->
          <div class="top1-item">
            <el-progress class="custom-progress-ring" :width="60" type="circle" color="#FCC801" :stroke-width=4 :percentage="basedata.guardrailProp">
              <template #default="{ percentage }">
                <span style="color:#CA6026;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>护栏</div>
          </div>
        </div>
        <!-- <div class="top1" style="margin-top: 20px">
          <div class="top1-item">
            <el-progress class="custom-progress-ring" :width="60" type="circle" color="#FCC801" :stroke-width=4 :percentage="basedata.guardrailProp">
              <template #default="{ percentage }">
                <span style="color:#CA6026;font-size: 16px;font-weight: bold">{{ percentage }}%</span>
              </template>
            </el-progress>
            <div>护栏</div>
          </div>
        </div> -->
      </div>
      <div class="areabox middle" v-show="showTypeName==='电警'">
        <div class="title">红绿灯监测分析</div>
        <div class="eventlist">
          <div class="eventlist-title">
            <div>类型</div>
            <div>方向</div>
            <div>IP</div>
            <div>故障次数</div>
          </div>
          <div class="eventlist-content-wrapper">
            <el-carousel :key="'dianjingDataList_' + dianjingDataList.length" indicator-position="none" height="300px" direction="vertical" :autoplay="true" :interval="10000">
              <el-carousel-item v-for="(item,index) in dianjingDataList" :key="'dianjingData_' + index">
                <div class="eventlist-content" v-for="i in item" :key="'dianjingData_' + i.ip_addr">
                  <div :title="i.event_type" class="ellipsis">{{i.event_type}}</div>
                  <div>{{i.direction}}</div>
                  <div>{{i.ip_addr}}</div>
                  <div>{{i.abnormal_cnt}}</div>
                </div>
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>
      </div>
      <div class="areabox bottom" v-show="showTypeName==='杆件'">
        <div class="title">报修分析</div>
        <div ref="elRef" style="width: 100%;height: 300px"></div>
      </div>
      <div class="areabox bottom" v-show="showTypeName==='电警'">
        <div class="title">红绿灯监测分析</div>
        <div ref="elRef5" style="width: 100%;height: 300px"></div>
      </div>
    </div>
    <div id="right-area" class="area right-area animate__animated animate__bounceInRight">
      <div class="areabox top" v-show="showTypeName==='杆件'">
        <div class="title">全景地图
          <a style="vertical-align: middle" href="https://www.720yun.com/vr/8bdjt0eaOy2" v-if="showssjk==1" target="_blank"><el-icon color="#ffffff"><Rank /></el-icon></a>
          <a style="vertical-align: middle" href="https://www.720yun.com/vr/0c1jt0eaOk8" v-else-if="showssjk==2" target="_blank"><el-icon color="#ffffff"><Rank /></el-icon></a>
        </div>
        <iframe v-if="showssjk==1" src="https://www.720yun.com/vr/8bdjt0eaOy2" frameborder="no" width="100%" height="92%" ></iframe>
        <iframe v-else-if="showssjk==2" src="https://www.720yun.com/vr/0c1jt0eaOk8" frameborder="no" width="100%" height="92%" ></iframe>
        <img v-else class="ssimg" src="../../assets/imgs/bigdata/img.png" alt=""/>
      </div>
      <div class="areabox top" style="padding: 0;overflow: hidden" v-show="showTypeName==='电警'">
        <div ref="elRef4" style="width: 100%;height: 100%;"></div>
      </div>
      <div class="areabox middle" v-show="showTypeName==='杆件'">
        <div class="title">报修事件播报</div>
        <div class="eventlist">
          <div class="eventlist-title">
            <div>问题</div>
            <div>上报单位</div>
            <div>时间</div>
          </div>
          <div class="eventlist-content-wrapper">
            <div class="eventlist-content" v-for="i in wxDetail.maintenanceList" :key="i.id">
              <div :title="i.problem" class="ellipsis">{{i.problem}}</div>
              <div>{{i.reportDept}}</div>
              <div>{{formatDate(i.reportTime)}}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="areabox middle" v-show="showTypeName==='电警'">
        <div class="title">电警监测分析</div>
        <div class="eventlist">
          <div class="eventlist-title">
            <div>类型</div>
            <div>方向</div>
            <div>IP</div>
            <div>故障次数</div>
          </div>
          <div class="eventlist-content-wrapper">
            <el-carousel :key="'dianjingData2List_' + dianjingData2List.length"  indicator-position="none" height="300px" direction="vertical" :autoplay="true" :interval="10000">
              <el-carousel-item v-for="(item,index) in dianjingData2List" :key="'dianjingData2_' + index">
                <div class="eventlist-content" v-for="i in item" :key="'dianjingData2_' + i.ip_addr">
                  <div :title="i.event_type" class="ellipsis">{{i.event_type}}</div>
                  <div>{{i.direction}}</div>
                  <div>{{i.ip_addr}}</div>
                  <div>{{i.abnormal_cnt}}</div>
                </div>
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>
      </div>
      <div class="areabox bottom" v-show="showTypeName==='杆件'">
        <div class="title">设备报修占比</div>
        <div ref="elRef2" style="width: 100%;height: 300px"></div>
      </div>
      <div class="areabox bottom" v-show="showTypeName==='电警'">
        <div class="title">电警监测分析</div>
        <div ref="elRef6" style="width: 100%;height: 300px"></div>
      </div>
    </div>
    <div class="right-area2 animate__animated" id="right-area2" style="display: none">
      <div class="bt-area">
        <div class="bt1" >{{ganjianRightInfo.intersectionName}}</div>
        <img class="close" @click="toggleBoxCloseRight" src="../../assets/imgs/close.png" alt=""/>
      </div>
      <div class="right-area2-1">
        <div class="r1-content">
          <img v-if="ganjianRightInfo.memberType=='T型杆'" class="tl" src="../../assets/imgs/t.png" alt=""/>
          <img v-else class="tl" src="../../assets/imgs/l.png" alt=""/>
          <div class="sxt-top">
            <template v-for="i in ganjianRightInfo.electricPoliceVOList" :key="i.id">
              <img v-if="i.addressXh != '球'" src="../../assets/imgs/sxt-qj-green.png" alt=""/>
            </template>
<!--            <img src="../../assets/imgs/sxt-qj2-green.png" alt=""/>-->
<!--            <img src="../../assets/imgs/sxt-qj-red.png" alt=""/>-->
<!--            <img src="../../assets/imgs/sxt-qj2-red.png" alt=""/>-->
          </div>
          <div class="sxt-bottom">
            <template v-for="i in ganjianRightInfo.electricPoliceVOList" :key="i.id">
             <img v-if="i.addressXh == '球'"  src="../../assets/imgs/sxt-qj3-green.png" alt=""/>
<!--            <img src="../../assets/imgs/sxt-qj3-red.png" alt=""/>-->
            </template>
          </div>
        </div>
      </div>
      <img style="display: block; width: 90%;margin: 0 auto;height: auto" src="../../assets/imgs/fgx.png" alt=""/>
      <div style="margin-top: 15px">
        <img class="bt2" src="../../assets/imgs/bt2.png" alt=""/>
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
          <img class="fdj" @click="dialogVisible=true" src="../../assets/imgs/fdj.png" alt=""/>
          <img class="left" :class="imgIndex===0?'disabled':''" @click="prev(-1)" src="../../assets/imgs/left.png" alt=""/>
          <img class="right" :class="imgIndex===ganjianRightInfo.equImgs.length -1?'disabled':''" @click="prev(1)" src="../../assets/imgs/right.png" alt=""/>
        </div>
      </div>
      <img style="display: block; width: 90%;margin: 0 auto;height: auto" src="../../assets/imgs/fgx.png" alt=""/>
      <div style="margin-top: 15px" v-if="ganjianRightInfo.electricPoliceVOList.length || ganjianRightInfo.accessoryEquipmentList.length">
        <img class="bt2" src="../../assets/imgs/bt3.png" alt=""/>
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
import echarts from "@/plugins/echarts";
import { GraphChart } from 'echarts/charts';
import { Rank } from '@element-plus/icons-vue'
echarts.use([GraphChart]);
import {
  analysisElectricPoliceByLabel, analysisMonitorResultByEventType, analysisMonitorResultByKeyWord,
  autocompleteOpsIntersection,
  baseEqu,
  getWxType, maintenancePagedp,
  queryTrafficOpsPositionList,
  trafficOpsMemberGet,
  trafficOpsMemberGetPage
} from '@/api/bigdata'
import {formatDate} from "@/utils/formatTime";
let echartRef: Nullable<echarts.ECharts> = null
const elRef = ref<ElRef>()
const elRef2 = ref<ElRef>()
const elRef4 = ref<ElRef>()
const elRef5 = ref<ElRef>()
const elRef6 = ref<ElRef>()
const map = ref(null);
const timer22 = ref(null);
const timer11 = ref(null);
const layer = ref(null);
const dialogVisible = ref(false);
const markers = ref([]);
const dianjingDataList = ref([]);
const dianjingData2List = ref([]);
const basedata = ref({});
const keyword = ref('');
const getWxTypeList = ref([])
const currentStyle = ref('2')
const showssjk = ref('3')
const imgIndex = ref(0)
const cluster = ref(null)
const showTypeName = ref('杆件')
const VITE_VUE_NETTYPE = ref(import.meta.env.VITE_VUE_NETTYPE)
const allPointList = ref([])
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
const wxDetail = ref({
  maintenanceList: [],
  dpdCount: 0,
  djdCount: 0,
});
const changeMapStyle = (item) => {
  currentMapOptions.value = item.mapOptions
  initMap(currentMapOptions.value)
  setTimeout(() => {
    getPosiList()
  }, 1200)
}
const initMap = (options) => {
  if(VITE_VUE_NETTYPE.value === '内网') {
    map.value = new AMap.Map('map', {
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
    map.value = new AMap.Map('map', options)
  }
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
  markersData.forEach((markersData) => {
    if(markersData.lng && markersData.lat && markersData.equType) {
      var curData = {
        position: new AMap.LngLat(markersData.lng, markersData.lat),
        data: markersData,
        icon: {
          type: 'image',
          image: `/${markersData.equType}.png`,
          size: [32, 32],
          anchor: 'bottom-center',
        }
      };
      let labelMarker = new AMap.LabelMarker(curData);
      markers.value.push(labelMarker);
      // 给marker绑定事件
      labelMarker.on('click', async function(e){
        console.log(e, markersData, 1)
        let infoWindow
        if(markersData.equType == 'led屏') {
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">IP地址：</div>
                    <div class="tankuang_content_item_info">${markersData.ipAddr || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">屏幕朝向：</div>
                    <div class="tankuang_content_item_info">${markersData.screenOrientation || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">大屏对应编号：</div>
                    <div class="tankuang_content_item_info">${markersData.dpbh || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">大小：</div>
                    <div class="tankuang_content_item_info">${markersData.size || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        } else if(markersData.equType == '信号机') {
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">信号控制相位：</div>
                    <div class="tankuang_content_item_info">${markersData.phase || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">信号机型号：</div>
                    <div class="tankuang_content_item_info">${markersData.equModel || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">信号机编号：</div>
                    <div class="tankuang_content_item_info">${markersData.equNumber || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">IP地址：</div>
                    <div class="tankuang_content_item_info">${markersData.ipAddr || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        } else if(markersData.equType == '电警') {
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">电警类型：</div>
                    <div class="tankuang_content_item_info">${markersData.djType || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">方向：</div>
                    <div class="tankuang_content_item_info">${markersData.direction || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">IP地址：</div>
                    <div class="tankuang_content_item_info">${markersData.ipAddr || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        } else if(markersData.equType == '杆件') {
          ganjianRightInfo.value = await trafficOpsMemberGet(markersData.id)
          imgIndex.value = 0
          console.log(ganjianRightInfo.value, 0)
          // if(ganjianRightInfo.value.electricPoliceVOList && ganjianRightInfo.value.electricPoliceVOList.length) {
          //
          // }
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
          var x = await trafficOpsMemberGetPage({pageNo: 1, pageSize: 99,equNumber: markersData.equNumber})
          toggleBoxRight()
          return
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">设备序号：</div>
                    <div class="tankuang_content_item_info">${markersData.serialNum || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">方向：</div>
                    <div class="tankuang_content_item_info">${markersData.adress || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">设备图片：</div>
                    <div class="tankuang_content_item_info"><img src="${markersData.equImg || ''}" width="130px" height="130px"></div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        }else {
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        }
        infoWindow.open(map.value, e.target.getPosition()); //打开信息窗体
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
const toggleBox = () => {
  const left = document.getElementById('left-area');
  const right = document.getElementById('right-area');
  if(left.className.indexOf('animate__bounceInLeft') !== -1) {
    left.classList.remove('animate__bounceInLeft');
    left.classList.add('animate__bounceOutLeft');
    right.classList.remove('animate__bounceInRight');
    right.classList.add('animate__bounceOutRight');
  } else {
    left.classList.remove('animate__bounceOutLeft');
    left.classList.add('animate__bounceInLeft');
    right.classList.remove('animate__bounceOutRight');
    right.classList.add('animate__bounceInRight');
  }
  const right2 = document.getElementById('right-area2')
  if(right2.className.indexOf('animate__bounceInRight') !== -1) {
    right2.classList.remove('animate__bounceInRight');
    right2.classList.add('animate__bounceOutRight');
  }
}
const toggleBoxRight = () => {
  const left = document.getElementById('left-area');
  const right = document.getElementById('right-area');
  if(left.className.indexOf('animate__bounceInLeft') !== -1) {
    left.classList.remove('animate__bounceInLeft');
    left.classList.add('animate__bounceOutLeft');
    right.classList.remove('animate__bounceInRight');
    right.classList.add('animate__bounceOutRight');
  }
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

const baseEquAPI = async (intersectionName) => {
  basedata.value = await baseEqu({intersectionName: intersectionName})
  getWxTypeList.value = await getWxType()
  initChart2(getWxTypeList.value)
  wxDetail.value = await maintenancePagedp({})
}
const initChart = () => {
  const options = {
    "grid": {
      "borderWidth": 1,
      "top": 40,
      "right": 20,
      "left": 40,
      "bottom": 40
    },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月', '9月', '10月', '11月', '12月'],
      axisLine: {
        lineStyle: {
          color: '#fff',
        },
      }
    },
    yAxis: {
      type: 'value',
      "splitLine": {
        "show": true,
        lineStyle: {
          color: 'rgba(242, 242, 242, 0.1)'
        }
      },
      axisLine: {
        lineStyle: {
          color: '#fff',
        },
      }
    },
    series: [
      {
        data: [150, 230, 224, 218, 135, 147, 260,  224, 218, 135, 147, 260],
        symbolSize: 7,
        symbol: 'circle',
        "itemStyle": {
          "normal": {
            "color": "#16FFFF",
            "barBorderRadius": 0,
            "borderColor":'#ffffff',//拐点边框颜色
          }
        },
        type: 'line'
      }
    ]
  };
  if (unref(elRef)) {
    echartRef = echarts.init(unref(elRef) as HTMLElement)
    echartRef?.setOption(unref(options))
  }
}
const initChart2 = (data) => {
  const colors = [
    ['#FFB400', '#28B1FF'],
    ['#8F2BFF', '#28B1FF'],
    ['#28B1FF', '#2B8AFF'],
    ['#FF0048', '#FFAB61'],
    ['#00FCFF', '#28B1FF'],
    ['#2BFFDF', '#28B1FF'],
    ['rgba(253,152,152,1)', 'rgba(238,97,97,1)'],
    ['rgba(184,152,253,1)', 'rgba(119,117,248,1)'],
    ['rgba(104,152,253,1)', 'rgba(109,117,248,1)'],
    ['rgba(104,102,253,1)', 'rgba(109,107,248,1)'],
    ['rgba(104,152,203,1)', 'rgba(109,117,208,1)']
  ]
  // const menuData = [
  //   {
  //     name: '事件1',
  //     value: 23627
  //   },
  //   {
  //     name: '事件2',
  //     value: 21602
  //   },
  //   {
  //     name: '事件3',
  //     value: 14123
  //   },
  //   {
  //     name: '事件4',
  //     value: 22822
  //   },
  //   {
  //     name: '事件5',
  //     value: 12761
  //   },
  //   {
  //     name: '事件6',
  //     value: 13261
  //   }
  // ]
  const resultdata = []
  const qtobj = {
    name: '其他',
    value: 0
  }
  data.forEach(e => {
    if(e.fault_type == '信号机故障' || e.fault_type == '信号灯故障' || e.fault_type == '网络故障' || e.fault_type == '供电故障') {
      resultdata.push({
        name: e.fault_type,
        value: e.count
      })
    } else {
      qtobj.value = qtobj.value + e.count
    }
  })
  resultdata.push(qtobj)
  const legendData = resultdata
  const options = {
    legend: [
      {
        icon: 'circle',
        textStyle: {
          color: '#ffffff',
          fontSize: 12
        },
        data: legendData,
        itemWidth: 15,
        itemHeight: 15,
        borderRadius: 15,
        x: 'center',
        top: 'bottom',
        align: 'left',
        itemGap: 20
      }
    ],
    toolbox: {
      show: false
    },
    tooltip: {
      trigger: 'item',
      formatter: (p) => {
        return `${p.name}${p.seriesName}</br> ${p.percent}%`
      }
    },
    series: [
      {
        name: '',
        type: 'pie',
        radius: [35, 110],
        center: ['50%', '45%'],
        roseType: 'area',
        startAngle: 90,
        label: {
          show: false
        },
        itemStyle: {
          borderRadius: 0,
          color: (p) => {
            return {
              colorStops: [
                {
                  offset: 0,
                  color: colors[p.dataIndex][0] // 0% 处的颜色
                },
                {
                  offset: 1,
                  color: colors[p.dataIndex][1] // 100% 处的颜色
                }
              ]
            }
          },
          borderColor: 'rgba(0, 0, 0, 0.6)',
          borderWidth: 10
        },
        data: resultdata
      }
    ]
  }
  if (unref(elRef2)) {
    echartRef = echarts.init(unref(elRef2) as HTMLElement)
    echartRef?.setOption(unref(options))
  }
}

const restaurants = ref([])
const querySearch = async (queryString, cb) => {
  if(queryString) {
    const data = await autocompleteOpsIntersection({intersectionName: queryString})
    data.map(e => {
      e.value = e.ldmc
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
const handleSelect = async (item) => {
  if(item === '杆件') {
    showTypeName.value = '杆件'

  } else if(item === '电警') {
    showTypeName.value = '电警'
    const data = await analysisElectricPoliceByLabel()
    var number = 0
    data.map(e => {
      e.name = e.label
      e.value = e.equ_cnt
      number = number + e.equ_cnt
      return e
    })
    const data2 = await analysisMonitorResultByEventType(1) //红绿灯监测分析
    data2.map(e => {
      e.name = e.event_type
      e.value = e.report_cnt
      return e
    })
    const data3 = await analysisMonitorResultByEventType(2) //电警监测分析
    data3.map(e => {
      e.name = e.event_type
      e.value = e.report_cnt
      return e
    })
    setTimeout(() => {
      dianjingChart1(data, number)
      dianjingChart2(data2)
      dianjingChart3(data3)
    }, 200)
  }
  filterSearch(item)
}
const filterSearch = (name) => {
  if(name === '电警' || name === '杆件') {
    const filterdata = allPointList.value.filter(e => e.equType === name)
    addCluster(filterdata)
  }
}
const getlist = async () => {
  dianjingDataList.value = []
  dianjingData2List.value = []
  const dianjingDataQuaryparams = {
    pageNo: 1,
    pageSize: 12,
    equType: 1
  }
  const dianjingDataQuaryparams2 = {
    pageNo: 1,
    pageSize: 12,
    equType: 2
  }
  var data4 = await analysisMonitorResultByKeyWord(dianjingDataQuaryparams)
  var data5 = await analysisMonitorResultByKeyWord(dianjingDataQuaryparams2)
  dianjingDataList.value.push(data4.list)
  dianjingData2List.value.push(data5.list)
  if(data5.length > 12) {
    data5.forEach((e, index) => {
      var iindex = Math.floor(index/12)
      if(!dianjingData2List.value[iindex]) {
        dianjingData2List.value[iindex] = []
      }
      dianjingData2List.value[iindex].push(e)
    })
  } else {
    dianjingData2List.value = data5
  }
  if(data4.length > 12) {
    data4.forEach((e, index) => {
      var iindex = Math.floor(index/12)
      if(!dianjingDataList.value[iindex]) {
        dianjingDataList.value[iindex] = []
      }
      dianjingDataList.value[iindex].push(e)
    })
  } else {
    dianjingDataList.value = data4
  }
}
const querySearchSelect = (intersectionName) => {
  if(intersectionName === '钢铁大街与富强路交叉口') {
    showssjk.value = '1'
  } else if(intersectionName === '富强路与正翔区间道交叉口') {
    showssjk.value = '2'
  } else {
    showssjk.value = '3'
  }
  baseEquAPI(intersectionName)
  getPosiList(intersectionName)
}
const getPosiList = async (intersectionName, equType) => {
  allPointList.value = await queryTrafficOpsPositionList({intersectionName: intersectionName, equType: equType || ''})
  // drawLayer(datalist)
  addCluster(allPointList.value)
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
const dianjingChart1 = (data, number) => {

  const seriesData = [];
  const seriesData2 = [];
  const seriesData3 = [
    {
      name: '总数',
      value: number,
      symbolSize: 100,
      itemStyle: {
        normal: {
          color: {
            type: 'radial',
            x: 0.5,
            y: 0.5,
            r: 0.5,
            colorStops: [
              {
                offset: 0,
                color: 'rgba(31,102,162,0)', // 0% 处的颜色
              },
              {
                offset: 1,
                color: 'rgba(31,102,192,0.6)', // 100% 处的颜色
              },
            ],
            global: false, // 缺省为 false
          }
        }
      },
      label: {
        show: true,
        formatter: ['{b|{b}}', '{a|{c}}'].join('\n'),
        fontWeight: '1000',
        fontSize: '12',
        align: 'center',
        verticalAlign: 'center',
        rich: {
          a: {
            color: '#ffffff',
            fontSize: 12,
            lineHeight: 20,
            textBorderColor: 'transparent',
            textBorderWidth: 0,
          },
          b: {
            color: '#ffffff',
            fontSize: 14,
            lineHeight: 20,
            textBorderColor: 'transparent',
            textBorderWidth: 0,
          },
        },
      },
    }]
  data.map((item, idx) => {
    seriesData.push({
      name: item.name,
      value: item.value,
    });

    seriesData2.push({
      name: item.name,
      value: item.value,
      symbolSize: 80,
      symbol: 'circle',
      itemStyle: {
        normal: {
          color: {
            type: 'radial',
            x: 0.5,
            y: 0.5,
            r: 0.5,
            colorStops: [
              {
                offset: 0,
                color: 'rgba(31,102,162,0)', // 0% 处的颜色
              },
              {
                offset: 1,
                color: 'rgba(31,102,192,0.6)', // 100% 处的颜色
              },
            ],
            global: false, // 缺省为 false
          }
        }
      },
      label: {
        show: true,
        formatter: ['{b|{b}}', '{a|{c}}'].join('\n'),
        fontWeight: '1000',
        fontSize: '12',
        align: 'center',
        verticalAlign: 'center',
        rich: {
          a: {
            color: '#ffffff',
            fontSize: 12,
            lineHeight: 20,
            textBorderColor: 'transparent',
            textBorderWidth: 0,
          },
          b: {
            color: '#ffffff',
            fontSize: 14,
            lineHeight: 20,
            textBorderColor: 'transparent',
            textBorderWidth: 0,
          },
        },
      },
    })
  })

  const option = {
    backgroundColor: '#0e2147',
    animationDurationUpdate: 1500,
    animationEasingUpdate: 'quinticInOut',
    roam: false, //鼠标缩放及平移
    focusNodeAdjacency: false, //是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点
    series: [
      {
        name: '',
        type: 'pie',
        startAngle: 0,
        hoverAnimation: false,
        radius: ['60%', '60%'],
        center: ['50%', '50%'],
        data: seriesData,
        itemStyle: {
          normal: {
            borderWidth: 5,
            borderColor: 'rgba(0,0,0,0)',
            // borderColor: '#ffffff',
            label: {
              show: false,
              color: '#ffffff',
              fontSize: 12,
              //  formatter: '{b}{d}%',
              formatter: function(params) {
                // console.log(params)
                return params.name + ' '+ params.value + '%'
              }
            },
            labelLine: {
              show: true,
              length: 30,
              lineStyle: {
                type: 'dotted'
              }
            }
          }
        }
      },
      {
        type: 'graph',
        tooltip: {},
        ribbonType: true,
        layout: 'circular',
        width: '60%',
        height: '60%',

        circular: {
          rotateLabel: false
        },
        symbolSize: 30,

        edgeSymbol: ['circle'],
        edgeSymbolSize: [8, 30],
        edgeLabel: {
          normal: {
            textStyle: {
              fontSize: 8,
              fontWeight: 'bold',
              fontFamily: '宋体'
            }
          }
        },

        itemStyle: {
          normal: {
            label: {
              rotate: true,
              show: true,
            },
            borderColor: '#7C9ECD',
            borderWidth: 0

          },
          emphasis: {
            label: {
              show: true,
            }
          }
        },

        data: seriesData2,
      },
      {
        type: 'graph',
        ribbonType: true,
        layout: 'circular',
        width: '50%',
        height: '0%',

        circular: {
          rotateLabel: false
        },
        symbolSize: 100,
        label: {
          normal: {
            show: false
          }
        },

        edgeSymbol: ['circle'],
        edgeSymbolSize: [8, 30],
        edgeLabel: {
          normal: {
            textStyle: {
              fontSize: 8,
              fontWeight: 'bold',
              fontFamily: '宋体'
            }
          }
        },

        itemStyle: {
          normal: {
            label: {
              rotate: true,
              show: false,
            },
            borderColor: '#7C9ECD',
            borderWidth: 0

          },
          emphasis: {
            label: {
              show: false,
            }
          }
        },

        data: seriesData3,
      },
    ]
  };
  if (unref(elRef)) {
    echartRef = echarts.init(unref(elRef4) as HTMLElement)
    echartRef?.setOption(unref(option))
  }
}
const dianjingChart2 = (pieData) => {
  const option = {
    legend: {
      orient: 'vertial',
      itemWidth: 10,
      itemHeight: 10,
      right: 10,
      top: '0',
      data: pieData,
      textStyle: {
        color: '#ffffff'
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b} {d}%',
    },
    series: [
      {
        title: {
          text: '',
          subtext: '',
          x: 'left',
          left: '5%',
          show: false
        },
        //你的代码
        color: [
          new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(61, 137, 247, 0.5)',
            },
            {
              offset: 1,
              color: 'rgba(61, 137, 247, 1)',
            },
          ]),
          new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(240, 123, 63, .5)',
            },
            {
              offset: 1,
              color: 'rgba(240, 123, 63, 1)',
            },
          ]),
          new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(61, 205, 124, .5)',
            },
            {
              offset: 1,
              color: 'rgba(61, 205, 124, 1)',
            },
          ]),
          new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(120, 150, 255, .5)',
            },
            {
              offset: 1,
              color: 'rgba(120, 150, 255, 1)',
            },
          ]),
          '#4cbdae',
          'rgba(61, 137, 247, 1)',
        ],
        type: 'pie',
        radius: ['0%', '70%'],
        center: ['40%', '50%'],
        avoidLabelOverlap: false,
        label: {
          show: true,
          position: 'inside',
          // formatter: '{b}\n{c}',
          formatter: '{d}%',
          color: '#fff',
          fontSise: 14
        },
        labelLine: {
          length: -30,
        },
        labelLayout: {
          hideOverlap: false,//是否隐藏重叠标签
        },
        // itemStyle: {
        //   borderColor:'#fff',
        //   borderWidth:1.2
        // },
        emphasis: {
          label:{
            color:'#fff',
            fontSise:14,
          }
        },
        data: pieData
      }
    ]
  };
  if (unref(elRef)) {
    echartRef = echarts.init(unref(elRef5) as HTMLElement)
    echartRef?.setOption(unref(option))
  }
}
const dianjingChart3 = (pieData) => {
  var defaultColor = ['#4cbdae',
    'rgba(61, 137, 247, 1)', '#7ed4ac', '#fe9c3a', '#ebd571']

  const option = {
    legend: {
      orient: 'vertial',
      itemWidth: 10,
      itemHeight: 10,
      right: 10,
      top: '0',
      data: pieData,
      textStyle: {
        color: '#ffffff'
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b} {d}%',
    },
    series: [
      {
        title: {
          text: '',
          subtext: '',
          x: 'left',
          left: '5%',
          show: false
        },
        color: [
          new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(120, 150, 255, .5)',
            },
            {
              offset: 1,
              color: 'rgba(120, 150, 255, 1)',
            },
          ]),
          new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(240, 123, 63, .5)',
            },
            {
              offset: 1,
              color: 'rgba(240, 123, 63, 1)',
            },
          ]),
          new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(61, 137, 247, 0.5)',
            },
            {
              offset: 1,
              color: 'rgba(61, 137, 247, 1)',
            },
          ]),
          new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(61, 205, 124, .5)',
            },
            {
              offset: 1,
              color: 'rgba(61, 205, 124, 1)',
            },
          ]),
          new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(120, 150, 255, .5)',
            },
            {
              offset: 1,
              color: 'rgba(120, 150, 255, 1)',
            },
          ]),
          '#4cbdae',
          'rgba(61, 137, 247, 1)',
        ],
        type: 'pie',
        radius: ['0%', '70%'],
        center: ['40%', '50%'],
        avoidLabelOverlap: false,
        label: {
          show: true,
          position: 'inside',
          // formatter: '{b}\n{c}',
          formatter: '{d}%',
          color: '#fff',
          fontSise: 14
        },
        labelLine: {
          length: -30,
        },
        labelLayout: {
          hideOverlap: false,//是否隐藏重叠标签
        },
        // itemStyle: {
        //   borderColor:'#fff',
        //   borderWidth:1.2
        // },
        emphasis: {
          label:{
            color:'#fff',
            fontSise:14,
          }
        },
        data: pieData
      }
    ]
  };
  if (unref(elRef)) {
    echartRef = echarts.init(unref(elRef6) as HTMLElement)
    echartRef?.setOption(unref(option))
  }
}

const addCluster = (datalist) => {
  if (cluster.value) {
    cluster.value.setMap(null);
    cluster.value = null
  }
  datalist.map(e => {
    e.lnglat = [e.lng, e.lat]
  })

  var gridSize = 60
  var count = datalist.length;

  var _renderClusterMarker = function (context) {
    var factor = Math.pow(context.count / count, 1 / 18);
    var div = document.createElement('div');
    var Hue = 180 - factor * 180;
    var bgColor = 'hsla(' + Hue + ',100%,40%,0.7)';
    var fontColor = 'hsla(' + Hue + ',100%,90%,1)';
    var borderColor = 'hsla(' + Hue + ',100%,40%,1)';
    var shadowColor = 'hsla(' + Hue + ',100%,90%,1)';
    div.style.backgroundColor = bgColor;
    var size = Math.round(30 + Math.pow(context.count / count, 1 / 5) * 20);
    div.style.width = div.style.height = size + 'px';
    div.style.border = 'solid 1px ' + borderColor;
    div.style.borderRadius = size / 2 + 'px';
    div.style.boxShadow = '0 0 5px ' + shadowColor;
    div.innerHTML = context.count;
    div.style.lineHeight = size + 'px';
    div.style.color = fontColor;
    div.style.fontSize = '14px';
    div.style.textAlign = 'center';
    context.marker.setOffset(new AMap.Pixel(-size / 2, -size / 2));
    context.marker.setContent(div)
  };
  var _renderMarker = function(context) {
    console.log(content, 2)
    var content = '<div style="background-color: hsla(180, 100%, 50%, 0.3); height: 18px; width: 18px; border: 1px solid hsl(180, 100%, 40%); border-radius: 12px; box-shadow: hsl(180, 100%, 50%) 0px 0px 3px;"></div>';
    var offset = new AMap.Pixel(-9, -9);
    context.marker.setContent(content)
    context.marker.setOffset(offset)
  }
  cluster.value = new AMap.MarkerCluster(map.value, datalist, {
    gridSize: gridSize, // 设置网格像素大小
    renderClusterMarker: _renderClusterMarker, // 自定义聚合点样式
    // renderMarker: _renderMarker, // 自定义非聚合点样式
    renderMarker: function (object) {
      let icon = new AMap.Icon({
        size: new AMap.Size(32, 32), // 图标尺寸
        image:`/${object.data[0].equType}.png`, // Icon的图像
        imageSize: new AMap.Size(32, 32), // 根据所设置的大小拉伸或压缩图片
      });
      object.marker.setIcon(icon);
      object.marker.on("click",async e => {
        var markersData = {}
          markersData = object.data[0]
          let infoWindow
          if(markersData.equType == 'led屏') {
            infoWindow = new AMap.InfoWindow({
              content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">IP地址：</div>
                    <div class="tankuang_content_item_info">${markersData.ipAddr || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">屏幕朝向：</div>
                    <div class="tankuang_content_item_info">${markersData.screenOrientation || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">大屏对应编号：</div>
                    <div class="tankuang_content_item_info">${markersData.dpbh || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">大小：</div>
                    <div class="tankuang_content_item_info">${markersData.size || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
              offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
            });
          } else if(markersData.equType == '信号机') {
            infoWindow = new AMap.InfoWindow({
              content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">信号控制相位：</div>
                    <div class="tankuang_content_item_info">${markersData.phase || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">信号机型号：</div>
                    <div class="tankuang_content_item_info">${markersData.equModel || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">信号机编号：</div>
                    <div class="tankuang_content_item_info">${markersData.equNumber || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">IP地址：</div>
                    <div class="tankuang_content_item_info">${markersData.ipAddr || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
              offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
            });
          } else if(markersData.equType == '电警') {
            infoWindow = new AMap.InfoWindow({
              content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">电警类型：</div>
                    <div class="tankuang_content_item_info">${markersData.djType || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">方向：</div>
                    <div class="tankuang_content_item_info">${markersData.direction || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">IP地址：</div>
                    <div class="tankuang_content_item_info">${markersData.ipAddr || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
              offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
            });
          } else if(markersData.equType == '杆件') {
            ganjianRightInfo.value = await trafficOpsMemberGet(markersData.id)
            imgIndex.value = 0
            console.log(ganjianRightInfo.value)
            // if(ganjianRightInfo.value.electricPoliceVOList && ganjianRightInfo.value.electricPoliceVOList.length) {
            //
            // }
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
            var x = await trafficOpsMemberGetPage({pageNo: 1, pageSize: 99,equNumber: markersData.equNumber})
            toggleBoxRight()
            return
            infoWindow = new AMap.InfoWindow({
              content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">设备序号：</div>
                    <div class="tankuang_content_item_info">${markersData.serialNum || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">方向：</div>
                    <div class="tankuang_content_item_info">${markersData.adress || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">设备图片：</div>
                    <div class="tankuang_content_item_info"><img src="${markersData.equImg || ''}" width="130px" height="130px"></div>
                </div>
             </div>
          </div>
        `, //内容
              offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
            });
          }else {
            infoWindow = new AMap.InfoWindow({
              content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
              offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
            });
          }
          infoWindow.open(map.value, e.target.getPosition()); //打开信息窗体
      })
    }, // 自定义非聚合点样式
  });
  return
  cluster.value.on("click",async e => {
    var markersData = {}
    console.log(e, e.clusterData)
    if(e.clusterData.length === 1) {
      markersData = e.clusterData[0]
        let infoWindow
        if(markersData.equType == 'led屏') {
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">IP地址：</div>
                    <div class="tankuang_content_item_info">${markersData.ipAddr || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">屏幕朝向：</div>
                    <div class="tankuang_content_item_info">${markersData.screenOrientation || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">大屏对应编号：</div>
                    <div class="tankuang_content_item_info">${markersData.dpbh || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">大小：</div>
                    <div class="tankuang_content_item_info">${markersData.size || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        } else if(markersData.equType == '信号机') {
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">信号控制相位：</div>
                    <div class="tankuang_content_item_info">${markersData.phase || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">信号机型号：</div>
                    <div class="tankuang_content_item_info">${markersData.equModel || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">信号机编号：</div>
                    <div class="tankuang_content_item_info">${markersData.equNumber || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">IP地址：</div>
                    <div class="tankuang_content_item_info">${markersData.ipAddr || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        } else if(markersData.equType == '电警') {
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">电警类型：</div>
                    <div class="tankuang_content_item_info">${markersData.djType || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">方向：</div>
                    <div class="tankuang_content_item_info">${markersData.direction || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">IP地址：</div>
                    <div class="tankuang_content_item_info">${markersData.ipAddr || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        } else if(markersData.equType == '杆件') {
          ganjianRightInfo.value = await trafficOpsMemberGet(markersData.id)
          imgIndex.value = 0
          console.log(ganjianRightInfo.value)
          // if(ganjianRightInfo.value.electricPoliceVOList && ganjianRightInfo.value.electricPoliceVOList.length) {
          //
          // }
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
          var x = await trafficOpsMemberGetPage({pageNo: 1, pageSize: 99,equNumber: markersData.equNumber})
          toggleBoxRight()
          return
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">设备序号：</div>
                    <div class="tankuang_content_item_info">${markersData.serialNum || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">方向：</div>
                    <div class="tankuang_content_item_info">${markersData.adress || ''}</div>
                </div>
                 <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">设备图片：</div>
                    <div class="tankuang_content_item_info"><img src="${markersData.equImg || ''}" width="130px" height="130px"></div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        }else {
          infoWindow = new AMap.InfoWindow({
            content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markersData.equType}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">路口：</div>
                    <div class="tankuang_content_item_info">${markersData.intersectionName ||''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">管辖单位：</div>
                    <div class="tankuang_content_item_info">${markersData.dominion || ''}</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">${markersData.status || ''}</div>
                </div>
             </div>
          </div>
        `, //内容
            offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
          });
        }
        infoWindow.open(map.value, e.target.getPosition()); //打开信息窗体
    }
  })
}
onMounted(() => {
  initMap(currentMapOptions.value)
  baseEquAPI()
  initChart()
  getlist()
  // initChart2()
  setTimeout(() => {
    getPosiList()
  }, 1200)
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
    background: url("../../assets/imgs/bigdata/title.png");
    background-size: 100% 100%;
    padding: 10px 0 20px 0;
    font-size: 19px;
  }
}
.left-area {
  position: absolute;
  top: 60px;
  left: 15px;
  width: 340px;
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
      color: #bfe3ff;
      font-size: 17px;
      line-height: 32px;
      padding-left: 10px;
      font-weight: bold;
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
    height: 330px;
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
    width: 25%;
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
