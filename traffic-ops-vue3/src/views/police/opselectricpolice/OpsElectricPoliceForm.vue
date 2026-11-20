<template>
  <Dialog :title="dialogTitle" width="900" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="150px"
      v-loading="formLoading"
    >
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>基础信息</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="所在道路名称" prop="roadName">
              <el-input v-model="formData.roadName" placeholder="请输入所在道路名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="路口名称" prop="intersectionName">
              <el-input v-model="formData.intersectionName" placeholder="请输入所在路口名称"/>

<!--              <el-select-->
<!--                style="width: 100%"-->
<!--                v-model="formData.intersectionName"-->
<!--                filterable-->
<!--                remote-->
<!--                reserve-keyword-->
<!--                placeholder="路口名称"-->
<!--                :remote-method="remoteMethod"-->
<!--                :loading="loading"-->
<!--                @change="initMemberInfoList">-->
<!--                <el-option-->
<!--                  v-for="item in options"-->
<!--                  :key="item.id"-->
<!--                  :label="item.ldmc"-->
<!--                  :value="item.ldmc"/>-->
<!--              </el-select>-->
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="设备名称" prop="equName">
<!--              <el-select-->
<!--                v-model="formData.equName"-->
<!--                multiple-->
<!--                filterable-->
<!--                remote-->
<!--                reserve-keyword-->
<!--                placeholder="请输入关键词"-->
<!--                :remote-method="remoteMethod"-->
<!--                :loading="loading"-->
<!--              >-->
<!--                <el-option-->
<!--                  v-for="item in options"-->
<!--                  :key="item.value"-->
<!--                  :label="item.label"-->
<!--                  :value="item.value"-->
<!--                />-->
<!--              </el-select>-->
              <el-autocomplete
                v-model="formData.equName"
                :fetch-suggestions="querySearchAsync"
                placeholder="请输入内容"
                @select="handleSelect"
                value-key="equName"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安装时间" prop="installDate">
              <el-date-picker
                style="width: 100%"
                v-model="formData.installDate"
                type="date"
                value-format="x"
                placeholder="选择安装时间"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img style="width: auto" src="../../../assets/imgs/anwz-icon.png" alt=""/>安装位置</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="安装位置" prop="adress">
              <el-input v-model="formData.adress" placeholder="请输入安装位置" />
              <div style="width: 100%">
                <ChoosePoint :address="formData.adress" :point="[formData.lng, formData.lat]" @success="handlePointSuccess" ref="choosePointRef"/>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="所在杆件" prop="memberBh">
              <div class="equlist" style="width: 100%">
                <div class="equlist-item" style="display: block" :class="formData.memberBh == i.id?'ischoose':''" @click="chooseEue(i)" v-for="i in searchEquList" :key="i.id">
                  <div>类型：{{i.equType}}&nbsp;</div>
                  <div>设备名称：{{i.equName}}&nbsp;</div>
                  <div>方向：{{i.direction}}&nbsp;</div>
<!--                  <div>位置：{{i.adress}}&nbsp;</div>-->
<!--                  <div>IP地址：{{i.ipAddr}}&nbsp;</div>-->
                  <div>取电位置：{{i.powerSupplyLocation}}</div>
                </div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="设备图纸" prop="equId" v-if="drawing">
              <UploadFile :disabled="true" :fileType="['pdf']" :limit="1" :fileSize="100" :isOnlyPdf="true" :isShowTip="false" v-model="drawing"/>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/sbpz-icon.png" alt=""/>设备配置</div>
        <el-row :gutter="20" style="width: 100%">
           <el-col :span="12">
            <el-form-item label="厂家" prop="manufacturer">
              <el-input v-model="formData.manufacturer" placeholder="请输入厂家" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="型号" prop="equModel">
              <el-input v-model="formData.equModel" placeholder="请输入型号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="软件版本" prop="softwareVersion">
              <el-input v-model="formData.softwareVersion" placeholder="请输入软件版本" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="像素" prop="pixel">
              <el-input v-model="formData.pixel" placeholder="请输入像素" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="方向" prop="direction">
              <el-input v-model="formData.direction" placeholder="请输入方向" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="电警类型" prop="djType">
              <el-select v-model="formData.djType" placeholder="请选择电警类型">
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.DJ_TYPE)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="路网编号" prop="lwBh">
              <el-input v-model="formData.lwBh" placeholder="请输入路网编号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="集指编号" prop="jzBh">
              <el-input v-model="formData.jzBh" placeholder="请输入集指编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拍摄范围" prop="shootingRange">
              <el-input v-model="formData.shootingRange" placeholder="请输入拍摄范围" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="焦距" prop="focalLength">
              <el-input v-model="formData.focalLength" placeholder="请输入焦距" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="光圈" prop="aperture">
              <el-input v-model="formData.aperture" placeholder="请输入光圈" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="安装方式" prop="installationMethod">
              <el-input v-model="formData.installationMethod" placeholder="请输入安装方式" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="通信方式" prop="communicationModel">
              <el-input v-model="formData.communicationModel" placeholder="请输入通信方式" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="协议" prop="protocol">
              <el-input v-model="formData.protocol" placeholder="请输入协议" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="IP地址" prop="ipAddr">
              <el-input v-model="formData.ipAddr" placeholder="请输入IP地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="网关" prop="gateway">
              <el-input v-model="formData.gateway" placeholder="请输入网关" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="光交箱位置信息" prop="gjxAddr">
              <el-input v-model="formData.gjxAddr" placeholder="请输入光交箱位置信息" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="光交箱GPS定位-经度" prop="gjxLng">
              <el-input v-model="formData.gjxLng" placeholder="请输入光交箱GPS定位-经度" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="光交箱GPS定位-纬度" prop="gjxLat">
              <el-input v-model="formData.gjxLat" placeholder="请输入光交箱GPS定位-纬度" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="镜头" prop="lens">
              <el-input v-model="formData.lens" placeholder="请输入镜头" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否在保" prop="underWarranty">
              <el-select v-model="formData.underWarranty" placeholder="请选择是否在保">
                <el-option label="是" value="1"/>
                <el-option label="否" value="0"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="维保到期时间" prop="maintenanceExpiryDate">
              <el-date-picker
                style="width: 100%"
                v-model="formData.maintenanceExpiryDate"
                type="date"
                value-format="x"
                placeholder="选择维保到期时间"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/dwxx-icon.png" alt=""/>单位信息</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="行政区域" prop="unit">
              <el-select v-model="formData.unit" filterable clearable class="!w-240px">
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
                  :key="dict.label"
                  :label="dict.label"
                  :value="dict.label"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="管辖单位" prop="dominion">
              <el-select v-model="formData.dominion" placeholder="请选择所属管辖单位">
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.DOMINION)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.label"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/sbzt-icon.png" alt=""/>设备状态</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="formData.status">
                <el-radio v-for="dict in getStrDictOptions(DICT_TYPE.EQU_STATUS)" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="设备照片" prop="equImg">
              <UploadImg v-model="formData.equImg" height="120px" width="120px" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { OpsElectricPoliceApi, OpsElectricPoliceVO } from '@/api/police/opselectricpolice'
