<template>
  <Dialog :title="dialogTitle" width="800px" v-model="dialogVisible" :closeOnClickModal="false">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>设备基本信息</div>
        <el-row :gutter="20">
<!--          <el-col :span="12">-->
<!--            <el-form-item label="设备名称" prop="equName">-->
<!--              <el-input v-model="formData.equName" placeholder="请输入设备名称" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :span="12">
            <el-form-item label="类型" prop="equType">
              <el-select v-model="formData.equType" placeholder="请选择类型">
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.Police_Equ_Type)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="加密卡" prop="encryptionCard">
              <el-input v-model="formData.encryptionCard" placeholder="请输入加密卡" />
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="PSN" prop="psn">
              <el-input v-model="formData.psn" placeholder="请输入PSN" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="呼号" prop="callSign">
              <el-input v-model="formData.callSign" placeholder="请输入呼号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="品牌" prop="brand">
              <el-input v-model="formData.brand" placeholder="请输入品牌" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="型号" prop="model">
              <el-autocomplete
                v-model="formData.model"
                :fetch-suggestions="(query, cb) => querySearchAsync(query, cb, 3)"
                placeholder="请输入型号"
              />
<!--              <el-input v-model="formData.model" placeholder="请输入型号" />-->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产厂家" prop="manufacturer">
              <el-autocomplete
                v-model="formData.manufacturer"
                :fetch-suggestions="(query, cb) => querySearchAsync(query, cb, 1)"
                placeholder="请输入生产厂家"
              />
<!--              <el-input v-model="formData.manufacturer" placeholder="请输入厂家" />-->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供货厂家" prop="supplier">
              <el-autocomplete
                v-model="formData.supplier"
                :fetch-suggestions="(query, cb) => querySearchAsync(query, cb, 2)"
                placeholder="请输入供货厂家"
              />
<!--              <el-input v-model="formData.supplier" placeholder="请输入供货厂家" />-->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电池" prop="battery">
              <el-input v-model="formData.battery" placeholder="请输入电池" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="版本号" prop="version">
              <el-input v-model="formData.version" placeholder="请输入版本号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" rows="2" v-model="formData.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
