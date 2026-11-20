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
      <!-- <el-form-item label="设备类型" prop="equType">
        <el-select
          v-model="queryParams.equType"
          placeholder="请选择设备类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.EQU_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item> -->
      <el-form-item label="报修地点" prop="equAddr">
        <el-input
          v-model="queryParams.equAddr"
          placeholder="请输入报修地点"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="存在问题" prop="problem">
        <el-input
          v-model="queryParams.problem"
          placeholder="请输入存在问题"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="故障分类" prop="faultType">
        <el-input
          v-model="queryParams.faultType"
          placeholder="请输入故障分类"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="equType">
        <el-select
          v-model="queryParams.equType"
          placeholder="请选择设备类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.EQU_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择检测状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.MAINTENANCE_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="报修时间" prop="faultDate">
        <el-date-picker
          v-model="queryParams.faultDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="上报人员" prop="reportPersonnel">
        <el-input
          v-model="queryParams.reportPersonnel"
          placeholder="请输入上报人员"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="派单人" prop="dispatch">
        <el-input
          v-model="queryParams.dispatch"
          placeholder="请输入派单人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="维护人员" prop="maintenancePersonnel">
        <el-input
          v-model="queryParams.maintenancePersonnel"
          placeholder="请输入维护人员"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="维护内容" prop="suggestion">
        <el-input
          v-model="queryParams.suggestion"
          placeholder="请输入维护内容"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="完成时间" prop="finishTime">
        <el-date-picker
          v-model="queryParams.finishTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="维修次数" prop="repairCntOperator">
        <div style="display: flex">
          <el-select
            v-model="queryParams.repairCntOperator"
            clearable
            style="margin-right: 5px; width: 110px;"
          >
            <el-option label="大于" value="1"/>
            <el-option label="大于等于" value="2"/>
            <el-option label="小于" value="3"/>
            <el-option label="小于等于" value="4"/>
            <el-option label="等于" value="5"/>
          </el-select>
          <el-input
            v-model="queryParams.repairCnt"
            placeholder="维修次数"
            clearable
            style="margin-right: 5px; width: 124px;"
            @keyup.enter="handleQuery"
          />
        </div>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openFormAdd('create')"
          v-hasPermi="['traffic:ops-maintenance:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="primary"
          plain
          @click="openFormSupplement('create')"
          v-hasPermi="['traffic:ops-maintenance:supplement']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 补录
        </el-button>
        <el-button
          type="primary"
          plain
          @click="openImportForm()"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 导入
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-maintenance:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="上报人员" align="center" prop="reportPersonnel" width="80px"/>
      <!-- <el-table-column label="报修地点" align="center" prop="equAddr" /> -->
      <el-table-column label="存在问题" align="center" prop="problem" width="480px"/>
      <el-table-column label="故障分类" align="center" prop="faultType" width="180px"/>
      <el-table-column label="派单人" align="center" prop="dispatch" />
      <el-table-column label="预计修复难度" align="center" prop="projectedRepair" />
      <el-table-column label="实际修复难度" align="center" prop="actualRepair" />
      <el-table-column label="维护人员"  width="150px" align="center" prop="maintenancePersonnel" />
      <el-table-column label="维修次数" align="center" prop="repairCnt">
        <template #default="scope">
          {{ scope.row.repairCnt }}
          <icon v-if="scope.row.repairCnt> 0" @click="showMaintenanceInfo(scope.row.equId)" style="color:#3F9EFF;margin-left: 5px;cursor:pointer;" icon="ep:search" :size="12" type="Success"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="118">
        <template #default="scope">
          <el-tag v-if="scope.row.status == 1" type="info">待派单</el-tag>
          <el-tag v-if="scope.row.status == 6" type="info">待接单</el-tag>
          <el-tag v-if="scope.row.status == 4" type="danger">维修中</el-tag>
          <el-tag v-if="scope.row.status == 10" type="warning">暂时无法修复</el-tag>
          <el-tag v-if="scope.row.status == 8" type="success">待确认</el-tag>
          <el-tag v-if="scope.row.status == 9" type="warning">驳回</el-tag>
          <el-tag v-if="scope.row.status == 5">已完成</el-tag>
          <el-tag v-if="scope.row.status == 7" type="danger">取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="报修时间"
        align="center"
        width="160px"
      >
        <template #default="scope">
          <span >
             {{dateFormatter3(scope.row.faultDate)}}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        label="完成时间"
        align="center"
        width="160px"
      >
        <template #default="scope">
          <span >
             {{dateFormatter3(scope.row.finishTime)}}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        label="用时[小时]"
        align="center"
        width="120px"
      >
        <template #default="scope">
          <span >
             {{ scope.row.workTime }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="245px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            v-if="scope.row.status != '5'"
            @click="handleEdit('update', scope.row.id)"
            v-hasPermi="['traffic:ops-maintenance:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="primary"
            v-if="scope.row.status=='1'"
            @click="handleReDispatch(scope.row.id)"
            v-hasPermi="['traffic:ops-maintenance:dispatch']"
          >
            派单
          </el-button>
          <el-button
            link
            type="primary"
            v-if="scope.row.status == '6' || scope.row.status == '4' || scope.row.status == '9' || scope.row.status == '10'"
            @click="handleReDispatch(scope.row.id)"
            v-hasPermi="['traffic:ops-maintenance:redispatch']"
          >
            重派
          </el-button>
          <el-button
            link
            type="primary"
            v-if="scope.row.status == '4'"
            @click="handleMountOrder(scope.row.id)"
            v-hasPermi="['traffic:ops-maintenance:mount']"
          >
            挂单
          </el-button>
          <el-button
            link
            type="primary"
            v-if="scope.row.status == '8'"
            @click="handleConfirm(scope.row.id)"
            v-hasPermi="['traffic:ops-maintenance:confirm']"
          >
            确认
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('detail', scope.row.id)"
            v-hasPermi="['traffic:ops-maintenance:query']"
          >
            详情
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-maintenance:delete']"
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
  <OpsMaintenanceForm ref="formRef" @success="getList" />
  <OpsMaintenanceSupplementForm ref="formSupplementRef" @success="getList" @gotoDetail="gotoDetailForm"/>
  <OpsMaintenanceEditForm ref="opsMaintenanceEditFormRef" @success="getList" />

  <OpsMaintenanceAddForm ref="formAddRef" @success="getList" @gotoDetail="gotoDetailForm"/>
  <OpsMaintenanceDispatchForm ref="formDispatchRef" @success="getList"/>
  <!-- 表单弹窗：添加/修改 -->
  <ImportRecordForm ref="importFormRef" @success="getList" />

  <OpsMaintenanceReDispatchForm ref="formReDispatchRef"  @success="getList"/>
  <OpsMaintenanceMountOrderForm ref="formMountOrderRef"  @success="getList"/>

  <OpsMaintenanceConfirmForm ref="confirmFormRef"  @success="getList"/>
  <OpsMaintenanceDetailForm ref="maintenanceFormRef" @success="getList" />
</template>

<script setup lang="ts">
import {dateFormatter, dateFormatter2, dateFormatter3} from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import OpsMaintenanceForm from './OpsMaintenanceForm.vue'
import OpsMaintenanceSupplementForm from './OpsMaintenanceAddFormAll.vue'
import OpsMaintenanceEditForm from './OpsMaintenanceEditForm.vue'
import OpsMaintenanceAddForm from './OpsMaintenanceAddForm.vue'
import OpsMaintenanceDispatchForm from './OpsMaintenanceDispatchForm.vue'
import OpsMaintenanceReDispatchForm from '../common/OpsMaintenanceReDispatchForm.vue'
import OpsMaintenanceMountOrderForm from '../common/OpsMaintenanceMountOrderForm.vue'
import OpsMaintenanceConfirmForm from '../common/OpsMaintenanceConfirmForm.vue'
import OpsMaintenanceDetailForm from '../common/OpsMaintenanceDetailForm.vue'
import ImportRecordForm from './OpsMaintenanceImportForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";


/** 设备维保信息 列表 */
defineOptions({ name: 'TrafficOpsMaintenance' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsMaintenanceVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  affiliatedUnit: 1,
  source: 1,
  equType: undefined,
  equId: undefined,
  equAddr: undefined,
  problem: undefined,
  faultType: undefined,
  faultDate: [],
  maintenance: undefined,
  maintenanceDate: [],
  reportPersonnel: undefined,
  maintenancePersonnel: undefined,
  suggestion: undefined,
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
    const data = await OpsMaintenanceApi.getOpsMaintenancePage(queryParams)
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

/** 确认按钮操作 */
const confirmFormRef = ref()
const handleConfirm = async (id: number) => {
  confirmFormRef.value.open(id)
}

/** 添加/修改操作 */
const formRef = ref()
const formSupplementRef = ref()
const opsMaintenanceEditFormRef = ref()
const formAddRef = ref()
// const formDispatchRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}
const gotoDetailForm = (id?: number) => {
  openForm('detail', id)
}
// const openFormPD = (type: string, id?: number) => {
//   formDispatchRef.value.open(type, id)
// }
const openFormAdd = (type: string, id?: number) => {
  formAddRef.value.open(type, id)
}

const openFormSupplement = (type: string, id?: number) => {
  formSupplementRef.value.open(type, id)
}
const handleEdit =  (type: string, id?: number) => {
  opsMaintenanceEditFormRef.value.open(type, id)
}
const formReDispatchRef = ref()
const handleReDispatch = (id?: number) => {
  formReDispatchRef.value.open(id)
}

const handleMountOrder = async (id: number) => {

  try {
    // 挂单的二次确认
    const data = await message.prompt('请输入暂时无法修复原因', t('common.reminder'))
    if (data?.action !== 'confirm') throw 'cancel'
    // 发起挂单
    await OpsMaintenanceApi.mountOrder({
      'id': id,
      'remark': data.value
    })
    message.success(t('common.saveSuccess'))
    // 刷新列表
    await getList()
  } catch(error) {
    if (error === 'cancel') return
  } finally {
    getList()
  }
}

/** 添加/修改操作 */
const maintenanceFormRef = ref()
const showMaintenanceInfo = (id: number) => {
  maintenanceFormRef.value.open(id)
}

const importFormRef = ref()
const openImportForm = () => {
  importFormRef.value.open()
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await OpsMaintenanceApi.deleteOpsMaintenance(id)
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
    const data = await OpsMaintenanceApi.exportOpsMaintenance(queryParams)
    download.excel(data, '设备维保信息.xls')
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
