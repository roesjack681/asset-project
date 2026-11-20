<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="98px"
    >
      <el-form-item label="领用部门" prop="useDept">
        <el-input
          v-model="queryParams.useDept"
          placeholder="请输入领用部门"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="领用人" prop="issueUserName">
        <el-input
          v-model="queryParams.issueUserName"
          placeholder="请输入领用人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="领用时间" prop="issueTime">
        <el-date-picker
          v-model="queryParams.issueTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="装备psn" prop="equId">
        <el-select v-model="queryParams.equId" @keyup.enter="handleQuery" placeholder="装备psn" class="!w-240px">
          <el-option
            v-for="dict in equDicList"
            :key="dict.id"
            :label="dict.psn"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="使用人" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入使用人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="回收人" prop="recycleUserName">
        <el-input
          v-model="queryParams.recycleUserName"
          placeholder="请输入回收人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="回收时间" prop="recycleTime">
        <el-date-picker
          v-model="queryParams.recycleTime"
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
        <el-button type="success" plain @click="handleExport" :loading="exportLoading" v-hasPermi="['traffic:ops-issue-history:export']">
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
<!--      <el-table-column label="领用部门编号" align="center" prop="useDeptBh" />-->
      <el-table-column label="领用部门" align="center" prop="useDept" />
      <el-table-column label="领用人" align="center" prop="issueUserName" />
      <el-table-column label="领用时间" align="center" prop="issueTime" :formatter="dateFormatter" width="180px"/>
      <el-table-column label="装备psn" align="center" prop="psn" />
      <el-table-column label="使用人" align="center" prop="userName" />
      <el-table-column label="回收人" align="center" prop="recycleUserName" />
      <el-table-column label="回收时间" align="center" prop="recycleTime" :formatter="dateFormatter" width="180px"/>
<!--      <el-table-column label="操作" align="center" min-width="120px">-->
<!--        <template #default="scope">-->
<!--          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['traffic:ops-issue-history:delete']">-->
<!--            删除-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
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
  <OpsIssueHistoryForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsIssueHistoryApi, OpsIssueHistoryVO } from '@/api/traffic/opsissuehistory'
import OpsIssueHistoryForm from './OpsIssueHistoryForm.vue'
import {OpsIssueRecordApi} from "@/api/traffic/opsissuerecord";
/** 警用装备历史领用记录 列表 */
defineOptions({ name: 'TrafficOpsIssueHistory' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsIssueHistoryVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  useDeptBh: undefined,
  useDept: undefined,
  issueUserName: undefined,
  issueUserBh: undefined,
  issueTime: [],
  equId: undefined,
  userName: undefined,
  userBh: undefined,
  recycleUserBh: undefined,
  recycleUserName: undefined,
  recycleTime: [],
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const equDicList = ref([])

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsIssueHistoryApi.getOpsIssueHistoryPage(queryParams)
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
    await OpsIssueHistoryApi.deleteOpsIssueHistory(id)
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
    const data = await OpsIssueHistoryApi.exportOpsIssueHistory(queryParams)
    download.excel(data, '警用装备历史领用记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(async() => {
  getList()
  equDicList.value = await OpsIssueRecordApi.queryPoliceEquipment(1)
})
</script>
