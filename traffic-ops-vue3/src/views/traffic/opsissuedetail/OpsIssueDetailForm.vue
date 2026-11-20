<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="领用编号" prop="issueBh">
        <el-input v-model="formData.issueBh" placeholder="请输入领用编号" />
      </el-form-item>
      <el-form-item label="装备编号" prop="equId">
        <el-input v-model="formData.equId" placeholder="请输入装备编号" />
      </el-form-item>
      <el-form-item label="使用部门编号" prop="useDeptBh">
        <el-input v-model="formData.useDeptBh" placeholder="请输入使用部门编号" />
      </el-form-item>
      <el-form-item label="使用部门" prop="useDept">
        <el-input v-model="formData.useDept" placeholder="请输入使用部门" />
      </el-form-item>
      <el-form-item label="使用人" prop="userName">
        <el-input v-model="formData.userName" placeholder="请输入使用人" />
      </el-form-item>
      <el-form-item label="使用人编号" prop="userBh">
        <el-input v-model="formData.userBh" placeholder="请输入使用人编号" />
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
import { OpsIssueDetailApi, OpsIssueDetailVO } from '@/api/traffic/opsissuedetail'

/** 领用详情 表单 */
defineOptions({ name: 'OpsIssueDetailForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  issueBh: undefined,
  equId: undefined,
  useDeptBh: undefined,
  useDept: undefined,
  userName: undefined,
  userBh: undefined,
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
      formData.value = await OpsIssueDetailApi.getOpsIssueDetail(id)
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
    const data = formData.value as unknown as OpsIssueDetailVO
    if (formType.value === 'create') {
      await OpsIssueDetailApi.createOpsIssueDetail(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsIssueDetailApi.updateOpsIssueDetail(data)
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
    issueBh: undefined,
    equId: undefined,
    useDeptBh: undefined,
    useDept: undefined,
    userName: undefined,
    userBh: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>