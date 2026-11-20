<template>
  <DialogFull class="myfullmap" width="50%" v-model="dialogVisible">
<!--    <div id="map2">-->
<!--      <img class="qp" @click="showFullMap" src="../../assets/imgs/bigdata2/qpa.png" alt=""/>-->
<!--    </div>-->
    <div id="map2">
      <img style="position: relative;width: 100%;height: 100%;border-radius: 10px;" src="../../assets/imgs/ditubig.png" alt=""/>
      <img class="ywry" src="../../assets/imgs/ywry.png" alt=""/>
      <img class="qp" @click="showFullMap" src="../../assets/imgs/bigdata2/qpa.png" alt=""/>
    </div>
  </DialogFull>
</template>
<script setup lang="ts">/** 大屏 表单 */
import {ref} from "vue";
import {queryTrafficOpsPositionList} from "@/api/bigdata";

defineOptions({ name: 'FullMap' })
const dialogVisible = ref(false) // 弹窗的是否展示
const map = ref(null)
const layer = ref(null);
const markers = ref([]);
/** 打开弹窗 */
const open = async () => {
  dialogVisible.value = true
  // setTimeout(() => {
  //   initMap()
  // }, 200)
  // setTimeout(() => {
  //   getPosiList()
  // }, 1200)
}
const getPosiList = async (intersectionName, equType) => {
  const datalist = await queryTrafficOpsPositionList({intersectionName: intersectionName, equType: equType || ''})
  drawLayer(datalist)
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
          size: [48, 48],
          anchor: 'bottom-center',
        }
      };
      let labelMarker = new AMap.LabelMarker(curData);
      markers.value.push(labelMarker);
      // 给marker绑定事件
      labelMarker.on('click', function(e){
        console.log(e, markersData)
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
        } else {
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
const showFullMap = () => {
  dialogVisible.value = false
}
const initMap = () => {
  map.value = new AMap.Map('map2', {
    resizeEnable: true,
    mapStyle: 'amap://styles/ac19c54a3821baa249afb9797ee19412',
    center: [109.95315,40.621327],
    zoom: 15
  });
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
<style>
#map2 {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 10px;
  overflow: hidden;
  .qp {
    width: 20px;
    height: 20px;
    position: absolute;
    right: 10px;
    top: 10px;
    z-index: 101;
    cursor: pointer;
  }
  .ywry {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 250px;
    height: auto;
  }
}
</style>
