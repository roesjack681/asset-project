<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="检测时间" prop="startDate">
        <el-date-picker
          v-model="queryDate"
          value-format="YYYY-MM-DD"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handleDateChange"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input
          v-model="queryParams.ipAddr"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="equName">
        <el-input
          v-model="queryParams.equName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="数据标签" prop="equLableList">
        <el-select
          v-model="queryParams.equLable"
          placeholder="请选择数据标签"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.POLICE_ELECTRIC_LABLE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
       </el-select>
      </el-form-item>
      <el-form-item label="ping状态" prop="pingStatus">
        <el-select
          v-model="queryParams.pingStatus"
          placeholder="请选择检测状态"
          clearable
          class="!w-240px"
        >
          <el-option label="全通" value="1"/>
          <el-option label="全断" value="2"/>
          <el-option label="时断时通" value="3"/>
        </el-select>
      </el-form-item>
      <el-form-item label="实时视频状态" prop="videoStatus">
        <el-select
          v-model="queryParams.videoStatus"
          placeholder="请选择检测状态"
          clearable
          class="!w-240px"
        >
          <el-option label="全通" value="1"/>
          <el-option label="全断" value="2"/>
          <el-option label="时断时通" value="3"/>
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
      <el-table-column label="设备名称" align="center" prop="equName" />
      <el-table-column label="设备标签" align="center" prop="equLable" />
      <el-table-column label="ping正常数" align="center" prop="pingNormalCnt" />
      <el-table-column label="ping异常数" align="center" prop="pingAbnormalCnt" />
      <el-table-column label="ping健康率" align="center">
        <template #default="scope">
          {{Number(scope.row.pingHealthRate * 100).toFixed(0)}}%
        </template>
      </el-table-column>
      <el-table-column label="ping状态" align="center">
        <template #default="scope">
          <el-text v-if="Number(scope.row.pingAbnormalCnt) == 0">全通</el-text>
          <el-text v-else-if="Number(scope.row.pingNormalCnt) == 0">全断</el-text>
          <el-text v-else>时断时通</el-text>
        </template>
      </el-table-column>
      <el-table-column label="视频正常数" align="center" prop="videoNormalCnt" />
      <el-table-column label="视频异常数" align="center" prop="videoAbnormalCnt" />
      <el-table-column label="视频健康率" align="center">
        <template #default="scope">
          {{Number(scope.row.videoHealthRate * 100).toFixed(0)}}%
        </template>
      </el-table-column>
      <el-table-column label="视频状态" align="center">
        <template #default="scope">
          <el-text v-if="Number(scope.row.videoAbnormalCnt) == 0">全通</el-text>
          <el-text v-else-if="Number(scope.row.videoNormalCnt) == 0">全断</el-text>
          <el-text v-else>时断时通</el-text>
        </template>
      </el-table-column>
      <el-table-column label="检测次数" align="center" prop="monitorCnt" />
      <el-table-column label="操作" align="center" min-width="60px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm(scope.row)"
            v-hasPermi="['police:monitor-results-import-analysis:detail']"
          >
            详情
          </el-button>
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

  <MonitorResultsHuazhiAnalysisForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { MonitorResultsImportAnalysisApi, MonitorResultsImportAnalysisVO } from '@/api/police/monitorresultsimportanalysis'
import MonitorResultsHuazhiAnalysisForm from './MonitorResultsHuazhiAnalysisForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 公安监测结果导入分析 列表 */
defineOptions({ name: 'PoliceMonitorResultsHuazhiAnalysis' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(false) // 列表的加载中
const queryDate = ref([])
const list = ref<MonitorResultsImportAnalysisVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equId: undefined,
  equName: undefined,
  ipAddr: undefined,
  equLable: undefined,
  pingStatus: undefined,
  videoStatus: undefined,
  eventType: undefined,
  startDate: undefined,
  endDate: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const handleDateChange = () => {
  if (queryDate.value && queryDate.value.length === 2) {
    queryParams.startDate = queryDate.value[0]
    queryParams.endDate = queryDate.value[1]
  } else {
    queryParams.startDate = undefined
    queryParams.endDate = undefined
  }
}

/** 查询列表 */
const getList = async () => {
  if (!queryParams.startDate || !queryParams.endDate) {
    message.warning('请选择查询日期！')
    return
  }
  loading.value = true
  try {
    const data = await MonitorResultsImportAnalysisApi.queryHuazhiMonitorOnOffResultPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (row: object) => {
  formRef.value.open(row.ipAddr, row.equId, queryParams.startDate)
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await MonitorResultsImportAnalysisApi.exportMonitorResultsHuazhiAnalysisExcel(queryParams)
    download.excel(data, '华智监测通断分析.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
})
</script>
