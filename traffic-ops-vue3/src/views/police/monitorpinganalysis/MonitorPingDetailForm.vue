<template>
  <Dialog id="exportDetail" :title="dialogTitle" width="80%" v-model="dialogVisible">
    <ContentWrap>
      <!-- 搜索工作栏 -->
      <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="84px"
      >
        <el-form-item label="检查时间" prop="faultDate">
          <el-date-picker
            v-model="queryDate"
            value-format="YYYY-MM-DD HH:mm:ss"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
            @change="handleDateChange"
            class="!w-220px"
          />
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        </el-form-item>
      </el-form>
    </ContentWrap>

    <!-- 列表 -->
    <ContentWrap>
      <el-card class="box-card" v-for="item in list" :key="item.monitorDay" style="margin-bottom: 5px;">
        <template #header>
          <div>
            <el-badge :value="item.dayMonitorCnt" class="item">
              <el-tag style="font-size: 18px;font-weight: bold;">检测时间：{{ item.monitorDay }}</el-tag>
            </el-badge>
            <span style="padding-right: 14px;"></span>
            <el-tag style="font-size: 18px;font-weight: bold;" type="warning">IP地址：{{ item.ipAddr }}</el-tag>
          </div>
        </template>
        <div v-for="result in item.hourStatusResultList" :key="result.monitorHour " style="display: inline-block;margin-right: 10px;">
          <div style="display: flex;align-items: center;margin-bottom: 10px ;margin-right: 10px;">
            <el-tooltip class="item" effect="dark" :content="result.pingCnt + '次ping丢包率：' + result.lossRate + '%'" placement="bottom">
              <div v-if="result.status=='正常'" style="background-color: green;width: 108px;height: 32px; display: flex; align-items: center; justify-content: center;color:white;">{{result.source==1?'自动':'手动'}} | {{ result.monitorHour }}</div>
              <div v-if="result.status!='正常'" style="background-color: grey;width: 108px;height: 32px; display: flex; align-items: center; justify-content: center;color:white;">{{result.source==1?'自动':'手动'}} | {{ result.monitorHour }}</div>
            </el-tooltip>
          </div>
        </div>
      </el-card>

      <!-- 分页 -->
      <Pagination
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </ContentWrap>
    <template #footer>
      <div>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { MonitorPingAnalysisApi, PoliceMonitorPingDetailVO } from '@/api/police/monitorpinganalysis'

/** 监测电警设备统计 列表 */
defineOptions({ name: 'MonitorPingDetailForm' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const queryDate = ref([])
const loading = ref(true) // 列表的加载中
const list = ref<PoliceMonitorPingDetailVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equId: undefined,
  startDate: undefined,
  endDate: undefined,
})
const queryFormRef = ref() // 搜索的表单

/** 打开弹窗 */
const open = async (equName: string, equId: string) => {
  dialogVisible.value = true
  dialogTitle.value = equName
  queryParams.pageNo = 1
  queryParams.pageSize = 10
  queryParams.equId = equId
  getList()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗


/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MonitorPingAnalysisApi.queryMonitorPingDetailResult(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleDateChange = () => {
  if (queryDate.value && queryDate.value.length === 2) {
    queryParams.startDate = queryDate.value[0]
    queryParams.endDate = queryDate.value[1]
  } else {
    queryParams.startDate = undefined
    queryParams.endDate = undefined
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

</script>
