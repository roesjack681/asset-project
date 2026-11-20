<template>
  <Dialog :title="dialogTitle" width="900px" v-model="dialogVisible">
    <ContentWrap>
      <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="88px"
      >
        <el-form-item label="装备名称" prop="equName">
          <el-input
            v-model="queryParams.equName"
            placeholder="请输入装备名称"
            clearable
            class="!w-200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="使用人" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入使用人"
            clearable
            class="!w-200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        </el-form-item>
      </el-form>
    </ContentWrap>

    <ContentWrap>
      <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
        <el-table-column label="序号" align="center" type="index" width="60" />
        <el-table-column label="借用组织" align="center" prop="useDept" min-width="160" />
        <el-table-column label="装备编号" align="center" prop="equId" width="120" />
        <el-table-column label="装备名称" align="center" prop="equName" min-width="120" />
        <el-table-column label="PSN" align="center" prop="psn" width="100" />
        <el-table-column label="品牌" align="center" prop="brand" width="100" />
        <el-table-column label="型号" align="center" prop="model" width="100" />
        <el-table-column label="使用人" align="center" prop="userName" width="100" />
        <el-table-column label="使用人编号" align="center" prop="userBh" width="100" />
        <!-- 资产表统计没有“领用时间”字段，这里不展示 -->
      </el-table>
      <Pagination
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </ContentWrap>
    <template #footer>
      <el-button @click="dialogVisible = false">关 闭</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { OpsPoliceEquipmentStatisticsApi, OpsPoliceEquipmentStatisticsDetailVO } from '@/api/traffic/opspoliceequipmentstatistics'

defineOptions({ name: 'OpsPoliceEquipmentStatisticsDetailForm' })

const dialogVisible = ref(false)
const dialogTitle = ref('')
const loading = ref(false)
const list = ref<OpsPoliceEquipmentStatisticsDetailVO[]>([])
const total = ref(0)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  deptId: undefined,
  deptIds: undefined as undefined | (number | string)[],
  equName: undefined,
  userName: undefined,
  isIncludeChild: '1'
})
const queryFormRef = ref()

/** 打开弹窗 */
const open = async (row: { deptId: string; deptName: string; deptIds?: (number | string)[] }) => {
  dialogVisible.value = true
  dialogTitle.value = row.deptName + ' - 借用设备详情'
  queryParams.pageNo = 1
  queryParams.deptId = row.deptId
  queryParams.deptIds = row.deptIds
  queryParams.equName = undefined
  queryParams.userName = undefined
  queryParams.isIncludeChild = '1'
  getList()
}
defineExpose({ open })

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    // 优先尝试：后端支持“包含下级”的话，一次请求即可
    if (!queryParams.deptIds || queryParams.deptIds.length <= 1) {
      const data = await OpsPoliceEquipmentStatisticsApi.getOpsPoliceEquipmentStatisticsDetail(queryParams)
      list.value = data.list
      total.value = data.total
      return
    }

    // 兜底：如果后端 detail 不支持包含下级，则按子部门逐个拉取并在前端合并展示
    const allDeptIds = queryParams.deptIds
    const pageNo = queryParams.pageNo
    const pageSize = queryParams.pageSize

    // 先取全量（避免各部门分页拼接导致缺数据），再做前端分页
    const reqs = allDeptIds.map((id) =>
      OpsPoliceEquipmentStatisticsApi.getOpsPoliceEquipmentStatisticsDetail({
        pageNo: 1,
        pageSize: 10000,
        deptId: String(id),
        equName: queryParams.equName,
        userName: queryParams.userName,
        isIncludeChild: '2'
      })
    )
    const resList = await Promise.all(reqs)
    const merged = resList.flatMap((r) => r.list || [])
    total.value = merged.length
    const start = (pageNo - 1) * pageSize
    const end = start + pageSize
    list.value = merged.slice(start, end)
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
</script>
