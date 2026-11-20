<template>
  <Dialog :title="dialogTitle" width="700" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="报修时间" prop="faultDate">
        <el-date-picker
          v-model="formData.faultDate"
          type="datetime"
          value-format="x"
          placeholder="选择维修时间"/>
      </el-form-item>
      <!-- <el-form-item label="报修地点" prop="problem">
        <el-input v-model="formData.equAddr" placeholder="请输入报修地点或在地图上选择位置" />
        <div style="width:100%;height: auto;margin-top: 10px;border-radius: 10px; overflow: hidden;position: relative">
          <div style="display: flex;position: absolute;top: 10px;left: 10px;right: 10px;z-index: 101">
            <el-autocomplete
              style="flex: 1;margin-right: 8px;"
              v-model="keyword"
              :fetch-suggestions="autoInput"
              placeholder="请输入关键字"
              @select="handleSelect"
            />
            <el-button type="primary" @click="searchPoint(cuttentChoosePoint)">搜索</el-button>
          </div>
          <div id="map2"></div>
        </div>
      </el-form-item> -->
      <div>
        <div style="padding: 10px;color: #2A83EF">工单信息：</div>
        <el-form-item label="存在问题" prop="problem">
          <el-input v-model="formData.problem" type="textarea" rows="4" placeholder="请输入存在问题" />
        </el-form-item>
        <el-form-item label="上报人" prop="reportUserId">
          <el-select v-model="formData.reportUserId" filterable clearable placeholder="请选择">
            <el-option
              @click="chooseUser(item)"
              v-for="item in userList"
              :key="item.id"
              :label="item.nickname"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="现场照片" prop="pic">
            <UploadImgs v-model="fileList"/>
        </el-form-item>
        <el-form-item label="维修人" prop="maintenanceUserId">
          <el-select v-model="formData.maintenanceUserId" filterable placeholder="请选择">
            <el-option
              @click="chooseMaintenanceUser(item)"
              v-for="item in userList"
              :key="item.id"
              :label="item.nickname"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="预计修复难度" prop="projectedRepair">
          <el-select v-model="formData.projectedRepair" placeholder="请选择">
            <el-option
              v-for="dict in getStrDictOptions(DICT_TYPE.work_difficulty)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.label"
            />
          </el-select>
        </el-form-item>
      </div>
      <div>
        <div style="padding: 10px;color: #2A83EF">设备信息：</div>
        <el-form-item label="设备类型" prop="equType">
          <el-select v-model="formData.equType" placeholder="请选择">
            <el-option
              @click="chooseOneEquType(item)"
              v-for="item in equTypeDic"
              :key="item.type"
              :label="item.type"
              :value="item.type"/>
          </el-select>
        </el-form-item>
        <el-form-item label="选择查询条件" prop="searchType" v-if="formData.equType === '电警'">
          <el-radio-group v-model="searchType" @change="changeSearchType">
            <el-radio label="1">路口查询</el-radio>
            <el-radio label="2">IP查询</el-radio>
          </el-radio-group>
        </el-form-item>
        <div v-if="formData.equType !== '护栏'">
          <el-form-item label="选择路口" prop="reportUserId" v-if="searchType == 1">
            <el-select
              style="width: 100%"
              v-model="formData.value"
              filterable
              remote
              reserve-keyword
              placeholder="请输入路口名称查询"
              :remote-method="remoteMethod"
              :loading="loading">
              <el-option
                @click="chooseOneLk(item)"
                v-for="item in options"
                :key="item.id"
                :label="item.ldmc"
                :value="item.ldmc"/>
            </el-select>
          </el-form-item>
          <el-form-item label="输入IP" prop="reportUserId" v-if="searchType == 2">
            <el-input placeholder="输入IP" v-model="formData.ip" class="input-with-select">
              <template #append>
                <el-button @click="searchIp">查询</el-button>
              </template>
            </el-input>
          </el-form-item>
        </div>
        <div v-else>
