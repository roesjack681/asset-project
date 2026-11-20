<template>
  <div class="bigdata">
    <div style="width: 100%;height: 100%;position: relative">
      <div id="map2"></div>
      <div class="bigdata-left">
        <div class="bigdata-left-item" @click="filterTypePoint(i)" v-for="(i, index) in MaintenancePointCount" :key="index">
          <div class="img-wrapper">
            <img v-if="i.type==='电子警察'" :src="icon5" alt=""/>
            <img v-if="i.type==='枪机'" :src="icon1" alt=""/>
            <img v-if="i.type==='球机'" :src="icon3" alt=""/>
            <img v-if="i.type==='卡口'" :src="icon2" alt=""/>
            <img v-if="i.type==='其他'" :src="icon4" alt=""/>
          </div>
          <div class="bigdata-left-item-name" style="color: #000">
            <div>{{i.type}}</div>
            <div>{{i.count}}</div>
          </div>
        </div>
      </div>
      <div class="bigdata-right">
        <div class="bigdata-right-title" style="color: #000">
          任务状态
          <img v-if="showAlltype===true" @click="showAlltype = false" src="./img/up.png" alt=""/>
          <img v-else @click="showAlltype = true" src="./img/down.png" alt=""/>
        </div>
        <div class="bigdata-right-items" v-if="showAlltype===true" >
          <div class="bigdata-right-item" @click="selectOneType(i)" v-for="(i, index) in maintenanceStatus" :key="index">
            <span class="yuandian" :class="'yuandian' + i.value"></span>
            {{i.label}}
            <span v-if="i.count">[{{i.count}}]</span>
            <img v-if="showType===i.value" src="./img/checked.png" alt=""/>
          </div>
        </div>
      </div>
      <div class="bigdata-bottom">
        <el-button size="small" type="warning" @click="gotoOption">就近派单</el-button>
      </div>
    </div>
    <el-dialog
      title="派单"
      v-model="dialogVisible"
      width="660px"
      :before-close="handleClose"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
      >
        <div>
          <el-form-item label="已选工单">
            <div style="display: block;width: 100%" v-for="(i,index) in inboxData" :key="index">
              <div>
                {{index + 1}}. 电警类型：{{i.djType}} 故障：{{i.faultType}}-{{i.problem}}
              </div>
            </div>
          </el-form-item>
          <el-form-item label="紧急程度" prop="reportUserId">
            <el-select v-model="formData.urgencyLevel" placeholder="请选择">
              <el-option
                @click="chooseUrgencyLevel(dict)"
                v-for="dict in urgencyLevelList"
                :key="dict.urgencyLevel"
                :label="dict.urgencyLevel"
                :value="dict.urgencyLevel"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="维修人" prop="reportUserId">
            <el-select v-model="formData.maintenanceUserId" filterable placeholder="请选择">
              <el-option
                @click="chooseUser(item)"
                v-for="item in userList"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"/>
            </el-select>
          </el-form-item>
        </div>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="batchDispatchRepairOrder">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import {onMounted, ref} from "vue";
import { OpsChedulingApi } from '@/api/traffic/maintenancescheduling'
import { ElMessage, ElMessageBox } from 'element-plus'
const message = useMessage() // 消息弹窗
const map = ref(null);
const showAlltype = ref(true);
const dialogVisible = ref(false);
const showType = ref('all');
const chooseDjType = ref('');
const list = ref([]);
const markers = ref([]);
const markersCar = ref([]);
const timer = ref([]);
const infoWindow = ref(null);
const maintenanceStatus = ref([]);
const MaintenancePointCount = ref({});
const MaintenancePointStatusCount = ref({});
const formData = ref({})
const urgencyLevelList = ref([])
const userList = ref([])
const markersObj = ref({})
const mapViewIndex =  ref(0);
const VITE_VUE_NETTYPE = ref(import.meta.env.VITE_VUE_NETTYPE)
import icon1 from './img/icon1.png'
import icon2 from './img/icon2.png'
import icon3 from './img/icon3.png'
import icon4 from './img/icon4.png'
import icon5 from './img/icon5.png'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import {OpsDeviceApi} from "@/api/public/opsdevice";
import {OpsUrgencySettingsApi} from "@/api/traffic/opsurgencysettings";
import {OpsMaintenanceApi} from "@/api/traffic/optmaintenance";
import {useUserStore} from "@/store/modules/user";
import moment from "moment/moment";
const mouseTool = ref(null);
const path = ref([]);
const polygon = ref(null);
const inboxData = ref([]);
const deptId = useUserStore().getUser.deptId
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  plateNum: undefined
})
const getList = async () => {
  try {
    list.value = await OpsChedulingApi.queryMaintenancePoint()
    list.value.forEach(e => {
      drawMarkerSingle(e)
    })
  } finally {
    //
  }
}
const queryMaintenancePointCount = async () => {
  try {
    MaintenancePointCount.value = await OpsChedulingApi.queryMaintenancePointCount()
    let allcount = 0
    MaintenancePointCount.value.forEach(e => {
      allcount = allcount + Number(e.count)
    })
    MaintenancePointCount.value.unshift({
      type: '全部',
      count: allcount
    })
  } finally {
    //
  }
}

