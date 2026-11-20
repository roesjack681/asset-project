<template>
  <Dialog title="详情" width="720px" v-model="dialogVisible">
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
              <el-dropdown>
                <img style="width: 20px;" v-if="formData[data.day].pingStatus=='全通'" src="~@/assets/imgs/pinglan.png" alt=""/>
                <img style="width: 24px;" v-else-if="formData[data.day].pingStatus=='时断时通'" src="~@/assets/imgs/pingb.png" alt=""/>
                <img style="width: 20px;" v-else src="~@/assets/imgs/pinghong.png" alt=""/>
                <template #dropdown>
                  <div style="padding: 10px 20px">
                    <div style="text-align: center;">
                      <img style="width: 38px;" v-if="formData[data.day].pingStatus=='全通'" src="~@/assets/imgs/pinglan.png" alt=""/>
                      <img style="width: 42px;" v-else-if="formData[data.day].pingStatus=='时断时通'" src="~@/assets/imgs/pingb.png" alt=""/>
                      <img style="width: 38px;" v-else src="~@/assets/imgs/pinghong.png" alt=""/>
                    </div>
                    <div style="text-align: center;font-size: 17px">{{ formData[data.day].pingStatus }}</div>
                  </div>
                </template>
              </el-dropdown>
              <el-dropdown>
                <img style="width: 20px;margin-left: 20px;" v-if="formData[data.day].videoStatus=='正常'" src="~@/assets/imgs/videolan.png" alt=""/>
                <img style="width: 20px;margin-left: 20px" v-else src="~@/assets/imgs/videohong.png" alt=""/>
                <template #dropdown>
                  <div style="padding: 10px 20px">
                    <img style="width: 38px;" v-if="formData[data.day].videoStatus=='正常'" src="~@/assets/imgs/videolan.png" alt=""/>
                    <img style="width: 38px;" v-else src="~@/assets/imgs/videohong.png" alt=""/>
                    <div style="text-align: center;font-size: 17px">{{ formData[data.day].videoStatus }}</div>
                  </div>
                </template>
              </el-dropdown>

              <el-dropdown>
                <img style="width: 20px;margin-left: 20px;" v-if="formData[data.day].validSurveilCnt>0" src="~@/assets/imgs/wwf.png" alt=""/>
                <img style="width: 20px;margin-left: 20px" v-else src="~@/assets/imgs/wf.png" alt=""/>
                <template #dropdown>
                  <div style="padding: 10px 20px">
                    <div style="text-align: center;">
                      <img style="width: 38px;" v-if="formData[data.day].validSurveilCnt>0" src="~@/assets/imgs/wwf.png" alt=""/>
                      <img style="width: 38px;" v-else src="~@/assets/imgs/wf.png" alt=""/>
                      <div style="text-align: center;font-size: 17px">{{ formData[data.day].validSurveilCnt>0?'抓拍正常':'无有效违法' }}</div>
                    </div>
                    <div style="padding-top: 5px">
                      <div>有效违法次数: {{ formData[data.day].validSurveilCnt }}</div>
                      <div>无效违法次数: {{ formData[data.day].invalidSurveilCnt }}
                        <img v-if="formData[data.day].invalidSurveilCnt>0" @click="openChartForm('bt', formData[data.day], data.day)" style="width: 14px;height: auto;margin-left: 4px;cursor:pointer;" src="../../../assets/imgs/bingtu.png" alt=""/>
                      </div>
                      <div>未审违法次数：{{ formData[data.day].noSurveilCnt }}</div>
                    </div>
                  </div>
                </template>
              </el-dropdown>
            </div>
          </div>
        </div>
      </template>
    </el-calendar>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
    <VioEquipmentMonitorChartForm ref="formChartRef" @success="getList" />
  </Dialog>
</template>
<script setup lang="ts">
import {VioEquipmentMonitorAnalysisApi} from "@/api/traffic/vioequipmentmonitoranalysis";
import VioEquipmentMonitorChartForm from './VioEquipmentMonitorChartForm.vue'
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
import * as echarts from "echarts";
const formRules = reactive({
})
const formRef = ref() // 表单 Ref
const calendarRef = ref()
const formatDate = (year, mounth) => {
  var monthx = mounth < 10 ? '0' + mounth : mounth;
  var start = year + '-' + monthx + '-01 00:00:00';
  var end = '';

  // 31 天的月份
  if ([1, 3, 5, 7, 8, 10, 12].includes(mounth)) {
    end = year + '-' + monthx + '-31 23:59:59';
  }
  // 30 天的月份
  else if ([4, 6, 9, 11].includes(mounth)) {
    end = year + '-' + monthx + '-30 23:59:59';
  }
  // 2 月：闰年 29 天，平年 28 天
  else if (mounth === 2) {
    var isLeap = (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
    end = year + '-' + monthx + '-' + (isLeap ? 29 : 28) + ' 23:59:59';
  }
  return [start, end];
}
const selectDate = (type) => {
  if(type == 'prev-month') {
    value.value = moment(value.value).subtract(1, 'months').format('YYYY-MM-DD')
  }
  if(type == 'next-month') {
    value.value = moment(value.value).add(1, 'months').format('YYYY-MM-DD')
  }
  getList(value.value)
}
const formChartRef = ref()
const openChartForm = (type: string, row: any, day: string) => {
  formChartRef.value.open(type, row.ipAddr, row.sbbh, moment(day).format('YYYYMMDD'))
}
const getList = async (time) => {
  // var date = new Date(time)
  var date
  if(time) {
    date = new Date(time)
  } else {
    date = new Date()
  }
  var yearr = date.getFullYear()
  var month = date.getMonth() + 1
  const {list} = await VioEquipmentMonitorAnalysisApi.vioEquipmentMonitorAnalysispageDetail({
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
const open = async (type: string, id?: number, time?: number) => {
  equid.value = id
  var date = time? new Date(time): new Date()
  var yearr = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  value.value = new Date(yearr, month - 1, day)
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const {list} = await VioEquipmentMonitorAnalysisApi.vioEquipmentMonitorAnalysispageDetail({
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
