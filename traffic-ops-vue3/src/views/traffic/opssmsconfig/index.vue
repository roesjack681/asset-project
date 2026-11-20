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
      <el-form-item label="发送类型" prop="sendType">
        <el-select
          v-model="queryParams.sendType"
          placeholder="请选择发送类型"
          clearable
          class="!w-240px"
        >
          <el-option label="重点考核视频" value="1" />
          <el-option label="重点考核卡口" value="2" />
          <el-option label="重点考核视频，重点考核卡口" value="1,2" />
        </el-select>
      </el-form-item>
      <el-form-item label="接收人姓名" prop="receiveName">
        <el-input
          v-model="queryParams.receiveName"
          placeholder="请输入接收人姓名"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="接收人手机号" prop="receivePhone">
        <el-input
          v-model="queryParams.receivePhone"
          placeholder="请输入接收人手机号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="接收状态" prop="receiveStatus">
        <el-select
          v-model="queryParams.receiveStatus"
          placeholder="请选择接收状态"
          clearable
          class="!w-240px"
        >
          <el-option label="on" value="on" />
          <el-option label="off" value="off" />
        </el-select>
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
          v-hasPermi="['traffic:ops-sms-config:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-sms-config:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="发送类型" align="center" prop="sendType">
        <template #default="scope">
          <span v-if="scope.row.sendType == 1">重点考核视频</span>
          <span v-if="scope.row.sendType == 2">重点考核卡口</span>
          <span v-if="scope.row.sendType == '1,2'">重点考核视频，重点考核卡口</span>
        </template>
      </el-table-column>
      <el-table-column label="接收人姓名" align="center" prop="receiveName" />
      <el-table-column label="接收人手机号" align="center" prop="receivePhone" />
      <el-table-column label="接收状态" align="center" prop="receiveStatus" />
      <el-table-column label="创建时间" align="center" prop="createTime" :formatter="dateFormatter" width="180px"/>
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-sms-config:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-sms-config:delete']"
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
  <OpsSmsConfigForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsSmsConfigApi, OpsSmsConfigVO } from '@/api/traffic/opssmsconfig'
import OpsSmsConfigForm from './OpsSmsConfigForm.vue'

/** 资产短信发送配置 列表 */
defineOptions({ name: 'TrafficOpsSmsConfig' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsSmsConfigVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  sendType: undefined,
  receiveName: undefined,
  receivePhone: undefined,
  userBh: undefined,
  receiveStatus: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsSmsConfigApi.getOpsSmsConfigPage(queryParams)
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
    await OpsSmsConfigApi.deleteOpsSmsConfig(id)
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
    const data = await OpsSmsConfigApi.exportOpsSmsConfig(queryParams)
    download.excel(data, '资产短信发送配置.xls')
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
