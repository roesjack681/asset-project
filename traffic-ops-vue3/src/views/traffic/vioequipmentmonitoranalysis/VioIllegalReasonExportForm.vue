<template>
  <Dialog :title="dialogTitle" width="920" v-model="dialogVisible1">
    <ContentWrap>
      <!-- 搜索工作栏 -->
      <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryDetailFormRef"
        :inline="true"
        label-width="88px"
      >
        <el-form-item label="分析时间" prop="date">
          <el-date-picker
            @change="changedate"
            v-model="queryParams.occurrenceDay"
            placeholder="分析时间"
            value-format="YYYY-MM-DD"
            type="date"
            class="!w-220px"
          />
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
          <el-button
            type="success"
            plain
            @click="handleExport"
            :loading="exportLoading"
          >
            <Icon icon="ep:download" class="mr-5px" /> 导出
          </el-button>
        </el-form-item>
      </el-form>
    </ContentWrap>

    <!-- 列表 -->
    <ContentWrap>
      <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
        <el-table-column label="设备编号" align="center" prop="sbbh" />
        <el-table-column
          label="检测时间"
          align="center"
          prop="occurrenceTime"
          :formatter="dateFormatter"
          width="180px"
        />
        <el-table-column label="违法行为" align="center" prop="illegalReason" />
        <el-table-column label="违法次数" align="center" prop="illegalCnt" />
      </el-table>
      <!-- 分页 -->
      <Pagination
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </ContentWrap>
    <template #footer>
      <el-button @click="dialogVisible1 = false">关 闭</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import download from '@/utils/download'
import { VioEquipmentMonitorAnalysisApi, ViewVioEquipmentDailySituationVO } from '@/api/traffic/vioequipmentmonitoranalysis'
import moment from 'moment'
/** 设备维保信息 表单 */
defineOptions({ name: 'MonitorPingDetailExportForm'})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible1 = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const exportLoading = ref(false) // 导出的加载中

const loading = ref(true) // 列表的加载中
const list = ref<ViewVioEquipmentDailySituationVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  occurrenceDay: undefined,
  date: ''
})
const queryDetailFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await VioEquipmentMonitorAnalysisApi.getVioEquipmentIllegalAnalysisPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const changedate = (val) => {
  if(val) {
    queryParams.occurrenceDay = val
  } else {
    queryParams.occurrenceDay = undefined
  }

}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryDetailFormRef.value.resetFields()
  queryParams.occurrenceDay = queryParams.date
  handleQuery()
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await VioEquipmentMonitorAnalysisApi.exportVioEquipmentIllegalAnalysis(queryParams)
    download.excel(data, '违法抓拍有效违法行为分析导出.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 打开弹窗 */
const open = async () => {
  dialogVisible1.value = true
  dialogTitle.value = '违法抓拍有效违法行为分析导出'
  queryParams.date = moment().subtract(10, 'days').format("YYYY-MM-DD")
  queryParams.occurrenceDay = queryParams.date
  setTimeout(() => {
    getList()
  }, 100);
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
