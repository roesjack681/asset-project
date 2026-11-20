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
            <el-form-item label="路段" prop="roadParts">
              <el-input v-model="formData.roadParts" placeholder="请输入路段" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/sbpz-icon.png" alt=""/>设备配置</div>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="施画种类" prop="marklineSort">
              <el-input v-model="formData.marklineSort" placeholder="请输入施画种类" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量" prop="marklineNum">
              <el-input v-model="formData.marklineNum" placeholder="请输入数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="线宽" prop="lineWidth">
              <el-input v-model="formData.lineWidth" placeholder="请输入线宽" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规格" prop="specifications">
              <el-input v-model="formData.specifications" placeholder="请输入规格" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="比例" prop="proportion">
              <el-input v-model="formData.proportion" placeholder="请输入比例" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="面积" prop="area">
              <el-input v-model="formData.area" placeholder="请输入面积" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="施画单位" prop="paintingUnit">
              <el-input v-model="formData.paintingUnit" placeholder="请输入施画单位" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="施划工艺" prop="drawingProcess">
              <el-input v-model="formData.drawingProcess" placeholder="请输入施划工艺" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12">
            <el-form-item label="施划时间" prop="applicationTime">
              <el-date-picker
                v-model="formData.applicationTime"
                type="date"
                value-format="x"
                placeholder="选择施划时间"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/dwxx-icon.png" alt=""/>单位信息</div>
        <el-row :gutter="20" style="width: 100%">
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
  </Dialog>
</template>
<script setup lang="ts">
import { OpsMarklineApi, OpsMarklineVO } from '@/api/traffic/opsmarkline'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 标线 表单 */
defineOptions({ name: 'OpsMarklineForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  roadName: undefined,
  roadParts: undefined,
  marklineSort: undefined,
  marklineNum: undefined,
  lineWidth: undefined,
  specifications: undefined,
  proportion: undefined,
  area: undefined,
  paintingUnit: undefined,
  drawingProcess: undefined,
  applicationTime: undefined,
  deletedTime: undefined,
  imgsList: [],
  equVideoList: [],
  attachInfolist: []
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref

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
      formData.value = await OpsMarklineApi.getOpsMarkline(id)
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
    const data = formData.value as unknown as OpsMarklineVO
    data.attachInfoList = []
    if(formData.value.imgsList.length) {
      data.attachInfoList.push(...formData.value.imgsList)
    }
    if(formData.value.equVideoList.length) {
      data.attachInfoList.push(...formData.value.equVideoList)
    }
    if (formType.value === 'create') {
      await OpsMarklineApi.createOpsMarkline(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsMarklineApi.updateOpsMarkline(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    roadName: undefined,
    roadParts: undefined,
    marklineSort: undefined,
    marklineNum: undefined,
    lineWidth: undefined,
    specifications: undefined,
    proportion: undefined,
    area: undefined,
    paintingUnit: undefined,
    drawingProcess: undefined,
    applicationTime: undefined,
    deletedTime: undefined,
    imgsList: [],
    attachInfolist: [],
    equVideoList: []
  }
  formRef.value?.resetFields()
}
</script>
