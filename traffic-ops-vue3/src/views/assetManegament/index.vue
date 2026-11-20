<template>
  <div class="bigdata">
    <div class="bigdata-title">
      资产运维大数据展示
    </div>
    <div class="bigdata-content">
      <div class="bigdata-content-left">
        <div class="bigdata-content-left-top">
          <div class="jcss-title">基础设施</div>
          <div class="jcss-content">
            <div class="jcss-content-items" @click="showDialogVisible2('信号机')">
              <img src="../../assets/imgs/bigdata2/xhd.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">信号机</div>
                <div>{{basedata.signalNum}}个</div>
              </div>
              <div style="color: #3176FF;font-size: 12px">
                <div>故障率：{{basedata.signalGNumPercent}}%</div>
                <div style="color:#FF0000">故障数：{{basedata.signalGNum}}</div>
<!--                <div>较昨日：0.1</div>-->
              </div>
            </div>
            <div class="jcss-content-items" @click="showDialogVisible2('标线')">
              <img src="../../assets/imgs/bigdata2/bx.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">标线</div>
                <div>{{basedata.marklineNum}}个</div>
              </div>
              <div style="color: #3176FF;font-size: 12px">
                <div>故障率：{{basedata.marklineGNumPercent}}%</div>
                <div style="color:#FF0000">故障数：{{basedata.marklineGNum}}</div>
<!--                <div>较昨日：0.1</div>-->
              </div>
            </div>
            <div class="jcss-content-items" @click="showDialogVisible2('标志牌')">
              <img style="width: 44px" src="../../assets/imgs/bigdata2/bzp.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">标志牌</div>
                <div>{{basedata.boardNum}}个</div>
              </div>
              <div style="color: #3176FF;font-size: 12px">
                <div>故障率：{{basedata.boardGNumPercent}}%</div>
                <div style="color:#FF0000">故障数：{{basedata.boardGNum}}</div>
<!--                <div>较昨日：0.1</div>-->
              </div>
            </div>
            <div class="jcss-content-items" @click="showDialogVisible2('信号灯')">
              <img src="../../assets/imgs/bigdata2/xhj.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">信号灯</div>
                <div>{{basedata.signalLightNum}}个</div>
              </div>
              <div style="color: #3176FF;font-size: 12px">
                <div>故障率：{{basedata.signalLightGNumPercent}}%</div>
                <div style="color:#FF0000">故障数：{{basedata.signalLightGNum}}</div>
<!--                <div>较昨日：0.1</div>-->
              </div>
            </div>
            <div class="jcss-content-items" @click="showDialogVisible2('雷达')">
              <img src="../../assets/imgs/bigdata2/ld.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">雷达</div>
                <div>{{basedata.radarNum}}个</div>
              </div>
              <div style="color: #3176FF;font-size: 12px">
                <div>故障率：{{basedata.radarGNumPercent}}%</div>
                <div style="color:#FF0000">故障数：{{basedata.radarGNum}}</div>
<!--                <div>较昨日：0.1</div>-->
              </div>
            </div>
            <div class="jcss-content-items" @click="showDialogVisible2('电警')">
              <img src="../../assets/imgs/bigdata2/dj.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">电警</div>
                <div>{{basedata.electricPoliceNum}}个</div>
              </div>
              <div style="color: #3176FF;font-size: 12px">
                <div>故障率：{{basedata.electricPoliceGNumPercent}}%</div>
                <div style="color:#FF0000">故障数：{{basedata.electricPoliceGNum}}</div>
<!--                <div>较昨日：0.1</div>-->
              </div>
            </div>
            <div class="jcss-content-items" @click="showDialogVisible2('杆件')">
              <img src="../../assets/imgs/bigdata2/gj.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">杆件</div>
                <div>{{basedata.memberZNum}}个</div>
              </div>
              <div style="color: #3176FF;font-size: 12px">
                <div>故障率：{{basedata.memberGNumPercent}}%</div>
                <div style="color:#FF0000">故障数：{{basedata.memberGNum}}</div>
<!--                <div>较昨日：0.1</div>-->
              </div>
            </div>
            <div class="jcss-content-items" @click="showDialogVisible2('led屏')">
              <img src="../../assets/imgs/bigdata2/dp.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">大屏</div>
                <div>{{basedata.bigLedNum}}个</div>
              </div>
              <div style="color: #3176FF;font-size: 12px">
                <div>故障率：{{basedata.bigLedGNumPercent}}%</div>
                <div style="color:#FF0000">故障数：{{basedata.bigLedGNum}}</div>
