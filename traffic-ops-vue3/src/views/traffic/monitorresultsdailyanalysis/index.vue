<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="118px"
    >
      <el-form-item label="分析时间" prop="analysisDate">
        <el-date-picker
          v-model="queryParams.analysisDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="道路名称" prop="roadName">
        <el-input
          v-model="queryParams.roadName"
          placeholder="请输入道路名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="交叉口名称" prop="intersectionName">
        <el-input
          v-model="queryParams.intersectionName"
          placeholder="请输入交叉口名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="方向" prop="direction">
        <el-input
          v-model="queryParams.direction"
          placeholder="请输入方向"
          clearable
          @keyup.enter="handleQuery"
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
      <el-form-item label="电警类型" prop="djType">
        <el-select
          v-model="queryParams.djType"
          placeholder="请选择电警类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.DJ_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="数据标签" prop="equLableList">
        <el-select
          v-model="queryParams.equLableList"
          multiple
          placeholder="请选择数据标签"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.ELECTRIC_LABLE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
       </el-select>
      </el-form-item>
      <el-form-item label="检测事件" prop="eventType">
        <el-select
          v-model="queryParams.eventType"
          placeholder="请选择检测事件"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.MONITOR_EVENT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择检测状态"
          clearable
          class="!w-240px"
        >
          <el-option label="全断" value="0"/>
          <el-option label="全通" value="1"/>
          <el-option label="时断时通" value="2"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :border="true" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column
        label="分析时间"
        align="center"
        prop="analysisDate"
        :formatter="dateFormatter2"
        width="180px"
      />
      <el-table-column label="道路名称" align="center" prop="memberName" min-width="300">
        <template #header>
          <span>杆件</span><span style="color: red;">(道路名称/交叉口名称/方向/杆件编号)</span>
        </template>
      </el-table-column>
      <el-table-column label="IP地址" align="center" prop="ipAddr" />
      <el-table-column label="检测类型" align="center" prop="eventType" />
      <el-table-column label="正常数" align="center" prop="normalCnt" />
      <el-table-column label="异常数" align="center" prop="abnormalCnt" />
      <el-table-column label="总检测次数" align="center" prop="monitorSumCnt" />
      <el-table-column label="总检测天数" align="center" prop="monitorSumDays" />
      <el-table-column label="健康率" align="center" prop="healthRate" />
      <el-table-column label="状态" align="center" >
        <template #default="scope">
          <el-text v-if="Number(scope.row.abnormalCnt) == 0">全通</el-text>
          <el-text v-else-if="Number(scope.row.normalCnt) == 0">全断</el-text>
          <el-text v-else>时断时通</el-text>
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

  <!-- 表单弹窗：添加/修改 -->
  <MonitorResultsDailyAnalysisForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter2 } from '@/utils/formatTime'
import download from '@/utils/download'
import { MonitorResultsDailyAnalysisApi, MonitorResultsDailyAnalysisVO } from '@/api/traffic/monitorresultsdailyanalysis'
import MonitorResultsDailyAnalysisForm from './MonitorResultsDailyAnalysisForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 设备监测结果每日分析 列表 */
defineOptions({ name: 'TrafficMonitorResultsDailyAnalysis' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<MonitorResultsDailyAnalysisVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  analysisDate: [],
  equId: undefined,
  memberBh: undefined,
  roadName: undefined,
  intersectionName: undefined,
  direction: undefined,
  ipAddr: undefined,
  djType: undefined,
  equLable: undefined,
  eventType: undefined,
  normalCnt: undefined,
  abnormalCnt: undefined,
  monitorSumDays: undefined,
  monitorSumCnt: undefined,
  healthRate: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MonitorResultsDailyAnalysisApi.getMonitorResultsDailyAnalysisPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
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

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await MonitorResultsDailyAnalysisApi.deleteMonitorResultsDailyAnalysis(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await MonitorResultsDailyAnalysisApi.exportMonitorResultsDailyAnalysis(queryParams)
    download.excel(data, '设备监测结果每日分析.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>