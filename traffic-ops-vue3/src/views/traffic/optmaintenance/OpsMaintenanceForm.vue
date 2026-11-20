<template>
  <Dialog id="exportDetail" :title="dialogTitle" width="700" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="报修地点" prop="problem">
        <el-text>{{ formData.equAddr }}</el-text>
      </el-form-item>
<!--      <el-form-item label="设备类型" prop="equType">-->
<!--        <el-select v-model="formData.equType" placeholder="请选择设备类型">-->
<!--          <el-option-->
<!--            v-for="dict in getStrDictOptions(DICT_TYPE.EQU_TYPE)"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item label="存在问题" prop="problem">
        <div class="yuyin" v-if="audioFile">
          <div @click="readVideo(audioFile)" style="cursor:pointer;width: 250px; border: 1px solid #dddddd;border-radius: 6px;display: flex;padding: 5px;align-items: center;margin-bottom: 10px">
            <img style="width: 50px" src="../../../assets/imgs/yuyin.png" alt=""/>
            <span style="flex: 1;padding: 0 22px"><img style="width: 100%" src="../../../assets/imgs/yuyinloading.png" alt=""/></span>
          </div>
        </div>
        <el-text>{{ formData.problem }}</el-text>
<!--        <el-input v-model="formData.problem" placeholder="请输入存在问题" />-->
      </el-form-item>
      <!-- <el-form-item label="报修时间" prop="faultDate">
        <el-date-picker
          v-if="formData.faultDate"
          v-model="formData.faultDate"
          type="datetime"
          value-format="x"
          placeholder="选择故障时间"
        />
        <el-date-picker
          v-else
          v-model="formData.createTime"
          type="datetime"
          value-format="x"
          placeholder="选择故障时间"
        />
      </el-form-item> -->
      <!-- <el-form-item label="现场照片" prop="pic">
        <el-image
          v-for="(i, index) in fileList"
          :key="i.fileId"
          :src="i.fileUrl"
          style="width: 100px; height: 100px;margin-right: 10px;"
          fit="fill"
          :preview-src-list="srcList(index, fileList)"
        />
        <div v-if="fileList.length === 0">暂无</div>
      </el-form-item> -->
      <el-form-item label="维护人员" prop="problem">
        <el-text>{{ formData.maintenancePersonnel }}</el-text>
      </el-form-item>
      <el-form-item label="故障类型" prop="faultType">
        <el-text>{{ formData.faultType }}</el-text>
      </el-form-item>
      <el-form-item label="设备类型" prop="equType">
        <el-text>{{ formData.equType }}</el-text>
      </el-form-item>
      <el-form-item v-if="formData.equipInfoDTO && formData.equipInfoDTO.ipAddr" label="设备IP" prop="ipAddr">
        <el-text>{{ formData.equipInfoDTO.ipAddr }}</el-text>
      </el-form-item>
      <el-form-item v-if="formData.equipInfoDTO && formData.equipInfoDTO.adress" label="安装位置" prop="adress">
        <el-text>{{ formData.equipInfoDTO.adress }}</el-text>
      </el-form-item>
      <el-form-item v-if="formData.djType" label="电警类型" prop="djType">
        <el-text>{{ formData.djType }}</el-text>
      </el-form-item>
      <el-form-item v-if="formData.repairCnt" label="维修次数" prop="repairCnt">
        <el-text>{{ formData.repairCnt }}</el-text>
      </el-form-item>
      <el-form-item v-if="formData.onlineRate" label="健康率" prop="onlineRate">
        <el-text>{{ formData.onlineRate }}</el-text>
      </el-form-item>
      <el-form-item v-if="formData.projectedRepair" label="预计修复难度" prop="projectedRepair">
        <el-text>{{ formData.projectedRepair }}</el-text>
      </el-form-item>
      <el-form-item v-if="formData.actualRepair" label="实际修复难度" prop="actualRepair">
        <el-text>{{ formData.actualRepair }}</el-text>
      </el-form-item>
      <el-form-item v-if="formData.status" label="状态" prop="status">
        <el-tag v-if="formData.status == 1" type="info">待派单</el-tag>
        <el-tag v-if="formData.status == 6" type="info">待接单</el-tag>
        <el-tag v-if="formData.status == 4" type="danger">维修中</el-tag>
        <el-tag v-if="formData.status == 8" type="success">待确认</el-tag>
        <el-tag v-if="formData.status == 9" type="warning">驳回</el-tag>
        <el-tag v-if="formData.status == 5">已完成</el-tag>
        <el-tag v-if="formData.status == 7" type="danger">取消</el-tag>
      </el-form-item>
      <el-form-item v-if="formData.rejectReason" label="驳回原因" prop="rejectReason">
        <el-text>{{ formData.rejectReason }}</el-text>
      </el-form-item>
      <el-form-item v-if="formData.finishTime" label="完成时间" prop="finishTime">
        <el-text>{{ moment(formData.finishTime).format('YYYY-MM-DD HH:mm:ss') }}</el-text>&nbsp;&nbsp;<el-tag>用时：{{ formData.workTime }}小时</el-tag>
      </el-form-item>
      <el-form-item label="维修记录" prop="pic">
        <div style="width: 100%;" v-if="progressRecord.length">
          <el-steps direction="vertical" finish-status="progressRecord" :active="progressRecord.length">
            <el-step v-for="(i, index) in progressRecord" :key="index">
              <template #icon>
                <img style="width: 30px;height: auto" v-if="i.type == '报修人'" src="../../../assets/imgs/baozi.png" alt=""/>
                <img style="width: 30px;height: auto" v-if="i.type == '派单人'" src="../../../assets/imgs/paizi.png" alt=""/>
                <img style="width: 28px;height: auto" v-if="i.maintenance == '已接单'" src="../../../assets/imgs/jiezi.png" alt=""/>
                <img style="width: 36px;height: auto" v-if="index >= 3 && progressRecord.length >= 4" src="../../../assets/imgs/icon.jpg" alt=""/>
              </template>
              <template #title>
                <div style="display: flex;width: 100%;justify-content: space-between;">
                  <h4>{{ i.maintenancePersonnel || '维护人员' }}
                    <span style="color: #999999;font-size: 12px;" v-if="i.type || i.phone">({{i.type || i.phone}})</span>
                  </h4>
                  <h4>
                    <span style="float: right;color: #999999;font-size: 12px;font-weight: lighter">
                      {{i.maintenanceDate?moment(i.maintenanceDate).format('YYYY-MM-DD HH:mm:ss') :''}}
                    </span>
                  </h4>

                </div>
              </template>
              <template #description>
                <div style="padding-bottom: 10px" v-if="i.maintenanceAdress">
                  <img style="width: 10px;height: auto;vertical-align: middle;margin-top: -2px;" src="../../../assets/imgs/anwz-icon.png" alt=""/>
                  {{i.maintenanceAdress}}
                </div>
                <div class="info">
                  <div>
                    {{i.maintenance}}
                  </div>
                  <div style="padding: 10px 0px" v-if="i.opsMaintainCmSaveReqVOList.length">
                    <div>更换设备:</div>
                    <div v-for="j in i.opsMaintainCmSaveReqVOList" :key="j">{{j.cmSort}} x {{j.cmNum}}</div>
                  </div>
                  <div v-if="formData.equType==='护栏'&& i.opsMaintenanceGuardrailVO">
                    <div style="margin-top: 10px">维修类型：{{i.opsMaintenanceGuardrailVO.maintenanceType}}</div>
                    <div v-if="i.opsMaintenanceGuardrailVO.maintenanceType==='更换'">
                      <div style="margin-top: 10px">更换类型：{{i.opsMaintenanceGuardrailVO.changeType}}</div>
                      <div style="margin-top: 10px">保险类型：{{i.opsMaintenanceGuardrailVO.claimType}}</div>
                      <div style="margin-top: 10px">
                        更换前产品信息：<br/>{{i.opsMaintenanceGuardrailVO.moditfyBeProductName}}-{{i.opsMaintenanceGuardrailVO.moditfyBePieces}}片
                      </div>
                      <div style="margin-top: 10px">
                        更换后产品信息：<br/>{{i.opsMaintenanceGuardrailVO.moditfyAfProductName}}-{{i.opsMaintenanceGuardrailVO.moditfyAfPieces}}片
                      </div>
                      <div style="margin-top: 10px;color: #3674a8" v-if="i.opsMaintenanceGuardrailVO.insuranceInfoVO.vehicleNum">
                        保险信息：<br/>
                        车牌号：{{i.opsMaintenanceGuardrailVO.insuranceInfoVO.vehicleNum}} <br/>
                        保险公司名称：{{i.opsMaintenanceGuardrailVO.insuranceInfoVO.insuranceCompany}}<br/>
                        保险单号：{{i.opsMaintenanceGuardrailVO.insuranceInfoVO.insurancePolicy}}<br/>
                        赔付产品：{{i.opsMaintenanceGuardrailVO.insuranceInfoVO.productName}}<br/>
                        赔付数量：{{i.opsMaintenanceGuardrailVO.insuranceInfoVO.pieces}}片<br/>
                        赔付金额：{{i.opsMaintenanceGuardrailVO.insuranceInfoVO.payMoney}}元
                      </div>
                    </div>
                    <div v-if="i.opsMaintenanceGuardrailVO.maintenanceType==='拆除'">
                      <div v-if="i.opsMaintenanceGuardrailVO.isHeadModitfy=='是'">拆除类型：<span>拆除首段</span></div>
                      <div v-else-if="i.opsMaintenanceGuardrailVO.isTailModitfy=='是'">拆除类型：<span>拆除尾段</span></div>
                      <div v-else>拆除类型：<span>拆除中段</span></div>
                      <div style="margin-top: 10px" v-if="i.opsMaintenanceGuardrailVO.guardrailGapVOList.length">
                        <div>新增开口信息：</div>
                        <div style="background: #eeebeb;padding: 5px;margin: 5px 0;border-radius: 3px" :key="indexxx" v-for="(x, indexxx) in i.opsMaintenanceGuardrailVO.guardrailGapVOList">
                          <div>新增开口{{index + 1}}</div>
                          <div>
                            开口名称：{{x.gapName}} <br/>
                            开口类型：{{x.gapType}} <br/>
                            开口位置：{{x.lng}}, {{x.lat}} <img @click="showMap(i, x)" style="height: 17px" src="~@/assets/imgs/anwz-icon.png" alt=""/> <br/>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div style="margin-top: 10px">{{i.opsMaintenanceGuardrailVO.maintenanceType}}前后照片：</div>
                    <div style="margin-top: 10px">
                      <el-image
                        v-for="(x, index1) in i.opsMaintenanceGuardrailVO.attachInfoList"
                        :key="index1"
                        width="73px"
                        height="73px"
                        fit="fill"
                        style="width: 100px; height: 100px;margin-right: 10px;"
                        :src="x.filePath"
                        :preview-src-list="srcList2(index1, i.opsMaintenanceGuardrailVO.attachInfoList)"
                      />
                    </div>
                  </div>
                </div>
                <div style="margin-top: 10px">
                  <el-image
                    v-for="(x, index1) in i.fileDTOList"
                    :key="index1"
                    width="73px"
                    height="73px"
                    fit="fill"
                    style="width: 100px; height: 100px;margin-right: 10px;"
                    :src="x.fileUrl"
                    :preview-src-list="srcList(index1, i.fileDTOList)"
                  />
                </div>
                <div style="padding: 10px 0px" v-if="i.opsMaintainTeamSaveReqVOList && i.opsMaintainTeamSaveReqVOList.length">
                  <div>维修成员: <span v-for="(p, pindex) in i.opsMaintainTeamSaveReqVOList" :key="pindex">{{p.maintenancePersonnel}}<span v-if="pindex != i.opsMaintainTeamSaveReqVOList.length - 1">,</span></span></div>
                </div>
              </template>
            </el-step>
          </el-steps>
        </div>
        <div v-else>暂无记录</div>
      </el-form-item>
    </el-form>
    <template #footer>
      <div v-if="isshow">
        <el-button @click="handleConfirm(formData.id)" v-if="formData.status == '8'" type="primary">确认工单</el-button>
        <el-button class="btn1" type="primary" v-if="formData.status==5" @click="handleExport">导出</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </template>
    <positiionKakou :startPosition="startPosition" :endPosition="endPosition" :point="position" @success="positionSuccess" ref="kakouRef"/>
    <OpsMaintenanceConfirmForm ref="confirmFormRef"  @success="confirmok"/>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import moment from 'moment'
