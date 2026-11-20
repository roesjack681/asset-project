<template>
  <div class="bigdata">
    <div class="bigdata-title">
      <img src="../../assets/imgs/bigdata3/title-img.png" alt=""/>
    </div>
    <div class="bigdata-content">
      <div class="bigdata-content-left">
        <div class="bigdata-content-left-top">
          <div class="jcss-title">全市资产概况</div>
          <div class="jcss-content">
            <div class="jcss-content-items">
              <img src="../../assets/imgs/bigdata3/dljksb.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">道路监控设备</div>
                <div>{{8032}}个</div>
              </div>
            </div>
            <div class="jcss-content-items">
              <img src="../../assets/imgs/bigdata3/xhd.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">信号灯</div>
                <div>{{basedata.signalLightNum}}个</div>
              </div>
            </div>
            <div class="jcss-content-items">
              <img style="width: 44px" src="../../assets/imgs/bigdata3/lks.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">路口数</div>
                <div>{{basedata.intersectionNum}}个</div>
              </div>
            </div>
            <div class="jcss-content-items">
              <img src="../../assets/imgs/bigdata3/dls.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">道路数</div>
                <div>{{basedata.roadNum}}个</div>
              </div>
            </div>
            <div class="jcss-content-items">
              <img src="../../assets/imgs/bigdata3/xklks.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">信控路口数</div>
                <div>{{basedata.signalNum}}个</div>
              </div>
            </div>
            <div class="jcss-content-items">
              <img src="../../assets/imgs/bigdata3/jcxhds.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">检测信号灯数</div>
                <div>{{3456}}个</div>
              </div>
            </div>
            <div class="jcss-content-items">
              <img src="../../assets/imgs/bigdata3/xhjsl.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">信控机数量</div>
                <div>{{1280}}个</div>
              </div>
            </div>
            <div class="jcss-content-items">
              <img src="../../assets/imgs/bigdata3/xkjlws.png" alt=""/>
              <div style="color: #ffffff">
                <div style="padding: 4px 0">信控机联网数</div>
                <div>{{1280}}个</div>
              </div>
            </div>
          </div>
        </div>
        <div class="bigdata-content-left-bottom">
          <div class="bigdata-content-left-bottom-left">
            <div style="min-height: 322px;flex: 1">
              <div class="bt-title">全市设备运行情况分析</div>
              <div style="display: flex;margin-top: 6px;height: calc(100% - 50px);">
                <div style="flex: 1;height: 100%">
                  <div class="bt-title3">
                    集指考核设备
                    <span v-if="timerange.length" style="cursor:pointer;color: #ffffff;float: right;font-size: 14px;margin-right: 10px;" @click="showDJjcList">详细</span>
                  </div>
                  <div @click="showLine" style="position: relative;text-align: center;color: #ffffff;margin-top: 10%">
                    <img style="width: 33%" src="../../assets/imgs/bigdata3/yuan.png" alt=""/>
                    <div style="font-size: 26px;font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">{{rate}}%</div>
                  </div>
                  <div style="text-align: center;color: #ffffff;margin-top: 10px">电警在线率</div>
                </div>
                <div style="flex: 1;position: relative;height: 100%">
                  <div class="bt-title3">网络运维分析</div>
                  <div ref="elRef3dbt1" style="height: calc(100% - 50px);position: relative;height: 230px;z-index: 2;"></div>
                  <img style="position: absolute;z-index: 1;top: 30%;width: 50%;left:5%;" src="../../assets/imgs/bigdata3/btbg1.png" alt=""/>
                </div>
                <div style="flex: 1;position: relative;height: 100%">
                  <div class="bt-title3">信号运维分析</div>
                  <div ref="elRef3dbt2" style="height: calc(100% - 50px);z-index: 2;"></div>
                  <img style="position: absolute;z-index: 1;top: 40%; width: 50%; left: 8%;" src="../../assets/imgs/bigdata3/btbg2.png" alt=""/>
                </div>
              </div>
            </div>
            <div style="min-height: 322px;flex: 1">
              <div class="bt-title">全市道路监控设备监测分析</div>
              <div style="display: flex;margin-top: 6px;height: calc(100% - 60px);">
                <div style="flex: 1">
                  <div class="bt-title3">道路监控设备监测数据</div>
                  <div style="display: flex;margin-top: 20%" class="flex-wrapper">
                    <div style="flex: 1" class="flex-wrapper-item">
                      <img src="../../assets/imgs/bigdata3/wlgz.png" alt=""/>
                      <div class="jianbianziti">1480 <span style="font-size: 14px">/个</span></div>
                      <div>网络故障数</div>
                    </div>
                    <div style="flex: 1" class="flex-wrapper-item">
                      <img src="../../assets/imgs/bigdata3/wsp.png" alt=""/>
                      <div class="jianbianziti">1480 <span style="font-size: 14px">/个</span></div>
                      <div>无视频数</div>
                    </div>
                    <div style="flex: 1" class="flex-wrapper-item">
                      <img src="../../assets/imgs/bigdata3/dzs.png" alt=""/>
                      <div class="jianbianziti">1480 <span style="font-size: 14px">/个</span></div>
                      <div>丢帧数</div>
                    </div>
                  </div>
                </div>
                <div style="flex: 1">
                  <div class="bt-title3">重点点位监测</div>
                  <div style="display: flex;margin-top: 13%" class="flex-wrapper">
                    <div style="flex: 1" class="flex-wrapper-item">
                      <div style="position: relative">
                        <img style="width: 120px" src="../../assets/imgs/bigdata3/gzbb.png" alt=""/>
                        <div style="font-size: 24px;font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">{{calcVioValidSurveilAvgRatedata}}%</div>
                      </div>
                      <div>有效违法率</div>
                    </div>
                    <!--
                    <div style="flex: 1" class="flex-wrapper-item">
                      <div style="position: relative">
                        <img style="width: 120px" src="../../assets/imgs/bigdata3/gzbb.png" alt=""/>
                        <div style="font-size: 24px;font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">98%</div>
                      </div>
                      <div>联网率</div>
                    </div>
                    <div style="flex: 1" class="flex-wrapper-item">
                      <div style="position: relative">
                        <img style="width: 120px" src="../../assets/imgs/bigdata3/gzbb.png" alt=""/>
                        <div style="font-size: 24px;font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">99%</div>
                      </div>
                      <div>视频联网率</div>
                    </div>
                    -->
                  </div>
                </div>
                <div style="flex: 1">
                  <div class="bt-title3">长时间不在线设备</div>
                  <div class="title-wrapper">
                    <div @click="changeHour(24)" :class="hour=='24'?'active':''">24小时</div>
                    <div @click="changeHour(48)" :class="hour=='48'?'active':''">48小时</div>
                    <div @click="changeHour(72)" :class="hour=='72'?'active':''">72小时</div>
                  </div>
                  <div ref="elRef2" v-if="hour" style="width: 100%;height: calc(100% - 20px)"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="bigdata-content-right">
        <div style="height:320px">
          <div class="bt-title1">运维</div>
          <div class="bt-title3">工作白板</div>
          <div style="display: flex;margin-top: -20px" class="flex-wrapper">
            <div style="flex: 1" class="flex-wrapper-item">
              <div style="position: relative">
                <img style="width: 72%" src="../../assets/imgs/bigdata3/gzbb.png" alt=""/>
                <div style="font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">
                  <span style="font-size: 20px;">{{right1data.today_order_sum}}条</span>
                  <div>当日工单数</div>
                </div>
              </div>
            </div>
            <div style="flex: 1" class="flex-wrapper-item">
              <div style="position: relative">
                <img style="width: 72%" src="../../assets/imgs/bigdata3/gzbb.png" alt=""/>
                <div style="font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">
                  <span style="font-size: 20px;">{{Number(right1data.today_dispatch_rate * 100).toFixed(0)}}%</span>
                  <div>单日派单率</div>
                </div>
              </div>
            </div>
            <div style="flex: 1" class="flex-wrapper-item">
              <div style="position: relative">
                <img style="width: 72%" src="../../assets/imgs/bigdata3/gzbb.png" alt=""/>
                <div style="font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">
                  <span style="font-size: 20px;">{{Number(right1data.today_finish_rate * 100).toFixed(0)}}%</span>
                  <div>当日完成率</div>
                </div>
              </div>
            </div>
          </div>
          <div style="display: flex;margin-top: -45px" class="flex-wrapper">
            <div style="flex: 1" class="flex-wrapper-item">
              <div style="position: relative">
                <img style="width: 72%" src="../../assets/imgs/bigdata3/gzbb.png" alt=""/>
                <div style="font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">
                  <span style="font-size: 20px;">{{right1data.pending_orders_sum}}条</span>
                  <div>待派单</div>
                </div>
              </div>
            </div>
            <div style="flex: 1;" class="flex-wrapper-item">
              <div style="position: relative">
                <img style="width: 72%" src="../../assets/imgs/bigdata3/gzbb.png" alt=""/>
                <div style="font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">
                  <span style="font-size: 20px;">{{right1data.avg_work_time}}小时</span>
                  <div>平均维修时长</div>
                </div>
              </div>
            </div>
            <div style="flex: 1;" class="flex-wrapper-item">
              <div style="position: relative">
                <img style="width: 72%" src="../../assets/imgs/bigdata3/gzbb.png" alt=""/>
                <div style="font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">
                  <span style="font-size: 20px;">{{right1data.untreated_sum}}条</span>
                  <div>未处理工单</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div style="flex: 1; min-height: 322px;">
          <div class="bt-title3">运维大数据</div>
          <div class="title-wrapper">
            <div @click="right2type='cur_month_'" :class="right2type==='cur_month_'?'active':''">月度</div>
            <div @click="right2type='cur_year_'" :class="right2type==='cur_year_'?'active':''">年度</div>
            <div @click="right2type='all_'" :class="right2type==='all_'?'active':''">全部</div>
          </div>
          <div>
            <div style="display: flex;margin-top: 13%;" class="flex-wrapper">
              <div style="flex: 1" class="flex-wrapper-item">
                <div style="position: relative">
                  <img style="width:75%" src="../../assets/imgs/bigdata3/gdzs.png" alt=""/>
                  <div style="font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">
                    <span style="font-size: 27px;">{{right2data[right2type+'order_sum']}}条</span>
                    <div style="margin-top: 15%">工单总数</div>
                  </div>
                </div>
              </div>
              <div style="flex: 1;" class="flex-wrapper-item">
                <div style="position: relative">
                  <img style="width: 75%" src="../../assets/imgs/bigdata3/gdzs.png" alt=""/>
                  <div style="font-weight: bold;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%)">
                    <span style="font-size: 27px;">{{Number(right2data[right2type+'finish_rate'] * 100).toFixed(0)}}%</span>
                    <div style="margin-top: 15%">工单完成率</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div style="flex: 1; min-height: 322px;">
          <div class="bt-title3">工单量统计</div>
          <div ref="elRef" style="width: 100%;height: calc(100% - 20px)"></div>
        </div>
      </div>
    </div>
    <DialogNotitle width="70%" v-model="dialogVisible2">
      <div class="DialogNotitle-content">
        <div class="DialogNotitle-title">
          在线率
          <span @click="dialogVisible2 = false">×</span>
        </div>
        <div class="ratetab">
          <div :class="rateTab=='7'?'active':''" @click="changeRateTab(7)">近七天</div>
          <div :class="rateTab=='30'?'active':''" @click="changeRateTab(30)">近一个月</div>
          <div :class="rateTab=='180'?'active':''" @click="changeRateTab(180)">近半年</div>
        </div>
        <div ref="elRef3" style="width: 100%;height: 500px"></div>
      </div>
    </DialogNotitle>
    <DialogFull title="电警监测管理" v-model="showdjjc">
      <div>
        <listPage :timerange="timerange"/>
      </div>
    </DialogFull>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import echarts from "@/plugins/echarts";
