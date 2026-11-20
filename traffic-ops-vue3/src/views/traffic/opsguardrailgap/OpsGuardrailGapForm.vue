<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
      <el-form-item label="护栏主键" prop="guardrailId">
        <el-input v-model="formData.guardrailId" placeholder="请输入护栏主键" />
      </el-form-item>
      <el-form-item label="维保主键" prop="maintenanceId">
        <el-input v-model="formData.maintenanceId" placeholder="请输入维保主键" />
      </el-form-item>
      <el-form-item label="护栏维护主键" prop="manceGuardrailId">
        <el-input v-model="formData.manceGuardrailId" placeholder="请输入护栏维护主键" />
      </el-form-item>
      <el-form-item label="开口名称" prop="gapName">
        <el-input v-model="formData.gapName" placeholder="请输入开口名称" />
      </el-form-item>
      <el-form-item label="开口类型" prop="gapType">
        <el-select v-model="formData.gapType" placeholder="请选择开口类型">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="GPS定位-经度" prop="lng">
        <el-input v-model="formData.lng" placeholder="请输入GPS定位-经度" />
      </el-form-item>
      <el-form-item label="GPS定位-纬度" prop="lat">
        <el-input v-model="formData.lat" placeholder="请输入GPS定位-纬度" />
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
import { OpsGuardrailGapApi, OpsGuardrailGapVO } from '@/api/traffic/opsguardrailgap'

/** 护栏开口信息 表单 */
defineOptions({ name: 'OpsGuardrailGapForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  guardrailId: undefined,
  maintenanceId: undefined,
  manceGuardrailId: undefined,
  gapName: undefined,
  gapType: undefined,
  lng: undefined,
  lat: undefined,
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
      formData.value = await OpsGuardrailGapApi.getOpsGuardrailGap(id)
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
    const data = formData.value as unknown as OpsGuardrailGapVO
    if (formType.value === 'create') {
      await OpsGuardrailGapApi.createOpsGuardrailGap(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsGuardrailGapApi.updateOpsGuardrailGap(data)
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
    guardrailId: undefined,
    maintenanceId: undefined,
    manceGuardrailId: undefined,
    gapName: undefined,
    gapType: undefined,
    lng: undefined,
    lat: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
