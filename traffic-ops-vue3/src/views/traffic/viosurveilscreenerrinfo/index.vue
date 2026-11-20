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
      <el-form-item label="设备编号" prop="sbbh">
        <el-input
          v-model="queryParams.sbbh"
          placeholder="请输入设备编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="行政区域" prop="unitList">
        <el-select v-model="queryParams.unitList" multiple filterable clearable class="!w-240px">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
            :key="dict.label"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="号牌号码" prop="hphm">
        <el-input
          v-model="queryParams.hphm"
          placeholder="请输入号牌号码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="无效原因" prop="cwyy">
        <el-select
          v-model="queryParams.cwyy"
          placeholder="请选择无效原因"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.JZ_INVALID_REASON)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否复核" prop="isReview">
        <el-select
          v-model="queryParams.isReview"
          placeholder="请选择是否复核"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option label="是" value="1"/>
          <el-option label="否" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="复核原因" prop="reviewResult">
        <el-select
          v-model="queryParams.reviewResult"
          placeholder="请选择复核原因"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.JZ_INVALID_REASON)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否一致" prop="isConsistentAuditReason">
        <el-select
          v-model="queryParams.isConsistentAuditReason"
          placeholder="请选择是否一致"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option label="是" value="1"/>
          <el-option label="否" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="违法时间" prop="wfsj">
        <el-date-picker
          v-model="queryParams.wfsj"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="录入时间" prop="lrsj">
        <el-date-picker
          v-model="queryParams.lrsj"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="复核时间" prop="reviewDate">
        <el-date-picker
          v-model="queryParams.reviewDate"
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
        <el-button type="success" plain @click="openAuditForm('detail')" v-hasPermi="['traffic:view-vio-surveil-screen-err-info:review']">
          <Icon icon="ep:star" class="mr-5px" /> 复核</el-button>
        <!-- <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:view-vio-surveil-screen-err-info:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:view-vio-surveil-screen-err-info:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="设备编号" align="center" prop="sbbh" />
      <el-table-column label="号牌号码" align="center" prop="hphm" />
      <el-table-column label="号牌种类" align="center" prop="hpzl" />
      <el-table-column
        label="违法时间"
        align="center"
        prop="wfsj"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="违法行为" align="center" prop="wfxw" />
      <el-table-column label="无效原因" align="center" prop="cwyy" />
      <el-table-column label="复核原因" align="center" prop="reviewResult" />
      <el-table-column label="图片1" align="center" prop="url1">
        <template #default="scope">
          <div class="qr-code-container">
            <el-image
              style="display: flex; flex-wrap: wrap; cursor: pointer;"
              :src="scope.row.url1"
              fit="cover"
              v-if="scope.row.url1"
              :z-index="2001"
              :preview-src-list="[scope.row.url1]"
              :preview-teleported="true"
              @click.stop
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="图片2" align="center" prop="url2">
        <template #default="scope">
          <div class="qr-code-container">
            <el-image
              style="display: flex; flex-wrap: wrap; cursor: pointer;"
              :src="scope.row.url2"
              fit="cover"
              v-if="scope.row.url2"
              :z-index="2001"
              :preview-src-list="[scope.row.url2]"
              :preview-teleported="true"
              @click.stop
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="图片3" align="center" prop="url3" >
        <template #default="scope">
          <div class="qr-code-container">
            <el-image
              style="display: flex; flex-wrap: wrap; cursor: pointer;"
              :src="scope.row.url3"
              fit="cover"
              v-if="scope.row.url3"
              :z-index="2001"
              :preview-src-list="[scope.row.url3]"
              :preview-teleported="true"
              @click.stop
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="录入时间"
        align="center"
        prop="lrsj"
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
  <ViewVioSurveilScreenErrInfoForm ref="formAuditRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import { ViewVioSurveilScreenErrInfoApi, ViewVioSurveilScreenErrInfoVO } from '@/api/traffic/viosurveilscreenerrinfo'
import ViewVioSurveilScreenErrInfoForm from './ViewVioSurveilScreenErrInfoForm.vue'

/** 集指无效违法记录 列表 */
defineOptions({ name: 'ViewVioSurveilScreenErrInfo' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<ViewVioSurveilScreenErrInfoVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  sbbh: undefined,
  hphm: undefined,
  hpzl: undefined,
  wfsj: undefined,
  wfsj: [],
  wfxw: undefined,
  cwyy: undefined,
  url1: undefined,
  url2: undefined,
  url3: undefined,
  lrsj: undefined,
  reviewResult: undefined,
  lrsj: [],
  reviewDate: [],
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ViewVioSurveilScreenErrInfoApi.getViewVioSurveilScreenErrInfoPage(queryParams)
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

// /** 添加/修改操作 */
// const formRef = ref()
// const openForm = (type: string, id?: number) => {
//   formRef.value.open(type, id)
// }

const formAuditRef = ref()
const openAuditForm = (type: string, id?: number) => {
  formAuditRef.value.open(type, id)
}

// /** 导出按钮操作 */
// const handleExport = async () => {
//   try {
//     // 导出的二次确认
//     await message.exportConfirm()
//     // 发起导出
//     exportLoading.value = true
//     const data = await ViewVioSurveilScreenErrInfoApi.exportViewVioSurveilScreenErrInfo(queryParams)
//     download.excel(data, '集指无效违法记录.xls')
//   } catch {
//   } finally {
//     exportLoading.value = false
//   }
// }

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
