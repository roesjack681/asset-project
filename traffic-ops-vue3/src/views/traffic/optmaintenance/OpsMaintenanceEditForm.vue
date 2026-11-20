<template>
  <Dialog :title="dialogTitle" width="850" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <div>
        <div style="padding: 10px;color: #2A83EF">报修信息：</div>
        <el-form-item label="报修时间" prop="faultDate">
          <el-date-picker
            v-model="formData.faultDate"
            type="datetime"
            value-format="x"
            placeholder="选择维修时间"/>
        </el-form-item>
        <el-form-item label="报修地点" prop="problem">
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
        </el-form-item>
        <el-form-item label="存在问题" prop="problem">
          <el-input v-model="formData.problem" type="textarea" rows="4" placeholder="请输入存在问题" />
        </el-form-item>
        <el-form-item label="上报人" prop="reportUserId">
          <el-select v-model="formData.reportUserId" filterable placeholder="请选择">
            <el-option
              @click="chooseUser(item)"
              v-for="item in userList"
              :key="item.id"
              :label="item.nickname"
              :value="item.id + ''"/>
          </el-select>
        </el-form-item>
        <el-form-item label="现场照片" prop="pic">
            <UploadImgs v-model="fileList"/>
        </el-form-item>
        <el-form-item label="紧急程度" prop="reportUserId">
          <el-select v-model="formData.urgencyLevel" placeholder="请选择">
            <el-option
              @click="chooseUrgencyLevel(dict)"
              v-for="dict in urgencyLevelList"
              :key="dict.urgencyLevel"
              :label="dict.urgencyLevel"
              :value="dict.urgencyLevel"
            />
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
        <el-form-item label="实际修复难度" prop="actualRepair">
          <el-select v-model="formData.actualRepair" placeholder="请选择">
            <el-option
              v-for="dict in getStrDictOptions(DICT_TYPE.work_difficulty)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.label"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="维修人" prop="reportUserId">
          <el-select v-model="formData.maintenanceUserId" filterable placeholder="请选择">
            <el-option
              @click="chooseUser2(item)"
              v-for="item in userList2"
              :key="item.id"
              :label="item.nickname"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="故障类型" prop="reportUserId">
          <div class="input-wrapper vertical" style="width: 100%;margin-top: 10px;padding-top: 0;display: flex;flex-wrap: wrap;">
            <div class="typeitem" style="width: 18%;flex: none;margin: 0 3px;margin-bottom: 10px" @click="chooseType('信号机故障')" :class="faultType == '信号机故障'?'active':''">信号机故障</div>
            <div class="typeitem" style="width: 18%;flex: none;margin: 0 3px;margin-bottom: 10px" @click="chooseType('信号灯故障')" :class="faultType == '信号灯故障'?'active':''">信号灯故障</div>
            <div class="typeitem" style="width: 18%;flex: none;margin: 0 3px;margin-bottom: 10px" @click="chooseType('网络故障')" :class="faultType == '网络故障'?'active':''">网络故障</div>
            <div class="typeitem" style="width: 18%;flex: none;margin: 0 3px;margin-bottom: 10px" @click="chooseType('供电故障')" :class="faultType == '供电故障'?'active':''">供电故障</div>
            <div class="typeitem" style="width: 18%;flex: none;margin: 0 3px;margin-bottom: 10px" @click="chooseType('其他')" :class="faultType == '其他'?'active':''">其他</div>
          </div>
          <div class="input-wrapper vertical" v-if="faultType == '其他'" style="margin-top: 10px;padding-top: 0">
            <el-input v-model="formData.qtlx" placeholder="请输入故障类型"/>
          </div>
        </el-form-item>
      </div>
      <div>
        <div style="padding: 10px;color: #2A83EF">设备信息：</div>
        <el-form-item label="已选设备IP" prop="faultDate">
          <el-text style="font-size: 18px;color: red;" v-if="formData.equipInfoDTO"><strong>{{ formData.equipInfoDTO.ipAddr }}</strong></el-text>
        </el-form-item>
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
<!--          <div class="equlist" style="width: 100%">-->
<!--            <div style="display: flex; flex-direction: column;" class="equlist-item" :class="formData.equId == i.id?'ischoose':''" @click="chooseEue(i)" v-for="i in searchEquList" :key="i.id">-->
<!--              <div>类型：{{i.equType}}</div>-->
<!--              <div>路段名称：{{i.intersectionName}}</div>-->
<!--              <div>位置：{{i.adress}}</div>-->
<!--              <div>IP地址：{{i.ipAddr}}</div>-->
<!--              <div v-if="formData.equType=='电警'">取电位置: {{ i.powerSupplyLocation }}</div>-->
<!--            </div>-->
<!--          </div>-->
        </el-form-item>
        <el-form-item label="取电位置" prop="powerSupplyLocation">
          <el-input v-model="formData.powerSupplyLocation" placeholder="请描述杆件取电位置"/>
        </el-form-item>
      </div>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary">提 交</el-button>
      <el-button @click="getSuccess">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import { useUserStore } from '@/store/modules/user'
