<template>
  <Dialog :title="dialogTitle" width="820px" v-model="dialogVisible">
    <div ref="elRef" style="width: 100%;height: 450px"></div>
    <template #footer>
      <el-button @click="dialogVisible = false">关闭</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import {VioEquipmentMonitorAnalysisApi} from "@/api/traffic/vioequipmentmonitoranalysis";
import * as echarts from 'echarts'
import moment from 'moment'
/** 公安监测结果导入分析 表单 */
defineOptions({ name: 'MonitorResultsImportAnalysisForm' })
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const value = ref(new Date())
const chartData = ref([])
let echartRef: Nullable<echarts.ECharts> = null
const elRef = ref<ElRef>()
const equid = ref('')
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const chartLineData = ref([]) // 折线图数据
const vioEquSituationList = getStrDictOptions(DICT_TYPE.VIO_EQU_SITUATION)
const formData = ref({
  id: undefined,
  equId: undefined,
  equLable: undefined,
  eventType: undefined,
  videoNormalCnt: undefined,
  videoAbnormalCnt: undefined,
  videoHealthRate: undefined,
  pingNormalCnt: undefined,
  pingAbnormalCnt: undefined,
  pingHealthRate: undefined,
  monitorCnt: undefined,
  lastRepairTime: undefined,
  deletedTime: undefined
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref
/** 打开弹窗 */
const open = async (type, title: string, id?: number, analysisDay) => {
  const analysisDayStr = analysisDay? ' ' + moment(analysisDay).format('YYYY-MM-DD'):''
  equid.value = id
  dialogVisible.value = true
  if(type === 'bt') {
    dialogTitle.value = title + analysisDayStr + ' 无效原因分析'
  } else {
    dialogTitle.value = title + analysisDayStr + ' 有效违法分析'
  }
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    if(type === 'bt') {
      dialogTitle.value = title + analysisDayStr + ' 无效原因分析'
      try {
        chartData.value = await VioEquipmentMonitorAnalysisApi.analysisVioInvalidReasonPieChart({
          sbbh: id,
          analysisDay: analysisDay
        })
        initChart()
      } finally {
        formLoading.value = false
      }
    } else if(type === 'bt-yxwfcs') {
      dialogTitle.value = title + analysisDayStr + ' 有效违法行为分析'
      try {
        chartData.value = await VioEquipmentMonitorAnalysisApi.analysisVioIllegalPieChart({
          sbbh: id,
          analysisDay: analysisDay
        })
        chartData.value.map(e => {
          e.invalid_reason = e.label
          return e
        })
        initChart()
      } finally {
        formLoading.value = false
      }
    } else {
      dialogTitle.value = title + analysisDayStr + ' 有效违法分析'
      try {
        chartLineData.value = await VioEquipmentMonitorAnalysisApi.analysisVioValidLineChart({
          sbbh: id
        })
        initChartLine()
      } finally {
        formLoading.value = false
      }
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

const formatOptionsColor = (data) => {
  const currentitem = vioEquSituationList.find(e => e.label === data)
  if(currentitem && currentitem.cssClass) {
    return currentitem.cssClass
  } else {
    return generateRandomColor()
  }
}
const initChart = () => {
  chartData.value.map(e => {
    e.value = e.sum_cnt
    e.name = e.invalid_reason
    e.label = {
      formatter: function(d) {
        return d.data.name + ' 次数：' + d.data.value+'次'
      }
    }
    return e
  })

  const options = {
    tooltip: {
      trigger: 'item',
      formatter: function(params) {
        // 处理数据过长时，tooltip显示不全的问题
        const {value, name} = params
        return `
              <div style="color: red; font-weight: bold;margin-bottom: 10px;max-width: 380px;text-align: justify;word-break: break-all;word-wrap: break-word;white-space:pre-wrap">${name}</div>
            <div style="max-width: 380px;display:flex;align-items: center;flex-wrap: wrap">
              <div style="flex: 1">次数：</div>
              <span style="font-weight:bold;margin-left:20px;">${value}次</span>
            </div>`
      }
    },
    series: [
      {
        name: '用英语讲中国故事四年级获奖人数',
        type: 'pie',
        radius: '50%',
        data: chartData.value,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };
  if (unref(elRef)) {
    echartRef = echarts.init(unref(elRef) as HTMLElement)
    echartRef?.setOption(unref(options))
  }
}
const initChartLine = () => {
  const seriesData = []
  const xAxisData = []
  const operateMarkData = []
  chartLineData.value.forEach((e, index) => {
    e.occurrence_time_str = moment(e.occurrence_time).format('YYYY-MM-DD')
    xAxisData.push(e.occurrence_time_str)
    seriesData.push(e.sum_cnt)
    if(e.operate_mark) {
      let fixedArray = new Array(chartLineData.value.length).fill(null); // 使用null填充，你也可以用其他值填充
      fixedArray[index] = e.sum_cnt
      operateMarkData.push({
        type:'line',
          smooth: true,
          itemStyle:{
          // color: generateRandomColor()
          color: formatOptionsColor(e.operate_mark)
        },
          symbolSize: 15,
            symbol:'circle',
          lineStyle:{
          color:'rgb(0,0,0,0)'
        },
          data: fixedArray,
        })
    }
    return e
  })
  var options = {
    tooltip: {
      trigger: 'axis',
      formatter:function(param){
        if(chartLineData.value[param[0].dataIndex].operate_mark) {
          return param[0].name + "<br />" + '重大变更：' + chartLineData.value[param[0].dataIndex].operate_mark + "<br />" + param[0].seriesName + "：" + param[0].data
        } else {
          return param[0].name + "<br />" + param[0].seriesName + "：" + param[0].data
        }
      }
    },
    grid: {
      left: '0',
      right: '0',
      bottom: '3',
      containLabel: true
    },
    xAxis: [{
      type: 'category',
      data: xAxisData,
      axisLine: {
        show: true,
      },
      axisTick: {
        show: true, //不显示刻度
      },
      boundaryGap: true,
      splitLine: {
        show: false,
        width: 0.05,
        lineStyle: {
          type: "solid",
          color: "#4B90BD"
        }
      },
      axisLabel: {          //坐标轴字体颜色
        interval: 0,
        rotate: 90,
        textStyle: {
          color: '#9eaaba'
        }
      },
      axisLine: {
        lineStyle: {
          color: "#e5e5e5"
        }
      },
      axisTick: {       //y轴刻度线
        show: false
      },
      splitLine: {    //网格
        show: false,
      },
      boundaryGap: true
    }],
    yAxis: {
      type: 'value',
      axisLabel: {        //坐标轴字体颜色
        textStyle: {
          color: '#9eaaba'
        }
      },
      axisLine: {
        show: false,
      },
      axisTick: { //y轴刻度线
        show: false
      },
      splitLine: {    //网格
        show: true,
        lineStyle: {
          color: '#dadde4',
          type: "dashed"
        }
      }
    },
    series: [
      {
        name: '有效违法次数',
        type: 'line',
        data: seriesData,
        color: '#34aaff',
        symbol: 'emptyCircle',
        // symbol: 'circle',
        showSymbol: false, //是否显示 symbol, 如果 false 则只有在 tooltip hover 的时候显示。
        symbolSize: 0,
        itemStyle: {
          color: '#34aaff',
          borderColor: '#34aaff',
          borderWidth: 0,
        },
        smooth: true
      },
      ...operateMarkData
    ]
  };
  if (unref(elRef)) {
    echartRef = echarts.init(unref(elRef) as HTMLElement)
    echartRef?.setOption(unref(options))
  }
}

const generateRandomColor = () => {
  // 生成三个随机数作为 RGB 值
  var r = Math.floor(Math.random() * 256);
  var g = Math.floor(Math.random() * 256);
  var b = Math.floor(Math.random() * 256);
  // 组装成颜色字符串
  var color = 'rgb(' + r + ',' + g + ',' + b + ')';
  return color;
}


/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equId: undefined,
    equLable: undefined,
    eventType: undefined,
    videoNormalCnt: undefined,
    videoAbnormalCnt: undefined,
    videoHealthRate: undefined,
    pingNormalCnt: undefined,
    pingAbnormalCnt: undefined,
    pingHealthRate: undefined,
    monitorCnt: undefined,
    lastRepairTime: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
