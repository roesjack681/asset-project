<template>
  <Dialog :title="dialogTitle" width="700px" v-model="dialogVisible">
    <el-calendar v-model="value" ref="calendarRef">
      <template #header="{date}">
        <span>{{ date }}</span>
        <el-button-group>
          <el-button size="mini" @click="selectDate('prev-month')">上个月</el-button>
          <el-button size="mini" @click="selectDate('next-month')">下个月</el-button>
        </el-button-group>
      </template>
      <template #date-cell="{ data }">
        <div :class="data.isSelected ? 'is-selected' : ''">
          {{ data.day.split("-").slice(1)[1]}}
         <div v-if="formData[data.day]">
           <div style="padding-top: 10px">
             <img style="width: 20px;" v-if="formData[data.day].pingStatus=='正常'" src="~@/assets/imgs/pinglan.png" alt=""/>
             <img style="width: 20px;" v-else src="~@/assets/imgs/pinghong.png" alt=""/>
             <img style="width: 20px;margin-left: 20px;" v-if="formData[data.day].videoStatus=='正常'" src="~@/assets/imgs/videolan.png" alt=""/>
             <img style="width: 20px;margin-left: 20px" v-else src="~@/assets/imgs/videohong.png" alt=""/>
           </div>
         </div>
        </div>
      </template>
    </el-calendar>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { MonitorResultsImportAnalysisApi, MonitorResultsImportAnalysisVO } from '@/api/police/monitorresultsimportanalysis'

/** 公安监测结果导入分析 表单 */
defineOptions({ name: 'MonitorResultsImportAnalysisForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const value = ref(new Date())
const equid = ref('')
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
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
import moment from "moment";
const formRules = reactive({
})
const formRef = ref() // 表单 Ref
const calendarRef = ref()
const formatDate = (year, month) => {
  var monthx = month < 10 ? '0' + month : month;
  var start = year + '-' + monthx + '-01 00:00:00';
  var end = '';

  // 31 天的月份
  if ([1, 3, 5, 7, 8, 10, 12].includes(month)) {
    end = year + '-' + monthx + '-31 23:59:59';
  }
  // 30 天的月份
  else if ([4, 6, 9, 11].includes(month)) {
    end = year + '-' + monthx + '-30 23:59:59';
  }
  // 2 月：闰年 29 天，平年 28 天
  else if (month === 2) {
    var isLeap = (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
    end = year + '-' + monthx + '-' + (isLeap ? 29 : 28) + ' 23:59:59';
  }
  return [start, end];
};
const selectDate = (type) => {
  // calendarRef.value.selectDate(value)
  if(type == 'prev-month') {
    value.value = moment(value.value).subtract(1, 'months').format('YYYY-MM-DD')
  }
  if(type == 'next-month') {
    value.value = moment(value.value).add(1, 'months').format('YYYY-MM-DD')
  }
  getList(value.value)
}
const getList = async (time) => {
  var date = new Date(time)
  var yearr = date.getFullYear()
  var month = date.getMonth() + 1
  const {list} = await MonitorResultsImportAnalysisApi.monitorResultsImportDetailPage({
    equId: equid.value,
    occurrenceTime: formatDate(yearr, month),
    pageNo: 1,
    pageSize: 50
  })
  formData.value = {}
  list.forEach(e => {
    e.dateTime = moment(e.occurrenceTime).format('YYYY-MM-DD')
    formData.value[e.dateTime] = e
  })
}
/** 打开弹窗 */
const open = async (ipAddr?:String, id?: number, time?: number) => {
  equid.value = id
  var date = new Date(time)
  var yearr = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  value.value = new Date(yearr, month - 1, day)
  dialogVisible.value = true
  dialogTitle.value = ipAddr + '通断检测结果'
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const {list} = await MonitorResultsImportAnalysisApi.monitorResultsImportDetailPage({
        equId: id,
        occurrenceTime: formatDate(yearr, month),
        pageNo: 1,
        pageSize: 50
      })
      formData.value = {}
      list.forEach(e => {
        e.dateTime = moment(e.occurrenceTime).format('YYYY-MM-DD')
        formData.value[e.dateTime] = e
      })
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as MonitorResultsImportAnalysisVO
    if (formType.value === 'create') {
      await MonitorResultsImportAnalysisApi.createMonitorResultsImportAnalysis(data)
      message.success(t('common.createSuccess'))
    } else {
      await MonitorResultsImportAnalysisApi.updateMonitorResultsImportAnalysis(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
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
