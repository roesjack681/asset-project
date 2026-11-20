<template>
  <Dialog :title="dialogTitle" width="800" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      label-width="100px"
      v-loading="formLoading"
    >
      <div class="form-box-wrapper">
<!--        <div class="addform-title"><img style="width: auto" src="../../../assets/imgs/anwz-icon.png" alt=""/>卡口位置选择</div>-->
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="24">
            <el-form-item label-width="0">
              <div style="width: 100%">
                <ChoosePointHulankakou :startPosition="props.startPosition" :endPosition="props.endPosition" :point="props.point" type="detail" @success="handlePointSuccess" ref="choosePointRef"/>
              </div>
            </el-form-item>
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
import { OpsSignBoardApi, OpsSignBoardVO } from '@/api/traffic/opssignboard'
import {DICT_TYPE, getStrDictOptions} from "@/utils/dict";
import {autocompleteOpsIntersection} from "@/api/bigdata";

/** 标志牌 表单 */
defineOptions({ name: 'OpsSignBoardForm' })
const props = defineProps({
  startPosition: {
    type: Array,
    default: () => [],
  },
  endPosition: {
    type: Array,
    default: () => [],
  },
  point: {
    type: Array,
    default: () => [],
  },
})
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({})

const options = ref([])
const loading = ref(false)

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
const formRef = ref() // 表单 Ref
const choosePointRef = ref() // 选择地图 Ref
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = '开口位置'
  formType.value = type
  setTimeout(() => {
    choosePointRef.value.initMap()
  }, 160)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if(!formData.value.lng) {
    return message.error('选择开口位置')
  }
  emit('success', formData.value)
  dialogVisible.value = false
}
const handlePointSuccess = (data) => {
  formData.value = data
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equName: undefined,
    equType: undefined,
    installDate: undefined,
    adress: undefined,
    lng: undefined,
    lat: undefined,
    direction: undefined,
    labelContent: undefined,
    specifications: undefined,
    ggunit: undefined,
    num: undefined,
    unit: undefined,
    dominion: undefined,
    manufacturer: undefined,
    installManufacturer: undefined,
    deletedTime: undefined
  }
  formRef.value?.resetFields()
}
</script>
