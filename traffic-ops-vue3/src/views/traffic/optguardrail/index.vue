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
      <el-form-item label="道路名称" prop="roadName">
        <el-input
          v-model="queryParams.roadName"
          placeholder="请输入道路名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="安装位置" prop="adress">
        <el-input
          v-model="queryParams.adress"
          placeholder="请输入安装位置"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>

      <el-form-item label="型号" prop="model">
        <el-input
          v-model="queryParams.model"
          placeholder="请输入型号"
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-guardrail:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-guardrail:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
          type="warning"
          plain
          @click="handleOpenReport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-guardrail:export']"
        >
          <Icon icon="ep:data-analysis" class="mr-5px" /> 查看分析报告
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="道路名称" align="center" prop="roadName" />
      <el-table-column label="安装位置" align="center" prop="adress" />
      <!-- <el-table-column label="型号" align="center" prop="model" /> -->
      <el-table-column label="片数" align="center" prop="pieces" />
      <el-table-column label="总长（米）" align="center" prop="length" />
      <!-- <el-table-column label="底座" align="center" prop="pedestal" />
      <el-table-column label="人行车档" align="center" prop="humanDecency" />
      <el-table-column label="立柱车档" align="center" prop="stud" /> -->
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="管辖单位" align="center" prop="dominion" />
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
            v-hasPermi="['traffic:ops-guardrail:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-guardrail:delete']"
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
  <OpsGuardrailForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsGuardrailApi, OpsGuardrailVO } from '@/api/traffic/optguardrail'
import OpsGuardrailForm from './OpsGuardrailForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 护栏 列表 */
defineOptions({ name: 'TrafficOpsGuardrail' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsGuardrailVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equName: undefined,
  equType: undefined,
  roadName: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  adress: undefined,
  installDate: [],
  hight: undefined,
  length: undefined,
  material: undefined,
  manufacturer: undefined,
  unit: undefined,
  dominion: undefined,
  status: undefined,
  equImg: undefined,
  model: undefined,
  pieces: undefined,
  totalLength: undefined,
  pedestal: undefined,
  humanDecency: undefined,
  stud: undefined,
  sign: undefined,
  gzbsz: undefined,
  tynbsd: undefined,
  rxdfzz: undefined,
  remark: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsGuardrailApi.getOpsGuardrailPage(queryParams)
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
    await OpsGuardrailApi.deleteOpsGuardrail(id)
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
    const data = await OpsGuardrailApi.exportOpsGuardrail(queryParams)
    download.excel(data, '护栏.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const handleOpenReport = () =>{
  window.open('/report/guardrail/opsGuardrailReport.html', '_blank');
}



/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
