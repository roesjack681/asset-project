<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="灯控路口名称" prop="intersectionName">
        <el-input
          v-model="queryParams.intersectionName"
          placeholder="请输入灯控路口名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="安装位置" prop="adress">
        <el-input
          v-model="queryParams.adress"
          placeholder="请输入安装位置"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input
          v-model="queryParams.ipAddr"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="屏幕朝向" prop="screenOrientation">
        <el-input
          v-model="queryParams.screenOrientation"
          placeholder="请输入屏幕朝向"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="子网" prop="subnet">
        <el-input
          v-model="queryParams.subnet"
          placeholder="请输入子网"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="网关" prop="gateway">
        <el-input
          v-model="queryParams.gateway"
          placeholder="请输入网关"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="球机IP" prop="ptzIp">
        <el-input
          v-model="queryParams.ptzIp"
          placeholder="请输入球机IP"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="安装时间" prop="installDate">
        <el-date-picker
          v-model="queryParams.installDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="厂家" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入厂家"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="归属单位" prop="unit">
        <el-input
          v-model="queryParams.unit"
          placeholder="请输入归属单位"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="管辖单位" prop="dominion">
        <el-select
          v-model="queryParams.dominion"
          placeholder="请选择管辖单位"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.DOMINION)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-bigled:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-bigled:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="设备编号" align="center" prop="equNumber" />
      <el-table-column label="设备名称" align="center" prop="equName" /> -->
<!--      <el-table-column label="设备类型" align="center" prop="equType"/>-->
<!--      <el-table-column label="资产类型" align="center" prop="equType">-->
<!--        <template #default="scope">-->
<!--          <dict-tag :type="DICT_TYPE.EQU_TYPE" :value="scope.row.equType" />-->
<!--        </template>-->
<!--      </el-table-column>-->
      <!-- <el-table-column label="所在道路名称" align="center" prop="roadName" /> -->
      <!-- <el-table-column label="灯控路口名称" align="center" prop="intersectionName" />
      <el-table-column label="GPS定位-经度" align="center" prop="lng" />
      <el-table-column label="GPS定位-纬度" align="center" prop="lat" /> -->
      <el-table-column label="安装位置" align="center" prop="adress" min-width="220px"/>
      <el-table-column label="屏幕朝向" align="center" prop="screenOrientation" />
      <el-table-column label="大小" align="center" prop="size" />
<!--      <el-table-column label="IP地址" align="center" prop="ipAddr" />-->
<!--      <el-table-column label="子网" align="center" prop="subnet" />-->
<!--      <el-table-column label="网关" align="center" prop="gateway" />-->
<!--      <el-table-column label="球机IP" align="center" prop="ptzIp" />-->
      <!-- <el-table-column label="用户名" align="center" prop="username" />
      <el-table-column label="密码" align="center" prop="passwd" /> -->
<!--      <el-table-column label="违章编码" align="center" prop="violationCode" />-->
<!--      <el-table-column label="诱导编码" align="center" prop="inducedEncoding" />-->
<!--      <el-table-column label="大屏对应编号" align="center" prop="dpbh" />-->
      <el-table-column label="管辖单位" prop="dominion" />
      <el-table-column label="描述" align="center" prop="des" min-width="150px"/>
      <el-table-column label="备注" align="center" prop="remark" />
<!--      <el-table-column label="安装时间" align="center" prop="installDate" :formatter="dateFormatter" width="180px"/>-->
<!--      <el-table-column label="厂家" prop="manufacturer" />-->
<!--      <el-table-column label="归属单位" prop="unit" />-->
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-bigled:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-bigled:delete']"
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
  <OpsBigledForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsBigledApi, OpsBigledVO } from '@/api/traffic/opsbigled'
import OpsBigledForm from './OpsBigledForm.vue'

/** 大屏 列表 */
defineOptions({ name: 'TrafficOpsBigled' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsBigledVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equNumber: undefined,
  equName: undefined,
  equType: undefined,
  roadName: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  des: undefined,
  adress: undefined,
  ipAddr: undefined,
  screenOrientation: undefined,
  subnet: undefined,
  gateway: undefined,
  ptzIp: undefined,
  username: undefined,
  passwd: undefined,
  violationCode: undefined,
  inducedEncoding: undefined,
  dpbh: undefined,
  remark: undefined,
  size: undefined,
  installDate: [],
  manufacturer: undefined,
  unit: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsBigledApi.getOpsBigledPage(queryParams)
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
    await OpsBigledApi.deleteOpsBigled(id)
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
    const data = await OpsBigledApi.exportOpsBigled(queryParams)
    download.excel(data, '大屏.xls')
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