<!--          <el-form-item label="选择道路" prop="reportUserId">-->
<!--            <el-select-->
<!--              style="width: 100%"-->
<!--              v-model="formData.value"-->
<!--              filterable-->
<!--              remote-->
<!--              reserve-keyword-->
<!--              placeholder="请输入道路名称查询"-->
<!--              :remote-method="remoteMethod"-->
<!--              :loading="loading">-->
<!--              <el-option-->
<!--                @click="chooseOneDl(item)"-->
<!--                v-for="item in optionsdl"-->
<!--                :key="item.id"-->
<!--                :label="item.ldmc"-->
<!--                :value="item.ldmc"/>-->
<!--            </el-select>-->
<!--          </el-form-item>-->
          <el-form-item label="选择道路" prop="roadName">
            <el-select
              v-model="formData.roadName"
              filterable
              remote
              clearable
              reserve-keyword
              placeholder="请选择道路"
              :remote-method="remoteMethod1"
              :loading="loading"
            >
              <el-option
                @click="chooseOneDl(item)"
                v-for="item in opsRoadDictSelect"
                :key="item.dldm"
                :label="item.dlmc"
                :value="item.dlmc"
              />
            </el-select>
          </el-form-item>
        </div>
          <el-form-item label="设备信息" prop="equId">
              <el-radio-group class="equradio" v-model="formData.equId">
                <el-radio border :label="i.id" v-for="i in searchEquList" :key="i.id">
                  <div class="equlist" style="width: 100%;padding-left: 12px;">
                    <div style="display: flex; flex-direction: column;" @click="chooseEue(i)">
                      <div>类型：{{i.equType}}</div>
                      <div>路段名称：{{i.intersectionName}}</div>
                      <div>位置：{{i.adress}}</div>
                      <div>IP地址：{{i.ipAddr}}</div>
                      <div v-if="formData.equType=='电警'">取电位置: {{ i.powerSupplyLocation }}</div>
                    </div>
                  </div>
                </el-radio>
              </el-radio-group>
<!--            <div class="equlist" style="width: 100%;height: auto">-->
<!--              <div style="display: flex; flex-direction: column;" class="equlist-item" :class="formData.equId == i.id?'ischoose':''" v-for="i in searchEquList" :key="i.id" @click="chooseEue(i)">-->
<!--                <div>类型：{{i.equType}}</div>-->
<!--                <div>路段名称：{{i.intersectionName}}</div>-->
<!--                <div>位置：{{i.adress}}</div>-->
<!--                <div>IP地址：{{i.ipAddr}}</div>-->
<!--                <div v-if="formData.equType=='电警'">取电位置: {{ i.powerSupplyLocation }}</div>-->
<!--              </div>-->
<!--            </div>-->
          </el-form-item>
        <el-form-item label="取电位置" prop="powerSupplyLocation">
          <el-input v-model="formData.powerSupplyLocation" placeholder="请描述杆件取电位置"/>
        </el-form-item>