const formatStatus = (val) => {
  var name = ''
  maintenanceStatus.value.forEach(e => {
    if(e.value === val) {
      name = e.label
    }
  })
  return name
}
const queryMaintenancePointStatusCount = async (djType) => {
  maintenanceStatus.value = getStrDictOptions(DICT_TYPE.MAINTENANCE_STATUS).filter(item => !["5", "7", "9"].includes(item.value))
  try {
    let allcount = 0
    MaintenancePointStatusCount.value = await OpsChedulingApi.queryMaintenancePointStatusCount(djType)
    maintenanceStatus.value.map(e => {
      MaintenancePointStatusCount.value.forEach(x => {
        if(e.value === x.type) {
          x.count = x.count - 0
          e.count = x.count
          allcount = allcount + x.count
        }
      })
      return e
    })
    maintenanceStatus.value.unshift({
      label: '全部',
      value: 'all',
      count: allcount
    })
  } finally {
    //
  }
}
const selectOneType = (item) => {
  showType.value = item.value
  if(item.value === 'all') {
    clearMarkers()
    list.value.forEach(e => {
      drawMarkerSingle(e)
    })
  } else {
    var filterData = []
    if(chooseDjType.value) {
      filterData = list.value.filter(e => e.status === item.value && e.djType===chooseDjType.value)
    } else {
      filterData = list.value.filter(e => e.status === item.value)
    }
    clearMarkers()
    filterData.forEach(e => {
      drawMarkerSingle(e)
    })
  }
  map.value.setFitView(markers.value)
}
const filterTypePoint = (item) => {
  showType.value = ''
  chooseDjType.value = item.type
  if(item.type === '全部') {
    chooseDjType.value = ''
    queryMaintenancePointStatusCount('')
    clearMarkers()
    list.value.forEach(e => {
      drawMarkerSingle(e)
    })
  } else if(item.type === '其他') {
    queryMaintenancePointStatusCount(item.type)
    const filterData = list.value.filter(e => (e.djType !== '电子警察' && e.djType !== '球机'&& e.djType !== '枪机'&& e.djType !== '卡口') || e.djType === item.type)
    clearMarkers()
    console.log(filterData, 0)
    filterData.forEach(e => {
      drawMarkerSingle(e)
    })
  } else {
    queryMaintenancePointStatusCount(item.type)
    const filterData = list.value.filter(e => e.djType === item.type)
    clearMarkers()
    filterData.forEach(e => {
      drawMarkerSingle(e)
    })
  }
  map.value.setFitView(markers.value)
}
const clearMarkers = () => {
  markers.value.forEach(marker => {
    map.value.remove(marker);
  });
  markers.value = []; // 清空数组
}
const initMap = () => {
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
    map.value = new AMap.Map('map2', {
      resizeEnable: true,
      center: [109.95315,40.621327],
      zoom: 14,
    })
  }
  AMap.plugin('AMap.MoveAnimation', function () {})
}
const drawMarkerSingle = (markersData) => {
  let content = ''
  let name = markersData.faultType ? markersData.faultType + '_' + markersData.problem : markersData.problem
  if(markersData.djType==='电子警察') {
    content = `<div class="map-img-wrapper yuandian ${'yuandian' + markersData.status}">
            <img src="${icon5}" alt=""/>
            <div class="problem ${'yuandian' + markersData.status}" title="${name}">${name}</div>
    </div>`
  } else if(markersData.djType==='枪机') {
    content = `<div class="map-img-wrapper yuandian ${'yuandian' + markersData.status}">
            <img src="${icon1}" alt=""/>
            <div class="problem ${'yuandian' + markersData.status}" title="${name}">${name}</div>
</div>`
  } else if (markersData.djType==='球机') {
    content = `<div class="map-img-wrapper yuandian ${'yuandian' + markersData.status}">
            <img src="${icon3}" alt=""/>
            <div class="problem ${'yuandian' + markersData.status}" title="${name}">${name}</div>
</div>`
  }else if (markersData.djType==='卡口') {
    content = `<div class="map-img-wrapper yuandian ${'yuandian' + markersData.status}">
            <img src="${icon2}" alt=""/>
            <div class="problem ${'yuandian' + markersData.status}" title="${name}">${name}</div>
</div>`
  } else {
    content = `<div class="map-img-wrapper yuandian ${'yuandian' + markersData.status}">
            <img v-else src="${icon4}" alt=""/>
            <div class="problem ${'yuandian' + markersData.status}" title="${name}">${name}</div>
</div>`
  }
  if(markersData.lat && markersData.lng) {
    let curData = {
      map: map.value,
      position: new AMap.LngLat(markersData.lng, markersData.lat),
      anchor:'bottom-center',
      autoRotation: true,
      content: content,
    };
    let newMarker = new AMap.Marker(curData);
    markers.value.push(newMarker);

    newMarker.on('click', function(e) {
      inboxData.value = [markersData]
      infoWindow.value = new AMap.InfoWindow({
        content: `<div style='min-width: 150px;max-width: 300px;font-size: 12px;padding: 5px;color: #000000;background: #ffffff'>
                <div>电警类型：${markersData.djType}</div>
                <div>存在问题：${markersData.problem}</div>
                <div>故障类型：${markersData.faultType==null?'':markersData.faultType}</div>
                <div>设备IP：${markersData.ipAddr}</div>
                <div>状态：${formatStatus(markersData.status)}</div>
                <div>维修人员：${markersData.maintenancePersonnel==null?'':markersData.maintenancePersonnel}</div>
                ${markersData.status==1?'<div style="text-align: right"><button id="mapareaPaidan">派单</button></div>':''}
          </div>`,  // 信息窗口内容
        offset: new AMap.Pixel(-10, -20)
      });
      infoWindow.value.open(map.value, [e.lnglat.lng, e.lnglat.lat]); // 在marker位置打开信息窗口
      if(markersData.status == 1) {
        document.getElementById('mapareaPaidan').addEventListener('click', function() {
          dialogVisible.value = true
        });
      }
      // inboxData.value = [markersData]
      // dialogVisible.value = true
    });
  }
}
const getListCar = async () => {
  try {
    const data = await OpsDeviceApi.getOpsDeviceLocationPage(queryParams)
    data.list.forEach(e => {
      if(!markersObj.value[e.plateNum]) {
        markersObj.value[e.plateNum] = {
          position: []
        }
        markersObj.value[e.plateNum].position.push([e.longitude, e.latitude])
        drawMarkerCarSingle(e)
      } else if(markersObj.value[e.plateNum].position.length === 1) {
        markersObj.value[e.plateNum].position.push([e.longitude, e.latitude])
        moveOneCar(markersObj.value[e.plateNum])
      } else {
        markersObj.value[e.plateNum].position.splice(0, 1)
        markersObj.value[e.plateNum].position.push([e.longitude, e.latitude])
        moveOneCar(markersObj.value[e.plateNum])
      }
    })
    if(markersCar.value.length < 1000 && mapViewIndex.value < 1) {
      mapViewIndex.value = 1
      map.value.setFitView(markersCar.value)
    }
  } finally {
    //
  }
}
const drawMarkerCarSingle = (markersData) => {
  const  content = `<div class="map-img-wrapper-car">
            <img src="/car.png" alt="">
            <div class="problem">${markersData.plateNum}</div>
</div>`
  if(markersData.latitude && markersData.longitude) {
    let curData = {
      map: map.value,
      position: new AMap.LngLat(markersData.longitude, markersData.latitude),
      angle: markersData.direction,
      anchor:'bottom-center',
      autoRotation: true,
      content: content,
      // icon: new AMap.Icon({
      //   image: `/car.png`,
      //   imageSize: new AMap.Size(20, 35),
      //   size: new AMap.Size(20, 35),
      //   imageOffset: new AMap.Pixel(0,0)
      // })
    };
    let newMarker = new AMap.Marker(curData);
    markersCar.value.push(newMarker);
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
      infoWindow.value.open(map.value, [e.lnglat.lng, e.lnglat.lat]); // 在marker位置打开信息窗口
    });
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
const gotoOption = () => {
  ElMessageBox.confirm(
    '选择行政区域，鼠标左键开始绘制，鼠标右键结束绘图。在该围栏内的工单可以进行批量派单',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
      drawPolygon()
    })
    .catch(() => {
      //
    })
}
const drawPolygon = () => {
  mouseTool.value = new AMap.MouseTool(map.value)
  mouseTool.value.polygon({
    strokeColor: "#FF33FF",
    strokeOpacity: 1,
    strokeWeight: 6,
    strokeOpacity: 0.2,
    fillColor: '#1791fc',
    fillOpacity: 0.4,
    strokeStyle: "solid"
  })
  mouseTool.value.on('draw', function(event) {
    // event.obj 为绘制出来的覆盖物对象
    polygon.value = event.obj
    filterInboxPoint()
    // console.log(event.obj.contains([109.912274,40.651548]))
    // console.log(event.obj.getPath().map((e) => e.toString()))
  })
}
const filterInboxPoint = () => {
  inboxData.value = []
  if(polygon.value) {
    list.value.forEach(e => {
      if(e.status == 1 && polygon.value.contains([e.lng, e.lat])) {
        inboxData.value.push(e)
      }
    })
  }
  if(inboxData.value.length === 0) {
    ElMessageBox.alert('您绘制的区域内没有待派单工单', '提示', {
      confirmButtonText: '关闭',
      callback: (action) => {
        clearPolygon()
      },
    });
  } else {
    ElMessageBox.confirm(`您绘制的区域内有${inboxData.value.length}条待派单工单, 是否批量派单?`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })
      .then(() => {
        dialogVisible.value = true
      })
      .catch(() => {
        clearPolygon()
      })
  }
}
const clearPolygon = () => {
  if(polygon.value) {
    map.value.remove(polygon.value);
    polygon.value = null
    mouseTool.value.close()
  }
}
const batchDispatchRepairOrder = async () => {
  if(!formData.value.maintenancePersonnel) return message.error('请选择维修人员')
  if(!formData.value.urgencyLevel) return message.error('请选择紧急程度')
  formData.value.maintenanceIdList = []
  inboxData.value.forEach(e => {
    formData.value.maintenanceIdList.push(e.id)
  })
  await OpsChedulingApi.batchDispatchRepairOrder(formData.value)
  message.success('派单成功')
  clearPolygon()
  dialogVisible.value = false
  formData.value = {}
  getList()
}
const handleClose = () => {
  clearPolygon()
  dialogVisible.value = false
  formData.value = {}
}
const getUrgencyLevelList = async () => {
  const data = await OpsUrgencySettingsApi.getOpsUrgencySettingsPage({pageNo: 1, pageSize: 99, affiliatedUnit: '1'})
  urgencyLevelList.value = data.list
  const userListData = await OpsMaintenanceApi.queryAllUserInfoList(deptId)
  userList.value = userListData
}
const chooseUrgencyLevel = (val) => {
  const deadline = val.deadline * 60 * 60 * 1000
  formData.value.deadline = Date.now() + deadline
  // console.log(moment(formData.value.deadline).format('YYYY-MM-DD HH:mm:ss'))
}
const chooseUser = (item) => {
  formData.value.maintenanceUserId = item.id
  formData.value.maintenancePersonnel = item.nickname
  formData.value.maintenancePhone = item.mobile
}
onMounted(() => {
  getUrgencyLevelList()
  getList()
  queryMaintenancePointCount()
  queryMaintenancePointStatusCount('')
  initMap()
  getListCar()
  timer.value = setInterval(() => {
    getListCar()
  }, 10000)
});
</script>
<style scoped lang="scss">
.bigdata {
  position: relative;
  width: 100%;
  height: calc(100vh);
  overflow: hidden;
  box-sizing: border-box;
  #map2 {
    width: 100%;
    height: 100%;
  }
  .bigdata-left {
    position: absolute;
    top: 10px;
    left: 10px;
    padding: 10px;
    background: #ffffff;
    border-radius: 5px;
    display: flex;
    .bigdata-left-item {
      padding: 10px;
      display: flex;
      align-items: center;
      font-size: 13px;
      margin-right: 10px;
      .img-wrapper {
        position: relative;
        background: #409EFF;
        width: 24px;
        height: 24px;
        margin-right: 10px;
        border-radius: 3px;
      }
      img {
        position: absolute;
        top: 50%;
        left: 50%;
        width: 16px;
        height: 16px;
        transform: translate(-50%, -50%);
      }
      div {
        text-align: center;
      }
    }
  }
  .bigdata-right {
    position: absolute;
    right: 10px;
    top: 10px;
    font-size: 13px;
    min-width: 150px;
    .bigdata-right-title {
      background: #ffffff;
      padding: 10px;
      margin-bottom: 4px;
      img {
        float: right;
        width: 15px;
        cursor: pointer;
      }
    }
    .bigdata-right-items {
      background: #ffffff;
      .bigdata-right-item {
        padding: 6px 10px;
        cursor: pointer;
        color: #000;
        img {
          float: right;
          width: 16px;
        }
        .yuandian {
          display: inline-block;
          width: 7px;
          height: 7px;
          background: #7300ff;
          border-radius: 50%;
          margin-right: 6px;
        }
      }
    }
  }
  .bigdata-bottom {
    background: #ffffff;
    padding: 10px;
    border-radius: 6px;
    box-shadow: 1px 1px 10px #dddddd;
    position: absolute;
    right: 10px;
    bottom: 10px;
  }
}
::v-deep .map-img-wrapper {
  border-radius: 50%;
  position: relative;
  width: 30px;
  height: 30px;
  border: 3px solid #ffffff;
  &.yuandian {
    background: #7300ff;
  }
  .problem {
    display: inline-block;
    position: absolute;
    min-width: 50px;
    max-width: 150px;
    font-size: 10px;
    padding: 2px;
    top: 27px;
    left: 50%;
    transform: translate(-50%, 0%);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis; /* 超出部分显示省略号 */
    color: #ffffff;
    border: 3px solid #ffffff;
    border-radius: 6px;
    background: #7300ff;
    //&.color1 {
    //  background: #409EFF;
    //}
    //&.color6 {
    //  background: #6D4C41;
    //}
    //&.color4 {
    //  background: #E6A23C;
    //}
    //&.color8 {
    //  background: #CE93D8;
    //}
    //&.color5 {
    //  background: #4CAF50;
    //}
    //&.color7 {
    //  background: #FFEB3B;
    //}
    //&.color9 {
    //  background: #FF7043;
    //}
    //&.color10 {
    //  background: #a85eff;
    //}
  }

  img {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 14px;
    height: 14px;
    transform: translate(-50%, -50%);
  }
}
::v-deep .map-img-wrapper-car {
  position: relative;
  img {
    width: 20px!important;
    height: auto!important;
  }
  .problem {
    width: 70px;
    font-size: 12px;
    position: absolute;
    top: -20px;
    left: 50%;
    transform: translate(-50%, 0%);
    font-weight: bold;
    color: #1027c2;
  }
}
::v-deep #mapareaPaidan {
  width: 100%;
  background: #0e9eed;
  margin-top: 10px;
  border: none;
  color: #ffffff;
  border-radius: 4px;
  padding: 3px 0;
  cursor: pointer;
}

::v-deep .yuandianall {
  background: #909399;
}
::v-deep .yuandian1 {
  background: #409EFF!important;
}
::v-deep .yuandian6 {
  background: #6D4C41!important;
}
::v-deep .yuandian4 {
  background: #E6A23C!important;
}
::v-deep .yuandian8 {
  background: #CE93D8!important;
}
::v-deep .yuandian5 {
  background: #4CAF50!important;
}
::v-deep .yuandian7 {
  background: #FFEB3B!important;
}
::v-deep.yuandian9 {
  background: #FF7043!important;
}
::v-deep .yuandian10 {
  background: red !important;
}
</style>
