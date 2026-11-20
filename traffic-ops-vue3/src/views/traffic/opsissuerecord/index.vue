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
      <el-form-item label="领用部门" prop="useDeptBh">
<!--        <el-input-->
<!--          v-model="queryParams.useDept"-->
<!--          placeholder="请输入领用部门"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--          class="!w-240px"-->
<!--        />-->
        <el-tree-select
          v-model="queryParams.useDeptBh"
          :data="deptList"
          :props="defaultProps"
          filterable
          check-strictly
          @change="chooseUseDept"
          node-key="id"
          placeholder="请选择领用部门"
          class="!w-240px"
        />
        <el-checkbox style="margin-left: 10px;" v-model="isIncludeChild" @change="changeIsIncludeChild">包含下级</el-checkbox>
      </el-form-item>
      <el-form-item label="领用人" prop="userName">
        <el-input
          v-model="queryParams.userName"
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
      <el-form-item label="领用数量" prop="issueNum">
        <el-input
          v-model="queryParams.issueNum"
          placeholder="请输入领用数量"
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
          v-hasPermi="['traffic:ops-issue-record:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          @click="handleExport"-->
<!--          :loading="exportLoading"-->
<!--          v-hasPermi="['traffic:ops-issue-record:export']"-->
<!--        >-->
<!--          <Icon icon="ep:download" class="mr-5px" /> 导出-->
<!--        </el-button>-->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
<!--      <el-table-column label="领用部门编号" align="center" prop="useDeptBh" />-->
      <el-table-column label="领用部门" align="center" prop="useDept" />
      <el-table-column label="上级部门" align="center" prop="useDeptBh">
        <template #default="scope">
          <span>{{getParentDeptName(scope.row.useDeptBh)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="领用人" align="center" prop="userName" />
<!--      <el-table-column label="领用人编号" align="center" prop="userBh" />-->
      <el-table-column label="领用时间" align="center" prop="issueTime" :formatter="dateFormatter" width="180px"/>
      <el-table-column label="领用数量" align="center" prop="issueNum" />
      <el-table-column label="剩余领用数量" align="center" prop="detailIssueNum" />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-issue-record:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('dispatch', scope.row.id)"
            v-hasPermi="['traffic:ops-issue-record:update']"
          >
            分配
          </el-button>
<!--          <el-button-->
<!--            link-->
<!--            type="danger"-->
<!--            @click="handleDelete(scope.row.id)"-->
<!--            v-hasPermi="['traffic:ops-issue-record:delete']"-->
<!--          >-->
<!--            删除-->
<!--          </el-button>-->
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
  <OpsIssueRecordForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsIssueRecordApi, OpsIssueRecordVO } from '@/api/traffic/opsissuerecord'
import OpsIssueRecordForm from './OpsIssueRecordForm.vue'
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import {getParentDeptName} from "@/utils/dict";

/** 领用记录 列表 */
defineOptions({ name: 'TrafficOpsIssueRecord' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const isIncludeChild = ref(false)
const list = ref<OpsIssueRecordVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const deptList = ref<Tree[]>([]) // 树形结构
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  useDeptBh: undefined,
  useDept: undefined,
  userName: undefined,
  userBh: undefined,
  issueTime: [],
  issueNum: undefined,
  createTime: [],
  deletedTime: [],
  isIncludeChild: '2'
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsIssueRecordApi.getOpsIssueRecordPage(queryParams)
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
    await OpsIssueRecordApi.deleteOpsIssueRecord(id)
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
    const data = await OpsIssueRecordApi.exportOpsIssueRecord(queryParams)
    download.excel(data, '领用记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const changeIsIncludeChild = (value: boolean) => {
  queryParams.isIncludeChild = value ? '1' : '2'
}

/** 初始化 **/
onMounted(async() => {
  getList()
  // 加载部门树
  deptList.value = handleTree(await DeptApi.getSimpleDeptList())
})
</script>
