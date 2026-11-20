<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="150px"
    >
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input
          v-model="queryParams.ipAddr"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-170px"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="equName">
        <el-input
          v-model="queryParams.equName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-170px"
        />
      </el-form-item>
      <el-form-item label="数据标签" prop="equLableList">
        <el-select
          v-model="queryParams.equLableList"
          multiple
          placeholder="请选择数据标签"
          clearable
          class="!w-170px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.POLICE_ELECTRIC_LABLE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
       </el-select>
      </el-form-item>
      <el-form-item label="行政区域" prop="unitList">
        <el-select v-model="queryParams.unitList" multiple filterable clearable class="!w-170px">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
            :key="dict.label"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测状态" prop="status">
        <div style="display: flex">
          <el-radio-group v-model="queryParams.source">
            <el-radio style="margin-right: 4px" label="1">自动</el-radio>
            <el-radio style="margin-right: 4px" label="2">手动</el-radio>
          </el-radio-group>
          <el-select
            v-model="queryParams.status"
            placeholder="请选择检测状态"
            clearable
            class="!w-170px"
          >
            <el-option label="全断" value="0"/>
            <el-option label="全通" value="1"/>
            <el-option label="时断时通" value="2"/>
          </el-select>
        </div>
      </el-form-item>
      <el-form-item label="近两次ping状态变化" prop="finalPingStatus">
        <el-select
          v-model="queryParams.finalPingStatus"
          placeholder="请选择ping状态"
          clearable
          class="!w-170px"
        >
          <el-option label="全正常" value="全正常"/>
          <el-option label="新上线" value="新上线"/>
          <el-option label="掉线" value="掉线"/>
          <el-option label="全异常" value="全异常"/>
       </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openImportForm()"
          v-hasPermi="['police:monitor-ping-analysis:import']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 导入
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['police:monitor-ping-analysis:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="IP地址" align="center" prop="ipAddr" />
      <el-table-column label="设备名称" align="center" min-width="120" prop="equName" />
      <el-table-column label="设备标签" align="center" prop="equLable" />
      <el-table-column label="自动ping正常数" align="center" prop="autoPingNormalCnt" />
      <el-table-column label="自动ping异常数" align="center" prop="autoPingAbnormalCnt" />
      <el-table-column label="自动ping健康率" align="center" prop="autoPingHealthRate" />
      <el-table-column label="自动检测次数" align="center" prop="autoMonitorCnt" />
      <el-table-column label="ping正常数" align="center" prop="pingNormalCnt" />
      <el-table-column label="ping异常数" align="center" prop="pingAbnormalCnt" />
      <el-table-column label="ping健康率" align="center" prop="pingHealthRate" />
      <el-table-column label="检测次数" align="center" prop="monitorCnt" />
      <el-table-column label="近两次ping状态变化" align="center" prop="finalPingStatus" />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm(scope.row.equName, scope.row.equId)"
          >
            详情
          </el-button>
          <el-button
            link
            type="primary"
            @click="handlePing(scope.row.equId)"
          >
            ping
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

  <!-- 表单弹窗：添加/修改 -->
  <MonitorPingAnalysisForm ref="formRef" @success="getList" />
  <MonitorPingDetailForm ref="detailRef" @success="getList" />
  <MonitorPingDetailImportForm ref="importFormRef" @success="getList" />
  <MonitorPingDetailExportForm ref="detailExportRef" @success="getList" />
  <MonitorPingResultForm ref="pingResultFormRef" @success="getList" />

</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { MonitorPingAnalysisApi, MonitorPingAnalysisVO } from '@/api/police/monitorpinganalysis'
import MonitorPingAnalysisForm from './MonitorPingAnalysisForm.vue'
import MonitorPingDetailExportForm from './MonitorPingDetailExportForm.vue'
import MonitorPingDetailImportForm from './MonitorPingDetailImportForm.vue'
import MonitorPingDetailForm from './MonitorPingDetailForm.vue'
import MonitorPingResultForm from '../common/PoliceMonitorPingResultForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 电警通断检测管理 列表 */
defineOptions({ name: 'PoliceMonitorPingAnalysis' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<MonitorPingAnalysisVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equName: undefined,
  equLableList: undefined,
  unitList: undefined,
  ipAddr: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MonitorPingAnalysisApi.getMonitorPingAnalysisPage(queryParams)
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

const importFormRef = ref()
const openImportForm = () => {
  importFormRef.value.open()
}

/** 添加/修改操作 */
const detailRef = ref()
const openForm = (equName?: String, equId?: String) => {
  detailRef.value.open(equName, equId)
}

/** ping通断操作 */
const pingResultFormRef = ref();
const handlePing = async (id: any) => {
  try {
    // ping的二次确认
    await message.confirm('确认执行ping检测?')
    loading.value = true
    // 发起删除
    const data = await MonitorPingAnalysisApi.pingPoliceOpsElectricPolice(id)
    pingResultFormRef.value.open(data)
  } finally {
    loading.value = false
  }
}

/** 导出按钮操作 */
const detailExportRef = ref()
const handleExport = async () => {
  detailExportRef.value.open()
}
// const handleExport = async () => {
//   try {
//     // 导出的二次确认
//     await message.exportConfirm()
//     // 发起导出
//     exportLoading.value = true
//     const data = await MonitorPingAnalysisApi.exportMonitorPingAnalysis(queryParams)
//     download.excel(data, '电警通断检测管理.xls')
//   } catch {
//   } finally {
//     exportLoading.value = false
//   }
// }

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
