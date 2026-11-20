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
          <el-select v-model="formData.reportUserId" placeholder="请选择">
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
        <el-form-item label="维修人" prop="reportUserId">
          <el-select v-model="formData.maintenanceUserId" placeholder="请选择">
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
            <el-select v-model="formData.faultType" multiple placeholder="请选择">
              <el-option
                v-for="dict in getStrDictOptions('equ_situation')"
                :key="dict.value"
                :label="dict.label"
                :value="dict.label"
              />
            </el-select>
          </div>
        </el-form-item>
      </div>
      <div>
        <div style="padding: 10px;color: #2A83EF">设备信息：</div>
        <el-form-item label="设备名称" prop="reportUserId">
          <el-select
            style="width: 100%"
            v-model="formData.equName"
            filterable
            remote
            reserve-keyword
            placeholder="请输入设备名称查询"
            :remote-method="remoteMethod"
            :loading="loading">
            <el-option
              @click="chooseOneLk(item)"
              v-for="item in options"
              :key="item.id"
              :label="item.equName"
              :value="item.equName"/>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="设备类型" prop="equType">
          <el-select v-model="formData.equType" placeholder="请选择">
            <el-option
              @click="chooseOneEquType(item)"
              v-for="item in equTypeDic"
              :key="item.type"
              :label="item.type"
              :value="item.type"/>
          </el-select>
        </el-form-item> -->
        <el-form-item label="设备信息" prop="equId">
          <div class="equlist" style="width: 100%;">
            <div style="display: flex;flex-direction: column;" class="equlist-item" :class="formData.equId == i.id?'ischoose':''" @click="chooseEue(i)" v-for="i in searchEquList" :key="i.id">
              <div>设备名：{{i.equName}}</div>
              <div>类型：{{i.equType}}</div>
              <div>IP地址：{{i.ipAddr}}</div>
              <div>取电位置：{{i.powerSupplyLocation}}</div>
            </div>
          </div>
