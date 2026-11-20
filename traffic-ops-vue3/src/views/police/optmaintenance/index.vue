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
      <el-form-item label="状态" prop="equType">
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
      <el-form-item label="确认时间" prop="confirmDate">
        <el-date-picker
          v-model="queryParams.confirmDate"
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
       <el-form-item label="确认人员" prop="confirmor">
        <el-input
          v-model="queryParams.confirmor"
          placeholder="请输入确认人员"
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
      <el-form-item label="数据标签" prop="equLableList">
        <el-select
          v-model="queryParams.equLableList"
          multiple
          placeholder="请选择数据标签"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.POLICE_ELECTRIC_LABLE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
       </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openFormAdd('create')"
          v-hasPermi="['police:ops-maintenance:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="primary"
          plain
          @click="openFormSupplement('create')"
          v-hasPermi="['police:ops-maintenance:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 补录
        </el-button>
        <el-button
          type="success"
          plain
          @click="openBatchDispatchForm('batchDispatch')"
          v-hasPermi="['police:ops-maintenance:dispatch']"
        >
          <Icon icon="ep:finished" class="mr-5px" /> 批量派单
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
          v-hasPermi="['police:ops-maintenance:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="false">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="上报人员" align="center" prop="reportPersonnel"/>
      <el-table-column label="报修地点" align="center" prop="equAddr" :show-overflow-tooltip="true"/>
      <el-table-column label="存在问题" align="center" prop="problem" :show-overflow-tooltip="true" />
      <el-table-column label="故障分类" align="center" prop="faultType" />
      <el-table-column label="派单人" align="center" prop="dispatch" min-width="74"/>
      <el-table-column label="维护人员" align="center" prop="maintenancePersonnel" min-width="84" />
      <el-table-column label="确认人" align="center" prop="confirmor" min-width="74"/>
      <el-table-column label="IP地址" align="center" prop="confirmor" min-width="174">
        <template #default="scope">
          <div class="worklist-info-item" v-if="!scope.row.newIpAddr">
            {{scope.row.ipAddr || '暂无'}}
          </div>
          <div class="worklist-info-item" v-else>
            <span style="font-size: 10px;border-radius: 50%;padding: 3px 5px;background: red;color: #ffffff">原</span><span>{{scope.row.ipAddr || '暂无'}}</span>;
            <span style="font-size: 10px;border-radius: 50%;padding: 3px 5px;background: #00e804;color: #ffffff">新</span><span>{{scope.row.newIpAddr || '暂无'}}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" min-width="124">
        <template #default="scope">
          <!-- <dict-tag :type="DICT_TYPE.MAINTENANCE_STATUS" :value="scope.row.status" /> -->
          <el-tag v-if="scope.row.status == 1" type="info">待派单</el-tag>
          <el-tag v-if="scope.row.status == 6" type="info">待接单</el-tag>
          <el-tag v-if="scope.row.status == 4" type="danger">维修中</el-tag>
          <el-tag v-if="scope.row.status == 8" type="success">待确认</el-tag>
          <el-tag v-if="scope.row.status == 9" type="warning">驳回</el-tag>
          <el-tag v-if="scope.row.status == 5">已完成</el-tag>
          <el-tag v-if="scope.row.status == 7" type="danger">取消</el-tag>
          <el-tag v-if="scope.row.status == 10" type="info">暂时无法修复</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="报修时间"
        align="center"
        width="180px"
      >
        <template #default="scope">
          <span v-if="scope.row.faultDate">
             {{dateFormatter3(scope.row.faultDate)}}
          </span>
          <span v-else>
             {{dateFormatter3(scope.row.faultDate)}}
          </span>
        </template>
      </el-table-column>
       <el-table-column
        label="确认时间"
        align="center"
        width="180px"
      >
        <template #default="scope">
          <span v-if="scope.row.confirmDate">
             {{dateFormatter3(scope.row.confirmDate)}}
          </span>
          <span v-else>
             {{dateFormatter3(scope.row.confirmDate)}}
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
      <el-table-column label="设备标签" align="center" prop="equLabel"/>
      <el-table-column label="操作" align="center" width="220px" :show-overflow-tooltip="false">
        <template #default="scope">