<!--                <div>较昨日：0.1</div>-->
              </div>
            </div>
            <div class="jcss-content-items" @click="showDialogVisible2('护栏')">
              <img src="../../assets/imgs/bigdata2/hl.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">护栏</div>
                <div>{{basedata.guardrailNum}}个</div>
              </div>
              <div style="color: #3176FF;font-size: 12px">
                <div>故障率：{{basedata.guardrailGNumPercent}}%</div>
                <div style="color:#FF0000">故障数：{{basedata.guardrailGNum}}</div>
<!--                <div>较昨日：0.1</div>-->
              </div>
            </div>
          </div>
        </div>
        <div class="bigdata-content-left-bottom">
          <div class="bigdata-content-left-bottom-left">
            <div style="height: 300px">
              <div class="bt-title">待维修设备</div>
              <div class="search-tab">
                <div @click="getMaintenancePagedp(1)">待派单（{{wxDetail.dpdCount}}）</div>
                <div @click="getMaintenancePagedp(6)" style="color:#FF907A;border-color:#FF907A">待接单（{{wxDetail.djdCount}}）</div>
              </div>
              <div class="bt-table">
                <div class="bt-table-thead">
                  <div>上报人</div>
                  <div style="max-width: 100px">存在问题</div>
                  <div>类型</div>
                  <div>上报时间</div>
                  <div>状态</div>
                </div>
                <div class="bt-table-tbody" @mouseenter="rollStop()" @mouseleave="rollStart(100)" id="review_box" style="height: 180px;overflow: hidden">
                  <div id="comment1">
                    <div class="bt-table-tbody-item2" v-for="i in wxDetail.maintenanceList" :key="i.id">
                    <div style="font-size: 12px;">{{i.reportPersonnel}}</div>
                    <div style="font-size: 12px;max-width: 100px" :title="i.problem" class="ellipsis">
                        {{i.problem}}
                    </div>
                    <div style="font-size: 12px"><span v-if="i.faultType" style="padding: 4px;background: #3176FF;color: #ffffff;border-radius: 4px;font-size: 11px">{{i.faultType}}</span></div>
                    <div style="font-size: 8px">{{formatDate(i.reportTime)}}</div>
                    <div style="font-size: 12px">
                        <img style="width: 18px;height: 18px" v-if="i.status==1" src="../../assets/imgs/bigdata2/dpd.png" alt=""/>
                        <img style="width: 18px;height: 18px" v-if="i.status==6" src="../../assets/imgs/bigdata2/djd.png" alt=""/>
                        <img style="width: 18px;height: 18px" v-if="i.status==4" src="../../assets/imgs/bigdata2/dwx.png" alt=""/>
                    </div>
                  </div>
                  </div>
                  <div id="comment2"></div>
                </div>
              </div>
            </div>
            <div style="height: 300px">
              <div class="bt-title">维修类型</div>
              <div ref="elRef2" style="width: 100%;height: 250px"></div>
            </div>
          </div>
          <div class="bigdata-content-left-bottom-right">
            <div id="map">
              <img style="position: relative;width: 100%;height: 100%;border-radius: 10px;" src="../../assets/imgs/ditu.png" alt=""/>
              <img class="ywry" src="../../assets/imgs/ywry.png" alt=""/>
              <img class="qp" @click="showFullMap" src="../../assets/imgs/bigdata2/qp.png" alt=""/>
            </div>

            <!--<div id="map">-->
