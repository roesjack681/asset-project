<template v-if="showData">
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="72%">
    <ContentWrap v-infinite-scroll="loadData" :infinite-scroll-disabled="loading || noMore" infinite-scroll-immediate="false" infinite-scroll-immediate-check="true" infinite-scroll-distance="50px" >
      <el-card v-for="roadMember in showData" :key="roadMember.id" style="margin-bottom: 10px;">
        <el-form
          ref="formRef"
          label-position="left"
          v-loading="formLoading"
        >
          <el-row :gutter="30">
            <el-col :span="16" :xs="24">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="道路名称" prop="roadNroadNameame">
                    <el-text class="mx-1">{{ roadMember.roadName }}</el-text>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="路口名称" prop="intersectionName">
                    <el-text class="mx-1">{{ roadMember.intersectionName }}</el-text>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="方向" prop="adress">
                    <el-text class="mx-1">{{ roadMember.adress }}</el-text>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="序号" prop="serialNum">
                    <el-text class="mx-1">{{ roadMember.serialNum }}</el-text>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-table 
                    :data="roadMember.electricPoliceVOList"
                    border
                    style="width: 100%">
                    <el-table-column
                      prop="adress"
                      label="电警安装位置"
                      align="center" />
                    <el-table-column
                      prop="ipAddr"
                      label="IP"
                      align="center"
                      width="120" />
                    <el-table-column
                      prop="djType"
                      label="电警类型"
                      align="center"
                      width="160" >
                      <template #default="scope">
                        <el-select
                          v-model="scope.row.djType"
                          placeholder="请选择电警类型"
                          clearable
                          class="!w-135px"
                        >
                          <el-option
                            v-for="dict in getStrDictOptions(DICT_TYPE.DJ_TYPE)"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                          />
                        </el-select>
                      </template>
                      </el-table-column>
                    <el-table-column
                      prop="date"
                      align="center"
                      width="80" 
                      label="操作">
                      <template #default="scope">
                        <el-button
                          link
                          type="danger"
                          @click="handleDelete(roadMember.id, scope.$index)"
                        >
                          删除
                        </el-button>
                      </template>
                      </el-table-column>
                  </el-table>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
                    <el-form-item label="备注" prop="checkRemark" style="margin-top: 20px;">
                      <el-input type="textarea" v-model="roadMember.checkRemark"  :resize="'none'" :rows="4" placeholder="请输入备注" />
                    </el-form-item>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="8" :xs="24">
              <div class="flex-container">
                <div class="image-div">
                  <el-carousel height="360px" :interval="5000" indicator-position="outside"  arrow="hover">
                    <el-carousel-item v-for="(imgPath, index) in roadMember.srcList" :key="index">
                      <el-image :src="imgPath" fit="contain" :preview-src-list="roadMember.srcList" preview-teleported/>
                    </el-carousel-item>
                  </el-carousel>
                </div>
                <div class="bottom-div">
                  <el-button type="primary" @click="submitForm(roadMember.id)" :disabled="formLoading">确 定</el-button>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </el-card>
    </ContentWrap>
  </Dialog>
</template>
<script setup lang="ts">
import { OpsRoadApi, OpsRoadMemberVO, OpsRoadMemberSaveVO } from '@/api/traffic/opsroad'
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'

/** 道路信息 表单 */
defineOptions({ name: 'OpsRoadDataCheckForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const showData = ref<OpsRoadMemberVO[]>([])
const total = ref(0) // 列表的总页数
const loading = ref(false)
const noMore = ref(false)
const opsRoadMemberSaveForm = ref({
  id: undefined,
  electricPoliceVOList: [
    {
      id: '',
      djType: ''
    }
  ],
  checkRemark: undefined
})
const queryParams = reactive({
  pageNo: 1,
  pageSize: 3,
  id: undefined
})

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number)  => {
  dialogVisible.value = true
  dialogTitle.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    queryParams.id = id
    try {
      getCheckData()
    } finally {
      formLoading.value = false
    }
  }
}

const getCheckData = async () => {
  if (!noMore.value) {
    const data = await OpsRoadApi.getRoadMember(queryParams)
    if (showData.value) {
      showData.value = showData.value.concat(data.list)
    } else {
      showData.value = data.list
    }
    if (data.list.size < queryParams.pageSize) {
      noMore.value = true
    }
    total.value = data.total
    
    showData.value.map((item, index) => {
      const tempList = [];
      if (item.equImg) {
        tempList.push(item.equImg)
      }
      if (item.equImg2) {
        tempList.push(item.equImg2)
      }
      if (item.equImg3) {
        tempList.push(item.equImg3)
      }
      item.srcList = tempList
    })
  }
  
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const loadData = () => {
  queryParams.pageNo = queryParams.pageNo + 1
  if (queryParams.pageNo >= total.value) {
    return
  }
  getCheckData()
}

const handleDelete = (memberId, index) => {
  const memberInfo = showData.value.find(item => item.id == memberId);
  if (memberInfo.electricPoliceVOList) {
    memberInfo.electricPoliceVOList.splice(index, 1)
  }
  message.success('电警已移除，请稍后点确定保存！')
}

const submitForm = async (memberId) => {
  const memberInfo = showData.value.find(item => item.id == memberId); 
  if (memberInfo) {
    opsRoadMemberSaveForm.value.id = memberInfo.id
    opsRoadMemberSaveForm.value.checkRemark = memberInfo.checkRemark

    const electricPoliceVO = [];
    memberInfo?.electricPoliceVOList.forEach(item => {
      electricPoliceVO.push({
        'id': item.id,
        'djType': item.djType
      })
    })
    opsRoadMemberSaveForm.value.electricPoliceVOList = electricPoliceVO
  }
  
  // 提交请求
  formLoading.value = true
  try {
    const data = opsRoadMemberSaveForm.value as OpsRoadMemberSaveVO
    await OpsRoadApi.checkResult(data)
    message.success('数据核查完成')
    
    resetForm()
    // 更新数据
    getCheckData()
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  showData.value = []
  total.value = 0

  opsRoadMemberSaveForm.value = {
    id: undefined,
    electricPoliceVOList: [
      {
        id: ''
      }
    ],
    checkRemark: undefined
  }

  queryParams.pageNo = 1
  queryParams.pageSize = 3
  queryParams.id = queryParams.id
}
</script>

<style lang="scss" scoped>
/* 设置表头背景色 */
::v-deep {
  .el-table {
    thead {
      background: rgba(250, 250, 250, 1) !important;
      & th {
        background-color: rgba(250, 250, 250, 1);
      }
      & tr {
        background-color: rgba(250, 250, 250, 1);
      }
    }
  }
}

.flex-container {
  display: flex;
  flex-direction: column; /* 垂直排列子元素 */
  height: 100%; /* 示例高度 */
}

.flex-container .image-div {
  height: 100%;
  width: 100%;
  justify-content: center; /* 水平居中 */
  align-items: center;     /* 垂直居中 */
}

.image-div .el-carousel__container{
  width: 100%;
  height: 100%;
}

.image-div .el-image {
  // display: flex;
  height: 100%;
  width: 100%;
}



.bottom-div {
  display: flex;
  justify-content: right; /* 水平居中 */
  margin-top: auto; /* 将元素推到容器底部 */
  padding: 10px; /* 添加内边距 */
}
</style>