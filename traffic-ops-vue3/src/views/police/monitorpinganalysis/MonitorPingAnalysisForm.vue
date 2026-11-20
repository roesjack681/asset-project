<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="设备id" prop="equId">
        <el-input v-model="formData.equId" placeholder="请输入设备id" />
      </el-form-item>
      <el-form-item label="设备标签" prop="equLable">
        <el-input v-model="formData.equLable" placeholder="请输入设备标签" />
      </el-form-item>
      <el-form-item label="自动ping正常数" prop="autoPingNormalCnt">
        <el-input v-model="formData.autoPingNormalCnt" placeholder="请输入自动ping正常数" />
      </el-form-item>
      <el-form-item label="自动ping异常数" prop="autoPingAbnormalCnt">
        <el-input v-model="formData.autoPingAbnormalCnt" placeholder="请输入自动ping异常数" />
      </el-form-item>
      <el-form-item label="自动ping健康率" prop="autoPingHealthRate">
        <el-input v-model="formData.autoPingHealthRate" placeholder="请输入自动ping健康率" />
      </el-form-item>
      <el-form-item label="自动检测次数" prop="autoMonitorCnt">
        <el-input v-model="formData.autoMonitorCnt" placeholder="请输入自动检测次数" />
      </el-form-item>
      <el-form-item label="ping正常数" prop="pingNormalCnt">
        <el-input v-model="formData.pingNormalCnt" placeholder="请输入ping正常数" />
      </el-form-item>
      <el-form-item label="ping异常数" prop="pingAbnormalCnt">
        <el-input v-model="formData.pingAbnormalCnt" placeholder="请输入ping异常数" />
      </el-form-item>
      <el-form-item label="ping健康率" prop="pingHealthRate">
        <el-input v-model="formData.pingHealthRate" placeholder="请输入ping健康率" />
      </el-form-item>
      <el-form-item label="检测次数" prop="monitorCnt">
        <el-input v-model="formData.monitorCnt" placeholder="请输入检测次数" />
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
import { MonitorPingAnalysisApi, MonitorPingAnalysisVO } from '@/api/police/monitorpinganalysis'

/** 电警通断检测管理 表单 */
defineOptions({ name: 'MonitorPingAnalysisForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  equId: undefined,
  equLable: undefined,
  autoPingNormalCnt: undefined,
  autoPingAbnormalCnt: undefined,
  autoPingHealthRate: undefined,
  autoMonitorCnt: undefined,
  pingNormalCnt: undefined,
  pingAbnormalCnt: undefined,
  pingHealthRate: undefined,
  monitorCnt: undefined,
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
      formData.value = await MonitorPingAnalysisApi.getMonitorPingAnalysis(id)
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
    const data = formData.value as unknown as MonitorPingAnalysisVO
    if (formType.value === 'create') {
      await MonitorPingAnalysisApi.createMonitorPingAnalysis(data)
      message.success(t('common.createSuccess'))
    } else {
      await MonitorPingAnalysisApi.updateMonitorPingAnalysis(data)
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
    autoPingNormalCnt: undefined,
    autoPingAbnormalCnt: undefined,
    autoPingHealthRate: undefined,
    autoMonitorCnt: undefined,
    pingNormalCnt: undefined,
    pingAbnormalCnt: undefined,
    pingHealthRate: undefined,
    monitorCnt: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>