<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="摄像头ID" prop="id">
        <el-input v-model="formData.id" placeholder="请输入摄像头ID" />
      </el-form-item>
      <el-form-item label="摄像头编号" prop="cameraBh">
        <el-input v-model="formData.cameraBh" placeholder="请输入摄像头编号" />
      </el-form-item>
      <el-form-item label="摄像头名称" prop="cameraName">
        <el-input v-model="formData.cameraName" placeholder="请输入摄像头编号" />
      </el-form-item>
      <el-form-item label="摄像头用户" prop="cameraUser">
        <el-input v-model="formData.cameraUser" placeholder="请输入摄像头用户" />
      </el-form-item>
      <el-form-item label="摄像头密码" prop="cameraPassword">
        <el-input v-model="formData.cameraPassword" placeholder="请输入摄像头密码" />
      </el-form-item>
      <el-form-item label="摄像头组" prop="cameraGroup">
        <el-input v-model="formData.cameraGroup" placeholder="请输入摄像头组" />
      </el-form-item>
      <el-form-item label="安装位置" prop="installLocation">
        <el-input v-model="formData.installLocation" placeholder="请输入安装位置" />
      </el-form-item>
      <el-form-item label="安装时间" prop="installDate">
        <el-date-picker
          v-model="formData.installDate"
          type="date"
          value-format="x"
          placeholder="选择安装时间"
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
import { OpsCameraInfoApi, OpsCameraInfoVO } from '@/api/public/opscamerainfo'

/** 资产摄像设备信息 表单 */
defineOptions({ name: 'OpsCameraInfoForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  cameraBh: undefined,
  cameraName: undefined,
  cameraUser: undefined,
  cameraPassword: undefined,
  cameraGroup: undefined,
  installLocation: undefined,
  installDate: undefined,
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
      formData.value = await OpsCameraInfoApi.getOpsCameraInfo(id)
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
    const data = formData.value as unknown as OpsCameraInfoVO
    if (formType.value === 'create') {
      await OpsCameraInfoApi.createOpsCameraInfo(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsCameraInfoApi.updateOpsCameraInfo(data)
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
    cameraBh: undefined,
    cameraUser: undefined,
    cameraPassword: undefined,
    cameraGroup: undefined,
    installLocation: undefined,
    installDate: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>