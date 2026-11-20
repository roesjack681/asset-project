<script lang="ts" setup>
import { provide, computed, watch, onMounted } from 'vue'
import start from '../../../assets/imgs/start.png'
import end from '../../../assets/imgs/end.png'
import kakou from '../../../assets/imgs/kakou.png'
defineComponent({
  name: 'ChoosePointHulankakou'
})
const props = defineProps({
  point: {
    type: Array,
    default: () => []
  },
  address: {
    type: String,
    default: ''
  },
  isAllowResetPoint: {
    type: Boolean,
    default: true
  },
  startPosition: {
    type: Array,
    default: () => [],
  },
  endPosition: {
    type: Array,
    default: () => [],
  },
  type: {
    type: String,
    default: ''
  }
})
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const map = ref(null) // 表单的类型：create - 新增；update - 修改
const keyword = ref('')
const cuttentChoosePoint = ref({})
const polyline = ref(null)
const marker = ref(null)
const startMarker = ref(null)
const endMarker = ref(null)
const chooseClickStart = ref('')
const chooseClickEnd = ref('')
const pointData = ref({})
const tab = ref('start')
const initMap = () => {
  if(map.value) {
    return
  }
  map.value = new AMap.Map('maphlkk', {
    resizeEnable: true,
    center: [109.95315,40.621327],
    zoom: 15
  });
  drawPolyline()
  if(props.point.length && props.point[0]) {
    pointData.value.lng = props.point[0]
    pointData.value.lat = props.point[1]
    drawMarker()
  }
  if (props.isAllowResetPoint) {
    if(props.type === 'detail') return;
    map.value.on('click', async (e) => {
        clearMarker()
        marker.value = new AMap.Marker({
          //添加自定义点标记
          position: [e.lnglat.getLng(), e.lnglat.getLat()], //基点位置
          offset: new AMap.Pixel(-15, -25), //相对于基点的偏移位置
          draggable: false, //是否可拖动
          content: `<img class="map-marker-img" src="${kakou}" />`, //自定义点标记覆盖物内容
        });
      marker.value.setMap(map.value);
      pointData.value.lng = e.lnglat.getLng()
      pointData.value.lat = e.lnglat.getLat()
      emit('success', pointData.value)
    });
  }
}
defineExpose({ initMap }) // 提供 open 方法，用于打开弹窗
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const drawMarker = () => {
  if(!pointData.value.lng || !pointData.value.lat) return
  marker.value = new AMap.Marker({ //添加自定义点标记
    position: [pointData.value.lng, pointData.value.lat], //基点位置
    offset: new AMap.Pixel(-13, -30), //相对于基点的偏移位置
    draggable: false,  //是否可拖动
    content: `<img class="map-marker-img" src="${kakou}" />`  //自定义点标记覆盖物内容
  });
  marker.value.setMap(map.value);
  map.value.setFitView(marker.value);
}
const autoInput = (queryString, cb) => {
  cuttentChoosePoint.value = {}
  AMap.plugin("AMap.AutoComplete", function () {
    var autoOptions = {
      city: "包头",
    };
    var autoComplete = new AMap.AutoComplete(autoOptions);
    autoComplete.search(queryString, function (status, result) {
      if (status === "complete" && result.info === "OK") {
        const list = []
        result.tips.forEach((e,index) => {
          list.push({
            value: e.name,
            lng: e.location.lng,
            lat: e.location.lat
          })
        })
        cb(list)
      } else {
        cb([])
      }
    });
  });
}
const handleSelect = (item) => {
  cuttentChoosePoint.value = item
}
const searchPoint = async (lnglat) => {
  if(!lnglat.lng) return
  clearMarker()
  marker.value = new AMap.Marker({ //添加自定义点标记
    position: [lnglat.lng, lnglat.lat], //基点位置
    offset: new AMap.Pixel(-13, -30), //相对于基点的偏移位置
    draggable: false,  //是否可拖动
    content: `<img class="map-marker" src="//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png" />`  //自定义点标记覆盖物内容
  });
  marker.value.setMap(map.value);
  map.value.setFitView(marker.value);
}
const getCityInfo = (lng, lat) => {
  return new Promise((resolve, reject) => {
    var geocoder = new AMap.Geocoder({
      radius: 1000,
      extensions: "all"
    });
    geocoder.getAddress([lng, lat], (status, result) => {
      if (status === 'complete' && result.info === 'OK') {
        if (result && result.regeocode) {
          resolve(result.regeocode.formattedAddress)
        }
      } else {
        reject('error')
      }
    })
  })
}
const clearMarker = () => {
  if (marker.value) {
    marker.value.setMap(null);
    marker.value = null;
  }
}
const clearMarkers = () => {
  if(tab.value === 'start') {
    if (startMarker.value) {
      startMarker.value.setMap(null);
      startMarker.value = null;
      chooseClickStart.value = ''
    }
  }
  if(tab.value === 'end') {
    if (endMarker.value) {
      endMarker.value.setMap(null);
      endMarker.value = null;
      chooseClickEnd.value = ''
    }
  }
}

const cleatPolyline = () => {
  polyline.value.setMap(null);
  polyline.value = null;
}

