<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
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
      <el-form-item label="辅助设备名称" prop="auxiliaryName">
        <el-input v-model="formData.auxiliaryName" placeholder="请输入辅助设备名称" />
      </el-form-item>
      <el-form-item label="数量" prop="auxiliaryNum">
        <el-input v-model="formData.auxiliaryNum" placeholder="请输入数量" />
      </el-form-item>
      <el-form-item label="图片" prop="auxiliaryImg">
        <el-input v-model="formData.auxiliaryImg" placeholder="请输入图片" />
      </el-form-item>
      <el-form-item label="状态：安装；拆除" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio value="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="安装时间" prop="installTime">
        <el-date-picker
          v-model="formData.installTime"
          type="date"
          value-format="x"
          placeholder="选择安装时间"
        />
      </el-form-item>
      <el-form-item label="拆除时间" prop="demolishTime">
        <el-date-picker
          v-model="formData.demolishTime"
          type="date"
          value-format="x"
          placeholder="选择拆除时间"
        />
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
import { OpsGuardrailAuxiliaryApi, OpsGuardrailAuxiliaryVO } from '@/api/traffic/opsguardrailauxiliary'

/** 护栏辅助设备 表单 */
defineOptions({ name: 'OpsGuardrailAuxiliaryForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  guardrailId: undefined,
  maintenanceId: undefined,
  manceGuardrailId: undefined,
  auxiliaryName: undefined,
  auxiliaryNum: undefined,
  auxiliaryImg: undefined,
  status: undefined,
  installTime: undefined,
  demolishTime: undefined,
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
      formData.value = await OpsGuardrailAuxiliaryApi.getOpsGuardrailAuxiliary(id)
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
    const data = formData.value as unknown as OpsGuardrailAuxiliaryVO
    if (formType.value === 'create') {
      await OpsGuardrailAuxiliaryApi.createOpsGuardrailAuxiliary(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsGuardrailAuxiliaryApi.updateOpsGuardrailAuxiliary(data)
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
    auxiliaryName: undefined,
    auxiliaryNum: undefined,
    auxiliaryImg: undefined,
    status: undefined,
    installTime: undefined,
    demolishTime: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>