import {autocompleteOpsIntersection, autocompletePoliceOpsMemberList} from "@/api/bigdata";
import request from '@/config/axios'
import {OpsMemberApi} from '@/api/police/opsmember'

/** 电警 表单 */
defineOptions({ name: 'PoliceElectricPoliceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const searchEquList = ref([])
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const drawing = ref(null) // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
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
  installDate: undefined,
  manufacturer: undefined,
  equModel: undefined,
  unit: undefined,
  shootingRange: undefined,
  deletedTime: undefined,
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
  memberBh: undefined,
  maintenanceExpiryDate: undefined,
  pixel:undefined,
  underWarranty:undefined,
  dominion:undefined,
  status:undefined,
  equImg:undefined
})
const formRules = reactive({
})

const options = ref([])
const loading = ref(false)

const remoteMethod =  async (query) => {
  if (query !== '') {
    loading.value = true;
    const data = await autocompleteOpsIntersection({intersectionName: query})
    // const data = await autocompletePoliceOpsMemberList({intersectionName: query})
    options.value = data
    loading.value = false;
  } else {
    options.value = [];
  }
}
const formRef = ref() // 表单 Ref
const choosePointRef = ref() // 选择地图 Ref
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsElectricPoliceApi.getOpsElectricPolice(id)
      if(formData.value.memberBh) {
        const ress = await request.get({ url: '/police/ops-member/get?id=' + formData.value.memberBh })
        drawing.value = ress.drawing
        searchEquList.value = [ress]
      } else {
        searchEquList.value = []
      }
    } finally {
      formLoading.value = false
    }
  }
  setTimeout(() => {
    choosePointRef.value.initMap()
  }, 160)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const initMemberInfoList = async () => {
  const params = {
    pageSize: 100,
    pageNo: 1,
    intersectionName: formData.value.intersectionName
  }
  const res = await request.get({ url: '/police/ops-member/page', params })
  searchEquList.value = res.list
}

const chooseEue = async (item) => {
  formData.value.memberBh = item.id
  if(item.id) {
    const ress = await request.get({ url: '/police/ops-member/get?id=' + item.id })
    drawing.value = ress.drawing
  }
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as OpsElectricPoliceVO
    if (formType.value === 'create') {
      await OpsElectricPoliceApi.createOpsElectricPolice(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsElectricPoliceApi.updateOpsElectricPolice(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const handlePointSuccess = (data) => {
  if(data.equAddr) {
    formData.value.adress = data.equAddr
    formData.value.lng = data.equLng
    formData.value.lat = data.equLat
  }
}

const querySearchAsync = async (queryString, cb) => {
  var results = []
  if(queryString) {
    let res = await OpsMemberApi.getOpsMemberPage({pageNo: 1,pageSize: 99, equName: queryString})
    if(res.list) results = res.list
  }
  cb(results)
}
const handleSelect = (item) => {
  searchEquList.value = [item]
  chooseEue(item)
}

/** 重置表单 */
const resetForm = () => {
  drawing.value = null
  formData.value = {
    id: undefined,
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
    installDate: undefined,
    manufacturer: undefined,
    equModel: undefined,
    unit: undefined,
    shootingRange: undefined,
    deletedTime: undefined,
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
    maintenanceExpiryDate: undefined,
    pixel:undefined,
    underWarranty:undefined,
    dominion:undefined,
    status:undefined,
    equImg:undefined
  }
  searchEquList.value = []
  formRef.value?.resetFields()
}
</script>

<style lang="scss" scoped>
.equlist-item {
  width: 100%;
  border: 1px solid #dddddd;
  padding: 10px;
  border-radius: 4px;
  margin-top: 10px;
  display: flex;
  &.ischoose {
    border-color: #2A83EF;
  }
}
</style>
