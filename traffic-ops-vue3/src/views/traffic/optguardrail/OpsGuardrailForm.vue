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
              <el-select
                v-model="formData.roadName"
                filterable
                remote
                clearable
                reserve-keyword
                placeholder="请选择道路"
                :remote-method="remoteMethod"
                :loading="loading"
              >
                <el-option
                  v-for="item in opsRoadDictSelect"
                  :key="item.dldm"
                  :label="item.dlmc"
                  :value="item.dlmc"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安装位置" prop="adress">
              <el-input v-model="formData.adress" placeholder="请输入安装位置" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="安装时间" prop="installDate">
              <el-date-picker
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
            <el-form-item label-width="0" prop="adress">
              <div style="width: 100%">
                <ChoosePointHulan :address="formData.adress" :startPosition="startPosition" :endPosition="endPosition" @success="handlePointSuccess" ref="choosePointRef"/>
<!--                <ChoosePoint :address="formData.adress" :point="[formData.lng, formData.lat]" @success="handlePointSuccess" ref="choosePointRef"/>-->
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>
          护栏产品
          <el-icon @click="addRproductList" style="vertical-align: middle;margin-top: -4px;cursor:pointer;" size="18" color="#409eff"><circle-plus-filled /></el-icon>
        </div>
        <el-row :gutter="20" style="width: 100%" v-for="(i, index) in formData.rproductList" :key="index">
          <el-col :span="12">
            <el-form-item label="护栏型号" prop="productLabel">
              <el-select v-model="i.productId">
                <el-option @click="chooseOneProduct(x, i,index)" v-for="x in opsGuardrailProduct" :key="x.id" :value="x.id" :label="x.label"/>
              </el-select>
<!--              <el-input v-model="i.productLabel" placeholder="护栏型号" />-->
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="数量" prop="pieces">
              <el-input v-model="i.pieces" placeholder="数量" />
            </el-form-item>
          </el-col>
          <el-col :span="1">
            <el-icon @click="deleteRproductList(index)" style="margin-top: 5px;cursor:pointer;" size="18" color="red"><remove-filled /></el-icon>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>
          辅助设备信息
          <el-icon @click="addAuxiliaryList" style="vertical-align: middle;margin-top: -4px;cursor:pointer;" size="18" color="#409eff"><circle-plus-filled /></el-icon>
        </div>
        <el-row :gutter="20" style="width: 100%" v-for="(i, index) in formData.auxiliaryList" :key="index">
          <el-col :span="12">
            <el-form-item label="名称" prop="marklineSort">
              <el-select v-model="i.auxiliaryName" placeholder="请选择名称">
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.AUXILIARY)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.label"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="数量" prop="auxiliaryNum">
              <el-input v-model="i.auxiliaryNum" placeholder="数量" />
            </el-form-item>
          </el-col>
          <el-col :span="1">
            <el-icon @click="deleteAuxiliaryList(index)" style="margin-top: 5px;cursor:pointer;" size="18" color="red"><remove-filled /></el-icon>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>
          开口信息
          <el-icon @click="addGapList" style="vertical-align: middle;margin-top: -4px;cursor:pointer;" size="18" color="#409eff"><circle-plus-filled /></el-icon>
        </div>
        <el-row :gutter="20" style="width: 100%" v-for="(i, index) in formData.gapList" :key="index">
          <el-col :span="8">
            <el-form-item label="开口名称" prop="gapName">
              <el-input v-model="i.gapName" placeholder="开口名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开口类型" prop="gapType">
              <el-input v-model="i.gapType" placeholder="开口类型" />
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="开口位置" prop="marklineNum">
              <img @click="showKakouDia(index)" style="width: 20px;cursor:pointer;" src="../../../assets/imgs/pos.png" alt=""/>
            </el-form-item>
          </el-col>
          <el-col :span="1">
            <el-icon @click="deleteGapList" style="margin-top: 5px;cursor:pointer;" size="18" color="red"><remove-filled /></el-icon>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/dwxx-icon.png" alt=""/>单位信息</div>
        <el-row :gutter="20" style="width: 100%">
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
                  :key="dict.label"
                  :label="dict.label"
                  :value="dict.label"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
