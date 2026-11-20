<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="128px"
      v-loading="formLoading"
    >
      <el-form-item label="终端编号" prop="deviceId">
        <el-input v-model="formData.deviceId" placeholder="请输入终端编号" />
      </el-form-item>
      <el-form-item label="GPS纬度" prop="latitude">
        <el-input v-model="formData.latitude" placeholder="请输入GPS纬度" />
      </el-form-item>
      <el-form-item label="GPS经度" prop="longitude">
        <el-input v-model="formData.longitude" placeholder="请输入GPS经度" />
      </el-form-item>
      <el-form-item label="GPS速度(km/h)" prop="speed">
        <el-input v-model="formData.speed" placeholder="请输入GPS速度(km/h)" />
      </el-form-item>
      <el-form-item label="方向(0-359度)" prop="direction">
        <el-input v-model="formData.direction" placeholder="请输入方向(0-359度)" />
      </el-form-item>
      <el-form-item label="位置上报时间" prop="reportTime">
        <el-date-picker
          v-model="formData.reportTime"
          type="date"
          value-format="x"
          placeholder="选择位置上报时间"
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
import { OpsDeviceLocationNewApi, OpsDeviceLocationNewVO } from '@/api/public/opsdevicelocationnew'

/** 终端最新位置 表单 */
defineOptions({ name: 'OpsDeviceLocationNewForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  deviceId: undefined,
  latitude: undefined,
  longitude: undefined,
  speed: undefined,
  direction: undefined,
  reportTime: undefined
})
const formRules = reactive({
  deviceId: [{ required: true, message: '终端编号不能为空', trigger: 'blur' }],
  latitude: [{ required: true, message: 'GPS纬度不能为空', trigger: 'blur' }],
  longitude: [{ required: true, message: 'GPS经度不能为空', trigger: 'blur' }],
  reportTime: [{ required: true, message: '位置上报时间不能为空', trigger: 'blur' }]
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
      formData.value = await OpsDeviceLocationNewApi.getOpsDeviceLocationNew(id)
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
    const data = formData.value as unknown as OpsDeviceLocationNewVO
    if (formType.value === 'create') {
      await OpsDeviceLocationNewApi.createOpsDeviceLocationNew(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsDeviceLocationNewApi.updateOpsDeviceLocationNew(data)
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
    deviceId: undefined,
    latitude: undefined,
    longitude: undefined,
    speed: undefined,
    direction: undefined,
    reportTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
