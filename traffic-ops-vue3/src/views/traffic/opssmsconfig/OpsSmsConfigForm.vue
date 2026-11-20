<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="110px"
      v-loading="formLoading"
    >
      <el-form-item label="发送类型" prop="sendType">
        <el-select v-model="formData.sendType" multiple placeholder="请选择发送类型">
          <el-option label="重点考核视频" value="1" />
          <el-option label="重点考核卡口" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="接收人姓名" prop="receiveName">
        <el-autocomplete
          popper-class="my-autocomplete"
          v-model="formData.receiveName"
          :fetch-suggestions="querySearch"
          placeholder="请输入接收人姓名"
          @select="handleSelect">
          <template #default="{ item }">
            <div class="name">{{ item.nickname }}</div>
          </template>
        </el-autocomplete>
      </el-form-item>
      <el-form-item label="接收人手机号" prop="receivePhone">
        <el-input v-model="formData.receivePhone" placeholder="请输入接收人手机号" />
      </el-form-item>
      <el-form-item label="接收状态" prop="receiveStatus">
        <el-radio-group v-model="formData.receiveStatus">
          <el-radio value="on">on</el-radio>
          <el-radio value="off">off</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsSmsConfigApi, OpsSmsConfigVO } from '@/api/traffic/opssmsconfig'
import {OpsMaintenanceApi} from "@/api/traffic/optmaintenance";

/** 资产短信发送配置 表单 */
defineOptions({ name: 'OpsSmsConfigForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const userList = ref([]) // 用户下拉
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  sendType: [],
  receiveName: undefined,
  receivePhone: undefined,
  userBh: undefined,
  receiveStatus: undefined,
  deletedTime: undefined
})
const formRules = reactive({
  sendType: [
    { required: true, message: '请选择发送类型', trigger: 'change' }
  ],
  receiveName: [
    { required: true, message: '请输入接收人姓名', trigger: ['blur', 'change'] }
  ],
  receivePhone: [
    { required: true, message: '请输入接收人手机号', trigger: 'blur' },
    {
      pattern: /^1[3-9]\d{9}$/,
      message: '请输入正确的手机号',
      trigger: 'blur'
    }
  ],
  receiveStatus: [
    { required: true, message: '请选择接收状态', trigger: 'change' }
  ]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  getUserList()
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsSmsConfigApi.getOpsSmsConfig(id)
      if(formData.value.sendType) {
        formData.value.sendType = formData.value.sendType.split(',')
      }
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
    const data = formData.value as unknown as OpsSmsConfigVO
    if(data.sendType.length) {
      data.sendType = data.sendType.join(',')
    }
    if(data.receiveName && data.userBh) {
      var user = userList.value.filter(item => item.id === data.userBh)
      if(user.length && user[0].nickname !== data.receiveName) {
        data.userBh = ''
      }
    }
    if (formType.value === 'create') {
      await OpsSmsConfigApi.createOpsSmsConfig(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsSmsConfigApi.updateOpsSmsConfig(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const getUserList = async () => {
  userList.value = await OpsMaintenanceApi.queryAllUserInfoList()
}
const handleSelect = (item) => {
  formData.value.userBh = item.id
  formData.value.receiveName = item.nickname
}
const querySearch = (queryString, cb) => {
  var results = queryString
    ? userList.value.filter(createFilter(queryString))
    : userList.value
  cb(results)
}

const createFilter = (queryString) => {
  return (userList) => {
    return (
      userList.nickname.indexOf(queryString) > -1
    )
  }
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    sendType: [],
    receiveName: undefined,
    receivePhone: undefined,
    userBh: undefined,
    receiveStatus: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
