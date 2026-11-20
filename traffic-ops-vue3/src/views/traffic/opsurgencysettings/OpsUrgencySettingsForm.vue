<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="紧急程度" prop="urgencyLevel">
        <el-select
          v-model="formData.urgencyLevel"
          placeholder="请选择紧急程度"
          clearable
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.urgency_Level)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理时限小时" prop="deadline">
        <el-input-number style="width: 100%" min="0" v-model="formData.deadline" placeholder="请输入处理时限小时" />
      </el-form-item>
      <el-form-item label="通知人员" prop="notifyPersonnel">
        <el-select
          multiple
          v-model="formData.notifyPersonnel"
          placeholder="请选择通知人员"
          clearable
        >
          <el-option
            v-for="dict in personList"
            :key="dict.userId"
            :label="dict.name"
            :value="dict.userId"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="删除时间" prop="deletedTime">-->
<!--        <el-date-picker-->
<!--          v-model="formData.deletedTime"-->
<!--          type="date"-->
<!--          value-format="x"-->
<!--          placeholder="选择删除时间"-->
<!--        />-->
<!--      </el-form-item>-->
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsUrgencySettingsApi, OpsUrgencySettingsVO } from '@/api/traffic/opsurgencysettings'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 维保紧急程度配置 表单 */
defineOptions({ name: 'OpsUrgencySettingsForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const personList = ref([])
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  urgencyLevel: undefined,
  deadline: undefined,
  notifyPersonnel: undefined,
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
  personList.value = await OpsUrgencySettingsApi.getAllOpsRegister()
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsUrgencySettingsApi.getOpsUrgencySettings(id)
      formData.value.notifyPersonnel = formData.value.notifyPersonnel.split(',')
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
    const data =  {...formData.value}
    data.notifyPersonnel = formData.value.notifyPersonnel.join(',')
    const notifyOpenid = []
    personList.value.forEach((e) => {
      if(data.notifyPersonnel.indexOf(e.userId)>-1) {
        notifyOpenid.push(e.openid)
      }
    })
    data.notifyOpenid = notifyOpenid.join(',')
    if (formType.value === 'create') {
      await OpsUrgencySettingsApi.createOpsUrgencySettings(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsUrgencySettingsApi.updateOpsUrgencySettings(data)
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
    urgencyLevel: undefined,
    deadline: undefined,
    notifyPersonnel: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
