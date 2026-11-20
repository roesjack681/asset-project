<template>
  <Dialog :title="dialogTitle" width="700" v-model="dialogVisible1">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="上报人" prop="problem">
        <span>{{ formData.reportPersonnel }}&nbsp;&nbsp;{{ formData.reportUserPhone }}</span>
      </el-form-item>
      <el-form-item label="存在问题" prop="problem">
        <span>
          <el-input v-model="formData.problem" type="textarea" rows="4" style="width: 450px;" placeholder="请输入存在问题" />
        </span>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary">挂 单</el-button>
      <el-button @click="dialogVisible1 = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
/** 设备维保信息 表单 */
defineOptions({ name: 'OpsMaintenanceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible1 = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref({
  id: undefined,
  reportPersonnel: undefined,
  reportUserPhone: undefined,
  problem: undefined
})
const formRules = reactive({
})

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (id?: number) => {
  dialogVisible1.value = true
  dialogTitle.value = '挂单'
  resetForm()
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsMaintenanceApi.getOpsMaintenance(id)
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
    const data = formData.value as unknown as OpsMaintenanceVO
    await OpsMaintenanceApi.mountOrder(data)
    message.success(t('common.saveSuccess'))
    dialogVisible1.value = false
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
    reportPersonnel: undefined,
    reportUserPhone: undefined, 
    problem: undefined
  }
  formRef.value?.resetFields()
}
</script>
