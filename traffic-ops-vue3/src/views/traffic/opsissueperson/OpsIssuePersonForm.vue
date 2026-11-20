<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" :closeOnClickModal="false">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="部门" prop="deptId">
        <el-tree-select
          v-model="formData.deptId"
          :data="deptList"
          :props="defaultProps"
          filterable
          check-strictly
          @change="chooseUseDept"
          node-key="id"
          placeholder="请选择部门"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="userName">
        <el-input v-model="formData.userName" placeholder="请输入姓名" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsIssuePersonApi, OpsIssuePersonVO } from '@/api/traffic/opsissueperson'
import {defaultProps, handleTree} from "@/utils/tree";
import * as DeptApi from '@/api/system/dept'
/** 设备领用人信息 表单 */
defineOptions({ name: 'OpsIssuePersonForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const deptList = ref([])
const formData = ref({
  id: undefined,
  userName: undefined,
  deptId: undefined,
  deletedTime: undefined
})
const formRules = reactive({
  userName: [{required: true, message: '请输入姓名', trigger: 'blur'}],
  deptId: [{required: true, message: '请选择部门', trigger: ' change'}],
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
      formData.value = await OpsIssuePersonApi.getOpsIssuePerson(id)
      formData.value.deptId = formData.value.deptId - 0
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
    const data = formData.value as unknown as OpsIssuePersonVO
    if (formType.value === 'create') {
      await OpsIssuePersonApi.createOpsIssuePerson(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsIssuePersonApi.updateOpsIssuePerson(data)
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
    userName: undefined,
    deptId: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
onMounted(async () => {
  // 加载部门树
  deptList.value = handleTree(await DeptApi.getSimpleDeptList())
})
</script>
