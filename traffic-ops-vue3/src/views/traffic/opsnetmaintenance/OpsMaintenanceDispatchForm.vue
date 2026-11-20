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
      <el-form-item label="维修人" prop="maintenanceUserId">
        <el-select v-model="formData.maintenanceUserId" filterable clearable placeholder="请选择">
          <el-option
            @click="chooseUser(item)"
            v-for="item in userList"
            :key="item.id"
            :label="item.nickname"
            :value="item.id"/>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary">派 单</el-button>
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
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const userList = ref([])
const formData = ref({
  id: undefined,
  equType: undefined,
  equId: undefined,
  problem: undefined,
  faultType: undefined,
  faultDate: undefined,
  maintenance: undefined,
  maintenanceDate: undefined,
  maintenancePersonnel: undefined,
  maintenanceUserId: undefined,
  maintenancePhone: undefined,
  dispatchUserId: undefined,
  suggestion: undefined,
  remark: undefined,
  deletedTime: undefined,
  fileList: []
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible1.value = true
  dialogTitle.value = '派单'
  formType.value = type
  resetForm()
  getUserList()
  if (id) {
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
  if(!formData.value.maintenanceUserId) return message.success('请选择派单人员')
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as OpsMaintenanceVO
    if (formType.value === 'dispatch') {
      await OpsMaintenanceApi.dispatchRepairOrderPc(data)
      // await OpsMaintenanceApi.acceptRepairOrderPc(data1)
      message.success(t('common.createSuccess'))
    }
    dialogVisible1.value = false
    // 发送操作成功的事件

    emit('success')
  } finally {
    formLoading.value = false
  }
}
const getUserList = async () => {
  const userListData = await OpsMaintenanceApi.queryAllUserInfoList()
  userList.value = userListData
}
const chooseUser = (item) => {
  formData.value.dispatch = item.nickname
  formData.value.maintenanceUserId = item.id
  formData.value.maintenancePersonnel = item.nickname
  formData.value.maintenancePhone = item.mobile
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equType: undefined,
    equId: undefined,
    problem: undefined,
    faultType: undefined,
    faultDate: undefined,
    maintenance: undefined,
    maintenanceDate: undefined,
    maintenancePersonnel: undefined,
    suggestion: undefined,
    remark: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
