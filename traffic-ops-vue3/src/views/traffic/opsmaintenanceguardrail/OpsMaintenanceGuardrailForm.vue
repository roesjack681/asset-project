<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="维保主键" prop="maintenanceId">
        <el-input v-model="formData.maintenanceId" placeholder="请输入维保主键" />
      </el-form-item>
      <el-form-item label="护栏主键" prop="guardrailId">
        <el-input v-model="formData.guardrailId" placeholder="请输入护栏主键" />
      </el-form-item>
      <el-form-item label="护栏维护类型：清洗；更换；拆除" prop="maintenanceType">
        <el-select v-model="formData.maintenanceType" placeholder="请选择护栏维护类型：清洗；更换；拆除">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="更换类型：损毁；换新" prop="changeType">
        <el-select v-model="formData.changeType" placeholder="请选择更换类型：损毁；换新">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="起始位置名称" prop="startName">
        <el-input v-model="formData.startName" placeholder="请输入起始位置名称" />
      </el-form-item>
      <el-form-item label="起始位置经度" prop="startLng">
        <el-input v-model="formData.startLng" placeholder="请输入起始位置经度" />
      </el-form-item>
      <el-form-item label="起始位置纬度" prop="startLat">
        <el-input v-model="formData.startLat" placeholder="请输入起始位置纬度" />
      </el-form-item>
      <el-form-item label="截止位置名称" prop="endName">
        <el-input v-model="formData.endName" placeholder="请输入截止位置名称" />
      </el-form-item>
      <el-form-item label="截止位置经度" prop="endLng">
        <el-input v-model="formData.endLng" placeholder="请输入截止位置经度" />
      </el-form-item>
      <el-form-item label="截止位置纬度" prop="endLat">
        <el-input v-model="formData.endLat" placeholder="请输入截止位置纬度" />
      </el-form-item>
      <el-form-item label="是否增加开口" prop="isGap">
        <el-input v-model="formData.isGap" placeholder="请输入是否增加开口" />
      </el-form-item>
      <el-form-item label="变更前产品编号" prop="moditfyBeProduct">
        <el-input v-model="formData.moditfyBeProduct" placeholder="请输入变更前产品编号" />
      </el-form-item>
      <el-form-item label="变更后产品编号" prop="moditfyAfProduct">
        <el-input v-model="formData.moditfyAfProduct" placeholder="请输入变更后产品编号" />
      </el-form-item>
      <el-form-item label="变更前图片" prop="moditfyBeImg">
        <el-input v-model="formData.moditfyBeImg" placeholder="请输入变更前图片" />
      </el-form-item>
      <el-form-item label="变更后图片" prop="moditfyAfImg">
        <el-input v-model="formData.moditfyAfImg" placeholder="请输入变更后图片" />
      </el-form-item>
      <el-form-item label="变更片数" prop="pieces">
        <el-input v-model="formData.pieces" placeholder="请输入变更片数" />
      </el-form-item>
      <el-form-item label="变更内容描述" prop="describe">
        <el-input v-model="formData.describe" placeholder="请输入变更内容描述" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" />
      </el-form-item>
      <el-form-item label="赔付类型：保险；运维" prop="claimType">
        <el-select v-model="formData.claimType" placeholder="请选择赔付类型：保险；运维">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否首位置变更" prop="isHeadModitfy">
        <el-input v-model="formData.isHeadModitfy" placeholder="请输入是否首位置变更" />
      </el-form-item>
      <el-form-item label="是否尾位置变更" prop="isTailModitfy">
        <el-input v-model="formData.isTailModitfy" placeholder="请输入是否尾位置变更" />
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
import { OpsMaintenanceGuardrailApi, OpsMaintenanceGuardrailVO } from '@/api/traffic/opsmaintenanceguardrail'

/** 护栏维护详细 表单 */
defineOptions({ name: 'OpsMaintenanceGuardrailForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  maintenanceId: undefined,
  guardrailId: undefined,
  maintenanceType: undefined,
  changeType: undefined,
  startName: undefined,
  startLng: undefined,
  startLat: undefined,
  endName: undefined,
  endLng: undefined,
  endLat: undefined,
  isGap: undefined,
  moditfyBeProduct: undefined,
  moditfyAfProduct: undefined,
  moditfyBeImg: undefined,
  moditfyAfImg: undefined,
  pieces: undefined,
  describe: undefined,
  remark: undefined,
  claimType: undefined,
  isHeadModitfy: undefined,
  isTailModitfy: undefined,
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
      formData.value = await OpsMaintenanceGuardrailApi.getOpsMaintenanceGuardrail(id)
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
    const data = formData.value as unknown as OpsMaintenanceGuardrailVO
    if (formType.value === 'create') {
      await OpsMaintenanceGuardrailApi.createOpsMaintenanceGuardrail(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsMaintenanceGuardrailApi.updateOpsMaintenanceGuardrail(data)
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
    maintenanceId: undefined,
    guardrailId: undefined,
    maintenanceType: undefined,
    changeType: undefined,
    startName: undefined,
    startLng: undefined,
    startLat: undefined,
    endName: undefined,
    endLng: undefined,
    endLat: undefined,
    isGap: undefined,
    moditfyBeProduct: undefined,
    moditfyAfProduct: undefined,
    moditfyBeImg: undefined,
    moditfyAfImg: undefined,
    pieces: undefined,
    describe: undefined,
    remark: undefined,
    claimType: undefined,
    isHeadModitfy: undefined,
    isTailModitfy: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>