<!--        <div v-if = "formData.equType && formData.equType == '电警'">-->
<!--          <el-form-item label="设备IP" prop="equId">-->
<!--            <el-autocomplete-->
<!--              size="large"-->
<!--              class="inline-input"-->
<!--              v-model="formData.ipAddr"-->
<!--              :fetch-suggestions="querySearch"-->
<!--              placeholder="请输入设备IP"-->
<!--              :trigger-on-focus="false"-->
<!--              @select="handleSelect"-->
<!--            />-->
<!--          </el-form-item>-->
<!--        </div>-->
      </div>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary">提 交</el-button>
      <!-- <el-button @click="dialogVisible = false" type="primary">派 单</el-button> -->
      <el-button @click="getSuccess">取 消</el-button>
    </template>
    <!-- <OpsMaintenanceDispatchForm ref="formDispatchRef" @success="getSuccess"/> -->
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import { OpsElectricPoliceApi } from '@/api/traffic/optelectricpolice'
// import OpsMaintenanceDispatchForm from './OpsMaintenanceDispatchForm.vue'
import moment from 'moment'
// import OpsMaintenanceForm from "@/views/traffic/optmaintenance/OpsMaintenanceForm.vue";
import { useUserStore } from '@/store/modules/user'
import request from '@/config/axios'
import {OpsRoadApi} from "@/api/traffic/opsroad";
import {OpsGuardrailApi} from "@/api/traffic/optguardrail";
/** 设备维保信息 表单 */
defineOptions({ name: 'OpsMaintenanceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const fileList = ref([]) // 弹窗的标题
const progress = ref([]) // 弹窗的标题
const audioFile = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
// const map = ref(null) // 表单的类型：create - 新增；update - 修改
// const keyword = ref('')
// const cuttentChoosePoint = ref({})
// const marker = ref(null)
const currentChooseUrl = ref('')
const searchEquList = ref([])
const currentId = ref('')
const searchType = ref('1')

const loading = ref(false)
const opsRoadDictSelect = ref([])
const options = ref([])
const opsRoadDict = ref([])
const options2 = ref([])
const equTypeDic = reactive([
  {
    type: '信号灯',
    url: 'traffic/ops-signal-light/page'
  },
  {
    type: '信号机',
    url: '/traffic/ops-signal/page'
  },
  {
    type: '电警',
    url: '/traffic/ops-electric-police/page'
  },
  {
    type: '杆件',
    url: '/traffic/ops-member/page'
  },
  {
    type: 'led屏',
    url: '/traffic/ops-bigled/page'
  },
  {
    type: '护栏',
    url: '/traffic/ops-guardrail/page'
  },
])
const userList = ref([])
const formData = ref({
  id: undefined,
  equType: undefined,
  equId: undefined,
  ipAddr: undefined,
  equAddr: undefined,
  equLng: undefined,
  equLat: undefined,
  problem: undefined,
  faultType: undefined,
  faultDate: undefined,
  reportUserId:undefined,
  maintenance: undefined,
  maintenanceDate: undefined,
  maintenanceUserId: undefined,
  maintenancePersonnel: undefined,
  maintenancePhone: undefined,
  suggestion: undefined,
  remark: undefined,
  deletedTime: undefined,
  fileList: []
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref
const formDispatchRef = ref() // 派单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  audioFile.value = ''
  searchType.value = '1'
  searchEquList.value = []
  resetForm()
  getUserList()
  getDic()
  // setTimeout(() => {
  //   initMap()
  // }, 160)
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsMaintenanceApi.getOpsMaintenance(id)
      progress.value = await OpsMaintenanceApi.maintenanceProgress(id)
      fileList.value = []
      formData.value.fileDTOList.forEach(e => {
        if(e.fileType == 1) {
          fileList.value.push(e)
        } else if(e.fileType == 2) {
          audioFile.value = e.fileUrl
        }
      })
    } finally {
      formLoading.value = false
    }
  }
  if (!formData.value.faultDate) {
    formData.value.faultDate = Date.now()
  }

  if (!formData.value.reportUserId) {
    formData.value.reportUserId = Number(useUserStore().getUser.id)
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const remoteMethod = async (query) => {
  options2.value = []
  if (query !== '') {
    loading.value = true;
    const res = await OpsMaintenanceApi.autocompleteOpsIntersection(query)
    loading.value = false;
    if(res && res.length) {
      options.value = res
    } else {
      options.value = []
    }
  } else {
    options.value = [];
  }
}
const remoteMethod1 =  async (query) => {
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
const chooseOneLk = async (item) => {
  formData.value.equId = ''
  const params = {
    pageSize: 100,
    pageNo: 1,
    intersectionName: formData.value.value
  }
  const res = await request.get({ url: currentChooseUrl.value, params })
  searchEquList.value = res.list
}
const chooseOneDl = async (item) => {
  formData.value.equId = ''
  const params = {
    pageSize: 100,
    pageNo: 1,
    roadName: formData.value.roadName
  }
  const res = await request.get({ url: currentChooseUrl.value, params })
  searchEquList.value = res.list
}
const chooseOneEquType = async (item) => {
  currentChooseUrl.value = item.url
  formData.value.equId = ''
  formData.value.value = ''
  searchEquList.value = []
  if(!formData.value.value) return
  // formData.value.equType = ''
  const params = {
    pageSize: 100,
    pageNo: 1,
    intersectionName: formData.value.value
  }
  const res = await request.get({ url: currentChooseUrl.value, params })
  searchEquList.value = res.list
}

const chooseEue = (item) => {
  formData.value.equId = item.id
  formData.value.equType = item.equType
  formData.value.ipAddr = item.ipAddr
}

const restaurants = ref([])
const querySearch = async (queryString, cb) => {
  if(queryString) {
    const data = await OpsElectricPoliceApi.autocompleteTrafficOpsElectricPoliceList(queryString)
    data.data.map(e => {
      e.value = e.ipAddr
      return
    })

    restaurants.value = data.data
  }
  var results = queryString
    ? restaurants.value.filter(createFilter(queryString))
    : restaurants.value
  // 调用 callback 返回建议列表的数据
  cb(results)
}
const createFilter = (queryString) => {
  return (restaurant) => {
    return (
      restaurant.value
        .toLowerCase()
        .indexOf(queryString.toLowerCase()) === 0
    )
  }
}

const handleSelect = (item) => {
  formData.value.equId = item.id
  formData.value.equAddr = item.equAddr
  formData.value.equLng = item.lng
  formData.value.equLat = item.lat
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  if(!formData.value.equType) return message.error("请选择设备类型")
  if(!formData.value.projectedRepair) return message.error("请选择预计修复难度")
  if ((searchType.value == '1' && formData.value.value && !formData.value.equId)
      || (searchType.value == '2' && formData.value.ip && !formData.value.equId)) {
    message.error("请选择设备！")
    return
  }

  // 提交请求
  formLoading.value = true
  let bh
  formData.value.fileInfoList = []
  fileList.value.forEach(e => {
    formData.value.fileInfoList.push({
      fileType: 1,
      fileUrl: e
    })
  })
  try {
    if(formData.value.equType === '电警' && formData.value.equId && formData.value.ipAddr) {
      let bh = await OpsMaintenanceApi.queryOrderByIp(formData.value.equId,formData.value.ipAddr)
      if(bh) {
        ElMessageBox.confirm('已有未完成工单，去查看？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            emit('gotoDetail', bh)
          })
          .catch(() => {
          });
        return
      }
    }
    const data = formData.value as unknown as OpsMaintenanceVO
    if (formType.value === 'create') {
      bh = await OpsMaintenanceApi.createOpsMaintenance(data)
      // dialogVisible.value = false
      // formDispatchRef.value.open('create', bh)
      message.success(t('common.createSuccess'))
    } else {
      // dialogVisible.value = false
      await OpsMaintenanceApi.updateOpsMaintenance(data)
      message.success(t('common.updateSuccess'))
    }
    // 发送操作成功的事件
    dialogVisible.value = false
    emit('success')
  } finally {
    dialogVisible.value = false
    formLoading.value = false
  }
}
const getUserList = async () => {
  const userListData = await OpsMaintenanceApi.queryAllUserInfoList()
  userList.value = userListData
}
const chooseUser = (item) => {
  formData.value.reportPersonnel = item.nickname
  formData.value.reportDeptId = item.deptId
  formData.value.reportDept = item.deptName
}

const chooseMaintenanceUser = (item) => {
  formData.value.maintenanceUserId = item.id
  formData.value.maintenancePersonnel = item.nickname
  formData.value.maintenancePhone = item.mobile
}


const getSuccess = () => {
  dialogVisible.value = false
  emit('success')
}
const changeSearchType =  async () => {
  searchEquList.value = []
  formData.value.value = ''
  formData.value.ipAddr = ''
}
const searchIp = async () => {
  if(!formData.value.equType || !formData.value.ip)  return
  const params = {
    pageSize: 100,
    pageNo: 1,
    ipAddr: formData.value.ip
  }
  const res = await request.get({ url: currentChooseUrl.value, params })
  if (!res.list || res.list.length == 0) {
    message.warning('未查询到该设备')
    return
  }
  searchEquList.value = res.list
}
const getDic = async () => {
  var roadRes = await OpsRoadApi.getOpsRoadDict({})
  opsRoadDict.value = roadRes.list
}
// const initMap = () => {
//   if(map.value) {
//     return
//   }
//     map.value = new AMap.Map('map2', {
//       resizeEnable: true,
//       center: [109.95315,40.621327],
//       zoom: 15
//     });
//     map.value.on('click', async (e) => {
//         clearMarker()
//         marker.value = new AMap.Marker({ //添加自定义点标记
//           position: [e.lnglat.getLng(), e.lnglat.getLat()], //基点位置
//           offset: new AMap.Pixel(-13, -30), //相对于基点的偏移位置
//           draggable: false,  //是否可拖动
//           content: `<img class="map-marker" src="//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png" />`  //自定义点标记覆盖物内容
//         });
//         marker.value.setMap(map.value);
//         formData.value.equAddr = await getCityInfo(e.lnglat.getLng(), e.lnglat.getLat())
//     });
// }

// const autoInput = (queryString, cb) => {
//   cuttentChoosePoint.value = {}
//   AMap.plugin("AMap.AutoComplete", function () {
//     var autoOptions = {
//       city: "包头",
//     };
//     var autoComplete = new AMap.AutoComplete(autoOptions);
//     autoComplete.search(queryString, function (status, result) {
//       if (status === "complete" && result.info === "OK") {
//         const list = []
//         result.tips.forEach((e,index) => {
//           list.push({
//             value: e.name,
//             lng: e.location.lng,
//             lat: e.location.lat
//           })
//         })
//         cb(list)
//       } else {
//         cb([])
//       }
//     });
//   });
// }
// const handleSelect = (item) => {
//   cuttentChoosePoint.value = item
// }
// const searchPoint = async (lnglat) => {
//   if(!lnglat.lng) return
//   clearMarker()
//   marker.value = new AMap.Marker({ //添加自定义点标记
//     position: [lnglat.lng, lnglat.lat], //基点位置
//     offset: new AMap.Pixel(-13, -30), //相对于基点的偏移位置
//     draggable: false,  //是否可拖动
//     content: `<img class="map-marker" src="//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png" />`  //自定义点标记覆盖物内容
//   });
//   marker.value.setMap(map.value);
//   formData.value.equAddr = await getCityInfo(lnglat.lng, lnglat.lat)
//   map.value.setFitView(marker.value);
// }
// const getCityInfo = (lng, lat) => {
//   return new Promise((resolve, reject) => {
//     var geocoder = new AMap.Geocoder({
//       radius: 1000,
//       extensions: "all"
//     });
//     geocoder.getAddress([lng, lat], (status, result) => {
//       if (status === 'complete' && result.info === 'OK') {
//         if (result && result.regeocode) {
//           resolve(result.regeocode.formattedAddress)
//         }
//       } else {
//         reject('error')
//       }
//     })
//   })
// }
// const clearMarker = () => {
//   if (marker.value) {
//     marker.value.setMap(null);
//     marker.value = null;
//   }
// }
/** 重置表单 */
const resetForm = () => {
  fileList.value = []
  formData.value = {
    id: undefined,
    equType: undefined,
    equId: undefined,
    problem: undefined,
    faultType: undefined,
    faultDate: undefined,
    reportUserId:undefined,
    maintenance: undefined,
    maintenanceDate: undefined,
    maintenanceUserId: undefined,
    maintenancePersonnel: undefined,
    maintenancePhone: undefined,
    suggestion: undefined,
    remark: undefined,
    deletedTime: undefined,
    fileList: []
  }
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
<style lang="scss">
.equradio {
  .el-radio.is-bordered {
    box-sizing: border-box;
    padding: 0 15px 0 9px;
    height: auto;
    margin-bottom: 10px;
    min-width: 550px;
  }
}
</style>
