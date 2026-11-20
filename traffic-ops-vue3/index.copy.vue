<template>
  <div class="bigdata">
    <img class="showicon" @click="toggleBox" src="../../assets/imgs/bigdata/show.png" alt=""/>
    <div id="map"></div>
    <div class="demo-autocomplete">
        <el-autocomplete
          size="large"
          class="inline-input"
          v-model="keyword"
          :fetch-suggestions="querySearch"
          placeholder="请输入内容"
          :trigger-on-focus="false"
          @select="handleSelect"
        />
      <el-button size="large" class="searchbtn" @click="querySearchSelect(keyword)">搜 索</el-button>
    </div>
    <div class="bigdata_title">
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
          <div class="top1-item">
            <img src="../../assets/imgs/bigdata/icon1.png" alt=""/>
            <div>信号机</div>
            <div style="color:#D63A0A">{{basedata.signalNum}}个</div>
          </div>
          <div class="top1-item">
            <img src="../../assets/imgs/bigdata/icon2.png" alt=""/>
            <div>电警</div>
            <div style="color:#E8980C">{{basedata.electricPoliceNum}}个</div>
          </div>
          <div class="top1-item">
            <img src="../../assets/imgs/bigdata/icon3.png" alt=""/>
            <div>led大屏</div>
            <div style="color:#06DA1A">{{basedata.bigLedNum}}个</div>
          </div>
          <div class="top1-item">
            <img src="../../assets/imgs/bigdata/icon4.png" alt=""/>
            <div>杆件</div>
            <div style="color:#ED5A02">{{basedata.memberNum}}个</div>
          </div>
        </div>
      </div>
      <div class="areabox middle">
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
      </div>
      <div class="areabox bottom">
        <div class="title">报修分析</div>
        <div ref="elRef" style="width: 100%;height: 300px"></div>
      </div>
    </div>
    <div id="right-area" class="area right-area animate__animated animate__bounceInRight">
      <div class="areabox top">
        <div class="title">实时监控</div>
        <img class="ssimg" src="../../assets/imgs/bigdata/img.png" alt=""/>
      </div>
      <div class="areabox middle">
        <div class="title">报修事件播报</div>
        <div class="eventlist">
          <div class="eventlist-title">
            <div>地点</div>
            <div>归属地</div>
            <div>时间</div>
          </div>
          <div class="eventlist-content-wrapper">
            <div class="eventlist-content" v-for="i in 20" :key="i">
              <div>XXXXXX红绿灯路口</div>
              <div>XXXXXX</div>
              <div>2024-11.30 17:22</div>
            </div>
          </div>
        </div>
      </div>
      <div class="areabox bottom">
        <div class="title">设备报修占比</div>
        <div ref="elRef2" style="width: 100%;height: 300px"></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import echarts from "@/plugins/echarts";
import {autocompleteOpsIntersection, baseEqu, queryTrafficOpsPositionList} from '@/api/bigdata'
let echartRef: Nullable<echarts.ECharts> = null
const elRef = ref<ElRef>()
const elRef2 = ref<ElRef>()
const map = ref(null);
const markers = ref([]);
const basedata = ref({});
const keyword = ref('');
const initMap = () => {
  if(map.value) {
    return
  }
  map.value = new AMap.Map('map', {
    resizeEnable: true,
    mapStyle: 'amap://styles/ac19c54a3821baa249afb9797ee19412',
    center: [109.95315,40.621327],
    zoom: 15
  });
}
const drawMarkers = (markersData) => {
  if(markers.value.length) {
    clearMarkers()
  }
  markersData.forEach((markerData, index) => {
    console.log(markerData.equType, index)
    let marker = new AMap.Marker({
      position: new AMap.LngLat(markerData.lng, markerData.lat),
      title: markerData.intersectionName,
      content: `<img class="map-marker" src="/${markerData.equType}.png" />`,
      // map: map.value
    });
    marker.on("click",function(e){
      console.log(e, markerData)
      let infoWindow = new AMap.InfoWindow({
        content: `
          <div class="tankuang">
             <div class="tankuang_title"><span>${markerData.intersectionName}</span></div>
             <div class="tankuang_content">
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">覆盖率：</div>
                    <div class="tankuang_content_item_info">100%</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">设备数：</div>
                    <div class="tankuang_content_item_info">${basedata.value.equNum}个</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">灯控状态：</div>
                    <div class="tankuang_content_item_info">正常</div>
                </div>
                <div class="tankuang_content_item">
                    <div class="tankuang_content_item_title">运行状态：</div>
                    <div class="tankuang_content_item_info">正常</div>
                </div>
             </div>
          </div>
        `, //内容
        offset: new AMap.Pixel(-200, 200), //信息窗体偏移量,(左右，上下)
      });
      infoWindow.open(map.value, e.target.getPosition()); //打开信息窗体
    })
    markers.value.push(marker)
    map.value.add(marker)
  })

  map.value.setFitView()
}

