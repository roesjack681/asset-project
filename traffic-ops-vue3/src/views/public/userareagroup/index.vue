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
      <el-form-item label="组名" prop="groupName">
        <el-input
          v-model="queryParams.groupName"
          placeholder="请输入组名"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="队长" prop="leadUserId">
        <el-select
          v-model="queryParams.leadUserId"
          placeholder="请选择队长"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.nickname"
            :value="item.id + ''"/>
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
          v-hasPermi="['public:user-area-group:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['public:user-area-group:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="false" :show-overflow-tooltip="false">
      <el-table-column label="所属单位" align="center" prop="affiliatedUnit" width="140">
        <template #default="scope">
          <el-tag v-if="scope.row.affiliatedUnit.indexOf(1) != -1">交警</el-tag>
          <el-tag style="margin-left: 6px;" v-if="scope.row.affiliatedUnit.indexOf(2) != -1">公安</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="组名" align="center" prop="groupName" />
      <el-table-column label="主要运维内容" align="center" prop="groupContent">
        <template #default="scope">
          <span v-html="scope.row.groupContent"></span>
        </template>
      </el-table-column>
      <el-table-column label="可维护区域" align="center" prop="groupArea">
        <template #default="scope">
          {{scope.row.affiliatedUnit == 2?scope.row.groupArea: getAreaNameById(scope.row.groupArea)}}
        </template>
      </el-table-column>
      <el-table-column label="组员" align="center" prop="groupUserId">
        <template #default="scope">
          {{getUserNameById(scope.row.groupUserId)}}
        </template>
      </el-table-column>
      <el-table-column label="队长" align="center" prop="leadUserId">
        <template #default="scope">
          {{getUserNameById(scope.row.leadUserId + '')}}
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
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['public:user-area-group:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['public:user-area-group:delete']"
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
  <UserAreaGroupForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { UserAreaGroupApi, UserAreaGroupVO } from '@/api/public/userareagroup'
import UserAreaGroupForm from './UserAreaGroupForm.vue'
import {OpsMaintenanceApi} from "@/api/traffic/optmaintenance";
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 区域用户组信息 列表 */
defineOptions({ name: 'PublicUserAreaGroup' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<UserAreaGroupVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  affiliatedUnit: undefined,
  groupName: undefined,
  groupContent: undefined,
  groupArea: undefined,
  groupUserId: undefined,
  leadUserId: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const userList = ref([])
const areaList = ref(getStrDictOptions(DICT_TYPE.GEO_INFO))
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await UserAreaGroupApi.getUserAreaGroupPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
const getUserList = async () => {
  userList.value = await OpsMaintenanceApi.queryAllUserInfoList()
}
const getUserNameById = (ids) => {
  if (!ids) {
    return ''
  }
  var idList = ids.split(';')
  var idNames = []
  idList.forEach(id => {
    userList.value.forEach(item => {
      if (item.id == id) {
        idNames.push(item.nickname)
      }
    })
  })
  if(idNames.length === 0) return ''
  if(idNames.length === 1) return idNames[0]
  return idNames.join(';')
}

const getAreaNameById = (ids) => {
  if (!ids) {
    return ''
  }
  var idList = ids.split(';')
  var idNames = []
  idList.forEach(id => {
    areaList.value.forEach(item => {
      if (item.value == id) {
        idNames.push(item.label)
      }
    })
  })
  if(idNames.length === 0) return ''
  if(idNames.length === 1) return idNames[0]
  return idNames.join(';')
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
    await UserAreaGroupApi.deleteUserAreaGroup(id)
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
    const data = await UserAreaGroupApi.exportUserAreaGroup(queryParams)
    download.excel(data, '区域用户组信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(async () => {
  await getUserList()
  await getList()
})
</script>