const drawPolyline = () => {
  if(!props.startPosition.length || !props.endPosition.length) return
  drawStartMarker(props.startPosition)
  drawEndMarker(props.endPosition)
  var path = []
  path.push(props.startPosition)
  path.push(props.endPosition)
  if(polyline.value) {
    cleatPolyline()
  }
  polyline.value = new AMap.Polyline({
    path: path,
    isOutline: true,
    outlineColor: '#ffeeff',
    borderWeight: 3,
    strokeColor: "#3366FF",
    strokeOpacity: 1,
    strokeWeight: 6,
    // 折线样式还支持 'dashed'
    strokeStyle: "solid",
    strokeDasharray: [15, 5],
    lineJoin: 'round',
    lineCap: 'round',
    zIndex: 50,
  })
  if (props.isAllowResetPoint) {
    polyline.value.on('click', async (e) => {
      clearMarker()
      marker.value = new AMap.Marker({
        //添加自定义点标记
        position: [e.lnglat.getLng(), e.lnglat.getLat()], //基点位置
        offset: new AMap.Pixel(-15, -25), //相对于基点的偏移位置
        draggable: false, //是否可拖动
        content: `<img class="map-marker-img" src="${kakou}" />`, //自定义点标记覆盖物内容
      });
      marker.value.setMap(map.value);
      pointData.value.lng = e.lnglat.getLng()
      pointData.value.lat = e.lnglat.getLat()
      emit('success', pointData.value)
    });
  }
  map.value.add(polyline.value);
  map.value.setFitView(polyline.value);
}
const drawStartMarker = (position) => {
  startMarker.value = new AMap.Marker({
    //添加自定义点标记
    position: position, //基点位置
    offset: new AMap.Pixel(-15, -25), //相对于基点的偏移位置
    draggable: false, //是否可拖动
    content: `<img class="map-marker-img" src="${start}" />`, //自定义点标记覆盖物内容
  });
  startMarker.value.setMap(map.value);
  // chooseClickStart.value = position.join(",");
}
const drawEndMarker = (position) => {
  endMarker.value = new AMap.Marker({
    //添加自定义点标记
    position: position, //基点位置
    offset: new AMap.Pixel(-15, -25), //相对于基点的偏移位置
    draggable: false, //是否可拖动
    content: `<img class="map-marker-img" src="${end}" />`, //自定义点标记覆盖物内容
  });
  endMarker.value.setMap(map.value);
  // chooseClickEnd.value = position.join(",");
}
// 初始化所有主题色
onMounted(() => {})
watch(chooseClickStart, (newValue, oldValue) => {
  if(chooseClickStart.value && chooseClickEnd.value) {
    var path = []
    path.push(chooseClickStart.value.split(','))
    path.push(chooseClickEnd.value.split(','))
    if(polyline.value) {
      cleatPolyline()
    }
    polyline.value = new AMap.Polyline({
      path: path,
      isOutline: true,
      outlineColor: '#ffeeff',
      borderWeight: 3,
      strokeColor: "#3366FF",
      strokeOpacity: 1,
      strokeWeight: 6,
      // 折线样式还支持 'dashed'
      strokeStyle: "solid",
      strokeDasharray: [15, 5],
      lineJoin: 'round',
      lineCap: 'round',
      zIndex: 50,
    })
    map.value.add(polyline.value);
  }
});
watch(chooseClickEnd, (newValue, oldValue) => {
  if(chooseClickStart.value && chooseClickEnd.value) {
    var path = []
    path.push(chooseClickStart.value.split(','))
    path.push(chooseClickEnd.value.split(','))
    if(polyline.value) {
      cleatPolyline()
    }
    polyline.value = new AMap.Polyline({
      path: path,
      isOutline: true,
      outlineColor: '#ffeeff',
      borderWeight: 3,
      strokeColor: "#3366FF",
      strokeOpacity: 1,
      strokeWeight: 6,
      // 折线样式还支持 'dashed'
      strokeStyle: "solid",
      strokeDasharray: [15, 5],
      lineJoin: 'round',
      lineCap: 'round',
      zIndex: 50,
    })
    map.value.add(polyline.value);
  }
});
</script>

<template>
  <div style="width:100%;height: auto;margin-top: 10px;border-radius: 10px;position: relative">
    <div style="display: flex;position: absolute;top:10px;left: 10px;right: 10px;z-index: 101">
      <el-autocomplete
        style="flex: 1;margin-right: 8px;"
        v-model="keyword"
        :fetch-suggestions="autoInput"
        placeholder="请输入关键字"
        @select="handleSelect"
      />
      <el-button type="primary" @click="searchPoint(cuttentChoosePoint)">搜索</el-button>
    </div>
    <div id="maphlkk"></div>
  </div>
</template>

<style scoped lang="scss">
.card-title {
  font-size: 14px;
  font-weight: 600;

  &::before {
    position: relative;
    top: 8px;
    left: -5px;
    display: inline-block;
    width: 3px;
    height: 14px;
    //background-color: #105cfb;
    background: var(--el-color-primary);
    border-radius: 5px;
    content: '';
    transform: translateY(-50%);
  }
}
#maphlkk {
  width: 100%;
  height: 300px;
}
::v-deep .map-marker {
  width: 30px;
  height: auto;
}
.position-tab {
  width: 200px;
  display: flex;
  border-radius: 5px;
  overflow: hidden;
  border: 1px solid #268bef;
  margin-bottom: 10px;
  span {
    flex: 1;
    padding: 2px;
    background: #ffffff;
    font-size: 12px;
    text-align: center;
    &.active {
      background: #268bef;
      color: #ffffff;
    }
  }
}
::v-deep .map-marker-img {
  width: 25px;
}
</style>
