<template>
  <Dialog :title="dialogTitle" width="600px" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="所属单位" prop="affiliatedUnit">
        <el-radio-group v-model="formData.affiliatedUnit">
          <el-radio label="交警" value="1"/>
          <el-radio label="公安" value="2"/>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="组名" prop="groupName">
        <el-input v-model="formData.groupName" placeholder="请输入组名" />
      </el-form-item>
      <el-form-item label="主要运维内容" prop="groupContent">
        <Editor v-model="formData.groupContent" height="150px" />
      </el-form-item>
      <el-form-item label="可维护区域" prop="groupArea" v-if="formData.affiliatedUnit==1">
        <el-select v-model="formData.groupAreaList" filterable multiple clearable class="!w-240px">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="可维护区域" prop="groupArea" v-else>
        <el-select v-model="formData.groupAreaList" filterable multiple clearable class="!w-240px">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="组员" prop="groupUserId">
        <el-select v-model="formData.groupUserIdList" filterable multiple clearable placeholder="请选择">
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.nickname"
            :value="item.id + ''"/>
        </el-select>
      </el-form-item>
      <el-form-item label="队长" prop="leadUserId">
        <el-select v-model="formData.leadUserId" filterable clearable placeholder="请选择">
          <el-option
            @click="formData.leadUserName=item.nickname"
            v-for="item in userList"
            :key="item.id"
            :label="item.nickname"
            :value="item.id"/>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { UserAreaGroupApi, UserAreaGroupVO } from '@/api/public/userareagroup'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import {OpsMaintenanceApi} from "@/api/traffic/optmaintenance";

/** 区域用户组信息 表单 */
defineOptions({ name: 'UserAreaGroupForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const userList = ref([])
const userLeaderList = ref([])
const formData = ref({
  id: undefined,
  affiliatedUnit: [],
  groupName: undefined,
  groupContent: undefined,
  groupArea: '',
  groupAreaList: [],
  groupUserId: '',
  leadUserName: '',
  groupUserIdList: [],
  leadUserId: undefined,
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
      formData.value = await UserAreaGroupApi.getUserAreaGroup(id)
      formData.value.groupUserIdList = formData.value.groupUserId.split(';')
      formData.value.groupAreaList = formData.value.groupArea.split(';')
    } finally {
      formLoading.value = false
    }
  }
  getUserList()
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
    const data = {
      ...formData.value
    }
    data.groupArea = data.groupAreaList.join(';')
    data.groupUserId = data.groupUserIdList.join(';')
    if (formType.value === 'create') {
      await UserAreaGroupApi.createUserAreaGroup(data)
      message.success(t('common.createSuccess'))
    } else {
      await UserAreaGroupApi.updateUserAreaGroup(data)
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
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    affiliatedUnit: [],
    groupName: undefined,
    groupContent: undefined,
    groupArea: [],
    groupUserId: [],
    leadUserId: undefined,
    leadUserName: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
