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
     <el-form-item label="杆件编号" prop="serialNumber">
        <el-input
          v-model="queryParams.serialNumber"
          placeholder="请输入杆件编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="equName">
        <el-input
          v-model="queryParams.equName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="设备编号" prop="equNumber">
        <el-input
          v-model="queryParams.equNumber"
          placeholder="请输入设备编号"
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
      <el-form-item label="副臂长度" prop="jibLength">
        <el-input
          v-model="queryParams.jibLength"
          placeholder="请输入副臂长度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <!-- <el-form-item label="所在道路名称" prop="roadName">
        <el-input
          v-model="queryParams.roadName"
          placeholder="请输入所在道路名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <!-- <el-form-item label="杆件类型" prop="memberType">
        <el-select
          v-model="queryParams.memberType"
          placeholder="请选择杆件类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.MEMBER_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item> -->
      <!-- <el-form-item label="灯控路口名称" prop="intersectionName">
        <el-input
          v-model="queryParams.intersectionName"
          placeholder="请输入灯控路口名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <!-- <el-form-item label="GPS定位-经度" prop="lng">
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
      <el-form-item label="归属单位" prop="unit">
        <el-input
          v-model="queryParams.unit"
          placeholder="请输入归属单位"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <!-- <el-form-item label="高度" prop="hight">
        <el-input
          v-model="queryParams.hight"
          placeholder="请输入高度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="横杆长度" prop="barLength">
        <el-input
          v-model="queryParams.barLength"
          placeholder="请输入横杆长度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
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
      <el-form-item label="创建人" prop="creator">
        <el-input
          v-model="queryParams.creator"
          placeholder="请输入创建人"
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['traffic:ops-member:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['traffic:ops-member:export']"
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
      <el-table-column label="设备名称" align="center" prop="equName" min-width="220px"/>
      <!-- <el-table-column label="杆件编号" align="center" prop="serialNum"/> -->
      <!-- <el-table-column label="设备编号" align="center" prop="equNumber" /> -->
      <!-- <el-table-column label="所在道路名称" align="center" prop="roadName" />
      <el-table-column label="路口名称" align="center" prop="intersectionName" min-width="180px"/> -->
<!--      <el-table-column label="设备类型" align="center" prop="equType" />-->
      <el-table-column label="杆件类型" align="center" prop="memberType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.MEMBER_TYPE" :value="scope.row.memberType" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="GPS定位-经度" align="center" prop="lng" /> -->
      <!-- <el-table-column label="GPS定位-纬度" align="center" prop="lat" /> -->
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <!-- <el-table-column label="安装位置" align="center" prop="adress" /> -->
       <!-- <el-table-column
        label="安装时间"
        align="center"
        prop="installDate"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="厂家" align="center" prop="manufacturer" />
      <el-table-column label="归属单位" align="center" prop="unit" />
       -->
      <!-- <el-table-column label="高度" align="center" prop="hight" /> -->
      <!-- <el-table-column label="序号" align="center" prop="serialNum" /> -->
      <!-- <el-table-column label="横杆长度" align="center" prop="barLength" /> -->
      <!-- <el-table-column label="副臂长度" align="center" prop="jibLength" /> -->
      <el-table-column label="创建人" align="center" prop="creator" />
      <!-- <el-table-column label="管辖单位" align="center" prop="dominion" /> -->
      <el-table-column label="设备二维码" align="center">
        <template #default="scope">
          <div class="qr-code-container">
            <el-image
              style="cursor: pointer; width: 30px; height: 30px"
              :src="scope.row.qrCodeUrl"
              fit="cover"
              v-if="scope.row.qrCodeUrl"
              @click="handleImagePreview(scope.row.equNumber)"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="绑定电警数" align="center" prop="ectricPoliceCnt" />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openDetailForm('detail', scope.row.id)"
          >
            详情
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['traffic:ops-member:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['traffic:ops-member:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-image-viewer
      v-if="imagePreviewParams.showViewer"
      :url-list="imagePreviewParams.imageList"
      :initial-index="imagePreviewParams.currentIndex"
      @close="imagePreviewParams.showViewer = false"
    />
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <OpsMemberForm ref="formRef" @success="getList" />

  <OpsMemberDetailForm ref="detailFormRef" @success="getList" />

  <OpsQrCodeForm ref="codeFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsMemberApi, OpsMemberVO } from '@/api/police/opsmember'
import OpsMemberForm from './OpsMemberForm.vue'
import OpsMemberDetailForm from './OpsMemberDetailForm.vue'
import OpsQrCodeForm from '../opsqrcode/OpsQrCodePrint.vue'
/** 杆件 列表 */
defineOptions({ name: 'TrafficOpsMember' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsMemberVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equName: undefined,
  equNumber: undefined,
  equType: undefined,
  jibLength: undefined,
  roadName: undefined,
  memberType: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  createTime: [],
  adress: undefined,
  installDate: [],
  manufacturer: undefined,
  deletedTime: [],
  unit: undefined,
  hight: undefined,
  barLength: undefined,
  serialNum: undefined,
  ectricPoliceCnt: undefined,
  creator: undefined,
  serialNumber: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const imagePreviewParams = reactive({
  imageList: [],
  currentIndex: 10,
  showViewer: false
})

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsMemberApi.getOpsMemberPage(queryParams)
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

const detailFormRef = ref()
const openDetailForm = (type: string, id?: number) => {
  detailFormRef.value.open(type, id)
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

const codeFormRef = ref()
const handleImagePreview = (qrCodeId) => {
  codeFormRef.value.open('single', qrCodeId)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await OpsMemberApi.deleteOpsMember(id)
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
    const data = await OpsMemberApi.exportOpsMember(queryParams)
    download.excel(data, '杆件.xls')
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
