<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="高度" prop="hight">
        <el-input v-model="formData.hight" placeholder="请输入高度" />
      </el-form-item>
      <el-form-item label="长度" prop="length">
        <el-input v-model="formData.length" placeholder="请输入长度" />
      </el-form-item>
      <el-form-item label="材质" prop="material">
        <el-input v-model="formData.material" placeholder="请输入材质" />
      </el-form-item>
      <el-form-item label="厂家" prop="manufacturer">
        <el-input v-model="formData.manufacturer" placeholder="请输入厂家" />
      </el-form-item>
      <el-form-item label="型号" prop="model">
        <el-input v-model="formData.model" placeholder="请输入型号" />
      </el-form-item>
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
      <el-form-item label="底座" prop="pedestal">
        <el-input v-model="formData.pedestal" placeholder="请输入底座" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsGuardrailProductApi, OpsGuardrailProductVO } from '@/api/traffic/opsguardrailproduct'

/** 护栏产品信息 表单 */
defineOptions({ name: 'OpsGuardrailProductForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  hight: undefined,
  length: undefined,
  material: undefined,
  manufacturer: undefined,
  model: undefined,
  remark: undefined,
  deletedTime: undefined,
  pedestal: undefined
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
      formData.value = await OpsGuardrailProductApi.getOpsGuardrailProduct(id)
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
    const data = formData.value as unknown as OpsGuardrailProductVO
    if (formType.value === 'create') {
      await OpsGuardrailProductApi.createOpsGuardrailProduct(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsGuardrailProductApi.updateOpsGuardrailProduct(data)
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
    hight: undefined,
    length: undefined,
    material: undefined,
    manufacturer: undefined,
    model: undefined,
    remark: undefined,
    deletedTime: undefined,
    pedestal: undefined
  }
  formRef.value?.resetFields()
}
</script>