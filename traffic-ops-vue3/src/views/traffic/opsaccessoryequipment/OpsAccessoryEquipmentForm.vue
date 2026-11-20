<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="杆件编号" prop="memberId">
        <el-input v-model="formData.memberId" placeholder="请输入杆件编号" />
      </el-form-item>
      <el-form-item label="设备类型名称" prop="equType">
        <el-select v-model="formData.equType" placeholder="请选择设备类型名称">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="厂家" prop="manufacturer">
        <el-input v-model="formData.manufacturer" placeholder="请输入厂家" />
      </el-form-item>
      <el-form-item label="设备型号" prop="equModel">
        <el-input v-model="formData.equModel" placeholder="请输入设备型号" />
      </el-form-item>
      <el-form-item label="数量" prop="equNum">
        <el-input v-model="formData.equNum" placeholder="请输入数量" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" />
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
import { OpsAccessoryEquipmentApi, OpsAccessoryEquipmentVO } from '@/api/traffic/opsaccessoryequipment'

/** 杆件附属设备 表单 */
defineOptions({ name: 'OpsAccessoryEquipmentForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  memberId: undefined,
  equType: undefined,
  manufacturer: undefined,
  equModel: undefined,
  equNum: undefined,
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
      formData.value = await OpsAccessoryEquipmentApi.getOpsAccessoryEquipment(id)
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
    const data = formData.value as unknown as OpsAccessoryEquipmentVO
    if (formType.value === 'create') {
      await OpsAccessoryEquipmentApi.createOpsAccessoryEquipment(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsAccessoryEquipmentApi.updateOpsAccessoryEquipment(data)
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
    memberId: undefined,
    equType: undefined,
    manufacturer: undefined,
    equModel: undefined,
    equNum: undefined,
    remark: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>