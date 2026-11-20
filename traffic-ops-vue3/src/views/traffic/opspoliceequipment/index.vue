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
<!--      <el-form-item label="设备名称" prop="equName">-->
<!--        <el-input-->
<!--          v-model="queryParams.equName"-->
<!--          placeholder="请输入设备名称"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--          class="!w-240px"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="类型" prop="equType">
        <el-select
          v-model="queryParams.equType"
          placeholder="请选择类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.Police_Equ_Type)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="加密卡" prop="encryptionCard">
        <el-input
          v-model="queryParams.encryptionCard"
          placeholder="请输入加密卡"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item label="PSN" prop="psn">
        <el-input
          v-model="queryParams.psn"
          placeholder="请输入PSN"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="品牌" prop="brand">
        <el-input
          v-model="queryParams.brand"
          placeholder="请输入品牌"
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
      <el-form-item label="生产厂家" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入生产厂家"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="电池" prop="battery">
        <el-input
          v-model="queryParams.battery"
          placeholder="请输入电池"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
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
      <el-form-item label="录入人编号" prop="recordBh">
        <el-input
          v-model="queryParams.recordBh"
          placeholder="请输入录入人编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="录入人" prop="recorder">
        <el-input
          v-model="queryParams.recorder"
          placeholder="请输入录入人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="录入部门编号" prop="recordDeptBh">
        <el-input
          v-model="queryParams.recordDeptBh"
          placeholder="请输入录入部门编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="录入部门" prop="recordDept">
        <el-input
          v-model="queryParams.recordDept"
          placeholder="请输入录入部门"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="使用部门编号" prop="useDeptBh">
        <el-input
          v-model="queryParams.useDeptBh"
          placeholder="请输入使用部门编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="使用部门" prop="useDept">
        <el-input
          v-model="queryParams.useDept"
          placeholder="请输入使用部门"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="使用人编号" prop="userBh">
        <el-input
          v-model="queryParams.userBh"
          placeholder="请输入使用人编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="使用人" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入使用人"
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
      </el-form-item> -->
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-police-equipment:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button type="primary" plain @click="openImportForm()"><Icon icon="ep:upload" class="mr-5px" /> 导入</el-button>
        <el-button type="primary" plain>
          <a href="/资产运维平台及手机端操作手册.docx" style="text-decoration: none;color: inherit" download><Icon icon="ep:download" class="mr-5px" />资产运维平台及手机端操作手册</a>
        </el-button>
<!--        <el-button type="success" plain @click="handleExport" :loading="exportLoading" v-hasPermi="['traffic:ops-police-equipment:export']">-->
<!--          <Icon icon="ep:download" class="mr-5px" /> 导出-->
<!--        </el-button>-->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
<!--      <el-table-column label="设备名称" align="center" prop="equName" />-->
      <el-table-column label="类型" align="center" prop="equType" />
      <!-- <el-table-column label="加密卡" align="center" prop="encryptionCard" /> -->
      <el-table-column label="PSN" align="center" prop="psn" />
      <el-table-column label="呼号" align="center" prop="callSign" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="型号" align="center" prop="model" />
      <el-table-column label="生产厂家" align="center" prop="manufacturer" />
      <el-table-column label="供货厂家" align="center" prop="supplier" />
      <el-table-column label="电池" align="center" prop="battery" />
       <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="录入人" align="center" prop="recorder" />
      <el-table-column label="录入部门" align="center" prop="recordDept" />
      <el-table-column label="上级部门" align="center">
        <template #default="scope">
          <span>{{getParentDeptName(scope.row.useDeptBh)}}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="使用部门编号" align="center" prop="useDeptBh" /> -->
      <el-table-column label="使用部门" align="center" prop="useDept" />
      <!-- <el-table-column label="使用人编号" align="center" prop="userBh" /> -->
      <el-table-column label="使用人" align="center" prop="userName" />
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
            v-hasPermi="['traffic:ops-police-equipment:update']"
          >
            编辑
          </el-button>
          <el-button v-if="scope.row.userName" link type="danger" @click="handleRecycle(scope.row.id)" v-hasPermi="['traffic:ops-police-equipment:recycle']">
            回收
          </el-button>
          <!-- <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-police-equipment:delete']"
          >
            删除
          </el-button> -->
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
  <!-- 表单弹窗：导入 -->
  <ImportRecordForm ref="importFormRef" @success="getList" />
  <!-- 表单弹窗：添加/修改 -->
  <OpsPoliceEquipmentForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsPoliceEquipmentApi, OpsPoliceEquipmentVO } from '@/api/traffic/opspoliceequipment'
import OpsPoliceEquipmentForm from './OpsPoliceEquipmentForm.vue'
import {DICT_TYPE, getParentDeptName, getStrDictOptions} from "@/utils/dict";
import ImportRecordForm from "./OpsDjMonitorStatisticsImportForm.vue";

/** 警用装备资产信息 列表 */
defineOptions({ name: 'TrafficOpsPoliceEquipment' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsPoliceEquipmentVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equName: undefined,
  equType: undefined,
  encryptionCard: undefined,
  psn: undefined,
  brand: undefined,
  model: undefined,
  manufacturer: undefined,
  battery: undefined,
  remark: undefined,
  recordBh: undefined,
  recorder: undefined,
  recordDeptBh: undefined,
  recordDept: undefined,
  useDeptBh: undefined,
  useDept: undefined,
  userBh: undefined,
  userName: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsPoliceEquipmentApi.getOpsPoliceEquipmentPage(queryParams)
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
    await OpsPoliceEquipmentApi.deleteOpsPoliceEquipment(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
const handleRecycle = async (id: number) => {
  try {
    // 删除的二次确认
    await message.recycleConfirm()
    // 发起删除
    await OpsPoliceEquipmentApi.recycleOpsPoliceEquipment(id)
    message.success('回收成功')
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
    const data = await OpsPoliceEquipmentApi.exportOpsPoliceEquipment(queryParams)
    download.excel(data, '警用装备资产信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
const importFormRef = ref()
const openImportForm = () => {
  importFormRef.value.open()
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