import moment from 'moment'
import OpsMaintenanceForm from "@/views/traffic/optmaintenance/OpsMaintenanceForm.vue";
import {OpsUrgencySettingsApi} from "@/api/traffic/opsurgencysettings";
import request from '@/config/axios'
/** 设备维保信息 表单 */
defineOptions({ name: 'OpsMaintenanceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const deptId = useUserStore().getUser.deptId
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const fileList = ref([]) // 弹窗的标题
const progress = ref([]) // 弹窗的标题
const audioFile = ref([]) // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const map = ref(null) // 表单的类型：create - 新增；update - 修改
const keyword = ref('')
const searchType = ref('1')
const cuttentChoosePoint = ref({})
const marker = ref(null)
const userList = ref([])
const userList2 = ref([])
const options = ref([])
const equConsumablesDicList = ref([])
const urgencyLevelList = ref([])
const faultType = ref('')
const loading = ref(false)
const currentChooseUrl = ref('')
const searchEquList = ref([])
const currentId = ref('')
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
  }
])
const formData = ref({
  id: undefined,
  equType: undefined,
  equId: undefined,
  problem: undefined,
  faultType: undefined,
  faultDate: undefined,
  powerSupplyLocation: undefined,
  maintenance: undefined,
  maintenanceDate: undefined,
  maintenancePersonnel: undefined,
  suggestion: undefined,
  remark: undefined,
  deletedTime: undefined,
  fileList: []
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  searchType.value = '1'
  dialogTitle.value = '编辑'
  formType.value = type
  faultType.value = ''
  audioFile.value = ''
  marker.value = null
  map.value = null

  searchEquList.value = []
  resetForm()
  getUserList()
  getUrgencyLevelList()
  equConsumablesDicList.value = getStrDictOptions(DICT_TYPE.EQU_COMSUMABLES)
  equConsumablesDicList.value.map(e => {
    e.id = e.id + ''
    e.cmSort = e.label
    e.cmNum = 0
    return e
  })
  setTimeout(() => {
    initMap()
  }, 160)
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    currentId.value = id
    try {
      formData.value = await OpsMaintenanceApi.getOpsMaintenance(id)
      formData.value.maintenanceUserId = formData.value.maintenanceUserId? Number(formData.value.maintenanceUserId):''

      if(formData.value.equType) {
        equTypeDic.forEach(item => {
          if(item.type === formData.value.equType) {
            currentChooseUrl.value = item.url
            if (formData.value.equipInfoDTO) {
              formData.value.value =  formData.value.equipInfoDTO.intersectionName
              formData.value.equType = formData.value.equipInfoDTO.equType
              chooseOneEquType(item)
            }

          }
        })
      }

      if (formData.value.equipInfoDTO) {
        formData.value.equId =  formData.value.equipInfoDTO.id
      }

      setTimeout(()  => {
       drawMarker()
      }, 1000)
      fileList.value = []
      if(formData.value.faultType != '信号机故障' && formData.value.faultType != '信号灯故障' && formData.value.faultType != '网络故障' && formData.value.faultType != '供电故障') {
        faultType.value = '其他'
        formData.value.qtlx = formData.value.faultType
      } else {
        faultType.value = formData.value.faultType
      }
      fileList.value = []
      formData.value.fileDTOList.forEach(e => {
        if(e.fileType == 1) {
          fileList.value.push(e.fileUrl)
        } else if(e.fileType == 2) {
          audioFile.value = e.fileUrl
        }
      })
    } finally {
      formLoading.value = false
    }
  } else {
    formData.value.faultDate = Date.now()
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if(!formData.value.projectedRepair) return message.error('请选择预计修复难度')
  formData.value.progressVO = []
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  formData.value.fileInfoList = []
  fileList.value.forEach(e => {
    formData.value.fileInfoList.push({
      fileType: 1,
      fileUrl: e
    })
  })
  try {
    if(faultType.value === '其他') {
      formData.value.faultType = formData.value.qtlx
    } else {
      formData.value.faultType = faultType.value
    }
    formData.value.affiliatedUnit = '1'
    const data = formData.value as unknown as OpsMaintenanceVO
    await OpsMaintenanceApi.createOpsMaintenance(data)
    message.success(t('common.updateSuccess'))
    dialogVisible.value = false
    // 发送操作成功的事件

    emit('success')
  } finally {
    formLoading.value = false
  }
}
const remoteMethod = async (query) => {
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
const chooseType2 = (type, item, index) => {
  console.log(type, item)
  item.faultType = type
}
const chooseType = (type) => {
  faultType.value = type
}
const chooseOneLk = async (item) => {
  if(formData.value.equType) {
    formData.value.equId = ''
    const params = {
      pageSize: 100,
      pageNo: 1,
      intersectionName: formData.value.value
    }
    const res = await request.get({ url: currentChooseUrl.value, params })
    searchEquList.value = res.list
  }
}
const chooseOneEquType = async (item) => {
  searchEquList.value = []
  currentChooseUrl.value = item.url
  formData.value.equId = ''
  if(!formData.value.value) return
  const params = {
    pageSize: 100,
    pageNo: 1,
    intersectionName: formData.value.value
  }
  const res = await request.get({ url: currentChooseUrl.value, params })
  searchEquList.value = res.list
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
const chooseEue = (item) => {
  formData.value.equId = item.id
  formData.value.equType = item.equType
}
const getUserList = async () => {
  // const userListData = await OpsMaintenanceApi.getUserList()
  const userListData = await OpsMaintenanceApi.queryAllUserInfoList()
  userList.value = userListData
  const userListData2 = await OpsMaintenanceApi.queryAllUserInfoList(deptId)
  userList2.value = userListData2
}
const chooseUser = (item) => {
  formData.value.reportPersonnel = item.nickname
  formData.value.reportDeptId = item.deptId
  formData.value.reportDept = item.deptName
}
const chooseUser2 = (item) => {
  // formData.value.dispatch = item.nickname
  formData.value.maintenanceUserId = item.id
  formData.value.maintenancePersonnel = item.nickname
  formData.value.maintenancePhone = item.mobile
}
const getSuccess = () => {
  dialogVisible.value = false
  emit('success')
}
const cancelForm = () => {
  console.log(useUserStore().getUser)
  ElMessageBox.prompt('请输入取消原因', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /^\S+$/,
    inputErrorMessage: '原因不能为空',
  })
    .then(async({ value }) => {
      const data = {
        id: currentId.value,
        cancelReason: value,
        cancelUserId: useUserStore().getUser.id,
        cancelPersonnel: useUserStore().getUser.nickname
      }
      await OpsMaintenanceApi.cancelRepairOrderPc(data)
      getSuccess()
    })
    .catch(() => {

    });
}
const initMap = () => {
  if(map.value) {
    return
  }
    map.value = new AMap.Map('map2', {
      resizeEnable: true,
      center: [109.95315,40.621327],
      zoom: 15
    });
    map.value.on('click', async (e) => {
        clearMarker()
        marker.value = new AMap.Marker({ //添加自定义点标记
          position: [e.lnglat.getLng(), e.lnglat.getLat()], //基点位置
          offset: new AMap.Pixel(-13, -30), //相对于基点的偏移位置
          draggable: false,  //是否可拖动
          content: `<img class="map-marker" src="//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png" />`  //自定义点标记覆盖物内容
        });
        marker.value.setMap(map.value);
        formData.value.equAddr = await getCityInfo(e.lnglat.getLng(), e.lnglat.getLat())
        formData.value.equLng = e.lnglat.getLng()
        formData.value.equLat = e.lnglat.getLat()
    });
}

const drawMarker = () => {
  if(!formData.value.equLng || !formData.value.equLat) return
  marker.value = new AMap.Marker({ //添加自定义点标记
    position: [formData.value.equLng, formData.value.equLat], //基点位置
    offset: new AMap.Pixel(-13, -30), //相对于基点的偏移位置
    draggable: false,  //是否可拖动
    content: `<img class="map-marker" src="//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png" />`  //自定义点标记覆盖物内容
  });
  marker.value.setMap(map.value);
  map.value.setFitView(marker.value);
}
const autoInput = (queryString, cb) => {
  cuttentChoosePoint.value = {}
  AMap.plugin("AMap.AutoComplete", function () {
    var autoOptions = {
      city: "包头",
    };
    var autoComplete = new AMap.AutoComplete(autoOptions);
    autoComplete.search(queryString, function (status, result) {
      if (status === "complete" && result.info === "OK") {
        const list = []
        result.tips.forEach((e,index) => {
          list.push({
            value: e.name,
            lng: e.location.lng,
            lat: e.location.lat
          })
        })
        cb(list)
      } else {
        cb([])
      }
    });
  });
}
const handleSelect = (item) => {
  cuttentChoosePoint.value = item
}
const searchPoint = async (lnglat) => {
  if(!lnglat.lng) return
  clearMarker()
  marker.value = new AMap.Marker({ //添加自定义点标记
    position: [lnglat.lng, lnglat.lat], //基点位置
    offset: new AMap.Pixel(-13, -30), //相对于基点的偏移位置
    draggable: false,  //是否可拖动
    content: `<img class="map-marker" src="//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png" />`  //自定义点标记覆盖物内容
  });
  marker.value.setMap(map.value);
  formData.value.equAddr = await getCityInfo(lnglat.lng, lnglat.lat)
  map.value.setFitView(marker.value);
}
const getCityInfo = (lng, lat) => {
  return new Promise((resolve, reject) => {
    var geocoder = new AMap.Geocoder({
      radius: 1000,
      extensions: "all"
    });
    geocoder.getAddress([lng, lat], (status, result) => {
      if (status === 'complete' && result.info === 'OK') {
        if (result && result.regeocode) {
          resolve(result.regeocode.formattedAddress)
        }
      } else {
        reject('error')
      }
    })
  })
}
const clearMarker = () => {
  if (marker.value) {
    marker.value.setMap(null);
    marker.value = null;
  }
}
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
    maintenance: undefined,
    maintenanceDate: undefined,
    maintenancePersonnel: undefined,
    suggestion: undefined,
    remark: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
const chooseUrgencyLevel = (val) => {
  const deadline = val.deadline * 60 * 60 * 1000
  console.log(Date.now())
  formData.value.deadline = Date.now() + deadline
  console.log(moment(formData.value.deadline).format('YYYY-MM-DD HH:mm:ss'))
}
const getUrgencyLevelList = async () => {
  const data = await OpsUrgencySettingsApi.getOpsUrgencySettingsPage({pageNo: 1, pageSize: 99, affiliatedUnit: '1'})
  urgencyLevelList.value = data.list
}
</script>
<style lang="scss" scoped>
#map2 {
  width: 100%;
  height: 300px;
}
::v-deep .map-marker {
  width: 30px;
  height: auto;
}
.typeitem {
  flex: 1;
  background: #F3F4F6;
  padding: 6px 10px;
  text-align: center;
  margin: 0 10px;
  border-radius: 8px;
  color: #4B5563;
  &.active {
    background: #2A83EF;
    color: #ffffff;
  }
}

.reparearea {
  border: 1px solid #dddddd;
  border-radius: 6px;
  padding: 10px;
  margin-bottom: 10px;
}

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