<!--      <div class="form-box-wrapper">-->
<!--        <div class="addform-title"><img src="../../../assets/imgs/sbzt-icon.png" alt=""/>设备状态</div>-->
<!--        <el-row :gutter="20" style="width: 100%">-->
<!--          <el-col :span="24">-->
<!--            <el-form-item label="状态" prop="status">-->
<!--              <el-radio-group v-model="formData.status">-->
<!--                <el-radio v-for="dict in getStrDictOptions(DICT_TYPE.EQU_STATUS)" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>-->
<!--              </el-radio-group>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--      </div>-->
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/sbzt-icon.png" alt=""/>图片管理</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="" prop="imgsList" label-width="0">
              <UploadImgsObj :limit="3" v-model="formData.imgsList" height="120px" width="120px" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/sbzt-icon.png" alt=""/>视频管理</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="" prop="equImg" label-width="0">
              <UploadVideo :fileSize="10" :limit="1" v-model="formData.equVideoList" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
    <positiionKakou :startPosition="startPosition" :endPosition="endPosition" :point="point" @success="positionSuccess" ref="kakouRef"/>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsGuardrailApi, OpsGuardrailVO } from '@/api/traffic/optguardrail'
import { OpsRoadApi, OpsRoadVO } from '@/api/traffic/opsroad'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import {CirclePlusFilled, RemoveFilled} from "@element-plus/icons-vue";
import {autocompleteOpsIntersection} from "@/api/bigdata";
import positiionKakou from './positionKakou.vue'
/** 护栏 表单 */
defineOptions({ name: 'OpsGuardrailForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  equName: undefined,
  equType: undefined,
  roadName: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  adress: undefined,
  installDate: undefined,
  hight: undefined,
  length: undefined,
  material: undefined,
  manufacturer: undefined,
  unit: undefined,
  dominion: undefined,
  status: undefined,
  equImg: undefined,
  model: undefined,
  pieces: undefined,
  totalLength: undefined,
  pedestal: undefined,
  humanDecency: undefined,
  stud: undefined,
  sign: undefined,
  gzbsz: undefined,
  tynbsd: undefined,
  rxdfzz: undefined,
  remark: undefined,
  deletedTime: undefined,
  imgsList: [],
  equVideoList: [],
  attachInfolist: [],
  rproductList: [],
  auxiliaryList: [],
  gapList: []
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref
const kakouRef = ref() // 表单 Ref
const choosePointRef = ref() // 选择地图 Ref

const opsRoadDict = ref([])
const opsRoadDictSelect = ref([])
const opsGuardrailProduct = ref([])
const options = ref([])
const startPosition = ref([])
const point = ref([])
const endPosition = ref([])
const loading = ref(false)
const kakouIndex = ref(-1)
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogTitle.value = t('action.' + type)
  formType.value = type
  startPosition.value = []
  endPosition.value = []
  point.value = []
  getDic()
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsGuardrailApi.getOpsGuardrail(id)
      formData.value.imgsList = []
      formData.value.equVideoList = []
      if(formData.value.attachInfoList.length) {
        formData.value.attachInfoList.forEach(e => {
          if(e.fileType == 1) {
            formData.value.imgsList.push(e)
          }
          if(e.fileType == 3) {
            formData.value.equVideoList.push(e)
          }
        })
      }
      if(formData.value.startLng && formData.value.endLng) {
        startPosition.value = [formData.value.startLng, formData.value.startLat]
        endPosition.value = [formData.value.endLng, formData.value.endLat]
      }
      var res = await OpsGuardrailApi.getOpsGuardrailProductPage({pageNo: 1, pageSize: 99})
      const list = res.list
      formData.value.rproductList.map(e => {
        list.forEach(x => {
          if(e.productId == x.id) {
            e.length = x.length
          }
        })
        return e
      })
      console.log(formData.value, '001')
    } finally {
      formLoading.value = false
    }
  }
  dialogVisible.value = true
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
    const data = formData.value as unknown as OpsGuardrailVO
    data.attachInfoList = []
    if(formData.value.imgsList.length) {
      data.attachInfoList.push(...formData.value.imgsList)
    }
    if(formData.value.equVideoList.length) {
      data.attachInfoList.push(...formData.value.equVideoList)
    }
    if(formData.value.startLng && formData.value.endLng) {
      formData.value.length = await calcuDistance([formData.value.startLng, formData.value.startLat], [formData.value.endLng, formData.value.endLat])
    }
    formData.value.pieces = 0
    formData.value.length = 0
    formData.value.rproductList.map(e => {
      formData.value.pieces = formData.value.pieces + Number(e.pieces)
      formData.value.length = formData.value.length + (e.length/1000 * e.pieces)
      if(e.id) delete e.id
      return e
    })
    formData.value.auxiliaryList.map(e => {
      if(e.id) delete e.id
      return e
    })
    formData.value.gapList.map(e => {
      if(e.id) delete e.id
      return e
    })
    if (formType.value === 'create') {
      await OpsGuardrailApi.createOpsGuardrail(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsGuardrailApi.updateOpsGuardrail(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const remoteMethod =  async (query) => {
  if (query !== '') {
    loading.value = true;
    opsRoadDictSelect.value = []
    opsRoadDict.value.forEach(e => {
      if(e.dlmc && e.dlmc.indexOf(query) > -1) {
        opsRoadDictSelect.value.push(e)
      }
    })
    loading.value = false;
  } else {
    options.value = [];
  }
}
const handlePointSuccess = (data) => {
  formData.value = {
    ...formData.value,
    ...data
  }
  console.log(formData.value)
}
const getDic = async () => {
  var roadRes = await OpsRoadApi.getOpsRoadDict({})
  opsRoadDict.value = roadRes.list
  var res = await OpsGuardrailApi.getOpsGuardrailProductPage({pageNo: 1, pageSize: 99})
  opsGuardrailProduct.value = res.list
  opsGuardrailProduct.value.map(e => {
    e.label = `型号:${e.model}长:${e.length}高:${e.hight}`
    e.id = e.id + ''
    return e
  })
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equName: undefined,
    equType: undefined,
    roadName: undefined,
    intersectionName: undefined,
    lng: undefined,
    lat: undefined,
    adress: undefined,
    installDate: undefined,
    hight: undefined,
    length: undefined,
    material: undefined,
    manufacturer: undefined,
    unit: undefined,
    dominion: undefined,
    status: undefined,
    equImg: undefined,
    model: undefined,
    pieces: undefined,
    totalLength: undefined,
    pedestal: undefined,
    humanDecency: undefined,
    stud: undefined,
    sign: undefined,
    gzbsz: undefined,
    tynbsd: undefined,
    rxdfzz: undefined,
    remark: undefined,
    deletedTime: undefined,
    imgsList: [],
    equVideoList: [],
    attachInfolist: [],
    rproductList: [],
    auxiliaryList: [],
    gapList: []
  }
  formRef.value?.resetFields()
}
const addRproductList = () => {
  formData.value.rproductList.push({})
}
const deleteRproductList = (index) => {
  console.log(index, formData.value.rproductList)
  formData.value.rproductList.splice(index, 1)
}
const addAuxiliaryList = () => {
  formData.value.auxiliaryList.push({})
}
const deleteAuxiliaryList = (index) => {
  formData.value.auxiliaryList.splice(index, 1)
}
const addGapList = () => {
  formData.value.gapList.push({})
}
const deleteGapList = (index) => {
  formData.value.gapList.splice(index, 1)
}
const showKakouDia = (index) => {
  startPosition.value = []
  endPosition.value = []
  point.value = []
  kakouIndex.value = index
  if(formData.value.startLng && formData.value.endLng) {
    startPosition.value = [formData.value.startLng, formData.value.startLat]
    endPosition.value = [formData.value.endLng, formData.value.endLat]
  }
  if(formData.value.gapList[kakouIndex.value].lng) {
    point.value = [formData.value.gapList[kakouIndex.value].lng, formData.value.gapList[kakouIndex.value].lat]
  }
  kakouRef.value.open()
}
const positionSuccess = (data) => {
  formData.value.gapList[kakouIndex.value].lng = data.lng
  formData.value.gapList[kakouIndex.value].lat = data.lat
}
const calcuDistance = (start, end) => {
  return new Promise((resolve, reject) => {
    //步行导航
    var walking = new AMap.Walking({panel: "panel"});
    //根据起终点坐标规划步行路线
    walking.search(start, end, function(status, result) {
      if (status === 'complete') {
        var routes = result.routes; // 获取路线信息
        if (routes && routes.length > 0) {
          var distance = routes[0].distance; // 获取第一条路线的总距离，单位为米
          resolve(distance) //单位米
        }
      } else {
        reject('步行路线数据查询失败');
      }
    });
  })
}
const chooseOneProduct = (dict, item, index) => {
  item.length = dict.length
}
</script>