const clearMarkers = () => {
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
}

const baseEquAPI = async (intersectionName) => {
  basedata.value = await baseEqu({intersectionName: intersectionName})
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
const initChart2 = () => {
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
  const menuData = [
    {
      name: '事件1',
      value: 23627
    },
    {
      name: '事件2',
      value: 21602
    },
    {
      name: '事件3',
      value: 14123
    },
    {
      name: '事件4',
      value: 22822
    },
    {
      name: '事件5',
      value: 12761
    },
    {
      name: '事件6',
      value: 13261
    }
  ]
  const legendData = menuData
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
        data: menuData
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
const loadAll = () => {
  return [
    { value: '三全鲜食（北新泾店）', address: '长宁区新渔路144号' },
    {
      value: 'Hot honey 首尔炸鸡（仙霞路）',
      address: '上海市长宁区淞虹路661号',
    },
    {
      value: '新旺角茶餐厅',
      address: '上海市普陀区真北路988号创邑金沙谷6号楼113',
    },
    { value: '泷千家(天山西路店)', address: '天山西路438号' },
    {
      value: '胖仙女纸杯蛋糕（上海凌空店）',
      address: '上海市长宁区金钟路968号1幢18号楼一层商铺18-101',
    },
    { value: '贡茶', address: '上海市长宁区金钟路633号' },
    {
      value: '豪大大香鸡排超级奶爸',
      address: '上海市嘉定区曹安公路曹安路1685号',
    },
    {
      value: '茶芝兰（奶茶，手抓饼）',
      address: '上海市普陀区同普路1435号',
    },
    { value: '十二泷町', address: '上海市北翟路1444弄81号B幢-107' },
    { value: '星移浓缩咖啡', address: '上海市嘉定区新郁路817号' },
    { value: '阿姨奶茶/豪大大', address: '嘉定区曹安路1611号' },
    { value: '新麦甜四季甜品炸鸡', address: '嘉定区曹安公路2383弄55号' },
    {
      value: 'Monica摩托主题咖啡店',
      address: '嘉定区江桥镇曹安公路2409号1F，2383弄62号1F',
    },
    {
      value: '浮生若茶（凌空soho店）',
      address: '上海长宁区金钟路968号9号楼地下一层',
    },
    {
      value: 'NONO JUICE  鲜榨果汁',
      address: '上海市长宁区天山西路119号',
    },
    { value: 'CoCo都可(北新泾店）', address: '上海市长宁区仙霞西路' },
    {
      value: '快乐柠檬（神州智慧店）',
      address: '上海市长宁区天山西路567号1层R117号店铺',
    },
    {
      value: 'Merci Paul cafe',
      address: '上海市普陀区光复西路丹巴路28弄6号楼819',
    },
    {
      value: '猫山王（西郊百联店）',
      address: '上海市长宁区仙霞西路88号第一层G05-F01-1-306',
    },
    { value: '枪会山', address: '上海市普陀区棕榈路' },
    { value: '纵食', address: '元丰天山花园(东门) 双流路267号' },
    { value: '钱记', address: '上海市长宁区天山西路' },
    { value: '壹杯加', address: '上海市长宁区通协路' },
    {
      value: '唦哇嘀咖',
      address: '上海市长宁区新泾镇金钟路999号2幢（B幢）第01层第1-02A单元',
    },
    { value: '爱茜茜里(西郊百联)', address: '长宁区仙霞西路88号1305室' },
    {
      value: '爱茜茜里(近铁广场)',
      address:
        '上海市普陀区真北路818号近铁城市广场北区地下二楼N-B2-O2-C商铺',
    },
    {
      value: '鲜果榨汁（金沙江路和美广店）',
      address: '普陀区金沙江路2239号金沙和美广场B1-10-6',
    },
    {
      value: '开心丽果（缤谷店）',
      address: '上海市长宁区威宁路天山路341号',
    },
    { value: '超级鸡车（丰庄路店）', address: '上海市嘉定区丰庄路240号' },
    { value: '妙生活果园（北新泾店）', address: '长宁区新渔路144号' },
    { value: '香宜度麻辣香锅', address: '长宁区淞虹路148号' },
    {
      value: '凡仔汉堡（老真北路店）',
      address: '上海市普陀区老真北路160号',
    },
    { value: '港式小铺', address: '上海市长宁区金钟路968号15楼15-105室' },
    { value: '蜀香源麻辣香锅（剑河路店）', address: '剑河路443-1' },
    { value: '北京饺子馆', address: '长宁区北新泾街道天山西路490-1号' },
    {
      value: '饭典*新简餐（凌空SOHO店）',
      address: '上海市长宁区金钟路968号9号楼地下一层9-83室',
    },
    {
      value: '焦耳·川式快餐（金钟路店）',
      address: '上海市金钟路633号地下一层甲部',
    },
    { value: '动力鸡车', address: '长宁区仙霞西路299弄3号101B' },
    { value: '浏阳蒸菜', address: '天山西路430号' },
    { value: '四海游龙（天山西路店）', address: '上海市长宁区天山西路' },
    {
      value: '樱花食堂（凌空店）',
      address: '上海市长宁区金钟路968号15楼15-105室',
    },
    { value: '壹分米客家传统调制米粉(天山店)', address: '天山西路428号' },
    {
      value: '福荣祥烧腊（平溪路店）',
      address: '上海市长宁区协和路福泉路255弄57-73号',
    },
    {
      value: '速记黄焖鸡米饭',
      address: '上海市长宁区北新泾街道金钟路180号1层01号摊位',
    },
    { value: '红辣椒麻辣烫', address: '上海市长宁区天山西路492号' },
    {
      value: '(小杨生煎)西郊百联餐厅',
      address: '长宁区仙霞西路88号百联2楼',
    },
    { value: '阳阳麻辣烫', address: '天山西路389号' },
    {
      value: '南拳妈妈龙虾盖浇饭',
      address: '普陀区金沙江路1699号鑫乐惠美食广场A13',
    },
  ]
}
const handleSelect = (item) => {
  // querySearchSelect(item.ldmc)
}

const querySearchSelect = (intersectionName) => {
  baseEquAPI(intersectionName)
  getPosiList(intersectionName)
}
const getPosiList = async (intersectionName) => {
  const datalist = await queryTrafficOpsPositionList({intersectionName: intersectionName})
  drawMarkers(datalist.filter(e => e.lng && e.lat))
  console.log(datalist)
}

onMounted(() => {
  initMap()
  baseEquAPI()
  initChart()
  initChart2()
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
  min-height: 900px;
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
    min-height: 900px;
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
    height: 230px;
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
    height: 180px;
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
        height: 100px;
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
    flex: 1;
    text-align: center;
    img {
      width: 20px;
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
    height: 180px;
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
      width: 60%;
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
</style>
