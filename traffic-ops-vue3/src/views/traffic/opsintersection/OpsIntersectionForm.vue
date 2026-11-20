<template>
  <Dialog :title="dialogTitle" width="720px" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="管理部门" prop="glbm">
            <el-input v-model="formData.glbm" placeholder="请输入管理部门" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="道路代码" prop="dldm">
            <el-input v-model="formData.dldm" placeholder="请输入道路代码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="行政区划" prop="xzqh">
            <el-cascader
              v-model="formData.xzqh"
              :options="areaList"
              :props="defaultProps"
              clearable
              filterable
              placeholder="请选择城市"
              style="width: 100%;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="路段代码" prop="lddm">
            <el-input v-model="formData.lddm" placeholder="请输入路段代码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="道路名称" prop="dlmc">
            <el-input v-model="formData.dlmc" placeholder="请输入道路名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="路段名称" prop="ldmc">
            <el-input v-model="formData.ldmc" placeholder="请输入路段名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="起始里程" prop="qslc">
            <el-input v-model="formData.qslc" placeholder="请输入起始里程" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="结束里程" prop="jslc">
            <el-input v-model="formData.jslc" placeholder="请输入结束里程" />
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="formData.ldmc === '钢铁大街与富强路交叉口'">
          <iframe src="https://www.720yun.com/vr/8bdjt0eaOy2" frameborder="no" width="100%" height="500" ></iframe>
        </el-col>
        <el-col :span="24" v-if="formData.ldmc === '富强路与正翔区间道交叉口'">
          <iframe src="https://www.720yun.com/vr/0c1jt0eaOk8" frameborder="no" width="100%" height="500" ></iframe>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsIntersectionApi, OpsIntersectionVO } from '@/api/traffic/opsintersection'
import {getAreaTree} from '@/api/system/area'
import { defaultProps } from '@/utils/tree'
/** 交叉口信息 表单 */
defineOptions({ name: 'OpsIntersectionForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const areaList = ref([])
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  glbm: undefined,
  dldm: undefined,
  xzqh: undefined,
  lddm: undefined,
  dlmc: undefined,
  ldmc: undefined,
  qslc: undefined,
  jslc: undefined,
  deletedTime: undefined
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
  getAreaTree2()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OpsIntersectionApi.getOpsIntersection(id)
      formData.value.xzqh = Number(formData.value.xzqh)
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
    const data = formData.value as unknown as OpsIntersectionVO
    if (formType.value === 'create') {
      await OpsIntersectionApi.createOpsIntersection(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsIntersectionApi.updateOpsIntersection(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
const getAreaTree2 = async () => {
  const data = await getAreaTree()
  areaList.value = data.filter(e => e.id == '150000')[0].children
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    glbm: undefined,
    dldm: undefined,
    xzqh: undefined,
    lddm: undefined,
    dlmc: undefined,
    ldmc: undefined,
    qslc: undefined,
    jslc: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
