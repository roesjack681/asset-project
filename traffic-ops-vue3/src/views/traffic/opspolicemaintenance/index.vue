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
      <el-form-item label="上报人员" prop="reportPersonnel">
        <el-input
          v-model="queryParams.reportPersonnel"
          placeholder="请输入上报人员"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="上报时间" prop="reportTime">
        <el-date-picker
          v-model="queryParams.reportTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="上报人员部门" prop="reportDeptId">
        <el-input
          v-model="queryParams.reportDeptId"
          placeholder="请输入上报人员部门编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="审核时间" prop="auditDate">
        <el-date-picker
          v-model="queryParams.auditDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="审核人" prop="auditor">
        <el-input
          v-model="queryParams.auditor"
          placeholder="请输入审核人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="审核意见" prop="auditOpinions">
        <el-input
          v-model="queryParams.auditOpinions"
          placeholder="请输入审核意见"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="维修人" prop="maintenancePersonnel">
        <el-input
          v-model="queryParams.maintenancePersonnel"
          placeholder="请输入维修人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="维护时间" prop="maintenanceDate">
        <el-date-picker
          v-model="queryParams.maintenanceDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="维护建议" prop="suggestion">
        <el-input
          v-model="queryParams.suggestion"
          placeholder="请输入维护建议"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入备注"
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
          v-hasPermi="['traffic:ops-police-maintenance:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          @click="handleExport"-->
<!--          :loading="exportLoading"-->
<!--          v-hasPermi="['traffic:ops-police-maintenance:export']"-->
<!--        >-->
<!--          <Icon icon="ep:download" class="mr-5px" /> 导出-->
<!--        </el-button>-->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="上报人员" align="center" prop="reportPersonnel" />
      <el-table-column label="上报时间" align="center" prop="reportTime" :formatter="dateFormatter" width="180px"/>
      <el-table-column label="上报人员部门" align="center" prop="reportDept" />
      <el-table-column label="审核时间" align="center" prop="auditDate" :formatter="dateFormatter" width="180px"/>
      <el-table-column label="审核人" align="center" prop="auditor" />
      <el-table-column label="审核意见" align="center" prop="auditOpinions" />
      <el-table-column label="维修人" align="center" prop="maintenancePersonnel" />
      <el-table-column label="维护人员手机号" align="center" prop="maintenancePhone" />
      <el-table-column label="维护时间" align="center" prop="maintenanceDate" :formatter="dateFormatter" width="180px"/>
      <el-table-column label="维护建议" align="center" prop="suggestion" />
      <el-table-column label="状态" align="center" prop="status" width="90">
        <template #default="scope">
<!--           <dict-tag :type="DICT_TYPE.MAINTENANCE_STATUS" :value="scope.row.status" />-->
          <el-tag v-if="scope.row.status == 1" type="info">待派单</el-tag>
          <el-tag v-if="scope.row.status == 6" type="info">待接单</el-tag>
          <el-tag v-if="scope.row.status == 4" type="danger">维修中</el-tag>
          <el-tag v-if="scope.row.status == 8" type="success">待审核</el-tag>
          <el-tag v-if="scope.row.status == 9" type="warning">驳回</el-tag>
          <el-tag v-if="scope.row.status == 5">已完成</el-tag>
          <el-tag v-if="scope.row.status == 7" type="danger">取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建时间" align="center" prop="createTime" :formatter="dateFormatter" width="180px"/>
      <el-table-column label="操作" align="center" min-width="220px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-police-maintenance:update']"
            v-if="scope.row.status == 8"
          >编辑</el-button>
          <el-button
            link
            type="primary"
            @click="openFormAudit('audit', scope.row.id)"
            v-hasPermi="['traffic:ops-police-maintenance:audit']"
            v-if="scope.row.status == 8"
          >审核</el-button>
          <el-button
            link
            type="primary"
            @click="openForm('detail', scope.row.id)"
            v-hasPermi="['traffic:ops-police-maintenance:update']"
          >
            详情
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-police-maintenance:delete']"
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
  <OpsPoliceMaintenanceForm ref="formRef" @success="getList" />
  <OpsPoliceMaintenanceFormAudit ref="formAuditRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsPoliceMaintenanceApi, OpsPoliceMaintenanceVO } from '@/api/traffic/opspolicemaintenance'
import OpsPoliceMaintenanceForm from './OpsPoliceMaintenanceForm.vue'
import OpsPoliceMaintenanceFormAudit from './OpsPoliceMaintenanceFormAudit.vue'

/** 警用装备报修信息 列表 */
defineOptions({ name: 'TrafficOpsPoliceMaintenance' })
import { DICT_TYPE } from '@/utils/dict'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsPoliceMaintenanceVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  reportPersonnel: undefined,
  reportUserId: undefined,
  reportTime: [],
  reportDeptId: undefined,
  reportDept: undefined,
  auditorBh: undefined,
  auditDate: [],
  auditor: undefined,
  auditOpinions: undefined,
  maintenanceUserId: undefined,
  maintenancePersonnel: undefined,
  maintenancePhone: undefined,
  maintenanceDate: [],
  suggestion: undefined,
  status: undefined,
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
    const data = await OpsPoliceMaintenanceApi.getOpsPoliceMaintenancePage(queryParams)
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

const formAuditRef = ref()
const openFormAudit = (type: string, id?: number) => {
  formAuditRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await OpsPoliceMaintenanceApi.deleteOpsPoliceMaintenance(id)
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
    const data = await OpsPoliceMaintenanceApi.exportOpsPoliceMaintenance(queryParams)
    download.excel(data, '警用装备报修信息.xls')
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
