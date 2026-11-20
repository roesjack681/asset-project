<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="90px"
    >
      <el-form-item label="管理部门" prop="glbm">
        <el-input
          v-model="queryParams.glbm"
          placeholder="请输入管理部门"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="道路代码" prop="dldm">
        <el-input
          v-model="queryParams.dldm"
          placeholder="请输入道路代码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item label="行政区划" prop="xzqh">
        <el-cascader
              v-model="queryParams.xzqh"
              :options="areaList"
              :props="defaultProps"
              class="!w-240px"
              clearable
              filterable
              placeholder="请选择城市"
            />
      </el-form-item>
      <!-- <el-form-item label="路段代码" prop="lddm">
        <el-input
          v-model="queryParams.lddm"
          placeholder="请输入路段代码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item label="道路名称" prop="dlmc">
        <el-input
          v-model="queryParams.dlmc"
          placeholder="请输入道路名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="交叉口名称" prop="ldmc">
        <el-input
          v-model="queryParams.ldmc"
          placeholder="请输入路段名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="起始里程" prop="qslc">
        <el-input
          v-model="queryParams.qslc"
          placeholder="请输入起始里程"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="结束里程" prop="jslc">
        <el-input
          v-model="queryParams.jslc"
          placeholder="请输入结束里程"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
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
      <!-- <el-form-item label="删除时间" prop="deletedTime">
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
          v-hasPermi="['traffic:ops-intersection:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-intersection:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="交叉口名称" align="center" prop="ldmc" min-width="220px"/>
      <el-table-column label="管理部门" align="center" prop="glbm" />
      <!-- <el-table-column label="道路代码" align="center" prop="dldm" /> -->
      <el-table-column label="行政区划" align="center" prop="xzqh">
        <template #default="scope">
          {{ formatToName(scope.row.xzqh) }}
        </template>
      </el-table-column>
      <!-- <el-table-column label="路段代码" align="center" prop="lddm" /> -->
      <el-table-column label="道路名称" align="center" prop="dlmc" min-width="100px"/>
      
      <el-table-column label="起始里程" align="center" prop="qslc" />
      <el-table-column label="结束里程" align="center" prop="jslc" />
      <!-- <el-table-column
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
      /> -->
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-intersection:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-intersection:delete']"
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
  <OpsIntersectionForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsIntersectionApi, OpsIntersectionVO } from '@/api/traffic/opsintersection'
import OpsIntersectionForm from './OpsIntersectionForm.vue'
import {getAreaTree} from '@/api/system/area'
import { defaultProps } from '@/utils/tree'
/** 交叉口信息 列表 */
defineOptions({ name: 'TrafficOpsIntersection' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsIntersectionVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  glbm: undefined,
  dldm: undefined,
  xzqh: undefined,
  lddm: undefined,
  dlmc: undefined,
  ldmc: undefined,
  qslc: undefined,
  jslc: undefined,
  createTime: [],
  deletedTime: []
})
const areaList = ref([])
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsIntersectionApi.getOpsIntersectionPage(queryParams)
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
    await OpsIntersectionApi.deleteOpsIntersection(id)
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
    const data = await OpsIntersectionApi.exportOpsIntersection(queryParams)
    download.excel(data, '交叉口信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const getAreaTree2 = async () => {
  const data = await getAreaTree()
  areaList.value = data.filter(e => e.id == '150000')[0].children
}
const formatToName = (code) => {
  let name = ''
  if (code) {
    areaList.value.forEach(e => {
      if(e.id == code) {
        name = e.name
      } else {
        if(e.children.length) {
          e.children.forEach(x => {
            if(x.id == code) {
              name = x.name
            }
          })
        }
      }
    })
  }
  return name
}

/** 初始化 **/
onMounted(() => {
  getList()
  getAreaTree2()
})
</script>