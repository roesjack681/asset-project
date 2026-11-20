<template>
  <Dialog title="导入" width="600px" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="0px"
    >
      <el-form-item label-width="0px">
       <span>一、请按照数据模板的格式准备要导入的数据。点击下载</span>
        <a href="/公安电警核查导入模板.xlsx" download>《导入模板》</a>
        <div style="margin-left: 30px;color: rgba(0,0,0,0.6)">
          注意事项:
          <div>1、模板中的表头名称不能更改，表头行不能删除</div>
        </div>
      </el-form-item>
      <el-form-item label-width="0px">
        <span>二、请选择导入的文件</span>
      </el-form-item>
      <el-form-item prop="importFile" style="width: 100%">
        <div style="margin-left: 30px;color: rgba(0,0,0,0.6);width: 100%">
          <el-upload
            class="upload-demo"
            :action="importUrl"
            accept=".xlsx,.xls"
            :file-list="fileList"
            :before-upload="beforeAvatarUpload"
            :http-request="httpRequest"
            :on-success="handleFileSuccess"
            :on-change="uploadVideoProcess"
            :show-file-list="false"
          >
            <el-button size="small" type="primary" :disabled="formLoading">点击上传</el-button>
            <template #tip>
              <div class="el-upload__tip">只能上传 xlsx/xls 文件，且不超过 500MB</div>
            </template>
          </el-upload>
          <div v-if="fileList.length">
            <div>{{fileList[0].name}}</div>
            <div v-if="progressFlag && fileList.length">
              <el-progress  v-if="!prostatus" :percentage="percentage" />
              <el-progress class="error" color="red" v-else :format="format" :percentage="percentage" />
            </div>
          </div>
        </div>
      </el-form-item>
      <el-form-item prop="importFile" style="width: 100%" v-if="importResult.importNum">
        <div style="margin-left: 30px;color: rgba(0,0,0,0.6)">
          <div style="font-weight: bold">导入结果 
            <a :href="importResult.failFile" download>《下载导入结果》</a>
          </div>
          <div>1、导入总数 <span>{{importResult.importNum}} </span> 条</div>
          <div>2、导入成功 <span style="color: #07c160">{{importResult.sucNum}}</span> 条</div>
          <div>3、导入失败 <span style="color: red">{{importResult.failNum}}</span> 条</div>
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">关 闭</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
// import { ImportRecordApi, ImportRecordVO } from '@/api/ebike/importrecord'
import {useUpload} from "@/components/UploadFile/src/useUploadPoliceMonitorImport";

/** Excel导入 表单 */
defineOptions({ name: 'ImportRecordForm' })
const { uploadUrl, httpRequest } = useUpload()
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const importUrl = import.meta.env.VITE_BASE_URL + import.meta.env.VITE_API_URL + '/police/monitor-results/importPoliceMonitorInfo'
const dialogVisible = ref(false) // 弹窗的是否展示
const progressFlag = ref(false)
const percentage = ref(0)
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  importFile: undefined,
  importNum: undefined,
  sucNum: undefined,
  failNum: undefined,
  failFile: undefined
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref
const fileList = ref([])
const importResult = ref({})
const prostatus = ref(null)

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  importResult.value = {}
  fileList.value = []
  percentage.value = 0
  progressFlag.value = false // 显示进度条
  dialogTitle.value = t('action.import')
  formType.value = type
  prostatus.value = null
  resetForm()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    importFile: undefined,
    importNum: undefined,
    sucNum: undefined,
    failNum: undefined,
    failFile: undefined
  }
  formRef.value?.resetFields()
}

const beforeAvatarUpload = (file) => {
  formLoading.value = true
  importResult.value = {}
  // const isJPG = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || file.type === 'application/vnd.ms-excel'
  const isLt2M = file.size / 1024 / 1024 < 500

  // if (!isJPG) {
  //   formLoading.value = false
  //   message.error('导入格式错误!')
  // }
  if (!isLt2M) {
    formLoading.value = false
    message.error('导入文件大小不能超过 500MB!')
  }
  if(isLt2M) {
    fileList.value = [file]
  }
  return isLt2M
}
const format = (percentage) => {
  return percentage != 100 ? '导入失败' : `${percentage}%`
}
// 文件上传成功
const handleFileSuccess: UploadProps['onSuccess'] = (res: any): void => {
  formLoading.value = false
  if(!res.data) {
    prostatus.value = 'exception'
    if(interval.value) {
      clearInterval(interval.value)
      interval.value = null
    }
    emit('success')
  } else {
    percentage.value = 100
    prostatus.value = null
    importResult.value = res.data
    message.success('导入成功')
    emit('success')
  }
}
const interval = ref(null)
const uploadVideoProcess = async (event: any) => {
  if (event.status === 'ready') {
    percentage.value = 0
    progressFlag.value = true // 显示进度条
    interval.value = setInterval(() => {
      if (percentage.value >= 98) {
        clearInterval(interval.value)
        return
      }
      percentage.value += 1 //进度条进度
    }, 100)
  } else if(event.status === 'success') {
    progressFlag.value = true
  }
}
</script>
<style lang="scss">
.error {
  .el-progress__text {
    color: red!important;
    font-size: 13px!important;
  }
}
</style>