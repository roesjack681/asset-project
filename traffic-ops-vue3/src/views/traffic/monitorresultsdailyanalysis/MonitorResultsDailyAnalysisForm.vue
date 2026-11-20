<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="分析时间" prop="analysisDate">
        <el-date-picker
          v-model="formData.analysisDate"
          type="date"
          value-format="x"
          placeholder="选择分析时间"
        />
      </el-form-item>
      <el-form-item label="设备ID" prop="equId">
        <el-input v-model="formData.equId" placeholder="请输入设备ID" />
      </el-form-item>
      <el-form-item label="杆件编号" prop="memberBh">
        <el-input v-model="formData.memberBh" placeholder="请输入杆件编号" />
      </el-form-item>
      <el-form-item label="道路名称" prop="roadName">
        <el-input v-model="formData.roadName" placeholder="请输入道路名称" />
      </el-form-item>
      <el-form-item label="交叉口名称" prop="intersectionName">
        <el-input v-model="formData.intersectionName" placeholder="请输入交叉口名称" />
      </el-form-item>
      <el-form-item label="方向" prop="direction">
        <el-input v-model="formData.direction" placeholder="请输入方向" />
      </el-form-item>
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input v-model="formData.ipAddr" placeholder="请输入IP地址" />
      </el-form-item>
      <el-form-item label="电警类型" prop="djType">
        <el-select v-model="formData.djType" placeholder="请选择电警类型">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="设备标签" prop="equLable">
        <el-input v-model="formData.equLable" placeholder="请输入设备标签" />
      </el-form-item>
      <el-form-item label="检测事件" prop="eventType">
        <el-select v-model="formData.eventType" placeholder="请选择检测事件">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="正常数" prop="normalCnt">
        <el-input v-model="formData.normalCnt" placeholder="请输入正常数" />
      </el-form-item>
      <el-form-item label="异常数" prop="abnormalCnt">
        <el-input v-model="formData.abnormalCnt" placeholder="请输入异常数" />
      </el-form-item>
      <el-form-item label="检测天数" prop="monitorSumDays">
        <el-input v-model="formData.monitorSumDays" placeholder="请输入检测天数" />
      </el-form-item>
      <el-form-item label="检测总次数" prop="monitorSumCnt">
        <el-input v-model="formData.monitorSumCnt" placeholder="请输入检测总次数" />
      </el-form-item>
      <el-form-item label="健康率" prop="healthRate">
        <el-input v-model="formData.healthRate" placeholder="请输入健康率" />
      </el-form-item>
      <el-form-item label="删除时间" prop="deletedTime">
        <el-date-picker
          v-model="formData.deletedTime"
          type="date"
          value-format="x"
          placeholder="选择删除时间"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { MonitorResultsDailyAnalysisApi, MonitorResultsDailyAnalysisVO } from '@/api/traffic/monitorresultsdailyanalysis'

/** 设备监测结果每日分析 表单 */
defineOptions({ name: 'MonitorResultsDailyAnalysisForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  analysisDate: undefined,
  equId: undefined,
  memberBh: undefined,
  roadName: undefined,
  intersectionName: undefined,
  direction: undefined,
  ipAddr: undefined,
  djType: undefined,
  equLable: undefined,
  eventType: undefined,
  normalCnt: undefined,
  abnormalCnt: undefined,
  monitorSumDays: undefined,
  monitorSumCnt: undefined,
  healthRate: undefined,
  deletedTime: undefined
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await MonitorResultsDailyAnalysisApi.getMonitorResultsDailyAnalysis(id)
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
    const data = formData.value as unknown as MonitorResultsDailyAnalysisVO
    if (formType.value === 'create') {
      await MonitorResultsDailyAnalysisApi.createMonitorResultsDailyAnalysis(data)
      message.success(t('common.createSuccess'))
    } else {
      await MonitorResultsDailyAnalysisApi.updateMonitorResultsDailyAnalysis(data)
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
    analysisDate: undefined,
    equId: undefined,
    memberBh: undefined,
    roadName: undefined,
    intersectionName: undefined,
    direction: undefined,
    ipAddr: undefined,
    djType: undefined,
    equLable: undefined,
    eventType: undefined,
    normalCnt: undefined,
    abnormalCnt: undefined,
    monitorSumDays: undefined,
    monitorSumCnt: undefined,
    healthRate: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>