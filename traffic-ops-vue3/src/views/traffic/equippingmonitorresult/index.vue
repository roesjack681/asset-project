<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="84px"
    >
      <el-form-item label="设备IP" prop="ipAddr">
        <el-input
          v-model="queryParams.ipAddr"
          placeholder="请输入设备IP"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="检测时间" prop="occurrenceTime">
        <el-date-picker
          v-model="queryParams.occurrenceTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="ping状态" prop="pingStatus">
        <el-select
          v-model="queryParams.pingStatus"
          placeholder="请选择ping状态"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="执行批次号" prop="execBatchNum">
        <el-input
          v-model="queryParams.execBatchNum"
          placeholder="请输入执行批次号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
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
          v-hasPermi="['traffic:equip-ping-monitor-result:export']"
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
      <el-table-column label="执行批次号" align="center" prop="execBatchNum" />
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { EquipPingMonitorResultApi, EquipPingMonitorResultVO } from '@/api/traffic/equippingmonitorresult'

/** 重点考核设备通断检测 列表 */
defineOptions({ name: 'TrafficEquipPingMonitorResult' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<EquipPingMonitorResultVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equId: undefined,
  ipAddr: undefined,
  occurrenceTime: [],
  pingStatus: undefined,
  pingCnt: undefined,
  lossRate: undefined,
  source: undefined,
  execBatchNum: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await EquipPingMonitorResultApi.getEquipPingMonitorResultPage(queryParams)
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

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await EquipPingMonitorResultApi.exportEquipPingMonitorResult(queryParams)
    download.excel(data, '重点考核设备通断检测.xls')
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