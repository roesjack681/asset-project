<template>
  <ContentWrap>
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="所在道路名称" prop="roadName">
        <el-input
          v-model="queryParams.roadName"
          placeholder="请输入道路名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="灯控路口名称" prop="intersectionName">
        <el-input
          v-model="queryParams.intersectionName"
          placeholder="请输入灯控路口名称"
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
      <el-form-item label="杆件编号" prop="serialNumber">
        <el-input
          v-model="queryParams.serialNumber"
          placeholder="请输入杆件编号"
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
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input
          v-model="queryParams.ipAddr"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="管辖单位" prop="dominion">
        <el-select
          v-model="queryParams.dominion"
          placeholder="请选择管辖单位"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.DOMINION)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
      <el-form-item label="状态" prop="equLableList">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.EQU_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
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
      <el-form-item label="所属辖区" prop="dominion">
        <el-select
          v-model="queryParams.unitList"
          placeholder="请选择所属辖区"
          clearable
          multiple
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openImportForm()"
          v-hasPermi="['traffic:ops-temp-report:import']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 导入
        </el-button>
        <el-button
          type="danger"
          plain
          @click="handleDelete"
          v-hasPermi="['traffic:ops-temp-report:clear']"
        >
          <Icon icon="ep:delete" class="mr-5px" /> 清空
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-temp-report:export']"
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

      <el-table-column label="所在道路名称" align="center" prop="roadName" />
      <el-table-column label="路口名称" align="center" prop="intersectionName" min-width="180px"/>
      <el-table-column label="方向" align="center" prop="direction" />
      <el-table-column label="杆件编号" align="center">
        <template #default="scope">
          <el-text v-if="scope.row.qrcodeRespVO">{{ padLeftZero(scope.row.qrcodeRespVO.serialNum, 3) }}</el-text>
        </template>
      </el-table-column>
      <el-table-column label="卡口编号" align="center" prop="kkbh" />
      <el-table-column label="取证设备编号" align="center" prop="sbbh" />
      <el-table-column label="电警类型" align="center" prop="djType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.DJ_TYPE" :value="scope.row.djType" />
        </template>
      </el-table-column>
      <el-table-column label="安装位置" align="center" prop="adress" />
      <el-table-column label="型号" align="center" prop="equModel" />
      <el-table-column label="是否绑定杆件" align="center">
        <template #default="scope">
          {{ scope.row.memberBh ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column label="所属区域" align="center">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.GEO_INFO" :value="scope.row.unit" />
        </template>
      </el-table-column>
      <el-table-column label="数据标签" align="center" prop="equLable" min-width="200"/>
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column
        label="维保到期时间"
        align="center"
        prop="maintenanceExpiryDate"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
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

  <ImportRecordForm ref="importFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import ImportRecordForm from './OpsTempReportImportForm.vue'
import { OpsTempReportApi, ViewOpsTempReportVO } from '@/api/traffic/opstempreport'

/** VIEW 列表 */
defineOptions({ name: 'ViewOpsTempReport' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<ViewOpsTempReportVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equId: undefined,
  ipAddr: undefined,
  source: undefined,
  createTime: [],
  deletedTime: [],
  equNumber: undefined,
  equName: undefined,
  equType: undefined,
  roadName: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  adress: undefined,
  installDate: [],
  manufacturer: undefined,
  equModel: undefined,
  unit: undefined,
  shootingRange: undefined,
  lens: undefined,
  focalLength: undefined,
  aperture: undefined,
  softwareVersion: undefined,
  installationMethod: undefined,
  communicationModel: undefined,
  protocol: undefined,
  gateway: undefined,
  gjxAddr: undefined,
  gjxLng: undefined,
  gjxLat: undefined,
  direction: undefined,
  djType: undefined,
  lwBh: undefined,
  jzBh: undefined,
  dominion: undefined,
  status: undefined,
  equImg: undefined,
  memberBh: undefined,
  isCheck: undefined,
  equLable: undefined,
  userName: undefined,
  password: undefined,
  addressXh: undefined,
  kkbh: undefined,
  sbbh: undefined,
  maintenanceExpiryDate: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsTempReportApi.getViewOpsTempReportPage(queryParams)
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

/** 删除按钮操作 */
const handleDelete = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm("确定要清空吗？")
    // 发起删除
    await OpsTempReportApi.clearOpsTempReport()
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

const importFormRef = ref()
const openImportForm = () => {
  importFormRef.value.open()
}

const padLeftZero = (serialNum, length) => {
  return String(serialNum).padStart(length, '0');
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await OpsTempReportApi.exportViewOpsTempReport(queryParams)
    download.excel(data, 'VIEW.xls')
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