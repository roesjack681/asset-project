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
          <el-col :span="12">
            <el-form-item label="设备名称" prop="equName">
              <el-input v-model="formData.equName" placeholder="请输入设备名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备编号" prop="equNumber">
              <el-input v-model="formData.equNumber" placeholder="请输入设备编号" />
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
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/sbpz-icon.png" alt=""/>设备配置</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="描述" prop="des">
              <el-input v-model="formData.des" placeholder="请输入描述" />
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
            <el-form-item label="屏幕朝向" prop="screenOrientation">
              <el-input v-model="formData.screenOrientation" placeholder="请输入屏幕朝向" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="子网" prop="subnet">
              <el-input v-model="formData.subnet" placeholder="请输入子网" />
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
            <el-form-item label="球机IP" prop="ptzIp">
              <el-input v-model="formData.ptzIp" placeholder="请输入球机IP" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="违章编码" prop="violationCode">
              <el-input v-model="formData.violationCode" placeholder="请输入违章编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="诱导编码" prop="inducedEncoding">
              <el-input v-model="formData.inducedEncoding" placeholder="请输入诱导编码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="大屏对应编号" prop="dpbh">
              <el-input v-model="formData.dpbh" placeholder="请输入大屏对应编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="大小" prop="size">
              <el-input v-model="formData.size" placeholder="请输入大小" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="formData.remark" placeholder="请输入备注" />
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
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { OpsBigledApi, OpsBigledVO } from '@/api/traffic/opsbigled'
import {autocompleteOpsIntersection} from "@/api/bigdata";

/** 大屏 表单 */
defineOptions({ name: 'OpsBigledForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
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
  installDate: undefined,
  manufacturer: undefined,
  unit: undefined,
  deletedTime: undefined
})
const formRules = reactive({
})

const options = ref([])
const loading = ref(false)

const remoteMethod =  async (query) => {
  if (query !== '') {
    loading.value = true;
    const data = await autocompleteOpsIntersection({intersectionName: query})
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
      formData.value = await OpsBigledApi.getOpsBigled(id)
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
    const data = formData.value as unknown as OpsBigledVO
    if (formType.value === 'create') {
      await OpsBigledApi.createOpsBigled(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsBigledApi.updateOpsBigled(data)
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
    installDate: undefined,
    manufacturer: undefined,
    unit: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
