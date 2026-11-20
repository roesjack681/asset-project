<template>
  <Dialog :title="dialogTitle" width="640" v-model="dialogVisible1">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-row :gutter="20" style="width: 100%">
        <el-col :span="12">
          <el-form-item label="设备IP" prop="ipAddr">
            <span>{{ formData.ipAddr }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备标签" prop="equLable">
            <span>{{ formData.equLable }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20" style="width: 100%">
        <el-col :span="12">
          <el-form-item label="检测时间" prop="occurrenceTime">
            <span>{{ formattime(formData.occurrenceTime) }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="ping状态" prop="pingStatus">
            <span>
              <el-tag v-if="formData.pingStatus == 'IP不可达'" type="danger">IP不可达</el-tag>
              <el-tag v-if="formData.pingStatus == '正常'" type="success">正常</el-tag>
            </span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20" style="width: 100%">
        <el-col :span="12">
          <el-form-item label="ping次数" prop="pingCnt">
            <span>{{ formData.pingCnt }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="丢包率" prop="lossRate">
            <span>{{ formData.lossRate }}%</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible1 = false">关 闭</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import moment from 'moment'
/** 设备维保信息 表单 */
defineOptions({ name: 'MonitorPingResultForm'})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible1 = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref({
  id: undefined,
  equId: undefined,
  ipAddr: undefined,
  equLable: undefined,
  occurrenceTime: undefined,
  pingStatus: undefined,
  pingCnt: undefined,
  lossRate: undefined,
  source: undefined
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (pingResult: any) => {
  dialogVisible1.value = true
  dialogTitle.value = 'ping检测结果'
  resetForm()
  formData.value = pingResult
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const formattime = (val) => {
  if(val) {
    return moment(val).format('YYYY-MM-DD HH:mm:ss')
  }
  return ''
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equId: undefined,
    ipAddr: undefined,
    equLable: undefined,
    occurrenceTime: undefined,
    pingStatus: undefined,
    pingCnt: undefined,
    lossRate: undefined,
    source: undefined
  }
  formRef.value?.resetFields()
}
</script>
