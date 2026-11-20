<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="108px"
    >
      <el-form-item label="护栏主键" prop="guardrailId">
        <el-input
          v-model="queryParams.guardrailId"
          placeholder="请输入护栏主键"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="维保主键" prop="maintenanceId">
        <el-input
          v-model="queryParams.maintenanceId"
          placeholder="请输入维保主键"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="护栏维护主键" prop="manceGuardrailId">
        <el-input
          v-model="queryParams.manceGuardrailId"
          placeholder="请输入护栏维护主键"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="辅助设备名称" prop="auxiliaryName">
        <el-input
          v-model="queryParams.auxiliaryName"
          placeholder="请输入辅助设备名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="数量" prop="auxiliaryNum">
        <el-input
          v-model="queryParams.auxiliaryNum"
          placeholder="请输入数量"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="图片" prop="auxiliaryImg">
        <el-input
          v-model="queryParams.auxiliaryImg"
          placeholder="请输入图片"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态：安装；拆除"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="安装时间" prop="installTime">
        <el-date-picker
          v-model="queryParams.installTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="拆除时间" prop="demolishTime">
        <el-date-picker
          v-model="queryParams.demolishTime"
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
      <el-form-item label="删除时间" prop="deletedTime">
        <el-date-picker
          v-model="queryParams.deletedTime"
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
          v-hasPermi="['traffic:ops-guardrail-auxiliary:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-guardrail-auxiliary:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="护栏主键" align="center" prop="guardrailId" />
      <el-table-column label="维保主键" align="center" prop="maintenanceId" />
      <el-table-column label="护栏维护主键" align="center" prop="manceGuardrailId" />
      <el-table-column label="辅助设备名称" align="center" prop="auxiliaryName" />
      <el-table-column label="数量" align="center" prop="auxiliaryNum" />
      <el-table-column label="图片" align="center" prop="auxiliaryImg" />
      <el-table-column label="状态：安装；拆除" align="center" prop="status" />
      <el-table-column
        label="安装时间"
        align="center"
        prop="installTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="拆除时间"
        align="center"
        prop="demolishTime"
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
      <el-table-column
        label="删除时间"
        align="center"
        prop="deletedTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-guardrail-auxiliary:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-guardrail-auxiliary:delete']"
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
  <OpsGuardrailAuxiliaryForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsGuardrailAuxiliaryApi, OpsGuardrailAuxiliaryVO } from '@/api/traffic/opsguardrailauxiliary'
import OpsGuardrailAuxiliaryForm from './OpsGuardrailAuxiliaryForm.vue'

/** 护栏辅助设备 列表 */
defineOptions({ name: 'TrafficOpsGuardrailAuxiliary' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsGuardrailAuxiliaryVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  guardrailId: undefined,
  maintenanceId: undefined,
  manceGuardrailId: undefined,
  auxiliaryName: undefined,
  auxiliaryNum: undefined,
  auxiliaryImg: undefined,
  status: undefined,
  installTime: [],
  demolishTime: [],
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsGuardrailAuxiliaryApi.getOpsGuardrailAuxiliaryPage(queryParams)
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
    await OpsGuardrailAuxiliaryApi.deleteOpsGuardrailAuxiliary(id)
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
    const data = await OpsGuardrailAuxiliaryApi.exportOpsGuardrailAuxiliary(queryParams)
    download.excel(data, '护栏辅助设备.xls')
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
