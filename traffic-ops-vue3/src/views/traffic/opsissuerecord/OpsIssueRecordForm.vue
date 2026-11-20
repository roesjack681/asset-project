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
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>基本信息</div>
        <el-form-item label="领用部门" prop="useDeptBh">
          <el-tree-select
            :disabled="formType==='dispatch' || formType==='update'"
            v-model="formData.useDeptBh"
            :data="deptList"
            :props="defaultProps"
            filterable
            check-strictly
            @change="chooseUseDept"
            node-key="id"
            placeholder="请选择领用部门"
          />
        </el-form-item>
        <el-form-item label="领用人" prop="userName">
          <el-select
            :disabled="formType==='dispatch' || formType==='update'"
            v-model="formData.userName"
            filterable
            remote
            reserve-keyword
            placeholder="请输入姓名"
            :remote-method="remoteMethod"
            :loading="loading"
          >
            <el-option
              @click="chooseUsePerson(item)"
              v-for="item in options"
              :key="item.id"
              :label="item.userName"
              :value="item.userName"
            />
          </el-select>
<!--          <el-select :disabled="formType==='dispatch'" v-model="formData.userBh" placeholder="请选择领用人">-->
<!--            <el-option @click="chooseUsePerson(i)" v-for="i in usePersonlistDic" :key="i.id" :label="i.nickname" :value="i.id"/>-->
<!--          </el-select>-->
        </el-form-item>
        <el-form-item label="领用时间" prop="issueTime">
          <el-date-picker
            :disabled="formType==='dispatch' || formType==='update'"
            v-model="formData.issueTime"
            type="date"
            value-format="x"
            placeholder="选择领用时间"
          />
        </el-form-item>
