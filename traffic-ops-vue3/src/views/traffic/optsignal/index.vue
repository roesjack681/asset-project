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
      <!-- <el-form-item label="设备名称" prop="equName">
        <el-input
          v-model="queryParams.equName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="equType">
        <el-select
          v-model="queryParams.equType"
          placeholder="请选择设备类型"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
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
      <el-form-item label="GPS定位-经度" prop="lng">
        <el-input
          v-model="queryParams.lng"
          placeholder="请输入GPS定位-经度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="GPS定位-纬度" prop="lat">
        <el-input
          v-model="queryParams.lat"
          placeholder="请输入GPS定位-纬度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
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
      <!-- <el-form-item label="联网方式" prop="networkType">
        <el-select
          v-model="queryParams.networkType"
          placeholder="请选择联网方式"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="联网位置" prop="networkAddr">
        <el-input
          v-model="queryParams.networkAddr"
          placeholder="请输入联网位置"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="信号控制相位" prop="phase">
        <el-input
          v-model="queryParams.phase"
          placeholder="请输入信号控制相位"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="联网状态" prop="networkStatus">
        <el-select
          v-model="queryParams.networkStatus"
          placeholder="请选择联网状态"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item> -->
      <el-form-item label="信号机厂家" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入信号机厂家"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="信号机型号" prop="equModel">
        <el-input
          v-model="queryParams.equModel"
          placeholder="请输入信号机型号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="信号机编号" prop="equNumber">
        <el-input
          v-model="queryParams.equNumber"
          placeholder="请输入信号机编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="IP地址" prop="ipAddr">
        <el-input
          v-model="queryParams.ipAddr"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="子网掩码" prop="mask">
        <el-input
          v-model="queryParams.mask"
          placeholder="请输入子网掩码"
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
      <el-form-item label="所属环路" prop="belongLoop">
        <el-input
          v-model="queryParams.belongLoop"
          placeholder="请输入所属环路"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="联网协议" prop="networkProtocol">
        <el-input
          v-model="queryParams.networkProtocol"
          placeholder="请输入联网协议"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="服务器IP地址" prop="serverIp">
        <el-input
          v-model="queryParams.serverIp"
          placeholder="请输入服务器IP地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="路口全景图" prop="panorama">
        <el-input
          v-model="queryParams.panorama"
          placeholder="请输入路口全景图"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备箱门禁" prop="accessControl">
        <el-input
          v-model="queryParams.accessControl"
          placeholder="请输入设备箱门禁"
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
          v-hasPermi="['traffic:ops-signal:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-signal:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :expand-row-keys="expandRowKeys" row-key="id" @expand-change="handleExpandChange" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column type="expand">
        <template #default="props">
          <div class="extendtable" :class="collapse? 'smallwith':'bigwidth'">
            <div class="left-table" style="width: 300px;margin-right: 10px">
              <el-table ref="singleTable" :data="tableData" row-key="key_id" highlight-current-row @current-change="handleCurrentChange" style="width: 100%" border :key="props.row.id">
                <el-table-column prop="date" label="星期"/>
                <el-table-column prop="name" label="使用的日方案号"/>
              </el-table>
            </div>
            <div style="flex: 1">
              <el-table :data="tableData2" row-key="id" :expand-row-keys="expandRowKeys2" @expand-change="handleExpandChange2" border style="width: 100%" :key="'uu' + props.row.id">
                <el-table-column type="expand">
                  <template #default="props">
                    <div style="padding: 5px 10px">
                      <el-table :data="tableData3" border stripe style="width: 100%" :key="'uuu' + props.row.id">
                        <el-table-column  type="index" width="80" label="阶段号"/>
                        <el-table-column prop="greeTime" label="绿灯时间"/>
                        <el-table-column prop="minGreeTime" label="最短绿灯"/>
                        <el-table-column prop="maxGreeTime" label="最长绿灯"/>
                        <el-table-column prop="yellowTime" label="黄灯时间"/>
                        <el-table-column prop="allRedTime" label="全红时间"/>
                        <el-table-column prop="greenFlash" label="绿闪时间"/>
                        <el-table-column prop="pedRed" label="行人红灯"/>
                        <el-table-column prop="pedFlash" label="行闪时间"/>
                      </el-table>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column type="index" width="60" label="序号"/>
                <el-table-column prop="planHour" label="小时"/>
                <el-table-column prop="planMinute" label="分钟"/>
                <el-table-column prop="planId" label="配时方案"/>
                <el-table-column prop="actuId" label="感应方案号"/>
                <el-table-column prop="stageListId" label="相序方案号"/>
                <el-table-column prop="phaseDifference" label="相位差"/>
                <el-table-column prop="cycleTime" label="周期"/>
              </el-table>
            </div>
          </div>
        </template>
      </el-table-column>
<!--      <el-table-column label="资产类型" align="center" prop="equType" />-->
      <el-table-column label="设备名称" align="center" prop="equName" />
      <el-table-column label="路口名称" align="center" prop="intersectionName" min-width="220px"/>
<!--      <el-table-column label="GPS定位-经度" align="center" prop="lng" />-->
<!--      <el-table-column label="GPS定位-纬度" align="center" prop="lat" />-->
      <el-table-column label="安装位置" align="center" prop="adress" />
<!--      <el-table-column label="安装时间" align="center" prop="installDate" :formatter="dateFormatter" width="180px"/>-->
      <el-table-column label="信号控制相位" align="center" prop="phase" width="120px"/>
