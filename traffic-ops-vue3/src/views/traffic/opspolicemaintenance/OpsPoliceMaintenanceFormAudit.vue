<template>
  <Dialog title="审核" width="800px" v-model="dialogVisible" :closeOnClickModal="false">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>维修信息</div>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="上报时间" prop="reportTime">
              <el-date-picker
                disabled
                v-model="formData.reportTime"
                type="date"
                value-format="x"
                placeholder="选择上报时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="上报人部门" prop="reportDeptId">
              <el-tree-select
                disabled
                v-model="formData.reportDeptId"
                :data="deptList"
                :props="defaultProps"
                filterable
                check-strictly
                @change="chooseUseDept"
                node-key="id"
                placeholder="请选择部门"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="上报人" prop="reportUserId">
              <el-select disabled v-model="formData.reportUserId" placeholder="请选择上报人员">
                <el-option @click="chooseUsePerson(i)" v-for="i in usePersonlistDic" :key="i.id" :label="i.nickname" :value="i.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="formType=='detail'">
            <el-form-item label="状态" prop="status">
              <el-select
                disabled
                v-model="formData.status"
                placeholder="请选择检测状态"
              >
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.MAINTENANCE_STATUS)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input disabled  type="textarea" rows="2" v-model="formData.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>
          维修设备
        </div>
        <el-row :gutter="20" style="width: 100%" v-for="(i, index) in formData.detailVOList" :key="index">
          <el-col :span="10">
            <el-form-item label="设备psn">
              <el-select
                disabled
                style="width: 100%"
                v-model="i.psn"
                filterable
                remote
                reserve-keyword
                placeholder="设备psn"
                :remote-method="(query) => remoteMethod(query, i)"
                :loading="loading">
                <el-option
                  @click="chooseEqu(i, item)"
                  v-for="item in i.options"
                  :key="item.id"
                  :label="item.psn"
                  :value="item.psn"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="描述" prop="problem">
              <el-input disabled v-model="i.problem" placeholder="描述" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>审核信息</div>
        <el-row>
          <el-col :span="24">
            <el-form-item label="审核意见" prop="auditOpinions">
              <el-radio-group v-model="formData.auditOpinions">
                <el-radio label="通过">通过</el-radio>
                <el-radio label="不通过">不通过</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="formData.auditOpinions==='通过'">
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
          </el-col>
        </el-row>
      </div>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">提交审核</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsPoliceMaintenanceApi, OpsPoliceMaintenanceVO } from '@/api/traffic/opspolicemaintenance'
