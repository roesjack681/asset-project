<script lang="ts" setup>
import { provide, computed, watch, onMounted } from 'vue'
defineComponent({
  name: 'ChoosePoint'
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
  }

})
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const map = ref(null) // 表单的类型：create - 新增；update - 修改
const keyword = ref('')
const cuttentChoosePoint = ref({})
const marker = ref(null)
const formData = ref({})
const initMap = () => {
  //alert(map.value)
  if(map.value) {
    return
  }
  map.value = new AMap.Map('map3', {
    resizeEnable: true,
    center: [109.95315,40.621327],
    zoom: 15
  });

  if (props.isAllowResetPoint) {
    map.value.on('click', async (e) => {
      clearMarker()
      marker.value = new AMap.Marker({ //添加自定义点标记
        position: [e.lnglat.getLng(), e.lnglat.getLat()], //基点位置
        offset: new AMap.Pixel(-13, -30), //相对于基点的偏移位置
        draggable: false,  //是否可拖动
        content: `<img class="map-marker" src="//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png" />`  //自定义点标记覆盖物内容
      });
      marker.value.setMap(map.value);
      formData.value.equAddr = await getCityInfo(e.lnglat.getLng(), e.lnglat.getLat())
      formData.value.equLng = e.lnglat.getLng()
      formData.value.equLat = e.lnglat.getLat()
      emit('success', formData.value)
    });
  }

  if(props.address) {
    formData.value.equAddr = props.address
  }
  if(props.point.length && props.point[0]) {
    formData.value.equLng = props.point[0]
    formData.value.equLat = props.point[1]
    drawMarker()
  }
}
defineExpose({ initMap }) // 提供 open 方法，用于打开弹窗
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const drawMarker = () => {
  if(!formData.value.equLng || !formData.value.equLat) return
  marker.value = new AMap.Marker({ //添加自定义点标记
    position: [formData.value.equLng, formData.value.equLat], //基点位置
    offset: new AMap.Pixel(-13, -30), //相对于基点的偏移位置
    draggable: false,  //是否可拖动
    content: `<img class="map-marker" src="//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png" />`  //自定义点标记覆盖物内容
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
  formData.value.equAddr = await getCityInfo(lnglat.lng, lnglat.lat)
  formData.value.equLng = lnglat.lng
  formData.value.equLat = lnglat.lat
  emit('success', formData.value)
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
// 初始化所有主题色
onMounted(() => {})
</script>

<template>
  <div style="width:100%;height: auto;margin-top: 10px;border-radius: 10px; overflow: hidden;position: relative">
    <div style="display: flex;position: absolute;top: 10px;left: 10px;right: 10px;z-index: 101">
      <el-autocomplete
        style="flex: 1;margin-right: 8px;"
        v-model="keyword"
        :fetch-suggestions="autoInput"
        placeholder="请输入关键字"
        @select="handleSelect"
      />
      <el-button type="primary" @click="searchPoint(cuttentChoosePoint)">搜索</el-button>
    </div>
    <div id="map3"></div>
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
#map3 {
  width: 100%;
  height: 300px;
}
::v-deep .map-marker {
  width: 30px;
  height: auto;
}
</style>
