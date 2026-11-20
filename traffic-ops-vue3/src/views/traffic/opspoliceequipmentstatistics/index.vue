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
      <el-form-item label="部门" prop="deptId">
        <el-tree-select
          v-model="queryParams.deptId"
          :data="deptList"
          :props="defaultProps"
          filterable
          check-strictly
          @change="chooseDept"
          node-key="id"
          placeholder="请选择部门"
          class="!w-240px"
          clearable
        />
        <el-checkbox style="margin-left: 10px;" v-model="isIncludeChild" @change="changeIsIncludeChild">包含下级</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-police-equipment-statistics:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="序号" align="center" type="index" width="80" />
      <el-table-column label="部门名称" align="center" prop="deptName" />
      <el-table-column label="借用设备总数" align="center" prop="totalNum" />
      <el-table-column label="操作" align="center" width="150px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openDetail(scope.row)"
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

  <!-- 详情弹窗 -->
  <DetailForm ref="detailRef" />
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { OpsPoliceEquipmentStatisticsApi, OpsPoliceEquipmentStatisticsVO } from '@/api/traffic/opspoliceequipmentstatistics'
import DetailForm from './DetailForm.vue'
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import { getDeptName } from '@/utils/dict'

/** 警用装备统计 列表 */
defineOptions({ name: 'TrafficOpsPoliceEquipmentStatistics' })

const message = useMessage() // 消息弹窗

const loading = ref(true) // 列表的加载中
const isIncludeChild = ref(false)
const list = ref<OpsPoliceEquipmentStatisticsVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const deptList = ref<Tree[]>([]) // 部门树形结构
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  deptId: undefined,
  deptName: undefined,
  isIncludeChild: '2'
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsPoliceEquipmentStatisticsApi.getOpsPoliceEquipmentStatisticsPage(queryParams)
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
  queryParams.isIncludeChild = '2'
  isIncludeChild.value = false
  handleQuery()
}

/** 选择部门 */
const chooseDept = (val) => {
  queryParams.deptName = getDeptName(val)
}

/** 包含下级 */
const changeIsIncludeChild = (value: boolean) => {
  queryParams.isIncludeChild = value ? '1' : '2'
}

/** 详情操作 */
const detailRef = ref()
const openDetail = (row: OpsPoliceEquipmentStatisticsVO) => {
  detailRef.value.open(row)
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await OpsPoliceEquipmentStatisticsApi.exportOpsPoliceEquipmentStatistics(queryParams)
    download.excel(data, '警用装备统计.xls')
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