<!--      <div class="form-box-wrapper">-->
<!--        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>人员信息</div>-->
<!--        <el-row :gutter="20">-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="录入部门" prop="recordDeptBh">-->
<!--             <el-tree-select-->
<!--                v-model="formData.recordDeptBh"-->
<!--                :data="deptList"-->
<!--                :props="defaultProps"-->
<!--                filterable-->
<!--                check-strictly-->
<!--                @change="chooseRecordDept"-->
<!--                node-key="id"-->
<!--                placeholder="请选择部门"-->
<!--              />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="录入人" prop="recorder">-->
<!--              <el-select v-model="formData.recordBh" placeholder="请选择领用人">-->
<!--                <el-option @click="chooseRecordPerson(i)" v-for="i in recordPersonlistDic" :key="i.id" :label="i.nickname" :value="i.id"/>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="使用部门" prop="useDeptBh">-->
<!--              <el-tree-select-->
<!--                v-model="formData.useDeptBh"-->
<!--                :data="deptList"-->
<!--                :props="defaultProps"-->
<!--                filterable-->
<!--                check-strictly-->
<!--                @change="chooseUseDept"-->
<!--                node-key="id"-->
<!--                placeholder="请选择部门"-->
<!--              />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="使用人" prop="userName">-->
<!--              <el-select v-model="formData.userBh" placeholder="请选择使用人">-->
<!--                <el-option @click="chooseUsePerson(i)" v-for="i in usePersonlistDic" :key="i.id" :label="i.nickname" :value="i.id"/>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--      </div>-->
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsPoliceEquipmentApi, OpsPoliceEquipmentVO } from '@/api/traffic/opspoliceequipment'
import {DICT_TYPE, getDeptName, getStrDictOptions} from "@/utils/dict";
import {defaultProps, handleTree} from "@/utils/tree";
import * as DeptApi from '@/api/system/dept'
import {OpsIssueRecordApi} from "@/api/traffic/opsissuerecord";
import {useUserStore} from "@/store/modules/user";
const deptList = ref<Tree[]>([]) // 树形结构
const recordPersonlistDic = ref([])
const usePersonlistDic = ref([])
/** 警用装备资产信息 表单 */
defineOptions({ name: 'OpsPoliceEquipmentForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  equName: undefined,
  equType: undefined,
  encryptionCard: undefined,
  psn: undefined,
  brand: undefined,
  model: undefined,
  manufacturer: undefined,
  battery: undefined,
  remark: undefined,
  recordBh: undefined,
  recorder: undefined,
  recordDeptBh: undefined,
  recordDept: undefined,
  useDeptBh: undefined,
  useDept: undefined,
  userBh: undefined,
  userName: undefined,
  deletedTime: undefined
})
const formRules = reactive({
  equName: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  equType: [{ required: true, message: '请选择类型', trigger: 'change' }],
  encryptionCard: [{ required: true, message: '请输入加密卡', trigger: 'blur' }],
  psn: [{ required: true, message: '请输入psn', trigger: 'blur' }],
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
      formData.value = await OpsPoliceEquipmentApi.getOpsPoliceEquipment(id)
      // formData.value.recordBh = formData.value.recordBh
      // formData.value.recordDeptBh = formData.value.recordDeptBh - 0
      // formData.value.userBh = formData.value.userBh
      // formData.value.useDeptBh = formData.value.useDeptBh - 0
      // chooseUseDept(formData.value.useDeptBh)
      // chooseRecordDept(formData.value.recordDeptBh)
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
  console.log(formData.value)
  try {
    const data = formData.value as unknown as OpsPoliceEquipmentVO
    // return console.log(useUserStore().getUser)

    if (formType.value === 'create') {
      data.recordBh = useUserStore().getUser.id
      await OpsPoliceEquipmentApi.createOpsPoliceEquipment(data)
      message.success(t('common.createSuccess'))
    } else {
      await OpsPoliceEquipmentApi.updateOpsPoliceEquipment(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const querySearchAsync = async (queryString, cb, type) => {
  if(!queryString) return cb([])
  //keyType 1-生产厂家，2-供货厂家，3-型号
  const queryparams = {
    keyword: queryString,
    keyType: type
  }
  var list =  await OpsPoliceEquipmentApi.autocompletePoliceEquipInfoList(queryparams)
  var results = []
  list.forEach(e => {
    results.push({value: e})
  })
  setTimeout(() => {
    cb(results)
  }, 3000 * Math.random())
}
const chooseRecordDept = async (val) => {
  recordPersonlistDic.value = await OpsIssueRecordApi.queryAllUserInfoList(val)
  recordPersonlistDic.value.map(e => {
    e.id = e.id + ''
    return
  })
  formData.value.recordDept = getDeptName(val)
}
const chooseRecordPerson = (val) => {
  formData.value.recorder = val.nickname
}
const chooseUseDept = async (val) => {
  usePersonlistDic.value = await OpsIssueRecordApi.queryAllUserInfoList(val)
  usePersonlistDic.value.map(e => {
    e.id = e.id + ''
    return
  })
  formData.value.useDept = getDeptName(val)
}
const chooseUsePerson = (val) => {
  formData.value.userName = val.nickname
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equName: undefined,
    equType: undefined,
    encryptionCard: undefined,
    psn: undefined,
    brand: undefined,
    model: undefined,
    manufacturer: undefined,
    battery: undefined,
    remark: undefined,
    recordBh: undefined,
    recorder: undefined,
    recordDeptBh: undefined,
    recordDept: undefined,
    useDeptBh: undefined,
    useDept: undefined,
    userBh: undefined,
    userName: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
/** 初始化 **/
onMounted(async () => {
  // 加载部门树
  deptList.value = handleTree(await DeptApi.getSimpleDeptList())
})
</script>
