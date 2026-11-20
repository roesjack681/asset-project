<template>
  <div class="asset" id="assetAccountAll">
    <div class="search-area" id="search-area">
      <el-form
        id="form"
        label-position="top"
        label-width="80px"
        :model="queryParams"
        ref="queryFormRef"
      >
        <el-row :gutter="26">
          <el-col :span="7">
            <el-form-item label="路口" prop="intersectionName">
              <el-select
                style="width: 100%"
                v-model="queryParams.intersectionName"
                filterable
                remote
                reserve-keyword
                placeholder="路口名称"
                :remote-method="remoteMethod"
                :loading="loading">
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.ldmc"
                  :value="item.ldmc"/>
              </el-select>
<!--              <el-input placeholder="路口名称" v-model="queryParams.intersectionName"/>-->
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="管辖单位" prop="dominion">
              <el-select
                v-model="queryParams.dominion"
                placeholder="请选择管辖单位"
                clearable
                class="!w-240px"
              >
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.DOMINION)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="设备状态" prop="status">
              <el-select clearable v-model="queryParams.status" placeholder="请选择状态">
                <el-option
                  v-for="dict in getStrDictOptions(DICT_TYPE.EQU_STATUS)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.label"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5" style="padding-top: 30px">
            <el-form-item>
              <el-button class="btn1" type="primary" @click="handleQuery">查询</el-button>
              <el-button class="btn1" type="primary" @click="handleExport">导出</el-button>
              <el-button class="btn2" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="options" id="options">
        <span style="font-size: 14px;color: #4B5563">设备类型:</span>
        <el-checkbox-group
          v-model="state.checkedCities"
          @change="handleCheckedCitiesChange"
        >
          <span v-for="city in state.cities" class="options-item" :key="city" :class="isActive(city)">
            <el-checkbox :label="city">{{city}}</el-checkbox>
          </span>
        </el-checkbox-group>
        <span @click="handleCheckAllChange" style="font-size: 14px;color: #2563EB">全选</span>
<!--        <el-checkbox-->
<!--          :indeterminate="state.isIndeterminate"-->
<!--          v-model="state.checkAll"-->
<!--          @change="handleCheckAllChange"-->
<!--        >全选</el-checkbox>-->
      </div>
    </div >
    <div class="chart" id="chart">
      <div class="chart-left">
        <div class="chart-title">设备数量统计 <span class="title-right">共计 {{basedata.equNum}}台</span></div>
        <div class="chart-left-list">
          <div class="chart-left-item">
            <img src="/xhj_icon.png" alt=""/>
            <div class="chart-left-progress">
              <div style="margin-bottom: 5px;font-size: 14px">信号机 <span class="number">{{basedata.signalNum}}</span></div>
