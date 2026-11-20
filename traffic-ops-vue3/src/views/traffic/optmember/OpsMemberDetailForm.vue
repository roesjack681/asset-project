<template>
  <Dialog :title="dialogTitle" width="800" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      label-width="100px"
      v-loading="formLoading"
    >
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>基础信息</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="所在道路名称" prop="roadName">
              <el-text class="mx-1" >{{ formData.roadName }}</el-text>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="路口名称" prop="intersectionName">
              <el-text class="mx-1" >{{ formData.intersectionName }}</el-text>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="设备名称" prop="equName">
              <el-text class="mx-1" >{{ formData.equName }}</el-text>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="序号" prop="serialNum">
              <el-text class="mx-1" >{{ formData.serialNum }}</el-text>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安装时间" prop="installDate">
              <el-text class="mx-1" >{{ formData.installDate? dayjs(formData.installDate).format('YYYY-MM-DD HH:mm:ss') : '' }}</el-text>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建人" >
              <el-text class="mx-1" >{{ formData.creator }}</el-text>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img style="width: auto" src="../../../assets/imgs/anwz-icon.png" alt=""/>安装位置</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="安装位置" prop="adress">
              <el-text class="mx-1" >{{ formData.adress }}</el-text>
              <div style="width: 100%">
                <ChoosePoint :address="formData.adress" :isAllowResetPoint="false" :point="[formData.lng, formData.lat]" ref="choosePointRef"/>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/sbpz-icon.png" alt=""/>设备配置</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="副臂长度" prop="jibLength">
              <el-text class="mx-1" >{{ formData.jibLength }}</el-text>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="杆件类型" prop="memberType">
              <el-text class="mx-1" >{{ formData.memberType }}</el-text>
              <!-- <el-select v-model="formData.memberType" placeholder="请选择杆件类型">
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.MEMBER_TYPE)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select> -->
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="高度" prop="hight">
              <el-text class="mx-1" >{{ formData.hight }}</el-text>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="横杆长度" prop="barLength">
              <el-text class="mx-1" >{{ formData.barLength }}</el-text>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="取电位置" prop="powerSupplyLocation">
              <el-text class="mx-1" >{{ formData.powerSupplyLocation }}</el-text>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/dwxx-icon.png" alt=""/>单位信息</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="厂家" prop="manufacturer">
              <el-text class="mx-1" >{{ formData.manufacturer }}</el-text>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属单位" prop="unit">
              <el-text class="mx-1" >{{ formData.unit }}</el-text>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="管辖单位" prop="dominion">
              <el-text class="mx-1" >{{ formData.unit }}</el-text>
              <!-- <el-select v-model="formData.dominion" placeholder="请选择所属管辖单位">
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.DOMINION)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.label"
                />
              </el-select> -->
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/sbzt-icon.png" alt=""/>设备状态</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-text class="mx-1" >{{ formData.status }}</el-text>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="设备照片" prop="equImg">
              <el-image v-if="formData.equImg" :src="formData.equImg" :preview-src-list="imgList" :initial-index="0" style="width: 120px; height: 120px; margin-right: 10px;" />
              <el-image v-if="formData.equImg2" :src="formData.equImg2" :preview-src-list="imgList" :initial-index="1" style="width: 120px; height: 120px; margin-right: 10px;" />
              <el-image v-if="formData.equImg3" :src="formData.equImg3" :preview-src-list="imgList" :initial-index="2" style="width: 120px; height: 120px;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label="电警信息" prop="memberBh">
              <div class="equlist" style="width: 100%">
                <div class="equlist-item" v-for="item in formData.electricPoliceVOList" :key="item.id">
                  <div>类型：{{item.djType}}&nbsp;</div>
                  <div>路段名称：{{item.intersectionName}}&nbsp;</div>
                  <div>位置：{{item.adress}}&nbsp;</div>
                  <div>IP地址：{{item.ipAddr}}&nbsp;</div>
                </div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import dayjs from 'dayjs';
import { OpsMemberApi, OpsMemberVO } from '@/api/traffic/optmember'
import {autocompleteOpsIntersection} from "@/api/bigdata";

/** 杆件 表单 */
defineOptions({ name: 'OpsMemberDetailForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const imgList = ref([]) // 弹窗的标题
const formData = ref({
  id: undefined,
  equName: undefined,
  equNumber: undefined,
  equType: undefined,
  jibLength: undefined,
  roadName: undefined,
  memberType: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  adress: undefined,
  installDate: undefined,
  manufacturer: undefined,
  deletedTime: undefined,
  unit: undefined,
  hight: undefined,
  barLength: undefined,
  creator: undefined,
  serialNum: undefined,
  equImg: undefined,
  equImg2: undefined,
  equImg3: undefined
})

const formRef = ref() // 表单 Ref
const choosePointRef = ref() // 选择地图 Ref
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsMemberApi.getOpsMember(id)
      if (formData.value.equImg) {
        imgList.value.push(formData.value.equImg)
      }

      if (formData.value.equImg2) {
        imgList.value.push(formData.value.equImg2)
      }

      if (formData.value.equImg3) {
        imgList.value.push(formData.value.equImg3)
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

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equName: undefined,
    equNumber: undefined,
    equType: undefined,
    jibLength: undefined,
    roadName: undefined,
    memberType: undefined,
    intersectionName: undefined,
    lng: undefined,
    lat: undefined,
    adress: undefined,
    installDate: undefined,
    manufacturer: undefined,
    deletedTime: undefined,
    unit: undefined,
    hight: undefined,
    barLength: undefined,
    creator: undefined,
    serialNum: undefined,
    equImg: undefined,
    equImg2: undefined,
    equImg3: undefined
  }
  formRef.value?.resetFields()
  imgList.value = []
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
  flex-direction: column;
}
</style>