<!--              <img class="qp" @click="showFullMap" src="../../assets/imgs/bigdata2/qp.png" alt=""/>-->
<!--            </div>-->
          </div>
        </div>
      </div>
      <div class="bigdata-content-right">
        <div style="height: 220px">
          <div class="bt-title">运维人员排行榜
            <span class="ckbd" @click="dialogVisible=true">查看榜单</span>
          </div>
          <div class="bt-table">
            <div class="bt-table-thead">
              <div>排名</div>
              <div>名称</div>
              <div>完成工单总数</div>
              <div>完成率</div>
            </div>
            <div class="bt-table-tbody">
              <div class="bt-table-tbody-item" v-for="(i, index) in RankingList" :key="index" v-show="index < 4">
                <div class="xh"><span :class="'bg' + (index+1)"></span></div>
                <div>{{i.userName}}</div>
                <div>{{i.doneOrderCnt}}</div>
                <div>{{i.finishRate}}</div>
              </div>
            </div>
          </div>
        </div>
        <div style="height: 300px">
          <div class="bt-title">当前运维人员状态</div>
          <div class="bt-table">
            <div class="bt-table-thead">
              <div>姓名</div>
              <div>状态</div>
              <div>当前进度</div>
              <div>操作</div>
            </div>
            <div class="bt-table-tbody" @mouseenter="rollStop1()" @mouseleave="rollStart1(100)" id="review_box1" style="height: 220px;overflow: hidden">
              <div id="comment11">
                <div class="bt-table-tbody-item2" v-for="i in RankingStatusList" :key="i.id">
                  <div>{{i.maintenancePersonnel}}</div>
                  <div>
                    <span class="dpd" v-if="i.status==1">待派单</span>
                    <span class="djd" v-if="i.status==6">待接单</span>
                    <span class="wxz" v-if="i.status==4">维修中</span>
                    <span class="ywc" v-if="i.status==5">已完成</span>
                  </div>
                  <div><span style="background: #3176FF;color: #ffffff;border-radius: 4px;padding: 5px;font-size: 10px">已到达维修地点</span></div>
                  <div style="color: #FF0000">查看位置</div>
                </div>
              </div>
              <div id="comment21"></div>
            </div>
          </div>
        </div>
        <div style="height: 300px">
          <div class="bt-title">工作量统计</div>
          <div id="container" style="width: 100%;height: 250px"></div>
        </div>
      </div>
    </div>
    <!-- 表单弹窗：添加/修改 -->
    <FullMap ref="formRef"/>
    <DialogNotitle width="50%" v-model="dialogVisible">
      <div class="DialogNotitle-content">
        <div class="DialogNotitle-title">
          运维人员排行榜
          <span @click="dialogVisible = false">×</span>
        </div>
        <div class="bt-table">
          <div class="bt-table-thead">
            <div>排名</div>
            <div>名称</div>
            <div>完成工单总数</div>
            <div>完成率</div>
          </div>
          <div class="bt-table-tbody">
            <div class="bt-table-tbody-item" v-for="(i, index) in RankingList" :key="index">
              <div class="xh">
                <span v-if="index < 3" :class="'bg' + (index+1)"></span>
                <span v-else>{{index+1}}</span>
              </div>
              <div>{{i.userName}}</div>
              <div>{{i.doneOrderCnt}}</div>
              <div>{{i.finishRate}}</div>
            </div>
          </div>
        </div>
      </div>
    </DialogNotitle>
    <DialogNotitle width="70%" v-model="dialogVisible2">
      <div class="DialogNotitle-content">
        <div class="DialogNotitle-title">
          {{currentChooseEquTYpe}}维修总量
          <span @click="dialogVisible2 = false">×</span>
        </div>
        <div ref="elRef3" style="width: 100%;height: 500px"></div>
      </div>
    </DialogNotitle>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import echarts from "@/plugins/echarts";
import 'echarts/lib/component/dataZoom';
import FullMap from './FullMap.vue'
import { formatDate } from '@/utils/formatTime'
import {autocompleteOpsIntersection, baseEqu, queryTrafficOpsPositionList, analysisMaintenanceRanking, maintenanceWorkStatus, maintenancePagedp,
  getWxType, analysisNearlyThreeYearsMaintenanceCnt} from '@/api/bigdata'
