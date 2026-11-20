<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="110px"
    >
<!--      <el-form-item label="电警类型" prop="djType">-->
<!--        <el-select-->
<!--          v-model="queryParams.djType"-->
<!--          placeholder="请选择电警类型"-->
<!--          clearable-->
<!--          class="!w-240px"-->
<!--        >-->
<!--          <el-option-->
<!--            v-for="dict in getStrDictOptions(DICT_TYPE.DJ_TYPE)"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否有卡口编号" prop="kkbh">-->
<!--        <el-select-->
<!--          v-model="queryParams.kkbh"-->
<!--          placeholder="请选择是否有卡口编号"-->
<!--          clearable-->
<!--          class="!w-240px"-->
<!--        >-->
<!--          <el-option-->
<!--            v-for="dict in getStrDictOptions(DICT_TYPE.INFRA_BOOLEAN_STRING)"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--       </el-select>-->
<!--      </el-form-item>-->
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
      <el-form-item label="日期" prop="occurrenceDay">
        <el-date-picker v-model="queryParams.occurrenceDay" value-format="YYYYMMDD" :disabled-date="disabledDate" type="date" placeholder="选择日期"/>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :border="true" :data="list" :show-overflow-tooltip="true">
      <el-table-column label="" align="center" prop="memberName" min-width="240">
        <template #header>
          <span>杆件</span><span style="color: red;">(道路名称/交叉口名称/方向/杆件编号)</span>
        </template>
      </el-table-column>
      <el-table-column label="IP地址" align="center" prop="ipAddr" />
      <el-table-column label="检测类型" align="center" min-width="72" prop="eventType"/>
      <el-table-column label="正常次数" align="center" min-width="72" prop="normalCnt" />
      <el-table-column label="异常次数" align="center" min-width="72" prop="abnormalCnt" />
      <el-table-column label="总检测次数" align="center" prop="monitorSumCnt" />
      <el-table-column label="总检测天数" align="center" prop="monitorSumDays"/>
      <el-table-column label="健康率" align="center" min-width="62" prop="healthRate"/>
      <el-table-column label="状态" align="center" >
        <template #default="scope">
          <el-text v-if="Number(scope.row.healthRate) == 1">全通</el-text>
          <el-text v-else-if="Number(scope.row.healthRate) == 0">全断</el-text>
          <el-text v-else>时断时通</el-text>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="82px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openDetailForm(scope.row.memberName, scope.row.ipAddr, scope.row.eventType)"
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

  <!-- 表单弹窗：添加/修改 -->
  <ImportRecordForm ref="importFormRef" @success="getList" />
  <OpsMonitorDetailForm ref="detailFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { DjMonitorStatisticsApi, ViewOpsMonitorResultsVO } from '@/api/traffic/djmonitorstatistics'
import ImportRecordForm from './OpsDjMonitorStatisticsImportForm.vue'
import OpsMonitorDetailForm from './OpsMonitorDetailForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import moment from 'moment'
/** 监测电警设备统计 列表 */
defineOptions({ name: 'TrafficDjMonitorStatistics' })
// 设置组件属性
const props = defineProps({
  timerange: {
    type: Array,
    default: () => [],
    required: true
  }
})
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<ViewOpsMonitorResultsVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  djType: '电子警察',
  occurrenceDay: null,
  kkbh: undefined,
  roadName: undefined,
  intersectionName: undefined,
  direction: undefined,
  ipAddr: undefined,
  eventType: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中


/** 查询列表 */
const getList = async () => {
  queryParams.occurrenceDay = moment(props.timerange[1]).format('YYYYMMDD')
  if(!queryParams.occurrenceDay) return message.error('请选择查询日期')
  loading.value = true
  try {
    const data = await DjMonitorStatisticsApi.getViewOpsMonitorDailyResultsPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

// const objectSpanMethod = ({ row, column, rowIndex, columnIndex }) => {
//   if (columnIndex !== 0 && columnIndex !== 0) {
//     return { rowspan: 1, colspan: 1 };
//   }

//   // 如果当前行是该组织机构的第一行，则计算该组织机构的行数，并返回 { rowspan, colspan: 1 }，表示需要合并的行数为 rowspan
//   if (rowIndex === 0 || row.memberName !== list.value[rowIndex - 1].memberName) {
//     const rowCount = list.value.filter((i) => i.memberName === row.memberName).length;
//     return { rowspan: rowCount, colspan: 1 };
//   }
//   // 否则返回 { rowspan: 0, colspan: 0 }，表示该单元格已被上方单元格合并
//   return { rowspan: 0, colspan: 0 };

// }

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  // queryFormRef.value.resetFields()
  queryParams.status = ''
  handleQuery()
}

const importFormRef = ref()
const openImportForm = () => {
  importFormRef.value.open()
}

const detailFormRef = ref()
const openDetailForm = (memberName:string, ipAddr: string, eventType: string) => {
  detailFormRef.value.open(memberName, ipAddr, eventType)
}

const disabledDate = (time) => {
  const last = moment(props.timerange[0]).subtract(1, 'days').format('YYYY-MM-DD')
  return time.getTime() >new Date(props.timerange[1]).getTime() || time.getTime() <= new Date(last).getTime()
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
