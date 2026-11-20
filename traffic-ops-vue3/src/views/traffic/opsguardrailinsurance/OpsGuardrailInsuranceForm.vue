<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="护栏维护主键" prop="manceGuardrailId">
        <el-input v-model="formData.manceGuardrailId" placeholder="请输入护栏维护主键" />
      </el-form-item>
      <el-form-item label="车牌号" prop="vehicleNum">
        <el-input v-model="formData.vehicleNum" placeholder="请输入车牌号" />
      </el-form-item>
      <el-form-item label="保险公司名称" prop="insuranceCompany">
        <el-input v-model="formData.insuranceCompany" placeholder="请输入保险公司名称" />
      </el-form-item>
      <el-form-item label="保单编号" prop="insurancePolicy">
        <el-input v-model="formData.insurancePolicy" placeholder="请输入保单编号" />
      </el-form-item>
      <el-form-item label="赔付金额" prop="payMoney">
        <el-input v-model="formData.payMoney" placeholder="请输入赔付金额" />
      </el-form-item>
      <el-form-item label="赔付护栏产品编号" prop="productId">
        <el-input v-model="formData.productId" placeholder="请输入赔付护栏产品编号" />
      </el-form-item>
      <el-form-item label="赔付片数" prop="pieces">
        <el-input v-model="formData.pieces" placeholder="请输入赔付片数" />
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
import { OpsGuardrailInsuranceApi, OpsGuardrailInsuranceVO } from '@/api/traffic/opsguardrailinsurance'

/** 护栏保险信息 表单 */
defineOptions({ name: 'OpsGuardrailInsuranceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  manceGuardrailId: undefined,
  vehicleNum: undefined,
  insuranceCompany: undefined,
  insurancePolicy: undefined,
  payMoney: undefined,
  productId: undefined,
  pieces: undefined,
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
      formData.value = await OpsGuardrailInsuranceApi.getOpsGuardrailInsurance(id)
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
    const data = formData.value as unknown as OpsGuardrailInsuranceVO
    if (formType.value === 'create') {
      await OpsGuardrailInsuranceApi.createOpsGuardrailInsurance(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsGuardrailInsuranceApi.updateOpsGuardrailInsurance(data)
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
    manceGuardrailId: undefined,
    vehicleNum: undefined,
    insuranceCompany: undefined,
    insurancePolicy: undefined,
    payMoney: undefined,
    productId: undefined,
    pieces: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>