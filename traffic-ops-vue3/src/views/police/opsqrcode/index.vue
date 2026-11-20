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

      <!-- <el-form-item label="资产类型" prop="opsType">
        <el-select
          v-model="queryParams.opsType"
          placeholder="请选择资产类型"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['police:ops-qrcode:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['police:ops-qrcode:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="二维码记录" name="qrcodeTab" >
        <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
          <el-table-column label="资产类型" align="center" prop="opsType" />
          <el-table-column label="设备二维码" align="center">
            <template #default="scope">
              <div class="qr-code-container">
                <el-image
                  style="cursor: pointer; width: 30px; height: 30px"
                  :src="scope.row.qrcodeFile"
                  fit="cover"
                  v-if="scope.row.qrcodeFile"
                />
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            :formatter="dateFormatter"
            width="180px"
          />
          <el-table-column label="操作" align="center" min-width="120px">
            <template #default="scope">
              <el-button
                link
                type="primary"
                @click="openPrintForm('single', scope.row.id)"
              >
                打印
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
      </el-tab-pane>
      <el-tab-pane label="生成记录" name="genRecordTab" >
        <el-table v-loading="loading" :data="genRecordList" :stripe="true" :show-overflow-tooltip="true">
          <el-table-column
            label="生成日期"
            align="center"
            prop="genDate"
            :formatter="dateFormatter"
            width="180px"
          />
          <el-table-column label="资产类型" align="center" prop="opsType" />
          <el-table-column label="二维码参数" align="center" prop="directionCnt" />
          <el-table-column label="操作" align="center" min-width="120px">
            <template #default="scope">
              <el-button
                link
                type="primary"
                @click="openPrintForm('batch', scope.row.id)"
              >
                批量打印
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <Pagination
          :total="genRecordTotal"
          v-model:page="queryGenRecordParams.pageNo"
          v-model:limit="queryGenRecordParams.pageSize"
          @pagination="getGenRecordList"
        />
      </el-tab-pane>
      <el-tab-pane label="生成二维码" name="genQrcode">
        <div class="code-card-container">
            <el-card class="box-card">
              <el-form
                ref="genCodeFormRef"
                v-model="genFormData"
                :rules="genFormRules"
                label-width="100px"
                v-loading="formLoading"
              >
                <el-form-item label="区域信息" prop="geoCode">
                  <el-select v-model="genFormData.geoCode" placeholder="请选择区域信息" style="width: 240px;">
                    <el-option
                      v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="生成数量" prop="genCnt">
                  <el-input-number v-model="genFormData.genCnt"  :min="1"  class="mx-4" />
                </el-form-item>
              </el-form>
              <template #footer>
                <el-button @click="submitForm" type="primary" :disabled="formLoading">生 成</el-button>
              </template>
            </el-card>
          </div>
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>

  <!-- 表单弹窗：打印 -->
  <OpsQrCodePrint ref="formPrintRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import download from '@/utils/download'
import { OpsQrcodeApi, OpsQrcodeVO } from '@/api/traffic/opsqrcode'
import { OpsQrcodeGenRecordApi, PoliceOpsQrcodeGenRecordVO } from '@/api/traffic/opsqrcodegenrecord'
import OpsQrCodePrint from './OpsQrCodePrint.vue'

/** 资产二维码信息 列表 */
defineOptions({ name: 'PoliceOpsQrcode' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const activeName = ref('qrcodeTab')
const list = ref<OpsQrcodeVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const genRecordList = ref<PoliceOpsQrcodeGenRecordVO[]>([]) // 列表的数据
const genRecordTotal = ref(0) // 列表的总页数
const formLoading = ref(false)

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  affiliatedUnit: 2,
  roadId: undefined,
  raodName: undefined,
  direction: undefined,
  genRecordId: undefined,
  opsType: undefined,
  serialNum: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const queryGenRecordParams = reactive({
  pageNo: 1,
  pageSize: 10,
  affiliatedUnit: 2,
  genDate: [],
  roadId: undefined,
  roadName: undefined,
  opsType: undefined,
  directionCnt: undefined,
  createTime: [],
  deletedTime: []
})
const queryGenRecordFormRef = ref() // 搜索的表单

const genCodeFormRef = ref() // 表单 Ref
const genFormData = ref({
  id: undefined,
  affiliatedUnit: 2,
  roadId: undefined,
  roadName: undefined,
  opsType: "杆件",
  genCnt: 1,
  geoCode: undefined
})
const genFormRules = reactive({
})

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsQrcodeApi.getOpsQrcodePage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const formPrintRef = ref()
const openPrintForm = (type: string, id?: number) => {
  formPrintRef.value.open(type, id)
}

/** 查询列表 */
const getGenRecordList = async () => {
  loading.value = true
  try {
    const data = await OpsQrcodeGenRecordApi.getOpsQrcodeGenRecordPage(queryGenRecordParams)
    genRecordList.value = data.list
    genRecordTotal.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 搜索按钮操作 */
const handleGenRecordQuery = () => {
  queryGenRecordParams.pageNo = 1
  getGenRecordList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 重置按钮操作 */
const resetGenRecordQuery = () => {
  queryGenRecordFormRef.value.resetFields()
  handleGenRecordQuery()
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
    await OpsQrcodeApi.deleteOpsQrcode(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

const handleClick = (tab, event) => {
  if (tab.paneName == "qrcodeTab") {
    getList()
  } else if (tab.paneName == "genRecordTab") {
    getGenRecordList()
  } else {
    resetForm()
  }
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await OpsQrcodeApi.exportOpsQrcode(queryParams)
    download.excel(data, '资产二维码信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await genCodeFormRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = genFormData.value as unknown as PoliceOpsQrcodeGenRecordVO
    await OpsQrcodeGenRecordApi.createPoliceOpsQrcodeGenRecord(data)
    message.success(t('common.createSuccess'))
    resetForm()
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  genFormData.value = {
    id: undefined,
    affiliatedUnit: 2,
    roadId: undefined,
    roadName: undefined,
    opsType: "杆件",
    genCnt: 1,
    geoCode: undefined
  }
  genCodeFormRef.value?.resetFields()
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
