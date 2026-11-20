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
      <!-- <el-form-item label="检测事件" prop="eventType">
        <el-select
          v-model="queryParams.eventType"
          placeholder="请选择检测事件"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item> -->
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
      <el-form-item label="维护人员" prop="maintenancePersonnel">
        <el-input
          v-model="queryParams.maintenancePersonnel"
          placeholder="请输入维护人员"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="是否有工单" prop="isRepairOrder">
        <el-select
          v-model="queryParams.isRepairOrder"
          placeholder="请选择是或否"
          clearable
          class="!w-240px"
        >
          <el-option label="是" value="1"/>
          <el-option label="否" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option label="正常" value="正常"/>
          <el-option label="IP不可达" value="IP不可达"/>
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
      <el-form-item label="有无现场照片" prop="evidenceFileId">
        <el-select
          v-model="queryParams.evidenceFileId"
          placeholder="请选择是或否"
          clearable
          class="!w-240px"
        >
          <el-option label="有" value="1"/>
          <el-option label="无" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="设备情况" prop="equSituationList">
        <div style="display: flex">
          <el-radio-group v-model="queryParams.includeEquSituation">
            <el-radio style="margin-right: 4px" label="1">包含</el-radio>
            <el-radio label="2">不包含</el-radio>
          </el-radio-group>
          <el-select style="margin-left: 10px" v-model="queryParams.equSituationList" multiple filterable clearable class="!w-172px">
            <el-option v-if="queryParams.includeEquSituation==1" key="0" label="无" value="0" />
            <el-option
              v-for="dict in getStrDictOptions(DICT_TYPE.EQU_SITUATION)"
              :key="dict.label"
              :label="dict.label"
              :value="dict.label"
            />
          </el-select>
        </div>
      </el-form-item>
      <el-form-item label="情况描述" prop="equDesc">
        <el-input
          v-model="queryParams.equDesc"
          placeholder="请输入情况描述"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
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
            v-for="dict in getStrDictOptions(DICT_TYPE.POLICE_ELECTRIC_LABLE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
       </el-select>
      </el-form-item>
      <el-form-item label="是否绑定杆件" prop="memberBh">
        <el-select
          v-model="queryParams.memberBh"
          placeholder="请选择是否绑定"
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
      <el-form-item label="行政区域" prop="unitList">
        <el-select v-model="queryParams.unitList" multiple filterable clearable class="!w-240px">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
            :key="dict.label"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="修改时间" prop="updateTime">
        <el-date-picker
          v-model="queryParams.updateTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="备注时间" prop="remarkDate">
        <el-date-picker
          v-model="queryParams.remarkDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openImportForm()"
          v-hasPermi="['police:monitor-results:import']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 导入
        </el-button>
        <el-popover v-hasPermi="['police:monitor-results:export']" placement="top" :width="220" v-model:visible="visible">
          <div style="text-align: right; margin: 0;display: flex;overflow: hidden">
            <el-button @click="handleExport(0)" size="mini" type="default" style="font-size: 12px">不导出照片</el-button>
            <el-button @click="handleExport(1)" type="primary" size="mini" style="font-size: 12px">导出照片</el-button>
          </div>
          <template #reference>
            <el-button
              type="success"
              plain
              :loading="exportLoading"
              @click="visible = true"
            >
              <Icon icon="ep:download" class="mr-5px" /> 导出
            </el-button>
          </template>
        </el-popover>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="IP地址" align="center" prop="ipAddr" />
      <el-table-column label="设备名称" align="center" prop="equName" />
      <el-table-column label="行政区域" align="center" prop="unit" />
      <el-table-column label="设备标签" align="center" prop="equLabel" />
      <!-- <el-table-column label="方向" align="center" prop="direction" /> -->
      <el-table-column
        label="检测时间"
        align="center"
        prop="occurrenceTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="状态" align="center" prop="status" />
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
            <el-button style="margin-top: 10px; width: 54px;" v-hasPermi="['police:monitor-results:resultImgUpload']" type="primary" @click="openUploadBox(scope.row.id)" size="small">上传图片</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="是否有工单" align="center" prop="isRepairOrder" >
        <template #default="scope">
          <el-tag v-if="scope.row.isRepairOrder == 1">是</el-tag>
          <el-tag type="danger" v-else>否</el-tag>
          <icon v-if="scope.row.maintenanceId" @click="openDetailForm('detail', scope.row.maintenanceId)" style="color:#3F9EFF;margin-left: 5px;" icon="ep:search" :size="12" type="Success"/>
        </template>
      </el-table-column>
      <el-table-column label="是否绑定杆件" align="center" prop="isBindMember" />
      <el-table-column label="维护人员" align="center" prop="maintenancePersonnel" />
      <el-table-column label="设备情况" align="center" prop="equSituation" />
      <el-table-column label="情况描述" align="center" prop="equDesc" />
      <el-table-column
        label="备注时间"
        align="center"
        prop="remarkDate"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="修改时间"
        align="center"
        prop="updateTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="现场照片" align="center">
        <template #default="scope">
          <div class="qr-code-container">
            <el-image
              style="display: flex; flex-wrap: wrap; cursor: pointer;"
              :src="scope.row.evidenceFileId"
              fit="cover"
              v-if="scope.row.evidenceFileId"
              :z-index="2001"
              :preview-src-list="[scope.row.evidenceFileId]"
              :preview-teleported="true"
              @click.stop
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="修改人" align="center" prop="updater" />
      <el-table-column label="操作" align="center" width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openRemarkForm(scope.row.id)"
            v-hasPermi="['police:monitor-results:remark']"
          >
            备注
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
  <ImportRecordForm ref="importFormRef" @success="getList" />
  <MonitorRemarkForm ref="remarkFormRef" @success="getList" />
  <OpsMaintenanceForm ref="detailFormRef" @success="getList" />
  <MonitorPingResultForm ref="pingResultFormRef" @success="getList" />
  <el-dialog
    title="上传检测结果照片"
    v-model="showUpload"
    width="30%"
    :before-close="handleClose"
  >
    <UploadImg style="display: block;width: 125px;margin: 0 auto" v-model="resultImg" height="125px" width="125px" />
    <template #footer>
    <span class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="submitResultImg">确 定</el-button>
    </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { MonitorResultsApi, MonitorResultsVO } from '@/api/police/monitorresults'
