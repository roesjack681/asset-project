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
      <el-form-item label="维保主键" prop="maintenanceId">
        <el-input
          v-model="queryParams.maintenanceId"
          placeholder="请输入维保主键"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="护栏主键" prop="guardrailId">
        <el-input
          v-model="queryParams.guardrailId"
          placeholder="请输入护栏主键"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="护栏维护类型：清洗；更换；拆除" prop="maintenanceType">
        <el-select
          v-model="queryParams.maintenanceType"
          placeholder="请选择护栏维护类型：清洗；更换；拆除"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="更换类型：损毁；换新" prop="changeType">
        <el-select
          v-model="queryParams.changeType"
          placeholder="请选择更换类型：损毁；换新"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="起始位置名称" prop="startName">
        <el-input
          v-model="queryParams.startName"
          placeholder="请输入起始位置名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="起始位置经度" prop="startLng">
        <el-input
          v-model="queryParams.startLng"
          placeholder="请输入起始位置经度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="起始位置纬度" prop="startLat">
        <el-input
          v-model="queryParams.startLat"
          placeholder="请输入起始位置纬度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="截止位置名称" prop="endName">
        <el-input
          v-model="queryParams.endName"
          placeholder="请输入截止位置名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="截止位置经度" prop="endLng">
        <el-input
          v-model="queryParams.endLng"
          placeholder="请输入截止位置经度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="截止位置纬度" prop="endLat">
        <el-input
          v-model="queryParams.endLat"
          placeholder="请输入截止位置纬度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="是否增加开口" prop="isGap">
        <el-input
          v-model="queryParams.isGap"
          placeholder="请输入是否增加开口"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="变更前产品编号" prop="moditfyBeProduct">
        <el-input
          v-model="queryParams.moditfyBeProduct"
          placeholder="请输入变更前产品编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="变更后产品编号" prop="moditfyAfProduct">
        <el-input
          v-model="queryParams.moditfyAfProduct"
          placeholder="请输入变更后产品编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="变更前图片" prop="moditfyBeImg">
        <el-input
          v-model="queryParams.moditfyBeImg"
          placeholder="请输入变更前图片"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="变更后图片" prop="moditfyAfImg">
        <el-input
          v-model="queryParams.moditfyAfImg"
          placeholder="请输入变更后图片"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="变更片数" prop="pieces">
        <el-input
          v-model="queryParams.pieces"
          placeholder="请输入变更片数"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="变更内容描述" prop="describe">
        <el-input
          v-model="queryParams.describe"
          placeholder="请输入变更内容描述"
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
      <el-form-item label="赔付类型：保险；运维" prop="claimType">
        <el-select
          v-model="queryParams.claimType"
          placeholder="请选择赔付类型：保险；运维"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否首位置变更" prop="isHeadModitfy">
        <el-input
          v-model="queryParams.isHeadModitfy"
          placeholder="请输入是否首位置变更"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="是否尾位置变更" prop="isTailModitfy">
        <el-input
          v-model="queryParams.isTailModitfy"
          placeholder="请输入是否尾位置变更"
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
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-maintenance-guardrail:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-maintenance-guardrail:export']"
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
      <el-table-column label="维保主键" align="center" prop="maintenanceId" />
      <el-table-column label="护栏主键" align="center" prop="guardrailId" />
      <el-table-column label="护栏维护类型：清洗；更换；拆除" align="center" prop="maintenanceType" />
      <el-table-column label="更换类型：损毁；换新" align="center" prop="changeType" />
      <el-table-column label="起始位置名称" align="center" prop="startName" />
      <el-table-column label="起始位置经度" align="center" prop="startLng" />
      <el-table-column label="起始位置纬度" align="center" prop="startLat" />
      <el-table-column label="截止位置名称" align="center" prop="endName" />
      <el-table-column label="截止位置经度" align="center" prop="endLng" />
      <el-table-column label="截止位置纬度" align="center" prop="endLat" />
      <el-table-column label="是否增加开口" align="center" prop="isGap" />
      <el-table-column label="变更前产品编号" align="center" prop="moditfyBeProduct" />
      <el-table-column label="变更后产品编号" align="center" prop="moditfyAfProduct" />
      <el-table-column label="变更前图片" align="center" prop="moditfyBeImg" />
      <el-table-column label="变更后图片" align="center" prop="moditfyAfImg" />
      <el-table-column label="变更片数" align="center" prop="pieces" />
      <el-table-column label="变更内容描述" align="center" prop="describe" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="赔付类型：保险；运维" align="center" prop="claimType" />
      <el-table-column label="是否首位置变更" align="center" prop="isHeadModitfy" />
      <el-table-column label="是否尾位置变更" align="center" prop="isTailModitfy" />
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
            v-hasPermi="['traffic:ops-maintenance-guardrail:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-maintenance-guardrail:delete']"
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
  <OpsMaintenanceGuardrailForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsMaintenanceGuardrailApi, OpsMaintenanceGuardrailVO } from '@/api/traffic/opsmaintenanceguardrail'
import OpsMaintenanceGuardrailForm from './OpsMaintenanceGuardrailForm.vue'

/** 护栏维护详细 列表 */
defineOptions({ name: 'TrafficOpsMaintenanceGuardrail' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsMaintenanceGuardrailVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  maintenanceId: undefined,
  guardrailId: undefined,
  maintenanceType: undefined,
  changeType: undefined,
  startName: undefined,
  startLng: undefined,
  startLat: undefined,
  endName: undefined,
  endLng: undefined,
  endLat: undefined,
  isGap: undefined,
  moditfyBeProduct: undefined,
  moditfyAfProduct: undefined,
  moditfyBeImg: undefined,
  moditfyAfImg: undefined,
  pieces: undefined,
  describe: undefined,
  remark: undefined,
  claimType: undefined,
  isHeadModitfy: undefined,
  isTailModitfy: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsMaintenanceGuardrailApi.getOpsMaintenanceGuardrailPage(queryParams)
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
    await OpsMaintenanceGuardrailApi.deleteOpsMaintenanceGuardrail(id)
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
    const data = await OpsMaintenanceGuardrailApi.exportOpsMaintenanceGuardrail(queryParams)
    download.excel(data, '护栏维护详细.xls')
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
