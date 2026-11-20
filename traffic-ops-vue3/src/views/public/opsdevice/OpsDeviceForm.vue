<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="psn" prop="id">
        <el-input v-model="formData.id" placeholder="请输入psn" />
      </el-form-item>
      <el-form-item label="终端型号" prop="model">
        <el-input v-model="formData.model" placeholder="请输入终端型号" />
      </el-form-item>
      <el-form-item label="IMEI" prop="imei">
        <el-input v-model="formData.imei" placeholder="请输入IMEI" />
      </el-form-item>
      <el-form-item label="车牌号" prop="plateNum">
        <el-input v-model="formData.plateNum" placeholder="请输入车牌号" />
      </el-form-item>
      <el-form-item label="摄像头信息" prop="cameraInfo">
        <el-input v-model="formData.cameraInfo" placeholder="请输入摄像头信息" />
      </el-form-item>
      <!-- <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio value="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item> -->
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" />
      </el-form-item>
      <!-- <el-form-item label="删除时间" prop="deletedTime">
        <el-date-picker
          v-model="formData.deletedTime"
          type="date"
          value-format="x"
          placeholder="选择删除时间"
        />
      </el-form-item> -->
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsDeviceApi, OpsDeviceVO } from '@/api/public/opsdevice'

/** 终端设备信息 表单 */
defineOptions({ name: 'OpsDeviceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  model: undefined,
  imei: undefined,
  plateNum: undefined,
  cameraInfo: undefined,
  status: undefined,
  remark: undefined,
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
      formData.value = await OpsDeviceApi.getOpsDevice(id)
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
    const data = formData.value as unknown as OpsDeviceVO
    if (formType.value === 'create') {
      await OpsDeviceApi.createOpsDevice(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsDeviceApi.updateOpsDevice(data)
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
    model: undefined,
    imei: undefined,
    plateNum: undefined,
    cameraInfo: undefined,
    status: undefined,
    remark: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>