<!--          <el-button-->
<!--            v-if="scope.row.status == 1"-->
<!--            link-->
<!--            type="primary"-->
<!--            @click="openFormPD('create', scope.row.id)"-->
<!--            v-hasPermi="['police:ops-maintenance:query']"-->
<!--          >-->
<!--            派单-->
<!--          </el-button>-->
          <el-button
            link
            type="primary"
            @click="openFormSupplement('update', scope.row.id)"
            v-hasPermi="['police:ops-maintenance:create']"
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
            v-hasPermi="['police:ops-maintenance:confirm']"
          >
            确认
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('detail', scope.row.id)"
            v-hasPermi="['police:ops-maintenance:query']"
          >
            详情
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['police:ops-maintenance:delete']"
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
  <OpsMaintenanceAddFormAll ref="formSupplementRef" @success="getList" @gotoDetail="gotoDetailForm"/>
  <OpsMaintenanceAddForm ref="formAddRef" @success="getList" @gotoDetail="gotoDetailForm"/>
  <OpsMaintenanceDispatchForm ref="formDispatchRef" @success="getList"/>
  <OpsMaintenanceBatchDispatchForm ref="formBatchDispatchForm" @success="getList"/>
  <!-- 表单弹窗：添加/修改 -->
  <ImportRecordForm ref="importFormRef" @success="getList" />
  <!--  派单重派-->
  <OpsMaintenanceReDispatchForm ref="formReDispatchRef"  @success="getList"/>

  <OpsMaintenanceConfirmForm ref="confirmFormRef"  @success="getList"/>
</template>

<script setup lang="ts">
import {dateFormatter, dateFormatter2, dateFormatter3} from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import OpsMaintenanceForm from './OpsMaintenanceForm.vue'
import OpsMaintenanceAddFormAll from './OpsMaintenanceAddFormAll.vue'
import OpsMaintenanceDispatchForm from './OpsMaintenanceDispatchForm.vue'
import OpsMaintenanceBatchDispatchForm from './OpsMaintenanceBatchDispatchForm.vue'
import OpsMaintenanceConfirmForm from '../common/OpsMaintenanceConfirmForm.vue'
import OpsMaintenanceAddForm from './OpsMaintenanceAddForm.vue'
import ImportRecordForm from './OpsMaintenanceImportForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import OpsMaintenanceReDispatchForm from "./OpsMaintenanceReDispatchForm.vue";

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
  affiliatedUnit: 2,
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
  confirmor: undefined,
  suggestion: undefined,
  remark: undefined,
  confirmDate: [],
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

/** 添加/修改操作 */
const formRef = ref()
const formAddRef = ref()
const formReDispatchRef = ref()
const formSupplementRef = ref()
const formDispatchRef = ref()
const formBatchDispatchForm = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}
const gotoDetailForm = (id?: number) => {
  openForm('detail', id)
}
const openFormPD = (type: string, id?: number) => {
  formDispatchRef.value.open(type, id)
}

const openBatchDispatchForm = (type: string, id?: number) => {
  formBatchDispatchForm.value.open(type, id)
}
const openFormAdd = (type: string, id?: number) => {
  formAddRef.value.open(type, id)
}
const handleReDispatch = (id?: number) => {
  formReDispatchRef.value.open(id)
}
const openFormSupplement = (type: string, id?: number) => {
  formSupplementRef.value.open(type, id)
}
const handleMountOrder = async (id: number) => {

  try {
    // 挂单的二次确认
    const data = await message.prompt('请输入暂时无法修复原因', t('common.reminder'))
    if (data?.action !== 'confirm') throw 'cancel'
    // 发起挂单
    await OpsMaintenanceApi.mountOrder({
      'id': id,
      'remark': data.value,
      affiliatedUnit: '2'
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

/** 确认按钮操作 */
const confirmFormRef = ref()
const handleConfirm = async (id: number) => {
  confirmFormRef.value.open(id)
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
