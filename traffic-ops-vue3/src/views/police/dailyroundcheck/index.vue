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
      <!-- <el-form-item label="巡检设备编号" prop="equId">
        <el-input
          v-model="queryParams.equId"
          placeholder="请输入巡检设备编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="所属单位：1-交警；2-公安" prop="affiliatedUnit">
        <el-input
          v-model="queryParams.affiliatedUnit"
          placeholder="请输入所属单位：1-交警；2-公安"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item label="设备状态" prop="equStatus">
        <el-select
          v-model="queryParams.equStatus"
          placeholder="请选择设备状态"
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
      <el-form-item label="巡检项" prop="checkItem">
        <el-input
          v-model="queryParams.checkItem"
          placeholder="请输入巡检项"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="问题" prop="question">
        <el-input
          v-model="queryParams.question"
          placeholder="请输入问题"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="解决方案" prop="solution">
        <el-input
          v-model="queryParams.solution"
          placeholder="请输入解决方案"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item label="巡检人" prop="checkPerson">
        <el-input
          v-model="queryParams.checkPerson"
          placeholder="请输入巡检人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="巡检方式：内勤；外勤" prop="checkType">
        <el-select
          v-model="queryParams.checkType"
          placeholder="请选择巡检方式：内勤；外勤"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="巡检人编号" prop="checkPersonId">
        <el-input
          v-model="queryParams.checkPersonId"
          placeholder="请输入巡检人编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item label="巡检时间" prop="checkDate">
        <el-date-picker
          v-model="queryParams.checkDate"
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
          v-hasPermi="['police:daily-round-check:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['police:daily-round-check:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column width="480" label="巡检设备编号" align="center" prop="equId">
        <template #default="scope">
          <p><el-tag>{{ scope.row.electricPoliceRespVO.ipAddr }}</el-tag></p>
          <p>{{ scope.row.electricPoliceRespVO.equName }}</p>
        </template>
      </el-table-column>
      <!-- <el-table-column label="所属单位：1-交警；2-公安" align="center" prop="affiliatedUnit" /> -->
      <el-table-column label="设备状态" align="center" prop="equStatus" />
      <el-table-column label="巡检项" align="center" prop="checkItem" />
      <!-- <el-table-column label="问题" align="center" prop="question" />
      <el-table-column label="解决方案" align="center" prop="solution" /> -->
      <el-table-column label="巡检人" align="center" prop="checkPerson" />
      <el-table-column label="巡检方式" align="center" prop="checkType" />
      <!-- <el-table-column label="巡检人编号" align="center" prop="checkPersonId" /> -->
      <el-table-column
        label="巡检时间"
        align="center"
        prop="checkDate"
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
      <!-- <el-table-column
        label="删除时间"
        align="center"
        prop="deletedTime"
        :formatter="dateFormatter"
        width="180px"
      /> -->
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['police:daily-round-check:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['police:daily-round-check:delete']"
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
  <DailyRoundCheckForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import download from '@/utils/download'
import { DailyRoundCheckApi, DailyRoundCheckVO } from '@/api/police/dailyroundcheck'
import DailyRoundCheckForm from './DailyRoundCheckForm.vue'

/** 日常巡检信息 列表 */
defineOptions({ name: 'PoliceDailyRoundCheck' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<DailyRoundCheckVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equId: undefined,
  affiliatedUnit: undefined,
  equStatus: undefined,
  checkItem: undefined,
  question: undefined,
  solution: undefined,
  checkPerson: undefined,
  checkType: undefined,
  checkPersonId: undefined,
  checkDate: [],
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await DailyRoundCheckApi.getDailyRoundCheckPage(queryParams)
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
    await DailyRoundCheckApi.deleteDailyRoundCheck(id)
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
    const data = await DailyRoundCheckApi.exportDailyRoundCheck(queryParams)
    download.excel(data, '日常巡检信息.xls')
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
