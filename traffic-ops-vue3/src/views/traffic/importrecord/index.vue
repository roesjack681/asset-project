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
      <!-- <el-form-item label="导入类型" prop="importType">
        <el-select
          v-model="queryParams.importType"
          placeholder="请选择导入类型"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item> -->
      <el-form-item label="文件名" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="导入时间" prop="createTime">
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
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="导入类型" align="center" prop="importType" /> -->
      <!-- <el-table-column label="文件名" align="center" prop="fileName" /> -->
      <el-table-column label="导入文件" align="center" prop="importFile">
        <template #default="scope">
          <a style="color: #419eff" :href="scope.row.importFile" download>{{scope.row.fileName}}</a>
        </template>
      </el-table-column>
      <el-table-column label="导入记录数" align="center" prop="importNum" />
      <el-table-column label="成功记录数" align="center" prop="sucNum" />
      <el-table-column label="失败记录数" align="center" prop="failNum" />
      <el-table-column label="失败文件" align="center" prop="failFile" >
        <template #default="scope">
          <a v-if="scope.row.failFile" style="color: #419eff" :href="scope.row.failFile" download>下载</a>
        </template>
      </el-table-column>
      <el-table-column
        label="导入时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
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
  <ImportRecordForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { ImportRecordApi, ImportRecordVO } from '@/api/excel/importrecord'

/** 维保单导入记录 列表 */
defineOptions({ name: 'ExcelImportRecord' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<ImportRecordVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  importType: undefined,
  fileName: undefined,
  importFile: undefined,
  importNum: undefined,
  sucNum: undefined,
  failNum: undefined,
  syncSucNum: undefined,
  syncFailNum: undefined,
  failFile: undefined,
  deptId: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ImportRecordApi.getImportRecordPage(queryParams)
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

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