<!--        <el-form-item label="领用数量" prop="issueNum">-->
<!--          <el-input :disabled="formType==='dispatch'" v-model="formData.issueNum" placeholder="请输入领用数量" />-->
<!--        </el-form-item>-->
      </div>
      <div class="form-box-wrapper">
        <div class="addform-title"><img src="../../../assets/imgs/jcxx-icon.png" alt=""/>
          设备信息
          <el-icon v-if="formType!=='dispatch'" @click="addIssueDetailList" style="vertical-align: middle;margin-top: -4px;cursor:pointer;" size="18" color="#409eff"><circle-plus-filled /></el-icon>
        </div>
        <el-row :gutter="20" style="width: 100%;padding-left: 20px" v-for="(i, index) in formData.issueDetailList" :key="index">
          <el-col :span="10" v-if="formType==='create'">
            <div style="display: flex;width: 100%; align-items: center">
              <span style="margin-top: -13px;font-weight: bold">{{index + 1}}.</span>
              <el-form-item style="flex: 1" label="设备" prop="psn" label-width="60px">
                <el-select filterable v-model="i.equId" placeholder="选择psn设备">
                  <el-option
                    @click="chooseOnePSN(dict.id, index)"
                    v-for="dict in equDicList"
                    :key="dict.id"
                    :label="dict.psn"
                    :value="dict.id"
                    :disabled="!!dict.disabled"
                  />
                </el-select>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="10" v-else>
              <div style="display: flex;width: 100%; align-items: center">
                <span style="margin-top: -13px;font-weight: bold">{{index + 1}}.</span>
                <el-form-item style="flex: 1" label="设备" prop="psn" label-width="60px">
                    <el-select filterable :disabled="formType==='dispatch' || formType==='update'"  v-model="i.equId" placeholder="选择psn设备">
                      <el-option
                        v-for="dict in equDicListAll"
                        :key="dict.id"
                        :label="dict.psn"
                        :value="dict.id"
                      />
                    </el-select>
                </el-form-item>
              </div>
          </el-col>
          <el-col :span="12">
            <el-form-item label="使用人" prop="userName">
              <el-select
                v-model="i.userName"
                filterable
                remote
                reserve-keyword
                placeholder="请输入姓名"
                :remote-method="remoteMethod"
                :loading="loading"
                :disabled="formType==='update'"
              >
                <el-option
                  @click="chooseUseEquPerson(i, item)"
                  v-for="item in options"
                  :key="item.id"
                  :label="item.userName"
                  :value="item.userName"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" style="width: 100%;padding-left: 20px" v-for="(i, index) in issueDetailListNew" :key="index">
          <el-col :span="10">
            <div style="display: flex;width: 100%; align-items: center">
              <span style="margin-top: -13px;font-weight: bold">{{index + 1 + formData.issueDetailList.length}}.</span>
              <el-form-item style="flex: 1" label="设备" prop="psn" label-width="60px">
                <el-select filterable v-model="i.equId" placeholder="选择psn设备">
                  <el-option
                    @click="chooseOnePSNNew(dict.id, index)"
                    v-for="dict in equDicList"
                    :key="dict.id"
                    :label="dict.psn"
                    :value="dict.id"
                  />
                </el-select>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="12">
            <el-form-item label="使用人" prop="userName">
              <el-select
                v-model="i.userName"
                filterable
                remote
                reserve-keyword
                placeholder="请输入姓名"
                :remote-method="remoteMethod"
                :loading="loading"
              >
                <el-option
                  @click="chooseUseEquPerson(i, item)"
                  v-for="item in options"
                  :key="item.id"
                  :label="item.userName"
                  :value="item.userName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="1">
            <el-icon @click="deleteIssueDetailList(index)" style="margin-top: 5px;cursor:pointer;" size="18" color="red"><remove-filled /></el-icon>
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
import { OpsIssueRecordApi, OpsIssueRecordVO } from '@/api/traffic/opsissuerecord'
import { defaultProps, handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import {CirclePlusFilled, RemoveFilled} from "@element-plus/icons-vue";
import {getDeptName} from '@/utils/dict'
import { OpsIssuePersonApi, OpsIssuePersonVO } from '@/api/traffic/opsissueperson'
import { ElMessageBox, ElMessage} from 'element-plus';
import { ElLoading } from 'element-plus'
/** 领用记录 表单 */
defineOptions({ name: 'OpsIssueRecordForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const loading = ref(false)
const formType = ref('') // 表单的类型：create - 新增；update - 修改

const deptList = ref<Tree[]>([]) // 树形结构
const options = ref([]) // 树形结构
const usePersonlistDic = ref([]) // 领用人列表
const issueDetailListNew = ref([]) // 编辑时的新增
const equDicList = ref([]) // 设备列表
const equDicListAll = ref([]) // 设备列表
const treeRef = ref() // 菜单树组件 Ref
const checkStrictly = ref(true) // 是否严格模式，即父子不关联

const formData = ref({
  id: undefined,
  useDeptBh: undefined,
  useDept: undefined,
  userName: undefined,
  userBh: undefined,
  issueTime: undefined,
  issueNum: undefined,
  deletedTime: undefined,
  issueDetailList: []
})
const formRules = reactive({
  useDeptBh: [{ required: true, message: '请选择领用部门', trigger: 'change' }],
  userBh: [{ required: true, message: '请选择领用人', trigger: 'change' }],
  issueTime: [{ required: true, message: '请选择领用时间', trigger: 'change' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = '新增'
  formType.value = type
  resetForm()

  // 修改时，设置数据
  if (id) {
    // formLoading.value = true
    const res = await OpsIssueRecordApi.queryPoliceEquipment('',1)
    equDicList.value = res
    const res2 = await OpsIssueRecordApi.queryPoliceEquipment(id,'')
    equDicListAll.value = res2
    let loadingInstance = ElLoading.service({
      lock: true,
      text: '加载中',
      background: 'rgba(0, 0, 0, 0.3)',
    })
    if(type === 'dispatch') {
      dialogTitle.value = '分配'
    } else if(type === 'update') {
      dialogTitle.value = '编辑'
    }
    try {
      OpsIssueRecordApi.getOpsIssueRecord(id).then((res) => {
        formData.value = res
        formData.value.useDeptBh = formData.value.useDeptBh - 0
        chooseUseDept(formData.value.useDeptBh, 1)
        loadingInstance.close()
      })
    } finally {
      formLoading.value = false
    }
  } else {
    const res = await OpsIssueRecordApi.queryPoliceEquipment('',1)
    equDicList.value = res
    // const res2 = await OpsIssueRecordApi.queryPoliceEquipment('','')
    // equDicListAll.value = res2
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
const chooseOnePSN = (id, index) => {
  // setTimeout(() => {
  //   var list = []
  //   formData.value.issueDetailList.map((item)=>{
  //     list.push(item.equId)
  //   })
  //   console.log(list, formData.value.issueDetailList)
  //   equDicList.value.map(e => {
  //     if (list.indexOf(e.id) > -1) {
  //       e.disabled = true
  //     } else {
  //       e.disabled = false
  //     }
  //     return e
  //   })
  // }, 150)
  // return
  for (let i = 0; i < formData.value.issueDetailList.length; i++) {
    if (formData.value.issueDetailList[i].equId === id && i !== index) {
      formData.value.issueDetailList[index].equId = ''
      return message.error('已经被选择！')
    }
  }
}
const chooseOnePSNNew = async (id, index) => {
  for (let i = 0; i < issueDetailListNew.value.length; i++) {
    if (issueDetailListNew.value[i].equId === id && i !== index) {
      issueDetailListNew.value[index].equId = ''
      return message.error('已经被选择！')
    }
  }
}
/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = {...formData.value}
    if(issueDetailListNew.value.length > 0) {
      data.issueDetailList = [...data.issueDetailList, ...issueDetailListNew.value]
    }
    if(data.issueDetailList.length === 0) return message.error('请选择设备')
    data.issueDetailList.map(e => {
      if(e.id) delete e.id
      return e
    })
    for(let i = 0; i < data.issueDetailList.length; i++) {
      if(!data.issueDetailList[i].equId) {
        return message.error('请完成填写设备信息！')
      }
    }
    data.issueNum = data.issueDetailList.length
    await OpsIssueRecordApi.createOpsIssueRecord(data)
    message.success(t('common.createSuccess'))
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
    useDeptBh: undefined,
    useDept: undefined,
    userName: undefined,
    userBh: undefined,
    issueTime: undefined,
    issueNum: undefined,
    deletedTime: undefined,
    issueDetailList: []
  }
  issueDetailListNew.value = []
  formRef.value?.resetFields()
}
const chooseUseDept = async (val, type) => {
  formData.value.useDept = getDeptName(val)
  if(formType.value === 'create') {
    formData.value.issueDetailList.map(e => {
      e.userBh = ''
      e.useDept = ''
      e.useDeptBh = ''
      e.userName = ''
      return e
    })
  }
  if(!type) formData.value.userName = ''
}
const chooseUsePerson = (val) => {
  formData.value.userBh = val.id
}
const chooseUseEquPerson = (item, dict) => {
  item.userBh = dict.id
  item.useDept = formData.value.useDept
  item.useDeptBh = formData.value.useDeptBh
}
const addIssueDetailList = () => {
  ElMessageBox.prompt('请填写新增数量', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    closeOnClickModal: false,
    inputPattern: /^[1-9]\d*$/,
    inputErrorMessage: '请填写数值格式',
  })
    .then(({ value }) => {
      for(let i = 0; i < value; i++) {
        if(formType.value === 'create') {
          formData.value.issueDetailList.push({
            equId: '',
            userBh: '',
            useDept: '',
            useDeptBh: '',
          })
        } else if (formType.value === 'update') {
          issueDetailListNew.value.push({
            equId: '',
            userBh: '',
            useDept: '',
            useDeptBh: '',
          })
        }
      }
    })
    .catch(() => {
      //
    });
}
const deleteIssueDetailList = index => {
  issueDetailListNew.value.splice(index, 1)
}
const remoteMethod = async (query) => {
  if (query !== '') {
    loading.value = true
    setTimeout(async () => {
      const data = {
        deptId: formData.value.useDeptBh,
        name: query
      }
      const {list} = await OpsIssuePersonApi.getOpsIssuePersonNoPage(data)
      loading.value = false
      options.value = list.filter((item) => {
        return item.userName.toLowerCase().indexOf(query.toLowerCase()) > -1
      })
    }, 200)
  } else {
    options.value = []
  }
}
onMounted(async() => {
  // 加载部门树
  deptList.value = handleTree(await DeptApi.getSimpleDeptList())
  // const res = await OpsIssueRecordApi.queryPoliceEquipment(1)
  // equDicList.value = res
  //
  // const res2 = await OpsIssueRecordApi.queryPoliceEquipment()
  // equDicListAll.value = res2
})
</script>
