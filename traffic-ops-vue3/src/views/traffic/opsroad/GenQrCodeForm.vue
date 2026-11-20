<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="65%">
    <ContentWrap>
      <el-tabs
        v-model="activeName"
        type="card"
        class="demo-tabs"
        @tab-click="handleClick"
      >
        <el-tab-pane label="二维码记录" name="qrcodeTab" >
          <ContentWrap>
            <el-form
              class="-mb-15px"
              :model="queryParams"
              ref="queryFormRef"
              :inline="true"
              label-width="88px"
            >
              <el-form-item label="方向" prop="direction">
                <el-input
                  v-model="queryParams.direction"
                  placeholder="请输入方向"
                  clearable
                  @keyup.enter="handleQuery"
                  class="!w-240px"
                />
              </el-form-item>
              <el-form-item label="序列号" prop="serialNum">
                <el-input
                  v-model="queryParams.serialNum"
                  placeholder="请输入序列号"
                  clearable
                  @keyup.enter="handleQuery"
                  class="!w-240px"
                />
              </el-form-item>
              <el-form-item>
                <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
                <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
              </el-form-item>
            </el-form>
          </ContentWrap>
          <ContentWrap>
            <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
              <el-table-column label="道路名称" align="center" prop="raodName" />
              <el-table-column label="方向" align="center" prop="direction" />
              <el-table-column label="资产类型" align="center" prop="opsType" />
              <el-table-column label="序列号" align="center" prop="serialNum" />
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
          </ContentWrap>
          <!-- 分页 -->
          <Pagination
            :total="total"
            v-model:page="queryParams.pageNo"
            v-model:limit="queryParams.pageSize"
            @pagination="getList"
          />
        </el-tab-pane>
        <el-tab-pane label="生成记录" name="genRecordTab">
          <ContentWrap>
            <el-form
              class="-mb-15px"
              :model="queryGenRecordParams"
              ref="queryGenRecordFormRef"
              :inline="true"
              label-width="88px"
            >
              <el-form-item label="生成日期" prop="genDate">
                <el-date-picker
                  v-model="queryGenRecordParams.genDate"
                  value-format="YYYY-MM-DD HH:mm:ss"
                  type="daterange"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
                  class="!w-220px"
                />
              </el-form-item>
              <el-form-item>
                <el-button @click="handleGenRecordQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
                <el-button @click="resetGenRecordQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
              </el-form-item>
            </el-form>
          </ContentWrap>

          <ContentWrap>
            <el-table v-loading="loading" :data="genRecordList" :stripe="true" :show-overflow-tooltip="true">
              <el-table-column
                label="生成日期"
                align="center"
                prop="genDate"
                :formatter="dateFormatter"
                width="180px"
              />
              <el-table-column label="道路名称" align="center" prop="roadName" />
              <el-table-column label="资产类型" align="center" prop="opsType" />
              <el-table-column label="方向和数量" align="center" prop="directionCnt" />
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
          </ContentWrap>
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
                <el-form-item label="道路名称" prop="roadName">
                  <el-text class="mx-1">{{ genFormData.roadName }}</el-text>
                </el-form-item>
                <el-form-item label="方向数量" prop="postDesc">
                  <div  style="width: 100%;">
                    <el-button type="success" @click="addDirectionCnt">+ 添加</el-button>
                    <el-table v-if="genFormData.directionCntList.length" :data="genFormData.directionCntList" style="width: 62%">
                      <el-table-column label="方向">
                        <template #default="scope">
                          <el-input v-model="scope.row.direction"/>
                        </template>
                      </el-table-column>
                      <el-table-column label="数量">
                        <template #default="scope">
                          <el-input-number v-model="scope.row.genCnt"  :min="0"  class="mx-4" />
                        </template>
                      </el-table-column>
                      <el-table-column label="操作" width="60">
                        <template #default="scope">
                          <el-button size="mini" type="text" @click="deleteDirectionCnt(scope.$index)">删除</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
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

    <template #footer>
      <el-button @click="dialogVisible = false">关 闭</el-button>
    </template>

    <!-- 表单弹窗：打印 -->
    <OpsQrCodePrint ref="formPrintRef" @success="getList" />

  </Dialog>
</template>
<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { OpsRoadApi, OpsRoadVO } from '@/api/traffic/opsroad'
import { OpsQrcodeApi, OpsQrcodeVO } from '@/api/traffic/opsqrcode'
import { OpsQrcodeGenRecordApi, OpsQrcodeGenRecordVO } from '@/api/traffic/opsqrcodegenrecord'
import OpsQrCodePrint from './OpsQrCodePrint.vue'

/** 道路信息 表单 */
defineOptions({ name: 'GenQrCodeForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗


const loading = ref(true) // 列表的加载中
const list = ref<OpsQrcodeVO[]>([]) // 列表的数据
const genRecordList = ref<OpsQrcodeGenRecordVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const genRecordTotal = ref(0) // 列表的总页数
const activeName = ref('qrcodeTab')
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false)

const genFormData = ref({
  id: undefined,
  roadId: undefined,
  roadName: undefined,
  opsType: "杆件",
  directionCntList: [{
    direction: undefined,
    genCnt: undefined
  }]
})

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
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
const genCodeFormRef = ref() // 表单 Ref
const genFormRules = reactive({
})

const queryGenRecordParams = reactive({
  pageNo: 1,
  pageSize: 10,
  genDate: [],
  roadId: undefined,
  roadName: undefined,
  opsType: undefined,
  directionCnt: undefined,
  createTime: [],
  deletedTime: []
})
const queryGenRecordFormRef = ref() // 搜索的表单


const exportLoading = ref(false) // 导出的加载中

/** 打开弹窗 */
const open = async (roadName: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = roadName + "-设备二维码"

  resetForm()

  genFormData.value.roadId = id
  genFormData.value.roadName = roadName
  queryParams.roadId = id
  queryGenRecordParams.roadId = id

  getList()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

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
const handleGenRecordQuery = () => {
  queryGenRecordParams.pageNo = 1
  getGenRecordList()
}

const formPrintRef = ref()
const openPrintForm = (type: string, id?: number) => {
  formPrintRef.value.open(type, id)
}

/** 重置按钮操作 */
const resetGenRecordQuery = () => {
  queryGenRecordFormRef.value.resetFields()
  handleGenRecordQuery()
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

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await genCodeFormRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = genFormData.value as unknown as OpsQrcodeGenRecordVO
    await OpsQrcodeGenRecordApi.createOpsQrcodeGenRecord(data)
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
    roadId: genFormData.value.roadId,
    roadName: genFormData.value.roadName,
    opsType: "杆件",
    directionCntList: [{
      direction: undefined,
      genCnt: undefined
    }]
  }
  genCodeFormRef.value?.resetFields()
}

const addDirectionCnt = () => {
  genFormData.value.directionCntList.push({
    direction: undefined,
    genCnt: undefined,
  })
}
const deleteDirectionCnt = (index) => {
  genFormData.value.directionCntList.splice(index,1)
}
</script>

<style scoped>
.box-card{
      flex-direction: column;
    align-items: start;
}
</style>
