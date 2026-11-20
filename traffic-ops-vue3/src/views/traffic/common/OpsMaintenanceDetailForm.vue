<template>
  <Dialog id="exportDetail" :title="dialogTitle" width="80%" v-model="dialogVisible">
    <!-- 列表 -->
    <ContentWrap>
      <el-collapse v-model="activeName" accordion @change="handleClick">
        <el-collapse-item  v-for="item in list" :key="item.id" :name="item.id">
          <template #title>
            <div>
              <span style="margin-right:10px">上报人：{{ item.reportPersonnel }}</span>
              <span style="margin-right:10px">维修人：{{ item.maintenancePersonnel }}</span>
              <span style="margin-right:10px">故障分类：{{ item.faultType }}</span>
              <span style="margin-right:10px">故障时间：{{ formattime(item.faultDate) }}</span>
              <span style="margin-right:10px">
                <el-tag v-if="item.status == 1">待派单</el-tag>
                <el-tag v-if="item.status == 4" type="danger">维修中</el-tag>
                <el-tag v-if="item.status == 5" type="success">已完成</el-tag>
                <el-tag v-if="item.status == 6" type="info">待接单</el-tag>
                <el-tag v-if="item.status == 7" type="warning">取消</el-tag>
              </span>
            </div>
          </template>

          <div>
            <el-form
              ref="formRef"
              :model="detailData"
              label-width="100px"
              v-loading="detailLoading"
            >
              <el-form-item label="报修地点" prop="problem">
                <el-text>{{ detailData.equAddr }}</el-text>
              </el-form-item>
              <el-form-item label="存在问题" prop="problem">
                <div class="yuyin" v-if="audioFile">
                  <div @click="readVideo(audioFile)" style="cursor:pointer;width: 250px; border: 1px solid #dddddd;border-radius: 6px;display: flex;padding: 5px;align-items: center;margin-bottom: 10px">
                    <img style="width: 50px" src="../../../assets/imgs/yuyin.png" alt=""/>
                    <span style="flex: 1;padding: 0 22px"><img style="width: 100%" src="../../../assets/imgs/yuyinloading.png" alt=""/></span>
                  </div>
                </div>
                <el-text>{{ detailData.problem }}</el-text>
        <!--        <el-input v-model="formData.problem" placeholder="请输入存在问题" />-->
              </el-form-item>
              <el-form-item label="报修时间" prop="faultDate">
                <el-text v-if="detailData.faultDate">{{ formattime(detailData.faultDate) }}</el-text>
                <el-text v-else>{{ formattime(detailData.createTime) }}</el-text>
              </el-form-item>
              <el-form-item label="现场照片" prop="pic">
                <el-image
                  v-for="(i, index) in fileList"
                  :key="i.fileId"
                  :src="i.fileUrl"
                  style="width: 100px; height: 100px;margin-right: 10px;"
                  fit="fill"
                  :preview-src-list="srcList(index, fileList)"
                />
                <div v-if="fileList.length === 0">暂无</div>
              </el-form-item>
              <el-form-item label="维护人员" prop="problem">
                <el-text>{{ detailData.maintenancePersonnel }}</el-text>
              </el-form-item>
              <el-form-item label="故障类型" prop="faultType">
                <el-text>{{ detailData.faultType }}</el-text>
              </el-form-item>
              <el-form-item label="维修记录" prop="pic">
                <div style="width: 100%;" v-if="progress.length">
                  <el-steps direction="vertical" finish-status="process" :active="progress.length">
                    <el-step v-for="(i, index) in progress" :key="index">
                      <template #icon>
                        <img style="width: 40px;height: auto" src="../../../assets/imgs/icon.jpg" alt=""/>
                      </template>
                      <template #title>
                        <div style="display: flex;width: 100%">
                          <span style="flex: 1">维修成员: {{getwxry(i.opsMaintainTeamSaveReqVOList)}}</span>
                          <span v-if="i.maintenanceDate" style="font-size: 13px;font-weight: normal">{{formattime(i.maintenanceDate)}}</span>
                          <span v-else style="font-size: 13px;font-weight: normal">{{formattime(i.createTime)}}</span>
                        </div>
                      </template>
                      <template #description>
                        <div style="display: flex;width: 100%;padding: 10px 10px 10px 10px;font-size: 14px">
                          <div>维修说明：</div>
                          <span style="flex: 1">{{i.maintenance}}</span>
                        </div>
                        <div style="display: flex;width: 100%;padding: 10px 10px 10px 10px;font-size: 14px">
                          <div>维修类型：</div>
                          <span style="flex: 1">{{i.isChange}}</span>
                        </div>
                        <div style="display: flex; width: 100%;padding: 10px 10px 10px 10px;font-size: 14px" v-if="i.opsMaintainCmSaveReqVOList.length">
                          <div>更换设备:</div>
                          <div>
                            <div style="padding-bottom: 5px;padding-left: 5px;" v-for="(j, jindex) in i.opsMaintainCmSaveReqVOList" :key="'100' + jindex">{{j.cmSort}} x {{j.cmNum}}</div>
                          </div>
                        </div>
                        <div style="display: flex;width: 100%;padding: 0px 10px 20px 10px;font-size: 14px">
                          <div>维修照片：</div>
                          <div v-if="i.fileDTOList.length===0">未上传</div>
                          <div>
                            <el-image
                              v-for="(x, index) in i.fileDTOList"
                              :key="index"
                              :src="x.fileUrl"
                              style="width: 100px; height: 100px;margin-right: 10px;"
                              fit="fill"
                              :preview-src-list="srcList(index, i.fileDTOList)"
                            />
                          </div>
                        </div>
                      </template>
                    </el-step>
                  </el-steps>
                </div>
                <div v-else>暂无记录</div>
              </el-form-item>
            </el-form>
          </div>
        </el-collapse-item>
      </el-collapse>

      <!-- 分页 -->
      <Pagination
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </ContentWrap>
    <template #footer>
      <div v-if="isshow">
        <el-button class="btn1" type="primary" v-if="formData.status==5" @click="handleExport">导出</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { OpsMaintenanceApi, OpsMaintenanceVO } from '@/api/traffic/optmaintenance'
