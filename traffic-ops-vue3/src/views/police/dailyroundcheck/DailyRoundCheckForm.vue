<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="巡检设备" prop="equId">
        <el-autocomplete
          size="large"
          v-model="queryParams.ipAddr"
          :fetch-suggestions="querySearch"
          @select="handleSelect"
          placeholder="请输入内容"
          :trigger-on-focus="false"
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="equStatus">
        <el-select
          v-model="formData.equStatus"
          placeholder="请选择设备状态"
          clearable
          class="!w-240px" 
        >
          <el-option 
            v-for="dict in getStrDictOptions(DICT_TYPE.EQU_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="巡检项" prop="checkItem">
        <el-input type="textarea" v-model="formData.checkItem" placeholder="请输入巡检项" />
      </el-form-item>
      <el-form-item label="问题" prop="question">
        <el-input type="textarea" v-model="formData.question" placeholder="请输入问题" />
      </el-form-item>
      <el-form-item label="解决方案" prop="solution">
        <el-input type="textarea" v-model="formData.solution" placeholder="请输入解决方案" />
      </el-form-item>
      <el-form-item label="巡检时间" prop="checkDate">
        <el-date-picker
          v-model="formData.checkDate"
          type="datetime"
          value-format="x"
          placeholder="选择巡检时间"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { DailyRoundCheckApi, DailyRoundCheckVO } from '@/api/police/dailyroundcheck'
import { OpsElectricPoliceApi, OpsElectricPoliceVO } from '@/api/police/opselectricpolice'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import { useUserStore } from '@/store/modules/user'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 日常巡检信息 表单 */
defineOptions({ name: 'DailyRoundCheckForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  equId: undefined,
  affiliatedUnit: '2',
  equStatus: undefined,
  checkItem: undefined,
  question: undefined,
  solution: undefined,
  checkPerson: undefined,
  checkType: undefined,
  checkPersonId: undefined,
  checkDate: undefined,
  deletedTime: undefined
})
const formRules = reactive({
  equId: [{ required: true, message: '请选择设备信息', trigger: 'blur' }],
  question: [{ required: true, message: '问题描述不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

const restaurants = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 20,
  ipAddr: undefined
})
const querySearch = async (queryString, cb) => {
  if(queryString) {
    const data1 = await OpsElectricPoliceApi.getOpsElectricPolicePage(queryParams)
    const data = data1.list
    data.map(e => {
      e.value =   e.ipAddr + ' [' + e.equName + ']'
      return
    })
    restaurants.value = data
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
  console.info(formData.value)
}


/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  queryParams.ipAddr = ''
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await DailyRoundCheckApi.getDailyRoundCheck(id)
      if (formData.value.electricPoliceRespVO) {
        queryParams.ipAddr = formData.value.electricPoliceRespVO.ipAddr + ' [' + formData.value.electricPoliceRespVO.equName+ ']'
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
    const data = formData.value as unknown as DailyRoundCheckVO
    if (formType.value === 'create') {
      await DailyRoundCheckApi.createDailyRoundCheck(data)

      ElMessageBox.confirm('是否下维修单？', '下单', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          affiliatedUnit: '2',
          equType: '电警',
          equId: formData.value.equId,
          problem: formData.value.question,
          faultDate: formData.value.checkDate,
          reportTime: formData.value.checkDate,
          reportUserId: useUserStore().getUser.id,
          reportPersonnel: useUserStore().getUser.nickname
        } as unknown as OpsMaintenanceVO
        OpsMaintenanceApi.createAllOpsMaintenance(data)
        message.success(t('common.createSuccess'))
      }).catch(()=> {
        message.success(t('common.createSuccess'))
      })

      
      
    } else {
      await DailyRoundCheckApi.updateDailyRoundCheck(data)
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
    equId: undefined,
    affiliatedUnit: undefined,
    equStatus: undefined,
    checkItem: undefined,
    question: undefined,
    solution: undefined,
    checkPerson: undefined,
    checkType: undefined,
    checkPersonId: undefined,
    checkDate: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>