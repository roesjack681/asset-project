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
      <el-form-item label="道路名称" prop="roadName">
        <el-input
          v-model="queryParams.roadName"
          placeholder="请输入道路名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="路段" prop="roadParts">
        <el-input
          v-model="queryParams.roadParts"
          placeholder="请输入路段"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="施画种类" prop="marklineSort">
        <el-input
          v-model="queryParams.marklineSort"
          placeholder="请输入施画种类"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="数量" prop="marklineNum">
        <el-input
          v-model="queryParams.marklineNum"
          placeholder="请输入数量"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="线宽" prop="lineWidth">
        <el-input
          v-model="queryParams.lineWidth"
          placeholder="请输入线宽"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="规格" prop="specifications">
        <el-input
          v-model="queryParams.specifications"
          placeholder="请输入规格"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="比例" prop="proportion">
        <el-input
          v-model="queryParams.proportion"
          placeholder="请输入比例"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="面积" prop="area">
        <el-input
          v-model="queryParams.area"
          placeholder="请输入面积"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="施画单位" prop="paintingUnit">
        <el-input
          v-model="queryParams.paintingUnit"
          placeholder="请输入施画单位"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="施划工艺" prop="drawingProcess">
        <el-input
          v-model="queryParams.drawingProcess"
          placeholder="请输入施划工艺"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="施划时间" prop="applicationTime">
        <el-date-picker
          v-model="queryParams.applicationTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-markline:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-markline:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
          type="warning"
          plain
          @click="handleOpenReport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-markline:export']"
        >
          <Icon icon="ep:data-analysis" class="mr-5px" /> 查看分析报告
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="道路名称" align="center" prop="roadName" />
      <el-table-column label="路段" align="center" prop="roadParts" />
      <el-table-column label="施画种类" align="center" prop="marklineSort" />
      <el-table-column label="数量" align="center" prop="marklineNum" />
      <el-table-column label="线宽" align="center" prop="lineWidth" />
      <el-table-column label="规格" align="center" prop="specifications" />
      <el-table-column label="比例" align="center" prop="proportion" />
      <el-table-column label="面积" align="center" prop="area" />
      <el-table-column label="施画单位" align="center" prop="paintingUnit" />
<!--      <el-table-column label="施划工艺" align="center" prop="drawingProcess" />-->
<!--      <el-table-column-->
<!--        label="施划时间"-->
<!--        align="center"-->
<!--        prop="applicationTime"-->
<!--        :formatter="dateFormatter"-->
<!--        width="180px"-->
<!--      />-->
      <el-table-column label="管辖单位" prop="dominion" />
<!--      <el-table-column-->
<!--        label="创建时间"-->
<!--        align="center"-->
<!--        prop="createTime"-->
<!--        :formatter="dateFormatter"-->
<!--        width="180px"-->
<!--      />-->
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-markline:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-markline:delete']"
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
  <OpsMarklineForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsMarklineApi, OpsMarklineVO } from '@/api/traffic/opsmarkline'
import OpsMarklineForm from './OpsMarklineForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 标线 列表 */
defineOptions({ name: 'TrafficOpsMarkline' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsMarklineVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  roadName: undefined,
  roadParts: undefined,
  marklineSort: undefined,
  marklineNum: undefined,
  lineWidth: undefined,
  specifications: undefined,
  proportion: undefined,
  area: undefined,
  paintingUnit: undefined,
  drawingProcess: undefined,
  applicationTime: [],
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsMarklineApi.getOpsMarklinePage(queryParams)
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
    await OpsMarklineApi.deleteOpsMarkline(id)
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
    const data = await OpsMarklineApi.exportOpsMarkline(queryParams)
    download.excel(data, '标线.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const handleOpenReport = () =>{
  window.open('/report/markline/v2/marklineReport.html', '_blank');
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
