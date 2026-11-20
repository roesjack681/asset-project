<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="115px"
    >
      <el-form-item label="IP地址" prop="ipAddr">
        <el-input
          v-model="queryParams.ipAddr"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <!-- <el-form-item label="电警类型" prop="djType">
        <el-select
          v-model="queryParams.djType"
          placeholder="请选择电警类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.DJ_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item> -->
      <el-form-item label="数据标签" prop="equLableList">
        <el-select
          v-model="queryParams.equLableList"
          multiple
          placeholder="请选择数据标签"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.ELECTRIC_LABLE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
       </el-select>
      </el-form-item>
      <el-form-item label="所属辖区" prop="dominion">
        <el-select
          v-model="queryParams.unitList"
          placeholder="请选择所属辖区"
          clearable
          multiple
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.GEO_INFO)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="卡口编号" prop="kkbh">
        <el-input
          v-model="queryParams.kkbh"
          placeholder="请输入卡口编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="ping状态" prop="pingStatus">
        <el-select
          v-model="queryParams.pingStatus"
          placeholder="请选择ping状态"
          clearable
          class="!w-240px"
        >
          <el-option label="全断" value="0"/>
          <el-option label="全通" value="1"/>
          <el-option label="时断时通" value="2"/>
        </el-select>
      </el-form-item>
      <el-form-item label="视频状态" prop="videoStatus">
        <el-select
          v-model="queryParams.videoStatus"
          placeholder="请选择视频状态"
          clearable
          class="!w-240px"
        >
          <el-option label="全断" value="0"/>
          <el-option label="全通" value="1"/>
          <el-option label="时断时通" value="2"/>
        </el-select>
      </el-form-item>
      <el-form-item label="有无有效违法" prop="hasValidSurveilCnt">
        <el-select
          v-model="queryParams.hasValidSurveilCnt"
          placeholder="请选择有无有效违法"
          clearable
          class="!w-240px"
        >
          <el-option label="无" value="0"/>
          <el-option label="有" value="1"/>
        </el-select>
      </el-form-item>
      <el-form-item label="有效违法数" prop="validSurveilCnt">
        <div style="display: flex">
          <el-select
            v-model="queryParams.validSurveilCntOperator"
            clearable
            style="margin-right: 5px; width: 110px;"
          >
            <el-option label="大于" value="1"/>
            <el-option label="大于等于" value="2"/>
            <el-option label="小于" value="3"/>
            <el-option label="小于等于" value="4"/>
            <el-option label="等于" value="5"/>
          </el-select>
          <el-input
            v-model="queryParams.validSurveilCnt"
            placeholder="有效违法数"
            clearable
            style="margin-right: 5px; width: 124px;"
            @keyup.enter="handleQuery"
          />
        </div>
      </el-form-item>
      <el-form-item label="无效违法数" prop="invalidSurveilCnt">
        <div style="display: flex">
          <el-select
            v-model="queryParams.invalidSurveilCntOperator"
            clearable
            style="margin-right: 5px; width: 110px;"
          >
            <el-option label="大于" value="1"/>
            <el-option label="大于等于" value="2"/>
            <el-option label="小于" value="3"/>
            <el-option label="小于等于" value="4"/>
            <el-option label="等于" value="5"/>
          </el-select>
          <el-input
            v-model="queryParams.invalidSurveilCnt"
            placeholder="无效违法数"
            clearable
            style="margin-right: 5px; width: 124px;"
            @keyup.enter="handleQuery"
          />
        </div>
      </el-form-item>
      <el-form-item label="未审违法数" prop="noSurveilCnt">
        <div style="display: flex">
          <el-select
            v-model="queryParams.noSurveilCntOperator"
            clearable
            style="margin-right: 5px; width: 110px;"
          >
            <el-option label="大于" value="1"/>
            <el-option label="大于等于" value="2"/>
            <el-option label="小于" value="3"/>
            <el-option label="小于等于" value="4"/>
            <el-option label="等于" value="5"/>
          </el-select>
          <el-input
            v-model="queryParams.noSurveilCnt"
            placeholder="有效违法率"
            clearable
            style="margin-right: 5px; width: 124px;"
            @keyup.enter="handleQuery"
          />
        </div>
      </el-form-item>
      <el-form-item label="有效违法率" prop="validSurveilRate">
        <div style="display: flex">
          <el-select
            v-model="queryParams.validSurveilOperator"
            clearable
            style="margin-right: 5px; width: 110px;"
          >
            <el-option label="大于" value="1"/>
            <el-option label="大于等于" value="2"/>
            <el-option label="小于" value="3"/>
            <el-option label="小于等于" value="4"/>
            <el-option label="等于" value="5"/>
          </el-select>
          <el-input
            v-model="queryParams.validSurveilRate"
            placeholder="有效违法率"
            clearable
            style="margin-right: 5px; width: 124px;"
            @keyup.enter="handleQuery"
          />
        </div>
      </el-form-item>
      <el-form-item label="有无识别准确率" prop="hasRecognitionAccuracyRate">
        <el-select
          v-model="queryParams.hasRecognitionAccuracyRate"
          placeholder="请选择有无有效违法"
          clearable
          class="!w-240px"
        >
          <el-option label="无" value="0"/>
          <el-option label="有" value="1"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-dropdown style="margin: 0 10px" trigger="click">
          <el-button type="primary" plain><Icon icon="ep:plus" class="mr-5px" /> 导入</el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>
                <span @click="openImportForm()" v-hasPermi="['traffic:vio-equipment-monitor-analysis:import']">导入违法抓拍分析</span>
              </el-dropdown-item>
              <el-dropdown-item>
                <span @click="openImportInvalidReasonForm()" v-hasPermi="['traffic:vio-equipment-monitor-analysis:import']">导入无效原因分析</span>
              </el-dropdown-item>
              <el-dropdown-item>
                <span @click="openImportIllegalFormRef()" v-hasPermi="['traffic:vio-equipment-monitor-analysis:import']">导入有效违法行为分析</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <el-dropdown style="margin: 0 10px" trigger="click">
          <el-button type="success" plain><Icon icon="ep:download" class="mr-5px" /> 导出</el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>
                <span @click="handleExport" v-hasPermi="['traffic:vio-equipment-monitor-analysis:export']">导出违法抓拍分析</span>
              </el-dropdown-item>
              <el-dropdown-item>
                <span @click="handleInvalidReasonExport" v-hasPermi="['traffic:vio-equipment-monitor-analysis:export']">导出无效原因分析</span>
              </el-dropdown-item>
              <el-dropdown-item>
                <span @click="handleIllegalExport" v-hasPermi="['traffic:vio-equipment-monitor-analysis:export']">导出有效违法行为分析</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          @click="openImportForm()"-->
