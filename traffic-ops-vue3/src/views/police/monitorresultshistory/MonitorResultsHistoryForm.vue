<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="检测日期" prop="monitorDate">
        <el-date-picker
          v-model="formData.monitorDate"
          type="date"
          value-format="x"
          placeholder="选择检测日期"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="equType">
        <el-select v-model="formData.equType" placeholder="请选择设备类型">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="设备id" prop="equId">
        <el-input v-model="formData.equId" placeholder="请输入设备id" />
      </el-form-item>
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input v-model="formData.ipAddr" placeholder="请输入IP地址" />
      </el-form-item>
      <el-form-item label="设备名称" prop="equName">
        <el-input v-model="formData.equName" placeholder="请输入设备名称" />
      </el-form-item>
      <el-form-item label="杆件编号" prop="memberBh">
        <el-input v-model="formData.memberBh" placeholder="请输入杆件编号" />
      </el-form-item>
      <el-form-item label="方向" prop="direction">
        <el-input v-model="formData.direction" placeholder="请输入方向" />
      </el-form-item>
      <el-form-item label="检测事件" prop="eventType">
        <el-select v-model="formData.eventType" placeholder="请选择检测事件">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="检测时间" prop="occurrenceTime">
        <el-date-picker
          v-model="formData.occurrenceTime"
          type="date"
          value-format="x"
          placeholder="选择检测时间"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio value="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="检测结果图片" prop="resultImg">
        <el-input v-model="formData.resultImg" placeholder="请输入检测结果图片" />
      </el-form-item>
      <el-form-item label="是否为工单" prop="isRepairOrder">
        <el-input v-model="formData.isRepairOrder" placeholder="请输入是否为工单" />
      </el-form-item>
      <el-form-item label="维保主键" prop="maintenanceId">
        <el-input v-model="formData.maintenanceId" placeholder="请输入维保主键" />
      </el-form-item>
      <el-form-item label="维护人员编号" prop="maintenanceUserId">
        <el-input v-model="formData.maintenanceUserId" placeholder="请输入维护人员编号" />
      </el-form-item>
      <el-form-item label="维护人员" prop="maintenancePersonnel">
        <el-input v-model="formData.maintenancePersonnel" placeholder="请输入维护人员" />
      </el-form-item>
      <el-form-item label="维护人员手机号" prop="maintenancePhone">
        <el-input v-model="formData.maintenancePhone" placeholder="请输入维护人员手机号" />
      </el-form-item>
      <el-form-item label="设备情况" prop="equSituation">
        <el-input v-model="formData.equSituation" placeholder="请输入设备情况" />
      </el-form-item>
      <el-form-item label="设备情况描述" prop="equDesc">
        <el-input v-model="formData.equDesc" placeholder="请输入设备情况描述" />
      </el-form-item>
      <el-form-item label="设备情况证据" prop="evidenceFileId">
        <el-input v-model="formData.evidenceFileId" placeholder="请输入设备情况证据" />
      </el-form-item>
      <el-form-item label="备注时间" prop="remarkDate">
        <el-date-picker
          v-model="formData.remarkDate"
          type="date"
          value-format="x"
          placeholder="选择备注时间"
        />
      </el-form-item>
      <el-form-item label="变更情况：0-未变化；1-已修复；2-未修复" prop="changeSituation">
        <el-input v-model="formData.changeSituation" placeholder="请输入变更情况：0-未变化；1-已修复；2-未修复" />
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
import { MonitorResultsHistoryApi, MonitorResultsHistoryVO } from '@/api/police/monitorresultshistory'

/** 公安设备监测结果历史 表单 */
defineOptions({ name: 'MonitorResultsHistoryForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  monitorDate: undefined,
  equType: undefined,
  equId: undefined,
  ipAddr: undefined,
  equName: undefined,
  memberBh: undefined,
  direction: undefined,
  eventType: undefined,
  occurrenceTime: undefined,
  status: undefined,
  resultImg: undefined,
  isRepairOrder: undefined,
  maintenanceId: undefined,
  maintenanceUserId: undefined,
  maintenancePersonnel: undefined,
  maintenancePhone: undefined,
  equSituation: undefined,
  equDesc: undefined,
  evidenceFileId: undefined,
  remarkDate: undefined,
  changeSituation: undefined,
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
      formData.value = await MonitorResultsHistoryApi.getMonitorResultsHistory(id)
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
    const data = formData.value as unknown as MonitorResultsHistoryVO
    if (formType.value === 'create') {
      await MonitorResultsHistoryApi.createMonitorResultsHistory(data)
      message.success(t('common.createSuccess'))
    } else {
      await MonitorResultsHistoryApi.updateMonitorResultsHistory(data)
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
    monitorDate: undefined,
    equType: undefined,
    equId: undefined,
    ipAddr: undefined,
    equName: undefined,
    memberBh: undefined,
    direction: undefined,
    eventType: undefined,
    occurrenceTime: undefined,
    status: undefined,
    resultImg: undefined,
    isRepairOrder: undefined,
    maintenanceId: undefined,
    maintenanceUserId: undefined,
    maintenancePersonnel: undefined,
    maintenancePhone: undefined,
    equSituation: undefined,
    equDesc: undefined,
    evidenceFileId: undefined,
    remarkDate: undefined,
    changeSituation: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>