import 'echarts-gl';
import 'echarts/lib/component/dataZoom';
import listPage from '@/views/traffic/djmonitorstatistics/showinopsdataIndexNew.vue'
import moment from 'moment'
let echartRef: Nullable<echarts.ECharts> = null
import {
  baseEqu,
  analysisMaintenanceRanking,
  getWxType,
  analysisElectricPoliceMonitorByLabel,
  analysisMaintenanceFinishSituation,
  analysisMaintenanceDashboardCnt,
  analysisKhsbMonitorResult,
  calcVioValidSurveilAvgRate, analysisNearlyOneYearsMaintenanceCnt
} from '@/api/bigdata'
const elRef = ref<ElRef>()
const elRef2 = ref<ElRef>()
const elRef3 = ref<ElRef>()
const elRef3dbt1 = ref<ElRef>()
const elRef3dbt2 = ref<ElRef>()
const map = ref(null);
const hour = ref(24);
const rateTab = ref(7);
const right2type = ref('cur_month_');
const layer = ref(null);
const daysRate = ref([]);
const timerange = ref([]);
const rate = ref(0)
const dialogVisible2 = ref(false);
const right1data = ref({})
const calcVioValidSurveilAvgRatedata = ref('')
const right2data = ref({
  all_finish_rate: 0,
  all_order_sum: 0,
  cur_month_finish_rate: 0,
  cur_month_order_sum: 0,
  cur_year_finish_rate: 0,
  cur_year_order_sum: 0
});
const markers = ref([]);
const basedata = ref({});
const RankingList = ref([]);
const getWxTypeList = ref([]);
const keyword = ref('');
const dialogVisible = ref(false);
const showdjjc = ref(false);
/** 打开地图全屏 */
const formRef = ref()
const queryParams = reactive({
  pageNo: 1,
  pageSize: 100,
})
const changeHour = async (h) => {
  hour.value = ''
  hour.value = h
  const barlist = await analysisElectricPoliceMonitorByLabel(hour.value)
  initBarChart(barlist)
}
const changeRateTab = async (intervalDay) => {
  rateTab.value = intervalDay
  daysRate.value = await analysisKhsbMonitorResult(intervalDay)
  showLine()
}
const baseEquAPI = async () => {
  daysRate.value = await analysisKhsbMonitorResult(7)
  console.log(daysRate.value, '0000')
  if(daysRate.value.length) {
    rate.value = daysRate.value[daysRate.value.length - 1].healthRate
  }
  timerange.value = [daysRate.value[0].analysisTime, daysRate.value[daysRate.value.length - 1].analysisTime]
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
  getWxTypeList.value = await getWxType()
  const barlist = await analysisElectricPoliceMonitorByLabel(hour.value)
  right2data.value = await analysisMaintenanceFinishSituation()
  right1data.value = await analysisMaintenanceDashboardCnt()
  initBarChart(barlist)
  initChart3dbt2(getWxTypeList.value)
}
const getcalcVioValidSurveilAvgRate = async () => {
  const data = await calcVioValidSurveilAvgRate()
  calcVioValidSurveilAvgRatedata.value = Math.floor(data * 10000)/100
}
const initChart = async () => {
  var chartdata = await analysisNearlyOneYearsMaintenanceCnt(1)
  var xdatalist = []
  var ydatalist = []
  for(let key in chartdata) {
    xdatalist.push(key)
    ydatalist.push(chartdata[key])
  }
  const options = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    "grid": {
      "borderWidth": 1,
      "top": 40,
      "right": 20,
      "left": 40,
      "bottom": 40
    },
    xAxis: {
      type: 'category',
      // data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月', '9月', '10月', '11月', '12月'],
      data: xdatalist,
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
        // data: [150, 230, 224, 218, 135, 147, 260,  224, 218, 135, 147, 260],
        data: ydatalist,
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
const initChart3dbt = () => {
  function getParametricEquation(
    startRatio,
    endRatio,
    isSelected,
    isHovered,
    k,
    height
  ) {
    // 计算
    let midRatio = (startRatio + endRatio) / 2;

    let startRadian = startRatio * Math.PI * 2;
    let endRadian = endRatio * Math.PI * 2;
    let midRadian = midRatio * Math.PI * 2;

    // 如果只有一个扇形，则不实现选中效果。
    if (startRatio === 0 && endRatio === 1) {
      isSelected = false;
    }

    // 通过扇形内径/外径的值，换算出辅助参数 k（默认值 1/3）
    k = typeof k !== "undefined" ? k : 1 / 3;

    // 计算选中效果分别在 x 轴、y 轴方向上的位移（未选中，则位移均为 0）
    let offsetX = isSelected ? Math.cos(midRadian) * 0.1 : 0;
    let offsetY = isSelected ? Math.sin(midRadian) * 0.1 : 0;

    // 计算高亮效果的放大比例（未高亮，则比例为 1）
    let hoverRate = isHovered ? 1.05 : 1;

    // 返回曲面参数方程
    return {
      u: {
        min: -Math.PI,
        max: Math.PI * 3,
        step: Math.PI / 32,
      },

      v: {
        min: 0,
        max: Math.PI * 2,
        step: Math.PI / 20,
      },

      x: function (u, v) {
        if (u < startRadian) {
          return (
            offsetX +
            Math.cos(startRadian) * (1 + Math.cos(v) * k) * hoverRate
          );
        }
        if (u > endRadian) {
          return (
            offsetX +
            Math.cos(endRadian) * (1 + Math.cos(v) * k) * hoverRate
          );
        }
        return offsetX + Math.cos(u) * (1 + Math.cos(v) * k) * hoverRate;
      },

      y: function (u, v) {
        if (u < startRadian) {
          return (
            offsetY +
            Math.sin(startRadian) * (1 + Math.cos(v) * k) * hoverRate
          );
        }
        if (u > endRadian) {
          return (
            offsetY +
            Math.sin(endRadian) * (1 + Math.cos(v) * k) * hoverRate
          );
        }
        return offsetY + Math.sin(u) * (1 + Math.cos(v) * k) * hoverRate;
      },

      z: function (u, v) {
        if (u < -Math.PI * 0.5) {
          return Math.sin(u);
        }
        if (u > Math.PI * 2.5) {
          return Math.sin(u);
        }
        return Math.sin(v) > 0 ? 1 * height : -1;
      },
    };
  }
  // 生成模拟 3D 饼图的配置项
  function getPie3D(pieData, internalDiameterRatio) {
    let series = [];
    let sumValue = 0;
    let startValue = 0;
    let endValue = 0;
    let legendData = [];
    let k =
      typeof internalDiameterRatio !== "undefined"
        ? (1 - internalDiameterRatio) / (1 + internalDiameterRatio)
        : 1 / 3;

    // 为每一个饼图数据，生成一个 series-surface 配置
    for (let i = 0; i < pieData.length; i++) {
      sumValue += pieData[i].value;

      let seriesItem = {
        name:
          typeof pieData[i].name === "undefined"
            ? `series${i}`
            : pieData[i].name,
        type: "surface",
        parametric: true,
        wireframe: {
          show: false,
        },
        pieData: pieData[i],
        pieStatus: {
          selected: false,
          hovered: false,
          k: k,
        },
      };

      if (typeof pieData[i].itemStyle != "undefined") {
        let itemStyle = {};

        typeof pieData[i].itemStyle.color != "undefined"
          ? (itemStyle.color = pieData[i].itemStyle.color)
          : null;
        typeof pieData[i].itemStyle.opacity != "undefined"
          ? (itemStyle.opacity = pieData[i].itemStyle.opacity)
          : null;

        seriesItem.itemStyle = itemStyle;
      }
      series.push(seriesItem);
    }

    // 使用上一次遍历时，计算出的数据和 sumValue，调用 getParametricEquation 函数，
    // 向每个 series-surface 传入不同的参数方程 series-surface.parametricEquation，也就是实现每一个扇形。
    for (let i = 0; i < series.length; i++) {
      endValue = startValue + series[i].pieData.value;
      console.log(series[i]);
      series[i].pieData.startRatio = startValue / sumValue;
      series[i].pieData.endRatio = endValue / sumValue;
      series[i].parametricEquation = getParametricEquation(
        series[i].pieData.startRatio,
        series[i].pieData.endRatio,
        false,
        false,
        k,
        series[i].pieData.value
      );

      startValue = endValue;

      legendData.push(series[i].name);
    }
    return series;
  }
  // 传入数据生成 option
  const optionsData = [
    {
      name: "网络",
      value: 4256,
      itemStyle: {
          opacity: 0.8,
        color: "#1FC6FF",
      },
    },

    {
      name: "供电",
      value: 2356,
      itemStyle: {
          opacity: 0.8,
        color: "#59FFFF",
      },
    },
    {
      name: "设备",
      value: 2018,
      itemStyle: {
          opacity: 0.8,
        color: "#F7C458",
      },
    },
    {
      name: "识别错误",
      value: 1998,
      itemStyle: {
          opacity: 0.8,
        color: "#EF7A30",
      },
    },
    {
      name: "其他",
      value: 998,
      itemStyle: {
          opacity: 0.8,
        color: "#1957A7",
      },
    },
  ];
  const series = getPie3D(optionsData, 0.7);
  // 准备待返回的配置项，把准备好的 legendData、series 传入。
  let options = {
    legend: {
      icon: 'circle',
      data: ["网络", "供电", "设备", "识别错误", "其他"],
      itemWidth: 8,      // 调整图标的宽度
      itemHeight: 8,     // 调整图标的高度
      orient: 'vertical',
      right: '2%',
      top: "center",
      itemGap: 20, // 设置图例项之间的间距
      textStyle: {
        color: "#fff",
        fontSize: 12,
        rich: {
          name: {
            // width: '45%',
            fontSize: 14,
            color: "#fff",
          },
          value: {
            fontSize: 16,
            padding: [0, 5, 0, 5],
            color: "#00C6FF",
          },
        },
      },
      formatter: function (name) {
        let res = optionsData.filter((v) => v.name === name);
        res = res[0] || {};
        const p = res.value;
        return name + "{value|" + p + "}";
      },
    },
    animation: true,
    tooltip: {
      formatter: (params) => {
        if (
          params.seriesName !== "mouseoutSeries" &&
          params.seriesName !== "pie2d"
        ) {
          return `${
            params.seriesName
          }<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${
            params.color
          };"></span>${
            option.series[params.seriesIndex].pieData.value + "人"
          }`;
        }
      },
      textStyle: {
        fontSize: 14,
      },
    },
    title: {
      x: "center",
      top: "20",
      textStyle: {
        color: "#fff",
        fontSize: 22,
      },
    },
    backgroundColor: "transparent",
    xAxis3D: {
      min: -1,
      max: 1,
    },
    yAxis3D: {
      min: -1,
      max: 1,
    },
    zAxis3D: {
      min: -1,
      max: 1,
    },
    grid3D: {
      show: false,
      boxHeight: 0.01,
      top: '-10%',
      left: '-20%',
      // bottom: "80%",
      viewControl: {
        distance: 180,
        alpha: 25,
        beta: 60,
        autoRotate: false, // 自动旋转
        zoomSensitivity: false
      },
    },
    series: series,
  };
  if (unref(elRef3dbt1)) {
    echartRef = echarts.init(unref(elRef3dbt1) as HTMLElement)
    echartRef?.setOption(unref(options))
  }
}
const initChart3dbt2 = () => {
  function getParametricEquation(startRatio, endRatio, isSelected, isHovered, k, height,) {
    // 计算
    let midRatio = (startRatio + endRatio) / 2;

    let startRadian = startRatio * Math.PI * 2;
    let endRadian = endRatio * Math.PI * 2;
    let midRadian = midRatio * Math.PI * 2;

    // 如果只有一个扇形，则不实现选中效果。
    if (startRatio === 0 && endRatio === 1) {
      isSelected = false;
    }

    // 通过扇形内径/外径的值，换算出辅助参数 k（默认值 1/3）
    k = 1;

    // 计算选中效果分别在 x 轴、y 轴方向上的位移（未选中，则位移均为 0）
    let offsetX = isSelected ? Math.cos(midRadian) * 0.1 : 0;
    let offsetY = isSelected ? Math.sin(midRadian) * 0.1 : 0;
    offsetX -= 1.56; // 向左移动

    // 计算高亮效果的放大比例（未高亮，则比例为 1）
    let hoverRate = isHovered ? 1.05 : 0.7;

    // 返回曲面参数方程
    return {
      u: {
        min: -Math.PI,
        max: Math.PI * 3,
        step: Math.PI / 32,
      },

      v: {
        min: 0,
        max: Math.PI * 2,
        step: Math.PI / 20,
      },

      x: function (u, v) {
        if (u < startRadian) {
          return (
            offsetX +
            Math.cos(startRadian) * (1 + Math.cos(v) * k) * hoverRate
          );
        }
        if (u > endRadian) {
          return (
            offsetX + Math.cos(endRadian) * (1 + Math.cos(v) * k) * hoverRate
          );
        }
        return offsetX + Math.cos(u) * (1 + Math.cos(v) * k) * hoverRate;
      },

      y: function (u, v) {
        if (u < startRadian) {
          return (
            offsetY +
            Math.sin(startRadian) * (1 + Math.cos(v) * k) * hoverRate
          );
        }
        if (u > endRadian) {
          return (
            offsetY + Math.sin(endRadian) * (1 + Math.cos(v) * k) * hoverRate
          );
        }
        return offsetY + Math.sin(u) * (1 + Math.cos(v) * k) * hoverRate;
      },

      z: function (u, v) {
        if (u < -Math.PI * 0.5) {
          return Math.sin(u);
        }
        if (u > Math.PI * 2.5) {
          return Math.sin(u);
        }
        return Math.sin(v) > 0 ? 1 * height : -1;
      },
    };
  }

  // 生成模拟 3D 饼图的配置项
  function getPie3D(pieData, internalDiameterRatio, distance, alpha, pieHeight, opacity = 1) {
    const series = [];
    let sumValue = 0;
    let startValue = 0;
    let endValue = 0;
    const legendData = [];
    const k =
      typeof internalDiameterRatio !== 'undefined'
        ? (1 - internalDiameterRatio) / (1 + internalDiameterRatio)
        : 1 / 3;
    // 为每一个饼图数据，生成一个 series-surface 配置
    for (let i = 0; i < pieData.length; i += 1) {
      sumValue += pieData[i].value;
      const seriesItem = {
        name: typeof pieData[i].name === 'undefined' ? `series${i}` : pieData[i].name,
        type: 'surface',
        parametric: true,
        wireframe: {
          show: false,
        },
        pieData: pieData[i],
        pieStatus: {
          selected: false,
          hovered: false,
          k: k,
        },
      };
      if (typeof pieData[i].itemStyle !== 'undefined') {
        const itemStyle = {};
        if (typeof pieData[i].itemStyle.color !== 'undefined') {
          itemStyle.color = pieData[i].itemStyle.color;
        }
        if (typeof pieData[i].itemStyle.opacity !== 'undefined') {
          itemStyle.opacity = pieData[i].itemStyle.opacity;
        }
        seriesItem.itemStyle = itemStyle;
      }
      series.push(seriesItem);
    }
    // 使用上一次遍历时，计算出的数据和 sumValue，调用 getParametricEquation 函数，
    // 向每个 series-surface 传入不同的参数方程 series-surface.parametricEquation，也就是实现每一个扇形。
    for (let i = 0; i < series.length; i += 1) {
      endValue = startValue + series[i].pieData.value;
      series[i].pieData.startRatio = startValue / sumValue;
      series[i].pieData.endRatio = endValue / sumValue;
      console.log(series[i].pieData.startRatio,
        series[i].pieData.endRatio,
        false,
        false,
        k,
        series[i].pieData.value)
      series[i].parametricEquation = getParametricEquation(
        series[i].pieData.startRatio,
        series[i].pieData.endRatio,
        false,
        false,
        k,
        series[i].pieData.value
      );
      startValue = endValue;
      legendData.push(series[i].name);
    }
    return series;
  }

  let colors = ['#3cdaa8', '#62cdfc', '#3586fa', '#f5b64b','rgba(153, 255, 64, 1)'];
  let xData = ['信号灯', '信号机', '网络', '供电', '其他'];
  let yData = [2345, 2347, 3456, 3234, 2320];
  // 使用 reduce 方法计算数组的总和
  let sum = yData.reduce((accumulator, currentValue) => accumulator + currentValue, 0);

  let optionsData = [];
  for(let i=0;i<xData.length;i++){
    optionsData.push(
      {
        name: xData[i],
        value: yData[i],
        itemStyle: {
          color: colors[i],
        }
      }
    );
  }

  const series = getPie3D(optionsData, 0.8, 240, 28, 26, 0.5);

  series.push({
    name: 'pie2d',
    type: 'pie',
    label: {
      opacity: 1,
      fontSize: 12,
      lineHeight: 20,
      textStyle: {
        fontSize: 12,
      },
    },
    labelLine: {
      length: 60,
      length2: 60,
    },
    startAngle: -50, //起始角度，支持范围[0, 360]。
    clockwise: false, //饼图的扇区是否是顺时针排布。上述这两项配置主要是为了对齐3d的样式
    radius: ['0', '0'],

    center: ['20%', '20%'],
    data: optionsData,
    itemStyle: {
      opacity: 0,
    },
  });
  // 准备待返回的配置项，把准备好的 legendData、series 传入。
  let option = {
    legend: {
      icon: 'circle',
      data: xData,
      itemWidth: 8,      // 调整图标的宽度
      itemHeight: 8,     // 调整图标的高度
      orient: 'vertical',
      right: '2%',
      top: "center",
      itemGap: 20, // 设置图例项之间的间距
      textStyle: {
        color: "#fff",
        fontSize: 12,
        rich: {
          name: {
            // width: '45%',
            fontSize: 14,
            color: "#fff",
          },
          value: {
            fontSize: 16,
            padding: [0, 5, 0, 5],
            color: "#00C6FF",
          },
        },
      },
      formatter: function (name) {
        let res = optionsData.filter((v) => v.name === name);
        res = res[0] || {};
        const p = res.value;
        return name + "{value|" + p + "}";
        // return "{name|" + name + "}";
      },
    },
    animation: false,
    tooltip: {
      formatter: (params) => {
        if (
          params.seriesName !== 'mouseoutSeries' &&
          params.seriesName !== 'pie2d'
        ) {
          console.log(params,'[');
          let value = option.series[params.seriesIndex].pieData.value
          let num = (value/sum).toFixed(2)
          return `
                占比：
            <span style="color: #2BFFCA ;font-size: 18px;">${num*100}% </span>`;
        }
      },

      borderColor: '#1CE7FF',
      backgroundColor: 'rgba(3,18,62,0.66)',
      padding: [5, 10],
      textStyle: {
        color: '#fff',
        fontSize: 16,
        align: 'left',
      },
    },
    title: {
      x: 'center',
      top: '20',
      textStyle: {
        color: '#fff',
        fontSize: 12,
      },
    },
    backgroundColor: 'transparent',
    labelLine: {
      show: false,
      lineStyle: {
        color: 'transparent',
      },
    },
    label: {
      show: false,
      color:'transparent',
      position: 'outside',
      formatter: '{b} \n{c} {d}%',
    },
    xAxis3D: {
      min: -1,
      max: 1,
    },
    yAxis3D: {
      min: -1,
      max: 1,
    },
    zAxis3D: {
      min: -1,
      max: 1,
    },
    grid3D: {
      show: false,
      boxHeight: 0.01,
      top: '10%',
      left: '-6%',
      viewControl: {
        distance: 180,
        alpha: 44,
        beta: 62,
        autoRotate: false, // 自动旋转
        zoomSensitivity: false
      },
    },

    series: series,
  };
  if (unref(elRef3dbt2)) {
    echartRef = echarts.init(unref(elRef3dbt2) as HTMLElement)
    echartRef?.setOption(unref(option))
  }
}
const initBarChart = (resultdata) => {
  let information = {
    legend: [],
    data: [],
  };
  console.log(resultdata, 0)
  resultdata.map(e => {
    information.legend.push(e.label)
    information.data.push(e.equ_cnt)
  })
  let options = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    backgroundColor: 'transparent',
    grid: {
      left: "5%",
      right: "15%",
      bottom: 0,
      top: "2%",
      containLabel: true
    },
    legend: {
      show: false,
      bottom: '0',
      icon: 'circle',
      textStyle: {
        color: '#fff',
        fontSize: 14,
        padding: [0, 8, 0, 8]
      }
    },
    xAxis: {
      show: false,
      type: "value"
    },
    yAxis: [
      {
        type: "category",
        inverse: true,
        axisLabel: {
          show: true,
          interval: 0,
          textStyle: {
            color: "#ffffff",
            fontSize: 14
          }
        },
        splitLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        axisLine: {
          show: false
        },
        data: information.legend
      }
    ],
    series: [
      {
        name: "数量",
        type: "bar",
        zlevel: 1,
        label: {
          //数据显示的位置
          show: true, //数值是否显示
          position: "right", //数值相对于柱状图显示的位置
          textStyle: {
            color: "#FFFFFF", // 标签字体颜色
            fontSize: 14, // 标签字体大小
            fontStyle: "italic", // 标签字体斜体
          }
        },
        itemStyle: {
          normal: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 1,
              y2: 0,
              colorStops: [
                { offset: 0, color: 'rgba(91, 143, 249, 0.85)' }, // 0% 处的颜色
                { offset: 1, color: 'rgba(91, 143, 249, 0.85)' } // 100% 处的颜色
              ],
              global: false
            }
          }
        },
        barWidth: 15,
        emphasis: {
          disabled: true,
          focus: "none"
        },
        data: information.data
      }
    ]
  }
  if (unref(elRef2)) {
    echartRef = echarts.init(unref(elRef2) as HTMLElement)
    echartRef?.setOption(unref(options))
  }
}
const showLine = () => {
  dialogVisible2.value=true
  let o = {}
  daysRate.value.forEach(e => {
    if(!o.xlend) o.xlend = []
    if(!o.mounth) o.mounth = []
    if(!o.list) o.list = []
    o.xlend.push(e.analysisTime)
    if(rateTab.value === 180) {
      const mounth = moment(e.analysisTime).format('M') + '月'
      if(o.mounth.indexOf(mounth) == -1) {
        o.mounth.push(mounth)
      } else {
        o.mounth.push('')
      }
    }
    console.log(o.mounth)
    o.name = '在线率'
    o.list.push(e.healthRate)
  })
  setTimeout(() => {
    if(rateTab.value === 7 || rateTab.value === 30) {
      initChart3(o, 'auto')
    } else {
      initChart3_180(o, 0)
    }
  }, 250)
}
const initChart3_180 = (obj, interval) => {
  var option = {
    backgroundColor: '#0630A5',
    legend: {
      show: false,
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
      // trigger: 'item',
      formatter: function (params) {
        console.log(params)
        // params 是一个包含当前数据系列信息的对象
        return obj.xlend[params[0].dataIndex] + '\n在线率：' + params[0].data + '%'
      },
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
      data: obj.mounth,
      axisLabel: {
        interval: interval,
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
        name: obj.name,
        type: 'line',
        // symbol: 'none',
        smooth: false,
        itemStyle: {
          normal: {
            color: '#C66300',
            lineStyle: {
              color: '#C66300',
              width: 2,
            }
          },
        },
        toolbox: {
          show: false,
        },
        data: obj.list,
      }
    ],
  };
  if (unref(elRef3)) {
    echartRef = echarts.init(unref(elRef3) as HTMLElement)
    echartRef?.setOption(unref(option))
  }
}
const initChart3 = (obj, interval) => {
  var option = {
    backgroundColor: '#0630A5',
    legend: {
      show: false,
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
      // trigger: 'item',
      formatter: function (params) {
        console.log(params)
        // params 是一个包含当前数据系列信息的对象
        return params[0].name + '\n在线率：' + params[0].data + '%'
      },
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
      data: obj.xlend,
      axisLabel: {
        interval: interval,
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
        name: obj.name,
        type: 'line',
        // symbol: 'none',
        smooth: false,
        itemStyle: {
          normal: {
            color: '#C66300',
            lineStyle: {
              color: '#C66300',
              width: 2,
            }
          },
        },
        toolbox: {
          show: false,
        },
        data: obj.list,
      }
    ],
  };
  if (unref(elRef3)) {
    echartRef = echarts.init(unref(elRef3) as HTMLElement)
    echartRef?.setOption(unref(option))
  }
}
const showDJjcList = () => {
  showdjjc.value = true
}
onMounted(() => {
  baseEquAPI()
  initChart()
  initChart3dbt()
  getcalcVioValidSurveilAvgRate()
});
</script>

