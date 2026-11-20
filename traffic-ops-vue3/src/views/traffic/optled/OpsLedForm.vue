<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="资产编号" prop="equNumber">
        <el-input v-model="formData.equNumber" placeholder="请输入设备编号" />
      </el-form-item>
      <!-- <el-form-item label="设备名称" prop="equName">
        <el-input v-model="formData.equName" placeholder="请输入设备名称" />
      </el-form-item>
      <el-form-item label="设备类型" prop="equType">
        <el-select v-model="formData.equType" placeholder="请选择设备类型">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item> -->
      <el-form-item label="所在道路名称" prop="roadName">
        <el-input v-model="formData.roadName" placeholder="请输入所在道路名称" />
      </el-form-item>
      <el-form-item label="灯控路口名称" prop="intersectionName">
        <el-input v-model="formData.intersectionName" placeholder="请输入灯控路口名称" />
      </el-form-item>
      <el-form-item label="GPS定位-经度" prop="lng">
        <el-input v-model="formData.lng" placeholder="请输入GPS定位-经度" />
      </el-form-item>
      <el-form-item label="GPS定位-纬度" prop="lat">
        <el-input v-model="formData.lat" placeholder="请输入GPS定位-纬度" />
      </el-form-item>
      <el-form-item label="安装位置" prop="adress">
        <el-input v-model="formData.adress" placeholder="请输入安装位置" />
      </el-form-item>
      <el-form-item label="安装时间" prop="installDate">
        <el-date-picker
          v-model="formData.installDate"
          type="date"
          value-format="x"
          placeholder="选择安装时间"
        />
      </el-form-item>
      <el-form-item label="厂家" prop="manufacturer">
        <el-input v-model="formData.manufacturer" placeholder="请输入厂家" />
      </el-form-item>
      <el-form-item label="型号" prop="equModel">
        <el-input v-model="formData.equModel" placeholder="请输入型号" />
      </el-form-item>
      <el-form-item label="归属单位" prop="unit">
        <el-input v-model="formData.unit" placeholder="请输入归属单位" />
      </el-form-item>
      <el-form-item label="尺寸" prop="size">
        <el-input v-model="formData.size" placeholder="请输入尺寸" />
      </el-form-item>
      <el-form-item label="显示用途" prop="displayPurpose">
        <el-input v-model="formData.displayPurpose" placeholder="请输入显示用途" />
      </el-form-item> 
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsLedApi, OpsLedVO } from '@/api/traffic/optled'

/** Led屏 表单 */
defineOptions({ name: 'OpsLedForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  equNumber: undefined,
  equName: undefined,
  equType: undefined,
  roadName: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  adress: undefined,
  installDate: undefined,
  manufacturer: undefined,
  equModel: undefined,
  unit: undefined,
  size: undefined,
  displayPurpose: undefined,
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
      formData.value = await OpsLedApi.getOpsLed(id)
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
    const data = formData.value as unknown as OpsLedVO
    if (formType.value === 'create') {
      await OpsLedApi.createOpsLed(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsLedApi.updateOpsLed(data)
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
    equNumber: undefined,
    equName: undefined,
    equType: undefined,
    roadName: undefined,
    intersectionName: undefined,
    lng: undefined,
    lat: undefined,
    adress: undefined,
    installDate: undefined,
    manufacturer: undefined,
    equModel: undefined,
    unit: undefined,
    size: undefined,
    displayPurpose: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>