import moment from 'moment'
import { number } from 'vue-types'
import { dateFormatter } from '@/utils/formatTime'
/** 设备维保信息 表单 */
defineOptions({ name: 'OpsMaintenanceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题

const loading = ref(true) // 列表的加载中
const list = ref<OpsMaintenanceVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  affiliatedUnit: 1,
  isPagination: 0,
  source: 1,
  equId: undefined,
})

const activeName = ref(number)
const fileList = ref([]) // 弹窗的标题
const progress = ref([]) // 弹窗的标题
const audioFile = ref('') // 弹窗的标题
const detailLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const detailData = ref({
  id: undefined,
  equType: undefined,
  equId: undefined,
  problem: undefined,
  faultType: undefined,
  faultDate: undefined,
  maintenance: undefined,
  maintenanceDate: undefined,
  maintenancePersonnel: undefined,
  suggestion: undefined,
  remark: undefined,
  deletedTime: undefined
})

/** 打开弹窗 */
const open = async (id: number) => {
  dialogVisible.value = true
  dialogTitle.value = '维修信息'
  queryParams.equId = id
  queryParams.pageNo = 1
  getList()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const openDetailInfo = async(id: number) => {
  detailLoading.value = true
  try {
    detailData.value = await OpsMaintenanceApi.getOpsMaintenance(id)
    progress.value = await OpsMaintenanceApi.maintenanceProgress(id)
    fileList.value = []
    detailData.value.fileDTOList.forEach(e => {
      if(e.fileType == 1) {
        fileList.value.push(e)
      } else if(e.fileType == 2) {
        audioFile.value = e.fileUrl
      }
    })
  } finally {
    detailLoading.value = false
  }
}

const handleClick = () => {
  if (activeName.value) {
    openDetailInfo(activeName.value)
  }
  
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OpsMaintenanceApi.getOpsMaintenancePage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const srcList = (index, list) => {
  let tempImgList = [];//所有图片地址
  list.forEach(e =>
    tempImgList.push(e.fileUrl)
  )
  if (index == 0) return tempImgList;
  // 调整图片顺序，把当前图片放在第一位
  let start = tempImgList.splice(index);
  let remain = tempImgList.splice(0, index);
  return start.concat(remain);//将当前图片调整成点击缩略图的那张图片
}

const getwxry = (list) => {
  var members = []
  if(list && list.length) {
    list.forEach(e => {
      members.push(e.maintenancePersonnel)
    })
    return members.join(',')
  }
  return ''
}

const formattime = (val) => {
  if(val) {
    return moment(val).format('YYYY-MM-DD HH:mm:ss')
  }
  return ''
}

</script>
