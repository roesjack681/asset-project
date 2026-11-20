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
      <el-form-item label="管理部门" prop="glbm">
        <el-input
          v-model="queryParams.glbm"
          placeholder="请输入管理部门"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="道路代码" prop="dldm">
        <el-input
          v-model="queryParams.dldm"
          placeholder="请输入道路代码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="道路名称" prop="dlmc">
        <el-input
          v-model="queryParams.dlmc"
          placeholder="请输入道路名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="道路类型" prop="dllx">
        <el-select
          v-model="queryParams.dllx"
          placeholder="请选择道路类型"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="公路行政等级" prop="glxzdj">
        <el-select
          v-model="queryParams.glxzdj"
          placeholder="请选择公路行政等级"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="起始" prop="qs">
        <el-input
          v-model="queryParams.qs"
          placeholder="请输入起始"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="结束" prop="js">
        <el-input
          v-model="queryParams.js"
          placeholder="请输入结束"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="辖区里程" prop="xqlc">
        <el-input
          v-model="queryParams.xqlc"
          placeholder="请输入辖区里程"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="起始名称" prop="qsmc">
        <el-input
          v-model="queryParams.qsmc"
          placeholder="请输入起始名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="结束名称" prop="jsmc">
        <el-input
          v-model="queryParams.jsmc"
          placeholder="请输入结束名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
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
      <!-- <el-form-item label="删除时间" prop="deletedTime">
        <el-date-picker
          v-model="queryParams.deletedTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item> -->
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-road:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-road:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="管理部门" align="center" prop="glbm" />
      <el-table-column label="道路代码" align="center" prop="dldm" />
      <el-table-column label="道路名称" align="center" prop="dlmc" />
      <el-table-column label="道路类型" align="center" prop="dllx" />
      <el-table-column label="公路行政等级" align="center" prop="glxzdj" />
      <el-table-column label="起始" align="center" prop="qs" />
      <el-table-column label="结束" align="center" prop="js" />
      <el-table-column label="辖区里程" align="center" prop="xqlc" />
      <el-table-column label="起始名称" align="center" prop="qsmc" />
      <el-table-column label="结束名称" align="center" prop="jsmc" />
      <!-- <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="删除时间"
        align="center"
        prop="deletedTime"
        :formatter="dateFormatter"
        width="180px"
      /> -->
      <el-table-column label="操作" align="center" min-width="240px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-road:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="primary"
            @click="openGenCodeForm(scope.row.dlmc, scope.row.id)"
          >
            生成码
          </el-button>
          <el-button
            link
            type="primary"
            @click="openDataCheckForm(scope.row.dlmc, scope.row.id)"
          >
            数据核查
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-road:delete']"
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
  <OpsRoadForm ref="formRef" @success="getList" />

  <!-- 表单弹窗：添加/修改 -->
  <GenQrCodeForm ref="genCodeFormRef" @success="getList" />
  <OpsRoadDataCheckForm ref="checkDataFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsRoadApi, OpsRoadVO } from '@/api/traffic/opsroad'
import OpsRoadForm from './OpsRoadForm.vue'
import GenQrCodeForm from './GenQrCodeForm.vue'
import OpsRoadDataCheckForm from './OpsRoadDataCheckForm.vue'

/** 道路信息 列表 */
defineOptions({ name: 'TrafficOpsRoad' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsRoadVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  glbm: undefined,
  dldm: undefined,
  dlmc: undefined,
  dllx: undefined,
  glxzdj: undefined,
  qs: undefined,
  js: undefined,
  xqlc: undefined,
  qsmc: undefined,
  jsmc: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsRoadApi.getOpsRoadPage(queryParams)
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

const genCodeFormRef = ref()
const openGenCodeForm = (roadName: string, id?: number) => {
  genCodeFormRef.value.open(roadName, id)
}

const checkDataFormRef = ref()
const openDataCheckForm = (roadName: string, id?: number) => {
  checkDataFormRef.value.open(roadName, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await OpsRoadApi.deleteOpsRoad(id)
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
    const data = await OpsRoadApi.exportOpsRoad(queryParams)
    download.excel(data, '道路信息.xls')
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