<!--          <el-select v-model="formData.equId" placeholder="请选择">-->
<!--            <el-option-->
<!--              @click="chooseOneEquType(item)"-->
<!--              v-for="item in searchEquList"-->
<!--              :key="item.id"-->
<!--              :label="item.equName"-->
<!--              :value="item.id"/>-->
<!--          </el-select>-->
        </el-form-item>
        <el-form-item label="设备图纸" prop="equId" v-if="drawing">
          <UploadFile :disabled="true" :fileType="['pdf']" :limit="1" :fileSize="100" :isOnlyPdf="true" :isShowTip="false" v-model="drawing"/>
        </el-form-item>
        <el-form-item label="取电位置" prop="powerSupplyLocation">
          <el-input v-model="formData.powerSupplyLocation" placeholder="请描述杆件取电位置"/>
        </el-form-item>
      </div>
      <div>
        <div style="padding: 10px;color: #2A83EF">维修信息：</div>
        <div class="reparearea" v-for="(x, index) in repareList" :key="x.key">
          <el-form-item label="维护情况" prop="maintenance">
            <el-input v-model="x.maintenance" type="textarea" rows="3" placeholder="请输入维护情况" />
          </el-form-item>
          <el-form-item label="处置方式" prop="reportUserId">
            <div class="input-wrapper vertical" style="width: 100%;margin-top: 10px;padding-top: 0;display: flex;flex-wrap: wrap;">
              <el-select v-model="x.disposalMethod" placeholder="请选择">
                <el-option
                  v-for="dict in getStrDictOptions('disposal_method')"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.label"
                />
              </el-select>
            </div>
          </el-form-item>
          <el-form-item label="维护类型" prop="problem">
            <el-radio-group v-model="x.isChange">
              <el-radio label="维修">维修</el-radio>
              <el-radio label="更换">更换</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="选择耗材" v-if="x.isChange === '更换'" prop="problem">
            <div style="width: 100%; display: flex; flex-wrap: wrap">
              <div v-for="(i, index) in x.equConsumablesDicList" style="width: 50%; display: flex;padding-bottom: 10px" :key="index">
                <span style="flex: 1">{{i.cmSort}}</span>
                <el-input-number style="width: 100px;margin-right: 10px;" v-model="i.cmNum" :min="0" label="描述文字"/>
              </div>
            </div>
          </el-form-item>
          <el-form-item label="维修照片" prop="pic">
            <UploadImgs v-model="x.fileList"/>
          </el-form-item>
          <el-form-item label="维修时间" prop="maintenanceDate">
            <el-date-picker
              v-model="x.maintenanceDate"
              type="datetime"
              value-format="x"
              placeholder="选择维修时间"/>
          </el-form-item>
          <el-form-item label="维修成员" prop="maintenanceUserIds">
            <el-select multiple v-model="x.maintenanceUserIds" placeholder="请选择">
              <el-option
                v-for="item in userList2"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <div style="text-align: right">
            <el-button plain type="primary" @click="addreRarelist()">新增</el-button>
            <el-button plain type="danger" v-if="repareList.length > 1" @click="deleteReparelist(index)">删除</el-button>
          </div>
        </div>
      </div>
    </el-form>
    <template #footer>
      <el-button @click="cancelForm" v-if="currentId" type="primary">取消报修单</el-button>
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
const dialogTitle = ref('补录') // 弹窗的标题
const equId = ref('')
const fileList = ref([]) // 弹窗的标题
const progress = ref([]) // 弹窗的标题
const audioFile = ref([]) // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const map = ref(null) // 表单的类型：create - 新增；update - 修改
const keyword = ref('')
const cuttentChoosePoint = ref({})
const marker = ref(null)
const userList = ref([])
const userList2 = ref([])
const options = ref([])
const options2 = ref([])
const repareList = ref([])
const equConsumablesDicList = ref([])
const urgencyLevelList = ref([])
const faultType = ref('')
const loading = ref(false)
const currentChooseUrl = ref('')
const searchEquList = ref([])
const currentId = ref('')
const drawing = ref(null)
const equTypeDic = reactive([
  {
    type: '电警',
    url: '/police/ops-electric-police/get?id='
  },
  {
    type: '杆件',
    url: '/traffic/ops-member/page'
  }
])
const formData = ref({
  id: undefined,
  affiliatedUnit: '2',
  equType: '电警',
  equName: undefined,
  equId: undefined,
  problem: undefined,
  faultType: [],
  faultDate: undefined,
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
  if(type === 'update') {
    dialogTitle.value = '新增'
  }
  formType.value = type
  faultType.value = ''
  audioFile.value = ''
  marker.value = null
  map.value = null
  searchEquList.value = null
  resetForm()
  getUserList()
  getUrgencyLevelList()
  equConsumablesDicList.value = getStrDictOptions(DICT_TYPE.POLICE_EQU_COMSUMABLES)
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
      formData.value.faultType = formData.value.faultType.split(',')
      equId.value = formData.value.equId
      if(formData.value.equType) {
        equTypeDic.forEach(item => {
          if(item.type === formData.value.equType) {
            currentChooseUrl.value = item.url
            chooseOneEquType()
          }
        })
      }
      setTimeout(()  => {
       drawMarker()
      }, 1000)
      repareList.value = await OpsMaintenanceApi.maintenanceProgress(id)
      repareList.value.map(e=> {
        e.equConsumablesDicList = JSON.parse(JSON.stringify(equConsumablesDicList.value))
        if(!e.fileList) e.fileList = []
        e.fileDTOList.forEach(x => {
          e.fileList.push(x.fileUrl)
        })
        if(e.opsMaintainCmSaveReqVOList.length) {
          e.equConsumablesDicList.map(p => {
            e.opsMaintainCmSaveReqVOList.forEach(q => {
              if(p.cmSort  === q.cmSort) {
                p.cmNum = q.cmNum
              }
            })
            return p
          })
        }
        if(!e.maintenanceUserIds) e.maintenanceUserIds = []
        if(e.opsMaintainTeamSaveReqVOList.length) {
          e.opsMaintainTeamSaveReqVOList.forEach(p => {
            if(p.maintenanceUserId) e.maintenanceUserIds.push(Number(p.maintenanceUserId))
          })
        }
      })
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
    formatrRepareList()
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  formData.value.progressVO = []
  for(let i = 0; i < repareList.value.length; i++) {
    const o = {...repareList.value[i]}
    o.fileDTOList = []
    if(!o.opsMaintainCmSaveReqVOList) o.opsMaintainCmSaveReqVOList = []
    o.fileList.forEach(e => {
      o.fileDTOList.push({
        fileType: 1,
        fileUrl: e
      })
    })
    if(o.isChange === '更换') {
      const list = []
      o.equConsumablesDicList.forEach(e => {
        if(e.cmNum > 0) {
          list.push({
              cmNum: e.cmNum,
              cmSort: e.cmSort
            })
          o.opsMaintainCmSaveReqVOList = list
        }
      })
    } else {
      o.equConsumablesDicList = []
    }
    if(i === repareList.value.length - 1) {
      o.isFinish = '1'
    } else {
      o.isFinish = '0'
    }
    if(o.maintenanceUserIds.length) {
      o.opsMaintainTeamSaveReqVOList = []
      o.maintenanceUserIds.forEach(p => {
        userList2.value.forEach(q => {
          if(p == q.id) {
            o.opsMaintainTeamSaveReqVOList.push({
              maintenanceUserId: q.id,
              maintenancePersonnel: q.nickname,
              maintenancePhone: q.mobile
            })
          }
        })
      })
    } else {
      return message.error('请选择维修成员')
    }
    formData.value.progressVO.push(o)
  }
  // return

  // 校验表单
  await formRef.value.validate()
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
    formData.value.faultType = formData.value.faultType.join(',')
    formData.value.affiliatedUnit = '2'
    const data = formData.value as unknown as OpsMaintenanceVO
    if (formType.value === 'create') {
      await OpsMaintenanceApi.createAllOpsMaintenance(data)
      message.success(t('common.createSuccess'))
    } else {
      // await OpsMaintenanceApi.updateOpsMaintenance(data)
      await OpsMaintenanceApi.createAllOpsMaintenance(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件

    emit('success')
  } finally {
    formLoading.value = false
  }
}
const remoteMethod = async (query) => {
  options2.value = []
  if (query !== '') {
    loading.value = true;
    const res = await OpsMaintenanceApi.autocompletePoliceOpsElectricPoliceList(query)
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
  if(item.equName) {
    const params = {
      pageSize: 100,
      pageNo: 1,
      equName: item.equName
    }
    const res = await request.get({ url: '/police/ops-electric-police/page', params })
    searchEquList.value = res.list
  }
}
const chooseOneEquType = async () => {
  const res = await request.get({ url: currentChooseUrl.value + equId.value })
  searchEquList.value = [res]
  chooseEue(searchEquList.value[0])
}
const chooseEue = async (item) => {
  formData.value.equId = item.id
  formData.value.equType = item.equType
  if(item.memberBh) {
    const ress = await request.get({ url: '/police/ops-member/get?id=' + item.memberBh })
    drawing.value = ress.drawing
  }
}
const getUserList = async () => {
  const userListData = await OpsMaintenanceApi.getUserList()
  userList.value = userListData.list
  const userListData2 = await OpsMaintenanceApi.getUserList(deptId)
  userList2.value = userListData2.list
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

const deleteReparelist = (index) => {
  repareList.value.splice(index, 1)
}
const addreRarelist = () => {
  repareList.value.push({
    key: Date.now(),
    faultType: [],
    maintenanceAdress: '',
    maintenance: '',
    isChange: '',
    fileList: [],
    isFinish: '',
    equConsumablesDicList: JSON.parse(JSON.stringify(equConsumablesDicList.value)),
    maintenanceUserIds: [...repareList.value[repareList.value.length-1].maintenanceUserIds],
    opsMaintainTeamSaveReqVOList: []
  })
}
/** 重置表单 */
const resetForm = () => {
  fileList.value = []
  drawing.value = null
  formData.value = {
    id: undefined,
    equType: undefined,
    equId: undefined,
    problem: undefined,
    faultType: [],
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
const formatrRepareList = () => {
  repareList.value = [{
    key: 1,
    faultType: '',
    maintenanceAdress: '',
    maintenance: '',
    isChange: '',
    fileDTOList: [],
    fileList: [],
    isFinish: '',
    equConsumablesDicList: JSON.parse(JSON.stringify(equConsumablesDicList.value)),
    maintenanceUserIds: [],
    opsMaintainTeamSaveReqVOList: []
  }]
}
const chooseUrgencyLevel = (val) => {
  const deadline = val.deadline * 60 * 60 * 1000
  console.log(Date.now())
  formData.value.deadline = Date.now() + deadline
  console.log(moment(formData.value.deadline).format('YYYY-MM-DD HH:mm:ss'))
}
const getUrgencyLevelList = async () => {
  const data = await OpsUrgencySettingsApi.getOpsUrgencySettingsPage({pageNo: 1, pageSize: 99, affiliatedUnit: '2'})
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
