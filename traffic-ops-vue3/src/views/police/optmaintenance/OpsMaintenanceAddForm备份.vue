<template>
  <Dialog :title="dialogTitle" width="700" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
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
            :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="现场照片" prop="pic">
          <UploadImgs v-model="fileList"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary">提 交</el-button>
      <el-button @click="dialogVisible = false" type="primary">派 单</el-button>
      <el-button @click="getSuccess">取 消</el-button>
    </template>
    <OpsMaintenanceDispatchForm ref="formDispatchRef" @success="getSuccess"/>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import OpsMaintenanceDispatchForm from './OpsMaintenanceDispatchForm.vue'
import moment from 'moment'
import OpsMaintenanceForm from "@/views/traffic/optmaintenance/OpsMaintenanceForm.vue";
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
const map = ref(null) // 表单的类型：create - 新增；update - 修改
const keyword = ref('')
const cuttentChoosePoint = ref({})
const marker = ref(null)
const userList = ref([])
const formData = ref({
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
  marker.value = null
  map.value = null
  resetForm()
  getUserList()
  setTimeout(() => {
    initMap()
  }, 160)
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsMaintenanceApi.getOpsMaintenance(id)
      progress.value = await OpsMaintenanceApi.maintenanceProgress(id)
      console.log(getStrDictOptions(DICT_TYPE.EQU_TYPE))
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
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // formDispatchRef.value.open('create', '1881897135272448001')
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
    const data = formData.value as unknown as OpsMaintenanceVO
    if (formType.value === 'create') {
      bh = await OpsMaintenanceApi.createOpsMaintenance(data)
      // dialogVisible.value = false
      formDispatchRef.value.open('create', bh)
      message.success(t('common.createSuccess'))
    } else {
      dialogVisible.value = false
      await OpsMaintenanceApi.updateOpsMaintenance(data)
      // message.success(t('common.updateSuccess'))
    }
    // 发送操作成功的事件

    // emit('success')
  } finally {
    formLoading.value = false
  }
}
const getUserList = async () => {
  const userListData = await OpsMaintenanceApi.getUserList()
  userList.value = userListData.list
}
const chooseUser = (item) => {
  formData.value.reportPersonnel = item.nickname
  formData.value.reportDeptId = item.deptId
  formData.value.reportDept = item.deptName
}
const getSuccess = () => {
  dialogVisible.value = false
  emit('success')
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
    });
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
</style>
