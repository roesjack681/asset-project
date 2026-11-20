<template>
  <div>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <template v-for="(i, index) in myTableColumn" :key="index">
        <el-table-column :label="i.label" v-if="i.label==='安装时间'" align="center" :prop="i.prop" :formatter="dateFormatter2" width="160px"/>
        <el-table-column :label="i.label" v-else align="center" :prop="i.prop"/>
      </template>
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('detail', scope.row.id)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
      <PaginationSize
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />

    <OpsSignalForm ref="formSignalRef" @success="getList" /> <!--信号机-->
    <OpsRadarForm ref="forRadarmRef" @success="getList" /> <!--雷达-->
    <OpsMemberForm ref="formMemberRef" @success="getList" />  <!--杆件-->
    <OpsBigledForm ref="formLedRef" @success="getList" />
    <OpsGuardrailForm ref="formGuardrailRef" @success="getList" /><!--护栏-->
    <OpsElectricPoliceForm ref="formElectricPoliceRef" @success="getList" /><!--电警-->
    <OpsSignBoardForm ref="formSignBoardRef" @success="getList" /><!--标志牌-->
    <OpsSignalLightForm ref="formSignalLightRef" @success="getList" /><!--信号灯-->
    <OpsMarklineForm ref="formMarklineRef" @success="getList" /><!--标线-->
  </div>
</template>

<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter2 } from '@/utils/formatTime'
import download from '@/utils/download'
import { OpsBigledApi, OpsBigledVO } from '@/api/traffic/opsbigled'
import {isString} from "@/utils/is";
import OpsSignalForm from "@/views/traffic/optsignal/OpsSignalForm.vue";
import OpsRadarForm from "@/views/traffic/optradar/OpsRadarForm.vue";
import OpsMemberForm from "@/views/traffic/optmember/OpsMemberForm.vue";
import OpsGuardrailForm from "@/views/traffic/optguardrail/OpsGuardrailForm.vue";
import OpsElectricPoliceForm from "@/views/traffic/optelectricpolice/OpsElectricPoliceForm.vue";
import OpsSignBoardForm from "@/views/traffic/opssignboard/OpsSignBoardForm.vue";
import OpsSignalLightForm from "@/views/traffic/opssignallight/OpsSignalLightForm.vue";
import OpsMarklineForm from "@/views/traffic/opsmarkline/OpsMarklineForm.vue";
import OpsBigledForm from "@/views/traffic/opsbigled/OpsBigledForm.vue";

/** 大屏 列表 */
defineOptions({ name: 'AllTypeTable' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<OpsBigledVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const props = defineProps({
  searchParams: {
    type: Object,
    default: () => {}
  },
  options: {
    type: Object,
    default: () => {}
  },
  equType: {
    type: String,
    default: ''
  }
})
const queryParams = reactive({
  pageNo: 1,
  pageSize: 5,
  ...props.searchParams
})
const formSignalRef = ref()
const forRadarmRef = ref()
const formMemberRef = ref()
const formLedRef = ref()
const formGuardrailRef = ref()
const formElectricPoliceRef = ref()
const formSignBoardRef = ref()
const formSignalLightRef = ref()
const formMarklineRef = ref()
const refobj = {
  '信号机': formSignalRef,
  '标线': formMarklineRef,
  '标志牌': formSignBoardRef,
  '信号灯': formSignalLightRef,
  '雷达': forRadarmRef,
  '电警': formElectricPoliceRef,
  '杆件': formMemberRef,
  '大屏': formLedRef,
  '护栏': formGuardrailRef,
}
const myTableColumn = computed(() => {
  const result = []
  if(props.searchParams.intersectionName && !props.searchParams.dominion && !props.searchParams.status) {
    props.options.column.forEach(e => {
      if(e.prop === 'intersectionName') {
        result.unshift(e)
      } else {
        result.push(e)
      }
    })
  } else if (!props.searchParams.intersectionName && props.searchParams.dominion && !props.searchParams.status) {
    props.options.column.forEach(e => {
      if(e.prop === 'dominion') {
        result.unshift(e)
      } else {
        result.push(e)
      }
    })
  }  else if (!props.searchParams.intersectionName && !props.searchParams.dominion && props.searchParams.status) {
    props.options.column.forEach(e => {
      if(e.prop === 'status') {
        result.unshift(e)
      } else {
        result.push(e)
      }
    })
  } else {
    props.options.column.forEach(e => {
      result.push(e)
    })
  }
  return result
})
/** 查询列表 */
const getList = async () => {
  // myTableColumn.value = sortMyTableColumns(props.options.column)
  loading.value = true
  try {
    const data = await props.options.list(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
defineExpose({ getList }) // 提供 open 方法
/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  setTimeout(() => {
    getList()
  }, 1000)
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  refobj[props.equType].value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await OpsBigledApi.deleteOpsBigled(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await OpsBigledApi.exportOpsBigled(queryParams)
    download.excel(data, '大屏.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