import OpsBigledForm from "@/views/traffic/opsbigled/OpsBigledForm.vue";
import {OpsUrgencySettingsApi} from "@/api/traffic/opsurgencysettings";
let echartRef: Nullable<echarts.ECharts> = null
const elRef = ref<ElRef>()
const elRef1 = ref<ElRef>()
const elRef2 = ref<ElRef>()
const elRef3 = ref<ElRef>()
const map = ref(null);
const isQp = ref(false);
const layer = ref(null);
const markers = ref([]);
const basedata = ref({});
const RankingList = ref([]);
const getWxTypeList = ref([]);
const RankingStatusList = ref([]);
const RankingStatusListTotal = ref([]);
const wxDetail = ref({
  maintenanceList: [],
  dpdCount: 0,
  djdCount: 0,
});
const MaintenancePagedpTotal = ref([]);
const keyword = ref('');
const dialogVisible = ref(false);
const dialogVisible2 = ref(false);
const currentChooseEquTYpe = ref('');
const timer = ref(null);
const timer1 = ref(null);
/** 打开地图全屏 */
const formRef = ref()
const queryParams = reactive({
  pageNo: 1,
  pageSize: 100,
})
const queryParams2 = reactive({
  pageNo: 1,
  pageSize: 100,
})
const showFullMap = () => {
  isQp.value = !isQp.value
  formRef.value.open()
}
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
    ['rgba(104,152,203,1)', 'rgba(109,117,208,1)'],
  ]
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
        align: 'left',
        itemGap: 20,
        type: 'scroll',
        orient: 'vertical',
        right: 10,
        top: 20,
        bottom: 20,
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
        center: ['40%', '55%'],
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
const showDialogVisible2 = async (equType) => {
  currentChooseEquTYpe.value = equType
  const data = await analysisNearlyThreeYearsMaintenanceCnt(equType)
  const lenglist = []
  for (let i in data){
    lenglist.push({name: i, list: data[i]})
  }

  dialogVisible2.value = true
  setTimeout(() => {
    initChart3(lenglist)
  }, 1000)
}
const initChart3 = (lenglist) => {
  if(lenglist.length !== 3) return
  var option = {
    backgroundColor: '#0630A5',
    legend: {
      top: '10%',
      right: '5%',
      icon: 'circle',
      itemWidth: 8,
      itemHeight: 8,
      itemGap: 12,
      textStyle: {
        color: '#FFFFFF'
      }
    },
    tooltip: {
      trigger: 'axis',
      backgroundColor: '#ffffff',
      borderWidth: 0,
      padding: [5, 10],
      textStyle: {
        color: '#000000',
      },
    },
    grid: {
      //   top: 0,
      //   left: 0,
      //   right: 0,
      //   bottom: 0,
      //   containLabel: false
    },
    toolbox: {
      show: false,
    },
    xAxis: {
      type: 'category',
      boundaryGap: false, //坐标轴两边留白
      data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月', '11月', '12月'],
      axisLabel: {
        textStyle: {
          color: '#D6E1FF',
        }
      },
      axisLine: {
        lineStyle: {
          color: '#9EA3B4',
          opacity: 0.3,
        }
      },
      axisTick: {
        show: false,
      },
    },
    yAxis: [
      {
        type: 'value',
        splitNumber: 5,

        splitLine: {
          lineStyle: {
            color: '#9EA3B4',
            opacity: 0.3,
          },
        },
        axisLabel: {
          textStyle: {
            color: '#D6E1FF',
          },
        },
      },
    ],
    series: [
      {
        name: lenglist[0].name +'年',
        type: 'line',
        symbol: 'none',
        smooth: true,
        itemStyle: {
          normal: {
            color: '#C66300',
            lineStyle: {
              color: '#C66300',
              width: 2,
            },
            areaStyle: {
              //区域填充样式
              color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                {
                  offset: 0,
                  color: 'rgba(198,99,0,0.1)',
                },
                {
                  offset: 1,
                  color: 'rgba(198,99,0,0.8)',
                },
              ]),
            },
          },
        },
        toolbox: {
          show: false,
        },
        data: lenglist[0].list,
      },
      {
        name: lenglist[1].name +'年',
        type: 'line',
        symbol: 'none',
        smooth: true,
        itemStyle: {
          normal: {
            color: '#3176FF',
            lineStyle: {
              color: '#3176FF',
              width: 2,
            },
            areaStyle: {
              //区域填充样式
              color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                {
                  offset: 0,
                  color: 'rgba(49,118,255,0.1)',
                },
                {
                  offset: 1,
                  color: 'rgba(49,118,255,0.8)',
                },
              ]),
            },
          },
        },
        toolbox: {
          show: false,
        },
        data: lenglist[1].list,
      },
      {
        name: lenglist[2].name +'年',
        type: 'line',
        symbol: 'none',
        smooth: true,
        itemStyle: {
          normal: {
            color: '#23CDF1',
            lineStyle: {
              color: '#23CDF1',
              width: 2,
            },
            areaStyle: {
              //区域填充样式
              color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                {
                  offset: 0,
                  color: 'rgba(35, 205, 241, 0.1)',
                },
                {
                  offset: 1,
                  color: 'rgba(35, 205, 241, 0.5)',
                },
              ]),
            },
          },
        },
        toolbox: {
          show: false,
        },
        data: lenglist[1].list
      },
    ],
  };
  if (unref(elRef3)) {
    echartRef = echarts.init(unref(elRef3) as HTMLElement)
    echartRef?.setOption(unref(option))
  }
}

