<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="设备编号" prop="deviceCode">
        <el-input v-model="formData.deviceCode" placeholder="请输入设备编号" />
      </el-form-item>
      <el-form-item label="机器名" prop="machineName">
        <el-input v-model="formData.machineName" placeholder="请输入机器名" />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-tree-select
          v-model="formData.deptId"
          :data="deptList"
          :props="defaultProps"
          filterable
          check-strictly
          @change="chooseDept"
          node-key="id"
          placeholder="请选择部门"
        />
      </el-form-item>
      <el-form-item label="设备厂商" prop="vendor">
        <el-input v-model="formData.vendor" placeholder="请输入设备厂商" />
      </el-form-item>
      <el-form-item label="CPU型号" prop="cpuModel">
        <el-input v-model="formData.cpuModel" placeholder="请输入CPU型号" />
      </el-form-item>
      <el-form-item label="内存信息" prop="memoryInfo">
        <el-input v-model="formData.memoryInfo" placeholder="请输入内存信息" />
      </el-form-item>
      <el-form-item label="硬盘信息" prop="diskInfo">
        <el-input v-model="formData.diskInfo" placeholder="请输入硬盘信息" />
      </el-form-item>
      <el-form-item label="操作系统" prop="osVersion">
        <el-input v-model="formData.osVersion" placeholder="请输入操作系统" />
      </el-form-item>
      <el-form-item label="软件版本" prop="softwareVersion">
        <el-input v-model="formData.softwareVersion" placeholder="请输入软件版本" />
      </el-form-item>
      <el-form-item label="MAC地址" prop="macAddr">
        <el-input v-model="formData.macAddr" placeholder="请输入MAC地址" />
      </el-form-item>
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input v-model="formData.ipAddr" placeholder="请输入IP地址" />
      </el-form-item>
      <el-form-item label="设备位置" prop="location">
        <el-input v-model="formData.location" placeholder="请输入设备位置" />
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio v-for="dict in getStrDictOptions(DICT_TYPE.EQU_STATUS)" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { CollectionStationInfoApi, CollectionStationInfoVO } from '@/api/police/collectionstationinfo'
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import { getDeptName, getStrDictOptions, DICT_TYPE } from '@/utils/dict'

/** 警用设备采集站信息 表单 */
defineOptions({ name: 'CollectionStationInfoForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const deptList = ref<Tree[]>([]) // 部门树形结构
const formData = ref({
  id: undefined,
  deviceCode: undefined,
  machineName: undefined,
  deptId: undefined,
  deptName: undefined,
  vendor: undefined,
  cpuModel: undefined,
  memoryInfo: undefined,
  diskInfo: undefined,
  osVersion: undefined,
  softwareVersion: undefined,
  macAddr: undefined,
  ipAddr: undefined,
  location: undefined,
  status: undefined,
  remark: undefined
})
const formRules = reactive({
  deviceCode: [{ required: true, message: '设备编号不能为空', trigger: 'blur' }]
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
      formData.value = await CollectionStationInfoApi.getCollectionStationInfo(id)
      // 将deptId转换为数字类型，以便树形选择器正确显示
      if (formData.value.deptId) {
        formData.value.deptId = Number(formData.value.deptId)
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
    const data = formData.value as unknown as CollectionStationInfoVO
    if (formType.value === 'create') {
      await CollectionStationInfoApi.createCollectionStationInfo(data)
      message.success(t('common.createSuccess'))
    } else {
      await CollectionStationInfoApi.updateCollectionStationInfo(data)
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
    deviceCode: undefined,
    machineName: undefined,
    deptId: undefined,
    deptName: undefined,
    vendor: undefined,
    cpuModel: undefined,
    memoryInfo: undefined,
    diskInfo: undefined,
    osVersion: undefined,
    softwareVersion: undefined,
    macAddr: undefined,
    ipAddr: undefined,
    location: undefined,
    status: undefined,
    remark: undefined
  }
  formRef.value?.resetFields()
}

/** 选择部门 */
const chooseDept = (val) => {
  formData.value.deptName = getDeptName(val)
}

/** 初始化 */
onMounted(async () => {
  // 加载部门树
  deptList.value = handleTree(await DeptApi.getSimpleDeptList())
})
</script>