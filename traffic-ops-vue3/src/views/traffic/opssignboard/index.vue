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
      <el-form-item label="所在道路名称" prop="roadName">
        <el-input
          v-model="queryParams.roadName"
          placeholder="请输入所在道路名称"
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
      <el-form-item label="安装时间" prop="installDate">
        <el-date-picker
          v-model="queryParams.installDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="归属单位" prop="unit">
        <el-input
          v-model="queryParams.unit"
          placeholder="请输入归属单位"
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
      <el-form-item label="生产厂家" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入生产厂家"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="安装厂家" prop="installManufacturer">
        <el-input
          v-model="queryParams.installManufacturer"
          placeholder="请输入安装厂家"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-sign-board:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-sign-board:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="道路名称" align="center" prop="roadName" />
<!--      <el-table-column label="设备类型" align="center" prop="equType" />-->
      <el-table-column label="路口名称" align="center" prop="intersectionName" />
<!--      <el-table-column label="安装时间" align="center" prop="installDate" :formatter="dateFormatter" width="180px"/>-->
      <el-table-column label="安装位置" align="center" prop="adress" />
      <!-- <el-table-column label="GPS定位-经度" align="center" prop="lng" />
      <el-table-column label="GPS定位-纬度" align="center" prop="lat" /> -->
      <el-table-column label="方向" align="center" prop="direction" />
      <el-table-column label="标牌类型" align="center" prop="labelContent" />
      <el-table-column label="标牌内容" align="center" prop="labelContent" />
<!--      <el-table-column label="规格" align="center" prop="specifications" />-->
<!--      <el-table-column label="规格单位" align="center" prop="ggunit" />-->
<!--      <el-table-column label="数量" align="center" prop="num" />-->
      <el-table-column label="归属单位" align="center" prop="unit" />
      <el-table-column label="管辖单位" align="center" prop="dominion" />
      <!-- <el-table-column label="生产厂家" align="center" prop="manufacturer" />
      <el-table-column label="安装厂家" align="center" prop="installManufacturer" /> -->
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-sign-board:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-sign-board:delete']"
          >
            删除
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
  <OpsSignBoardForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsSignBoardApi, OpsSignBoardVO } from '@/api/traffic/opssignboard'
import OpsSignBoardForm from './OpsSignBoardForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 标志牌 列表 */
defineOptions({ name: 'TrafficOpsSignBoard' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsSignBoardVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equName: undefined,
  equType: undefined,
  installDate: [],
  adress: undefined,
  lng: undefined,
  lat: undefined,
  direction: undefined,
  labelContent: undefined,
  specifications: undefined,
  ggunit: undefined,
  num: undefined,
  unit: undefined,
  dominion: undefined,
  manufacturer: undefined,
  installManufacturer: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsSignBoardApi.getOpsSignBoardPage(queryParams)
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
    await OpsSignBoardApi.deleteOpsSignBoard(id)
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
    const data = await OpsSignBoardApi.exportOpsSignBoard(queryParams)
    download.excel(data, '标志牌.xls')
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