<!--          v-hasPermi="['traffic:vio-equipment-monitor-analysis:import']"-->
<!--        >-->
<!--          <Icon icon="ep:plus" class="mr-5px" /> 导入违法抓拍分析-->
<!--        </el-button>-->
<!--        <el-button type="success" plain @click="handleExport" :loading="exportLoading" v-hasPermi="['traffic:vio-equipment-monitor-analysis:export']">-->
<!--          <Icon icon="ep:download" class="mr-5px" /> 导出违法抓拍分析-->
<!--        </el-button>-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          @click="openImportInvalidReasonForm()"-->
<!--          v-hasPermi="['traffic:vio-equipment-monitor-analysis:import']"-->
<!--        >-->
<!--          <Icon icon="ep:plus" class="mr-5px" /> 导入无效原因分析-->
<!--        </el-button>-->
<!--        <el-button type="success" plain @click="handleInvalidReasonExport" :loading="exportLoading" v-hasPermi="['traffic:vio-equipment-monitor-analysis:export']">-->
<!--          <Icon icon="ep:download" class="mr-5px" /> 导出无效原因分析-->
<!--        </el-button>-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          @click="openImportIllegalFormRef()"-->
<!--          v-hasPermi="['traffic:vio-equipment-monitor-analysis:import']"-->
<!--        >-->
<!--          <Icon icon="ep:plus" class="mr-5px" /> 导入有效违法行为分析-->
<!--        </el-button>-->
<!--        <el-button type="success" plain @click="handleIllegalExport" :loading="exportLoading" v-hasPermi="['traffic:vio-equipment-monitor-analysis:export']">-->
<!--          <Icon icon="ep:download" class="mr-5px" /> 导出有效违法行为分析-->
<!--        </el-button>-->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column align="center" prop="memberName">
        <template #header>
          <span>杆件</span><span style="color: red;">(道路名称/交叉口名称/方向/杆件编号)</span>
        </template>
      </el-table-column>
      <el-table-column label="ip地址" align="center" prop="ipAddr" />
      <el-table-column label="ip通断检测次数" align="center">
        <el-table-column label="正常次数" align="center" prop="pingNormalCnt" />
        <el-table-column label="异常次数" align="center" prop="pingAbnormalCnt" />
      </el-table-column>
      <el-table-column label="拉流检测次数" align="center">
        <el-table-column label="正常次数" align="center" prop="videoNormalCnt" />
        <el-table-column label="异常次数" align="center" prop="videoAbnormalCnt" />
      </el-table-column>

      <el-table-column label="违法检测次数" align="center">
        <el-table-column label="有效违法次数" align="center" prop="validSurveilCnt">
          <template #default="scope">
            {{scope.row.validSurveilCnt}}
            <img v-if="scope.row.validSurveilCnt>0" @click="openChartForm('zxt', scope.row)" style="vertical-align: middle; margin-top: -6px;width: 20px;height: auto;margin-left: 4px;cursor:pointer;" src="../../../assets/imgs/zxt.png" alt=""/>
            <img v-if="scope.row.validSurveilCnt>0" @click="openChartForm('bt-yxwfcs', scope.row)" style="width: 14px;height: auto;margin-left: 4px;cursor:pointer;" src="../../../assets/imgs/bingtu.png" alt=""/>
          </template>
        </el-table-column>
        <el-table-column label="无效违法次数" align="center" prop="invalidSurveilCnt">
          <template #default="scope">
            {{scope.row.invalidSurveilCnt}}
            <img v-if="scope.row.invalidSurveilCnt>0" @click="openChartForm('bt', scope.row)" style="width: 14px;height: auto;margin-left: 4px;cursor:pointer;" src="../../../assets/imgs/bingtu.png" alt=""/>
          </template>
        </el-table-column>
        <el-table-column label="未审违法次数" align="center" prop="noSurveilCnt" />
        <el-table-column label="有效违法率" align="center" prop="validSurveilRate" />
      </el-table-column>
      <el-table-column label="识别准确率" align="center" prop="recognitionAccuracyRate" />
      <el-table-column label="检测天数" align="center" prop="monitorCnt" />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('detail', scope.row)" v-hasPermi="['traffic:vio-equipment-monitor-analysis:detail']">详情</el-button>
          <el-button link type="primary" @click="openRemarkForm(scope.row)" v-hasPermi="['traffic:vio-equipment-monitor-analysis:remark']">备注</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <VioEquipmentMonitorAnalysisForm ref="formRef" @success="getList" />
  <VioEquipmentMonitorChartForm ref="formChartRef" @success="getList" />
  <VioDailySituationExportForm ref="detailExportRef" @success="getList" />
  <VioInvalidReasonExportForm ref="invalidReasonExportRef" @success="getList" />
  <VioInvalidReasonImportForm ref="importInvalidReasonFormRef" @success="getList" />
  <VioDailySituationImportForm ref="importFormRef" @success="getList" />
  <VioEquipmentMonitorRemarkForm ref="formRemarkRef" @success="getList" />
  <VioIllegalReasonImportForm ref="importIllegalFormRef" @success="getList" />
  <VioIllegalReasonExportForm ref="illegalExportRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { VioEquipmentMonitorAnalysisApi, VioEquipmentMonitorAnalysisVO } from '@/api/traffic/vioequipmentmonitoranalysis'