import {exportPDF} from "@/views/assetAccount/pdf";
import {htmlToPDF} from '@/views/assetAccount/exportToPdf'
import { formatTime } from '@/utils'
import {OpsGuardrailProductApi} from "@/api/traffic/opsguardrailproduct";
import positiionKakou from '@/views/traffic/optguardrail/positionKakou.vue'
import OpsMaintenanceConfirmForm from "@/views/traffic/common/OpsMaintenanceConfirmForm.vue";
/** 设备维保信息 表单 */
defineOptions({ name: 'OpsMaintenanceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const fileList = ref([]) // 弹窗的标题
const progress = ref([]) // 弹窗的标题
const progressRecord = ref([])
const opsGuardrailProduct = ref([])
const startPosition = ref([])
const endPosition = ref([])
const position = ref([])
const audioFile = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const isshow = ref(true)
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const kakouRef = ref() // 表单 Ref
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
  finishTime:undefined,
  deletedTime: undefined
})
const formRules = reactive({
})

const confirmFormRef = ref()
const handleConfirm = async (id: number) => {
  confirmFormRef.value.open(id)
}
const confirmok = async () => {
  dialogVisible.value = false
  emit('success')
}
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  audioFile.value = ''
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsMaintenanceApi.getOpsMaintenance(id)
      progress.value = await OpsMaintenanceApi.maintenanceProgress(id)
      const data = await OpsGuardrailProductApi.getOpsGuardrailProductPage({pageNo:1,pageSize: 99})
      opsGuardrailProduct.value = data.list
      opsGuardrailProduct.value.map(e => {
        e.label = `型号:${e.model}长:${e.length}高:${e.hight}`
        e.id = e.id + ''
        return e
      })
      progressRecord.value = []
      progressRecord.value.unshift({
        type: formData.value.maintenancePhone,
        maintenance: formData.value.receiveDate?'已接单':'待接单',
        maintenanceAdress: formData.value.receiveAdress,
        maintenanceDate: formData.value.receiveDate,
        maintenanceId:'',
        maintenanceLat:'',
        maintenanceLng:'',
        maintenancePersonnel: formData.value.maintenancePersonnel,
        maintenanceUserId:formData.value.maintenanceUserId,
        opsMaintainCmSaveReqVOList: []
      })
      progressRecord.value.unshift({
        type: '派单人',
        maintenance: '已派单',
        maintenanceAdress: formData.value.dispatchAdress,
        maintenanceDate: formData.value.dispatchTime,
        maintenanceId:'',
        maintenanceLat:'',
        maintenanceLng:'',
        maintenancePersonnel: formData.value.dispatch,
        maintenanceUserId: formData.value.dispatchUserId,
        opsMaintainCmSaveReqVOList: []
      })
      progressRecord.value.unshift({
        type: '报修人',
        maintenance: formData.value.problem,
        maintenanceAdress: formData.value.equAddr,
        maintenanceDate: formData.value.reportTime,
        fileDTOList: formData.value.fileDTOList,
        maintenanceId:'',
        maintenanceLat:'',
        maintenanceLng:'',
        maintenancePersonnel: formData.value.reportPersonnel,
        maintenanceUserId: formData.value.reportUserId,
        opsMaintainCmSaveReqVOList: []
      })

      progressRecord.value.push(...progress.value)

      console.log(progressRecord.value)
      fileList.value = []
      formData.value.fileDTOList.forEach(e => {
        if(e.fileType == 1) {
          fileList.value.push(e)
        } else if(e.fileType == 2) {
          audioFile.value = e.fileUrl
        }
      })


      if(formData.value.equType === '护栏') {
        progressRecord.value.map(e => {
          if(e.opsMaintenanceGuardrailVO && e.opsMaintenanceGuardrailVO.moditfyBeProduct) {
            const product = opsGuardrailProduct.value.filter(x => x.id === e.opsMaintenanceGuardrailVO.moditfyBeProduct)
            e.opsMaintenanceGuardrailVO.moditfyBeProductName = product[0].label
          }
          if(e.opsMaintenanceGuardrailVO && e.opsMaintenanceGuardrailVO.moditfyAfProduct) {
            const product = opsGuardrailProduct.value.filter(x => x.id === e.opsMaintenanceGuardrailVO.moditfyAfProduct)
            e.opsMaintenanceGuardrailVO.moditfyAfProductName = product[0].label
          }
          if(e.opsMaintenanceGuardrailVO && e.opsMaintenanceGuardrailVO.insuranceInfoVO && e.opsMaintenanceGuardrailVO.insuranceInfoVO.vehicleNum) {
            const product = opsGuardrailProduct.value.filter(x => x.id === e.opsMaintenanceGuardrailVO.insuranceInfoVO.productId)
            e.opsMaintenanceGuardrailVO.insuranceInfoVO.productName = product[0].label
          }
          return e
        })
      }
    } finally {
      formLoading.value = false
    }
  }
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
    const data = formData.value as unknown as OpsMaintenanceVO
    if (formType.value === 'create') {
      await OpsMaintenanceApi.createOpsMaintenance(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsMaintenanceApi.updateOpsMaintenance(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const srcList = (index, list) => {
  let tempImgList = [];//所有图片地址
  list.forEach(e =>
    tempImgList.push(e.fileUrl)
  )
  if (index == 0) return tempImgList;
  // 调整图片顺序，把当前图片放在第一位
  let start = tempImgList.splice(index);
  let remain = tempImgList.splice(0, index);
  return start.concat(remain);//将当前图片调整成点击缩略图的那张图片
}

const srcList2 = (index, list) => {
  let tempImgList = [];//所有图片地址
  list.forEach(e =>
    tempImgList.push(e.filePath)
  )
  if (index == 0) return tempImgList;
  // 调整图片顺序，把当前图片放在第一位
  let start = tempImgList.splice(index);
  let remain = tempImgList.splice(0, index);
  return start.concat(remain);//将当前图片调整成点击缩略图的那张图片
}
const readVideo = (fileurl) => {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', fileurl);
  xhr.responseType = 'blob';
  xhr.onload = function() {
    readBlob(this.response, function(data) {
      playAmrArray(data);
    });
  };
  xhr.onerror = function() {
    alert('Failed to fetch ' + url);
  };
  xhr.send();
  window.AudioContext = window.AudioContext || window.webkitAudioContext || window.mozAudioContext || window.msAudioContext;
}
const readBlob = (blob, callback) => {
  var reader = new FileReader();
  reader.onload = function(e) {
    var data = new Uint8Array(e.target.result);
    callback(data);
  };
  reader.readAsArrayBuffer(blob);
}
const playAmrArray = (array) => {
  var samples = AMR.decode(array);
  if(!samples) {
    alert('Failed to decode!');
    return;
  }
  playPcm(samples);
}
const playPcm = (samples) => {
  try {
    var ctx = new AudioContext();
  } catch(e) {
    alert('您的浏览器暂不支持播放录音');
    return;
  }
  var src = ctx.createBufferSource();
  var buffer = ctx.createBuffer(1, samples.length, 8000);
  if(buffer.copyToChannel) {
    buffer.copyToChannel(samples, 0, 0);
  } else {
    var channelBuffer = buffer.getChannelData(0);
    channelBuffer.set(samples);
  }
  src.buffer = buffer;
  src.connect(ctx.destination);
  src.start();
}
const formattime = (val) => {
  if(val) {
    return moment(val).format('YYYY-MM-DD HH:mm:ss')
  }
  return ''
}

const getwxry = (list) => {
  var members = []
  if(list && list.length) {
    list.forEach(e => {
      members.push(e.maintenancePersonnel)
    })
    return members.join(',')
  }
  return ''
}
const handleExport = () => {
  isshow.value = false
  setTimeout(() => {
    htmlToPDF('exportDetail')
  }, 160)
  setTimeout(() => {
    isshow.value = true
  }, 1000)
}
/** 重置表单 */
const resetForm = () => {
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
const showMap = (i, x) => {
  console.log(x, 0)
  if(i.opsMaintenanceGuardrailVO.startLat) {
    startPosition.value = [i.opsMaintenanceGuardrailVO.startLng, i.opsMaintenanceGuardrailVO.startLat]
  }
  if(i.opsMaintenanceGuardrailVO.endLat) {
    endPosition.value = [i.opsMaintenanceGuardrailVO.endLng, i.opsMaintenanceGuardrailVO.endLat]
  }
  if(x.lng) {
    position.value = [x.lng, x.lat]
  } else {
    position.value = []
  }
  setTimeout(() => {
    kakouRef.value.open()
  }, 160)
}
</script>
