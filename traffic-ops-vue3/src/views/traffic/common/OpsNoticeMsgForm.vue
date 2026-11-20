<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="80%">
    <ContentWrap>
      <el-tabs
        v-model="activeName"
        type="card"
        class="demo-tabs"
        @tab-click="handleClick"
      >
        <el-tab-pane label="消息通知记录" name="noticeList" >
          <ContentWrap>
            <!-- 搜索工作栏 -->
            <el-form
              class="-mb-15px"
              :model="queryParams"
              ref="queryFormRef"
              :inline="true"
              label-width="96px"
            >
              <el-form-item label="接收者姓名" prop="receiveUserName">
                <el-input
                  v-model="queryParams.receiveUserName"
                  placeholder="请输入接收者姓名"
                  clearable
                  @keyup.enter="handleQuery"
                  class="!w-240px"
                />
              </el-form-item>
              <el-form-item label="接收者电话" prop="receiveUserPhone">
                <el-input
                  v-model="queryParams.receiveUserPhone"
                  placeholder="请输入接收者电话"
                  clearable
                  @keyup.enter="handleQuery"
                  class="!w-240px"
                />
              </el-form-item>
              <el-form-item label="发送时间" prop="sendTime">
                <el-date-picker
                  v-model="queryParams.sendTime"
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
          <ContentWrap>
            <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
              <!-- <el-table-column label="消息类型" align="center" prop="noticeType" /> -->
              <el-table-column label="接收者姓名" align="center" prop="receiveUserName" min-width="28"/>
              <el-table-column label="接收者电话" align="center" prop="receiveUserPhone" min-width="28"/>
              <el-table-column label="通知消息" align="center" prop="noticeMsg" />
              <el-table-column
                label="发送时间"
                align="center"
                prop="sendTime"
                :formatter="dateFormatter"
                min-width="48"
              />
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
        
        <el-tab-pane label="发送通知" name="sendNotice">
          <div class="code-card-container">
            <el-card class="box-card">
              <el-form
                ref="formRef"
                :model="formData"
                label-width="100px"
                v-loading="formLoading"
              >
                <el-form-item label="接收人" prop="userId">
                  <el-select v-model="formData.userId" filterable clearable placeholder="请选择">
                    <el-option
                     @click="chooseUser(item)"
                      v-for="item in userList"
                      :key="item.id"
                      :label="item.nickname"
                      :value="item.id"/>
                  </el-select>
                </el-form-item>
                <el-form-item label="通知内容" prop="noticeMsg">
                  <el-input
                    type="textarea"
                    v-model="formData.noticeMsg"
                    resize="vertical"
                    maxlength="60"
                    :autosize="{ minRows: 4, maxRows: 6 }"
                  />
                </el-form-item>
              </el-form>
              <template #footer>
                <el-button @click="submitForm" type="primary" :disabled="formLoading">发 送</el-button>
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
import { OpsMaintenanceApi } from '@/api/traffic/optmaintenance'
import { OpsNoticeRecordApi, OpsNoticeRecordVO, OpsNoticeMsgVO } from '@/api/public/opsnoticerecord'

/** 道路信息 表单 */
defineOptions({ name: 'OpsNoticeMsgForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗


const loading = ref(true) // 列表的加载中
const activeName = ref('noticeList')
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false)
const defaultBusinessId = ref('')
const defaultUserId  = ref('')
const userList = ref([])
const formData = ref({
  businessId: undefined,
  userId: undefined,
  receivePhone: undefined,
  noticeMsg: undefined,
  msgType: '1'
})

const list = ref<OpsNoticeRecordVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  businessType: undefined,
  businessId: undefined,
  noticeType: undefined,
  receiveUserType: undefined,
  receiveUserId: undefined,
  receiveUserName: undefined,
  receiveUserPhone: undefined,
  noticeMsg: undefined,
  sendTime: [],
})
const queryFormRef = ref() // 搜索的表单

/** 打开弹窗 */
const open = async (businessId: string, userId?: number) => {
  dialogVisible.value = true
  dialogTitle.value = "消息通知"

  activeName.value = 'noticeList'

  resetForm()
  getUserList()
  
  queryParams.pageNo = 1
  queryParams.pageSize = 10
  queryParams.businessId = businessId
  queryParams.receiveUserName = undefined
  queryParams.receiveUserPhone = undefined
  queryParams.sendTime = []

  defaultBusinessId.value = businessId
  defaultUserId.value = userId

  getList()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsNoticeRecordApi.getOpsNoticeRecordPage(queryParams)
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

const handleClick = (tab, event) => {
  if (tab.paneName == "noticeList") {
    getList()
  } else {
    resetForm()
    formData.value.businessId = defaultBusinessId.value
    formData.value.userId = Number(defaultUserId.value)
  }
}

/** 提交表单 */
const formRef = ref() // 表单 Ref
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if(!formData.value.userId) return message.error('请选择接收人')
  if(!formData.value.noticeMsg) return message.error('请输入通知内容')
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as OpsNoticeMsgVO
    await OpsNoticeRecordApi.sendNoticeMsg(data)
    message.success(t('common.createSuccess'))
  } finally {
    formLoading.value = false
  }
}
const getUserList = async () => {
  const userListData = await OpsMaintenanceApi.queryAllUserInfoList('')
  userList.value = userListData
}

const chooseUser = (item) => {
  formData.value.userId = item.id
  formData.value.receivePhone = item.mobile
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    businessId:undefined,
    userId: undefined,
    receivePhone: undefined,
    noticeMsg: undefined,
    msgType: '1',
  }
  formRef.value?.resetFields()
}
</script>

<style scoped>
.box-card{
      flex-direction: column;
    align-items: start;
}
</style>