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
      <el-form-item label="电警名称" prop="equName">
        <el-input
          v-model="queryParams.equName"
          placeholder="请输入电警名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="电警类型" prop="djType">
        <el-select
          v-model="queryParams.djType"
          placeholder="请选择电警类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.POLICE_DJ_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="方向" prop="direction">
        <el-input
          v-model="queryParams.direction"
          placeholder="请输入方向"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="GPS定位-经度" prop="lng">
        <el-input
          v-model="queryParams.lng"
          placeholder="请输入GPS定位-经度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="路网编号" prop="lwBh">
        <el-input
          v-model="queryParams.lwBh"
          placeholder="请输入路网编号"
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
      </el-form-item>
      <el-form-item label="集指编号" prop="jzBh">
        <el-input
          v-model="queryParams.jzBh"
          placeholder="请输入集指编号"
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
      <el-form-item label="厂家" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入厂家"
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
      <el-form-item label="拍摄范围" prop="shootingRange">
        <el-input
          v-model="queryParams.shootingRange"
          placeholder="请输入拍摄范围"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
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
      </el-form-item>
      <el-form-item label="镜头" prop="lens">
        <el-input
          v-model="queryParams.lens"
          placeholder="请输入镜头"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="焦距" prop="focalLength">
        <el-input
          v-model="queryParams.focalLength"
          placeholder="请输入焦距"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="光圈" prop="aperture">
        <el-input
          v-model="queryParams.aperture"
          placeholder="请输入光圈"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="安装方式" prop="installationMethod">
        <el-input
          v-model="queryParams.installationMethod"
          placeholder="请输入安装方式"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="通信方式" prop="communicationModel">
        <el-input
          v-model="queryParams.communicationModel"
          placeholder="请输入通信方式"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="协议" prop="protocol">
        <el-input
          v-model="queryParams.protocol"
          placeholder="请输入协议"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      -->
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input
          v-model="queryParams.ipAddr"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="管辖单位" prop="dominion">
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
      </el-form-item> -->
      <el-form-item label="是否绑定杆件" prop="memberBh">
        <el-select
          v-model="queryParams.memberBh"
          placeholder="请选择是否绑定"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.INFRA_BOOLEAN_STRING)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
       </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="equLableList">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.EQU_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
       </el-select>
      </el-form-item>
      <el-form-item label="数据标签" prop="equLableList">
        <el-select
          v-model="queryParams.equLableList"
          multiple 
          placeholder="请选择数据标签"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.POLICE_ELECTRIC_LABLE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
       </el-select>
      </el-form-item>
      <el-form-item label="杆件编号" prop="serialNumber">
        <el-input
          v-model="queryParams.serialNumber"
          placeholder="请输入杆件编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="是否在保" prop="underWarranty">
        <el-select
          v-model="queryParams.underWarranty"
          placeholder="请选择是或否"
          clearable
          class="!w-240px"
        >
          <el-option label="是" value="1"/>
          <el-option label="否" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="像素" prop="pixel">
        <el-input
          v-model="queryParams.pixel"
          placeholder="请输入像素"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!--
      <el-form-item label="网关" prop="gateway">
        <el-input
          v-model="queryParams.gateway"
          placeholder="请输入网关"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="光交箱位置信息" prop="gjxAddr">
        <el-input
          v-model="queryParams.gjxAddr"
          placeholder="请输入光交箱位置信息"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="光交箱GPS定位-经度" prop="gjxLng">
        <el-input
          v-model="queryParams.gjxLng"
          placeholder="请输入光交箱GPS定位-经度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="光交箱GPS定位-纬度" prop="gjxLat">
        <el-input
          v-model="queryParams.gjxLat"
          placeholder="请输入光交箱GPS定位-纬度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-electric-police:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-electric-police:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <!-- <el-table-column label="设备编号" align="center" prop="equNumber" /> -->
      <el-table-column label="设备编码" align="center" prop="equCode" min-width="240px"/>
      <el-table-column label="设备名称" align="center" prop="equName" min-width="240px"/>
<!--      <el-table-column label="设备类型" align="center" prop="equType" />-->
      <!-- <el-table-column label="所在道路名称" align="center" prop="roadName" />
      <el-table-column label="路口名称" align="center" prop="intersectionName" min-width="220px"/> -->
      <el-table-column label="电警类型" align="center" prop="djType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.DJ_TYPE" :value="scope.row.djType" />
        </template>
      </el-table-column>
      <el-table-column label="是否在保" align="center">
        <template #default="scope">
          {{ scope.row.underWarranty == 1 ? '是' : (scope.row.underWarranty == 0 ? '否' : '') }}
        </template>
      </el-table-column>
      <!-- <el-table-column label="软件版本" align="center" prop="softwareVersion" /> -->
      <!-- <el-table-column label="所在道路名称" align="center" prop="roadName" /> -->
      <el-table-column label="方向" align="center" prop="direction" />


<!--      <el-table-column label="GPS定位-经度" align="center" prop="lng" />-->
<!--      <el-table-column label="GPS定位-纬度" align="center" prop="lat" />-->
      <!-- <el-table-column label="路网编号" align="center" prop="lwBh" min-width="180px"/>
      <el-table-column label="集指编号" align="center" prop="jzBh" min-width="180px"/> -->
      <!-- <el-table-column label="安装位置" align="center" prop="adress" /> -->
