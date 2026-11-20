<template>
  <Dialog :title="dialogTitle" width="840" v-model="dialogVisible1">
    <ContentWrap>
      <!-- 搜索工作栏 -->
      <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryDetailFormRef"
        :inline="true"
        label-width="88px"
      >
        <el-form-item label="检测时间" prop="date">
          <el-date-picker
            @change="changedate"
            v-model="queryParams.date"
            placeholder="检测时间"
            value-format="YYYY-MM-DD"
            type="date"
            class="!w-220px"
          />
        </el-form-item>
        <el-form-item label="检测方式" prop="source">
          <el-select
            v-model="queryParams.source"
            placeholder="请选择检测方式"
            clearable
            class="!w-240px"
          >
            <el-option label="自动" value="1"/>
            <el-option label="手动" value="2"/>
          </el-select>
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
        <el-table-column label="设备IP" align="center" prop="ipAddr" />
        <el-table-column label="设备标签" align="center" prop="equLable" />
        <el-table-column
          label="检测时间"
          align="center"
          prop="occurrenceTime"
          :formatter="dateFormatter"
          width="180px"
        />
        <el-table-column label="ping状态" align="center" prop="pingStatus" />
        <el-table-column label="ping次数" align="center" prop="pingCnt" />
        <el-table-column label="丢包率" align="center" prop="lossRate" />
        <el-table-column label="检测方式" align="center" prop="source">
          <template #default="scope">
            <el-tag v-if="scope.row.source == 1">自动</el-tag>
            <el-tag type="danger" v-else>手动</el-tag>
          </template>
        </el-table-column>
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
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { MonitorPingDetailApi, MonitorPingDetailVO } from '@/api/police/monitorpingdetail'
import moment from 'moment'
/** 设备维保信息 表单 */
defineOptions({ name: 'MonitorPingDetailExportForm'})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible1 = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const exportLoading = ref(false) // 导出的加载中

const loading = ref(true) // 列表的加载中
const list = ref<MonitorPingDetailVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equId: undefined,
  ipAddr: undefined,
  equLable: undefined,
  occurrenceTime: [],
  pingStatus: undefined,
  pingCnt: undefined,
  lossRate: undefined,
  source: undefined,
  createTime: [],
  deletedTime: [],
  date: ''
})
const queryDetailFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MonitorPingDetailApi.getMonitorPingDetailPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const changedate = (val) => {
  if(val) {
    queryParams.occurrenceTime = [val + ' 00:00:00', val + ' 23:59:59']
  } else {
    queryParams.occurrenceTime = []
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
  queryParams.occurrenceTime = [queryParams.date + ' 00:00:00', queryParams.date + ' 23:59:59']
  handleQuery()
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await MonitorPingDetailApi.exportMonitorPingDetail(queryParams)
    download.excel(data, '公安监测通达详情.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 打开弹窗 */
const open = async () => {
  dialogVisible1.value = true
  dialogTitle.value = '通断检测详情导出'
  queryParams.date = moment().subtract(1, 'days').format("YYYY-MM-DD")
  queryParams.occurrenceTime = [queryParams.date + ' 00:00:00', queryParams.date + ' 23:59:59']
  setTimeout(() => {
    getList()
  }, 100);
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
