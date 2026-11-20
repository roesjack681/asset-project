<template>
  <Dialog :title="dialogTitle" width="800px" v-model="dialogVisible">
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
          <el-col :span="12">
            <el-form-item label="上报人部门" prop="reportDeptId">
              <el-tree-select
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
          <el-col :span="12">
            <el-form-item label="上报人" prop="reportPersonnel">
              <el-select v-model="formData.reportUserId" placeholder="请选择上报人员">
                <el-option @click="chooseUsePerson(i)" v-for="i in usePersonlistDic" :key="i.id" :label="i.nickname" :value="i.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上报时间" prop="reportTime">
              <el-date-picker
                style="width: 100%"
                v-model="formData.reportTime"
                type="date"
                value-format="x"
                placeholder="选择上报时间"
              />
            </el-form-item>
          </el-col>
          <!--          <el-form-item label="上报人员编号" prop="reportUserId">-->
          <!--            <el-input v-model="formData.reportUserId" placeholder="请输入上报人员编号" />-->
          <!--          </el-form-item>-->
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="维修人" prop="maintenancePersonnel">
              <el-input v-model="formData.maintenancePersonnel" placeholder="请输入维修人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维护时间" prop="maintenanceDate">
              <el-date-picker
                v-model="formData.maintenanceDate"
                type="date"
                value-format="x"
                placeholder="选择维护时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维护建议" prop="suggestion">
              <el-input type="textarea" rows="2" v-model="formData.suggestion" placeholder="请输入维护建议" />
            </el-form-item>
          </el-col>
        </el-row>
<!--          <el-form-item label="上报人员部门编号" prop="reportDeptId">-->
<!--            <el-input v-model="formData.reportDeptId" placeholder="请输入上报人员部门编号" />-->
<!--          </el-form-item>-->

<!--          <el-form-item label="审核人员编号" prop="auditorBh">-->
<!--            <el-input v-model="formData.auditorBh" placeholder="请输入审核人员编号" />-->
<!--          </el-form-item>-->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="审核人" prop="auditor">
              <el-input v-model="formData.auditor" placeholder="请输入审核人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核时间" prop="auditDate">
              <el-date-picker
                v-model="formData.auditDate"
                type="date"
                value-format="x"
                placeholder="选择审核时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核意见" prop="auditOpinions">
              <el-input type="textarea" rows="2" v-model="formData.auditOpinions" placeholder="请输入审核意见" />
            </el-form-item>
          </el-col>
        </el-row>
<!--          <el-form-item label="维修人编号" prop="maintenanceUserId">-->
<!--            <el-input v-model="formData.maintenanceUserId" placeholder="请输入维修人编号" />-->
<!--          </el-form-item>-->

<!--          <el-form-item label="维护人员手机号" prop="maintenancePhone">-->
<!--            <el-input v-model="formData.maintenancePhone" placeholder="请输入维护人员手机号" />-->
<!--          </el-form-item>-->



          <el-form-item label="状态" prop="status">
<!--            <el-select v-model="queryParams.status" placeholder="请选择状态" clearable class="!w-240px">-->
<!--              <el-option-->
<!--                v-for="dict in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"-->
<!--                :key="dict.value"-->
<!--                :label="dict.label"-->
<!--                :value="dict.value"-->
<!--              />-->
<!--            </el-select>-->
            <el-radio-group v-model="formData.status">
              <el-radio v-for="dict in getStrDictOptions(DICT_TYPE.EQU_STATUS)" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="formData.remark" placeholder="请输入备注" />
          </el-form-item>
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>
          维修设备<el-icon @click="addDetailVOList" style="vertical-align: middle;margin-top: -4px;cursor:pointer;" size="18" color="#409eff"><circle-plus-filled /></el-icon>
        </div>
        <el-row :gutter="20" style="width: 100%" v-for="(i, index) in formData.detailVOList" :key="index">
          <el-col :span="11">
            <el-form-item label="设备" prop="equId">
              <el-select v-model="i.equId" placeholder="选择设备">
                <el-option
                  v-for="dict in equDicList"
                  :key="dict.id"
                  :label="dict.equName"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="描述" prop="problem">
              <el-input v-model="i.problem" placeholder="描述" />
            </el-form-item>
          </el-col>
          <el-col :span="1" >
            <el-icon @click="deleteDetailVOList(index)" style="margin-top: 5px;cursor:pointer;" size="18" color="red"><remove-filled /></el-icon>
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
import { OpsPoliceMaintenanceApi, OpsPoliceMaintenanceVO } from '@/api/traffic/opspolicemaintenance'
import {DICT_TYPE, getDeptName, getIntDictOptions, getStrDictOptions} from "@/utils/dict";
import {defaultProps, handleTree} from "@/utils/tree";
const deptList = ref<Tree[]>([]) // 树形结构
const usePersonlistDic = ref([])
/** 警用装备报修信息 表单 */
defineOptions({ name: 'OpsPoliceMaintenanceForm' })
import * as DeptApi from '@/api/system/dept'
import {OpsIssueRecordApi} from "@/api/traffic/opsissuerecord";
import {CirclePlusFilled, RemoveFilled} from "@element-plus/icons-vue";
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
      formData.value = await OpsPoliceMaintenanceApi.getOpsPoliceMaintenance(id)
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
      await OpsPoliceMaintenanceApi.createOpsPoliceMaintenance(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsPoliceMaintenanceApi.updateOpsPoliceMaintenance(data)
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
onMounted(async () => {
  // 加载部门树
  deptList.value = handleTree(await DeptApi.getSimpleDeptList())
})
</script>
