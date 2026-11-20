<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="120px"
    >
<!--      <el-form-item label="护栏维护主键" prop="manceGuardrailId">
        <el-input
          v-model="queryParams.manceGuardrailId"
          placeholder="请输入护栏维护主键"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>-->
      <el-form-item label="车牌号" prop="vehicleNum">
        <el-input
          v-model="queryParams.vehicleNum"
          placeholder="请输入车牌号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="保险公司名称" prop="insuranceCompany">
        <el-input
          v-model="queryParams.insuranceCompany"
          placeholder="请输入保险公司名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="保单编号" prop="insurancePolicy">
        <el-input
          v-model="queryParams.insurancePolicy"
          placeholder="请输入保单编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="赔付金额" prop="payMoney">
        <el-input
          v-model="queryParams.payMoney"
          placeholder="请输入赔付金额"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="赔付护栏产品编号" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入赔付护栏产品编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="赔付片数" prop="pieces">
        <el-input
          v-model="queryParams.pieces"
          placeholder="请输入赔付片数"
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-guardrail-insurance:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-guardrail-insurance:export']"
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
      <el-table-column label="护栏维护主键" align="center" prop="manceGuardrailId" />
      <el-table-column label="车牌号" align="center" prop="vehicleNum" />
      <el-table-column label="保险公司名称" align="center" prop="insuranceCompany" />
      <el-table-column label="保单编号" align="center" prop="insurancePolicy" />
      <el-table-column label="赔付金额" align="center" prop="payMoney" />
      <el-table-column label="赔付护栏产品编号" align="center" prop="productId" />
      <el-table-column label="赔付片数" align="center" prop="pieces" />
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
            v-hasPermi="['traffic:ops-guardrail-insurance:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-guardrail-insurance:delete']"
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
  <OpsGuardrailInsuranceForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsGuardrailInsuranceApi, OpsGuardrailInsuranceVO } from '@/api/traffic/opsguardrailinsurance'
import OpsGuardrailInsuranceForm from './OpsGuardrailInsuranceForm.vue'

/** 护栏保险信息 列表 */
defineOptions({ name: 'TrafficOpsGuardrailInsurance' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsGuardrailInsuranceVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  manceGuardrailId: undefined,
  vehicleNum: undefined,
  insuranceCompany: undefined,
  insurancePolicy: undefined,
  payMoney: undefined,
  productId: undefined,
  pieces: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsGuardrailInsuranceApi.getOpsGuardrailInsurancePage(queryParams)
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
    await OpsGuardrailInsuranceApi.deleteOpsGuardrailInsurance(id)
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
    const data = await OpsGuardrailInsuranceApi.exportOpsGuardrailInsurance(queryParams)
    download.excel(data, '护栏保险信息.xls')
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
