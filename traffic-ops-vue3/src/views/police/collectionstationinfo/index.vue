<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="设备编号" prop="deviceCode">
        <el-input
          v-model="queryParams.deviceCode"
          placeholder="请输入设备编号/资产编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="机器名" prop="machineName">
        <el-input
          v-model="queryParams.machineName"
          placeholder="请输入机器名"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-tree-select
          v-model="queryParams.deptId"
          :data="deptList"
          :props="defaultProps"
          filterable
          check-strictly
          node-key="id"
          placeholder="请选择部门"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="设备厂商" prop="vendor">
        <el-input
          v-model="queryParams.vendor"
          placeholder="请输入设备厂商"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <!-- <el-form-item label="CPU型号" prop="cpuModel">
        <el-input
          v-model="queryParams.cpuModel"
          placeholder="请输入CPU型号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="内存信息" prop="memoryInfo">
        <el-input
          v-model="queryParams.memoryInfo"
          placeholder="请输入内存信息"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="硬盘信息" prop="diskInfo">
        <el-input
          v-model="queryParams.diskInfo"
          placeholder="请输入硬盘信息"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="操作系统" prop="osVersion">
        <el-input
          v-model="queryParams.osVersion"
          placeholder="请输入操作系统"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="软件版本" prop="softwareVersion">
        <el-input
          v-model="queryParams.softwareVersion"
          placeholder="请输入软件版本"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <!-- <el-form-item label="MAC地址" prop="macAddr">
        <el-input
          v-model="queryParams.macAddr"
          placeholder="请输入MAC地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input
          v-model="queryParams.ipAddr"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备位置" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入设备位置"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择设备状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.EQU_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="备注" prop="remark">
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入备注"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
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
          @click="openForm('create')"
          v-hasPermi="['police:collection-station-info:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['police:collection-station-info:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <el-table-column label="设备编号" align="center" prop="deviceCode" />
      <el-table-column label="机器名" align="center" prop="machineName" />
      <el-table-column label="部门" align="center" prop="deptId">
        <template #default="scope">
          <span>{{getDeptName(scope.row.deptId)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="设备厂商" align="center" prop="vendor" />
      <!-- <el-table-column label="CPU型号" align="center" prop="cpuModel" /> -->
      <!-- <el-table-column label="内存信息" align="center" prop="memoryInfo" /> -->
      <!-- <el-table-column label="硬盘信息" align="center" prop="diskInfo" /> -->
      <!-- <el-table-column label="操作系统" align="center" prop="osVersion" /> -->
      <!-- <el-table-column label="软件版本" align="center" prop="softwareVersion" /> -->
      <!-- <el-table-column label="MAC地址" align="center" prop="macAddr" /> -->
      <el-table-column label="IP地址" align="center" prop="ipAddr" />
      <el-table-column label="设备位置" align="center" prop="location" />
      <el-table-column label="设备状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.EQU_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['police:collection-station-info:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['police:collection-station-info:delete']"
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
  <CollectionStationInfoForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { CollectionStationInfoApi, CollectionStationInfoVO } from '@/api/police/collectionstationinfo'
import CollectionStationInfoForm from './CollectionStationInfoForm.vue'
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import { getStrDictOptions, DICT_TYPE, getDeptName } from '@/utils/dict'

/** 警用设备采集站信息 列表 */
defineOptions({ name: 'PoliceCollectionStationInfo' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<CollectionStationInfoVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const deptList = ref<Tree[]>([]) // 部门树形结构
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  deviceCode: undefined,
  machineName: undefined,
  deptId: undefined,
  vendor: undefined,
  cpuModel: undefined,
  memoryInfo: undefined,
  diskInfo: undefined,
  osVersion: undefined,
  softwareVersion: undefined,
  macAddr: undefined,
  ipAddr: undefined,
  location: undefined,
  status: undefined,
  remark: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CollectionStationInfoApi.getCollectionStationInfoPage(queryParams)
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
    await CollectionStationInfoApi.deleteCollectionStationInfo(id)
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
    const data = await CollectionStationInfoApi.exportCollectionStationInfo(queryParams)
    download.excel(data, '警用设备采集站信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(async () => {
  // 加载部门树
  deptList.value = handleTree(await DeptApi.getSimpleDeptList())
  getList()
})
</script>