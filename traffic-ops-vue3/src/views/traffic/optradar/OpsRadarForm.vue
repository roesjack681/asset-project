<template>
  <Dialog :title="dialogTitle" width="800" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>基础信息</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="所在道路名称" prop="roadName">
              <el-input v-model="formData.roadName" placeholder="请输入所在道路名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="路口名称" prop="intersectionName">
              <el-select
                style="width: 100%"
                v-model="formData.intersectionName"
                filterable
                remote
                reserve-keyword
                placeholder="路口名称"
                :remote-method="remoteMethod"
                :loading="loading">
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.ldmc"
                  :value="item.ldmc"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
<!--          <el-col :span="12">-->
<!--            <el-form-item label="设备名称" prop="equName">-->
<!--              <el-input v-model="formData.equName" placeholder="请输入设备名称" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
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
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/sbpz-icon.png" alt=""/>设备配置</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="资产编号" prop="equNumber">
              <el-input v-model="formData.equNumber" placeholder="请输入资产编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="软件版本" prop="softwareVersion">
              <el-input v-model="formData.softwareVersion" placeholder="请输入软件版本" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="型号" prop="equModel">
              <el-input v-model="formData.equModel" placeholder="请输入型号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作频率" prop="operatingFrequency">
              <el-input v-model="formData.operatingFrequency" placeholder="请输入工作频率" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="探测距离" prop="detectionRange">
              <el-input v-model="formData.detectionRange" placeholder="请输入探测距离" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="探测角度" prop="detectionAngle">
              <el-input v-model="formData.detectionAngle" placeholder="请输入探测角度" />
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
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/dwxx-icon.png" alt=""/>单位信息</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="厂家" prop="manufacturer">
              <el-input v-model="formData.manufacturer" placeholder="请输入厂家" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属单位" prop="unit">
              <el-input v-model="formData.unit" placeholder="请输入归属单位" />
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
      <el-button @click="submitForm" type="primary" v-if="formType!='detail'" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsRadarApi, OpsRadarVO } from '@/api/traffic/optradar'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 雷达 表单 */
defineOptions({ name: 'OpsRadarForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  equName: undefined,
  equNumber: undefined,
  equType: undefined,
  softwareVersion: undefined,
  roadName: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  adress: undefined,
  installDate: undefined,
  manufacturer: undefined,
  equModel: undefined,
  unit: undefined,
  operatingFrequency: undefined,
  detectionRange: undefined,
  detectionAngle: undefined,
  installationMethod: undefined,
  communicationModel: undefined,
  protocol: undefined,
  deletedTime: undefined
})
const formRules = reactive({
})
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
      formData.value = await OpsRadarApi.getOpsRadar(id)
    } finally {
      formLoading.value = false
    }
  }
  setTimeout(() => {
    choosePointRef.value.initMap()
  }, 160)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as OpsRadarVO
    if (formType.value === 'create') {
      await OpsRadarApi.createOpsRadar(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsRadarApi.updateOpsRadar(data)
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
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equName: undefined,
    equNumber: undefined,
    equType: undefined,
    softwareVersion: undefined,
    roadName: undefined,
    intersectionName: undefined,
    lng: undefined,
    lat: undefined,
    adress: undefined,
    installDate: undefined,
    manufacturer: undefined,
    equModel: undefined,
    unit: undefined,
    operatingFrequency: undefined,
    detectionRange: undefined,
    detectionAngle: undefined,
    installationMethod: undefined,
    communicationModel: undefined,
    protocol: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
