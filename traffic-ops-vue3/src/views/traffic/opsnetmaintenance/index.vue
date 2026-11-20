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
      <!-- <el-form-item label="故障分类" prop="faultType">
        <el-input
          v-model="queryParams.faultType"
          placeholder="请输入故障分类"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
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
      <el-table-column
        label="报修时间"
        align="center"
        width="160px"
      >
        <template #default="scope">
          <span>
             {{dateFormatter3(scope.row.faultDate)}}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="存在问题" align="center" prop="problem" width="480px"/>
      <!-- <el-table-column label="故障分类" align="center" prop="faultType" /> -->
      <el-table-column label="派单人" align="center" prop="dispatch" width="120px"/>
      <el-table-column label="维护人员"  width="150px" align="center" prop="maintenancePersonnel" />
      <el-table-column label="状态" align="center" prop="status" width="90">
        <template #default="scope">
          <!-- <dict-tag :type="DICT_TYPE.MAINTENANCE_STATUS" :value="scope.row.status" /> -->
          <el-tag v-if="scope.row.status == 1" type="info">待派单</el-tag>
          <el-tag v-if="scope.row.status == 6" type="info">待接单</el-tag>
          <el-tag v-if="scope.row.status == 4" type="danger">维修中</el-tag>
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
      <el-table-column label="操作" align="center" width="250px">
        <template #default="scope">
<!--          <el-button-->
<!--            v-if="scope.row.status == 1"-->
<!--            link-->
<!--            type="primary"-->
<!--            @click="openFormPD('create', scope.row.id)"-->
<!--            v-hasPermi="['traffic:ops-maintenance:query']"-->
<!--          >-->
<!--            派单-->
<!--          </el-button>-->
          <el-button
            link
            type="primary"
            v-if="scope.row.status=='1'"
            @click="openFormDispatch('dispatch', scope.row.id)"
            v-hasPermi="['traffic:net-maintenance:dispatch']"
          >
            派单
          </el-button>
          <el-button
            link
            type="primary"
            v-if="scope.row.status == '6' || scope.row.status == '4' || scope.row.status == '9'"
            @click="handleReDispatch(scope.row.id)"
            v-hasPermi="['traffic:net-maintenance:redispatch']"
          >
            重派
          </el-button>
          <el-button
            link
            type="primary"
            @click="handleNoticeMsg(scope.row.id, scope.row.reportUserId)"
            v-hasPermi="['traffic:net-maintenance:notice']"
          >
            短信通知
          </el-button>
          <el-button
            link
            type="primary"
            v-if="scope.row.status == '8'"
            @click="handleConfirm(scope.row.id)"
            v-hasPermi="['traffic:net-maintenance:confirm']"
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
            v-if="scope.row.status=='1' || scope.row.status=='6'"
            @click="cancelForm(scope.row.id)"
            v-hasPermi="['traffic:net-maintenance:cancel']"
          >
            取消
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
  <OpsMaintenanceDispatchForm ref="formDispatchRef" @success="getList"/>

  <OpsMaintenanceReDispatchForm ref="formReDispatchRef" @success="getList"/>

  <OpsNoticeMsgForm ref="formNoticeMsgRef" @success="getList"/>

  <OpsMaintenanceConfirmForm ref="confirmFormRef"  @success="getList"/>
</template>

<script setup lang="ts">
import {dateFormatter, dateFormatter2, dateFormatter3} from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import OpsMaintenanceForm from './OpsMaintenanceForm.vue'
import OpsMaintenanceDispatchForm from './OpsMaintenanceDispatchForm.vue'
import OpsMaintenanceReDispatchForm from '../common/OpsMaintenanceReDispatchForm.vue'
import OpsMaintenanceConfirmForm from '../common/OpsMaintenanceConfirmForm.vue'
import OpsNoticeMsgForm from '../common/OpsNoticeMsgForm.vue'
import { useUserStore } from '@/store/modules/user'
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
  source: 2,
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

/** 添加/修改操作 */
const formRef = ref()
const formDispatchRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}
const openFormDispatch = (type: string, id?: number) => {
  formDispatchRef.value.open(type, id)
}

const formReDispatchRef = ref()
const handleReDispatch = (id?: number) => {
  formReDispatchRef.value.open(id)
}

const formNoticeMsgRef = ref()
const handleNoticeMsg = (businessId, userId?) => {
  formNoticeMsgRef.value.open(businessId, userId)
}

/** 确认按钮操作 */
const confirmFormRef = ref()
const handleConfirm = async (id: number) => {
  confirmFormRef.value.open(id)
}

const cancelForm = (id) => {
  console.log(useUserStore().getUser)
  ElMessageBox.prompt('请输入取消原因', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /^\S+$/,
    inputErrorMessage: '原因不能为空',
  })
    .then(async({ value }) => {
      const data = {
        id: id,
        cancelReason: value,
        cancelUserId: useUserStore().getUser.id,
        cancelPersonnel: useUserStore().getUser.nickname
      }
      await OpsMaintenanceApi.cancelRepairOrderPc(data)
      message.success(t('维修订单取消成功'))
      handleQuery()
    })
    .catch(() => {

    });
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