import {DICT_TYPE, getDeptName, getIntDictOptions, getStrDictOptions} from "@/utils/dict";
import {defaultProps, handleTree} from "@/utils/tree";
const deptList = ref<Tree[]>([]) // 树形结构
const usePersonlistDic = ref([])
const fileList = ref([])
const equDicList = ref([])
const userList = ref([])
const loading = ref(false)
/** 警用装备报修信息 表单 */
defineOptions({ name: 'OpsPoliceMaintenanceForm' })
import * as DeptApi from '@/api/system/dept'
import {OpsIssueRecordApi} from "@/api/traffic/opsissuerecord";
import {CirclePlusFilled, RemoveFilled, Upload} from "@element-plus/icons-vue";
import {OpsMaintenanceApi} from "@/api/traffic/optmaintenance";
import {useUserStore} from "@/store/modules/user";
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  reportPersonnel: undefined,
  reportUserId: undefined,
  reportTime: undefined,
  reportDeptId: undefined,
  reportDept: undefined,
  auditorBh: undefined,
  auditDate: undefined,
  auditor: undefined,
  auditOpinions: undefined,
  maintenanceUserId: undefined,
  maintenancePersonnel: undefined,
  maintenancePhone: undefined,
  maintenanceDate: undefined,
  suggestion: undefined,
  status: undefined,
  remark: undefined,
  deletedTime: undefined,
  detailVOList: []
})
const formRules = reactive({
  reportDeptId: [{ required: true, message: '请输入选择上报人部门', trigger: 'change' }],
  reportTime: [{ required: true, message: '请选择上报时间', trigger: 'change' }],
  reportUserId: [{ required: true, message: '请选择上报人', trigger: 'change' }],
  psn: [{ required: true, message: '请输入psn', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const remoteMethod =  async (query, item) => {
  if(!formData.value.reportDeptId) return message.error('请选择上报人部门')
  if (query !== '') {
    loading.value = true;
    const data = await OpsPoliceMaintenanceApi.queryIssuedPoliceEquipment({keword: query, deptId: formData.value.reportDeptId})
    item.options = data.list
    loading.value = false;
  } else {
    item.options = [];
  }
}
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
      formData.value = await OpsPoliceMaintenanceApi.getOpsPoliceMaintenance(id)
      formData.value.reportDeptId = formData.value.reportDeptId - 0
      chooseUseDept(formData.value.reportDeptId)
      if(formData.value.maintenanceUserId) formData.value.maintenanceUserId = formData.value.maintenanceUserId - 0
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
    const data = formData.value as unknown as OpsPoliceMaintenanceVO
    if (formType.value === 'create') {
      if(formData.value.detailVOList.length === 0){
        return message.error('请选择维修设备')
      }
      await OpsPoliceMaintenanceApi.createOpsPoliceMaintenance(data)
      message.success(t('common.createSuccess'))
    } else if(formType.value === 'update') {
      if(formData.value.detailVOList && formData.value.detailVOList.length) {
        formData.value.detailVOList.map(e => {
          if(e.id) delete e.id
          return e
        })
      } else {
        return message.error('请选择维修设备')
      }
      await OpsPoliceMaintenanceApi.updateOpsPoliceMaintenance(data)
      message.success(t('common.updateSuccess'))
    } else {
      var auditData = {}
      if(formData.value.auditOpinions == '通过') {
        auditData = {
          id: formData.value.id,
          auditOpinions: formData.value.auditOpinions,
          status: '4',
          maintenanceUserId: formData.value.maintenanceUserId,
          maintenancePersonnel: formData.value.maintenancePersonnel,
          maintenancePhone: formData.value.maintenancePhone,
        }
      } else if (formData.value.auditOpinions == '不通过'){
        auditData = {
          auditOpinions: formData.value.auditOpinions,
          status: '9',
          id: formData.value.id,
        }
      }
      await OpsPoliceMaintenanceApi.auditOpsPoliceMaintenance(auditData)
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
    reportPersonnel: undefined,
    reportUserId: undefined,
    reportTime: undefined,
    reportDeptId: undefined,
    reportDept: undefined,
    auditorBh: undefined,
    auditDate: undefined,
    auditor: undefined,
    auditOpinions: undefined,
    maintenanceUserId: undefined,
    maintenancePersonnel: undefined,
    maintenancePhone: undefined,
    maintenanceDate: undefined,
    suggestion: undefined,
    status: undefined,
    remark: undefined,
    deletedTime: undefined,
    detailVOList: []
  }
  formRef.value?.resetFields()
}
const chooseUseDept = async (val) => {
  usePersonlistDic.value = await OpsIssueRecordApi.queryAllUserInfoList(val)
  usePersonlistDic.value.map(e => {
    e.id = e.id + ''
    return
  })
  formData.value.reportDept = getDeptName(val)
}
const chooseUsePerson = (val) => {
  formData.value.reportPersonnel = val.nickname
}
const addDetailVOList = () => {
  formData.value.detailVOList.push({})
}
const deleteDetailVOList = index => {
  formData.value.detailVOList.splice(index, 1)
}
const getUserList = async () => {
  const userListData = await OpsMaintenanceApi.queryAllUserInfoList()
  userList.value = userListData
}
const chooseMaintenanceUser = (item) => {
  formData.value.maintenanceUserId = item.id
  formData.value.maintenancePersonnel = item.nickname
  formData.value.maintenancePhone = item.mobile
}

const copyDetailVOList = (index, item) => {
  if(formData.value.detailVOList[index - 1].problem) {
    item.problem = formData.value.detailVOList[index - 1].problem
  }
}
const chooseEqu = (item, dict) => {
  item.equId = dict.id
}
onMounted(async () => {
  // 加载部门树
  deptList.value = handleTree(await DeptApi.getSimpleDeptList())
  const res = await OpsIssueRecordApi.unallocatedPage()
  equDicList.value = res.list
  getUserList()
})
</script>
