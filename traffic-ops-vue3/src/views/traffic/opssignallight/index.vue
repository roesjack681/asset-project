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
      <el-form-item label="所在道路名称" prop="roadName">
        <el-input
          v-model="queryParams.roadName"
          placeholder="请输入所在道路名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="灯控路口名称" prop="intersectionName">
        <el-input
          v-model="queryParams.intersectionName"
          placeholder="请输入灯控路口名称"
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
      <el-form-item label="型号" prop="equModel">
        <el-input
          v-model="queryParams.equModel"
          placeholder="请输入型号"
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
      <!-- <el-form-item label="信号灯图案" prop="ledPattern">
        <el-input
          v-model="queryParams.ledPattern"
          placeholder="请输入信号灯图案"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="灯具类型" prop="ledType">
        <el-select
          v-model="queryParams.ledType"
          placeholder="请选择灯具类型"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="尺寸数据" prop="ledSize">
        <el-input
          v-model="queryParams.ledSize"
          placeholder="请输入尺寸数据"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="灯头数量" prop="ledHeadNum">
        <el-input
          v-model="queryParams.ledHeadNum"
          placeholder="请输入灯头数量"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="排列方式" prop="arrangement">
        <el-input
          v-model="queryParams.arrangement"
          placeholder="请输入排列方式"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="信号显示方式" prop="signalDisplayMode">
        <el-input
          v-model="queryParams.signalDisplayMode"
          placeholder="请输入信号显示方式"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
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
      <el-form-item label="删除时间" prop="deletedTime">
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
          v-hasPermi="['traffic:ops-signal-light:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-signal-light:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
<!--      <el-table-column label="资产类型" align="center" prop="equType" />-->
      <el-table-column label="路口名称" width="200" align="center" prop="intersectionName" />
      <el-table-column label="所在道路名称" align="center" prop="roadName" />
      <el-table-column label="设备名称" width="300" align="center" prop="equName" />
      <el-table-column label="排列方式" align="center" prop="arrangement" />
      <el-table-column label="灯具类型" align="center" prop="ledType" />
      <el-table-column label="尺寸数据" align="center" prop="ledSize" />
      <!-- <el-table-column label="GPS定位-经度" align="center" prop="lng" />
      <el-table-column label="GPS定位-纬度" align="center" prop="lat" /> -->
<!--      <el-table-column label="安装位置" align="center" prop="adress" />-->
<!--      <el-table-column label="安装时间" align="center" prop="installDate" :formatter="dateFormatter" width="180px"/>-->
<!--      <el-table-column label="厂家" align="center" prop="manufacturer" />-->
<!--      <el-table-column label="型号" align="center" prop="equModel" />-->
<!--      <el-table-column label="管辖单位" align="center" prop="dominion" />-->
<!--      <el-table-column label="信号灯图案" align="center" prop="ledPattern" />-->
<!--      <el-table-column label="灯头数量" align="center" prop="ledHeadNum" />-->
<!--      <el-table-column label="信号显示方式" align="center" prop="signalDisplayMode" />-->
<!--      <el-table-column label="创建时间" align="center" prop="createTime" :formatter="dateFormatter" width="180px"/>-->
      <!-- <el-table-column
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
            v-hasPermi="['traffic:ops-signal-light:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-signal-light:delete']"
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
  <OpsSignalLightForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsSignalLightApi, OpsSignalLightVO } from '@/api/traffic/opssignallight'
import OpsSignalLightForm from './OpsSignalLightForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 信号灯 列表 */
defineOptions({ name: 'TrafficOpsSignalLight' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsSignalLightVO[]>([]) // 列表的数据
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
  adress: undefined,
  installDate: [],
  manufacturer: undefined,
  equModel: undefined,
  unit: undefined,
  ledPattern: undefined,
  ledType: undefined,
  ledSize: undefined,
  ledHeadNum: undefined,
  arrangement: undefined,
  signalDisplayMode: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsSignalLightApi.getOpsSignalLightPage(queryParams)
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
    await OpsSignalLightApi.deleteOpsSignalLight(id)
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
    const data = await OpsSignalLightApi.exportOpsSignalLight(queryParams)
    download.excel(data, '信号灯.xls')
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