<!--      <el-table-column label="联网方式" align="center" prop="networkType" />-->
<!--      <el-table-column label="联网位置" align="center" prop="networkAddr" />-->
      <el-table-column label="联网状态" align="center" prop="networkStatus" />
      <el-table-column label="信号机厂家" align="center" prop="manufacturer" />
      <el-table-column label="信号机型号" align="center" prop="equModel" />
<!--      <el-table-column label="信号机编号" align="center" prop="equNumber" />-->
<!--      <el-table-column label="IP地址" align="center" prop="ipAddr" />-->
<!--      <el-table-column label="子网掩码" align="center" prop="mask" />-->
<!--      <el-table-column label="网关" align="center" prop="gateway" />-->
<!--      <el-table-column label="所属环路" align="center" prop="belongLoop" />-->
<!--      <el-table-column label="联网协议" align="center" prop="networkProtocol" />-->
      <!-- <el-table-column label="服务器IP地址" align="center" prop="serverIp" />
      <el-table-column label="路口全景图" align="center" prop="panorama" />
      <el-table-column label="设备箱门禁" align="center" prop="accessControl" /> -->
      <!-- <el-table-column label="归属单位" align="center" prop="unit" /> -->
      <el-table-column label="管辖单位" align="center" prop="dominion" />
<!--      <el-table-column label="创建时间" align="center" prop="createTime" :formatter="dateFormatter" width="180px"/>-->
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-signal:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-signal:delete']"
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
  <OpsSignalForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsSignalApi, OpsSignalVO } from '@/api/traffic/optsignal'
import OpsSignalForm from './OpsSignalForm.vue'
import {useAppStore} from "@/store/modules/app";
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
const appStore = useAppStore()
// 菜单折叠
const collapse = computed(() => appStore.getCollapse)
console.log(collapse.value)
/** 信号灯 列表 */
defineOptions({ name: 'TrafficOpsSignal' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsSignalVO[]>([]) // 列表的数据
const expandRowKeys = ref([]) // 展开的列
const expandRowKeys2 = ref([]) // 展开的列
const currentRow = ref({}) // 展开的列
const singleTable = ref() // 展开的列
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equName: undefined,
  equType: undefined,
  roadName: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  adress: undefined,
  installDate: [],
  networkType: undefined,
  networkAddr: undefined,
  phase: undefined,
  networkStatus: undefined,
  manufacturer: undefined,
  equModel: undefined,
  equNumber: undefined,
  ipAddr: undefined,
  mask: undefined,
  gateway: undefined,
  belongLoop: undefined,
  networkProtocol: undefined,
  serverIp: undefined,
  panorama: undefined,
  accessControl: undefined,
  unit: undefined,
  dominion: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const objformat = ref({
  0: '一',
  1: '二',
  2: '三',
  3: '四',
  4: '五',
  5: '六',
  6: '日',
})
const tableData = ref([])
const tableData2 = ref([])
const tableData3 = ref([])

const handleExpandChange = async (newExpandKeys, expandRows) => {
  console.log(newExpandKeys, expandRows)
  expandRowKeys.value = []
  expandRowKeys.value = expandRows.length? [newExpandKeys.id]: []
  loading.value = true
  const data = await OpsSignalApi.queryJunctionPlanInfo(newExpandKeys.id)
  loading.value = false
  tableData.value = []
  tableData2.value = []
  tableData3.value = []
  const weekPlans = JSON.parse(data.weekPlans)
  weekPlans.forEach((e, index) => {
    const obj = {
      date: objformat.value[index],
      key_id: index+1,
      name: e,
      ...data
    }
    tableData.value.push(obj)
    if(index + 1 == data.week) {
      setCurrent(obj)
      if(expandRowKeys.value[0]) {
        queryJunctionDayPlanList(obj.id, obj.name)
      }
    }
  })
}
const handleExpandChange2 = async (newExpandKeys, expandRows) => {
  console.log(newExpandKeys, expandRows)
  expandRowKeys2.value =expandRows.length? [newExpandKeys.id]:[];
  if(expandRowKeys2.value[0]) {
    queryJunctionTimeStageList(newExpandKeys.junctionId, newExpandKeys.planId)
  }
}
const queryJunctionDayPlanList = async (junctionId, dayPlanId) => {
  loading.value = true
  tableData2.value = await OpsSignalApi.queryJunctionDayPlanList(junctionId, dayPlanId)
  loading.value = false
}
const queryJunctionTimeStageList = async (junctionId, planId) => {
  loading.value = true
  tableData3.value = await OpsSignalApi.queryJunctionTimeStageList(junctionId, planId)
  loading.value = false
}
const  handleCurrentChange = (val) => {
  currentRow.value = val
  expandRowKeys2.value = []
  queryJunctionDayPlanList(val.id, val.name)
}
const setCurrent = (row) => {
  singleTable.value.setCurrentRow(row)
}
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsSignalApi.getOpsSignalPage(queryParams)
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
    await OpsSignalApi.deleteOpsSignal(id)
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
    const data = await OpsSignalApi.exportOpsSignal(queryParams)
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
<style lang="scss" scoped>
::v-deep(.left-table .el-table__body tr.current-row>td.el-table__cell) {
  background: #1b5ba4;
  color: #ffffff;
}
::v-deep(.left-table .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell) {
  background-color: #ecf5ff;
  color: #333333;
}
</style>