<!--              <el-progress :text-inside="true" color="#9333EA" :stroke-width="9" :percentage="(basedata.signalNum/basedata.maxNumber) * 100"-->
<!--              ><span> </span></el-progress> -->
              <el-progress :text-inside="true" color="#9333EA" :stroke-width="9" :percentage="basedata.signalProp"
              ><span> </span></el-progress>
            </div>
          </div>
          <div class="chart-left-item">
            <img src="/bx_icon.png" alt=""/>
            <div class="chart-left-progress">
              <div style="margin-bottom: 5px;font-size: 14px">标线 <span class="number">{{basedata.marklineNum}}</span></div>
              <el-progress :text-inside="true" color="#16A34A" :stroke-width="9" :percentage="(basedata.marklineNum/basedata.maxNumber) * 100"
              ><span> </span></el-progress>
            </div>
          </div>
          <div class="chart-left-item">
            <img src="/bzp_icon.png" alt=""/>
            <div class="chart-left-progress">
              <div style="margin-bottom: 5px;font-size: 14px">标志牌 <span class="number">{{basedata.boardNum}}</span></div>
              <el-progress :text-inside="true" color="#EA580C" :stroke-width="9" :percentage="(basedata.boardNum/basedata.maxNumber) * 100"
              ><span> </span></el-progress>
            </div>
          </div>
          <div class="chart-left-item">
            <img src="/xhd_icon.png" alt=""/>
            <div class="chart-left-progress">
              <div style="margin-bottom: 5px;font-size: 14px">信号灯 <span class="number">{{basedata.signalLightNum}}</span></div>
              <el-progress :text-inside="true" color="#4F46E5" :stroke-width="9" :percentage="(basedata.signalLightNum/basedata.maxNumber) * 100"
              ><span> </span></el-progress>
            </div>
          </div>
          <div class="chart-left-item">
            <img src="/ld_icon.png" alt=""/>
            <div class="chart-left-progress">
              <div style="margin-bottom: 5px;font-size: 14px">雷达 <span class="number">{{basedata.radarNum}}</span></div>
              <el-progress :text-inside="true" color="#DC2626" :stroke-width="9" :percentage="(basedata.radarNum/basedata.maxNumber) * 100"
              ><span> </span></el-progress>
            </div>
          </div>
          <div class="chart-left-item">
            <img src="/dj_icon.png" alt=""/>
            <div class="chart-left-progress">
              <div style="margin-bottom: 5px;font-size: 14px">电警 <span class="number">{{basedata.electricPoliceNum}}</span></div>
              <el-progress :text-inside="true" color="#0D9488" :stroke-width="9" :percentage="(basedata.electricPoliceNum/basedata.maxNumber) * 100"
              ><span> </span></el-progress>
            </div>
          </div>
          <div class="chart-left-item">
            <img src="/gj_icon.png" alt=""/>
            <div class="chart-left-progress">
              <div style="margin-bottom: 5px;font-size: 14px">杆件 <span class="number">{{basedata.memberNum}}</span></div>
              <el-progress :text-inside="true" color="#0891B2" :stroke-width="9" :percentage="(basedata.memberNum/basedata.maxNumber) * 100"
              ><span> </span></el-progress>
            </div>
          </div>
          <div class="chart-left-item">
            <img src="/dp_icon.png" alt=""/>
            <div class="chart-left-progress">
              <div style="margin-bottom: 5px;font-size: 14px">大屏 <span class="number">{{basedata.bigLedNum}}</span></div>
              <el-progress :text-inside="true" color="#059669" :stroke-width="9" :percentage="(basedata.bigLedNum/basedata.maxNumber) * 100"
              ><span> </span></el-progress>
            </div>
          </div>
          <div class="chart-left-item">
            <img src="/hl_icon.png" alt=""/>
            <div class="chart-left-progress">
              <div style="margin-bottom: 5px;font-size: 14px">护栏 <span class="number">{{basedata.guardrailNum}}</span></div>
              <el-progress :text-inside="true" color="#D97706" :stroke-width="9" :percentage="(basedata.guardrailNum/basedata.maxNumber) * 100"
              ><span> </span></el-progress>
            </div>
          </div>
        </div>
      </div>
      <div class="chart-right">
        <div class="chart-title">设备状态统计
          <span class="title-right">
            <span>正常</span>
            <span>维修中</span>
            <span>故障</span>
          </span>
        </div>
        <div class="chart-right-list">
          <div class="chart-right-item">
            <div class="chart-right-progress">
              <div style="margin-bottom: 22px;font-size: 14px">信号机
                <span class="number" style="display: flex">
                  <span style="margin-left: 15px; color: #2563EB">正常 <div>{{basedata.signalZNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #D97706">维修中 <div>{{basedata.signalWNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #475569">故障 <div>{{basedata.signalGNumPercent}}%</div></span>
                </span>
              </div>
            </div>
            <div style="width: 100%;clear: both">
              <div class="myprogress">
                <div class="pro1" :style="`width: ${basedata.signalZNumPercent}%`"></div>
                <div class="pro2" :style="`width: ${basedata.signalWNumPercent}%`"></div>
                <div class="pro3" :style="`width: ${basedata.signalGNumPercent}%`"></div>
              </div>
            </div>
          </div>
          <div class="chart-right-item">
            <div class="chart-right-progress">
              <div style="margin-bottom: 22px;font-size: 14px">标线
                <span class="number" style="display: flex">
                  <span style="margin-left: 15px; color: #2563EB">正常 <div>{{basedata.marklineZNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #D97706">维修中 <div>{{basedata.marklineWNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #475569">故障 <div>{{basedata.marklineGNumPercent}}%</div></span>
                </span>
              </div>
            </div>
            <div style="width: 100%;clear: both">
              <div class="myprogress">
                <div class="pro1" :style="`width: ${basedata.marklineZNumPercent}%`"></div>
                <div class="pro2" :style="`width: ${basedata.marklineWNumPercent}%`"></div>
                <div class="pro3" :style="`width: ${basedata.marklineGNumPercent}%`"></div>
              </div>
            </div>
          </div>
          <div class="chart-right-item">
            <div class="chart-right-progress">
              <div style="margin-bottom: 22px;font-size: 14px">标志牌
                <span class="number" style="display: flex">
                  <span style="margin-left: 15px; color: #2563EB">正常 <div>{{basedata.boardZNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #D97706">维修中 <div>{{basedata.boardWNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #475569">故障 <div>{{basedata.boardGNumPercent}}%</div></span>
                </span>
              </div>
            </div>
            <div style="width: 100%;clear: both">
              <div class="myprogress">
                <div class="pro1" :style="`width: ${basedata.boardZNumPercent}%`"></div>
                <div class="pro2" :style="`width: ${basedata.boardWNumPercent}%`"></div>
                <div class="pro3" :style="`width: ${basedata.boardGNumPercent}%`"></div>
              </div>
            </div>
          </div>
          <div class="chart-right-item">
            <div class="chart-right-progress">
              <div style="margin-bottom: 22px;font-size: 14px">信号灯
                <span class="number" style="display: flex">
                  <span style="margin-left: 15px; color: #2563EB">正常 <div>{{basedata.signalLightZNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #D97706">维修中 <div>{{basedata.signalLightWNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #475569">故障 <div>{{basedata.signalLightGNumPercent}}%</div></span>
                </span>
              </div>
            </div>
            <div style="width: 100%;clear: both">
              <div class="myprogress">
                <div class="pro1" :style="`width: ${basedata.signalLightZNumPercent}%`"></div>
                <div class="pro2" :style="`width: ${basedata.signalLightWNumPercent}%`"></div>
                <div class="pro3" :style="`width: ${basedata.signalLightGNumPercent}%`"></div>
              </div>
            </div>
          </div>
          <div class="chart-right-item">
            <div class="chart-right-progress">
              <div style="margin-bottom: 22px;font-size: 14px">电警
                <span class="number" style="display: flex">
                  <span style="margin-left: 15px; color: #2563EB">正常 <div>{{basedata.electricPoliceZNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #D97706">维修中 <div>{{basedata.electricPoliceWNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #475569">故障 <div>{{basedata.electricPoliceGNumPercent}}%</div></span>
                </span>
              </div>
            </div>
            <div style="width: 100%;clear: both">
              <div class="myprogress">
                <div class="pro1" :style="`width: ${basedata.electricPoliceZNumPercent}%`"></div>
                <div class="pro2" :style="`width: ${basedata.electricPoliceWNumPercent}%`"></div>
                <div class="pro3" :style="`width: ${basedata.electricPoliceGNumPercent}%`"></div>
              </div>
            </div>
          </div>
          <div class="chart-right-item">
            <div class="chart-right-progress">
              <div style="margin-bottom: 22px;font-size: 14px">大屏
                <span class="number" style="display: flex">
                  <span style="margin-left: 15px; color: #2563EB">正常 <div>{{basedata.bigLedZNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #D97706">维修中 <div>{{basedata.bigLedWNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #475569">故障 <div>{{basedata.bigLedGNumPercent}}%</div></span>
                </span>
              </div>
            </div>
            <div style="width: 100%;clear: both">
              <div class="myprogress">
                <div class="pro1" :style="`width: ${basedata.bigLedZNumPercent}%`"></div>
                <div class="pro2" :style="`width: ${basedata.bigLedWNumPercent}%`"></div>
                <div class="pro3" :style="`width: ${basedata.bigLedGNumPercent}%`"></div>
              </div>
            </div>
          </div>
          <div class="chart-right-item">
            <div class="chart-right-progress">
              <div style="margin-bottom: 22px;font-size: 14px">杆件
                <span class="number" style="display: flex">
                  <span style="margin-left: 15px; color: #2563EB">正常 <div>{{basedata.memberZNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #D97706">维修中 <div>{{basedata.memberWNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #475569">故障 <div>{{basedata.memberGNumPercent}}%</div></span>
                </span>
              </div>
            </div>
            <div style="width: 100%;clear: both">
              <div class="myprogress">
                <div class="pro1" :style="`width: ${basedata.memberZNumPercent}%`"></div>
                <div class="pro2" :style="`width: ${basedata.memberWNumPercent}%`"></div>
                <div class="pro3" :style="`width: ${basedata.memberGNumPercent}%`"></div>
              </div>
            </div>
          </div>
          <div class="chart-right-item">
            <div class="chart-right-progress">
              <div style="margin-bottom: 22px;font-size: 14px">雷达
                <span class="number" style="display: flex">
                  <span style="margin-left: 15px; color: #2563EB">正常 <div>{{basedata.radarZNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #D97706">维修中 <div>{{basedata.radarWNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #475569">故障 <div>{{basedata.radarGNumPercent}}%</div></span>
                </span>
              </div>
            </div>
            <div style="width: 100%;clear: both">
              <div class="myprogress">
                <div class="pro1" :style="`width: ${basedata.radarZNumPercent}%`"></div>
                <div class="pro2" :style="`width: ${basedata.radarWNumPercent}%`"></div>
                <div class="pro3" :style="`width: ${basedata.radarGNumPercent}%`"></div>
              </div>
            </div>
          </div>
          <div class="chart-right-item">
            <div class="chart-right-progress">
              <div style="margin-bottom: 22px;font-size: 14px">护栏
                <span class="number" style="display: flex">
                  <span style="margin-left: 15px; color: #2563EB">正常 <div>{{basedata.guardrailZNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #D97706">维修中 <div>{{basedata.guardrailWNumPercent}}%</div></span>
                  <span style="margin-left: 15px; color: #475569">故障 <div>{{basedata.guardrailGNumPercent}}%</div></span>
                </span>
              </div>
            </div>
            <div style="width: 100%;clear: both">
              <div class="myprogress">
                <div class="pro1" :style="`width: ${basedata.guardrailZNumPercent}%`"></div>
                <div class="pro2" :style="`width: ${basedata.guardrailWNumPercent}%`"></div>
                <div class="pro3" :style="`width: ${basedata.guardrailGNumPercent}%`"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="tablearea" id="tablearea" v-for="i in state.checkedCities" :key="'options-choose-' + i">
      <div class="options-choose">
        <div class="options-choose-item" :style="{color: cityOptionsObj2[i], borderColor: cityOptionsObj2[i]}">{{i}}</div>
      </div>
      <div class="alltype-table" style="clear:both;" v-if="state.checkedCities.length">
        <AllInfoTable :key="'table_' + i + '_' + date" :equType="i" ref="allTypeTableRef" :searchParams="queryParams" :options="urls[i]"/>
      </div>
      <div class="alltype-table" style="clear:both;" v-else>
        <el-empty :image-size="200"/>
      </div>
    </div>
  </div>
</template>


<script setup lang="ts">
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import {autocompleteOpsIntersection, baseEqu} from "@/api/bigdata";
import {dateFormatter} from "@/utils/formatTime";
import AllInfoTable from './AllInfoTable.vue'
import { ElLoading, LoadingOptionsResolved } from 'element-plus'
import {htmlToPDF} from './exportToPdf'
import {exportPDF} from './pdf'
const queryParams = reactive({})

const activeTab = ref('信号机')
const options = ref([])
const loading = ref(false)
const basedata = ref({})
const date =  ref(0)

import { OpsBigledApi } from '@/api/traffic/opsbigled'  //led
import { OpsSignalApi } from '@/api/traffic/optsignal'  //信号机
import { OpsMarklineApi } from '@/api/traffic/opsmarkline' //标线
import { OpsSignBoardApi } from '@/api/traffic/opssignboard' //标志牌
import { OpsSignalLightApi } from '@/api/traffic/opssignallight' //信号机灯
import { OpsRadarApi } from '@/api/traffic/optradar' //雷达
import { OpsElectricPoliceApi } from '@/api/traffic/optelectricpolice' //电警
import { OpsMemberApi } from '@/api/traffic/optmember' //杆件
import { OpsGuardrailApi } from '@/api/traffic/optguardrail' //护栏
const remoteMethod =  async (query) => {
  if (query !== '') {
    loading.value = true;
    const data = await autocompleteOpsIntersection({intersectionName: query})
    options.value = data
    loading.value = false;
  } else {
    options.value = [];
  }
}
const queryFormRef = ref() // 搜索的表单
// const cityOptions = ['信号机', '标线', '标志牌', '信号灯','雷达', '电警', '护栏','大屏', '杆件'];
const cityOptions = ['信号机', '标志牌', '信号灯','雷达', '电警', '护栏','大屏', '杆件'];
const cityOptionsObj2 = {
  '信号机': '#9333EA',
  // '标线': '#16A34A',
  '标志牌': '#EA580C',
  '信号灯': '#4F46E5',
  '雷达': '#DC2626',
  '电警': '#0D9488',
  '杆件': '#0891B2',
  '大屏': '#059669',
  '护栏': '#D97706',
}

const allTypeTableRef = ref()
const formSignalRef = ref()
const forRadarmRef = ref()
const formMemberRef = ref()
const formLedRef = ref()
const formGuardrailRef = ref()
const formElectricPoliceRef = ref()
const formSignBoardRef = ref()
const formSignalLightRef = ref()
const formMarklineRef = ref()
const urls = {
  '信号机': {
    list: OpsSignalApi.getOpsSignalPage,
    formRef: formSignalRef.value,
    column: [
      {label: '设备名称', prop: 'equName'},
      {label: '路口名称', prop: 'intersectionName'},
      {label: '安装位置', prop: 'adress'},
      {label: '安装时间', prop: 'installDate'},
      {label: '信号控制相位', prop: 'phase'},
      {label: '联网状态', prop: 'networkStatus'},
      {label: '信号机厂家', prop: 'manufacturer'},
      {label: '信号机型号', prop: 'equModel'},
      {label: '管辖单位', prop: 'dominion'},
      {label: '状态', prop: 'status'},
    ]
  },
  '标线': {
    list: OpsMarklineApi.getOpsMarklinePage,
    formRef: formMarklineRef,
    column: [
      {label: '路口名称', prop: 'intersectionName'},
      {label: '路段', prop: 'roadParts'},
      {label: '施画种类', prop: 'marklineSort'},
      {label: '数量', prop: 'marklineNum'},
      {label: '线宽', prop: 'lineWidth'},
      {label: '规格', prop: 'specifications'},
      {label: '比例', prop: 'proportion'},
      {label: '面积', prop: 'area'},
      {label: '施画单位', prop: 'paintingUnit'},
      {label: '管辖单位', prop: 'dominion'},
      {label: '状态', prop: 'status'},
    ]
  },
  '标志牌': {
    list: OpsSignBoardApi.getOpsSignBoardPage,
    formRef: formSignBoardRef,
    column: [
      // {label: '资产类型', prop: 'equType'},
      {label: '道路名称', prop: 'roadName'},
      {label: '路口名称', prop: 'intersectionName'},
      {label: '安装位置', prop: 'adress'},
      // {label: '安装时间', prop: 'installDate'},
      {label: '方向', prop: 'direction'},
      {label: '标牌内容', prop: 'labelContent'},
      {label: '数量', prop: 'num'},
      {label: '归属单位', prop: 'ggunit'},
      {label: '管辖单位', prop: 'dominion'},
      {label: '状态', prop: 'status'},
    ]
  },
  '信号灯': {
    list: OpsSignalLightApi.getOpsSignalLightPage,
    formRef: formSignalLightRef,
    column: [
      // {label: '资产类型', prop: 'equType'},
      {label: '路口名称', prop: 'intersectionName'},
      {label: '所在道路名称', prop: 'roadName'},
      {label: '设备名称', prop: 'equName'},
      {label: '排列方式', prop: 'arrangement'},
      {label: '安装位置', prop: 'adress'},
      {label: '灯具类型', prop: 'ledType'},
      // {label: '厂家', prop: 'manufacturer'},
      // {label: '型号', prop: 'equModel'},
      {label: '尺寸数据', prop: 'ledSize'},
      // {label: '灯头数量', prop: 'ledHeadNum'},
      // {label: '信号显示方式', prop: 'signalDisplayMode'},
      {label: '管辖单位', prop: 'dominion'},
      {label: '状态', prop: 'status'},
    ]
  },
  '雷达': {
    list: OpsRadarApi.getOpsRadarPage,
    formRef: forRadarmRef,
    column: [
      // {label: '资产类型', prop: 'equType'},
      // {label: '设备名称', prop: 'equName'},
      {label: '所在道路名称', prop: 'roadName'},
      {label: '路口名称', prop: 'intersectionName'},
      {label: '安装位置', prop: 'adress'},
      {label: '安装时间', prop: 'installDate'},
      {label: '厂家', prop: 'manufacturer'},
      {label: '型号', prop: 'equModel'},
      {label: '管辖单位', prop: 'dominion'},
      {label: '归属单位', prop: 'unit'},
      {label: '状态', prop: 'status'},
    ]
  },
  '电警': {
    list: OpsElectricPoliceApi.getOpsElectricPolicePage,
    formRef: formElectricPoliceRef,
    column: [
      // {label: '资产类型', prop: 'equType'},
      {label: '所在道路名称', prop: 'roadName'},
      {label: '路口名称', prop: 'intersectionName'},
      {label: '电警类型', prop: 'djType'},
      {label: '方向', prop: 'direction'},
      {label: '路网编号', prop: 'lwBh'},
      {label: '集指编号', prop: 'jzBh'},
      {label: '安装位置', prop: 'adress'},
      {label: '安装时间', prop: 'installDate'},
      {label: '厂家', prop: 'manufacturer'},
      {label: '型号', prop: 'equModel'},
      // {label: '镜头', prop: 'lens'},
      {label: '管辖单位', prop: 'dominion'},
      {label: '状态', prop: 'status'},
    ]
  },
  '杆件': {
    list: OpsMemberApi.getOpsMemberPage,
    formRef: formMemberRef,
    column: [
      // {label: '资产类型', prop: 'equType'},
      {label: '道路名称', prop: 'roadName'},
      {label: '路口名称', prop: 'intersectionName'},
      {label: '杆件类型', prop: 'memberType'},
      {label: '安装位置', prop: 'adress'},
      {label: '安装时间', prop: 'installDate'},
      {label: '高度', prop: 'hight'},
      {label: '副臂长度', prop: 'jibLength'},
      {label: '横杆长度', prop: 'barLength'},
      {label: '管辖单位', prop: 'dominion'},
      {label: '状态', prop: 'status'},
    ]
  },
  '大屏': {
    list: OpsBigledApi.getOpsBigledPage,
    formRef: formLedRef,
    column: [
      // {label: '资产类型', prop: 'equType'},
      // {label: '路口名称', prop: 'intersectionName'},
      {label: '屏幕朝向', prop: 'screenOrientation'},
      {label: '安装位置', prop: 'adress'},
      {label: '安装时间', prop: 'installDate'},
      {label: '大小', prop: 'size'},
      {label: '管辖单位', prop: 'dominion'},
      {label: '描述', prop: 'des'},
      {label: '备注', prop: 'remark'},
      {label: '状态', prop: 'status'},
    ]
  },
  '护栏': {
    list: OpsGuardrailApi.getOpsGuardrailPage,
    formRef: formGuardrailRef,
    column: [
      // {label: '资产类型', prop: 'equType'},
      {label: '路口名称', prop: 'intersectionName'},
      {label: '安装位置', prop: 'adress'},
      {label: '安装时间', prop: 'installDate'},
      {label: '型号', prop: 'model'},
      {label: '片数', prop: 'pieces'},
      {label: '总长（米数）', prop: 'totalLength'},
      {label: '底座', prop: 'pedestal'},
      {label: '人行车档', prop: 'humanDecency'},
      {label: '立柱车档', prop: 'stud'},
      {label: '管辖单位', prop: 'dominion'},
      {label: '状态', prop: 'status'},
    ]
  },
}
const cityOptionsObj = [
  {
    name: '信号机',
    color: '#9333EA'
  },
  {
    name: '标线',
    color: '#16A34A'
  },
  {
    name: '标志牌',
    color: '#EA580C'
  },
  {
    name: '信号灯',
    color: '#4F46E5'
  },
  {
    name: '雷达',
    color: '#DC2626'
  },
  {
    name: '电警',
    color: '#0D9488'
  },
  {
    name: '杆件',
    color: '#0891B2'
  },
  {
    name: '大屏',
    color: '#059669'
  },
  {
    name: '护栏',
    color: '#D97706'
  },
];
const state = reactive({
  checkAll: false,
  checkedCities: ['信号机', '标志牌', '信号灯','雷达', '电警', '护栏','大屏', '杆件'],
  // checkedCities: ['信号机'],
  cities: cityOptions,
  isIndeterminate: true,
});
const baseEquAPI = async () => {
  basedata.value = await baseEqu({
    dominion: queryParams.dominion,
    status: queryParams.status,
    intersectionName: queryParams.intersectionName,
  })
  basedata.value.maxNumber = 0
  for(let key in basedata.value) {
    if(basedata.value[key]=='null' || basedata.value[key]==null) basedata.value[key] = 0
    basedata.value[key] = Number(basedata.value[key])
    if(basedata.value[key]>=0) {
      if(basedata.value.maxNumber < basedata.value[key]) {
        basedata.value.maxNumber = basedata.value[key]
      }
    }
    //信号机
    basedata.value.signalZNumPercent = basedata.value.signalZNum?Math.round((basedata.value.signalZNum/basedata.value.signalNum) * 10000)/100:0
    basedata.value.signalWNumPercent = basedata.value.signalWNum?Math.round((basedata.value.signalWNum/basedata.value.signalNum) * 10000)/100:0
    basedata.value.signalGNumPercent =basedata.value.signalGNum?Math.round((100 - basedata.value.signalZNumPercent - basedata.value.signalWNumPercent)*100)/100:0
    //标线
    basedata.value.marklineZNumPercent = 100
    basedata.value.marklineWNumPercent = 0
    basedata.value.marklineGNumPercent = 0
    //标志牌
    basedata.value.boardZNumPercent = basedata.value.boardZNum?Math.round((basedata.value.boardZNum/basedata.value.boardNum) * 10000)/100:0
    basedata.value.boardWNumPercent = basedata.value.boardWNum?Math.round((basedata.value.boardWNum/basedata.value.boardNum) * 10000)/100:0
    basedata.value.boardGNumPercent = basedata.value.boardGNum?Math.round((100 - basedata.value.boardZNumPercent - basedata.value.boardWNumPercent)*100)/100: 0

    //信号灯
    basedata.value.signalLightZNumPercent = basedata.value.signalLightZNum?Math.round((basedata.value.signalLightZNum/basedata.value.signalLightNum) * 10000)/100:0
    basedata.value.signalLightWNumPercent = basedata.value.signalLightWNum?Math.round((basedata.value.signalLightWNum/basedata.value.signalLightNum) * 10000)/100:0
    basedata.value.signalLightGNumPercent = basedata.value.signalLightGNum?Math.round((100 - basedata.value.signalLightZNumPercent - basedata.value.signalLightWNumPercent)*100)/100:0

    //雷达
    basedata.value.radarZNumPercent = basedata.value.radarZNum? Math.round((basedata.value.radarZNum/basedata.value.radarNum) * 10000)/100:0
    basedata.value.radarWNumPercent = basedata.value.radarWNum? Math.round((basedata.value.radarWNum/basedata.value.radarNum) * 10000)/100:0
    basedata.value.radarGNumPercent = basedata.value.radarGNum? Math.round((100 - basedata.value.radarZNumPercent - basedata.value.radarWNumPercent)*100)/100:0

    //护栏
    basedata.value.guardrailZNumPercent = basedata.value.guardrailZNum? Math.round((basedata.value.guardrailZNum/basedata.value.guardrailNum) * 10000)/100:0
    basedata.value.guardrailWNumPercent = basedata.value.guardrailWNum? Math.round((basedata.value.guardrailWNum/basedata.value.guardrailNum) * 10000)/100:0
    basedata.value.guardrailGNumPercent = basedata.value.guardrailGNum? Math.round((100 - basedata.value.guardrailZNumPercent - basedata.value.guardrailWNumPercent)*100)/100:0

    //电警
    basedata.value.electricPoliceZNumPercent = basedata.value.electricPoliceZNum? Math.round((basedata.value.electricPoliceZNum/basedata.value.electricPoliceNum) * 10000)/100:0
    basedata.value.electricPoliceWNumPercent = basedata.value.electricPoliceWNum? Math.round((basedata.value.electricPoliceWNum/basedata.value.electricPoliceNum) * 10000)/100:0
    basedata.value.electricPoliceGNumPercent = basedata.value.electricPoliceGNum? Math.round((100 - basedata.value.electricPoliceZNumPercent - basedata.value.electricPoliceWNumPercent)*100)/100:0

    //led
    basedata.value.bigLedZNumPercent = basedata.value.bigLedZNum? Math.round((basedata.value.bigLedZNum/basedata.value.bigLedNum) * 10000)/100:0
    basedata.value.bigLedWNumPercent = basedata.value.bigLedWNum? Math.round((basedata.value.bigLedWNum/basedata.value.bigLedNum) * 10000)/100:0
    basedata.value.bigLedGNumPercent = basedata.value.bigLedGNum? Math.round((100 - basedata.value.bigLedZNumPercent - basedata.value.bigLedWNumPercent)*100)/100:0

    //杆件
    basedata.value.memberZNumPercent = basedata.value.memberZNum? Math.round((basedata.value.memberZNum/basedata.value.memberNum) * 10000)/100:0
    basedata.value.memberWNumPercent = basedata.value.memberWNum? Math.round((basedata.value.memberWNum/basedata.value.memberNum) * 10000)/100:0
    basedata.value.memberGNumPercent = basedata.value.memberGNum? Math.round((100 - basedata.value.memberZNumPercent - basedata.value.memberWNumPercent)*100)/100:0
  }
}
const handleQuery = () => {
  baseEquAPI()
  date.value = Date.now()
  allTypeTableRef.value.getList()
}
const handleExport = () => {
  // baseEquAPI()
  // date.value = Date.now()
  // allTypeTableRef.value.getList()
  setTimeout(() => {
    htmlToPDF('assetAccountAll')
    // const pdfDom: HTMLElement | null = document.getElementById('assetAccountAll') as HTMLElement
    // exportPDF('assetAccountAll')
  }, 1000)
}
const resetQuery = () => {
  queryFormRef.value.resetFields()
  baseEquAPI()
  date.value = Date.now()
  allTypeTableRef.value.getList()
}
const handleCheckAllChange = (val) => {
  state.checkedCities = val ? cityOptions : [];
  state.isIndeterminate = false;
};
const handleCheckedCitiesChange = (value) => {
  const checkedCount = value.length;
  state.checkAll = checkedCount === state.cities.length;
  state.isIndeterminate = checkedCount > 0 && checkedCount < state.cities.length;
};
const isActive = (item) => {
  if(state.checkedCities.join(',').indexOf(item) > -1) {
    return 'isactive'
  } else {
    return ''
  }
}
const chooseOneOption = (item) => {
  activeTab.value = item
  date.value = Date.now()
  allTypeTableRef.value.getList()
}

onMounted(async () => {
  date.value = Date.now()
  baseEquAPI()
})
</script>
<style lang="scss">
#assetAccountAll {
  width: 100%;
  height: auto;
  .search-area {
    width: 100%;
    padding: 15px;
    box-sizing: border-box;
    background: #ffffff;
    border-radius: 12px;
    ::v-deep .el-select__wrapper {
      border-radius: 20px;
    }
    ::v-deep .el-input__wrapper {
      border-radius: 20px;
    }
    .btn1 {
      border-radius: 10px;
      background: #2563EB;
      border-color: #2563EB;
    }
    .btn2 {
      border-radius: 10px;
    }
    .options {
      border-top: 1px solid #dddddd;
      display: flex;
      align-items: center;
      padding-top: 10px;
      .options-item {
        margin: 10px;
        border: 1px solid #dddddd;
        padding: 0 14px;
        display: inline-block;
        border-radius: 20px;
      }
      .isactive {
        background: #EFF6FF;
        border: 1px solid #2563EB;
      }
    }
  }
  .chart {
    width: 100%;
    display: flex;
    height: 620px;
    margin-top: 20px;
    .chart-title {
      font-size: 15px;
      font-weight: bold;
      .title-right {
        float: right;
        font-size: 14px;
        font-weight: normal;
        color: #6B7280;
        span {
          position: relative;
          margin-left: 5px;
          color: rgba(0, 0, 0, 0.5);
          &:nth-child(1) {
            &:before {
              display: inline-block;
              content: '';
              width: 10px;
              height: 10px;
              border-radius: 50%;
              background: #3B82F6;
              margin-right: 3px;
            }
          }
          &:nth-child(2) {
            &:before {
              display: inline-block;
              content: '';
              width: 10px;
              height: 10px;
              border-radius: 50%;
              background: #F59E0B;
              margin-right: 3px;
            }
          }
          &:nth-child(3) {
            &:before {
              display: inline-block;
              content: '';
              width: 10px;
              height: 10px;
              border-radius: 50%;
              background: #94A3B8;
              margin-right: 3px;
            }
          }
        }
      }
    }
    .chart-left {
      flex: 1;
      background: #ffffff;
      height: 100%;
      border-radius: 12px;
      margin-right: 6px;
      padding: 10px;
    }
    .chart-right {
      flex: 1;
      background: #ffffff;
      height: 100%;
      border-radius: 12px;
      margin-left: 6px;
      padding: 10px;
    }
  }
  .tablearea {
    height: auto;
    background: #ffffff;
    border-radius: 12px;
    //padding: 10px;
    margin-top: 20px;
    .options-choose {
      display: flex;
      border-bottom: 1px solid #dddddd;
      .options-choose-item {
        padding: 14px;
        margin-right: 6px;
        border-bottom: 2px solid transparent;
        cursor: pointer;
      }
    }
  }

  .chart-left-list {
    width: 100%;
    height: calc(100% - 36px);
    overflow: auto;
    margin-top: 10px;
    .chart-left-item {
      display: flex;
      align-items: end;
      padding: 4px 10px;
      margin-bottom: 10px;
      img {
        width: 36px;
        height: 36px;
        margin-right: 10px;
      }
      .chart-left-progress {
        margin-top: 10px;
        flex: 1;
        .number {
          float: right;
        }
      }
    }
  }
  .chart-right-list {
    width: 100%;
    height: calc(100% - 36px);
    overflow: auto;
    margin-top: 10px;
    display: flex;
    flex-wrap: wrap;
    .chart-right-item {
      width: 48%;
      align-items: end;
      box-sizing: border-box;
      background: #F9FAFB;
      margin: 1%;
      padding: 15px;
      border-radius: 10px;
      .chart-right-progress {
        .number {
          float: right;
        }
      }
    }
  }
  .myprogress {
    display: flex;
    width: 100%;
    height: 8px;
    background: #F3F4F6;
    border-radius: 10px;
    overflow: hidden;
    div {
      height: 8px;
    }
    .pro1 {
      background: #2563EB;
    }
    .pro2 {
      background: #F59E0B;
    }
    .pro3 {
      background: #94A3B8;
    }
  }
}
</style>
