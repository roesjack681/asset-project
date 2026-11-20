<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="88px"
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['police:monitor-results-history:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="已修复" name="sucList" >
        <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
          <el-table-column label="IP地址" align="center" prop="ipAddr" min-width="120"/>
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
          <el-table-column label="修改人" align="center" prop="updater" />
        </el-table>
        <!-- 分页 -->
        <Pagination
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </el-tab-pane>
      <el-tab-pane label="已损坏" name="badList" >
        <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
          <el-table-column label="IP地址" align="center" prop="ipAddr" min-width="120"/>
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
          <el-table-column label="修改人" align="center" prop="updater" />
        </el-table>
        <!-- 分页 -->
        <Pagination
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </el-tab-pane>
      <el-tab-pane label="无变化" name="noChangeList" >
        <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
          <el-table-column label="IP地址" align="center" prop="ipAddr" min-width="120"/>
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
          <el-table-column label="修改人" align="center" prop="updater" />
        </el-table>
        <!-- 分页 -->
        <Pagination
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <OpsMaintenanceForm ref="detailFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { MonitorResultsHistoryApi, MonitorResultsHistoryVO } from '@/api/police/monitorresultshistory'
import OpsMaintenanceForm from '../optmaintenance/OpsMaintenanceForm.vue'

/** 公安设备监测结果历史 列表 */
defineOptions({ name: 'PoliceMonitorResultsHistory' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const activeName = ref('sucList')
const list = ref<MonitorResultsHistoryVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  monitorDate: [],
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
  equSituation: undefined,
  equDesc: undefined,
  evidenceFileId: undefined,
  remarkDate: [],
  changeSituation: '1',
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MonitorResultsHistoryApi.getMonitorResultsHistoryPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const detailFormRef = ref()
const openDetailForm = (type: string, id?: number) => {
  detailFormRef.value.open(type, id)
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

const handleClick = (tab, event) => {
  queryParams.pageNo = 1
  if (tab.paneName == "sucList") {
    queryParams.changeSituation = '1'
  } else if (tab.paneName == "badList") {
    queryParams.changeSituation = '2'
  } else if (tab.paneName == "noChangeList") {
    queryParams.changeSituation = '0'
  }
  resetQuery()
  getList()
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await MonitorResultsHistoryApi.exportMonitorResultsHistory(queryParams)
    download.excel(data, '公安设备监测结果历史.xls')
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
