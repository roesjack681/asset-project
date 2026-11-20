<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="120px"
    >
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
      <el-form-item label="是否在保" prop="underWarranty">
        <el-select
          v-model="queryParams.underWarranty"
          placeholder="请选择是或否"
          clearable
          class="!w-240px"
        >
          <el-option label="是" value="1"/>
          <el-option label="否" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="有无照片" prop="resultImg">
        <el-select
          v-model="queryParams.resultImg"
          placeholder="请选择是或否"
          clearable
          class="!w-240px"
        >
          <el-option label="有" value="1"/>
          <el-option label="无" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="有无检测情况" prop="monitorSituation">
        <el-select
          v-model="queryParams.monitorSituation"
          placeholder="请选择是或否"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.INFRA_BOOLEAN_STRING)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测情况" prop="monitorSituationList">
        <el-select
          v-model="queryParams.monitorSituationList"
          multiple
          placeholder="请选择检测情况"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.monitor_situation)"
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
          <el-option label="正常" value="正常"/>
          <el-option label="IP不可达" value="IP不可达"/>
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
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="道路名称" align="center" prop="memberName" min-width="145">
        <template #header>
          <span>杆件</span><span style="color: red;">(道路名称/交叉口名称/方向/杆件编号)</span>
        </template>
      </el-table-column>
      <el-table-column label="IP地址" align="center" prop="ipAddr" />
      <el-table-column
        label="检测时间"
        align="center"
        prop="occurrenceTime"
        :formatter="dateFormatter2"
        width="180px"
      />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="是否在保" align="center" prop="underWarranty" />
      <el-table-column label="检测结果照片" align="center">
        <template #default="scope">
          <div class="qr-code-container">
            <el-image
              style="display: flex; flex-wrap: wrap; cursor: pointer;"
              :src="scope.row.resultImg"
              fit="cover"
              v-if="scope.row.resultImg"
              :z-index="2001"
              :preview-src-list="[scope.row.resultImg]"
              :preview-teleported="true"
              @click.stop
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="检测情况" align="center" prop="monitorSituation" />
      <el-table-column label="备注情况" align="center" prop="equDesc" />
      <el-table-column label="备注人" align="center" prop="remarkName" />
      <el-table-column
        label="备注时间"
        align="center"
        prop="remarkDate"
        :formatter="dateFormatter"
        width="180px"
      />
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
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import download from '@/utils/download'
import { MonitorResultsImportDetailApi, MonitorResultsImportDetailVO } from '@/api/traffic/monitorresultsimportdetail'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 设备监测结果导入信息 列表 */
defineOptions({ name: 'TrafficMonitorResultsImportDetail' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<MonitorResultsImportDetailVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equId: undefined,
  ipAddr: undefined,
  roadName: undefined,
  intersectionName: undefined,
  memberBh: undefined,
  direction: undefined,
  occurrenceTime: [],
  status: undefined,
  equSituation: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MonitorResultsImportDetailApi.getMonitorResultsImportDetailPage(queryParams)
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

// /** 导出按钮操作 */
// const handleExport = async () => {
//   try {
//     // 导出的二次确认
//     await message.exportConfirm()
//     // 发起导出
//     exportLoading.value = true
//     const data = await MonitorResultsImportDetailApi.exportMonitorResultsImportDetail(queryParams)
//     download.excel(data, '设备监测结果导入信息.xls')
//   } catch {
//   } finally {
//     exportLoading.value = false
//   }
// }

const importFormRef = ref()
const openImportForm = () => {
  importFormRef.value.open()
}

const remarkFormRef = ref()
const openRemarkForm = (id) => {
  remarkFormRef.value.open(id)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