const initChart1 = (data) => {
  console.log(data)
  const dataList = {
    xdataName: [],
    contractnum: []
  }
  data.forEach(e => {
    dataList.xdataName.push(e.userName)
    dataList.contractnum.push(e.doneOrderCnt)
  })
  var dom = document.getElementById("container");
  var option = null;
  var myChart = echarts.init(dom);
  // let dataArray = {
  //   xdataName: ['张三', '张大锤', '王五', '王大锤', '李佳琦', '王萌萌', '赵丽颖', '杨幂', '花花', '郭郭', '大锤', '李四'],
  //   contractnum: [9999, 8888, 7777, 6666, 5555, 4444, 3333, 6333, 5333, 5333, 6333, 5333]
  // }
  let dataArray = dataList
  // tooltip
  let tooltip = {
    trigger: 'axis',
    textStyle: {
      fontSize: 18
    },
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#283b56'
      }
    },
    formatter: v => {
      let [a] = v
      return `
            <div class='u-p-2'>
                <div>${a.name}：${a.value}</div>
            </div>
        `
    }
  }
  // grid
    let grid = {
      top: '10%',
      left: '13%',
      right: '3%',
      bottom: '10%'
    }
  // xAxis
  let xAxis = {
    type: 'category',
    boundaryGap: true,
    data: dataArray.xdataName,
    axisLine: {
      lineStyle: {
        color: '#fff'
      }
    },
    axisLabel: {
      textStyle: {
        fontSize: 12,
        color: '#fff'
      },
      rotate: 0
    },
    axisTick: {
      show: false
    } //坐标轴刻度
  }
  // yAxis
  let yAxis = {
    type: 'value',
    scale: true,
    min: 0,
    name: '',
    nameTextStyle: {
      color: '#fff',
      fontSize: 12,
      padding: [0, 0, 0, 10]
    },
    splitLine: {
      show: false
    },
    axisLine: {
      lineStyle: {
        color: '#fff'
      }
    },
    axisLabel: {
      textStyle: {
        fontSize: 12,
        color: '#fff'
      }
    }
  }
  let dataZoom = [ //给x轴设置滚动条
    {
      start: 0, //默认为0
      end: 50, //默认为100
      type: 'slider',
      show: false,
      xAxisIndex: [0],
      handleSize: 10, //滑动条的 左右2个滑动条的大小
      height: 0, //组件高度

    },
    //下面这个属性是里面拖到
    {
      type: 'inside',
      show: true,
      xAxisIndex: [0],
      start: 0, //默认为1
      end: 50 // 100 - 1500 / 31, //默认为100
    }
  ]
  // series
  let series = [
    {
      name: '工作量统计',
      type: 'bar',
      barWidth: 20,
      symbolClip: false,
      data: dataArray.contractnum,
      label: {
        show: true,
        position: 'top',
        formatter: params => {
          return params.value
        },
        textStyle: {
          // fontSize: 16,
          color: '#fff'
        }
      },
    }
  ]
  // 渲染
  option = {
    tooltip,
    grid,
    xAxis,
    yAxis,
    series,
    dataZoom
  }
  //自己滚动图表
  var timeOut = null
  myChart.on('mouseover', stop);
  myChart.on('mouseout', goMove);
  autoMove()
  function autoMove() {
    timeOut = setInterval(() => {

      if (Number(option.dataZoom[0].end) > 100) {
        option.dataZoom[0].end = 50;
        option.dataZoom[0].start = 0;
      } else {
        option.dataZoom[0].end = option.dataZoom[0].end + 1 * (100 / option.series[0].data.length);
        option.dataZoom[0].start = option.dataZoom[0].start + 1 * (100 / option.series[0].data.length);
      }
      myChart.setOption(option);
    }, 2000);
  }
  //鼠标按住停止滚动
  function stop() {
    clearInterval(timeOut)
  }
  //鼠标松开继续滚动
  function goMove() {
    autoMove()
  }
  if (option && typeof option === 'object') {
    myChart.setOption(option);
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


const baseEquAPI = async () => {
  basedata.value = await baseEqu()
  basedata.value.maxNumber = 0
  for(let key in basedata.value) {
    if(basedata.value[key]=='null' || basedata.value[key]==null) basedata.value[key] = 0
    basedata.value[key] = Number(basedata.value[key])
    if(basedata.value[key]>=0) {
      if(basedata.value.maxNumber < basedata.value[key]) {
        basedata.value.maxNumber = basedata.value[key]
      }
    }
    //信号机
    basedata.value.signalZNumPercent = basedata.value.signalZNum?Math.round((basedata.value.signalZNum/basedata.value.signalNum) * 10000)/100:0
    basedata.value.signalWNumPercent = basedata.value.signalWNum?Math.round((basedata.value.signalWNum/basedata.value.signalNum) * 10000)/100:0
    basedata.value.signalGNumPercent =basedata.value.signalGNum?Math.round((100 - basedata.value.signalZNumPercent - basedata.value.signalWNumPercent)*100)/100:0
    //标线
    basedata.value.marklineZNumPercent = 100
    basedata.value.marklineWNumPercent = 0
    basedata.value.marklineGNumPercent = 0
    //标志牌
    basedata.value.boardZNumPercent = basedata.value.boardZNum?Math.round((basedata.value.boardZNum/basedata.value.boardNum) * 10000)/100:0
    basedata.value.boardWNumPercent = basedata.value.boardWNum?Math.round((basedata.value.boardWNum/basedata.value.boardNum) * 10000)/100:0
    basedata.value.boardGNumPercent = basedata.value.boardGNum?Math.round((100 - basedata.value.boardZNumPercent - basedata.value.boardWNumPercent)*100)/100: 0

    //信号灯
    basedata.value.signalLightZNumPercent = basedata.value.signalLightZNum?Math.round((basedata.value.signalLightZNum/basedata.value.signalLightNum) * 10000)/100:0
    basedata.value.signalLightWNumPercent = basedata.value.signalLightWNum?Math.round((basedata.value.signalLightWNum/basedata.value.signalLightNum) * 10000)/100:0
    basedata.value.signalLightGNumPercent = basedata.value.signalLightGNum?Math.round((100 - basedata.value.signalLightZNumPercent - basedata.value.signalLightWNumPercent)*100)/100:0

    //雷达
    basedata.value.radarZNumPercent = basedata.value.radarZNum? Math.round((basedata.value.radarZNum/basedata.value.radarNum) * 10000)/100:0
    basedata.value.radarWNumPercent = basedata.value.radarWNum? Math.round((basedata.value.radarWNum/basedata.value.radarNum) * 10000)/100:0
    basedata.value.radarGNumPercent = basedata.value.radarGNum? Math.round((100 - basedata.value.radarZNumPercent - basedata.value.radarWNumPercent)*100)/100:0

    //护栏
    basedata.value.guardrailZNumPercent = basedata.value.guardrailZNum? Math.round((basedata.value.guardrailZNum/basedata.value.guardrailNum) * 10000)/100:0
    basedata.value.guardrailWNumPercent = basedata.value.guardrailWNum? Math.round((basedata.value.guardrailWNum/basedata.value.guardrailNum) * 10000)/100:0
    basedata.value.guardrailGNumPercent = basedata.value.guardrailGNum? Math.round((100 - basedata.value.guardrailZNumPercent - basedata.value.guardrailWNumPercent)*100)/100:0

    //电警
    basedata.value.electricPoliceZNumPercent = basedata.value.electricPoliceZNum? Math.round((basedata.value.electricPoliceZNum/basedata.value.electricPoliceNum) * 10000)/100:0
    basedata.value.electricPoliceWNumPercent = basedata.value.electricPoliceWNum? Math.round((basedata.value.electricPoliceWNum/basedata.value.electricPoliceNum) * 10000)/100:0
    basedata.value.electricPoliceGNumPercent = basedata.value.electricPoliceGNum? Math.round((100 - basedata.value.electricPoliceZNumPercent - basedata.value.electricPoliceWNumPercent)*100)/100:0

    //led
    basedata.value.bigLedZNumPercent = basedata.value.bigLedZNum? Math.round((basedata.value.bigLedZNum/basedata.value.bigLedNum) * 10000)/100:0
    basedata.value.bigLedWNumPercent = basedata.value.bigLedWNum? Math.round((basedata.value.bigLedWNum/basedata.value.bigLedNum) * 10000)/100:0
    basedata.value.bigLedGNumPercent = basedata.value.bigLedGNum? Math.round((100 - basedata.value.bigLedZNumPercent - basedata.value.bigLedWNumPercent)*100)/100:0

    //电警
    basedata.value.memberZNumPercent = basedata.value.memberZNum? Math.round((basedata.value.memberZNum/basedata.value.memberNum) * 10000)/100:0
    basedata.value.memberWNumPercent = basedata.value.memberWNum? Math.round((basedata.value.memberWNum/basedata.value.memberNum) * 10000)/100:0
    basedata.value.memberGNumPercent = basedata.value.memberGNum? Math.round((100 - basedata.value.memberZNumPercent - basedata.value.memberWNumPercent)*100)/100:0
  }

  RankingList.value = await analysisMaintenanceRanking()
  initChart1(RankingList.value)
  getWxTypeList.value = await getWxType()
  initChart2(getWxTypeList.value)
}
const getMaintenanceWorkStatus = async () => {
  const data = await maintenanceWorkStatus(queryParams)
  RankingStatusList.value = data.list
  RankingStatusListTotal.value = data.total
}
const getMaintenancePagedp = async (status) => {
  if(status) {
    queryParams2.status = status
  } else {
    queryParams2.status = ''
  }
  wxDetail.value = await maintenancePagedp(queryParams2)
}

//滚动
const roll = (t) => {
  var ul1 = document.getElementById("comment1");
  var ul2 = document.getElementById("comment2");
  var ulbox = document.getElementById("review_box");
  ul2.innerHTML = ul1.innerHTML;
  ulbox.scrollTop = 0;
  rollStart(t);
}
const rollStart = (t) => {
  var ul1 = document.getElementById("comment1");
  // var ul2 = document.getElementById("comment2");
  var ulbox = document.getElementById("review_box");
  rollStop();
  timer.value = setInterval(()=>{
    // 当滚动高度大于列表内容高度时恢复为0
    if (ulbox.scrollTop >= ul1.scrollHeight) {
      ulbox.scrollTop = 0;
    } else {
      ulbox.scrollTop++;
    }
  }, t);
}
const rollStop = () => {
  clearInterval(timer.value);
}

const roll1 = (t) => {
  var ul1 = document.getElementById("comment11");
  var ul2 = document.getElementById("comment21");
  var ulbox = document.getElementById("review_box1");
  ul2.innerHTML = ul1.innerHTML;
  ulbox.scrollTop = 0;
  rollStart1(t);
}
const rollStart1 = (t) => {
  var ul1 = document.getElementById("comment11");
  var ul2 = document.getElementById("comment21");
  var ulbox = document.getElementById("review_box1");
  rollStop1();
  timer1.value = setInterval(()=>{
    // 当滚动高度大于列表内容高度时恢复为0
    if (ulbox.scrollTop >= ul1.scrollHeight) {
      ulbox.scrollTop = 0;
    } else {
      ulbox.scrollTop++;
    }
  }, t);
}
const rollStop1 = () => {
  clearInterval(timer1.value);
}
onMounted(() => {
  // initMap()
  baseEquAPI()
  getMaintenanceWorkStatus()
  getMaintenancePagedp()
  setTimeout(() => {
    roll(100)
    roll1(100)
  }, 2000)
  // baseEquAPI()
  // initChart()
});
</script>

<style scoped lang="scss">
.bigdata {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: auto;
  background: #02004D;
  .bigdata-title {
    width: 100%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    color: #ffffff;
    font-size: 19px;
    background: url("../../assets/imgs/bigdata2/title.png");
    background-size: 100% 100%;

  }
  .bigdata-content {
    display: flex;
    .bigdata-content-left {
      flex: 1;
      padding: 20px;
      .bigdata-content-left-top {
        height: 220px;
      }
      .bigdata-content-left-bottom {
        display: flex;
        height: auto;
        .bigdata-content-left-bottom-left {
          width: 45%;
          margin-right: 10px;
        }
        .bigdata-content-left-bottom-right {
          flex: 1
        }
      }
      .jcss-title {
        width: 100%;
        height: 40px;
        line-height: 40px;
        color: #ffffff;
        background: url("../../assets/imgs/bigdata2/jcss-title.png");
        background-size: 100% 100%;
        padding-left: 30px;
      }
      .jcss-content {
        display: flex;
        padding-top: 10px;
        .jcss-content-items {
          flex: 1;
          text-align: center;
          padding: 10px 0;
        }
        img {
          height: 28px;
          width: auto;
        }
      }
    }
    .search-tab {
      display: flex;
      padding: 10px;
      margin: 0 10px;
      div {
        flex: 1;
        text-align: center;
        font-size: 13px;
        color: #FF0000;
        border: 1px solid #FF0000;
        border-radius: 4px;
        padding: 4px;
        margin: 0 20px;
        cursor: pointer;
      }
    }
    .bt-title {
      width: 100%;
      height: 40px;
      line-height: 40px;
      color: #ffffff;
      background: url("../../assets/imgs/bigdata2/title-bg.png");
      background-size: 100% 100%;
      padding-left: 30px;
      padding-right: 8px;
      .ckbd {
        padding: 0 10px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
        border-radius: 5px;
        border: 1px solid rgba(0, 102, 255, 0.3);
        background: rgba(0, 18, 64, 0.6);
        font-size: 13px;
        float: right;
        margin-top: 5px;
        cursor: pointer;
      }
    }
    .bt-table {
      .bt-table-thead {
        display: flex;
        margin-top: 10px;
        div {
          text-align: center;
          font-size: 14px;
          flex: 1;
          color: #1FC7FF;
        }
      }
      .bt-table-tbody {
        .bt-table-tbody-item {
          display: flex;
          height: 30px;
          line-height: 30px;
          background: url(../../assets/imgs/bigdata2/trbg.png);
          background-size: 100% 100%;
          margin-top: 5px;
          div {
            text-align: center;
            font-size: 14px;
            flex: 1;
            color: #ffffff;
          }
          .xh span {
            display: inline-block;
            width: 50px;
            height: 30px;
            &.bg1 {
              background: url("../../assets/imgs/bigdata2/n1.png");
              background-size: 100% 100%;
            }
            &.bg2 {
              background: url("../../assets/imgs/bigdata2/n2.png");
              background-size: 100% 100%;
            }
            &.bg3 {
              background: url("../../assets/imgs/bigdata2/n3.png");
              background-size: 100% 100%;
            }
            &.bg4 {
              background: url("../../assets/imgs/bigdata2/n4.png");
              background-size: 100% 100%;
            }
          }

        }
        .bt-table-tbody-item2 {
          display: flex;
          height: 30px;
          line-height: 30px;
          margin-top: 4px;
          div {
            text-align: center;
            font-size: 14px;
            flex: 1;
            color: #ffffff;
          }
          .wxz {
            background: rgba(241, 39, 84, 0.7);
            color: #ffffff;
            border-radius: 4px;
            border: 1px solid rgba(241, 39, 84, 1);
            font-size: 12px;
            padding: 2px;
          }
          .dpd {
            background: rgba(238, 232, 169, 0.7);
            color: #ffffff;
            border-radius: 4px;
            border: 1px solid rgba(238, 232, 169, 1);
            font-size: 12px;
            padding: 2px;
          }
          .ywc {
            background: rgba(108, 250, 205, 0.7);
            color: #ffffff;
            border-radius: 4px;
            border: 1px solid rgba(108, 250, 205, 1);
            font-size: 12px;
            padding: 2px;
          }
          .xh span {
            display: inline-block;
            width: 50px;
            height: 30px;
            &.bg1 {
              background: url("../../assets/imgs/bigdata2/n1.png");
              background-size: 100% 100%;
            }
            &.bg2 {
              background: url("../../assets/imgs/bigdata2/n2.png");
              background-size: 100% 100%;
            }
            &.bg3 {
              background: url("../../assets/imgs/bigdata2/n3.png");
              background-size: 100% 100%;
            }
            &.bg4 {
              background: url("../../assets/imgs/bigdata2/n4.png");
              background-size: 100% 100%;
            }
          }

        }
      }
    }
    .bigdata-content-right {
        width: 400px;
        padding: 20px 10px 20px 0;
        box-sizing: border-box;
    }
  }
  #map {
    position: relative;
    width: 100%;
    height: 100%;
    border-radius: 10px;
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
.ellipsis {
  overflow: hidden;/* 超出的文本隐藏 */
  text-overflow: ellipsis;/* 用省略号显示 */
  white-space: nowrap;/* 不换行 */
}
</style>