import { MonitorPingAnalysisApi } from '@/api/police/monitorpinganalysis'
import ImportRecordForm from './PoliceMonitorImportForm.vue'
import OpsMaintenanceForm from '../optmaintenance/OpsMaintenanceForm.vue'
import MonitorRemarkForm from './MonitorRemarkForm.vue'
import MonitorPingResultForm from '../common/PoliceMonitorPingResultForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 公安设备监测结果 列表 */
defineOptions({ name: 'PoliceMonitorResults' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<MonitorResultsVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const showUpload = ref(false) // 上传
const visible = ref(false) // 导出
const resultImg = ref('') // 上传
const selectId = ref('') // 当前选中数据的id
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equType: undefined,
  equId: undefined,
  ipAddr: undefined,
  equName: undefined,
  memberBh: undefined,
  direction: undefined,
  eventType: undefined,
  occurrenceTime: [],
  status: undefined,
  resultImg: undefined,
  isRepairOrder: undefined,
  maintenanceId: undefined,
  maintenanceUserId: undefined,
  maintenancePersonnel: undefined,
  maintenancePhone: undefined,
  source: undefined,
  createTime: [],
  deletedTime: [],
  updateTime: [],
  unitList:[],
  evidenceFileId: undefined,
  includeEquSituation:'1'
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MonitorResultsApi.getMonitorResultsPage(queryParams)
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
const handleExport = async (isExportImg) => {
  try {
    // 导出的二次确认
    visible.value = false
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    queryParams.isExportImg = isExportImg
    const data = await MonitorResultsApi.exportMonitorResults(queryParams)
    download.excel(data, '公安设备检测信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const detailFormRef = ref()
const openDetailForm = (type: string, id?: number) => {
  detailFormRef.value.open(type, id)
}

const importFormRef = ref()
const openImportForm = () => {
  importFormRef.value.open()
}

const openUploadBox = (id) => {
  selectId.value = id
  showUpload.value = true
}

const remarkFormRef = ref()
const openRemarkForm = (id) => {
  remarkFormRef.value.open(id)
}

const handleClose = () => {
  showUpload.value = false
  resultImg.value = ''
  selectId.value = ''
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

const submitResultImg = async () => {
  console.log(resultImg.value)
  if(!resultImg.value) return message.error('请先上传图片');
  const data= {
    id: selectId.value,
    resultImg: resultImg.value
  }
  await MonitorResultsApi.updateMonitorResults(data)
  message.success('保存成功');
  getList()
  handleClose()
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
