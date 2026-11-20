<template>
  <Dialog :title="dialogTitle" width="80%" v-model="dialogVisible1">
    <div class="form-box-wrapper">
      <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>查询条件&nbsp;&nbsp;<span style="color:red; font-size: 12px;">请至少输入一项查询条件</span></div>
      <!-- 搜索工作栏 -->
      <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="88px"
      >
        <el-form-item label="存在问题" prop="problem">
          <el-input
            v-model="queryParams.problem"
            placeholder="请输入存在问题"
            @keyup.enter="handleQuery"
            clearable
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item label="维护人员" prop="maintenancePersonnel">
          <el-input
            v-model="queryParams.maintenancePersonnel"
            placeholder="请输入维护人员"
            @keyup.enter="handleQuery"
            clearable
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item label="状态" prop="equType">
          <el-select
            v-model="queryParams.status"
            class="!w-240px"
          >
            <el-option label="待派单" value="1"/>
            <el-option label="待接单" value="6"/>
            <el-option label="维修中" value="4"/>
          </el-select>
        </el-form-item>
        <el-form-item label="所属区域" prop="equAddr">
          <el-input
            v-model="queryParams.equAddr"
            placeholder="请输入所属区域"
            @keyup.enter="handleQuery"
            clearable
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
      >
    <ContentWrap>
      <el-collapse accordion>
        <el-collapse-item>
          <template #title>
            符合条件的维修共计：<span style="color:red;font-size: 18px;">{{ total }}</span>&nbsp;条
          </template>
          <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
            <el-table-column width="640" label="存在问题" align="center" prop="problem" />
            <el-table-column label="故障分类" align="center" prop="faultType" />
            <el-table-column label="维护人员"  width="150px" align="center" prop="maintenancePersonnel" />
            <el-table-column label="状态" align="center" prop="status" width="90">
              <template #default="scope">
                <el-tag v-if="scope.row.status == 1">待派单</el-tag>
                <el-tag v-if="scope.row.status == 4" type="danger">维修中</el-tag>
                <el-tag v-if="scope.row.status == 5" type="success">已完成</el-tag>
                <el-tag v-if="scope.row.status == 6" type="info">待接单</el-tag>
                <el-tag v-if="scope.row.status == 7" type="warning">取消</el-tag>
                <el-tag v-if="scope.row.status == 8" type="warning">待确认</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="180px">
              <template #default="scope">
                <el-button
                  link
                  type="danger"
                  @click="deleteRepairOrder(scope.$index)"
                  v-hasPermi="['traffic:ops-maintenance:delete']"
                >
                  移除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-collapse-item>
      </el-collapse>
    </ContentWrap>
    <div class="form-box-wrapper">
      <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>选择维修人员</div>
      <el-row :gutter="20" style="width: 100%">
        <el-col :span="8">
          <el-form-item label="维修人" prop="reportUserId">
            <el-select filterable clearable v-model="formData.maintenanceUserId" placeholder="请选择">
              <el-option
                @click="chooseUser(item)"
                v-for="item in userList"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"/>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary">确认派单</el-button>
      <el-button @click="dialogVisible1 = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsMaintenanceApi, OpsMaintenanceBatchDispatchVO } from '@/api/traffic/optmaintenance'
import moment from 'moment'
import { useUserStore } from '@/store/modules/user'
/** 设备维保信息 表单 */
defineOptions({ name: 'OpsMaintenanceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible1 = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const audioFile = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const userList = ref([])
const deptId = useUserStore().getUser.deptId

const loading = ref(false) // 列表的加载中
const list = ref<OpsMaintenanceVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  isPagination: 0,
  affiliatedUnit: 2,
  equType: undefined,
  problem: undefined,
  status: '1',
  reportPersonnel: undefined,
  maintenancePersonnel: undefined,
  equAddr: undefined
})

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsMaintenanceApi.getOpsMaintenancePage(queryParams)
    list.value = data.list
    total.value = data.total
    if (total.value == 0) {
      message.warning('未查询到符合条件的维修记录！')
    }
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  list.value = []
  total.value = 0
  queryParams.pageNo = 1
  if (!queryParams.problem && !queryParams.maintenancePersonnel && !queryParams.equAddr) {
    message.error('请输入查询条件！')
    return
  }
  getList()
}

/** 重置按钮操作 */
const queryFormRef = ref() // 搜索的表单
const resetQuery = () => {
  list.value = []
  total.value = 0
  queryFormRef.value.resetFields()
}

const deleteRepairOrder = (index) => {
  total.value -= 1
  list.value.splice(index, 1)
}

const formData = ref({
  maintenanceUserId: undefined,
  maintenancePersonnel: undefined,
  maintenanceIdList: []
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible1.value = true
  dialogTitle.value = '批量派单'
  formType.value = type

  list.value = []
  total.value = 0

  queryParams.problem = undefined
  queryParams.maintenancePersonnel = undefined
  queryParams.status = '1'
  queryParams.pageNo = 1

  resetForm()
  getUserList()
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsMaintenanceApi.getOpsMaintenance(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 提交请求
  formLoading.value = true
  try {
    formData.value.maintenanceIdList = list.value.map(item => {
      return item.id
    });
    const data = formData.value as unknown as OpsMaintenanceBatchDispatchVO
    await OpsMaintenanceApi.batchDispatchRepairOrder(data)
    message.success(t('common.createSuccess'))
    dialogVisible1.value = false
    // 发送操作成功的事件

    emit('success')
  } finally {
    formLoading.value = false
  }
}
const getUserList = async () => {
  const userListData = await OpsMaintenanceApi.getUserList(deptId)
  userList.value = userListData.list
}
const chooseUser = (item) => {
  formData.value.maintenanceUserId = item.id
  formData.value.maintenancePersonnel = item.nickname
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    maintenanceUserId: undefined,
    maintenancePersonnel: undefined,
    maintenanceIdList: []
  }
  formRef.value?.resetFields()
}
</script>