<style scoped lang="scss">
.bigdata {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: auto;
  background: url("../../assets/imgs/bigdata3/bg.png");
  background-size: 100% 100%;
  .bigdata-title {
    width: 100%;
    height: 80px;
    line-height: 40px;
    text-align: center;
    color: #ffffff;
    padding-top: 5px;
    background: url("../../assets/imgs/bigdata3/title.png");
    background-size: 100% 100%;
    img {
      height: 35px;
    }

  }
  .bigdata-content {
    display: flex;
    height: calc(100% - 80px);
    .bigdata-content-left {
      flex: 3;
      padding: 0 20px 20px 20px;
      height: 100%;
      .bigdata-content-left-top {
        height: 190px;
        font-size: 13px;
      }
      .bigdata-content-left-bottom {
        height: calc(100% - 190px);
        .bigdata-content-left-bottom-left {
          height: 100%;
          margin-right: 10px;
          display: flex;
          flex-direction: column;
        }
      }
      .jcss-title {
        width: 100%;
        height: 40px;
        line-height: 40px;
        color: #ffffff;
        background: url("../../assets/imgs/bigdata3/bt-2.png");
        background-size: 100% 100%;
        padding-left: 30px;
        font-weight: bold;
      }
      .jcss-content {
        display: flex;
        padding-top: 30px;
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
      font-weight: bold;
      background: url("../../assets/imgs/bigdata3/bt-2.png");
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
    .bt-title1 {
      width: 100%;
      height: 40px;
      line-height: 40px;
      color: #ffffff;
      font-weight: bold;
      background: url("../../assets/imgs/bigdata3/bt-3.png");
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
    .bt-title3 {
      width: 100%;
      height: 40px;
      line-height: 40px;
      font-weight: bold;
      color: #ffffff;
      background: url("../../assets/imgs/bigdata3/bt-1.png");
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
      max-width: 600px;
      display: flex;
      flex-direction: column;
      flex: 1;
      padding: 0px 10px 0px 0;
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
.flex-wrapper {
  display: flex;
  margin-top: 20px;
  .flex-wrapper-item {
    font-size: 13px;
    flex: 1;
    padding: 10px 0;
    text-align: center;
    color: #ffffff;
    div {
      width: 100%;
    }
    img {
      width: 70px;
      height: auto;
    }
  }
}
.title-wrapper {
  display: flex;
  div {
    cursor: pointer;
    heihgt: 34px;
    line-height: 34px;
    flex: 1;
    text-align: center;
    margin: 10px 10px 0 10px;
    font-size: 14px;
    font-weight: bold;
    color: #ffffff;
    background: url("../../assets/imgs/bigdata3/wxz.png");
    background-size: 100% 100%;
    &.active {
      background: url("../../assets/imgs/bigdata3/xz.png");
      background-size: 100% 100%;
    }
  }
}
.jianbianziti {
  background: linear-gradient(to right, rgba(247, 254, 255, 1), rgba(123, 205, 235, 1), rgba(25, 111, 143, 1));
  background-clip: text;
  color: transparent;
  font-size: 26px;
  font-weight: bold;
  padding: 4px 0;
}

.ratetab {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
  div {
    padding: 5px;
    cursor: pointer;
    border: 1px solid #1FC7FF;
    color:#ffffff;
    border-radius: 4px;
    margin: 5px;
    font-size: 13px;
    &.active {
      background: #1FC7FF;
    }
  }
}
</style>