<!--      <el-table-column-->
<!--        label="安装时间"-->
<!--        align="center"-->
<!--        prop="installDate"-->
<!--        :formatter="dateFormatter"-->
<!--        width="180px"-->
<!--      />-->
      <!-- <el-table-column label="厂家" align="center" prop="manufacturer" /> -->
      <!-- <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      /> -->
      <el-table-column label="型号" align="center" prop="equModel" />
      <el-table-column label="像素" align="center" prop="pixel" />
      <el-table-column label="是否绑定杆件" align="center">
        <template #default="scope">
          {{ scope.row.memberBh ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column label="数据标签" align="center" prop="equLable" />
      <!-- <el-table-column label="归属单位" align="center" prop="unit" /> -->
      <!-- <el-table-column label="拍摄范围" align="center" prop="shootingRange" /> -->
      <!-- <el-table-column
        label="删除时间"
        align="center"
        prop="deletedTime"
        :formatter="dateFormatter"
        width="180px"
      /> -->
<!--      <el-table-column label="镜头" align="center" prop="lens" />-->
<!--      <el-table-column label="焦距" align="center" prop="focalLength" />-->
<!--      <el-table-column label="光圈" align="center" prop="aperture" />-->
      <!-- <el-table-column label="安装方式" align="center" prop="installationMethod" />
      <el-table-column label="通信方式" align="center" prop="communicationModel" />
      <el-table-column label="协议" align="center" prop="protocol" /> -->
<!--      <el-table-column label="IP地址" align="center" prop="ipAddr" min-width="120px"/>-->
<!--      <el-table-column label="网关" align="center" prop="gateway" />-->
<!--      <el-table-column label="光交箱位置信息" align="center" prop="gjxAddr" />-->
<!--      <el-table-column label="光交箱GPS定位-经度" align="center" prop="gjxLng" />-->
<!--      <el-table-column label="光交箱GPS定位-纬度" align="center" prop="gjxLat" />-->
<!--      <el-table-column label="管辖单位" align="center" prop="dominion" />-->
      <el-table-column label="杆件编号" align="center" prop="serialNum">
        <template #default="scope">
          <span v-if="scope.row.qrcodeRespVO">{{ scope.row.qrcodeRespVO.serialPrefix }}{{ padLeftZero(scope.row.qrcodeRespVO.serialNum, 5) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="杆件二维码" align="center">
        <template #default="scope">
          <div class="qr-code-container">
            <el-image
              style="cursor: pointer; width: 30px; height: 30px"
              :src="scope.row.qrcodeRespVO.qrcodeFile"
              fit="cover"
              v-if="scope.row.qrcodeRespVO && scope.row.qrcodeRespVO.qrcodeFile"
              @click="handleImagePreview(scope.row.qrcodeRespVO.id)"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="维修次数" align="center" prop="maintenanceCnt">
        <template #default="scope">
          {{ scope.row.maintenanceCnt }}
          <icon v-if="scope.row.maintenanceCnt> 0" @click="showMaintenanceInfo(scope.row.id)" style="color:#3F9EFF;margin-left: 5px;" icon="ep:search" :size="12" type="Success"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-electric-police:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-electric-police:delete']"
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
  <OpsElectricPoliceForm ref="formRef" @success="getList" />

  <OpsQrCodeForm ref="codeFormRef" @success="getList" />
  <OpsMaintenanceDetailForm ref="maintenanceFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsElectricPoliceApi, OpsElectricPoliceVO } from '@/api/police/opselectricpolice'
import OpsElectricPoliceForm from './OpsElectricPoliceForm.vue'
import OpsQrCodeForm from '../opsqrcode/OpsQrCodePrint.vue'
import OpsMaintenanceDetailForm from '../common/OpsMaintenanceDetailForm.vue'



/** 电警 列表 */
defineOptions({ name: 'PoliceOpsElectricPolice' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsElectricPoliceVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equNumber: undefined,
  equName: undefined,
  equType: undefined,
  softwareVersion: undefined,
  roadName: undefined,
  direction: undefined,
  intersectionName: undefined,
  djType: undefined,
  lng: undefined,
  lwBh: undefined,
  lat: undefined,
  jzBh: undefined,
  adress: undefined,
  installDate: [],
  manufacturer: undefined,
  createTime: [],
  equModel: undefined,
  unit: undefined,
  shootingRange: undefined,
  deletedTime: [],
  lens: undefined,
  focalLength: undefined,
  aperture: undefined,
  installationMethod: undefined,
  communicationModel: undefined,
  protocol: undefined,
  ipAddr: undefined,
  gateway: undefined,
  gjxAddr: undefined,
  gjxLng: undefined,
  gjxLat: undefined,
  memberBh:undefined,
  equLableList:[],
  serialNumber: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsElectricPoliceApi.getOpsElectricPolicePage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 添加/修改操作 */
const maintenanceFormRef = ref()
const showMaintenanceInfo = (id: number) => {
  maintenanceFormRef.value.open(id)
}

const padLeftZero = (serialNum, length) => {
  return String(serialNum).padStart(length, '0');
}

const codeFormRef = ref()
const handleImagePreview = (qrCodeId) => {
  codeFormRef.value.open('single', qrCodeId)
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
    await OpsElectricPoliceApi.deleteOpsElectricPolice(id)
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
    const data = await OpsElectricPoliceApi.exportOpsElectricPolice(queryParams)
    download.excel(data, '电警.xls')
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
