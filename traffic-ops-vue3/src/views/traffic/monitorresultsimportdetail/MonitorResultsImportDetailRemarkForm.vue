<template>
  <Dialog :title="dialogTitle" width="700" v-model="dialogVisible1">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="设备IP" prop="ipAddr">
        <span>{{ ipAddr }}</span>
      </el-form-item>
      <el-form-item label="设备状态" prop="ipAddr">
        <span>{{ status }}</span>
      </el-form-item>
      <!-- <el-form-item label="设备情况" prop="equSituationList">
        <el-select v-model="formData.equSituationList" multiple filterable clearable placeholder="请选择">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.EQU_SITUATION)"
            :key="dict.label"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item> -->
      <el-form-item label="设备情况说明" prop="equDesc">
        <el-input v-model="formData.equDesc" :rows="3" type="textarea" />
      </el-form-item>
      <!-- <el-form-item label="现场照片" prop="evidenceFileId">
        <UploadImg v-model="formData.evidenceFileId" height="125px" width="125px" />
      </el-form-item> -->
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary">保 存</el-button>
      <el-button @click="dialogVisible1 = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { MonitorResultsImportDetailApi, MonitorResultsImportDetailVO } from '@/api/traffic/monitorresultsimportdetail'
/** 设备维保信息 表单 */
defineOptions({ name: 'MonitorResultsImportDetailRemarkForm'})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible1 = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const ipAddr = ref('')
const status = ref('')
const formData = ref({
  id: undefined,
  equSituationList: [],
  equDesc: undefined,
  evidenceFileId: undefined
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (id?: number) => {
  dialogVisible1.value = true
  dialogTitle.value = '设备情况'
  resetForm()
  if (id) {
    formLoading.value = true
    try {
      const res = await MonitorResultsImportDetailApi.getMonitorResultsImportDetail(id)
      formData.value.id = res.id
      ipAddr.value = res.ipAddr
      status.value = res.status
      if (res.equSituation) {
        formData.value.equSituationList = res.equSituation.split(';')
      }
      formData.value.equDesc = res.equDesc
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if(!formData.value.equSituationList) return message.warning('请选择设备情况')
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as MonitorResultsImportDetailVO
    await MonitorResultsImportDetailApi.remarktMonitorResultsImportDetail(data)
    message.success(t('common.updateSuccess'))
    dialogVisible1.value = false
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
    equSituationList: [],
    equDesc: undefined,
    evidenceFileId: undefined
  }
  formRef.value?.resetFields()
}
</script>