import VioDailySituationExportForm from './VioDailySituationExportForm.vue'
import VioInvalidReasonExportForm from './VioInvalidReasonExportForm.vue'
import VioEquipmentMonitorAnalysisForm from './VioEquipmentMonitorAnalysisForm.vue'
import VioEquipmentMonitorChartForm from './VioEquipmentMonitorChartForm.vue'
import VioDailySituationImportForm from './VioDailySituationImportForm.vue'
import VioInvalidReasonImportForm from './VioInvalidReasonImportForm.vue'
import VioEquipmentMonitorRemarkForm from './VioEquipmentMonitorRemarkForm.vue'

import VioIllegalReasonImportForm from './VioIllegalReasonImportForm.vue'
import VioIllegalReasonExportForm from './VioIllegalReasonExportForm.vue'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";

/** 集指设备检测分析 列表 */
defineOptions({ name: 'TrafficVioEquipmentMonitorAnalysis' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<VioEquipmentMonitorAnalysisVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equId: undefined,
  kkbh: undefined,
  sbbh: undefined,
  videoNormalCnt: undefined,
  videoAbnormalCnt: undefined,
  videoHealthRate: undefined,
  pingNormalCnt: undefined,
  pingAbnormalCnt: undefined,
  pingHealthRate: undefined,
  noSurveilCnt: undefined,
  validSurveilCnt: undefined,
  invalidSurveilCnt: undefined,
  recognitionAccuracyRate: undefined,
  monitorCnt: undefined,
  createTime: [],
  deletedTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await VioEquipmentMonitorAnalysisApi.getVioEquipmentMonitorAnalysisPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
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

/** 添加/修改操作 */
const formRef = ref()
const formChartRef = ref()
const formRemarkRef = ref()
const openForm = (type: string, row: any) => {
  formRef.value.open(type, row.equId, row.lastRepairTime)
}

const openChartForm = (type: string, row: any) => {
  formChartRef.value.open(type, row.ipAddr, row.sbbh)
}

const openRemarkForm = (row: any) => {
  formRemarkRef.value.open(row.sbbh, row.ipAddr)
}

const importFormRef = ref()
const openImportForm = () => {
  importFormRef.value.open()
}

const importInvalidReasonFormRef = ref()
const openImportInvalidReasonForm = () => {
  importInvalidReasonFormRef.value.open()
}

/** 导出按钮操作 */
const detailExportRef = ref()
const handleExport = async () => {
  // try {
  //   // 导出的二次确认
  //   await message.exportConfirm()
  //   // 发起导出
  //   exportLoading.value = true
  //   const data = await VioEquipmentMonitorAnalysisApi.exportVioEquipmentMonitorAnalysis(queryParams)
  //   download.excel(data, '集指设备检测分析.xls')
  // } catch {
  // } finally {
  //   exportLoading.value = false
  // }
  detailExportRef.value.open()
}

/** 导出按钮操作 */
const invalidReasonExportRef = ref()
const handleInvalidReasonExport = async () => {
  invalidReasonExportRef.value.open()
}

const importIllegalFormRef = ref()
const openImportIllegalFormRef = () => {
  importIllegalFormRef.value.open()
}

/** 导出按钮操作 */
const illegalExportRef = ref()
const handleIllegalExport = async () => {
  illegalExportRef.value.open()
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
