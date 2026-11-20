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
        <span>{{ formData.problem }}</span>
      </el-form-item>
      <el-form-item label="是否修复" prop="statt">
        <el-select v-model="confirmFormData.status" placeholder="请选择">
          <el-option label="已修复" value="5"/>
          <el-option label="未修复" value="4"/>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="receiveUserName">
        <el-input
          v-model="confirmFormData.remark"
          placeholder="请输入备注"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary">确 认</el-button>
      <el-button @click="dialogVisible1 = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import moment from 'moment'
/** 设备维保信息 表单 */
defineOptions({ name: 'OpsMaintenanceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible1 = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref({
  id: undefined,
  equType: undefined,
  equId: undefined,
  problem: undefined,
  reportPersonnel: undefined,
  reportUserPhone: undefined
})
const confirmFormData = ref({
  id: undefined,
  status: undefined,
  remark: undefined
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (id: number) => {
  dialogVisible1.value = true
  dialogTitle.value = '确认'
  resetForm()
  if (id) {
    confirmFormData.value.id = id
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
  if(!confirmFormData.value.status) return message.success('请选择是否修复')
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = confirmFormData.value as unknown as OpsMaintenanceConfirmVO
    await OpsMaintenanceApi.confirmRepairOrder(data)
    message.success(t('common.createSuccess'))
    dialogVisible1.value = false
    // 发送操作成功的事件

    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  confirmFormData.value = {
    id: undefined,
    status: undefined,
    remark: undefined
  }
  formRef.value?.resetFields()
}
</script>
